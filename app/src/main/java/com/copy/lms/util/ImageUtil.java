package com.copy.lms.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUtil {
    Context activity;

    public ImageUtil(Context activity) {
        this.activity = activity;
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static void deleteFileFromMediaStore(
            final ContentResolver contentResolver, final File file) {
        String canonicalPath;
        try {
            canonicalPath = file.getCanonicalPath();
        } catch (IOException e) {
            canonicalPath = file.getAbsolutePath();
        }
        final Uri uri = MediaStore.Files.getContentUri("external");
        final int result = contentResolver.delete(uri,
                MediaStore.Files.FileColumns.DATA + "=?",
                new String[]{canonicalPath});
        if (result == 0) {
            final String absolutePath = file.getAbsolutePath();
            if (!absolutePath.equals(canonicalPath)) {
                contentResolver.delete(uri, MediaStore.Files.FileColumns.DATA
                        + "=?", new String[]{absolutePath});
            }
        }
    }

    public void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public void galleryAddPic(Uri currentFileUri) {
        Intent mediaScanIntent = new Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentFileUri.getPath());
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        activity.sendBroadcast(mediaScanIntent);
    }

    public static String Download(String Url, String filename, File dir) {
        String filepath = null;
        try {
            // set the download URL, a url that points to a file on the internet
            // this is the file to be downloaded
            URL url = new URL(Url);
            // create the new connection
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();

            // set up some things on the connection
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            // and connect!
            urlConnection.connect();
            File file = new File(dir, filename);

            // this will be used to write the downloaded data into the file we
            // created
            FileOutputStream fileOutput = new FileOutputStream(file);

            // this will be used in reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            // this is the total size of the file
            int totalSize = urlConnection.getContentLength();
            // variable to store total downloaded bytes
            int downloadedSize = 0;

            // create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0; // used to store a temporary size of the
            // buffer

            // now, read through the input buffer and write the contents to the
            // file
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                // add the data in the buffer to the file in the file output
                // stream (the file on the sd card
                fileOutput.write(buffer, 0, bufferLength);
                // add up the size so we know how much is downloaded
                downloadedSize += bufferLength;
                // this is where you would do something to report the prgress,
                // like this maybe

            }
            // close the output stream when done
            fileOutput.close();
            if (downloadedSize == totalSize)
                filepath = file.getPath();

            // catch some possible errors...
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            filepath = null;
            e.printStackTrace();
        }
        return filepath;

    }

//    public boolean storeImage(Bitmap imageData, String filename) {
// //      get path to external storage (SD card)
//        try {
//            String filePath = getImagePath() + "/" + filename;
//            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//
//            BufferedOutputStream bos = new BufferedOutputStream(
//                    fileOutputStream);
//
//            // choose another format if PNG doesn't suit you
//            imageData.compress(CompressFormat.JPEG, 100, bos);
//            if (null != filePath)
//                galleryAddPic(new CropImage().getImageUri(filePath));
//            bos.flush();
//            bos.close();
//
//        } catch (FileNotFoundException e) {
//            Log.w("TAG", "Error saving image file: " + e.getMessage());
//            return false;
//        } catch (IOException e) {
//            Log.w("TAG", "Error saving image file: " + e.getMessage());
//            return false;
//        }
//
//        return true;
//    }


    public static boolean saveImage(String filePath, File destination)
            throws IOException {

        if (destination.exists()) destination.delete();
        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            in = new FileInputStream(filePath);
            out = new FileOutputStream(destination.getPath());

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;
            return true;
        } catch (FileNotFoundException fnfe1) {
//            Log.e("tag", fnfe1.getMessage());
            Log.e("tag %s !", fnfe1.getMessage());
            return false;
        } catch (Exception e) {
//            Log.e("tag", e.getMessage());
            Log.e("tag %s !", e.getMessage());
            return false;
        }
    }


    public static File saveBitmap(Bitmap bmp, String filename) {
        OutputStream outStream = null;
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
            file = new File(filename);
        }
        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }




}
