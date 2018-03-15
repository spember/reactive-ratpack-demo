import TodoItem from "../domain/todoItem";
import * as React from 'react';
import {ChangeEvent} from "react";

interface RowProps {
    todoItem: TodoItem,
    textChangeHandler: (event:ChangeEvent<HTMLInputElement>, item:TodoItem) => void
    completeHandler: (event:MouseEvent, item:TodoItem) => void;
}

const TodoItemRow = ({todoItem, textChangeHandler, completeHandler}:RowProps) => (
    <div className={"todo-items-container__row"}>
        <span className="todo-items-container__row__header">Item: </span><input defaultValue={todoItem.text} onChange={(event:ChangeEvent<HTMLInputElement>) => {textChangeHandler(event, todoItem)}}/>
        <button className="todo-items-container__row__button" onClick={(event:any) => completeHandler(event,todoItem)}>Mark Complete</button>
    </div>
);


export default TodoItemRow;