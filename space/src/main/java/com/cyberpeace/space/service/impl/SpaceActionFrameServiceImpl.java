package com.cyberpeace.space.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.space.constant.SpaceCommonConstant;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.*;
import com.cyberpeace.space.service.*;
import com.cyberpeace.space.service.context.frame.*;
import com.cyberpeace.space.service.context.strategy.SpaceStrategyBo;
import com.cyberpeace.space.service.context.tactic.SpaceTacticBo;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 空间行动框架Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ServiceImpl
 * @see SpaceActionFrameService
 * @since 1.0.0
 */
/**
 * 这段代码是一个Spring Boot服务类`SpaceActionFrameServiceImpl`的实现，主要负责管理与“空间行动框架”相关的业务逻辑。以下是对其核心功能和结构的简要分析： 
 * ### 核心功能模块： 
 * 1. **框架计数**： 
 * - `countByType`：根据类型统计框架数量。 
 * 2. **框架层级获取**： 
 * - `getFrameLevels`：从数据库中获取框架数据，并解析其层级结构（包括目标、方法和动作），并填充名称信息。 
 * 3. **框架保存与更新**： 
 * - `saveFrame`：将指定的`SpaceFrameLevel`对象序列化后保存到数据库。 
 * - `saveFrame`（重载）：处理战法步骤的变化，并记录调用次数。 
 * - `difference`：计算旧战法步骤与新战法步骤之间的差异。 
 * 4. **框架使用记录**： 
 * - `recordUsage`：记录某种类型的框架使用情况。 
 * 5. **框架查询**： 
 * - `getFrame`：根据资源ID获取框架详细信息，并加载相关战法名称。 
 * - `searchFrame`：基于关键词搜索框架。 
 * - `getTuiyanRecFrame`：推荐相关框架。 
 * 6. **框架添加**： 
 * - `add`：新增框架，确保名称唯一性，并通过事务模板插入关联关系。 
 * 7. **辅助方法**： 
 * - `entity2RecBo`：将实体转换为推荐业务对象。 
 * - `createRel`：创建上下级关系。 
 * - `getOrException`：获取框架或抛出异常。 
 * - `getTacticNameMap`：获取战法名称映射。 
 * ### 技术特点： 
 * - 使用了MyBatis Plus进行数据库操作。 
 * - 利用了Guava库中的`Maps`和`Sets`来处理集合操作。 
 * - JSON序列化/反序列化使用了`fastjson`。 
 * - 日志记录通过`slf4j`实现。 
 * - 事务管理依赖于Spring的`TransactionTemplate`。 
 * ### 总结： 
 * 该类实现了复杂的业务逻辑，涉及框架数据的多层次管理和动态调整，同时具备良好的扩展性和可维护性。 
**/
@Service
@Slf4j
public class SpaceActionFrameServiceImpl extends ServiceImpl<SpaceActionFrameMapper, SpaceActionFrame> implements SpaceActionFrameService {


	@Autowired
	private SpaceActionTargetService targetService;

	@Autowired
	protected TransactionTemplate transactionTemplate;

	@Autowired
	protected SpaceActionRelationService relationService;

	@Autowired
	private SpaceActionMethodService methodService;

	@Autowired
	private SpaceActionService actionService;
	
	@Autowired
	private SpaceActionFrameUseMapper useMapper;

	@Autowired
	private SpaceTacticAttackService attackService;

	@Autowired
	private SpaceTacticDefenseService defenseService;

	@Autowired
	private SpaceActionFrameTargetTypeMapper targetTypeMapper;

	@Autowired
	private SpaceActionFrameEventTypeMapper eventTypeMapper;

	@Autowired
	private SpaceTacticAttackMapper tacticAttackMapper;

	@Autowired
	private SpaceTacticDefenseMapper tacticDefenseMapper;

	@Autowired
	private SpaceStrategyService strategyService;

	@Autowired
	private SpaceCallCountService callCountService;


