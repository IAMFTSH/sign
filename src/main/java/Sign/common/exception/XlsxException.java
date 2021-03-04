package Sign.common.exception;

/**
 * @author 邝明山
 * on 2020/10/29 22:04
 */
public class XlsxException extends Exception{
    // 存储提示信息的
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public XlsxException(String message) {
        this.message = message;
    }
}
