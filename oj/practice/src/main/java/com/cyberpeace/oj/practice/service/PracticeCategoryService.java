package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.controller.req.PracticeListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.service.bo.PracticeCategoryParent;

import java.util.List;

public interface PracticeCategoryService extends IService<PracticeCategory> {

    PracticeCategory get(String resourceId);

    ListRsp<PracticeCategory> search(PracticeListReq queryParams);

    Integer batchDelete(List<String> resourceIds);

    List<PracticeCategory> getChildren(PracticeCategory root);

    PracticeCategoryParent nodeInfo(String categoryId);

}
