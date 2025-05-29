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
 * eval 变量 ref
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalVariableRef` 的 Java 类，该类位于 `com.cyberpeace.cr.evaluation.dao.entity` 包中。这个类主要用于表示一个评估变量引用（eval variable ref），并继承了 `BaseTable` 类。以下是对该代码的详细分析： 
 * ### 1. 类和注解 
 * - **类声明**：`@Getter` 和 `@Setter` 注解来自 Lombok 库，用于自动生成 getter 和 setter 方法。 
 * - **表名注解**：`@TableName("cr_eval_variable_ref")` 表示该类对应数据库中的 `cr_eval_variable_ref` 表。 
 * ### 2. 数据库字段映射 
 * - **资源 ID** (`resource_id`)：使用 `@TableId` 注解标记为主键，并且主键类型为 UUID。 
 * - **名称** (`name`)：字符串类型，表示变量的名称。 
 * - **变量项得分** (`score`)：整型，表示变量项的得分。 
 * - **类型** (`type`)：整型，表示变量项的类型。 
 * - **场景主机节点 ID** (`deviceId`)：字符串类型，表示场景主机节点的 ID。 
 * - **主机使用探针 ID** (`probeId`)：字符串类型，表示主机使用的探针 ID。 
 * - **评估任务 ID** (`recordId`)：字符串类型，表示评估任务的 ID。 
 * - **格式化算法 ID** (`formatAlgorithmId`)：字符串类型，表示格式化算法的 ID。 
 * - **格式化算法其它参数** (`formatParams`)：字符串类型，表示格式化算法的其他参数。 
 * - **匹配算法 ID** (`matchAlgorithmId`)：字符串类型，表示匹配算法的 ID。 
 * - **匹配算法其它参数** (`matchParams`)：字符串类型，表示匹配算法的其他参数。 
 * - **评分算法 ID** (`markAlgorithmId`)：字符串类型，表示评分算法的 ID。 
 * - **评分算法其它参数** (`markParams`)：字符串类型，表示评分算法的其他参数。 
 * - **进度** (`progress`)：整型，表示任务的进度。 
 * - **格式化开始时间** (`formatStartTime`)：日期类型，表示格式化操作的开始时间。 
 * - **格式化结束时间** (`formatEndTime`)：日期类型，表示格式化操作的结束时间。 
 * - **匹配开始时间** (`matchStartTime`)：日期类型，表示匹配操作的开始时间。 
 * - **匹配结束时间** (`matchEndTime`)：日期类型，表示匹配操作的结束时间。 
 * - **评分开始时间** (`markStartTime`)：日期类型，表示评分操作的开始时间。 
 * - **评分结束时间** (`markEndTime`)：日期类型，表示评分操作的结束时间。 
 * ### 3. 枚举类型 
 * - **进度枚举** (`Progress`)：定义了一个内部枚举类型 `Progress`，实现了 `GenericEnum` 接口。该枚举类型描述了不同阶段的任务状态，包括未开始、开始、格式化进行中、匹配进行中、评分进行中、异常和结束等状态。 
 * ### 4. 总结 
 * - **功能**：`EvalVariableRef` 类用于存储和管理评估过程中涉及的各种变量引用信息，包括任务进度、算法执行情况等。 
 * - **数据库映射**：该类通过 MyBatis Plus 的注解将类的属性与数据库表的字段进行映射。 
 * - **扩展性**：枚举类型的使用增加了代码的可读性和维护性，方便后续扩展和修改。 
 * ### 5. 注意事项 
 * - **重复字段**：字段 `score` 和 `type` 有重复的注释，建议删除其中一个以避免混淆。 
 * - **注释**：部分字段的注释可以进一步细化，以便更清晰地理解每个字段的具体含义。 
 * 整体而言，这是一个典型的持久化实体类，用于在数据库中存储评估变量引用的相关信息。 
**/
@Getter
@Setter
@TableName("cr_eval_variable_ref")
public class EvalVariableRef extends BaseTable {
    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 变量项得分
     */
    private Integer score;


    /**
     * 变量项得分
     */
    private Integer type;

    /**
     * 场景主机节点ID
     */
    private String deviceId;

    /**
     * 主机使用探针ID
     */
    private String probeId;

    /**
     * 评估任务ID
     */
    private String recordId;

    /**
     * 格式化算法ID
     */
    private String formatAlgorithmId;

    /**
     * 格式化算法其它参数
     */
    private String formatParams;

    /**
     * 匹配算法ID
     */
    private String matchAlgorithmId;

    /**
     * 匹配算法其它参数
     */
    private String matchParams;

    /**
     * 评分算法ID
     */
    private String markAlgorithmId;

    /**
     * 评分算法其它参数
     */
    private String markParams;

    /**
     * 进度
     */
    private Integer progress;

    /**
     * 格式化开始时间
     */
    private Date formatStartTime;

    /**
     * 格式化结束时间
     */
    private Date formatEndTime;

    /**
     * 匹配开始时间
     */
    private Date matchStartTime;

    /**
     * 匹配结束时间
     */
    private Date matchEndTime;

    /**
     * 评分开始时间
     */
    private Date markStartTime;

    /**
     * 评分结束时间
     */
    private Date markEndTime;

//    /**
//     * 执行轮次
//     */
//    private Integer exeCycle;

    public enum Progress implements GenericEnum {
        NOT_START(1, "未开始"),
        START(2, "开始/数据同步中"),
        FORMATTING(3, "格式化进行中"),
        MATCHING(4, "匹配进行中"),
        MARKING(5, "评分进行中"),
        EXCEPTION(6, "异常"),
        END(10, "结束");

        @Getter
        private final Integer code;
        @Getter
        private final String desc;

        Progress(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}

