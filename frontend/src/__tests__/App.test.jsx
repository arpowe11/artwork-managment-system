import { describe, it, vi, expect, afterEach } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import '@testing-library/jest-dom/vitest';
import React from "react";
import { MemoryRouter } from "react-router-dom";
import App from "../App";


function renderWithRoute(route) {
  return render(
    <MemoryRouter initialEntries={[route]}>
      <App />
    </MemoryRouter>
  );
}

describe("App routing", () => {
  it("renders login page on /", () => {
    render(<App/>);
    const loginBtn = screen.getByRole("button", {name: /login with google/i});
    expect(loginBtn).toBeInTheDocument();
    expect(loginBtn).toHaveTextContent(/Login with Google/i);
  });
});
