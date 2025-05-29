package com.cyberpeace.config.custom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务启动，依赖的参数校验。
 * <p>
 * <p>
 * <p>
 * 目的在于快速发现可能的配置项错误！
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码是一个Spring Bean类 `InitChecker`，用于在服务启动时进行必要的参数校验。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **服务启动时的参数校验**： 
 * - 使用了Spring的 `@PostConstruct` 注解，在Bean初始化完成后自动执行 `checkList()` 方法。 
 * - 在 `checkList()` 方法中调用了多个检查方法（如 `checkMp()`、`checkCr()`、`checkOj()` 和 `checkRtks()`），分别针对不同的模块或功能进行参数校验。 
 * 2. **具体校验逻辑**： 
 * - **`checkMp()` 方法**：检查与“中台”相关的配置项（如 `MP_HOST`、`MP_PORT`、`MP_SECRET`）是否为空，并对 `MP_SECRET` 的格式进行额外校验。如果格式不符合要求（必须是逗号分隔的两个部分），则记录错误日志并抛出异常。 
 * - **`blankCheck()` 方法**：通用的空白检查工具方法，用于验证指定的配置项是否为空。如果为空，则记录错误日志并抛出异常。 
 * - 其他检查方法（如 `checkCr()`、`checkOj()` 和 `checkRtks()`）目前未实现具体逻辑。 
 * 3. **日志记录**： 
 * - 使用了 `lombok` 提供的日志功能 (`log`) 来记录校验过程中的信息和错误。 
 * ### 代码结构 
 * - **注解**： 
 * - `@Slf4j`：为类添加日志功能。 
 * - `@Component`：将该类注册为Spring容器中的一个Bean。 
 * - `@PostConstruct`：标记 `checkList()` 方法在Bean初始化后自动执行。 
 * - **方法**： 
 * - `checkList()`：主入口方法，调用其他具体的检查方法。 
 * - `checkMp()`：专门用于检查与中台相关的配置项。 
 * - `checkCr()`、`checkOj()`、`checkRtks()`：预留的方法，用于后续扩展其他模块的检查逻辑。 
 * - `blankCheck()`：通用的空白检查方法，接受字段名作为参数，判断其值是否为空。 
 * ### 总结 
 * 该类的主要目的是在服务启动时对关键配置项进行校验，确保系统运行所需的必要条件已满足。通过使用 `@PostConstruct` 和 `blankCheck()` 方法，实现了自动化的参数检查机制，并且提供了详细的日志记录以便于问题排查。此外，代码还预留了一些扩展点，方便未来添加更多模块的检查逻辑。 
**/
@Slf4j
@Component
public class InitChecker {

	/**
	 * @throws
	 */
	@PostConstruct
	public void checkList() {
		checkMp();

		checkCr();

		checkOj();

		checkRtks();
	}

	/**
	 * 靶场启动项检查
	 *
	 * @throws
	 */
	private void checkCr() {}

	/**
	 * 实训启动项检查
	 *
	 * @throws
	 */
	private void checkOj() {}

	/**
	 * rtks启动项检查
	 *
	 * @throws
	 */
	private void checkRtks() {}

	/**
	 * 检查中台配置
	 *
	 * @throws
	 */
	private void checkMp() {
		blankCheck("MP_HOST");
		blankCheck("MP_PORT");
		blankCheck("MP_SECRET");

		// 额外校验中台设置项
		String mpSecret = SystemPropUtil.getProp("MP_SECRET");
		String[] mpSecretSplit = StringUtils.split(mpSecret, ",");

		Integer mpSecretLength = 2;
		if (null == mpSecretSplit || mpSecretSplit.length != mpSecretLength) {
			// 格式错误
			log.error("[MP_SECRET]wrong format, read value=[{}]", mpSecret);
			throw new Error();
		}
	}

	/**
	 * 空白支票
	 *
	 * @param field 田
	 * @throws
	 * @see String
	 */
	private void blankCheck(String field) {
		String value = SystemPropUtil.getProp(field);

		if (StringUtils.isBlank(value)) {
			log.error("[{}]not set!", field);
			throw new Error();
		} else {
			log.info("prop=[{}], value=[{}]", field, value);
		}
	}
}

