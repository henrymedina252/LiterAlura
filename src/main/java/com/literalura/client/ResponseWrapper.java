package com.literalura.client;

public class ResponseWrapper<T> {
    private T data;
    private String errorMessage;

    public ResponseWrapper(T data) {
        this.data = data;
        this.errorMessage = null;
    }

    public ResponseWrapper(String errorMessage) {
        this.errorMessage = errorMessage;
        this.data = null;
    }

    public T getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean hasError() {
        return errorMessage != null;
    }

    @Override
    public String toString() {
        return hasError() ? "Error: " + errorMessage : "Data: " + (data != null ? data.toString() : "No data");
    }
}

