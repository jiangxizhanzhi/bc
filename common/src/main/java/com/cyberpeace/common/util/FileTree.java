package com.cyberpeace.common.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

/**
 * 指代文件系统中的一个文件夹
 * path 相对路径
 * fileMap 子文件名:子文件
 * dirMap 子文件夹名:子文件夹
 *
 * @author SN
 * @date 2024/11/07
 */
@Slf4j
public class FileTree {

	/**
	 * 分隔符
	 */
	public static final String SEPARATOR = "/";

	/**
	 * 相对于根节点的相对路径
	 */
	private final String path;

	/**
	 * 文件映射
	 */
	private final Map<String, File> fileMap;

	/**
	 * dir map （目录映射）
	 */
	private final Map<String, FileTree> dirMap;

	/**
	 * 文件树
	 */
	public FileTree() {
		this("");
	}

	/**
	 * 文件树
	 *
	 * @param path 路径
	 */
	public FileTree(String path) {
		this.path = path;
		dirMap = new HashMap<>();
		fileMap = new HashMap<>();
	}

	/**
	 * 获取文件地图
	 *
	 * @return {@code Map<String, File> }
	 */
	private Map<String, File> getFileMap() {
		return fileMap;
	}

	/**
	 * 获取满路径
	 *
	 * @param name 名称
	 * @return {@code String }
	 */
	public String getFullPath(String name) {
		if (StringUtils.isBlank(path)) {
			return name;
		}
		return path + SEPARATOR + name;
	}

	/**
	 * 添加一个子目录 存在则跳过
	 *
	 * @param pathName 子目录名 不允许多级
	 * @return {@code FileTree }
	 */
	private FileTree getOrAddDir(String pathName) {
		FileTree childTree = dirMap.get(pathName);

		if (childTree == null) {
			String fullPath = getFullPath(pathName);
			childTree = new FileTree(fullPath);
			dirMap.put(pathName, childTree);
		}

		return childTree;
	}

	/**
	 * 向文件夹中添加一个空文件/文件夹
	 *
	 * @param filePath 文件/文件夹的相对路径
	 * @return 添加的文件
	 */
	public File addFile(String filePath) {
		String[] pathList = filePath.split(SEPARATOR);

		FileTree fileTree = this;
		for (int i = 0; i < pathList.length - 1; i++) {
			String pathName = pathList[i];
			fileTree = getOrAddDir(pathName);
		}

		String fileName = pathList[pathList.length - 1];
		File file = new File(fileName);
		if (!StringUtils.isBlank(fileName)) {
			fileTree.getFileMap().put(fileName, file);
		}
		return file;
	}

	/**
	 * 根据名称获取当前文件夹的子文件
	 * 不存在则报错
	 *
	 * @param name 名称
	 * @return {@code File }
	 */
	public File getFileByName(String name) {
		File file = getFileMap().get(name);

		if (file == null) {
			log.error("file is null,name =[{}]", name);
			throw new BusinessException(ErrorCode.FILE_NOT_EXIST, name);
		}

		return file;
	}

	/**
	 * 来自 Stream 输入
	 *
	 * @param inputStream 输入流量
	 * @throws IOException io异常
	 */
	public void inputFromStream(InputStream inputStream) throws IOException {
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);

		while (true) {
			ZipEntry entry = zipInputStream.getNextEntry();
			if (entry == null) {
				break;
			}

			// 文件相对路径
			String filePath = entry.getName();

			// 忽略文件夹本身
			if (filePath.endsWith(SEPARATOR)) {
				continue;
			}

			File file = addFile(filePath);

			OutputStream output = new FileOutputStream(file);
			for (int i = 0; i < entry.getSize(); i++) {
				output.write(zipInputStream.read());
			}
			output.close();

		}
	}

	/**
	 * 输出到流
	 *
	 * @param outputStream 输出流
	 * @throws IOException io异常
	 */
	public void outputToStream(OutputStream outputStream) throws IOException {
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
		compress(zipOutputStream, bufferedOutputStream);
		bufferedOutputStream.close();
		zipOutputStream.close();
	}

	/**
	 * 压缩
	 *
	 * @param zos ZOS
	 * @param bos BOS
	 * @throws IOException io异常
	 */
	private void compress(ZipOutputStream zos, BufferedOutputStream bos) throws IOException {

		for (String dirName : dirMap.keySet()) {
			FileTree childTree = dirMap.get(dirName);
			childTree.compress(zos, bos);
		}
		// 忽略空文件夹

		Map<String, File> fileMap = getFileMap();
		for (String fileName : fileMap.keySet()) {
			String filePath = getFullPath(fileName);
			zos.putNextEntry(new ZipEntry(filePath));

			File file = getFileByName(fileName);
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
