# ✅ ToDo App – Android (Kotlin)

This is a simple **To-Do list application** built using **Kotlin**, **RecyclerView**, and **SQLite** for local data storage. It features task creation, deletion, and status updates with swipe gestures and a splash screen.

---

## 🚀 Features

- 📋 Add, update, and delete tasks
- ✅ Mark tasks as completed with a checkbox
- 🔄 Swipe left to delete, swipe right to edit
- 💾 Tasks stored locally using SQLite
- 🎨 Custom splash screen
- 🌓 Material 3 theme (Dark mode supported)

---

## 🧰 Built With

- Kotlin
- Android SDK (min SDK 24)
- RecyclerView
- SQLite (custom handler)
- Material Design Components (Material 3)
- AndroidX libraries

---

## 📸 Screenshots
### Home Screen
<img src="sampledata/s1.jpeg" alt="Home Screen" width="300"/>

### Task Editing
<img src="sampledata/s2.jpeg" alt="Edit Task" width="300"/>


---

## 📂 Project Structure
```
ToDoApp/
├── app/
│ ├── src/
│ │ ├── main/
│ │ │ ├── java/com/example/todo/
│ │ │ │ ├── Adapter/
│ │ │ │ │ └── ToDoAdapter.kt
│ │ │ │ ├── Model/
│ │ │ │ │ └── ToDoModel.kt
│ │ │ │ ├── Utils/
│ │ │ │ │ └── DatabaseHandler.kt
│ │ │ │ ├── SplashActivity.kt
│ │ │ │ ├── MainActivity.kt
│ │ │ │ ├── RecyclerItemTouchHelper.kt
│ │ │ │ └── DialogCloseListener.kt
│ │ │ ├── res/
│ │ │ │ ├── layout/
│ │ │ │ │ ├── activity_main.xml
│ │ │ │ │ ├── activity_splash.xml
│ │ │ │ │ └── task_layout.xml
│ │ │ │ ├── drawable/
│ │ │ │ │ └── splash_background.xml
│ │ │ │ ├── values/
│ │ │ │ │ ├── styles.xml
│ │ │ │ │ └── themes.xml
│ │ │ │ ├── mipmap/
│ │ │ │ └── colors.xml
│ └── AndroidManifest.xml
├── build.gradle
└── README.md
```

## 🛠 Setup

- 1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/todo-app.git
- 2. Open the project in Android Studio

- 3. Run the app on an emulator or physical device


## 💡 How It Works
- Tasks are stored in a local SQLite DB using a DatabaseHandler

- The ToDoAdapter binds data to a RecyclerView

- Swipe actions are handled using ItemTouchHelper

- SplashActivity loads before the main app screen

