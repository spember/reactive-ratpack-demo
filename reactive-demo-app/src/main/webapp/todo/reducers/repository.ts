import TodoList from "../domain/todoList";
import Action from "../../core/domain/Action";
import ValueAction from "../../core/domain/ValueAction";

export module RepositoryActionTypes {
    export const RESET = 'TODO:REPOSITORY:RESET';
    export const ADD = 'TODO:REPOSITORY:ADD'
}

export interface RepositoryStateTypes {
    lists: TodoList[]
}

const initialState:RepositoryStateTypes = {lists: []};

function reducer<A extends ValueAction<TodoList>>(repositoryState:RepositoryStateTypes = initialState, action:Action) {
    switch (action.type) {
        case RepositoryActionTypes.RESET:
            return initialState;
        case RepositoryActionTypes.ADD:
            return {...repositoryState, lists: action}
        default:
            return repositoryState;
    }
}


