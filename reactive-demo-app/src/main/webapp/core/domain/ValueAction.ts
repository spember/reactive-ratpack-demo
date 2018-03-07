import Action from './Action';

interface ValueAction<V> extends Action {
    value: V
}

export default ValueAction;