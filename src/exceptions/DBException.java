package exceptions;

public class DBException extends AppException{
    private static final long serialVersionUID = 1234567890L;
    public DBException() {super();}
    public DBException(String message, Throwable cause) { super(message, cause);}
}
