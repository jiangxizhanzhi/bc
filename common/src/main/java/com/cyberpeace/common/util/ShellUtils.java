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
