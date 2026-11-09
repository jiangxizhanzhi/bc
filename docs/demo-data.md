# 首页实验权限与每日演示数据

首页实验统计列表、对应人员明细、实验详情、报告及 PDF / DOCX 导出根据登录人员的参与关系过滤。教员、学员仅查看参与的实验，管理员和超级管理员查看全部。身份来自登录认证上下文；外部接口原有的接口认证仍然保留，还需携带有效登录凭据。查询参数及响应结构保持不变。其余首页趋势、人数及得分榜单仍使用全局统计。

## 配置与运行

环境配置文件 `env/{dev,test,prod}/modules/ojj.properties` 中新增：

```properties
ojj.demoDataEnabled=true
```

默认开启。关闭后定时入口和已有手动入口均停止新增，不清理已生成的数据。按原有配置加载方式修改配置并重启应用后生效。

任务使用 `TZ` 时区（默认 `Asia/Shanghai`），每天 00:10 为前一自然日生成数据。70% 的执行日期生成一场，30% 生成两场；开始时间在 09:00～16:00，每场持续两小时。不限制月份，不补跑更早日期。

样本为关联完整、人员仍有效、拓扑摘要可读的已结束实验。演示数据沿用样本的人员、实验类别、任务、提交和成绩，计入全局统计、榜单、个人参与记录与既有课程参与关系。各实验使用独立 ID，任务与角色编排同步映射。拓扑摘要保存为快照；运行场景、复盘场景等关联清空，不创建或启动真实仿真与虚拟机。

## 数据迁移与验证

部署时通过现有 Flyway 流程执行 `V5.6.0.20261109.1__demo_data.sql`，增加：

- `cr_demo_data_run`：业务日期为主键，记录当天完成的场次数。
- `cr_demo_data_room`：记录生成实验、来源实验、业务日期及拓扑摘要。生成的数据不会再次成为样本。

一次执行的标记与全部关联数据在同一事务内提交。定时、手动或多实例同时执行相同业务日期时仅一次生效。写入失败全部回滚；无有效样本时记录原因，不留下成功标记。

可以只读核对生成情况：

```sql
SELECT business_date, generated_count, create_time
FROM cr_demo_data_run ORDER BY business_date DESC;

SELECT business_date, room_id, source_room_id
FROM cr_demo_data_room ORDER BY business_date DESC;
```

定向测试位于 `cr/team_train/src/test`，覆盖参与权限、分页总数、越权报告及导出、每日数量、时区、开关、并发去重、回滚和业务关联完整性。运行：

```sh
mvn -pl template -am -Dtest=TeamRoomReadPermissionTest,TeamRoomReportPermissionTest,XdStatisticsReadScopeTest,XdDemoDataIntegrationTest,MybatisFillHandlerTest -Dsurefire.failIfNoSpecifiedTests=false test
```

2026-11-09 本地验证及 review 结果：Java 8 定向编译通过；上述五个测试类共 27 项测试通过，无失败或跳过。数据库测试使用真实 MyBatis Mapper 和 H2 内存数据库，覆盖并发唯一约束、整批回滚、拓扑快照展示，以及实际报告和导出数据组装；PDF / DOCX 入口另验证了越权拒绝。

Review 修正了两项问题并补充回归测试：演习文书和阶段名称保持原文，仅重映射编排中的任务 ID；历史记录缺失创建、更新时间时，生成记录使用对应实验的开始、结束时间，避免缺失时间或被插入填充改为任务执行当天。

完整 Maven 构建受原工程定制依赖 `org.slf4j:slf4j-log4j12:1.7.28-cp1` 缺失影响。定向验证采用临时公开依赖类路径，编译本次改动及其所需的 317 个源码文件，不包含依赖定制 `TraceIdContext` 的既有 HTTP 实现；工程日志依赖声明未修改。本轮未执行部署、线上迁移或线上造数，也未生成实际 PDF / DOCX 文件。
