package com.cyberpeace.cr.crbase.service.context.resource;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * 子关常数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
interface ZiguanConstants {

	/**
	 * 判断类型
	 *
	 * @see Integer
	 */
	Integer JUDGEMENT_TYPE = 0;

	/**
	 * 单选型
	 *
	 * @see Integer
	 */
	Integer SINGLE_CHOICE_TYPE = 1;

	/**
	 * 多选型
	 *
	 * @see Integer
	 */
	Integer MULTI_CHOICE_TYPE = 2;

	/**
	 * 任务类型映射
	 *
	 * @see Map<String, Integer>
	 */
	Map<String, Integer> MISSION_TYPE_MAPPING = new HashMap<String, Integer>() {

		{
			put("判断", JUDGEMENT_TYPE);
			put("单选", SINGLE_CHOICE_TYPE);
			put("多选", MULTI_CHOICE_TYPE);
			put("填空", 3);
			put("问答", 4);
			put("攻击", 5);
			put("防御", 6);
			put("文件", 7);
		}
	};

	/**
	 * 正确
	 *
	 * @see String
	 */
	String CORRECT = "A";

	/**
	 * 错
	 *
	 * @see String
	 */
	String WRONG = "B";

	/**
	 * 判断答案映射
	 *
	 * @see Map<String, String>
	 */
	Map<String, String> JUDGEMENT_ANSWER_MAPPING = new HashMap<String, String>() {

		{
			put("正确", CORRECT);
			put("错误", WRONG);
		}
	};

	/**
	 * 单训练
	 *
	 * @see Integer
	 */
	Integer SINGLE_TRAIN = 1;

	/**
	 * 团队训练
	 *
	 * @see Integer
	 */
	Integer TEAM_TRAIN = 3;

	/**
	 * RVB
	 *
	 * @see Integer
	 */
	Integer RVB = 4;

	/**
	 * CE 类型映射
	 *
	 * @see Map<String, Integer>
	 */
	Map<String, Integer> CE_TYPE_MAPPING = new HashMap<String, Integer>() {

		{
			put("单兵训练", SINGLE_TRAIN);
			put("团队训练", TEAM_TRAIN);
			put("红蓝对抗", RVB);
		}
	};

	/**
	 * 模板名称
	 *
	 * @see String
	 */
	String TEMPLATE_NAME = "name";

	/**
	 * 描述
	 *
	 * @see String
	 */
	String DESCRIPTION = "description";

	/**
	 * 类型
	 *
	 * @see String
	 */
	String TYPE = "type";

	/**
	 * 类别
	 *
	 * @see String
	 */
	String CATEGORY = "category";

	/**
	 * 难度
	 *
	 * @see String
	 */
	String DIFFICULTY = "difficulty";

	/**
	 * 持续时间
	 *
	 * @see String
	 */
	String DURATION_TIME = "duration_time";

	/**
	 * 预技能
	 *
	 * @see String
	 */
	String PRE_SKILL = "pre_skill";

	/**
	 * 训练技能
	 *
	 * @see String
	 */
	String TRAIN_SKILL = "train_skill";

	/**
	 * 角色信息
	 *
	 * @see String
	 */
	String ROLE_INFO = "role_info";

	/**
	 * 模板基本信息字段
	 *
	 * @see Map<String, String>
	 */
	Map<String, String> TEMPLATE_BASE_INFO_FIELDS = new HashMap<String, String>() {

		{
			put("场景名称", TEMPLATE_NAME);
			put("场景描述", DESCRIPTION);
			put("场景类型", TYPE);
			put("训练类型", CATEGORY);
			put("训练难度", DIFFICULTY);
			put("建议时间", DURATION_TIME);
			put("LOGO", "logo");
			put("前置技能", PRE_SKILL);
			put("训练目标", TRAIN_SKILL);
			put("场景角色", ROLE_INFO);
			put("GIS坐标", "GIS_coordinate");
			put("APT组织", "APT_org");

		}
	};

	/**
	 * 任务名称
	 *
	 * @see String
	 */
	String MISSION_TITLE = "title";

	/**
	 * 得分
	 *
	 * @see String
	 */
	String SCORE = "score";

	/**
	 * 技能点
	 *
	 * @see String
	 */
	String SKILL_POINTS = "skillPoints";

	/**
	 * 选择
	 *
	 * @see String
	 */
	String OPTION = "option";

	/**
	 * 答案
	 *
	 * @see String
	 */
	String ANSWER = "answer";

