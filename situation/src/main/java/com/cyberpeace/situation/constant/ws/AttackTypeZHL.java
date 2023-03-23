package com.cyberpeace.situation.constant.ws;

public class AttackTypeZHL {
    private final AttackType ZHL_ATTACK1 = AttackType.NETWORK_SCANNING;
    private final AttackType ZHL_ATTACK2 = AttackType.TRY_ATTACK;
    private final AttackType ZHL_ATTACK3 = AttackType.HOST_ATTACK;
    private final AttackType ZHL_ATTACK4 = AttackType.SUCCESS_ATTACK;
    private final AttackType ZHL_ATTACK5 = AttackType.NORMAL;
    private final AttackType ZHL_ATTACK6 = null;  // todo 未定义


    private final AttackType ZHL_DEFENSE1 = AttackType.NETWORK_SCANNING;
    private final AttackType ZHL_DEFENSE2 = AttackType.NETWORK_SCANNING;
    private final AttackType ZHL_DEFENSE3 = AttackType.NETWORK_SCANNING;
    private final AttackType ZHL_DEFENSE4 = AttackType.TROJAN;  // todo 目前是攻击木马特效，需要替换未
    private final AttackType ZHL_DEFENSE5 = AttackType.REPAIR;
    private final AttackType ZHL_DEFENSE6 = AttackType.REPAIR;
}
