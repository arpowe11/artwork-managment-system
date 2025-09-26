import { describe, it, expect, afterEach } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import '@testing-library/jest-dom/vitest';
import React from "react";
import { MemoryRouter } from "react-router-dom";
import Dashboard from "../../components/Dashboard";

afterEach(() => {
    cleanup();
})

describe("Dashboard", () => {
    let user = {firstName: "Test"};

    it("should render the dashboard div", () => {
        render(
            <MemoryRouter>
                <Dashboard user={user}/>
            </MemoryRouter>
        );

        const div = screen.getByTestId("dash-div");
        expect(div).toBeInTheDocument();
    });
});