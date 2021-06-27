package kludge.sample.databus

import android.os.Bundle
import kludge.common.component.activity.BaseBindingActivity
import kludge.sample.R
import kludge.sample.databinding.DataBusSampleActivity2Binding

class DataBusSampleActivity2 : BaseBindingActivity<DataBusSampleActivity2Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
    }

    override fun getLayoutID(): Int = R.layout.data_bus_sample_activity_2
}