	/**
	 * 任务模板字段
	 *
	 * @see Map<String, String>
	 */
	Map<String, String> MISSION_TEMPLATE_FIELDS = new HashMap<String, String>() {

		{
			put("任务名称", MISSION_TITLE);
			put("简述", DESCRIPTION);
			put("任务分类", CATEGORY);
			put("分数", SCORE);
			put("技能点", SKILL_POINTS);
			put("题目类型", TYPE);
			put("题目选项", OPTION);
			put("答案", ANSWER);
		}
	};

	/**
	 * 默认任务类别
	 *
	 * @see Map<String, String>
	 */
	Map<String, String> DEFAULT_MISSION_CATEGORIES = new HashMap<String, String>() {

		{
			put("情报侦查研判", "05b739fc686a073756399f79c34039b5");
			put("密码破解", "1102f907c7650ad541c381f59c700f5f");
			put("终端渗透控制", "2eec84346538e972a962b165e499f52d");
			put("威胁发现和检测", "2f4f15de59cd2dbadc5d8a582e9f6849");
			put("内网渗透控制", "3b6e90254227833303d908336b105ade");
			put("网络应用控制破解", "446bbba739678a4e6e103e7d214d55aa");
			put("网络侦查", "6753aa8cad6fa9760308871e707ea033");
			put("反侦查", "8568759a4d514ff730ed6eb1721a2f22");
			put("漏洞挖掘利用", "a2e58506b8d588d7e1cef85f7350728a");
			put("应急响应", "a7914af4e20435cb7ad7b8b4ae0bac6a");
			put("网站渗透控制", "b27501c31132b288df1bef259e2625e6");
			put("系统加固", "bd43486dee157d5e5106d547801e2ea2");
			put("数据分析", "c4173e927825d4ff52b112e268fb01ad");
			put("技术反制", "d0cf5fc50285e1b64c1dfc3adb9ba39d");
			put("隐蔽能力", "dff8a9b76f5536babae3d4c22943004d");
		}
	};

	/**
	 * 可见节点
	 *
	 * @see String
	 */
	String VISIBLE_NODES = "visibleNodes";

	/**
	 * 资产节点
	 *
	 * @see String
	 */
	String ASSET_NODES = "assetNodes";

	/**
	 * 角色节点类别
	 *
	 * @see Map<String, String>
	 */
	Map<String, String> ROLE_NODES_CATEGORY = new HashMap<String, String>() {

		{
			put("战争迷雾", VISIBLE_NODES);
			put("用户资产", ASSET_NODES);
		}
	};

	/**
	 * 节点 Col
	 *
	 * @see String
	 */
	String NODE_COLL = "节点集合";

	/**
	 * Mission Col
	 *
	 * @see String
	 */
	String MISSION_COLL = "任务集合";

	/**
	 * 难度选择
	 *
	 * @see Map<String, Integer>
	 */
	Map<String, Integer> DIFFICULTY_CHOICE = new HashMap<String, Integer>() {

		{
			put("低", 1);
			put("中", 2);
			put("高", 3);
		}
	};

	/**
	 * 业务类别
	 *
	 * @see Map<String, Integer>
	 */
	Map<String, Integer> BUSINESS_CATEGORIES = new HashMap<String, Integer>() {

		{
			put("渗透测试", 1);
			put("运维防护", 2);
			put("系统仿真", 3);
			put("综合场景", 4);
			put("其他", 5);

		}
	};
}

/**
 * 角色组名称
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
interface RoleGroupNames {

	/**
	 * 学员
	 *
	 * @see String
	 */
	String STUDENT = "学员组";

	/**
	 * 红
	 *
	 * @see String
	 */
	String RED = "红方";

	/**
	 * 蓝
	 *
	 * @see String
	 */
	String BLUE = "蓝方";
}

