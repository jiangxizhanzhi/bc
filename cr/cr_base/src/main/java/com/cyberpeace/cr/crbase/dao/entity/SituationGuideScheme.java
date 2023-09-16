package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;

/**
 * 态势导调方案表
 *
 * @author YaoyaoMei
 */
@Data
@TableName("situation_guide_scheme")
public class SituationGuideScheme extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String roomId;

    private Integer businessType;

    private String eventName;

    /**
     * 1:任务导调 2:手动导调
     */
    private Integer type;

    private Integer eventType;

    private Integer category;

    private String source;

    private String target;

    private Integer result;

    private Integer role;

    private String missionId;

    private Integer triggerCondition;

    public enum Type implements GenericEnum {

        MISSION(1, "任务导调"), MANUALLY(2, "手动导调"), FLOW(3, "流量采集事件"), HOST(4, "主机采集事件");

        @Getter
        private Integer code;
        @Getter
        private String desc;

        Type(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum EventType implements GenericEnum {

        ATTACK(1, "攻击"), DEFENSE(2, "防御"), BLOCKING(3, "阻断"), BELONGING(4, "归属");

        @Getter
        private Integer code;
        @Getter
        private String desc;

        public static EventType getEventTypeByCode(Integer code) {
            return Arrays.stream(EventType.values())
                    .filter(item -> item.getCode().equals(code))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("The code of EventType isn't available"));
        }

        EventType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum ResultType implements GenericEnum {

        SUCCESS(1, "成功"), FAIL(2, "失败"), UNKNOWN(3, "未知");

        @Getter
        private Integer code;
        @Getter
        private String desc;

        ResultType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum RoleType implements GenericEnum {

        RED(4, "归属红方"), BLUE(5, "归属蓝方");

        @Getter
        private Integer code;
        @Getter
        private String desc;

        RoleType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 触发条件枚举
     */
    public enum conditionType implements GenericEnum {

        MISSION_SUBMIT(1, "任务提交"), MISSION_SUCCESS(2, "任务判断成功");

        @Getter
        private Integer code;
        @Getter
        private String desc;

        conditionType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 攻击类型url枚举
     */
    public enum AttackUrlEnum {
        RECONNAISSANCE(1, "侦察", "对手正试图收集可用于规划未来行动的信息。", "", ""),
        RESOURCE_DEVELOPMENT(2, "资源开发", "攻击者正在尝试建立可用于支持操作的资源。", "", ""),
        INITIAL_VISIT(3, "初始访问", "攻击者正试图进入您的网络。", "", ""),
        IMPLEMENT(4, "执行", "攻击者正在尝试运行恶意代码。", "", ""),
        PERSISTENCE(5, "持久化", "对手正试图保持他们的立足点。", "", ""),
        PRIVACY_IMPROVEMENT(6, "特权提升", "攻击者正在尝试获取更高级别的权限。", "", ""),
        ESCAPE_DEFENSE(7, "逃避防御", "对手试图避免被检测到。", "", ""),
        ACCORDING_INTERVIEW(8, "凭据访问", "攻击者试图窃取帐户名和密码。", "", ""),
        ATTACK_DISCOVER(9, "发现", "对手正在尝试找出您的环境。", "", ""),
        MOVE_HORIZONTALLY(10, "横向移动", "攻击者正在尝试在您的环境中移动。", "", ""),
        COLLECT_MESSAGE(11, "信息收集", "对手正试图收集与其目标相关的数据。", "", ""),
        COMMAND_CONTROL(12, "命令与控制", "攻击者正试图与受感染的系统进行通信以控制它们。", "", ""),
        INFORMATION_THEFT(13, "信息盗取", "攻击者试图窃取数据。", "", ""),
        INFLUENCE(14, "影响", "攻击者试图操纵、中断或破坏您的系统和数据。", "", "");

        @Getter
        private final Integer code;

        @Getter
        private final String name;

        @Getter
        private final String desc;

        @Getter
        private final String successUrl;

        @Getter
        private final String failUrl;

        AttackUrlEnum(Integer code, String name, String desc, String successUrl, String failUrl) {
            this.code = code;
            this.name = name;
            this.desc = desc;
            this.successUrl = successUrl;
            this.failUrl = failUrl;
        }

        public static AttackUrlEnum getEnumByCode(Integer code) {
            return Arrays.stream(AttackUrlEnum.values())
                    .filter(item -> item.getCode().equals(code))
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * 防御类型url枚举
     */
    public enum DefenseUrlEnum {
        PLANNING(15, "规划", "确定操作并将其与所需的最终状态对齐。", "", ""),
        COLLECT(16, "收集", "收集对手工具，观察战术，并收集有关对手活动的其他原始情报。", "", ""),
        DEFENSE_DISCOVER(17, "发现", "建立或保持对对手活动的意识。", "", ""),
        DEFENSE(18, "防御", "停止对手按预期执行其操作的全部或部分能力。", "", ""),
        MOVEMENT(19, "转移", "鼓励或阻止对手按预期进行操作。", "", ""),
        DESTROY(20, "破坏", "损害对手按预期执行操作的能力。", "", ""),
        ENSURE(21, "保证", "为欺骗性组件添加真实性，以使对手相信环境是真实的。", "", ""),
        EXCITATION(22, "激励", "鼓励对手执行部分或全部任务。", "", ""),
        ANALYZE(23, "分析", "回顾性审查从操作中获得的信息。", "", "");


        @Getter
        private final Integer code;
        @Getter
        private final String name;
        @Getter
        private final String desc;
        @Getter
        private final String successUrl;
        @Getter
        private final String failUrl;

        DefenseUrlEnum(Integer code, String name, String desc, String successUrl, String failUrl) {
            this.code = code;
            this.name = name;
            this.desc = desc;
            this.successUrl = successUrl;
            this.failUrl = failUrl;
        }

        public static DefenseUrlEnum getEnumByCode(Integer code) {
            return Arrays.stream(DefenseUrlEnum.values())
                    .filter(item -> item.getCode().equals(code))
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * 阻断和归属url
     */
    public enum BlockingAndBelongingUrlEnum {

        BLOCKING("阻断", "", ""),
        BELONGING ("归属", "", "");

        @Getter
        private final String name;
        @Getter
        private final String oneUrl;
        @Getter
        private final String twoUrl;

        BlockingAndBelongingUrlEnum(String name, String oneUrl, String twoUrl) {
            this.name = name;
            this.oneUrl = oneUrl;
            this.twoUrl = twoUrl;
        }

    }
}