	/**
	 * 计数由类型
	 *
	 * @param code 代号
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 * @see Integer
	 */
	@Override
	public Integer countByType(Integer code) {
		LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrame::getType, code);
		return this.count(wrapper);
	}

	/**
	 * 获取框架水平
	 *
	 * @param resourceId 资源id
	 * @return {@code SpaceFrameLevel }
	 * @throws
	 * @see String
	 * @see SpaceFrameLevel
	 */
	@Override
	public SpaceFrameLevel getFrameLevels(String resourceId) {
		SpaceActionFrame frame = this.getOrException(resourceId);
		SpaceFrameLevel level = JsonUtil.toJsonObject(frame.getFrame(),
				SpaceFrameLevel.class);
		if (null == level) {
			return new SpaceFrameLevel(resourceId, frame.getName());
		}

		// gather ids
		// List<String> targetIds = Lists.newArrayList();
		// List<SpaceActionTarget> targetList = Lists.newArrayList();
		List<String> methodIds = Lists.newArrayList();
		List<SpaceActionMethod> methodList = Lists.newArrayList();
		List<String> actionIds = Lists.newArrayList();
		List<SpaceAction> actionList = Lists.newArrayList();
		// for (SpaceFrameLevel target : level.getChildren()) {
		// targetIds.add(target.getResourceId());
		for (SpaceFrameLevel method : level.getChildren()) {
			methodIds.add(method.getResourceId());
			for (SpaceFrameLevel action : method.getChildren()) {
				actionIds.add(action.getResourceId());
			}
		}
		// }
		// get entities
// if (!targetIds.isEmpty()) {
// targetList = targetService.listByIds(targetIds);
// }
		if (!methodIds.isEmpty()) {
			methodList = methodService.listByIds(methodIds);
		}
		if (!actionIds.isEmpty()) {
			actionList = actionService.listByIds(actionIds);
		}
		// mapping
		// Map<String, SpaceActionTarget> targetMap = StreamApiUtils.toMap(targetList,
		// SpaceActionTarget::getResourceId, t -> t);
		Map<String, SpaceActionMethod> methodMap = StreamApiUtils.toMap(methodList,
				SpaceActionMethod::getResourceId, t -> t);
		Map<String, SpaceAction> actionMap = StreamApiUtils.toMap(actionList,
				SpaceAction::getResourceId, t -> t);
		// set name
		// int i = 0;
		int j = 0;
		// while (j < level.getChildren().size()) {
		// SpaceFrameLevel target = level.getChildren().get(i);
		// String targetId = target.getResourceId();
		// if (targetMap.containsKey(targetId)) {
		// target.setName(targetMap.get(targetId).getName());

		while (j < level.getChildren().size()) {
			SpaceFrameLevel method = level.getChildren().get(j);
			String methodId = method.getResourceId();
			if (methodMap.containsKey(methodId)) {
				method.setName(methodMap.get(methodId).getName());
				int k = 0;
				while (k < method.getChildren().size()) {
					SpaceFrameLevel action = method.getChildren().get(k);
					String actionId = action.getResourceId();
					if (actionMap.containsKey(actionId)) {
						action.setName(actionMap.get(actionId).getName());
						k += 1;
					} else {
						method.getChildren().remove(k);
					}
				}
				j += 1;
			} else {
				level.getChildren().remove(j);
			}
		}
		// i += 1;
		// } else {
		// level.getChildren().remove(i);
		// }
		// }
		return level;
	}

	/**
	 * 保存框架
	 *
	 * @param req 请求体
	 * @return boolean
	 * @throws
	 * @see SpaceFrameLevel
	 * @see boolean
	 */
	@Override
	public boolean saveFrame(SpaceFrameLevel req) {
		req.checkLevel(false);
		SpaceActionFrame frame = this.getOrException(req.getResourceId());
		frame.setFrame(JsonUtil.toJsonString(req));
		return this.updateById(frame);
	}

	@Override
	public void saveFrame(ActionFrameArrangeBo req) {
		SpaceActionFrame frame = this.getOrException(req.getResourceId());
		ActionFrameArrangeBo old;
		if (StringUtils.isBlank(frame.getFrame())) {
			old = new ActionFrameArrangeBo();
		} else {
			old = JsonUtil.toJsonObject(frame.getFrame(), ActionFrameArrangeBo.class);
			old.checkTacticDuplication();
		}
		// 差分计算战法使用次数
		Map<String, ActionFrameStep> oldMap = StreamApiUtils.toMap(old.getSteps(),
				ActionFrameStep::getResourceId, s -> s);
		Map<String, ActionFrameStep> newMap = StreamApiUtils.toMap(req.getSteps(),
				ActionFrameStep::getResourceId, s -> s);
		int usage = 0;
		for (String id : newMap.keySet()) {
			if (oldMap.containsKey(id)) {
				List<String> oldTacticIds = StreamApiUtils.map(oldMap.get(id).getTactics(),
						ActionFrameTactic::getResourceId);
				List<String> newTacticIds = StreamApiUtils.map(newMap.get(id).getTactics(),
						ActionFrameTactic::getResourceId);
				usage += this.difference(oldTacticIds, newTacticIds);
			} else {
				usage += newMap.get(id).getTactics().size();
			}
		}
		if (usage > 0) {
			callCountService.addCount(CallType.TACTIC, usage);
		}

		frame.setFrame(JsonUtil.toJsonString(req));
		this.updateById(frame);
	}

	/**
	 * 差异
	 *
	 * @param oldTacticIds 老战法id列表
	 * @param newTacticIds 新增功能战法id列表
	 * @return int
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 * @see int
	 */
	private int difference(List<String> oldTacticIds, List<String> newTacticIds) {
		int count = 0;
		for (String id : newTacticIds) {
			if (!oldTacticIds.contains(id)) {
				count += 1;
			}
		}
		return count;
	}

	/**
	 * 记录使用情况
	 *
	 * @param type 类型
	 * @throws
	 * @see String
	 */
	@Override
	public void recordUsage(String type) {
		if (!SpaceActionFrame.Type.TARGET.getName().equals(type)
				&& !SpaceActionFrame.Type.PROCESS.getName().equals(type)) {
			return;
		}
		String date = TimeUtil.format6(new Date());
		LambdaQueryWrapper<SpaceActionFrameUse> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SpaceActionFrameUse::getType, type);
		queryWrapper.eq(SpaceActionFrameUse::getDate, date);
		SpaceActionFrameUse use = useMapper.selectOne(queryWrapper);
		if (null == use) {
			use = new SpaceActionFrameUse();
			use.setType(type);
			use.setDate(date);
			use.setCount(1);
			useMapper.insert(use);
		} else {
			use.setCount(use.getCount() + 1);
			useMapper.updateById(use);
		}
	}

	/**
	 * 获取框架
	 *
	 * @param resourceId 资源id
	 * @return {@code ActionFrameArrangeBo }
	 * @throws
	 * @see String
	 * @see ActionFrameArrangeBo
	 */
	@Override
    public ActionFrameArrangeBo getFrame(String resourceId) {
		SpaceActionFrame frame = this.getOrException(resourceId);
		ActionFrameArrangeBo bo = JsonUtil.toJsonObject(frame.getFrame(), ActionFrameArrangeBo.class);
		if (null == bo) {
			bo = new ActionFrameArrangeBo();
			bo.setResourceId(resourceId);
			return bo;
		}
		List<String> attackIds = Lists.newArrayList();
		List<String> defenseIds = Lists.newArrayList();
		for (ActionFrameStep step : bo.getSteps()) {
			for (ActionFrameTactic tactic : step.getTactics()) {
				if(SpaceCommonConstant.TacticType.ATTACK.equals(tactic.getType())){
					attackIds.add(tactic.getResourceId());
				} else if(SpaceCommonConstant.TacticType.DEFENSE.equals(tactic.getType())){
					defenseIds.add(tactic.getResourceId());
				}
			}
		}
		List<SpaceTacticAttack> attackList = attackIds.isEmpty() ? Lists.newArrayList()
				: attackService.listByIds((attackIds));
		List<SpaceTacticDefense> defenseList = defenseIds.isEmpty() ? Lists.newArrayList()
				: defenseService.listByIds((defenseIds));
		Map<String, SpaceTacticAttack> attackMap = StreamApiUtils.toMap(attackList,
				SpaceTacticAttack::getResourceId, t -> t);
		Map<String, SpaceTacticDefense> defenseMap = StreamApiUtils.toMap(defenseList,
				SpaceTacticDefense::getResourceId, t -> t);
		for (ActionFrameStep step : bo.getSteps()) {
			List<ActionFrameTactic> tactics = Lists.newArrayList();
			for (ActionFrameTactic tactic : step.getTactics()) {
				if(SpaceCommonConstant.TacticType.ATTACK.equals(tactic.getType())){
					if(attackMap.containsKey(tactic.getResourceId())){
						tactic.setName(attackMap.get(tactic.getResourceId()).getName());
						tactics.add(tactic);
					}
				} else if(SpaceCommonConstant.TacticType.DEFENSE.equals(tactic.getType())){
					if(defenseMap.containsKey(tactic.getResourceId())){
						tactic.setName(defenseMap.get(tactic.getResourceId()).getName());
						tactics.add(tactic);
					}
				}
			}
			step.setTactics(tactics);
		}
		return bo;
	}

	/**
	 * 搜索框架
	 *
	 * @param req 请求体
	 * @return {@code List<SpaceActionFrameBo> }
	 * @throws
	 * @see String
	 * @see List<SpaceActionFrameBo>
	 */
	@Override
	public List<SpaceActionFrameBo> searchFrame(String req) {
		List<SpaceActionFrame> frameList = this.list();
		Map<String, SpaceActionFrame> frameMap = StreamApiUtils.toMap(frameList,
				SpaceActionFrame::getResourceId, t -> t);
		Map<String, List<String>> frameKeyWordsMap = StreamApiUtils.toMap(frameList,
				SpaceActionFrame::getResourceId,
				f -> JsonUtil.toJsonObjectArray(f.getKeyWords(), String.class));

		List<SpaceActionFrameTargetType> targetTypes = targetTypeMapper.selectList(null);
		List<SpaceActionFrameEventType> eventTypes = eventTypeMapper.selectList(null);
		Map<String, List<String>> keyWordsMap = Maps.newHashMap();
		Map<String, List<String>> keyWordsPrimaryMap = Maps.newHashMap();
		for (SpaceActionFrameTargetType type : targetTypes) {
			keyWordsMap.put(type.getResourceId(),
					JsonUtil.toJsonObjectArray(type.getKeyWords(), String.class));
			keyWordsPrimaryMap.put(type.getResourceId(),
					JsonUtil.toJsonObjectArray(type.getKeyWordsPrimary(), String.class));
		}
		for (SpaceActionFrameEventType type : eventTypes) {
			keyWordsMap.put(type.getResourceId(),
					JsonUtil.toJsonObjectArray(type.getKeyWords(), String.class));
			keyWordsPrimaryMap.put(type.getResourceId(),
					JsonUtil.toJsonObjectArray(type.getKeyWordsPrimary(), String.class));
		}

		Set<String> res = Sets.newLinkedHashSet();
		// 先匹配框架的关键字
		frameKeyWordsMap.forEach((k, v) -> {
			for (String word : v) {
				if (req.contains(word)) {
					res.add(k);
					break;
				}
			}
		});
		// 匹配到了名称和场景关键字，之后只匹配类型关键字，不匹配基础关键字；没匹配到名称和场景关键字，之后匹配类型关键字，为空再匹配基础关键字
		Set<String> types = Sets.newHashSet();
		// 匹配类型关键字
		keyWordsMap.forEach((k, v) -> {
			for (String word : v) {
				if (req.contains(word)) {
					types.add(k);
				}
			}
		});

		if (res.isEmpty() && types.isEmpty()) {
			// 未匹配到类型关键字，匹配类型基础关键字
			keyWordsPrimaryMap.forEach((k, v) -> {
				for (String word : v) {
					if (req.contains(word)) {
						types.add(k);
					}
				}
			});
		}
		// 都没匹配到，返回空列表
		if (res.isEmpty() && types.isEmpty()) {
			return Lists.newArrayList();
		}
		// 类型相符的加入结果
		for (SpaceActionFrame frame : frameList) {
			if (types.contains(frame.getCategoryId())) {
				res.add(frame.getResourceId());
			}
		}
		List<SpaceActionFrame> frames = StreamApiUtils.map(res, frameMap::get);
		List<SpaceActionFrameBo> boList = StreamApiUtils.map(frames,
				frame -> frame.entity2Bo(null));
		return boList;
	}

	@Override
	public List<SpaceActionFrameRecBo> getTuiyanRecFrame(String categoryId, String keyWord) {
		LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrame::getCategoryId, categoryId);
		if (StringUtils.isNotBlank(keyWord)) {
			wrapper.and(wrapperKeyWord -> getKeyWordWrapper(keyWord, wrapperKeyWord));
		}
		List<SpaceActionFrame> frameList = this.list(wrapper);

		List<SpaceActionFrameRecBo> res = StreamApiUtils.map(frameList, this::entity2RecBo);
		return res;
	}

	@Override
	public void add(SpaceActionFrame entity) {

		CrCommonValidator.validateNameLen(entity.getName(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
		LambdaQueryWrapper<SpaceActionFrame> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(SpaceActionFrame::getName, entity.getName());
		wrapper.eq(SpaceActionFrame::getType, entity.getType());
		SpaceActionFrame one = baseMapper.selectOne(wrapper);
		if (null != one && (StringUtils.isBlank(entity.getResourceId())
				|| !one.getResourceId().equals(entity.getResourceId()))) {
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				baseMapper.insert(entity);
				relationService.createRelation(com.google.common.collect.Lists.newArrayList(), entity.getResourceId());
			}
		});
	}

	private SpaceActionFrameRecBo entity2RecBo(SpaceActionFrame frame){
		SpaceActionFrameRecBo bo = BeanUtils.copy(frame, SpaceActionFrameRecBo.class);
		bo.setTag(JsonUtil.toJsonObjectArray(frame.getTag(), String.class));
		
		bo.setFrame(frame.getArrangeBo());
		// collect tacticIds
		List<String> tacticIds = Lists.newArrayList();
		for (ActionFrameStep step : bo.getFrame().getSteps()) {
			for (ActionFrameTactic tactic : step.getTactics()) {
				tacticIds.add(tactic.getResourceId());
			}
		}
		// search tactics
		List<SpaceTacticAttack> attackList = tacticIds.isEmpty() ? Lists.newArrayList()
				: tacticAttackMapper.selectBatchIds(tacticIds);
		List<SpaceTacticDefense> defenseList = tacticIds.isEmpty() ? Lists.newArrayList()
				: tacticDefenseMapper.selectBatchIds(tacticIds);
		Map<String, String> tacticNameMap = StreamApiUtils.toMap(attackList,
				SpaceTacticAttack::getResourceId, SpaceTacticAttack::getName);
		tacticNameMap.putAll(StreamApiUtils.toMap(defenseList, SpaceTacticDefense::getResourceId,
				SpaceTacticDefense::getName));
		// set tactic name
		for (ActionFrameStep step : bo.getFrame().getSteps()) {
			for (ActionFrameTactic tactic : step.getTactics()) {
				if (tacticNameMap.containsKey(tactic.getResourceId())) {
					tactic.setName(tacticNameMap.get(tactic.getResourceId()));
				}
			}
		}
		// tactic bo list
		List<SpaceTacticBo> tactics = StreamApiUtils.map(attackList,attack -> {
			SpaceTacticBo tacticBo = BeanUtils.copy(attack, SpaceTacticBo.class);
			List<String> toolIds = JsonUtil.toJsonObjectArray(attack.getTool(), String.class);
			tacticBo.setToolCount(toolIds.size());
			JSONArray actions = JsonUtil.stringToArray(attack.getAction());
			tacticBo.setActionCount(actions.size());
			tacticBo.setType(SpaceCommonConstant.TacticType.ATTACK);
			return tacticBo;
		});
		tactics.addAll(StreamApiUtils.map(defenseList,defense -> {
			SpaceTacticBo tacticBo = BeanUtils.copy(defense, SpaceTacticBo.class);
			List<String> toolIds = JsonUtil.toJsonObjectArray(defense.getTool(), String.class);
			tacticBo.setToolCount(toolIds.size());
			JSONArray actions = JsonUtil.stringToArray(defense.getAction());
			tacticBo.setActionCount(actions.size());
			tacticBo.setType(SpaceCommonConstant.TacticType.DEFENSE);
			return tacticBo;
		}));

		bo.setTactics(tactics);

		// strategy ids
		List<String> strategyIds = Lists.newArrayList();
		for (SpaceTacticAttack attack : attackList) {
			strategyIds.addAll(JsonUtil.toJsonObjectArray(attack.getStrategy(), String.class));
		}
		for (SpaceTacticDefense defense : defenseList) {
			strategyIds.addAll(JsonUtil.toJsonObjectArray(defense.getStrategy(), String.class));
		}
		if(!strategyIds.isEmpty()){
			List<SpaceStrategy> strategyList = strategyService.listByIds(strategyIds);
			List<SpaceStrategyBo> strategyBos = StreamApiUtils.map(strategyList, strategy -> {
				SpaceStrategyBo strategyBo = BeanUtils.copy(strategy, SpaceStrategyBo.class);
				strategyBo.setRoute(strategyService.getFrameRouteById(strategy.getFrameId()));
				return strategyBo;
			}) ;
			bo.setStrategies(strategyBos);
		}
		return bo;
	}

	private void getKeyWordWrapper(String keyWord, LambdaQueryWrapper<SpaceActionFrame> wrapper) {
		wrapper.like(SpaceActionFrame::getName, keyWord);
		
		List<String> tacticIds = Lists.newArrayList();
		LambdaQueryWrapper<SpaceTacticAttack> attackLambdaQueryWrapper = new LambdaQueryWrapper<>();
		attackLambdaQueryWrapper.like(SpaceTacticAttack::getName, keyWord);
		List<SpaceTacticAttack> attackList = tacticAttackMapper.selectList(attackLambdaQueryWrapper);
		tacticIds.addAll(StreamApiUtils.map(attackList, SpaceTacticAttack::getResourceId));

		LambdaQueryWrapper<SpaceTacticDefense> defenseLambdaQueryWrapper = new LambdaQueryWrapper<>();
		defenseLambdaQueryWrapper.like(SpaceTacticDefense::getName, keyWord);
		List<SpaceTacticDefense> defenseList = tacticDefenseMapper.selectList(defenseLambdaQueryWrapper);
		tacticIds.addAll(StreamApiUtils.map(defenseList, SpaceTacticDefense::getResourceId));
		for (String tacticId : tacticIds) {
			wrapper.or().like(SpaceActionFrame::getFrame, tacticId);
		}
	}


    private SpaceActionRelation createRel(String upperId, String lowerId) {
		SpaceActionRelation rel = new SpaceActionRelation();
		rel.setUpperId(upperId);
		rel.setLowerId(lowerId);
		return rel;
	}

	private SpaceActionFrame getOrException(String resourceId) {
		SpaceActionFrame frame = this.getById(resourceId);
		if (null == frame) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return frame;
	}
	
	@Override
	public Map<String, String> getTacticNameMap(ActionFrameArrangeBo bo) {
		List<String> tacticIds = Lists.newArrayList();
		for (ActionFrameStep step : bo.getSteps()) {
			for (ActionFrameTactic tactic : step.getTactics()) {
				tacticIds.add(tactic.getResourceId());
			}
		}
		List<SpaceTacticAttack> attackList = tacticIds.isEmpty() ? Lists.newArrayList()
				: tacticAttackMapper.selectBatchIds(tacticIds);
		List<SpaceTacticDefense> defenseList = tacticIds.isEmpty() ? Lists.newArrayList()
				: tacticDefenseMapper.selectBatchIds(tacticIds);
		Map<String, String> tacticNameMap = StreamApiUtils.toMap(attackList,
				SpaceTacticAttack::getResourceId, SpaceTacticAttack::getName);
		tacticNameMap.putAll(StreamApiUtils.toMap(defenseList, SpaceTacticDefense::getResourceId,
				SpaceTacticDefense::getName));
		return tacticNameMap;
	}
}
