package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;

public interface CrTaskSubmitService extends IService<CrTaskSubmit> {

    public  void addTaskSubmit(CrTaskSubmit crTaskSubmit);

    public CrTaskSubmit getById(String taskSubmitId);

}
