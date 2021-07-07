package kludge.sample;

import android.content.Intent;
import android.os.Bundle;

import kludge.common.component.activity.BaseBindingActivity;
import kludge.router.Route;
import kludge.router.Router;
import kludge.sample.databinding.MainActivityBinding;
import kludge.sample.databus.DataBusSampleActivity1;

@Route(route = "MainActivity")
public class MainActivity extends BaseBindingActivity<MainActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        Router.init();
        startActivity(new Intent(this, DataBusSampleActivity1.class));
    }

    @Override
    public int getLayoutID() {
        return R.layout.main_activity;
    }
}