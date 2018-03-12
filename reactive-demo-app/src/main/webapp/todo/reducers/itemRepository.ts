import {IdWrapper} from "../domain/todoList";
import TodoItem from '../domain/todoItem';
import ValueAction from "../../core/domain/ValueAction";


export module ItemRepositoryActionTypes {
    export const RESET = 'TODO:ITEM:REPOSITORY:RESET';
    export const ADD = 'TODO:ITEM:REPOSITORY:ADD';
    export const SET_ITEMS = 'TODO:ITEM:REPOSITORY:SET_ALL';
    export const FETCH_ITEMS = 'TODO:ITEM:FETCH';
}

module ServerEventTypes {
    export const ITEM_CREATED_EVENT = 'ItemCreatedEvent';
}

interface ItemStructure {
    [id:string] : TodoItem
}

const convertEventToItem = (createdEvent:any):TodoItem => {
    return {
        id: {
            value: createdEvent.entity.value
        },
        text: "",
        dateCreated: "",
        complete: false
    }
};


const initialState:ItemStructure = {};


function reducer<A extends ValueAction<any>>(repositoryState:ItemStructure = initialState, action:ValueAction<any>) {
    switch(action.type) {
        case ServerEventTypes.ITEM_CREATED_EVENT:
            return Object.assign({}, repositoryState, {[action.value.entity.value]: convertEventToItem(action.value)});
        default:
            return repositoryState;
    }
}

export default reducer;