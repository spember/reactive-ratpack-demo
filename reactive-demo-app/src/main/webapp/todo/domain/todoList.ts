import TodoItem from './todoItem';

export interface IdWrapper {
    value: string;
}

interface TodoList {
    id: IdWrapper,
    name: string,
    owner: IdWrapper,
    dateCreated: string,
    lastUpdated: string,
    items: IdWrapper[]
}

export default TodoList