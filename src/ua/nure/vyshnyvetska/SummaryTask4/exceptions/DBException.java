package ua.nure.vyshnyvetska.SummaryTask4.exceptions;

public class DBException extends AppException{
    private static final long serialVersionUID = 1234567890L;
    public DBException() {super();}
    public DBException(String message, Throwable cause) { super(message, cause);}
}
