package com.cyberpeace.cr.cebase.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileTree;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.cebase.resource.Collector;
import com.cyberpeace.cr.cebase.resource.Dispatcher;
import com.cyberpeace.cr.cebase.resource.Representation;
import com.cyberpeace.cr.cebase.resource.ResourceConfig;
import com.cyberpeace.cr.cebase.resource.impl.CollectorImpl;
import com.cyberpeace.cr.cebase.resource.impl.DispatcherImpl;
import com.cyberpeace.cr.cebase.service.CrEventService;

import lombok.Data;

@RestController
@RequestMapping("/cr/ce_base")
public class CrEventController {

	private final String INDEX_DATA_JSON_FILE = "data.json";

	@Autowired
	private CrEventService crEventService;

	@PostMapping("/load")
	public BaseRsp<String> load(@RequestParam("file") @NotNull MultipartFile file) {
		FileTree fileTree = new FileTree();

		try {
			fileTree.inputFromStream(file.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		IndexData indexData = readIndexData(fileTree);

		loadData(indexData);

		return new BaseRsp<>();
	}

	@PostMapping("/dump")
	public void dump(IdsInput input, HttpServletResponse response) {
		List<String> ids = input.getIds();

		FileTree fileTree = new FileTree();

		IndexDataOutput indexDataOutput = dumpData(ids);

		writeIndexData(fileTree, indexDataOutput);

		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=dump.zip");
		try {
			fileTree.outputToStream(response.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 读取data.json文件
	 * */
	private IndexData readIndexData(FileTree fileTree) {
		File file = fileTree.getFileByName(INDEX_DATA_JSON_FILE);
		String text = FileUtil.textFromFile(file);
		IndexData indexData = JsonUtil.toJsonObject(text, IndexData.class);
		if (indexData == null) {
			throw new RuntimeException("Fail to read data.json");
		}
		return indexData;
	}

	/**
	 * 写入data.json文件
	 * */
	private void writeIndexData(FileTree fileTree, IndexDataOutput indexDataOutput) {
		String text = JsonUtil.toJsonString(indexDataOutput);

		File file = fileTree.addFile(INDEX_DATA_JSON_FILE);

		try {
			FileUtil.textToFile(file, text);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 根据data.json文件的配置导入
	 * */
	private void loadData(IndexData indexData) {
		Map<String, List<JSONObject>> dataMap = indexData.getData();
		String file = indexData.getFile();
		// # 资源文件复制添加
		Dispatcher dispatcher = getDispatcher();
		dispatcher.addFiles(file);

		for (String label : dataMap.keySet()) {
			List<JSONObject> jsonObjectList = dataMap.get(label);
			ResourceConfig resourceConfig = getResourceConfig(label);

			for (JSONObject jsonObject : jsonObjectList) {
				resourceConfig.load(jsonObject, dispatcher);
			}
		}
	}

	/**
	 * 导出场景模板 生成data.json配置
	 * */
	private IndexDataOutput dumpData(List<String> ids) {
		Collector collector = getCollector();

		Map<String, List<Representation>> dataMap = new HashMap<>();

		List<CrEvent> crEventList = crEventService.listByIds(ids);

		for (CrEvent crEvent : crEventList) {
			int ceType = crEvent.getCeType();
			ResourceConfig resourceConfig = getResourceConfig(ceType);
			Representation representation = resourceConfig.dump(crEvent, collector);

			String label = resourceConfig.getLabel();
			if (!dataMap.containsKey(label)) {
				dataMap.put(label, new ArrayList<>());
			}
			dataMap.get(label).add(representation);
		}

		IndexDataOutput indexDataOutput = new IndexDataOutput();
		indexDataOutput.setData(dataMap);
		indexDataOutput.setFile(collector.getFiles());
		return indexDataOutput;
	}

	private ResourceConfig getResourceConfig(String label) {
		for (ResourceConfig resourceConfig : getResourceConfigs()) {
			if (Objects.equals(resourceConfig.getLabel(), label)) {
				return resourceConfig;
			}
		}

		throw new RuntimeException("ResourceConfig for label[" + label + "] not found");

	}

	private ResourceConfig getResourceConfig(int ceType) {
		for (ResourceConfig resourceConfig : getResourceConfigs()) {
			if (resourceConfig.getCeType() == ceType) {
				return resourceConfig;
			}
		}

		throw new RuntimeException("ResourceConfig for ceType[" + ceType + "] not found");
	}

	private Collection<ResourceConfig> getResourceConfigs() {
		return SpringContextUtil.getBeansOfType(ResourceConfig.class).values();
	}

	private Dispatcher getDispatcher() {
		return new DispatcherImpl();
	}

	private Collector getCollector() {
		return new CollectorImpl();
	}

}

@Data
class IdsInput {

	@NotNull
	private List<String> ids;
}

/**
 * data.json数据结构
 * */
@Data
class IndexData {

	private Map<String, List<JSONObject>> data;

	private String file;
}

@Data
class IndexDataOutput {

	private Map<String, List<Representation>> data;

	private String file;
}