package com.cyberpeace.cr.crbase.service.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.ExportTrainMDService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.integratation.model.scene.SceneDumpContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Maps;

/**
 * 导出训练
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class ExportTrainMDServiceImpl implements ExportTrainMDService {

	public static Map<Integer, ExportTrainMDService> register = Maps.newHashMap();

	public static final String BASE_MD_FILE = "README.md";

	public static final String IMG = "img";

	public static final String WRITE_UP_FILE = "writeup.md";

	public static final String TASK = "task";

	public static final String MD_LOGO = "./logo.png";

	public static final String ENV = "env";

	public static final String IMAGES = "images";

	public static final String VERSION = "version.txt";

	public static final String DOUBLE_BLANK = "  ";

	private static final Logger log = LoggerFactory.getLogger("resourceImport");

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@PostConstruct
	public void init() {
		if (register.containsKey(getCode())) {
			throw new Error("declareMessageType=[" + getCode() + "] repeat");
		}

		register.put(getCode(), this);
	}

	public Integer getCode() {
		return -1;
	}

	/**
	 * 选择类型和导出训练
	 *
	 * @param resourceIds 资源id
	 * @param path        路径
	 * @param type        类型
	 * @throws IOException ioexception
	 */
	@Override
	public void chooseTypeAndExportTrain(List<String> resourceIds, String path, Integer type)
			throws IOException {

		List<BusinessTemplate> businessTemplateList = businessTemplateMapper
				.getAllByIds(resourceIds, type);
		if (businessTemplateList.size() == 0) {
			throw new BusinessException(CrBusinessErrorCode.EXPORT_TRAIN_ID_NOT_EXIST);
		}
		if (businessTemplateList.size() < resourceIds.size()) {
			log.warn("部分id不存在");
		}
		ExportTrainMDService exportTrainMDService = register.get(type);
		exportTrainMDService.exportTrainMD(businessTemplateList,
				path + File.separator + "cr" + File.separator + "场景库");

		OutputStreamWriter osw = new OutputStreamWriter(
				new FileOutputStream(path + File.separator + VERSION), StandardCharsets.UTF_8);
		FileUtil.writeString(IDGeneratorUtil.getID(), osw);
		log.info(VERSION + "导出结束");

	}

	/**
	 * 导出训练
	 *
	 * @param businessTemplateList 业务模板列表
	 * @param path                 路径
	 */
	@Override
	public void exportTrainMD(List<BusinessTemplate> businessTemplateList, String path) {

	}

	public void writeStringWriteUp(String writeUp, File writeUpFile, String title) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(writeUpFile);
			bw = new BufferedWriter(fw);
			bw.write(writeUp);
			bw.flush();
			log.info(title + DOUBLE_BLANK + WRITE_UP_FILE + "导出结束");
		} catch (Exception e) {
			log.error("Markdown content write failed！file_path=[{}], catch error:",
					writeUpFile.getAbsolutePath(), e);
			throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
		} finally {
			try {
				if (null != fw) {
					fw.close();
				}
				if (null != bw) {
					bw.close();
				}
			} catch (IOException e) {}
		}
	}

	public Map<String, String> dumpScene(String topoId, String envPath, String imagePath) {
		SceneDumpContext sceneDumpContext = new SceneDumpContext();
		sceneDumpContext.setId(topoId);
		FileUtil.mkdirs(envPath);
		sceneDumpContext.setEnv_path(envPath);
		FileUtil.mkdirs(imagePath);
		sceneDumpContext.setImage_path(imagePath);
		JSONObject jsonObject = remoteSceneService.sceneDump(sceneDumpContext);
		List<JSONObject> convertInfos = jsonObject.getObject("convert_infos", List.class);
		Map<String, String> convertMap = new HashMap<>();
		for (JSONObject convert : convertInfos) {
			convertMap.put(convert.getString("origin_id"), convert.getString("id"));
		}
		return convertMap;
	}

	/**
	 * 验证任务
	 *
	 * @param missionTemplateMap 任务模板映射
	 * @param schemeData         计划数据
	 * @param trainName          训练名字
	 */
	public void validateTaskTile(Map<String, MissionTemplate> missionTemplateMap,
			List<CrRoleConfigSchemeStage> schemeData, String trainName) {
		List<String> titleList = new ArrayList<>();
		for (CrRoleConfigSchemeStage crRoleConfigSchemeStage : schemeData) {
			for (CrRoleConfigMission crRoleConfigMission : crRoleConfigSchemeStage.getMissions()) {
				MissionTemplate missionTemplate = missionTemplateMap
						.get(crRoleConfigMission.getResourceId());
				if (missionTemplate != null) {
					String taskName = missionTemplate.getTitle();
					if (titleList.contains(taskName)) {
						throw new BusinessException(
								CrBusinessErrorCode.EXPORT_TRAIN_DUPLICATED_TASK_TITLE, trainName,
								taskName);
					}
					titleList.add(taskName);
				}
			}
		}
	}
}
