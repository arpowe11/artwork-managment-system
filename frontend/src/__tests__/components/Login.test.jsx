import { describe, it, expect, afterEach } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import '@testing-library/jest-dom/vitest';
import React from "react";
import { MemoryRouter } from "react-router-dom";
import Login from "../../components/Login";

afterEach(() => {
    cleanup();
})

describe("Login", () => {
    it("should render the h3 tag", () => {
        render(
            <MemoryRouter>
                <Login />
            </MemoryRouter>
        );

        const heading = screen.getByRole("heading", {name: /artwork management system/i});
        expect(heading).toBeInTheDocument();
        expect(heading).toHaveTextContent(/Artwork Management System/i);
    });
});