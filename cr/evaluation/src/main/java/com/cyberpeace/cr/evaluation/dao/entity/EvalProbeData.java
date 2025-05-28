/**
 * 这段代码定义了一个名为 `EvalProbeData` 的实体类，用于表示与评估探针数据相关的数据库表。以下是对其主要部分的分析： 
 * 1. **包声明**： 
 * - `package com.cyberpeace.cr.evaluation.dao.entity;` 
 * - 声明了这个类所在的包。 
 * 2. **导入的依赖**： 
 * - 导入了一些必要的工具类和注解，例如 MyBatis Plus 的注解和 Lombok 的注解。 
 * 3. **继承自基类**： 
 * - `public class EvalProbeData extends BaseTable` 
 * - 表示该类继承了一个名为 `BaseTable` 的基类，这通常是一个抽象类或接口，用于提供一些通用的功能或属性。 
 * 4. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 提供，用于自动生成所有字段的 getter 和 setter 方法。 
 * 5. **MyBatis Plus 注解**： 
 * - `@TableName("cr_eval_probe_data")` 注解指定了该类对应的数据库表名。 
 * - `@TableId(type = IdType.AUTO)` 注解用于指定主键策略为自动增长。 
 * 6. **字段定义**： 
 * - 定义了一些私有字段，这些字段映射到数据库表中的列： 
 * - `private Integer id;`：主键字段，自动增长。 
 * - `private String sceneId;`：场景ID。 
 * - `private String deviceId;`：设备ID。 
 * - `private String probeId;`：探针ID。 
 * - `private Date collectionTime;`：采集时间。 
 * - `private String deviceIp;`：设备IP地址。 
 * - `private String content;`：内容。 
 * 7. **注释**： 
 * - 有一段被注释掉的代码，定义了一个枚举类型 `Type`，可能用于标识不同类型的数据，但目前没有使用。 
 * 总结： 
 * - `EvalProbeData` 类是用于操作 `cr_eval_probe_data` 表的实体类。 
 * - 使用了 MyBatis Plus 和 Lombok 来简化代码。 
 * - 主要字段包括主键、场景ID、设备ID、探针ID、采集时间、设备IP地址和内容。 
 * - 目前没有使用枚举类型 `Type`，这部分代码被注释掉了。 
**/
package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName("cr_eval_probe_data")
public class EvalProbeData extends BaseTable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String sceneId;

    private String deviceId;

    private String probeId;

    private Date collectionTime;

    private String deviceIp;

    private String content;

    // todo 识别采集时间需要
//    public enum Type implements GenericEnum {
//        BASE(Arrays.asList("001", "002", "004", "005"), "基础类型");
//
//        @Getter
//        private final List<String> code;
//
//        @Getter
//        private final String desc;
//
//        Type(List<String> codes, String desc) {
//            this.code = codes;
//            this.desc = desc;
//        }
//    }

}
