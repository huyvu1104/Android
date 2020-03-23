package com.example.module1_drawingnotes;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class ImageUtils {
    private static final String TAG = "ImageUtils";
    private static String folderName = "DrawingNotes";

    public static void saveImage(Bitmap bitmap, Context context) {
        String root = Environment.getExternalStorageDirectory().toString();
        File folder = new File(root, folderName);
        folder.mkdir();
        String imageName = Calendar.getInstance().getTimeInMillis()+".png";
        File imageFile = new File(folder, imageName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.close();
            MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, null);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<File> getListImage(){
        List<File> image=new ArrayList<>();
        File folder=new File(Environment.getExternalStorageDirectory().toString(),folderName);
        if(folder.listFiles() != null){
            image= Arrays.asList(folder.listFiles());
        }
        return image;
    }
    public  static void DeleteImage(int index){
        getListImage().get(index).delete();
    }
}
