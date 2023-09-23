package com.cyberpeace.cr.singletrain.service.impl;

import com.cyberpeace.common.mdparser.MdParser;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.md.*;
import com.cyberpeace.cr.crbase.service.context.convert.BusinessTypeConvert;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cr.crbase.service.impl.ExportTrainMDServiceImpl;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleTemplateMapper;
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
public class ExportSingleMDServiceImpl extends ExportTrainMDServiceImpl {

	@Autowired
	private SingleTemplateMapper singleTemplateMapper;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Autowired
	private CrConstantsService crConstantsService;

	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	@Override
	public Integer getCode() {
		return BusinessTypeConvert.SINGLE.to();
	}

	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path, String sceneNumber, Boolean disableImageExport) {

		if (businessTemplateList.isEmpty()){
			return;
		}
		//获取单兵训练模板
		List<SingleTemplate> singleTemplateList =
				singleTemplateMapper.batchGetByBusinessId(businessTemplateList.stream()
																			  .map(BusinessTemplate::getResourceId)
																			  .collect(Collectors.toList()));
		Map<String, SingleTemplate> singleTemplateMap = StreamApiUtils.toMap(singleTemplateList,
				SingleTemplate::getBusinessTemplateId, singleTemplate -> singleTemplate);
		//获取任务分类
		List<CrConstantsConfig> constants =
				crConstantsService.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
		Map<String, String> constantMap = StreamApiUtils.toMap(constants, CrConstantsConfig::getResourceId,
				CrConstantsConfig::getCnName);

		for (BusinessTemplate businessTemplate : businessTemplateList) {
			//校验任务名称
			SingleTemplate singleTemplate = singleTemplateMap.get(businessTemplate.getResourceId());
			CrRoleConfigMissionsScheme rspMissionsScheme = JsonUtil.toJsonObject(singleTemplate.getMissionsScheme(),
					CrRoleConfigMissionsScheme.class);
			Map<String, MissionTemplate> mapping =
					missionTemplateService.getMappingBySchemeJson(singleTemplate.getMissionsScheme());
			validateTaskTile(mapping, rspMissionsScheme.getSchemeData(), businessTemplate.getName());

			//单个训练导出
			log.info("start export single training [{}]", businessTemplate.getName());

			File file = new File(path + File.separator + businessTemplate.getName());
			if (file.exists()) {
				FileUtil.deleteDir(file);
			}
			FileUtil.mkdirs(file.getPath());

			//场景信息导出
			log.info("start export single training [{}] scene", businessTemplate.getName());
			exportScene(businessTemplate.getTopoId(), file.getPath() + File.separator + ENV,
					file.getPath() + File.separator + ENV + File.separator + IMAGES, sceneNumber, disableImageExport);
			log.info("export single training [{}] scene successfully", businessTemplate.getName());

			TrainMD trainMD = BeanUtils.copy(businessTemplate, TrainMD.class);
			trainMD.setTrainType(singleTemplate.getSubType());
			BeanUtils.copyProperties(singleTemplate, trainMD);
			//导出logo
			MdParser mdParser = new MdParser();
			if(!StringUtils.isBlank(businessTemplate.getLogo())){
				String imgUrl = businessTemplate.getLogo();
				trainMD.setLogo(MD_LOGO);
				FileUtil.copyFile(imgUrl, file.getPath() + File.separator + "logo.png", false);
				log.info("export single training [{}] logo.png successfully", businessTemplate.getName());
			}
			//导出README.md
			trainMD.setPreSkills(JsonUtil.toJsonObjectArray(singleTemplate.getPreSkills(), String.class));
			trainMD.setTrainSkills(JsonUtil.toJsonObjectArray(singleTemplate.getTrainSkills(), String.class));
			List<RoleBo> roleBos = new ArrayList<>();
			List<String> roleName = new ArrayList<>();
			roleName.add("学员");
			roleBos.add(new RoleBo("学员组", roleName));
			trainMD.setRoles(roleBos);
			mdParser.write(trainMD, new File(file.getPath() + File.separator + BASE_MD_FILE));
			log.info("export single training [{}] README.md successfully", businessTemplate.getName());
			//导出task
			File taskFile = new File(file.getPath() + File.separator + TASK);
			FileUtil.mkdirs(taskFile.getPath());

			SingleRoleMD singleRoleMD = new SingleRoleMD();
			//获取任务信息
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
			singleRoleMD.setTask(roleBoList);
			mdParser.write(singleRoleMD, new File(taskFile.getPath() + File.separator + "学员.md"));
			log.info("export single training [{}] 学员.md successfully", businessTemplate.getName());
			log.info("export single training [{}] successfully", businessTemplate.getName());
		}
	}


}
