package com.cyberpeace.space.service.context.frame;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.StreamApiUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * 动作框架层级
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/05
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceFrameLevel` 的类，用于表示某种动作框架的层级结构。以下是代码的主要功能和特点： 
 * 1. **类的功能**： 
 * - `SpaceFrameLevel` 类用于构建一个树状结构，每个节点包含资源 ID、名称和子节点列表。 
 * - 它提供了一个递归检查方法 `checkLevel`，用于验证树结构的正确性。 
 * 2. **主要属性**： 
 * - `resourceId`: 表示节点的唯一标识符。 
 * - `name`: 表示节点的名称。 
 * - `children`: 存储该节点的所有子节点，类型为 `List<SpaceFrameLevel>`。 
 * 3. **构造方法**： 
 * - 提供了两个构造方法： 
 * - 默认构造方法 `@NoArgsConstructor`。 
 * - 带参数的构造方法 `SpaceFrameLevel(String resourceId, String name)`，初始化 `resourceId` 和 `name`，并创建空的 `children` 列表。 
 * 4. **检查方法**： 
 * - `checkLevel(boolean ignoreFirstLevelId)`：递归检查树的合法性。 
 * - 如果 `ignoreFirstLevelId` 为 `false`，且 `resourceId` 为空，则抛出 `BusinessException` 异常，提示 ID 不能为空。 
 * - 使用 `StreamApiUtils.distinctByColumn` 确保子节点中没有重复的 `resourceId`。 
 * - 对每个子节点递归调用 `checkLevel` 方法。 
 * 5. **依赖的工具和异常**： 
 * - 使用了 `StringUtils`（来自 MyBatis-Plus）来判断字符串是否为空。 
 * - 使用了 `StreamApiUtils.distinctByColumn` 来去重。 
 * - 抛出自定义异常 `BusinessException`，并使用 `ErrorCode` 来标识具体的错误类型。 
 * 总结来说，这个类主要用于构建和验证一个树状结构的数据模型，确保数据的完整性和一致性。 
**/
@Getter
@Setter
@NoArgsConstructor
public class SpaceFrameLevel {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 子级
	 */
	private List<SpaceFrameLevel> children;

	public SpaceFrameLevel(String resourceId, String name){
		this.resourceId = resourceId;
		this.name = name;
		this.children = Lists.newArrayList();
	}

	/**
	 * 检查框架
	 *
	 * @param ignoreFirstLevelId 忽视第一级id
	 */
	public void checkLevel(boolean ignoreFirstLevelId) {
		if (null == this.children) {
			this.children = Lists.newArrayList();
		}
		if (!ignoreFirstLevelId && StringUtils.isBlank(this.resourceId)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		List<SpaceFrameLevel> childSet = StreamApiUtils.distinctByColumn(this.children,
				SpaceFrameLevel::getResourceId);
		if (childSet.size() != this.children.size()) {
			throw new BusinessException(ErrorCode.DUPLICATE_ID);
		}
		for (SpaceFrameLevel child : this.children) {
			child.checkLevel(false);
		}
	}

}

