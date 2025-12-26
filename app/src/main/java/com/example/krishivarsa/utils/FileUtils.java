package com.example.krishivarsa.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    public static File getFile(Context context, Uri uri) {

        File file = new File(
                context.getCacheDir(),
                System.currentTimeMillis() + ".jpg"
        );

        try {
            InputStream in =
                    context.getContentResolver().openInputStream(uri);
            OutputStream out = new FileOutputStream(file);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
}
