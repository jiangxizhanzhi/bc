package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 远程变量响应
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcVariableRspBo extends LcVariableBase implements Serializable {

    /**
     * 更新时间
     */
    private Date updateTime;
}
