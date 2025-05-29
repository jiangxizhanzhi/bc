package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

import java.util.Date;

/**
 * 这段代码定义了一个名为 `EvalRecord` 的实体类，用于表示评估记录。以下是该类的主要特性和功能： 
 * ### 类结构 
 * - **包名**：`com.cyberpeace.cr.evaluation.dao.entity` 
 * - **继承关系**：`EvalRecord` 继承自 `BaseTable`。 
 * - **注解**： 
 * - `@TableName("cr_eval_record")`：指定该实体类对应的数据库表名为 `cr_eval_record`。 
 * - `@Getter` 和 `@Setter`：为所有字段提供 getter 和 setter 方法。 
 * ### 字段 
 * - **resourceId**：字符串类型，表示资源 ID，使用 `@TableId` 注解标记为主键，并且类型为 UUID。 
 * - **name**：字符串类型，表示名称。 
 * - **description**：字符串类型，表示描述。 
 * - **status**：整数类型，表示评估任务的状态。 
 * - **score**：整数类型，表示评估任务的得分。 
 * - **systemId**：字符串类型，表示引用的指标体系 ID。 
 * - **taskId**：字符串类型，表示靶场测评实例 ID。 
 * - **message**：字符串类型，表示评估过程中收集的错误信息。 
 * - **startTime**：日期类型，表示评估任务采集数据的开始时间。 
 * - **endTime**：日期类型，表示评估任务采集数据的结束时间。 
 * - **evalTime**：日期类型，表示评估开始时间。 
 * - **evalEndTime**：日期类型，表示评估结束时间。 
 * ### 枚举类型 
 * - **Status**：表示评估任务的状态枚举，包含以下状态： 
 * - `NOT_START` (未评估) 
 * - `RUNNING` (评估中) 
 * - `END` (已评估) 
 * - `EXCEPTION` (异常) 
 * - **InvokeType**：表示调用类型枚举，包含以下类型： 
 * - `INDEX` (指标项) 
 * - `VARIABLE` (变量项) 
 * ### 总结 
 * `EvalRecord` 类是一个典型的实体类，用于映射数据库表 `cr_eval_record`。它包含了一些基本信息以及一些状态信息和时间戳，用于记录评估任务的详细情况。通过使用 MyBatis-Plus 提供的注解，可以方便地进行数据库操作。同时，类中定义了两个枚举类型 `Status` 和 `InvokeType`，分别用于表示评估任务的状态和调用类型。 
**/
@Getter
@Setter
@TableName("cr_eval_record")
public class EvalRecord extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    private String description;

    /**
     * 评估任务状态
     */
    private Integer status;

    /**
     * 评估任务得分
     */
    private Integer score;

    /**
     * 引用指标体系ID
     */
    private String systemId;

    /**
     * 靶场测评实例ID
     */
    private String taskId;

    /**
     * 评估任务进行中的错误信息收集
     */
    private String message;

    /**
     * 开始时间（评估任务采集数据时间范围）
     */
    private Date startTime;

    /**
     * 结束时间（评估任务采集数据时间范围）
     */
    private Date endTime;

    /**
     * 评估开始时间
     */
    private Date evalTime;

    /**
     * 评估结束时间
     */
    private Date evalEndTime;

    public enum Status implements GenericEnum {
        NOT_START(1, "未评估"),
        RUNNING(2, "评估中"),
        END(3, "已评估"),
        EXCEPTION(4, "异常");

        @Getter
        private final Integer code;
        @Getter
        private final String desc;

        Status(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum InvokeType implements GenericEnum {
        INDEX(1, "指标项"), VARIABLE(2, "变量项");

        @Getter
        private final Integer code;
        @Getter
        private final String desc;

        InvokeType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}

