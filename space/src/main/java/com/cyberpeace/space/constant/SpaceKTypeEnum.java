package com.cyberpeace.space.constant;

import lombok.Getter;

/**
 * space ktype 枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Enum
 * @since 1.0.0
 * 知识库类型
 *
 * @author zhangxuepei
 * @since 1.0
 */
public enum SpaceKTypeEnum {


    KNOWLEDGE("知识库管理子系统","knowledge", 0, ""),
    /**
     * 理论
     *
     * @see SpaceKTypeEnum
     */
    THEORY("理论知识库","theory", 1, "practices-folder"),
    /**
     * 课程
     *
     * @see SpaceKTypeEnum
     */
    COURSE("课程","course", 11, "courses"),
    /**
     * 练习理论
     *
     * @see SpaceKTypeEnum
     */
    PRACTICE_THEORY("理论考题库","practice_theory", 12, "theory-base"),
    /**
     * 练习实操
     *
     * @see SpaceKTypeEnum
     */
    PRACTICE_OPERATION("实操考题库","practice_operation", 13, "flags-solve"),
    /**
     * 策略
     *
     * @see SpaceKTypeEnum
     */
    STRATEGY("策略库","strategy", 2, "strategy-library"),
    /**
     * 战法
     *
     * @see SpaceKTypeEnum
     */
    TACTIC("战法库","tactic", 3, "warfare-folder"),
    /**
     * 战法攻击
     *
     * @see SpaceKTypeEnum
     */
    TACTIC_ATTACK("攻击战法库","tactic_attack", 31, "attack-warfare-library"),
    /**
     * 战法防御
     *
     * @see SpaceKTypeEnum
     */
    TACTIC_DEFENSE("防御战法库","tactic_defense", 32, "defense-warfare-library"),
    /**
     * 统治
     *
     * @see SpaceKTypeEnum
     */
    RULE("规则库","rule", 4, "rule-folder"),
    /**
     * 规则库
     *
     * @see SpaceKTypeEnum
     */
    RULE_BASE("基础数据规则","rule_base", 41, "basic-rule"),
    /**
     * 规则漏洞
     *
     * @see SpaceKTypeEnum
     */
    RULE_VULN("漏洞库","rule_vuln", 411, ""),
    /**
     * 规则 apt org
     *
     * @see SpaceKTypeEnum
     */
    RULE_APT_ORG("APT组织库","rule_apt_org", 412, ""),
    /**
     * 规则 威胁防御
     *
     * @see SpaceKTypeEnum
     */
    RULE_THREAT_DEFENSE("威胁防御规则","rule_threat_defense", 42, "threat-rule"),
    /**
     * 规则病毒
     *
     * @see SpaceKTypeEnum
     */
    RULE_VIRUS("病毒威胁防御规则","rule_virus", 421, ""),
    /**
     * 规则主机
     *
     * @see SpaceKTypeEnum
     */
    RULE_HOST("主机攻击防御规则","rule_host", 422, ""),
    /**
     * 规则网络
     *
     * @see SpaceKTypeEnum
     */
    RULE_NETWORK("网络攻击防御规则","rule_network", 423, ""),
    /**
     * 规则 apt detect
     *
     * @see SpaceKTypeEnum
     */
    RULE_APT_DETECT("特定APT威胁检测规则","rule_apt_detect", 424, ""),
    /**
     * 规则竞赛
     *
     * @see SpaceKTypeEnum
     */
    RULE_RACE("比赛及训练评估规则","rule_race", 43, "competition-rule"),
    /**
     * 规则分数
     *
     * @see SpaceKTypeEnum
     */
    RULE_SCORE("计分排行规则","rule_score", 431, ""),
    /**
     * 规则回合
     *
     * @see SpaceKTypeEnum
     */
    RULE_ROUND("轮次机制规则","rule_round", 432, ""),
    /**
     * 违反规则
     *
     * @see SpaceKTypeEnum
     */
    RULE_VIOLATION("违规判定规则","rule_violation", 433, ""),
    /**
     * 算法
     *
     * @see SpaceKTypeEnum
     */
    ALGORITHM("算法库","algorithm", 5, "algorithm-library-folder"),
    /**
     * 算法测评
     *
     * @see SpaceKTypeEnum
     */
    ALGORITHM_EVAL("能力评估算法","algorithm_eval", 51, "assessment-algorithm"),
    /**
     * 流量
     *
     * @see SpaceKTypeEnum
     */
    TRAFFIC("流量库","traffic", 6, ""),
    /**
     * 场景
     *
     * @see SpaceKTypeEnum
     */
    SCENE("场景库","scene", 7, "")
    ;


    /**
     * 名称
     *
     * @see String
     */
    @Getter
	private String name;

    /**
     * id
     *
     * @see String
     */
    @Getter
	private String id;

    /**
     * 类型
     *
     * @see Integer
     */
    @Getter
	private Integer type;

    /**
     * 菜单id
     *
     * @see String
     */
    @Getter
    private String menuId;

    /**
     * space ktype 枚举
     *
     * @param name   名称
     * @param id     id
     * @param type   类型
     * @param menuId 菜单id
     * @throws
     * @see String
     * @see String
     * @see Integer
     * @see String
     */
    SpaceKTypeEnum(String name, String id, Integer type, String menuId){
        this.name = name;
        this.id = id;
        this.type = type;
        this.menuId = menuId;
    }

}
