import TodoItem from './todoItem';

interface IdWrapper {
    value: string;
}

interface TodoList {
    id: IdWrapper,
    name: string,
    owner: IdWrapper,
    dateCreated: string,
    lastUpdated: string,
    items: TodoItem[]
}

export default TodoList