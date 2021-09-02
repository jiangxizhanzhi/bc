package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.controller.req.PracticeListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PracticeCategoryService extends IService<PracticeCategory> {

    PracticeCategory get(String resource_id);

    ListRsp<PracticeCategory> search(PracticeListReq queryParams);

    PracticeCategory create(PracticeCategory object);

    Integer partialUpdate(String tableName, String id, String name);

    Integer batchDelete(List<String> resourceIds);

}
