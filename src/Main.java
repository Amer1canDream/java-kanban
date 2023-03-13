import controllers.Manager;
import model.Epic;
import model.Subtask;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        ArrayList<Integer> subtasksEpic1 = new ArrayList<>();
        subtasksEpic1.add(1);
        subtasksEpic1.add(2);

        ArrayList<Integer> subtasksEpic4 = new ArrayList<>();
        subtasksEpic4.add(4);
        subtasksEpic4.add(5);

        manager.createEpic(new Epic("Переезд", "Нужно переехать в новую квартиру", subtasksEpic1));
        manager.createSubtask(new Subtask("Перевезти вещи", "Нужно перевезти вещи в новую квартиру", 1, "NEW"));
        manager.createSubtask(new Subtask("Перевезти кота", "Нужно перевезти кота", 1, "NEW"));

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.createEpic(new Epic("Обставить квартиру", "Нужно обставить квартиру", subtasksEpic4));
        manager.createSubtask(new Subtask("Купить шкаф", "купить шкаф", 4, "NEW"));
        manager.createSubtask(new Subtask("купить люстру", "купить люстру", 4, "NEW"));

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println("---------------");

        manager.setSubtaskStatus(5, "DONE");
        manager.setSubtaskStatus(6, "DONE");

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println("---------------");

        manager.deleteSubtask(5);
        manager.deleteSubtask(6);


        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

    }
}
