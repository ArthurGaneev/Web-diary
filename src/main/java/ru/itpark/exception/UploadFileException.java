package ru.itpark.exception;

import java.io.IOException;

public class UploadFileException extends RuntimeException {
    public UploadFileException(IOException e) {
    }
}