/**
 * 资管标准场景资源模板解析
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@Slf4j
public class MDParser {

	/**
	 * H1
	 *
	 * @see String
	 */
	private static final String H1 = "# ";

	/**
	 * H2
	 *
	 * @see String
	 */
	private static final String H2 = "## ";

	/**
	 * H3
	 *
	 * @see String
	 */
	private static final String H3 = "### ";

	/**
	 * 列出 SEP
	 *
	 * @see String
	 */
	private static final String LIST_SEP = "\n- ";

	/**
	 * 入口头
	 *
	 * @see String
	 */
	private static final String ENTRY_HEAD = "- ";

	/**
	 * 线路 SEP
	 *
	 * @see String
	 */
	private static final String LINE_SEP = "\n";

	/**
	 * 九月
	 *
	 * @see String
	 */
	private static final String SEP = File.separator;

	/**
	 * 基本 MD 文件
	 *
	 * @see String
	 */
	private static final String BASE_MD_FILE = "README.md";

	/**
	 * Mission MD 文件
	 *
	 * @see String
	 */
	private static final String MISSION_MD_FILE = "README.md";

	/**
	 * 撰写文件名称
	 *
	 * @see String
	 */
	private static final String WRITEUP_FILE_NAME = "writeup.md";

	/**
	 * 默认角色
	 *
	 * @see String
	 */
	private static final String DEFAULT_ROLE = "学员";

	/**
	 * md 后缀
	 *
	 * @see String
	 */
	private static final String MD_SUFFIX = ".md";

	/**
	 * img dir （英语）
	 *
	 * @see String
	 */
	private static final String IMG_DIR = "img";

	/**
	 * 任务目录
	 *
	 * @see String
	 */
	private static final String TASK_DIR = "task";

	/**
	 * 图标文件
	 *
	 * @see String
	 */
	private static final String LOGO_FILE = "/logo.png";

	/**
	 * 图标后缀
	 *
	 * @see String
	 */
	private static final String LOGO_SUFFIX = ".png";

	/**
	 * 拓扑YAML
	 *
	 * @see String
	 */
	private static final String TOPO_YAML = "/env/topo.yml";

	/**
	 * dir 路径
	 *
	 * @see String
	 */
	private final String dirPath;

	/**
	 * 拓扑id
	 *
	 * @see String
	 */
	private final String topoId;

	/**
	 * 图像
	 *
	 * @see Map<String, String>
	 */
	private Map<String, String> images = Maps.newHashMap();

	/**
	 * 任务模板
	 *
	 * @see List<MissionTemplate>
	 */
	private List<MissionTemplate> missionTemplates = Lists.newArrayList();

	/**
	 * 业务模板
	 *
	 * @see BusinessTemplate
	 */
	private BusinessTemplate businessTemplate = new BusinessTemplate();

	/**
	 * 模板上下文
	 *
	 * @see TrainingTemplateContext
	 */
	private TrainingTemplateContext templateContext = new TrainingTemplateContext();


	/**
	 * 文件错误 msg
	 *
	 * @see ThreadLocal<String>
	 */
	public static ThreadLocal<String> fileErrorMsg = ThreadLocal.withInitial(() -> "");

	/**
	 * 归档错误 msg
	 *
	 * @see ThreadLocal<String>
	 */ // 字段
	public static ThreadLocal<String> filedErrorMsg = ThreadLocal.withInitial(() -> "");

	public MDParser(String dirPath, String topoId) {
		this.dirPath = dirPath;
		this.topoId = topoId;
	}

	/**
	 * 用于验证是否已存在
	 */
	public String getName() {
		String mdPath = dirPath + SEP + BASE_MD_FILE;
		String baseText = stripMd(mdPath);
		Map<String, String> data = parseText(baseText, LINE_SEP + H3,
				ZiguanConstants.TEMPLATE_BASE_INFO_FIELDS);
		return data.get(ZiguanConstants.TEMPLATE_NAME);
	}

	public void loadDealError(String businessTemplateId) {
		try {
			load(businessTemplateId);
		}  finally {
			fileErrorMsg.remove();
			filedErrorMsg.remove();
		}
	}
	/**
	 * 解析入口
	 */
	public void load(String businessTemplateId) {
		String mdPath = dirPath + SEP + BASE_MD_FILE;
		String baseText = stripMd(mdPath);
		Map<String, String> data = parseText(baseText, LINE_SEP + H3,
				ZiguanConstants.TEMPLATE_BASE_INFO_FIELDS);
		// type
		Integer businessType = ZiguanConstants.CE_TYPE_MAPPING.get(data.get(ZiguanConstants.TYPE));
		if (businessType == null) {
			String businessTypeString = String.join(",", ZiguanConstants.CE_TYPE_MAPPING.keySet());
			log.error("invalid businessType! businessType is[{}]", businessType);
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, fileErrorMsg.get(), "场景类型不在范围内" + businessTypeString);
		}
		businessTemplate.setBusinessType(businessType);
		// difficulty
		Integer difficulty = ZiguanConstants.DIFFICULTY_CHOICE
				.get(data.get(ZiguanConstants.DIFFICULTY));
		if (difficulty == null) {
			log.error("Invalid difficulty! difficulty = [{}]", difficulty);
			String difficultString = String.join(",", ZiguanConstants.DIFFICULTY_CHOICE.keySet());
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, fileErrorMsg.get(), "难度不在范围" + difficultString);
		}
		businessTemplate.setDifficulty(difficulty);
		// category
		Integer category = ZiguanConstants.BUSINESS_CATEGORIES
				.get(data.get(ZiguanConstants.CATEGORY));
		if (category == null) {
			log.error("Invalid training category! category is null");
			String categoryString = String.join(",", ZiguanConstants.BUSINESS_CATEGORIES.keySet());
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, fileErrorMsg.get(), "训练类型不在范围内" + categoryString);
		}
		templateContext.setSubType(category);
		// duration time
		Integer durationTime = data.get(ZiguanConstants.DURATION_TIME) != null ? new Integer(data.get(ZiguanConstants.DURATION_TIME)): 4;
		templateContext.setDurationTime(durationTime);
		// pre skill
		String[] preSkills = data.get(ZiguanConstants.PRE_SKILL).split(LINE_SEP);
		if (preSkills.length == 0) {
			log.error("pre_skill length is 0");
			throw new BusinessException(ErrorCode.IMPORT_RESOURCE_ERROR, fileErrorMsg.get(), "前置技能不能为空");
		}
		for (int i = 0; i < preSkills.length; i++) {
			preSkills[i] = preSkills[i].substring(2).trim();
		}
		templateContext.setPreSkills(JsonUtil.arrayToString(Lists.newArrayList(preSkills)));
		// train skill
		String[] trainSkills = data.get(ZiguanConstants.TRAIN_SKILL).split(LINE_SEP);
		List<String> skills = Lists.newArrayList();
		for (int i = 1; i < trainSkills.length; i++) {
			skills.add(trainSkills[i].substring(4).trim());
		}
		templateContext.setTrainSkills(JsonUtil.arrayToString(skills));
		// other
		businessTemplate.setName(data.get(ZiguanConstants.TEMPLATE_NAME));
		businessTemplate.setDescription(data.get(ZiguanConstants.DESCRIPTION));
		businessTemplate.setTopoId(topoId);
		businessTemplate.setResourceId(businessTemplateId);
		templateContext.setBusinessTemplateId(businessTemplate.getResourceId());
		businessTemplate.setCreateUserId(CommonConstant.ADMIN_ID);

		// 设为全部可见
		businessTemplate.setOperationAuthType(AuthTable.AuthType.ALL_SHARE_MODE.getCode());
		businessTemplate.setOperationAuthIds("[]");

		// single training
		if (businessTemplate.getBusinessType().equals(ZiguanConstants.SINGLE_TRAIN)) {
			templateContext.setMissionsScheme(JsonUtil.toJsonString(single().getMissionsScheme(),
					PropertyNamingStrategy.SnakeCase));
		}
		// team training
		if (businessTemplate.getBusinessType().equals(ZiguanConstants.TEAM_TRAIN)) {
			List<Role> roles = parseRoles(data.get(ZiguanConstants.ROLE_INFO)).getStudent();
			templateContext.setRoleConfig(
					JsonUtil.arrayToString(team(roles), PropertyNamingStrategy.SnakeCase));
		}
		// red vs blue
		if (businessTemplate.getBusinessType().equals(ZiguanConstants.RVB)) {
			GroupRoles groupRoles = parseRoles(data.get(ZiguanConstants.ROLE_INFO));
			templateContext.setRoleConfig(
					JsonUtil.toJsonString(rvb(groupRoles), PropertyNamingStrategy.SnakeCase));
		}
		// other
		templateContext.setPeopleNumber(4);
		templateContext.setTimeLineConfig("{}");
		// add mission_template-business_template relation
		for (MissionTemplate missionTemplate : missionTemplates) {
			missionTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		}
		// set logo, 资源包中logo文件名固定为logo.png
		String logoPath = dirPath + LOGO_FILE;
		if (new File(logoPath).isFile()) {
			String logoId = IDGeneratorUtil.getID();
			String newFileName = logoId + LOGO_SUFFIX;
			String dest = ConfigUtil.getContextProperty("cr.local.file.prefix")
				+ AttachmentPath.UPLOAD_IMAGE.getOriginDesc() + newFileName;
			FileUtil.copyFile(logoPath, dest, CommonConstant.FALSE);
			images.put(logoId, newFileName);
			businessTemplate.setLogo(dest);
		}

	}

	/**
	 * 单兵
	 */
	private RoleInfo single() {
		return parseRoleMd(DEFAULT_ROLE);
	}

	/**
	 * 团队
	 */
	private List<RoleInfo> team(List<Role> roles) {
		List<RoleInfo> infos = Lists.newArrayList();
		roles.forEach(role -> infos.add(parseRoleMd(role.getName())));
		return infos;
	}

	/**
	 * 红蓝
	 */
	private RbRoleInfo rvb(GroupRoles groupRoles) {
		RbRoleInfo rbRoleInfo = new RbRoleInfo();
		rbRoleInfo.setRedRoles(team(groupRoles.getRed()));
		rbRoleInfo.setBlueRoles(team(groupRoles.getBlue()));
		return rbRoleInfo;
	}

	/**
	 * 解析角色信息（分组， 角色名等）
	 * @param roleInfo
	 * @return
	 */
	private GroupRoles parseRoles(String roleInfo) {
		if (StringUtils.isBlank(roleInfo)) {
			log.error("Invalid role configuration info! roleInfo is blank");
			throw new BusinessException(CrBusinessErrorCode.INVALID_ROLE_INFO);
		}
		// 逐行读取
		String[] lines = roleInfo.split(LINE_SEP);
		Iterator<String> iter = Arrays.stream(lines).iterator();
		String line = iter.next();
		if (!line.startsWith(ENTRY_HEAD)) {
			log.error("Invalid role configuration info! line = [{}]", line);
			throw new BusinessException(CrBusinessErrorCode.INVALID_ROLE_INFO);
		}
		GroupRoles groupRoles = new GroupRoles();
		while (StringUtils.isNotBlank(line)) {
			if (line.startsWith(ENTRY_HEAD)) {
				String groupName = line.substring(2);
				line = iter.next();
				while (!line.startsWith(ENTRY_HEAD)) {
					// 读取并分类
					Role role = new Role(line.substring(4), iter.next().substring(6));
					if (groupName.equals(RoleGroupNames.STUDENT)) {
						groupRoles.getStudent().add(role);
					}
					if (groupName.equals(RoleGroupNames.RED)) {
						groupRoles.getRed().add(role);
					}
					if (groupName.equals(RoleGroupNames.BLUE)) {
						groupRoles.getBlue().add(role);
					}
					if (iter.hasNext()) {
						line = iter.next();
					} else {
						break;
					}
				}
			} else {
				break;
			}
		}
		return groupRoles;

	}

	/**
	 * 解析一个角色
	 * @param roleName
	 * @return
	 */
	private RoleInfo parseRoleMd(String roleName) {
		String mdFile = dirPath + SEP + TASK_DIR + SEP + roleName + MD_SUFFIX;
		String nodeText = "";
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setName(roleName);
		if (!new File(mdFile).isFile()) {
			log.warn(String.format("%s does not exist.", mdFile));
			// role md does not exist, create empty mission scheme as default
			roleInfo.setMissionsScheme(new MissionScheme(Lists.newArrayList()));
			return roleInfo;
		}
		String mdText = stripMd(mdFile);
		Map<String, String> roleMdDict = parseText(mdText, LINE_SEP + H1, null);
		// split nodes and missions
		nodeText = roleMdDict.get(ZiguanConstants.NODE_COLL);
		String missionsText = roleMdDict.get(ZiguanConstants.MISSION_COLL);
		String[] missionStages = missionsText.split(LINE_SEP + H2);
		// parse nodes
		parseRoleNodes(roleInfo, nodeText);
		// load mission scheme
		roleInfo.setMissionsScheme(loadMissionScheme(missionStages));
		return roleInfo;
	}

	/**
	 * 解析节点信息，（战争迷雾，角色资产）
	 * @param roleInfo
	 * @param nodeText
	 */
	private void parseRoleNodes(RoleInfo roleInfo, String nodeText) {
		if (StringUtils.isNotBlank(nodeText)) {
			Map<String, String> nodeDict = parseText(nodeText, LINE_SEP + H2,
					ZiguanConstants.ROLE_NODES_CATEGORY);
			parseNodes(nodeDict.get(ZiguanConstants.ASSET_NODES), roleInfo.getAssetNodes());
			parseNodes(nodeDict.get(ZiguanConstants.VISIBLE_NODES), roleInfo.getVisibleNodes());

			// filter invalid nodes
			List<String> validNodeIds = getValidNodes();
			filterInvalidNodes(roleInfo.getVisibleNodes(), validNodeIds);
			// 当markdown中不存在资产数据时，导入资产数据等于迷雾数据
			if (roleInfo.getAssetNodes().isEmpty()) {
				roleInfo.setAssetNodes(
						BeanUtils.deepCopyList(roleInfo.getVisibleNodes(), Node.class));
			} else {
				filterInvalidNodes(roleInfo.getAssetNodes(), validNodeIds);
			}
		}
	}

	/**
	 * 解析节点列表文本
	 * @param nodesText
	 * @return
	 */
	private void parseNodes(String nodesText, List<Node> nodes) {
		if (StringUtils.isNotBlank(nodesText)) {
			for (String nodePart : nodesText.split(LINE_SEP)) {
				nodes.add(new Node(nodePart.trim().substring(2)));
			}
		}
	}

	/**
	 * 读取模板任务编排信息
	 * @param missionStages
	 * @return
	 */
	private MissionScheme loadMissionScheme(String[] missionStages) {
		String taskDir = dirPath + SEP + TASK_DIR;
		// parse scheme
		List<SchemeDatum> schemeData = Lists.newArrayList();
		for (String stage : missionStages) {
			// 角色任务为空
			if (stage.isEmpty()) {
				break;
			}
			String[] stageParts = stage.split(LINE_SEP + H3, 2);
			String stagePart = stageParts[0];
			String missionPart = stageParts[1];
			// stage name
			String stageName = stagePart.split(LINE_SEP, 2)[1].trim();
			// mission titles
			List<String> missionTitles = Lists.newArrayList();
			String[] titleParts = missionPart.split(LIST_SEP);
			for (int i = 1; i < titleParts.length; i++) {
				missionTitles.add(titleParts[i].trim());
			}
			// missions
			List<Mission> missions = Lists.newArrayList();
			for (String missionTitle : missionTitles) {
				String missionDir = taskDir + SEP + missionTitle;
				if (!new File(missionDir).exists()) {
					log.warn(String.format("%s folder not exist", missionTitle));
					continue;
				}
				String missionTemplateId = parseMission(missionDir, missionTitle);
				missions.add(new Mission(missionTemplateId));
			}
			schemeData.add(new SchemeDatum(stageName, missions));
		}

		return new MissionScheme(schemeData);
	}

	/**
	 * 解析任务信息
	 * @param missionDir
	 * @param missionTitle
	 */
	private String parseMission(String missionDir, String missionTitle) {
		// read
		String missionText = stripMd(missionDir + SEP + MISSION_MD_FILE);
		Map<String, String> missionData = parseText(missionText, LINE_SEP + H3,
				ZiguanConstants.MISSION_TEMPLATE_FIELDS);
		MissionTemplate template = new MissionTemplate();
		template.setTitle(missionData.get(ZiguanConstants.MISSION_TITLE));
		template.setDescription(missionData.get(ZiguanConstants.DESCRIPTION));
		// set category
		template.setCategoryId(ZiguanConstants.DEFAULT_MISSION_CATEGORIES
				.get(missionData.get(ZiguanConstants.CATEGORY)));
		// score
		template.setScore(new Integer(missionData.get(ZiguanConstants.SCORE)));
		// skill points
		String skillPointsText = missionData.get(ZiguanConstants.SKILL_POINTS);
		template.setSkillPoints(
				StringUtils.isBlank(skillPointsText) ? genSkillPoints() : skillPointsText);
		// type
		template.setType(
				ZiguanConstants.MISSION_TYPE_MAPPING.get(missionData.get(ZiguanConstants.TYPE)));
		// option, single choice & multi choice
		if (template.getType().equals(ZiguanConstants.SINGLE_CHOICE_TYPE)
				|| template.getType().equals(ZiguanConstants.MULTI_CHOICE_TYPE)) {
			String rawOption = missionData.get(ZiguanConstants.OPTION).substring(2);
			List<Option> options = Lists.newArrayList();
			char[] a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			String[] rawOptions = rawOption.split(LIST_SEP);
			int len = rawOptions.length;
			for (int i = 0; i < len; i++) {
				options.add(new Option(a[i], rawOptions[i]));
			}
			template.setOption(JsonUtil.arrayToString(options));
		} else {
			template.setOption(CrCommonConstants.NULL);
		}
		// answer
		template.setAnswer(missionData.get(ZiguanConstants.ANSWER).trim());
		// judge
		if (template.getType().equals(ZiguanConstants.JUDGEMENT_TYPE)) {
			template.setAnswer(ZiguanConstants.JUDGEMENT_ANSWER_MAPPING.get(template.getAnswer()));
		}
		// multi choice
		if (template.getType().equals(ZiguanConstants.MULTI_CHOICE_TYPE)) {
			List<String> answers = LocalStringUtil.getMatchStr(template.getAnswer(), "[A-Z]");
			template.setAnswer(JsonUtil.arrayToString(answers));
		}
		// writeup
		handleWriteup(template, missionDir, missionTitle);
		// return
		template.setResourceId(IDGeneratorUtil.getID());
		template.setCreateUserId(CommonConstant.ADMIN_ID);
		missionTemplates.add(template);
		return template.getResourceId();
	}

	/**
	 * 生成技能点
	 * @return
	 */
	private String genSkillPoints() {
		int len = CrCommonConstants.SKILL_POINTS_SIZE;
		List<Integer> defaultPoints = Lists.newArrayList();
		for (int i = 0; i < len; i++) {
			defaultPoints.add(1);
		}
		return JsonUtil.arrayToString(defaultPoints);
	}

	/**
	 * 读取并处理 writeup 中的图片
	 * @param missionDir
	 * @param missionTitle
	 */
	private void handleWriteup(MissionTemplate template, String missionDir, String missionTitle) {
		List<String> attachments = Lists.newArrayList();
		StringBuilder ret = new StringBuilder();
		List<String> lines = Lists.newArrayList();
		// load writeup
		File writeupFile = new File(missionDir + SEP + WRITEUP_FILE_NAME);
		if (writeupFile.exists()) {
			lines = FileUtil.listContent(writeupFile);
		} else {
			log.warn(String.format("writeup md file missing: [%s]", writeupFile.getAbsolutePath()));
		}
		String regex = String.format(PatternConstant.WRITEUP_IMG_MD, IMG_DIR);
		for (String line : lines) {
			// match img like ![](./img/1.png) ---> ./img/1.png
			String img = LocalStringUtil.getFirstMatchStr(line,
					"!\\[[^]]*]\\((\\./img/[0-9]+\\.(?:jpg|jpeg|png|bmp|webp|gif))\\)", 1);
			if (!img.isEmpty()) {
				// copy & rename img
				// remove initial dot .
				String originalPath = missionDir + img.substring(1);
				// rename
				String imgId = IDGeneratorUtil.getID();
				String newFileName = imgId + "." + img.split("\\.", 3)[2];
				String dest = ConfigUtil.getContextProperty("cr.local.file.prefix")
						+ AttachmentPath.UPLOAD_IMAGE.getOriginDesc() + newFileName;
				// copy
				FileUtil.copyFile(originalPath, dest, CommonConstant.FALSE);
				// mission attachments
				attachments.add(imgId);
				images.put(imgId, newFileName);
				// replace writeup text
				line = line.replace(img, dest);
			}
			ret.append(line).append(LINE_SEP);
		}
		template.setWriteUp(ret.toString());
		template.setAttachment(JsonUtil.arrayToString(attachments));
	}

	/**
	 * 读取topo.yaml里的节点id列表
	 * @return 节点id列表
	 */
	private List<String> getValidNodes() {
		File topoYaml = new File(dirPath + TOPO_YAML);
		if (!topoYaml.isFile()) {
			return Lists.newArrayList();
		}
		String text = FileUtil.getContent(topoYaml);
		List<String> nodes = LocalStringUtil.getMatchStr(text, "\\n-[ ]id:[ ](.+)\\s", 1);
		List<String> networks = LocalStringUtil.getMatchStr(text, "\\n[ ]{2}id:[ ](.+)\\s", 1);
		nodes.addAll(networks);
		return nodes;
	}

	/**
	 * 筛除节点脏数据
	 * @param nodes 节点列表
	 * @param validNodeIds 存在的节点id列表
	 */
	private void filterInvalidNodes(List<Node> nodes, List<String> validNodeIds) {
		if (validNodeIds.isEmpty()) {
			return;
		}
		int i = 0;
		while (i < nodes.size()) {
			String nodeId = nodes.get(i).getId();
			if (!validNodeIds.contains(nodeId)) {
				nodes.remove(i);
			} else {
				i++;
			}
		}
	}

	/**
	 * 读取md文件，并移除空行
	 * @param mdPath
	 * @return
	 */
	private String stripMd(String mdPath) {
		fileErrorMsg.set(mdPath);
		List<String> lines = FileUtil.listContent(new File(mdPath));
		StringBuilder ret = new StringBuilder();
		String tempStr;
		for (int i = 0; i < lines.size(); i++) {
			tempStr = lines.get(i).trim();
			if (!tempStr.isEmpty()) {
				ret.append(lines.get(i));
				if (i < lines.size() - 1) {
					ret.append(LINE_SEP);
				}
			}
		}
		return ret.toString();
	}

	/**
	* 解析文本
	* @param text 文本
	* @param separator 分隔符
	* @param mapping 中文字段名映射
	* @return
	*/
	private Map<String, String> parseText(String text, String separator,
			Map<String, String> mapping) {
		String[] parts = text.split(separator);
		Map<String, String> ret = new MsgMap(mapping);

		for (String part : parts) {
			String[] partParts = StringUtils.split(part, LINE_SEP, 2);
			String label = partParts[0];
			String value = partParts.length == 2 ? partParts[1] : "";

			if (mapping == null) {
				ret.put(stripLabel(label), value);
			} else {
				ret.put(mapping.get(stripLabel(label)), value);
			}
		}
		return ret;
	}

	/**
	 * 处理 md 里的标题
	 * @param label 标题
	 * @return
	 */
	private String stripLabel(String label) {
		while (true) {
			if (label.startsWith("#")) {
				label = label.substring(1);
			} else {
				break;
			}
		}
		return label.trim();
	}
}

