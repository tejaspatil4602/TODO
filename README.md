Project url -- https://roadmap.sh/projects/task-tracker

# 📝 Task Tracker

A simple **Command Line Interface (CLI)** tool to manage your tasks.  
You can add, update, delete, and mark tasks as `TODO`, `IN_PROGRESS`, or `DONE`.  
All tasks are stored in a local **JSON file (`tasks.json`)**.

---

## 🚀 Features
- Add new tasks  
- Update or delete tasks  
- Mark tasks as **TODO**, **IN_PROGRESS**, or **DONE**  
- List all tasks or filter by status  
- Data persistence using `tasks.json`  

---

## ⚙️ How to Run

1. Compile the files:
   ```bash
   javac *.java
2.Run the application with a command:
java TaskTracker <command> [args]

📌 Commands
=================================================================================
| Command                   | Description                                        |
| ------------------------- | -------------------------------------------------- |
| `add <title>`             | Add a new task                                     |
| `list`                    | List all tasks                                     |
| `list done`               | List completed tasks                               |
| `list notdone`            | List tasks not yet done                            |
| `list progress`           | List tasks in progress                             |
| `update <id> <new title>` | Update task title                                  |
| `delete <id>`             | Delete a task                                      |
| `mark <id> <status>`      | Change task status (`TODO`, `IN_PROGRESS`, `DONE`) |
----------------------------------------------------------------------------------


📂 File Storage
All tasks are stored in a file named tasks.json in the current directory.
The file is automatically created if it doesn’t exist.


🌱 Future Improvements
Add deadlines for tasks
Add task priority levels
Improve JSON parsing
