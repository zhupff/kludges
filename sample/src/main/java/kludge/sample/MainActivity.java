package kludge.sample;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import kludge.sample.R;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }
}