package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.controller.req.PracticeListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;

import java.util.List;

public interface PracticeCategoryService extends IService<PracticeCategory> {

    ListRsp<PracticeCategory> search(PracticeListReq queryParams);

    Integer batchDelete(List<String> resourceIds);

    List<PracticeCategory> getChildren(PracticeCategory root);

}
