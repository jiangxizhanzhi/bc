package com.cyberpeace.cr.crbase.utils;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.google.common.collect.Lists;

/**
 * crcommon工具
 * 一些靶场工具
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class CrCommonUtil {

	/**
	 * 控制最终存入数据库的 role_config 和 missions_scheme json 只包含哪些字段
	 */
	public static <T> String roleConfig2DbJson(T roleConfig) {
		SimplePropertyPreFilter missionFilter = new SimplePropertyPreFilter(
				CrRoleConfigMission.class, "resource_id");
		SimplePropertyPreFilter schemeFilter = new SimplePropertyPreFilter(
				CrRoleConfigSchemeStage.class, "stage", "missions");
		SimplePropertyPreFilter roleFilter = new SimplePropertyPreFilter(CrRoleConfig.class, "name",
				"asset_nodes", "visible_nodes", "missions_scheme");
		SerializeConfig config = new SerializeConfig();
		config.setPropertyNamingStrategy(PropertyNamingStrategy.SnakeCase);
		return JSONObject.toJSONString(roleConfig, config,
				new SimplePropertyPreFilter[] { missionFilter, schemeFilter, roleFilter });
	}

	/**
	 * 从 roleConfig 读取 任务模板id 列表
	 */
	public static List<String> getMissionTemplateIds(List<CrRoleConfig> roles) {
		List<String> missionTemplateIds = Lists.newArrayList();
		roles.forEach(
				role -> missionTemplateIds.addAll(getMissionTemplateIds(role.getMissionsScheme())));
		return missionTemplateIds;

	}

	/**
	 * 从 missions scheme 读取 任务模板id 列表
	 * @param missionsScheme
	 * @return
	 */
	public static List<String> getMissionTemplateIds(CrRoleConfigMissionsScheme missionsScheme) {
		List<String> missionTemplateIds = Lists.newArrayList();
		missionsScheme.getSchemeData().forEach(stage -> stage.getMissions()
				.forEach(mission -> missionTemplateIds.add(mission.getResourceId())));
		return missionTemplateIds;
	}

	/**
	 * 校验训练 tag
	 * @param tags
	 * @return
	 */
	public static String handleTrainingTag(List<String> tags) {
		CrCommonValidator.validateSize(tags, CrCommonConstants.MIN_TAG_COUNT,
				CrCommonConstants.MAX_TAG_COUNT, CrBusinessErrorCode.INVALID_TAGS_NUM);

		tags.forEach(
				tag -> CrCommonValidator.validateNameLen(tag, CrBusinessErrorCode.ILLEGAL_TAG_LEN));
		CrCommonValidator.checkStringListDuplicate(tags, CrBusinessErrorCode.TAG_DUPLICATE);

		return JsonUtil.arrayToString(tags);
	}

	/**
	 * 原地操作，去掉两个列表中相交的元素
	 * @param l1
	 * @param l2
	 * @param <T>
	 */
	public static <T> void differ2List(List<T> l1, List<T> l2) {
		if (l1 == null || l1.isEmpty() || l2 == null || l2.isEmpty()) {
			return;
		}
		int i = 0;
		while (i < l1.size()) {
			int j = l2.indexOf(l1.get(i));
			if (j > -1) {
				l1.remove(i);
				l2.remove(j);
			} else {
				i++;
			}
		}
	}
}
