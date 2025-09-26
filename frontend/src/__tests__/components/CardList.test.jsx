import { describe, it, expect, afterEach } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import '@testing-library/jest-dom/vitest';
import React from "react";
import { MemoryRouter } from "react-router-dom";
import CardList from "../../components/CardList";

afterEach(() => {
    cleanup();
})

describe("Card", () => {
    let art = {
        id: "1",
        title: "title",
        artist: "artist",
        description: "test",
        artType: "art"
    };

    it("should render the card div", () => {
        render(
            <MemoryRouter>
                <CardList artwork={art}/>
            </MemoryRouter>
        );
        
        const div = screen.getByTestId("cardlist-div");
        expect(div).toBeInTheDocument();
    });
    
});