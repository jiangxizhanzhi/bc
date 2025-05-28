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
