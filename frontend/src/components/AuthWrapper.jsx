import React, { useEffect, useState } from "react";
import getUser from "../utils/userService"; 
import CheckUser from "./CheckUser";

const AuthWrapper = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getUser().then((data) => {
      setUser(data);
      setLoading(false);
    });
  }, []);

  if (loading) return <div style={{ backgroundColor: "white", height: "100vh", width: "100vw" }}></div>;

  if (!user) return <CheckUser user={user} />; 

  return React.cloneElement(children, { user }); // user is logged in, render the page
};

export default AuthWrapper;
