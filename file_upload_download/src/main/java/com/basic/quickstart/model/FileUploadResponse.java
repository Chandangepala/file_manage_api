package com.basic.quickstart.model;

public class FileUploadResponse {
    private String fileName;
    private String message;
    private boolean success;

    public FileUploadResponse(String fileName, String message, boolean success) {
        this.fileName = fileName;
        this.message = message;
        this.success = success;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}

