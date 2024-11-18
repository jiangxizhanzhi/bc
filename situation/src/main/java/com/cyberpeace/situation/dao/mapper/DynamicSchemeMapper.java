package com.cyberpeace.situation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.dao.entity.Defense;
import com.cyberpeace.situation.dao.entity.DynamicScheme;
import com.cyberpeace.situation.dao.entity.ToolVuln;
import com.cyberpeace.situation.dao.entity.Vuln;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DynamicSchemeMapper extends BaseMapper<DynamicScheme> {

	/**
	 * 获取动效方案名称列表
	 * @param sceneId 场景编号
	 * @return 名称列表
	 */
	List<DynamicSchemeNameRes> getAllNamesBySceneId(String sceneId);

	/**
	 * 删除场景下动效方案数据
	 * @param sceneId 场景编号
	 */
	void deleteBySceneId(String sceneId);

	/**
	 * 根据动效名称查询动效方案
	 * @param name 动效方案名称
	 * @param sceneId 场景id
	 * @return 动效方案数据
	 */
	DynamicScheme selectByName(@Param("name") String name, @Param("sceneId") String sceneId);

	/**
	 * 根据资源编号获取动效方案数据
	 * @param resourceId 资源编号
	 * @return 动效方案数据
	 */
	DynamicScheme selectByResourceId(String resourceId);

	/**
	 * 查询漏洞下拉列表
	 * @return 漏洞信息
	 */
    List<Vuln> queryLeak();

	/**
	 * 查询工具信息
	 * @param tool 工具id
	 * @param leak 漏洞id
	 * @return 工具信息
	 */
	List<ToolVuln> toolVulnById(@Param("tool") String tool, @Param("leak") String leak, @Param("type") Integer type);

	/**
	 * 新增防御记录
	 * @param defense defense
	 */
	void addDefense(Defense defense);

	/**
	 * 防御记录查询
	 * @param sceneId sceneId
	 * @return Defense
	 */
	List<Defense> queryDefenseBySceneId(@Param("sceneId") String sceneId, @Param("type") Integer type);

	/**
	 * 清空防御记录
	 * @param sceneId sceneId
	 */
	void deleteDefenseBySceneId(@Param("sceneId") String sceneId);

	/**
	 * 获取工具信息
	 * @param vulnId vulnId
	 * @param toolType toolType
	 */
	List<ToolVuln> queryToolByVulnId(@Param("vulnId") String vulnId, @Param("toolType") Integer toolType);

	/**
	 * 获取漏洞信息
	 * @param vulns vulns
	 */
    List<Vuln> queryVulnTop(List<String> vulns);

	/**
	 * 获取攻防记录信息
	 * @param sceneId sceneId
	 * @param nodeId nodeId
	 * @param type type
	 */
	List<Defense> queryDefenseByNodeId(String sceneId, String nodeId, Integer type);

	/**
	 * 根据id查询漏洞信息
	 * @param vulns vulns
	 */
	List<Vuln> queryLeakByIds(List<String> vulns);

	/**
	 * 根据id查询工具
	 * @param tool tool
	 */
	ToolVuln queryToolById(String tool);

	/**
	 * 根据id查询漏洞
	 * @param leak leak
	 */
	Vuln queryById(String leak);
}
