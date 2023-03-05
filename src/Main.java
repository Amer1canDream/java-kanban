public class Main {

    public static void main(String[] args) {

        int[] subTasks1 = {2, 3};
        int[] subTasks2 = {5};

        Manager manager = new Manager();

        manager.createSubtask(new Subtask("Перевезти вещи", "Нужно перевезти вещи в новую квартиру", 2, "NEW", 1));
        manager.createSubtask(new Subtask("Перевезти кота", "Нужно перевезти кота", 3, "NEW", 3));
        manager.createEpic(new Epic("Переезд", "Нужно переехать в новую квартиру", 1, "IN PROGRESS", subTasks1));

        manager.createSubtask(new Subtask("Перевезти вещи", "Нужно перевезти вещи в новую квартиру", 5, "IN PROGRESS", 4));
        manager.createEpic(new Epic("Обставить квартиру", "Нужно обставить квартиру", 4, "NEW", subTasks2));

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.changeSubtask(new Subtask("Перевезти вещи", "Нужно перевезти вещи в новую квартиру", 5, "DONE", 4));

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.deleteById(5);

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

/**
        manager.changeSubtask(new Subtask("Купить новый тостер", "Нужно купить новый тостер", 4, "DONE", 3));

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());

        manager.changeSubtask(new Subtask("Перевезти вещи", "Нужно перевезти вещи в новую квартиру", 2, "DONE", 3));

        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getTasks());

        manager.deleteById(1);

        System.out.println(manager.getTasks());

        manager.deleteAllTasks();
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getTasks());
 **/
    }
}
