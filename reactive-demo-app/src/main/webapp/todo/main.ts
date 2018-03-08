import {combineReducers} from "redux";
import { combineEpics, Epic } from 'redux-observable';
import RepositoryReducer from './reducers/repository';
import CommsReducer, {CommsStateTypes} from './reducers/comms';
import * as Epics from './rxEpics';
// import * as Epics from './rxEpics';
// import {DeadLetter} from "./types";

// I like the structure of a module for outside access, but not sure how to use it effectively yet
module TodoListModule {
    export const reducers = combineReducers({
        repository: RepositoryReducer,
        comms: CommsReducer,
    });
    export const epics:Epic<any, any, any> = combineEpics(
        Epics.listsRequestEpic,
        Epics.listsCreateEpic
    );
    // // so we can enforce types in our store. Should be used by the outer 'main' app to create the shape of the State store
    // export interface StoreProps {
    //     comms: CommsStateTypes,
    // }
}

export default TodoListModule;
