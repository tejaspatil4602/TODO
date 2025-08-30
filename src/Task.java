public class Task {
    private int id;
    private String title;
    private Status status;

    // Constructor
    public Task(int id, String title) {
        this.id = id;
        this.title = title;
        this.status = Status.TODO; // default when created
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public Status getStatus() { return status; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setStatus(Status status) { this.status = status; }

    // To display task nicely
    @Override
    public String toString() {
        return id + ". " + title + " [" + status + "]";
    }
}
