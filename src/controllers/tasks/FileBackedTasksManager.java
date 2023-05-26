package controllers.tasks;
import java.io.*;

import controllers.Managers;
import controllers.exceptions.ManagerSaveException;
import controllers.histroy.InMemoryHistoryManager;
import model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    private static final Path filePath = Path.of("src/resources/taskManagerDump.csv");
    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }
    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }
    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }
    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }
    @Override
    public void updateSubtask(Integer id ,Subtask subtask) {
        super.updateSubtask(id, subtask);
        save();
    }
    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }
    @Override
    public void deleteTaskById (Integer id) {
        super.deleteTaskById(id);
        save();
    }
    @Override
    public void deleteSubtaskById(Integer id) {
        super.deleteSubtaskById(id);
        save();
    }
    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    protected void save() {
        try {
            Writer fileWriter = new FileWriter(filePath.toFile());
            fileWriter.write("id,type,name,status,description,epic\n");

            String lines = Formatter.tasksToString(this) + "\n" + Formatter.historyToString(historyManager);

            fileWriter.write(lines);
            fileWriter.close();

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    public static TaskManager load(Path filePath) {

        TaskManager fileBackedTasksManager = Managers.getDefault();

        try {
            String file = Files.readString(filePath);
            String[] lines = file.split("\n");

            for (int i = 1; i < lines.length - 2; i++) {

                var task = Formatter.tasksFromString(lines[i]);
                String type = lines[i].split(",")[1];

                if (TasksTypes.valueOf(type).equals(TasksTypes.TASK)) {
                    fileBackedTasksManager.createTask(task);
                }
                if (TasksTypes.valueOf(type).equals(TasksTypes.SUBTASK)) {
                    Subtask subtask = (Subtask) task;
                    fileBackedTasksManager.createSubtask(subtask);
                }
                if (TasksTypes.valueOf(type).equals(TasksTypes.EPIC)) {
                    Epic epic = (Epic) task;
                    fileBackedTasksManager.createEpic(epic);
                }
            }

            String historyLine = lines[lines.length - 1];
            String[] historyTaskIds = historyLine.split(",");

            for (String historyTaskId: historyTaskIds) {
                Integer taskId = Integer.parseInt(historyTaskId);
                // Для каждого спаршеного айдишника проверяем есть ли такие в Тасках, Сабтасках и Эпиках
                // И добавляем его в нужный список
                if ( fileBackedTasksManager.getTasks().containsKey(taskId) ) {
                    fileBackedTasksManager.getTaskById(taskId);
                } else if ( fileBackedTasksManager.getSubtasks().containsKey(taskId) ) {
                    fileBackedTasksManager.getSubtaskById(taskId);
                } else if ( fileBackedTasksManager.getEpics().containsKey(taskId) ) {
                    fileBackedTasksManager.getEpicById(taskId);
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки из файла");
        }

        return fileBackedTasksManager;
    }
}
