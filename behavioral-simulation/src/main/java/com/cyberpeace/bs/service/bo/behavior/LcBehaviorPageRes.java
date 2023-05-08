package com.cyberpeace.bs.service.bo.behavior;

import com.cyberpeace.bs.service.bo.lc.LcBehaviorRspBo;
import com.cyberpeace.bs.utils.model.LcListBase;
import lombok.Data;

import java.util.List;

/**
 * 远程行为分页返回体
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcBehaviorPageRes extends LcListBase {

    /**
     * 记录
     */
    private List<LcBehaviorRspBo> records;
}
