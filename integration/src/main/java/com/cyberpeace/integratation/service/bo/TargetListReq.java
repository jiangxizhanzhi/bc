package com.cyberpeace.integratation.service.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * 这段代码定义了一个名为 `TargetListReq` 的 Java 类，该类用于表示一个请求对象，通常用于从某个服务或API中获取目标列表。以下是对此代码的详细分析： 
 * 1. **包声明**： 
 * - `package com.cyberpeace.integratation.service.bo;` 
 * 这行代码表明这个类属于 `com.cyberpeace.integratation.service.bo` 包。 
 * 2. **导入语句**： 
 * - `import lombok.Getter;` 
 * - `import lombok.Setter;` 
 * 这两行代码导入了 Lombok 库中的 `@Getter` 和 `@Setter` 注解，它们用于自动生成类的 getter 和 setter 方法。 
 * 3. **类定义**： 
 * - `@Getter @Setter public class TargetListReq { ... }` 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成所有属性的 getter 和 setter 方法。 
 * - 该类没有继承其他类，也没有实现任何接口。 
 * 4. **属性**： 
 * - `private String imageStatus;` 
 * - `private String category;` 
 * - `private String parentSystemType;` 
 * - `private String imageTypeList;` 
 * - `private String preTypeList;` 
 * - `private String name;` 
 * - `private String secondCategoryList;` 
 * 所有这些属性都是 `String` 类型，并且使用 `private` 关键字修饰，表明它们是私有的。 
 * 5. **方法**： 
 * - `public String toQuery() { ... }` 
 * - `toQuery()` 方法返回一个字符串，该字符串包含所有非空属性的查询参数格式。 
 * - 使用 `StringBuilder` 来构建查询字符串，确保每个参数前都有 `&` 符号连接。 
 * - 每个属性都通过检查是否为 `null` 来决定是否将其添加到查询字符串中。 
 * ### 总结 
 * - `TargetListReq` 类主要用于封装与目标列表相关的查询条件。 
 * - 该类提供了方便的方法来生成一个包含所有非空查询参数的字符串。 
 * - 使用 Lombok 提供的注解简化了 getter 和 setter 方法的编写。 
 * 这种设计使得类易于扩展和维护，同时提供了一种简单的方式来生成查询字符串。 
**/
@Getter
@Setter
public class TargetListReq {

    private String imageStatus;

    private String category;

    private String parentSystemType;

    private String imageTypeList;

    private String preTypeList;

    private String name;

    private String secondCategoryList;

    public String toQuery() {
        StringBuilder builder = new StringBuilder();
        builder.append("?imageStatus=").append(this.imageStatus);
        builder.append("&category=").append(this.category);
        if (this.parentSystemType != null) {
            builder.append("&parentSystemType=").append(this.parentSystemType);
        }
        if (this.imageTypeList != null) {
            builder.append("&imageTypeList=").append(this.imageTypeList);
        }
        if (this.preTypeList != null) {
            builder.append("&preTypeList=").append(this.preTypeList);
        }
        if (this.name != null) {
            builder.append("&name=").append(this.name);
        }
        if (this.secondCategoryList != null) {
            builder.append("&secondCategoryList=").append(this.secondCategoryList);
        }
        return builder.toString();
    }
}

