SET IDENTITY_INSERT "ai_user_profile_report" ON;
SET IDENTITY_INSERT "ai_user_profile_report" OFF;
SET IDENTITY_INSERT "attachment" ON;
SET IDENTITY_INSERT "attachment" OFF;
SET IDENTITY_INSERT "behavior_record" ON;
SET IDENTITY_INSERT "behavior_record" OFF;
SET IDENTITY_INSERT "cr_badge" ON;
SET IDENTITY_INSERT "cr_badge" OFF;
SET IDENTITY_INSERT "cr_business_template" ON;
SET IDENTITY_INSERT "cr_business_template" OFF;
SET IDENTITY_INSERT "cr_constants_config" ON;
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(1,'05b739fc686a073756399f79c34039b5',null,1,'情报侦查研判','Intelligence Investigation');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(2,'1102f907c7650ad541c381f59c700f5f',null,1,'密码破解','Password Cracking');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(3,'2eec84346538e972a962b165e499f52d',null,1,'终端渗透控制','Terminal Penetration Control');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(4,'2f4f15de59cd2dbadc5d8a582e9f6849',null,1,'威胁发现和检测','Threat Discovery And Detection');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(5,'3b6e90254227833303d908336b105ade',null,1,'内网渗透控制','Intranet Penetration Control');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(6,'446bbba739678a4e6e103e7d214d55aa',null,1,'网络应用控制破解','Network Application Control Cracking');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(7,'6753aa8cad6fa9760308871e707ea033',null,1,'网络侦查','Network Investigation');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(8,'8568759a4d514ff730ed6eb1721a2f22',null,1,'反侦查','Anti Detection');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(9,'a2e58506b8d588d7e1cef85f7350728a',null,1,'漏洞挖掘利用','Exploit');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(10,'a7914af4e20435cb7ad7b8b4ae0bac6a',null,1,'应急响应','Emergency Response');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(11,'b27501c31132b288df1bef259e2625e6',null,1,'网站渗透控制','Website Penetration Control');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(12,'bd43486dee157d5e5106d547801e2ea2',null,1,'系统加固','System Hardening');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(13,'c4173e927825d4ff52b112e268fb01ad',null,1,'数据分析','Data Analysis');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(14,'d0cf5fc50285e1b64c1dfc3adb9ba39d',null,1,'技术反制','Technological Countermeasures');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(15,'dff8a9b76f5536babae3d4c22943004d',null,1,'隐蔽能力','Concealment');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(16,null,1,2,'初级','Primary');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(17,null,2,2,'中级','Advanced');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(18,null,3,2,'高级','Senior');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(19,null,1,3,'渗透测试','Penetration Test');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(20,null,2,3,'运维防护','Security Operations');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(21,null,3,3,'系统仿真','System Emulation');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(22,null,4,3,'综合场景','Countermea');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(23,null,5,3,'其他','Others');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(24,null,1,4,'系统评估','System Evaluation');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(25,null,2,4,'渗透测试','Penetration Test');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(26,null,3,4,'安全验证','Safety Verification');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(27,null,4,4,'装备测评','Equipment Test');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(28,null,5,4,'漏洞测试','Vulnerability Test');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(29,null,1,5,'评分算法','Scoring Algorithm');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(30,null,2,5,'格式化算法','Format Algorithm');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(31,null,3,5,'模型匹配算法','Matching Algorithm');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(32,null,4,5,'聚合算法','Aggregating Algorithm');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(33,null,1,6,'效益型','Benefit Type');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(34,null,2,6,'成本型','Cost Type');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(35,null,3,6,'固定型','Fixed Type');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(36,null,4,6,'偏离型','Deviation Type');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(37,null,1,7,'百分比','Percentage');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(38,null,2,7,'周期','Cycle');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(39,null,3,7,'整数值','Integer');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(40,null,0,8,'Number','Number');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(41,null,1,8,'String','String');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(42,null,2,8,'Bool','Bool');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(43,null,3,8,'IP','IP');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(44,null,0,9,'初级','Primary');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(45,null,1,9,'中级','Intermediate');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(46,null,2,9,'高级','Advanced');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(47,null,0,10,'简单','Easy');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(48,null,1,10,'中等','Medium');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(49,null,2,10,'困难','Difficult');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(50,null,1,11,'已解出','Solved');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(51,null,0,11,'未解出','Unsolved');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(52,null,0,12,'理论题','Theory');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(53,null,2,12,'实操题','Practice');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(54,null,3,13,'课程','Course');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(55,null,4,13,'课时','Class');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(56,null,0,13,'理论题','Theory');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(57,null,2,13,'实操题','Practice');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(58,null,0,14,'未请求协助','Assistance Not Requested');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(59,null,1,14,'请求协助','Request Assistance');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(60,null,2,14,'协助中','Assistance In Progress');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(61,null,3,14,'结束协助','End Assistance');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(65,null,0,16,'理论课','Theory Class');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(66,null,1,16,'实验课','Experimental Class');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(67,null,0,17,'未配置','Not Configured');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(68,null,1,17,'选修','Elective');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(69,null,2,17,'必修','Required');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(70,null,1,18,'课程','Course');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(71,null,2,18,'课时','Class');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(72,null,3,18,'实操题','Practice');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(73,null,1,19,'笔记','Note');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(74,null,2,19,'实验报告','Report');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(75,null,0,20,'理论基础','Theoretical Basis');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(76,null,1,20,'真实漏洞','Real Vulnerability');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(77,null,2,20,'实践操作','Operation Practice');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(78,null,3,20,'课程','Course');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(79,null,4,20,'课时','Class');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(80,null,0,21,'单选','Single Choice');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(81,null,1,21,'多选','Multiple Choice');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(82,null,2,21,'判断','True Or False');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(83,null,3,21,'填空','Completion');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(84,null,4,21,'简答','Q & A');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(94,null,0,25,'未开始','Not Started');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(95,null,1,25,'进行中','In Progress');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(96,null,2,25,'已结束','Ended');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(97,null,0,26,'个人参赛','Personal');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(98,null,1,26,'团队参赛','Group');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(99,null,0,27,'理论赛','Theoretical Competition');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(100,null,1,27,'运维赛','Operation And Maintenance Competition');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(101,null,2,27,'夺旗赛','Flag Competition');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(102,null,3,27,'攻防赛','Offensive And Defensive Competition');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(103,null,4,27,'渗透赛','Infiltration Competition');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(104,null,5,27,'人工智能赛','AI Competition');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(105,null,0,28,'未开始','Not Started');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(106,null,1,28,'进行中','In Progress');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(107,null,2,28,'已结束','Ended');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(108,null,3,28,'暂停中','Suspended');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(109,null,1,31,'课程','Course');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(110,null,2,31,'课时','Class');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(111,null,3,31,'实践操作','Operation Practice');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(112,null,1,36,'单兵训练','Single Person Training');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(113,null,3,36,'团队训练','Team Training');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(114,null,4,36,'红蓝对抗','Red Blue Confrontation');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(118,null,1,38,'未评估','No Assess');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(119,null,2,38,'评估中','Assessing');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(120,null,3,38,'已评估','Has Assessed');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(121,null,4,38,'异常','Abnormal');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(122,null,1,15,'初级','Primary');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(123,null,2,15,'中级','Intermediate');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(124,null,3,15,'高级','Advanced');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(125,null,1,22,'初级','Primary');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(126,null,2,22,'中级','Intermediate');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(127,null,3,22,'高级','Advanced');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(128,null,1,23,'初级','Primary');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(129,null,2,23,'中级','Intermediate');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(130,null,3,23,'高级','Advanced');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(131,null,1,24,'初级','Primary');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(132,null,2,24,'中级','Intermediate');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(133,null,3,24,'高级','Advanced');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(134,null,0,37,'初级','Primary');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(135,null,1,37,'中级','Intermediate');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(136,null,2,37,'高级','Advanced');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(138,null,1,39,'已解出','Solved');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(139,null,2,39,'未开始','Not started');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(140,null,0,39,'尝试过','Attempted');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(141,null,1,40,'课程','Course');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(142,null,2,40,'课时','Class');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(143,null,3,40,'实操题','Practice');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(144,null,4,40,'课后练习','AfterClass');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(145,null,5,40,'理论题','Theory');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(146,null,1,45,'未开始','Not Started');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(147,null,2,45,'进行中','Ongoing');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(148,null,3,45,'已结束','Ended');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(149,null,1,46,'待提交','Not Submitted');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(150,null,2,46,'待批改','Not Evaluated');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(151,null,3,46,'已批改','Evaluated');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(152,null,1,47,'优','Excellent');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(153,null,2,47,'良','Good');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(154,null,3,47,'中','Average');
INSERT INTO "cr_constants_config"("id","resource_id","code","type","cn_name","en_name") VALUES(155,null,4,47,'差','Poor');

