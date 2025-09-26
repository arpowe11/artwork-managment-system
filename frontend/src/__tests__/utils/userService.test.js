import { describe, it, expect, vi, beforeEach, afterEach } from "vitest";
import getUser from "../../utils/userService";

describe("getUser", () => {
  beforeEach(() => {
    // Make sure sessionStorage is cleared before each test
    sessionStorage.clear();
  });

  afterEach(() => {
    // Restore mocks after each test
    vi.restoreAllMocks();
  });

  it("returns null if there is no token", async () => {
    const result = await getUser();
    expect(result).toBeNull();
  });

  it("returns user data when fetch succeeds", async () => {
    // Mock token in sessionStorage
    sessionStorage.setItem("token", "fake-token");

    // Mock fetch to return fake user data
    const fakeUser = { id: 1, name: "Alice" };
    global.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve(fakeUser),
      })
    );

    const result = await getUser();
    expect(result).toEqual(fakeUser);
    expect(fetch).toHaveBeenCalledOnce();
    expect(fetch).toHaveBeenCalledWith(
      "http://localhost:8080/api/v1/auth/users",
      expect.objectContaining({
        method: "GET",
        headers: expect.objectContaining({
          Authorization: "Bearer fake-token",
        }),
      })
    );
  });

  it("returns null if fetch fails", async () => {
    sessionStorage.setItem("token", "fake-token");

    global.fetch = vi.fn(() => Promise.reject("API down"));

    const result = await getUser();
    expect(result).toBeNull();
  });

  it("returns null if response is not ok", async () => {
    sessionStorage.setItem("token", "fake-token");

    global.fetch = vi.fn(() =>
      Promise.resolve({
        ok: false,
      })
    );

    const result = await getUser();
    expect(result).toBeNull();
  });
});
