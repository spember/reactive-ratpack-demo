import TodoItem from './todoItem';

interface TodoList {
    id: string,
    name: string,
    owner: string,
    dateCreated: number,
    lastUpdated: number,

    items: TodoItem[]

}

export default TodoList