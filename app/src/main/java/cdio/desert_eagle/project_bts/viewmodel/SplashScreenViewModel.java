package cdio.desert_eagle.project_bts.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import cdio.desert_eagle.project_bts.data.SharedPref;

public class SplashScreenViewModel extends AndroidViewModel {

    private final SharedPref sharedPref;

    public SplashScreenViewModel(@NonNull Application application) {
        super(application);
        sharedPref = new SharedPref(application);
    }

    public String getLoggedInformation() {
        return sharedPref.getStringData("loggedIn");
    }
}
