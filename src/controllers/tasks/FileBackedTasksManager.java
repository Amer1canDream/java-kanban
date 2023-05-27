package controllers.tasks;
import java.io.*;

import controllers.Managers;
import controllers.exceptions.ManagerSaveException;
import model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {

    private static final Path filePath = Path.of("resources/taskManagerDump.csv");

    @Override
    public Task getTaskById(Integer id) {
        Task returnedTask = super.getTaskById(id);
        save();
        //System.out.println(returnedTask);
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

            //System.out.println(Formatter.historyToString(historyManager));
            String lines = Formatter.tasksToString(this) + "\n" + Formatter.historyToString(historyManager);
            //System.out.println(Formatter.historyToString(historyManager));
            bufferedWriter.write(lines);

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл");
        }
    }

    public TaskManager load(Path filePath) {


        TaskManager fileBackedTasksManager = Managers.getDefaultFileBackedManager();

        try {
            String file = Files.readString(filePath);
            System.out.println(file);
            String[] lines = file.split("\n");

            for (int i = 1; i < lines.length - 2; i++) {

                Task task = Formatter.tasksFromString(lines[i]);
                String type = lines[i].split(",")[1];

                if (TasksTypes.valueOf(type).equals(TasksTypes.TASK)) {
                    super.tasks.put(task.getId(), task);
                }
                if (TasksTypes.valueOf(type).equals(TasksTypes.SUBTASK)) {
                    Subtask subtask = (Subtask) task;
                    super.subtasks.put(task.getId(), subtask);
                }
                if (TasksTypes.valueOf(type).equals(TasksTypes.EPIC)) {
                    Epic epic = (Epic) task;
                    super.epics.put(task.getId(), epic);
                }
            }

            String historyLine = lines[lines.length - 1];

            List<Integer> historyIds = Formatter.historyFromString(historyLine);
            //System.out.println(historyIds);
            for (Integer taskId: historyIds) {
                //System.out.println(taskId);
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
        //System.out.println(historyManager);
        //System.out.println(fileBackedTasksManager.getHistory());
        return fileBackedTasksManager;
    }
}
