import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FcGoogle } from "react-icons/fc"; // Google icon
import { FaGithub } from "react-icons/fa"; // GitHub icon
import loginStyles from "../assets/styles/login-styles"; 

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const baseUrl = "http://4.156.243.142";

  const handleGoogleLogin = () => {
    const width = 500;
    const height = 600;
    const left = window.innerWidth / 2 - width / 2;
    const top = window.innerHeight / 2 - height / 2;
    
    const popup = window.open(
      `${baseUrl}/oauth2/authorization/google`, // Spring’s default Google OAuth endpoint
      "Google Login",
      `width=${width},height=${height},top=${top},left=${left}`
    );
  };

  // 🔑 Google OAuth token handler
  useEffect(() => {
    function handleMessage(event) {
      console.log("EVENT:", event.origin);
      if (event.origin !== baseUrl) return; // only trust same origin
      if (event.data?.token) {
        sessionStorage.setItem("token", event.data.token);
        console.log("IN HERE")
        navigate("/dashboard");
      }
    }

    window.addEventListener("message", handleMessage);
    return () => window.removeEventListener("message", handleMessage);
  }, [navigate]);

  const handleGithubLogin = () => {
    window.location.href = "http://localhost:3001/api/auth/github";
  };

  const handleRegularLogin = async (e) => {
    e.preventDefault();  // Prevent page reload

    try {
      const response = await fetch(`${baseUrl}/api/v1/auth/login`, {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json"},
        body: JSON.stringify({ email, password })
      });

      let result = null;
        const text = await response.text();
        if (text) {
          result = JSON.parse(text);
        }

      if (response.ok) {
        // Using sessionStorage here, not secure will be patched later
        sessionStorage.setItem("token", result.token);
        navigate("/dashboard");
      } else {
        alert("Login failed: " + (result?.errorMessage || "Unknown error"));
      }
    } catch (err) {
      console.error("Login error:", err);
      alert("An error occurred. Try again.");
    }
  };

  return (
    <div style={loginStyles.container} >
      <div style={loginStyles.leftPane}>
        <div style={loginStyles.loginBox}>
          <h1 style={loginStyles.title}>Artwork Management System</h1>

          <hr style={loginStyles.breakLine}/>

          <h2 style={loginStyles.heading}>Login to your account</h2>

          <button style={loginStyles.googleButton} onClick={handleGoogleLogin}>
            <FcGoogle style={{ marginRight: "10px", fontSize: "1.2rem" }} />
            Login with Google
          </button>

          {/* <button style={loginStyles.githubButton} onClick={handleGithubLogin}>
            <FaGithub style={{ marginRight: "10px", fontSize: "1.2rem" }} />
            Login with GitHub
          </button> */}

          <h3 style={ {color: "black"} }> Or </h3>

          <div style={loginStyles.loginForm}>
            <form onSubmit={handleRegularLogin}>
              <input
                style={loginStyles.input} 
                type="text" 
                name="email" 
                placeholder="Email" 
                value={email} 
                onChange={(e) => setEmail(e.target.value)}
                required>
              </input>
              <input 
                style={loginStyles.input} 
                type="password" 
                name="password" 
                placeholder="Password" 
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required></input>
              <button style={loginStyles.button} type="submit">Login</button>
            </form>
          </div>

        <p style={ {color: "black"} }>Dont have an account? <a href="https://ams-frontend-app-dcexhvh9e0f5fjg3.canadacentral-01.azurewebsites.net/register">Register</a></p>
        </div>
      </div>

      <div style={loginStyles.rightPane}></div>
    </div>
  );
};

export default Login;
