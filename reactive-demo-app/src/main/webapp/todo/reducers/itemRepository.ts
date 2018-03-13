import {IdWrapper} from "../domain/todoList";
import TodoItem from '../domain/todoItem';
import ValueAction from "../../core/domain/ValueAction";

export module ItemRepositoryActionTypes {
    export const RESET = 'TODO:ITEM:REPOSITORY:RESET';
    export const ADD = 'TODO:ITEM:REPOSITORY:ADD';
    export const SET_ITEMS = 'TODO:ITEM:REPOSITORY:SET_ALL';
    export const FETCH_ITEMS = 'TODO:ITEM:FETCH';
    export const CHANGE_TEXT = 'TODO:ITEM:TEXT:CHANGE';
    export const MARK_COMPLETE = 'TODO:ITEM:ITEM:COMPLETE';
}

module ServerEventTypes {
    export const ITEM_CREATED_EVENT = 'ItemCreatedEvent';
    export const ITEM_TEXT_CHANGE_EVENT = 'ItemTextChangedEvent';
    export const ITEM_COMPLETE = 'ItemCompletedEvent';
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


const updateItemText = (repository:ItemStructure, event:any):TodoItem => {
    let updatedValue = repository[event.entity.value];
    updatedValue.text = event.text;
    return updatedValue;
};

const markItemComplete = (repository:ItemStructure, event:any):TodoItem => {
    let updatedValue = repository[event.entity.value];
    updatedValue.complete = true;
    return updatedValue;
};

const initialState:ItemStructure = {};


function reducer<A extends ValueAction<any>>(repositoryState:ItemStructure = initialState, action:ValueAction<any>) {
    switch(action.type) {
        case ServerEventTypes.ITEM_CREATED_EVENT:
            return Object.assign({}, repositoryState, {[action.value.entity.value]: convertEventToItem(action.value)});
        case ServerEventTypes.ITEM_TEXT_CHANGE_EVENT:
            return Object.assign({}, repositoryState, {[action.value.entity.value]: updateItemText(repositoryState, action.value)});
        case ServerEventTypes.ITEM_COMPLETE:
            return Object.assign({}, repositoryState, {[action.value.entity.value]: markItemComplete(repositoryState, action.value)});
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
export interface ItemCompleteCommand {
    id: IdWrapper,
    listId: IdWrapper,
    complete: boolean
}
export const initiateItemNameChangeAction = (id:IdWrapper, listId:IdWrapper, text:string):ValueAction<ItemTextChangeCommand> =>
    ({type:ItemRepositoryActionTypes.CHANGE_TEXT, value:{id, listId, text}});
export const initiateItemCompleteAction = (id:IdWrapper, listId:IdWrapper):ValueAction<ItemCompleteCommand> =>
    ({type:ItemRepositoryActionTypes.MARK_COMPLETE, value: {id, listId, complete:true}});