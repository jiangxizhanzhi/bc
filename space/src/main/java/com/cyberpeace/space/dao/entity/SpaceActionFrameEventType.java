package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameEventTypeBo;
import lombok.Data;

import java.util.Map;


/**
 * 空间行动框架事件类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 1. **类的功能** 
 * - `SpaceActionFrameEventType` 是一个实体类，用于表示空间行动框架事件类型的数据库表映射。 
 * - 它继承了 `SpaceBaseTable`，表明它是一个基于数据库表的实体类，并且可能包含一些通用字段（如主键、创建时间等）。 
 * #### 2. **关键注解** 
 * - `@Data`：Lombok 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - `@TableName("space_action_frame_event_type")`：MyBatis-Plus 注解，指定该类对应的数据库表名为 `space_action_frame_event_type`。 
 * #### 3. **字段** 
 * - `keyWords`: 字段存储关键字信息。 
 * - `keyWordsPrimary`: 字段存储主关键字信息。 
 * #### 4. **方法** 
 * - `entity2Bo(Map<String, IdAndName> relatedMap)`： 
 * - 将当前实体对象转换为 `SpaceActionFrameEventTypeBo` 对象。 
 * - 使用工具类 `BeanUtils.copy()` 进行对象的属性复制。 
 * - 返回值是目标业务对象 `SpaceActionFrameEventTypeBo`。 
 * #### 5. **依赖类** 
 * - `SpaceBaseTable`：基础表类，可能定义了一些通用字段和逻辑。 
 * - `IdAndName`：一个模型类，可能用于存储 ID 和名称的组合。 
 * - `SpaceActionFrameEventTypeBo`：业务对象类，用于表示业务逻辑中的数据结构。 
 * - `BeanUtils`：工具类，提供对象属性拷贝功能。 
 * #### 6. **总结** 
 * - 该类主要用于数据库表 `space_action_frame_event_type` 的实体映射。 
 * - 提供了一个方法将实体对象转换为业务对象，便于在业务层使用。 
 * - 类的设计体现了分层架构的思想，通过工具类实现对象转换，保持了代码的可读性和可维护性。 
**/
@Data
@TableName("space_action_frame_event_type")
public class SpaceActionFrameEventType extends SpaceBaseTable {
	private String keyWords;

	private String keyWordsPrimary;
	public SpaceActionFrameEventTypeBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameEventTypeBo bo = BeanUtils.copy(this, SpaceActionFrameEventTypeBo.class);
		return bo;
    }
}
    
