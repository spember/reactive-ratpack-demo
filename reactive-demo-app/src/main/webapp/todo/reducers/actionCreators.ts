/**
    Contains action creators for combined actions of multiple reducers
 */
import {Dispatch} from "redux";
import {beginLoadingAction, cancelLoadingAction} from "./comms";
import {fetchListsAction, createListAction, listNameChangeAction, initiateItemCreateAction} from './repository';
import {IdWrapper} from "../domain/todoList";

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