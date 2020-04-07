import { SET_GOOGLE_USER_PAYLOAD } from "./action-types";

export function setGoogleUserPayload(payload) {
  return {
    type: SET_GOOGLE_USER_PAYLOAD,
    payload,
  };
}
