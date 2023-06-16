package controllers.exceptions;

public class ManagerGetTaskException extends NullPointerException{
    public ManagerGetTaskException(String message) {
        super(message);
    }
}
