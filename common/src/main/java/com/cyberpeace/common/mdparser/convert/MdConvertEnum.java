package com.cyberpeace.common.mdparser.convert;

/**
 * md convert 枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 该代码定义了一个泛型接口 `MdConvertEnum`，用于表示一种转换关系。以下是代码的简要分析： 
 * 1. **接口定义**： 
 * - 接口 `MdConvertEnum` 是一个泛型接口，包含两个类型参数 `K` 和 `V`。 
 * - 它定义了两种方法： 
 * - `to()`：返回类型为 `K`，表示某种转换的目标值。 
 * - `reTo()`：返回类型为 `V`，表示某种反向转换的目标值。 
 * 2. **用途**： 
 * - 该接口可以用于表示双向转换关系，例如从一种格式转换为另一种格式（如 Markdown 转 HTML 和反向转换）。 
 * 3. **注释**： 
 * - 注释清晰地描述了接口的功能，并标明了作者、版本、日期和依赖版本信息。 
 * 总结：这是一个通用的双向转换接口，适用于需要实现特定转换逻辑的场景。 
**/
public interface MdConvertEnum<K, V> {

    /**
     * 自
     *
     * @return {@code K }
     */
    K to();

    /**
     * re 更改为
     *
     * @return {@code V }
     */
    V reTo();

}

