import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Toaster } from "react-hot-toast";
import Login from "./components/Login";
import Dashboard from "./components/Dashboard";
import Register from "./components/Register";
import ArtworkInjest from "./components/ArtworkInjest";
import ArtworkManager from "./components/ArtworkManager";
import AccountSettings from "./components/AccountSettings";
import AuthWrapper from "./components/AuthWrapper";
import Oauth2Redirect from "./components/Oauth2Redirect";

function App() {
  return (
    <Router>
      <Toaster position="top-center" reverseOrder={false} />
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/dashboard" element={<AuthWrapper><Dashboard /></AuthWrapper>} />
        <Route path="/artwork-injest" element={<AuthWrapper><ArtworkInjest /></AuthWrapper>} />
        <Route path="/artwork-manager" element={<AuthWrapper><ArtworkManager /></AuthWrapper>} />
        <Route path="/account" element={<AuthWrapper><AccountSettings /></AuthWrapper>} />
        <Route path="/register" element={<Register />} />
        <Route path="/oauth2-redirect" element={<Oauth2Redirect></Oauth2Redirect>} />
      </Routes>
    </Router>
  );
}

export default App;
