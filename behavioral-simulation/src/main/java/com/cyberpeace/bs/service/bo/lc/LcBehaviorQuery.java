package com.cyberpeace.bs.service.bo.lc;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 远程行为查询
 * 根据id批量查询行为接口
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class LcBehaviorQuery implements Serializable {

    /**
     * id列表
     */
    private List<Long> ids = Lists.newArrayList();

    /**
     * 是否返回变量
     */
    private Boolean withVariable = false;

    /**
     * 添加id
     *
     * @param ids id
     */
    public void addIds(Collection<String> ids) {
        ids.forEach(id -> this.ids.add(new Long(id)));
    }
}
