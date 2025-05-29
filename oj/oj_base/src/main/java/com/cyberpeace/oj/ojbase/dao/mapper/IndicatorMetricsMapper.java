package com.cyberpeace.oj.ojbase.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.IndicatorMetrics;

/**
 * 这段代码定义了一个名为 `IndicatorMetricsMapper` 的接口，它继承了 MyBatis-Plus 提供的 `BaseMapper` 接口，并且用于操作 `IndicatorMetrics` 实体类。以下是对该代码的简要分析： 
 * 1. **包声明**： 
 * - `package com.cyberpeace.oj.ojbase.dao.mapper;`：此行指定了该文件所在的包位置。 
 * 2. **导入的库**： 
 * - `org.apache.ibatis.annotations.Mapper`：这是一个注解，用于标识这是一个 MyBatis 的 Mapper 接口。 
 * - `org.springframework.stereotype.Repository`：这个注解表示这是一个数据访问对象（DAO）组件，可以被 Spring 容器管理。 
 * - `com.baomidou.mybatisplus.core.mapper.BaseMapper`：这是 MyBatis-Plus 提供的一个基础接口，提供了基本的 CRUD 操作。 
 * - `com.cyberpeace.oj.ojbase.dao.entity.IndicatorMetrics`：这是实体类 `IndicatorMetrics` 的引用，该类表示数据库中的一个表。 
 * 3. **接口定义**： 
 * - `public interface IndicatorMetricsMapper extends BaseMapper<IndicatorMetrics>`：`IndicatorMetricsMapper` 继承自 `BaseMapper<IndicatorMetrics>`。这意味着 `IndicatorMetricsMapper` 可以直接使用 `BaseMapper` 提供的通用方法来操作 `IndicatorMetrics` 表，例如增删改查等。 
 * 4. **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口，MyBatis 会自动扫描并创建相应的代理对象。 
 * - `@Repository`：表明这是一个 Spring 的数据访问组件，可以被 Spring 容器管理。 
 * 总结来说，这段代码定义了一个用于操作 `IndicatorMetrics` 表的 MyBatis-Plus Mapper 接口，提供了基本的 CRUD 功能，并且可以被 Spring 容器管理。 
**/
@Mapper
@Repository
public interface IndicatorMetricsMapper extends BaseMapper<IndicatorMetrics> {

}

