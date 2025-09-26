const dashboardStyles = {
  container: {
    minHeight: "100vh",
    margin: 0,
    padding: "20px",
    textAlign: "center",
    background: "linear-gradient(to right, #4facfe, #00f2fe)",
    display: "flex",
    flexDirection: "column",
    alignItems: "center", // horizontal center only
    justifyContent: "flex-start", // ensure content flows from top
  },
  welcomeContainer: {
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    justifyContent: "flex-start",
    backgroundColor: "rgba(255, 255, 255, 0.95)",
    padding: "20px 30px",       // smaller padding
    borderRadius: "12px",
    boxShadow: "0 4px 12px rgba(0, 0, 0, 0.15)",
    maxWidth: "400px",
    margin: "60px auto 20px auto", // move it lower from nav
  },
  welcomeTitle: {
    fontSize: "2rem",
    color: "rgba(0, 0, 0, 1)",
    marginBottom: "20px", // reduce spacing below title
  },
  emailText: {
    fontSize: "1rem",
    color: "rgba(0, 0, 0, 1)",
    marginBottom: "15px", // spacing between input and button
  },
  input: {
    padding: "0.6rem",
    borderRadius: "6px",
    border: "1px solid #ccc",
    fontSize: "1rem",
    width: "100%",
    marginBottom: "1rem",
    boxSizing: "border-box",
  },
  button: {
    padding: "0.75rem 1.5rem",
    fontSize: "1rem",
    fontWeight: "bold",
    backgroundColor: "#007BFF",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
    transition: "all 0.2s ease",
    alignSelf: "center", // ensure the button is centered
  },
  buttonHover: {
    backgroundColor: "#0056b3",
    transform: "scale(1.05)",
  },
};

export default dashboardStyles;
