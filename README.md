# QuickNotes

QuickNotes is an Android application built using Kotlin and Clean Architecture (MVVM) for rapid note-taking and organization.  
It demonstrates modern Android development practices with a layered architecture, Jetpack libraries, and local + cloud data handling.

---

## Features

- Add, edit, and delete notes quickly and easily  
- Swipe-to-delete with undo functionality  
- Color-patterned note cards for better organization  
- Offline storage using Room Database and optional sync to Firebase Firestore  
- Search notes by title or content  
- Jetpack Compose / Material 3 UI  
- Clean Architecture structure with clear separation of data, domain, and presentation layers  

---

## Project Architecture

The project follows Clean Architecture + MVVM to ensure maintainability and testability:

```
app/
 ├── data/
 │   ├── model/           # Data layer models (Room entities, DTOs)
 │   ├── local/           # Room database, DAOs
 │   ├── remote/          # Firestore integration, repository implementations
 │   ├── repository/      # Concrete implementations of repository interfaces
 │
 ├── domain/
 │   ├── model/           # Domain models
 │   ├── repository/      # Repository interfaces
 │   ├── use_case/        # Use-cases / business logic
 │
 ├── presentation/
 │   ├── ui/              # Compose UI or XML layouts
 │   ├── viewmodel/       # ViewModels for state management
 │
 ├── di/                  # Dependency injection setup (Hilt or Koin)
 └── util/                # Utility classes and extensions
```

---

## Tech Stack

| Category | Technology |
|-----------|-------------|
| Language | Kotlin |
| Architecture | Clean Architecture (MVVM) |
| Local Storage | Room Database |
| Cloud Sync | Firebase Firestore |
| Coroutines & Flow | Kotlin Coroutines + Flow |
| UI | Jetpack Compose | Material 3 |
| Jetpack Navigation |
| Dependency Injection | Hilt |

---

## Setup Instructions

1. Clone the repository  
   ```bash
   git clone https://github.com/skubaid5837/QuickNotes.git
   ```

2. Open in Android Studio  
   - Use Android Studio Ladybug or newer.  
   - Import the project as a Gradle project.

3. Configure Firebase (optional)  
   - Add your `google-services.json` file inside the `app/` folder.  
   - Ensure Firestore Database is enabled in your Firebase project.

4. Build & Run  
   - Select a device or emulator and click Run.

---

## Learning Highlights

This project demonstrates:
- Modular and clean code structure.  
- Reactive state management using Kotlin Flow.  
- Repository pattern and use-case separation.  
- Dependency injection with Hilt.  
- Offline-first architecture with Firestore sync.  
- Following Android best practices and Material Design guidelines.

---

## Folder Structure Overview

```
QuickNotes/
 ├── app/
 │   ├── data/
 │   ├── domain/
 │   ├── presentation/
 │   ├── di/
 │   └── util/
 ├── build.gradle.kts
 └── settings.gradle.kts
```

---


## Contributing

Contributions are welcome.  
Feel free to open a pull request or report bugs in the [Issues](https://github.com/skubaid5837/QuickNotes/issues) section.

---

## Author

Ubaidur Rahman Shaikh  
Android Developer | Kotlin Enthusiast  
Email: sk.ubaid.connect@gmail.com  
