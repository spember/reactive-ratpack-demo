import * as React from 'react';
import {RouteComponentProps} from "react-router";
import {connect} from "react-redux";
import TodoList from "../domain/todoList";
import {Observable} from "rxjs/Observable";
import {ajax} from "rxjs/observable/dom/ajax"
import Spinner from "../components/Spinner";
import TodoLister from "../components/TodoLister";
import {bindActionCreators} from "redux";
import {fetchListsAction} from "../reducers/repository";
import AddListControl from "../components/AddListControl";
import {initiateTodoListCreate, initiateTodoListFetch} from "../reducers/actionCreators";

export interface StateProps {
    lists: any[],
    isLoading: boolean
}

export interface DispatchProps {
    loadLists: () => void,
    createList: (name:string) => void
}

export interface OverviewProps extends RouteComponentProps<any> {

}

interface internalStateProps {
    isLoading: boolean,
    lists: TodoList[]
}



class Overview extends React.Component<StateProps & DispatchProps & OverviewProps, internalStateProps> {

    componentDidMount() {
        this.props.loadLists();
        // const self = this;
        // ajax.getJSON("/api/todo/lists")
        //     .subscribe((lists:TodoList[]) => self.setState({lists, isLoading:false}))
    }

    test() {
        this.props.createList("Sample List");
    }

    render () {
        const {isLoading, lists} = this.props;
        const self = this;
        return (
            <section className="main">
                <AddListControl submitHandler={this.test.bind(self)}/>
                {isLoading ? <Spinner/> : <TodoLister lists={lists} />}
            </section>
        )
    }

}

const mapStateToProps = (state: any): StateProps => {
    console.log(state);
    return {
        lists: state.todo.repository.lists,
        isLoading: state.todo.isLoading
    };
};

const mapDispatchToProps = (dispatch: any):DispatchProps => {
    return {
        loadLists: bindActionCreators(initiateTodoListFetch, dispatch),
        createList: bindActionCreators(initiateTodoListCreate, dispatch)
    }
};


// export default Overview
export default connect<StateProps, DispatchProps, OverviewProps>(mapStateToProps, mapDispatchToProps)(Overview);