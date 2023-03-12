package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.situation.service.context.BoundContext;
import com.cyberpeace.situation.service.context.GeometryContext;
import com.google.common.collect.Lists;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.controller.req.SituationGisReq;
import com.cyberpeace.situation.dao.entity.SituationGis;
import com.cyberpeace.situation.dao.mapper.SituationGisMapper;
import com.cyberpeace.situation.service.SituationGisService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午3:31
 */
@Service
public class SituationGisServiceImpl extends ServiceImpl<SituationGisMapper, SituationGis> implements SituationGisService {

	@Autowired
	private SituationGisMapper situationGisMapper;

	private volatile Map<String, List<Polygon>> provincePolygonMap = Maps.newHashMap();

	@PostConstruct
	public void init() {

		// 初始化省份边界数据
		InputStream inputStream = getClass().getResourceAsStream("/100000_full.json");
		BoundContext bound = JsonUtil.toJsonObject(inputStream, BoundContext.class);
		for (JSONObject feature : bound.getFeatures()) {
			List<Polygon> polygons = Lists.newArrayList();
			String name = feature.getJSONObject("properties").getString("name");
			GeometryContext geometryContext = feature.getObject("geometry", GeometryContext.class);
			for (List<List<List<Float>>> first : geometryContext.getCoordinates()) {
				for (List<List<Float>> second : first) {
					Polygon polygon = new Polygon();
					for (List<Float> node : second) {
						polygon.addPoint((int) (node.get(0) * 1000), (int) (node.get(1) * 1000));
					}
					polygons.add(polygon);
				}
				provincePolygonMap.put(name, polygons);
			}
		}
	}

	@Override
	public SituationGis getSituationGisConfig(String resourceId) {
		SituationGis situationGis = situationGisMapper.selectById(resourceId);
		if (situationGis == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return situationGis;
	}

	@Override
	public SituationGis createSituationGisConfig(SituationGisReq situationGisReq) {
		SituationGis situationGis = BeanUtils.copy(situationGisReq, SituationGis.class);
		situationGis.setResourceId(IDGeneratorUtil.getID());
		situationGisMapper.insert(situationGis);
		return situationGis;
	}

	@Override
	public SituationGis updateSituationGisConfig(SituationGisReq situationGisReq) {
		String resourceId = situationGisReq.getResourceId();
		SituationGis situationGis = situationGisMapper.selectById(resourceId);
		if (situationGis == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		SituationGis copy = BeanUtils.copy(situationGisReq, SituationGis.class);
		situationGisMapper.updateById(copy);
		return copy;
	}

	@Override
	public Integer deleteSituationGisConfig(List<String> idList) {
		return situationGisMapper.deleteBatchIds(idList);
	}

	@Override
	public String getProvince(Float longitude, Float latitude) {

		for (Map.Entry<String, List<Polygon>> entry : provincePolygonMap.entrySet()) {
			for (Polygon polygon : entry.getValue()) {
				if (polygon.contains((int) (longitude * 1000), (int) (latitude * 1000))) {
					return entry.getKey();
				}
			}
		}
		return "未知";
	}
}
