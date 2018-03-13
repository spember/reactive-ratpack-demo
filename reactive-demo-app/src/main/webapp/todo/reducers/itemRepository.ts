import {IdWrapper} from "../domain/todoList";
import TodoItem from '../domain/todoItem';
import ValueAction from "../../core/domain/ValueAction";

export module ItemRepositoryActionTypes {
    export const RESET = 'TODO:ITEM:REPOSITORY:RESET';
    export const ADD = 'TODO:ITEM:REPOSITORY:ADD';
    export const SET_ITEMS = 'TODO:ITEM:REPOSITORY:SET_ALL';
    export const FETCH_ITEMS = 'TODO:ITEM:FETCH';
    export const CHANGE_TEXT = 'TODO:ITEM:TEXT:CHANGE';
}

module ServerEventTypes {
    export const ITEM_CREATED_EVENT = 'ItemCreatedEvent';
    export const ITEM_TEXT_CHANGE_EVENT = 'ItemTextChangedEvent';
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
        case ServerEventTypes.ITEM_TEXT_CHANGE_EVENT:
            console.log(action);
            let updatedValue = repositoryState[action.value.entity.value];
            updatedValue.text = action.value.text;
            return Object.assign({}, repositoryState, {[action.value.entity.value]: updatedValue});
        default:
            return repositoryState;
    }
}

export default reducer;

export interface ItemTextChangeCommand {
    id:IdWrapper,
    listId: IdWrapper,
    text:string
}
export const initiateItemNameChangeAction = (id:IdWrapper, listId:IdWrapper, text:string):ValueAction<ItemTextChangeCommand> => ({type:ItemRepositoryActionTypes.CHANGE_TEXT, value:{id, listId, text}});