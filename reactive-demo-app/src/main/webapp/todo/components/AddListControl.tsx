import * as React from 'react';

export interface AddListControlProps {
    submitHandler: (name:string) => void;
}

const inputId = "todoListName";

const AddListControl = ({submitHandler}:AddListControlProps) => (
    <section className="todo-list-add">
        <p>Name of new list:</p>
        <input type="text" placeholder="My sample list" id={inputId}/>
        <button className="button todo-list-add__button" onClick={(event:any) => {
            event.preventDefault();
            let input = document.getElementById(inputId) as HTMLInputElement;
            submitHandler(input.value);
            input.value = "";
        }}>add</button>
    </section>
);

export default AddListControl;

