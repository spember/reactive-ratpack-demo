import * as React from 'react';

export interface AddListControlProps {
    submitHandler: (name:string) => void;
}

const inputId = "todoListName";

const AddListControl = ({submitHandler}:AddListControlProps) => (
    <div className="todo-list-add">
        <p className="todo-list-add__name">Name of new list:</p>
        <input type="text" placeholder="My sample list" id={inputId}/>
        <button className="button todo-list-add__button" onClick={(event:any) => {
            event.preventDefault();
            let input = document.getElementById(inputId) as HTMLInputElement;
            submitHandler(input.value);
            input.value = "";
        }}>add</button>
    </div>
);

export default AddListControl;

