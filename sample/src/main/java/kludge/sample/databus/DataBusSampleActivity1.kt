package kludge.sample.databus

import android.content.Intent
import android.os.Bundle
import android.util.Log
import kludge.common.component.activity.BaseBindingActivity
import kludge.databus.DataBus
import kludge.databus.LifecycleReceiver
import kludge.databus.Receiver
import kludge.databus.ReceiverRef
import kludge.sample.R
import kludge.sample.databinding.DataBusSampleActivity1Binding

class DataBusSampleActivity1 : BaseBindingActivity<DataBusSampleActivity1Binding>() {

    private var receiverRef1: ReceiverRef<String>? = null
    private val receiver1 = object : Receiver<String>() {
//        override fun sticky(): Boolean = true
        override fun onReceive(data: String?): Boolean {
            "Receiver1:${data.toString()}".let {
                binding.receiver1TextView.text = it
                Log.i(TAG, it)
            }
            return false
        }
    }
    private var lifeReceiverRef1: ReceiverRef<String>? = null
    private val lifeReceiver1 = object : LifecycleReceiver<String>(this) {
        override fun priority(): Int = 2
//        override fun keepActive(): Boolean = true
        override fun onReceive(data: String?): Boolean {
            "LifeReceiver1:${data.toString()}".let {
                binding.lifeReceiver1TextView.text = it
                Log.i(TAG, it)
            }
            return true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()

        binding.toActivity2Button.setOnClickListener {
            startActivity(Intent(this, DataBusSampleActivity2::class.java))
        }

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
        // Init Receiver1.
        binding.receiver1RegisterButton.setOnClickListener {
            receiverRef1 = DataBus.subscribe(String::class.java)
                .register(receiver1)
        }
        binding.receiver1UnregisterButton.setOnClickListener {
            receiverRef1?.unregister()
        }
        binding.receiver1ActiveButton.setOnClickListener {
            receiverRef1?.switchActive()
        }
        binding.receiver1InactiveButton.setOnClickListener {
            receiverRef1?.switchInactive()
        }
        // Init LifeReceiver1.
        binding.lifeReceiver1RegisterButton.setOnClickListener {
            lifeReceiverRef1 = DataBus.subscribe(String::class.java)
                .register(lifeReceiver1)
        }
        binding.lifeReceiver1UnregisterButton.setOnClickListener {
            lifeReceiverRef1?.unregister()
        }
        binding.lifeReceiver1ActiveButton.setOnClickListener {
            lifeReceiverRef1?.switchActive()
        }
        binding.lifeReceiver1InactiveButton.setOnClickListener {
            lifeReceiverRef1?.switchInactive()
        }
    }

    override fun getLayoutID(): Int = R.layout.data_bus_sample_activity_1
}

private val TAG = DataBus::class.java.simpleName