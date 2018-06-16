package com.data.diff.validators;

public interface Validator<T, R> {

    /**
     * Performs custom validations on target object with type {@code T} and returns result of type {@code R}
     * @param target
     * @return
     */
    R validate(T target);
}
