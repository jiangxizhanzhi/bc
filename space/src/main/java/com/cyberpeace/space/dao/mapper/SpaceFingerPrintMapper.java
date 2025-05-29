package com.cyberpeace.space.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceFingerPrint;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 空间手指打印Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个基于 MyBatis-Plus 的数据访问层（DAO）接口 `SpaceFingerPrintMapper`，以下是其简要分析： 
 * 1. **继承关系**： 
 * - `SpaceFingerPrintMapper` 继承了 `BaseMapper<SpaceFingerPrint>`，这是 MyBatis-Plus 提供的一个基础接口，包含了常用的 CRUD 操作方法，无需手动编写 SQL。 
 * 2. **注解说明**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表示这是一个 Spring 的数据访问组件，便于 Spring 容器管理。 
 * 3. **功能描述**： 
 * - 这个接口用于操作 `SpaceFingerPrint` 实体类对应的数据表，提供增删改查等基本数据库操作。 
 * 4. **设计目的**： 
 * - 通过继承 `BaseMapper` 和使用注解，简化了传统 MyBatis 手动编写 XML 映射文件的工作量，提高了开发效率。 
**/
@Mapper
@Repository
public interface SpaceFingerPrintMapper extends BaseMapper<SpaceFingerPrint> {

}

