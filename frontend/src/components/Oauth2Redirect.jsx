import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Oauth2Redirect = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const token = params.get("token");

    if (window.opener && token) {
      window.opener.postMessage({ token }, "http://localhost:5173");
      window.close();
    } else {
      // Fallback: save token directly if opened in same window
      if (token) {
        sessionStorage.setItem("token", token);
        navigate("/dashboard");
      }
    }
  }, [navigate]);

  return <div>Logging in...</div>;
};

export default Oauth2Redirect;
