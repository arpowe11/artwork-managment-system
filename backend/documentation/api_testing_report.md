# Artwork Management System API Testing Report

All API testing was conducted using **Postman**.

---

## 1. Testing the API Endpoints (Unsecured)

The following endpoints were tested **without authentication**:

### 1.1 `/api/v1/artworks` – Get All Artworks
![GET All Artworks - 1](./screenshots/without_security/get_all_1.png)
![GET All Artworks - 2](./screenshots/without_security/get_all_2.png)
![GET All Artworks - 3](./screenshots/without_security/get_all_3.png)

### 1.2 `/api/v1/artworks/{id}` – Get Artwork by ID
![GET Artwork by ID](./screenshots/without_security/get_by_id.png)
![GET Artwork by ID - Error](./screenshots/without_security/get_by_id_err.png)

### 1.3 `/api/v1/artworks/{id}` – Delete Artwork by ID
![Delete Artwork](./screenshots/without_security/delete_by_id.png)
![Delete Artwork - Error](./screenshots/without_security/delete_by_id_err.png)

### 1.4 `/api/v1/artworks/{id}` – Update Artwork by ID
![Update Artwork](./screenshots/without_security/update_by_id.png)
![Update Artwork - Error](./screenshots/without_security/update_by_id_err.png)

### 1.5 `/api/v1/artworks` – Create New Artwork
![Create Artwork](./screenshots/without_security/post_data.png)
![Create Artwork - Error](./screenshots/without_security/post_data_err.png)

---

## 2. Testing the API Endpoints (Secured)

The following endpoints were tested **with security enabled but without authentication**:

### 2.1 `/api/v1/artworks` and `/api/v1/artworks/{id}`
All requests return **403 Forbidden** without a valid JWT token.

![GET Artwork Forbidden](./screenshots/with_security/get_by_id_forbidden.png)
![GET All Artworks Forbidden](./screenshots/with_security/get_forbidden.png)
![POST Artwork Forbidden](./screenshots/with_security/post_forbidden.png)
![Update Artwork Forbidden](./screenshots/with_security/update_forbidden.png)
![Delete Artwork Forbidden](./screenshots/with_security/delete_forbidden.png)

### 2.2 `/api/v1/auth/register` and `/api/v1/auth/login`
User registration and login endpoints were tested:

![Register User](./screenshots/users/register_user.png)
![User Saved in Database](./screenshots/users/user_saved.png)
![Login User](./screenshots/users/login_user.png)

---

## 3. Testing the API Endpoints (Secured with Auth)

The endpoints were tested **with valid JWT tokens**:

- All requests now succeed according to the user roles (`ROLE_USER` or `ROLE_ADMIN`).
- `ROLE_USER` can **GET** resources.
- `ROLE_ADMIN` can **GET, POST, PUT, DELETE** resources.

A video showcasing the authenticated API testing is available here:  
[Retest API with Auth](https://youtu.be/l-Vyt-1LBUc)

---

### Notes:

- Screenshots are organized in `/screenshots` folder by security context (`with_security` and `without_security`).
- Auth tokens must be included in the `Authorization` header as `Bearer <token>`.
- All errors are correctly handled and returned with appropriate HTTP status codes (e.g., 403, 404, 500).

