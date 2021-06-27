package kludge.common.component.activity

import androidx.fragment.app.FragmentActivity
import kludge.common.interfaces.ILayoutID

/**
 * Author: Zhupf
 */
abstract class BaseActivity : FragmentActivity(), ILayoutID {

    /**
     * 设置布局。
     */
    protected open fun setContentView() {
        setContentView(getLayoutID())
    }
}