const cardStyles = {
  cardContainer: {
    width: "250px",
    backgroundColor: "rgba(91, 88, 95, 0.36)",
    borderRadius: "10px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
    padding: "20px",
    margin: "10px",
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
    transition: "transform 0.2s ease, box-shadow 0.2s ease",
    cursor: "pointer",
  },
  cardContainerHover: {
    transform: "translateY(-5px)",
    boxShadow: "0 8px 20px rgba(0,0,0,0.25)",
  },
  idText: {
    fontSize: "0.9rem",
    color: "#777",
    marginBottom: "8px",
  },
  titleText: {
    fontSize: "1.2rem",
    fontWeight: "bold",
    color: "#333",
    textAlign: "center",
    marginBottom: "5px",
  },
  artistText: {
    fontSize: "1rem",
    fontStyle: "italic",
    color: "#555",
    marginBottom: "10px",
  },
  descriptionText: {
    fontSize: "0.95rem",
    color: "#444",
    textAlign: "center",
  },
};

export default cardStyles;
