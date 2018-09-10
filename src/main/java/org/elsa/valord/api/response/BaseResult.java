package org.elsa.valord.api.response;

/**
 * @author valor
 * @date 2018/9/10 11:30
 */
public class BaseResult {

    public static final BaseResult SUCCESS;
    public static final BaseResult FAIL;

    private boolean success;
    private String message;

    static {
        SUCCESS = new BaseResult();
        FAIL = new BaseResult();
        FAIL.setSuccess(false);
    }

    public BaseResult() {
        this.setSuccess(true);
        this.message = "";
    }

    public BaseResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public BaseResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
