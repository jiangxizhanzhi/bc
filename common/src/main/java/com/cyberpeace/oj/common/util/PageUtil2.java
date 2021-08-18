package com.cyberpeace.oj.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.oj.common.model.ListRsp;

public class PageUtil2 {

    public static <T> ListRsp<T> getListResult(IPage<T> ipage) {
        ListRsp<T> ret = new ListRsp<>();
        ret.setList(ipage.getRecords());
        ret.setTotal(ipage.getTotal());
        return ret;
    }
}
