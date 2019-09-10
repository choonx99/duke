import java.text.ParseException;

/**
 * The Ui deals with interactions with the user.
 * A Ui object includes greetings and a read input function
 * to receive user interactions and respond accordingly.
 */
public class Ui {
    public void greet() {
        System.out.println("Hello! I'm Duke\nWhat can i do for you?");
    }

    public void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints all tasks currently in the tasklist.
     */
    public static String printTaskList() {
        return TaskList.printTasks();
    }

    /**
     * Search in a tasklist for the tasks that matches the term provided.
     *
     * @param term Used to search for tasks that matches the term
     */
    public static String findTasks(String term) {
        return TaskList.findTasks(term);
    }

    /**
     * Adds a task into the tasklist.
     *
     * @param task The task to be added into tasklist
     */
    public static String addTaskToTaskList(Task task) {
        TaskList.addTask(task);
        String output = "Got it. I've added this task: \n" + task + "\n" + "Now you have "
                    + TaskList.getTaskListSize() + " tasks in the list.";
        return output;
    }

    /**
     * Deletes a task from the tasklist by calling delTask from TaskList class.
     *
     * @param taskNum The task number of the task to be deleted
     */
    public static String delTaskInTaskList(int taskNum) {
        Task taskToRemove = TaskList.getTaskAt(taskNum);
        TaskList.delTask(taskNum);
        String output = "Noted. I've removed this task: \n" + taskToRemove + "\n"
                    + "Now you have " + TaskList.getTaskListSize() + " tasks in the list.";
        return output;
    }

    /**
     * Reads users' commands and respond accordingly.
     */
    public static String readInput(String userInput) {
        if (userInput.equals("bye")) {
            return "goodbye!";
        } else if (userInput.equals("list")) {
            return printTaskList();
        } else {
            String[] userInputSplit = userInput.split(" ");
            try {
                Parser.handleInput(userInputSplit[0], userInput);
            } catch (DukeException ex) {
                return ex.getMessage();
            } catch (ParseException ex) {
                return "OOPS!!! The Date/Time field is invalid";
            }

            if (userInput.startsWith("find")) {
                return findTasks(userInputSplit[1]);
            } else if (userInput.startsWith("done")) {
                int taskNumber = Integer.parseInt(userInput.substring(5));
                Task selectedTask = TaskList.getTaskAt(taskNumber);
                selectedTask.markAsDone();
                return "Nice! I've marked this task as done: \n" + selectedTask;
            } else if (userInput.startsWith("delete")) {
                int taskNumber = Integer.parseInt(userInput.substring(7));
                return delTaskInTaskList(taskNumber);
            } else if (userInput.startsWith("todo")) {
                String description = userInput.substring(5);
                return addTaskToTaskList(new ToDo(description));
            } else if (userInput.startsWith("deadline")) {
                String description = userInput.substring(9, userInput.indexOf('/') - 1);
                String by = userInput.substring(13 + description.length()).trim();

                try {
                    Parser.handleInput("by", by);
                    return addTaskToTaskList(new Deadline(description, new DateTime(by)));
                } catch (DukeException ex) {
                    return ex.getMessage();
                } catch (ParseException ex) {
                    return "OOPS!!! The Date/Time field is invalid";
                }
            } else if (userInput.startsWith("event")) {
                String description = userInput.substring(6, userInput.indexOf('/') - 1);
                String at = userInput.substring(10 + description.length()).trim();
                try {
                    Parser.handleInput("at", at);
                    return addTaskToTaskList(new Event(description, new DateTime(at)));
                } catch (DukeException ex) {
                    return ex.getMessage();
                } catch (ParseException ex) {
                    return "OOPS!!! The Date/Time field is invalid";
                }
            } else {
                return "Something went wrong";
            }
        }
    }

    public static String showLoadingError() {
        return "Unable to load file from file path";
    }

    public static String showSavingError() {
        return "Unable to save onto file";
    }
}

