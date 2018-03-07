import Action from "../../core/domain/Action";

export module CommsActionTypes {
    export const LOADING_BEGIN = 'TODO:LOADING:BEGIN';
    export const LOADING_END = 'TODO:LOADING:END';

    export const CANCEL = 'TODO:LOADING:CANCEL';
    // export const UPDATE_BEGIN = 'dead-letters.updating.initiated';
    // export const UPDATE_END = 'deadl-letters.updating.end'
}

export interface CommsStateTypes {
    isLoading: boolean,
    isUpdating: boolean
}

const initialCommsState:CommsStateTypes = {isLoading:false, isUpdating:false};

function reducer<A extends Action>(commsState:CommsStateTypes = initialCommsState, action: Action) {
    switch(action.type) {
        case CommsActionTypes.LOADING_BEGIN:
            return {...commsState, isLoading: true};
        case CommsActionTypes.LOADING_END:
            return {...commsState, isLoading: false};

        // case CommsActionTypes.UPDATE_BEGIN:
        //     return {...commsState, isUpdating: true};
        // case CommsActionTypes.UPDATE_END:
        //     return {...commsState, isUpdating: false};

        default:
            return commsState;
    }
}

export default reducer;