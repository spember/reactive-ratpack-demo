import * as React from 'react';
import * as Redux from 'redux';
import {render} from "react-dom";
import './scss/main.scss';
import * as ReactRedux from 'react-redux';
import thunk from 'redux-thunk';
import { createEpicMiddleware } from 'redux-observable';
import {BrowserRouter as Router, Switch} from 'react-router-dom';

import TodoListModule from './todo/main';
import {Route} from "react-router";
import Overview from "./todo/containers/Overview";
import * as Socket from './socketEventTranslator';
import ListView from "./todo/containers/SingleListView";

const reducers = Redux.combineReducers({
   todo: TodoListModule.reducers
});

const epicMiddleware = createEpicMiddleware(TodoListModule.epics);

const finalCreateStore = Redux.compose(
    // thunk is required for 'dispatch()' operations
    Redux.applyMiddleware(thunk, epicMiddleware),
)(Redux.createStore);

const store:any = finalCreateStore(reducers);

const MainLayout = () => (
    <section className="main-layout">
        <main>
            <Switch>
                {/*<Route path="/app/login" exact component={Single}/>*/}
                <Route path="/list/:id" exact component={ListView}/>
                <Route path="/" component={Overview}/>

            </Switch>
        </main>

    </section>
);


render(<ReactRedux.Provider store={store}>
        <Router>
            <MainLayout />
        </Router>
    </ReactRedux.Provider>
    ,
    document.getElementById("appRoot")
);

Socket.init(store);