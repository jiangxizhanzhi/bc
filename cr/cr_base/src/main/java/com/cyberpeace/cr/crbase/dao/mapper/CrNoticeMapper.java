/**
 * 这段代码定义了一个基于 MyBatis-Plus 的数据访问接口 `CrNoticeMapper`，用于操作数据库中与 `CrNotice` 实体类相关的数据。以下是代码的简要分析： 
 * 1. **包名**： 
 * - `com.cyberpeace.cr.crbase.dao.mapper` 表明该文件位于 `crbase` 模块下的 `dao.mapper` 包中，通常用于存放与数据库操作相关的接口。 
 * 2. **依赖框架**： 
 * - 使用了 MyBatis-Plus 提供的 `BaseMapper` 接口，`BaseMapper` 是一个通用的 CRUD 操作接口，无需手动编写 SQL 即可完成增删改查功能。 
 * - `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口，用于映射 SQL 操作。 
 * - `@Repository` 注解表示这是一个 Spring 容器中的 Bean，用于依赖注入。 
 * 3. **泛型定义**： 
 * - `BaseMapper<CrNotice>` 泛型参数指定了操作的实体类为 `CrNotice`。 
 * 4. **接口功能**： 
 * - 该接口继承自 `BaseMapper`，因此可以自动拥有对 `CrNotice` 实体的 CRUD 操作方法（如 `insert`、`deleteById`、`selectById` 等）。 
 * 5. **注释说明**： 
 * - 类上的注释描述了该接口的功能是操作“靶场通知”相关的数据。 
 * 总结： 
 * 这是一段简洁高效的代码，利用 MyBatis-Plus 的特性实现了对 `CrNotice` 实体的数据库操作，减少了冗余代码，提高了开发效率。 
**/
package com.cyberpeace.cr.crbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrNotice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 靶场通知Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrNoticeMapper extends BaseMapper<CrNotice> {



}