SET IDENTITY_INSERT "cr_constants_config" OFF;
SET IDENTITY_INSERT "cr_eval_aggregation" ON;
SET IDENTITY_INSERT "cr_eval_aggregation" OFF;
SET IDENTITY_INSERT "cr_eval_algorithm" ON;
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(1,'992c712d877c31529833a24d6ef6a347','内存使用率',null,0,2,0,'import json


def solution(data, other_arguments):
    """
    格式化算法模板
    :param data: 探针采集数据, jsonString
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 格式化结果字符串
    """
    _data = json.loads(data)
    return int(_data.get("memoryPercent"))
','[]',0,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:37:15.000000000','2022-06-23 19:07:11.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(2,'ac09d98b4025d967e55bc684ae4f9560','磁盘使用率',null,0,2,0,'import json


def solution(data, other_arguments):
    """
    格式化算法模板
    :param data: 探针采集数据, jsonString
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 格式化结果字符串
    """
    _data = json.loads(data)
    return int(_data.get("diskPercent"))
','[]',0,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:40:54.000000000','2022-06-23 19:07:29.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(3,'bb23247a834e0b34c225cd7e9b612a7c','CPU使用率',null,0,2,0,'import json


def solution(data, other_arguments):
    """
    格式化算法模板
    :param data: 探针采集数据, jsonString
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 格式化结果字符串
    """
    _data = json.loads(data)
    return int(_data.get("cpuPercent"))
','[]',0,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:41:40.000000000','2022-06-23 19:07:40.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(4,'4c2903a078ce12a7adb853b960e3ec04','JSON属性获取',null,0,2,0,'def solution(data: str, other_arguments) -> str:
    """
    格式化算法模板
    :param data: 探针采集数据, jsonString
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 格式化结果字符串
    """
    import json
    _data = json.loads(data)
    return _data.get(other_arguments[0])
','[{"defaultValue":"key","desc":"JSON对象KEY","paramName":"属性","paramType":"1"}]',1,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:42:41.000000000','2022-06-23 19:11:41.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(5,'df17fa0f5f52880128d079ed14f98eb7','绝对值范围',null,0,3,0,'from typing import Dict, List


def solution(data, other_arguments):
    """
    模型匹配算法模板
    :param data: {
         "baseTime": #{时间戳 ms}: int,
         "data": [
           {
                 "t": #{baseTime偏移量 ms}: int,
                 "v": #{格式化算法返回值}: str
           },
           { }...
       ]
    }
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 匹配结果. 为[0, 1, 1, ...], 0-未匹配, 1-匹配. 长度、索引应与data["data"]中元素一致
    """
    results = []
    _data: List[Dict] = data.get("data")

    for _item in _data:
        results.append(1 if other_arguments[0] <= int(_item.get("v")) <= other_arguments[1] else 0)

    return results
','[{"defaultValue":"0","desc":"","paramName":"Min","paramType":"0"},{"defaultValue":"100","desc":"","paramName":"Max","paramType":"0"}]',2,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:44:37.000000000','2022-06-23 19:21:24.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(6,'34151eee3021f4cc8d9642a5fe67afb5','绝对值过大',null,0,3,0,'
def solution(data, other_arguments):
    """
    模型匹配算法模板
    :param data: {
         "baseTime": #{时间戳 ms}: int,
         "data": [
           {
                 "t": #{baseTime偏移量 ms}: int,
                 "v": #{格式化算法返回值}: str
           },
           { }...
       ]
    }
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 匹配结果. 为[0, 1, 1, ...], 0-未匹配, 1-匹配. 长度、索引应与data["data"]中元素一致
    """
    results = []
    for i in data.get("data"):
        if int(i.get("v")) >= other_arguments[0]:
            results.append(1)
        else:
            results.append(0)

    return results','[{"defaultValue":"60","desc":"幅度","paramName":"幅度","paramType":"0"}]',1,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:45:32.000000000','2022-06-23 19:21:35.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(7,'3f686ec6badc22fd8bd8a4ca1dbd8a95','绝对值过小',null,0,3,0,'
def solution(data, other_arguments):
    """
    模型匹配算法模板
    :param data: {
         "baseTime": #{时间戳 ms}: int,
         "data": [
           {
                 "t": #{baseTime偏移量 ms}: int,
                 "v": #{格式化算法返回值}: str
           },
           { }...
       ]
    }
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 匹配结果. 为[0, 1, 1, ...], 0-未匹配, 1-匹配. 长度、索引应与data["data"]中元素一致
    """
    results = []
    for i in data.get("data"):
        if int(i.get("v")) <= other_arguments[0]:
            results.append(1)
        else:
            results.append(0)

    return results','[{"defaultValue":"60","desc":"幅度","paramName":"幅度","paramType":"0"}]',1,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:46:07.000000000','2022-06-23 19:21:50.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(8,'cb74b797f4dc095f344bb3deb6584373','关键字匹配',null,0,3,0,'from typing import Dict, List


def solution(data, other_arguments):
    """
    模型匹配算法模板
    :param data: {
         "baseTime": #{时间戳 ms}: int,
         "data": [
           {
                 "t": #{baseTime偏移量 ms}: int,
                 "v": #{格式化算法返回值}: str
           },
           { }...
       ]
    }
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 匹配结果. 为[0, 1, 1, ...], 0-未匹配, 1-匹配. 长度、索引应与data["data"]中元素一致
    """
    results = []
    _data: List[Dict] = data.get("data")

    for _item in _data:
        if not isinstance( _item.get("v"), str):
            results.append(0)
            continue
        results.append(1 if other_arguments[0] in _item.get("v") else 0)

    return results
','[{"defaultValue":"Keyword","desc":"匹配关键字","paramName":"关键字\t","paramType":"1"}]',1,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:47:38.000000000','2022-06-23 19:22:04.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(9,'b4e35ea158219701083147285c50f968','关键字非匹配',null,0,3,0,'from typing import Dict, List


def solution(data, other_arguments):
    """
    模型匹配算法模板
    :param data: {
         "baseTime": #{时间戳 ms}: int,
         "data": [
           {
                 "t": #{baseTime偏移量 ms}: int,
                 "v": #{格式化算法返回值}: str
           },
           { }...
       ]
    }
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 匹配结果. 为[0, 1, 1, ...], 0-未匹配, 1-匹配. 长度、索引应与data["data"]中元素一致
    """
    results = []
    _data: List[Dict] = data.get("data")

    for _item in _data:
        if not isinstance( _item.get("v"), str):
            results.append(0)
            continue
        results.append(1 if other_arguments[0] not in _item.get("v") else 0)

    return results
','[{"defaultValue":"Keyword","desc":"","paramName":"关键字","paramType":"1"}]',1,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:48:36.000000000','2022-06-23 19:22:12.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(10,'6813e38d6eb0195230626e1e09902a71','高于平均值',null,0,3,0,'from typing import List


def solution(data, other_arguments) -> List[int]:
    """
    模型匹配算法模板
    :param data: {
         "baseTime": #{时间戳 ms}: int,
         "data": [
           {
                 "t": #{baseTime偏移量 ms}: int,
                 "v": #{格式化算法返回值}: str
           },
           { }...
       ]
    }
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 匹配结果. 为[0, 1, 1, ...], 0-未匹配, 1-匹配. 长度、索引应与data["data"]中元素一致
    """
    results = []
    _data = data.get("data")
    values = [int(_item["v"]) for _item in _data]
    _range = other_arguments[0] / 100

    avg = sum(values) / len(values)
    score = int(avg * _range)
    for v in values:
        if v - score >= avg:
            results.append(1)
        else:
            results.append(0)

    return results','[{"defaultValue":"50","desc":"","paramName":"幅度","paramType":"0"}]',1,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:50:44.000000000','2022-06-23 18:50:44.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(11,'f1e4ccd6698aa7f4dfb9d07e97fdf7b6','匹配次数绝对值衰减',null,0,1,0,'

def solution(data, other_arguments):
    """
    评分算法模板
    :param data: {
         "baseTime": #{时间戳}: ms,
         "data": [
           {
                 "t": #{baseTime偏移量 ms}: int,
                 "v": #{格式化算法返回值}: str,
                 "m": #{是否被匹配, 0|1}: int
           },
          { }...
        ]
   }
   :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
   :return: 评分结果, 0~100
    """
    result = 100 - len([i.get("v") for i in data.get("data") if i.get("m")]) * other_arguments[0]
    return max(0, result)
','[{"defaultValue":"5","desc":"衰减幅度","paramName":"幅度","paramType":"0"}]',1,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:55:46.000000000','2022-06-23 19:22:44.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(12,'6c45132ff7b193a160a70ca33d0e6f20','匹配次数百分比衰减',null,0,1,0,'

def solution(data, other_arguments):
    """
    评分算法模板
    :param data: {
         "baseTime": #{时间戳}: ms,
         "data": [
           {
                 "t": #{baseTime偏移量 ms}: int,
                 "v": #{格式化算法返回值}: str,
                 "m": #{是否被匹配, 0|1}: int
           },
          { }...
        ]
   }
   :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
   :return: 评分结果, 0~100
    """
    result = 100
    matched_count = len([i.get("v") for i in data.get("data") if i.get("m")])

    for i in range(matched_count):
        result = (100 - other_arguments[0]) / 100 * result

    return int(result)
','[{"defaultValue":"5","desc":"衰减幅度","paramName":"幅度","paramType":"0"}]',1,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:56:33.000000000','2022-06-23 19:24:38.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(13,'fdf0b59f18c473622d8182e7cd94bb87','匹配次数绝对值增长',null,0,1,0,'

def solution(data, other_arguments):
    """
    评分算法模板
    :param data: {
         "baseTime": #{时间戳}: ms,
         "data": [
           {
                 "t": #{baseTime偏移量 ms}: int,
                 "v": #{格式化算法返回值}: str,
                 "m": #{是否被匹配, 0|1}: int
           },
          { }...
        ]
   }
   :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
   :return: 评分结果, 0~100
    """
    result = len([i.get("v") for i in data.get("data") if i.get("m")]) * other_arguments[0]
    return min(100, result) if result > 100 else result','[{"defaultValue":"5","desc":"增长幅度","paramName":"幅度","paramType":"0"}]',1,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:57:48.000000000','2022-06-23 19:24:47.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(14,'f417010759937eedd2994f9755228168','计算最高分',null,0,4,0,'def solution(data, other_arguments):
    """
    聚合算法模板
    :param data: 包含元素均为0~100 int
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 聚合算法结果, 0~100
    """
    return max([int(i) for i in data])','[]',0,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 18:59:33.000000000','2022-06-23 19:25:01.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(15,'8dd5037ab80e6bdc2451e20bd161c916','计算最低分',null,0,4,0,'def solution(data, other_arguments):
    """
    聚合算法模板
    :param data: 包含元素均为0~100 int
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 聚合算法结果, 0~100
    """
    return min([int(i) for i in data])','[]',0,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 19:00:28.000000000','2022-06-23 19:25:12.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(16,'86983b7bad3567cc2e36db0c1f7d3cd9','算术平均',null,0,4,0,'def solution(data, other_arguments):
    """
    聚合算法模板
    :param data: 包含元素均为0~100 int
    :param other_arguments: List[int, str, bool], 可配置参数列表, 长度、索引一致
    :return: 聚合算法结果, 0~100
    """
    variables = [int(i) for i in data]
    return sum(variables) / len(variables)
','[]',0,'3f655d87112f4ae094bbaf70830e20aa','2022-06-23 19:03:12.000000000','2022-06-23 19:03:12.000000000',0,0);
INSERT INTO "cr_eval_algorithm"("id","resource_id","name","description","state","type","algorithm_language","code","params","params_num","create_user_id","create_time","update_time","mode","builtin") VALUES(17,'230ced924ec485dd37c3945b4d4c13d4','层次分析法',null,0,4,0,'import numpy as np


def solution(matrix_data, data, other_arguments):
    RI = (0, 0, 0.58, 0.9, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49)
    input_matrix = np.array(matrix_data)
    n, n1 = input_matrix.shape
    assert n == n1, ''不是一个方阵''
    for i in range(n):
        for j in range(n):
            if np.abs(input_matrix[i, j] * input_matrix[j, i] - 1) > 1e-7:
                raise ValueError(''不是反互对称矩阵'')

    eigenvalues, eigenvectors = np.linalg.eig(input_matrix)

    max_idx = np.argmax(eigenvalues)
    eigen = eigenvectors[:, max_idx].real
    eigen = eigen / eigen.sum()

    res = 0
    if n > 9:
        return res
    for i in range(len(eigen)):
        res += data[i] * eigen[i]
    return round(res, 2)','[]',0,'3f655d87112f4ae094bbaf70830e20aa','2023-05-05 16:25:14.000000000','2023-05-05 16:25:45.000000000',1,0);

SET IDENTITY_INSERT "cr_eval_algorithm" OFF;
SET IDENTITY_INSERT "cr_eval_algorithm_ref" ON;
SET IDENTITY_INSERT "cr_eval_algorithm_ref" OFF;
SET IDENTITY_INSERT "cr_eval_collection" ON;
SET IDENTITY_INSERT "cr_eval_collection" OFF;
SET IDENTITY_INSERT "cr_eval_index" ON;
SET IDENTITY_INSERT "cr_eval_index" OFF;
SET IDENTITY_INSERT "cr_eval_index_ref" ON;
SET IDENTITY_INSERT "cr_eval_index_ref" OFF;
SET IDENTITY_INSERT "cr_eval_indicator" ON;
SET IDENTITY_INSERT "cr_eval_indicator" OFF;
SET IDENTITY_INSERT "cr_eval_probe_data" ON;
SET IDENTITY_INSERT "cr_eval_probe_data" OFF;
SET IDENTITY_INSERT "cr_eval_record" ON;
SET IDENTITY_INSERT "cr_eval_record" OFF;
SET IDENTITY_INSERT "cr_eval_result" ON;
SET IDENTITY_INSERT "cr_eval_result" OFF;
SET IDENTITY_INSERT "cr_eval_system" ON;
SET IDENTITY_INSERT "cr_eval_system" OFF;
SET IDENTITY_INSERT "cr_eval_task" ON;
SET IDENTITY_INSERT "cr_eval_task" OFF;
SET IDENTITY_INSERT "cr_eval_task_step" ON;
SET IDENTITY_INSERT "cr_eval_task_step" OFF;
SET IDENTITY_INSERT "cr_eval_template" ON;
SET IDENTITY_INSERT "cr_eval_template" OFF;
SET IDENTITY_INSERT "cr_eval_variable_ref" ON;
SET IDENTITY_INSERT "cr_eval_variable_ref" OFF;
SET IDENTITY_INSERT "cr_eval_variable_ref_probe_data" ON;
SET IDENTITY_INSERT "cr_eval_variable_ref_probe_data" OFF;
SET IDENTITY_INSERT "cr_mark" ON;
SET IDENTITY_INSERT "cr_mark" OFF;
SET IDENTITY_INSERT "cr_mission" ON;
SET IDENTITY_INSERT "cr_mission" OFF;
SET IDENTITY_INSERT "cr_mission_category" ON;
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(1,'05b739fc686a073756399f79c34039b5','情报侦查研判','Intelligence Investigation');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(2,'1102f907c7650ad541c381f59c700f5f','密码破解','Password Cracking');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(3,'2eec84346538e972a962b165e499f52d','终端渗透控制','Terminal Penetration Control');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(4,'2f4f15de59cd2dbadc5d8a582e9f6849','威胁发现和检测','Threat Discovery And Detection');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(5,'3b6e90254227833303d908336b105ade','内网渗透控制','Intranet Penetration Control');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(6,'446bbba739678a4e6e103e7d214d55aa','网络应用控制破解','Network Application Control Cracking');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(7,'6753aa8cad6fa9760308871e707ea033','网络侦查','Network Investigation');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(8,'8568759a4d514ff730ed6eb1721a2f22','反侦查','Anti Detection');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(9,'a2e58506b8d588d7e1cef85f7350728a','漏洞挖掘利用','Exploit');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(10,'a7914af4e20435cb7ad7b8b4ae0bac6a','应急响应','Emergency Response');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(11,'b27501c31132b288df1bef259e2625e6','网站渗透控制','Website Penetration Control');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(12,'bd43486dee157d5e5106d547801e2ea2','系统加固','System Hardening');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(13,'c4173e927825d4ff52b112e268fb01ad','数据分析','Data Analysis');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(14,'d0cf5fc50285e1b64c1dfc3adb9ba39d','技术反制','Technological Countermeasures');
INSERT INTO "cr_mission_category"("id","resource_id","name","key") VALUES(15,'dff8a9b76f5536babae3d4c22943004d','隐蔽能力','Concealment');

SET IDENTITY_INSERT "cr_mission_category" OFF;
SET IDENTITY_INSERT "cr_mission_scheme_template" ON;
SET IDENTITY_INSERT "cr_mission_scheme_template" OFF;
SET IDENTITY_INSERT "cr_mission_situation" ON;
SET IDENTITY_INSERT "cr_mission_situation" OFF;
SET IDENTITY_INSERT "cr_mission_template" ON;
SET IDENTITY_INSERT "cr_mission_template" OFF;
SET IDENTITY_INSERT "cr_notice" ON;
SET IDENTITY_INSERT "cr_notice" OFF;
SET IDENTITY_INSERT "cr_radar_label" ON;
INSERT INTO "cr_radar_label"("id","resource_id","label") VALUES(1,1,'侦测跟踪');
INSERT INTO "cr_radar_label"("id","resource_id","label") VALUES(2,2,'武器构建');
INSERT INTO "cr_radar_label"("id","resource_id","label") VALUES(3,3,'载荷投递');
INSERT INTO "cr_radar_label"("id","resource_id","label") VALUES(4,4,'漏洞利用');
INSERT INTO "cr_radar_label"("id","resource_id","label") VALUES(5,5,'安装植入');
INSERT INTO "cr_radar_label"("id","resource_id","label") VALUES(6,6,'命令与控制');
INSERT INTO "cr_radar_label"("id","resource_id","label") VALUES(7,7,'目标达成');

SET IDENTITY_INSERT "cr_radar_label" OFF;
SET IDENTITY_INSERT "cr_rb_common_device" ON;
SET IDENTITY_INSERT "cr_rb_common_device" OFF;
SET IDENTITY_INSERT "cr_rb_device" ON;
SET IDENTITY_INSERT "cr_rb_device" OFF;
SET IDENTITY_INSERT "cr_rb_room" ON;
SET IDENTITY_INSERT "cr_rb_room" OFF;
SET IDENTITY_INSERT "cr_rb_template" ON;
SET IDENTITY_INSERT "cr_rb_template" OFF;
SET IDENTITY_INSERT "cr_rb_time_management" ON;
SET IDENTITY_INSERT "cr_rb_time_management" OFF;
SET IDENTITY_INSERT "cr_role" ON;
SET IDENTITY_INSERT "cr_role" OFF;
SET IDENTITY_INSERT "cr_score" ON;
SET IDENTITY_INSERT "cr_score" OFF;
SET IDENTITY_INSERT "cr_single_room" ON;
SET IDENTITY_INSERT "cr_single_room" OFF;
SET IDENTITY_INSERT "cr_single_template" ON;
SET IDENTITY_INSERT "cr_single_template" OFF;
SET IDENTITY_INSERT "cr_task" ON;
SET IDENTITY_INSERT "cr_task" OFF;
SET IDENTITY_INSERT "cr_task_log" ON;
SET IDENTITY_INSERT "cr_task_log" OFF;
SET IDENTITY_INSERT "cr_task_submit" ON;
SET IDENTITY_INSERT "cr_task_submit" OFF;
SET IDENTITY_INSERT "cr_team_common_device" ON;
SET IDENTITY_INSERT "cr_team_common_device" OFF;
SET IDENTITY_INSERT "cr_team_device" ON;
SET IDENTITY_INSERT "cr_team_device" OFF;
SET IDENTITY_INSERT "cr_team_room" ON;
SET IDENTITY_INSERT "cr_team_room" OFF;
SET IDENTITY_INSERT "cr_team_template" ON;
SET IDENTITY_INSERT "cr_team_template" OFF;
SET IDENTITY_INSERT "cr_tool_tool" ON;
SET IDENTITY_INSERT "cr_tool_tool" OFF;
SET IDENTITY_INSERT "cr_tool_toolcategory" ON;
SET IDENTITY_INSERT "cr_tool_toolcategory" OFF;
SET IDENTITY_INSERT "cr_tool_toolfile" ON;
SET IDENTITY_INSERT "cr_tool_toolfile" OFF;
SET IDENTITY_INSERT "cr_train_record" ON;
SET IDENTITY_INSERT "cr_train_record" OFF;
SET IDENTITY_INSERT "file_chunk" ON;
SET IDENTITY_INSERT "file_chunk" OFF;

INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0001',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0300AQXY','K0001',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0401JSJZCYL','K0001',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJWL','K0001',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0001',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISB0500LAWMGT','K0002',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISB0300WLAQF','K0003',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0403GFJSYSJ','K0005',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503PWN','K0005',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503PWN','K0006',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0400AC','K0007',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0007',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100WEB','K0009',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0900YYXX','K0009',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0105DSA','K0014',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0105DSA','K0015',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0302SJFXSF','K0015',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0201MLADM','K0015',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0301NSAML','K0015',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PCXSJ','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103JS','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PHP','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103HTML','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103ASM','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103CYY','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103GOLANG','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJSP','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJAVASE','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFHTML','K0016',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0017',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT02FZ','K0018',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT03XL','K0018',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT05GY','K0018',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT04HASH','K0018',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100CRYPTO','K0018',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT09GL','K0019',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT01CRYPTO','K0019',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT06QM','K0019',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0019',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT08XY','K0019',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT09YS','K0019',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100CRYPTO','K0019',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBEXP','K0023',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0023',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0600DBSEC','K0023',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBGL','K0023',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFORACLE','K0023',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0024',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0600DBSEC','K0024',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBGL','K0024',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFORACLE','K0024',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJZW','K0033',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0400AC','K0033',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405WLFWJS','K0034',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0034',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0035',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0700SSEC','K0039',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0900YYXX','K0039',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0044',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0046',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0200IDSA','K0046',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103ASM','K0051',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISB0200INFOMATH','K0052',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0104MATH','K0052',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0301SSCI','K0052',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0200SBAQ','K0057',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD2','K0059',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0501CZXTAQ','K0060',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503LINUX','K0060',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0502WIN','K0060',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0062',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103WLBC','K0068',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0069',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFMYSQL','K0069',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0504WEBAQ','K0070',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0501WEBUG','K0070',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0503DVWA','K0070',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0505WEBSZ','K0070',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0503WEB','K0070',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0501CZXTAQ','K0077 ',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503LINUX','K0077 ',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0502WIN','K0077 ',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1100ANDROID','K0080',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0504WEBAQ','K0085',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0501WEBUG','K0085',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100WEB','K0085',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0099',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0099',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFBOOTSTRAP','K0105',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402CZXTYL','K0109',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0112',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0301SSCI','K0113',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0302TXYL','K0113',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0303JCBM','K0113',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0113',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0133',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0134',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0307DJYSJ','K0145',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0102XMAN17','K0147',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0102XMAN16','K0147',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0501CZXTAQ','K0167',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503LINUX','K0167',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0502WIN','K0167',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0600WXWL','K0174',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0300AQXY','K0174',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0401JSJZCYL','K0174',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0174',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100RE','K0175',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0300AQXY','K0179',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0179',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYS','K0179',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD1','K0179',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1201ICS','K0179',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405WLFWJS','K0179',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0183',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100RE','K0183',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0185',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0202',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0501CZXTAQ','K0205',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503LINUX','K0205',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0502WIN','K0205',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0221',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0224',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0303SJKSH','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0304SJFXCL','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0201MLADM','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0305HADOOP','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0306BIGDATA','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0301NSAML','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PCXSJ','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103JCXSJ','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJAVASE','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFMYSQL','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFSERVLET','K0236',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD2','K0264',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD3','K0264',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0101DZDL','K0265',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0300KKXJS','K0265',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0102EDA','K0265',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402CZXTYL','K0271',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT09GL','K0277',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0100AQGN','K0290',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0301',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0501XXYC','K0305',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0600YSBH','K0315',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0200IDSA','K0324',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0331',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0332',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0334',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0336',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0339',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100MISC','K0339',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0342',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0345',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0303SJKSH','K0352',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0501XXYC','K0356',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0356',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0360',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0364',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103WLBC','K0372',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0403BYXT','K0372',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0200WLSJHQ','K0388',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJZW','K0388',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0200SBAQ','K0393',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0394',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBGL','K0394',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0401JSJZCYL','K0395',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402CZXTYL','K0395',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJWL','K0395',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0395',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103CYY','K0396',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PCXSJ','K0396',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103JCXSJ','K0396',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103GOLANG','K0396',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PHP','K0396',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJSP','K0396',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJAVASE','K0396',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0397',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103HTML','K0398',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJQUERY','K0398',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFBOOTSTRAP','K0398',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFHTML','K0398',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJAVASCRIPT','K0398',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0403GFJSYSJ','K0408',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0400FH','K0408',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0420',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBGL','K0420',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0433',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0435',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0452',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0106INFT','K0454',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISB0100XXAQ','K0455',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD4','K0455',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD6','K0455',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0302TXYL','K0459',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0459',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0801TCB','K0469',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT06QM','K0472',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0200IDSA','K0472',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0473',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100PWN','K0481',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503PWN','K0481',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0487',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0487',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0488',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0400AC','K0488',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJZW','K0493',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0400AC','K0537',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0537',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0555',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0561',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0561',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0302TXYL','K0564',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0303JCBM','K0564',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0564',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0565',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0304SJFXCL','K0578',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFCSS','K0578',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0608',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405VT','K0609',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405VT','K0610',1,1);
INSERT INTO "init_rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD5','K0612',1,1);

SET IDENTITY_INSERT "init_rtks_knowledge" ON;
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(86,'K0001','13d63fdbe3da0ff264b9647565cfaed5','计算机网络概念和协议，以及网络安全方法论知识','[{"relatedId":"K0001","resourceId":"NS0100SEC","type":1,"weight":1},{"relatedId":"K0001","resourceId":"NS0300AQXY","type":1,"weight":1},{"relatedId":"K0001","resourceId":"ISTB0401JSJZCYL","type":1,"weight":1},{"relatedId":"K0001","resourceId":"ISTB0405JSJWL","type":1,"weight":1},{"relatedId":"K0001","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 15:55:30.000000000','2022-01-07 14:50:43.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(87,'K0002','13d63fdbe3da0ff264b9647565cfaed5','风险管理过程知识(比如评估和风险环境的方法)','[{"relatedId":"K0002","resourceId":"ISB0500LAWMGT","type":1,"weight":1}]','2021-10-14 16:09:45.000000000','2022-01-07 14:51:07.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(90,'K0003','90207603744123196c360addca94cc41','与网络空间安全相关的国家和国际法律、规定、政策和道德要求知识。','[{"relatedId":"K0003","resourceId":"ISB0300WLAQF","type":1,"weight":1}]','2021-10-14 16:25:08.000000000','2022-01-07 14:51:33.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(91,'K0202','0bf2eb74863de071afa24856a266e4c7','应用防火墙概念和功能知识。(例如单点认证/审计/策略执行，恶意内容扫描， PCI 和 PII 中的数据匿名，数据丢失保护扫描，加速密码操作，SSL、REST/JSON 处理等)','[{"relatedId":"K0202","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 16:28:32.000000000','2022-01-10 09:57:05.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(92,'K0004','0bf2eb74863de071afa24856a266e4c7','网络空间安全原则知识','','2021-10-14 16:28:37.000000000','2021-10-14 16:28:37.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(93,'K0005','23f60bb0501dc27b096dd3205683cfa9','网络空间威胁和漏洞知识','[{"relatedId":"K0005","resourceId":"NS0403GFJSYSJ","type":1,"weight":1},{"relatedId":"K0005","resourceId":"SS0503PWN","type":1,"weight":1}]','2021-10-14 16:29:05.000000000','2022-01-07 15:01:32.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(94,'K0205','521e0b5143744d0fff1e528e08c486b5','基本的系统、网络和操作系统加固技术知识','[{"relatedId":"K0205","resourceId":"SS0501CZXTAQ","type":1,"weight":1},{"relatedId":"K0205","resourceId":"SS0503LINUX","type":1,"weight":1},{"relatedId":"K0205","resourceId":"SS0502WIN","type":1,"weight":1}]','2021-10-14 16:29:16.000000000','2022-01-10 09:57:48.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(95,'K0006','0bf2eb74863de071afa24856a266e4c7','网络空间安全漏洞对具体操作影响的知识','[{"relatedId":"K0006","resourceId":"SS0503PWN","type":1,"weight":1}]','2021-10-14 16:29:37.000000000','2022-01-07 15:02:10.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(96,'K0206','521e0b5143744d0fff1e528e08c486b5','道德黑客(白帽)的原则和技术知识','','2021-10-14 16:29:51.000000000','2021-10-14 16:29:51.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(97,'K0007','521e0b5143744d0fff1e528e08c486b5','认证、授权和访问控制的知识','[{"relatedId":"K0007","resourceId":"SS0400AC","type":1,"weight":1},{"relatedId":"K0007","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 16:30:03.000000000','2022-01-07 15:02:48.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(98,'K0214','d78b0449533e94ee751bad6b568ecfb9','风险管理框架评估方法论知识','','2021-10-14 16:30:18.000000000','2021-10-14 16:30:18.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(99,'K0009','0bf2eb74863de071afa24856a266e4c7','应用漏洞的知识','[{"relatedId":"K0009","resourceId":"ISC0100WEB","type":1,"weight":1},{"relatedId":"K0009","resourceId":"SS0900YYXX","type":1,"weight":1}]','2021-10-14 16:30:26.000000000','2022-01-07 15:03:23.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(100,'K0219','521e0b5143744d0fff1e528e08c486b5','局域网和广域网原理知识','','2021-10-14 16:30:48.000000000','2021-10-14 16:30:48.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(101,'K0013','d78b0449533e94ee751bad6b568ecfb9','网络空间防御和漏洞评估工具的知识，包括开源工具和他们的能力','','2021-10-14 16:30:51.000000000','2021-10-14 16:30:51.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(102,'K0221','0bf2eb74863de071afa24856a266e4c7','OSI 模型和底层网络协议知识(比如 TCP/IP)','[{"relatedId":"K0221","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:31:21.000000000','2022-01-10 09:58:41.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(103,'K0024','d78b0449533e94ee751bad6b568ecfb9','数据库系统的知识','[{"relatedId":"K0024","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0024","resourceId":"SS0600DBSEC","type":1,"weight":1},{"relatedId":"K0024","resourceId":"ISTB0404DBGL","type":1,"weight":1},{"relatedId":"K0024","resourceId":"ISTB0103VFORACLE","type":1,"weight":1}]','2021-10-14 16:31:24.000000000','2022-01-10 09:04:49.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(104,'K0222','90207603744123196c360addca94cc41','网络空间防御活动相关的法律、法定权利、限制和规定知识。','','2021-10-14 16:31:51.000000000','2021-10-14 16:31:51.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(105,'K0033','521e0b5143744d0fff1e528e08c486b5','主机/网络访问控制机制的知识(比如访问控制列表)','[{"relatedId":"K0033","resourceId":"ISTB0405JSJZW","type":1,"weight":1},{"relatedId":"K0033","resourceId":"SS0400AC","type":1,"weight":1}]','2021-10-14 16:31:51.000000000','2022-01-10 09:05:29.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(106,'K0037','8d5fe8f769c936b86c3092aab0a80e7c','安全评估和授权流程知识','','2021-10-14 16:32:26.000000000','2021-10-14 16:32:26.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(107,'K0224','90207603744123196c360addca94cc41','Unix/Linux 和/或 Windows 操作系统的系统管理概念知识','[{"relatedId":"K0224","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 16:32:28.000000000','2022-01-10 09:59:08.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(108,'K0040','0bf2eb74863de071afa24856a266e4c7','从警报、建议、勘误表和公告中了解已知漏洞','','2021-10-14 16:32:48.000000000','2021-10-14 16:32:48.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(109,'K0225','13d63fdbe3da0ff264b9647565cfaed5','部署在 CC/S/A 上的常用网络协议和服务知识','','2021-10-14 16:32:52.000000000','2021-10-14 16:32:52.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(110,'K0041','0bf2eb74863de071afa24856a266e4c7','事件分类、事件响应和响应时间轴的知识','','2021-10-14 16:33:14.000000000','2021-10-14 16:33:14.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(111,'K0230','521e0b5143744d0fff1e528e08c486b5','服务模型和事件响应的可能限制的知识','','2021-10-14 16:33:15.000000000','2021-10-14 16:33:15.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(112,'K0236','d78b0449533e94ee751bad6b568ecfb9','了解如何利用 Hadoop，java、python，SQL，Hive，PIG 来探索数据。','[{"relatedId":"K0236","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0303SJKSH","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0304SJFXCL","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0201MLADM","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0305HADOOP","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0306BIGDATA","type":1,"weight":1},{"relatedId":"K0236","resourceId":"NS0301NSAML","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103PCXSJ","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103JCXSJ","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103VFJAVASE","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103VFMYSQL","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103VFSERVLET","type":1,"weight":1}]','2021-10-14 16:33:40.000000000','2022-01-10 10:01:22.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(113,'K0042','d78b0449533e94ee751bad6b568ecfb9','事件响应和处理方法的知识','','2021-10-14 16:33:41.000000000','2021-10-14 16:33:41.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(114,'K0271','8d5fe8f769c936b86c3092aab0a80e7c','操作系统结构和内部知识(比如进程管理、目录结构、已安装应用)','[{"relatedId":"K0271","resourceId":"ISTB0402CZXTYL","type":1,"weight":1}]','2021-10-14 16:34:07.000000000','2022-01-10 10:03:58.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(115,'K0044','0bf2eb74863de071afa24856a266e4c7','网络空间安全原则和组织机构级需求知识(机密性、完整性、可用性、认证授 权和不可抵赖性相关知识)','[{"relatedId":"K0044","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 16:34:08.000000000','2022-01-10 09:25:12.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(116,'K0046','521e0b5143744d0fff1e528e08c486b5','通过入侵检测技术来检测主机和网络入侵的相关方法和技术知识。','[{"relatedId":"K0046","resourceId":"CS0400NRGK","type":1,"weight":1},{"relatedId":"K0046","resourceId":"NS0200IDSA","type":1,"weight":1}]','2021-10-14 16:34:36.000000000','2022-01-10 09:25:54.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(117,'K0273','23f60bb0501dc27b096dd3205683cfa9','入侵链(Kill Chain)的一般知识(比如踩点扫描、枚举猜测、获取访问权限、 特权提升、维持访问、网络渗透、轨迹隐藏)','','2021-10-14 16:34:36.000000000','2021-10-14 16:34:36.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(118,'K0277','13d63fdbe3da0ff264b9647565cfaed5','当前和新兴的数据加密(比如列和表空间加密、文件和磁盘加密)、数据内置 安全特性(包括内置密钥管理特性)的知识','[{"relatedId":"K0277","resourceId":"CRYPT09GL","type":1,"weight":1}]','2021-10-14 16:35:05.000000000','2022-01-10 10:04:18.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(119,'K0049','8d5fe8f769c936b86c3092aab0a80e7c','IT 安全原理和方法知识(比如防火墙、DMZ 区和加密)','','2021-10-14 16:35:07.000000000','2021-10-14 16:35:07.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(120,'K0290','90207603744123196c360addca94cc41','系统安全测试和评估方法的知识','[{"relatedId":"K0290","resourceId":"SS0100AQGN","type":1,"weight":1}]','2021-10-14 16:35:30.000000000','2022-01-10 10:04:40.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(121,'K0058','521e0b5143744d0fff1e528e08c486b5','网络流量分析方法的知识','','2021-10-14 16:35:46.000000000','2021-10-14 16:35:46.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(122,'K0292','d78b0449533e94ee751bad6b568ecfb9','针对突发事件、问题和大事件管理的操作和处理流程知识','','2021-10-14 16:35:56.000000000','2021-10-14 16:35:56.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(123,'K0059','521e0b5143744d0fff1e528e08c486b5','新兴的 IT 和网络空间安全技术的知识','[{"relatedId":"K0059","resourceId":"NS0101AQYSVD2","type":1,"weight":1}]','2021-10-14 16:36:15.000000000','2022-01-10 09:31:20.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(124,'K0297','23f60bb0501dc27b096dd3205683cfa9','对已确认安全风险的应对措施设计知识','','2021-10-14 16:36:19.000000000','2021-10-14 16:36:19.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(125,'K0060','13d63fdbe3da0ff264b9647565cfaed5','操作系统知识','[{"relatedId":"K0060","resourceId":"SS0501CZXTAQ","type":1,"weight":1},{"relatedId":"K0060","resourceId":"SS0503LINUX","type":1,"weight":1},{"relatedId":"K0060","resourceId":"SS0502WIN","type":1,"weight":1}]','2021-10-14 16:36:39.000000000','2022-01-10 09:32:10.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(126,'K0298','521e0b5143744d0fff1e528e08c486b5','对已确认安全风险的应对措施知识','','2021-10-14 16:36:41.000000000','2021-10-14 16:36:41.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(127,'K0061','13d63fdbe3da0ff264b9647565cfaed5','网络中流量如何流动的知识(比如 TCP、IP、OSI 模型、ITIL)','','2021-10-14 16:37:04.000000000','2021-10-14 16:37:04.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(128,'K0301','0bf2eb74863de071afa24856a266e4c7','使用合适工具(比如 Wireshark, tcpdump)进行数据包层次分析的知识','[{"relatedId":"K0301","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 16:37:10.000000000','2022-01-10 10:05:04.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(129,'K0302','d78b0449533e94ee751bad6b568ecfb9','计算机基本操作的知识','','2021-10-14 16:37:32.000000000','2021-10-14 16:37:32.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(130,'K0062','d78b0449533e94ee751bad6b568ecfb9','流量包分析的知识','[{"relatedId":"K0062","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 16:37:36.000000000','2022-01-10 09:32:56.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(131,'K0069','0bf2eb74863de071afa24856a266e4c7','比如 SQL 语言类似的查询语言知识','[{"relatedId":"K0069","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0069","resourceId":"ISTB0103VFMYSQL","type":1,"weight":1}]','2021-10-14 16:37:57.000000000','2022-01-10 09:34:04.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(132,'K0303','521e0b5143744d0fff1e528e08c486b5','使用子网工具的知识','','2021-10-14 16:37:58.000000000','2021-10-14 16:37:58.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(133,'K0307','23f60bb0501dc27b096dd3205683cfa9','常用网络工具的知识(比如 Ping\traceroute\nslookup)','','2021-10-14 16:38:23.000000000','2021-10-14 16:38:23.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(134,'K0070','13d63fdbe3da0ff264b9647565cfaed5','系统和应用安全威胁和漏洞的知识(比如缓冲区溢出、移动代码、跨站脚本、 SQL 注入、竞争条件攻击、隐蔽信道、重放攻击、面向返回攻击、恶意代码)','[{"relatedId":"K0070","resourceId":"NS0504WEBAQ","type":1,"weight":1},{"relatedId":"K0070","resourceId":"NS0501WEBUG","type":1,"weight":1},{"relatedId":"K0070","resourceId":"NS0503DVWA","type":1,"weight":1},{"relatedId":"K0070","resourceId":"NS0505WEBSZ","type":1,"weight":1},{"relatedId":"K0070","resourceId":"NS0503WEB","type":1,"weight":1}]','2021-10-14 16:38:29.000000000','2022-01-10 09:35:06.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(135,'K0309','8d5fe8f769c936b86c3092aab0a80e7c','那些可能被对手利用的新兴技术的知识','','2021-10-14 16:38:45.000000000','2021-10-14 16:38:45.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(136,'K0071','13d63fdbe3da0ff264b9647565cfaed5','远程访问技术概念知识','','2021-10-14 16:39:00.000000000','2021-10-14 16:39:00.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(137,'K0310','90207603744123196c360addca94cc41','黑客方法论的知识','','2021-10-14 16:39:17.000000000','2021-10-14 16:39:17.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(138,'K0085','d78b0449533e94ee751bad6b568ecfb9','系统和应用安全威胁和漏洞知识','[{"relatedId":"K0085","resourceId":"NS0504WEBAQ","type":1,"weight":1},{"relatedId":"K0085","resourceId":"NS0501WEBUG","type":1,"weight":1},{"relatedId":"K0085","resourceId":"ISC0100WEB","type":1,"weight":1}]','2021-10-14 16:39:26.000000000','2022-01-10 09:37:35.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(139,'K0314','d78b0449533e94ee751bad6b568ecfb9','行业技术知识以及他们影响渗透/漏洞的区别。','','2021-10-14 16:39:40.000000000','2021-10-14 16:39:40.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(140,'K0098','d78b0449533e94ee751bad6b568ecfb9','了解网络防御服务提供商报告结构和其组织内部的处理流程','','2021-10-14 16:39:52.000000000','2021-10-14 16:39:52.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(141,'K0315','521e0b5143744d0fff1e528e08c486b5','信息收集和生产、汇报和信息共享方面的原则方法、过程和技术知识','[{"relatedId":"K0315","resourceId":"CS0600YSBH","type":1,"weight":1}]','2021-10-14 16:40:04.000000000','2022-01-10 10:05:50.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(142,'K0318','d78b0449533e94ee751bad6b568ecfb9','操作系统命令行/提示符的知识','','2021-10-14 16:40:26.000000000','2021-10-14 16:40:26.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(143,'K0099','521e0b5143744d0fff1e528e08c486b5','常用网络协议(比如 TCP/IP)、服务(比如 Web、Mail、DNS)以及他们如 何交互，来实现网络通信的知识','[{"relatedId":"K0099","resourceId":"ISTB0304WXTX","type":1,"weight":1},{"relatedId":"K0099","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:40:30.000000000','2022-01-10 09:39:13.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(144,'K0324','23f60bb0501dc27b096dd3205683cfa9','入侵检测系统 IDS/入侵防御系统 IPS 的工具和应用知识','[{"relatedId":"K0324","resourceId":"NS0200IDSA","type":1,"weight":1}]','2021-10-14 16:41:01.000000000','2022-01-10 10:06:10.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(145,'K0104','d78b0449533e94ee751bad6b568ecfb9','VPN 安全的知识','','2021-10-14 16:41:03.000000000','2021-10-14 16:41:03.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(146,'K0326','90207603744123196c360addca94cc41','网络空间方法的知识，比如防火墙、DMZ 区和加密','','2021-10-14 16:41:26.000000000','2021-10-14 16:41:26.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(147,'K0105','8d5fe8f769c936b86c3092aab0a80e7c','Web 服务知识，包括 SOA、SOAP 和 WSDL','[{"relatedId":"K0105","resourceId":"ISTB0103VFBOOTSTRAP","type":1,"weight":1}]','2021-10-14 16:41:32.000000000','2022-01-10 09:39:36.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(148,'K0331','d78b0449533e94ee751bad6b568ecfb9','网络协议知识(比如 TCP、IP、DHCP、目录服务(比如 DNS))','[{"relatedId":"K0331","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:41:51.000000000','2022-01-10 10:06:39.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(149,'K0106','8d5fe8f769c936b86c3092aab0a80e7c','了解网络攻击构成要素的知识以及网络威胁和漏洞关系的知识','','2021-10-14 16:42:07.000000000','2021-10-14 16:42:07.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(150,'K0332','0bf2eb74863de071afa24856a266e4c7','网络协议知识，比如 TCP/IP、动态主机配置(DHCP?)、DNS 和目录服务','[{"relatedId":"K0332","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:42:13.000000000','2022-01-10 10:08:14.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(151,'K0107','8d5fe8f769c936b86c3092aab0a80e7c','内部威胁调查、报告、调查工具和法律/规定的经验和知识','','2021-10-14 16:42:30.000000000','2021-10-14 16:42:30.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(152,'K0334','521e0b5143744d0fff1e528e08c486b5','网络流量分析知识(工具、方法、流程)','[{"relatedId":"K0334","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 16:42:42.000000000','2022-01-10 10:08:39.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(153,'K0111','13d63fdbe3da0ff264b9647565cfaed5','了解常见的网络工具(比如 ping\tracert\nslookup)和相关提示信息的解释','','2021-10-14 16:42:53.000000000','2021-10-14 16:42:53.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(154,'K0335','13d63fdbe3da0ff264b9647565cfaed5','当前和新兴的网络空间技术知识','','2021-10-14 16:43:12.000000000','2021-10-14 16:43:12.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(155,'K0112','0bf2eb74863de071afa24856a266e4c7','纵深防御的原则和网络安全的结构知识','[{"relatedId":"K0112","resourceId":"NS0100SEC","type":1,"weight":1}]','2021-10-14 16:43:19.000000000','2022-01-10 09:40:27.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(156,'K0336','d78b0449533e94ee751bad6b568ecfb9','访问身份认证方法的知识','[{"relatedId":"K0336","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 16:43:42.000000000','2022-01-10 10:08:58.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(157,'K0113','13d63fdbe3da0ff264b9647565cfaed5','不同类型的网络通信知识(比如 LAN、WAN、MAN、WLAN、WWAN)','[{"relatedId":"K0113","resourceId":"ISTB0301SSCI","type":1,"weight":1},{"relatedId":"K0113","resourceId":"ISTB0302TXYL","type":1,"weight":1},{"relatedId":"K0113","resourceId":"ISTB0303JCBM","type":1,"weight":1},{"relatedId":"K0113","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 16:43:47.000000000','2022-01-10 09:41:16.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(158,'K0339','13d63fdbe3da0ff264b9647565cfaed5','如何使用网络分析工具确认漏洞的知识','[{"relatedId":"K0339","resourceId":"ISC0101TOOLS","type":1,"weight":1},{"relatedId":"K0339","resourceId":"ISC0100MISC","type":1,"weight":1}]','2021-10-14 16:44:09.000000000','2022-01-10 10:09:27.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(159,'K0115','521e0b5143744d0fff1e528e08c486b5','那些可能被对手利用的计算机新兴技术知识','','2021-10-14 16:44:28.000000000','2021-10-14 16:44:28.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(160,'K0340','0bf2eb74863de071afa24856a266e4c7','网络中流量如何流动的知识(比如 TCP、IP、OSI 模型)','','2021-10-14 16:44:37.000000000','2021-10-14 16:44:37.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(161,'K0119','0bf2eb74863de071afa24856a266e4c7','Windows 或 Linux/Unix 环境下的黑客攻击方法知识','','2021-10-14 16:44:55.000000000','2021-10-14 16:44:55.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(162,'K0342','90207603744123196c360addca94cc41','渗透测试原理、工具和技术知识','[{"relatedId":"K0342","resourceId":"ISC0101TOOLS","type":1,"weight":1}]','2021-10-14 16:45:05.000000000','2022-01-10 10:09:49.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(163,'K0129','521e0b5143744d0fff1e528e08c486b5','Unix 命令行的知识(比如 mkdir\mv\ls\passwd\grep)','','2021-10-14 16:45:23.000000000','2021-10-14 16:45:23.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(164,'K0344','13d63fdbe3da0ff264b9647565cfaed5','威胁环境知识','','2021-10-14 16:45:41.000000000','2021-10-14 16:45:41.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(165,'K0345','90207603744123196c360addca94cc41','网络空间攻击者知识(比如脚本小子、内部威胁、非国家支持黑客、国家支持 黑客)','[{"relatedId":"K0345","resourceId":"ISC0101TOOLS","type":1,"weight":1}]','2021-10-14 16:46:03.000000000','2022-01-10 10:10:07.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(166,'K0352','13d63fdbe3da0ff264b9647565cfaed5','各种形式的情报支持需求、话题和关注领域的知识','[{"relatedId":"K0352","resourceId":"CS0303SJKSH","type":1,"weight":1}]','2021-10-14 16:46:23.000000000','2022-01-10 10:10:24.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(167,'K0131','521e0b5143744d0fff1e528e08c486b5','网络邮件收集、搜索/分析的技术、工具和 cooki 知识','','2021-10-14 16:46:39.000000000','2021-10-14 16:46:39.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(168,'K0355','d78b0449533e94ee751bad6b568ecfb9','所有来源报告和传播过程的知识','','2021-10-14 16:46:49.000000000','2021-10-14 16:46:49.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(169,'K0132','0bf2eb74863de071afa24856a266e4c7','哪些系统文件(比如日志文件、注册表文件、配置文件)包含相关信息，以及 如何找到这些系统文件的知识','','2021-10-14 16:47:05.000000000','2021-10-14 16:47:05.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(170,'K0356','23f60bb0501dc27b096dd3205683cfa9','分析工具和技术的知识','[{"relatedId":"K0356","resourceId":"CS0501XXYC","type":1,"weight":1},{"relatedId":"K0356","resourceId":"ISC0101TOOLS","type":1,"weight":1}]','2021-10-14 16:47:13.000000000','2022-01-10 10:10:56.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(171,'K0135','521e0b5143744d0fff1e528e08c486b5','Web 过滤器技术知识','','2021-10-14 16:47:25.000000000','2021-10-14 16:47:25.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(172,'K0362','d78b0449533e94ee751bad6b568ecfb9','攻击方法和技术的知识(DDoS、暴力、欺骗等等)','','2021-10-14 16:47:42.000000000','2021-10-14 16:47:42.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(173,'K0142','d78b0449533e94ee751bad6b568ecfb9','收集管理过程、能力和限制的知识','','2021-10-14 16:47:52.000000000','2021-10-14 16:47:52.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(174,'K0363','8d5fe8f769c936b86c3092aab0a80e7c','审计和日志处理的知识(比如基于服务器的日志)','','2021-10-14 16:48:08.000000000','2021-10-14 16:48:08.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(175,'K0143','d78b0449533e94ee751bad6b568ecfb9','前端收集系统的知识，包括网络流量收集、过滤器和选择器','','2021-10-14 16:48:14.000000000','2021-10-14 16:48:14.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(176,'K0364','0bf2eb74863de071afa24856a266e4c7','评估(信息)采集任务所需的可用数据库和工具知识','[{"relatedId":"K0364","resourceId":"ISTB0103VFREDIS","type":1,"weight":1}]','2021-10-14 16:48:34.000000000','2022-01-10 10:11:36.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(177,'K0144','13d63fdbe3da0ff264b9647565cfaed5','全球化背景下，社会化动态计算机攻击的知识','','2021-10-14 16:48:42.000000000','2021-10-14 16:48:42.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(178,'K0367','13d63fdbe3da0ff264b9647565cfaed5','基本的网络空间(攻击)操作活动的概念(比如踩点、扫描和枚举，渗透测试， 黑/白名单)','','2021-10-14 16:49:12.000000000','2021-10-14 16:49:12.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(179,'K0371','0bf2eb74863de071afa24856a266e4c7','(信息)收集过程中的基本原理知识。(比如被叫号码识别，社交网络分析)','','2021-10-14 16:49:37.000000000','2021-10-14 16:49:37.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(180,'K0145','0bf2eb74863de071afa24856a266e4c7','安全事件关联工具的知识','[{"relatedId":"K0145","resourceId":"CS0307DJYSJ","type":1,"weight":1}]','2021-10-14 16:49:50.000000000','2022-01-10 09:42:28.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(181,'K0386','521e0b5143744d0fff1e528e08c486b5','(信息)收集管理工具的知识','','2021-10-14 16:50:02.000000000','2021-10-14 16:50:02.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(182,'K0387','23f60bb0501dc27b096dd3205683cfa9','(信息)收集计划流程和收集计划的知识','','2021-10-14 16:50:28.000000000','2021-10-14 16:50:28.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(183,'K0388','13d63fdbe3da0ff264b9647565cfaed5','搜索/分析技术和工具(信息)收集的知识，包括聊天/好友列表,新兴技术、VIOP、 基于 IP 的媒体(Media Over IP)、VPN、卫星小数据站 VSAT/无线、网络邮 件和 cookie 等','[{"relatedId":"K0388","resourceId":"CS0200WLSJHQ","type":1,"weight":1},{"relatedId":"K0388","resourceId":"ISTB0405JSJZW","type":1,"weight":1}]','2021-10-14 16:50:54.000000000','2022-01-10 10:12:43.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(184,'K0389','90207603744123196c360addca94cc41','传统的或非传统的(信息)收集来源的知识。','','2021-10-14 16:51:22.000000000','2021-10-14 16:51:22.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(185,'K0390','0bf2eb74863de071afa24856a266e4c7','(信息)收集策略的知识','','2021-10-14 16:51:47.000000000','2021-10-14 16:51:47.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(186,'K0391','d78b0449533e94ee751bad6b568ecfb9','(信息)收集系统、能力和流程的知识','','2021-10-14 16:52:14.000000000','2021-10-14 16:52:14.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(187,'K0392','521e0b5143744d0fff1e528e08c486b5','常见的计算机/网络感染(病毒、木马等)和感染方法(端口、附件等)的知识','','2021-10-14 16:52:41.000000000','2021-10-14 16:52:41.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(188,'K0147','0bf2eb74863de071afa24856a266e4c7','新兴的安全话题、风险和漏洞知识','[{"relatedId":"K0147","resourceId":"ISC0102XMAN17","type":1,"weight":1},{"relatedId":"K0147","resourceId":"ISC0102XMAN16","type":1,"weight":1}]','2021-10-14 16:53:06.000000000','2022-01-10 09:43:27.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(189,'K0393','d78b0449533e94ee751bad6b568ecfb9',' 常见的网络设备及其配置的知识','[{"relatedId":"K0393","resourceId":"SS0200SBAQ","type":1,"weight":1}]','2021-10-14 16:53:18.000000000','2022-01-10 10:13:06.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(190,'K0150','90207603744123196c360addca94cc41','企业事件响应程序、规则和职责知识','','2021-10-14 16:53:30.000000000','2021-10-14 16:53:30.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(191,'K0394','13d63fdbe3da0ff264b9647565cfaed5','常见的数据库和数据库工具报告的知识','[{"relatedId":"K0394","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0394","resourceId":"ISTB0404DBGL","type":1,"weight":1}]','2021-10-14 16:53:38.000000000','2022-01-10 10:13:37.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(192,'K0395','8d5fe8f769c936b86c3092aab0a80e7c','计算机网络基础知识(比如网络的基本计算机组件，网络类型等)','[{"relatedId":"K0395","resourceId":"ISTB0401JSJZCYL","type":1,"weight":1},{"relatedId":"K0395","resourceId":"ISTB0402CZXTYL","type":1,"weight":1},{"relatedId":"K0395","resourceId":"ISTB0405JSJWL","type":1,"weight":1},{"relatedId":"K0395","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:54:00.000000000','2022-01-10 10:15:22.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(193,'K0157','90207603744123196c360addca94cc41','网络防御政策、过程和法规的知识','','2021-10-14 16:54:04.000000000','2021-10-14 16:54:04.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(194,'K0161','521e0b5143744d0fff1e528e08c486b5','不同攻击类别的知识(比如被动攻击、主动攻击、内部攻击、临近攻击、分布 攻击)','','2021-10-14 16:54:22.000000000','2021-10-14 16:54:22.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(195,'K0397','d78b0449533e94ee751bad6b568ecfb9','操作系统的概念知识(比如 Linux、Unix)','[{"relatedId":"K0397","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 16:54:30.000000000','2022-01-10 10:17:18.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(196,'K0162','90207603744123196c360addca94cc41','不同操作威胁环境的知识(比如第一代的脚本小子，第二代的非国家支持黑客， 第三代的国家支持的黑客)','','2021-10-14 16:54:48.000000000','2021-10-14 16:54:48.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(197,'K0398','23f60bb0501dc27b096dd3205683cfa9','网站相关的概念知识(比如 Web 服务/网页、主机、DNS、注册、类似 HTML 的 Web 语言)','[{"relatedId":"K0398","resourceId":"ISTB0103HTML","type":1,"weight":1},{"relatedId":"K0398","resourceId":"ISTB0103VFJQUERY","type":1,"weight":1},{"relatedId":"K0398","resourceId":"ISTB0103VFBOOTSTRAP","type":1,"weight":1},{"relatedId":"K0398","resourceId":"ISTB0103VFHTML","type":1,"weight":1},{"relatedId":"K0398","resourceId":"ISTB0103VFJAVASCRIPT","type":1,"weight":1}]','2021-10-14 16:54:52.000000000','2022-01-10 10:18:28.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(198,'K0165','0bf2eb74863de071afa24856a266e4c7','风险威胁评估知识','','2021-10-14 16:55:07.000000000','2021-10-14 16:55:07.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(199,'K0401','90207603744123196c360addca94cc41','(信息)收集产品评估标准的知识','','2021-10-14 16:55:20.000000000','2021-10-14 16:55:20.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(200,'K0167','23f60bb0501dc27b096dd3205683cfa9','了解基本的系统管理，网络和操作系统加固技术','[{"relatedId":"K0167","resourceId":"SS0501CZXTAQ","type":1,"weight":1},{"relatedId":"K0167","resourceId":"SS0503LINUX","type":1,"weight":1},{"relatedId":"K0167","resourceId":"SS0502WIN","type":1,"weight":1}]','2021-10-14 16:55:27.000000000','2022-01-10 09:44:50.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(201,'K0402','d78b0449533e94ee751bad6b568ecfb9','网络空间领域的目标选择和适用性的关键和漏洞条件(比如价值、恢复、隔离、 对策)','','2021-10-14 16:55:47.000000000','2021-10-14 16:55:47.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(202,'K0172','13d63fdbe3da0ff264b9647565cfaed5','中间件知识(比如企业服务总线和消息队列)','','2021-10-14 16:55:48.000000000','2021-10-14 16:55:48.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(203,'K0404','13d63fdbe3da0ff264b9647565cfaed5','当前(信息)搜集需求的知识','','2021-10-14 16:56:09.000000000','2021-10-14 16:56:09.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(204,'K0174','13d63fdbe3da0ff264b9647565cfaed5','网络协议知识','[{"relatedId":"K0174","resourceId":"NS0600WXWL","type":1,"weight":1},{"relatedId":"K0174","resourceId":"NS0300AQXY","type":1,"weight":1},{"relatedId":"K0174","resourceId":"ISTB0401JSJZCYL","type":1,"weight":1},{"relatedId":"K0174","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:56:10.000000000','2022-01-10 09:47:41.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(205,'K0405','521e0b5143744d0fff1e528e08c486b5','当前计算机入侵汇总的知识','','2021-10-14 16:56:32.000000000','2021-10-14 16:56:32.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(206,'K0177','0bf2eb74863de071afa24856a266e4c7','一般攻击阶段的知识(比如踩点扫描、枚举猜测、获取访问权限、特权提升、 维持访问、网络渗透、轨迹隐藏)','','2021-10-14 16:56:34.000000000','2021-10-14 16:56:34.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(207,'K0406','0bf2eb74863de071afa24856a266e4c7','主动防御和系统加固方面，主流的软件和方法知识','','2021-10-14 16:56:53.000000000','2021-10-14 16:56:53.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(208,'K0179','23f60bb0501dc27b096dd3205683cfa9','网络安全架构概念知识，包括拓扑、协议、组件和原理(比如应用纵深防御)','[{"relatedId":"K0179","resourceId":"NS0300AQXY","type":1,"weight":1},{"relatedId":"K0179","resourceId":"NS0100SEC","type":1,"weight":1},{"relatedId":"K0179","resourceId":"NS0101AQYS","type":1,"weight":1},{"relatedId":"K0179","resourceId":"NS0101AQYSVD1","type":1,"weight":1},{"relatedId":"K0179","resourceId":"SS1201ICS","type":1,"weight":1},{"relatedId":"K0179","resourceId":"ISTB0405WLFWJS","type":1,"weight":1}]','2021-10-14 16:56:56.000000000','2022-01-10 09:55:28.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(209,'K0185','0bf2eb74863de071afa24856a266e4c7','普通取证工具配置和支撑应用知识(比如 VMWare、Wireshark)','[{"relatedId":"K0185","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 16:57:17.000000000','2022-01-10 09:56:40.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(210,'K0407','0bf2eb74863de071afa24856a266e4c7','客户信息需求的知识','','2021-10-14 16:57:27.000000000','2021-10-14 16:57:27.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(211,'K0189','0bf2eb74863de071afa24856a266e4c7','虚拟机觉察到的恶意软件，调试器觉察到的恶意软件以及打包 packing 的知识','','2021-10-14 16:57:42.000000000','2021-10-14 16:57:42.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(212,'K0408','13d63fdbe3da0ff264b9647565cfaed5','网络空间行动(比如网络防御、信息收集、环境准备、网络攻击)、原理、能 力、限制和效果的知识','[{"relatedId":"K0408","resourceId":"NS0403GFJSYSJ","type":1,"weight":1},{"relatedId":"K0408","resourceId":"NS0400FH","type":1,"weight":1}]','2021-10-14 16:57:52.000000000','2022-01-10 10:32:02.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(213,'K0192','0bf2eb74863de071afa24856a266e4c7','Windows/Unix 端口和服务的知识','','2021-10-14 16:58:09.000000000','2021-10-14 16:58:09.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(214,'K0409','8d5fe8f769c936b86c3092aab0a80e7c','网络空间情报/信息收集能力和知识库的知识','','2021-10-14 16:58:14.000000000','2021-10-14 16:58:14.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(215,'K0410','90207603744123196c360addca94cc41','网络空间法律和他们对网络空间规划影响的知识','','2021-10-14 16:58:36.000000000','2021-10-14 16:58:36.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(216,'K0411','23f60bb0501dc27b096dd3205683cfa9','网络空间法律、法规和他们对网络空间规划影响的知识','','2021-10-14 16:58:58.000000000','2021-10-14 16:58:58.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(220,'K0014','13d63fdbe3da0ff264b9647565cfaed5','复杂数据结构的知识','[{"relatedId":"K0014","resourceId":"ISTB0105DSA","type":1,"weight":1}]','2021-10-14 16:59:28.000000000','2022-01-07 15:21:33.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(221,'K0430','23f60bb0501dc27b096dd3205683cfa9','规避策略和技术的知识','','2021-10-14 16:59:58.000000000','2021-10-14 16:59:58.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(222,'K0432','521e0b5143744d0fff1e528e08c486b5','了解网络空间运营策略、政策和机构相关的已有的、新兴的和长期的话题','','2021-10-14 17:00:22.000000000','2021-10-14 17:00:22.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(223,'K0433','0bf2eb74863de071afa24856a266e4c7','了解对操作系统结构和操作进行取证的含义','[{"relatedId":"K0433","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 17:00:46.000000000','2022-01-10 10:32:52.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(224,'K0434','90207603744123196c360addca94cc41','包括流量收集、过滤和选择在内的前端收集系统知识','','2021-10-14 17:01:09.000000000','2021-10-14 17:01:09.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(225,'K0435','d78b0449533e94ee751bad6b568ecfb9','基础的网络空间概念、原则、限制和效果知识','[{"relatedId":"K0435","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 17:01:35.000000000','2022-01-10 10:33:12.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(226,'K0436','90207603744123196c360addca94cc41','基本的网络空间操作概念、术语/词汇(比如环境准备、网络攻击、网络防御)、 原则、能力、限制和效果的知识','','2021-10-14 17:02:10.000000000','2021-10-14 17:02:10.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(227,'K0015','0bf2eb74863de071afa24856a266e4c7','计算机算法的知识','[{"relatedId":"K0015","resourceId":"ISTB0105DSA","type":1,"weight":1},{"relatedId":"K0015","resourceId":"CS0302SJFXSF","type":1,"weight":1},{"relatedId":"K0015","resourceId":"CS0201MLADM","type":1,"weight":1},{"relatedId":"K0015","resourceId":"NS0301NSAML","type":1,"weight":1}]','2021-10-14 17:02:31.000000000','2022-01-07 15:22:47.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(228,'K0440','8d5fe8f769c936b86c3092aab0a80e7c','基于主机的安全产品以及他们如何影响渗透利用和漏洞的知识','','2021-10-14 17:02:32.000000000','2021-10-14 17:02:32.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(229,'K0016','13d63fdbe3da0ff264b9647565cfaed5','计算机编程原则的知识，比如面向对象的设计','[{"relatedId":"K0016","resourceId":"ISTB0103PCXSJ","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103JS","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103PHP","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103HTML","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103ASM","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103CYY","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103GOLANG","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103VFJSP","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103VFJAVASE","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103VFHTML","type":1,"weight":1}]','2021-10-14 17:02:49.000000000','2022-01-07 15:25:50.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(230,'K0452','0bf2eb74863de071afa24856a266e4c7','可以提供 radius 认证和登录、DNS、邮件、Web 服务、FTF 服务、DHCP、防 火墙和 SNMP 服务的 Unix 和 Windows 系统的知识。','[{"relatedId":"K0452","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 17:03:05.000000000','2022-01-10 10:33:31.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(231,'K0018','23f60bb0501dc27b096dd3205683cfa9','密码算法知识。(比如 IPSEC、AES、GRE、IKE、MD5、SHA、3DES)','[{"relatedId":"K0018","resourceId":"CRYPT02FZ","type":1,"weight":1},{"relatedId":"K0018","resourceId":"CRYPT03XL","type":1,"weight":1},{"relatedId":"K0018","resourceId":"CRYPT05GY","type":1,"weight":1},{"relatedId":"K0018","resourceId":"CRYPT04HASH","type":1,"weight":1},{"relatedId":"K0018","resourceId":"ISC0100CRYPTO","type":1,"weight":1}]','2021-10-14 17:03:11.000000000','2022-01-10 08:59:36.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(232,'K0453','521e0b5143744d0fff1e528e08c486b5','指标和警告的知识','','2021-10-14 17:03:24.000000000','2021-10-14 17:03:24.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(233,'K0019','23f60bb0501dc27b096dd3205683cfa9','密码学和密钥管理的概念知识','[{"relatedId":"K0019","resourceId":"CRYPT09GL","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT01CRYPTO","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT06QM","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT07RZ","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT08XY","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT09YS","type":1,"weight":1},{"relatedId":"K0019","resourceId":"ISC0100CRYPTO","type":1,"weight":1}]','2021-10-14 17:03:29.000000000','2022-01-10 09:01:12.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(234,'K0454','90207603744123196c360addca94cc41','信息需求的知识','[{"relatedId":"K0454","resourceId":"ISTB0106INFT","type":1,"weight":1}]','2021-10-14 17:03:41.000000000','2022-01-10 10:33:50.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(235,'K0455','d78b0449533e94ee751bad6b568ecfb9','了解信息安全概念、以及对技术和方法促进','[{"relatedId":"K0455","resourceId":"ISB0100XXAQ","type":1,"weight":1},{"relatedId":"K0455","resourceId":"NS0101AQYSVD4","type":1,"weight":1},{"relatedId":"K0455","resourceId":"NS0101AQYSVD6","type":1,"weight":1}]','2021-10-14 17:04:01.000000000','2022-01-10 10:34:28.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(236,'K0023','13d63fdbe3da0ff264b9647565cfaed5','数据库管理系统、查询语言、关系表和视图的知识','[{"relatedId":"K0023","resourceId":"ISTB0404DBEXP","type":1,"weight":1},{"relatedId":"K0023","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0023","resourceId":"SS0600DBSEC","type":1,"weight":1},{"relatedId":"K0023","resourceId":"ISTB0404DBGL","type":1,"weight":1},{"relatedId":"K0023","resourceId":"ISTB0103VFORACLE","type":1,"weight":1}]','2021-10-14 17:04:02.000000000','2022-01-10 09:03:51.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(237,'K0456','90207603744123196c360addca94cc41','情报的能力和局限性的知识','','2021-10-14 17:04:21.000000000','2021-10-14 17:04:21.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(238,'K0039','13d63fdbe3da0ff264b9647565cfaed5','在软件开发中应用的网络空间安全原则和方法知识','[{"relatedId":"K0039","resourceId":"SS0700SSEC","type":1,"weight":1},{"relatedId":"K0039","resourceId":"SS0900YYXX","type":1,"weight":1}]','2021-10-14 17:04:22.000000000','2022-01-10 09:24:49.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(239,'K0457','13d63fdbe3da0ff264b9647565cfaed5','情报密级的知识','','2021-10-14 17:04:45.000000000','2021-10-14 17:04:45.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(240,'K0052','0bf2eb74863de071afa24856a266e4c7','数学知识，包括对数，三角函数，线性代数，微积分，统计学','[{"relatedId":"K0052","resourceId":"ISB0200INFOMATH","type":1,"weight":1},{"relatedId":"K0052","resourceId":"ISTB0104MATH","type":1,"weight":1},{"relatedId":"K0052","resourceId":"ISTB0301SSCI","type":1,"weight":1}]','2021-10-14 17:04:50.000000000','2022-01-10 09:27:30.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(241,'K0458','8d5fe8f769c936b86c3092aab0a80e7c','情报学科知识','','2021-10-14 17:05:04.000000000','2021-10-14 17:05:04.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(243,'K0459','90207603744123196c360addca94cc41','情报使用需求的知识(比如逻辑的需求、通信支持需求、可操作性需求、法律 限制需求等)','[{"relatedId":"K0459","resourceId":"ISTB0302TXYL","type":1,"weight":1},{"relatedId":"K0459","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 17:05:32.000000000','2022-01-10 10:35:14.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(244,'K0068','0bf2eb74863de071afa24856a266e4c7','编程语言结构和逻辑知识','[{"relatedId":"K0068","resourceId":"ISTB0103WLBC","type":1,"weight":1}]','2021-10-14 17:05:56.000000000','2022-01-10 09:33:27.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(245,'K0469','521e0b5143744d0fff1e528e08c486b5','对威胁能力和行为进行预演和/或模拟的内部行动的知识','[{"relatedId":"K0469","resourceId":"SS0801TCB","type":1,"weight":1}]','2021-10-14 17:05:58.000000000','2022-01-10 10:35:51.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(247,'K0470','d78b0449533e94ee751bad6b568ecfb9','互联网和路由协议的知识','','2021-10-14 17:06:26.000000000','2021-10-14 17:06:26.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(248,'K0080','13d63fdbe3da0ff264b9647565cfaed5','软件设计工具、方法和技术知识','[{"relatedId":"K0080","resourceId":"SS1100ANDROID","type":1,"weight":1}]','2021-10-14 17:06:36.000000000','2022-01-10 09:36:23.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(249,'K0471','521e0b5143744d0fff1e528e08c486b5','互联网网络地址知识(IP 地址、无类域间路由、TCP/UDP 端口号)','','2021-10-14 17:06:48.000000000','2021-10-14 17:06:48.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(250,'K0472','8d5fe8f769c936b86c3092aab0a80e7c','入侵检测系统和签名(技术)的发展','[{"relatedId":"K0472","resourceId":"CRYPT06QM","type":1,"weight":1},{"relatedId":"K0472","resourceId":"NS0200IDSA","type":1,"weight":1}]','2021-10-14 17:07:08.000000000','2022-01-10 10:37:10.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(251,'K0473','23f60bb0501dc27b096dd3205683cfa9','入侵(规则)集的知识','[{"relatedId":"K0473","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 17:07:30.000000000','2022-01-10 10:39:28.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(252,'K0082','0bf2eb74863de071afa24856a266e4c7','软件工程知识','','2021-10-14 17:07:38.000000000','2021-10-14 17:07:38.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(253,'K0474','d78b0449533e94ee751bad6b568ecfb9','键网络威胁行为和他们构成的知识','','2021-10-14 17:07:53.000000000','2021-10-14 17:07:53.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(256,'K0140','d78b0449533e94ee751bad6b568ecfb9','安全编码技术的知识','','2021-10-14 17:08:12.000000000','2021-10-14 17:08:12.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(257,'K0475','8d5fe8f769c936b86c3092aab0a80e7c','操作环境和威胁关键因素的知识','','2021-10-14 17:08:19.000000000','2021-10-14 17:08:19.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(258,'K0178','13d63fdbe3da0ff264b9647565cfaed5','安全软件开发方法、工具和实践知识','','2021-10-14 17:08:37.000000000','2021-10-14 17:08:37.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(259,'K0481','0bf2eb74863de071afa24856a266e4c7','检查各种漏洞利用行为的方法和技术知识','[{"relatedId":"K0481","resourceId":"ISC0100PWN","type":1,"weight":1},{"relatedId":"K0481","resourceId":"SS0503PWN","type":1,"weight":1}]','2021-10-14 17:08:40.000000000','2022-01-10 10:40:02.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(260,'K0486','d78b0449533e94ee751bad6b568ecfb9','网络构建和网络拓扑的知识','','2021-10-14 17:09:00.000000000','2021-10-14 17:09:00.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(261,'K0487','8d5fe8f769c936b86c3092aab0a80e7c','网络安全的知识(比如加密、防火墙、认证、蜜罐、边界保护)','[{"relatedId":"K0487","resourceId":"CRYPT07RZ","type":1,"weight":1},{"relatedId":"K0487","resourceId":"NS0100SEC","type":1,"weight":1}]','2021-10-14 17:09:28.000000000','2022-01-10 10:42:44.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(262,'K0017','13d63fdbe3da0ff264b9647565cfaed5','处理数字取证数据的概念和实践知识','[{"relatedId":"K0017","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 17:09:34.000000000','2022-01-07 15:26:27.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(263,'K0488','d78b0449533e94ee751bad6b568ecfb9','网络安全实现的知识(比如基于主机的 IDS、IPS、访问控制列表)，包括他们的功能以及在网络中的位置','[{"relatedId":"K0488","resourceId":"NS0100SEC","type":1,"weight":1},{"relatedId":"K0488","resourceId":"SS0400AC","type":1,"weight":1}]','2021-10-14 17:09:52.000000000','2022-01-10 10:43:16.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(264,'K0021','13d63fdbe3da0ff264b9647565cfaed5','数据备份、备份类型(比如全备份、增量备份)和数据恢复的概念和工具的知识','','2021-10-14 17:10:02.000000000','2021-10-14 17:10:02.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(265,'K0493','521e0b5143744d0fff1e528e08c486b5','混淆隐藏技术的知识(比如 TOR/Onion/匿名者，VPN/VPS，加密)','[{"relatedId":"K0493","resourceId":"ISTB0405JSJZW","type":1,"weight":1}]','2021-10-14 17:10:21.000000000','2022-01-10 10:44:38.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(266,'K0500','8d5fe8f769c936b86c3092aab0a80e7c','机构和/或合作伙伴(信息)收集系统能力和流程的知识(比如收集和协议处理)','','2021-10-14 17:10:42.000000000','2021-10-14 17:10:42.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(267,'K0077 ','521e0b5143744d0fff1e528e08c486b5','服务端和客户端操作系统的知识','[{"relatedId":"K0077 ","resourceId":"SS0501CZXTAQ","type":1,"weight":1},{"relatedId":"K0077 ","resourceId":"SS0503LINUX","type":1,"weight":1},{"relatedId":"K0077 ","resourceId":"SS0502WIN","type":1,"weight":1}]','2021-10-14 17:10:53.000000000','2022-01-10 09:35:52.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(268,'K0501','d78b0449533e94ee751bad6b568ecfb9','组织机构网络空间运营程序、策略和资源的知识','','2021-10-14 17:11:02.000000000','2021-10-14 17:11:02.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(269,'K0078','13d63fdbe3da0ff264b9647565cfaed5','服务器诊断工具和容错技术的知识','','2021-10-14 17:11:21.000000000','2021-10-14 17:11:21.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(270,'K0515','90207603744123196c360addca94cc41','OSI 模型和底层网络协议(比如 TCP/IP)的知识','','2021-10-14 17:11:29.000000000','2021-10-14 17:11:29.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(271,'K0525','13d63fdbe3da0ff264b9647565cfaed5','与网络空间运营计划相关的情报计划产品知识','','2021-10-14 17:11:48.000000000','2021-10-14 17:11:48.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(272,'K0109','0bf2eb74863de071afa24856a266e4c7','包括各种类型组件和外设(比如 CPU、网卡、数据存储)的功能在内的，物理计算机基本组件和架构的知识','[{"relatedId":"K0109","resourceId":"ISTB0402CZXTYL","type":1,"weight":1}]','2021-10-14 17:11:56.000000000','2022-01-10 09:40:02.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(273,'K0117','0bf2eb74863de071afa24856a266e4c7','文件系统实现的相关知识(比如 NTFS、FAT、EXT)','','2021-10-14 17:12:16.000000000','2021-10-14 17:12:16.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(274,'K0118','13d63fdbe3da0ff264b9647565cfaed5','获取和保存数字证据的过程知识(比如物证连续保存)','','2021-10-14 17:12:37.000000000','2021-10-14 17:12:37.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(275,'K0122','13d63fdbe3da0ff264b9647565cfaed5','了解硬件、操作系统和网络技术调查研究的意义','','2021-10-14 17:13:02.000000000','2021-10-14 17:13:02.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(276,'K0526','90207603744123196c360addca94cc41','研究策略和知识管理的知识','','2021-10-14 17:13:23.000000000','2021-10-14 17:13:23.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(277,'K0123','90207603744123196c360addca94cc41','与(证据)采纳(比如联邦证据规则)相关的法律管理知识','','2021-10-14 17:13:24.000000000','2021-10-14 17:13:24.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(278,'K0527','8d5fe8f769c936b86c3092aab0a80e7c','风险管理和缓解策略知识','','2021-10-14 17:13:41.000000000','2021-10-14 17:13:41.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(279,'K0125','521e0b5143744d0fff1e528e08c486b5','收集、打包、传输和存储电子证据，以避免(证据)改变、丢失、物理损坏或 数据破坏的处理流程知识','','2021-10-14 17:13:47.000000000','2021-10-14 17:13:47.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(280,'K0530','23f60bb0501dc27b096dd3205683cfa9','包括在渗透利用中影响的网络组件在内的，安全软硬件选项知识，','','2021-10-14 17:14:01.000000000','2021-10-14 17:14:01.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(281,'K0128','0bf2eb74863de071afa24856a266e4c7','持久化数据的类型和集合的相关知识','','2021-10-14 17:14:17.000000000','2021-10-14 17:14:17.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(282,'K0536','d78b0449533e94ee751bad6b568ecfb9','渗透利用工具(比如 sniffer、keylogger)的结构、方法和策略知识，以及相 关技术(比如获取后门权限、收集/导出数据、对网络中的其他系统进行漏洞分 析)的知识','','2021-10-14 17:14:26.000000000','2021-10-14 17:14:26.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(284,'K0537','d78b0449533e94ee751bad6b568ecfb9','Unix/Linux、Windows 操作系统系统管理概念的知识(比如进程管理、目录结 构、已安装应用、访问控制)','[{"relatedId":"K0537","resourceId":"SS0400AC","type":1,"weight":1},{"relatedId":"K0537","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 17:14:50.000000000','2022-01-10 10:47:34.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(286,'K0133','8d5fe8f769c936b86c3092aab0a80e7c','数字取证数据的类型以及如何识别他们的知识','[{"relatedId":"K0133","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 17:15:00.000000000','2022-01-10 09:41:41.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(287,'K0538','d78b0449533e94ee751bad6b568ecfb9','了解目标和威胁的组织结构、关键能力和关键漏洞的知识','','2021-10-14 17:15:12.000000000','2021-10-14 17:15:12.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(288,'K0134','90207603744123196c360addca94cc41','移动取证知识','[{"relatedId":"K0134","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 17:15:20.000000000','2022-01-10 09:42:05.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(289,'K0544','23f60bb0501dc27b096dd3205683cfa9','目标情报收集和操作准备的技术和生命周期的知识','','2021-10-14 17:15:33.000000000','2021-10-14 17:15:33.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(290,'K0548','8d5fe8f769c936b86c3092aab0a80e7c','目标或威胁的网络空间参与者和程序','','2021-10-14 17:15:55.000000000','2021-10-14 17:15:55.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(291,'K0155','521e0b5143744d0fff1e528e08c486b5','电子证据法的知识','','2021-10-14 17:16:05.000000000','2021-10-14 17:16:05.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(292,'K0549','521e0b5143744d0fff1e528e08c486b5','了解目标审核和验证程序','','2021-10-14 17:16:16.000000000','2021-10-14 17:16:16.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(293,'K0156','13d63fdbe3da0ff264b9647565cfaed5','证据的法律规则和法庭程序的知识','','2021-10-14 17:16:29.000000000','2021-10-14 17:16:29.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(294,'K0555','d78b0449533e94ee751bad6b568ecfb9','TCP/IP 网络协议的知识','[{"relatedId":"K0555","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 17:16:39.000000000','2022-01-10 10:47:53.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(295,'K0168','8d5fe8f769c936b86c3092aab0a80e7c','适用相关法律的知识(电子通信隐私法、外国情报监视法、保护美国法案、搜 查扣押法案、公民自由和隐私法案)，美国议会法案(比如法案编号 10、18、 32、50)，总统令、行政法规以及工作相关的行政刑事法规和程序','','2021-10-14 17:17:00.000000000','2021-10-14 17:17:00.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(296,'K0561','90207603744123196c360addca94cc41','基本网络安全的知识(比如加密、防火墙、认证、蜜罐、边界保护)','[{"relatedId":"K0561","resourceId":"CRYPT07RZ","type":1,"weight":1},{"relatedId":"K0561","resourceId":"NS0100SEC","type":1,"weight":1}]','2021-10-14 17:17:02.000000000','2022-01-10 10:48:20.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(297,'K0183','0bf2eb74863de071afa24856a266e4c7','逆向工程概念知识','[{"relatedId":"K0183","resourceId":"ISC0101TOOLS","type":1,"weight":1},{"relatedId":"K0183","resourceId":"ISC0100RE","type":1,"weight":1}]','2021-10-14 17:17:44.000000000','2022-01-10 09:56:03.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(298,'K0562','521e0b5143744d0fff1e528e08c486b5','了解新兴(信息)收集能力、访问方式和/或流程的能力和局限性。','','2021-10-14 17:17:48.000000000','2021-10-14 17:17:48.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(299,'K0184','521e0b5143744d0fff1e528e08c486b5','反取证的策略、技术和程序知识','','2021-10-14 17:18:09.000000000','2021-10-14 17:18:09.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(300,'K0563','90207603744123196c360addca94cc41','在应用到规划中的网络空间行动时，这些内部和外部(信息)收集能力、局限 性和任务处理方法知识。','','2021-10-14 17:18:20.000000000','2021-10-14 17:18:20.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(301,'K0564','d78b0449533e94ee751bad6b568ecfb9','目标通信网络的特征知识(比如容量、功能、路径、关键节点)','[{"relatedId":"K0564","resourceId":"ISTB0302TXYL","type":1,"weight":1},{"relatedId":"K0564","resourceId":"ISTB0303JCBM","type":1,"weight":1},{"relatedId":"K0564","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 17:18:45.000000000','2022-01-10 10:49:04.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(302,'K0186','d78b0449533e94ee751bad6b568ecfb9','调试过程和工具知识','','2021-10-14 17:18:50.000000000','2021-10-14 17:18:50.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(303,'K0565','d78b0449533e94ee751bad6b568ecfb9','常用的网络和路由协议(比如 TCP/IP)、服务(比如 Web、邮件、DNS)， 以及他们如何交互来提供网络通信服务的知识。','[{"relatedId":"K0565","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 17:19:04.000000000','2022-01-10 10:49:24.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(304,'K0568','521e0b5143744d0fff1e528e08c486b5','(信息)收集管理定义和收集管理权限的知识','','2021-10-14 17:19:26.000000000','2021-10-14 17:19:26.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(305,'K0187','13d63fdbe3da0ff264b9647565cfaed5','了解异常行为可能使用哪些不同类型文件的知识','','2021-10-14 17:19:30.000000000','2021-10-14 17:19:30.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(306,'K0572','90207603744123196c360addca94cc41','了解模拟威胁行为帮助组织机构(提升安全)的内部团队的能力','','2021-10-14 17:19:45.000000000','2021-10-14 17:19:45.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(307,'K0188','90207603744123196c360addca94cc41','恶意软件分析工具(比如 OD、IDA)知识','','2021-10-14 17:19:58.000000000','2021-10-14 17:19:58.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(308,'K0578','d78b0449533e94ee751bad6b568ecfb9','情报需求开发和信息处理要求的知识','[{"relatedId":"K0578","resourceId":"CS0304SJFXCL","type":1,"weight":1},{"relatedId":"K0578","resourceId":"ISTB0103VFCSS","type":1,"weight":1}]','2021-10-14 17:20:05.000000000','2022-01-10 10:49:51.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(309,'K0593','0bf2eb74863de071afa24856a266e4c7','了解网络空间运营的范围和他们潜在的情报支持需求、问题和关注点。','','2021-10-14 17:20:27.000000000','2021-10-14 17:20:27.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(310,'K0595','13d63fdbe3da0ff264b9647565cfaed5','了解操作目标、情报需求和情报产品任务之间关系','','2021-10-14 17:20:49.000000000','2021-10-14 17:20:49.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(311,'K0596','d78b0449533e94ee751bad6b568ecfb9','了解信息处理的要求','','2021-10-14 17:21:10.000000000','2021-10-14 17:21:10.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(312,'K0051','13d63fdbe3da0ff264b9647565cfaed5','低层次计算机语言的知识(比如汇编语言)','[{"relatedId":"K0051","resourceId":"ISTB0103ASM","type":1,"weight":1}]','2021-10-14 17:21:10.000000000','2022-01-10 09:26:44.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(313,'K0597','d78b0449533e94ee751bad6b568ecfb9','了解网络空间运营在支撑和促进其他机构运营上的作用。','','2021-10-14 17:21:33.000000000','2021-10-14 17:21:33.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(314,'K0097','0bf2eb74863de071afa24856a266e4c7','物理和虚拟数据存储介质特征的知识','','2021-10-14 17:21:53.000000000','2021-10-14 17:21:53.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(315,'K0602','0bf2eb74863de071afa24856a266e4c7','了解各种(信息)收集的科目和能力','','2021-10-14 17:21:56.000000000','2021-10-14 17:21:56.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(316,'K0603','90207603744123196c360addca94cc41','了解目标或威胁使用互联网的方式','','2021-10-14 17:22:16.000000000','2021-10-14 17:22:16.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(317,'K0171','13d63fdbe3da0ff264b9647565cfaed5','硬件逆向工程技术知识','','2021-10-14 17:22:38.000000000','2021-10-14 17:22:38.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(318,'K0604','521e0b5143744d0fff1e528e08c486b5','威胁和/或目标系统的知识','','2021-10-14 17:22:44.000000000','2021-10-14 17:22:44.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(319,'K0175','521e0b5143744d0fff1e528e08c486b5','软件逆向工程技术知识','[{"relatedId":"K0175","resourceId":"ISC0100RE","type":1,"weight":1}]','2021-10-14 17:23:03.000000000','2022-01-10 09:50:18.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(320,'K0608','8d5fe8f769c936b86c3092aab0a80e7c','Unix、Linux 和 Windows 操作系统的结构和内部知识(比如进程管理、目录结 构、已安装应用)','[{"relatedId":"K0608","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 17:23:07.000000000','2022-01-10 10:50:13.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(321,'K0609','0bf2eb74863de071afa24856a266e4c7','虚拟机技术的知识','[{"relatedId":"K0609","resourceId":"ISTB0405VT","type":1,"weight":1}]','2021-10-14 17:23:27.000000000','2022-01-10 10:50:35.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(322,'K0610','13d63fdbe3da0ff264b9647565cfaed5','虚拟化产品(VMWare、虚拟 PC)的知识','[{"relatedId":"K0610","resourceId":"ISTB0405VT","type":1,"weight":1}]','2021-10-14 17:23:50.000000000','2022-01-10 10:50:54.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(323,'K0612','0bf2eb74863de071afa24856a266e4c7','了解什么构成了网络的“威胁”','[{"relatedId":"K0612","resourceId":"NS0101AQYSVD5","type":1,"weight":1}]','2021-10-14 17:24:10.000000000','2022-01-10 10:51:14.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(324,'K0034','521e0b5143744d0fff1e528e08c486b5','了解网络服务和协议如何交互提供网络通信服务的知识','[{"relatedId":"K0034","resourceId":"ISTB0405WLFWJS","type":1,"weight":1},{"relatedId":"K0034","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 17:24:16.000000000','2022-01-10 09:06:15.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(325,'K0035','521e0b5143744d0fff1e528e08c486b5','系统组件如何安装、集成和优化的知识','[{"relatedId":"K0035","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 17:24:43.000000000','2022-01-10 09:24:00.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(327,'K0372','13d63fdbe3da0ff264b9647565cfaed5','基本的编程概念知识(比如层次、结构、编译型 vs 解释型语言)','[{"relatedId":"K0372","resourceId":"ISTB0103WLBC","type":1,"weight":1},{"relatedId":"K0372","resourceId":"ISTB0403BYXT","type":1,"weight":1}]','2021-10-14 17:25:14.000000000','2022-01-10 10:12:12.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(329,'K0050','0bf2eb74863de071afa24856a266e4c7','包括带宽管理的局域网和广域网原则和概念知识','','2021-10-14 17:25:46.000000000','2021-10-14 17:25:46.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(330,'K0396','521e0b5143744d0fff1e528e08c486b5','包括计算机语言、编程、测试、调试和文件类型在内的计算机编程概念知识，','[{"relatedId":"K0396","resourceId":"ISTB0103CYY","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103PCXSJ","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103JCXSJ","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103GOLANG","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103PHP","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103VFJSP","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103VFJAVASE","type":1,"weight":1}]','2021-10-14 17:26:00.000000000','2022-01-10 10:16:54.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(331,'K0056','0bf2eb74863de071afa24856a266e4c7','网络接入、身份识别和访问管理知识(比如 PKI)','','2021-10-14 17:26:07.000000000','2021-10-14 17:26:07.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(333,'K0057','0bf2eb74863de071afa24856a266e4c7','网络硬件设备和功能的知识','[{"relatedId":"K0057","resourceId":"SS0200SBAQ","type":1,"weight":1}]','2021-10-14 17:26:26.000000000','2022-01-10 09:30:17.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(334,'K0420','0bf2eb74863de071afa24856a266e4c7','数据库理论知识','[{"relatedId":"K0420","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0420","resourceId":"ISTB0404DBGL","type":1,"weight":1}]','2021-10-14 17:26:36.000000000','2022-01-10 10:32:31.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(335,'K0088','13d63fdbe3da0ff264b9647565cfaed5','系统管理概念的知识','','2021-10-14 17:26:42.000000000','2021-10-14 17:26:42.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(336,'K0089','13d63fdbe3da0ff264b9647565cfaed5','系统诊断工具和容错技术的知识','','2021-10-14 17:26:57.000000000','2021-10-14 17:26:57.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(337,'K0305','23f60bb0501dc27b096dd3205683cfa9','加密算法、信息隐写(stenography)和其他数据隐藏的形式知识','[{"relatedId":"K0305","resourceId":"CS0501XXYC","type":1,"weight":1}]','2021-10-14 17:27:08.000000000','2022-01-10 10:05:30.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(338,'K0149','521e0b5143744d0fff1e528e08c486b5','组织机构的风险承受能力和/或风险管理方法知识','','2021-10-14 17:27:25.000000000','2021-10-14 17:27:25.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(341,'K0151','0bf2eb74863de071afa24856a266e4c7','当前和新兴的威胁和微信向量的知识','','2021-10-14 17:27:59.000000000','2021-10-14 17:27:59.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(345,'K0220','0bf2eb74863de071afa24856a266e4c7','学习模式的知识(比如死记硬背、观察学习)','','2021-10-14 17:29:08.000000000','2021-10-14 17:29:08.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(348,'K0360','521e0b5143744d0fff1e528e08c486b5','汇编代码的知识','[{"relatedId":"K0360","resourceId":"ISC0101TOOLS","type":1,"weight":1}]','2021-10-14 17:29:34.000000000','2022-01-10 10:11:15.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(349,'K0264','13d63fdbe3da0ff264b9647565cfaed5','包括 IT 供应链安全/风险管理政策、防篡改技术和需求在内的程序保护设计知识','[{"relatedId":"K0264","resourceId":"NS0101AQYSVD2","type":1,"weight":1},{"relatedId":"K0264","resourceId":"NS0101AQYSVD3","type":1,"weight":1}]','2021-10-14 17:29:41.000000000','2022-01-10 10:01:54.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(350,'K0369','23f60bb0501dc27b096dd3205683cfa9','恶意活动的概念知识(比如踩点、扫描和枚举)','','2021-10-14 17:29:59.000000000','2021-10-14 17:29:59.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(351,'K0265','d78b0449533e94ee751bad6b568ecfb9','支撑 IT 安全性、性能和可靠性的基础设施知识','[{"relatedId":"K0265","resourceId":"ISTB0101DZDL","type":1,"weight":1},{"relatedId":"K0265","resourceId":"SS0300KKXJS","type":1,"weight":1},{"relatedId":"K0265","resourceId":"ISTB0102EDA","type":1,"weight":1}]','2021-10-14 17:30:03.000000000','2022-01-10 10:02:38.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(353,'K0479','d78b0449533e94ee751bad6b568ecfb9','恶意软件分析和特征化的知识','','2021-10-14 17:30:30.000000000','2021-10-14 17:30:30.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(354,'K0516','0bf2eb74863de071afa24856a266e4c7','包括集线器、交换机、路由器、防火墙等在内的物理的和逻辑的网络设备和基 础设施知识','','2021-10-14 17:30:49.000000000','2021-10-14 17:30:49.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(355,'K0480','521e0b5143744d0fff1e528e08c486b5','恶意软件的知识','','2021-10-14 17:30:54.000000000','2021-10-14 17:30:54.000000000');
INSERT INTO "init_rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(362,'K0182','90207603744123196c360addca94cc41','Data Carving 技术和工具(比如 Foremost)','','2021-10-15 09:12:27.000000000','2021-10-15 09:12:27.000000000');

SET IDENTITY_INSERT "init_rtks_knowledge" OFF;
SET IDENTITY_INSERT "init_rtks_knowledge_category" ON;
INSERT INTO "init_rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(12,'13d63fdbe3da0ff264b9647565cfaed5','ISTB_信息科学技术基础领域','2021-10-14 15:50:59.000000000','2021-10-14 15:50:59.000000000');
INSERT INTO "init_rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(13,'0bf2eb74863de071afa24856a266e4c7','ISB_信息安全基础领域','2021-10-14 15:51:06.000000000','2021-10-14 15:51:06.000000000');
INSERT INTO "init_rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(14,'23f60bb0501dc27b096dd3205683cfa9','CRYPT_密码学知识领域','2021-10-14 15:53:11.000000000','2021-10-14 15:53:11.000000000');
INSERT INTO "init_rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(15,'521e0b5143744d0fff1e528e08c486b5','CS_内容安全知识领域','2021-10-14 15:53:23.000000000','2021-10-14 15:53:23.000000000');
INSERT INTO "init_rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(17,'90207603744123196c360addca94cc41','ISC_信息安全竞赛','2021-10-14 15:53:49.000000000','2021-10-14 15:53:49.000000000');
INSERT INTO "init_rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(18,'d78b0449533e94ee751bad6b568ecfb9','SS_系统安全知识领域','2021-10-14 15:54:02.000000000','2021-10-14 15:54:02.000000000');
INSERT INTO "init_rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(19,'8d5fe8f769c936b86c3092aab0a80e7c','NS_网络安全知识领域','2021-10-14 15:54:08.000000000','2021-10-14 15:54:08.000000000');

SET IDENTITY_INSERT "init_rtks_knowledge_category" OFF;
SET IDENTITY_INSERT "init_rtks_level" ON;
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(1,'default','网络安全框架',null,null,'2021-09-09 08:52:09.000000000','2021-09-09 09:56:47.000000000',null);
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(3,'160a82bb64a4b931d1ecfc0907e34820','安全基础设施提供',null,'default','2021-09-09 08:52:09.000000000','2021-09-09 09:56:47.000000000','default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(4,'9c47cad21bf1c3a19168126aef24c771','运营与维护',null,'default',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(5,'8dba745e3fcaa3448cc303db510f43e0','监管与治理',null,'default',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(6,'39b1a4360ca4cb64e14e90534246f27b','保护和防御',null,'default',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(7,'bc4e6ddddbe2ff78d0eae79de829272e','分析',null,'default',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(8,'5836fceb39ebb1867f2d9d357e52cd2f','搜集与运作',null,'default',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(9,'a6ff1c1d65b1c411aea838460333d0a9','调查',null,'default',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(10,'fb9be3d8be71435eef3144ed9744a473','风险管理',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(11,'2f2a09ed40e6166f8a54f136f7f4a25d','软件开发',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(12,'76a295b70048f129a5ba8b44e8467bc3','系统架构',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(13,'103b4f90a89015f9b88600c7eaf7b998','技术研发',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(14,'8e2fb48d3431b905cedeb37e4a0e0dca','系统需求规划',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(15,'dd26731845f65a12356ad356c62acb04','测试评估',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(16,'9f16afbb771c6cda169d3ac1b8862774','系统开发',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(17,'49a1982ccda399c198a1c452a0164d84','数据管理',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(18,'9487b7559c2d5c786ca680f335a2ef92','知识管理',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(19,'cbe07af2274f5d5ae136c8dd0afdaf96','客户服务和技术支持',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(20,'c301984071d24a98f7f38260b98441f8','网络服务',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(21,'d01fe0d4855de8f7a8440d054cce4bd1','系统管理',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(22,'65f63e779bc673e3ccde5dd45627a27d','系统分析',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(23,'c2085b43474d5a86e0184903903e3e8b','法律咨询和辩护',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(24,'36322f436eb79c7cd0554e54a78ed384','培训、教育和养成',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(25,'658a4e7c8b496e0a690103ff2793048f','网络空间安全管理',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(26,'0798f66e0a3b1c0e48e3ce0946e28b60','战略规划和策略',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(27,'c149fd006f78ff5fb69752030f03bded','网络空间安全执行领导',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(28,'b8fbbb94bd3d961bb06e649a9e36acd3','采购和项目管理',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(29,'107ac7e46706743c38fa190ba65f86d7','网络空间安全防御分析',null,'39b1a4360ca4cb64e14e90534246f27b',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(30,'91abaa83081fea4f76a2b9ade501fc2f','网络空间安全防御基础设施支持',null,'39b1a4360ca4cb64e14e90534246f27b',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(31,'38314f7f03e45c90bd5f69e04d8cb7c4','事件响应',null,'39b1a4360ca4cb64e14e90534246f27b',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(32,'bfd62abaccf84cd3c072127719af257d','漏洞评估和管理',null,'39b1a4360ca4cb64e14e90534246f27b',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(33,'16a326962fd10baed76e656f905cb8e9','威胁分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(34,'6e2bfa5b7fd30825e784d1d4bdfa9b6d','渗透分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(35,'95266cc7d4b48de23fdff2ec8429c539','全源分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(36,'32b7ba539a3aad2cdf9e09e843e5362c','目标分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(37,'69790adaf0b2c9d87527db95399567d6','语言分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(38,'b70c0b9ae595c7e2544c1d0eb1c9f539','搜集与运作',null,'5836fceb39ebb1867f2d9d357e52cd2f',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(39,'90decfd39b34eac09bd23c22cf746edb','网络空间运营规划',null,'5836fceb39ebb1867f2d9d357e52cd2f',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(40,'5c82d106b63705bca1462a10003991da','网络空间运营',null,'5836fceb39ebb1867f2d9d357e52cd2f',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(41,'5f452e2bd7d74a4b5b2b1cd0098adf33','网络调查',null,'a6ff1c1d65b1c411aea838460333d0a9',null,null,'default');
INSERT INTO "init_rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(42,'146862c6f4aebb353891381cf1aa832c','数字取证',null,'a6ff1c1d65b1c411aea838460333d0a9',null,null,'default');

SET IDENTITY_INSERT "init_rtks_level" OFF;
SET IDENTITY_INSERT "init_rtks_role" ON;
INSERT INTO "init_rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions") VALUES(121,'c3fe96a8de569cdb92e273c8809a73b7','渗透测试工程师','是网络安全领域的核心角色，通过模拟黑客攻击手段，对目标系统（如 Web 应用、网络基础设施、移动应用等）进行安全性测试，识别潜在漏洞并提供修复建议，以增强系统的抗攻击能力。','2021-10-15 08:52:18.000000000','2022-01-10 10:51:14.000000000','bfd62abaccf84cd3c072127719af257d','','["T0023","T0024","T0027","T0028","T0120","T0161","T0165","T0166","T0175","T0180","T0181","T0198","T0214","T0229","T0240","T0266","T0285","T0295","T0297","T0299","T0310","T0360","T0397","T0423","T0430","T0432","T0433","T0518","T0565","T0570","T0572","T0593","T0605","T0606","T0609","T0610","T0616","T0686","T0687","T0696","T0703","T0705","T0708","T0710","T0715","T0718","T0719","T0845","T0848"]','[{"id":"K0001","weight":1},{"id":"K0002","weight":1},{"id":"K0003","weight":1},{"id":"K0202","weight":1},{"id":"K0004","weight":1},{"id":"K0005","weight":1},{"id":"K0205","weight":1},{"id":"K0006","weight":1},{"id":"K0206","weight":1},{"id":"K0007","weight":1},{"id":"K0214","weight":1},{"id":"K0009","weight":1},{"id":"K0219","weight":1},{"id":"K0013","weight":1},{"id":"K0221","weight":1},{"id":"K0024","weight":1},{"id":"K0222","weight":1},{"id":"K0033","weight":1},{"id":"K0037","weight":1},{"id":"K0224","weight":1},{"id":"K0040","weight":1},{"id":"K0225","weight":1},{"id":"K0041","weight":1},{"id":"K0230","weight":1},{"id":"K0236","weight":1},{"id":"K0042","weight":1},{"id":"K0271","weight":1},{"id":"K0044","weight":1},{"id":"K0046","weight":1},{"id":"K0273","weight":1},{"id":"K0277","weight":1},{"id":"K0049","weight":1},{"id":"K0290","weight":1},{"id":"K0058","weight":1},{"id":"K0292","weight":1},{"id":"K0059","weight":1},{"id":"K0297","weight":1},{"id":"K0060","weight":1},{"id":"K0298","weight":1},{"id":"K0061","weight":1},{"id":"K0301","weight":1},{"id":"K0302","weight":1},{"id":"K0062","weight":1},{"id":"K0069","weight":1},{"id":"K0303","weight":1},{"id":"K0307","weight":1},{"id":"K0070","weight":1},{"id":"K0309","weight":1},{"id":"K0071","weight":1},{"id":"K0310","weight":1},{"id":"K0085","weight":1},{"id":"K0314","weight":1},{"id":"K0098","weight":1},{"id":"K0315","weight":1},{"id":"K0318","weight":1},{"id":"K0099","weight":1},{"id":"K0324","weight":1},{"id":"K0104","weight":1},{"id":"K0326","weight":1},{"id":"K0105","weight":1},{"id":"K0331","weight":1},{"id":"K0106","weight":1},{"id":"K0332","weight":1},{"id":"K0107","weight":1},{"id":"K0334","weight":1},{"id":"K0111","weight":1},{"id":"K0335","weight":1},{"id":"K0112","weight":1},{"id":"K0336","weight":1},{"id":"K0113","weight":1},{"id":"K0339","weight":1},{"id":"K0115","weight":1},{"id":"K0340","weight":1},{"id":"K0119","weight":1},{"id":"K0342","weight":1},{"id":"K0129","weight":1},{"id":"K0344","weight":1},{"id":"K0345","weight":1},{"id":"K0352","weight":1},{"id":"K0131","weight":1},{"id":"K0355","weight":1},{"id":"K0132","weight":1},{"id":"K0356","weight":1},{"id":"K0135","weight":1},{"id":"K0362","weight":1},{"id":"K0142","weight":1},{"id":"K0363","weight":1},{"id":"K0143","weight":1},{"id":"K0364","weight":1},{"id":"K0144","weight":1},{"id":"K0367","weight":1},{"id":"K0371","weight":1},{"id":"K0145","weight":1},{"id":"K0386","weight":1},{"id":"K0387","weight":1},{"id":"K0388","weight":1},{"id":"K0389","weight":1},{"id":"K0390","weight":1},{"id":"K0391","weight":1},{"id":"K0392","weight":1},{"id":"K0147","weight":1},{"id":"K0393","weight":1},{"id":"K0150","weight":1},{"id":"K0394","weight":1},{"id":"K0395","weight":1},{"id":"K0157","weight":1},{"id":"K0161","weight":1},{"id":"K0397","weight":1},{"id":"K0162","weight":1},{"id":"K0398","weight":1},{"id":"K0165","weight":1},{"id":"K0401","weight":1},{"id":"K0167","weight":1},{"id":"K0402","weight":1},{"id":"K0404","weight":1},{"id":"K0405","weight":1},{"id":"K0406","weight":1},{"id":"K0185","weight":1},{"id":"K0407","weight":1},{"id":"K0189","weight":1},{"id":"K0408","weight":1},{"id":"K0192","weight":1},{"id":"K0409","weight":1},{"id":"K0410","weight":1},{"id":"K0411","weight":1},{"id":"K0430","weight":1},{"id":"K0432","weight":1},{"id":"K0433","weight":1},{"id":"K0434","weight":1},{"id":"K0435","weight":1},{"id":"K0436","weight":1},{"id":"K0440","weight":1},{"id":"K0452","weight":1},{"id":"K0453","weight":1},{"id":"K0454","weight":1},{"id":"K0455","weight":1},{"id":"K0456","weight":1},{"id":"K0457","weight":1},{"id":"K0458","weight":1},{"id":"K0459","weight":1},{"id":"K0469","weight":1},{"id":"K0470","weight":1},{"id":"K0471","weight":1},{"id":"K0472","weight":1},{"id":"K0473","weight":1},{"id":"K0474","weight":1},{"id":"K0475","weight":1},{"id":"K0481","weight":1},{"id":"K0486","weight":1},{"id":"K0487","weight":1},{"id":"K0488","weight":1},{"id":"K0493","weight":1},{"id":"K0500","weight":1},{"id":"K0501","weight":1},{"id":"K0515","weight":1},{"id":"K0525","weight":1},{"id":"K0526","weight":1},{"id":"K0527","weight":1},{"id":"K0530","weight":1},{"id":"K0536","weight":1},{"id":"K0537","weight":1},{"id":"K0538","weight":1},{"id":"K0544","weight":1},{"id":"K0548","weight":1},{"id":"K0549","weight":1},{"id":"K0555","weight":1},{"id":"K0561","weight":1},{"id":"K0562","weight":1},{"id":"K0563","weight":1},{"id":"K0564","weight":1},{"id":"K0565","weight":1},{"id":"K0568","weight":1},{"id":"K0572","weight":1},{"id":"K0578","weight":1},{"id":"K0593","weight":1},{"id":"K0595","weight":1},{"id":"K0596","weight":1},{"id":"K0597","weight":1},{"id":"K0602","weight":1},{"id":"K0603","weight":1},{"id":"K0604","weight":1},{"id":"K0608","weight":1},{"id":"K0609","weight":1},{"id":"K0610","weight":1},{"id":"K0612","weight":1},{"id":"K0420","weight":1}]','["K0001","K0002","K0003","K0202","K0004","K0005","K0205","K0006","K0206","K0007","K0214","K0009","K0219","K0013","K0221","K0024","K0222","K0033","K0037","K0224","K0040","K0225","K0041","K0230","K0236","K0042","K0271","K0044","K0046","K0273","K0277","K0049","K0290","K0058","K0292","K0059","K0297","K0060","K0298","K0061","K0301","K0302","K0062","K0069","K0303","K0307","K0070","K0309","K0071","K0310","K0085","K0314","K0098","K0315","K0318","K0099","K0324","K0104","K0326","K0105","K0331","K0106","K0332","K0107","K0334","K0111","K0335","K0112","K0336","K0113","K0339","K0115","K0340","K0119","K0342","K0129","K0344","K0345","K0352","K0131","K0355","K0132","K0356","K0135","K0362","K0142","K0363","K0143","K0364","K0144","K0367","K0371","K0145","K0386","K0387","K0388","K0389","K0390","K0391","K0392","K0147","K0393","K0150","K0394","K0395","K0157","K0161","K0397","K0162","K0398","K0165","K0401","K0167","K0402","K0404","K0405","K0406","K0185","K0407","K0189","K0408","K0192","K0409","K0410","K0411","K0430","K0432","K0433","K0434","K0435","K0436","K0440","K0452","K0453","K0454","K0455","K0456","K0457","K0458","K0459","K0469","K0470","K0471","K0472","K0473","K0474","K0475","K0481","K0486","K0487","K0488","K0493","K0500","K0501","K0515","K0525","K0526","K0527","K0530","K0536","K0537","K0538","K0544","K0548","K0549","K0555","K0561","K0562","K0563","K0564","K0565","K0568","K0572","K0578","K0593","K0595","K0596","K0597","K0602","K0603","K0604","K0608","K0609","K0610","K0612","K0420"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISB0500LAWMGT","ISB0300WLAQF","CRYPT07RZ","NS0403GFJSYSJ","SS0503PWN","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","SS0400AC","ISC0100WEB","SS0900YYXX","ISTB0103VFREDIS","SS0600DBSEC","ISTB0404DBGL","ISTB0103VFORACLE","ISTB0405JSJZW","ISTB0402VFLINUX","CS0303SJKSH","CS0304SJFXCL","CS0201MLADM","CS0305HADOOP","CS0306BIGDATA","NS0301NSAML","ISTB0103PCXSJ","ISTB0103JCXSJ","ISTB0103VFJAVASE","ISTB0103VFMYSQL","ISTB0103VFSERVLET","ISTB0402CZXTYL","CS0400NRGK","NS0200IDSA","CRYPT09GL","SS0100AQGN","NS0101AQYSVD2","NS0504WEBAQ","NS0501WEBUG","NS0503DVWA","NS0505WEBSZ","NS0503WEB","CS0600YSBH","ISTB0304WXTX","ISTB0103VFBOOTSTRAP","ISTB0301SSCI","ISTB0302TXYL","ISTB0303JCBM","ISC0101TOOLS","ISC0100MISC","CS0501XXYC","CS0307DJYSJ","CS0200WLSJHQ","ISC0102XMAN17","ISC0102XMAN16","SS0200SBAQ","ISTB0103HTML","ISTB0103VFJQUERY","ISTB0103VFHTML","ISTB0103VFJAVASCRIPT","SS1001DZSJQZ","NS0400FH","ISTB0106INFT","ISB0100XXAQ","NS0101AQYSVD4","NS0101AQYSVD6","SS0801TCB","CRYPT06QM","ISC0100PWN","ISTB0103VFCSS","ISTB0405VT","NS0101AQYSVD5"]',50,'[]');
INSERT INTO "init_rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions") VALUES(122,'e25dc8c9d15ddd621f16616083d68726','安全开发工程师','专注于设计与开发安全相关的软件、工具及系统，通过代码层面的安全能力构建，从源头预防漏洞，提升产品或系统的安全防护水平。','2021-10-15 08:54:27.000000000','2022-01-10 10:32:31.000000000','2f2a09ed40e6166f8a54f136f7f4a25d','','["T0014","T0016","T0303","T0304","T0311","T0324","T0327","T0329","T0428","T0455","T0563"]','[{"id":"K0001","weight":1},{"id":"K0003","weight":1},{"id":"K0004","weight":1},{"id":"K0014","weight":1},{"id":"K0015","weight":1},{"id":"K0016","weight":1},{"id":"K0018","weight":1},{"id":"K0019","weight":1},{"id":"K0023","weight":1},{"id":"K0039","weight":1},{"id":"K0052","weight":1},{"id":"K0060","weight":1},{"id":"K0068","weight":1},{"id":"K0069","weight":1},{"id":"K0080","weight":1},{"id":"K0082","weight":1},{"id":"K0119","weight":1},{"id":"K0140","weight":1},{"id":"K0178","weight":1},{"id":"K0224","weight":1},{"id":"K0372","weight":1},{"id":"K0395","weight":1},{"id":"K0396","weight":1},{"id":"K0397","weight":1},{"id":"K0420","weight":1}]','["K0001","K0003","K0004","K0014","K0015","K0016","K0018","K0019","K0023","K0039","K0052","K0060","K0068","K0069","K0080","K0082","K0119","K0140","K0178","K0224","K0372","K0395","K0396","K0397","K0420"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISB0300WLAQF","ISTB0105DSA","CS0302SJFXSF","CS0201MLADM","NS0301NSAML","ISTB0103PCXSJ","ISTB0103JS","ISTB0103PHP","ISTB0103HTML","ISTB0103ASM","ISTB0103CYY","ISTB0103GOLANG","ISTB0103VFJSP","ISTB0103VFJAVASE","ISTB0103VFHTML","CRYPT02FZ","CRYPT03XL","CRYPT05GY","CRYPT04HASH","ISC0100CRYPTO","CRYPT09GL","CRYPT01CRYPTO","CRYPT06QM","CRYPT07RZ","CRYPT08XY","CRYPT09YS","ISTB0404DBEXP","ISTB0103VFREDIS","SS0600DBSEC","ISTB0404DBGL","ISTB0103VFORACLE","SS0700SSEC","SS0900YYXX","ISB0200INFOMATH","ISTB0104MATH","ISTB0301SSCI","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","ISTB0103WLBC","ISTB0103VFMYSQL","SS1100ANDROID","ISTB0402VFLINUX","ISTB0403BYXT","ISTB0402CZXTYL","ISTB0103JCXSJ"]',50,'[]');
INSERT INTO "init_rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions") VALUES(123,'aeec2fc773691f459105b0323c3e1711','安全取证工程师','负责收集、分析网络安全事件中的电子证据，还原攻击过程，为网络犯罪调查、法律诉讼或企业内部安全事件溯源提供技术支持。','2021-10-15 08:55:09.000000000','2022-01-10 10:05:30.000000000','658a4e7c8b496e0a690103ff2793048f','','["T0048","T0172","T0216","T0238","T0286","T0289","T0532"]','[{"id":"K0001","weight":1},{"id":"K0002","weight":1},{"id":"K0003","weight":1},{"id":"K0004","weight":1},{"id":"K0006","weight":1},{"id":"K0017","weight":1},{"id":"K0021","weight":1},{"id":"K0042","weight":1},{"id":"K0060","weight":1},{"id":"K0070","weight":1},{"id":"K0077 ","weight":1},{"id":"K0078","weight":1},{"id":"K0099","weight":1},{"id":"K0109","weight":1},{"id":"K0117","weight":1},{"id":"K0118","weight":1},{"id":"K0119","weight":1},{"id":"K0122","weight":1},{"id":"K0123","weight":1},{"id":"K0125","weight":1},{"id":"K0128","weight":1},{"id":"K0131","weight":1},{"id":"K0132","weight":1},{"id":"K0133","weight":1},{"id":"K0134","weight":1},{"id":"K0145","weight":1},{"id":"K0155","weight":1},{"id":"K0156","weight":1},{"id":"K0167","weight":1},{"id":"K0168","weight":1},{"id":"K0179","weight":1},{"id":"K0182","weight":1},{"id":"K0183","weight":1},{"id":"K0184","weight":1},{"id":"K0185","weight":1},{"id":"K0186","weight":1},{"id":"K0187","weight":1},{"id":"K0188","weight":1},{"id":"K0189","weight":1},{"id":"K0305","weight":1}]','["K0001","K0002","K0003","K0004","K0006","K0017","K0021","K0042","K0060","K0070","K0077 ","K0078","K0099","K0109","K0117","K0118","K0119","K0122","K0123","K0125","K0128","K0131","K0132","K0133","K0134","K0145","K0155","K0156","K0167","K0168","K0179","K0182","K0183","K0184","K0185","K0186","K0187","K0188","K0189","K0305"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISB0500LAWMGT","ISB0300WLAQF","SS0503PWN","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","NS0504WEBAQ","NS0501WEBUG","NS0503DVWA","NS0505WEBSZ","NS0503WEB","ISTB0304WXTX","CS0307DJYSJ","NS0101AQYS","NS0101AQYSVD1","SS1201ICS","ISTB0405WLFWJS","SS1001DZSJQZ","ISTB0402CZXTYL","ISC0101TOOLS","ISC0100RE","CS0501XXYC"]',50,'[]');
INSERT INTO "init_rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions") VALUES(124,'f7753c4236c6b6981a577118a686aeb3','逆向分析工程师','通过反汇编、反编译等技术，对软件、固件或恶意代码进行逆向分析，以识别漏洞、提取功能逻辑或分析恶意行为，为安全防御提供技术支撑。','2021-10-15 08:56:45.000000000','2022-01-10 10:50:54.000000000','6e2bfa5b7fd30825e784d1d4bdfa9b6d','','["T0077","T0176","T0179","T0182","T0260","T0287","T0288","T0296","T0385","T0396","T0398","T0403","T0411"]','[{"id":"K0001","weight":1},{"id":"K0051","weight":1},{"id":"K0060","weight":1},{"id":"K0061","weight":1},{"id":"K0062","weight":1},{"id":"K0097","weight":1},{"id":"K0117","weight":1},{"id":"K0118","weight":1},{"id":"K0129","weight":1},{"id":"K0132","weight":1},{"id":"K0171","weight":1},{"id":"K0175","weight":1},{"id":"K0183","weight":1},{"id":"K0186","weight":1},{"id":"K0188","weight":1},{"id":"K0189","weight":1},{"id":"K0224","weight":1},{"id":"K0271","weight":1},{"id":"K0301","weight":1},{"id":"K0302","weight":1},{"id":"K0305","weight":1},{"id":"K0307","weight":1},{"id":"K0309","weight":1},{"id":"K0360","weight":1},{"id":"K0369","weight":1},{"id":"K0433","weight":1},{"id":"K0479","weight":1},{"id":"K0480","weight":1},{"id":"K0493","weight":1},{"id":"K0515","weight":1},{"id":"K0537","weight":1},{"id":"K0609","weight":1},{"id":"K0610","weight":1}]','["K0001","K0051","K0060","K0061","K0062","K0097","K0117","K0118","K0129","K0132","K0171","K0175","K0183","K0186","K0188","K0189","K0224","K0271","K0301","K0302","K0305","K0307","K0309","K0360","K0369","K0433","K0479","K0480","K0493","K0515","K0537","K0609","K0610"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISTB0103ASM","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","CS0400NRGK","ISC0100RE","ISC0101TOOLS","ISTB0402VFLINUX","ISTB0402CZXTYL","CS0501XXYC","SS1001DZSJQZ","ISTB0405JSJZW","SS0400AC","ISTB0405VT"]',50,'[]');
INSERT INTO "init_rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions") VALUES(125,'542fc3e2caf0fa9b4935432ffe4daafa','安全运维工程师','负责企业安全基础设施的日常运维、安全事件响应及风险管控，通过监控、配置与优化安全设备，确保系统持续抵御网络攻击。','2021-10-15 08:57:17.000000000','2022-01-10 10:47:53.000000000','cbe07af2274f5d5ae136c8dd0afdaf96','','["T0158","T0159","T0160","T0162","T0163","T0164"]','[{"id":"K0001","weight":1},{"id":"K0033","weight":1},{"id":"K0034","weight":1},{"id":"K0035","weight":1},{"id":"K0041","weight":1},{"id":"K0042","weight":1},{"id":"K0049","weight":1},{"id":"K0050","weight":1},{"id":"K0051","weight":1},{"id":"K0056","weight":1},{"id":"K0057","weight":1},{"id":"K0060","weight":1},{"id":"K0088","weight":1},{"id":"K0089","weight":1},{"id":"K0099","weight":1},{"id":"K0149","weight":1},{"id":"K0150","weight":1},{"id":"K0151","weight":1},{"id":"K0219","weight":1},{"id":"K0220","weight":1},{"id":"K0224","weight":1},{"id":"K0264","weight":1},{"id":"K0265","weight":1},{"id":"K0302","weight":1},{"id":"K0516","weight":1},{"id":"K0555","weight":1}]','["K0001","K0033","K0034","K0035","K0041","K0042","K0049","K0050","K0051","K0056","K0057","K0060","K0088","K0089","K0099","K0149","K0150","K0151","K0219","K0220","K0224","K0264","K0265","K0302","K0516","K0555"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISTB0405JSJZW","SS0400AC","ISTB0405WLFWJS","ISTB0304WXTX","ISTB0402VFLINUX","ISTB0103ASM","SS0200SBAQ","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","NS0101AQYSVD2","NS0101AQYSVD3","ISTB0101DZDL","SS0300KKXJS","ISTB0102EDA"]',50,'[]');

SET IDENTITY_INSERT "init_rtks_role" OFF;
SET IDENTITY_INSERT "init_rtks_role_skill_relation" ON;
SET IDENTITY_INSERT "init_rtks_role_skill_relation" OFF;
SET IDENTITY_INSERT "init_rtks_task" ON;
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(42,'T0023','描述和分析网络流量以识别网络资源的异常活动和潜在威胁。','2021-10-14 15:41:45.000000000','2021-10-14 15:41:45.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(46,'T0024','收集并维护满足系统网络空间安全报告所需的数据。','2021-10-14 15:48:20.000000000','2021-10-14 15:48:20.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(47,'T0027','对日志文件、证据和其他信息进行分析，以确定识别对网络入侵者的最佳方法。','2021-10-14 15:48:31.000000000','2021-10-14 15:48:31.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(48,'T0028','进行和/或支持对网络资产的授权渗透测试。','2021-10-14 15:48:45.000000000','2021-10-14 15:48:45.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(49,'T0120','识别，收集，并抓住文件或物理证据，包括数字媒体和与网络入侵事件，调查 和操作相关的日志。','2021-10-14 15:49:10.000000000','2021-10-14 15:49:10.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(50,'T0161','执行日志文件的分析，从各种来源(例如，个别主机日志，网络流量日志，防 火墙日志和入侵检测系统[ IDS ]日志)，以确定可能对网络安全的威胁。','2021-10-14 15:49:22.000000000','2021-10-14 15:49:22.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(51,'T0165','执行动态分析，启动一个驱动器的“图像”(不必有原驱动器)，在本机环境 查看用户可能已经看到的入侵。','2021-10-14 15:49:37.000000000','2021-10-14 15:49:37.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(52,'T0166','对企业内部各种来源收集的信息执行事件相关性，以获得态势感知，并确定所 观察到的攻击的有效性。','2021-10-14 15:49:52.000000000','2021-10-14 15:49:52.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(53,'T0175','执行实时网络空间防御事件处理(例如，法庭收集，入侵相关和跟踪，威胁分析和直接系统补救)任务，以支持可部署的事件响应小组(IRT)。','2021-10-14 15:50:02.000000000','2021-10-14 15:50:02.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(54,'T0180','在专门的网络空间防御应用和系统(例如，防病毒，审计和修复)或虚拟专用 网络(VPN)设备上执行系统管理，包括安装，配置，维护，备份和恢复。','2021-10-14 15:50:15.000000000','2021-10-14 15:50:15.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(55,'T0181','当应用程序或系统发生重大变化时执行风险分析(例如，威胁，漏洞和事件概 率)。','2021-10-14 15:50:29.000000000','2021-10-14 15:50:29.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(56,'T0198','提供与网络空间防御做法相关的网络事件和活动的每日总结报告。','2021-10-14 15:50:42.000000000','2021-10-14 15:50:42.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(57,'T0214','接收和分析企业内各种来源的网络警报，并确定此类警报的可能原因。','2021-10-14 15:50:53.000000000','2021-10-14 15:50:53.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(58,'T0229','发现网络空间安全事件或漏洞时，监督或管理保护或纠正措施。','2021-10-14 15:51:05.000000000','2021-10-14 15:51:05.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(59,'T0240','使用网络监控工具捕获和分析与恶意活动相关的网络流量。','2021-10-14 15:51:15.000000000','2021-10-14 15:51:15.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(60,'T0266','根据新的或更新的应用程序执行渗透测试。','2021-10-14 15:51:29.000000000','2021-10-14 15:51:29.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(61,'T0285','在数字介质上执行病毒扫描','2021-10-14 15:51:40.000000000','2021-10-14 15:51:40.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(62,'T0295','针对网络流量使用包分析工具验证入侵检测系统(IDS)警报。','2021-10-14 15:51:51.000000000','2021-10-14 15:51:51.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(63,'T0297','基于网络流量识别网络设备的应用程序和操作系统。','2021-10-14 15:52:02.000000000','2021-10-14 15:52:02.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(64,'T0299','识别网络映射和操作系统(OS)指纹识别活动。','2021-10-14 15:52:13.000000000','2021-10-14 15:52:13.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(65,'T0310','协助建立可以在网络空间防御网络工具上实施的签名，以响应 NE 或飞地内的 新的或观察到的威胁。','2021-10-14 15:52:23.000000000','2021-10-14 15:52:23.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(66,'T0360','确定威胁的程度，并建议减轻风险的行动方案或对策。','2021-10-14 15:52:34.000000000','2021-10-14 15:52:34.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(67,'T0397','执行 Windows 注册表分析。','2021-10-14 15:52:45.000000000','2021-10-14 15:52:45.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(68,'T0423','分析计算机生成的威胁用于反情报或犯罪活动。','2021-10-14 15:52:55.000000000','2021-10-14 15:52:55.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(69,'T0430','收集和保存用于起诉计算机犯罪的证据。','2021-10-14 15:53:08.000000000','2021-10-14 15:53:08.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(70,'T0432','收集和分析入侵工件(例如，源代码，恶意软件和系统配置)，并使用发现的 数据来减少企业内潜在的网络防御事故。','2021-10-14 15:53:18.000000000','2021-10-14 15:53:18.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(71,'T0433','对日志文件，证据和其他信息进行分析，以确定识别网络入侵或其他犯罪行为 的最佳方法。','2021-10-14 15:53:31.000000000','2021-10-14 15:53:31.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(72,'T0518','进行安全审查，识别架构中的安全漏洞。','2021-10-14 15:53:42.000000000','2021-10-14 15:53:42.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(73,'T0565','分析收到的搜集请求','2021-10-14 15:53:51.000000000','2021-10-14 15:53:51.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(74,'T0570','利用已经被授权的网络空间能力，来访问目标网络','2021-10-14 15:54:03.000000000','2021-10-14 15:54:03.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(75,'T0572','进行网络空间收集、环境准备和具备职业能力，让新的利用和/或持续的收集操 作或可支持客户需求的东西(可以运作)','2021-10-14 15:54:15.000000000','2021-10-14 15:54:15.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(76,'T0593','做威胁和/或目标当前情况的简报','2021-10-14 15:54:25.000000000','2021-10-14 15:54:25.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(77,'T0605','对在组织结构搜集目标的搜集管理活动中获得的经验教训进行汇总','2021-10-14 15:54:34.000000000','2021-10-14 15:54:34.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(78,'T0606','对情报的所有来源数据或特定目标的漏洞价值数据进行汇总、集成和/或解释。','2021-10-14 15:54:44.000000000','2021-10-14 15:54:44.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(79,'T0609','对无线计算机和数字网络进行访问授权','2021-10-14 15:54:53.000000000','2021-10-14 15:54:53.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(80,'T0610','对无线计算机和数字网络进行(信息)收集和处理','2021-10-14 15:55:03.000000000','2021-10-14 15:55:03.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(81,'T0616','对网络中的系统进行网络探测和漏洞分析','2021-10-14 15:55:12.000000000','2021-10-14 15:55:12.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(82,'T0686','识别威胁漏洞','2021-10-14 15:55:23.000000000','2021-10-14 15:55:23.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(83,'T0687','识别蓝牙漏洞威胁','2021-10-14 15:55:34.000000000','2021-10-14 15:55:34.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(84,'T0696','使用各种方法或工具，渗透利用网络设备、安全设备和/或终端设备或环境。','2021-10-14 15:56:38.000000000','2021-10-14 15:56:38.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(85,'T0703','收集和分析数据(比如有效性措施)确定有效性，并提供后续活动的报告。','2021-10-14 15:56:51.000000000','2021-10-14 15:56:51.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(86,'T0705','结合情报获取和反情报窃取(情况)支持计划制定','2021-10-14 15:57:02.000000000','2021-10-14 15:57:02.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(87,'T0708','识别威胁的手段和方法','2021-10-14 15:57:13.000000000','2021-10-14 15:57:13.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(88,'T0710','识别、评估威胁的关键能力、要求和漏洞','2021-10-14 15:57:24.000000000','2021-10-14 15:57:24.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(89,'T0715','确定收集的差距，和针对目标潜在的收集策略。','2021-10-14 15:57:33.000000000','2021-10-14 15:57:33.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(90,'T0718','识别情报的差距和不足','2021-10-14 15:57:43.000000000','2021-10-14 15:57:43.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(91,'T0719','识别网络空间情报差距和不足','2021-10-14 15:57:54.000000000','2021-10-14 15:57:54.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(92,'T0845','识别网络威胁的策略和方法。','2021-10-14 15:58:06.000000000','2021-10-14 15:58:06.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(93,'T0848','向搜集管理者提供反馈，以加强未来收集和分析。','2021-10-14 15:58:19.000000000','2021-10-14 15:58:19.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(94,'T0048','创建一个可靠的、重复的证据取证(即法医图像)，确保原始证据不会被无意 修改，用于数据的恢复和分析过程。包括但不限于硬盘、软盘、光盘、掌上电 脑、手机、GPS、和所有的磁带格式文件。','2021-10-14 15:59:38.000000000','2021-10-14 15:59:38.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(95,'T0172','执行实时取证分析(例如，将 Helix 与 LiveView 结合使用)。','2021-10-14 15:59:50.000000000','2021-10-14 15:59:50.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(96,'T0216','识别并准确地报告指示特定操作系统的取证工件。','2021-10-14 16:00:02.000000000','2021-10-14 16:00:02.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(97,'T0238','使用数据克隆技术(例如，取证工具包 [FTK]，Foremost)提取数据。','2021-10-14 16:00:12.000000000','2021-10-14 16:00:12.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(98,'T0286','执行文件系统取证分析。','2021-10-14 16:00:26.000000000','2021-10-14 16:00:26.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(99,'T0289','部署的取证工具包以在必要时支持操作。','2021-10-14 16:00:35.000000000','2021-10-14 16:00:35.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(100,'T0532','为恢复潜在相关信息，对取证图像和其他数据源(比如易失数据)进行审查。','2021-10-14 16:00:47.000000000','2021-10-14 16:00:47.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(101,'T0077','开发安全代码和错误处理。','2021-10-14 16:01:06.000000000','2021-10-14 16:01:06.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(102,'T0176','执行安全编程和识别代码中的潜在缺陷以减少漏洞。','2021-10-14 16:01:15.000000000','2021-10-14 16:01:15.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(103,'T0179','执行静态媒介分析。','2021-10-14 16:01:26.000000000','2021-10-14 16:01:26.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(104,'T0182','执行第 1，2，和 3 层恶意软件分析。','2021-10-14 16:01:39.000000000','2021-10-14 16:01:39.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(105,'T0260','分析确定的恶意活动，以确定利用的弱点，利用方法，对系统和信息的影响。','2021-10-14 16:01:49.000000000','2021-10-14 16:01:49.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(107,'T0287','执行静态分析以安装驱动器的“映像”(不必具有原始驱动器)。','2021-10-14 16:02:14.000000000','2021-10-14 16:02:14.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(108,'T0288','执行静态恶意软件分析。','2021-10-14 16:02:27.000000000','2021-10-14 16:02:27.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(109,'T0296','隔离并删除恶意软件。','2021-10-14 16:02:36.000000000','2021-10-14 16:02:36.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(110,'T0385','基于数据分析和发现，向关键利益相关者提供可行的建议。','2021-10-14 16:02:45.000000000','2021-10-14 16:02:45.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(111,'T0396','根据分析师的目标使用适当的工具处理图像。','2021-10-14 16:02:54.000000000','2021-10-14 16:02:54.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(113,'T0398','在通过动态分析识别入侵之后，对运行的系统执行文件和注册表监视。','2021-10-14 16:03:18.000000000','2021-10-14 16:03:18.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(114,'T0403','在 Windows 和 UNIX 系统(例如，执行以下任务:解析大型数据文件，自动执 行手动任务以及获取/处理远程数据)中读取，解释，写入，修改和执行简单脚 本(例如 PERL，VBS) 。','2021-10-14 16:03:29.000000000','2021-10-14 16:03:29.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(115,'T0411','识别和/或开发逆向工程工具以增强功能和检测漏洞。','2021-10-14 16:03:44.000000000','2021-10-14 16:03:44.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(116,'T0014','使用安全代码文档。','2021-10-14 16:05:06.000000000','2021-10-14 16:05:06.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(117,'T0016','运用安全性策略以满足系统的安全目标。','2021-10-14 16:05:16.000000000','2021-10-14 16:05:16.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(118,'T0303','在设计和开发安全应用程序时，确定并利用企业级版本控制系统。','2021-10-14 16:05:27.000000000','2021-10-14 16:05:27.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(119,'T0304','实现并将系统开发生命周期(SDLC)方法(例如，IBM 统一开发过程)集成 到开发环境中。','2021-10-14 16:05:41.000000000','2021-10-14 16:05:41.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(120,'T0311','咨询客户关于软件系统设计和维护。','2021-10-14 16:05:51.000000000','2021-10-14 16:05:51.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(121,'T0324','直接软件编程和开发文档。','2021-10-14 16:06:02.000000000','2021-10-14 16:06:02.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(122,'T0327','评估网络基础设施漏洞，以增强正在开发的功能。','2021-10-14 16:06:12.000000000','2021-10-14 16:06:12.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(123,'T0329','遵循软件和系统工程生命周期标准和过程。','2021-10-14 16:06:24.000000000','2021-10-14 16:06:24.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(124,'T0428','分析安全需求和软件需求，以确定在时间和成本限制和安全任务中的设计可行性。','2021-10-14 16:06:36.000000000','2021-10-14 16:06:36.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(125,'T0455','开发软件系统测试和验证程序，编程和文档。','2021-10-14 16:06:47.000000000','2021-10-14 16:06:47.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(126,'T0563','为分析、设计、开发和满足目标要求的能力获取，提供输入项。','2021-10-14 16:06:57.000000000','2021-10-14 16:06:57.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(127,'T0158','参与安全评估和授权过程中的信息安全风险评估。','2021-10-14 16:09:11.000000000','2021-10-14 16:09:11.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(128,'T0159','参与开发或修改计算机环境网络空间安全计划的方案和要求。','2021-10-14 16:09:22.000000000','2021-10-14 16:09:22.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(129,'T0160','修补网络漏洞，确保信息的对外保护。','2021-10-14 16:09:35.000000000','2021-10-14 16:09:35.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(131,'T0162','执行备份和恢复数据库，以确保数据完整性。','2021-10-14 16:10:03.000000000','2021-10-14 19:12:49.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(132,'T0163','执行网络空间防御事件分类，包括确定范围、紧迫性、和潜在的影响;识别特 定的脆弱性;并提出建议，使快速修复。','2021-10-14 16:10:11.000000000','2021-10-14 16:10:11.000000000');
INSERT INTO "init_rtks_task"("id","resource_id","content","create_time","update_time") VALUES(133,'T0164','进行网络空间防御趋势分析和报告。','2021-10-14 16:10:21.000000000','2021-10-14 16:10:21.000000000');

SET IDENTITY_INSERT "init_rtks_task" OFF;
SET IDENTITY_INSERT "local_scene" ON;
SET IDENTITY_INSERT "local_scene" OFF;
SET IDENTITY_INSERT "oj_classroom" ON;
SET IDENTITY_INSERT "oj_classroom" OFF;
SET IDENTITY_INSERT "oj_classroom_group_archive" ON;
SET IDENTITY_INSERT "oj_classroom_group_archive" OFF;
SET IDENTITY_INSERT "oj_classroom_group_info" ON;
SET IDENTITY_INSERT "oj_classroom_group_info" OFF;
SET IDENTITY_INSERT "oj_classroom_sign" ON;
SET IDENTITY_INSERT "oj_classroom_sign" OFF;
SET IDENTITY_INSERT "oj_comment" ON;
SET IDENTITY_INSERT "oj_comment" OFF;
SET IDENTITY_INSERT "oj_comment_like" ON;
SET IDENTITY_INSERT "oj_comment_like" OFF;
SET IDENTITY_INSERT "oj_course" ON;
SET IDENTITY_INSERT "oj_course" OFF;
SET IDENTITY_INSERT "oj_course_category" ON;
SET IDENTITY_INSERT "oj_course_category" OFF;
SET IDENTITY_INSERT "oj_course_lesson" ON;
SET IDENTITY_INSERT "oj_course_lesson" OFF;
SET IDENTITY_INSERT "oj_course_study_history" ON;
SET IDENTITY_INSERT "oj_course_study_history" OFF;
SET IDENTITY_INSERT "oj_lesson_config" ON;
SET IDENTITY_INSERT "oj_lesson_config" OFF;
SET IDENTITY_INSERT "oj_lesson_paper_evaluate" ON;
SET IDENTITY_INSERT "oj_lesson_paper_evaluate" OFF;
SET IDENTITY_INSERT "oj_lesson_record" ON;
SET IDENTITY_INSERT "oj_lesson_record" OFF;
SET IDENTITY_INSERT "oj_local_scene" ON;
SET IDENTITY_INSERT "oj_local_scene" OFF;
SET IDENTITY_INSERT "oj_note" ON;
SET IDENTITY_INSERT "oj_note" OFF;
SET IDENTITY_INSERT "oj_note_evaluate" ON;
SET IDENTITY_INSERT "oj_note_evaluate" OFF;
SET IDENTITY_INSERT "oj_paper" ON;
SET IDENTITY_INSERT "oj_paper" OFF;
SET IDENTITY_INSERT "oj_paper_practice_theory" ON;
SET IDENTITY_INSERT "oj_paper_practice_theory" OFF;
SET IDENTITY_INSERT "oj_practice_category" ON;
SET IDENTITY_INSERT "oj_practice_category" OFF;
SET IDENTITY_INSERT "oj_practice_mistake" ON;
SET IDENTITY_INSERT "oj_practice_mistake" OFF;
SET IDENTITY_INSERT "oj_practice_operation" ON;
SET IDENTITY_INSERT "oj_practice_operation" OFF;
SET IDENTITY_INSERT "oj_practice_operation_ref" ON;
SET IDENTITY_INSERT "oj_practice_operation_ref" OFF;
SET IDENTITY_INSERT "oj_practice_paper" ON;
SET IDENTITY_INSERT "oj_practice_paper" OFF;
SET IDENTITY_INSERT "oj_practice_random_flag" ON;
SET IDENTITY_INSERT "oj_practice_random_flag" OFF;
SET IDENTITY_INSERT "oj_practice_set" ON;
SET IDENTITY_INSERT "oj_practice_set" OFF;
SET IDENTITY_INSERT "oj_practice_solved" ON;
SET IDENTITY_INSERT "oj_practice_solved" OFF;
SET IDENTITY_INSERT "oj_practice_theory" ON;
SET IDENTITY_INSERT "oj_practice_theory" OFF;
SET IDENTITY_INSERT "oj_practice_theory_ref" ON;
SET IDENTITY_INSERT "oj_practice_theory_ref" OFF;
SET IDENTITY_INSERT "oj_resource_collection" ON;
SET IDENTITY_INSERT "oj_resource_collection" OFF;
SET IDENTITY_INSERT "oj_train_plan" ON;
SET IDENTITY_INSERT "oj_train_plan" OFF;
SET IDENTITY_INSERT "radar_label" ON;
SET IDENTITY_INSERT "radar_label" OFF;
SET IDENTITY_INSERT "rtks_ability" ON;
SET IDENTITY_INSERT "rtks_ability" OFF;
SET IDENTITY_INSERT "rtks_attack" ON;
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(1,'T1595','TA0043','主动扫描','Active Scanning');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(2,'T1592','TA0043','收集目标主机信息','Gather Victim Host Information');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(3,'T1589','TA0043','收集目标身份信息','Gather Victim Identity Information');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(4,'T1590','TA0043','收集目标网络信息','Gather Victim Network Information');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(5,'T1591','TA0043','收集目标组织信息','Gather Victim Org Information');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(6,'T1598','TA0043','信息钓鱼','Phishing for Information');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(7,'T1597','TA0043','搜索闭源','Search Closed Sources');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(8,'T1596','TA0043','搜索开放数据库','Search Open Technical Databases');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(9,'T1593','TA0043','搜索开放的网站、域名','Search Open Websites/Domains');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(10,'T1594','TA0043','搜索目标拥有的网站','Search Victim-Owned Websites');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(11,'T1583','TA0042','获取基础架构','Acquire Infrastructure');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(12,'T1586','TA0042','入侵账户','Compromise Accounts');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(13,'T1584','TA0042','破坏基础设施','Compromise Infrastructure');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(14,'T1587','TA0042','开发能力','Develop Capabilities');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(15,'T1585','TA0042','建立账户','Establish Accounts');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(16,'T1588','TA0042','获得能力','Obtain Capabilities');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(17,'T1608','TA0042','伪装能力','Stage Capabilities');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(18,'T1189','TA0001','网站挂马攻击','Drive-by Compromise');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(19,'T1190','TA0001','利用互联网上应用程序的缺陷','Exploit Public-Facing Application');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(20,'T1133','TA0001','外部远程服务','External Remote Services');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(21,'T1200','TA0001','硬件接入','Hardware Additions');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(22,'T1598','TA0001','网络钓鱼','Phishing');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(23,'T1091','TA0001','通过可移动介质进行复制','Replication Through Removable Media');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(24,'T1195','TA0001','供应链攻击','Supply Chain Compromise');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(25,'T1199','TA0001','可信关系','Trusted Relationship');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(26,'T1078','TA0001','有效凭证','Valid Accounts');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(27,'T1059','TA0002','命令行界面','Command and Scripting Interpreter');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(28,'T1609','TA0002','容器管理命令','Container Administration Command');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(29,'T1610','TA0002','部署容器','Deploy Container');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(30,'T1203','TA0002','利用客户端漏洞获取执行权限','Exploitation for Client Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(31,'T1559','TA0002','跨进程通信','Inter-Process Communication');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(32,'T1106','TA0002','通过API执行','Native API');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(33,'T1053','TA0002','计划任务','Scheduled Task/Job');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(34,'T1648','TA0002','云服务器执行','Serverless Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(35,'T1129','TA0002','通过模块加载执行','Shared Modules');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(36,'T1072','TA0002','利用第三方软件部署工具','Software Deployment Tools');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(37,'T1569','TA0002','系统服务','System Services');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(38,'T1204','TA0002','用户执行','User Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(39,'T1047','TA0002','Windows管理规范技术','Windows Management Instrumentation');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(40,'T1098','TA0003','账户修改、监控','Account Manipulation');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(41,'T1197','TA0003','BITS  作业','BITS Jobs');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(42,'T1547','TA0003','启动或登录自动执行','Boot or Logon Autostart Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(43,'T1037','TA0003','登录脚本','Boot or Logon Initialization Scripts');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(44,'T1176','TA0003','浏览器扩展','Browser Extensions');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(45,'T1554','TA0003','攻击客户的软件二进制文件','Compromise Client Software Binary');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(46,'T1136','TA0003','创建账户','Create Account');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(47,'T1543','TA0003','创建或修改系统进程','Create or Modify System Process');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(48,'T1546','TA0003','事件触发执行','Event Triggered Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(49,'T1133','TA0003','外部远程服务','External Remote Services');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(50,'T1574','TA0003','劫持执行流量','Hijack Execution Flow');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(51,'T1525','TA0003','植入容器镜像','Implant Internal Image');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(52,'T1556','TA0003','修改身份验证流程','Modify Authentication Process');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(53,'T1137','TA0003','启动Office应用程序','Office Application Startup');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(54,'T1542','TA0003','预启动操作系统','Pre-OS Boot');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(55,'T1053','TA0003','计划任务','Scheduled Task/Job');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(56,'T1505','TA0003','服务器软件组件','Server Software Component');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(57,'T1205','TA0003','流量特征','Traffic Signaling');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(58,'T1078','TA0003','有效凭证','Valid Accounts');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(59,'T1548','TA0004','滥用提权控制机制','Abuse Elevation Control Mechanism');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(60,'T1134','TA0004','篡改访问令牌','Access Token Manipulation');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(61,'T1547','TA0004','启动或登录自动执行','Boot or Logon Autostart Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(62,'T1037','TA0004','登录脚本','Boot or Logon Initialization Scripts');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(63,'T1543','TA0004','创建或修改系统进程','Create or Modify System Process');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(64,'T1484','TA0004','修改组策略','Domain Policy Modification');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(65,'T1611','TA0004','主机逃逸','Escape to Host');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(66,'T1546','TA0004','事件触发执行','Event Triggered Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(67,'T1068','TA0004','利用漏洞进行权限升级','Exploitation for Privilege Escalation');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(68,'T1574','TA0004','劫持执行流量','Hijack Execution Flow');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(69,'T1055','TA0004','进程注入','Process Injection');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(70,'T1053','TA0004','计划任务','Scheduled Task/Job');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(71,'T1078','TA0004','有效凭证','Valid Accounts');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(72,'T1548','TA0005','滥用提权控制机制','Abuse Elevation Control Mechanism');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(73,'T1134','TA0005','篡改访问令牌','Access Token Manipulation');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(74,'T1197','TA0005','BITS  作业','BITS Jobs');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(75,'T1612','TA0005','在主机上构建镜像','Build Image on Host');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(76,'T1622','TA0005','绕过排错程序','Debugger Evasion');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(77,'T1140','TA0005','反混淆/解码文件或信息','Deobfuscate/Decode Files or Information');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(78,'T1610','TA0005','部署容器','Deploy Container');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(79,'T1006','TA0005','直接访问卷','Direct Volume Access');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(80,'T1484','TA0005','修改组策略','Domain Policy Modification');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(81,'T1480','TA0005','执行防护','Execution Guardrails');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(82,'T1211','TA0005','利用漏洞实现防御绕过','Exploitation for Defense Evasion');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(83,'T1222','TA0005','修改文件或目录权限','File and Directory Permissions Modification');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(84,'T1564','TA0005','隐藏工件','Hide Artifacts');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(85,'T1574','TA0005','劫持执行流量','Hijack Execution Flow');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(86,'T1562','TA0005','破坏防御','Impair Defenses');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(87,'T1070','TA0005','删除主机上的指标','Indicator Removal');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(88,'T1202','TA0005','间接命令执行','Indirect Command Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(89,'T1036','TA0005','伪装','Masquerading');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(90,'T1556','TA0005','修改身份验证流程','Modify Authentication Process');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(91,'T1578','TA0005','修改云计算基础架构','Modify Cloud Compute Infrastructure');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(92,'T1112','TA0005','修改注册表','Modify Registry');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(93,'T1601','TA0005','修改系统镜像','Modify System Image');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(94,'T1599','TA0005','网络边界桥接','Network Boundary Bridging');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(95,'T1027','TA0005','混淆的文件或信息','Obfuscated Files or Information');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(96,'T1647','TA0005','属性列表文件修改','Plist File Modification');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(97,'T1542','TA0005','预启动操作系统','Pre-OS Boot');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(98,'T1055','TA0005','进程注入','Process Injection');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(99,'T1620','TA0005','反射代码加载','Reflective Code Loading');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(100,'T1207','TA0005','域控制器','Rogue Domain Controller');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(101,'T1014','TA0005','恶意软件','Rootkit');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(102,'T1553','TA0005','破坏可信控件','Subvert Trust Controls');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(103,'T1218','TA0005','系统二进制文件代理执行','System Binary Proxy Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(104,'T1216','TA0005','系统脚本代理执行','System Script Proxy Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(105,'T1221','TA0005','模板注入','Template Injection');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(106,'T1205','TA0005','流量特征','Traffic Signaling');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(107,'T1127','TA0005','受信任的开发工具','Trusted Developer Utilities Proxy Execution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(108,'T1535','TA0005','未使用/不支持的云区域','Unused/Unsupported Cloud Regions');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(109,'T1550','TA0005','使用备用身份验证材料','Use Alternate Authentication Material');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(110,'T1078','TA0005','有效凭证','Valid Accounts');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(111,'T1497','TA0005','逃避虚拟化/沙箱检测','Virtualization/Sandbox Evasion');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(112,'T1600','TA0005','弱加密','Weaken Encryption');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(113,'T1220','TA0005','XSL脚本处理','XSL Script Processing');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(114,'T1557','TA0006','中间人','Adversary-in-the-Middle');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(115,'T1110','TA0006','暴力破解','Brute Force');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(116,'T1555','TA0006','密钥库中的凭据','Credentials from Password Stores');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(117,'T1212','TA0006','利用漏洞获取凭证访问的权限','Exploitation for Credential Access');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(118,'T1187','TA0006','强制身份验证','Forced Authentication');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(119,'T1606','TA0006','伪造web凭据','Forge Web Credentials');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(120,'T1056','TA0006','捕获用户输入','Input Capture');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(121,'T1556','TA0006','修改身份验证流程','Modify Authentication Process');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(122,'T1111','TA0006','多因素身份认证拦截','Multi-Factor Authentication Interception');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(123,'T1621','TA0006','多因素身份认证请求','Multi-Factor Authentication Request Generation');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(124,'T1040','TA0006','网络嗅探','Network Sniffing');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(125,'T1003','TA0006','OS凭据转储','OS Credential Dumping');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(126,'T1528','TA0006','窃取应用访问令牌','Steal Application Access Token');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(127,'T1539','TA0006','窃取网络会话Cookie','Steal Web Session Cookie');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(128,'T1649','TA0006','窃取或伪造认证证书','Steal or Forge Authentication Certificates');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(129,'T1558','TA0006','窃取或伪造Kerberos票据','Steal or Forge Kerberos Tickets');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(130,'T1552','TA0006','不安全凭据','Unsecured Credentials');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(131,'T1087','TA0007','账户发现','Account Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(132,'T1010','TA0007','应用程序窗口发现','Application Window Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(133,'T1217','TA0007','发现浏览器书签发','Browser Bookmark Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(134,'T1580','TA0007','云基础架构发现','Cloud Infrastructure Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(135,'T1538','TA0007','云服务仪表盘','Cloud Service Dashboard');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(136,'T1526','TA0007','云服务发现','Cloud Service Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(137,'T1619','TA0007','云存储对象发现','Cloud Storage Object Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(138,'T1613','TA0007','容器与资源发现','Container and Resource Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(139,'T1622','TA0007','绕过排错程序','Debugger Evasion');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(140,'T1482','TA0007','域信任发现','Domain Trust Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(141,'T1083','TA0007','文件与目录发现','File and Directory Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(142,'T1615','TA0007','组策略发现','Group Policy Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(143,'T1046','TA0007','服务发现','Network Service Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(144,'T1135','TA0007','网络共享发现','Network Share Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(145,'T1040','TA0007','网络嗅探','Network Sniffing');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(146,'T1201','TA0007','密码策略发现','Password Policy Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(147,'T1120','TA0007','外围设备发现','Peripheral Device Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(148,'T1069','TA0007','组权限发现','Permission Groups Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(149,'T1057','TA0007','进程发现','Process Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(150,'T1012','TA0007','查询注册表','Query Registry');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(151,'T1018','TA0007','远程系统发现','Remote System Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(152,'T1518','TA0007','软件发现','Software Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(153,'T1082','TA0007','系统信息发现','System Information Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(154,'T1614','TA0007','系统位置发现','System Location Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(155,'T1016','TA0007','系统网络配置发现','System Network Configuration Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(156,'T1049','TA0007','系统网络连接发现','System Network Connections Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(157,'T1033','TA0007','系统所有者/用户发现','System Owner/User Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(158,'T1007','TA0007','系统服务发现','System Service Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(159,'T1124','TA0007','系统时间发现','System Time Discovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(160,'T1497','TA0007','逃避虚拟化/沙箱检测','Virtualization/Sandbox Evasion');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(161,'T1210','TA0008','利用远程服务','Exploitation of Remote Services');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(162,'T1534','TA0008','内部鱼叉式攻击','Internal Spearphishing');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(163,'T1570','TA0008','工具横向转移','Lateral Tool Transfer');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(164,'T1563','TA0008','远程服务会话劫持','Remote Service Session Hijacking');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(165,'T1021','TA0008','远程服务','Remote Services');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(166,'T1091','TA0008','通过可移动介质进行复制','Replication Through Removable Media');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(167,'T1072','TA0008','利用第三方软件部署工具','Software Deployment Tools');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(168,'T1080','TA0008','污染共享内容','Taint Shared Content');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(169,'T1550','TA0008','使用备用身份验证材料','Use Alternate Authentication Material');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(170,'T1557','TA0009','中间人','Adversary-in-the-Middle');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(171,'T1560','TA0009','存档已收集的数据','Archive Collected Data');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(172,'T1123','TA0009','音频捕获','Audio Capture');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(173,'T1119','TA0009','自动收集','Automated Collection');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(174,'T1185','TA0009','浏览器中间人','Browser Session Hijacking');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(175,'T1115','TA0009','剪贴板数据','Clipboard Data');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(176,'T1074','TA0009','暂存数据','Data Staged');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(177,'T1530','TA0009','云数据获取','Data from Cloud Storage');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(178,'T1602','TA0009','配置存储库中的数据','Data from Configuration Repository');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(179,'T1213','TA0009','信息存储库中的数据','Data from Information Repositories');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(180,'T1005','TA0009','从本地系统收集敏感数据','Data from Local System');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(181,'T1039','TA0009','网络共享驱动器中的数据','Data from Network Shared Drive');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(182,'T1025','TA0009','来自可移动介质的数据','Data from Removable Media');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(183,'T1114','TA0009','电子邮件收集','Email Collection');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(184,'T1056','TA0009','捕获用户输入','Input Capture');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(185,'T1113','TA0009','屏幕截图','Screen Capture');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(186,'T1125','TA0009','视频捕获','Video Capture');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(187,'T1071','TA0011','标准应用层协议','Application Layer Protocol');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(188,'T1092','TA0011','通过可移动介质进行通信','Communication Through Removable Media');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(189,'T1132','TA0011','数据编码','Data Encoding');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(190,'T1001','TA0011','数据混淆','Data Obfuscation');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(191,'T1568','TA0011','动态解决方案','Dynamic Resolution');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(192,'T1573','TA0011','加密信道','Encrypted Channel');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(193,'T1008','TA0011','备用通信信道','Fallback Channels');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(194,'T1105','TA0011','远程文件拷贝','Ingress Tool Transfer');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(195,'T1104','TA0011','多阶段通信信道','Multi-Stage Channels');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(196,'T1095','TA0011','标准非应用层协议','Non-Application Layer Protocol');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(197,'T1571','TA0011','非标准端口','Non-Standard Port');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(198,'T1572','TA0011','隧道协议','Protocol Tunneling');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(199,'T1090','TA0011','连接代理','Proxy');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(200,'T1219','TA0011','远程访问工具','Remote Access Software');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(201,'T1205','TA0011','流量特征','Traffic Signaling');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(202,'T1102','TA0011','网络服务','Web Service');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(203,'T1020','TA0010','自动化数据渗出','Automated Exfiltration');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(204,'T1030','TA0010','限制数据传输大小','Data Transfer Size Limits');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(205,'T1048','TA0010','备用协议上的数据渗出','Exfiltration Over Alternative Protocol');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(206,'T1041','TA0010','使用命令与控制信道窃取','Exfiltration Over C2 Channel');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(207,'T1011','TA0010','其他网络介质的数据渗漏','Exfiltration Over Other Network Medium');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(208,'T1052','TA0010','物理介质上的数据渗出','Exfiltration Over Physical Medium');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(209,'T1567','TA0010','通过Web服务进行数据渗出','Exfiltration Over Web Service');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(210,'T1029','TA0010','计划传输','Scheduled Transfer');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(211,'T1537','TA0010','将数据传输到云账户中','Transfer Data to Cloud Account');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(212,'T1531','TA0040','删除账户访问权限','Account Access Removal');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(213,'T1485','TA0040','销毁数据','Data Destruction');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(214,'T1486','TA0040','数据加密','Data Encrypted for Impact');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(215,'T1565','TA0040','数据修改、监控','Data Manipulation');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(216,'T1491','TA0040','篡改','Defacement');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(217,'T1561','TA0040','删除磁盘数据','Disk Wipe');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(218,'T1499','TA0040','端点拒绝服务','Endpoint Denial of Service');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(219,'T1495','TA0040','固件损坏','Firmware Corruption');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(220,'T1490','TA0040','禁用系统恢复','Inhibit System Recovery');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(221,'T1498','TA0040','网络拒绝服务','Network Denial of Service');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(222,'T1496','TA0040','资源劫持','Resource Hijacking');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(223,'T1489','TA0040','服务停止','Service Stop');
INSERT INTO "rtks_attack"("id","resource_id","category_id","name_zh","name_en") VALUES(224,'T1529','TA0040','系统关闭/重启','System Shutdown/Reboot');

SET IDENTITY_INSERT "rtks_attack" OFF;
SET IDENTITY_INSERT "rtks_attack_category" ON;
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(1,'TA0043','侦察','Reconnaissance');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(2,'TA0042','资源开发','Resource Development');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(3,'TA0001','初始访问','Initial Access');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(4,'TA0002','执行','Execution');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(5,'TA0003','持久化','Persistence');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(6,'TA0004','特权提升','Privilege Escalation');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(7,'TA0005','逃避防御','Defense Evasion');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(8,'TA0006','凭据访问','Credential Access');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(9,'TA0007','发现','Discovery');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(10,'TA0008','横向移动','Lateral Movement');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(11,'TA0009','信息收集','Collection');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(12,'TA0011','命令与控制','Command and Control');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(13,'TA0010','信息盗取','Exfiltration');
INSERT INTO "rtks_attack_category"("id","resource_id","name_zh","name_en") VALUES(14,'TA0040','影响','Impact');

SET IDENTITY_INSERT "rtks_attack_category" OFF;
SET IDENTITY_INSERT "rtks_attack_ics" ON;
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(1,'T0817','TA0108','偷渡式渗透','Drive-by Compromise');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(2,'T0819','TA0108','利用公众应用程序','Exploit Public-Facing Application');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(3,'T0866','TA0108','利用远程服务','Exploitation of Remote Services');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(4,'T0822','TA0108','外部远程服务','External Remote Services');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(5,'T0883','TA0108','互联网开放设备','Internet Accessible Device');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(6,'T0886','TA0108','远程服务','Remote Services');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(7,'T0847','TA0108','移动介质复制','Replication Through Removable Media');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(8,'T0848','TA0108','恶意主机','Rogue Master');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(9,'T0865','TA0108','鱼叉式网络钓鱼附件','Spearphishing Attachment');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(10,'T0862','TA0108','供应链渗透','Supply Chain Compromise');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(11,'T0864','TA0108','临时网络资产','Transient Cyber Asset');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(12,'T0860','TA0108','无线渗透','Wireless Compromise');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(13,'T0858','TA0104','利用特权提升','Change Operating Mode');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(14,'T0807','TA0104','命令行接口','Command-Line Interface');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(15,'T0871','TA0104','API执行','Execution through API');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(16,'T0823','TA0104','用户图形接口','Graphical User Interface');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(17,'T0874','TA0104','篡改','Hooking');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(18,'T0821','TA0104','修改控制器任务','Modify Controller Tasking');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(19,'T0834','TA0104','原生API接口','Native API');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(20,'T0853','TA0104','脚本','Scripting');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(21,'T0863','TA0104','用户执行','User Execution');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(22,'T0891','TA0110','硬编码凭据','Hardcoded Credentials');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(23,'T0889','TA0110','修改程序','Modify Program');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(24,'T0839','TA0110','模块固件','Module Firmware');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(25,'T0873','TA0110','项目文件感染','Project File Infection');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(26,'T0857','TA0110','系统固件','System Firmware');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(27,'T0859','TA0110','有效账户','Valid Accounts');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(28,'T0890','TA0111','利用特权提升','Exploitation for Privilege Escalation');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(29,'T0874','TA0111','篡改','Hooking');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(30,'T0858','TA0103','利用特权提升','Change Operating Mode');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(31,'T0820','TA0103','利用逃避','Exploitation for Evasion');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(32,'T0872','TA0103','主机上的指示移除','Indicator Removal on Host');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(33,'T0849','TA0103','伪装','Masquerading');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(34,'T0851','TA0103','恶意软件','Rootkit');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(35,'T0856','TA0103','欺骗报告消息','Spoof Reporting Message');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(36,'T0840','TA0102','网络连接枚举','Network Connection Enumeration');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(37,'T0842','TA0102','网络嗅探','Network Sniffing');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(38,'T0846','TA0102','远程系统发现','Remote System Discovery');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(39,'T0888','TA0102','远程系统信息发现','Remote System Information Discovery');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(40,'T0887','TA0102','无线嗅探','Wireless Sniffing');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(41,'T0812','TA0109','默认凭据','Default Credentials');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(42,'T0866','TA0109','利用远程服务','Exploitation of Remote Services');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(43,'T0891','TA0109','硬编码凭据','Hardcoded Credentials');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(44,'T0867','TA0109','横向移动工具','Lateral Tool Transfer');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(45,'T0843','TA0109','程序下载','Program Download');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(46,'T0886','TA0109','远程服务','Remote Services');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(47,'T0859','TA0109','有效账户','Valid Accounts');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(48,'T0830','TA0100','中间对手（AiTM）技术','Adversary-in-the-Middle');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(49,'T0802','TA0100','自动收集','Automated Collection');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(50,'T0811','TA0100','信息存储库中的数据','Data from Information Repositories');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(51,'T0868','TA0100','检测操作模式','Detect Operating Mode');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(52,'T0877','TA0100','I/O 镜像','I/O Image');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(53,'T0801','TA0100','监视进程状态','Monitor Process State');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(54,'T0861','TA0100','节点和标签的识别','Point & Tag Identification');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(55,'T0845','TA0100','程序上传','Program Upload');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(56,'T0852','TA0100','屏幕捕获','Screen Capture');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(57,'T0887','TA0100','无线嗅探','Wireless Sniffing');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(58,'T0885','TA0101','常用端口','Commonly Used Port');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(59,'T0884','TA0101','连接代理','Connection Proxy');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(60,'T0869','TA0101','标准应用层协议','Standard Application Layer Protocol');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(61,'T0800','TA0107','激活固件更新模式','Activate Firmware Update Mode');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(62,'T0878','TA0107','报警抑制','Alarm Suppression');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(63,'T0803','TA0107','阻止命令消息','Block Command Message');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(64,'T0804','TA0107','阻止报告消息','Block Reporting Message');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(65,'T0805','TA0107','阻止串行 COM','Block Serial COM');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(66,'T0809','TA0107','数据销毁','Data Destruction');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(67,'T0814','TA0107','拒绝服务','Denial of Service');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(68,'T0816','TA0107','设备重启/关机','Device Restart/Shutdown');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(69,'T0835','TA0107','操作 I/O 镜像','Manipulate I/O Image');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(70,'T0838','TA0107','修改报警设置','Modify Alarm Settings');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(71,'T0851','TA0107','恶意软件','Rootkit');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(72,'T0881','TA0107','服务停止','Service Stop');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(73,'T0857','TA0107','系统固件','System Firmware');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(74,'T0806','TA0106','暴力破解 I/O','Brute Force I/O');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(75,'T0836','TA0106','修改参数','Modify Parameter');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(76,'T0839','TA0106','模块固件','Module Firmware');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(77,'T0856','TA0106','欺骗报告消息','Spoof Reporting Message');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(78,'T0855','TA0106','未经授权的命令消息','Unauthorized Command Message');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(79,'T0879','TA0105','财产损失','Damage to Property');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(80,'T0813','TA0105','拒绝控制','Denial of Control');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(81,'T0815','TA0105','拒绝查看','Denial of View');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(82,'T0826','TA0105','可用性损失','Loss of Availability');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(83,'T0827','TA0105','可控性损失','Loss of Control');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(84,'T0828','TA0105','生产力和收益损失','Loss of Productivity and Revenue');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(85,'T0837','TA0105','保护功能损失','Loss of Protection');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(86,'T0880','TA0105','安全功能损失','Loss of Safety');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(87,'T0829','TA0105','视野损失','Loss of View');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(88,'T0831','TA0105','操作控制','Manipulation of Control');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(89,'T0832','TA0105','操作视野','Manipulation of View');
INSERT INTO "rtks_attack_ics"("id","resource_id","category_id","name_zh","name_en") VALUES(90,'T0882','TA0105','运营信息窃取','Theft of Operational Information');

SET IDENTITY_INSERT "rtks_attack_ics" OFF;
SET IDENTITY_INSERT "rtks_attack_ics_category" ON;
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(1,'TA0108','初始访问','Initial Access');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(2,'TA0104','执行','Execution');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(3,'TA0110','持续危害','Persistence');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(4,'TA0111','权限提升','Privilege Escalation');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(5,'TA0103','规避','Evasion');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(6,'TA0102','发现','Discovery');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(7,'TA0109','横向移动','Lateral Movement');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(8,'TA0100','收集','Collection');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(9,'TA0101','命令与控制','Command and Control');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(10,'TA0107','抑制响应','Inhibit Response Function');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(11,'TA0106','损害过程控制','Impair Process Control');
INSERT INTO "rtks_attack_ics_category"("id","resource_id","name_zh","name_en") VALUES(12,'TA0105','影响','Impact');

SET IDENTITY_INSERT "rtks_attack_ics_category" OFF;
SET IDENTITY_INSERT "rtks_attack_ics_train_relation" ON;
SET IDENTITY_INSERT "rtks_attack_ics_train_relation" OFF;
SET IDENTITY_INSERT "rtks_attack_train_relation" ON;
SET IDENTITY_INSERT "rtks_attack_train_relation" OFF;
SET IDENTITY_INSERT "rtks_course_name" ON;
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(163,'NS0100SEC','网络安全概念',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(164,'NS0300AQXY','网络安全协议',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(165,'ISTB0401JSJZCYL','计算机组成原理',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(166,'ISTB0405JSJWL','计算机网络基础',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(167,'ISTB0405CM','网络配置与管理',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(168,'ISB0500LAWMGT','信息安全管理基础',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(169,'ISB0300WLAQF','信息安全法律基础',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(170,'NS0403GFJSYSJ','网络攻防技术与实践',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(171,'SS0503PWN','二进制漏洞挖掘与利用',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(172,'SS0400AC','访问控制',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(173,'CRYPT07RZ','认证',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(174,'ISC0100WEB','CTF-WEB',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(175,'SS0900YYXX','应用信息系统安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(176,'ISTB0105DSA','数据结构与算法',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(177,'CS0302SJFXSF','数据分析算法',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(178,'CS0201MLADM','基于python语言的机器学习及数据挖掘课程',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(179,'NS0301NSAML','网络安全与机器学习',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(180,'ISTB0103PCXSJ','Python程序设计',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(181,'ISTB0103JS','JavaScript程序设计',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(182,'ISTB0103PHP','PHP程序设计',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(183,'ISTB0103HTML','HTML语言程序设计',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(184,'ISTB0103ASM','汇编语言程序设计',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(185,'ISTB0103CYY','C语言程序设计',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(186,'ISTB0103GOLANG','Golang程序设计',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(187,'ISTB0103VFJSP','JSP基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(188,'ISTB0103VFJAVASE','JavaSE基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(189,'ISTB0103VFHTML','HTML基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(190,'SS1001DZSJQZ','电子数据取证技术',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(191,'CRYPT02FZ','分组密码',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(192,'CRYPT03XL','流（序列）密码',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(193,'CRYPT05GY','公钥密码',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(194,'CRYPT04HASH','哈希（Hash）函数',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(195,'ISC0100CRYPTO','CTF-CRYPTO',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(196,'CRYPT09GL','密钥管理',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(197,'CRYPT01CRYPTO','密码学概念',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(198,'CRYPT06QM','数字签名',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(199,'CRYPT08XY','密码协议',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(200,'CRYPT09YS','密码学应用与实践',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(201,'ISTB0404DBEXP','数据库实验',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(202,'ISTB0103VFREDIS','Redis基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(203,'SS0600DBSEC','数据库安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(204,'ISTB0404DBGL','数据库概论',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(205,'ISTB0103VFORACLE','Oracle基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(206,'ISTB0405JSJZW','计算机组网技术',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(207,'ISTB0405WLFWJS','通用网络服务架设',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(208,'ISTB0304WXTX','无线通信',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(209,'ISTB0402VFLINUX','Linux基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(210,'SS0700SSEC','软件安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(211,'CS0400NRGK','内容管控',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(212,'NS0200IDSA','入侵检测与态势感知',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(213,'ISB0200INFOMATH','信息安全数学基础',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(214,'ISTB0104MATH','离散数学',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(215,'ISTB0301SSCI','信号与系统',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(216,'SS0200SBAQ','系统设备安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(217,'NS0101AQYSVD2','信息安全技术知识科普',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(218,'SS0501CZXTAQ','操作系统安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(219,'SS0503LINUX','Linux系统安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(220,'SS0502WIN','Windows系统安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(221,'ISTB0103WLBC','网络编程',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(222,'ISTB0103VFMYSQL','Mysql基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(223,'NS0504WEBAQ','Web安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(224,'NS0501WEBUG','WEB安全专题训练（WeBug）',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(225,'NS0503DVWA','WEB安全专题训练（DVWA）',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(226,'NS0505WEBSZ','Web安全实战分析',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(227,'NS0503WEB','WEB安全专题训练（WebGoat）',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(228,'SS1100ANDROID','Android安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(229,'ISTB0103VFBOOTSTRAP','BootStrap基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(230,'ISTB0402CZXTYL','操作系统原理',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(231,'ISTB0302TXYL','通信原理',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(232,'ISTB0303JCBM','纠错编码',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(233,'CS0307DJYSJ','基于大数据的SOC平台的搭建与实践',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(234,'ISC0102XMAN17','XMAN-2017-专题',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(235,'ISC0102XMAN16','XMAN-2016-专题',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(236,'NS0600WXWL','无线网络安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(237,'ISC0100RE','CTF-REVERSE',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(238,'NS0101AQYS','网络安全意识',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(239,'NS0101AQYSVD1','安全意识基础',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(240,'SS1201ICS','工业控制系统安全',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(241,'ISC0101TOOLS','CTF基础知识与常用工具',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(242,'CS0303SJKSH','Python数据可视化',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(243,'CS0304SJFXCL','基于python语言的数据处理分析',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(244,'CS0305HADOOP','Hadoop大数据架构',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(245,'CS0306BIGDATA','HCIE-BigData',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(246,'ISTB0103JCXSJ','Java程序设计',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(247,'ISTB0103VFSERVLET','Servlet基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(248,'NS0101AQYSVD3','安全管理意识',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(249,'ISTB0101DZDL','电子电路基础',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(250,'SS0300KKXJS','系统可靠性技术',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(251,'ISTB0102EDA','EDA技术基础',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(252,'SS0100AQGN','系统安全概念',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(253,'CS0501XXYC','多媒体信息隐藏',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(254,'CS0600YSBH','隐私保护',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(255,'ISC0100MISC','CTF-MISC',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(256,'ISTB0403BYXT','编译系统',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(257,'CS0200WLSJHQ','网络数据获取',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(258,'ISTB0103VFJQUERY','Jquery基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(259,'ISTB0103VFJAVASCRIPT','JS基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(260,'NS0400FH','网络安全防护',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(261,'ISTB0106INFT','信息论',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(262,'ISB0100XXAQ','信息安全概述',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(263,'NS0101AQYSVD4','个人信息安全意识',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(264,'NS0101AQYSVD6','信息安全案例分析',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(265,'SS0801TCB','可信计算',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(266,'ISC0100PWN','CTF-PWN',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(267,'ISTB0103VFCSS','CSS基础入门',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(268,'ISTB0405VT','虚拟化技术',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);
INSERT INTO "rtks_course_name"("id","resource_id","name","description","difficulty","lesson_count","e_book","course_num","exercise_count","theory_count","direction","direction_name","interview") VALUES(269,'NS0101AQYSVD5','涉密单位保密意识',null,0,0,0,null,null,null,'4d6dcb8537f93a8d6fa9d98d9d07c360','教指委体系',null);

SET IDENTITY_INSERT "rtks_course_name" OFF;
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0001',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0300AQXY','K0001',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0401JSJZCYL','K0001',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJWL','K0001',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0001',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISB0500LAWMGT','K0002',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISB0300WLAQF','K0003',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0403GFJSYSJ','K0005',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503PWN','K0005',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503PWN','K0006',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0400AC','K0007',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0007',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100WEB','K0009',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0900YYXX','K0009',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0105DSA','K0014',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0105DSA','K0015',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0302SJFXSF','K0015',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0201MLADM','K0015',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0301NSAML','K0015',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PCXSJ','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103JS','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PHP','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103HTML','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103ASM','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103CYY','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103GOLANG','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJSP','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJAVASE','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFHTML','K0016',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0017',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT02FZ','K0018',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT03XL','K0018',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT05GY','K0018',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT04HASH','K0018',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100CRYPTO','K0018',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT09GL','K0019',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT01CRYPTO','K0019',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT06QM','K0019',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0019',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT08XY','K0019',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT09YS','K0019',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100CRYPTO','K0019',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBEXP','K0023',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0023',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0600DBSEC','K0023',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBGL','K0023',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFORACLE','K0023',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0024',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0600DBSEC','K0024',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBGL','K0024',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFORACLE','K0024',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJZW','K0033',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0400AC','K0033',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405WLFWJS','K0034',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0034',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0035',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0700SSEC','K0039',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0900YYXX','K0039',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0044',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0046',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0200IDSA','K0046',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103ASM','K0051',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISB0200INFOMATH','K0052',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0104MATH','K0052',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0301SSCI','K0052',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0200SBAQ','K0057',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD2','K0059',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0501CZXTAQ','K0060',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503LINUX','K0060',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0502WIN','K0060',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0062',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103WLBC','K0068',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0069',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFMYSQL','K0069',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0504WEBAQ','K0070',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0501WEBUG','K0070',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0503DVWA','K0070',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0505WEBSZ','K0070',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0503WEB','K0070',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0501CZXTAQ','K0077 ',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503LINUX','K0077 ',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0502WIN','K0077 ',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1100ANDROID','K0080',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0504WEBAQ','K0085',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0501WEBUG','K0085',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100WEB','K0085',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0099',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0099',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFBOOTSTRAP','K0105',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402CZXTYL','K0109',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0112',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0301SSCI','K0113',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0302TXYL','K0113',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0303JCBM','K0113',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0113',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0133',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0134',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0307DJYSJ','K0145',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0102XMAN17','K0147',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0102XMAN16','K0147',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0501CZXTAQ','K0167',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503LINUX','K0167',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0502WIN','K0167',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0600WXWL','K0174',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0300AQXY','K0174',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0401JSJZCYL','K0174',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0174',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100RE','K0175',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0300AQXY','K0179',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0179',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYS','K0179',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD1','K0179',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1201ICS','K0179',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405WLFWJS','K0179',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0183',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100RE','K0183',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0185',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0202',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0501CZXTAQ','K0205',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503LINUX','K0205',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0502WIN','K0205',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0221',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0224',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0303SJKSH','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0304SJFXCL','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0201MLADM','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0305HADOOP','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0306BIGDATA','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0301NSAML','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PCXSJ','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103JCXSJ','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJAVASE','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFMYSQL','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFSERVLET','K0236',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD2','K0264',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD3','K0264',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0101DZDL','K0265',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0300KKXJS','K0265',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0102EDA','K0265',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402CZXTYL','K0271',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT09GL','K0277',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0100AQGN','K0290',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0301',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0501XXYC','K0305',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0600YSBH','K0315',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0200IDSA','K0324',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0331',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0332',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0334',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0336',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0339',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100MISC','K0339',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0342',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0345',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0303SJKSH','K0352',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0501XXYC','K0356',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0356',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0101TOOLS','K0360',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0364',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103WLBC','K0372',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0403BYXT','K0372',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0200WLSJHQ','K0388',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJZW','K0388',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0200SBAQ','K0393',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0394',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBGL','K0394',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0401JSJZCYL','K0395',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402CZXTYL','K0395',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJWL','K0395',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0395',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103CYY','K0396',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PCXSJ','K0396',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103JCXSJ','K0396',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103GOLANG','K0396',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103PHP','K0396',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJSP','K0396',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJAVASE','K0396',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0397',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103HTML','K0398',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJQUERY','K0398',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFBOOTSTRAP','K0398',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFHTML','K0398',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFJAVASCRIPT','K0398',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0403GFJSYSJ','K0408',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0400FH','K0408',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFREDIS','K0420',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0404DBGL','K0420',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS1001DZSJQZ','K0433',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0435',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0452',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0106INFT','K0454',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISB0100XXAQ','K0455',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD4','K0455',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD6','K0455',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0302TXYL','K0459',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0459',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0801TCB','K0469',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT06QM','K0472',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0200IDSA','K0472',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0400NRGK','K0473',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISC0100PWN','K0481',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0503PWN','K0481',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0487',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0487',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0488',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0400AC','K0488',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405JSJZW','K0493',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('SS0400AC','K0537',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0537',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405CM','K0555',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CRYPT07RZ','K0561',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0100SEC','K0561',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0302TXYL','K0564',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0303JCBM','K0564',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0564',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0304WXTX','K0565',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('CS0304SJFXCL','K0578',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0103VFCSS','K0578',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0402VFLINUX','K0608',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405VT','K0609',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('ISTB0405VT','K0610',1,1);
INSERT INTO "rtks_course_related"("resource_id","related_id","type","weight") VALUES('NS0101AQYSVD5','K0612',1,1);

SET IDENTITY_INSERT "rtks_engage" ON;
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(1,'SAC0004','SGO0001','SAP0001','网络威胁情报','Cyber Threat Intelligence');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(2,'SAC0012','SGO0001','SAP0001','参与环境','Engagement Environment');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(3,'SAC0005','SGO0001','SAP0001','选通标准','Gating Criteria');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(4,'SAC0001','SGO0001','SAP0001','作战目标','Operational Objective');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(5,'SAC0002','SGO0001','SAP0001','角色创建','Persona Creation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(6,'SAC0003','SGO0001','SAP0001','故事板','Storyboarding');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(7,'SAC0009','SGO0001','SAP0001','威胁模型','Threat Model');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(8,'EAC0001','EGO0001','EAP0001','API监控','API Monitoring');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(9,'EAC0002','EGO0001','EAP0001','网络监控','Network Monitoring');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(10,'EAC0014','EGO0001','EAP0001','软件操作','Software Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(11,'EAC0003','EGO0001','EAP0001','系统活动监控','System Activity Monitoring');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(12,'EAC0023','EGO0001','EAP0002','引入漏洞','Introduced Vulnerabilities');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(13,'EAC0005','EGO0001','EAP0002','诱饵','Lures');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(14,'EAC0013','EGO0001','EAP0002','引爆恶意软件','Malware Detonation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(15,'EAC0004','EGO0001','EAP0002','网络分析','Network Analysis');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(16,'EAC0019','EGO0002','EAP0003','基线','Baseline');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(17,'EAC0017','EGO0002','EAP0003','硬件操作','Hardware Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(18,'EAC0020','EGO0002','EAP0003','隔离','Isolation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(19,'EAC0016','EGO0002','EAP0003','网络操纵','Network Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(20,'EAC0018','EGO0002','EAP0003','安全控制','Security Controls');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(21,'EAC0021','EGO0002','EAP0004','攻击向量转移','Attack Vector Migration');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(22,'EAC0009','EGO0002','EAP0004','邮件管理','Email Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(23,'EAC0023','EGO0002','EAP0004','引入漏洞','Introduced Vulnerabilities');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(24,'EAC0005','EGO0002','EAP0004','诱饵','Lures');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(25,'EAC0013','EGO0002','EAP0004','引爆恶意软件','Malware Detonation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(26,'EAC0016','EGO0002','EAP0004','网络操纵','Network Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(27,'EAC0010','EGO0002','EAP0004','外设管理','Peripheral Management');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(28,'EAC0018','EGO0002','EAP0004','安全控制','Security Controls');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(29,'EAC0014','EGO0002','EAP0004','软件操作','Software Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(30,'EAC0020','EGO0002','EAP0005','隔离','Isolation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(31,'EAC0005','EGO0002','EAP0005','诱饵','Lures');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(32,'EAC0016','EGO0002','EAP0005','网络操纵','Network Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(33,'EAC0014','EGO0002','EAP0005','软件操作','Software Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(34,'EAC0006','EGO0003','EAP0006','应用多样性','Application Diversity');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(35,'EAC0022','EGO0003','EAP0006','工件多样性','Artifact Diversity');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(36,'EAC0008','EGO0003','EAP0006','磨合','Burn-In');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(37,'EAC0009','EGO0003','EAP0006','邮件管理','Email Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(38,'EAC0015','EGO0003','EAP0006','信息处理','Information Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(39,'EAC0007','EGO0003','EAP0006','网络多样性','Network Diversity');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(40,'EAC0010','EGO0003','EAP0006','外设管理','Peripheral Management');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(41,'EAC0011','EGO0003','EAP0006','杂项信息','Pocket Litter');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(42,'EAC0006','EGO0003','EAP0007','应用多样性','Application Diversity');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(43,'EAC0022','EGO0003','EAP0007','工件多样性','Artifact Diversity');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(44,'EAC0015','EGO0003','EAP0007','信息处理','Information Manipulation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(45,'EAC0023','EGO0003','EAP0007','引入漏洞','Introduced Vulnerabilities');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(46,'EAC0013','EGO0003','EAP0007','引爆恶意软件','Malware Detonation');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(47,'EAC0007','EGO0003','EAP0007','网络多样性','Network Diversity');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(48,'EAC0012','EGO0003','EAP0007','人物角色','Persona');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(49,'SAC0006','SGO0002','SAP0002','行动后审查','After-Action Review');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(50,'SAC0004','SGO0002','SAP0002','网络威胁情报','Cyber Threat Intelligence');
INSERT INTO "rtks_engage"("id","resource_id","category_id","sub_category_id","name_zh","name_en") VALUES(51,'SAC0009','SGO0002','SAP0002','威胁模型','Threat Model');

SET IDENTITY_INSERT "rtks_engage" OFF;
SET IDENTITY_INSERT "rtks_engage_category" ON;
INSERT INTO "rtks_engage_category"("id","resource_id","name_zh","name_en") VALUES(1,'SGO0001','准备','Prepare');
INSERT INTO "rtks_engage_category"("id","resource_id","name_zh","name_en") VALUES(2,'EGO0001','暴露','Expose');
INSERT INTO "rtks_engage_category"("id","resource_id","name_zh","name_en") VALUES(3,'EGO0002','影响','Affect');
INSERT INTO "rtks_engage_category"("id","resource_id","name_zh","name_en") VALUES(4,'EGO0003','引出','Elicit');
INSERT INTO "rtks_engage_category"("id","resource_id","name_zh","name_en") VALUES(5,'SGO0002','理解','Understand');

SET IDENTITY_INSERT "rtks_engage_category" OFF;
SET IDENTITY_INSERT "rtks_engage_sub_category" ON;
INSERT INTO "rtks_engage_sub_category"("id","resource_id","category_id","name_zh","name_en") VALUES(1,'SAP0001','SGO0001','规划','Plan');
INSERT INTO "rtks_engage_sub_category"("id","resource_id","category_id","name_zh","name_en") VALUES(2,'EAP0001','EGO0001','收集','Collect');
INSERT INTO "rtks_engage_sub_category"("id","resource_id","category_id","name_zh","name_en") VALUES(3,'EAP0002','EGO0001','发现','Detect');
INSERT INTO "rtks_engage_sub_category"("id","resource_id","category_id","name_zh","name_en") VALUES(4,'EAP0003','EGO0002','防御','Prevent');
INSERT INTO "rtks_engage_sub_category"("id","resource_id","category_id","name_zh","name_en") VALUES(5,'EAP0004','EGO0002','转移','Direct');
INSERT INTO "rtks_engage_sub_category"("id","resource_id","category_id","name_zh","name_en") VALUES(6,'EAP0005','EGO0002','破坏','Disrupt');
INSERT INTO "rtks_engage_sub_category"("id","resource_id","category_id","name_zh","name_en") VALUES(7,'EAP0006','EGO0003','保证','Reassure');
INSERT INTO "rtks_engage_sub_category"("id","resource_id","category_id","name_zh","name_en") VALUES(8,'EAP0007','EGO0003','激励','Motivate');
INSERT INTO "rtks_engage_sub_category"("id","resource_id","category_id","name_zh","name_en") VALUES(9,'SAP0002','SGO0002','分析','Analyze');

SET IDENTITY_INSERT "rtks_engage_sub_category" OFF;
SET IDENTITY_INSERT "rtks_engage_train_relation" ON;
SET IDENTITY_INSERT "rtks_engage_train_relation" OFF;
SET IDENTITY_INSERT "rtks_knowledge" ON;
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(86,'K0001','13d63fdbe3da0ff264b9647565cfaed5','计算机网络概念和协议，以及网络安全方法论知识','[{"relatedId":"K0001","resourceId":"NS0100SEC","type":1,"weight":1},{"relatedId":"K0001","resourceId":"NS0300AQXY","type":1,"weight":1},{"relatedId":"K0001","resourceId":"ISTB0401JSJZCYL","type":1,"weight":1},{"relatedId":"K0001","resourceId":"ISTB0405JSJWL","type":1,"weight":1},{"relatedId":"K0001","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 15:55:30.000000000','2022-01-07 14:50:43.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(87,'K0002','13d63fdbe3da0ff264b9647565cfaed5','风险管理过程知识(比如评估和风险环境的方法)','[{"relatedId":"K0002","resourceId":"ISB0500LAWMGT","type":1,"weight":1}]','2021-10-14 16:09:45.000000000','2022-01-07 14:51:07.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(90,'K0003','90207603744123196c360addca94cc41','与网络空间安全相关的国家和国际法律、规定、政策和道德要求知识。','[{"relatedId":"K0003","resourceId":"ISB0300WLAQF","type":1,"weight":1}]','2021-10-14 16:25:08.000000000','2022-01-07 14:51:33.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(91,'K0202','0bf2eb74863de071afa24856a266e4c7','应用防火墙概念和功能知识。(例如单点认证/审计/策略执行，恶意内容扫描， PCI 和 PII 中的数据匿名，数据丢失保护扫描，加速密码操作，SSL、REST/JSON 处理等)','[{"relatedId":"K0202","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 16:28:32.000000000','2022-01-10 09:57:05.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(92,'K0004','0bf2eb74863de071afa24856a266e4c7','网络空间安全原则知识','','2021-10-14 16:28:37.000000000','2021-10-14 16:28:37.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(93,'K0005','23f60bb0501dc27b096dd3205683cfa9','网络空间威胁和漏洞知识','[{"relatedId":"K0005","resourceId":"NS0403GFJSYSJ","type":1,"weight":1},{"relatedId":"K0005","resourceId":"SS0503PWN","type":1,"weight":1}]','2021-10-14 16:29:05.000000000','2022-01-07 15:01:32.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(94,'K0205','521e0b5143744d0fff1e528e08c486b5','基本的系统、网络和操作系统加固技术知识','[{"relatedId":"K0205","resourceId":"SS0501CZXTAQ","type":1,"weight":1},{"relatedId":"K0205","resourceId":"SS0503LINUX","type":1,"weight":1},{"relatedId":"K0205","resourceId":"SS0502WIN","type":1,"weight":1}]','2021-10-14 16:29:16.000000000','2022-01-10 09:57:48.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(95,'K0006','0bf2eb74863de071afa24856a266e4c7','网络空间安全漏洞对具体操作影响的知识','[{"relatedId":"K0006","resourceId":"SS0503PWN","type":1,"weight":1}]','2021-10-14 16:29:37.000000000','2022-01-07 15:02:10.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(96,'K0206','521e0b5143744d0fff1e528e08c486b5','道德黑客(白帽)的原则和技术知识','','2021-10-14 16:29:51.000000000','2021-10-14 16:29:51.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(97,'K0007','521e0b5143744d0fff1e528e08c486b5','认证、授权和访问控制的知识','[{"relatedId":"K0007","resourceId":"SS0400AC","type":1,"weight":1},{"relatedId":"K0007","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 16:30:03.000000000','2022-01-07 15:02:48.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(98,'K0214','d78b0449533e94ee751bad6b568ecfb9','风险管理框架评估方法论知识','','2021-10-14 16:30:18.000000000','2021-10-14 16:30:18.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(99,'K0009','0bf2eb74863de071afa24856a266e4c7','应用漏洞的知识','[{"relatedId":"K0009","resourceId":"ISC0100WEB","type":1,"weight":1},{"relatedId":"K0009","resourceId":"SS0900YYXX","type":1,"weight":1}]','2021-10-14 16:30:26.000000000','2022-01-07 15:03:23.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(100,'K0219','521e0b5143744d0fff1e528e08c486b5','局域网和广域网原理知识','','2021-10-14 16:30:48.000000000','2021-10-14 16:30:48.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(101,'K0013','d78b0449533e94ee751bad6b568ecfb9','网络空间防御和漏洞评估工具的知识，包括开源工具和他们的能力','','2021-10-14 16:30:51.000000000','2021-10-14 16:30:51.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(102,'K0221','0bf2eb74863de071afa24856a266e4c7','OSI 模型和底层网络协议知识(比如 TCP/IP)','[{"relatedId":"K0221","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:31:21.000000000','2022-01-10 09:58:41.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(103,'K0024','d78b0449533e94ee751bad6b568ecfb9','数据库系统的知识','[{"relatedId":"K0024","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0024","resourceId":"SS0600DBSEC","type":1,"weight":1},{"relatedId":"K0024","resourceId":"ISTB0404DBGL","type":1,"weight":1},{"relatedId":"K0024","resourceId":"ISTB0103VFORACLE","type":1,"weight":1}]','2021-10-14 16:31:24.000000000','2022-01-10 09:04:49.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(104,'K0222','90207603744123196c360addca94cc41','网络空间防御活动相关的法律、法定权利、限制和规定知识。','','2021-10-14 16:31:51.000000000','2021-10-14 16:31:51.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(105,'K0033','521e0b5143744d0fff1e528e08c486b5','主机/网络访问控制机制的知识(比如访问控制列表)','[{"relatedId":"K0033","resourceId":"ISTB0405JSJZW","type":1,"weight":1},{"relatedId":"K0033","resourceId":"SS0400AC","type":1,"weight":1}]','2021-10-14 16:31:51.000000000','2022-01-10 09:05:29.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(106,'K0037','8d5fe8f769c936b86c3092aab0a80e7c','安全评估和授权流程知识','','2021-10-14 16:32:26.000000000','2021-10-14 16:32:26.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(107,'K0224','90207603744123196c360addca94cc41','Unix/Linux 和/或 Windows 操作系统的系统管理概念知识','[{"relatedId":"K0224","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 16:32:28.000000000','2022-01-10 09:59:08.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(108,'K0040','0bf2eb74863de071afa24856a266e4c7','从警报、建议、勘误表和公告中了解已知漏洞','','2021-10-14 16:32:48.000000000','2021-10-14 16:32:48.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(109,'K0225','13d63fdbe3da0ff264b9647565cfaed5','部署在 CC/S/A 上的常用网络协议和服务知识','','2021-10-14 16:32:52.000000000','2021-10-14 16:32:52.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(110,'K0041','0bf2eb74863de071afa24856a266e4c7','事件分类、事件响应和响应时间轴的知识','','2021-10-14 16:33:14.000000000','2021-10-14 16:33:14.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(111,'K0230','521e0b5143744d0fff1e528e08c486b5','服务模型和事件响应的可能限制的知识','','2021-10-14 16:33:15.000000000','2021-10-14 16:33:15.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(112,'K0236','d78b0449533e94ee751bad6b568ecfb9','了解如何利用 Hadoop，java、python，SQL，Hive，PIG 来探索数据。','[{"relatedId":"K0236","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0303SJKSH","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0304SJFXCL","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0201MLADM","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0305HADOOP","type":1,"weight":1},{"relatedId":"K0236","resourceId":"CS0306BIGDATA","type":1,"weight":1},{"relatedId":"K0236","resourceId":"NS0301NSAML","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103PCXSJ","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103JCXSJ","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103VFJAVASE","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103VFMYSQL","type":1,"weight":1},{"relatedId":"K0236","resourceId":"ISTB0103VFSERVLET","type":1,"weight":1}]','2021-10-14 16:33:40.000000000','2022-01-10 10:01:22.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(113,'K0042','d78b0449533e94ee751bad6b568ecfb9','事件响应和处理方法的知识','','2021-10-14 16:33:41.000000000','2021-10-14 16:33:41.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(114,'K0271','8d5fe8f769c936b86c3092aab0a80e7c','操作系统结构和内部知识(比如进程管理、目录结构、已安装应用)','[{"relatedId":"K0271","resourceId":"ISTB0402CZXTYL","type":1,"weight":1}]','2021-10-14 16:34:07.000000000','2022-01-10 10:03:58.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(115,'K0044','0bf2eb74863de071afa24856a266e4c7','网络空间安全原则和组织机构级需求知识(机密性、完整性、可用性、认证授 权和不可抵赖性相关知识)','[{"relatedId":"K0044","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 16:34:08.000000000','2022-01-10 09:25:12.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(116,'K0046','521e0b5143744d0fff1e528e08c486b5','通过入侵检测技术来检测主机和网络入侵的相关方法和技术知识。','[{"relatedId":"K0046","resourceId":"CS0400NRGK","type":1,"weight":1},{"relatedId":"K0046","resourceId":"NS0200IDSA","type":1,"weight":1}]','2021-10-14 16:34:36.000000000','2022-01-10 09:25:54.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(117,'K0273','23f60bb0501dc27b096dd3205683cfa9','入侵链(Kill Chain)的一般知识(比如踩点扫描、枚举猜测、获取访问权限、 特权提升、维持访问、网络渗透、轨迹隐藏)','','2021-10-14 16:34:36.000000000','2021-10-14 16:34:36.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(118,'K0277','13d63fdbe3da0ff264b9647565cfaed5','当前和新兴的数据加密(比如列和表空间加密、文件和磁盘加密)、数据内置 安全特性(包括内置密钥管理特性)的知识','[{"relatedId":"K0277","resourceId":"CRYPT09GL","type":1,"weight":1}]','2021-10-14 16:35:05.000000000','2022-01-10 10:04:18.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(119,'K0049','8d5fe8f769c936b86c3092aab0a80e7c','IT 安全原理和方法知识(比如防火墙、DMZ 区和加密)','','2021-10-14 16:35:07.000000000','2021-10-14 16:35:07.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(120,'K0290','90207603744123196c360addca94cc41','系统安全测试和评估方法的知识','[{"relatedId":"K0290","resourceId":"SS0100AQGN","type":1,"weight":1}]','2021-10-14 16:35:30.000000000','2022-01-10 10:04:40.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(121,'K0058','521e0b5143744d0fff1e528e08c486b5','网络流量分析方法的知识','','2021-10-14 16:35:46.000000000','2021-10-14 16:35:46.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(122,'K0292','d78b0449533e94ee751bad6b568ecfb9','针对突发事件、问题和大事件管理的操作和处理流程知识','','2021-10-14 16:35:56.000000000','2021-10-14 16:35:56.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(123,'K0059','521e0b5143744d0fff1e528e08c486b5','新兴的 IT 和网络空间安全技术的知识','[{"relatedId":"K0059","resourceId":"NS0101AQYSVD2","type":1,"weight":1}]','2021-10-14 16:36:15.000000000','2022-01-10 09:31:20.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(124,'K0297','23f60bb0501dc27b096dd3205683cfa9','对已确认安全风险的应对措施设计知识','','2021-10-14 16:36:19.000000000','2021-10-14 16:36:19.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(125,'K0060','13d63fdbe3da0ff264b9647565cfaed5','操作系统知识','[{"relatedId":"K0060","resourceId":"SS0501CZXTAQ","type":1,"weight":1},{"relatedId":"K0060","resourceId":"SS0503LINUX","type":1,"weight":1},{"relatedId":"K0060","resourceId":"SS0502WIN","type":1,"weight":1}]','2021-10-14 16:36:39.000000000','2022-01-10 09:32:10.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(126,'K0298','521e0b5143744d0fff1e528e08c486b5','对已确认安全风险的应对措施知识','','2021-10-14 16:36:41.000000000','2021-10-14 16:36:41.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(127,'K0061','13d63fdbe3da0ff264b9647565cfaed5','网络中流量如何流动的知识(比如 TCP、IP、OSI 模型、ITIL)','','2021-10-14 16:37:04.000000000','2021-10-14 16:37:04.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(128,'K0301','0bf2eb74863de071afa24856a266e4c7','使用合适工具(比如 Wireshark, tcpdump)进行数据包层次分析的知识','[{"relatedId":"K0301","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 16:37:10.000000000','2022-01-10 10:05:04.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(129,'K0302','d78b0449533e94ee751bad6b568ecfb9','计算机基本操作的知识','','2021-10-14 16:37:32.000000000','2021-10-14 16:37:32.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(130,'K0062','d78b0449533e94ee751bad6b568ecfb9','流量包分析的知识','[{"relatedId":"K0062","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 16:37:36.000000000','2022-01-10 09:32:56.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(131,'K0069','0bf2eb74863de071afa24856a266e4c7','比如 SQL 语言类似的查询语言知识','[{"relatedId":"K0069","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0069","resourceId":"ISTB0103VFMYSQL","type":1,"weight":1}]','2021-10-14 16:37:57.000000000','2022-01-10 09:34:04.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(132,'K0303','521e0b5143744d0fff1e528e08c486b5','使用子网工具的知识','','2021-10-14 16:37:58.000000000','2021-10-14 16:37:58.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(133,'K0307','23f60bb0501dc27b096dd3205683cfa9','常用网络工具的知识(比如 Ping\traceroute\nslookup)','','2021-10-14 16:38:23.000000000','2021-10-14 16:38:23.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(134,'K0070','13d63fdbe3da0ff264b9647565cfaed5','系统和应用安全威胁和漏洞的知识(比如缓冲区溢出、移动代码、跨站脚本、 SQL 注入、竞争条件攻击、隐蔽信道、重放攻击、面向返回攻击、恶意代码)','[{"relatedId":"K0070","resourceId":"NS0504WEBAQ","type":1,"weight":1},{"relatedId":"K0070","resourceId":"NS0501WEBUG","type":1,"weight":1},{"relatedId":"K0070","resourceId":"NS0503DVWA","type":1,"weight":1},{"relatedId":"K0070","resourceId":"NS0505WEBSZ","type":1,"weight":1},{"relatedId":"K0070","resourceId":"NS0503WEB","type":1,"weight":1}]','2021-10-14 16:38:29.000000000','2022-01-10 09:35:06.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(135,'K0309','8d5fe8f769c936b86c3092aab0a80e7c','那些可能被对手利用的新兴技术的知识','','2021-10-14 16:38:45.000000000','2021-10-14 16:38:45.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(136,'K0071','13d63fdbe3da0ff264b9647565cfaed5','远程访问技术概念知识','','2021-10-14 16:39:00.000000000','2021-10-14 16:39:00.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(137,'K0310','90207603744123196c360addca94cc41','黑客方法论的知识','','2021-10-14 16:39:17.000000000','2021-10-14 16:39:17.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(138,'K0085','d78b0449533e94ee751bad6b568ecfb9','系统和应用安全威胁和漏洞知识','[{"relatedId":"K0085","resourceId":"NS0504WEBAQ","type":1,"weight":1},{"relatedId":"K0085","resourceId":"NS0501WEBUG","type":1,"weight":1},{"relatedId":"K0085","resourceId":"ISC0100WEB","type":1,"weight":1}]','2021-10-14 16:39:26.000000000','2022-01-10 09:37:35.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(139,'K0314','d78b0449533e94ee751bad6b568ecfb9','行业技术知识以及他们影响渗透/漏洞的区别。','','2021-10-14 16:39:40.000000000','2021-10-14 16:39:40.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(140,'K0098','d78b0449533e94ee751bad6b568ecfb9','了解网络防御服务提供商报告结构和其组织内部的处理流程','','2021-10-14 16:39:52.000000000','2021-10-14 16:39:52.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(141,'K0315','521e0b5143744d0fff1e528e08c486b5','信息收集和生产、汇报和信息共享方面的原则方法、过程和技术知识','[{"relatedId":"K0315","resourceId":"CS0600YSBH","type":1,"weight":1}]','2021-10-14 16:40:04.000000000','2022-01-10 10:05:50.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(142,'K0318','d78b0449533e94ee751bad6b568ecfb9','操作系统命令行/提示符的知识','','2021-10-14 16:40:26.000000000','2021-10-14 16:40:26.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(143,'K0099','521e0b5143744d0fff1e528e08c486b5','常用网络协议(比如 TCP/IP)、服务(比如 Web、Mail、DNS)以及他们如 何交互，来实现网络通信的知识','[{"relatedId":"K0099","resourceId":"ISTB0304WXTX","type":1,"weight":1},{"relatedId":"K0099","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:40:30.000000000','2022-01-10 09:39:13.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(144,'K0324','23f60bb0501dc27b096dd3205683cfa9','入侵检测系统 IDS/入侵防御系统 IPS 的工具和应用知识','[{"relatedId":"K0324","resourceId":"NS0200IDSA","type":1,"weight":1}]','2021-10-14 16:41:01.000000000','2022-01-10 10:06:10.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(145,'K0104','d78b0449533e94ee751bad6b568ecfb9','VPN 安全的知识','','2021-10-14 16:41:03.000000000','2021-10-14 16:41:03.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(146,'K0326','90207603744123196c360addca94cc41','网络空间方法的知识，比如防火墙、DMZ 区和加密','','2021-10-14 16:41:26.000000000','2021-10-14 16:41:26.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(147,'K0105','8d5fe8f769c936b86c3092aab0a80e7c','Web 服务知识，包括 SOA、SOAP 和 WSDL','[{"relatedId":"K0105","resourceId":"ISTB0103VFBOOTSTRAP","type":1,"weight":1}]','2021-10-14 16:41:32.000000000','2022-01-10 09:39:36.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(148,'K0331','d78b0449533e94ee751bad6b568ecfb9','网络协议知识(比如 TCP、IP、DHCP、目录服务(比如 DNS))','[{"relatedId":"K0331","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:41:51.000000000','2022-01-10 10:06:39.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(149,'K0106','8d5fe8f769c936b86c3092aab0a80e7c','了解网络攻击构成要素的知识以及网络威胁和漏洞关系的知识','','2021-10-14 16:42:07.000000000','2021-10-14 16:42:07.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(150,'K0332','0bf2eb74863de071afa24856a266e4c7','网络协议知识，比如 TCP/IP、动态主机配置(DHCP?)、DNS 和目录服务','[{"relatedId":"K0332","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:42:13.000000000','2022-01-10 10:08:14.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(151,'K0107','8d5fe8f769c936b86c3092aab0a80e7c','内部威胁调查、报告、调查工具和法律/规定的经验和知识','','2021-10-14 16:42:30.000000000','2021-10-14 16:42:30.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(152,'K0334','521e0b5143744d0fff1e528e08c486b5','网络流量分析知识(工具、方法、流程)','[{"relatedId":"K0334","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 16:42:42.000000000','2022-01-10 10:08:39.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(153,'K0111','13d63fdbe3da0ff264b9647565cfaed5','了解常见的网络工具(比如 ping\tracert\nslookup)和相关提示信息的解释','','2021-10-14 16:42:53.000000000','2021-10-14 16:42:53.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(154,'K0335','13d63fdbe3da0ff264b9647565cfaed5','当前和新兴的网络空间技术知识','','2021-10-14 16:43:12.000000000','2021-10-14 16:43:12.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(155,'K0112','0bf2eb74863de071afa24856a266e4c7','纵深防御的原则和网络安全的结构知识','[{"relatedId":"K0112","resourceId":"NS0100SEC","type":1,"weight":1}]','2021-10-14 16:43:19.000000000','2022-01-10 09:40:27.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(156,'K0336','d78b0449533e94ee751bad6b568ecfb9','访问身份认证方法的知识','[{"relatedId":"K0336","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 16:43:42.000000000','2022-01-10 10:08:58.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(157,'K0113','13d63fdbe3da0ff264b9647565cfaed5','不同类型的网络通信知识(比如 LAN、WAN、MAN、WLAN、WWAN)','[{"relatedId":"K0113","resourceId":"ISTB0301SSCI","type":1,"weight":1},{"relatedId":"K0113","resourceId":"ISTB0302TXYL","type":1,"weight":1},{"relatedId":"K0113","resourceId":"ISTB0303JCBM","type":1,"weight":1},{"relatedId":"K0113","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 16:43:47.000000000','2022-01-10 09:41:16.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(158,'K0339','13d63fdbe3da0ff264b9647565cfaed5','如何使用网络分析工具确认漏洞的知识','[{"relatedId":"K0339","resourceId":"ISC0101TOOLS","type":1,"weight":1},{"relatedId":"K0339","resourceId":"ISC0100MISC","type":1,"weight":1}]','2021-10-14 16:44:09.000000000','2022-01-10 10:09:27.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(159,'K0115','521e0b5143744d0fff1e528e08c486b5','那些可能被对手利用的计算机新兴技术知识','','2021-10-14 16:44:28.000000000','2021-10-14 16:44:28.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(160,'K0340','0bf2eb74863de071afa24856a266e4c7','网络中流量如何流动的知识(比如 TCP、IP、OSI 模型)','','2021-10-14 16:44:37.000000000','2021-10-14 16:44:37.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(161,'K0119','0bf2eb74863de071afa24856a266e4c7','Windows 或 Linux/Unix 环境下的黑客攻击方法知识','','2021-10-14 16:44:55.000000000','2021-10-14 16:44:55.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(162,'K0342','90207603744123196c360addca94cc41','渗透测试原理、工具和技术知识','[{"relatedId":"K0342","resourceId":"ISC0101TOOLS","type":1,"weight":1}]','2021-10-14 16:45:05.000000000','2022-01-10 10:09:49.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(163,'K0129','521e0b5143744d0fff1e528e08c486b5','Unix 命令行的知识(比如 mkdir\mv\ls\passwd\grep)','','2021-10-14 16:45:23.000000000','2021-10-14 16:45:23.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(164,'K0344','13d63fdbe3da0ff264b9647565cfaed5','威胁环境知识','','2021-10-14 16:45:41.000000000','2021-10-14 16:45:41.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(165,'K0345','90207603744123196c360addca94cc41','网络空间攻击者知识(比如脚本小子、内部威胁、非国家支持黑客、国家支持 黑客)','[{"relatedId":"K0345","resourceId":"ISC0101TOOLS","type":1,"weight":1}]','2021-10-14 16:46:03.000000000','2022-01-10 10:10:07.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(166,'K0352','13d63fdbe3da0ff264b9647565cfaed5','各种形式的情报支持需求、话题和关注领域的知识','[{"relatedId":"K0352","resourceId":"CS0303SJKSH","type":1,"weight":1}]','2021-10-14 16:46:23.000000000','2022-01-10 10:10:24.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(167,'K0131','521e0b5143744d0fff1e528e08c486b5','网络邮件收集、搜索/分析的技术、工具和 cooki 知识','','2021-10-14 16:46:39.000000000','2021-10-14 16:46:39.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(168,'K0355','d78b0449533e94ee751bad6b568ecfb9','所有来源报告和传播过程的知识','','2021-10-14 16:46:49.000000000','2021-10-14 16:46:49.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(169,'K0132','0bf2eb74863de071afa24856a266e4c7','哪些系统文件(比如日志文件、注册表文件、配置文件)包含相关信息，以及 如何找到这些系统文件的知识','','2021-10-14 16:47:05.000000000','2021-10-14 16:47:05.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(170,'K0356','23f60bb0501dc27b096dd3205683cfa9','分析工具和技术的知识','[{"relatedId":"K0356","resourceId":"CS0501XXYC","type":1,"weight":1},{"relatedId":"K0356","resourceId":"ISC0101TOOLS","type":1,"weight":1}]','2021-10-14 16:47:13.000000000','2022-01-10 10:10:56.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(171,'K0135','521e0b5143744d0fff1e528e08c486b5','Web 过滤器技术知识','','2021-10-14 16:47:25.000000000','2021-10-14 16:47:25.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(172,'K0362','d78b0449533e94ee751bad6b568ecfb9','攻击方法和技术的知识(DDoS、暴力、欺骗等等)','','2021-10-14 16:47:42.000000000','2021-10-14 16:47:42.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(173,'K0142','d78b0449533e94ee751bad6b568ecfb9','收集管理过程、能力和限制的知识','','2021-10-14 16:47:52.000000000','2021-10-14 16:47:52.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(174,'K0363','8d5fe8f769c936b86c3092aab0a80e7c','审计和日志处理的知识(比如基于服务器的日志)','','2021-10-14 16:48:08.000000000','2021-10-14 16:48:08.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(175,'K0143','d78b0449533e94ee751bad6b568ecfb9','前端收集系统的知识，包括网络流量收集、过滤器和选择器','','2021-10-14 16:48:14.000000000','2021-10-14 16:48:14.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(176,'K0364','0bf2eb74863de071afa24856a266e4c7','评估(信息)采集任务所需的可用数据库和工具知识','[{"relatedId":"K0364","resourceId":"ISTB0103VFREDIS","type":1,"weight":1}]','2021-10-14 16:48:34.000000000','2022-01-10 10:11:36.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(177,'K0144','13d63fdbe3da0ff264b9647565cfaed5','全球化背景下，社会化动态计算机攻击的知识','','2021-10-14 16:48:42.000000000','2021-10-14 16:48:42.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(178,'K0367','13d63fdbe3da0ff264b9647565cfaed5','基本的网络空间(攻击)操作活动的概念(比如踩点、扫描和枚举，渗透测试， 黑/白名单)','','2021-10-14 16:49:12.000000000','2021-10-14 16:49:12.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(179,'K0371','0bf2eb74863de071afa24856a266e4c7','(信息)收集过程中的基本原理知识。(比如被叫号码识别，社交网络分析)','','2021-10-14 16:49:37.000000000','2021-10-14 16:49:37.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(180,'K0145','0bf2eb74863de071afa24856a266e4c7','安全事件关联工具的知识','[{"relatedId":"K0145","resourceId":"CS0307DJYSJ","type":1,"weight":1}]','2021-10-14 16:49:50.000000000','2022-01-10 09:42:28.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(181,'K0386','521e0b5143744d0fff1e528e08c486b5','(信息)收集管理工具的知识','','2021-10-14 16:50:02.000000000','2021-10-14 16:50:02.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(182,'K0387','23f60bb0501dc27b096dd3205683cfa9','(信息)收集计划流程和收集计划的知识','','2021-10-14 16:50:28.000000000','2021-10-14 16:50:28.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(183,'K0388','13d63fdbe3da0ff264b9647565cfaed5','搜索/分析技术和工具(信息)收集的知识，包括聊天/好友列表,新兴技术、VIOP、 基于 IP 的媒体(Media Over IP)、VPN、卫星小数据站 VSAT/无线、网络邮 件和 cookie 等','[{"relatedId":"K0388","resourceId":"CS0200WLSJHQ","type":1,"weight":1},{"relatedId":"K0388","resourceId":"ISTB0405JSJZW","type":1,"weight":1}]','2021-10-14 16:50:54.000000000','2022-01-10 10:12:43.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(184,'K0389','90207603744123196c360addca94cc41','传统的或非传统的(信息)收集来源的知识。','','2021-10-14 16:51:22.000000000','2021-10-14 16:51:22.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(185,'K0390','0bf2eb74863de071afa24856a266e4c7','(信息)收集策略的知识','','2021-10-14 16:51:47.000000000','2021-10-14 16:51:47.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(186,'K0391','d78b0449533e94ee751bad6b568ecfb9','(信息)收集系统、能力和流程的知识','','2021-10-14 16:52:14.000000000','2021-10-14 16:52:14.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(187,'K0392','521e0b5143744d0fff1e528e08c486b5','常见的计算机/网络感染(病毒、木马等)和感染方法(端口、附件等)的知识','','2021-10-14 16:52:41.000000000','2021-10-14 16:52:41.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(188,'K0147','0bf2eb74863de071afa24856a266e4c7','新兴的安全话题、风险和漏洞知识','[{"relatedId":"K0147","resourceId":"ISC0102XMAN17","type":1,"weight":1},{"relatedId":"K0147","resourceId":"ISC0102XMAN16","type":1,"weight":1}]','2021-10-14 16:53:06.000000000','2022-01-10 09:43:27.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(189,'K0393','d78b0449533e94ee751bad6b568ecfb9',' 常见的网络设备及其配置的知识','[{"relatedId":"K0393","resourceId":"SS0200SBAQ","type":1,"weight":1}]','2021-10-14 16:53:18.000000000','2022-01-10 10:13:06.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(190,'K0150','90207603744123196c360addca94cc41','企业事件响应程序、规则和职责知识','','2021-10-14 16:53:30.000000000','2021-10-14 16:53:30.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(191,'K0394','13d63fdbe3da0ff264b9647565cfaed5','常见的数据库和数据库工具报告的知识','[{"relatedId":"K0394","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0394","resourceId":"ISTB0404DBGL","type":1,"weight":1}]','2021-10-14 16:53:38.000000000','2022-01-10 10:13:37.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(192,'K0395','8d5fe8f769c936b86c3092aab0a80e7c','计算机网络基础知识(比如网络的基本计算机组件，网络类型等)','[{"relatedId":"K0395","resourceId":"ISTB0401JSJZCYL","type":1,"weight":1},{"relatedId":"K0395","resourceId":"ISTB0402CZXTYL","type":1,"weight":1},{"relatedId":"K0395","resourceId":"ISTB0405JSJWL","type":1,"weight":1},{"relatedId":"K0395","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:54:00.000000000','2022-01-10 10:15:22.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(193,'K0157','90207603744123196c360addca94cc41','网络防御政策、过程和法规的知识','','2021-10-14 16:54:04.000000000','2021-10-14 16:54:04.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(194,'K0161','521e0b5143744d0fff1e528e08c486b5','不同攻击类别的知识(比如被动攻击、主动攻击、内部攻击、临近攻击、分布 攻击)','','2021-10-14 16:54:22.000000000','2021-10-14 16:54:22.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(195,'K0397','d78b0449533e94ee751bad6b568ecfb9','操作系统的概念知识(比如 Linux、Unix)','[{"relatedId":"K0397","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 16:54:30.000000000','2022-01-10 10:17:18.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(196,'K0162','90207603744123196c360addca94cc41','不同操作威胁环境的知识(比如第一代的脚本小子，第二代的非国家支持黑客， 第三代的国家支持的黑客)','','2021-10-14 16:54:48.000000000','2021-10-14 16:54:48.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(197,'K0398','23f60bb0501dc27b096dd3205683cfa9','网站相关的概念知识(比如 Web 服务/网页、主机、DNS、注册、类似 HTML 的 Web 语言)','[{"relatedId":"K0398","resourceId":"ISTB0103HTML","type":1,"weight":1},{"relatedId":"K0398","resourceId":"ISTB0103VFJQUERY","type":1,"weight":1},{"relatedId":"K0398","resourceId":"ISTB0103VFBOOTSTRAP","type":1,"weight":1},{"relatedId":"K0398","resourceId":"ISTB0103VFHTML","type":1,"weight":1},{"relatedId":"K0398","resourceId":"ISTB0103VFJAVASCRIPT","type":1,"weight":1}]','2021-10-14 16:54:52.000000000','2022-01-10 10:18:28.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(198,'K0165','0bf2eb74863de071afa24856a266e4c7','风险威胁评估知识','','2021-10-14 16:55:07.000000000','2021-10-14 16:55:07.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(199,'K0401','90207603744123196c360addca94cc41','(信息)收集产品评估标准的知识','','2021-10-14 16:55:20.000000000','2021-10-14 16:55:20.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(200,'K0167','23f60bb0501dc27b096dd3205683cfa9','了解基本的系统管理，网络和操作系统加固技术','[{"relatedId":"K0167","resourceId":"SS0501CZXTAQ","type":1,"weight":1},{"relatedId":"K0167","resourceId":"SS0503LINUX","type":1,"weight":1},{"relatedId":"K0167","resourceId":"SS0502WIN","type":1,"weight":1}]','2021-10-14 16:55:27.000000000','2022-01-10 09:44:50.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(201,'K0402','d78b0449533e94ee751bad6b568ecfb9','网络空间领域的目标选择和适用性的关键和漏洞条件(比如价值、恢复、隔离、 对策)','','2021-10-14 16:55:47.000000000','2021-10-14 16:55:47.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(202,'K0172','13d63fdbe3da0ff264b9647565cfaed5','中间件知识(比如企业服务总线和消息队列)','','2021-10-14 16:55:48.000000000','2021-10-14 16:55:48.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(203,'K0404','13d63fdbe3da0ff264b9647565cfaed5','当前(信息)搜集需求的知识','','2021-10-14 16:56:09.000000000','2021-10-14 16:56:09.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(204,'K0174','13d63fdbe3da0ff264b9647565cfaed5','网络协议知识','[{"relatedId":"K0174","resourceId":"NS0600WXWL","type":1,"weight":1},{"relatedId":"K0174","resourceId":"NS0300AQXY","type":1,"weight":1},{"relatedId":"K0174","resourceId":"ISTB0401JSJZCYL","type":1,"weight":1},{"relatedId":"K0174","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 16:56:10.000000000','2022-01-10 09:47:41.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(205,'K0405','521e0b5143744d0fff1e528e08c486b5','当前计算机入侵汇总的知识','','2021-10-14 16:56:32.000000000','2021-10-14 16:56:32.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(206,'K0177','0bf2eb74863de071afa24856a266e4c7','一般攻击阶段的知识(比如踩点扫描、枚举猜测、获取访问权限、特权提升、 维持访问、网络渗透、轨迹隐藏)','','2021-10-14 16:56:34.000000000','2021-10-14 16:56:34.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(207,'K0406','0bf2eb74863de071afa24856a266e4c7','主动防御和系统加固方面，主流的软件和方法知识','','2021-10-14 16:56:53.000000000','2021-10-14 16:56:53.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(208,'K0179','23f60bb0501dc27b096dd3205683cfa9','网络安全架构概念知识，包括拓扑、协议、组件和原理(比如应用纵深防御)','[{"relatedId":"K0179","resourceId":"NS0300AQXY","type":1,"weight":1},{"relatedId":"K0179","resourceId":"NS0100SEC","type":1,"weight":1},{"relatedId":"K0179","resourceId":"NS0101AQYS","type":1,"weight":1},{"relatedId":"K0179","resourceId":"NS0101AQYSVD1","type":1,"weight":1},{"relatedId":"K0179","resourceId":"SS1201ICS","type":1,"weight":1},{"relatedId":"K0179","resourceId":"ISTB0405WLFWJS","type":1,"weight":1}]','2021-10-14 16:56:56.000000000','2022-01-10 09:55:28.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(209,'K0185','0bf2eb74863de071afa24856a266e4c7','普通取证工具配置和支撑应用知识(比如 VMWare、Wireshark)','[{"relatedId":"K0185","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 16:57:17.000000000','2022-01-10 09:56:40.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(210,'K0407','0bf2eb74863de071afa24856a266e4c7','客户信息需求的知识','','2021-10-14 16:57:27.000000000','2021-10-14 16:57:27.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(211,'K0189','0bf2eb74863de071afa24856a266e4c7','虚拟机觉察到的恶意软件，调试器觉察到的恶意软件以及打包 packing 的知识','','2021-10-14 16:57:42.000000000','2021-10-14 16:57:42.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(212,'K0408','13d63fdbe3da0ff264b9647565cfaed5','网络空间行动(比如网络防御、信息收集、环境准备、网络攻击)、原理、能 力、限制和效果的知识','[{"relatedId":"K0408","resourceId":"NS0403GFJSYSJ","type":1,"weight":1},{"relatedId":"K0408","resourceId":"NS0400FH","type":1,"weight":1}]','2021-10-14 16:57:52.000000000','2022-01-10 10:32:02.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(213,'K0192','0bf2eb74863de071afa24856a266e4c7','Windows/Unix 端口和服务的知识','','2021-10-14 16:58:09.000000000','2021-10-14 16:58:09.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(214,'K0409','8d5fe8f769c936b86c3092aab0a80e7c','网络空间情报/信息收集能力和知识库的知识','','2021-10-14 16:58:14.000000000','2021-10-14 16:58:14.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(215,'K0410','90207603744123196c360addca94cc41','网络空间法律和他们对网络空间规划影响的知识','','2021-10-14 16:58:36.000000000','2021-10-14 16:58:36.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(216,'K0411','23f60bb0501dc27b096dd3205683cfa9','网络空间法律、法规和他们对网络空间规划影响的知识','','2021-10-14 16:58:58.000000000','2021-10-14 16:58:58.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(220,'K0014','13d63fdbe3da0ff264b9647565cfaed5','复杂数据结构的知识','[{"relatedId":"K0014","resourceId":"ISTB0105DSA","type":1,"weight":1}]','2021-10-14 16:59:28.000000000','2022-01-07 15:21:33.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(221,'K0430','23f60bb0501dc27b096dd3205683cfa9','规避策略和技术的知识','','2021-10-14 16:59:58.000000000','2021-10-14 16:59:58.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(222,'K0432','521e0b5143744d0fff1e528e08c486b5','了解网络空间运营策略、政策和机构相关的已有的、新兴的和长期的话题','','2021-10-14 17:00:22.000000000','2021-10-14 17:00:22.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(223,'K0433','0bf2eb74863de071afa24856a266e4c7','了解对操作系统结构和操作进行取证的含义','[{"relatedId":"K0433","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 17:00:46.000000000','2022-01-10 10:32:52.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(224,'K0434','90207603744123196c360addca94cc41','包括流量收集、过滤和选择在内的前端收集系统知识','','2021-10-14 17:01:09.000000000','2021-10-14 17:01:09.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(225,'K0435','d78b0449533e94ee751bad6b568ecfb9','基础的网络空间概念、原则、限制和效果知识','[{"relatedId":"K0435","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 17:01:35.000000000','2022-01-10 10:33:12.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(226,'K0436','90207603744123196c360addca94cc41','基本的网络空间操作概念、术语/词汇(比如环境准备、网络攻击、网络防御)、 原则、能力、限制和效果的知识','','2021-10-14 17:02:10.000000000','2021-10-14 17:02:10.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(227,'K0015','0bf2eb74863de071afa24856a266e4c7','计算机算法的知识','[{"relatedId":"K0015","resourceId":"ISTB0105DSA","type":1,"weight":1},{"relatedId":"K0015","resourceId":"CS0302SJFXSF","type":1,"weight":1},{"relatedId":"K0015","resourceId":"CS0201MLADM","type":1,"weight":1},{"relatedId":"K0015","resourceId":"NS0301NSAML","type":1,"weight":1}]','2021-10-14 17:02:31.000000000','2022-01-07 15:22:47.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(228,'K0440','8d5fe8f769c936b86c3092aab0a80e7c','基于主机的安全产品以及他们如何影响渗透利用和漏洞的知识','','2021-10-14 17:02:32.000000000','2021-10-14 17:02:32.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(229,'K0016','13d63fdbe3da0ff264b9647565cfaed5','计算机编程原则的知识，比如面向对象的设计','[{"relatedId":"K0016","resourceId":"ISTB0103PCXSJ","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103JS","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103PHP","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103HTML","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103ASM","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103CYY","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103GOLANG","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103VFJSP","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103VFJAVASE","type":1,"weight":1},{"relatedId":"K0016","resourceId":"ISTB0103VFHTML","type":1,"weight":1}]','2021-10-14 17:02:49.000000000','2022-01-07 15:25:50.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(230,'K0452','0bf2eb74863de071afa24856a266e4c7','可以提供 radius 认证和登录、DNS、邮件、Web 服务、FTF 服务、DHCP、防 火墙和 SNMP 服务的 Unix 和 Windows 系统的知识。','[{"relatedId":"K0452","resourceId":"CRYPT07RZ","type":1,"weight":1}]','2021-10-14 17:03:05.000000000','2022-01-10 10:33:31.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(231,'K0018','23f60bb0501dc27b096dd3205683cfa9','密码算法知识。(比如 IPSEC、AES、GRE、IKE、MD5、SHA、3DES)','[{"relatedId":"K0018","resourceId":"CRYPT02FZ","type":1,"weight":1},{"relatedId":"K0018","resourceId":"CRYPT03XL","type":1,"weight":1},{"relatedId":"K0018","resourceId":"CRYPT05GY","type":1,"weight":1},{"relatedId":"K0018","resourceId":"CRYPT04HASH","type":1,"weight":1},{"relatedId":"K0018","resourceId":"ISC0100CRYPTO","type":1,"weight":1}]','2021-10-14 17:03:11.000000000','2022-01-10 08:59:36.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(232,'K0453','521e0b5143744d0fff1e528e08c486b5','指标和警告的知识','','2021-10-14 17:03:24.000000000','2021-10-14 17:03:24.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(233,'K0019','23f60bb0501dc27b096dd3205683cfa9','密码学和密钥管理的概念知识','[{"relatedId":"K0019","resourceId":"CRYPT09GL","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT01CRYPTO","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT06QM","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT07RZ","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT08XY","type":1,"weight":1},{"relatedId":"K0019","resourceId":"CRYPT09YS","type":1,"weight":1},{"relatedId":"K0019","resourceId":"ISC0100CRYPTO","type":1,"weight":1}]','2021-10-14 17:03:29.000000000','2022-01-10 09:01:12.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(234,'K0454','90207603744123196c360addca94cc41','信息需求的知识','[{"relatedId":"K0454","resourceId":"ISTB0106INFT","type":1,"weight":1}]','2021-10-14 17:03:41.000000000','2022-01-10 10:33:50.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(235,'K0455','d78b0449533e94ee751bad6b568ecfb9','了解信息安全概念、以及对技术和方法促进','[{"relatedId":"K0455","resourceId":"ISB0100XXAQ","type":1,"weight":1},{"relatedId":"K0455","resourceId":"NS0101AQYSVD4","type":1,"weight":1},{"relatedId":"K0455","resourceId":"NS0101AQYSVD6","type":1,"weight":1}]','2021-10-14 17:04:01.000000000','2022-01-10 10:34:28.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(236,'K0023','13d63fdbe3da0ff264b9647565cfaed5','数据库管理系统、查询语言、关系表和视图的知识','[{"relatedId":"K0023","resourceId":"ISTB0404DBEXP","type":1,"weight":1},{"relatedId":"K0023","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0023","resourceId":"SS0600DBSEC","type":1,"weight":1},{"relatedId":"K0023","resourceId":"ISTB0404DBGL","type":1,"weight":1},{"relatedId":"K0023","resourceId":"ISTB0103VFORACLE","type":1,"weight":1}]','2021-10-14 17:04:02.000000000','2022-01-10 09:03:51.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(237,'K0456','90207603744123196c360addca94cc41','情报的能力和局限性的知识','','2021-10-14 17:04:21.000000000','2021-10-14 17:04:21.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(238,'K0039','13d63fdbe3da0ff264b9647565cfaed5','在软件开发中应用的网络空间安全原则和方法知识','[{"relatedId":"K0039","resourceId":"SS0700SSEC","type":1,"weight":1},{"relatedId":"K0039","resourceId":"SS0900YYXX","type":1,"weight":1}]','2021-10-14 17:04:22.000000000','2022-01-10 09:24:49.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(239,'K0457','13d63fdbe3da0ff264b9647565cfaed5','情报密级的知识','','2021-10-14 17:04:45.000000000','2021-10-14 17:04:45.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(240,'K0052','0bf2eb74863de071afa24856a266e4c7','数学知识，包括对数，三角函数，线性代数，微积分，统计学','[{"relatedId":"K0052","resourceId":"ISB0200INFOMATH","type":1,"weight":1},{"relatedId":"K0052","resourceId":"ISTB0104MATH","type":1,"weight":1},{"relatedId":"K0052","resourceId":"ISTB0301SSCI","type":1,"weight":1}]','2021-10-14 17:04:50.000000000','2022-01-10 09:27:30.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(241,'K0458','8d5fe8f769c936b86c3092aab0a80e7c','情报学科知识','','2021-10-14 17:05:04.000000000','2021-10-14 17:05:04.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(243,'K0459','90207603744123196c360addca94cc41','情报使用需求的知识(比如逻辑的需求、通信支持需求、可操作性需求、法律 限制需求等)','[{"relatedId":"K0459","resourceId":"ISTB0302TXYL","type":1,"weight":1},{"relatedId":"K0459","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 17:05:32.000000000','2022-01-10 10:35:14.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(244,'K0068','0bf2eb74863de071afa24856a266e4c7','编程语言结构和逻辑知识','[{"relatedId":"K0068","resourceId":"ISTB0103WLBC","type":1,"weight":1}]','2021-10-14 17:05:56.000000000','2022-01-10 09:33:27.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(245,'K0469','521e0b5143744d0fff1e528e08c486b5','对威胁能力和行为进行预演和/或模拟的内部行动的知识','[{"relatedId":"K0469","resourceId":"SS0801TCB","type":1,"weight":1}]','2021-10-14 17:05:58.000000000','2022-01-10 10:35:51.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(247,'K0470','d78b0449533e94ee751bad6b568ecfb9','互联网和路由协议的知识','','2021-10-14 17:06:26.000000000','2021-10-14 17:06:26.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(248,'K0080','13d63fdbe3da0ff264b9647565cfaed5','软件设计工具、方法和技术知识','[{"relatedId":"K0080","resourceId":"SS1100ANDROID","type":1,"weight":1}]','2021-10-14 17:06:36.000000000','2022-01-10 09:36:23.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(249,'K0471','521e0b5143744d0fff1e528e08c486b5','互联网网络地址知识(IP 地址、无类域间路由、TCP/UDP 端口号)','','2021-10-14 17:06:48.000000000','2021-10-14 17:06:48.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(250,'K0472','8d5fe8f769c936b86c3092aab0a80e7c','入侵检测系统和签名(技术)的发展','[{"relatedId":"K0472","resourceId":"CRYPT06QM","type":1,"weight":1},{"relatedId":"K0472","resourceId":"NS0200IDSA","type":1,"weight":1}]','2021-10-14 17:07:08.000000000','2022-01-10 10:37:10.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(251,'K0473','23f60bb0501dc27b096dd3205683cfa9','入侵(规则)集的知识','[{"relatedId":"K0473","resourceId":"CS0400NRGK","type":1,"weight":1}]','2021-10-14 17:07:30.000000000','2022-01-10 10:39:28.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(252,'K0082','0bf2eb74863de071afa24856a266e4c7','软件工程知识','','2021-10-14 17:07:38.000000000','2021-10-14 17:07:38.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(253,'K0474','d78b0449533e94ee751bad6b568ecfb9','键网络威胁行为和他们构成的知识','','2021-10-14 17:07:53.000000000','2021-10-14 17:07:53.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(256,'K0140','d78b0449533e94ee751bad6b568ecfb9','安全编码技术的知识','','2021-10-14 17:08:12.000000000','2021-10-14 17:08:12.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(257,'K0475','8d5fe8f769c936b86c3092aab0a80e7c','操作环境和威胁关键因素的知识','','2021-10-14 17:08:19.000000000','2021-10-14 17:08:19.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(258,'K0178','13d63fdbe3da0ff264b9647565cfaed5','安全软件开发方法、工具和实践知识','','2021-10-14 17:08:37.000000000','2021-10-14 17:08:37.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(259,'K0481','0bf2eb74863de071afa24856a266e4c7','检查各种漏洞利用行为的方法和技术知识','[{"relatedId":"K0481","resourceId":"ISC0100PWN","type":1,"weight":1},{"relatedId":"K0481","resourceId":"SS0503PWN","type":1,"weight":1}]','2021-10-14 17:08:40.000000000','2022-01-10 10:40:02.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(260,'K0486','d78b0449533e94ee751bad6b568ecfb9','网络构建和网络拓扑的知识','','2021-10-14 17:09:00.000000000','2021-10-14 17:09:00.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(261,'K0487','8d5fe8f769c936b86c3092aab0a80e7c','网络安全的知识(比如加密、防火墙、认证、蜜罐、边界保护)','[{"relatedId":"K0487","resourceId":"CRYPT07RZ","type":1,"weight":1},{"relatedId":"K0487","resourceId":"NS0100SEC","type":1,"weight":1}]','2021-10-14 17:09:28.000000000','2022-01-10 10:42:44.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(262,'K0017','13d63fdbe3da0ff264b9647565cfaed5','处理数字取证数据的概念和实践知识','[{"relatedId":"K0017","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 17:09:34.000000000','2022-01-07 15:26:27.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(263,'K0488','d78b0449533e94ee751bad6b568ecfb9','网络安全实现的知识(比如基于主机的 IDS、IPS、访问控制列表)，包括他们的功能以及在网络中的位置','[{"relatedId":"K0488","resourceId":"NS0100SEC","type":1,"weight":1},{"relatedId":"K0488","resourceId":"SS0400AC","type":1,"weight":1}]','2021-10-14 17:09:52.000000000','2022-01-10 10:43:16.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(264,'K0021','13d63fdbe3da0ff264b9647565cfaed5','数据备份、备份类型(比如全备份、增量备份)和数据恢复的概念和工具的知识','','2021-10-14 17:10:02.000000000','2021-10-14 17:10:02.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(265,'K0493','521e0b5143744d0fff1e528e08c486b5','混淆隐藏技术的知识(比如 TOR/Onion/匿名者，VPN/VPS，加密)','[{"relatedId":"K0493","resourceId":"ISTB0405JSJZW","type":1,"weight":1}]','2021-10-14 17:10:21.000000000','2022-01-10 10:44:38.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(266,'K0500','8d5fe8f769c936b86c3092aab0a80e7c','机构和/或合作伙伴(信息)收集系统能力和流程的知识(比如收集和协议处理)','','2021-10-14 17:10:42.000000000','2021-10-14 17:10:42.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(267,'K0077 ','521e0b5143744d0fff1e528e08c486b5','服务端和客户端操作系统的知识','[{"relatedId":"K0077 ","resourceId":"SS0501CZXTAQ","type":1,"weight":1},{"relatedId":"K0077 ","resourceId":"SS0503LINUX","type":1,"weight":1},{"relatedId":"K0077 ","resourceId":"SS0502WIN","type":1,"weight":1}]','2021-10-14 17:10:53.000000000','2022-01-10 09:35:52.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(268,'K0501','d78b0449533e94ee751bad6b568ecfb9','组织机构网络空间运营程序、策略和资源的知识','','2021-10-14 17:11:02.000000000','2021-10-14 17:11:02.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(269,'K0078','13d63fdbe3da0ff264b9647565cfaed5','服务器诊断工具和容错技术的知识','','2021-10-14 17:11:21.000000000','2021-10-14 17:11:21.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(270,'K0515','90207603744123196c360addca94cc41','OSI 模型和底层网络协议(比如 TCP/IP)的知识','','2021-10-14 17:11:29.000000000','2021-10-14 17:11:29.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(271,'K0525','13d63fdbe3da0ff264b9647565cfaed5','与网络空间运营计划相关的情报计划产品知识','','2021-10-14 17:11:48.000000000','2021-10-14 17:11:48.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(272,'K0109','0bf2eb74863de071afa24856a266e4c7','包括各种类型组件和外设(比如 CPU、网卡、数据存储)的功能在内的，物理计算机基本组件和架构的知识','[{"relatedId":"K0109","resourceId":"ISTB0402CZXTYL","type":1,"weight":1}]','2021-10-14 17:11:56.000000000','2022-01-10 09:40:02.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(273,'K0117','0bf2eb74863de071afa24856a266e4c7','文件系统实现的相关知识(比如 NTFS、FAT、EXT)','','2021-10-14 17:12:16.000000000','2021-10-14 17:12:16.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(274,'K0118','13d63fdbe3da0ff264b9647565cfaed5','获取和保存数字证据的过程知识(比如物证连续保存)','','2021-10-14 17:12:37.000000000','2021-10-14 17:12:37.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(275,'K0122','13d63fdbe3da0ff264b9647565cfaed5','了解硬件、操作系统和网络技术调查研究的意义','','2021-10-14 17:13:02.000000000','2021-10-14 17:13:02.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(276,'K0526','90207603744123196c360addca94cc41','研究策略和知识管理的知识','','2021-10-14 17:13:23.000000000','2021-10-14 17:13:23.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(277,'K0123','90207603744123196c360addca94cc41','与(证据)采纳(比如联邦证据规则)相关的法律管理知识','','2021-10-14 17:13:24.000000000','2021-10-14 17:13:24.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(278,'K0527','8d5fe8f769c936b86c3092aab0a80e7c','风险管理和缓解策略知识','','2021-10-14 17:13:41.000000000','2021-10-14 17:13:41.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(279,'K0125','521e0b5143744d0fff1e528e08c486b5','收集、打包、传输和存储电子证据，以避免(证据)改变、丢失、物理损坏或 数据破坏的处理流程知识','','2021-10-14 17:13:47.000000000','2021-10-14 17:13:47.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(280,'K0530','23f60bb0501dc27b096dd3205683cfa9','包括在渗透利用中影响的网络组件在内的，安全软硬件选项知识，','','2021-10-14 17:14:01.000000000','2021-10-14 17:14:01.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(281,'K0128','0bf2eb74863de071afa24856a266e4c7','持久化数据的类型和集合的相关知识','','2021-10-14 17:14:17.000000000','2021-10-14 17:14:17.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(282,'K0536','d78b0449533e94ee751bad6b568ecfb9','渗透利用工具(比如 sniffer、keylogger)的结构、方法和策略知识，以及相 关技术(比如获取后门权限、收集/导出数据、对网络中的其他系统进行漏洞分 析)的知识','','2021-10-14 17:14:26.000000000','2021-10-14 17:14:26.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(284,'K0537','d78b0449533e94ee751bad6b568ecfb9','Unix/Linux、Windows 操作系统系统管理概念的知识(比如进程管理、目录结 构、已安装应用、访问控制)','[{"relatedId":"K0537","resourceId":"SS0400AC","type":1,"weight":1},{"relatedId":"K0537","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 17:14:50.000000000','2022-01-10 10:47:34.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(286,'K0133','8d5fe8f769c936b86c3092aab0a80e7c','数字取证数据的类型以及如何识别他们的知识','[{"relatedId":"K0133","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 17:15:00.000000000','2022-01-10 09:41:41.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(287,'K0538','d78b0449533e94ee751bad6b568ecfb9','了解目标和威胁的组织结构、关键能力和关键漏洞的知识','','2021-10-14 17:15:12.000000000','2021-10-14 17:15:12.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(288,'K0134','90207603744123196c360addca94cc41','移动取证知识','[{"relatedId":"K0134","resourceId":"SS1001DZSJQZ","type":1,"weight":1}]','2021-10-14 17:15:20.000000000','2022-01-10 09:42:05.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(289,'K0544','23f60bb0501dc27b096dd3205683cfa9','目标情报收集和操作准备的技术和生命周期的知识','','2021-10-14 17:15:33.000000000','2021-10-14 17:15:33.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(290,'K0548','8d5fe8f769c936b86c3092aab0a80e7c','目标或威胁的网络空间参与者和程序','','2021-10-14 17:15:55.000000000','2021-10-14 17:15:55.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(291,'K0155','521e0b5143744d0fff1e528e08c486b5','电子证据法的知识','','2021-10-14 17:16:05.000000000','2021-10-14 17:16:05.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(292,'K0549','521e0b5143744d0fff1e528e08c486b5','了解目标审核和验证程序','','2021-10-14 17:16:16.000000000','2021-10-14 17:16:16.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(293,'K0156','13d63fdbe3da0ff264b9647565cfaed5','证据的法律规则和法庭程序的知识','','2021-10-14 17:16:29.000000000','2021-10-14 17:16:29.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(294,'K0555','d78b0449533e94ee751bad6b568ecfb9','TCP/IP 网络协议的知识','[{"relatedId":"K0555","resourceId":"ISTB0405CM","type":1,"weight":1}]','2021-10-14 17:16:39.000000000','2022-01-10 10:47:53.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(295,'K0168','8d5fe8f769c936b86c3092aab0a80e7c','适用相关法律的知识(电子通信隐私法、外国情报监视法、保护美国法案、搜 查扣押法案、公民自由和隐私法案)，美国议会法案(比如法案编号 10、18、 32、50)，总统令、行政法规以及工作相关的行政刑事法规和程序','','2021-10-14 17:17:00.000000000','2021-10-14 17:17:00.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(296,'K0561','90207603744123196c360addca94cc41','基本网络安全的知识(比如加密、防火墙、认证、蜜罐、边界保护)','[{"relatedId":"K0561","resourceId":"CRYPT07RZ","type":1,"weight":1},{"relatedId":"K0561","resourceId":"NS0100SEC","type":1,"weight":1}]','2021-10-14 17:17:02.000000000','2022-01-10 10:48:20.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(297,'K0183','0bf2eb74863de071afa24856a266e4c7','逆向工程概念知识','[{"relatedId":"K0183","resourceId":"ISC0101TOOLS","type":1,"weight":1},{"relatedId":"K0183","resourceId":"ISC0100RE","type":1,"weight":1}]','2021-10-14 17:17:44.000000000','2022-01-10 09:56:03.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(298,'K0562','521e0b5143744d0fff1e528e08c486b5','了解新兴(信息)收集能力、访问方式和/或流程的能力和局限性。','','2021-10-14 17:17:48.000000000','2021-10-14 17:17:48.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(299,'K0184','521e0b5143744d0fff1e528e08c486b5','反取证的策略、技术和程序知识','','2021-10-14 17:18:09.000000000','2021-10-14 17:18:09.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(300,'K0563','90207603744123196c360addca94cc41','在应用到规划中的网络空间行动时，这些内部和外部(信息)收集能力、局限 性和任务处理方法知识。','','2021-10-14 17:18:20.000000000','2021-10-14 17:18:20.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(301,'K0564','d78b0449533e94ee751bad6b568ecfb9','目标通信网络的特征知识(比如容量、功能、路径、关键节点)','[{"relatedId":"K0564","resourceId":"ISTB0302TXYL","type":1,"weight":1},{"relatedId":"K0564","resourceId":"ISTB0303JCBM","type":1,"weight":1},{"relatedId":"K0564","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 17:18:45.000000000','2022-01-10 10:49:04.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(302,'K0186','d78b0449533e94ee751bad6b568ecfb9','调试过程和工具知识','','2021-10-14 17:18:50.000000000','2021-10-14 17:18:50.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(303,'K0565','d78b0449533e94ee751bad6b568ecfb9','常用的网络和路由协议(比如 TCP/IP)、服务(比如 Web、邮件、DNS)， 以及他们如何交互来提供网络通信服务的知识。','[{"relatedId":"K0565","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 17:19:04.000000000','2022-01-10 10:49:24.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(304,'K0568','521e0b5143744d0fff1e528e08c486b5','(信息)收集管理定义和收集管理权限的知识','','2021-10-14 17:19:26.000000000','2021-10-14 17:19:26.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(305,'K0187','13d63fdbe3da0ff264b9647565cfaed5','了解异常行为可能使用哪些不同类型文件的知识','','2021-10-14 17:19:30.000000000','2021-10-14 17:19:30.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(306,'K0572','90207603744123196c360addca94cc41','了解模拟威胁行为帮助组织机构(提升安全)的内部团队的能力','','2021-10-14 17:19:45.000000000','2021-10-14 17:19:45.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(307,'K0188','90207603744123196c360addca94cc41','恶意软件分析工具(比如 OD、IDA)知识','','2021-10-14 17:19:58.000000000','2021-10-14 17:19:58.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(308,'K0578','d78b0449533e94ee751bad6b568ecfb9','情报需求开发和信息处理要求的知识','[{"relatedId":"K0578","resourceId":"CS0304SJFXCL","type":1,"weight":1},{"relatedId":"K0578","resourceId":"ISTB0103VFCSS","type":1,"weight":1}]','2021-10-14 17:20:05.000000000','2022-01-10 10:49:51.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(309,'K0593','0bf2eb74863de071afa24856a266e4c7','了解网络空间运营的范围和他们潜在的情报支持需求、问题和关注点。','','2021-10-14 17:20:27.000000000','2021-10-14 17:20:27.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(310,'K0595','13d63fdbe3da0ff264b9647565cfaed5','了解操作目标、情报需求和情报产品任务之间关系','','2021-10-14 17:20:49.000000000','2021-10-14 17:20:49.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(311,'K0596','d78b0449533e94ee751bad6b568ecfb9','了解信息处理的要求','','2021-10-14 17:21:10.000000000','2021-10-14 17:21:10.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(312,'K0051','13d63fdbe3da0ff264b9647565cfaed5','低层次计算机语言的知识(比如汇编语言)','[{"relatedId":"K0051","resourceId":"ISTB0103ASM","type":1,"weight":1}]','2021-10-14 17:21:10.000000000','2022-01-10 09:26:44.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(313,'K0597','d78b0449533e94ee751bad6b568ecfb9','了解网络空间运营在支撑和促进其他机构运营上的作用。','','2021-10-14 17:21:33.000000000','2021-10-14 17:21:33.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(314,'K0097','0bf2eb74863de071afa24856a266e4c7','物理和虚拟数据存储介质特征的知识','','2021-10-14 17:21:53.000000000','2021-10-14 17:21:53.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(315,'K0602','0bf2eb74863de071afa24856a266e4c7','了解各种(信息)收集的科目和能力','','2021-10-14 17:21:56.000000000','2021-10-14 17:21:56.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(316,'K0603','90207603744123196c360addca94cc41','了解目标或威胁使用互联网的方式','','2021-10-14 17:22:16.000000000','2021-10-14 17:22:16.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(317,'K0171','13d63fdbe3da0ff264b9647565cfaed5','硬件逆向工程技术知识','','2021-10-14 17:22:38.000000000','2021-10-14 17:22:38.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(318,'K0604','521e0b5143744d0fff1e528e08c486b5','威胁和/或目标系统的知识','','2021-10-14 17:22:44.000000000','2021-10-14 17:22:44.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(319,'K0175','521e0b5143744d0fff1e528e08c486b5','软件逆向工程技术知识','[{"relatedId":"K0175","resourceId":"ISC0100RE","type":1,"weight":1}]','2021-10-14 17:23:03.000000000','2022-01-10 09:50:18.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(320,'K0608','8d5fe8f769c936b86c3092aab0a80e7c','Unix、Linux 和 Windows 操作系统的结构和内部知识(比如进程管理、目录结 构、已安装应用)','[{"relatedId":"K0608","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 17:23:07.000000000','2022-01-10 10:50:13.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(321,'K0609','0bf2eb74863de071afa24856a266e4c7','虚拟机技术的知识','[{"relatedId":"K0609","resourceId":"ISTB0405VT","type":1,"weight":1}]','2021-10-14 17:23:27.000000000','2022-01-10 10:50:35.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(322,'K0610','13d63fdbe3da0ff264b9647565cfaed5','虚拟化产品(VMWare、虚拟 PC)的知识','[{"relatedId":"K0610","resourceId":"ISTB0405VT","type":1,"weight":1}]','2021-10-14 17:23:50.000000000','2022-01-10 10:50:54.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(323,'K0612','0bf2eb74863de071afa24856a266e4c7','了解什么构成了网络的“威胁”','[{"relatedId":"K0612","resourceId":"NS0101AQYSVD5","type":1,"weight":1}]','2021-10-14 17:24:10.000000000','2022-01-10 10:51:14.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(324,'K0034','521e0b5143744d0fff1e528e08c486b5','了解网络服务和协议如何交互提供网络通信服务的知识','[{"relatedId":"K0034","resourceId":"ISTB0405WLFWJS","type":1,"weight":1},{"relatedId":"K0034","resourceId":"ISTB0304WXTX","type":1,"weight":1}]','2021-10-14 17:24:16.000000000','2022-01-10 09:06:15.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(325,'K0035','521e0b5143744d0fff1e528e08c486b5','系统组件如何安装、集成和优化的知识','[{"relatedId":"K0035","resourceId":"ISTB0402VFLINUX","type":1,"weight":1}]','2021-10-14 17:24:43.000000000','2022-01-10 09:24:00.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(327,'K0372','13d63fdbe3da0ff264b9647565cfaed5','基本的编程概念知识(比如层次、结构、编译型 vs 解释型语言)','[{"relatedId":"K0372","resourceId":"ISTB0103WLBC","type":1,"weight":1},{"relatedId":"K0372","resourceId":"ISTB0403BYXT","type":1,"weight":1}]','2021-10-14 17:25:14.000000000','2022-01-10 10:12:12.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(329,'K0050','0bf2eb74863de071afa24856a266e4c7','包括带宽管理的局域网和广域网原则和概念知识','','2021-10-14 17:25:46.000000000','2021-10-14 17:25:46.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(330,'K0396','521e0b5143744d0fff1e528e08c486b5','包括计算机语言、编程、测试、调试和文件类型在内的计算机编程概念知识，','[{"relatedId":"K0396","resourceId":"ISTB0103CYY","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103PCXSJ","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103JCXSJ","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103GOLANG","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103PHP","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103VFJSP","type":1,"weight":1},{"relatedId":"K0396","resourceId":"ISTB0103VFJAVASE","type":1,"weight":1}]','2021-10-14 17:26:00.000000000','2022-01-10 10:16:54.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(331,'K0056','0bf2eb74863de071afa24856a266e4c7','网络接入、身份识别和访问管理知识(比如 PKI)','','2021-10-14 17:26:07.000000000','2021-10-14 17:26:07.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(333,'K0057','0bf2eb74863de071afa24856a266e4c7','网络硬件设备和功能的知识','[{"relatedId":"K0057","resourceId":"SS0200SBAQ","type":1,"weight":1}]','2021-10-14 17:26:26.000000000','2022-01-10 09:30:17.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(334,'K0420','0bf2eb74863de071afa24856a266e4c7','数据库理论知识','[{"relatedId":"K0420","resourceId":"ISTB0103VFREDIS","type":1,"weight":1},{"relatedId":"K0420","resourceId":"ISTB0404DBGL","type":1,"weight":1}]','2021-10-14 17:26:36.000000000','2022-01-10 10:32:31.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(335,'K0088','13d63fdbe3da0ff264b9647565cfaed5','系统管理概念的知识','','2021-10-14 17:26:42.000000000','2021-10-14 17:26:42.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(336,'K0089','13d63fdbe3da0ff264b9647565cfaed5','系统诊断工具和容错技术的知识','','2021-10-14 17:26:57.000000000','2021-10-14 17:26:57.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(337,'K0305','23f60bb0501dc27b096dd3205683cfa9','加密算法、信息隐写(stenography)和其他数据隐藏的形式知识','[{"relatedId":"K0305","resourceId":"CS0501XXYC","type":1,"weight":1}]','2021-10-14 17:27:08.000000000','2022-01-10 10:05:30.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(338,'K0149','521e0b5143744d0fff1e528e08c486b5','组织机构的风险承受能力和/或风险管理方法知识','','2021-10-14 17:27:25.000000000','2021-10-14 17:27:25.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(341,'K0151','0bf2eb74863de071afa24856a266e4c7','当前和新兴的威胁和微信向量的知识','','2021-10-14 17:27:59.000000000','2021-10-14 17:27:59.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(345,'K0220','0bf2eb74863de071afa24856a266e4c7','学习模式的知识(比如死记硬背、观察学习)','','2021-10-14 17:29:08.000000000','2021-10-14 17:29:08.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(348,'K0360','521e0b5143744d0fff1e528e08c486b5','汇编代码的知识','[{"relatedId":"K0360","resourceId":"ISC0101TOOLS","type":1,"weight":1}]','2021-10-14 17:29:34.000000000','2022-01-10 10:11:15.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(349,'K0264','13d63fdbe3da0ff264b9647565cfaed5','包括 IT 供应链安全/风险管理政策、防篡改技术和需求在内的程序保护设计知识','[{"relatedId":"K0264","resourceId":"NS0101AQYSVD2","type":1,"weight":1},{"relatedId":"K0264","resourceId":"NS0101AQYSVD3","type":1,"weight":1}]','2021-10-14 17:29:41.000000000','2022-01-10 10:01:54.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(350,'K0369','23f60bb0501dc27b096dd3205683cfa9','恶意活动的概念知识(比如踩点、扫描和枚举)','','2021-10-14 17:29:59.000000000','2021-10-14 17:29:59.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(351,'K0265','d78b0449533e94ee751bad6b568ecfb9','支撑 IT 安全性、性能和可靠性的基础设施知识','[{"relatedId":"K0265","resourceId":"ISTB0101DZDL","type":1,"weight":1},{"relatedId":"K0265","resourceId":"SS0300KKXJS","type":1,"weight":1},{"relatedId":"K0265","resourceId":"ISTB0102EDA","type":1,"weight":1}]','2021-10-14 17:30:03.000000000','2022-01-10 10:02:38.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(353,'K0479','d78b0449533e94ee751bad6b568ecfb9','恶意软件分析和特征化的知识','','2021-10-14 17:30:30.000000000','2021-10-14 17:30:30.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(354,'K0516','0bf2eb74863de071afa24856a266e4c7','包括集线器、交换机、路由器、防火墙等在内的物理的和逻辑的网络设备和基 础设施知识','','2021-10-14 17:30:49.000000000','2021-10-14 17:30:49.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(355,'K0480','521e0b5143744d0fff1e528e08c486b5','恶意软件的知识','','2021-10-14 17:30:54.000000000','2021-10-14 17:30:54.000000000');
INSERT INTO "rtks_knowledge"("id","resource_id","category_id","content","courses","create_time","update_time") VALUES(362,'K0182','90207603744123196c360addca94cc41','Data Carving 技术和工具(比如 Foremost)','','2021-10-15 09:12:27.000000000','2021-10-15 09:12:27.000000000');

SET IDENTITY_INSERT "rtks_knowledge" OFF;
SET IDENTITY_INSERT "rtks_knowledge_category" ON;
INSERT INTO "rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(12,'13d63fdbe3da0ff264b9647565cfaed5','ISTB_信息科学技术基础领域','2021-10-14 15:50:59.000000000','2021-10-14 15:50:59.000000000');
INSERT INTO "rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(13,'0bf2eb74863de071afa24856a266e4c7','ISB_信息安全基础领域','2021-10-14 15:51:06.000000000','2021-10-14 15:51:06.000000000');
INSERT INTO "rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(14,'23f60bb0501dc27b096dd3205683cfa9','CRYPT_密码学知识领域','2021-10-14 15:53:11.000000000','2021-10-14 15:53:11.000000000');
INSERT INTO "rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(15,'521e0b5143744d0fff1e528e08c486b5','CS_内容安全知识领域','2021-10-14 15:53:23.000000000','2021-10-14 15:53:23.000000000');
INSERT INTO "rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(17,'90207603744123196c360addca94cc41','ISC_信息安全竞赛','2021-10-14 15:53:49.000000000','2021-10-14 15:53:49.000000000');
INSERT INTO "rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(18,'d78b0449533e94ee751bad6b568ecfb9','SS_系统安全知识领域','2021-10-14 15:54:02.000000000','2021-10-14 15:54:02.000000000');
INSERT INTO "rtks_knowledge_category"("id","resource_id","content","create_time","update_time") VALUES(19,'8d5fe8f769c936b86c3092aab0a80e7c','NS_网络安全知识领域','2021-10-14 15:54:08.000000000','2021-10-14 15:54:08.000000000');

SET IDENTITY_INSERT "rtks_knowledge_category" OFF;
SET IDENTITY_INSERT "rtks_level" ON;
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(1,'default','网络安全框架',null,null,'2021-09-09 08:52:09.000000000','2021-09-09 09:56:47.000000000',null);
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(3,'160a82bb64a4b931d1ecfc0907e34820','安全基础设施提供',null,'default','2021-09-09 08:52:09.000000000','2021-09-09 09:56:47.000000000','default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(4,'9c47cad21bf1c3a19168126aef24c771','运营与维护',null,'default',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(5,'8dba745e3fcaa3448cc303db510f43e0','监管与治理',null,'default',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(6,'39b1a4360ca4cb64e14e90534246f27b','保护和防御',null,'default',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(7,'bc4e6ddddbe2ff78d0eae79de829272e','分析',null,'default',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(8,'5836fceb39ebb1867f2d9d357e52cd2f','搜集与运作',null,'default',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(9,'a6ff1c1d65b1c411aea838460333d0a9','调查',null,'default',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(10,'fb9be3d8be71435eef3144ed9744a473','风险管理',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(11,'2f2a09ed40e6166f8a54f136f7f4a25d','软件开发',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(12,'76a295b70048f129a5ba8b44e8467bc3','系统架构',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(13,'103b4f90a89015f9b88600c7eaf7b998','技术研发',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(14,'8e2fb48d3431b905cedeb37e4a0e0dca','系统需求规划',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(15,'dd26731845f65a12356ad356c62acb04','测试评估',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(16,'9f16afbb771c6cda169d3ac1b8862774','系统开发',null,'160a82bb64a4b931d1ecfc0907e34820',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(17,'49a1982ccda399c198a1c452a0164d84','数据管理',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(18,'9487b7559c2d5c786ca680f335a2ef92','知识管理',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(19,'cbe07af2274f5d5ae136c8dd0afdaf96','客户服务和技术支持',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(20,'c301984071d24a98f7f38260b98441f8','网络服务',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(21,'d01fe0d4855de8f7a8440d054cce4bd1','系统管理',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(22,'65f63e779bc673e3ccde5dd45627a27d','系统分析',null,'9c47cad21bf1c3a19168126aef24c771',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(23,'c2085b43474d5a86e0184903903e3e8b','法律咨询和辩护',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(24,'36322f436eb79c7cd0554e54a78ed384','培训、教育和养成',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(25,'658a4e7c8b496e0a690103ff2793048f','网络空间安全管理',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(26,'0798f66e0a3b1c0e48e3ce0946e28b60','战略规划和策略',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(27,'c149fd006f78ff5fb69752030f03bded','网络空间安全执行领导',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(28,'b8fbbb94bd3d961bb06e649a9e36acd3','采购和项目管理',null,'8dba745e3fcaa3448cc303db510f43e0',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(29,'107ac7e46706743c38fa190ba65f86d7','网络空间安全防御分析',null,'39b1a4360ca4cb64e14e90534246f27b',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(30,'91abaa83081fea4f76a2b9ade501fc2f','网络空间安全防御基础设施支持',null,'39b1a4360ca4cb64e14e90534246f27b',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(31,'38314f7f03e45c90bd5f69e04d8cb7c4','事件响应',null,'39b1a4360ca4cb64e14e90534246f27b',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(32,'bfd62abaccf84cd3c072127719af257d','漏洞评估和管理',null,'39b1a4360ca4cb64e14e90534246f27b',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(33,'16a326962fd10baed76e656f905cb8e9','威胁分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(34,'6e2bfa5b7fd30825e784d1d4bdfa9b6d','渗透分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(35,'95266cc7d4b48de23fdff2ec8429c539','全源分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(36,'32b7ba539a3aad2cdf9e09e843e5362c','目标分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(37,'69790adaf0b2c9d87527db95399567d6','语言分析',null,'bc4e6ddddbe2ff78d0eae79de829272e',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(38,'b70c0b9ae595c7e2544c1d0eb1c9f539','搜集与运作',null,'5836fceb39ebb1867f2d9d357e52cd2f',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(39,'90decfd39b34eac09bd23c22cf746edb','网络空间运营规划',null,'5836fceb39ebb1867f2d9d357e52cd2f',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(40,'5c82d106b63705bca1462a10003991da','网络空间运营',null,'5836fceb39ebb1867f2d9d357e52cd2f',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(41,'5f452e2bd7d74a4b5b2b1cd0098adf33','网络调查',null,'a6ff1c1d65b1c411aea838460333d0a9',null,null,'default');
INSERT INTO "rtks_level"("id","resource_id","name","description","parent","create_time","update_time","frame") VALUES(42,'146862c6f4aebb353891381cf1aa832c','数字取证',null,'a6ff1c1d65b1c411aea838460333d0a9',null,null,'default');

SET IDENTITY_INSERT "rtks_level" OFF;
SET IDENTITY_INSERT "rtks_role" ON;
INSERT INTO "rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions","avatar") VALUES(121,'c3fe96a8de569cdb92e273c8809a73b7','渗透测试工程师','是网络安全领域的核心角色，通过模拟黑客攻击手段，对目标系统（如 Web 应用、网络基础设施、移动应用等）进行安全性测试，识别潜在漏洞并提供修复建议，以增强系统的抗攻击能力。','2021-10-15 08:52:18.000000000','2022-01-10 10:51:14.000000000','bfd62abaccf84cd3c072127719af257d','','["T0023","T0024","T0027","T0028","T0120","T0161","T0165","T0166","T0175","T0180","T0181","T0198","T0214","T0229","T0240","T0266","T0285","T0295","T0297","T0299","T0310","T0360","T0397","T0423","T0430","T0432","T0433","T0518","T0565","T0570","T0572","T0593","T0605","T0606","T0609","T0610","T0616","T0686","T0687","T0696","T0703","T0705","T0708","T0710","T0715","T0718","T0719","T0845","T0848"]','[{"id":"K0001","weight":1},{"id":"K0002","weight":1},{"id":"K0003","weight":1},{"id":"K0202","weight":1},{"id":"K0004","weight":1},{"id":"K0005","weight":1},{"id":"K0205","weight":1},{"id":"K0006","weight":1},{"id":"K0206","weight":1},{"id":"K0007","weight":1},{"id":"K0214","weight":1},{"id":"K0009","weight":1},{"id":"K0219","weight":1},{"id":"K0013","weight":1},{"id":"K0221","weight":1},{"id":"K0024","weight":1},{"id":"K0222","weight":1},{"id":"K0033","weight":1},{"id":"K0037","weight":1},{"id":"K0224","weight":1},{"id":"K0040","weight":1},{"id":"K0225","weight":1},{"id":"K0041","weight":1},{"id":"K0230","weight":1},{"id":"K0236","weight":1},{"id":"K0042","weight":1},{"id":"K0271","weight":1},{"id":"K0044","weight":1},{"id":"K0046","weight":1},{"id":"K0273","weight":1},{"id":"K0277","weight":1},{"id":"K0049","weight":1},{"id":"K0290","weight":1},{"id":"K0058","weight":1},{"id":"K0292","weight":1},{"id":"K0059","weight":1},{"id":"K0297","weight":1},{"id":"K0060","weight":1},{"id":"K0298","weight":1},{"id":"K0061","weight":1},{"id":"K0301","weight":1},{"id":"K0302","weight":1},{"id":"K0062","weight":1},{"id":"K0069","weight":1},{"id":"K0303","weight":1},{"id":"K0307","weight":1},{"id":"K0070","weight":1},{"id":"K0309","weight":1},{"id":"K0071","weight":1},{"id":"K0310","weight":1},{"id":"K0085","weight":1},{"id":"K0314","weight":1},{"id":"K0098","weight":1},{"id":"K0315","weight":1},{"id":"K0318","weight":1},{"id":"K0099","weight":1},{"id":"K0324","weight":1},{"id":"K0104","weight":1},{"id":"K0326","weight":1},{"id":"K0105","weight":1},{"id":"K0331","weight":1},{"id":"K0106","weight":1},{"id":"K0332","weight":1},{"id":"K0107","weight":1},{"id":"K0334","weight":1},{"id":"K0111","weight":1},{"id":"K0335","weight":1},{"id":"K0112","weight":1},{"id":"K0336","weight":1},{"id":"K0113","weight":1},{"id":"K0339","weight":1},{"id":"K0115","weight":1},{"id":"K0340","weight":1},{"id":"K0119","weight":1},{"id":"K0342","weight":1},{"id":"K0129","weight":1},{"id":"K0344","weight":1},{"id":"K0345","weight":1},{"id":"K0352","weight":1},{"id":"K0131","weight":1},{"id":"K0355","weight":1},{"id":"K0132","weight":1},{"id":"K0356","weight":1},{"id":"K0135","weight":1},{"id":"K0362","weight":1},{"id":"K0142","weight":1},{"id":"K0363","weight":1},{"id":"K0143","weight":1},{"id":"K0364","weight":1},{"id":"K0144","weight":1},{"id":"K0367","weight":1},{"id":"K0371","weight":1},{"id":"K0145","weight":1},{"id":"K0386","weight":1},{"id":"K0387","weight":1},{"id":"K0388","weight":1},{"id":"K0389","weight":1},{"id":"K0390","weight":1},{"id":"K0391","weight":1},{"id":"K0392","weight":1},{"id":"K0147","weight":1},{"id":"K0393","weight":1},{"id":"K0150","weight":1},{"id":"K0394","weight":1},{"id":"K0395","weight":1},{"id":"K0157","weight":1},{"id":"K0161","weight":1},{"id":"K0397","weight":1},{"id":"K0162","weight":1},{"id":"K0398","weight":1},{"id":"K0165","weight":1},{"id":"K0401","weight":1},{"id":"K0167","weight":1},{"id":"K0402","weight":1},{"id":"K0404","weight":1},{"id":"K0405","weight":1},{"id":"K0406","weight":1},{"id":"K0185","weight":1},{"id":"K0407","weight":1},{"id":"K0189","weight":1},{"id":"K0408","weight":1},{"id":"K0192","weight":1},{"id":"K0409","weight":1},{"id":"K0410","weight":1},{"id":"K0411","weight":1},{"id":"K0430","weight":1},{"id":"K0432","weight":1},{"id":"K0433","weight":1},{"id":"K0434","weight":1},{"id":"K0435","weight":1},{"id":"K0436","weight":1},{"id":"K0440","weight":1},{"id":"K0452","weight":1},{"id":"K0453","weight":1},{"id":"K0454","weight":1},{"id":"K0455","weight":1},{"id":"K0456","weight":1},{"id":"K0457","weight":1},{"id":"K0458","weight":1},{"id":"K0459","weight":1},{"id":"K0469","weight":1},{"id":"K0470","weight":1},{"id":"K0471","weight":1},{"id":"K0472","weight":1},{"id":"K0473","weight":1},{"id":"K0474","weight":1},{"id":"K0475","weight":1},{"id":"K0481","weight":1},{"id":"K0486","weight":1},{"id":"K0487","weight":1},{"id":"K0488","weight":1},{"id":"K0493","weight":1},{"id":"K0500","weight":1},{"id":"K0501","weight":1},{"id":"K0515","weight":1},{"id":"K0525","weight":1},{"id":"K0526","weight":1},{"id":"K0527","weight":1},{"id":"K0530","weight":1},{"id":"K0536","weight":1},{"id":"K0537","weight":1},{"id":"K0538","weight":1},{"id":"K0544","weight":1},{"id":"K0548","weight":1},{"id":"K0549","weight":1},{"id":"K0555","weight":1},{"id":"K0561","weight":1},{"id":"K0562","weight":1},{"id":"K0563","weight":1},{"id":"K0564","weight":1},{"id":"K0565","weight":1},{"id":"K0568","weight":1},{"id":"K0572","weight":1},{"id":"K0578","weight":1},{"id":"K0593","weight":1},{"id":"K0595","weight":1},{"id":"K0596","weight":1},{"id":"K0597","weight":1},{"id":"K0602","weight":1},{"id":"K0603","weight":1},{"id":"K0604","weight":1},{"id":"K0608","weight":1},{"id":"K0609","weight":1},{"id":"K0610","weight":1},{"id":"K0612","weight":1},{"id":"K0420","weight":1}]','["K0001","K0002","K0003","K0202","K0004","K0005","K0205","K0006","K0206","K0007","K0214","K0009","K0219","K0013","K0221","K0024","K0222","K0033","K0037","K0224","K0040","K0225","K0041","K0230","K0236","K0042","K0271","K0044","K0046","K0273","K0277","K0049","K0290","K0058","K0292","K0059","K0297","K0060","K0298","K0061","K0301","K0302","K0062","K0069","K0303","K0307","K0070","K0309","K0071","K0310","K0085","K0314","K0098","K0315","K0318","K0099","K0324","K0104","K0326","K0105","K0331","K0106","K0332","K0107","K0334","K0111","K0335","K0112","K0336","K0113","K0339","K0115","K0340","K0119","K0342","K0129","K0344","K0345","K0352","K0131","K0355","K0132","K0356","K0135","K0362","K0142","K0363","K0143","K0364","K0144","K0367","K0371","K0145","K0386","K0387","K0388","K0389","K0390","K0391","K0392","K0147","K0393","K0150","K0394","K0395","K0157","K0161","K0397","K0162","K0398","K0165","K0401","K0167","K0402","K0404","K0405","K0406","K0185","K0407","K0189","K0408","K0192","K0409","K0410","K0411","K0430","K0432","K0433","K0434","K0435","K0436","K0440","K0452","K0453","K0454","K0455","K0456","K0457","K0458","K0459","K0469","K0470","K0471","K0472","K0473","K0474","K0475","K0481","K0486","K0487","K0488","K0493","K0500","K0501","K0515","K0525","K0526","K0527","K0530","K0536","K0537","K0538","K0544","K0548","K0549","K0555","K0561","K0562","K0563","K0564","K0565","K0568","K0572","K0578","K0593","K0595","K0596","K0597","K0602","K0603","K0604","K0608","K0609","K0610","K0612","K0420"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISB0500LAWMGT","ISB0300WLAQF","CRYPT07RZ","NS0403GFJSYSJ","SS0503PWN","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","SS0400AC","ISC0100WEB","SS0900YYXX","ISTB0103VFREDIS","SS0600DBSEC","ISTB0404DBGL","ISTB0103VFORACLE","ISTB0405JSJZW","ISTB0402VFLINUX","CS0303SJKSH","CS0304SJFXCL","CS0201MLADM","CS0305HADOOP","CS0306BIGDATA","NS0301NSAML","ISTB0103PCXSJ","ISTB0103JCXSJ","ISTB0103VFJAVASE","ISTB0103VFMYSQL","ISTB0103VFSERVLET","ISTB0402CZXTYL","CS0400NRGK","NS0200IDSA","CRYPT09GL","SS0100AQGN","NS0101AQYSVD2","NS0504WEBAQ","NS0501WEBUG","NS0503DVWA","NS0505WEBSZ","NS0503WEB","CS0600YSBH","ISTB0304WXTX","ISTB0103VFBOOTSTRAP","ISTB0301SSCI","ISTB0302TXYL","ISTB0303JCBM","ISC0101TOOLS","ISC0100MISC","CS0501XXYC","CS0307DJYSJ","CS0200WLSJHQ","ISC0102XMAN17","ISC0102XMAN16","SS0200SBAQ","ISTB0103HTML","ISTB0103VFJQUERY","ISTB0103VFHTML","ISTB0103VFJAVASCRIPT","SS1001DZSJQZ","NS0400FH","ISTB0106INFT","ISB0100XXAQ","NS0101AQYSVD4","NS0101AQYSVD6","SS0801TCB","CRYPT06QM","ISC0100PWN","ISTB0103VFCSS","ISTB0405VT","NS0101AQYSVD5"]',50,'[]','/cpms/rtks_role/PenetrationTestingEngineer.png');
INSERT INTO "rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions","avatar") VALUES(122,'e25dc8c9d15ddd621f16616083d68726','安全开发工程师','专注于设计与开发安全相关的软件、工具及系统，通过代码层面的安全能力构建，从源头预防漏洞，提升产品或系统的安全防护水平。','2021-10-15 08:54:27.000000000','2022-01-10 10:32:31.000000000','2f2a09ed40e6166f8a54f136f7f4a25d','','["T0014","T0016","T0303","T0304","T0311","T0324","T0327","T0329","T0428","T0455","T0563"]','[{"id":"K0001","weight":1},{"id":"K0003","weight":1},{"id":"K0004","weight":1},{"id":"K0014","weight":1},{"id":"K0015","weight":1},{"id":"K0016","weight":1},{"id":"K0018","weight":1},{"id":"K0019","weight":1},{"id":"K0023","weight":1},{"id":"K0039","weight":1},{"id":"K0052","weight":1},{"id":"K0060","weight":1},{"id":"K0068","weight":1},{"id":"K0069","weight":1},{"id":"K0080","weight":1},{"id":"K0082","weight":1},{"id":"K0119","weight":1},{"id":"K0140","weight":1},{"id":"K0178","weight":1},{"id":"K0224","weight":1},{"id":"K0372","weight":1},{"id":"K0395","weight":1},{"id":"K0396","weight":1},{"id":"K0397","weight":1},{"id":"K0420","weight":1}]','["K0001","K0003","K0004","K0014","K0015","K0016","K0018","K0019","K0023","K0039","K0052","K0060","K0068","K0069","K0080","K0082","K0119","K0140","K0178","K0224","K0372","K0395","K0396","K0397","K0420"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISB0300WLAQF","ISTB0105DSA","CS0302SJFXSF","CS0201MLADM","NS0301NSAML","ISTB0103PCXSJ","ISTB0103JS","ISTB0103PHP","ISTB0103HTML","ISTB0103ASM","ISTB0103CYY","ISTB0103GOLANG","ISTB0103VFJSP","ISTB0103VFJAVASE","ISTB0103VFHTML","CRYPT02FZ","CRYPT03XL","CRYPT05GY","CRYPT04HASH","ISC0100CRYPTO","CRYPT09GL","CRYPT01CRYPTO","CRYPT06QM","CRYPT07RZ","CRYPT08XY","CRYPT09YS","ISTB0404DBEXP","ISTB0103VFREDIS","SS0600DBSEC","ISTB0404DBGL","ISTB0103VFORACLE","SS0700SSEC","SS0900YYXX","ISB0200INFOMATH","ISTB0104MATH","ISTB0301SSCI","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","ISTB0103WLBC","ISTB0103VFMYSQL","SS1100ANDROID","ISTB0402VFLINUX","ISTB0403BYXT","ISTB0402CZXTYL","ISTB0103JCXSJ"]',50,'[]','/cpms/rtks_role/SecurityDevelopmentEngineer.png');
INSERT INTO "rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions","avatar") VALUES(123,'aeec2fc773691f459105b0323c3e1711','安全取证工程师','负责收集、分析网络安全事件中的电子证据，还原攻击过程，为网络犯罪调查、法律诉讼或企业内部安全事件溯源提供技术支持。','2021-10-15 08:55:09.000000000','2022-01-10 10:05:30.000000000','658a4e7c8b496e0a690103ff2793048f','','["T0048","T0172","T0216","T0238","T0286","T0289","T0532"]','[{"id":"K0001","weight":1},{"id":"K0002","weight":1},{"id":"K0003","weight":1},{"id":"K0004","weight":1},{"id":"K0006","weight":1},{"id":"K0017","weight":1},{"id":"K0021","weight":1},{"id":"K0042","weight":1},{"id":"K0060","weight":1},{"id":"K0070","weight":1},{"id":"K0077 ","weight":1},{"id":"K0078","weight":1},{"id":"K0099","weight":1},{"id":"K0109","weight":1},{"id":"K0117","weight":1},{"id":"K0118","weight":1},{"id":"K0119","weight":1},{"id":"K0122","weight":1},{"id":"K0123","weight":1},{"id":"K0125","weight":1},{"id":"K0128","weight":1},{"id":"K0131","weight":1},{"id":"K0132","weight":1},{"id":"K0133","weight":1},{"id":"K0134","weight":1},{"id":"K0145","weight":1},{"id":"K0155","weight":1},{"id":"K0156","weight":1},{"id":"K0167","weight":1},{"id":"K0168","weight":1},{"id":"K0179","weight":1},{"id":"K0182","weight":1},{"id":"K0183","weight":1},{"id":"K0184","weight":1},{"id":"K0185","weight":1},{"id":"K0186","weight":1},{"id":"K0187","weight":1},{"id":"K0188","weight":1},{"id":"K0189","weight":1},{"id":"K0305","weight":1}]','["K0001","K0002","K0003","K0004","K0006","K0017","K0021","K0042","K0060","K0070","K0077 ","K0078","K0099","K0109","K0117","K0118","K0119","K0122","K0123","K0125","K0128","K0131","K0132","K0133","K0134","K0145","K0155","K0156","K0167","K0168","K0179","K0182","K0183","K0184","K0185","K0186","K0187","K0188","K0189","K0305"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISB0500LAWMGT","ISB0300WLAQF","SS0503PWN","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","NS0504WEBAQ","NS0501WEBUG","NS0503DVWA","NS0505WEBSZ","NS0503WEB","ISTB0304WXTX","CS0307DJYSJ","NS0101AQYS","NS0101AQYSVD1","SS1201ICS","ISTB0405WLFWJS","SS1001DZSJQZ","ISTB0402CZXTYL","ISC0101TOOLS","ISC0100RE","CS0501XXYC"]',50,'[]','/cpms/rtks_role/SecurityForensicsEngineer.png');
INSERT INTO "rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions","avatar") VALUES(124,'f7753c4236c6b6981a577118a686aeb3','逆向分析工程师','通过反汇编、反编译等技术，对软件、固件或恶意代码进行逆向分析，以识别漏洞、提取功能逻辑或分析恶意行为，为安全防御提供技术支撑。','2021-10-15 08:56:45.000000000','2022-01-10 10:50:54.000000000','6e2bfa5b7fd30825e784d1d4bdfa9b6d','','["T0077","T0176","T0179","T0182","T0260","T0287","T0288","T0296","T0385","T0396","T0398","T0403","T0411"]','[{"id":"K0001","weight":1},{"id":"K0051","weight":1},{"id":"K0060","weight":1},{"id":"K0061","weight":1},{"id":"K0062","weight":1},{"id":"K0097","weight":1},{"id":"K0117","weight":1},{"id":"K0118","weight":1},{"id":"K0129","weight":1},{"id":"K0132","weight":1},{"id":"K0171","weight":1},{"id":"K0175","weight":1},{"id":"K0183","weight":1},{"id":"K0186","weight":1},{"id":"K0188","weight":1},{"id":"K0189","weight":1},{"id":"K0224","weight":1},{"id":"K0271","weight":1},{"id":"K0301","weight":1},{"id":"K0302","weight":1},{"id":"K0305","weight":1},{"id":"K0307","weight":1},{"id":"K0309","weight":1},{"id":"K0360","weight":1},{"id":"K0369","weight":1},{"id":"K0433","weight":1},{"id":"K0479","weight":1},{"id":"K0480","weight":1},{"id":"K0493","weight":1},{"id":"K0515","weight":1},{"id":"K0537","weight":1},{"id":"K0609","weight":1},{"id":"K0610","weight":1}]','["K0001","K0051","K0060","K0061","K0062","K0097","K0117","K0118","K0129","K0132","K0171","K0175","K0183","K0186","K0188","K0189","K0224","K0271","K0301","K0302","K0305","K0307","K0309","K0360","K0369","K0433","K0479","K0480","K0493","K0515","K0537","K0609","K0610"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISTB0103ASM","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","CS0400NRGK","ISC0100RE","ISC0101TOOLS","ISTB0402VFLINUX","ISTB0402CZXTYL","CS0501XXYC","SS1001DZSJQZ","ISTB0405JSJZW","SS0400AC","ISTB0405VT"]',50,'[]','/cpms/rtks_role/ReverseAnalysisEngineer.png');
INSERT INTO "rtks_role"("id","resource_id","name","description","create_time","update_time","level_id","category","task_ids","knowledge_id_weights","knowledge_ids","skill_id_weights","skill_ids","ability_ids","skill_courses","knowledge_courses","knowledge_weight","skill_missions","avatar") VALUES(125,'542fc3e2caf0fa9b4935432ffe4daafa','安全运维工程师','负责企业安全基础设施的日常运维、安全事件响应及风险管控，通过监控、配置与优化安全设备，确保系统持续抵御网络攻击。','2021-10-15 08:57:17.000000000','2022-01-10 10:47:53.000000000','cbe07af2274f5d5ae136c8dd0afdaf96','','["T0158","T0159","T0160","T0162","T0163","T0164"]','[{"id":"K0001","weight":1},{"id":"K0033","weight":1},{"id":"K0034","weight":1},{"id":"K0035","weight":1},{"id":"K0041","weight":1},{"id":"K0042","weight":1},{"id":"K0049","weight":1},{"id":"K0050","weight":1},{"id":"K0051","weight":1},{"id":"K0056","weight":1},{"id":"K0057","weight":1},{"id":"K0060","weight":1},{"id":"K0088","weight":1},{"id":"K0089","weight":1},{"id":"K0099","weight":1},{"id":"K0149","weight":1},{"id":"K0150","weight":1},{"id":"K0151","weight":1},{"id":"K0219","weight":1},{"id":"K0220","weight":1},{"id":"K0224","weight":1},{"id":"K0264","weight":1},{"id":"K0265","weight":1},{"id":"K0302","weight":1},{"id":"K0516","weight":1},{"id":"K0555","weight":1}]','["K0001","K0033","K0034","K0035","K0041","K0042","K0049","K0050","K0051","K0056","K0057","K0060","K0088","K0089","K0099","K0149","K0150","K0151","K0219","K0220","K0224","K0264","K0265","K0302","K0516","K0555"]','[]','[]','[]','[]','["NS0100SEC","NS0300AQXY","ISTB0401JSJZCYL","ISTB0405JSJWL","ISTB0405CM","ISTB0405JSJZW","SS0400AC","ISTB0405WLFWJS","ISTB0304WXTX","ISTB0402VFLINUX","ISTB0103ASM","SS0200SBAQ","SS0501CZXTAQ","SS0503LINUX","SS0502WIN","NS0101AQYSVD2","NS0101AQYSVD3","ISTB0101DZDL","SS0300KKXJS","ISTB0102EDA"]',50,'[]','/cpms/rtks_role/SecurityOperationMaintenanceEngineer.png');

SET IDENTITY_INSERT "rtks_role" OFF;
SET IDENTITY_INSERT "rtks_role_match_record" ON;
SET IDENTITY_INSERT "rtks_role_match_record" OFF;
SET IDENTITY_INSERT "rtks_role_related" ON;
SET IDENTITY_INSERT "rtks_role_related" OFF;
SET IDENTITY_INSERT "rtks_role_skill_relation" ON;
SET IDENTITY_INSERT "rtks_role_skill_relation" OFF;
SET IDENTITY_INSERT "rtks_situation_log" ON;
SET IDENTITY_INSERT "rtks_situation_log" OFF;
SET IDENTITY_INSERT "rtks_skill" ON;
SET IDENTITY_INSERT "rtks_skill" OFF;
SET IDENTITY_INSERT "rtks_skill_category" ON;
SET IDENTITY_INSERT "rtks_skill_category" OFF;
SET IDENTITY_INSERT "rtks_skill_mission_relation" ON;
SET IDENTITY_INSERT "rtks_skill_mission_relation" OFF;
SET IDENTITY_INSERT "rtks_skill_system" ON;
SET IDENTITY_INSERT "rtks_skill_system" OFF;
SET IDENTITY_INSERT "rtks_task" ON;
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(42,'T0023','描述和分析网络流量以识别网络资源的异常活动和潜在威胁。','2021-10-14 15:41:45.000000000','2021-10-14 15:41:45.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(46,'T0024','收集并维护满足系统网络空间安全报告所需的数据。','2021-10-14 15:48:20.000000000','2021-10-14 15:48:20.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(47,'T0027','对日志文件、证据和其他信息进行分析，以确定识别对网络入侵者的最佳方法。','2021-10-14 15:48:31.000000000','2021-10-14 15:48:31.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(48,'T0028','进行和/或支持对网络资产的授权渗透测试。','2021-10-14 15:48:45.000000000','2021-10-14 15:48:45.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(49,'T0120','识别，收集，并抓住文件或物理证据，包括数字媒体和与网络入侵事件，调查 和操作相关的日志。','2021-10-14 15:49:10.000000000','2021-10-14 15:49:10.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(50,'T0161','执行日志文件的分析，从各种来源(例如，个别主机日志，网络流量日志，防 火墙日志和入侵检测系统[ IDS ]日志)，以确定可能对网络安全的威胁。','2021-10-14 15:49:22.000000000','2021-10-14 15:49:22.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(51,'T0165','执行动态分析，启动一个驱动器的“图像”(不必有原驱动器)，在本机环境 查看用户可能已经看到的入侵。','2021-10-14 15:49:37.000000000','2021-10-14 15:49:37.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(52,'T0166','对企业内部各种来源收集的信息执行事件相关性，以获得态势感知，并确定所 观察到的攻击的有效性。','2021-10-14 15:49:52.000000000','2021-10-14 15:49:52.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(53,'T0175','执行实时网络空间防御事件处理(例如，法庭收集，入侵相关和跟踪，威胁分析和直接系统补救)任务，以支持可部署的事件响应小组(IRT)。','2021-10-14 15:50:02.000000000','2021-10-14 15:50:02.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(54,'T0180','在专门的网络空间防御应用和系统(例如，防病毒，审计和修复)或虚拟专用 网络(VPN)设备上执行系统管理，包括安装，配置，维护，备份和恢复。','2021-10-14 15:50:15.000000000','2021-10-14 15:50:15.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(55,'T0181','当应用程序或系统发生重大变化时执行风险分析(例如，威胁，漏洞和事件概 率)。','2021-10-14 15:50:29.000000000','2021-10-14 15:50:29.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(56,'T0198','提供与网络空间防御做法相关的网络事件和活动的每日总结报告。','2021-10-14 15:50:42.000000000','2021-10-14 15:50:42.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(57,'T0214','接收和分析企业内各种来源的网络警报，并确定此类警报的可能原因。','2021-10-14 15:50:53.000000000','2021-10-14 15:50:53.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(58,'T0229','发现网络空间安全事件或漏洞时，监督或管理保护或纠正措施。','2021-10-14 15:51:05.000000000','2021-10-14 15:51:05.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(59,'T0240','使用网络监控工具捕获和分析与恶意活动相关的网络流量。','2021-10-14 15:51:15.000000000','2021-10-14 15:51:15.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(60,'T0266','根据新的或更新的应用程序执行渗透测试。','2021-10-14 15:51:29.000000000','2021-10-14 15:51:29.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(61,'T0285','在数字介质上执行病毒扫描','2021-10-14 15:51:40.000000000','2021-10-14 15:51:40.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(62,'T0295','针对网络流量使用包分析工具验证入侵检测系统(IDS)警报。','2021-10-14 15:51:51.000000000','2021-10-14 15:51:51.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(63,'T0297','基于网络流量识别网络设备的应用程序和操作系统。','2021-10-14 15:52:02.000000000','2021-10-14 15:52:02.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(64,'T0299','识别网络映射和操作系统(OS)指纹识别活动。','2021-10-14 15:52:13.000000000','2021-10-14 15:52:13.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(65,'T0310','协助建立可以在网络空间防御网络工具上实施的签名，以响应 NE 或飞地内的 新的或观察到的威胁。','2021-10-14 15:52:23.000000000','2021-10-14 15:52:23.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(66,'T0360','确定威胁的程度，并建议减轻风险的行动方案或对策。','2021-10-14 15:52:34.000000000','2021-10-14 15:52:34.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(67,'T0397','执行 Windows 注册表分析。','2021-10-14 15:52:45.000000000','2021-10-14 15:52:45.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(68,'T0423','分析计算机生成的威胁用于反情报或犯罪活动。','2021-10-14 15:52:55.000000000','2021-10-14 15:52:55.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(69,'T0430','收集和保存用于起诉计算机犯罪的证据。','2021-10-14 15:53:08.000000000','2021-10-14 15:53:08.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(70,'T0432','收集和分析入侵工件(例如，源代码，恶意软件和系统配置)，并使用发现的 数据来减少企业内潜在的网络防御事故。','2021-10-14 15:53:18.000000000','2021-10-14 15:53:18.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(71,'T0433','对日志文件，证据和其他信息进行分析，以确定识别网络入侵或其他犯罪行为 的最佳方法。','2021-10-14 15:53:31.000000000','2021-10-14 15:53:31.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(72,'T0518','进行安全审查，识别架构中的安全漏洞。','2021-10-14 15:53:42.000000000','2021-10-14 15:53:42.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(73,'T0565','分析收到的搜集请求','2021-10-14 15:53:51.000000000','2021-10-14 15:53:51.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(74,'T0570','利用已经被授权的网络空间能力，来访问目标网络','2021-10-14 15:54:03.000000000','2021-10-14 15:54:03.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(75,'T0572','进行网络空间收集、环境准备和具备职业能力，让新的利用和/或持续的收集操 作或可支持客户需求的东西(可以运作)','2021-10-14 15:54:15.000000000','2021-10-14 15:54:15.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(76,'T0593','做威胁和/或目标当前情况的简报','2021-10-14 15:54:25.000000000','2021-10-14 15:54:25.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(77,'T0605','对在组织结构搜集目标的搜集管理活动中获得的经验教训进行汇总','2021-10-14 15:54:34.000000000','2021-10-14 15:54:34.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(78,'T0606','对情报的所有来源数据或特定目标的漏洞价值数据进行汇总、集成和/或解释。','2021-10-14 15:54:44.000000000','2021-10-14 15:54:44.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(79,'T0609','对无线计算机和数字网络进行访问授权','2021-10-14 15:54:53.000000000','2021-10-14 15:54:53.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(80,'T0610','对无线计算机和数字网络进行(信息)收集和处理','2021-10-14 15:55:03.000000000','2021-10-14 15:55:03.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(81,'T0616','对网络中的系统进行网络探测和漏洞分析','2021-10-14 15:55:12.000000000','2021-10-14 15:55:12.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(82,'T0686','识别威胁漏洞','2021-10-14 15:55:23.000000000','2021-10-14 15:55:23.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(83,'T0687','识别蓝牙漏洞威胁','2021-10-14 15:55:34.000000000','2021-10-14 15:55:34.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(84,'T0696','使用各种方法或工具，渗透利用网络设备、安全设备和/或终端设备或环境。','2021-10-14 15:56:38.000000000','2021-10-14 15:56:38.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(85,'T0703','收集和分析数据(比如有效性措施)确定有效性，并提供后续活动的报告。','2021-10-14 15:56:51.000000000','2021-10-14 15:56:51.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(86,'T0705','结合情报获取和反情报窃取(情况)支持计划制定','2021-10-14 15:57:02.000000000','2021-10-14 15:57:02.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(87,'T0708','识别威胁的手段和方法','2021-10-14 15:57:13.000000000','2021-10-14 15:57:13.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(88,'T0710','识别、评估威胁的关键能力、要求和漏洞','2021-10-14 15:57:24.000000000','2021-10-14 15:57:24.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(89,'T0715','确定收集的差距，和针对目标潜在的收集策略。','2021-10-14 15:57:33.000000000','2021-10-14 15:57:33.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(90,'T0718','识别情报的差距和不足','2021-10-14 15:57:43.000000000','2021-10-14 15:57:43.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(91,'T0719','识别网络空间情报差距和不足','2021-10-14 15:57:54.000000000','2021-10-14 15:57:54.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(92,'T0845','识别网络威胁的策略和方法。','2021-10-14 15:58:06.000000000','2021-10-14 15:58:06.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(93,'T0848','向搜集管理者提供反馈，以加强未来收集和分析。','2021-10-14 15:58:19.000000000','2021-10-14 15:58:19.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(94,'T0048','创建一个可靠的、重复的证据取证(即法医图像)，确保原始证据不会被无意 修改，用于数据的恢复和分析过程。包括但不限于硬盘、软盘、光盘、掌上电 脑、手机、GPS、和所有的磁带格式文件。','2021-10-14 15:59:38.000000000','2021-10-14 15:59:38.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(95,'T0172','执行实时取证分析(例如，将 Helix 与 LiveView 结合使用)。','2021-10-14 15:59:50.000000000','2021-10-14 15:59:50.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(96,'T0216','识别并准确地报告指示特定操作系统的取证工件。','2021-10-14 16:00:02.000000000','2021-10-14 16:00:02.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(97,'T0238','使用数据克隆技术(例如，取证工具包 [FTK]，Foremost)提取数据。','2021-10-14 16:00:12.000000000','2021-10-14 16:00:12.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(98,'T0286','执行文件系统取证分析。','2021-10-14 16:00:26.000000000','2021-10-14 16:00:26.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(99,'T0289','部署的取证工具包以在必要时支持操作。','2021-10-14 16:00:35.000000000','2021-10-14 16:00:35.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(100,'T0532','为恢复潜在相关信息，对取证图像和其他数据源(比如易失数据)进行审查。','2021-10-14 16:00:47.000000000','2021-10-14 16:00:47.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(101,'T0077','开发安全代码和错误处理。','2021-10-14 16:01:06.000000000','2021-10-14 16:01:06.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(102,'T0176','执行安全编程和识别代码中的潜在缺陷以减少漏洞。','2021-10-14 16:01:15.000000000','2021-10-14 16:01:15.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(103,'T0179','执行静态媒介分析。','2021-10-14 16:01:26.000000000','2021-10-14 16:01:26.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(104,'T0182','执行第 1，2，和 3 层恶意软件分析。','2021-10-14 16:01:39.000000000','2021-10-14 16:01:39.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(105,'T0260','分析确定的恶意活动，以确定利用的弱点，利用方法，对系统和信息的影响。','2021-10-14 16:01:49.000000000','2021-10-14 16:01:49.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(107,'T0287','执行静态分析以安装驱动器的“映像”(不必具有原始驱动器)。','2021-10-14 16:02:14.000000000','2021-10-14 16:02:14.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(108,'T0288','执行静态恶意软件分析。','2021-10-14 16:02:27.000000000','2021-10-14 16:02:27.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(109,'T0296','隔离并删除恶意软件。','2021-10-14 16:02:36.000000000','2021-10-14 16:02:36.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(110,'T0385','基于数据分析和发现，向关键利益相关者提供可行的建议。','2021-10-14 16:02:45.000000000','2021-10-14 16:02:45.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(111,'T0396','根据分析师的目标使用适当的工具处理图像。','2021-10-14 16:02:54.000000000','2021-10-14 16:02:54.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(113,'T0398','在通过动态分析识别入侵之后，对运行的系统执行文件和注册表监视。','2021-10-14 16:03:18.000000000','2021-10-14 16:03:18.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(114,'T0403','在 Windows 和 UNIX 系统(例如，执行以下任务:解析大型数据文件，自动执 行手动任务以及获取/处理远程数据)中读取，解释，写入，修改和执行简单脚 本(例如 PERL，VBS) 。','2021-10-14 16:03:29.000000000','2021-10-14 16:03:29.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(115,'T0411','识别和/或开发逆向工程工具以增强功能和检测漏洞。','2021-10-14 16:03:44.000000000','2021-10-14 16:03:44.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(116,'T0014','使用安全代码文档。','2021-10-14 16:05:06.000000000','2021-10-14 16:05:06.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(117,'T0016','运用安全性策略以满足系统的安全目标。','2021-10-14 16:05:16.000000000','2021-10-14 16:05:16.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(118,'T0303','在设计和开发安全应用程序时，确定并利用企业级版本控制系统。','2021-10-14 16:05:27.000000000','2021-10-14 16:05:27.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(119,'T0304','实现并将系统开发生命周期(SDLC)方法(例如，IBM 统一开发过程)集成 到开发环境中。','2021-10-14 16:05:41.000000000','2021-10-14 16:05:41.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(120,'T0311','咨询客户关于软件系统设计和维护。','2021-10-14 16:05:51.000000000','2021-10-14 16:05:51.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(121,'T0324','直接软件编程和开发文档。','2021-10-14 16:06:02.000000000','2021-10-14 16:06:02.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(122,'T0327','评估网络基础设施漏洞，以增强正在开发的功能。','2021-10-14 16:06:12.000000000','2021-10-14 16:06:12.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(123,'T0329','遵循软件和系统工程生命周期标准和过程。','2021-10-14 16:06:24.000000000','2021-10-14 16:06:24.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(124,'T0428','分析安全需求和软件需求，以确定在时间和成本限制和安全任务中的设计可行性。','2021-10-14 16:06:36.000000000','2021-10-14 16:06:36.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(125,'T0455','开发软件系统测试和验证程序，编程和文档。','2021-10-14 16:06:47.000000000','2021-10-14 16:06:47.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(126,'T0563','为分析、设计、开发和满足目标要求的能力获取，提供输入项。','2021-10-14 16:06:57.000000000','2021-10-14 16:06:57.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(127,'T0158','参与安全评估和授权过程中的信息安全风险评估。','2021-10-14 16:09:11.000000000','2021-10-14 16:09:11.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(128,'T0159','参与开发或修改计算机环境网络空间安全计划的方案和要求。','2021-10-14 16:09:22.000000000','2021-10-14 16:09:22.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(129,'T0160','修补网络漏洞，确保信息的对外保护。','2021-10-14 16:09:35.000000000','2021-10-14 16:09:35.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(131,'T0162','执行备份和恢复数据库，以确保数据完整性。','2021-10-14 16:10:03.000000000','2021-10-14 19:12:49.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(132,'T0163','执行网络空间防御事件分类，包括确定范围、紧迫性、和潜在的影响;识别特 定的脆弱性;并提出建议，使快速修复。','2021-10-14 16:10:11.000000000','2021-10-14 16:10:11.000000000');
INSERT INTO "rtks_task"("id","resource_id","content","create_time","update_time") VALUES(133,'T0164','进行网络空间防御趋势分析和报告。','2021-10-14 16:10:21.000000000','2021-10-14 16:10:21.000000000');

SET IDENTITY_INSERT "rtks_task" OFF;
SET IDENTITY_INSERT "rtks_user_role" ON;
SET IDENTITY_INSERT "rtks_user_role" OFF;
SET IDENTITY_INSERT "situation_attack_message" ON;
SET IDENTITY_INSERT "situation_attack_message" OFF;
SET IDENTITY_INSERT "situation_config" ON;
SET IDENTITY_INSERT "situation_config" OFF;
SET IDENTITY_INSERT "situation_dynamic_scheme" ON;
SET IDENTITY_INSERT "situation_dynamic_scheme" OFF;
SET IDENTITY_INSERT "situation_guide_history" ON;
SET IDENTITY_INSERT "situation_guide_history" OFF;
SET IDENTITY_INSERT "situation_guide_scheme" ON;
SET IDENTITY_INSERT "situation_guide_scheme" OFF;
SET IDENTITY_INSERT "situation_scene_config" ON;
SET IDENTITY_INSERT "situation_scene_config" OFF;
SET IDENTITY_INSERT "system_property" ON;
INSERT INTO "system_property"("id","name","desc","content") VALUES(1,'export_builtin','是否可以导出内置资源','false');
INSERT INTO "system_property"("id","name","desc","content") VALUES(2,'isShowSituation','是否显示态势导调','true');

SET IDENTITY_INSERT "system_property" OFF;
SET IDENTITY_INSERT "system_use_status" ON;
INSERT INTO "system_use_status"("id","cpu","disk","ram","alter_time","update_time") VALUES(1,'7.0','13.0',14,'2025-06-25 11:31:05.000000000',null);
INSERT INTO "system_use_status"("id","cpu","disk","ram","alter_time","update_time") VALUES(2,'0.2','13.0',14,'2025-06-25 11:36:05.000000000',null);
INSERT INTO "system_use_status"("id","cpu","disk","ram","alter_time","update_time") VALUES(3,'0.4','13.0',14,'2025-06-25 11:41:05.000000000',null);
INSERT INTO "system_use_status"("id","cpu","disk","ram","alter_time","update_time") VALUES(4,'0.7','13.0',13,'2025-06-25 11:46:05.000000000',null);
INSERT INTO "system_use_status"("id","cpu","disk","ram","alter_time","update_time") VALUES(5,'3.2','15.0',13,'2025-06-25 11:51:05.000000000',null);

SET IDENTITY_INSERT "system_use_status" OFF;
SET IDENTITY_INSERT "video_progress" ON;
SET IDENTITY_INSERT "video_progress" OFF;
