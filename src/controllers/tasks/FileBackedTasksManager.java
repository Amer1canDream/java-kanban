package controllers.tasks;
import java.io.*;

import controllers.Managers;
import controllers.exceptions.ManagerSaveException;
import model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        fileBackedTasksManager.createTask(new Task("Задача 1 ", "Описание задачи 1 с сохранением в файл", Status.NEW));
        fileBackedTasksManager.createTask(new Task("Задача 2 ", "Описание задачи 2 с сохранением в файл", Status.NEW));
        fileBackedTasksManager.createEpic(new Epic("Эпик 2", "Описание эпика 2 с сохранением в файл"));
        fileBackedTasksManager.createSubtask(new Subtask("Подзадача 2", "Описание подзадачи 2 с сохранением в файл", 3, Status.NEW));
        fileBackedTasksManager.getTaskById(1);
        fileBackedTasksManager.getTaskById(2);
        fileBackedTasksManager.getEpicById(3);
        fileBackedTasksManager.getSubtaskById(4);

        FileBackedTasksManager fileBackedTasksManagerLoaded = new FileBackedTasksManager();
        fileBackedTasksManagerLoaded.load(new File("resources/taskManagerDump.csv"));
        System.out.println(fileBackedTasksManagerLoaded.getTasks());
        System.out.println(fileBackedTasksManagerLoaded.getSubtasks());
        System.out.println(fileBackedTasksManagerLoaded.getEpics());
        System.out.println(fileBackedTasksManagerLoaded.getHistory());
    }

    private static final Path filePath = Path.of("resources/taskManagerDump.csv");

    public static FileBackedTasksManager loadFromFile (File file){
        FileBackedTasksManager fileBackedTasksManager = FileBackedTasksManager.load(file);
        return fileBackedTasksManager;
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

    static FileBackedTasksManager load(File file) {


        FileBackedTasksManager fileBackedTasksManager = Managers.getDefaultFileBackedManager();

        Path filePath = Path.of(file.toString());
        String fileData;
        try {
            fileData = Files.readString(filePath);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки из файла");
        }

        String[] lines = fileData.split("\n");

        for (int i = 1; i < lines.length - 2; i++) {

            Task task = Formatter.tasksFromString(lines[i]);
            String type = lines[i].split(",")[1];

            if (TasksTypes.valueOf(type).equals(TasksTypes.TASK)) {
                tasks.put(task.getId(), task);
            }
            if (TasksTypes.valueOf(type).equals(TasksTypes.SUBTASK)) {
                Subtask subtask = (Subtask) task;
                subtasks.put(task.getId(), subtask);
            }
            if (TasksTypes.valueOf(type).equals(TasksTypes.EPIC)) {
                Epic epic = (Epic) task;
                epics.put(task.getId(), epic);
            }
        }

        String historyLine = lines[lines.length - 1];

        List<Integer> historyIds = Formatter.historyFromString(historyLine);
        for (Integer taskId : historyIds) {
            // Для каждого спаршеного айдишника проверяем есть ли такие в Тасках, Сабтасках и Эпиках
            // И добавляем его в нужный список
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
