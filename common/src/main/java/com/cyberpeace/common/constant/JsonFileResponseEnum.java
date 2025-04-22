package com.cyberpeace.common.constant;


import lombok.Getter;
import org.springframework.util.ClassUtils;

/**
 * HTML 模板枚举
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @since 1.0.0
 */
public enum JsonFileResponseEnum {

    ATTACK("attack.json", "static/demonstration/attack.json"),

    ENGAGE("engage.json", "static/demonstration/engage.json"),
    DEFEND("engage.json", "static/demonstration/defend.json"),
    USERS("users.json", "static/demonstration/users.json"),
    ;

    /**
     * 模板路径
     *
     * @see String
     */
    @Getter
    private String filePath;

    /**
     * 模板名称
     *
     * @see String
     */
    @Getter
    private String name;

    JsonFileResponseEnum(String name, String filePath) {
        this.name = name;
        this.filePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + filePath;
    }

}

