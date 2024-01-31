package com.cyberpeace.integratation.service;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.integratation.model.WjRecordRsp;

/**
 * 竞赛外部接口调用
 */
public interface RemoteWjService {

	ListRsp<WjRecordRsp> listWjRecord(PageReqBase req);

}
