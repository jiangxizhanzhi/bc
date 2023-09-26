package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.constant.BaseI18n;
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

    public enum Type implements GenericEnum, BaseI18n {

        MISSION(1, "任务导调", "Task facilitation", ""),
        MANUALLY(2, "手动导调", "Manual tuning", ""),
        FLOW(3, "流量采集事件", "Traffic collection", "flow"),
        HOST(4, "主机采集事件", "Host acquisition", "host");

        @Getter
        private Integer code;
        @Getter
        private String chinese;
        @Getter
        private String english;
        @Getter
        private String mpCode;

        Type(Integer code, String chinese, String english, String mpCode) {
            this.code = code;
            this.chinese = chinese;
            this.english = english;
            this.mpCode = mpCode;
        }

        public static Type getTypeByCode(Integer code) {
            return Arrays.stream(Type.values())
                    .filter(item -> item.getCode().equals(code))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("The code of Type isn't available"));
        }

        public static Type getTypeByMpCode(String mpCode) {
            return Arrays.stream(Type.values())
                         .filter(item -> item.getMpCode().equals(mpCode))
                         .findFirst()
                         .orElseThrow(() -> new IllegalArgumentException("The code of Type isn't available"));
        }

        @Override
        public String getDesc() {
            return this.getMsg();
        }
    }

    public enum EventType implements GenericEnum, BaseI18n {

        ATTACK(1, "攻击", "attack", "attack"),
        DEFENSE(2, "防御", "defense", "defense"),
        BLOCKING(3, "阻断", "interdiction", ""),
        BELONGING(4, "归属", "ascription", "");

        @Getter
        private Integer code;
        @Getter
        private String chinese;
        @Getter
        private String english;
        @Getter
        private String mpCode;

        public static EventType getEventTypeByCode(Integer code) {
            return Arrays.stream(EventType.values())
                    .filter(item -> item.getCode().equals(code))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("The code of EventType isn't available"));
        }

        public static EventType getEventTypeByMpCode(String mpCode) {
            return Arrays.stream(EventType.values())
                         .filter(item -> item.getMpCode().equals(mpCode))
                         .findFirst()
                         .orElseThrow(() -> new IllegalArgumentException("The code of EventType isn't available"));
        }

        EventType(Integer code, String chinese, String english, String mpCode) {
            this.code = code;
            this.chinese = chinese;
            this.english = english;
            this.mpCode = mpCode;
        }

        @Override
        public String getDesc() {
            return this.getMsg();
        }
    }

    public enum ResultType implements GenericEnum, BaseI18n {

        SUCCESS(1, "成功", "success"),
        FAIL(2, "失败", "fail"),
        UNKNOWN(3, "未知", "unknown");

        @Getter
        private Integer code;
        @Getter
        private String chinese;
        @Getter
        private String english;

        ResultType(Integer code, String chinese, String english) {
            this.code = code;
            this.chinese = chinese;
            this.english = english;
        }

        public static ResultType getResultTypeByCode(Integer code) {
            return Arrays.stream(ResultType.values())
                    .filter(item -> item.getCode().equals(code))
                    .findFirst()
                    .orElse(null);
        }


        @Override
        public String getDesc() {
            return this.getMsg();
        }
    }

    public enum RoleType implements GenericEnum, BaseI18n {

        RED(4, "归属红方", "red"), BLUE(5, "归属蓝方", "blue");

        @Getter
        private Integer code;
        @Getter
        private String chinese;
        @Getter
        private String english;

        RoleType(Integer code, String chinese, String english) {
            this.code = code;
            this.chinese = chinese;
            this.english = english;
        }

        public static RoleType getRoleTypeByCode(Integer code) {
            return Arrays.stream(RoleType.values())
                    .filter(item -> item.getCode().equals(code))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public String getDesc() {
            return this.getMsg();
        }
    }

    /**
     * 触发条件枚举
     */
    public enum ConditionType implements GenericEnum, BaseI18n {

        MISSION_SUBMIT(1, "任务提交", "submit"),
        MISSION_SUCCESS(2, "任务判断成功", "judge success");

        @Getter
        private Integer code;
        @Getter
        private String chinese;
        @Getter
        private String english;

        ConditionType(Integer code, String chinese, String english) {
            this.code = code;
            this.chinese = chinese;
            this.english = english;
        }

        @Override
        public String getDesc() {
            return this.getMsg();
        }

        public static ConditionType getEnumByCode(Integer code) {
            return Arrays.stream(ConditionType.values())
                    .filter(item -> item.getCode().equals(code))
                    .findFirst()
                    .orElse(null);
        }
    }

    /**
     * 攻击类型url枚举
     */
    public enum AttackUrlEnum implements BaseI18n {
        RECONNAISSANCE(1, "侦察", "Reconnaissance", "对手正试图收集可用于规划未来行动的信息。", "/cpms/guide/1.侦察.gif", "", "reconnaissance", "compound"),
        RESOURCE_DEVELOPMENT(2, "资源开发", "Resource Development", "攻击者正在尝试建立可用于支持操作的资源。", "/cpms/guide/2.资源开发.gif", "", "resource development", "element"),
        INITIAL_VISIT(3, "初始访问", "Initial Access", "攻击者正试图进入您的网络。", "/cpms/guide/3.初始访问.gif", "", "initial access", "element"),
        IMPLEMENT(4, "执行", "Execution", "攻击者正在尝试运行恶意代码。", "/cpms/guide/4.执行.gif", "", "execution", "element"),
        PERSISTENCE(5, "持久化", "Persistence", "对手正试图保持他们的立足点。", "/cpms/guide/5.持久化.gif", "", "persistence", "element"),
        PRIVACY_IMPROVEMENT(6, "特权提升", "Privilege Escalation", "攻击者正在尝试获取更高级别的权限。", "/cpms/guide/6.特权提升.gif", "", "privilege escalation", "element"),
        ESCAPE_DEFENSE(7, "逃避防御", "Defense Evasion", "对手试图避免被检测到。", "/cpms/guide/7.逃避防御.gif", "", "defense evasion", "element"),
        ACCORDING_INTERVIEW(8, "凭据访问", "Credential Access", "攻击者试图窃取帐户名和密码。", "/cpms/guide/8.凭据访问.gif", "", "credential access", "element"),
        ATTACK_DISCOVER(9, "发现", "Discovery", "对手正在尝试找出您的环境。", "/cpms/guide/9.访问.gif", "", "discovery", "element"),
        MOVE_HORIZONTALLY(10, "横向移动", "Lateral Movement", "攻击者正在尝试在您的环境中移动。", "/cpms/guide/10.横向移动.gif", "", "lateral movement", "element"),
        COLLECT_MESSAGE(11, "信息收集", "Collection", "对手正试图收集与其目标相关的数据。", "/cpms/guide/11.信息收集.gif", "", "collection", "element"),
        COMMAND_CONTROL(12, "命令与控制", "Command and Control", "攻击者正试图与受感染的系统进行通信以控制它们。", "/cpms/guide/12.命令与控制.gif", "", "command and control", "element"),
        INFORMATION_THEFT(13, "信息盗取", "Exfiltration", "攻击者试图窃取数据。", "/cpms/guide/13.信息盗取.gif", "", "exfiltration", "element"),
        INFLUENCE(14, "影响", "Impact", "攻击者试图操纵、中断或破坏您的系统和数据。", "/cpms/guide/14.影响.gif", "", "impact", "element");

        @Getter
        private final Integer code;

        @Getter
        private final String chinese;

        @Getter
        private final String english;

        @Getter
        private final String desc;

        @Getter
        private final String successUrl;

        @Getter
        private final String failUrl;

        @Getter
        private final String mpCode;

        @Getter
        private final String type;

        AttackUrlEnum(Integer code, String chinese, String english, String desc, String successUrl, String failUrl,
                      String mpCode, String type) {
            this.code = code;
            this.chinese = chinese;
            this.english = english;
            this.desc = desc;
            this.successUrl = successUrl;
            this.failUrl = failUrl;
            this.mpCode = mpCode;
            this.type = type;
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
    public enum DefenseUrlEnum implements BaseI18n {
        PLANNING(15, "规划", "Plan", "确定操作并将其与所需的最终状态对齐。", "/cpms/guide/15.规划.gif", "", "plan"),
        COLLECT(16, "收集", "Collect", "收集对手工具，观察战术，并收集有关对手活动的其他原始情报。", "/cpms/guide/16.收集.gif", "", "collect"),
        DEFENSE_DISCOVER(17, "发现", "Detect", "建立或保持对对手活动的意识。", "/cpms/guide/17.发现.gif", "", "detect"),
        DEFENSE(18, "防御", "Prevent", "停止对手按预期执行其操作的全部或部分能力。", "/cpms/guide/18.防御.gif", "", "prevent"),
        MOVEMENT(19, "转移", "Direct", "鼓励或阻止对手按预期进行操作。", "/cpms/guide/19.转移.gif", "", "direct"),
        DESTROY(20, "破坏", "Disrupt", "损害对手按预期执行操作的能力。", "/cpms/guide/20.破坏.gif", "", "disrupt"),
        ENSURE(21, "保证", "Reassure", "为欺骗性组件添加真实性，以使对手相信环境是真实的。", "/cpms/guide/21.保证.gif", "", "reassure"),
        EXCITATION(22, "激励", "Motivate", "鼓励对手执行部分或全部任务。", "/cpms/guide/22.激励.gif", "", "motivate"),
        ANALYZE(23, "分析", "Analyze", "回顾性审查从操作中获得的信息。", "/cpms/guide/23.分析.gif", "", "analyze");

        @Getter
        private final Integer code;
        @Getter
        private final String chinese;
        @Getter
        private final String english;
        @Getter
        private final String desc;
        @Getter
        private final String successUrl;
        @Getter
        private final String failUrl;
        @Getter
        private final String mpCode;

        DefenseUrlEnum(Integer code, String chinese, String english, String desc, String successUrl, String failUrl,
                       String mpCode) {
            this.code = code;
            this.chinese = chinese;
            this.english = english;
            this.desc = desc;
            this.successUrl = successUrl;
            this.failUrl = failUrl;
            this.mpCode = mpCode;
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
