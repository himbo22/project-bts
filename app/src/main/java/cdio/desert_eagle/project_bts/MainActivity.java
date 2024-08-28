package cdio.desert_eagle.project_bts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

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

        // attach tab layout
        new TabLayoutMediator(tlMain, vpMain, ((tab, pos) -> tab.setIcon(tabIcons[pos]))).attach();

    }

}