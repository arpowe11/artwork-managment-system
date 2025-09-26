import { describe, it, expect, afterEach } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import '@testing-library/jest-dom/vitest';
import React from "react";
import { MemoryRouter } from "react-router-dom";
import Register from "../../components/Register";


afterEach(() => {
    cleanup();
})

describe("Register", () => {
    it("should render the register div", () => {
        render(
            <MemoryRouter>
                <Register />
            </MemoryRouter>
        );

        const div = screen.getByTestId("reg-div");
        expect(div).toBeInTheDocument();
    });

    it("should render the register form", () => {
        render(
            <MemoryRouter>
                <Register />
            </MemoryRouter>
        );

        const form = screen.getByTestId("reg-form");
        expect(form).toBeInTheDocument();
    });

    it("should render the form header", () => {
        render(
            <MemoryRouter>
                <Register />
            </MemoryRouter>
        );

        const form = screen.getByTestId("form-head");
        expect(form).toBeInTheDocument();
    });

    it("should render the input first name", () => {
        render(
            <MemoryRouter>
                <Register />
            </MemoryRouter>
        );

        const form = screen.getByPlaceholderText("First Name");
        expect(form).toBeInTheDocument();
    });

    it("should render the input last name", () => {
        render(
            <MemoryRouter>
                <Register />
            </MemoryRouter>
        );

        const form = screen.getByPlaceholderText("Last Name");
        expect(form).toBeInTheDocument();
    });

    it("should render the input email", () => {
        render(
            <MemoryRouter>
                <Register />
            </MemoryRouter>
        );

        const form = screen.getByPlaceholderText("Email");
        expect(form).toBeInTheDocument();
    });

    it("should render the input password", () => {
        render(
            <MemoryRouter>
                <Register />
            </MemoryRouter>
        );

        const form = screen.getByPlaceholderText("Password");
        expect(form).toBeInTheDocument();
    });

    it("should render the input confirm password", () => {
        render(
            <MemoryRouter>
                <Register />
            </MemoryRouter>
        );

        const form = screen.getByPlaceholderText("Confirm Password");
        expect(form).toBeInTheDocument();
    });   
});

