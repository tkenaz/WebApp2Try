package ua.nure.vyshnyvetska.SummaryTask4.exceptions;

public class AppException extends Exception {
    private static final long serialVersionUID = 1234567890L;
    public AppException() {super();}
    public AppException(String message, Throwable cause) { super(message, cause);}
    public AppException(String message) { super(message);}
}
