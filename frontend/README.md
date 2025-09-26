# Artwork Management System: Frontend Code Walkthrough 

This document walks through the major parts of the React frontend for the Artwork App, with a focus on **how authentication, routing, and security** are implemented. 
- For a video demo of the project you can go here: [Video Demo](https://youtu.be/UvUxm6GuFSk)  
  - The CRUD failed in this video, see below video as well for fixed CRUD
- For the fixed CRUD operations, you can go here [Fixed CRUD](https://youtu.be/rvIlv_gF1RY)
- To read the implementation report you can go here: [Report](./documents/implementation_report.md)

---

## 🔑 Authentication Flow

1. **Google OAuth Popup**
   - The app uses a **popup window** for Google login.
   - Once the user authenticates with Google, the popup redirects back to your Spring Boot backend (`/api/v1/auth/oauth2/redirect`).
   - The backend generates a **JWT access token** and sends it back to the frontend via `postMessage`.

   ```jsx
   // listener for messages from the popup
   useEffect(() => {
     const handleMessage = (event) => {
       if (event.origin !== "http://localhost:8080") return; // SECURITY: validate origin
       const { token } = event.data;
       if (token) {
         localStorage.setItem("token", token);
         navigate("/dashboard");
       }
     };
     window.addEventListener("message", handleMessage);
     return () => window.removeEventListener("message", handleMessage);
   }, []);
   ```

   **Security Notes**
   - Always check `event.origin` to prevent **malicious iframes or popups** from injecting data.
   - Tokens are only accepted from your backend origin (`https://localhost:8080` in dev, production domain in prod).

---

## 📦 Token Storage

- The **JWT access token** is stored in `localStorage` (currently).
- On each request, it’s attached as an **Authorization: Bearer** header.  

```jsx
fetch("/api/v1/artworks", {
  headers: {
    "Authorization": `Bearer ${localStorage.getItem("token")}`,
  }
});
```

**Security Notes**
- `localStorage` is easy but vulnerable to **XSS attacks**.  
  - Consider using **HTTP-only cookies** for production.  
- Use short-lived access tokens with **refresh tokens** on the backend.  

---

## 🛡️ Auth Wrapper (Route Protection)

The frontend uses an **AuthWrapper** (or ProtectedRoute) to check whether a user is authenticated before rendering a page.  

```jsx
import { Navigate } from "react-router-dom";

const AuthWrapper = ({ children }) => {
  const token = localStorage.getItem("token");
  return token ? children : <Navigate to="/login" />;
};
```

Usage:

```jsx
<Route path="/dashboard" element={
  <AuthWrapper>
    <Dashboard />
  </AuthWrapper>
} />
```

**Security Notes**
- Prevents **unauthorized access** to sensitive routes (dashboard, artwork upload, etc.).
- Frontend-only checks are **not sufficient** — the backend must also validate JWTs.

---

## 🌍 CORS & Cross-Origin Setup

- Backend configured with:

```java
registry.addMapping("/**")
        .allowedOrigins("http://localhost:5173")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("Content-Type", "Authorization")
        .allowCredentials(true);
```

**Security Notes**
- Only allow trusted origins (`localhost:5173` in dev, production domain later).
- Don’t use `*` with `.allowCredentials(true)` — this opens you up to CSRF attacks.
- Keep `Authorization` and `Content-Type` headers explicit to prevent abuse.

---

## 🖼️ UI State Management (User Context vs Wrapper)

- Earlier, a **UserContext** was considered for managing the authenticated user globally + **JWT payload decoding**.
- Now, we use a custom **AuthWrapper** instead.

Old Code:
```jsx
import jwtDecode from "jwt-decode";

const getUser = () => {
  const token = localStorage.getItem("token");
  if (!token) return null;
  return jwtDecode(token);
};
```
```jsx
const getUser = async () => {
  const token = sessionStorage.getItem("token");
  if (!token) return null;

  try {
    const response = await fetch("http://localhost:8080/api/v1/auth/users", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`,
      },
    });

    if (!response.ok) return null;

    const data = await response.json();
    return data;
  } catch (err) {
    console.error("Error fetching user:", err);
    return null;
  }
};

export default getUser;
```
**Security Notes**
- Never trust the decoded JWT blindly — the **backend must validate** the signature.
- Decoding is only for convenience (displaying username, email, etc.).
- For the AuthWrapper we fetch the user directly from the database via a custom util
---

## 🚀 Best Practices for Production

- ✅ Switch from `localStorage` to **HTTP-only cookies** for tokens.  
- ✅ Add **CSRF protection** if using cookies.  
- ✅ Set `Secure`, `SameSite=Strict`, and `HttpOnly` flags on cookies.  
- ✅ Configure frontend to only trust **your production domain**.  
- ✅ Keep access tokens short-lived and rotate them with refresh tokens.  
- ✅ Always validate tokens on the backend for **every request**.  

---

# Summary

- The React frontend handles **OAuth login, token storage, and route protection**.  
- CORS and `postMessage` checks prevent unauthorized origins.  
- Tokens are stored in `localStorage` for now (easy in dev), but cookies should be used in production.  
- Both **frontend and backend enforce security**, but the backend is the ultimate gatekeeper.  
- Users have roles, **ROLE_USER** and **ROLE_ADMIN**. Admins can GET, POST, PUT, DELETE where user can only GET and POST
  - All users who register or use their google account will automatically be ROLE_USER unless the admin adds ROLE_ADMIN to you.
