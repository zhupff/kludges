package kludge.sample.databus

import android.os.Bundle
import kludge.common.component.activity.BaseBindingActivity
import kludge.databus.DataBus
import kludge.sample.R
import kludge.sample.databinding.DataBusSampleActivity2Binding

class DataBusSampleActivity2 : BaseBindingActivity<DataBusSampleActivity2Binding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()

        // Init input layout.
        binding.postSyncButton.setOnClickListener {
            val tag = binding.tagEditText.text.toString()
            val msg = binding.msgEditText.text.toString()
            DataBus.subscribe(String::class.java, tag).postSync(msg)
        }
        binding.postAsyncButton.setOnClickListener {
            val tag = binding.tagEditText.text.toString()
            val msg = binding.msgEditText.text.toString()
            DataBus.subscribe(String::class.java, tag).postAsync(msg)
        }
    }

    override fun getLayoutID(): Int = R.layout.data_bus_sample_activity_2
}