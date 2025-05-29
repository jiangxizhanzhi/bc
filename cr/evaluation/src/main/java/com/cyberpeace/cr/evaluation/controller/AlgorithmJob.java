package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAlgorithmMapper;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

/**
 * 定时一周清理一次不再使用的备份算法
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个定时任务类 `AlgorithmJob`，用于每周清理一次数据库中未被使用的备份算法。以下是代码的主要功能和逻辑分析： 
 * 1. **类与注解**： 
 * - 类 `AlgorithmJob` 使用了 `@Component` 注解，表明这是一个 Spring 管理的组件。 
 * - 使用了 `@Slf4j` 注解，方便日志记录。 
 * 2. **依赖注入**： 
 * - `EvalAlgorithmService` 和 `EvalAlgorithmMapper` 被 `@Autowired` 注入，分别用于处理业务逻辑和服务层的操作。 
 * 3. **定时任务**： 
 * - 使用 `@Scheduled(fixedRate = 7 * 24 * 60 * 60 * 1000)` 定义了一个定时任务，每7天执行一次。 
 * - 方法 `deleteAllUnUsedBackup` 是具体的定时任务逻辑。 
 * 4. **逻辑流程**： 
 * - 首先记录日志，提示开始删除未使用的备份算法。 
 * - 调用 `evalAlgorithmMapper.getAll()` 获取所有算法列表。 
 * - 如果算法列表为空，则直接返回，不进行后续操作。 
 * - 调用 `evalAlgorithmService.selectUsedAlgorithmIds()` 获取当前正在使用的算法ID集合。 
 * - 使用集合操作（`HashSet` 的 `removeAll`）找出未使用的算法ID。 
 * - 最后调用 `evalAlgorithmMapper.deleteAllUnUsedBackup()` 删除这些未使用的备份算法。 
 * 5. **注意事项**： 
 * - 代码中假设 `evalAlgorithmMapper.getAll()` 不会返回空列表，否则在调用 `selectUsedAlgorithmIds` 时可能会出错。 
 * - 使用了 Guava 的 `Lists.newArrayList` 来创建未使用算法ID的列表。 
 * 总结：该类通过定时任务每周清理一次未使用的备份算法，确保数据库的整洁性和高效性。 
**/
@Slf4j
@Component
public class AlgorithmJob {

	/**
	 * 评估算法Service
	 */
	@Autowired
	private EvalAlgorithmService evalAlgorithmService;

	/**
	 * 评估算法Mapper
	 */
	@Autowired
	private EvalAlgorithmMapper evalAlgorithmMapper;

	@Scheduled(fixedRate = 7 * 24 * 60 * 60 * 1000)
	public void deleteAllUnUsedBackup() {

		log.info("delete all unused backup algorithms!");

		List<EvalAlgorithm> evalAlgorithms = evalAlgorithmMapper.getAll();

		List<String> allAlgorithmIds = StreamApiUtils.map(evalAlgorithms,
				EvalAlgorithm::getResourceId);
		// "evalAlgorithms"为空, 传入"selectUsedAlgorithmIds"报错
		if (evalAlgorithms.isEmpty()) {
			return;
		}
		List<String> usedAlgorithmIds = evalAlgorithmService.selectUsedAlgorithmIds(
				StreamApiUtils.map(evalAlgorithms, EvalAlgorithm::getResourceId));

		List<String> unUsedAlgorithmIds = Lists.newArrayList();

		HashSet h1 = new HashSet(allAlgorithmIds);
		HashSet h2 = new HashSet(usedAlgorithmIds);
		h1.removeAll(h2);

		unUsedAlgorithmIds.addAll(h1);
		evalAlgorithmMapper.deleteAllUnUsedBackup(unUsedAlgorithmIds);

	}

}

