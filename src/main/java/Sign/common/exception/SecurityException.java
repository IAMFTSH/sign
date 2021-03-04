package Sign.common.exception;

/**
 * @author 邝明山
 * on 2020/10/30 14:00
 */
public class SecurityException {
    // 存储提示信息的
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SecurityException(String message) {
        this.message = message;
    }
}
