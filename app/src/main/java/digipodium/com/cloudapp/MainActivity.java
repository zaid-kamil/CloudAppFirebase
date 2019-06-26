package digipodium.com.cloudapp;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;
import android.widget.TextView;

import digipodium.com.cloudapp.fragments.AddMovieFragment;
import digipodium.com.cloudapp.fragments.LogoutFragment;
import digipodium.com.cloudapp.fragments.MoviesFragment;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    pager.setCurrentItem(0);
                    return true;
                case R.id.navigation_dashboard:
                    pager.setCurrentItem(1);
                    return true;
                case R.id.navigation_notifications:
                    pager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };
    private ViewPager pager;
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        pager = findViewById(R.id.pager);
        adapter = new MovieAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    class MovieAdapter extends FragmentStatePagerAdapter{

        public MovieAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:return new MoviesFragment();
                case 1:return new AddMovieFragment();
                case 2:return new LogoutFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
