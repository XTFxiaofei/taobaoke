package org.elsa.valord.common.enums;

/**
 * @author valor
 * @date 2018/9/10 11:45
 */
public enum Errors {

    /**
     * 没有获取到淘宝cookie
     */
    No_cookie("错误代码: 1008\n服务暂不可用");

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
