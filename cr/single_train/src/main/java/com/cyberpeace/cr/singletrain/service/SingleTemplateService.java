package com.cyberpeace.cr.singletrain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateBo;

public interface SingleTemplateService extends IService<SingleTemplate> {

    SingleTemplateBo get(String trainBusinessId);

    SingleTemplateBo update(String trainBusinessId, BaseInfo baseInfo, JSONObject missionScheme);

}