/**
 * 选择
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
class Option implements Serializable {

	/**
	 * 选项标签
	 *
	 * @see Character
	 */
	private Character optionLabel;

	/**
	 * 选择值
	 *
	 * @see String
	 */
	private String optionValue;

	public Option(Character optionLabel, String optionValue) {
		this.optionLabel = optionLabel;
		this.optionValue = optionValue;
	}
}

/**
 * RB 角色信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
class RbRoleInfo {

	/**
	 * RED 角色
	 *
	 * @see List<RoleInfo>
	 */
	private List<RoleInfo> redRoles;

	/**
	 * 蓝色角色
	 *
	 * @see List<RoleInfo>
	 */
	private List<RoleInfo> blueRoles;
}

/**
 * 角色信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
class RoleInfo {

	/**
	 * 资产节点
	 *
	 * @see List<Node>
	 */
	private List<Node> assetNodes = Lists.newArrayList();

	/**
	 * 可见节点
	 *
	 * @see List<Node>
	 */
	private List<Node> visibleNodes = Lists.newArrayList();

	/**
	 * 任务计划
	 *
	 * @see MissionScheme
	 */
	private MissionScheme missionsScheme;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

}

/**
 * 任务计划
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
class MissionScheme {

	/**
	 * 颜色
	 *
	 * @see Integer
	 */
	private Integer color = 1;

	/**
	 * 模板
	 *
	 * @see Integer
	 */
	private Integer template = 1;

	/**
	 * 方案数据
	 *
	 * @see List<SchemeDatum>
	 */
	private List<SchemeDatum> schemeData;

	public MissionScheme(List<SchemeDatum> schemeData) {
		this.schemeData = schemeData;
	}
}

