package sign.common.result;

/**
 * @Author 邝明山
 * @Date 2020/10/25 15:33
 * @Description: 响应码枚举
 * @Version 1.0
 */

public enum ResultCode {
    SUCCESS(200, "操作成功"),
    CREATED(201,"用户新建或修改数据成功"),
    ACCEPTED (202,"请求已经进入后台排队"),
    NO_CONTENT (204,"用户删除数据成功"),
    FAIL(400 ,"用户发出的请求有错误"),
    UNAUTHORIZED(401,"用户未登录，请先登录"),
    FORBIDDEN(403,"访问被禁止"),
    NOT_ACCEPTABLE(406 ,"用户请求的格式不可得"),
    SYSTEM_INNER_ERROR(500, "系统繁忙，请稍后重试"),
    MOVED_PERM(301,"信息已过期");


    int code;

    String message;

    ResultCode(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
