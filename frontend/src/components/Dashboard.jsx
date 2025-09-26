import React, { useState } from "react";
import Navigation from "./Navigation";
import CardList from "./CardList";
import dashboardStyles from "../assets/styles/dashboard-styles";

const Dashboard = ({user}) => {
  const [id, setId] = useState("");
  const [data, setData] = useState([{
      id: "",
      title: "",
      artist: "",
      description: "",
      art_type: "",
      thumbnail: ""
  }]);
  
  // Fetches the artwork via GET, 0 gets all data otherwise get by ID
  const getData = async (id) => {
    let url;
    console.log(id);
    if (!id || id === "0") {
      url = "http://localhost:8080/api/v1/artworks"
    } else {
      url = `http://localhost:8080/api/v1/artworks/${id}`
    }
    console.log("Getting data");

    try {
      const response = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${sessionStorage.getItem("token")}`
        }
      })
      
      let result = null;
      const data = await response.text();
      if (data) {
        result = JSON.parse(data);
      }

      if (response.ok) {
        setData(data);
      } else {
        alert("Failed to fetch artwork: " + (result?.message || "Unknown"));
      }

    } catch (err) {
      console.error("Login error:", err);
      alert("An error occurred. Try again.");
    } 
  };

  return (
    <div data-testid="dash-div" style={dashboardStyles.container}>
      <Navigation />
      <div style={dashboardStyles.welcomeContainer}>
        <h1 style={dashboardStyles.welcomeTitle}>Welcome, {user.firstName} 👋</h1>
        <p style={dashboardStyles.emailText}>
          Leave the field blank to retrieve all artwork information, 
          or enter a specific ID to fetch a single piece of artwork.
        </p>
        <input
          style={dashboardStyles.input}
          type="text"
          placeholder="Enter id, leave blank to get all artworks"
          value={id}
          onChange={(e) => setId(e.target.value)}
          >
        </input>
        <button style={dashboardStyles.button} onClick={() => getData(id)}>Get Artwork</button>
      </div>
      <CardList artwork={data}></CardList>
    </div>
  );
};


export default Dashboard;
