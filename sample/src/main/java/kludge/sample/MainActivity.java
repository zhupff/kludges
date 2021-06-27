package kludge.sample;

import android.os.Bundle;

import kludge.common.component.activity.BaseBindingActivity;
import kludge.sample.databinding.MainActivityBinding;

public class MainActivity extends BaseBindingActivity<MainActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
    }

    @Override
    public int getLayoutID() {
        return R.layout.main_activity;
    }
}