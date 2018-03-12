import {ajax} from "rxjs/observable/dom/ajax";
import {
    RepositoryActionTypes, buildListsSetAllAction, NameChangeCommand,
    CreateItemCommand
} from './reducers/repository';
import {CommsActionTypes, endLoadingAction} from './reducers/comms';
import {ActionsObservable, combineEpics, Epic} from "redux-observable";
import {MiddlewareAPI} from "redux";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mapTo';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/takeUntil';
import 'rxjs/add/operator/concatMap';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/pluck';
import TodoList, {IdWrapper} from './domain/todoList';
import {AjaxResponse, Observable} from "rxjs";
import ValueAction from "../core/domain/ValueAction";

interface TodoListCustomResponse {
    ArrayList: TodoList[]
}

const postHeaders = { 'Content-Type': 'application/json' };

// these epics could all be combined to something more generic, and have
// the incoming action determine the url and payload
export const listsRequestEpic = (action$:ActionsObservable<any>, store:MiddlewareAPI<any>) =>
    // I need to fetch, and emit end:loading regardless
    action$.ofType(RepositoryActionTypes.FETCH_LISTS)
        .mergeMap(action =>
            ajax.getJSON("/api/todo/lists")
                .takeUntil(action$.ofType(CommsActionTypes.CANCEL))
                //although we don't have any Error handling setup so, the action is empty)
                .catch(error => Observable.of({type:CommsActionTypes.LOADING_ERROR, value: []}))
                .map((response:TodoListCustomResponse) => (buildListsSetAllAction(response.ArrayList)))
        )
    // ensure our action returned from the ajax call comes along, along with a loadingEnd message
    .mergeMap(action => Observable.of(action, endLoadingAction));


export const listsCreateEpic = (action$:ActionsObservable<ValueAction<string>>, store:MiddlewareAPI<any>) =>
    action$.ofType(RepositoryActionTypes.CREATE_LIST)
        .debounceTime(250) // don't spam the server if the user is typing or clicks multiple times
        .mergeMap(action =>
            ajax.post("/api/todo/lists", {name: action.value}, postHeaders)
                .takeUntil(action$.ofType(CommsActionTypes.CANCEL))
                //although we don't have any Error handling setup so, the action is empty)
                .catch(error => Observable.of({type:CommsActionTypes.LOADING_ERROR, value: []}))
                .map((response) => endLoadingAction)
        )
    .mergeMap(action => Observable.of(action, endLoadingAction));

export const listNameChangeEpic = (action$:ActionsObservable<ValueAction<NameChangeCommand>>) =>
    action$.ofType(RepositoryActionTypes.NAME_CHANGE)
        .debounceTime(250)
        .mergeMap(action =>
            ajax.post("/api/todo/lists/"+action.value.id.value, action.value, postHeaders)
                .takeUntil(action$.ofType(CommsActionTypes.CANCEL))
                //although we don't have any Error handling setup so, the action is empty)
                .catch(error => Observable.of({type:CommsActionTypes.LOADING_ERROR, value: []}))
                .map((response) => endLoadingAction)
        )
        // ensure our action returned from the ajax call comes along, along with a loadingEnd message
        .mergeMap(action => Observable.of(action, endLoadingAction));


export const itemCreateEpic = (action$:ActionsObservable<ValueAction<CreateItemCommand>>) =>
    action$.ofType(RepositoryActionTypes.ITEM_CREATE)
        .mergeMap(action =>
            ajax.post("/api/todo/lists/"+action.value.listId.value +"/items", action.value, postHeaders)
                .takeUntil(action$.ofType(CommsActionTypes.CANCEL))
                //although we don't have any Error handling setup so, the action is empty)
                .catch(error => Observable.of({type:CommsActionTypes.LOADING_ERROR, value: []}))
                .map((response) => endLoadingAction)
        )
        // ensure our action returned from the ajax call comes along, along with a loadingEnd message
        .mergeMap(action => Observable.of(action, endLoadingAction));




const epics:Epic<any, any, any> = combineEpics(
    listsRequestEpic,
    listsCreateEpic,
    listNameChangeEpic,
    itemCreateEpic
);

export default epics;