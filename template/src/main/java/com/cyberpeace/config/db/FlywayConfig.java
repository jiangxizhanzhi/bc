package com.cyberpeace.config.db;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yinqingrong
 */
@Slf4j
@Configuration
@DependsOn({ "dataSourceConfig", "oj", "rtks" })
public class FlywayConfig {

	@Autowired
	private DataSourceConfig dataSourceConfig;

	@PostConstruct
	public void migrateFlyway() {
		String needMigrateFlag = "0";

		// 0、如果设置了不允许自动刷库，跳过该功能
		String needMigrate = SystemPropUtil.getProp("NEED_MIGRATE");
		log.info(needMigrate);
		// 明确设置了不允许刷库才不刷库
		if (StringUtils.equals(needMigrateFlag, needMigrate)) {
			log.info("auto migrate skipped!");

			return;
		}

		// 1、获取需要自动刷库的db
		// 格式dbname1,dbname2
		String needMigrateDbs = SystemPropUtil.getProp("MIGRATE_DB");
		log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + needMigrateDbs);
		String[] configs = StringUtils.split(needMigrateDbs, "|");

		if (null == configs || configs.length == 0) {
			return;
		}

		for (String config : configs) {
			migrateDb(config);
		}
	}

	/**
	 * 执行实际刷库
	 * @param config
	 */
	private void migrateDb(String config) {
		String[] ss = StringUtils.split(config, "&");

		String datasourceName = ss[0];
		String schemaName = ss[1];

		DataSource dataSource = dataSourceConfig.getDataSource(datasourceName);
		String sqlLocations = "static/sql/" + datasourceName;

		log.info(
				"config loaded! prepare to migrateDb, datasourceName=[{}], schemaName=[{}], sqlLocation=[{}]",
				new Object[] { datasourceName, schemaName, sqlLocations });

		Flyway flyway = Flyway.configure().baselineOnMigrate(true).dataSource(dataSource)
				.schemas(schemaName).validateOnMigrate(false).ignoreFutureMigrations(false)
				.locations(sqlLocations).load();

		log.info(
				"config loaded! migrateDb, datasourceName=[{}], schemaName=[{}], sqlLocation=[{}] success!",
				new Object[] { datasourceName, schemaName, sqlLocations });

		flyway.migrate();
	}
}
