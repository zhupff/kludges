package kludge.sample;

import android.os.Bundle;

import kludge.common.component.activity.BaseBindingActivity;
import kludge.router.Route;
import kludge.router.Router;
import kludge.sample.databinding.MainActivityBinding;

@Route(route = "MainActivity")
public class MainActivity extends BaseBindingActivity<MainActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        Router.init();
    }

    @Override
    public int getLayoutID() {
        return R.layout.main_activity;
    }
}