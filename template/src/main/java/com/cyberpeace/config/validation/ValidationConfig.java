package com.cyberpeace.config.validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 验证配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码是一个 Spring Boot 的配置类 `ValidationConfig`，用于全局配置 Java Bean Validation（JSR 380）的验证规则。以下是代码的主要功能和结构分析： 
 * --- 
 * ### **主要功能** 
 * 1. **快速失败模式**： 
 * - 使用 Hibernate Validator 提供的快速失败模式 (`failFast=true`)，当第一个校验失败时立即停止后续校验并抛出异常。 
 * 2. **配置两个 Bean**： 
 * - 提供一个全局的 `Validator` 实例，用于校验基于 `@RequestBody` 的参数。 
 * - 提供一个 `MethodValidationPostProcessor` 实例，用于校验基于 `@RequestParam` 的方法参数。 
 * --- 
 * ### **代码结构分析** 
 * 1. **类定义**： 
 * - 类 `ValidationConfig` 被标注为 `@Configuration`，表示这是一个 Spring 的配置类。 
 * - 它包含了两个 `@Bean` 方法，分别定义了 `Validator` 和 `MethodValidationPostProcessor`。 
 * 2. **`validator()` 方法**： 
 * - 创建了一个自定义的 `Validator` 实例。 
 * - 使用 `Validation.byProvider(HibernateValidator.class)` 指定使用 Hibernate Validator 作为实现。 
 * - 配置了 `failFast(true)`，启用快速失败模式。 
 * - 返回 `Validator` 实例，供其他地方注入使用。 
 * 3. **`methodValidationPostProcessor()` 方法**： 
 * - 创建了一个 `MethodValidationPostProcessor` 实例。 
 * - 将前面定义的 `Validator` 注入到该处理器中，使其能够处理方法级别的校验（如 `@RequestParam` 参数）。 
 * --- 
 * ### **应用场景** 
 * - **`@RequestBody` 校验**：当接收 JSON 数据时，可以通过 `@Valid` 注解触发校验。 
 * - **`@RequestParam` 校验**：当处理 URL 参数时，通过 `MethodValidationPostProcessor` 自动校验方法参数。 
 * --- 
 * ### **优点** 
 * - 集成 Hibernate Validator，支持丰富的校验注解（如 `@NotNull`, `@Size`, `@Email` 等）。 
 * - 快速失败模式提高了性能，避免不必要的校验开销。 
 * - Spring 提供的 `MethodValidationPostProcessor` 简化了方法级别校验的配置。 
 * --- 
 * ### **总结** 
 * 这段代码的核心是通过 Spring 配置类提供了全局的校验能力，并结合 Hibernate Validator 的快速失败模式优化了校验流程。适用于需要对请求参数进行严格校验的场景，尤其是 RESTful API 开发中对输入数据的合法性要求较高的情况。 
**/
@Configuration
public class ValidationConfig {

	/**
	 * 效验@RequestBody时，采用快速失败模式
	 *
	 * @return 验证人
	 * @throws
	 * @see Validator
	 */
	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
				.configure()
				// 快速失败
				.failFast(true).buildValidatorFactory();
		return validatorFactory.getValidator();
	}

	/**
	 * 效验@RequestParam时，采用快速失败模式
	 *
	 * @param validator 验证人
	 * @return 方法验证后处理器
	 * @throws
	 * @see Validator
	 * @see MethodValidationPostProcessor
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor(Validator validator) {
		MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
		// 设置validator
		postProcessor.setValidator(validator);
		return postProcessor;
	}
}

