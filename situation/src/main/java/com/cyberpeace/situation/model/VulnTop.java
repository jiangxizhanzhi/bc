package com.cyberpeace.situation.model;

import lombok.Data;

import java.util.List;

@Data
public class VulnTop {
    private String nodeId;

    private Double cvss;

    private List<Vulns> vulnList;

    private List<Defenses> defenseList;

    // 节点状态列表
    private List<NodeState> nodeStateList;

    // 节点被攻击动作列表
    private List<NodeAttackedAction> nodeAttackedActionList;

    @Data
    public static class Vulns {
        private String vulnId;

        private String vulnNmae;

        private Double vulnCvss;

        private String vulnLevl;

        private String vulnNumber;
    }

    @Data
    public static class Defenses {
        private String defenseId;

        private String defenseVulnName;

        private String defenseVulnCode;

        private String description;
    }

    @Data
    public static class NodeState {

        // 状态编号
        private Integer stateId;

        // 状态名称
        private String stateName;

        // 成功攻击次数
        private Integer successAttackCount;

        public NodeState(Integer stateId, String stateName, Integer successAttackCount) {
            this.stateId = stateId;
            this.stateName = stateName;
            this.successAttackCount = successAttackCount;
        }

    }

    @Data
    public static class NodeAttackedAction {

        // 攻击节点名称
        private String attackNodeName;

        // 攻击动作
        private String attackAction;

        // 利用漏洞
        private String attackVuln;

        // 描述
        private String description;

        public NodeAttackedAction(String attackNodeName, String attackAction, String attackVuln) {

            // 如果attackAction以攻击结尾，则去除攻击后缀
            if (attackAction.endsWith("攻击")) {
                attackAction = attackAction.substring(0, attackAction.length() - 2);
            }

            // 处理数据
            this.attackNodeName = attackNodeName;
            this.attackAction = attackAction;
            this.attackVuln = attackVuln;
            this.description = attackNodeName + "完成" + attackAction + "攻击事件，利用漏洞为" + attackVuln;
        }
    }
}
