HackSie Task Manager is an Android application built for organizing and delegating tasks within a team.  
The project uses Firebase as a backend for real-time synchronization and authentication, allowing users to log in, manage their own tasks, and assign them to others.
The goal of the app is to make collaboration easier by providing a clear view of who is responsible for each task and how tasks are distributed between team members.

# Features:

1. User Authentication
The app uses Firebase Authentication for user login and registration.  
Each user has a personal account and can access only their assigned or created tasks.

2. Real-Time Database (Firestore)
All data is stored and updated in real-time using Firebase Firestore.  
Any change, such as adding, editing, or delegating a task, is instantly visible to all users without the need to refresh.

3. Task Management
Users can:
- Create new tasks with a title, description, and deadline  
- Edit or delete existing tasks  
- Assign tasks to other users within the app  

Each task is represented visually as a card containing all relevant information.

4. Task Delegation Diagram
The app includes a visual diagram that shows the delegation flow between users.  
For example, if user X delegates a task to user Y, the diagram displays a link `X → Y`.  
This helps users understand the structure of responsibilities and collaboration within the team.

5. Dark / Light Mode
The interface supports both dark and light modes, allowing users to choose the theme they prefer.

# Tech Stack:
-Language: Java  
- Database: Firebase Firestore  
- Authentication: Firebase Auth  
- IDE: Android Studio  
