package com.cyberpeace.cr.crbase.constant;

/**
 * @author SN
 * @date 2023/09/25
 */
public final class SituationRouteStatus {

    /**
     * 未被攻击
     */
    public static final int NOT_ATTACKED = 1;

    /**
     * 正在被攻击
     */
    public static final int UNDER_ATTACK = 2;

    /**
     * 攻击完成
     */
    public static final int CONQUERED = 3;

    private SituationRouteStatus() {}
}
