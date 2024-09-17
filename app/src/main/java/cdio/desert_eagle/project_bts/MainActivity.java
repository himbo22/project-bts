package cdio.desert_eagle.project_bts;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Timer;
import java.util.TimerTask;

import cdio.desert_eagle.project_bts.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPager2 vpMain;
    TabLayout tlMain;
    private static final int[] tabIcons = {
            R.drawable.home,
            R.drawable.search,
            R.drawable.add,
            R.drawable.message,
            R.drawable.profile
    };
    private int onBackClickAvailable = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init components
        tlMain = findViewById(R.id.tlMain);
        vpMain = findViewById(R.id.vpMain);
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(this);

        // set adapter
        vpMain.setAdapter(vpAdapter);

        // disable swipe
        vpMain.setUserInputEnabled(false);
        vpMain.setOffscreenPageLimit(4);

        // attach tab layout
        new TabLayoutMediator(tlMain, vpMain, ((tab, pos) -> tab.setIcon(tabIcons[pos]))).attach();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                onBackClickAvailable--;
                if (onBackClickAvailable == 1) {
                    vpMain.setCurrentItem(0, true);
                    Toast.makeText(MainActivity.this, "Tap again to exit", Toast.LENGTH_SHORT).show();
                } else {
                    handleOnBackPressed();
                }
                startTimerToResetBackClickAvailable();
            }
        });
    }

    private void startTimerToResetBackClickAvailable() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onBackClickAvailable = 2;
            }
        }, 30000L);
    }

}