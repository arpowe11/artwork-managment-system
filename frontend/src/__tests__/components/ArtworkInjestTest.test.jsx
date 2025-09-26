import { describe, it, vi, expect, afterEach } from "vitest";
import { render, screen, cleanup } from "@testing-library/react";
import '@testing-library/jest-dom/vitest';
import React from "react";
import { MemoryRouter } from "react-router-dom";
import ArtworkInjest from "../../components/ArtworkInjest";


// Mock useNavigate
const mockedNavigate = vi.fn();

vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");
  return {
    ...actual,
    useNavigate: () => mockedNavigate,
  };
});

afterEach(() => {
  cleanup();
  mockedNavigate.mockReset();
});

describe("ArtworkInjest", () => {
    it("should render the heading", () => {
        render(
            <MemoryRouter>
                <ArtworkInjest />
            </MemoryRouter>
        );

        const heading = screen.getByRole("heading", { name: /artwork ingest page/i });
        expect(heading).toBeInTheDocument();
    });

    it("should render the ingest button", () => {
        render(
            <MemoryRouter>
                <ArtworkInjest />
            </MemoryRouter>
        );

        const button = screen.getByRole("button", { name: /ingest data/i });
        expect(button).toBeInTheDocument();
        expect(button).toHaveTextContent(/ingest data/i);
    });

    it("should render the link", () => {
        render(
            <MemoryRouter>
                <ArtworkInjest />
            </MemoryRouter>
        );

        const link = screen.getByRole("link", { name: /here/i });
        expect(link).toBeInTheDocument();
        expect(link).toHaveTextContent(/here/i);
    });
});
