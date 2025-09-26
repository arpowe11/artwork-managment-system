import React from "react";
import Logout from "./Logout";
import navStyles from "../assets/styles/nav-styles";

const Navigation = () => {
    return (
        <div>
            <nav style={navStyles.container}>
                <div style={navStyles.leftContainer}>
                    <a href="http://localhost:5173/dashboard" style={navStyles.link}>Home</a>
                    <a href="http://localhost:5173/artwork-injest" style={navStyles.link}>Artwork Ingest</a>
                    <a href="http://localhost:5173/artwork-manager" style={navStyles.link}>Artwork Manager</a>
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
