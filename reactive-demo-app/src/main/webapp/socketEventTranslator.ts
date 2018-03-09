
const exampleSocket = new WebSocket("ws://localhost:5055/api/events");
export const init = (store:any) => {
    console.log(store);
    exampleSocket.onmessage = (event:MessageEvent) => {

        const payloadWithWrapper = JSON.parse(event.data);
        // todo: take the first key, and convert into an action
        let key = Object.keys(payloadWithWrapper)[0];
        console.log("dispatching on key ", key);
        store.dispatch({type: key, value: payloadWithWrapper[key]})
    };
};