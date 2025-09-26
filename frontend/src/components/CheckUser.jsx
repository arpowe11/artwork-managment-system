import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const CheckUser = ({ user }) => {
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) {
      navigate("/"); // redirect to login
    }
  }, [user, navigate]);

  if (!user) { 
    return (
      <div data-testid="valid-user-div" style={{ backgroundColor: "white", height: "100vh" }}></div>
    );
  }

  return null; // render nothing if user exists
};


export default CheckUser;
