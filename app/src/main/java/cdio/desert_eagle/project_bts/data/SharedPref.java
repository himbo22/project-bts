package cdio.desert_eagle.project_bts.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    final Context context;
    final SharedPreferences sharedPref;
    final SharedPreferences.Editor editor;

    public SharedPref(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences("bts", 0);
        editor = sharedPref.edit();
    }

    public String getStringData(String key) {
        return sharedPref.getString(key, null);
    }

    public Long getLongData(String key) {
        return sharedPref.getLong(key, 0L);
    }

    public void setStringData(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public void setLongData(String key, Long value) {
        editor.putLong(key, value);
        editor.apply();
    }
}
