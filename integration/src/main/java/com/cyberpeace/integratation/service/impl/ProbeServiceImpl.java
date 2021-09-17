package com.cyberpeace.integratation.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.evaluation.ProbeOriginData;
import com.cyberpeace.common.model.evaluation.ProbeOriginPageData;
import com.cyberpeace.common.util.LocalClient;
import com.cyberpeace.integratation.model.evaluation.ProbeDate;
import com.cyberpeace.integratation.model.evaluation.ProbeDateRsp;
import com.cyberpeace.integratation.service.ProbeService;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.ConfigUtil;
import com.google.common.collect.Lists;

@Service
public class ProbeServiceImpl implements ProbeService {

	@Override
	public ProbeOriginPageData originData(String sceneId, String hostId, String collectorId,
										  Date startTime, Date endTime, Page pageBase) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BasicNameValuePair> nvps = Lists.newArrayList();
		nvps.add(new BasicNameValuePair("scene_hash", sceneId));
		nvps.add(new BasicNameValuePair("server_id", hostId));
		nvps.add(new BasicNameValuePair("probe_hash", collectorId));
		nvps.add(new BasicNameValuePair("start_time", dateFormat.format(startTime)));
		nvps.add(new BasicNameValuePair("end_time", dateFormat.format(endTime)));
		nvps.add(new BasicNameValuePair("per_page", String.valueOf(Long.valueOf(pageBase.getSize()).intValue())));
		nvps.add(new BasicNameValuePair("page", String.valueOf(Long.valueOf(pageBase.getCurrent()).intValue())));
		String url = ConfigUtil.getContextProperty("cr.host")
				+ ConfigUtil.getContextProperty("cr.probe.upload");

		ProbeDateRsp probeDateRsp = LocalClient.get(nvps, url, ProbeDateRsp.class);

		ProbeOriginPageData ret = new ProbeOriginPageData();
        //解析数据
		if (!probeDateRsp.getData().isEmpty()) {
			List<ProbeOriginData> retDataList = Lists.newArrayList();
			List<ProbeDate> probeDateList = probeDateRsp.getData();
			probeDateList.forEach(probeDate -> {
				ProbeOriginData retData = new ProbeOriginData();
				try {
					retData.setTime(dateFormat.parse(probeDate.getCreate_time()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				retData.setHostName(probeDate.getMachine_identification());
				retData.setData(probeDate.getResult());
				retDataList.add(retData);
			});
			ret.setData(retDataList);
		}
		ret.setTotal(probeDateRsp.getLinks().getPagination().getTotal());

		return ret;
	}
}
