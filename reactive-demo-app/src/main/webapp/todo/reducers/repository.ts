import TodoList, {IdWrapper} from "../domain/todoList";
import Action from "../../core/domain/Action";
import ValueAction from "../../core/domain/ValueAction";

export module RepositoryActionTypes {
    export const RESET = 'TODO:REPOSITORY:RESET';
    export const ADD = 'TODO:REPOSITORY:ADD';
    export const SET_LISTS = 'TODO:REPOSITORY:SET_ALL';
    export const FETCH_LISTS = 'TODO:LIST:FETCH';
    export const CREATE_LIST = 'TODO:LIST:CREATE';
    export const NAME_CHANGE = 'TODO:LIST:NAME_CHANGE';
}

module ServerEventTypes {
    export const LIST_CREATED = 'ListCreatedEvent';
    export const LIST_NAME_UPDATED = 'ListNameUpdatedEvent';
}

interface ListStructure {
    [id: string] : TodoList
}

export interface RepositoryStateTypes {
    lists: ListStructure
}

const convertToMap = (lists:TodoList[]) => {
    let store:ListStructure = {};
    console.log("Converting ", lists);
    lists.forEach( list => store[list.id.value] = list);
    return store;
};

const convertEventToList = (createdEvent:any):TodoList => {
    return {
        id: {
            value: createdEvent.entity.value
        },
        name: "",
        owner: {
            value: createdEvent.userId.value
        },
        dateCreated: "",
        lastUpdated: "",
        items: []
    }
};

const initialState:RepositoryStateTypes = {lists: {}};

function reducer<A extends ValueAction<any>>(repositoryState:RepositoryStateTypes = initialState, action:ValueAction<any>) {
    switch (action.type) {
        case RepositoryActionTypes.RESET:
            return initialState;
        case RepositoryActionTypes.SET_LISTS:
            return {...repositoryState, lists: convertToMap(action.value)};
        case ServerEventTypes.LIST_CREATED:
            return {...repositoryState, lists: Object.assign({}, repositoryState.lists, {[action.value.entity.value]: convertEventToList(action.value)})}
        case ServerEventTypes.LIST_NAME_UPDATED:
            let updatedList = {...repositoryState.lists[action.value.entity.value], name: action.value.name}
            return {...repositoryState, lists: Object.assign({}, repositoryState.lists, {[action.value.entity.value]: updatedList})}
        default:
            return repositoryState;
    }
}



export default reducer;

export const fetchListsAction = ():Action => ({type: RepositoryActionTypes.FETCH_LISTS});
export const createListAction = (name:string):ValueAction<string> => ({type: RepositoryActionTypes.CREATE_LIST, value:name});
export interface NameChangeCommand {
    id: IdWrapper,
    name:string
}
export const listNameChangeAction = (id:string, name:string):ValueAction<NameChangeCommand> => ({type: RepositoryActionTypes.NAME_CHANGE, value:{id: {value: id}, name}});
export const buildListsSetAllAction = (lists:TodoList[]):ValueAction<TodoList[]> => ({type:RepositoryActionTypes.SET_LISTS, value:lists});


