package com.copy.lms.util;

public class IlleagalUsageException extends RuntimeException {
  public IlleagalUsageException() {
  }

  public IlleagalUsageException(String detailMessage) {
    super(detailMessage);
  }

  public IlleagalUsageException(String detailMessage, Throwable throwable) {
    super(detailMessage, throwable);
  }

  public IlleagalUsageException(Throwable throwable) {
    super(throwable);
  }
}
