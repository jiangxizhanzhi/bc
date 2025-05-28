/**
 * 这段代码定义了一个名为 `EvalAlgorithmRefServiceImpl` 的类，该类实现了 `EvalAlgorithmRefService` 接口，并继承了 `ServiceImpl` 类。这个类主要用于处理与 `EvalAlgorithmRef` 实体相关的业务逻辑。以下是详细的分析： 
 * ### 包和导入 
 * - 该类位于 `com.cyberpeace.cr.evaluation.service.impl` 包中。 
 * - 导入了一些常用的工具类和框架类，如 `JSONObject`、`JsonUtil` 和 `EvalAlgorithmRefMapper`。 
 * ### 类定义 
 * - 类名：`EvalAlgorithmRefServiceImpl` 
 * - 注解： 
 * - `@Slf4j`：用于日志记录。 
 * - `@Service`：表明这是一个 Spring 服务类。 
 * - 继承自 `ServiceImpl<EvalAlgorithmRefMapper, EvalAlgorithmRef>`，这表明它是一个 MyBatis-Plus 提供的服务实现类。 
 * ### 成员变量 
 * - 定义了三个常量字符串： 
 * - `PARAM_NAME`：参数名称。 
 * - `PARAM_TYPE`：参数类型。 
 * - `PARAM_VALUE`：参数值（默认值）。 
 * ### 方法 
 * - `buildOtherArguments(String argumentsString)`：根据给定的 JSON 格式的字符串构建其他参数列表。 
 * - 输入参数：`argumentsString`，一个包含参数信息的 JSON 字符串。 
 * - 返回值：一个 `List<Serializable>` 类型的参数列表。 
 * - 方法逻辑： 
 * 1. 将 `argumentsString` 转换为 `List<JSONObject>`。 
 * 2. 遍历每个参数对象： 
 * - 获取参数类型 (`type`)。 
 * - 根据类型的不同，将参数值添加到结果列表中： 
 * - 数字类型 (`NUMBER`)：尝试转换为整数，如果失败则转换为双精度浮点数。 
 * - 字符串类型 (`STRING`)：直接添加字符串值。 
 * - 布尔类型 (`BOOL`)：直接添加布尔值。 
 * - IP 地址类型 (`IP`)：直接添加字符串值。 
 * ### 总结 
 * - 这个类的主要功能是解析传入的 JSON 字符串并根据不同的参数类型将其转换为相应的 Java 对象，最终返回一个参数列表。 
 * - 使用了 Lombok 的 `@Slf4j` 注解来简化日志记录的代码。 
 * - 利用了 MyBatis-Plus 的 `ServiceImpl` 类来简化数据访问层的操作。 
**/
package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmRefService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * 评估算法裁判Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class EvalAlgorithmRefServiceImpl extends ServiceImpl<EvalAlgorithmRefMapper, EvalAlgorithmRef> implements EvalAlgorithmRefService {

    /**
     * 参数名称
     */
    private final String PARAM_NAME = "param_name";

    /**
     * param 类型
     */
    private final String PARAM_TYPE = "param_type";

    /**
     * 参数值
     */
    private final String PARAM_VALUE = "default_value";

    /**
     * 构建其他参数
     *
     * @param argumentsString arguments 字符串
     * @return {@code List<Serializable> }
     */
    @Override
    public List<Serializable> buildOtherArguments(String argumentsString) {
        List<Serializable> args = Lists.newArrayList();
        List<JSONObject> params = JsonUtil.toJsonObjectArray(argumentsString, JSONObject.class);

        params.forEach(param -> {
            Integer type = param.getInteger(PARAM_TYPE);
            if (type.equals(EvalAlgorithmRef.Params.NUMBER.getCode())) {
                try {
                    args.add(param.getInteger(PARAM_VALUE));
                } catch (NumberFormatException e) {
                    args.add(param.getDouble(PARAM_VALUE));
                }
            } else if (type.equals(EvalAlgorithmRef.Params.STRING.getCode())) {
                args.add(param.getString(PARAM_VALUE));
            } else if (type.equals(EvalAlgorithmRef.Params.BOOL.getCode())) {
                args.add(param.getBoolean(PARAM_VALUE));
            } else if (type.equals(EvalAlgorithmRef.Params.IP.getCode())) {
                args.add(param.getString(PARAM_VALUE));
            }
        });
        return args;
    }
}
