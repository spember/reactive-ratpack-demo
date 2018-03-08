import * as React from 'react';
import TodoList from "../domain/todoList";

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
            console.log("Rendering lists: ", lists);
            return (

                <section>
                   <p>Lists:</p>
                    {lists.forEach(list => (<p>List: {list.name}</p>))}
                </section>
            )
        }

    }

}

export default TodoLister;