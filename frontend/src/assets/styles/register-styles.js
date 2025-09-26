const registerStyles = {
    container: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
        backgroundColor: "#f0f2f5",
        fontFamily: "'Segoe UI', Tahoma, Geneva, Verdana, sans-serif",
    },
    form: {
        backgroundColor: "#fff",
        padding: "40px 30px",
        borderRadius: "12px",
        boxShadow: "0 4px 15px rgba(0,0,0,0.1)",
        width: "100%",
        maxWidth: "400px",
        display: "flex",
        flexDirection: "column",
    },
    input: {
        padding: "12px 15px",
        marginBottom: "15px",
        border: "1px solid #ccc",
        borderRadius: "6px",
        fontSize: "16px",
        outline: "none",
        transition: "border-color 0.3s",
    },
    inputFocus: {
        borderColor: "#007bff",
    },
    button: {
        padding: "12px 15px",
        backgroundColor: "#007bff",
        color: "#fff",
        fontSize: "16px",
        fontWeight: "bold",
        border: "none",
        borderRadius: "6px",
        cursor: "pointer",
        transition: "background-color 0.3s",
    },
    buttonHover: {
        backgroundColor: "#0056b3",
    },
    title: {
        textAlign: "center",
        marginBottom: "25px",
        fontSize: "24px",
        fontWeight: "600",
        color: "#333",
    },
};

export default registerStyles;
