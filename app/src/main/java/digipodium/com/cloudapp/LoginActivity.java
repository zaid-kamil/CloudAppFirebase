package digipodium.com.cloudapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import digipodium.com.cloudapp.fragments.SignInFragment;
import digipodium.com.cloudapp.fragments.SignUpFragment;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        ViewPager pager = findViewById(R.id.pager);
        TabLayout tabs = findViewById(R.id.tablayout);
        tabs.setupWithViewPager(pager);
        pager.setAdapter(new AuthAdapter(getSupportFragmentManager()));

    }

    class AuthAdapter extends FragmentStatePagerAdapter {

        public AuthAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SignUpFragment();
                case 1:
                    return new SignInFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Sign Up";
                case 1:
                    return "Sign In";
            }
            return super.getPageTitle(position);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        updateUI(user);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(this, "login to continue", Toast.LENGTH_SHORT).show();
        } else {
            if (user.isEmailVerified()) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "please verify email", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
