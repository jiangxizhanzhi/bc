# 首页实验权限与年度演示数据

首页实验统计列表、对应人员明细、实验详情、报告及 PDF / DOCX 导出按登录人员参与关系过滤。教员、学员查看自己参与的实验，管理员和超级管理员查看全部；其余首页趋势、人数及得分榜单沿用全局统计。外部接口仍需原接口认证及有效登录凭据，查询参数和响应结构不变。

## 排期与人员

从 2026 年开始，每名有效学员每年完成每个具体实验模板一次：

| 时间窗 | 教员登录用户名 | 实验类型 |
| --- | --- | --- |
| 4—6 月 | `shenjuan` | 新型网络基础、攻防（6、7） |
| 10—12 月 | `liuwenyan` | 云计算资源、云网络资源、扫描探测攻击、漏洞挖掘与利用、拒绝服务攻击（1—5） |

范围为 `xd_template_category=1` 的实验模板，排除科目训练与综合演训。学员来自用户服务的有效“学员”账号，教员从有效“教员”账号中按 `userName` 精确匹配。教员不存在或重名时跳过对应分组并记录原因，另一分组可继续生成。

按年度、模板 ID、学员 ID 的稳定散列分配三个月窗口内日期，同模板同日期的待补学员组成一场实验。人员列表排序不影响日期；新增学员按同一规则补缺，当前有效学员参与 2026 年以来各年度的检查。

沿用 `env/{dev,test,prod}/modules/ojj.properties` 中的 `ojj.demoDataEnabled=true` 开关。每天 00:10 按 `TZ` 时区（默认 `Asia/Shanghai`）检查从 2026 年起截至昨天的排期；未来日期到期再补。现有 `GET /cr/admin/xd_automatic/automatic` 执行相同检查，关闭开关后两个入口均停止新增。

## 补缺与生成规则

旧记录按年度和具体模板抵扣，必须同时满足：实验及训练记录位于对应月份且已结束，指定老师参与教员岗位并负责训练记录，学员实际任务覆盖模板全部学员岗位任务并集，各任务有已评估提交且状态为完成（成功或失败），并且已有最终成绩。不同房间的零散任务不拼成一次完整完成。

旧业务记录、提交和成绩保留。部分完成者另补完整实验，每项任务只新增一次提交。已生成记录同样可抵扣，旧版每日成功标记本身不能阻止检查缺口。历史记录无法通过任务模板 ID 确认完整覆盖时，不用于抵扣。

直接从模板生成已结束的业务记录，不依赖历史实验样本。各学员岗位的任务合并去重，统一到演示学员岗位，保留任务阶段名称并重映射任务 ID。实验、角色、任务、提交、评估、操作日志、成绩及训练记录保持关联。实验在排期日 09:00—16:00 开始，持续两小时，关联时间均位于该窗口。

综合成绩满分为 100，新增分数为 `round(clamp(75 + 10 × nextGaussian(), 0, 100))`，不强制每批达到精确均值和标准差。每项任务按该比例及自身满分取整，提交分和任务分相同，训练分保存实际任务得分之和；能力分保存为五项 0—5 整数，沿用训练/能力 30/70 权重字段。最终综合成绩沿用现有独立保存口径，首页、明细及报告读取同一值，旧成绩不重算。

课程关系按同模板、同学员已有课程归属去重继承，新增关系标记已完成。没有个人课程归属时仅补实验，不新建课程或成员关系。

拓扑摘要保存为快照；不创建或启动真实场景、虚拟机或复盘场景。缺失任务或拓扑摘要的模板跳过并记录原因。

## 迁移与并发

保留 T28 的 `V5.6.0.20261109.1__demo_data.sql`，追加 MySQL、达梦各自的 `V5.6.0.20261110.1__annual_demo_data.sql`，允许 `cr_demo_data_room.source_room_id` 为空。由模板生成的记录该字段为空，历史来源关联不变。

`cr_demo_data_run` 的业务日期行用于事务锁和场次数统计，不再表示当天不能追加。MySQL 使用原生 upsert 取得排他写锁，达梦使用 MERGE；随后按日期加锁并重新查询年度完成状态。MySQL 普通 INSERT 遇重复键会取得共享锁，故不采用重复键异常后升级锁的方式，避免并发锁升级冲突（[官方锁说明](https://dev.mysql.com/doc/refman/8.0/en/innodb-locks-set.html)）。

每批（模板、排期日）关联业务记录与计数在同一事务内提交，失败整批回滚，无缺口时不新增空成功标记。一次跨日期补数由多个批次组成，已经成功提交的其他批次保留；再次执行重新检查缺口。

只读核对：

```sql
SELECT business_date, generated_count, create_time
FROM cr_demo_data_run ORDER BY business_date DESC;

SELECT room.team_template_id, room.xd_type, demo.business_date,
       room.resource_id, record.user_id
FROM cr_demo_data_room demo
JOIN cr_team_room room ON room.resource_id = demo.room_id
JOIN cr_train_record record ON record.room_id = room.resource_id
ORDER BY demo.business_date, room.team_template_id;
```

## 本地验证

定向测试位于 `cr/team_train/src/test` 和 `template/src/test`，使用真实 MyBatis Mapper 与 H2 内存数据库，并模拟远程用户、拓扑和场景服务。运行：

```sh
mvn -pl template -am -Dtest=TeamRoomReadPermissionTest,TeamRoomReportPermissionTest,XdStatisticsReadScopeTest,XdDemoDataIntegrationTest,MybatisFillHandlerTest -Dsurefire.failIfNoSpecifiedTests=false test
```

当前环境的完整 Maven 构建仍受定制日志依赖影响。定向验证复用临时公开依赖类路径，以 Java 8 编译改动和所需源码，生产依赖声明不变。H2 的 SQL 测试不代替真实 MySQL/达梦验证；本任务未部署应用、执行线上迁移或实际造数，也未生成实际 PDF / DOCX 文件。

2026-11-10 本地结果：Java 8 定向编译 320 个相关生产源码文件通过，五个测试类共 48 项测试通过，无失败或跳过；覆盖新旧日期行并发去重、逐表保留旧记录、年度与截止边界、无历史样本、任务并集、课程去重、成绩抽样、两套增量迁移及达梦 MERGE 分支。迁移与 SQL 测试均在 H2 内存数据库中执行。完整 Maven 在线编译明确受 `org.slf4j:slf4j-log4j12:1.7.28-cp1` 无法获取影响。
