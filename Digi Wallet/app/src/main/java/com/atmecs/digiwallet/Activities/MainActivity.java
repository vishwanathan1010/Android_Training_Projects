package com.atmecs.digiwallet.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.atmecs.digiwallet.BuildConfig;
import com.atmecs.digiwallet.Fragments.LoginFragment;
import com.atmecs.digiwallet.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadLoginFragment();
    }

    public void loadLoginFragment() {
        Fragment fragment = null;
        fragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, fragment);
        fragmentTransaction.commit();
    }
}
