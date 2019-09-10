import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a storage for users' tasklist.
 */
public class Storage {
    private static String filePath;
    private ArrayList<Task> taskList;

    /**
     * Represents a storage for users' tasklist.
     */
    public Storage(String filePath) {
        assert filePath != "" : "file path cannot be empty";
        this.filePath = filePath;
        this.taskList = new ArrayList<>();
    }

    /**
     * Returns user's tasklist.
     *
     * @return arraylist of tasks
     * @throws IOException occurs when file does not exist or is corrupted
     */
    public ArrayList<Task> load() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = null;

        while ((line = reader.readLine()) != null) {
            char taskType = line.charAt(1);
            char isDone = line.charAt(4);
            if (taskType == 'T') {
                String description = line.substring(7);
                Task todo = new ToDo(description);
                if (isDone == '0') {
                    taskList.add(todo);
                } else {
                    todo.markAsDone();
                    taskList.add(todo);
                }
            } else {
                String date = "";
                String time = "";
                String description = line.substring(7, line.indexOf('(') - 1);
                String by = line.substring(line.indexOf(':') + 2, line.indexOf(')')).trim();
                String[] dateTimeSplit = by.split(" ");

                if (dateTimeSplit.length == 3) {
                    date = dateTimeSplit[0] + " " + dateTimeSplit[1] + " "
                                   + dateTimeSplit[2].substring(0, dateTimeSplit[2].length() - 1);
                    time = "";
                } else if (dateTimeSplit.length == 4) {
                    date = dateTimeSplit[0] + " " + dateTimeSplit[1] + " "
                                   + dateTimeSplit[2].substring(0, dateTimeSplit[2].length() - 1);
                    time = dateTimeSplit[3];
                }

                Task task = taskType == 'D'
                    ? new Deadline(description, new DateTime(date, time))
                    : new Event(description, new DateTime(date, time));

                if (isDone == '0') {
                    taskList.add(task);
                } else {
                    task.markAsDone();
                    taskList.add(task);
                }
            }
        }
        reader.close();
        return taskList;
    }

    /**
     * Saves the current tasks in tasklist to the file specified by user.
     *
     * @throws IOException occurs when file does not exist or is corrupted
     */
    public static void save() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        for (Task task : TaskList.getCurrentTasks()) {
            writer.write(task + "\n");
        }
        writer.close();
    }
}
