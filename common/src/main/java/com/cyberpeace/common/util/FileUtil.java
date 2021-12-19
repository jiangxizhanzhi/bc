package com.cyberpeace.common.util;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Lists;

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
				builder.append(content);
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
	 */
	public static void copyFile(String path, String dest) {

		FileChannel input = null;
		FileChannel output = null;

		try {
			input = new FileInputStream(path).getChannel();
			output = new FileOutputStream(dest).getChannel();

			output.transferFrom(input, 0, input.size());
		} catch (Exception e) {
			log.error("copyFile: File copy failed! path=[{}], dest=[{}]", path, dest);
			throw new BusinessException(ErrorCode.FILE_COPY_FAIL);
		} finally {
			try {
				input.close();
				output.close();
			} catch (Exception e) {
				// 不管
			}

		}
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
}
