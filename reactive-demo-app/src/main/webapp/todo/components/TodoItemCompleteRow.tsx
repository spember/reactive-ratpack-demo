import TodoItem from "../domain/todoItem";
import * as React from 'react';
import {ChangeEvent} from "react";

interface RowProps {
    todoItem: TodoItem
}

const TodoItemCompleteRow = ({todoItem}:RowProps) => (
    <div className={"todo-items-container__complete-row"}>
        <span>{todoItem.id.value}</span>:<span>{todoItem.text} (complete)</span>
    </div>
);


export default TodoItemCompleteRow;