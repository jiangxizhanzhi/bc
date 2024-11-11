package com.cyberpeace.cr.teamtrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.mdparser.MdParser;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.convert.BusinessTypeConvert;
import com.cyberpeace.cr.crbase.service.context.md.*;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cr.crbase.service.impl.ExportTrainMDServiceImpl;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cs.service.context.RoleBo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 导出团队md服务实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class ExportTeamMDServiceImpl extends ExportTrainMDServiceImpl {

	/**
	 * 团队模板Mapper
	 */
	@Autowired
	private TeamTemplateMapper teamTemplateMapper;

	/**
	 * 任务模板Service
	 */
	@Autowired
	private MissionTemplateService missionTemplateService;

	/**
	 * 靶场常数Service
	 */
	@Autowired
	private CrConstantsService crConstantsService;

	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	/**
	 * 获取代号
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer getCode(){
		return BusinessTypeConvert.TEAM.to();
	}

	/**
	 * 导出训练Markdown
	 *
	 * @param businessTemplateList 业务模板列表
	 * @param path                 路径
	 * @param sceneNumber          场景数
	 * @param disableImageExport   禁用图像导出
	 */
	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path, String sceneNumber, Boolean disableImageExport) {

		if(businessTemplateList.size() == 0){
			return;
		}
		//获取团队训练模板
		List<TeamTemplate> teamTemplateList =
				teamTemplateMapper.batchGetByBusinessId(businessTemplateList.stream()
																			  .map(BusinessTemplate::getResourceId)
																			  .collect(Collectors.toList()));
		Map<String, TeamTemplate> teamTemplateMap = StreamApiUtils.toMap(teamTemplateList,
				TeamTemplate::getBusinessTemplateId, teamTemplate -> teamTemplate);
		//获取任务分类
		List<CrConstantsConfig> constants =
				crConstantsService.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
		Map<String, String> constantMap = StreamApiUtils.toMap(constants, CrConstantsConfig::getResourceId,
				CrConstantsConfig::getCnName);

		for (BusinessTemplate businessTemplate : businessTemplateList) {
			//校验任务名称
			TeamTemplate teamTemplate = teamTemplateMap.get(businessTemplate.getResourceId());
			JSONArray roleList = JSON.parseArray(teamTemplate.getRoleConfig());
			List<CrRoleConfigSchemeStage> allSchemeStage = new ArrayList<>();
			List<String> schemeJsonList = new ArrayList<>();
			for (int i = 0; i < roleList.size(); i++) {
				JSONObject role = roleList.getJSONObject(i);
				CrRoleConfigMissionsScheme rspMissionsScheme = JsonUtil.toJsonObject(role.getString(MISSIONS_SCHEME), CrRoleConfigMissionsScheme.class);
				allSchemeStage.addAll(rspMissionsScheme.getSchemeData());
				schemeJsonList.add(role.getString(MISSIONS_SCHEME));
			}
			Map<String, MissionTemplate> allMissionTemplate = missionTemplateService.getMappingBySchemeJsonList(schemeJsonList);
			validateTaskTile(allMissionTemplate, allSchemeStage, businessTemplate.getName());

			//单个训练导出
			log.info("start export team training [{}]", businessTemplate.getName());

			File file = new File(path + File.separator + businessTemplate.getName());
			if (file.exists()) {
				FileUtil.deleteDir(file);
			}
			FileUtil.mkdirs(file.getPath());

			//场景信息导出
			log.info("start export team training [{}] scene", businessTemplate.getName());
			Map<String, String> nodeIdMap = exportScene(businessTemplate.getTopoId(), file.getPath() + File.separator + ENV,
					file.getPath() + File.separator + ENV + File.separator + IMAGES, sceneNumber, disableImageExport);
			log.info("export team training [{}] scene successfully", businessTemplate.getName());

			TrainMD trainMD = BeanUtils.copy(businessTemplate, TrainMD.class);
			trainMD.setTrainType(teamTemplate.getSubType());
			BeanUtils.copyProperties(teamTemplate, trainMD);
			//logo导出
			MdParser mdParser = new MdParser();
			if(!StringUtils.isBlank(businessTemplate.getLogo())){
				String imgUrl = businessTemplate.getLogo();
				trainMD.setLogo(MD_LOGO);
				FileUtil.copyFile(imgUrl, file.getPath() + File.separator + "logo.png", false);
				log.info("export team training [{}] logo.png successfully", businessTemplate.getName());
			}

			trainMD.setPreSkills(JsonUtil.toJsonObjectArray(teamTemplate.getPreSkills(), String.class));
			trainMD.setTrainSkills(JsonUtil.toJsonObjectArray(teamTemplate.getTrainSkills(), String.class));
			List<RoleBo> roleBos = new ArrayList<>();
			List<String> roleName = new ArrayList<>();
			File taskFile = new File(file.getPath() + File.separator + TASK);

			FileUtil.mkdirs(taskFile.getPath());

			for (int i = 0; i < roleList.size(); i++) {
				//单个角色
				JSONObject role = roleList.getJSONObject(i);
				roleName.add(role.getString("name"));

				TeamAndRBMD teamAndRBMD = new TeamAndRBMD();
				List<JSONObject> seeNodeObject = role.getObject("visible_nodes", List.class);
				List<String> seeNodeList = new ArrayList<>();
				for (JSONObject jsonObject : seeNodeObject) {
					seeNodeList.add(nodeIdMap.get(jsonObject.getString("id")));
				}
				teamAndRBMD.setSeeNode(seeNodeList);
				List<JSONObject> useNodeObject = role.getObject("asset_nodes", List.class);
				List<String> useNodeList = new ArrayList<>();
				for (JSONObject jsonObject : useNodeObject) {
					useNodeList.add(nodeIdMap.get(jsonObject.getString("id")));
				}
				teamAndRBMD.setUseNode(useNodeList);

				Map<String, MissionTemplate> mapping =
						missionTemplateService.getMappingBySchemeJson(role.getString(MISSIONS_SCHEME));
				CrRoleConfigMissionsScheme rspMissionsScheme = JsonUtil.toJsonObject(role.getString(MISSIONS_SCHEME), CrRoleConfigMissionsScheme.class);

				List<RoleBo> roleBoList = new ArrayList<>();
				for (CrRoleConfigSchemeStage crRoleConfigSchemeStage : rspMissionsScheme.getSchemeData()) {
					RoleBo roleBo = new RoleBo();
					List<String> taskList = new ArrayList<>();
					for (CrRoleConfigMission crRoleConfigMission : crRoleConfigSchemeStage.getMissions()) {
						//单个任务导出
						MissionTemplate missionTemplate = mapping.get(crRoleConfigMission.getResourceId());

						exportMission(missionTemplate, taskList, constantMap, taskFile);
					}
					roleBo.setTeamName(crRoleConfigSchemeStage.getStage());
					roleBo.setRoleName(taskList);
					roleBoList.add(roleBo);

				}
				teamAndRBMD.setTask(roleBoList);
				mdParser.write(teamAndRBMD, new File(taskFile.getPath() + File.separator + role.getString("name") + ".md"));
				log.info("export team training [{}] {}.md successfully", businessTemplate.getName(), role.getString("name"));

			}
			roleBos.add(new RoleBo("学员组", roleName));
			trainMD.setRoles(roleBos);
			mdParser.write(trainMD, new File(file.getPath() + File.separator + BASE_MD_FILE));
			log.info("export team training [{}] README.md successfully", businessTemplate.getName());
			log.info("export team training [{}] successfully", businessTemplate.getName());
		}

	}

}
