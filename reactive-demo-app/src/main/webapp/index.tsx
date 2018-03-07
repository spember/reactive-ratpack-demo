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

// import DeadLetterSearch from './modules/letterManagement/containers/DeadLetterSearch';
// import DeadLetterFilters from './modules/letterManagement/containers/FilterManagement';
// import Single from './modules/letterManagement/containers/DeadLetterSingleView';

// we only have one reducer, but by grouping them inside our modules we achieve more hierarchy (e.g. state.letters could have multiple
// sub state objects beneath it)
const reducers = Redux.combineReducers({
   todo: TodoListModule.reducers
});

// const epicMiddleware = createEpicMiddleware(LetterManagement.epics);

const finalCreateStore = Redux.compose(
    // thunk is required for 'dispatch()' operations
    // Redux.applyMiddleware(thunk),
)(Redux.createStore);

const store:any = finalCreateStore(reducers);

const MainLayout = () => (
    <section className="main-layout">
        <main>
            <Switch>
                {/*<Route path="/app/login" exact component={Single}/>*/}
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
