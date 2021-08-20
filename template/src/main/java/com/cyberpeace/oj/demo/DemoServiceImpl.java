package com.cyberpeace.oj.demo;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.dao.mapper.LevelMapper;
import com.cyberpeace.oj.rtks.dao.mapper.RoleMapper;

@Service
public class DemoServiceImpl implements DemoService {

	@Autowired
	private LevelMapper levelMapper;

	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 根据实际需要连接的数据库，设置Qualifier的名字
	 * Qualifier = 数据库名+TransactionTemplate
	 */
	@Autowired
	@Qualifier(value = "rtksTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	@Override
	public void transactionDemo() {
		Level level = new Level();
		Role role = new Role();
		String uuid = UUID.randomUUID().toString();
		level.setName(uuid);
		level.setDescription(uuid);
		level.setParent(uuid);
		level.setFrame(uuid);

		role.setName(uuid);
		role.setDescription(uuid);
		role.setCategory(1);
		role.setLevelId(uuid);

		// 事务只处理数据库相关操作
		// 调用远程服务、或者其他费时操作，放到事务之外

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				levelMapper.insert(level);

				int random = new Random().nextInt(10);
				System.out.println(random);
				// 概率性出错
				if (random > 5) {
					throw new RuntimeException();
				}
				roleMapper.insert(role);
			}
		});
	}
}
