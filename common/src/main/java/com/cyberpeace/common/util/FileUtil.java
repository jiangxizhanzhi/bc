package com.cyberpeace.common.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.CommonConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtil {

	public static File saveFile(MultipartFile file, String basePath) throws IOException {
		String originalFilename = file.getOriginalFilename();
		assert originalFilename != null;
		String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
		String uuid = String.valueOf(UUID.randomUUID());
		String fileName = uuid + fileSuffix;

		mkdirs(basePath);
		File newFile = new File(basePath, fileName);
		file.transferTo(newFile);
		return newFile;
	}

	public static File mkdirs(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 获取文件名称
	 * @param filePath 文件路径
	 * @param suffix 是否包含后缀
	 * @return
	 */
	public static String getFileName(String filePath, Boolean suffix) {

		File file = new File(filePath);

		// 文件加返回名称
		if (file.isDirectory()) {
			return file.getName();
		}

		// 无后缀名称直接返回文件名
		if (file.getName().indexOf(CommonConstant.PERIOD) != -1) {
			return file.getName();
		}

		return !suffix
				? file.getName().substring(CommonConstant.ZERO, file.getName().lastIndexOf("."))
				: file.getName();
	}

	/**
	 * 读取文件内容
	 * @param file
	 * @return
	 */
	public static String getContent(File file) {
		BufferedReader br = null;
		StringBuilder builder = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

			String content = "";
			while ((content = br.readLine()) != null) {
				builder.append(content + System.lineSeparator());
			}

			return builder.toString();
		} catch (Exception e) {
			log.error("文件读取失败！ filePath={} error:", file.getAbsolutePath(), e);
			throw new RuntimeException();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// 不做任何处理
			}
		}
	}

	/**
	 * 以行读取文件 返回行数据列表
	 * @param file
	 * @return
	 */
	public static List<String> listContent(File file) {
		BufferedReader br = null;
		List<String> contentList = Lists.newArrayList();

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

			String content;
			while ((content = br.readLine()) != null) {
				contentList.add(content);
			}

			return contentList;
		} catch (Exception e) {
			log.error("文件读取失败！ filePath={} error:", file.getAbsolutePath(), e);
			throw new RuntimeException();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// 不做任何处理
			}
		}
	}

	/**
	 * 文件拷贝
	 * @param path
	 * @param dest
	 * @param rename true=已存在修改名称 false已存在则覆盖
	 */
	public static String copyFile(String path, String dest, boolean rename) {

		File destFile = new File(dest);

		// 文件重命名
		if (rename && destFile.exists()) {
			dest = reName(dest);
		}

		// 父级文件目录创建
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}

		// 文件拷贝
		FileChannel input = null;
		FileChannel output = null;
		try {
			input = new FileInputStream(path).getChannel();
			output = new FileOutputStream(dest).getChannel();

			output.transferFrom(input, 0, input.size());
		} catch (Exception e) {
			log.error("copyFile: File copy failed! path=[{}], dest=[{}], catch error:", path, dest,
					e);
			throw new BusinessException(ErrorCode.FILE_COPY_FAIL);
		} finally {
			try {
				input.close();
				output.close();
			} catch (Exception e) {
				// 不管
			}

		}

		return dest;
	}

	/**
	 * 将字符串写入文件
	 * @param data 要写入的字符串
	 * @param osw 输出流
	 * @throws IOException
	 */
	public static void writeString(String data, OutputStreamWriter osw) throws IOException {
		try {
			osw.write(data);
		} catch (Exception e) {
			log.error("error occurred when writing data.json", e);
			throw new IOException(e);
		} finally {
			try {
				osw.flush();
				osw.close();
			} catch (IOException e) {
				log.error("failed to close OutputStreamWriter", e);
				throw new IOException(e);
			}
		}
	}

	/**
	 * @Author 陈卓
	 * 生成文件响应
	 * @param file 返回的文件
	 * @param delete 是否删除本地文件
	 * @param response 响应
	 */
	public static void setResponse(File file, Boolean delete, HttpServletResponse response) {
		// 以流的形式下载文件。
		InputStream fis = null;
		OutputStream outputStream = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			if (fis != null) {
				fis.close();
			}
			// 清空response
			response.reset();

			// 设置返回
			response.setContentType("application/x-msdownload;charset=UTF-8");
			String fileName = new String(file.getName().getBytes("gb2312"), "iso8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			outputStream = response.getOutputStream();
			outputStream.write(buffer);
			outputStream.flush();
		} catch (IOException e) {
			log.error("setResponse error:", e);
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				log.error("setResponse error:", e);
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("setResponse error:", e);
				}
			}

			if (delete && null != file) {
				file.delete();
			}
		}
	}

	/**
	 * 递归删除文件夹
	 * @param dir 要删除的文件夹
	 * @return true: 删除成功； false: 删除失败
	 */
	public static Boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			assert children != null;
			for (File file : children) {
				if (!deleteDir(file)) {
					log.error("failed to delete file {}", file);
					return false;
				}
			}
		}
		return dir.delete();
	}

	/**
	 * 文件目录拷贝 如果存在则覆盖
	 * @param dir
	 * @param destDir
	 * @param self true=目录拷贝进目标文件夹 false=把目录下文件拷贝进目标文件夹
	 */
	public static void copyDirectory(String dir, String destDir, Boolean self) {

		// 不存在不做任何操作
		File originFile = new File(dir);
		if (!originFile.exists()) {
			return;
		}

		File destFile = new File(destDir);
		if (!destFile.exists()) {
			destFile.mkdirs();
		}

		String selfPre = originFile.getParentFile().getAbsolutePath();
		String noSelfPre = originFile.getAbsolutePath();

		// 拷贝目录 文件夹则创建对应文件夹 文件则拷贝文件
		List<String> childrenFile = listDirs(dir, true);
		for (String direction : childrenFile) {
			File file = new File(direction);

			// 过滤不必要的文件夹 隐藏文件夹
			if (file.getName().startsWith(".")) {
				continue;
			}
			// 创建对应目录
			if (file.isDirectory()) {
				String pathName = self ? file.getAbsolutePath().replace(selfPre, "")
						: file.getAbsolutePath().replace(noSelfPre, "");
				File copyDir = new File(destDir, pathName);
				copyDir.mkdirs();
				copyDirectory(file.getAbsolutePath(), copyDir.getAbsolutePath(), false);
			}

			// 拷贝对应文件
			if (file.isFile()) {
				String pathName = self ? file.getAbsolutePath().replace(selfPre, "")
						: file.getAbsolutePath().replace(noSelfPre, "");
				copyFile(file.getAbsolutePath(), new File(destDir, pathName).getAbsolutePath(),
						false);
			}
		}
	}

	/**
	 * 根据传入的根目录 获取对应的目录名称的文件路径列表
	 * @param rootFile
	 * @param depth 深度 无限往下则为null
	 * @param includeFile 是否包含文件 true包含 false不包含
	 * @return
	 */
	public static List<String> listFileByFile(File rootFile, Integer depth, Boolean includeFile) {

		// 文件校验返回
		if (!rootFile.exists()) {
			return Lists.newArrayList();
		}

		// 是包含文件过滤
		if (!includeFile && rootFile.isFile()) {
			return Lists.newArrayList();
		}

		// 根据审读计算是否继续往下
		if (depth != null && depth > 0) {
			depth--;
		} else if (depth != null && depth <= 0) {
			return Lists.newArrayList();
		}

		// 遍历文件返回
		File[] files = rootFile.listFiles();
		List<String> allDir = Lists.newArrayList();
		for (File file : files) {
			allDir.add(file.getAbsolutePath());
			List<String> childrenDirs = listFileByFile(file, depth, includeFile);
			allDir.addAll(childrenDirs);
		}

		return allDir;
	}

	/**
	 * 获取目录下的文件夹
	 *
	 * @param path
	 * @param includeFile true 包含文件 false不包含文件
	 * @return
	 */
	public static List<String> listDirs(String path, boolean includeFile) {

		File rootFile = new File(path);
		File[] files = rootFile.listFiles();

		List<String> directions = Arrays.stream(files)
				.filter(file -> !file.getName().startsWith("."))
				.filter(file -> includeFile ? true : file.isDirectory()).map(File::getAbsolutePath)
				.sorted().collect(Collectors.toList());
		return directions;
	}

	/**
	 * 获取文件夹下的一个文件
	 * @param path
	 * @return
	 */
	public static String getOneFile(String path) {
		File file = new File(path);
		if (file.isFile()) {
			return "";
		}

		List<String> dirs = listDirs(file.getAbsolutePath(), true);
		return CollectionUtils.isNotEmpty(dirs) ? dirs.get(CommonConstant.ZERO) : "";
	}

	/**
	 * 文件重命名
	 * @param dest
	 * @return
	 */
	private static String reName(String dest) {
		String suffix = dest.substring(dest.lastIndexOf("."));
		String filePath = dest.substring(0, dest.lastIndexOf("."));

		return filePath + IDGeneratorUtil.getID() + suffix;
	}

}
