package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceFilterReq;
import com.cyberpeace.situation.controller.res.SituationPublishInstancePageRes;
import com.cyberpeace.situation.dao.entity.SituationPublishInstance;

/**
 * 大屏实例映射器
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Mapper
public interface SituationPublishInstanceMapper extends BaseMapper<SituationPublishInstance> {

	/**
	 * 分页查询
	 *
	 * @param page                              分页条件
	 * @param situationPublishInstanceFilterReq 查询条件
	 * @return {@code IPage<SituationPublishInstancePageRes>}
	 */
	IPage<SituationPublishInstancePageRes> getSituationPublishInstancePage(@Param("page") Page page,
			@Param("situationPublishInstanceFilterReq") SituationPublishInstanceFilterReq situationPublishInstanceFilterReq);

	/**
	 * 通过大屏id得到资源id
	 *
	 * @param resourceIds 资源id
	 * @return {@code List<String>}
	 */
	List<String> getAttributeIds(@Param("resourceIds") List<String> resourceIds);

	/**
	 * 根据任务id和科目id选择xd实例
	 *
	 * @param taskId    任务id
	 * @param subjectId 科目id
	 * @return {@code SituationPublishInstance}
	 */
	SituationPublishInstance selectXdDashboardBySubjectId(@Param("taskId") String taskId,
			@Param("subjectId") String subjectId);

	/**
	 * 根据可视化方案id选择xd实例
	 *
	 * @param schemeId 可视化方案id
	 * @return {@code String}
	 */
	String selectXdInstanceBySchemeId(@Param("schemeId") String schemeId);

	/**
	 * 根据模板id选择实例
	 *
	 * @param templateId 模板id
	 * @return {@code SituationPublishInstance}
	 */
	SituationPublishInstance selectByTemplateId(@Param("templateId") String templateId);

	/**
	 * 获取大屏层级
	 *
	 * @param dashboardId 大屏id
	 * @return {@code Integer}
	 */
	Integer getDashboardLevel(@Param("dashboardId") String dashboardId);

	/**
	 * 根据大屏id获取参数配置
	 *
	 * @param dashboardId 大屏id
	 * @return {@code String}
	 */
	String getParameterConfigByDashboardId(@Param("dashboardId") String dashboardId);

	/**
	 * 根据任务id和科目id选择大屏列表
	 *
	 * @param taskId    任务id
	 * @param subjectId 科目id
	 * @return {@code List<SituationPublishInstance>}
	 */
	List<SituationPublishInstance> selectDashboardBySubjectId(@Param("taskId") String taskId,
			@Param("subjectId") String subjectId);

	/**
	 * 获取综合大屏
	 *
	 * @return {@code List<SituationPublishInstance>}
	 */
	List<SituationPublishInstance> selectSynthesizeDashboard();

	/**
	 * 根据可视化方案id选择xd实例
	 *
	 * @param schemeId 可视化方案id
	 * @return {@code SituationPublishInstance}
	 */
	SituationPublishInstance selectXdDashboardBySchemeId(@Param("schemeId") String schemeId);

	List<String> getDashboardIdBySubjectIdAndTaskId(@Param("subjectId") String subjectId, @Param("taskId") String taskId);

	String getTemplateId(@Param("resourceId") String resourceId);

}
