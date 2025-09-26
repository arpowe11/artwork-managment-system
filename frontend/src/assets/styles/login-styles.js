const loginStyles = {
  container: {
    display: "flex",
    height: "100vh",
    width: "100vw",
  },
  title: {
    color: "rgb(100, 173, 248)",
    margin: "20px", 
    textDecoration: "none",
  },
  leftPane: {
    flex: 1, // takes 1/3 of the space
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5", // or keep red for testing
  },
  rightPane: {
    flex: 2, // takes 2/3 of the space
    background: "linear-gradient(to right, #4facfe, #00f2fe)", // gradient fill
  },
  loginBox: {
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "white",
    padding: "30px",
    borderRadius: "8px",
    boxShadow: "0 4px 8px rgba(0,0,0,0.1)",
    width: "80%",
    maxWidth: "350px",
  },
  loginForm: {
    display: "flex",
    flexDirection: "column",   
    alignItems: "center",       
    gap: "1rem",               
    width: "100%",             
    maxWidth: "400px",          
    margin: "0 auto",           
    padding: "20px",            
    boxSizing: "border-box",    
  },
  heading: {
    fontSize: "1.5rem",
    marginBottom: "20px",
    color: "rgb(100, 173, 248)",
  },
  googleButton: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    margin: "10px",
    padding: "10px 20px",
    cursor: "pointer",
    backgroundColor: "#ffffff",
    color: "#000000",
    border: "1px solid #dadce0",
    borderRadius: "5px",
    fontWeight: "500",
    fontSize: "1rem",
    boxShadow: "0 1px 2px rgba(0,0,0,0.1)",
  },
  githubButton: {
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    margin: "10px",
    padding: "10px 20px",
    cursor: "pointer",
    backgroundColor: "#24292e",
    color: "#ffffff",
    border: "none",
    borderRadius: "5px",
    fontWeight: "500",
    fontSize: "1rem",
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
    width: "100%", // make button full width like inputs
    maxWidth: "400px", // match input max width
  },
  buttonHover: {
    backgroundColor: "#0056b3",
    transform: "scale(1.05)",
  },
    breakLine:{
      border: "none",
      borderTop: "2px solid #333",
      margin: "20px 0",
      width: "80% ",
      color: "#333", 
  },
};

export default loginStyles;
