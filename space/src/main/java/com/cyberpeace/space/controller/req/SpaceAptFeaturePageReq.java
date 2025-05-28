/**
 * 这段代码定义了一个名为 `SpaceAptFeaturePageReq` 的类，它继承自 `SpacePageReqBase`，并用于生成针对 `SpaceAptFeature` 实体的查询包装器。以下是代码的主要功能和结构分析： 
 * 1. **类和注解**： 
 * - 类 `SpaceAptFeaturePageReq` 使用了 `@ApiModel` 注解，表明这是一个与 API 相关的模型类。 
 * - 类的作用是实现从请求到查询条件的转换。 
 * 2. **方法 `req2QueryWrapper`**： 
 * - 方法的功能是将当前请求转换为一个 `LambdaQueryWrapper` 对象。 
 * - 首先，创建一个 `LambdaQueryWrapper` 实例用于构建查询条件。 
 * - 设置查询结果按 `updateTime` 字段降序排列。 
 * - 如果请求中包含非空的 `pattern` 参数，则在 `name` 字段上添加模糊查询条件。 
 * - 最后返回配置好的 `LambdaQueryWrapper` 对象。 
 * 3. **依赖的类和方法**： 
 * - 使用了 `com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper` 进行查询条件的构建。 
 * - 使用了 `com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank` 来检查 `pattern` 是否为空。 
 * - 继承的父类 `SpacePageReqBase` 提供了 `getPattern()` 方法来获取请求中的模式字符串。 
 * 4. **用途**： 
 * - 该类主要用于处理分页请求，并根据请求参数动态生成查询条件，适用于 MyBatis-Plus 的查询操作。 
 * 总结来说，这段代码的核心是一个方法 `req2QueryWrapper`，它能够根据请求参数动态生成查询条件，并且支持按更新时间排序以及名称模糊查询的功能。 
**/
package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import io.swagger.annotations.ApiModel;

/**
 * 空间容易特征分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpacePageReqBase
 * @since 1.0.0
 * @author zhuyuzhi
 * @date 2021/9/28
 * comemnt
 */
@ApiModel
public class SpaceAptFeaturePageReq extends SpacePageReqBase {
    /**
     * 将请求转换为针对SpaceAptFeature实体的查询包装器
     *
     * 此方法用于构建一个LambdaQueryWrapper对象，该对象根据当前请求的条件来查询SpaceAptFeature表
     * 它首先按照更新时间降序排序查询结果，然后根据请求中的模式字符串对名称进行模糊查询
     * 如果模式字符串为空，则只进行排序操作
     *
     * @return LambdaQueryWrapper<SpaceAptFeature> 返回一个配置好的查询包装器对象
     */
    @Override
    public LambdaQueryWrapper<SpaceAptFeature> req2QueryWrapper() {
        // 创建LambdaQueryWrapper对象，用于后续的查询条件设置
        LambdaQueryWrapper<SpaceAptFeature> wrapper = new LambdaQueryWrapper<>();
        // 设置查询结果按照更新时间降序排序
        wrapper.orderByDesc(SpaceAptFeature::getUpdateTime);
        // 如果模式字符串不为空，则添加对名称的模糊查询条件
        if (StringUtils.isNotBlank(this.getPattern())) {
            wrapper.like(SpaceAptFeature::getName, this.getPattern());
        }
        // 设置查询的实体类类型
        wrapper.setEntityClass(SpaceAptFeature.class);
        // 返回配置好的查询包装器对象
        return wrapper;
    }
}
