package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评估变量裁判Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalVariableRefMapper` 的接口，继承自 `BaseMapper<EvalVariableRef>`。这个接口用于与数据库进行交互，处理与 `EvalVariableRef` 实体相关的数据操作。以下是对代码的详细分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.cr.evaluation.dao.mapper; 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef; 
 * import org.apache.ibatis.annotations.Mapper; 
 * import org.apache.ibatis.annotations.Param; 
 * import org.springframework.stereotype.Repository; 
 * import java.util.List; 
 * ``` 
 * - 这些导入语句引入了所需的类和注解，如 MyBatis 的 `@Mapper` 注解、Spring 的 `@Repository` 注解以及基本的数据结构 `List`。 
 * ### 接口定义 
 * ```java 
 * @Mapper 
 * @Repository 
 * public interface EvalVariableRefMapper extends BaseMapper<EvalVariableRef> { 
 * ``` 
 * - `@Mapper` 注解表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository` 注解表明这是一个 Spring 管理的持久层组件。 
 * - `EvalVariableRefMapper` 继承了 `BaseMapper<EvalVariableRef>`，这意味着它自动拥有了 MyBatis-Plus 提供的基本 CRUD 操作方法。 
 * ### 方法定义 
 * #### 1. 获取变量ID 
 * ```java 
 * List<String> getVariableIdByRecordId(@Param("recordId") String recordId); 
 * ``` 
 * - 该方法通过给定的 `recordId` 查询并返回相关的变量ID列表。 
 * #### 2. 获取评估信息 
 * ```java 
 * List<EvalVariableRef> getByRecordId(@Param("recordId") String recordId); 
 * ``` 
 * - 该方法通过给定的 `recordId` 查询并返回相关的 `EvalVariableRef` 对象列表。 
 * #### 3. 获取多个记录ID对应的变量信息 
 * ```java 
 * List<EvalVariableRef> getVariableIdByRecordIds(List<String> evalIndicatorIds); 
 * ``` 
 * - 该方法通过给定的 `evalIndicatorIds` 列表查询并返回相关的 `EvalVariableRef` 对象列表。 
 * ### 总结 
 * - `EvalVariableRefMapper` 接口提供了与 `EvalVariableRef` 实体相关的数据访问方法。 
 * - 通过继承 `BaseMapper<EvalVariableRef>`，它获得了默认的 CRUD 操作。 
 * - 自定义的方法允许通过 `recordId` 和 `evalIndicatorIds` 获取特定的变量信息。 
 * - 使用了 MyBatis 和 Spring 的相关注解来确保接口可以被正确地识别和管理。 
**/
@Mapper
@Repository
public interface EvalVariableRefMapper extends BaseMapper<EvalVariableRef> {
    /**
     * 通过评估获取id
     *
     * @param recordId
     * @return
     */
    List<String>  getVariableIdByRecordId(@Param("recordId") String recordId);

    /**
     * 通过评估获取测评
     *
     * @param recordId
     * @return
     */
    List<EvalVariableRef> getByRecordId(@Param("recordId") String recordId);


    /**
     * 获取变量id由记录id列表
     *
     * @param evalIndicatorIds 评估指示器id列表
     * @return {@code List<EvalVariableRef> }
     */
    List<EvalVariableRef> getVariableIdByRecordIds(List<String> evalIndicatorIds);
}

