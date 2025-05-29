package com.cyberpeace.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * shell 工具
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 功能概述： 
 * 该代码定义了一个名为 `ShellUtils` 的工具类，用于执行 Linux Shell 脚本。它提供了两个方法： 
 * 1. `execShell(String shell)`：执行指定的 Shell 命令，并返回命令的标准输出内容（`List<String>`）。 
 * 2. `execErrorShell(String shell)`：执行指定的 Shell 命令，并返回命令的错误输出内容（`String`）。 
 * #### 主要功能模块： 
 * 1. **日志记录**： 
 * - 使用了 `log4j` 日志框架，通过 `@Slf4j` 注解为类添加了日志记录功能。 
 * - 在方法开始时记录日志信息（如命令开始执行），在结束时记录日志信息（如命令执行完成）。 
 * - 标准输出内容记录为 `INFO` 级别日志，错误输出内容记录为 `ERROR` 级别日志。 
 * 2. **执行 Shell 命令**： 
 * - 使用 `Runtime.getRuntime().exec()` 方法来执行 Shell 命令。 
 * - 通过 `BufferedReader` 读取命令的标准输出流或错误输出流。 
 * - 将每行输出存储到集合或字符串中，并返回结果。 
 * 3. **异常处理**： 
 * - 如果命令执行失败，捕获异常并记录详细的错误日志。 
 * - 抛出自定义异常 `BusinessException`，并附带错误码 `ErrorCode.LOCAL_SHELL_EXEC_FAILED`。 
 * 4. **线程安全性**： 
 * - 代码中没有涉及共享资源的修改，因此不存在线程安全问题。 
 * #### 优点： 
 * - **模块化设计**：将标准输出和错误输出分别封装为不同的方法，便于复用。 
 * - **日志清晰**：通过日志记录命令执行的详细过程，方便排查问题。 
 * - **异常处理完善**：通过自定义异常明确指示执行失败的原因。 
 * #### 缺点与改进建议： 
 * 1. **命令注入风险**： 
 * - `Runtime.getRuntime().exec()` 方法直接执行传入的字符串命令，可能存在命令注入的风险。 
 * - 改进建议：使用更安全的方式（如 `ProcessBuilder`）来构建和执行命令。 
 * 2. **资源管理**： 
 * - 当前代码未关闭 `BufferedReader`，可能会导致资源泄漏。 
 * - 改进建议：使用 `try-with-resources` 语句确保资源自动关闭。 
 * 3. **错误处理**： 
 * - 当前仅抛出自定义异常，但未提供具体的错误信息。 
 * - 改进建议：在异常中包含更多的上下文信息，例如命令本身、标准输出和错误输出。 
 * #### 总结： 
 * 该工具类实现了基本的 Shell 命令执行功能，日志记录和异常处理较为完善。但在命令注入风险、资源管理以及异常信息丰富性方面仍有改进空间。 
**/
@Slf4j
public class ShellUtils {

	/**
	 * 运行shell脚本
	 *
	 * @param shell 需要运行的shell脚本
	 * @return {@code List<String> }
	 */
	public static List<String> execShell(String shell) {
		log.info("Start exec shell! command=[{}]", shell);

		Process process;
		List<String> processResult = Lists.newArrayList();

		try {
			process = Runtime.getRuntime().exec(shell);
			BufferedReader input = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = input.readLine()) != null) {
				log.info(line);
				processResult.add(line);
			}

		} catch (Exception e) {
			log.error("Exec shell error! command=[{}], catch error:", shell, e);
			throw new BusinessException(ErrorCode.LOCAL_SHELL_EXEC_FAILED);
		} finally {
			log.info("End exec shell!");
		}

		return processResult;
	}

	/**
	 * 运行shell脚本
	 *
	 * @param shell 需要运行的shell脚本
	 * @return {@code String }
	 */
	public static String execErrorShell(String shell) {
		log.info("Start exec shell! command=[{}]", shell);

		Process process;
		StringBuilder builder = new StringBuilder();

		try {
			process = Runtime.getRuntime().exec(shell);
			BufferedReader input = new BufferedReader(
					new InputStreamReader(process.getErrorStream()));

			String line;
			while ((line = input.readLine()) != null) {
				log.error(line);
				builder.append(line).append(System.lineSeparator());
			}

		} catch (Exception e) {
			log.error("Exec local shell failed! command=[{}]", shell, e);
			throw new BusinessException(ErrorCode.LOCAL_SHELL_EXEC_FAILED);
		} finally {
			log.info("End exec shell!");
		}

		return builder.toString().trim();

	}

}

