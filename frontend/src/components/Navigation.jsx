import React from "react";
import Logout from "./Logout";
import navStyles from "../assets/styles/nav-styles";

const Navigation = () => {
    let baseUrl = "http://localhost:5173";  // TODO: change this to frontend url
    return (
        <div>
            <nav style={navStyles.container}>
                <div style={navStyles.leftContainer}>
                    <a href={`${baseUrl}/dashboard`} style={navStyles.link}>Home</a>
                    <a href={`${baseUrl}/artwork-injest`} style={navStyles.link}>Artwork Ingest</a>
                    <a href={`${baseUrl}/artwork-manager`} style={navStyles.link}>Artwork Manager</a>
                    {/* <a href="http://localhost:5173/account" style={navStyles.link}>Account Settings</a> */}
                </div>
                <div style={navStyles.rightContainer}>
                    <Logout />
                </div>
            </nav>
        </div>
    );
};

export default Navigation;
