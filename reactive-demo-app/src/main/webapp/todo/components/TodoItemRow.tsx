import TodoItem from "../domain/todoItem";
import * as React from 'react';
import {ChangeEvent} from "react";

interface RowProps {
    todoItem: TodoItem,
    textChangeHandler: (event:ChangeEvent<HTMLInputElement>, item:TodoItem) => void
}

const TodoItemRow = ({todoItem, textChangeHandler}:RowProps) => (
    <div className={"todo-items-container__row"}>
        <span>{todoItem.id.value}</span>:<input defaultValue={todoItem.text} onChange={(event:ChangeEvent<HTMLInputElement>) => {textChangeHandler(event, todoItem)}}/>
        <button>Mark Complete</button>
    </div>
);


export default TodoItemRow;