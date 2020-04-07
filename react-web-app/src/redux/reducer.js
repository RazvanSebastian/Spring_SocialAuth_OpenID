import { SET_GOOGLE_USER_PAYLOAD } from "./action/action-types";

const initialState = {
  googleUserPayload: { email: "" },
};

function rootReducer(state = initialState, action) {
  if (action.type === SET_GOOGLE_USER_PAYLOAD) {
    return Object.assign({}, state, {
      googleUserPayload: action.payload,
    });
  }
  return state;
}

export default rootReducer;
