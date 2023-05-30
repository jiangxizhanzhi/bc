package com.cyberpeace.cr.crbase.utils;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.cyberpeace.common.model.scene.AssetProperty;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.mission.MissionOption;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cs.service.context.IdNameType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 一些靶场工具
 */
public class CrCommonUtil {

	private static final String NULL = "null";

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

	/**
	 * 获取雷达图初始点数列表
	 *
	 * @return {@code List<Integer>}
	 */
	public static List<Integer> getInitPoints() {
		List<Integer> points = Lists.newArrayList();
		int skillPointsSize = CrCommonConstants.SKILL_POINTS_SIZE;
		for (int i = 0; i < skillPointsSize; i++) {
			points.add(0);
		}
		return points;
	}

	/**
	 * 技能点数加和
	 *
	 * @param sum    总和
	 * @param addend 加数
	 */
	public static void integratePoints(List<Integer> sum, List<Integer> addend) {
		if (sum.size() != addend.size()) {
			return;
		}
		for (int i = 0; i < sum.size(); i++) {
			sum.set(i, sum.get(i) + addend.get(i));
		}
	}

	/**
	 * 计算雷达图
	 *
	 * @param all 所有
	 * @param get 获取
	 * @return {@code List<Integer>}
	 */
	public static List<Integer> calculateRadar(List<Integer> all, List<Integer> get) {
		List<Integer> res = Lists.newArrayList();
		for (int i = 0; i < all.size(); i++) {
			res.add(MathUtils.roundDivide(get.get(i) * 100, all.get(i)));
		}
		return res;
	}

	/**
	 * 解析选项
	 *
	 * @param optionString 选项字符串
	 * @return {@code List<MissionOption>}
	 */
	public static List<MissionOption> parseOption(String optionString) {
		if (StringUtils.isBlank(optionString) || optionString.equals(NULL)) {
			return Lists.newArrayList();
		}
		List<MissionOption> res = JsonUtil.toJsonObjectArray(optionString, MissionOption.class);
		return res;
	}

	/**
	 * 给多选题的答案排序
	 *
	 * @param answerString 回答字符串
	 * @return {@code String}
	 */
	public static String handleMultiAnswer(String answerString) {
		List<String> characterList = JsonUtil.toJsonObjectArray(answerString, String.class);
		// natural order
		characterList.sort(Comparator.naturalOrder());
		return String.join(",", characterList);
	}

	/**
	 * 获取操作系统列表
	 * 去重
	 *
	 * @param assetList 资产列表
	 * @return {@code List<IdNameType>}
	 */
	public static List<IdNameType> getOsListFromAssets(List<JSONObject> assetList) {
		Set<String> osTypeSet = Sets.newHashSet();
		List<IdNameType> res = Lists.newArrayList();
		for (JSONObject jsonObject : assetList) {
			String osType = jsonObject.getString(AssetProperty.SYSTEM_TYPE);
			String osName = jsonObject.getString(AssetProperty.SYSTEM_TYPE_NAME);
			if (osTypeSet.contains(osType)) {
				continue;
			}
			IdNameType idNameType = new IdNameType();
			idNameType.setId(osType);
			idNameType.setName(osName);

			osTypeSet.add(osType);
			res.add(idNameType);
		}
		return res;
	}

	/**
	 * 列表通过角色任务配置获取动态任务id
	 *
	 * @param missionSchemeJson 任务计划json
	 * @return {@code List<String>}
	 */
	public static List<String> getDynamicMissionIdsByRoleMissionScheme(String missionSchemeJson) {
		List<String> res = Lists.newArrayList();
		List<MissionStageScheme> stages = JsonUtil.toJsonObjectArray(missionSchemeJson,
				MissionStageScheme.class);
		if (stages.isEmpty()) {
			return res;
		}
		for (MissionStageScheme stage : stages) {
			List<String> dynamicIds = stage.getDynamic();
			if (CollectionUtils.isNotEmpty(dynamicIds)) {
				res.addAll(dynamicIds);
			}
		}
		return res;
	}

}
