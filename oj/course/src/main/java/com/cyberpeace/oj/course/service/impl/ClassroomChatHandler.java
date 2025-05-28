/**
 * 这段代码定义了一个名为 `ClassroomChatHandler` 的类，该类继承自 `ChatWebSocketHandler`。这个类主要用于处理与课堂相关的聊天信息。以下是代码的主要组成部分和功能分析： 
 * ### 类和依赖注入 
 * - **类声明**：`ClassroomChatHandler` 类是一个 `@Component` 类型的 Spring Bean，用于处理课堂相关的聊天信息。 
 * - **依赖注入**： 
 * - `classroomGroupInfoMapper`：用于访问 `ClassroomGroupInfo` 实体的数据。 
 * - `classroomMapper`：用于访问 `Classroom` 实体的数据。 
 * ### 方法实现 
 * 1. **declareMessageType** 
 * - **功能**：返回一个表示消息类型的整数。该方法覆盖了父类中的同名方法，并返回 `CHAT_TYPE.CLASSROOM.getCode()`。 
 * - **用途**：用于标识消息属于哪个类型（在这个上下文中是课堂类型）。 
 * 2. **getGroup** 
 * - **功能**：根据传入的 `businessId` 和 `ChatContent` 对象获取参与聊天的用户列表。 
 * - **逻辑**： 
 * - 从 `chatContent` 中提取 `classroomId`。 
 * - 使用 `classroomId` 查询 `Classroom` 实体。 
 * - 如果找不到对应的课堂，则返回空列表。 
 * - 使用 `classroomId` 查询与该课堂相关的群组信息。 
 * - 如果没有找到任何群组信息，则返回空列表。 
 * - 将群组信息中的用户ID解析为字符串列表。 
 * - 确保超级管理员和课堂创建者被包含在用户列表中。 
 * - 返回最终的用户ID列表。 
 * ### 日志记录 
 * - 使用 `lombok` 的 `@Slf4j` 注解来简化日志记录。例如，在找不到课堂信息时会记录错误日志。 
 * ### 辅助工具 
 * - **StreamApiUtils**：用于将群组信息转换为用户ID列表。 
 * - **JsonUtil**：用于将群组用户ID列表转换为JSON对象数组。 
 * ### 总结 
 * `ClassroomChatHandler` 类主要用于处理与课堂相关的聊天消息，包括获取参与聊天的用户列表。它通过依赖注入获取数据库操作接口，并使用这些接口查询和处理数据。该类的功能主要集中在从数据库中获取课堂信息和相关群组信息，以及生成参与聊天的用户列表。 
**/
package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.model.ws.ChatContent;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.service.impl.ChatWebSocketHandler;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.ojbase.constant.ResourceImportConstant;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课堂聊天处理程序
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ChatWebSocketHandler
 * @since 1.0.0
 */
@Slf4j
@Component
public class ClassroomChatHandler extends ChatWebSocketHandler {

	/**
	 * 课堂群信息Mapper
	 *
	 * @see ClassroomGroupInfoMapper
	 */
	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	/**
	 * 课堂Mapper
	 *
	 * @see ClassroomMapper
	 */
	@Autowired
	private ClassroomMapper classroomMapper;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 */
	@Override
	public Integer declareMessageType() {
		return CHAT_TYPE.CLASSROOM.getCode();
	}

	/**
	 * 获取群
	 *
	 * @param businessId  业务id
	 * @param chatContent 聊天内容
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see ChatContent
	 * @see List<String>
	 */
	@Override
	public List<String> getGroup(String businessId, ChatContent chatContent) {
		String classroomId = chatContent.getTo().getId();
		Classroom classroom = classroomMapper.selectById(classroomId);
		if (null == classroom) {
			log.error("Classroom information not found in group chat! classroom_id=[{}]", classroomId);
			return Lists.newArrayList();
		}
		
		List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);
		if (CollectionUtils.isEmpty(groupInfos)) {
			return Lists.newArrayList();
		}

		List<String> userIds = StreamApiUtils.flatMap(groupInfos,
				groupInfo -> JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(), String.class));
		if (!userIds.contains(ResourceImportConstant.SUPER_ADMIN_HASH)) {
			userIds.add(ResourceImportConstant.SUPER_ADMIN_HASH);
		}
		if (!userIds.contains(classroom.getCreateUserId())) {
			userIds.add(classroom.getCreateUserId());
		}

		return userIds;
	}
}
