package com.cyberpeace.oj.config.db;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.cyberpeace.oj.common.util.SystemPropUtil;

@DependsOn("dataSourceConfig")
@Configuration
public class FlywayConfig {

	@Autowired
	private DataSourceConfig dataSourceConfig;

	@PostConstruct
	public void migrateFlyway() {
		String needMigrateFlag = "false";

		// 0、如果设置了不允许自动刷库，跳过该功能
		String needMigrate = SystemPropUtil.getProp("NEED_MIGRATE");

		// 明确设置了不允许刷库才不刷库
		if (StringUtils.equals(needMigrateFlag, needMigrate)) {
			return;
		}

		// 1、获取需要自动刷库的db
		// 格式dbname1,dbname2
		String needMigrateDbs = SystemPropUtil.getProp("MIGRATE_DB");

		String[] schemaNames = StringUtils.split(needMigrateDbs, ",");

		if (null == schemaNames || schemaNames.length == 0) {
			return;
		}

		for (String schemaName : schemaNames) {
//			migrateDb(schemaName);
		}
	}

	/**
	 * 执行实际刷库
	 * @param schemaName
	 */
	private void migrateDb(String schemaName) {
		DataSource dataSource = dataSourceConfig.getDataSource(schemaName);
		String locations = "static/sql/" + schemaName;

		Flyway flyway = Flyway.configure().baselineOnMigrate(true).dataSource(dataSource)
				.schemas(schemaName).validateOnMigrate(false).ignoreFutureMigrations(false)
				.locations(locations).load();

		flyway.migrate();
	}
}
