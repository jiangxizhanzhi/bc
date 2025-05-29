package com.cyberpeace.common.model.room;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 仪表板好友
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `DashboardFriend` 的 Java 类，用于表示仪表板中的好友信息。以下是代码的主要分析和总结： 
 * 1. **类与接口**： 
 * - `DashboardFriend` 类实现了 `Serializable` 接口，表明它可以被序列化。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter` 来自动生成 getter 和 setter 方法。 
 * 2. **字段说明**： 
 * - `groupname`：表示分组名称，用 `@ApiModelProperty` 注解标记，用于生成 API 文档。 
 * - `id`：表示编号，同样用 `@ApiModelProperty` 注解。 
 * - `list`：存储 `DashboardUser` 对象的列表，表示好友列表。 
 * 3. **枚举类**： 
 * - 定义了一个嵌套枚举类 `GROUP_NAME`，实现了 `GenericEnum` 和 `BaseI18n` 接口。 
 * - 枚举值 `FRIEND_LIST` 表示“好友列表”，包含三个字段：`code`（代号）、`chinese`（中文描述）和 `english`（英文描述）。 
 * - 提供了 `getDesc()` 方法，返回描述信息（调用了 `getMsg()` 方法）。 
 * 4. **国际化支持**： 
 * - 枚举类支持多语言描述（中文和英文），符合国际化设计。 
 * 5. **用途**： 
 * - 该类主要用于表示仪表板中的好友信息，包括分组名称、唯一标识符和好友列表。 
 * 总结：这是一个简单的数据传输对象（DTO），结合了 Lombok、Swagger 注解和国际化功能，适用于前后端交互场景。 
**/
@Getter
@Setter
public class DashboardFriend implements Serializable {

	/**
	 * 组名
	 */
	@ApiModelProperty("分组名称")
	private String groupname;

	/**
	 * id
	 */
	@ApiModelProperty("编号")
	private Integer id;

	/**
	 * 列表
	 */
	@ApiModelProperty("好友列表")
	private List<DashboardUser> list;

	/**
	 * 群名称
	 *
	 * @author SN
	 * @date 2024/11/08
	 */
	public enum GROUP_NAME implements GenericEnum, BaseI18n {

		/**
		 * 好友列表
		 */
		FRIEND_LIST(1, "好友列表", "Friends List");

		/**
		 * 代号
		 */
		@Getter
		private final Integer code;

		/**
		 * 中文
		 */
		@Getter
		private final String chinese;

		/**
		 * 英语
		 */
		@Getter
		private final String english;

		@Override
		public String getDesc() {
			return getMsg();
		}

		GROUP_NAME(Integer code, String desc, String enDesc) {
			this.code = code;
			this.chinese = desc;
			this.english = enDesc;
		}
	}
}

