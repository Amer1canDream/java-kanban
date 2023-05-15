import controllers.Managers;
import controllers.TaskManager;
import model.*;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        System.out.println("----- ТЗ 5---");
        System.out.println("----- Создаем две задчи, эпик с 3 подзадачами и эпик без подзадача");

        manager.createTask(new Task("Задача 1", "Описание задачи 1", Status.NEW));
        manager.createTask(new Task("Задача 2", "Описание задачи 2", Status.NEW));
        manager.createEpic(new Epic("Эпик 1", "Описание эпика 1"));
        manager.createSubtask(new Subtask("Подзадача 1", "Описание подзадачи 1", 3, Status.NEW));
        manager.createSubtask(new Subtask("Подзадача 2", "Описание подзадачи 2", 3, Status.NEW));
        manager.createSubtask(new Subtask("Подзадача 3", "Описание подзадачи 3", 3, Status.NEW));
        manager.createEpic(new Epic("Эпик 2", "Описание эпика 2"));
        System.out.println(manager.getEpics());
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(1);
        manager.getTaskById(1);
        System.out.println(manager.getHistory());
        manager.deleteTaskById(2);
        manager.getEpicById(3);
        manager.getEpicById(7);
        manager.getEpicById(7);
        System.out.println(manager.getHistory());
        manager.getSubtaskById(4);
        manager.getSubtaskById(4);
        manager.getSubtaskById(5);
        manager.getSubtaskById(5);
        manager.getSubtaskById(6);
        manager.getSubtaskById(6);
        System.out.println(manager.getHistory());
        manager.deleteEpicById(3);
        System.out.println(manager.getHistory());
    }
}
