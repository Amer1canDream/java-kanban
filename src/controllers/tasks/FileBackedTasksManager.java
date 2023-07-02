package controllers.tasks;
import java.io.*;

import controllers.exceptions.ManagerSaveException;
import model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private Path filePath;

    public FileBackedTasksManager () {}
    public FileBackedTasksManager (Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Task getTaskById(Integer id) {
        Task returnedTask = super.getTaskById(id);
        save();
        return returnedTask;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask returnedSubtask = super.getSubtaskById(id);
        save();
        return returnedSubtask;
    }
    @Override
    public Epic getEpicById(Integer id) {
        Epic returnedEpic = super.getEpicById(id);
        save();
        return returnedEpic;
    }
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
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
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

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    protected void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath.toFile()));
             BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath.toFile()))) {

            if (bufferedReader.readLine() == null) {
                String header = "id,type,name,status,description,startTime,duration,endTime,epic" + "\n";
                bufferedWriter.write(header);
            }

            String lines = Formatter.tasksToString(this) + "\n" + Formatter.historyToString(historyManager);
            bufferedWriter.write(lines);

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    public static FileBackedTasksManager loadFromFile (File file) {
        Path filePath = Path.of(file.toString());
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(filePath);
        String fileData;
        try {
            fileData = Files.readString(filePath);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки из файла");
        }

        if ( fileData.isBlank()) {
            return fileBackedTasksManager;
        }

        String[] lines = fileData.split("\n");

        for (int i = 1; i < lines.length - 2; i++) {

            Task task = Formatter.tasksFromString(lines[i]);

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

        List<Integer> historyIds = Formatter.historyFromString(historyLine);
        for (Integer taskId : historyIds) {
            if (fileBackedTasksManager.getTasks().containsKey(taskId)) {
                fileBackedTasksManager.getTaskById(taskId);
            } else if (fileBackedTasksManager.getSubtasks().containsKey(taskId)) {
                fileBackedTasksManager.getSubtaskById(taskId);
            } else if (fileBackedTasksManager.getEpics().containsKey(taskId)) {
                fileBackedTasksManager.getEpicById(taskId);
            }
        }
        return fileBackedTasksManager;
    }

}
