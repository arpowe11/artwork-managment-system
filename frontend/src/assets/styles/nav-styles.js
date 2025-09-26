const navStyles = {
  container: {
    position: "fixed",
    top: 0,
    left: 0,
    width: "100%",
    display: "flex",
    justifyContent: "space-between", // left and right items
    alignItems: "center",
    padding: "0 2rem", // horizontal padding only
    height: "60px",    // fixed height to avoid overflow
    backgroundColor: "#333",
    zIndex: 1000,
    boxSizing: "border-box", // important for padding to not break layout
  },
  leftContainer: {
    display: "flex",
    gap: "2rem",
  },
  rightContainer: {
    display: "flex",
    alignItems: "center", // vertically center the Logout button
  },
  link: {
    color: "#fff",
    textDecoration: "none",
    fontSize: "1.1rem",
    transition: "color 0.3s ease",
  },
};

export default navStyles;
