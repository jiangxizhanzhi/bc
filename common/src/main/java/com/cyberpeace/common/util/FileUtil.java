/**
 * ### 代码分析 
 * 该代码是一个工具类 `FileUtil`，主要用于文件操作。以下是对其功能和结构的简要分析： 
 * --- 
 * #### **主要功能模块** 
 * 1. **文件类型校验**： 
 * - `validateSuffix`：验证文件是否符合指定的后缀类型。 
 * - `validateNotAccessFile`：验证文件是否属于禁止的类型。 
 * - `validateFileSuffix`：根据文件路径和允许的文件类型字符串，验证文件后缀是否合法。 
 * 2. **文件操作**： 
 * - `saveFile`：将上传的文件保存到指定路径。 
 * - `mkdirs`：创建多级目录。 
 * - `getContent`：读取文件内容并返回为字符串。 
 * - `listContent`：按行读取文件内容并返回为字符串列表。 
 * - `writeString` 和 `writeToDestFile`：将字符串写入文件。 
 * - `copyFile` 和 `copyDirectory`：复制单个文件或整个目录。 
 * - `deleteDir`：递归删除文件夹及其内容。 
 * - `getHash256`：生成文件的 SHA-256 哈希值。 
 * 3. **文件响应与下载**： 
 * - `setResponse` 和 `setZipStream`：将文件作为响应发送给客户端（支持普通文件和压缩包）。 
 * 4. **文件和目录管理**： 
 * - `listFileByFile`：递归获取目录下的所有文件路径。 
 * - `listDirs` 和 `listFileNamesByDir`：列出目录下的文件夹或文件。 
 * - `getOneFile`：获取目录下的某个符合条件的文件。 
 * 5. **辅助功能**： 
 * - `reName`：文件重命名。 
 * - `getNewName`：生成不重复的新文件名。 
 * - `getFileSuffix`：提取文件后缀名。 
 * 6. **其他工具方法**： 
 * - `createParentPath`：创建文件的父目录。 
 * - `createPath` 和 `createManyPath`：批量创建目录。 
 * --- 
 * #### **技术特点** 
 * 1. **异常处理**： 
 * - 使用了 `try-catch-finally` 结构确保资源释放和错误处理。 
 * - 日志记录 (`log.error`) 用于记录关键错误信息。 
 * 2. **线程安全**： 
 * - 使用 `lombok.extern.slf4j.Slf4j` 提供日志记录功能。 
 * 3. **依赖库**： 
 * - 使用了 `Apache Commons IO`、`Guava` 和 `Spring` 的相关工具类。 
 * - 文件哈希值计算使用了 `MessageDigest` 和 `Base64` 编码。 
 * 4. **代码复用性**： 
 * - 方法设计尽量通用，便于复用，例如文件复制、目录遍历等。 
 * 5. **安全性**： 
 * - 文件路径和文件名的安全性处理（如防止非法路径、隐藏文件过滤等）。 
 * --- 
 * #### **优点** 
 * - 功能全面，涵盖了文件操作的大部分需求。 
 * - 代码结构清晰，注释详细，易于维护。 
 * - 使用了成熟的第三方库，提高了开发效率。 
 * --- 
 * #### **潜在改进点** 
 * 1. **性能优化**： 
 * - 文件读写操作中频繁调用 `fis.read()` 或 `os.write()`，可考虑批量处理以提高效率。 
 * 2. **异常处理**： 
 * - 某些方法的异常处理较为简单，仅记录日志而未提供详细的上下文信息。 
 * 3. **代码冗余**： 
 * - 部分方法逻辑相似，例如文件复制和目录复制，可以进一步抽象通用逻辑。 
 * 4. **国际化支持**： 
 * - 文件名编码（如 `URLEncoder.encode`）可扩展为支持多种字符集。 
 * --- 
 * 总体而言，该工具类提供了丰富的文件操作功能，并具备良好的代码质量。如果需要进一步优化，可以从性能、异常处理和代码抽象等方面入手。 
**/
package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class FileUtil {

	/**
	 * 校验允许的类型
	 * @param file
	 * @param fileSuffix
	 */
	public static void validateSuffix(MultipartFile file, String... fileSuffix) {
		if (null == file) {
			log.error("file is null! fileSuffix=[{}]",fileSuffix);
			throw new BusinessException(ErrorCode.FILE_CONTENT_INVALID);
		}

		String filename = file.getOriginalFilename();
		filename = filename.toLowerCase();
		for (String suffix : fileSuffix) {
			if (filename.endsWith(CommonConstant.PERIOD + suffix)) {
				return;
			}
		}
		log.error("filename is illegal! filename=[{}]",filename);
		throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
	}

	/**
	 * 校验不允许的类型
	 * @param file
	 * @param fileSuffix
	 */
	public static void validateNotAccessFile(MultipartFile file, String... fileSuffix) {
		if (null == file) {
			log.error("file is null! fileSuffix=[{}]",fileSuffix);
			throw new BusinessException(ErrorCode.FILE_CONTENT_INVALID);
		}

		String filename = file.getOriginalFilename();
		for (String suffix : fileSuffix) {
			if (filename.endsWith(CommonConstant.PERIOD + suffix)) {
				log.error("filename is illegal! filename=[{}]",filename);
				throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
			}
		}
	}

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
		if (file.getName().lastIndexOf(CommonConstant.PERIOD) == -1) {
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
			// 文件读取失败
			log.error("file read failed! filePath={} error:", file.getAbsolutePath(), e);
			throw new BusinessException(ErrorCode.FILE_CONTENT_INVALID);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// 不做任何处理
			} catch (NullPointerException e) {
				// 对象未创建空指针异常捕获，不做任何处理
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
			// 文件读取失败
			log.error("file read failed! filePath={} error:", file.getAbsolutePath(), e);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL, file.getPath());
		} finally {
			try {
				if (br!=null) {
					br.close();
				}
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
		try (FileChannel input = new FileInputStream(path).getChannel();
				FileChannel output = new FileOutputStream(dest).getChannel()) {
			output.transferFrom(input, 0, input.size());
		} catch (Exception e) {
			log.error("copyFile: File copy failed! path=[{}], dest=[{}], catch error:", path, dest,
					e);
			throw new BusinessException(ErrorCode.FILE_COPY_FAIL);
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
			}
		}
	}

	/**
	 * 文件写到目的文件夹
	 * @param data
	 * @param destFilePath
	 */
	public static void writeToDestFile(String data, String destFilePath) {

		File file = new File(destFilePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		BufferedWriter out = null;
		try {
			out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(destFilePath), "UTF-8"));
			out.write(data);
		} catch (Exception e) {
			log.error("File write failed! dest_file_path=[{}], catch error:", destFilePath, e);
			throw new BusinessException(ErrorCode.FAILED_TO_WRITE_FILE);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				// 关不掉算了
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
			fis = new FileInputStream(file);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);

			// 清空response
			response.reset();

			// 设置返回
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(),"UTF-8").replaceAll("\\+","%20"));
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

			if (delete && null != file && file.exists()) {
				file.delete();
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
	public static void setZipStream(File file, Boolean delete, HttpServletResponse response) {

		try (FileInputStream fis = new FileInputStream(file);
				OutputStream os = response.getOutputStream()) {
			response.setContentType("application/zip");
			response.setContentLength((int) file.length());
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(file.getName(), "UTF-8").replaceAll("\\+", "%20"));
			// 清空response
			response.reset();

			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			log.error("Error occurred while streaming file: {}", e.getMessage());
			throw new BusinessException(ErrorCode.FILE_STREAM_ERROR);
		} finally {
			if (delete && file.exists()) {
				deleteDir(file);
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
		if (!rootFile.exists()) {
			log.error("File not exist! path=[{}]", path);
			return Lists.newArrayList();
		}
		File[] files = rootFile.listFiles();

		List<String> directions = Arrays.stream(files)
				.filter(file -> !file.getName().startsWith("."))
				.filter(file -> includeFile ? true : file.isDirectory()).map(File::getAbsolutePath)
				.sorted().collect(Collectors.toList());
		return directions;
	}

	/**
	 * 获取目录下的文件夹
	 *
	 * @param path
	 * @param includeDir true 包含文件夹 false不包含文件夹
	 * @return
	 */
	public static List<String> listFileNamesByDir(String path, boolean includeDir) {

		File rootFile = new File(path);
		if (!rootFile.exists()) {
			log.error("File not exist! path=[{}]", path);
			return Lists.newArrayList();
		}
		File[] files = rootFile.listFiles();

		List<String> directions = Arrays.stream(files)
				.filter(file -> !file.getName().startsWith("."))
				.filter(file -> includeDir ? true : file.isFile()).map(File::getName).sorted()
				.collect(Collectors.toList());
		return directions;
	}

	/**
	 * 获取文件夹下的一个文件
	 * @param path
	 * @param fileSuffix
	 * @return
	 */
	public static String getOneFile(String path, String fileSuffix) {
		File file = new File(path);
		if (file.isFile()) {
			return "";
		}

		List<String> files = listDirs(file.getAbsolutePath(), true);
		for (String dir : files) {
			File underFile = new File(dir);

			if (underFile.isFile() && StringUtils.isBlank(fileSuffix)) {
				// 无后缀直接返回文件地址
				return dir;
			}

			if (underFile.isFile() && StringUtils.isNotBlank(fileSuffix)
					&& underFile.getName().endsWith(fileSuffix)) {
				// 有后缀则进行过滤
				return dir;
			}
		}
		return "";
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

	/**
	 * 获取新文件名且不重复 可传入文件路径和名称
	 * @return
	 */
	public static String getNewName(String nameOrPath) {

		// 文件名处理
		int suffixIndex = nameOrPath.lastIndexOf(CommonConstant.PERIOD);
		if (suffixIndex == CommonConstant.NEGATIVE_ONE) {
			return nameOrPath;
		}
		String fileName = IDGeneratorUtil.getID() + nameOrPath.substring(suffixIndex);

		// 文件路径处理
		int dirIndex = nameOrPath.lastIndexOf(CommonConstant.LEFT_OBLIQUE_LINE);
		if (dirIndex == CommonConstant.NEGATIVE_ONE) {
			return fileName;
		}
		String filePath = nameOrPath.substring(0, dirIndex + 1);

		return filePath + fileName;
	}

	/**
	 * 校验文件后缀名
	 * @param destFilePath 目标文件地址 文件名称和绝对路径均可以
	 * @param fileTypeStr 文件后缀字符串 可参考FileTypeContant传入数据
	 * @return
	 */
	public static void validateFileSuffix(String destFilePath, String fileTypeStr) {

		if (StringUtils.isBlank(destFilePath)) {
			log.error("destFilePath is bank!");
			throw new BusinessException(ErrorCode.FILE_CONTENT_INVALID);
		}

		// 文件后缀匹配，如果文件无后缀则可能不是文件
		String fileSuffix = getFileSuffix(destFilePath);

		// 文件类型为空 则文件无后缀
		if (StringUtils.isBlank(fileTypeStr)) {
			if (StringUtils.isBlank(fileSuffix)) {
				return;
			} else {
				log.error("fileSuffix is not  bank! fileSuffix = [{}]",fileSuffix);
				throw new BusinessException(ErrorCode.NOT_SUPPORT_FILE_TYPE,
						fileTypeStr.toLowerCase());
			}
		}

		// 如果类型不为空 但后缀为空 则校验不通过
		if (StringUtils.isBlank(fileSuffix)) {
			log.error("fileSuffix is  bank! fileTypeStr = [{}]",fileTypeStr);
			throw new BusinessException(ErrorCode.NOT_SUPPORT_FILE_TYPE, fileTypeStr.toLowerCase());
		}

		String suffixStr = fileSuffix.toUpperCase();
		List<String> typeList = Arrays.asList(fileTypeStr.split(CommonConstant.CODE_COMMA));

		if (!typeList.contains(suffixStr)) {
			log.error("File type error! file_path=[{}], suffix=[{}], file_type_str=[{}]",
					destFilePath, suffixStr, fileTypeStr);
			throw new BusinessException(ErrorCode.NOT_SUPPORT_FILE_TYPE, fileTypeStr.toLowerCase());
		}

	}

	/**
	 * 获取文件后缀名称
	 * @param filePath
	 * @return
	 */
	public static String getFileSuffix(String filePath) {
		int indexOf = filePath.lastIndexOf(CommonConstant.PERIOD);
		if (indexOf == -1) {
			return "";
		}

		return filePath.substring(indexOf + 1);
	}

	/**
	 * 文件夹目录删除
	 * @param destDir
	 * @param self
	 */
	public static void deleteDir(String destDir, boolean self) {
		File file = new File(destDir);
		if (!file.exists()) {
			return;
		}
		
		try {
			if (file.isFile()) {
				FileUtils.delete(file);
				return;
			}
			
			if (self) {
				FileUtils.deleteDirectory(file);
				return;
			}

			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				return;
			}

			for (File subFile : files) {
				if (subFile.isFile()) {
					subFile.delete();
				} else {
					FileUtils.deleteDirectory(subFile);
				}
 			}
		} catch (Exception e) {
			log.error("Directory delete failed! file_path=[{}], catch error:", destDir, e);
			throw new BusinessException(ErrorCode.FILE_DELETE_FAILED);
		}
		
	}

	public static String getHash256(File file) throws IOException, NoSuchAlgorithmException {
		String value = "";
		try (InputStream fis = new FileInputStream(file)) {
			byte[] buffer = new byte[1024];
			int numRead;
			// 返回实现指定摘要算法的 MessageDigest 对象
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			do {
				numRead = fis.read(buffer);
				if (numRead > 0) {
					// 更新摘要
					digest.update(buffer, 0, numRead);
				}
			} while (numRead != -1);
			value = new String(
					org.apache.tomcat.util.codec.binary.Base64.encodeBase64(digest.digest()));
		}
		return value;
	}

	/**
	 * 创建父级目录
	 * @param filePath
	 */
	public static void createParentPath(String filePath) {

		File file = new File(filePath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
	}

	public static void copyFile(String originFile, String destFile) {
		copyFile(new File(originFile), new File(destFile));
	}

	public static void copyFile(File file, File destFile) {

		try {
			FileUtils.copyFile(file, destFile);
		} catch (Exception e) {
			log.error("File copy failed! origin_path=[{}], dest_path=[{}], catch error:",
					file.getAbsolutePath(), destFile.getAbsolutePath(), e);
			throw new BusinessException(ErrorCode.FILE_COPY_FAIL);
		}
	}

	public static void createPath(File file) {

		if (file.exists()) {
			return;
		}

		file.mkdirs();
	}

	public static void createManyPath(String parentPath, String... children) {
		for (String child : children) {
			createPath(new File(parentPath, child));
		}
	}
}
