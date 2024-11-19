package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.bo.ClassroomGroupArchiveBo;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;

import java.util.List;

/**
 * 课堂组信息Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see IService
 * @since 1.0.0
 */
public interface ClassroomGroupInfoService extends IService<ClassroomGroupInfo> {

	/**
	 * 查询课堂场景 前台使用后台直接返回为空
	 *
	 * @param lessonId
	 * @param roomId      房间id
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see ClassroomGroupInfo
	 */
	ClassroomGroupInfo selectByLessonAndUser(String lessonId, String roomId, AuthContext authContext);

	/**
	 * 查询课堂分组信息
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see List<GroupInfoBo>
	 */
	List<GroupInfoBo> getGroupInfoByRoom(String classroomId, AuthContext authContext);

	/**
	 * 更新课堂分组信息
	 *
	 * @param classroomId
	 * @param groupCount
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<GroupInfoBo>
	 */
	List<GroupInfoBo> updateGroupInfo(String classroomId, Integer groupCount);

	/**
	 * 课堂分组模板数据保存
	 *
	 * @param classroomId
	 * @param name
	 * @param data
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<GroupInfoBo>
	 * @see Integer
	 */
	Integer saveGroupInfo(String classroomId, String name, List<GroupInfoBo> data);

	/**
	 * 课堂分组模板删除
	 *
	 * @param classroomId
	 * @param groupArchiveId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	Integer deleteClassroomArchive(String classroomId, String groupArchiveId);

	/**
	 * 使用课堂分组模板
	 *
	 * @param classroomId
	 * @param groupArchiveId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<GroupInfoBo>
	 */
	List<GroupInfoBo> userGroupInfo(String classroomId, String groupArchiveId);

	/**
	 * 设置场景延时
	 *
	 * @param classroomId
	 * @param end
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see Boolean
	 */
	Boolean setGroupSceneDelay(String classroomId, Boolean end);

	/**
	 * 获取课堂场景延时状态 分组中存在课堂场景延时场景状态即为true
	 *
	 * @param classroomId
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean getGroupSceneDelayStatus(String classroomId);

	/**
	 * 查询分组模板列表
	 *
	 * @param classroomId
	 * @return
	 * @throws
	 * @see String
	 * @see List<ClassroomGroupArchiveBo>
	 */
	List<ClassroomGroupArchiveBo> selectGroupArchive(String classroomId);

	/**
	 * 移动用户
	 *
	 * @param classroomId
	 * @param fromGroupId
	 * @param toGroupId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	Boolean moveGroupUser(String classroomId, String fromGroupId, String toGroupId, String userId);

	/**
	 * 初始化分组数据
	 *
	 * @param classroomId
	 * @param courseLesson
	 * @param userIds
	 * @param groupNo
	 * @return
	 * @throws
	 * @see String
	 * @see CourseLesson
	 * @see List<String>
	 * @see Integer
	 * @see List<ClassroomGroupInfo>
	 */
	List<ClassroomGroupInfo> initClassroomGroupInfo(String classroomId, CourseLesson courseLesson,
			List<String> userIds, Integer groupNo);
}
