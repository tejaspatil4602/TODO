import java.io.*;
import java.util.*;

public class TaskTracker {
    private static final String FILE_NAME = "tasks.json";

    // Load tasks from file
    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return tasks;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) json.append(line);

            String data = json.toString().trim();
            if (data.startsWith("[") && data.endsWith("]")) {
                data = data.substring(1, data.length() - 1).trim();
                if (!data.isEmpty()) {
                    String[] items = data.split("},\\s*\\{");
                    for (String item : items) {
                        item = item.replace("{", "").replace("}", "").trim();
                        String[] fields = item.split(",");
                        int id = 0; String title = ""; Status status = Status.TODO;

                        for (String field : fields) {
                            String[] pair = field.split(":");
                            String key = pair[0].replace("\"", "").trim();
                            String value = pair[1].replace("\"", "").trim();

                            switch (key) {
                                case "id": id = Integer.parseInt(value); break;
                                case "title": title = value; break;
                                case "status": status = Status.valueOf(value); break;
                            }
                        }
                        Task t = new Task(id, title);
                        t.setStatus(status);
                        tasks.add(t);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading file.");
        }
        return tasks;
    }

    // Save tasks to file
    public static void saveTasks(List<Task> tasks) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            pw.println("[");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                pw.print("  {\"id\":" + t.getId()
                        + ",\"title\":\"" + t.getTitle()
                        + "\",\"status\":\"" + t.getStatus() + "\"}");
                if (i < tasks.size() - 1) pw.println(",");
            }
            pw.println("\n]");
        } catch (IOException e) {
            System.out.println("❌ Error saving file.");
        }
    }

    // ➡ Add new task
    public static void addTask(String title) {
        List<Task> tasks = loadTasks();
        int newId = tasks.isEmpty() ? 1 : tasks.get(tasks.size() - 1).getId() + 1;
        Task newTask = new Task(newId, title);
        tasks.add(newTask);
        saveTasks(tasks);
        System.out.println("✅ Task added: " + newTask);
    }
    // ➡ Update task title
    public static void updateTask(int id, String newTitle) {
        List<Task> tasks = loadTasks();
        boolean found = false;

        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setTitle(newTitle);
                found = true;
                break;
            }
        }
        if (found) {
            saveTasks(tasks);
            System.out.println("✅ Task updated.");
        } else {
            System.out.println("❌ Task not found.");
        }
    }

    // ➡ Delete a task
    public static void deleteTask(int id) {
        List<Task> tasks = loadTasks();
        boolean removed = tasks.removeIf(t -> t.getId() == id);

        if (removed) {
            saveTasks(tasks);
            System.out.println("✅ Task deleted.");
        } else {
            System.out.println("❌ Task not found.");
        }
    }

    // ➡ Mark task with new status
    public static void markTask(int id, Status newStatus) {
        List<Task> tasks = loadTasks();
        boolean found = false;

        for (Task t : tasks) {
            if (t.getId() == id) {
                t.setStatus(newStatus);
                found = true;
                break;
            }
        }
        if (found) {
            saveTasks(tasks);
            System.out.println("✅ Task status updated to " + newStatus);
        } else {
            System.out.println("❌ Task not found.");
        }
    }

    // ➡ List tasks by status
    public static void listByStatus(Status status) {
        List<Task> tasks = loadTasks();
        boolean any = false;
        for (Task t : tasks) {
            if (t.getStatus() == status) {
                System.out.println(t);
                any = true;
            }
        }
        if (!any) {
            System.out.println("📂 No tasks with status: " + status);
        }
    }

    // ➡ List all tasks
    public static void listTasks() {
        List<Task> tasks = loadTasks();
        if (tasks.isEmpty()) {
            System.out.println("📂 No tasks found.");
        } else {
            for (Task t : tasks) {
                System.out.println(t);
            }
        }
    }

    // ➡ Main method for CLI
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Task Tracker =====");
            System.out.println("1. Add Task");
            System.out.println("2. List All Tasks");
            System.out.println("3. List Done Tasks");
            System.out.println("4. List In-Progress Tasks");
            System.out.println("5. List Not Done Tasks");
            System.out.println("6. Update Task Title");
            System.out.println("7. Delete Task");
            System.out.println("8. Mark Task as In Progress");
            System.out.println("9. Mark Task as Done");
            System.out.println("10. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task title: ");
                    String title = sc.nextLine();
                    addTask(title);
                    break;

                case 2:
                    listTasks();
                    break;

                case 3:
                    listByStatus(Status.DONE);
                    break;

                case 4:
                    listByStatus(Status.IN_PROGRESS);
                    break;

                case 5:
                    listByStatus(Status.TODO);
                    break;

                case 6:
                    System.out.print("Enter task id to update: ");
                    int idU = sc.nextInt(); sc.nextLine();
                    System.out.print("Enter new title: ");
                    String newTitle = sc.nextLine();
                    updateTask(idU, newTitle);
                    break;

                case 7:
                    System.out.print("Enter task id to delete: ");
                    int idD = sc.nextInt();
                    deleteTask(idD);
                    break;

                case 8:
                    System.out.print("Enter task id to mark In Progress: ");
                    int idIP = sc.nextInt();
                    markTask(idIP, Status.IN_PROGRESS);
                    break;

                case 9:
                    System.out.print("Enter task id to mark Done: ");
                    int idDone = sc.nextInt();
                    markTask(idDone, Status.DONE);
                    break;

                case 10:
                    System.out.println("👋 Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice, try again.");
            }
        }
    }


}
