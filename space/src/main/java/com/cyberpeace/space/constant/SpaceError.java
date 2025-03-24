package com.cyberpeace.space.constant;

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
}
