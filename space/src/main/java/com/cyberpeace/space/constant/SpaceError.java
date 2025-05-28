/**
 * 这段代码定义了一个名为 `SpaceError` 的常量类，用于存储与空间相关的错误码。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于存储各种空间操作过程中可能出现的错误信息，每个错误都有一个唯一的字符串形式的错误码。 
 * 2. **设计特点**： 
 * - 所有字段均为 `public static final` 类型，表明这些错误码是不可变的全局常量。 
 * - 每个字段都附带了详细的注释，描述了对应的错误含义，便于开发者理解和使用。 
 * 3. **具体错误码说明**： 
 * - `BUILTIN_FRAME`: 表示无法删除内置框架。 
 * - `STRATEGY_EXIST`: 提示框架下存在策略，导致无法删除。 
 * - `METHOD_REFERENCE_EXIST`: 指出交战方法被引用，无法删除。 
 * - `ACTION_REFERENCE_EXIST`: 表明交战活动被引用，无法删除。 
 * - `PERCENTAGE_LARGER_THAN_100`: 警告占比输入超过100%。 
 * - `RVB_FRAME_USED`: 框架在同一红蓝对抗业务中只能使用一次。 
 * - `STAGE_RELEASED`: 阶段已下发。 
 * - `TACTIC_RELEASED`: 技法已下发。 
 * - `TACTIC_DUPLICATED`: 单个步骤下的战法不能重复。 
 * 4. **适用场景**： 
 * - 这些错误码可以在业务逻辑中作为返回值或提示信息，帮助前端或调用方快速定位问题并采取相应措施。 
 * 总结来说，此代码提供了一组结构化的错误码定义，有助于提高代码的可读性和维护性。 
**/
package com.cyberpeace.space.constant;

/**
 * 空间错误
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 * 错误码
 *
 * @author zhangyong
 * @date 2021/12/07
 */
public class SpaceError {
    /**
     * 无法删除内置框架
     */
    public static final String BUILTIN_FRAME = "105001";
    /**
     * 该框架及其子框架下存在策略，无法删除
     *
     * @see String
     */
    public static final String STRATEGY_EXIST = "105002";

    /**
     * 交战方法[]被行动框架[]引用，无法删除
     *
     * @see String
     */
    public static final String METHOD_REFERENCE_EXIST = "105003";

    /**
     * 交战活动[]被行动框架[]引用，无法删除
     *
     * @see String
     */
    public static final String ACTION_REFERENCE_EXIST = "105004";

    /**
     * 占比输入之和不能大于100%
     *
     * @see String
     */
    public static final String PERCENTAGE_LARGER_THAN_100 = "105005";
    /**
     * 一个框架在一次红蓝对抗业务中只能运用一次
     *
     * @see String
     */
    public static final String RVB_FRAME_USED = "105006";
    /**
     * 阶段已下发
     *
     * @see String
     */
    public static final String STAGE_RELEASED = "105007";
    /**
     * 技法已下发
     *
     * @see String
     */
    public static final String TACTIC_RELEASED = "105008";
    /**
     * 单个步骤下战法不能重复
     *
     * @see String
     */
    public static final String TACTIC_DUPLICATED = "105009";
}
