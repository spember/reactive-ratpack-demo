import * as React from 'react';
import {RouteComponentProps} from "react-router";
import {connect} from "react-redux";
import TodoList from "../domain/todoList";

export interface StateProps {
    lists: any[],
    isLoading: boolean
}

export interface DispatchProps {
    loadLists: () => void
}

export interface OverviewProps extends RouteComponentProps<any> {

}

interface internalStateProps {
    isLoading: boolean,
    lists: TodoList[]
}

class Overview extends React.Component<StateProps & DispatchProps & OverviewProps, internalStateProps> {

    componentWillMount() {
        this.setState({isLoading: true})
    }

    render () {
        return (
            <section>
                <h2>Heyy</h2>
            </section>
        )
    }

}

const mapStateToProps = (state: any): StateProps => {
    return {
        lists: [],
        isLoading: false
    };
};

const mapDispatchToProps = (dispatch: any):DispatchProps => {
    return {
        // loadLists: bindActionCreators(queryForLetters, dispatch),
        loadLists: () => {}
    }
};


// export default Overview
export default connect<StateProps, DispatchProps, OverviewProps>(mapStateToProps, mapDispatchToProps)(Overview);