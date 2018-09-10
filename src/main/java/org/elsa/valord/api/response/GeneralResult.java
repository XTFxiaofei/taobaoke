package org.elsa.valord.api.response;

/**
 * @author valor
 * @date 2018/9/10 11:34
 */
public class GeneralResult<T> extends BaseResult {

    private T value;

    public GeneralResult<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public T getValue() {
        return value;
    }
}
