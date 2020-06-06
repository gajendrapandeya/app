package com.copy.lms.net;

import android.content.Context;
import android.os.AsyncTask;

import com.copy.lms.Config;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class DownloadAttachmentTask extends AsyncTask<String, String, String> {
    private String fileName, serverPath;
    private int position;
    private Context context;
    private int responseCode = 0;
    private boolean flag = false;
    private UpdateProgress updateProgress;
    private int attachmentType;
    private String localPath = null;

    public interface UpdateProgress {
        void onProgressUpdate(int position, int progress, boolean isError, String localPath);
    }

    public DownloadAttachmentTask(Context context, int position, int attachmentType,
                                  String serverPath, UpdateProgress updateProgress) {
        this.context = context;
        this.position = position;
        this.serverPath = serverPath;
        this.updateProgress = updateProgress;
        this.attachmentType = attachmentType;
    }

    @Override
    protected String doInBackground(String... aurl) {
        int count;


        try {
            URL url = new URL(serverPath);
            URLConnection connection = url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream());
            String path = Config.LmsFolder.toString();



            fileName = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
            localPath = path + "/" + fileName;
            OutputStream output = new FileOutputStream(localPath);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / fileLength));
                output.write(data, 0, count);
            }
            flag = true;
            output.flush();
            output.close();
            input.close();
            responseCode = ((HttpURLConnection) connection).getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
            responseCode = 0;
        }
        return null;

    }

    protected void onProgressUpdate(String... progress) {
        if (null != updateProgress)
            updateProgress.onProgressUpdate(position, Integer.parseInt(progress[0]), false, null);
    }

    @Override
    protected void onPostExecute(String unused) {
        if (responseCode == 200) {
            if (flag) {
                if (null != updateProgress)
                    updateProgress.onProgressUpdate(position, 100, false, localPath);
            } else {
                if (null != updateProgress)
                    updateProgress.onProgressUpdate(position, 0, false, null);

            }
        } else {
            if (null != updateProgress)
                updateProgress.onProgressUpdate(position, 0, true, null);

        }
    }

}
