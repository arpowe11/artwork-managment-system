const managerStyles = {
  container: {
    minHeight: "100vh",
    margin: 0,
    padding: "2rem 1rem",
    background: "linear-gradient(to right, #4facfe, #00f2fe)",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },

  header: {
    fontSize: "2rem",
    marginTop: "3rem",         // ⬅ pushes header down below nav
    marginBottom: "2rem",
    color: "#fff",
    textShadow: "1px 1px 4px rgba(0,0,0,0.2)",
  },

  methodSelector: {
    display: "flex",
    alignItems: "center",
    gap: "2rem",
    marginBottom: "2rem",
    padding: "1rem 2rem",
    backgroundColor: "rgba(255,255,255,0.95)",
    borderRadius: "10px",
    boxShadow: "0 2px 8px rgba(0,0,0,0.15)",
    color: "black",
  },

  radioGroup: {
    display: "flex",
    gap: "2rem",
    alignItems: "center",
  },

  form: {
    display: "flex",
    flexDirection: "column",
    gap: "1.5rem",
    padding: "2rem",
    width: "100%",
    maxWidth: "600px",
    backgroundColor: "rgba(255,255,255,0.98)",
    borderRadius: "12px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.2)",
    marginBottom: "3rem",
  },

  formGroup: {
    display: "flex",
    flexDirection: "column",
    textAlign: "left",
  },

  label: {
    fontWeight: "600",
    marginBottom: "0.5rem",   // ⬅ space between label and input
    color: "#333",
  },

  input: {
    padding: "0.75rem",
    borderRadius: "8px",
    border: "1px solid #bbb",
    fontSize: "1rem",
    outline: "none",
    transition: "border-color 0.2s ease",
  },

  textarea: {
    padding: "0.75rem",
    borderRadius: "8px",
    border: "1px solid #bbb",
    fontSize: "1rem",
    outline: "none",
    transition: "border-color 0.2s ease",
    minHeight: "100px",
    resize: "vertical",
  },

  button: {
    marginTop: "0.5rem",
    padding: "0.9rem",
    fontSize: "1rem",
    fontWeight: "bold",
    background: "linear-gradient(to right, #007BFF, #00c6ff)",
    color: "white",
    border: "none",
    borderRadius: "10px",
    cursor: "pointer",
    boxShadow: "0 3px 8px rgba(0,0,0,0.2)",
    transition: "transform 0.15s ease, background 0.3s ease",
  },

  buttonHover: {
    background: "linear-gradient(to right, #0056b3, #0096c7)",
    transform: "scale(1.02)",
  },
};

export default managerStyles;
