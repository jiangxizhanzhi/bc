/**
 * 这段代码定义了一个名为 `EvalProbeDataMapper` 的接口，该接口继承自 `BaseMapper<EvalProbeData>`。下面是对这段代码的详细分析： 
 * 1. **包声明**： 
 * ```java 
 * package com.cyberpeace.cr.evaluation.dao.mapper; 
 * ``` 
 * - 这行代码指定了该类所在的包路径。 
 * 2. **导入语句**： 
 * ```java 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.cyberpeace.cr.evaluation.dao.entity.EvalProbeData; 
 * ``` 
 * - `com.baomidou.mybatisplus.core.mapper.BaseMapper`：引入了 MyBatis-Plus 提供的 `BaseMapper` 接口，用于简化数据操作。 
 * - `com.cyberpeace.cr.evaluation.dao.entity.EvalProbeData`：引入了 `EvalProbeData` 类，该类表示数据库中的实体对象。 
 * 3. **接口定义**： 
 * ```java 
 * public interface EvalProbeDataMapper extends BaseMapper<EvalProbeData> { 
 * } 
 * ``` 
 * - `public interface EvalProbeDataMapper`：定义了一个公共接口 `EvalProbeDataMapper`。 
 * - `extends BaseMapper<EvalProbeData>`：继承自 `BaseMapper<EvalProbeData>`，使得该接口具备了对 `EvalProbeData` 实体进行 CRUD（创建、读取、更新、删除）操作的能力。 
 * 4. **注释**： 
 * ```java 
 * /** 
 * * 评估探针数据Mapper 
 * * 
 * * @author SN 
 * * @date 2024/11/08 
 * */ 
 * ``` 
 * - 注释部分说明了这个接口的功能是用于处理“评估探针数据”，并且标注了作者和日期。 
 * ### 总结 
 * 这段代码定义了一个用于处理 `EvalProbeData` 实体数据的 MyBatis-Plus Mapper 接口。通过继承 `BaseMapper`，它自动获得了对 `EvalProbeData` 实体的基本增删改查功能。这个接口可以方便地在业务逻辑层中使用，以实现对评估探针数据的数据库操作。 
**/
package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.EvalProbeData;

/**
 * 评估探针数据Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalProbeDataMapper extends BaseMapper<EvalProbeData> {
}
