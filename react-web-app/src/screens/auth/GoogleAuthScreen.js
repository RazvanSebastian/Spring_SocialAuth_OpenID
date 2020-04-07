import React, { useEffect } from "react";
import { useLocation, useHistory } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setGoogleUserPayload } from "../../redux/action/actions";

const CODE_QUERY_PARAM = "code";
const STATE_QUERY_PARAM = "state";

export const GoogleAuthenticationScreen = (props) => {
  let history = useHistory();
  const dispatch = useDispatch();
  const params = new URLSearchParams(useLocation().search);

  useEffect(() => {
    let code = params.get(CODE_QUERY_PARAM);
    let state = params.get(STATE_QUERY_PARAM);
    exchangeCode(code, state);
  }, []);

  async function exchangeCode(code, state) {
    console.log(code);
    console.log(state);
    let options = {
      headers: {
        "csrf-auth-token": localStorage.getItem("csrf-auth-token"),
      },
    };
    const response = await fetch(
      "http://localhost:8080/api/oauth/google/token-exchange?code=" +
        encodeURIComponent(code) +
        "&state=" +
        encodeURIComponent(state),
      options
    );
    if (response.status == 200) {
      history.push("/auth-success");
    } else {
      const responseBody = await response.json();
      dispatch(setGoogleUserPayload(responseBody.userPayload));
      history.push("/registration");
    }
  }
  return null;
};
