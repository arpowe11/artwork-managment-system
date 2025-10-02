import React, { useState } from "react";
import toast from "react-hot-toast";
import Navigation from "./Navigation";
import ingestStyles from "../assets/styles/ingest-styles";
import { useNavigate } from "react-router-dom";

const ArtworkInjest = () => {
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleInjest = async () => {
        try {
            setLoading(true);
            toast.loading("Ingest started...");

            const response = await fetch("http://localhost:5001/api/ingest", { // TODO: Python api deploy uri
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({}),
            });

            if (!response.ok) throw new Error(`Server error: ${response.status}`);

            const result = await response.json();
            toast.dismiss(); // remove loading toast
            toast.success(result.message || "Ingestion completed!");
        } catch (err) {
            toast.dismiss();
            toast.error("An error occured during injestion.");
        } finally {
            setLoading(false);
        }
    };

    const handleClick = (e) => {
        e.preventDefault();
        navigate("/artwork-manager");
    }

    return (
        <div style={ingestStyles.container}>
            <Navigation />
            <h1 style={ingestStyles.header}>Artwork Ingest Page</h1>
            <p style={ingestStyles.paragraph}>
                To get anything new from the Artwork API, click the button below.
                To add your own artwork navigate to <a href="#" onClick={handleClick}> here </a>
            </p>
            <button
                onClick={handleInjest}
                style={ingestStyles.button}
                onMouseEnter={(e) => {
                    e.target.style.background = ingestStyles.buttonHover.background;
                    e.target.style.transform = ingestStyles.buttonHover.transform;
                }}
                onMouseLeave={(e) => {
                e.target.style.background = ingestStyles.button.background;
                e.target.style.transform = "scale(1)";
                }}
            >
                Ingest Data
            </button>
        </div>

    );
}

export default ArtworkInjest;