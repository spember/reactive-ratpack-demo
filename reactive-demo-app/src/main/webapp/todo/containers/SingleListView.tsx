import * as React from 'react';
import {RouteComponentProps} from "react-router";
import TodoList, {IdWrapper} from "../domain/todoList";
import {bindActionCreators} from "redux";
import {
    initiateTodoItemCreation, initiateTodoItemNameChange,
    initiateTodoListNameChange, initiateTodoItemComplete
} from "../reducers/actionCreators";
import {connect} from "react-redux";
import TodoItem from "../domain/todoItem";
import TodoItemRow from "../components/TodoItemRow";
import {ChangeEvent} from "react";
import TodoItemCompleteRow from "../components/TodoItemCompleteRow";

export interface ListViewProps extends RouteComponentProps<any> {
}

export interface ExternalStateProps {
    list: TodoList,
    items: TodoItem[]
}

export interface DispatchProps {
    changeName: (id:string, name:string) => void;
    addItem: (listId:IdWrapper) => void;
    changeItemText: (id:IdWrapper, listId:IdWrapper, text:string) => void;
    markItemComplete: (id:IdWrapper, listId:IdWrapper) => void;
}


class SingleListView extends React.Component<ListViewProps & ExternalStateProps& DispatchProps, any> {
    handleNameChange(event:Event) {
        event.preventDefault();
        this.props.changeName(this.props.list.id.value, (event.target as HTMLInputElement).value);
    }
    handleAddItem(event:Event) {
        event.preventDefault();
        this.props.addItem(this.props.list.id);
    }

    handleTextChange(event:ChangeEvent<HTMLInputElement>, item:TodoItem) {
        event.preventDefault();
        let name = (event.target as HTMLInputElement).value;
        this.props.changeItemText(item.id, this.props.list.id, name);
    }

    handleItemComplete(event:MouseEvent, item:TodoItem) {
        event.preventDefault();
        this.props.markItemComplete(item.id, this.props.list.id);
    }

    //add items, then edit name. mark as done
    // need empty item commands
    // need item name change commands
    // need item mark done commands
    render() {
        const {list, items=[]} = this.props;
        console.log("Have items: ", items);
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
                    {items.map(item => (item.complete
                        ? <TodoItemCompleteRow key={item.id.value} todoItem={item}/>
                        : <TodoItemRow key={item.id.value} todoItem={item} textChangeHandler={this.handleTextChange.bind(this)} completeHandler={this.handleItemComplete.bind(this)}/>))}
                </section>
            )
        }

    }
}

const mapStateToProps = (state: any, props:ListViewProps & ExternalStateProps& DispatchProps): ExternalStateProps => {
    const listId = props.match.params.id;
    const list:TodoList = state.todo.repository.lists[listId];
    let items:TodoItem[] = [];
    list.items.forEach(value => {if (state.todo.items.hasOwnProperty(value.value)){
        items.push(state.todo.items[value.value]);
    }});
    return {
        list,
        items
    }
};

const mapDispatchToProps = (dispatch: any):DispatchProps => {
    return {
        changeName: bindActionCreators(initiateTodoListNameChange, dispatch),
        addItem: bindActionCreators(initiateTodoItemCreation, dispatch),
        changeItemText: bindActionCreators(initiateTodoItemNameChange, dispatch),
        markItemComplete: bindActionCreators(initiateTodoItemComplete, dispatch)

    }
};

export default connect<ExternalStateProps, DispatchProps, ListViewProps>(mapStateToProps, mapDispatchToProps)(SingleListView);
