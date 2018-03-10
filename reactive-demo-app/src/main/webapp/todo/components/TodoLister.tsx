import * as React from 'react';
import TodoList from "../domain/todoList";
import TodoListRow from './TodoListRow';

interface Props {
    lists: TodoList[]
}

class TodoLister extends React.Component<Props, any> {

    render () {
        const {lists = []}:Props = this.props;
        if (lists.length === 0) {
            return (<p>No Lists Found. Maybe add one?</p>)
        }
        else {
            return (
                <section className="todo-list-container">
                   <p>Lists:</p>
                    {lists.map(list => (<TodoListRow key={list.id.value} todoList={list}/>))}
                </section>
            )
        }

    }

}

export default TodoLister;