import * as React from 'react';
import {RouteComponentProps} from "react-router";
import TodoList from "../domain/todoList";
import {bindActionCreators} from "redux";
import {initiateTodoListNameChange} from "../reducers/actionCreators";
import {connect} from "react-redux";

export interface ListViewProps extends RouteComponentProps<any> {
}

export interface ExternalStateProps {
    list: TodoList
}

export interface DispatchProps {
    changeName: (id:string, name:string) => void;
}


class SingleListView extends React.Component<ListViewProps & ExternalStateProps& DispatchProps, any> {
    handleNameChange(event:Event) {
        event.preventDefault();
        console.log("new name is ", (event.target as HTMLInputElement).value);
        this.props.changeName(this.props.list.id.value, (event.target as HTMLInputElement).value);
    }
    handleAddItem(event:Event) {
        event.preventDefault();
        console.log("adding new item");
        //
    }

    //add items, then edit name. mark as done
    // need empty item commands
    // need item name change commands
    // need item mark done commands
    render() {
        const {list} = this.props;
        if (!list) {
            return (
                <section><h2>Could not find list!</h2></section>
            )
        } else {
            return (
                <section>
                    <a href="/">Go Back</a>
                    <h2>Editing: {list.name}</h2>
                    <p>change name? <input type="text" defaultValue={list.name} onChange={this.handleNameChange.bind(this)}/> </p>
                    <button onClick={this.handleAddItem.bind(this)}>add item</button>
                    {list.items.map(item => {
                        return (<p>Have item!</p>);
                    })}
                </section>
            )
        }

    }
}

const mapStateToProps = (state: any, props:ListViewProps & ExternalStateProps& DispatchProps): ExternalStateProps => {
    const listId = props.match.params.id;
    return {
        list: state.todo.repository.lists[listId]
    }
};

const mapDispatchToProps = (dispatch: any):DispatchProps => {
    return {
        changeName: bindActionCreators(initiateTodoListNameChange, dispatch)
    }
};

export default connect<ExternalStateProps, DispatchProps, ListViewProps>(mapStateToProps, mapDispatchToProps)(SingleListView);
