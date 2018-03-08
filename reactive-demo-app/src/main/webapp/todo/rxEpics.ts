import {ajax} from "rxjs/observable/dom/ajax";
import {RepositoryActionTypes, buildListsSetAllAction} from './reducers/repository';
import {CommsActionTypes, endLoadingAction} from './reducers/comms';
import {ActionsObservable} from "redux-observable";
import {MiddlewareAPI} from "redux";
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mapTo';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/operator/takeUntil';
import 'rxjs/add/operator/concatMap';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/pluck';
import TodoList from './domain/todoList';
import {AjaxResponse, Observable} from "rxjs";
import {ajaxPost} from "rxjs/observable/dom/AjaxObservable";
import ValueAction from "../core/domain/ValueAction";



export const listsRequestEpic = (action$:ActionsObservable<any>, store:MiddlewareAPI<any>) =>
    // I need to fetch, and emit end:loading regardless
    action$.ofType(RepositoryActionTypes.FETCH_LISTS)
        .mergeMap(action =>
            ajax.getJSON("/api/todo/lists")
                .takeUntil(action$.ofType(CommsActionTypes.CANCEL))
                //although we don't have any Error handling setup so, the action is empty)
                .catch(error => Observable.of({type:CommsActionTypes.LOADING_ERROR, value: []}))
                .map((response:TodoList[]) => (buildListsSetAllAction(response)))
        )
        // ensure our action returned from the ajax call comes along, along with a loadingEnd message
        .mergeMap(action => Observable.of(action, endLoadingAction));


export const listsCreateEpic = (action$:ActionsObservable<ValueAction<string>>, store:MiddlewareAPI<any>) =>
    action$.ofType(RepositoryActionTypes.CREATE_LIST)
        .debounceTime(250) // don't spam the server if the user is typing or clicks multiple times
        .mergeMap(action =>
            ajaxPost("/api/todo/lists", {name: action.value}, {})
                .takeUntil(action$.ofType(CommsActionTypes.CANCEL))
                //although we don't have any Error handling setup so, the action is empty)
                .catch(error => Observable.of({type:CommsActionTypes.LOADING_ERROR, value: []}))
                .map((response) => (endLoadingAction))
        )
.mergeMap(action => Observable.of(action, endLoadingAction));