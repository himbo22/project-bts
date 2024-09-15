package cdio.desert_eagle.project_bts.extension;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class StringEx {

    public static String getRealPathFromUri(Uri uri, Context context) {
        String res;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor;
        try {
            cursor = context.getApplicationContext().getContentResolver()
                    .query(uri, projection, null, null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (cursor == null) {
            return null;
        }
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        res = cursor.getString(columnIndex);
        cursor.close();
        return res;
    }

}
