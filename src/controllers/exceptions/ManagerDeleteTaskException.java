package controllers.exceptions;

public class ManagerDeleteTaskException extends NullPointerException{
    public ManagerDeleteTaskException(String message) {
        super(message);
    }
}
