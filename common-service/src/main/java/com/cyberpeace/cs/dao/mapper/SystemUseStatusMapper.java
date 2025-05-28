/**
 * 这段代码定义了一个基于 MyBatis-Plus 的 Mapper 接口 `SystemUseStatusMapper`，用于操作数据库中的 `SystemUseStatus` 实体类。以下是代码的简要分析： 
 * 1. **继承关系**： 
 * - `SystemUseStatusMapper` 继承了 `BaseMapper<SystemUseStatus>`，这是 MyBatis-Plus 提供的一个通用接口，提供了基本的 CRUD 操作方法，无需手动编写 SQL。 
 * 2. **注解说明**： 
 * - 使用了 `@Mapper` 注解，表明这是一个 MyBatis 的 Mapper 接口，MyBatis-Spring 会自动扫描并注册该接口。 
 * 3. **自定义方法**： 
 * - `selectList()`：扩展了 `BaseMapper`，提供了一个自定义查询方法，返回 `SystemUseStatus` 的列表。 
 * - `deleteOverdueData()`：定义了一个删除过期数据的方法，具体逻辑需要在实现类中完成。 
 * 总结：这是一个典型的 MyBatis-Plus Mapper 接口，结合了通用的 CRUD 方法和自定义业务方法，方便快速开发数据访问层功能。 
**/
package com.cyberpeace.cs.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用状态Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface SystemUseStatusMapper extends BaseMapper<SystemUseStatus> {

    /**
     * 查询列表
     *
     * @return {@code List<SystemUseStatus> }
     */
    List<SystemUseStatus> selectList();

    /**
     * 删除过期数据
     */
    void deleteOverdueData();
}
