import React, { useState } from "react";
import { useSelector } from "react-redux";

export const RegistrationScreen = (props) => {
  const [userPayload, setUserPayload] = useState(
    useSelector((state) => state.googleUserPayload)
  );

  console.log(userPayload);
  return (
    <form>
      <label>
        <img width="100" height="100" src={userPayload.urlPicture} />
      </label>
      <br />
      <label>
        Email:
        <br />
        <input
          type="text"
          disabled
          value={userPayload.email}
          onChange={(event) =>
            setUserPayload({ ...userPayload, email: event.target.value })
          }
        />
      </label>
      <br />
      <label>
        First name:
        <br />
        <input
          type="text"
          value={userPayload.givenName}
          onChange={(event) =>
            setUserPayload({ ...userPayload, givenName: event.target.value })
          }
        />
      </label>
      <br />
      <label>
        Last name:
        <br />
        <input
          type="text"
          value={userPayload.familyName}
          onChange={(event) =>
            setUserPayload({ ...userPayload, familyName: event.target.value })
          }
        />
      </label>
      <br />
      <label>
        Location:
        <br />
        <input
          type="text"
          disabled
          value={userPayload.location}
          onChange={(event) =>
            setUserPayload({ ...userPayload, location: event.target.value })
          }
        />
      </label>
      <br />
      <input type="submit" value="Submit" />
    </form>
  );
};
