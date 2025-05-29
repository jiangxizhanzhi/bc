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
 * EVAL 可变参考探头数据
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalVariableRefProbeData` 的 Java 类，该类继承自 `BaseTable` 并且用于表示评估系统中的可变参考探头数据。以下是代码的详细分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.dao.entity` 
 * - **导入**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 和 `com.baomidou.mybatisplus.annotation.TableId`：用于 MyBatis-Plus 的注解，帮助生成数据库表字段。 
 * - `com.baomidou.mybatisplus.annotation.TableName`：用于指定数据库表名。 
 * - `com.cyberpeace.common.baseinterface.GenericEnum`：一个通用枚举接口。 
 * - `com.cyberpeace.common.model.BaseTable`：基类，可能包含一些基础字段如创建时间、更新时间等。 
 * - `java.util.Date`：用于表示日期。 
 * ### 类定义 
 * - **类名**：`EvalVariableRefProbeData` 
 * - **注解**： 
 * - `@TableName("cr_eval_variable_ref_probe_data")`：指定数据库表名为 `cr_eval_variable_ref_probe_data`。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 字段 
 * - `id`：主键，使用 `@TableId(type = IdType.AUTO)` 自动增长。 
 * - `variableId`：变量 ID，字符串类型。 
 * - `probeDataId`：探针数据 ID，整数类型。 
 * - `time`：数据记录的时间，`Date` 类型。 
 * - `formattedValue`：格式化后的值，字符串类型。 
 * - `status`：状态，整数类型。 
 * ### 枚举定义 
 * - `Status`：内部枚举类，实现了 `GenericEnum` 接口。 
 * - `NONE`：状态码为 0，描述为 "无"。 
 * - `MATCHED`：状态码为 1，描述为 "被匹配"。 
 * - 每个枚举项都有 `code` 和 `desc` 属性，并使用 Lombok 的 `@Getter` 自动生成 getter 方法。 
 * ### 总结 
 * - **功能**：这个类主要用于存储和操作评估系统的可变参考探头数据。 
 * - **技术栈**：使用了 MyBatis-Plus 和 Lombok 进行简化开发。 
 * - **扩展性**：通过枚举类定义状态，可以方便地扩展其他状态。 
 * 这段代码简洁明了，遵循了良好的编码规范，适用于实际应用中。 
**/
@Getter
@Setter
@TableName("cr_eval_variable_ref_probe_data")
public class EvalVariableRefProbeData extends BaseTable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 变量id
     */
    private String variableId;

    /**
     * 探针数据id
     */
    private Integer probeDataId;

    /**
     * 时间
     */
    private Date time;

    /**
     * 格式 化值
     */
    private String formattedValue;

    /**
     * 状态
     */
    private Integer status;

    public enum Status implements GenericEnum {
        NONE(0, "无"),
        MATCHED(1, "被匹配");

        @Getter
        private final Integer code;
        @Getter
        private final String desc;

        Status(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}

