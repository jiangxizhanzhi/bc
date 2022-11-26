package com.cyberpeace.bs.service.bo.lc;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

@Data
public class LcActualDeviceReq implements Serializable {
    private List<Integer> deviceType = Lists.newArrayList();
}
