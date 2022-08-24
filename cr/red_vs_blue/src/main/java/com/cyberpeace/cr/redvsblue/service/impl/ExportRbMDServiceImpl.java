package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MdConstant;
import com.cyberpeace.common.constant.PatternConstant;
import com.cyberpeace.common.exception.BusinessException;
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
import com.cyberpeace.integratation.model.scene.SceneDumpContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
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
public class ExportRbMDServiceImpl extends ExportTrainMDServiceImpl {

	@Autowired
	private RbTemplateMapper rbTemplateMapper;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private CrConstantsService crConstantsService;

	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	@Override
	public Integer getCode(){
		return BusinessTypeConvert.REDVSBLUE.to();
	}

	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path) {

		if(businessTemplateList.size() == 0){
			return;
		}
		//获取红蓝对抗模板
		List<RbTemplate> rbTemplateList =
				rbTemplateMapper.batchGetByBusinessId(businessTemplateList.stream()
																			.map(BusinessTemplate::getResourceId)
																			.collect(Collectors.toList()));
		Map<String, RbTemplate> teamTemplateMap = StreamApiUtils.toMap(rbTemplateList,
				RbTemplate::getBusinessTemplateId, rbTemplate -> rbTemplate);
		//获取任务分类
		List<CrConstantsConfig> constants =
				crConstantsService.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
		Map<String, String> constantMap = StreamApiUtils.toMap(constants, CrConstantsConfig::getResourceId,
				CrConstantsConfig::getCnName);

		for (BusinessTemplate businessTemplate : businessTemplateList) {
			//单个训练导出
			log.info("红蓝对抗【" + businessTemplate.getName() + "】开始导出");
			File file = new File(path + File.separator + businessTemplate.getName());
			FileUtil.mkdirs(file.getPath());
			//场景信息导出
			log.info(businessTemplate.getName() + "开始导出场景信息");
			Map<String, String> nodeIdMap = dumpScene(businessTemplate.getTopoId(), file.getPath() + File.separator + ENV,
					file.getPath() + File.separator + ENV + File.separator + IMAGES);
			log.info(businessTemplate.getName() + "场景信息导出结束");

			RbTemplate rbTemplate = teamTemplateMap.get(businessTemplate.getResourceId());
			TrainMD trainMD = BeanUtils.copy(businessTemplate, TrainMD.class);
			trainMD.setTrainType(rbTemplate.getSubType());
			BeanUtils.copyProperties(rbTemplate, trainMD);
			//LOGO
			MdParser mdParser = new MdParser();
			if(!StringUtils.isBlank(businessTemplate.getLogo())){
				String imgUrl = businessTemplate.getLogo();
				trainMD.setLogo(MD_LOGO);
				FileUtil.copyFile(imgUrl, file.getPath() + File.separator + "logo.png", false);
				log.info(businessTemplate.getName() + DOUBLE_BLANK + "logo.png导出结束");
			}

			trainMD.setPreSkills(JsonUtil.toJsonObjectArray(rbTemplate.getPreSkill(), String.class));
			trainMD.setTrainSkills(JsonUtil.toJsonObjectArray(rbTemplate.getTrainSkill(), String.class));
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
			trainMD.setRoles(roleBos);
			mdParser.write(trainMD, new File(file.getPath() + File.separator + BASE_MD_FILE));
			log.info(businessTemplate.getName() + DOUBLE_BLANK + BASE_MD_FILE + "导出结束");

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
						missionTemplateService.getMappingBySchemeJson(role.getString("missions_scheme"));
				CrRoleConfigMissionsScheme rspMissionsScheme = JsonUtil.toJsonObject(role.getString("missions_scheme"), CrRoleConfigMissionsScheme.class);

				List<RoleBo> roleBoList = new ArrayList<>();
				for (CrRoleConfigSchemeStage crRoleConfigSchemeStage : rspMissionsScheme.getSchemeData()) {
					RoleBo roleBo = new RoleBo();
					List<String> taskList = new ArrayList<>();
					for (CrRoleConfigMission crRoleConfigMission : crRoleConfigSchemeStage.getMissions()) {
						MissionTemplate missionTemplate = mapping.get(crRoleConfigMission.getResourceId());
						taskList.add(missionTemplate.getTitle());

						File task = new File(taskFile.getPath() + File.separator + missionTemplate.getTitle());
						if (!task.exists()) {
							task.mkdir();
						}
						File img = new File(task.getPath() + File.separator + IMG);
						if (!img.exists()) {
							img.mkdir();
						}
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

			log.info("红蓝对抗【" + businessTemplate.getName() + "】导出结束");
		}

	}
}
