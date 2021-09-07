package com.cyberpeace.cr.cebase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.cebase.model.crevent.CrEventAddInput;
import com.cyberpeace.cr.cebase.model.crevent.CrEventModifyInput;

public interface CrEventService extends IService<CrEvent> {

	CrEvent create(CrEventAddInput crEventAddInput, int ceType);

	CrEvent update(String crEventId, CrEventModifyInput crEventModifyInput);

	CrEvent getOr404(String crEvent);
}
