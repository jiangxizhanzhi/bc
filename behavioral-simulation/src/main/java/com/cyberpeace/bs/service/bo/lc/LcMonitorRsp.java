package com.cyberpeace.bs.service.bo.lc;

import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.utils.BsUtil;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LcMonitorRsp implements Serializable {

    /**
     * 分钟
     */
    List<Integer> bandwidth = Lists.newArrayList();

    /**
     * 分钟
     */
    List<Integer> packet = Lists.newArrayList();

    /**
     * 分钟
     */
    List<Integer> connection = Lists.newArrayList();

    /**
     * 分钟
     */
    List<Integer> connected =  Lists.newArrayList();

    /**
     * 小时
     */
    List<Integer> step =  Lists.newArrayList();

    /**
     * 半小时
     */
    List<Integer> session =  Lists.newArrayList();
}
