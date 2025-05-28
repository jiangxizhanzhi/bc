package com.cyberpeace.space.constant;

/**
 * 空间错误
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 * 错误码
 *
 * @author zhangyong
 * @date 2021/12/07
 */
public class SpaceError {
    /**
     * 无法删除内置框架
     */
    public static final String BUILTIN_FRAME = "105001";
    /**
     * 该框架及其子框架下存在策略，无法删除
     *
     * @see String
     */
    public static final String STRATEGY_EXIST = "105002";

    /**
     * 交战方法[]被行动框架[]引用，无法删除
     *
     * @see String
     */
    public static final String METHOD_REFERENCE_EXIST = "105003";

    /**
     * 交战活动[]被行动框架[]引用，无法删除
     *
     * @see String
     */
    public static final String ACTION_REFERENCE_EXIST = "105004";

    /**
     * 占比输入之和不能大于100%
     *
     * @see String
     */
    public static final String PERCENTAGE_LARGER_THAN_100 = "105005";
    /**
     * 一个框架在一次红蓝对抗业务中只能运用一次
     *
     * @see String
     */
    public static final String RVB_FRAME_USED = "105006";
    /**
     * 阶段已下发
     *
     * @see String
     */
    public static final String STAGE_RELEASED = "105007";
    /**
     * 技法已下发
     *
     * @see String
     */
    public static final String TACTIC_RELEASED = "105008";
    /**
     * 单个步骤下战法不能重复
     *
     * @see String
     */
    public static final String TACTIC_DUPLICATED = "105009";
}
