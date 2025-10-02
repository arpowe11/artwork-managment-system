import React, { useState } from "react";
import Navigation from "./Navigation";
import managerStyles from "../assets/styles/manager-styles";

const ArtworkManager = ({ user }) => {
    const [method, setMethod] = useState("");
    const [formData, setFormData] = useState({
        id: "",
        title: "",
        artist: "",
        description: "",
        art_type: "",
        thumbnail: ""
    });

    // Handle method field changes
    const handleMethodChange = (e) => {
        setMethod(e.target.value);
    }

     // Handle form field changes
    const handleFieldChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
        ...prev,
        [name]: value
        }));
    };

    // Handles the submit
    const manageArtworkSubmit = () => {
        const baseUrl = "https://ams-app-backend-chdndmaghpetfabk.canadacentral-01.azurewebsites.net";
        let url = "";
        let httpMethod = "";
        
        if (!method) {
            alert("Please select a method...");
            return;
        }

        const data = { ...formData };
        if (!data.thumbnail || data.thumbnail.trim() === "") {
            data.thumbnail = "N/A";
        }

        if (method === "POST") {
            url = `${baseUrl}/api/v1/artworks1`;
            data.id = 0;
            httpMethod = "POST";
        } else if (method === "UPDATE") {
            url = `${baseUrl}/api/v1/artworks/${data.id}`;
            data.id = parseInt(data.id, 10);
            httpMethod = "PUT"; // 👈 correct verb
        } else if (method === "DELETE") {
            url = `${baseUrl}/api/v1/artworks/${data.id}`;
            data.id = parseInt(data.id, 10);
            httpMethod = "DELETE";
        }
        
        console.log("Method:", method);
        console.log("Data:", JSON.stringify(data, null, 2));
        console.log("User Role: ", user.role);

        fetch(url, {
            method: httpMethod,
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${sessionStorage.getItem("token")}`
            },
            body: JSON.stringify(data),
        })
            .then(res => res.json())
            .then(result => alert(result?.message))
            .catch(err => console.error(err));
    };

    return (
        <div style={managerStyles.container}>
            <Navigation />
            <h1 style={managerStyles.header}>Artwork Manager Page</h1>

            {/* Method Selector */}
            <div style={managerStyles.methodSelector}>
                <label>
                <input
                    type="radio"
                    name="method"
                    value="POST"
                    checked={method === "POST"}
                    onChange={handleMethodChange}
                />
                POST
                </label>

                {user.roles.includes("ROLE_ADMIN") && (
                <div style={managerStyles.radioGroup}>
                    <label>
                    <input
                        type="radio"
                        name="method"
                        value="UPDATE"
                        checked={method === "UPDATE"}
                        onChange={handleMethodChange}
                    />
                    UPDATE
                    </label>

                    <label>
                    <input
                        type="radio"
                        name="method"
                        value="DELETE"
                        checked={method === "DELETE"}
                        onChange={handleMethodChange}
                    />
                    DELETE
                    </label>
                </div>
                )}
            </div>

            {/* Form Fields */}
            <form
            data-testid="artwork-form"
            onSubmit={(e) => {
                e.preventDefault();
                manageArtworkSubmit();
            }}
            style={managerStyles.form}
            >
            {(method === "UPDATE" || method === "DELETE") && (
                <div style={managerStyles.formGroup}>
                    <label style={managerStyles.label}>ID *</label>
                    <input
                        type="text"
                        name="id"
                        value={formData.id}
                        onChange={handleFieldChange}
                        required
                        style={managerStyles.input}
                    />
                </div>
            )}

            {method !== "DELETE" && (
                <>
                    <div style={managerStyles.formGroup}>
                        <label style={managerStyles.label}>Title *</label>
                        <input
                        type="text"
                        name="title"
                        value={formData.title}
                        onChange={handleFieldChange}
                        required
                        style={managerStyles.input}
                        />
                    </div>

                    <div style={managerStyles.formGroup}>
                        <label style={managerStyles.label}>Artist *</label>
                        <input
                        type="text"
                        name="artist"
                        value={formData.artist}
                        onChange={handleFieldChange}
                        required
                        style={managerStyles.input}
                        />
                    </div>

                    <div style={managerStyles.formGroup}>
                        <label style={managerStyles.label}>Description *</label>
                        <textarea
                        name="description"
                        value={formData.description}
                        onChange={handleFieldChange}
                        required
                        style={managerStyles.textarea}
                        />
                    </div>

                    <div style={managerStyles.formGroup}>
                        <label style={managerStyles.label}>Art Type *</label>
                        <input
                        type="text"
                        name="art_type"
                        value={formData.art_type}
                        onChange={handleFieldChange}
                        required
                        style={managerStyles.input}
                        />
                    </div>

                    <div style={managerStyles.formGroup}>
                        <label style={managerStyles.label}>Thumbnail</label>
                        <input
                        type="text"
                        name="thumbnail"
                        value={formData.thumbnail}
                        onChange={handleFieldChange}
                        placeholder="Optional"
                        style={managerStyles.input}
                        />
                    </div>
                </>
            )}

                <button
                    type="submit"
                    style={managerStyles.button}
                    onMouseEnter={(e) => {
                    e.target.style.background =
                        managerStyles.buttonHover.background;
                    e.target.style.transform =
                        managerStyles.buttonHover.transform;
                    }}
                    onMouseLeave={(e) => {
                    e.target.style.background =
                        managerStyles.button.background;
                    e.target.style.transform = "scale(1)";
                    }}
                >
                    Submit
                </button>
            </form>
        </div>
    );
};

export default ArtworkManager;
