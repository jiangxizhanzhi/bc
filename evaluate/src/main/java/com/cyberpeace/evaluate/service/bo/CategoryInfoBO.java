package com.cyberpeace.evaluate.service.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoryInfoBO implements Serializable {

    /**分类编号 */
    private String categoryId;

    /**分类类型 */
    private String subType;

    /**分类描述 */
    private String subDesc;

}
