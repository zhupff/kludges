package kludge.databus

/**
 * @Author:Zhupf
 * @Description: 数据接收器状态。
 */
internal enum class State {
    /** 刚初始化。 **/
    INIT,
    /** 不活跃状态。 **/
    INACTIVE,
    /** 活跃状态。 **/
    ACTIVE,
    /** 已释放，该状态下不会响应任何操作。 **/
    RELEASE,
}