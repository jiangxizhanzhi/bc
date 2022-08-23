package com.cyberpeace.cr.singletrain.service.impl;

import com.alibaba.fastjson.JSONArray;
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
import com.cyberpeace.cr.crbase.service.context.md.*;
import com.cyberpeace.cr.crbase.service.context.convert.BusinessTypeConvert;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cr.crbase.service.impl.ExportTrainMDServiceImpl;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleTemplateMapper;
import com.cyberpeace.cs.service.context.RoleBo;
import com.cyberpeace.integratation.model.scene.SceneDumpContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

	@Autowired
	private RemoteSceneService remoteSceneService;

	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	@Override
	public Integer getCode() {
		return BusinessTypeConvert.SINGLE.to();
	}

	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path) {

		if(businessTemplateList.size() == 0){
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
			//单个训练导出
			log.info("单兵训练【" + businessTemplate.getName() + "】开始导出");

			File file = new File(path + File.separator + businessTemplate.getName());
			FileUtil.mkdirs(file.getPath());

			SingleTemplate singleTemplate = singleTemplateMap.get(businessTemplate.getResourceId());
			TrainMD trainMD = BeanUtils.copy(businessTemplate, TrainMD.class);
			trainMD.setTrainType(singleTemplate.getSubType());
			BeanUtils.copyProperties(singleTemplate, trainMD);
			//导出logo
			MdParser mdParser = new MdParser();
			if(!StringUtils.isBlank(businessTemplate.getLogo())){
				String imgUrl = businessTemplate.getLogo();
				trainMD.setLogo(MD_LOGO);
				FileUtil.copyFile(imgUrl, file.getPath() + File.separator + "logo.png", false);

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
			//导出task
			File taskFile = new File(file.getPath() + File.separator + TASK);
			if (!taskFile.exists()) {
				taskFile.mkdir();
			}

			SingleRoleMD singleRoleMD = new SingleRoleMD();
			//获取任务信息
			CrRoleConfigMissionsScheme rspMissionsScheme = JsonUtil.toJsonObject(singleTemplate.getMissionsScheme(),
					CrRoleConfigMissionsScheme.class);
			Map<String, MissionTemplate> mapping =
					missionTemplateService.getMappingBySchemeJson(singleTemplate.getMissionsScheme());
			List<RoleBo> roleBoList = new ArrayList<>();

			for (CrRoleConfigSchemeStage crRoleConfigSchemeStage : rspMissionsScheme.getSchemeData()) {
				//单个任务导出
				RoleBo roleBo = new RoleBo();
				List<String> taskList = new ArrayList<>();
				for (CrRoleConfigMission crRoleConfigMission : crRoleConfigSchemeStage.getMissions()) {
					MissionTemplate missionTemplate = mapping.get(crRoleConfigMission.getResourceId());
					taskList.add(missionTemplate.getTitle());

					File task = new File(taskFile.getPath() + File.separator + missionTemplate.getTitle());
					if (!task.exists()) {
						task.mkdir();
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
							for (int i = 0; i < options.size(); i++) {
								optionList.add(options.getJSONObject(i)
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
							for (int i = 0; i < options.size(); i++) {
								optionList.add(options.getJSONObject(i)
													  .getString("optionValue"));
							}
							multipleChoiceMD.setOptions(optionList);
							multipleChoiceMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
							mdParser.write(multipleChoiceMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
							break;
						case 7:
							//其他
							FileMD fileMD = BeanUtils.copy(missionTemplate, FileMD.class);
							fileMD.setTaskType(constantMap.get(missionTemplate.getCategoryId()));
							mdParser.write(fileMD, new File(task.getPath() + File.separator + BASE_MD_FILE));
							break;
					}
					//导出writeUp
					String writeUp = missionTemplate.getWriteUp();
					//导出img
					File img = new File(task.getPath() + File.separator + IMG);
					FileUtil.mkdirs(img.getPath());
					List<String> strList = LocalStringUtil.getMatchStr(writeUp, PatternConstant.MARKDOWN_IMAGE);
					for (int i = 0; i < strList.size(); i++) {
						String str = strList.get(i);
						String imgUrl = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
						FileUtil.copyFile(imgUrl, img.getPath() + File.separator + (i + 1) + "." + FileUtil.getFileSuffix(imgUrl), false);
						imgUrl = "./img/" + (i+1) + "." + FileUtil.getFileSuffix(imgUrl);
						writeUp = writeUp.replace(str, String.format(MdConstant.ONLY_URL_IMG,imgUrl));
					}

					FileWriter fw = null;
					BufferedWriter bw = null;
					File writeUpFile = new File(task.getPath() + File.separator + WRITE_UP_File);
					try{
						fw = new FileWriter(writeUpFile);
						bw = new BufferedWriter(fw);
						bw.write(writeUp);
						bw.flush();
					}catch (Exception e) {
						log.error("Markdown content write failed！file_path=[{}], catch error:", file.getAbsolutePath(), e);
						throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
					} finally {
						try {
							if (null != fw) {
								fw.close();
							}
							if (null != bw) {
								bw.close();
							}
						} catch (IOException e) {
						}
					}
				}
				roleBo.setTeamName(crRoleConfigSchemeStage.getStage());
				roleBo.setRoleName(taskList);
				roleBoList.add(roleBo);
			}
			singleRoleMD.setTask(roleBoList);
			mdParser.write(singleRoleMD, new File(taskFile.getPath() + File.separator + "学员.md"));
			//场景信息导出
			SceneDumpContext sceneDumpContext = new SceneDumpContext();
			sceneDumpContext.setId(businessTemplate.getTopoId());
			File envFile = new File(file.getPath() + File.separator + ENV);
			FileUtil.mkdirs(envFile.getPath());
			sceneDumpContext.setEnv_path(envFile.getPath());
			File imagesFile = new File(envFile.getPath() + File.separator + IMAGES);
			FileUtil.mkdirs(imagesFile.getPath());
			sceneDumpContext.setImage_path(imagesFile.getPath());
			remoteSceneService.sceneDump(sceneDumpContext);

			log.info("单兵训练【" + businessTemplate.getName() + "】导出结束");
		}
	}


}
