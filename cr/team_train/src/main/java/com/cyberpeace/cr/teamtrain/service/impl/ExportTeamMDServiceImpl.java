package com.cyberpeace.cr.teamtrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.MdConstant;
import com.cyberpeace.common.constant.PatternConstant;
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

@Service
public class ExportTeamMDServiceImpl extends ExportTrainMDServiceImpl {

	@Autowired
	private TeamTemplateMapper teamTemplateMapper;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private CrConstantsService crConstantsService;

	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	@Override
	public Integer getCode(){
		return BusinessTypeConvert.TEAM.to();
	}

	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path) {

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
				CrRoleConfigMissionsScheme rspMissionsScheme = JsonUtil.toJsonObject(role.getString("missions_scheme"), CrRoleConfigMissionsScheme.class);
				allSchemeStage.addAll(rspMissionsScheme.getSchemeData());
				schemeJsonList.add(role.getString("missions_scheme"));
			}
			Map<String, MissionTemplate> allMissionTemplate = missionTemplateService.getMappingBySchemeJsonList(schemeJsonList);
			validateTaskTile(allMissionTemplate, allSchemeStage, businessTemplate.getName());

			//单个训练导出
			log.info("团队训练【" + businessTemplate.getName() + "】开始导出");

			File file = new File(path + File.separator + businessTemplate.getName());
			FileUtil.mkdirs(file.getPath());

			//场景信息导出
			log.info(businessTemplate.getName() + "开始导出场景信息");
			Map<String, String> nodeIdMap = dumpScene(businessTemplate.getTopoId(), file.getPath() + File.separator + ENV,
					file.getPath() + File.separator + ENV + File.separator + IMAGES);
			log.info(businessTemplate.getName() + "场景信息导出结束");

			TrainMD trainMD = BeanUtils.copy(businessTemplate, TrainMD.class);
			trainMD.setTrainType(teamTemplate.getSubType());
			BeanUtils.copyProperties(teamTemplate, trainMD);
			//logo导出
			MdParser mdParser = new MdParser();
			if(!StringUtils.isBlank(businessTemplate.getLogo())){
				String imgUrl = businessTemplate.getLogo();
				trainMD.setLogo(MD_LOGO);
				FileUtil.copyFile(imgUrl, file.getPath() + File.separator + "logo.png", false);
				log.info(businessTemplate.getName() + DOUBLE_BLANK + "logo.png导出结束");
			}

			trainMD.setPreSkills(JsonUtil.toJsonObjectArray(teamTemplate.getPreSkills(), String.class));
			trainMD.setTrainSkills(JsonUtil.toJsonObjectArray(teamTemplate.getTrainSkills(), String.class));
			List<RoleBo> roleBos = new ArrayList<>();
			List<String> roleName = new ArrayList<>();
			File taskFile = new File(file.getPath() + File.separator + TASK);

			if (!taskFile.exists()) {
				taskFile.mkdir();
			}

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
						missionTemplateService.getMappingBySchemeJson(role.getString("missions_scheme"));
				CrRoleConfigMissionsScheme rspMissionsScheme = JsonUtil.toJsonObject(role.getString("missions_scheme"), CrRoleConfigMissionsScheme.class);

				List<RoleBo> roleBoList = new ArrayList<>();
				for (CrRoleConfigSchemeStage crRoleConfigSchemeStage : rspMissionsScheme.getSchemeData()) {
					RoleBo roleBo = new RoleBo();
					List<String> taskList = new ArrayList<>();
					for (CrRoleConfigMission crRoleConfigMission : crRoleConfigSchemeStage.getMissions()) {
						//单个任务导出
						MissionTemplate missionTemplate = mapping.get(crRoleConfigMission.getResourceId());
						taskList.add(missionTemplate.getTitle());

						File task = new File(taskFile.getPath() + File.separator + missionTemplate.getTitle());
						FileUtil.mkdirs(task.getPath());
						File img = new File(task.getPath() + File.separator + IMG);
						FileUtil.mkdirs(img.getPath());
						JSONArray options = null;
						List<String> optionList = null;

						switch (missionTemplate.getType()) {
							case 0:
								//判断
								JudgeMD judgeMD = BeanUtils.copy(missionTemplate, JudgeMD.class);
								judgeMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
								mdParser.write(judgeMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
								break;
							case 1:
								//单选
								RadioMD radioMD = BeanUtils.copy(missionTemplate, RadioMD.class);
								options = JSONArray.parseArray(missionTemplate.getOption());
								optionList = new ArrayList<>();
								for (int j = 0; j < options.size(); j++) {
									optionList.add(options.getJSONObject(j)
														  .getString("optionValue"));
								}
								radioMD.setOptions(optionList);
								radioMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
								mdParser.write(radioMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
								break;
							case 2:
								//多选
								MultipleChoiceMD multipleChoiceMD = BeanUtils.copy(missionTemplate,
										MultipleChoiceMD.class);
								options = JSONArray.parseArray(missionTemplate.getOption());
								optionList = new ArrayList<>();
								for (int j = 0; j < options.size(); j++) {
									optionList.add(options.getJSONObject(j)
														  .getString("optionValue"));
								}
								multipleChoiceMD.setOptions(optionList);
								multipleChoiceMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
								mdParser.write(multipleChoiceMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
								break;
							default:
								//其他
								FileMD fileMD = BeanUtils.copy(missionTemplate, FileMD.class);
								fileMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
								mdParser.write(fileMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
								break;
						}
						log.info(missionTemplate.getTitle() + DOUBLE_BLANK + BASE_MD_FILE + "导出结束");
						//导出writeUp
						String writeUp = missionTemplate.getWriteUp();
						//导出图片
						List<String> strList = LocalStringUtil.getMatchStr(writeUp, PatternConstant.MARKDOWN_IMAGE);
						for (int j = 0; j < strList.size(); j++) {
							String str = strList.get(j);
							String imgUrl = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
							FileUtil.copyFile(imgUrl, img.getPath() + File.separator + (i + 1) + "." + FileUtil.getFileSuffix(imgUrl), false);
							imgUrl = "./img/" + (j + 1) + "." + FileUtil.getFileSuffix(imgUrl);
							writeUp = writeUp.replace(str, String.format(MdConstant.ONLY_URL_IMG,imgUrl));
						}

						File writeUpFile = new File(task.getPath() + File.separator + WRITE_UP_FILE);
						super.writeStringWriteUp(writeUp, writeUpFile, missionTemplate.getTitle());
					}
					roleBo.setTeamName(crRoleConfigSchemeStage.getStage());
					roleBo.setRoleName(taskList);
					roleBoList.add(roleBo);

				}
				teamAndRBMD.setTask(roleBoList);
				mdParser.write(teamAndRBMD, new File(taskFile.getPath() + File.separator + role.getString("name") + ".md"));
				log.info(businessTemplate.getName() + DOUBLE_BLANK + role.getString("name") + ".md导出结束");

			}
			roleBos.add(new RoleBo("学员组", roleName));
			trainMD.setRoles(roleBos);
			mdParser.write(trainMD, new File(file.getPath() + File.separator + BASE_MD_FILE));
			log.info(businessTemplate.getName() + DOUBLE_BLANK + BASE_MD_FILE + "导出结束");

			log.info("团队训练【" + businessTemplate.getName() + "】导出结束");
		}

	}

}
