import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";
import { GoogleAuthenticationScreen } from "./screens/auth/GoogleAuthScreen";
import { SignInScreen } from "./screens/auth/SignInScreen";
import { RegistrationScreen } from "./screens/auth/RegistrationScreen";

export default function App() {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
          </ul>
        </nav>

        {/* A <Switch> looks through its children <Route>s and
            renders the first one that matches the current URL. */}
        <Switch>
          <Route path="/google-auth">
            <GoogleAuthenticationScreen />
          </Route>
          <Route path="/registration">
            <RegistrationScreen />
          </Route>
          <Route path="/auth-success">
            <AuthSuccess />
          </Route>
          <Route path="/">
            <SignInScreen />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

function AuthSuccess() {
  return <div>Authentication Success !!!</div>;
}
