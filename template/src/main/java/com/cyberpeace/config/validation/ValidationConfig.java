package com.cyberpeace.config.validation;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class ValidationConfig {

    /**
     * 创建一个Validator bean，用于处理@RequestBody注解的参数验证
     * 采用快速失败模式，即在验证到第一个错误时立即停止，提高效率
     *
     * @return Validator实例，用于执行验证操作
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
     * 创建一个MethodValidationPostProcessor bean，用于处理@RequestParam注解的参数验证
     * 同样采用快速失败模式，确保在验证到第一个错误时停止
     *
     * @param validator 由validator方法创建的Validator实例，用于执行验证操作
     * @return MethodValidationPostProcessor实例，用于处理方法级别的验证
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(Validator validator) {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        // 设置validator
        postProcessor.setValidator(validator);
        return postProcessor;
    }
}

