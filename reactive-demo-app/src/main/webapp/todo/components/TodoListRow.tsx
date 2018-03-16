import * as React from 'react';
import {Route} from 'react-router-dom';
import TodoList from "../domain/todoList";

interface RowProps {
    todoList: TodoList
}

const Button = ({name, id}:{name:string, id:string}) => (
    <Route render={({ history}) => (
        <a
            href="#"
            onClick={() => { history.push('/list/'+id) }}
        >
            {name}
        </a>
    )} />
);


const TodoListRow = ({todoList}:RowProps) => (
    <div className="todo-list-container__row">
        <Button name={todoList.name ? todoList.name : "Unnamed List"} id={todoList.id.value} />
        <span className="todo-list-container__count">Items: {todoList.items.length}</span>
    </div>
);

export default TodoListRow;
