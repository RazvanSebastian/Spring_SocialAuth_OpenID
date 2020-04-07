import React, { useEffect, useState } from "react";

export const SignInScreen = (props) => {
  const [googleEndpointResponse, setGoogleEndpointResponse] = useState("");
  const [csrf, setCsrf] = useState("");

  useEffect(() => {
    initializeGoogleOAuthEndpoint();
  }, []);

  function initializeGoogleOAuthEndpoint() {
    let otpions = {
      headers: { "Access-Control-Expose-Headers": "*" },
    };
    fetch("http://localhost:8080/api/oauth/google/auth-uri")
      .then((response) => {
        var csrfHeader = response.headers.get("csrf-auth-token");
        console.log(csrfHeader);
        localStorage.setItem("csrf-auth-token", csrfHeader);
        return response.json();
      })
      .then((response) => setGoogleEndpointResponse(response));
  }

  function onClick() {
    window.location.href = googleEndpointResponse.uri;
  }

  return (
    <div>
      <button onClick={onClick}>Sign in with Google</button>;
    </div>
  );
};
