import controllers.Managers;
import controllers.TaskManager;
import model.*;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        /**
         manager.createEpic(new Epic("Переезд", "Нужно переехать в новую квартиру", new ArrayList<>()));
         manager.createSubtask(new Subtask("Перевезти вещи", "Нужно перевезти вещи в новую квартиру", 1, Status.NEW));
         manager.createSubtask(new Subtask("Перевезти кота", "Нужно перевезти кота", 1, Status.NEW));

         System.out.println("----- Эпики");
         System.out.println(manager.getEpics());
         System.out.println("----- Сабтаски");
         System.out.println(manager.getSubtasks());

         System.out.println(manager.getSubtasks());
         manager.createEpic(new Epic("Обставить квартиру", "Нужно обставить квартиру", new ArrayList<>()));
         manager.createSubtask(new Subtask("Купить шкаф", "купить шкаф", 4, Status.NEW));
         manager.createSubtask(new Subtask("купить люстру", "купить люстру", 4, Status.NEW));

         System.out.println("----- Эпики, добавили 4 эпик");
         System.out.println(manager.getEpics());
         System.out.println("----- Сабтаски, прилинковали к 4 эпику");
         System.out.println(manager.getSubtasks());
         System.out.println("---------------");

         manager.updateSubtask(5, new Subtask("купить люстру", "купить люстру", 4, Status.DONE));
         manager.updateSubtask(6, new Subtask("купить люстру", "купить люстру", 4, Status.DONE));

         System.out.println("----- Эпики, у 4 все сабтаски в статусе DONE, эпик тоже должен быть в этом статусе");
         System.out.println(manager.getEpics());
         System.out.println("----- Сабтаски");
         System.out.println(manager.getSubtasks());

         manager.deleteSubtaskById(5);
         manager.deleteSubtaskById(6);
         System.out.println("----- Эпики, удалил 5 и 6 сабтаски у 4 эпика должен стать статус NEW");
         System.out.println(manager.getEpics());

         System.out.println("----- Эпик, добавили сабтаски");
         manager.createSubtask(new Subtask("Купить шкаф", "купить шкаф", 4, Status.NEW));
         manager.createSubtask(new Subtask("купить люстру", "купить люстру", 4, Status.NEW));
         System.out.println(manager.getEpics());
         System.out.println(manager.getSubtasks());
         System.out.println("----- Удалили эпик");
         manager.deleteEpicById(4);
         System.out.println(manager.getEpics());
         System.out.println(manager.getSubtasks());

         System.out.println("----- Эпик и пустые");
         manager.createEpic(new Epic("Обставить квартиру", "Нужно обставить квартиру", new ArrayList<>()));
         manager.createEpic(new Epic("Обставить квартиру", "Нужно обставить квартиру", new ArrayList<>()));
         System.out.println(manager.getEpics());
         System.out.println(manager.getSubtasks());

         System.out.println("----- Поменял стаус у сабтаски 9 эпика");
         manager.createSubtask(new Subtask("Купить шкаф", "купить шкаф", 9, Status.IN_PROGRESS));
         System.out.println(manager.getEpics());
         System.out.println(manager.getSubtasks());

         System.out.println("----- Создаем эпик и меняем статусы у его сабтасков");
         manager.createEpic(new Epic("Обставить квартиру", "Нужно обставить квартиру", new ArrayList<>()));
         manager.createSubtask(new Subtask("Купить шкаф", "купить шкаф", 12, Status.NEW));
         manager.createSubtask(new Subtask("купить люстру", "купить люстру", 12, Status.NEW));
         manager.createSubtask(new Subtask("Купить шкаф", "купить шкаф", 12, Status.NEW));
         manager.createSubtask(new Subtask("купить люстру", "купить люстру", 12, Status.NEW));
         System.out.println(manager.getEpicById(12));
         manager.updateSubtask(13, new Subtask("купить люстру", "купить люстру", 12, Status.DONE));
         manager.updateSubtask(14, new Subtask("купить люстру", "купить люстру", 12, Status.DONE));
         manager.updateSubtask(15, new Subtask("купить люстру", "купить люстру", 12, Status.DONE));
         manager.updateSubtask(16, new Subtask("купить люстру", "купить люстру", 12, Status.DONE));
         System.out.println(manager.getEpicById(12));
         manager.updateSubtask(15, new Subtask("купить люстру", "купить люстру", 12, Status.NEW));
         manager.updateSubtask(16, new Subtask("купить люстру", "купить люстру", 12, Status.NEW));
         System.out.println(manager.getEpicById(12));
         manager.deleteSubtaskById(13);
         manager.deleteSubtaskById(14);
         manager.deleteSubtaskById(15);
         manager.deleteSubtaskById(16);
         System.out.println(manager.getEpicById(12));

         System.out.println("---- История 1");
         manager.getSubtaskById(3);
         manager.getSubtaskById(3);
         manager.getEpicById(4);
         manager.getEpicById(4);
         manager.getSubtaskById(5);
         manager.getSubtaskById(6);
         manager.getSubtaskById(6);
         manager.getSubtaskById(3);
         manager.getSubtaskById(3);
         System.out.println(manager.getHistory());
         manager.getEpicById(4);
         manager.getSubtaskById(2);
         manager.getSubtaskById(2);
         System.out.println("---- История 2");
         System.out.println(manager.getHistory());
         System.out.println(manager.getHistory().size());

         manager.deleteAllTasks();
         **/
        System.out.println("Удаляем все задачи эпики и подзадачи");
        System.out.println("----- ТЗ 5---");
        System.out.println("----- Создаем две задчи, эпик с 3 подзадачами и эпик без подзадача");

        manager.createTask(new Task("Задача 1", "Описание задачи 1", Status.NEW));
        manager.createTask(new Task("Задача 2", "Описание задачи 2", Status.NEW));
        manager.createEpic(new Epic("Эпик 1", "Описание эпика 1", new ArrayList<>()));
        manager.createSubtask(new Subtask("Подзадача 1", "Описание подзадачи 1", 3, Status.NEW));
        manager.createSubtask(new Subtask("Подзадача 2", "Описание подзадачи 2", 3, Status.NEW));
        manager.createSubtask(new Subtask("Подзадача 3", "Описание подзадачи 3", 3, Status.NEW));
        manager.createEpic(new Epic("Эпик 2", "Описание эпика 2", new ArrayList<>()));
        System.out.println(manager.getEpics());
        System.out.println(manager.getTasks());
        System.out.println(manager.getSubtasks());
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(1);
        manager.getTaskById(1);
        System.out.println(manager.getHistory());
        manager.getEpicById(3);
        manager.getEpicById(7);
        manager.getEpicById(7);
        System.out.println(manager.getHistory());
        manager.getSubtaskById(4);
        manager.getSubtaskById(4);
        manager.getSubtaskById(5);
        manager.getSubtaskById(5);
        System.out.println(manager.getHistory());
        manager.getSubtaskById(6);
        manager.getSubtaskById(6);
        System.out.println(manager.getHistory());
    }
}
