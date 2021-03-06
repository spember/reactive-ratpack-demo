/**
    Contains action creators for combined actions of multiple reducers
 */
import {Dispatch} from "redux";
import {beginLoadingAction, cancelLoadingAction} from "./comms";
import {fetchListsAction, createListAction, listNameChangeAction, initiateItemCreateAction} from './repository';
import {IdWrapper} from "../domain/todoList";
import {initiateItemNameChangeAction, initiateItemCompleteAction} from "./itemRepository";

const cancelAndBegin = (dispatch:Dispatch<any>) => {
    dispatch(cancelLoadingAction);
    dispatch(beginLoadingAction);
};

export const initiateTodoListFetch = () => (dispatch:Dispatch<any>) => {
    cancelAndBegin(dispatch);
    dispatch(fetchListsAction());
};

export const initiateTodoListCreate = (name:string) => (dispatch:Dispatch<any>) => {
    cancelAndBegin(dispatch);
    dispatch(createListAction(name))
};

export const initiateTodoListNameChange = (id:string, name:string) => (dispatch:Dispatch<any>) => {
    cancelAndBegin(dispatch);
    dispatch(listNameChangeAction(id, name))
};

export const initiateTodoItemCreation = (listId:IdWrapper) => (dispatch:Dispatch<any>) => {
    cancelAndBegin(dispatch);
    dispatch(initiateItemCreateAction(listId));
};

export const initiateTodoItemNameChange = (itemId:IdWrapper, listId:IdWrapper, text:string) => (dispatch:Dispatch<any>) => {
    cancelAndBegin(dispatch);
    dispatch(initiateItemNameChangeAction(itemId, listId, text));
};

export const initiateTodoItemComplete = (itemId:IdWrapper, listId:IdWrapper) => (dispatch:Dispatch<any>) => {
    cancelAndBegin(dispatch);
    dispatch(initiateItemCompleteAction(itemId, listId));
};