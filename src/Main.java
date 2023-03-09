import controllers.Manager;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        ArrayList<Integer> subtasksEpic3 = new ArrayList<>();
        subtasksEpic3.add(1);
        subtasksEpic3.add(2);

        ArrayList<Integer> subtasksEpic6 = new ArrayList<>();
        subtasksEpic6.add(4);
        subtasksEpic6.add(5);

        manager.createSubtask("Перевезти вещи", "Нужно перевезти вещи в новую квартиру", 3, "NEW");
        manager.createSubtask("Перевезти кота", "Нужно перевезти кота", 3, "NEW");
        manager.createEpic("Переезд", "Нужно переехать в новую квартиру", subtasksEpic3);

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.createSubtask("Купить шкаф", "купить шкаф", 6, "NEW");
        manager.createSubtask("Купить люстру", "купить люстру", 6, "NEW");
        manager.createEpic("Обставить квартиру", "Нужно обставить квартиру", subtasksEpic6);

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println("---------------");

        manager.setSubtaskStatus(4, "DONE");
        manager.setSubtaskStatus(5, "DONE");

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println("---------------");

        manager.deleteSubtask(4);
        manager.deleteSubtask(5);


        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

    }
}
