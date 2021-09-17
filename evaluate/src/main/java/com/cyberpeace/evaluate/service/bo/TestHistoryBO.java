package com.cyberpeace.evaluate.service.bo;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class TestHistoryBO implements Serializable {

    /**测试名称 */
    private String name;

    /**描述 */
    private String desc;

    /**测试时间 */
    private Date startTime;

    /**对应数值的位置 */
    private Integer index;

}
