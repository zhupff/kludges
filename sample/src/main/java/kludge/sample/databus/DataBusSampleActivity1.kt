package kludge.sample.databus

import android.os.Bundle
import kludge.common.component.activity.BaseBindingActivity
import kludge.sample.R
import kludge.sample.databinding.DataBusSampleActivity1Binding

class DataBusSampleActivity1 : BaseBindingActivity<DataBusSampleActivity1Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
    }

    override fun getLayoutID(): Int = R.layout.data_bus_sample_activity_1
}