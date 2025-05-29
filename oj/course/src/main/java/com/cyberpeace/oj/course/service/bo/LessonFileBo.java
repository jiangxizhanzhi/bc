package com.cyberpeace.oj.course.service.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程档案 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonFileBo` 的 Java 类，用于表示课程档案的业务对象（BO）。以下是对其主要特性的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`LessonFileBo` 
 * - **继承关系**：实现了 `Serializable` 接口，表明这个类的对象可以被序列化。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。这样可以避免手动编写这些方法，提高代码的简洁性。 
 * ### 属性 
 * - `fileType`：一个整数类型，用于表示文件类型。 
 * - `storeUrl`：一个字符串类型，表示商店 URL。 
 * - `originUrl`：一个字符串类型，表示源 URL。 
 * ### 文档注释 
 * - 类的文档注释提供了类的基本描述、作者、版本信息及创建日期。 
 * - 每个属性也有对应的 Javadoc 注释，说明了每个属性的用途和类型。 
 * ### 总结 
 * - 这是一个简单的数据传输对象（DTO）或业务对象（BO），用于在不同层之间传递课程档案的数据。 
 * - 使用 Lombok 减少了样板代码，使得类更加简洁易读。 
 * - 通过实现 `Serializable` 接口，该对象可以在需要时进行序列化处理。 
**/
@Getter
@Setter
public class LessonFileBo implements Serializable {

    /**
     * 文件类型
     *
     * @see Integer
     */
    private Integer fileType;

    /**
     * 商店 URL
     *
     * @see String
     */
    private String storeUrl;

    /**
     * 源 URL
     *
     * @see String
     */
    private String originUrl;

}

