package com.cyberpeace.space.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.constant.SpaceError;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.dao.entity.SpaceStrategyFrame;
import com.cyberpeace.space.dao.mapper.SpaceStrategyFrameMapper;
import com.cyberpeace.space.dao.mapper.SpaceStrategyMapper;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import com.cyberpeace.space.service.context.index.*;
import com.cyberpeace.space.service.context.strategy.*;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 空间策略Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceStrategyService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceStrategyServiceImpl extends ServiceImpl<SpaceStrategyMapper, SpaceStrategy> implements SpaceStrategyService {

	@Autowired
	private SpaceStrategyFrameMapper frameMapper;

	@Autowired
	private SpaceStrategyMapper mapper;

	@Autowired
	private RemoteUserService remoteUserService;

	private final Map<String, SpaceStrategyFrameBo> frameCache = Maps.newConcurrentMap();

	/**
	 * 获取框架
	 *
	 * @return 空间策略框架Bo
	 * @throws
	 * @see SpaceStrategyFrameBo
	 */
	@Override
	public SpaceStrategyFrameBo getFrame() {
		Map<String, SpaceStrategyFrameBo> map = this.getFrameMap();
		this.decorateFrameMap(map);
		return map.get(SpaceCommonConstant.DEFAULT_STRATEGY_FRAME);
	}

	/**
	 * 新增框架
	 *
	 * @param req 请求体
	 * @throws
	 * @see SpaceStrategyFrameAddReq
	 */
	@Override
	public void addFrame(SpaceStrategyFrameAddReq req) {
		SpaceStrategyFrame frame = this.req2Entity(req);
		// todo 重名校验
		frameMapper.insert(frame);
		this.refreshFrameCache();
	}

	/**
	 * 修改
	 *
	 * @param req 请求体
	 * @throws
	 * @see SpaceStrategyFrameModifyReq
	 */
	@Override
	public void modify(SpaceStrategyFrameModifyReq req) {
		SpaceStrategyFrame frame = frameMapper.selectById(req.getResourceId());
		if (Boolean.TRUE.equals(frame.getBuiltin())) {
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
		frame.setName(req.getName());
		frameMapper.updateById(frame);
		this.refreshFrameCache();
	}

	/**
	 * 删除框架
	 *
	 * @param resourceId 资源id
	 * @throws
	 * @see String
	 */
	@Override
	public void deleteFrame(String resourceId) {
		Map<String, SpaceStrategyFrameBo> frameMap = this.getFrameMap();
		SpaceStrategyFrameBo frame = frameMap.get(resourceId);
		if (null == frame) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if (Boolean.TRUE.equals(frame.getBuiltin())) {
			throw new BusinessException(SpaceError.BUILTIN_FRAME);
		}
		List<String> frameIds = Lists.newArrayList();
		this.collectFrameId(frameIds, frameMap.get(resourceId));
		List<SpaceStrategy> strategies = mapper.selectList(
				new LambdaQueryWrapper<SpaceStrategy>().in(SpaceStrategy::getFrameId, frameIds));
		if (!strategies.isEmpty()) {
			throw new BusinessException(SpaceError.STRATEGY_EXIST);
		}
		frameMapper.deleteBatchIds(frameIds);
		this.refreshFrameCache();
	}

	/**
	 * 列表策略由框架
	 *
	 * @param frameId 框架id
	 * @param name    名称
	 * @return 列表<空间策略简单>
	 * @throws
	 * @see String
	 * @see String
	 * @see List<SpaceStrategySimple>
	 */
	@Override
	public List<SpaceStrategySimple> listStrategiesByFrame(String frameId, String name) {
		List<SpaceStrategySimple> simpleList = mapper.selectIdAndNameByFrame(frameId, name);
		String route = this.getFrameRouteById(frameId);
		for (SpaceStrategySimple simple : simpleList) {
			simple.setRoute(route);
		}
		return simpleList;
	}

	/**
	 * 新增策略
	 *
	 * @param req 请求体
	 * @throws
	 * @see SpaceStrategyAddReq
	 */
	@Override
	public void addStrategy(SpaceStrategyAddReq req) {
		SpaceStrategy strategy = this.req2Entity(req);

		strategy.setView(0);
		strategy.setUserId(UserInfoContext.getUserContext().getUserId());
		mapper.insert(strategy);
	}

	/**
	 * 更新策略
	 *
	 * @param req 请求体
	 * @throws
	 * @see SpaceStrategyModifyReq
	 */
	@Override
	public void updateStrategy(SpaceStrategyModifyReq req) {
		SpaceStrategy strategy = this.req2Entity(req);
		strategy.setResourceId(req.getResourceId());
		mapper.updateById(strategy);
	}

	/**
	 * 删除策略
	 *
	 * @param ids id列表
	 * @throws
	 * @see List<String>
	 */
	@Override
	public void deleteStrategy(List<String> ids) {
		if (null == ids || ids.isEmpty()) {
			return;
		}
		mapper.deleteBatchIds(ids);
		refreshFrameCache();
	}

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return 空间策略Bo
	 * @throws
	 * @see String
	 * @see SpaceStrategyBo
	 */
	@Override
	public SpaceStrategyBo getDetail(String resourceId) {
		SpaceStrategy strategy = this.getOrException(resourceId);
		strategy.setView(strategy.getView() + 1);
		mapper.updateById(strategy);
		SpaceStrategyBo bo = BeanUtils.copy(strategy, SpaceStrategyBo.class);
		UserInfo userInfo = remoteUserService.getById(bo.getUserId());
		bo.setUser(userInfo.getName());
		bo.setUserAvatar(userInfo.getAvatarUrl());
		bo.setRoute(this.getFrameRouteById(strategy.getFrameId()));
		return bo;
	}

	/**
	 * 获取框架路线由id
	 *
	 * @param frameId 框架id
	 * @return 字符串
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public String getFrameRouteById(String frameId) {
		List<String> names = Lists.newArrayList();
		Map<String, SpaceStrategyFrameBo> frameMap = this.getFrameMap();
		while (!SpaceCommonConstant.DEFAULT_STRATEGY_FRAME.equals(frameId)) {
			SpaceStrategyFrameBo frame = frameMap.get(frameId);
			if (null == frame) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
			names.add(frame.getName());
			frameId = frame.getParent();
		}
		Collections.reverse(names);
		return String.join("/", names);
	}

	/**
	 * 获取统计学
	 *
	 * @return 编号策略
	 * @throws
	 * @see IndexStrategy
	 */
	@Override
	public IndexStrategy getStatistics() {
		SpaceStrategyFrameBo frameBo = this.getUndecoratedFrame();
		Map<String, List<String>> map = Maps.newHashMap();
		for (SpaceStrategyFrameBo child : frameBo.getChildren()) {
			if (null == child) {
				continue;
			}
			List<String> frameIds = Lists.newArrayList();
			this.collectFrameId(frameIds, child);
			map.put(child.getResourceId(), frameIds);
		}
		IndexStrategy strategy = new IndexStrategy();
		List<SpaceStrategy> strategies = mapper.selectList(null);
		strategy.setOs(
				this.countByFrameIds(map.get("47d82955780346d98f92250e1e4ecd10"), strategies));
		strategy.setDatabase(
				this.countByFrameIds(map.get("f41c777715754d3c99c76f17f2b78975"), strategies));
		strategy.setMiddleware(
				this.countByFrameIds(map.get("52e001b892ae4d38aced84842918a4ba"), strategies));
		strategy.setSwitchboard(
				this.countByFrameIds(map.get("8114e09e4dee4ca6b15eaa4772f36bf9"), strategies));
		strategy.setRouter(
				this.countByFrameIds(map.get("e0fafbc784c9486c967ab0952c0c3a3f"), strategies));
		strategy.setBigData(
				this.countByFrameIds(map.get("da6a5308594e4cbab220d136bf1002d9"), strategies));
		strategy.setAntiVirus(
				this.countByFrameIds(map.get("02ef1b4cf4d94a2bb9d2312defd3ed6f"), strategies));
		strategy.setTerminal(
				this.countByFrameIds(map.get("5a4243d5c16b4376ab7a63da386ea993"), strategies));
		strategy.setBorder(
				this.countByFrameIds(map.get("518eccd483a94d218b4994de4c714557"), strategies));
		strategy.setInvasionCheck(
				this.countByFrameIds(map.get("ca6efb4d930b438badc1649f098eb966"), strategies));
		strategy.calSum();

		return strategy;
	}

	/**
	 * 分页策略由框架
	 *
	 * @param req     请求体
	 * @param frameId 框架id
	 * @param name    名称
	 * @return 列出 rsp< Space Strategy BO>
	 * @throws
	 * @see PageReqBase
	 * @see String
	 * @see String
	 * @see ListRsp<SpaceStrategyBo>
	 */
	@Override
	public ListRsp<SpaceStrategyBo> pageStrategiesByFrame(PageReqBase req, String frameId,
			String name) {
		Page page = req.toPageParam();
		LambdaQueryWrapper<SpaceStrategy> wrapper = new LambdaQueryWrapper<>();
		if (StringUtils.isNotBlank(name)) {
			wrapper.like(SpaceStrategy::getName, name);
		}
		Map<String, SpaceStrategyFrameBo> map = this.getFrameMap();
		if (!StringUtils.isBlank(frameId)) {
			List<String> frameIds = Lists.newArrayList();
			SpaceStrategyFrameBo frame = map.get(frameId);
			this.collectFrameId(frameIds, frame);
			wrapper.in(SpaceStrategy::getFrameId, frameIds);
		}
		IPage<SpaceStrategy> iPage = mapper.selectPage(page, wrapper);
		Map<String, String> routeMap = Maps.newHashMap();
		List<String> names = Lists.newArrayList();
		collectFrameRoute(routeMap, names, map.get(SpaceCommonConstant.DEFAULT_STRATEGY_FRAME));
		IPage<SpaceStrategyBo> resPage = iPage.convert(item -> {
			SpaceStrategyBo simple = BeanUtils.copy(item, SpaceStrategyBo.class);
			simple.setRoute(routeMap.get(item.getFrameId()));
			return simple;
		});
		return PageUtil.getListResult(resPage);
	}

	/**
	 * 转换
	 *
	 * @param ids id列表
	 * @return {@code List<SpaceActionStrategy> }
	 * @throws
	 * @see List<String>
	 * @see List<SpaceActionStrategy>
	 */
	@Override
	public List<SpaceActionStrategy> convert2ActionStrategy(List<String> ids) {
		List<SpaceActionStrategy> res = Lists.newArrayList();
		if (ids.isEmpty()) {
			return res;
		}
		List<SpaceStrategy> strategyList = this.listByIds(ids);
		Map<String, SpaceActionStrategy> map = Maps.newHashMap();

		for (SpaceStrategy strategy : strategyList) {
			SpaceActionStrategy bo = new SpaceActionStrategy();
			bo.setResourceId(strategy.getResourceId());
			bo.setName(strategy.getName());
			bo.setContent(strategy.getContent());
			String frameRoute = this.getFrameRouteById(strategy.getFrameId());
			bo.setRoute(frameRoute);

			map.put(bo.getResourceId(), bo);
		}
		for (String id : ids) {
			if (map.containsKey(id)) {
				res.add(map.get(id));
			}
		}
		return res;
	}

	/**
	 * 获取策略图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	@Override
	public KnowledgeChart getStrategyChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.STRATEGY,
				SpaceKTypeEnum.KNOWLEDGE.getId(), 1);
		chart.getData().add(baseItem);
		chart.getCategories().add(new KnowledgeChartCategory(SpaceKTypeEnum.STRATEGY));
		SpaceStrategyFrameBo frame = this.getUndecoratedFrame();
		for (SpaceStrategyFrameBo bo : frame.getChildren()) {
			this.fillChart(chart, bo, SpaceKTypeEnum.STRATEGY.getId(), 2);
		}
		return chart;
	}

	/**
	 * 导出策略路径
	 *
	 * @param strategyIds 策略id列表
	 * @return 字符串
	 * @throws
	 * @see List<String>
	 * @see String
	 */
	public String dumpStrategyPath(List<String> strategyIds) {
		String destPdfPath;
		JSONObject data = getStrategyJsonObject(strategyIds);
		String basePath = AttachmentPath.STRATEGY.getDesc();
		String timestamp = String.valueOf(System.currentTimeMillis());
		String destHtmlPath = String.format("%s_%s.html", basePath, timestamp);
//		String destHtmlPath = "C:/temp/strategy_" + timestamp + ".html";
		try {
			HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.Strategy, destHtmlPath);
			destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html")) + "pdf";
			HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			log.info("生成的 HTML 文件路径: {}", destHtmlPath);
			if (!new File(destHtmlPath).delete()) {
				log.error("failed to delete file:{}", destHtmlPath);
			}
		}
		return destPdfPath;
	}

	/**
	 * 获取策略JSON 格式对象
	 *
	 * @param strategyIds 策略id列表
	 * @return json对象
	 * @throws
	 * @see List<String>
	 * @see JSONObject
	 */
	private JSONObject getStrategyJsonObject(List<String> strategyIds) {
		JSONObject data = new JSONObject();
		List<SpaceStrategy> strategies = this.listByIds(strategyIds);
		//把其中的resourceid都拿出来
		List<String> resourceIds = strategies.stream()
				.map(SpaceStrategy::getResourceId)
				.collect(Collectors.toList());
		List<SpaceStrategyBo> bos = new ArrayList<>();
		for (String resourceId : resourceIds) {
			SpaceStrategyBo bo = this.getDetail(resourceId);
			bos.add(bo);
		}
		JSONArray dataArray = new JSONArray();
		for (SpaceStrategyBo bo : bos) {
			JSONObject boJson = new JSONObject();
			boJson.put("name", bo.getName());
			boJson.put("route", bo.getRoute());
			boJson.put("example",bo.getExample());
			boJson.put("content",bo.getContent());
			boJson.put("gauge",bo.getGauge());
			dataArray.add(boJson);
		}
		data.put("data", dataArray);
		return data;
	}

	/**
	 * 获取未装饰框架
	 *
	 * @return 空间策略框架Bo
	 * @throws
	 * @see SpaceStrategyFrameBo
	 */
	private SpaceStrategyFrameBo getUndecoratedFrame() {
		Map<String, SpaceStrategyFrameBo> map = this.getFrameMap();
		return map.get(SpaceCommonConstant.DEFAULT_STRATEGY_FRAME);
	}

	/**
	 * 填充图表
	 *
	 * @param chart    图表
	 * @param bo       Bo
	 * @param parentId 父母id
	 * @param level    层级
	 * @throws
	 * @see KnowledgeChart
	 * @see SpaceStrategyFrameBo
	 * @see String
	 * @see int
	 */
	private void fillChart(KnowledgeChart chart, SpaceStrategyFrameBo bo, String parentId,
			int level) {
		KnowledgeChartItem item = new KnowledgeChartItem(level);
		item.setName(bo.getName());
		item.setId(bo.getResourceId());
		item.setType(SpaceKTypeEnum.STRATEGY.getType());
		item.setCategory(parentId);
		item.setValue(bo.getChildren().size());
		chart.getData().add(item);
		KnowledgeChartLink link = new KnowledgeChartLink(parentId, bo.getResourceId());
		chart.getLinks().add(link);
		if (!bo.getChildren().isEmpty()) {
			chart.getCategories().add(new KnowledgeChartCategory(bo.getName(), bo.getResourceId()));
		}
		for (SpaceStrategyFrameBo child : bo.getChildren()) {
			fillChart(chart, child, bo.getResourceId(), level + 1);
		}
	}

	/**
	 * 收集框架id
	 *
	 * @param frameIds 框架id列表
	 * @param bo       Bo
	 * @throws
	 * @see List<String>
	 * @see SpaceStrategyFrameBo
	 */
	private void collectFrameId(List<String> frameIds, SpaceStrategyFrameBo bo) {
		if (null == bo) {
			return;
		}
		frameIds.add(bo.getResourceId());
		for (SpaceStrategyFrameBo child : bo.getChildren()) {
			collectFrameId(frameIds, child);
		}
	}

	/**
	 * 收集框架路线
	 *
	 * @param routeMap   路线映射
	 * @param frameNames 框架名字
	 * @param bo         Bo
	 * @throws
	 * @see Map<String, String>
	 * @see List<String>
	 * @see SpaceStrategyFrameBo
	 */
	private void collectFrameRoute(Map<String, String> routeMap, List<String> frameNames,
			SpaceStrategyFrameBo bo) {
		if (null == bo) {
			return;
		}
		frameNames.add(bo.getName());
		routeMap.put(bo.getResourceId(),
				String.join("/", frameNames.subList(1, frameNames.size())));
		for (SpaceStrategyFrameBo child : bo.getChildren()) {
			collectFrameRoute(routeMap, frameNames, child);
		}
		frameNames.remove(frameNames.size() - 1);
	}

	/**
	 * 计数由框架id列表
	 *
	 * @param frameIds   框架id列表
	 * @param strategies 策略
	 * @return 整数
	 * @throws
	 * @see List<String>
	 * @see List<SpaceStrategy>
	 * @see Integer
	 */
	private Integer countByFrameIds(List<String> frameIds, List<SpaceStrategy> strategies) {
		int cnt = 0;
		Set<String> set = new HashSet<>(frameIds);
		for (SpaceStrategy strategy : strategies) {
			if (set.contains(strategy.getFrameId())) {
				cnt++;
			}
		}
		return cnt;
	}

	/**
	 * req2 实体
	 *
	 * @param req 请求体
	 * @return 空间策略
	 * @throws
	 * @see SpaceStrategyAddReq
	 * @see SpaceStrategy
	 */
	private SpaceStrategy req2Entity(SpaceStrategyAddReq req) {
		SpaceStrategy strategy = BeanUtils.copy(req, SpaceStrategy.class);
		strategy.setBuiltin(false);
		return strategy;
	}

	/**
	 * req2 实体
	 *
	 * @param req 请求体
	 * @return 空间策略框架
	 * @throws
	 * @see SpaceStrategyFrameAddReq
	 * @see SpaceStrategyFrame
	 */
	private SpaceStrategyFrame req2Entity(SpaceStrategyFrameAddReq req) {
		SpaceStrategyFrame frame = new SpaceStrategyFrame();
		frame.setParent(req.getParent());
		frame.setName(req.getName());
		frame.setBuiltin(false);
		return frame;
	}

	/**
	 * 实体 2Bo
	 *
	 * @param spaceStrategyFrame 空间策略框架
	 * @return 空间策略框架Bo
	 * @throws
	 * @see SpaceStrategyFrame
	 * @see SpaceStrategyFrameBo
	 */
	private SpaceStrategyFrameBo entity2Bo(SpaceStrategyFrame spaceStrategyFrame) {
		SpaceStrategyFrameBo bo = new SpaceStrategyFrameBo();
		bo.setName(spaceStrategyFrame.getName());
		bo.setResourceId(spaceStrategyFrame.getResourceId());
		bo.setBuiltin(spaceStrategyFrame.getBuiltin());
		bo.setParent(spaceStrategyFrame.getParent());
		bo.setRemovable(true);
		return bo;
	}

	/**
	 * 获取或例外
	 *
	 * @param resourceId 资源id
	 * @return 空间策略
	 * @throws
	 * @see String
	 * @see SpaceStrategy
	 */
	public SpaceStrategy getOrException(String resourceId) {
		SpaceStrategy strategy = mapper.selectById(resourceId);
		if (null == strategy) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return strategy;
	}

	private SpaceStrategyFrame getFrameOrException(String resourceId) {
		SpaceStrategyFrame frame = frameMapper.selectById(resourceId);
		if (null == frame) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return frame;
	}

	/**
	 * 装饰框架映射
	 *
	 * @param map 映射
	 * @throws
	 * @see Map<String, SpaceStrategyFrameBo>
	 */
	private void decorateFrameMap(Map<String, SpaceStrategyFrameBo> map) {
		List<SpaceStrategy> strategyList = mapper.selectList(null);
		List<String> frameIds = StreamApiUtils.map(strategyList, SpaceStrategy::getFrameId);
		Set<String> notRemovableIds = Sets.newHashSet(frameIds);

		// 有策略的不可删除
		for (String notId : notRemovableIds) {
			String id = notId;
			while (StringUtils.isNotBlank(id)) {
				if (map.containsKey(id)) {
					map.get(id).setRemovable(false);
					id = map.get(id).getParent();
				} else {
					break;
				}
			}
		}
	}

	/**
	 * 获取框架映射
	 *
	 * @return 地图<字符串空间策略框架博>
	 * @throws
	 * @see Map<String, SpaceStrategyFrameBo>
	 */
	private Map<String, SpaceStrategyFrameBo> getFrameMap() {
		if (frameCache.isEmpty()) {
			initFrameCache();
		}
		return frameCache;
	}

	/**
	 * 初始化框架缓存
	 *
	 * @throws
	 */
	private synchronized void initFrameCache() {
		if (frameCache.isEmpty()) {
			List<SpaceStrategyFrame> strategyFrameList = frameMapper.getAll();

			frameCache.put(SpaceCommonConstant.DEFAULT_STRATEGY_FRAME, new SpaceStrategyFrameBo());
			for (SpaceStrategyFrame strategyFrame : strategyFrameList) {
				SpaceStrategyFrameBo bo = this.entity2Bo(strategyFrame);
				frameCache.put(bo.getResourceId(), bo);
			}
			for (SpaceStrategyFrame strategyFrame : strategyFrameList) {
				SpaceStrategyFrameBo bo = frameCache.get(strategyFrame.getResourceId());
				if (null != bo.getParent()) {
					SpaceStrategyFrameBo parent = frameCache.get(bo.getParent());
					if (null != parent) {
						parent.getChildren().add(bo);
					}
				}
			}
		}
	}

	/**
	 * 刷新框架缓存
	 *
	 * @throws
	 */
	private synchronized void refreshFrameCache() {
		frameCache.clear();
		initFrameCache();
	}

}
