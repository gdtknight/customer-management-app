package io.github.gdtknight.smartstore.exceptions;

/**
 * @author YongHo Shin
 * @version v1.0
 * @since 2023-05-10
 */
public class AppException extends RuntimeException {
  private final AppErrorCode appErrorCode;
  private final String message;

  public AppException() {
    this.appErrorCode = AppErrorCode.INTERNAL_ERROR;
    this.message = AppErrorCode.INTERNAL_ERROR.getMessage();
  }

  public AppException(AppErrorCode appErrorCode) {
    this.appErrorCode = appErrorCode;
    this.message = appErrorCode.getMessage();
  }

  public AppException(AppErrorCode appErrorCode, String message) {
    this.appErrorCode = appErrorCode;
    this.message = message;
  }

  public AppException(String message) {
    this.appErrorCode = AppErrorCode.INTERNAL_ERROR;
    this.message = message;
  }

  public AppErrorCode getErrorCode() {
    return appErrorCode;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
