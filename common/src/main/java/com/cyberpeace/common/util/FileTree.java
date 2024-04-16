package com.cyberpeace.common.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

/**
 * 指代文件系统中的一个文件夹
 * path 相对路径
 * fileMap 子文件名:子文件
 * dirMap 子文件夹名:子文件夹
 * */
@Slf4j
public class FileTree {

	public static final String SEPARATOR = "/";

	/**
	 * 相对于根节点的相对路径
	 */
	private final String path;

	private final Map<String, File> fileMap;

	private final Map<String, FileTree> dirMap;

	public FileTree() {
		this("");
	}

	public FileTree(String path) {
		this.path = path;
		dirMap = new HashMap<>();
		fileMap = new HashMap<>();
	}

	private Map<String, File> getFileMap() {
		return fileMap;
	}

	public String getFullPath(String name) {
		if (StringUtils.isBlank(path)) {
			return name;
		}
		return path + SEPARATOR + name;
	}

	/**
	 * 添加一个子目录 存在则跳过
	 * @param pathName 子目录名 不允许多级
	 */
	private FileTree getOrAddDir(String pathName) {
		dirMap.putIfAbsent(pathName, new FileTree(getFullPath(pathName)));
		return dirMap.get(pathName);
	}

	/**
	 * 向文件夹中添加一个空文件/文件夹
	 * @param filePath 文件/文件夹的相对路径
	 * @return 添加的文件
	 * */
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
	 * */
	public File getFileByName(String name) {
		File file = getFileMap().get(name);

		if (file == null) {
			log.error("file is null,name =[{}]", name);
			throw new BusinessException(ErrorCode.FILE_NOT_EXIST, name);
		}

		return file;
	}

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

			try (OutputStream output = new FileOutputStream(file)) {
				for (int i = 0; i < entry.getSize(); i++) {
					output.write(zipInputStream.read());
				}
			}
		}
	}

	public void outputToStream(OutputStream outputStream) throws IOException {
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(zipOutputStream);
		compress(zipOutputStream, bufferedOutputStream);
		bufferedOutputStream.close();
		zipOutputStream.close();
	}

	private void compress(ZipOutputStream zos, BufferedOutputStream bos) throws IOException {

		for (FileTree childTree : dirMap.values()) {
			childTree.compress(zos, bos);
		}
		// 忽略空文件夹

		Map<String, File> map = getFileMap();
		for (String fileName : map.keySet()) {
			String filePath = getFullPath(fileName);
			zos.putNextEntry(new ZipEntry(filePath));

			File file = getFileByName(fileName);
			try (FileInputStream fos = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fos)) {

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

}
