package cdio.desert_eagle.project_bts.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    final Context context;
    final SharedPreferences sharedPref;
    final SharedPreferences.Editor editor;

    SharedPref(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences("bts", 0);
        editor = sharedPref.edit();
    }

    public String getData(String key) {
        return sharedPref.getString(key, null);
    }

    public void setData(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }
}
