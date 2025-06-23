# IrakliPlants 🌿 – Plant Care App

**IrakliPlants** is a personal plant guide app that allows users to:
- View a list of plants with detailed information
- Add new plants (specific to their own account)
- Sign up, sign in, and manage their profile using Firebase Authentication

## 📱 App Purpose
- Create a digital plant library
- Allow users to manage their own added plants
- Offer a clean and user-friendly interface for navigation and interaction

## ⚙️ Technologies Used
- **Kotlin** — for all backend and logic implementation
- **XML** — for building intuitive and responsive layouts
- **RecyclerView** — for dynamic list display of plant items
- **Single Activity Architecture** — entire app runs on one Activity using multiple Fragments
- **Navigation Component** — handles all fragment navigation seamlessly
- **BottomNavigationView** — for switching between Plant List, Add Plant, and Profile screens
- **Room (SQLite)** — local database to store plant data tied to user accounts
- **Firebase Authentication** — for secure user sign-in, sign-up, and password reset
- **Firebase Realtime Database** — optionally used for storing base plant data accessible to all users
- **ViewModel + Repository + LiveData** — following MVVM architecture for data handling
  
## 📦 Features
- Display a dynamic list of all plants added by the current user
- Open a detail page with full plant information (including optional photo, blooming season, Wikipedia link, etc.)
- Add a plant with optional photo from device gallery
- Firebase-based sign up, sign in, and sign out
- Password reset and password change functionality
- Profile screen and About App information screen
- Bottom Navigation Bar for quick access to all sections

## 🔐 Firebase Authentication
- Sign Up and Sign In functionality with form validation
- Forgot Password feature
- Sign Out from profile

## 💾 Room Local Database
- All added plants are saved locally using Room
- Each user can only view the plants they personally added

## ✅ Requirements to Run
- Android Studio (Arctic Fox or later)
- API Level 26 or higher (Minimum Android 7.0)
- Internet connection (required for Firebase)
- Gradle build must be synced successfully

## 🚀 Future Improvements (Optional Ideas)
- Use Firebase Firestore for real-time cloud syncing
- Add categories or filters for plants
- Push notifications for plant care reminders

---

![image](https://github.com/user-attachments/assets/8bc85601-e442-40cd-acf6-a91fd442b2bc)
![image](https://github.com/user-attachments/assets/9b28aa98-b402-4d9f-b393-0f7e33e4de12)
![image](https://github.com/user-attachments/assets/5348b11e-3ff1-4f47-a2b0-ec2bf094c6f2)
![image](https://github.com/user-attachments/assets/fe26ede1-fc38-41a2-9bce-c82a49c8ca74)





Created by Irakli Matcharashvili
