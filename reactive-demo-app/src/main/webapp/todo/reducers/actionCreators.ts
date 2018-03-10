/**
    Contains action creators for combined actions of multiple reducers
 */
import {Dispatch} from "redux";
import {beginLoadingAction, cancelLoadingAction} from "./comms";
import {fetchListsAction, createListAction, listNameChangeAction} from './repository';

export const initiateTodoListFetch = () => (dispatch:Dispatch<any>) => {
    dispatch(cancelLoadingAction);
    dispatch(beginLoadingAction);
    dispatch(fetchListsAction());
};

export const initiateTodoListCreate = (name:string) => (dispatch:Dispatch<any>) => {
    dispatch(cancelLoadingAction);
    dispatch(beginLoadingAction);
    dispatch(createListAction(name))
};

export const initiateTodoListNameChange = (id:string, name:string) => (dispatch:Dispatch<any>) => {
    dispatch(cancelLoadingAction);
    dispatch(beginLoadingAction);
    dispatch(listNameChangeAction(id, name))
};