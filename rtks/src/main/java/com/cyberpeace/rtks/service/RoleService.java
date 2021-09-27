package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.rtks.controller.req.DeleteReq;
import com.cyberpeace.rtks.dao.entity.Role;

public interface RoleService extends IService<Role> {

	/**
	 * 获取层级id下的岗位列表
	 * */
	List<Role> getRolesByLevelId(String levelId);

	/**
	 * 给定一个id的数组，返回level_id在该数组中的岗位id列表
	 * */
	List<String> getRoleIdsByLevelIds(List<String> ids);

	IPage<Role> listRoles(Page<Role> page, String keyword, String frameId);

	Role getOr404(String resourceId);

	void removeAll();

	void initDefaultFromPreset();

	/**修改知识点或技能点，重新查询对应的课程，重置相关岗位关联的知识点或技能点课程字符串
	 * @param id 关联的知识点或技能点id
	 * @param type 类型
	 * */
	void updateBatchByModifyRelatedIds(String id, String type);

	/**删除知识点或技能点，通过删除的ids,重置相关岗位关联的id,并且修改变化的知识点或技能点课程字符串。
	 * @param deleteReq 关联的rtks的id
	 * @param type 类型
	 * */
	void updateBatchByDeleteRelatedIds(DeleteReq deleteReq, String type);
}
