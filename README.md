# ✨ News App
A modern Android application built using **MVVM with Clean Architecture** and **Jetpack Compose**, focusing on best practices in UI design, data management, and user experience. 
# 🏗️ Architecture & Core Principles 
This project is structured using the robust **Clean Architecture** combined with the **MVVM (Model-View-ViewModel)** design pattern to ensure scalability, testability, and clear separation of concerns. 
* MVVM with Clean Architecture:
Separates the application into distinct layers (Presentation, Domain, Data) for maximum modularity and testability.
* Repository Pattern: Abstracts data sources (local and remote) from the ViewModel, managed within the Data layer.
# 🏗️ Architecture Overview
This project is built on a clear and scalable architecture to ensure long-term maintainability and high testability.
# 🔹Layers
* Presentation Layer: Jetpack Compose UI + ViewModels
* Domain Layer: Use cases & business logic
* Data Layer: Repositories, Retrofit services, Room DAOs
# 🔹Core Principles
* MVVM Architecture: Efficient state management with lifecycle awareness
* Clean Architecture: Strict separation between UI, business logic, and data sources
* Repository Pattern: Abstracted data handling (local & remote sources)

# 🛠 Technology Stack (Built With)

| Category | Technology | Description |
| :--- | :--- | :--- |
| **Architecture** | **Hilt** | Dependency injection for modular and testable code |
| **Architecture** | **ViewModel** | Manages UI state cleanly and lifecycle-aware |
| **UI/UX** | **Jetpack Compose** | Modern toolkit for building native Android UI declaratively. |
| **UI/UX** | **Material Design 3** | Implements Google's latest design system for a modern aesthetic. |
| **UI/UX** | **Navigation Component** | Manages in-app navigation, handling fragments and composables. |
| **Asynchronous** | **Coroutines & Flow** | Handles asynchronous operations efficiently and manages data streams. |
| **Data Persistence** | **Room Database** | Robust SQLite object mapping for local data storage. |
| **Data Persistence** | **Data Store** | Modern and safe solution for storing small, simple key-value pairs or typed objects. |
| **Networking** | **Retrofit** | Type-safe HTTP client for interacting with REST APIs. |
| **Data/Media** | **Coil** | Fast, lightweight image loading library for Android. |
# 🌍 Localization & User Preferences
* In-App Language Switching:
  Users can change the app language through a built-in language selector powered by DataStore.
* Localized Date Formatting:
  Dates are displayed based on the selected locale for a seamless user experience.
# 🔗 Article Sharing
* Users can share news links with other apps using the system-level “Share” mechanism.
# 📱 Modern UI/UX
* Clean Material 3 design
* Smooth animations
* Compose-based layouts optimized for phones & tablets
# ⚙️ Project Setup
1. **Clone** the repository.
2. Open the project in **Android Studio (latest version)**
3. Sync Gradle
4. Run on a physical device or emulator
# Screens
![1](https://github.com/user-attachments/assets/5996bb99-0a82-4f0d-b38e-b902fa3fb7d9)
![2](https://github.com/user-attachments/assets/6fc9d67a-e674-44ce-bb65-0c6ca07ec9c6)
![3](https://github.com/user-attachments/assets/0e7bfde7-f1e6-4d1a-ae13-613b96f58d9e)
![4](https://github.com/user-attachments/assets/cda97f45-1553-45cf-b498-3489b5532610)
# 🧩 Folder Structure (Simplified)
```
data/
    api/
    dto/
    local/
    mapper/
di/
domain/
    repository/
    state/
presentation/
    home/
    favorite/
    detail/
navigation/
util/
```
# 📬 Contact
If you have improvements or questions, feel free to open an issue or contribute!
