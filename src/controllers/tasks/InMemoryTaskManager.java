package controllers.tasks;

import controllers.Managers;
import controllers.exceptions.IntersectionException;
import controllers.exceptions.ManagerDeleteTaskException;
import controllers.exceptions.ManagerGetTaskException;
import controllers.history.HistoryManager;
import model.*;
import java.time.Duration;

import java.time.Instant;
import java.util.*;

public class InMemoryTaskManager implements TaskManager, Comparator<Task>{
    protected HashMap<Integer, Task> tasks  = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks  = new HashMap<>();
    protected HashMap<Integer, Epic> epics  = new HashMap<>();
    private Set<Task> prioritizedTasks = new TreeSet<>(this::compare);

    protected HistoryManager historyManager = Managers.getDefaultHistory();
    private int id;
    @Override
    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }
    @Override
    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }
    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }
    @Override
    public void deleteAllTasks() {
        tasks.clear();
        subtasks.clear();
        epics.clear();
        historyManager.removeAll();
    }
    @Override
    public Task getTaskById(Integer id) {
        Task value = tasks.get(id);
        if (id != null ) {
            if ( value != null ) {
                historyManager.add(tasks.get(id));
                return (tasks.get(id));
            } else {
                throw new ManagerGetTaskException("Задача с таким ID не найдена");
            }
        } else {
            throw new ManagerGetTaskException("Поле ID пустое");
        }
    }
    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask value = subtasks.get(id);
        if (id != null ) {
            if ( value != null ) {
                historyManager.add(subtasks.get(id));
                return(subtasks.get(id));
            } else {
                throw new ManagerGetTaskException("Задача с таким ID не найдена");
            }
        } else {
            throw new ManagerGetTaskException("Поле ID пустое");
        }
    }
    @Override
    public Epic getEpicById(Integer id) {
        Epic value = epics.get(id);
        if (id != null ) {
            if ( value != null ) {
                historyManager.add(epics.get(id));
                return(epics.get(id));
            } else {
                throw new ManagerGetTaskException("Задача с таким ID не найдена");
            }
        } else {
            throw new ManagerGetTaskException("Поле ID пустое");
        }
    }
    @Override
    public void deleteTaskById(Integer id) {
        Task value = tasks.get(id);
        if (id != null ) {
            if ( value != null ) {
                historyManager.remove(id);
                tasks.remove(id);
            } else {
                throw new ManagerDeleteTaskException("Задача с таким ID не найдена");
            }
        } else {
            throw new ManagerDeleteTaskException("Поле ID пустое");
        }
    }

    @Override
    public void deleteEpicById(Integer id) {
        Epic value = epics.get(id);
        if (id != null ) {
            if (value != null) {
                Epic epic = epics.get(id);
                for (Integer subtasksId : epic.getSubtasks()) {
                    historyManager.remove(subtasksId);
                    subtasks.remove(subtasksId);
                }
                epics.remove(id);
                historyManager.remove(id);
            } else {
                throw new ManagerDeleteTaskException("Задача с таким ID не найдена");
            }
        } else {
            throw new ManagerDeleteTaskException("Поле ID пустое");
        }
    }
    @Override
    public void deleteSubtaskById(Integer id) {
        Subtask value = subtasks.get(id);
        if (id != null ) {
            if (value != null) {
                Integer epicId = subtasks.get(id).getEpicId();
                subtasks.remove(id);
                getEpicById(epicId).deleteSubtask(id);
                setEpicStatus(epicId);
                setEpicStartAndEndTime(epicId);
                historyManager.remove(id);
            } else {
                throw new ManagerDeleteTaskException("Задача с таким ID не найдена");
            }
        } else {
            throw new ManagerDeleteTaskException("Поле ID пустое");
        }
    }

    public void addTaskWithoutHistory(Integer id, Task task) {
        tasks.put(id, task);
    }

    public void addSubtaskWithoutHistory(Integer id, Subtask subtask) {
        subtasks.put(id, subtask);
    }

    public void addEpicWithoutHistory(Integer id, Epic epic) {
        epics.put(id, epic);
    }

    @Override
    public void createTask(Task task) {
        if ( task.getId() != null ) {
            tasks.put(task.getId(), task);
        } else {
            Integer id = getId();
            task.setId(id);
            tasks.put(id, task);
            addToPrioritizedTasks(task);
        }
    }
    @Override
    public void createSubtask(Subtask subtask) {
        if ( subtask.getId() != null ) {
            subtasks.put(subtask.getId(), subtask);
        } else {
            Integer id = getId();
            subtask.setId(id);
            subtasks.put(id, subtask);
            int epicId = subtasks.get(id).getEpicId();
            epics.get(epicId).setSubtasks(id);
            setEpicStatus(epicId);
            setEpicStartAndEndTime(epicId);
            addToPrioritizedTasks(subtask);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        if ( epic.getId() != null ) {
            epics.put(epic.getId(), epic);
        } else {
            Integer id = getId();
            epic.setId(id);
            epics.put(id, epic);
            setEpicStatus(id);
        }
    }
    @Override
    public void updateTask(Task task) {
        Integer id = task.getId();
        Task savedTask = tasks.get(id);
        if (savedTask == null) {
            return;
        }
        tasks.put(id, task);
    }
    @Override
    public void updateSubtask(Subtask subtask) {
        Task savedTask = subtasks.get(subtask.getId());
        if (savedTask == null) {
            return;
        }
        subtasks.remove(subtask.getId());
        subtasks.put(subtask.getId(), subtask);
        Integer epicId = subtask.getEpicId();
        setEpicStatus(epicId);
        setEpicStartAndEndTime(epicId);
    }
    @Override
    public void updateEpic(Epic epic) {
        Integer id = epic.getId();
        Task savedTask = epics.get(id);
        if (savedTask == null) {
            return;
        }
        epics.put(id, epic);
    }

    private ArrayList<Subtask> getEpicSubtasks(Integer epicId) {

        ArrayList<Subtask> epicSubtasks = new ArrayList<Subtask>();
        List<Integer> subtasksIds = epics.get(epicId).getSubtasks();

        for (int i : subtasksIds) {
            epicSubtasks.add(subtasks.get(i));
        }

        return epicSubtasks;
    }

    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private Integer getId() {
        id++;
        return id;
    }
    private void setEpicStatus(Integer epicId) {

        if (epics.get(epicId).getSubtasks().isEmpty()) {
            epics.get(epicId).setStatus(Status.NEW);
            return;
        }

        int countNew = 0;
        int countInProgress = 0;
        int countDone = 0;

        for (Subtask subtask : getEpicSubtasks(epicId)) {
            if (subtask.getStatus() == Status.NEW) {
                countNew++;
            } else if (subtask.getStatus() == Status.DONE) {
                countDone++;
            } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                countInProgress++;
            }
            if ( getEpicSubtasks(epicId).size() == countNew ) {
                epics.get(epicId).setStatus(Status.NEW);
            } else if ( getEpicSubtasks(epicId).size() == countDone ) {
                epics.get(epicId).setStatus(Status.DONE);
            } else {
                epics.get(epicId).setStatus(Status.IN_PROGRESS);
            }
        }
    }

    private void setEpicStartAndEndTime(Integer epicId) {

        ArrayList<Subtask> subtasks = getEpicSubtasks(epicId);

        if (subtasks.size() == 0) {
            epics.get(epicId).setStartTime(null);
            epics.get(epicId).setEndTime(null);
            epics.get(epicId).setDuration(0);
            return;
        } else {
            Instant startTime = subtasks.get(0).getStartTime();
            Instant endTime = subtasks.get(0).getEndTime();
            for (Subtask subtask : subtasks) {
                if (subtask.getStartTime().isBefore(startTime))
                    startTime = subtask.getStartTime();

                if (subtask.getEndTime().isAfter(endTime))
                    endTime = subtask.getEndTime();
            }

            epics.get(epicId).setStartTime(startTime);
            epics.get(epicId).setEndTime(endTime);
            epics.get(epicId).setDuration(Duration.between(startTime, endTime).toMinutes());
        }
    }
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }
    private void addToPrioritizedTasks(Task task) {
        prioritizedTasks.add(task);
        checkIntersections();
    }
    private void checkIntersections() {
        List<Task> prioritizedTasks = getPrioritizedTasks();
        for (int i = 1; i < prioritizedTasks.size(); i++) {
            Task prioritizedTask = prioritizedTasks.get(i);
            if (prioritizedTask.getStartTime().isBefore(prioritizedTasks.get(i - 1).getEndTime()))
                throw new IntersectionException("Найдено пересечение "
                        + prioritizedTasks.get(i)
                        + " и "
                        + prioritizedTasks.get(i - 1));
        }
    }
    public void printPrioritizedTasks() {
        System.out.println("Приоритетные задачи: ");
        prioritizedTasks.forEach(System.out::println);
    }
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getStartTime().compareTo(o2.getStartTime());
    }
}
