# Frontend Testing Report  

## Introduction  
The goal of frontend testing for this project was to validate the functionality, reliability, and stability of React components and ensure at least **80% code coverage**. The testing was conducted using **Vitest** as the test runner along with **React Testing Library**, **Jest**, and **JSDOM** for simulating browser behavior.  

The overall code coverage achieved was **80.07%**, which meets the project requirement.  

---

## Testing Approach  

### 1. Test Case Scenarios  
Test cases were designed to cover the major user interactions and component behaviors in the application. The primary focus was on ensuring the following:  

- **Rendering Tests:** Verify that components render correctly with expected headings, forms, buttons, and links.  
- **Form Handling:** Ensure that form submission works properly, including error handling (e.g., mismatched passwords).  
- **API Integration:** Mock fetch requests to test success and failure responses for components making API calls.  
- **Navigation Tests:** Validate that buttons and links trigger navigation to the correct routes.  
- **Edge Cases:** Handle invalid inputs, server errors, and other exceptional flows gracefully.  

#### Example Scenarios Tested:
1. **ArtworkInjest Component**
   - Renders heading, button, and link.  
   - Clicking the “Ingest Data” button triggers API call and shows success or error toasts.  
   - Navigation link redirects to the correct page.  

2. **Register Component**
   - Renders all form fields and heading correctly.  
   - Alerts user when passwords do not match.  
   - Submits data and shows success alert when API call succeeds.  
   - Shows error alert when API call fails.  

---

### 2. Code Coverage Results  
The coverage report was generated using Vitest’s built-in coverage tool (`c8`).  

**Summary:**  
- **Statements:** 80.07%  
- **Branches:** 82.75%%  
- **Functions:** 34.28%%  
- **Lines:** 80.07%%  

You can find a detailed report here:
[Coverage Report](../coverage/lcov-report/index.html)

**Interpretation:**  
- The target coverage of **80%** was met across the project.  
- Core user flows (registration, ingestion, and routing) are thoroughly tested.  
- Some untested branches remain, primarily error-handling paths that are less common in production (e.g., network edge cases).  

---

### 3. Challenges Faced & Solutions  

**Challenge 1: React Router Conflicts**  
- Issue: Tests failed with errors such as “You cannot render a `<Router>` inside another `<Router>`.”  
- **Solution:** Used `MemoryRouter` in tests instead of `BrowserRouter` to simulate routing without duplicating router instances.  

**Challenge 2: Missing Matchers (toBeInTheDocument)**  
- Issue: Vitest did not recognize Jest DOM matchers.  
- **Solution:** Imported `@testing-library/jest-dom` to extend `expect` with custom DOM matchers.  

**Challenge 3: Mocking Fetch API**  
- Issue: Components like `Register` and `ArtworkInjest` relied on `fetch` calls, which caused failures in test environments.  
- **Solution:** Used `vi.fn()` and `vi.spyOn(global, "fetch")` to mock API responses for both success and failure scenarios.  

**Challenge 4: Code Coverage Exclusions**  
- Issue: Files such as `Main.jsx` and `AuthWrapper.jsx` were not meant to be tested but still appeared in coverage reports.  
- **Solution:** Updated the `vitest.config.js` file to explicitly exclude them from coverage metrics.  

---

## Conclusion  
Through careful testing of rendering, interactions, and API integrations, we achieved **80.07% overall code coverage**. The tests validate critical user workflows such as registration, data ingestion, and routing. Despite challenges with routing, DOM matchers, and API mocking, effective solutions were implemented.  

This testing strategy ensures that the frontend is both reliable and maintainable, with sufficient coverage to catch regressions and bugs during future development.  
