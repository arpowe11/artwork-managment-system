import { describe, it, expect, afterEach } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import '@testing-library/jest-dom/vitest';
import React from "react";
import { MemoryRouter } from "react-router-dom";
import ArtworkManage from "../../components/ArtworkManager";

afterEach(() => {
    cleanup();
})

describe("ArtworkManager", () => {
    let user = {roles: "ROLE_USER"};
    let admin = {roles: "ROLE_ADMIN"};

    it("should render the page heading", () => {
        render(
            <MemoryRouter>
                <ArtworkManage user={user}/>
            </MemoryRouter>
        )

        const heading = screen.getByRole("heading", {name: /Artwork Manager Page/i})
        expect(heading).toBeInTheDocument();
        expect(heading).toHaveTextContent(/Artwork Manager Page/i)
    });

    it("should render the POST input", () => {
        render(
            <MemoryRouter>
                <ArtworkManage user={admin}/>
            </MemoryRouter>
        )

        const input = screen.getByDisplayValue("POST")
        expect(input).toBeInTheDocument();
    });

    it("should render the PUT input", () => {
        render(
            <MemoryRouter>
                <ArtworkManage user={admin}/>
            </MemoryRouter>
        )

        const input = screen.getByDisplayValue("UPDATE")
        expect(input).toBeInTheDocument();
    });

    it("should render the DELETE input", () => {
        render(
            <MemoryRouter>
                <ArtworkManage user={admin}/>
            </MemoryRouter>
        )

        const input = screen.getByDisplayValue("DELETE")
        expect(input).toBeInTheDocument();
    });

    it("should render the submit button", () => {
        render(
            <MemoryRouter>
                <ArtworkManage user={admin}/>
            </MemoryRouter>
        )

        const button = screen.getByRole("button", {name: /submit/i})
        expect(button).toBeInTheDocument();
        expect(button).toHaveTextContent(/Submit/i)
    });

    it("should render the artwork input form", () => {
        render(
            <MemoryRouter>
                <ArtworkManage user={admin}/>
            </MemoryRouter>
        )

        const form = screen.getByTestId("artwork-form");
        expect(form).toBeInTheDocument();
    });
});
