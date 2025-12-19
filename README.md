# 🚚 Transport Company Management System

A comprehensive web application for managing transport company operations, including vehicles, employees, clients, and transport services.

## 🎯 Features

- **Company Management**: Manage transport companies and their operations
- **Fleet Management**: Track vehicles (buses, trucks, tankers, vans)
- **Employee Management**: Manage drivers and staff with qualifications
- **Client Management**: Maintain client database
- **Transport Operations**: Record and track transport services (goods & passengers)
- **Payment Tracking**: Monitor payment status for transport services
- **Reports & Analytics**: Generate reports on revenue, transports, and driver performance

## 🛠️ Tech Stack

### Backend
- **Java 21**
- **Hibernate ORM** (without Spring)
- **Javalin** (Lightweight REST API framework)
- **MySQL** (Azure Flexible Server)
- **Gradle** (Build tool)

### Frontend
- **React 18**
- **Vite** (Build tool)
- **Material-UI (MUI)** (Component library)
- **React Query** (Data fetching)
- **React Router** (Navigation)

### Infrastructure
- **Azure App Service** (Backend hosting)
- **Azure Static Web Apps** (Frontend hosting)
- **Azure MySQL Flexible Server** (Database)
- **GitHub Actions** (CI/CD pipeline)

## 📁 Project Structure
```
transport-company-app/
├── backend/          # Java backend application
│   ├── src/
│   │   └── main/
│   │       ├── java/
│   │       └── resources/
│   └── build.gradle
├── frontend/         # React frontend application
│   ├── src/
│   └── package.json
└── .github/
    └── workflows/    # CI/CD pipelines
```

## 🚀 Getting Started

### Prerequisites
- Java 21
- Node.js 18+
- MySQL 8.0+
- Gradle

### Backend Setup
```bash
cd backend
./gradlew build
./gradlew run
```

### Frontend Setup
```bash
cd frontend
npm install
npm run dev
```

## 🌿 Git Workflow

- `main` - Production branch (protected)
- `develop` - Development branch (auto-deploy to Azure)
- `feature/*` - Feature branches

## 📄 License

This project is part of an academic assignment.

## 👨‍💻 Author

Developed as a university project for transport company management.
