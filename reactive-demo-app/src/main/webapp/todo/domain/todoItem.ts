import {IdWrapper} from "./todoList";

interface TodoItem {
    id: IdWrapper,
    text: string,
    complete: boolean,
    dateCreated: string
}

export default TodoItem;