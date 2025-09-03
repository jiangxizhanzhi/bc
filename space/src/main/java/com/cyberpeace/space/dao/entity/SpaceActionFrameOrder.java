package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import lombok.Data;

import java.util.Map;

/**
 * 空间行动框架次序
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionFrameOrder` 的实体类，用于表示与数据库表 `space_action_frame_order` 对应的数据模型。以下是代码的简要分析： 
 * 1. **包名和依赖**： 
 * - 该类位于 `com.cyberpeace.space.dao.entity` 包中。 
 * - 使用了 MyBatis-Plus 提供的注解 (`@TableName`, `@TableId`) 来映射数据库表和字段。 
 * - 引入了 `BaseTable` 基类，可能包含通用的字段或方法。 
 * 2. **类的功能**： 
 * - 这是一个数据传输对象（DTO），用于存储空间行动框架次序的相关信息。 
 * - 继承了 `BaseTable`，表明它可能具有通用的时间戳或其他公共属性。 
 * 3. **主要字段**： 
 * - `resourceId`: 主键，使用 UUID 自动分配。 
 * - `applicationId`, `rvbId`, `frameName`, `step`, `tacticId`, `userId`, `userName`, `sender`, `senderName`: 描述空间行动框架的具体细节。 
 * - `status`: 表示当前状态的整数值。 
 * 4. **方法**： 
 * - `entity2Bo`: 将当前实体转换为业务对象 `SpaceActionFrameOrderBo`，通过工具类 `BeanUtils` 实现深拷贝。 
 * 5. **注解**： 
 * - `@Data`: Lombok 注解，自动生成 getter、setter、toString 等方法。 
 * - `@TableName`: 指定对应的数据库表名。 
 * - `@TableId`: 定义主键类型为 UUID。 
 * 6. **用途**： 
 * - 该类主要用于数据库操作和业务逻辑之间的数据传递，支持 MyBatis-Plus 的 ORM 功能。 
 * 总结：这是一个典型的基于 MyBatis-Plus 的实体类，设计清晰，功能单一，便于在持久层和业务层之间进行数据交互。 
**/
@Data
@TableName("space_action_frame_order")
public class SpaceActionFrameOrder extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String resourceId;

	private String applicationId;

	private String rvbId;

	private String frameName;

	private String step;

	private String tacticId;

	private Integer tacticType;

	private String userId;

	private String userName;

	private String sender;

	private String senderName;

	private Integer status;

	private String nodes;

	public SpaceActionFrameOrderBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameOrderBo bo = BeanUtils.copy(this, SpaceActionFrameOrderBo.class);
		return bo;
	}
}

