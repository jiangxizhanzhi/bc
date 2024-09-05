package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.JsonUtil;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("cr_eval_rule_template")
public class EvalRuleTemplate extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则描述
     */
    private String description;

    /**
     * 云模板id
     */
    private String cloudTemplateId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 标签
     */
    private String labels;

    /**
     * 等级
     */
    private Integer grade;

    /**
     * 布尔关系
     */
    private Integer booleanRelation;

    /**
     * 关联步骤id列表
     */
    private String stepIds;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 告警名称
     */
    private String alarmName;

    /**
     * 告警描述说明
     */
    private String alarmDescription;

    /**
     * 告警事件级别
     */
    private Integer alarmLevel;

    /**
     * 自定义属性
     */
    private String alarmAttributes;

    public List<String> getStepIdList() {
        if (StringUtils.isEmpty(stepIds)) {
            return new ArrayList<>();
        } else {
            return JsonUtil.toJsonObjectArray(stepIds, String.class);
        }
    }

    public void setStepIdList(List<String> tasks) {
        stepIds = JsonUtil.toJsonString(tasks);
    }

    public void setLabelList(List<String> labelList) {
        this.labels = JsonUtil.toJsonString(labelList);
    }

    public List<String> getLabelList() {
        if (StringUtils.isEmpty(this.labels)) {
            return new ArrayList<>();
        } else {
            return JsonUtil.toJsonObjectArray(this.labels, String.class);
        }
    }


    //	状态
    public enum Status implements GenericEnum {

        CLOSE(0, "关闭"),
        OPEN(1, "打开");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Status(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    //	等级
    public enum Grade implements GenericEnum {

        SEVERITY(0, "严重"),
        ERROR(1, "错误"),
        WARN(2, "警告"),
        INFO(3, "信息性"),
        DETA(4, "详细");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Grade(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    //	运算符
    public enum Operator {

        GREATER_THAN("GreaterThan", "大于"),
        GREATER_THAN_OR_EQUAL("GreaterThanOrEqual", "大于或等于"),
        less_than("LessThan","小于"),
        LESS_THAN_OR_EQUAL("LessThanOrEqual","小于或等于");

        @Getter
        private final String code;

        @Getter
        private final String desc;

        Operator(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Aggregation {

        AVERAGE("Average", "平均值"),
        MAXIMUM("Maximum","最大值"),
        MINIMUM("Minimum","最小值"),
        TOTAL("LessThanOrEqual","总计"),
        COUNT("Count", "计数");

        @Getter
        private final String code;

        @Getter
        private final String desc;

        Aggregation(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Condition {
        CUSTOM_LOG_SEARCH("Custom Log Search", "自定义日志搜索"),
        AVAILABLE_MEMORY_BYTES("Available Memory Bytes", "Available Memory Bytes"),
        CPU_CREDITS_CONSUMED("CPU Credits Consumed", "CPU Credits Consumed"),
        CPU_CREDITS_REMAINING("CPU Credits Remaining", "CPU Credits Remaining"),
        DATA_DISK_BANDWIDTH_CONSUMED_PERCENTAGE("Data Disk Bandwidth Consumed Percentage", "Data Disk Bandwidth Consumed Percentage"),
        DATA_DISK_IOPS_CONSUMED_PERCENTAGE("Data Disk IOPS Consumed Percentage", "Data Disk IOPS Consumed Percentage"),
        DATA_DISK_LATENCY("Data Disk Latency", "Data Disk Latency"),
        DATA_DISK_MAX_BURST_BANDWIDTH("Data Disk Max Burst Bandwidth", "Data Disk Max Burst Bandwidth"),
        DATA_DISK_MAX_BURST_IOPS("Data Disk Max Burst IOPS", "Data Disk Max Burst IOPS"),
        DATA_DISK_QUEUE_DEPTH("Data Disk Queue Depth", "Data Disk Queue Depth"),
        DATA_DISK_READ_BYTES_PER("Data Disk Read Bytes", "Data Disk Read Bytes"),
        DATA_DISK_READ_OPERATIONS_PER("Data Disk Read Operations", "Data Disk Read Operations"),
        DATA_DISK_TARGET_BANDWIDTH("Data Disk Target Bandwidth", "Data Disk Target Bandwidth"),
        DATA_DISK_TARGET_IOPS("Data Disk Target IOPS", "Data Disk Target IOPS"),
        DATA_DISK_USED_BURST_BPS_CREDITS_PERCENTAGE("Data Disk Used Burst BPS Credits Percentage", "Data Disk Used Burst BPS Credits Percentage"),
        DATA_DISK_USED_BURST_IO_CREDITS_PERCENTAGE("Data Disk Used Burst IO Credits Percentage", "Data Disk Used Burst IO Credits Percentage"),
        DATA_DISK_WRITE_BYTES_PER("Data Disk Write Bytes", "Data Disk Write Bytes"),
        DATA_DISK_WRITE_OPERATIONS_PER("Data Disk Write Operations", "Data Disk Write Operations"),
        DISK_READ_BYTES("Disk Read Bytes", "Disk Read Bytes"),
        DISK_READ_OPERATIONS_PER("Disk Read Operations", "Disk Read Operations"),
        DISK_WRITE_BYTES("Disk Write Bytes", "Disk Write Bytes"),
        INBOUND_FLOWS("Inbound Flows", "Inbound Flows"),
        INBOUND_FLOWS_MAXIMUM_CREATION_RATE("Inbound Flows Maximum Creation Rate", "Inbound Flows Maximum Creation Rate"),
        NETWORK_IN_BILLABLE("Network In Billable", "Network In Billable"),
        NETWORK_IN_TOTAL("Network In Total", "Network In Total"),
        NETWORK_OUT_BILLABLE("Network Out Billable", "Network Out Billable"),
        NETWORK_OUT_TOTAL("Network Out Total", "Network Out Total"),
        OS_DISK_BANDWIDTH_CONSUMED_PERCENTAGE("OS Disk Bandwidth Consumed Percentage", "OS Disk Bandwidth Consumed Percentage"),
        OS_DISK_IOPS_CONSUMED_PERCENTAGE("OS Disk IOPS Consumed Percentage", "OS Disk IOPS Consumed Percentage"),
        OS_DISK_LATENCY("OS Disk Latency", "OS Disk Latency"),
        OS_DISK_MAX_BURST_BANDWIDTH("OS Disk Max Burst Bandwidth", "OS Disk Max Burst Bandwidth"),
        OS_DISK_MAX_BURST_IOPS("OS Disk Max Burst IOPS", "OS Disk Max Burst IOPS"),
        OS_DISK_QUEUE_DEPTH("OS Disk Queue Depth", "OS Disk Queue Depth"),
        OS_DISK_READ_BYTES_PER("OS Disk Read Bytes", "OS Disk Read Bytes"),
        OS_DISK_READ_OPERATIONS_PER("OS Disk Read Operations", "OS Disk Read Operations"),
        OS_DISK_TARGET_BANDWIDTH("OS Disk Target Bandwidth", "OS Disk Target Bandwidth"),
        OS_DISK_TARGET_IOPS("OS Disk Target IOPS", "OS Disk Target IOPS"),
        OS_DISK_USED_BURST_BPS_CREDITS_PERCENTAGE("OS Disk Used Burst BPS Credits Percentage", "OS Disk Used Burst BPS Credits Percentage"),
        OS_DISK_USED_BURST_IO_CREDITS_PERCENTAGE("OS Disk Used Burst IO Credits Percentage", "OS Disk Used Burst IO Credits Percentage"),
        OS_DISK_WRITE_BYTES_PER("OS Disk Write Bytes", "OS Disk Write Bytes"),
        OS_DISK_WRITE_OPERATIONS_PER("OS Disk Write Operations", "OS Disk Write Operations"),
        OUTBOUND_FLOWS("Outbound Flows", "Outbound Flows"),
        OUTBOUND_FLOWS_MAXIMUM_CREATION_RATE("Outbound Flows Maximum Creation Rate", "Outbound Flows Maximum Creation Rate"),
        PERCENTAGE_CPU("Percentage CPU", "Percentage CPU"),
        PREMIUM_DATA_DISK_CACHE_READ_HIT("Premium Data Disk Cache Read Hit", "Premium Data Disk Cache Read Hit"),
        PREMIUM_DATA_DISK_CACHE_READ_MISS("Premium Data Disk Cache Read Miss", "Premium Data Disk Cache Read Miss"),
        PREMIUM_OS_DISK_CACHE_READ_HIT("Premium OS Disk Cache Read Hit", "Premium OS Disk Cache Read Hit"),
        PREMIUM_OS_DISK_CACHE_READ_MISS("Premium OS Disk Cache Read Miss", "Premium OS Disk Cache Read Miss"),
        TEMP_DISK_LATENCY("Temp Disk Latency", "Temp Disk Latency"),
        TEMP_DISK_QUEUE_DEPTH("Temp Disk Queue Depth", "Temp Disk Queue Depth"),
        TEMP_DISK_READ_BYTES_PER("Temp Disk Read Bytes", "Temp Disk Read Bytes"),
        TEMP_DISK_READ_OPERATIONS_PER("Temp Disk Read Operations", "Temp Disk Read Operations"),
        TEMP_DISK_WRITE_BYTES_PER("Temp Disk Write Bytes", "Temp Disk Write Bytes"),
        TEMP_DISK_WRITE_OPERATIONS_PER("Temp Disk Write Operations", "Temp Disk Write Operations"),
        VM_AVAILABILITY_METRIC("VM Availability Metric", "VM Availability Metric"),
        VM_CACHED_BANDWIDTH_CONSUMED_PERCENTAGE("VM Cached Bandwidth Consumed Percentage", "VM Cached Bandwidth Consumed Percentage"),
        VM_CACHED_IOPS_CONSUMED_PERCENTAGE("VM Cached IOPS Consumed Percentage", "VM Cached IOPS Consumed Percentage"),
        VM_CACHED_USED_BURST_BPS_CREDITS_PERCENTAGE("VM Cached Used Burst BPS Credits Percentage", "VM Cached Used Burst BPS Credits Percentage"),
        VM_CACHED_USED_BURST_IO_CREDITS_PERCENTAGE("VM Cached Used Burst IO Credits Percentage", "VM Cached Used Burst IO Credits Percentage"),
        VM_UNCACHED_BANDWIDTH_CONSUMED_PERCENTAGE("VM Uncached Bandwidth Consumed Percentage", "VM Uncached Bandwidth Consumed Percentage"),
        VM_UNCACHED_IOPS_CONSUMED_PERCENTAGE("VM Uncached IOPS Consumed Percentage", "VM Uncached IOPS Consumed Percentage"),
        VM_UNCACHED_USED_BURST_BPS_CREDITS_PERCENTAGE("VM Uncached Used Burst BPS Credits Percentage", "VM Uncached Used Burst BPS Credits Percentage"),
        VM_UNCACHED_USED_BURST_IO_CREDITS_PERCENTAGE("VM Uncached Used Burst IO Credits Percentage", "VM Uncached Used Burst IO Credits Percentage");

        @Getter
        private final String code;

        @Getter
        private final String desc;

        Condition(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum EvaluationFrequency {

        oneMinute("PT1M", "1分钟"),
        fiveMinutes("PT5M","5分钟"),
        tenMinutes("PT10M","10分钟"),
        fifteenMinutes("PT15M","15分钟"),
        thirtyMinutes("PT30M", "30分钟"),
        fortyFiveMinutes("PT45M", "45分钟"),
        oneHour("PT1H", "1小时"),
        twoHours("PT2H", "1小时"),
        threeHours("PT3H", "1小时"),
        fourHours("PT4H", "1小时"),
        fiveHours("PT5H", "1小时"),
        sixHours("PT6H", "1小时"),
        oneDay("P1D", "1天");

        @Getter
        private final String code;

        @Getter
        private final String desc;

        EvaluationFrequency(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}