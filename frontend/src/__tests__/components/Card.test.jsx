import { describe, it, expect, afterEach } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import '@testing-library/jest-dom/vitest';
import React from "react";
import { MemoryRouter } from "react-router-dom";
import Card from "../../components/Card";

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
                <Card
                    id={ art.id }
                    title={ art.title}
                    artist={ art.artist }
                    description={ art.description }
                    artType={ art.artType }
                />
            </MemoryRouter>
        );

        const div = screen.getByTestId("card-div");
        expect(div).toBeInTheDocument();
    });

    it("should render the card div ID section", () => {
        render(
            <MemoryRouter>
                <Card
                    id={ art.id }
                    title={ art.title}
                    artist={ art.artist }
                    description={ art.description }
                    artType={ art.artType }
                />
            </MemoryRouter>
        );

        const div = screen.getByTestId("art-id");
        expect(div).toBeInTheDocument();
    });

    it("should render the card div title section", () => {
        render(
            <MemoryRouter>
                <Card
                    id={ art.id }
                    title={ art.title}
                    artist={ art.artist }
                    description={ art.description }
                    artType={ art.artType }
                />
            </MemoryRouter>
        );

        const div = screen.getByTestId("art-title");
        expect(div).toBeInTheDocument();
    });

    it("should render the card div artist section", () => {
        render(
            <MemoryRouter>
                <Card
                    id={ art.id }
                    title={ art.title}
                    artist={ art.artist }
                    description={ art.description }
                    artType={ art.artType }
                />
            </MemoryRouter>
        );

        const div = screen.getByTestId("art-artist");
        expect(div).toBeInTheDocument();
    });
    
        it("should render the card div description section", () => {
        render(
            <MemoryRouter>
                <Card
                    id={ art.id }
                    title={ art.title}
                    artist={ art.artist }
                    description={ art.description }
                    artType={ art.artType }
                />
            </MemoryRouter>
        );

        const div = screen.getByTestId("art-desc");
        expect(div).toBeInTheDocument();
    });
});
