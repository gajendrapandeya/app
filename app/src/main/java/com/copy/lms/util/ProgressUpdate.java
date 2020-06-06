package com.copy.lms.util;


public interface ProgressUpdate {
    void onUpdateProgress(int progress, boolean isRunning, double fileSize, String fileName, double downloadSize);
}
