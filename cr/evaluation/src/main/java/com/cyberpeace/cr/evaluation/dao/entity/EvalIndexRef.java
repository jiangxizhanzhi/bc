/**
 * 这段代码定义了一个名为 `EvalIndexRef` 的 Java 类，该类扩展了 `BaseTable` 类，并使用 Lombok 注解来简化 getter 和 setter 方法的生成。以下是对该代码的简要分析： 
 * ### 包与导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.dao.entity` 
 * - **导入的类和接口**： 
 * - `com.baomidou.mybatisplus.annotation.IdType` 和 `com.baomidou.mybatisplus.annotation.TableId` 用于数据库字段注解。 
 * - `com.baomidou.mybatisplus.annotation.TableName` 用于指定数据库表名。 
 * - `com.cyberpeace.common.baseinterface.GenericEnum` 用于自定义枚举接口。 
 * - `com.cyberpeace.common.model.BaseTable` 是基类。 
 * - `lombok.Getter` 和 `lombok.Setter` 用于自动生成 getter 和 setter 方法。 
 * - `java.util.Date` 用于日期处理。 
 * ### 类定义 
 * - **类名**：`EvalIndexRef` 
 * - **继承**：`BaseTable` 
 * - **注解**： 
 * - `@TableName("cr_eval_index_ref")`：指定数据库表名为 `cr_eval_index_ref`。 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的 getter 和 setter 方法。 
 * ### 字段定义 
 * - `resourceId`：唯一标识符，类型为 UUID。 
 * - `name`：名称。 
 * - `type`：指标项类型，用于设置默认分值。 
 * - `score`：指标项得分。 
 * - `level`：指标等级。 
 * - `recordId`：评估任务 ID。 
 * - `algorithmId`：使用的聚合算法 ID。 
 * - `algorithmParams`：算法实际额外参数。 
 * - `specialistValue`：专家评分设置值。 
 * - `specialistType`：专家评分设置类型。 
 * - `specialistAttr`：专家评分设置属性。 
 * - `specialistAttrValue`：专家评分设置中心值。 
 * - `nextIds`：当前指标项下一级 ID，为 JSON 字符串。 
 * - `startTime`：计算开始时间。 
 * - `endTime`：计算结束时间。 
 * - `progress`：评估进度。 
 * - `matrixData`：矩阵内容。 
 * ### 枚举定义 
 * - `Type`：枚举类型，表示指标项类型，实现 `GenericEnum` 接口。 
 * - `Level`：枚举类型，表示指标等级，实现 `GenericEnum` 接口。 
 * - `DataAttr`：枚举类型，表示数据属性，实现 `GenericEnum` 接口。 
 * - `DataType`：枚举类型，表示数据类型，实现 `GenericEnum` 接口。 
 * - `Progress`：枚举类型，表示评估进度，实现 `GenericEnum` 接口。 
 * ### 枚举结构 
 * 每个枚举类型都包含一个构造函数，初始化 `code` 和 `desc` 字段，并且这些字段通过 Lombok 的 `@Getter` 注解自动提供 getter 方法。 
 * ### 总结 
 * - **主要功能**：`EvalIndexRef` 类用于表示评估指标项的相关信息。 
 * - **数据存储**：该类定义了一系列字段，用于存储评估指标项的各种属性。 
 * - **枚举类型**：定义了多个枚举类型，用于表示不同类型的数据属性和状态。 
 * - **框架支持**：使用了 MyBatis Plus 框架的注解来简化数据库操作，并利用 Lombok 来减少样板代码。 
 * 这段代码是一个典型的 Java 实体类，适用于数据持久化和业务逻辑处理。 
**/
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

@Getter
@Setter
@TableName("cr_eval_index_ref")
public class EvalIndexRef extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String name;

    /**
     * 指标项类型, 用于设置默认分值
     */
    private Integer type;

    /**
     * 指标项得分
     */
    private Integer score;

    /**
     * 指标等级
     */
    private Integer level;

    /**
     * 评估任务ID
     */
    private String recordId;

    /**
     * 使用的聚合算法ID
     */
    private String algorithmId;

    /**
     * 算法实际额外入参
     */
    private String algorithmParams;

    /**
     * 专家评分设置值（三级指标包含）类型输入
     */
    private Integer specialistValue;

    /**
     * 专家评分设置类型（三级指标包含）百分比/周期/整数值
     */
    private Integer specialistType;

    /**
     * 专家评分设置属性（三级指标包含）效益/成本/固定/偏离
     */
    private Integer specialistAttr;

    /**
     * 专家评分设置中心值（三级指标包含）固定/偏离-中心值
     */
    private Integer specialistAttrValue;

    /**
     * 当前指标项下一级ID, 为json字符串
     */
    private String nextIds;

    /**
     * 计算开始时间
     */
    private Date startTime;

    /**
     * 计算结束时间
     */
    private Date endTime;

    /**
     * 评估进度
     */
    private Integer progress;

    /**
     * 矩阵内容
     */
    private String matrixData;

    public enum Type implements GenericEnum {
        SYSTEM(1, "系统评分"), SPECIALIST(2, "专家评分");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Type(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Level implements GenericEnum {
        AGGREGATION(4, "聚合项"),
        VARIABLE(5, "变量项");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Level(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum DataAttr implements GenericEnum {
        BENEFIT(1, "效益型"), COST(2, "成本型"), FIXED(3, "固定型"), DEVIATE(4, "偏离型");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        DataAttr(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum DataType implements GenericEnum {
        PERCENT(1, "百分比"), CYCLE(2, "周期"), INTEGER(3, "整数型");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        DataType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Progress implements GenericEnum {
        NOT_START(1, "未开始"),
        EXCEPTION(2, "异常"),
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
