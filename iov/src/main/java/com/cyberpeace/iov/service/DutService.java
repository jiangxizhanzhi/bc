package com.cyberpeace.iov.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.iov.dao.entity.Dut;
import com.cyberpeace.iov.service.bo.dut.DutAddReq;
import com.cyberpeace.iov.service.bo.dut.DutListBo;
import com.cyberpeace.iov.service.bo.dut.EnableReq;

public interface DutService extends IService<Dut> {

	ListRsp<DutListBo> pageDuts(PageReqBase req, Integer model, Integer area, Integer status);

	void create(DutAddReq req);

	void deleteByIds(List<String> ids);

	List<DutListBo> listDuts(Integer model, Integer area);

	void enable(EnableReq req);
}
