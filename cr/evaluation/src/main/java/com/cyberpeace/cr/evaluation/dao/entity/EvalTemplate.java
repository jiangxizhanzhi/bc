package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Getter;
import lombok.Setter;

/**
 * 这段代码定义了一个名为 `EvalTemplate` 的 Java 类，该类位于包 `com.cyberpeace.cr.evaluation.dao.entity` 中。以下是对此代码的详细分析： 
 * 1. **包声明**： 
 * - `package com.cyberpeace.cr.evaluation.dao.entity;` 
 * - 这行代码指定了该类所在的包名。 
 * 2. **导入语句**： 
 * - `import com.baomidou.mybatisplus.annotation.IdType;` 
 * - `import com.baomidou.mybatisplus.annotation.TableId;` 
 * - `import com.baomidou.mybatisplus.annotation.TableName;` 
 * - `import com.cyberpeace.common.model.BaseTable;` 
 * - 这些导入语句引入了所需的外部类和注解。 
 * - `com.baomidou.mybatisplus.annotation` 包提供了 MyBatis-Plus 框架的注解，用于数据库操作。 
 * - `com.cyberpeace.common.model.BaseTable` 是一个基类，可能包含了通用的字段和方法。 
 * 3. **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 库提供，自动生成 getter 和 setter 方法。 
 * - `@TableName("cr_eval_template")`：指定该实体类对应的数据库表名为 `cr_eval_template`。 
 * 4. **类定义**： 
 * - `public class EvalTemplate extends BaseTable { ... }` 
 * - `EvalTemplate` 类继承自 `BaseTable` 类，因此它可能包含一些通用字段和方法。 
 * 5. **属性**： 
 * - `@TableId(type = IdType.UUID, value = "resource_id") private String resourceId;` 
 * - 使用 `@TableId` 注解标记 `resourceId` 作为主键，并且其值类型为 UUID。 
 * - 其他属性如 `name`, `description`, `logo`, `topoId`, `categoryCode`, `stepConfig`, `enableRecording`, `currentTaskId`, `createUserId` 都是基本数据类型的成员变量。 
 * 6. **方法**： 
 * - `public void setLogoId(String logoId)`：这是一个自定义的方法，用于设置 `logo` 属性。 
 * 总结： 
 * - `EvalTemplate` 类是一个数据库表 `cr_eval_template` 的实体类，使用 MyBatis-Plus 进行 ORM 映射。 
 * - 它继承自 `BaseTable` 类，可能包含了一些通用字段和方法。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解简化了属性的访问器生成。 
 * - 该类的实例将映射到数据库中的 `cr_eval_template` 表，并且可以通过这些属性进行操作。 
**/
@Getter
@Setter
@TableName("cr_eval_template")
public class EvalTemplate extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private String name;

	private String description;

	private String logo;

	private String topoId;

	private Integer categoryCode;

	private String stepConfig;

	private Boolean enableRecording;

	private String currentTaskId;

	private String createUserId;

	public void setLogoId(String logoId) {
		this.logo = logoId;
	}

}

