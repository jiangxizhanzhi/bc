/**
 * 这段代码定义了一个 MyBatis 的 Mapper 接口，用于操作数据库中与 `SpaceAptThreatDetectRule` 实体类相关的数据。以下是对代码的简要分析： 
 * 1. **包名**：`com.cyberpeace.space.dao.mapper` 表示该文件位于项目中的 `mapper` 包下，通常存放与数据库交互的接口。 
 * 2. **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口，Spring 会自动扫描并注册它。 
 * - `@Repository`：将该接口标记为 Spring 的 Bean，便于依赖注入。 
 * 3. **继承关系**：该接口继承了 `BaseMapper<SpaceAptThreatDetectRule>`，`BaseMapper` 是 MyBatis-Plus 提供的一个通用 Mapper，提供了常见的增删改查方法，无需手动编写 SQL。 
 * 4. **功能描述**：通过该接口可以对 `SpaceAptThreatDetectRule` 实体类对应的数据库表进行 CRUD 操作。 
 * 5. **作者和版本信息**：注释中提供了作者 (`SN`) 和版本信息 (`1.0.0`)，以及创建日期 (`2025/05/28`)。 
 * 总结：这是一段基于 MyBatis-Plus 的通用 Mapper 接口代码，用于管理 `SpaceAptThreatDetectRule` 数据实体的操作。 
**/

package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;

/**
 * 空间容易威胁检测统治Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SpaceAptThreatDetectRuleMapper extends BaseMapper<SpaceAptThreatDetectRule>

{}