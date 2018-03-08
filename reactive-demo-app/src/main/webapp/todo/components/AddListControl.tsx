import * as React from 'react';

export interface AddListControlProps {
    submitHandler: () => void;
}


const AddListControl = ({submitHandler}:AddListControlProps) => (
    <section className="todo-list-add">
        <p>Name of new list:</p>
        <input type="text" placeholder="My sample list"/>
        <button className="button todo-list-add__button" onClick={submitHandler}>add</button>
    </section>
);

export default AddListControl;

