import controllers.InMemoryTaskManager;
import model.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        ArrayList<Integer> subtasksEpic1 = new ArrayList<>();
        subtasksEpic1.add(1);
        subtasksEpic1.add(2);

        ArrayList<Integer> subtasksEpic4 = new ArrayList<>();
        subtasksEpic4.add(4);
        subtasksEpic4.add(5);

        manager.createEpic(new Epic("Переезд", "Нужно переехать в новую квартиру", subtasksEpic1));
        manager.createSubtask(new Subtask("Перевезти вещи", "Нужно перевезти вещи в новую квартиру", 1, Statuses.NEW));
        manager.createSubtask(new Subtask("Перевезти кота", "Нужно перевезти кота", 1, Statuses.NEW));

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.createEpic(new Epic("Обставить квартиру", "Нужно обставить квартиру", subtasksEpic4));
        manager.createSubtask(new Subtask("Купить шкаф", "купить шкаф", 4, Statuses.NEW));
        manager.createSubtask(new Subtask("купить люстру", "купить люстру", 4, Statuses.NEW));

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println("---------------");

        manager.setSubtaskStatus(5, Statuses.DONE);
        manager.setSubtaskStatus(6, Statuses.DONE);

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());


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

    }
}
