const ingestStyles = {
  container: {
    minHeight: "100vh",
    margin: 0,
    padding: "2rem",
    textAlign: "center",
    background: "linear-gradient(to right, #4facfe, #00f2fe)",
    display: "flex",
    flexDirection: "column",
    justifyContent: "flex-start", // so header + text start below nav
    alignItems: "center",
  },
  header: {
    fontSize: "2rem",
    marginTop: "3rem",         // ⬅ pushes header down below nav
    marginBottom: "2rem",
    color: "#fff",
    textShadow: "1px 1px 4px rgba(0,0,0,0.2)",
  },
  paragraph: {
    fontSize: "1rem",
    color: "#444",
    marginBottom: "2rem",
    maxWidth: "600px",
    lineHeight: "1.5",
  },
  button: {
    padding: "0.8rem 1.5rem",
    fontSize: "1rem",
    fontWeight: "bold",
    background: "#505250ff",
    color: "#fff",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
    transition: "all 0.2s ease",
  },
  buttonHover: {
    background: "#919692ff",
    transform: "scale(1.05)",
  },
};

export default ingestStyles;
