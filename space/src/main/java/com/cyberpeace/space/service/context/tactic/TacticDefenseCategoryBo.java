package com.cyberpeace.space.service.context.tactic;

import lombok.Data;

@Data
public class TacticDefenseCategoryBo {
    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 名称
     */
    private String name;

    /**
     * 阶段
     */
    private Integer stage;

    /**
     * 封面
     */
    private String cover;
}
