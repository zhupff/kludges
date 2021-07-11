package kludge.sample;

import android.os.Bundle;

import kludge.common.component.activity.BaseBindingActivity;
import kludge.router.Route;
import kludge.router.Router;
import kludge.sample.databinding.MainActivityBinding;

@Route(route = RouteTableKt.MAIN_ACTIVITY)
public class MainActivity extends BaseBindingActivity<MainActivityBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        Router.init();

        binding.toDataBusSampleActivity1Btn.setOnClickListener(v -> {
            Router.from(this).to(RouteTableKt.DATABUS_SAMPLE_ACTIVITY1).jump();
        });
    }

    @Override
    public int getLayoutID() {
        return R.layout.main_activity;
    }
}