import controllers.Manager;

public class Main {

    public static void main(String[] args) {

        Manager manager = new Manager();

        manager.createSubtask("Перевезти вещи", "Нужно перевезти вещи в новую квартиру");
        manager.createSubtask("Перевезти кота", "Нужно перевезти кота");
        manager.createEpic("Переезд", "Нужно переехать в новую квартиру");
        manager.linkSubtask(3, 1);
        manager.linkSubtask(3, 2);

        manager.createSubtask("Купить шкаф", "купить шкаф");
        manager.createSubtask("Купить люстру", "купить люстру");
        manager.createEpic("Обставить квартиру", "Нужно обставить квартиру");
        manager.linkSubtask(6, 4);
        manager.linkSubtask(6, 5);

        manager.setSubtaskStatus(4, "DONE");
        manager.setSubtaskStatus(5, "DONE");


        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.deleteById(5);

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

    }
}
