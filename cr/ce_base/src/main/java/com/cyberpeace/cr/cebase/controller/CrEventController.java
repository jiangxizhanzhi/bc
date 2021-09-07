package com.cyberpeace.cr.cebase.controller;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.cebase.resource.*;
import com.cyberpeace.cr.cebase.resource.impl.CollectorImpl;
import com.cyberpeace.cr.cebase.resource.impl.DispatcherImpl;
import com.cyberpeace.cr.cebase.service.CrEventService;

import lombok.Data;

@RestController
@RequestMapping("/cr/ce_base")
public class CrEventController {

	@Autowired
	private CrEventService crEventService;

	@PostMapping("/load")
	public BaseRsp<String> load(@RequestParam("file") @NotNull MultipartFile file) {
		String tempPath = getTempPath();

		try {
			uploadedFileToPath(tempPath, file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		IndexData indexData = readIndexData(tempPath);

		loadData(indexData);

		clearPath(tempPath);

		return new BaseRsp<>();
	}

	@PostMapping("/dump")
	public void dump(IdsInput input, HttpServletResponse response) {
		List<String> ids = input.getIds();

		String tempPath = getTempPath();

		IndexData indexData = dumpData(ids);

		writeIndexData(tempPath, indexData);

		try {
			pathToDownloadFile(tempPath, response);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		clearPath(tempPath);

	}

	/**
	 * 读取data.json文件
	 * */
	private IndexData readIndexData(String filePath) {
		String path = filePath + File.separator + Constant.INDEX_DATA_JSON_FILE;
		String text = FileUtil.textFromFile(path);
		IndexData indexData = JsonUtil.toJsonObject(text, IndexData.class);
		if (indexData == null) {
			throw new RuntimeException("Fail to read data.json");
		}
		return indexData;
	}

	/**
	 * 写入data.json文件
	 * */
	private void writeIndexData(String filePath, IndexData indexData) {
		String path = filePath + File.separator + Constant.INDEX_DATA_JSON_FILE;
		String text = JsonUtil.toJsonString(indexData);
		FileUtil.textToFile(path, text);
	}

	/**
	 * 根据data.json文件的配置导入
	 * */
	private void loadData(IndexData indexData) {
		Map<String, List<Representation>> dataMap = indexData.getData();
		String file = indexData.getFile();
		// # 资源文件复制添加
		Dispatcher dispatcher = getDispatcher();
		dispatcher.addFiles(file);

		for (String label : dataMap.keySet()) {
			List<Representation> representationList = dataMap.get(label);

			ResourceConfig resourceConfig = getResourceConfig(label);
			Loader loader = resourceConfig.getLoader();

			for (Representation representation : representationList) {
				loader.load(representation, dispatcher);
			}
		}
	}

	/**
	 * 导出场景模板 生成data.json配置
	 * */
	private IndexData dumpData(List<String> ids) {
		Collector collector = getCollector();

		Map<String, List<Representation>> dataMap = new HashMap<>();

		List<CrEvent> crEventList = crEventService.listByIds(ids);

		for (CrEvent crEvent : crEventList) {
			int ceType = crEvent.getCeType();
			ResourceConfig resourceConfig = getResourceConfig(ceType);
			Dumper dumper = resourceConfig.getDumper();
			Representation representation = dumper.dump(crEvent, collector);

			String label = resourceConfig.getLabel();
			if (!dataMap.containsKey(label)) {
				dataMap.put(label, new ArrayList<>());
			}
			dataMap.get(label).add(representation);
		}

		IndexData indexData = new IndexData();
		indexData.setData(dataMap);
		indexData.setFile(collector.getFiles());
		return indexData;
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

	/**
	 * 创建一个临时文件夹 返回其路径
	 * */
	private String getTempPath() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String path = "/tmp/cr/resource/" + uuid;

		File file = new File(path);

		boolean created = file.mkdirs();
		if (!created) {
			throw new RuntimeException("Fail to generate temp path " + path);
		}

		return path;
	}

	/**
	 * 删除临时文件夹
	 * */
	private void clearPath(String path) {
		File file = new File(path);
		boolean deleted = file.delete();
		if (!deleted) {
			throw new RuntimeException("Fail to remove temp path " + path);
		}
	}

	/**
	 * 获取接口请求中的zip文件 解压到指定目录
	 * */
	private void uploadedFileToPath(String path, @NotNull MultipartFile file) throws IOException {
		InputStream inputStream = file.getInputStream();
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);

		while (true) {
			ZipEntry entry = zipInputStream.getNextEntry();
			if (entry == null) {
				break;
			}

			String fileName = path + File.separator + entry.getName();
			File outFile = new File(fileName);

			if (fileName.endsWith("/")) {

				outFile.mkdirs();

			} else {

				File parent = outFile.getParentFile();
				if (!parent.exists()) {
					parent.mkdirs();
				}

				boolean created = outFile.createNewFile();
				if (!created) {
					throw new RuntimeException("Fail to create file: " + fileName);
				}

				OutputStream output = new FileOutputStream(outFile);
				for (int i = 0; i < entry.getSize(); i++) {
					output.write(zipInputStream.read());
				}
				output.close();
			}
		}
	}

	/**
	 * 把指定目录中的文件压缩到zip文件
	 * */
	private void pathToDownloadFile(String path, HttpServletResponse response) throws IOException {
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=dump.zip");

		ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
		File file = new File(path);
		String base = file.getName();

		compress(zipOutputStream, bufferedOutputStream, file, base);
		bufferedOutputStream.close();
		zipOutputStream.close();
	}

	private void compress(ZipOutputStream zos, BufferedOutputStream bos, File file, String base)
			throws IOException {
		if (file.isDirectory()) {
			File[] children = file.listFiles();
			assert children != null;
			if (children.length == 0) {
				// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
				zos.putNextEntry(new ZipEntry(base + File.separator));
			} else {
				// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
				for (File child : children) {
					compress(zos, bos, child, base + File.separator + child.getName());
				}
			}
		} else {
			// 如果不是文件夹，即为文件，则先写入目录进入点，之后将文件写入zip文件中
			zos.putNextEntry(new ZipEntry(base));

			FileInputStream fos = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fos);

			while (true) {
				int tag = bis.read();
				if (tag == -1) {
					break;
				}
				bos.write(tag);
			}

			bis.close();
			fos.close();
		}
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

	private Map<String, List<Representation>> data;

	private String file;
}

interface Constant {

	String INDEX_DATA_JSON_FILE = "data.json";
}
