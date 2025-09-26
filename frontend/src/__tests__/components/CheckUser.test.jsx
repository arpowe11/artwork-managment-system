import { describe, it, expect, afterEach } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import '@testing-library/jest-dom/vitest';
import React from "react";
import { MemoryRouter } from "react-router-dom";
import CheckUser from "../../components/CheckUser";

afterEach(() => {
    cleanup();
})

describe("CheckUser", () => {
    let user = false;
    
    it("should render the check user div", () => {
        render(
            <MemoryRouter>
                <CheckUser user={user}/>
            </MemoryRouter>
        );
        
        const div = screen.getByTestId("valid-user-div");
        expect(div).toBeInTheDocument();
    });
    
});