/**
 * 方案基准
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
class SchemeDatum {

	/**
	 * 阶段
	 *
	 * @see String
	 */
	private String stage;

	/**
	 * 任务
	 *
	 * @see List<Mission>
	 */
	private List<Mission> missions;

	public SchemeDatum(String stageName, List<Mission> missions) {
		this.stage = stageName;
		this.missions = missions;
	}
}

/**
 * 任务
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
class Mission {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	public Mission(String resourceId) {
		this.resourceId = resourceId;
	}
}

/**
 * 节点
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
class Node {

	/**
	 * 给前端用的
	 *
	 * @see String
	 */
	private String category;

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	public Node(String id) {
		this.id = id;
		if (id.startsWith("server")) {
			this.category = "1";
		} else {
			this.category = "3";
		}
	}
}

/**
 * 角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
class Role {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String desc;

	public Role(String name, String desc) {
		this.name = name;
		this.desc = desc;
	}
}

/**
 * 组角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
class GroupRoles {

	/**
	 * 学员
	 *
	 * @see List<Role>
	 */
	private List<Role> student = Lists.newArrayList();

	/**
	 * 红
	 *
	 * @see List<Role>
	 */
	private List<Role> red = Lists.newArrayList();

	/**
	 * 蓝
	 *
	 * @see List<Role>
	 */
	private List<Role> blue = Lists.newArrayList();
}

/**
 * MSG 地图
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see HashMap
 * @since 1.0.0
 */
class MsgMap extends HashMap {

	/**
	 * 映射
	 *
	 * @see Map<String, String>
	 */
	Map<String, String> mapping;

	public MsgMap(Map<String, String> mapping) {
		super();
		this.mapping = new HashMap<>();
		if (mapping != null) {
			for (Entry<String, String> entry : mapping.entrySet()) {
				this.mapping.put(entry.getValue(), entry.getKey());
			}
		}
	}

	@Override
	public Object get(Object key) {
		MDParser.filedErrorMsg.set(mapping.getOrDefault(key.toString(), key.toString()));
		return super.get(key);
	}

	@Override
	public Object getOrDefault(Object key, Object defaultValue) {
		MDParser.filedErrorMsg.set(mapping.getOrDefault(key.toString(), key.toString()));
		return super.getOrDefault(key, defaultValue);
	}
}