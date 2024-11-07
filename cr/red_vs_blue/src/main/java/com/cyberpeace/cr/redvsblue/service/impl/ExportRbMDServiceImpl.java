package com.cyberpeace.cr.redvsblue.service.impl;

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
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
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
 * 出口RBmd服务实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class ExportRbMDServiceImpl extends ExportTrainMDServiceImpl {

	/**
	 * RB模板Mapper
	 */
	@Autowired
	private RbTemplateMapper rbTemplateMapper;

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
		return BusinessTypeConvert.REDVSBLUE.to();
	}

	/**
	 * 出口训练医学博士
	 *
	 * @param businessTemplateList 业务模板列表
	 * @param path                 路径
	 * @param sceneNumber          场景数
	 * @param disableImageExport
	 */
	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path, String sceneNumber, Boolean disableImageExport) {

		if(businessTemplateList.size() == 0){
			return;
		}
		//获取红蓝对抗模板
		List<RbTemplate> rbTemplateList =
				rbTemplateMapper.batchGetByBusinessId(businessTemplateList.stream()
																			.map(BusinessTemplate::getResourceId)
																			.collect(Collectors.toList()));
		Map<String, RbTemplate> rbTemplateMap = StreamApiUtils.toMap(rbTemplateList,
				RbTemplate::getBusinessTemplateId, rbTemplate -> rbTemplate);
		//获取任务分类
		List<CrConstantsConfig> constants =
				crConstantsService.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
		Map<String, String> constantMap = StreamApiUtils.toMap(constants, CrConstantsConfig::getResourceId,
				CrConstantsConfig::getCnName);

		for (BusinessTemplate businessTemplate : businessTemplateList) {
			//校验任务名称
			RbTemplate rbTemplate = rbTemplateMap.get(businessTemplate.getResourceId());
			List<RoleBo> roleBos = new ArrayList<>();
			List<String> roleName = new ArrayList<>();
			JSONArray roleList = new JSONArray();
			JSONObject roleObject = JSONObject.parseObject(rbTemplate.getRoleConfig());
			JSONArray redList = roleObject.getJSONArray("red_roles");
			for(int i = 0;i< redList.size();i++){
				JSONObject role = redList.getJSONObject(i);
				roleName.add(role.getString("name"));
				roleList.add(role);
			}
			roleBos.add(new RoleBo("红方", roleName));

			roleName = new ArrayList<>();
			JSONArray blueList = roleObject.getJSONArray("blue_roles");
			for(int i = 0;i< blueList.size();i++){
				JSONObject role = blueList.getJSONObject(i);
				roleName.add(role.getString("name"));
				roleList.add(role);
			}
			roleBos.add(new RoleBo("蓝方", roleName));
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
			log.info("start export red_vs_blue [{}]", businessTemplate.getName());

			File file = new File(path + File.separator + businessTemplate.getName());
			if (file.exists()) {
				FileUtil.deleteDir(file);
			}
			FileUtil.mkdirs(file.getPath());
			//场景信息导出
			log.info("start export red_vs_blue [{}] scene", businessTemplate.getName());
			Map<String, String> nodeIdMap = exportScene(businessTemplate.getTopoId(), file.getPath() + File.separator + ENV,
					file.getPath() + File.separator + ENV + File.separator + IMAGES, sceneNumber, disableImageExport);
			log.info("export red_vs_blue [{}] scene successfully",businessTemplate.getName());

			TrainMD trainMD = BeanUtils.copy(businessTemplate, TrainMD.class);
			trainMD.setTrainType(rbTemplate.getSubType());
			BeanUtils.copyProperties(rbTemplate, trainMD);
			//LOGO
			MdParser mdParser = new MdParser();
			if(!StringUtils.isBlank(businessTemplate.getLogo())){
				String imgUrl = businessTemplate.getLogo();
				trainMD.setLogo(MD_LOGO);
				FileUtil.copyFile(imgUrl, file.getPath() + File.separator + "logo.png", false);
				log.info("export red_vs_blue [{}] logo.png successfully", businessTemplate.getName());
			}

			trainMD.setPreSkills(JsonUtil.toJsonObjectArray(rbTemplate.getPreSkill(), String.class));
			trainMD.setTrainSkills(JsonUtil.toJsonObjectArray(rbTemplate.getTrainSkill(), String.class));

			trainMD.setRoles(roleBos);
			mdParser.write(trainMD, new File(file.getPath() + File.separator + BASE_MD_FILE));
			log.info("export red_vs_blue [{}] README.md successfully", businessTemplate.getName());

			File taskFile = new File(file.getPath() + File.separator + TASK);

			if (!taskFile.exists()) {
				taskFile.mkdirs();
			}

			for (int i = 0; i < roleList.size(); i++) {
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
						MissionTemplate missionTemplate = mapping.get(crRoleConfigMission.getResourceId());

						exportMission(missionTemplate, taskList, constantMap, taskFile);
					}
					roleBo.setTeamName(crRoleConfigSchemeStage.getStage());
					roleBo.setRoleName(taskList);
					roleBoList.add(roleBo);

				}
				teamAndRBMD.setTask(roleBoList);
				mdParser.write(teamAndRBMD, new File(taskFile.getPath() + File.separator + role.getString("name") + ".md"));
				log.info("export red_vs_blue [{}] {}.md successfully", businessTemplate.getName(), role.getString("name"));
			}
			log.info("export red_vs_blue [{}] successfully", businessTemplate.getName());
		}

	}
}
