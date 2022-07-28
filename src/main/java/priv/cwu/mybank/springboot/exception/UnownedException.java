package priv.cwu.mybank.springboot.exception;

public class UnownedException extends RuntimeException {
    public UnownedException() {
        super("所有权错误");
    }

    public UnownedException(String msg) {
        super(msg);
    }

    public UnownedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
