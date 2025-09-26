import React from "react";
import Card from "./Card.jsx";
import cardListStyles from "../assets/styles/card-list-styles.js";

// Create a CardList component that iterates through all the cards to dynamically display all the cards
const CardList = ({ artwork }) => {
    let artworks = artwork;

    if (typeof artworks === "string") {
    artworks = JSON.parse(artworks);
    }

    if (Array.isArray(artworks) && typeof artworks[0] === "string") {
    artworks = JSON.parse(artworks[0]);
    }

    artworks = Array.isArray(artworks) ? artworks : [artworks];

    return (
        <div data-testid="cardlist-div" style={cardListStyles.container}>
            {
                artworks.map((art, i) => {
                    return (
                        <Card
                            key={ i }
                            id={ art.id }
                            title={ art.title}
                            artist={ art.artist }
                            description={ art.description }
                            artType={ art.artType }
                        />
                    );
                })
            }
        </div>
    );
}

export default CardList;
