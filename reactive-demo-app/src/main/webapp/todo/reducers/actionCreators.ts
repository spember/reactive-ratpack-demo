/**
    Contains action creators for combined actions of multiple reducers
 */
import {Dispatch} from "redux";
import {beginLoadingAction, cancelLoadingAction} from "./comms";
import {fetchListsAction, createListAction} from './repository';

export const initiateTodoListFetch = () => (dispatch:Dispatch<any>) => {
    dispatch(cancelLoadingAction);
    dispatch(beginLoadingAction);
    dispatch(fetchListsAction);
};

export const initiateTodoListCreate = (name:string) => (dispatch:Dispatch<any>) => {
    dispatch(cancelLoadingAction);
    dispatch(beginLoadingAction);
    console.log("Creating with ", name);
    dispatch(createListAction(name))
};