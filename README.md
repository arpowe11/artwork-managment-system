# Artwork Management System

Welcome to the **Artwork Management System**, a full-stack project for managing and organizing artwork collections. This application is built using **React**, **Python**, **Spring Boot**, and **MongoDB**, and it is fully tested using **Vitest** and **Jest** on the frontend and **JUnit** and **Mockito** on the backend.

---

## Project Structure

### Frontend
- Built with **React** for a responsive and interactive user interface.
- Features include artwork ingestion, management, and user account handling.

#### Frontend Resources:
- **Implementation Details:** [Frontend Implementation](./frontend/README.md)  
- **Testing Report:** [Frontend Testing](./frontend/documents/testing_report.md)

---

### Backend
- Built with **Spring Boot** and **MongoDB** for a robust and scalable API.
- Handles authentication, artwork CRUD operations, and database interactions.

#### Backend Resources:
- **Implementation Details:** [Backend Implementation](./backend/README.md)  
- **Testing Report:** [Backend Testing](./backend/documentation/testing_report.md)

---

### Data Injestion Service:
- Built with **Python** for quick and easy scripting for fetching json data from a free artwork api
- Handles injesting of free artwork API data and stores it into a  MongoDB Collection
- Exposed to the frontend via **Flask** endpoint

---

## Testing Overview
- **Frontend:** Vitest + Jest for unit and integration tests.
- **Backend:** JUnit + Mockito for service and controller tests.
- Code coverage is tracked and documented for both frontend and backend components.

---

## Deployment
- **Docker** Docker images pushed to Azure Container Registry
- **Cloud Provider** Azure Cloud
- **CI/CD** Github Actions
- Docs for deployment can be found [here](./docs/deployment.md)

## Documentation
- All project-related documentation, including testing reports and implementation guides, is available in the respective `documents` or `documentation` folders.

---

This system demonstrates full-stack development, testing best practices, and code coverage reporting for both frontend and backend components.
