import React, { useState } from "react";
import cardStyles from "../assets/styles/card-styles"

const Card = ({ id, title, artist, description, artType }) => {
  const [hover, setHover] = useState(false);

  return (
    
    <div
      data-testid="card-div"
      style={{
        ...cardStyles.cardContainer,
        ...(hover ? cardStyles.cardContainerHover : {}),
      }}
      onMouseEnter={() => setHover(true)}
      onMouseLeave={() => setHover(false)}
    >
      <div>
        <div data-testid="art-id" style={cardStyles.idText}>Artwork ID: {id}</div>
        <div data-testid="art-title" style={cardStyles.titleText}>Art Title: {title}</div>
        <div data-testid="art-artist" style={cardStyles.artistText}>Artist: {artist}</div>
        <div data-testid="art-desc" style={cardStyles.descriptionText}>Description: {description}</div>
      </div>
    </div>
  );
};

export default Card;
