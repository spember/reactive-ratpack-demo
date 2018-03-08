import TodoList from "../domain/todoList";
import Action from "../../core/domain/Action";
import ValueAction from "../../core/domain/ValueAction";

export module RepositoryActionTypes {
    export const RESET = 'TODO:REPOSITORY:RESET';
    export const ADD = 'TODO:REPOSITORY:ADD';
    export const SET_LISTS = 'TODO:REPOSITORY:SET_ALL';
    export const FETCH_LISTS = 'TODO:LIST:FETCH';
    export const CREATE_LIST = 'TODO:LIST:CREATE';
}

export interface RepositoryStateTypes {
    lists: TodoList[]
}

const initialState:RepositoryStateTypes = {lists: []};

function reducer<A extends ValueAction<TodoList[]>>(repositoryState:RepositoryStateTypes = initialState, action:ValueAction<TodoList[]>) {
    switch (action.type) {
        case RepositoryActionTypes.RESET:
            return initialState;
        case RepositoryActionTypes.ADD:
            return {...repositoryState, lists: action.value};
        default:
            return repositoryState;
    }
}



export default reducer;

export const fetchListsAction = ():Action => ({type: RepositoryActionTypes.FETCH_LISTS});
export const createListAction = (name:string):ValueAction<string> => ({type: RepositoryActionTypes.CREATE_LIST, value:name});
export const buildListsSetAllAction = (lists:TodoList[]):ValueAction<TodoList[]> => ({type:RepositoryActionTypes.SET_LISTS, value:lists})


