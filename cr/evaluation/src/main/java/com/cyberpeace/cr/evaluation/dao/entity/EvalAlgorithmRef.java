/**
 * 这段代码定义了一个名为 `EvalAlgorithmRef` 的 Java 类，该类是用于表示评估算法引用的实体。以下是对其主要特性和功能的简要分析： 
 * ### 包与导入 
 * - **包声明**：代码位于 `com.cyberpeace.cr.evaluation.dao.entity` 包中。 
 * - **导入语句**：导入了多个常用的库和框架，包括 MyBatis Plus、Swagger、Lombok 和自定义的基础接口和模型。 
 * ### 类定义 
 * - **注解**： 
 * - `@TableName("cr_eval_algorithm_ref")`：指定该实体对应的数据库表名。 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - **继承**：`EvalAlgorithmRef` 继承了 `BaseTable` 类，该类可能是用于处理基础数据库操作的抽象类。 
 * ### 属性 
 * - `resourceId`：资源 ID，类型为 UUID，作为主键。 
 * - `name`：名称。 
 * - `description`：描述。 
 * - `type`：类型。 
 * - `algorithmLanguage`：算法语言。 
 * - `code`：代号。 
 * - `matrixData`：矩阵数据。 
 * ### 内部枚举 
 * - `Params` 枚举：用于表示参数类型，实现了 `GenericEnum` 接口，并包含四个选项：数字、字符串、布尔值和 IP 地址。 
 * - `Mode` 枚举：用于表示模式，同样实现了 `GenericEnum` 接口，包含两个选项：评分算法和其他算法。 
 * ### 其他字段 
 * - `mode`：算法模式，通过 Swagger 注解 `@ApiModelProperty` 来描述。 
 * ### 总结 
 * - **用途**：`EvalAlgorithmRef` 类用于存储评估算法相关的数据，包括资源 ID、名称、描述等信息。 
 * - **数据库映射**：使用 MyBatis Plus 进行数据库映射，通过注解指定了表名和主键。 
 * - **枚举**：内部定义了两个枚举类型 `Params` 和 `Mode`，分别用于表示参数类型和算法模式。 
 * - **代码生成**：使用 Lombok 库来简化 getter 和 setter 的编写。 
 * 这段代码展示了如何使用现代 Java 技术（如 Lombok、MyBatis Plus 和 Swagger）来构建一个简单的实体类，用于数据库操作和 API 文档生成。 
**/
package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

import java.util.List;

/**
 * eval 算法 ref
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_eval_algorithm_ref")
public class EvalAlgorithmRef extends BaseTable {
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
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 算法语言
     */
    private Integer algorithmLanguage;

    /**
     * 代号
     */
    private String code;

    /**
     * 矩阵数据
     */
    private String matrixData;

    public enum Params implements GenericEnum {
        NUMBER(0, "Number"),
        STRING(1, "String"),
        BOOL(2, "Bool"),
        IP(3, "IP");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Params(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    /**
     * 模式
     */
    @ApiModelProperty("算法模型")
    private Integer mode;

    public enum Mode implements GenericEnum {

        SCORING_ALGORITHM(0, "其他算法"), FORMAT_ALGORITHM(1, "层次分析法");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        private Mode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

    }


}
