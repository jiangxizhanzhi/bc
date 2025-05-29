package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.oj.ojbase.dao.entity.Note;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 备注列表 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Note
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `NoteListBo` 的类，该类继承自 `Note` 类。以下是对其主要特性和功能的简要分析： 
 * 1. **包声明**： 
 * - `com.cyberpeace.oj.ojbase.service.bo`：表明这个类属于 `com.cyberpeace.oj.ojbase` 项目中的 `service.bo` 包。 
 * 2. **导入语句**： 
 * - 导入了 `com.cyberpeace.oj.ojbase.dao.entity.Note` 类。 
 * - 导入了 Swagger 注解 `@ApiModelProperty`。 
 * - 导入了 Lombok 的 `@Getter` 和 `@Setter` 注解。 
 * 3. **类声明**： 
 * - `NoteListBo` 类继承自 `Note` 类。 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动为所有字段生成 getter 和 setter 方法。 
 * 4. **类文档**： 
 * - 类上有 Javadoc 文档，描述了该类的功能（备注列表 BO）以及作者、版本、日期等信息。 
 * 5. **字段**： 
 * - `userName` 字段：表示用户的名称，并使用了 Swagger 的 `@ApiModelProperty` 注解来描述其用途。 
 * 6. **继承**： 
 * - `NoteListBo` 继承自 `Note` 类，这意味着它可能包含 `Note` 类中定义的所有属性和方法。 
 * 总结： 
 * - `NoteListBo` 类是一个用于表示备注列表的数据传输对象（BO），它继承自 `Note` 类并添加了一个新的字段 `userName`。 
 * - 使用 Lombok 的注解简化了代码，减少了手动编写 getter 和 setter 方法的工作量。 
 * - 通过 Swagger 注解，可以方便地生成 API 文档。 
**/
@Getter
@Setter
public class NoteListBo extends Note {

    /**
     * 用户名称
     *
     * @see String
     */
    @ApiModelProperty("用户名称")
    private String userName;

}

