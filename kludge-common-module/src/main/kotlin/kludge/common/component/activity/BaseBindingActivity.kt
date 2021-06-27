package kludge.common.component.activity

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * Author: Zhupf
 */
abstract class BaseBindingActivity<V : ViewDataBinding> : BaseActivity() {

    protected lateinit var binding: V

    override fun setContentView() {
        binding = DataBindingUtil.setContentView(this, getLayoutID())
    }
}