insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('3bd37605863a42b6a4345be99ebb1b68', '侦察', 'Reconnaissance');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('d1b19920f21e44d789ac668411d24e12', '3bd37605863a42b6a4345be99ebb1b68', '主动扫描', 'Active Scanning');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('529f69ac92e041448bb3e2fc9813a31e', '3bd37605863a42b6a4345be99ebb1b68', '收集目标主机信息', 'Gather Victim Host Information');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('a96b2ecd4495448ea7d54c93501cd4e2', '3bd37605863a42b6a4345be99ebb1b68', '收集目标身份信息', 'Gather Victim Identity Information');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('4e170d8e0dab4eaca6c916ccd8a48c6b', '3bd37605863a42b6a4345be99ebb1b68', '收集目标网络信息', 'Gather Victim Network Information');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('fd1163fa00b14f17900a9805c73083d3', '3bd37605863a42b6a4345be99ebb1b68', '收集目标组织信息', 'Gather Victim Org Information');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('445b995c050c4a68a1cdad4c4d54e083', '3bd37605863a42b6a4345be99ebb1b68', '信息钓鱼', 'Phishing for Information');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('6be4726aef47474dbd9d221ef6b1cdc0', '3bd37605863a42b6a4345be99ebb1b68', '搜索闭源', 'Search Closed Sources');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('3e0fea09e107412a8342b3813819af60', '3bd37605863a42b6a4345be99ebb1b68', '搜索开放数据库', 'Search Open Technical Databases');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0f5fcb4e96f34c389444c93d5fd54454', '3bd37605863a42b6a4345be99ebb1b68', '搜索开放的网站、域名', 'Search Open Websites/Domains');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('72df8b2120534205b273db0247750888', '3bd37605863a42b6a4345be99ebb1b68', '搜索目标拥有的网站', 'Search Victim-Owned Websites');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('0c0dd8014c4a471ca7e94bd031746c10', '资源开发', 'Resource Development');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('c33242bc4321424ba30d5ee80525f53a', '0c0dd8014c4a471ca7e94bd031746c10', '获取基础架构', 'Acquire Infrastructure');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('82571065ffe643f2901e3a9f0daf62d2', '0c0dd8014c4a471ca7e94bd031746c10', '入侵账户', 'Compromise Accounts');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('6af1b3277ff14c67bea665b2d492708e', '0c0dd8014c4a471ca7e94bd031746c10', '破坏基础设施', 'Compromise Infrastructure');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('5015ba561c3341249c18d02a64fb9040', '0c0dd8014c4a471ca7e94bd031746c10', '开发能力', 'Develop Capabilities');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('80b0aa737a694188a7f49d6badaca5e6', '0c0dd8014c4a471ca7e94bd031746c10', '建立账户', 'Establish Accounts');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('cab52852bd824fd98d7aabd777f86127', '0c0dd8014c4a471ca7e94bd031746c10', '获得能力', 'Obtain Capabilities');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('2c8617c99a6146208703428629ff544b', '0c0dd8014c4a471ca7e94bd031746c10', '伪装能力', 'Stage Capabilities');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('09c950de3ff84c0ca8b6d68ea9eef000', '初始访问', 'Initial Access');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('d4c20d13b3ae4615a2f2304ddd1ad46c', '09c950de3ff84c0ca8b6d68ea9eef000', '网站挂马攻击', 'Drive-by Compromise');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('526b1489950147e29551dffdf2761cbd', '09c950de3ff84c0ca8b6d68ea9eef000', '利用互联网上应用程序的缺陷', 'Exploit Public-Facing Application');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('7e5fd86518bf4ff9be455c1e5e18d0ea', '09c950de3ff84c0ca8b6d68ea9eef000', '外部远程服务', 'External Remote Services');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('fd78358d5bec449a87010b1f2f0fe357', '09c950de3ff84c0ca8b6d68ea9eef000', '硬件接入', 'Hardware Additions');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('084bc4a7ba1c4bb5812514d423773dac', '09c950de3ff84c0ca8b6d68ea9eef000', '网络钓鱼', 'Phishing');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0c70d58792f642a49cad9f9d149f5911', '09c950de3ff84c0ca8b6d68ea9eef000', '通过可移动介质进行复制', 'Replication Through Removable Media');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('3243637138b44afe8975d10f5fdf80d9', '09c950de3ff84c0ca8b6d68ea9eef000', '供应链攻击', 'Supply Chain Compromise');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('71999010f66c433587adf1fa4ece4ad4', '09c950de3ff84c0ca8b6d68ea9eef000', '可信关系', 'Trusted Relationship');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('d6f626ad587e49f0bdd57a10df373793', '09c950de3ff84c0ca8b6d68ea9eef000', '有效凭证', 'Valid Accounts');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('7167a7721fa44518b2f48c8fba822972', '执行', 'Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b98b9869cf194fb6b87abac623fc786c', '7167a7721fa44518b2f48c8fba822972', '命令行界面', 'Command and Scripting Interpreter');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('d5ffb6a424574ac38958a63dad3fa2b4', '7167a7721fa44518b2f48c8fba822972', '容器管理命令', 'Container Administration Command');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('04f8cab86ef54bab82f70dad254c7692', '7167a7721fa44518b2f48c8fba822972', '部署容器', 'Deploy Container');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('29a2ccc54b494ddb959bbed1f8e0e402', '7167a7721fa44518b2f48c8fba822972', '利用客户端漏洞获取执行权限', 'Exploitation for Client Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('bdcb85842d354b97b583a593cc4ce6ae', '7167a7721fa44518b2f48c8fba822972', '跨进程通信', 'Inter-Process Communication');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('c4a79ad3c37149039a41b6cc77a80223', '7167a7721fa44518b2f48c8fba822972', '通过API执行', 'Native API');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('c3f66b94eeff40c598f0abbe27c8446a', '7167a7721fa44518b2f48c8fba822972', '计划任务', 'Scheduled Task/Job');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('57e74f475d204616b98b6c2ce738e985', '7167a7721fa44518b2f48c8fba822972', '通过模块加载执行', 'Shared Modules');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('4d100aa7304944b8b9011809ba356a55', '7167a7721fa44518b2f48c8fba822972', '利用第三方软件部署工具', 'Software Deployment Tools');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0f7df937e4f1444ab518f810c3644c7e', '7167a7721fa44518b2f48c8fba822972', '系统服务', 'System Services');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('c85335978dfa4ca09703179a6f51ef15', '7167a7721fa44518b2f48c8fba822972', '用户执行', 'User Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('3cd04aa3baf04fcdba01f720c00168b5', '7167a7721fa44518b2f48c8fba822972', 'Windows管理规范技术', 'Windows Management Instrumentation');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('3153a2a94097465eb1cc3c36330f2d90', '持久化', 'Persistence');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('f2affb19d3834fe88fd9c1a1c060260d', '3153a2a94097465eb1cc3c36330f2d90', '账户修改、监控', 'Account Manipulation');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('6e24df2687ce4d6cb1f17948e3ad0a98', '3153a2a94097465eb1cc3c36330f2d90', 'BITS作业', 'BITS Jobs');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('3b04ca02875a4c199db996b14b6d4927', '3153a2a94097465eb1cc3c36330f2d90', '启动或登录自动执行', 'Boot or Logon Autostart Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('89747db41902496eaec8eb5a56bc8fe7', '3153a2a94097465eb1cc3c36330f2d90', '登录脚本', 'Boot or Logon Initialization Scripts');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('ff32f8976b2d44d99bcf798b889cbedc', '3153a2a94097465eb1cc3c36330f2d90', '浏览器扩展', 'Browser Extensions');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('9b2ff3fd2a6f4b5eb010345a9d0f6072', '3153a2a94097465eb1cc3c36330f2d90', '攻击客户的软件二进制文件', 'Compromise Client Software Binary');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('dfe41ad5af544dbd93d984cbaf156b0c', '3153a2a94097465eb1cc3c36330f2d90', '创建账户', 'Create Account');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('7f2083b57c6245969eacdb7ea1bf1b4e', '3153a2a94097465eb1cc3c36330f2d90', '创建或修改系统进程', 'Create or Modify System Process');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('4e4960bd9c774e429de69b45decee583', '3153a2a94097465eb1cc3c36330f2d90', '事件触发执行', 'Event Triggered Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('de86e967abf24d91872e62c504ef7239', '3153a2a94097465eb1cc3c36330f2d90', '外部远程服务', 'External Remote Services');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b5c6980ed1d04b2bb099d745f75dc199', '3153a2a94097465eb1cc3c36330f2d90', '劫持执行流量', 'Hijack Execution Flow');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('a355f84cdbb74058919ccb4d73bc3dd7', '3153a2a94097465eb1cc3c36330f2d90', '植入容器镜像', 'Implant Internal Image');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('91344e6094b448848649029d69b83ffd', '3153a2a94097465eb1cc3c36330f2d90', '修改身份验证流程', 'Modify Authentication Process');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('5ceb7dd6b66a4a1081944ab8e7d18e59', '3153a2a94097465eb1cc3c36330f2d90', '启动Office应用程序', 'Office Application Startup');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('626ef5e60d824b35bafd76818d25e7b4', '3153a2a94097465eb1cc3c36330f2d90', '预启动操作系统', 'Pre-OS Boot');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('60547a7c96ff4ad7a8fc706a0eaf9ccb', '3153a2a94097465eb1cc3c36330f2d90', '计划任务', 'Scheduled Task/Job');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('e2a539d7497447589f11d624180b5c10', '3153a2a94097465eb1cc3c36330f2d90', '服务器软件组件', 'Server Software Component');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('799cea8942af4bdc94766b809eee9e87', '3153a2a94097465eb1cc3c36330f2d90', '流量特征', 'Traffic Signaling');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('ef97c2f61fb84fbd85d965d12d48e2d6', '3153a2a94097465eb1cc3c36330f2d90', '有效凭证', 'Valid Accounts');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('b652efb941e44493957281adb50e016b', '特权提升', 'Privilege Escalation');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('73d6727ec9ec477f80ac974d78bc2a6c', 'b652efb941e44493957281adb50e016b', '滥用提权控制机制', 'Abuse Elevation Control Mechanism');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('3ac10dc53d914e51b9d56315c64d9a70', 'b652efb941e44493957281adb50e016b', '篡改访问令牌', 'Access Token Manipulation');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('03f8e0e873df45a1a1fa0c85d78f0fb1', 'b652efb941e44493957281adb50e016b', '启动或登录自动执行', 'Boot or Logon Autostart Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('52a3c9b9ebc848c7a037e5cec6bf6337', 'b652efb941e44493957281adb50e016b', '登录脚本', 'Boot or Logon Initialization Scripts');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('77767000a1a44f29a97872b5e84a4161', 'b652efb941e44493957281adb50e016b', '创建或修改系统进程', 'Create or Modify System Process');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0d178c429f584dcda666db1a03333153', 'b652efb941e44493957281adb50e016b', '修改组策略', 'Domain Policy Modification');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('4f9ac7bfdd324eaf90fe25175a9c9d3c', 'b652efb941e44493957281adb50e016b', '主机逃逸', 'Escape to Host');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('ae36f03fb5ad4f619479154896b895d7', 'b652efb941e44493957281adb50e016b', '事件触发执行', 'Event Triggered Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('487d94c9bf65424585fcffb24e23dc23', 'b652efb941e44493957281adb50e016b', '利用漏洞进行权限升级', 'Exploitation for Privilege Escalation');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('935197ab99914b51b6217a944aea326c', 'b652efb941e44493957281adb50e016b', '劫持执行流量', 'Hijack Execution Flow');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('05380ccc454241e291702487e8fd981f', 'b652efb941e44493957281adb50e016b', '进程注入', 'Process Injection');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0087aab3de584324b8657c9a408679f0', 'b652efb941e44493957281adb50e016b', '计划任务', 'Scheduled Task/Job');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('aa63062a40034832853ceb76b1ce23a4', 'b652efb941e44493957281adb50e016b', '有效凭证', 'Valid Accounts');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('76c1af082bc7445cbc62f57e295e0228', '逃避防御', 'Defense Evasion');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('30ddee65191240e3a6abda1307ea1ab5', '76c1af082bc7445cbc62f57e295e0228', '滥用提权控制机制', 'Abuse Elevation Control Mechanism');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('a6ce851f087e4f76b5b41fac8a9873f9', '76c1af082bc7445cbc62f57e295e0228', '篡改访问令牌', 'Access Token Manipulation');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('69bf8415c82c4630b7636456251ee182', '76c1af082bc7445cbc62f57e295e0228', 'BITS  作业', 'BITS Jobs');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('ad5f465910a44d8aa56247886002d663', '76c1af082bc7445cbc62f57e295e0228', '在主机上构建镜像', 'Build Image on Host');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('e62f9b3312554700867fc88a27afaac2', '76c1af082bc7445cbc62f57e295e0228', '反混淆/解码文件或信息', 'Deobfuscate/Decode Files or Information');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('bd1a8a79a2f8472bb057d2bdd78d95f7', '76c1af082bc7445cbc62f57e295e0228', '部署容器', 'Deploy Container');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('57cf01e27da84f89be2673e0b7501231', '76c1af082bc7445cbc62f57e295e0228', '直接访问卷', 'Direct Volume Access');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('1f1db4a4b4c040559fc7eb99b98a6042', '76c1af082bc7445cbc62f57e295e0228', '修改组策略', 'Domain Policy Modification');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('5ac01d994fbf4084a3ba1013e74d072f', '76c1af082bc7445cbc62f57e295e0228', '执行防护', 'Execution Guardrails');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('9f0b1bb07bb24dea806cc4314160d6bd', '76c1af082bc7445cbc62f57e295e0228', '利用漏洞实现防御绕过', 'Exploitation for Defense Evasion');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b0ea76a125ba41288bd3ca2526339f44', '76c1af082bc7445cbc62f57e295e0228', '修改文件或目录权限', 'File and Directory Permissions Modification');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('f6e03a8f3d55468eb81d9d8482ab33eb', '76c1af082bc7445cbc62f57e295e0228', '隐藏工件', 'Hide Artifacts');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('fc7fc54f0dbe46edbd53a3b511affef7', '76c1af082bc7445cbc62f57e295e0228', '劫持执行流量', 'Hijack Execution Flow');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('c1c4a22655364696930a3255f8c4009d', '76c1af082bc7445cbc62f57e295e0228', '破坏防御', 'Impair Defenses');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('2c9edb2fde9f45c792131eb05c409c4e', '76c1af082bc7445cbc62f57e295e0228', '删除主机上的指标', 'Indicator Removal on Host');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('f56a466e544f4d05a1e62eeec0f6e201', '76c1af082bc7445cbc62f57e295e0228', '间接命令执行', 'Indirect Command Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('251a76e8c6d84a01b03057dda309a9f8', '76c1af082bc7445cbc62f57e295e0228', '伪装', 'Masquerading');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0c54c519fe4242fcb9489aafad154609', '76c1af082bc7445cbc62f57e295e0228', '修改身份验证流程', 'Modify Authentication Process');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b0b8144b556e4a24934db5b298b11af5', '76c1af082bc7445cbc62f57e295e0228', '修改云计算基础架构', 'Modify Cloud Compute Infrastructure');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('6dc6fc66b34f42ee994f258558295380', '76c1af082bc7445cbc62f57e295e0228', '修改注册表', 'Modify Registry');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('04a39c94f04a4fa4954e83dc54f9e4fa', '76c1af082bc7445cbc62f57e295e0228', '修改系统镜像', 'Modify System Image');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('6797bbeddbe344069ea536b7465c8e68', '76c1af082bc7445cbc62f57e295e0228', '网络边界桥接', 'Network Boundary Bridging');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('d03ed496069345efb4c61d7f1ee796b0', '76c1af082bc7445cbc62f57e295e0228', '混淆的文件或信息', 'Obfuscated Files or Information');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('01b2ef8df3404cd9bd06f159b458e36e', '76c1af082bc7445cbc62f57e295e0228', '预启动操作系统', 'Pre-OS Boot');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('95d06161f49a4991935e5a446d26c3eb', '76c1af082bc7445cbc62f57e295e0228', '进程注入', 'Process Injection');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('a79e807b71c74fa48cb1852aa985dd60', '76c1af082bc7445cbc62f57e295e0228', '反射代码加载', 'Reflective Code Loading');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('a182627cdecc47aa9a13f3b1b8409964', '76c1af082bc7445cbc62f57e295e0228', '域控制器', 'Rogue Domain Controller');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('7d49c98518954438bc67839d640aeb9f', '76c1af082bc7445cbc62f57e295e0228', '恶意软件', 'Rootkit');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('086ebd9b71824ea1aa26d7f59acf1ac6', '76c1af082bc7445cbc62f57e295e0228', '利用已签名的二进制文件代理执行', 'Signed Binary Proxy Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('9c110f5f3d6a4f18a10caa701637477c', '76c1af082bc7445cbc62f57e295e0228', '利用已签名脚本代理执行', 'Signed Script Proxy Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('caa3f0005fbb40cebe3d6e043df109bc', '76c1af082bc7445cbc62f57e295e0228', '破坏可信控件', 'Subvert Trust Controls');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b6e40a09bfeb477a9baad8d1cdeb3cb8', '76c1af082bc7445cbc62f57e295e0228', '模板注入', 'Template Injection');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('7a268c756bbc41d5b26c578999f81040', '76c1af082bc7445cbc62f57e295e0228', '流量特征', 'Traffic Signaling');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('bbce173f856e4046a0d941e8e9936dc1', '76c1af082bc7445cbc62f57e295e0228', '受信任的开发工具', 'Trusted Developer Utilities Proxy Execution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b6fcdd8ff1f8449ebf691c09c21f9dcb', '76c1af082bc7445cbc62f57e295e0228', '未使用/不支持的云区域', 'Unused/Unsupported Cloud Regions');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('bebc510e85ed400b94e848001d226578', '76c1af082bc7445cbc62f57e295e0228', '使用备用身份验证材料', 'Use Alternate Authentication Material');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('c1699316dcd442a8941743a8d55d26be', '76c1af082bc7445cbc62f57e295e0228', '有效凭证', 'Valid Accounts');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b4f9cc2483bc4c0b8d37bce4a4c6bb3a', '76c1af082bc7445cbc62f57e295e0228', '逃避虚拟化/沙箱检测', 'Virtualization/Sandbox Evasion');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('113f2fe6f0b247b1b17395dd5e608881', '76c1af082bc7445cbc62f57e295e0228', '弱加密', 'Weaken Encryption');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('8d4cb0cef910463a99b22c7116024ccd', '76c1af082bc7445cbc62f57e295e0228', 'XSL脚本处理', 'XSL Script Processing');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('2dd01c843f03429381fca6c624bef13b', '凭据访问', 'Credential Access');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('dccd50ba17e04438973c9928c646bbe5', '2dd01c843f03429381fca6c624bef13b', '中间人', 'Adversary-in-the-Middle');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('deb00e514a9a45c9810b294ae7936766', '2dd01c843f03429381fca6c624bef13b', '暴力破解', 'Brute Force');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('e6ce4627b21443a8af8df959c4f52adb', '2dd01c843f03429381fca6c624bef13b', '密钥库中的凭据', 'Credentials from Password Stores');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('98700980a536465dbd440bf70acfbfaa', '2dd01c843f03429381fca6c624bef13b', '利用漏洞获取凭证访问的权限', 'Exploitation for Credential Access');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('a2f2e7322c9a4763b8af900598522a09', '2dd01c843f03429381fca6c624bef13b', '强制身份验证', 'Forced Authentication');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('1a352244d82d425eb5fadb1c9b7207ac', '2dd01c843f03429381fca6c624bef13b', '伪造web凭据', 'Forge Web Credentials');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('18bb9da1d25c478f8f7f62fffe56b00f', '2dd01c843f03429381fca6c624bef13b', '捕获用户输入', 'Input Capture');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('fc60168ff50845109bf08eb3f6b3311b', '2dd01c843f03429381fca6c624bef13b', '修改身份验证流程', 'Modify Authentication Process');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('5b39f8e34b4745ab9dcac29503e190a4', '2dd01c843f03429381fca6c624bef13b', '网络嗅探', 'Network Sniffing');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('df5fb123a43c4982ba770d49bd64d3bb', '2dd01c843f03429381fca6c624bef13b', 'OS凭据转储', 'OS Credential Dumping');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('976ac6f2793442e5b11075ec65fbeb10', '2dd01c843f03429381fca6c624bef13b', '窃取应用访问令牌', 'Steal Application Access Token');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('1ce2073e8d394b1eb58822237034ab64', '2dd01c843f03429381fca6c624bef13b', '窃取或伪造Kerberos票据', 'Steal or Forge Kerberos Tickets');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('6edbe26057e141daa9e55aeca877492b', '2dd01c843f03429381fca6c624bef13b', '窃取网络会话Cookie', 'Steal Web Session Cookie');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('822798164de94c96b1685ceac05805fb', '2dd01c843f03429381fca6c624bef13b', '双因素身份验证拦截', 'Two-Factor Authentication Interception');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('fe0956ff6e86479590d43d93d48480ad', '2dd01c843f03429381fca6c624bef13b', '不安全凭据', 'Unsecured Credentials');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('1969e1662f874258910e1c6f0dc8d882', '发现', 'Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('f464e627a28c4a49a8da692d8657e00a', '1969e1662f874258910e1c6f0dc8d882', '账户发现', 'Account Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('675c982d9e1140a2b8c2d6e6b3626a11', '1969e1662f874258910e1c6f0dc8d882', '应用程序窗口发现', 'Application Window Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('818a7976183b4cfd81132898d3c33700', '1969e1662f874258910e1c6f0dc8d882', '发现浏览器书签发', 'Browser Bookmark Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('5e0400a2222f43df9454f996e0d8e503', '1969e1662f874258910e1c6f0dc8d882', '云基础架构发现', 'Cloud Infrastructure Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('62d12588d5f94fbf90c2b3160f756e62', '1969e1662f874258910e1c6f0dc8d882', '云服务仪表盘', 'Cloud Service Dashboard');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('2bdbd1dd152a408dae6ff8e3fccc03df', '1969e1662f874258910e1c6f0dc8d882', '云服务发现', 'Cloud Service Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('920c994d66b94972b15a11181665f296', '1969e1662f874258910e1c6f0dc8d882', '云存储对象发现', 'Cloud Storage Object Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('610897c920da42ed84a25eed2d2792de', '1969e1662f874258910e1c6f0dc8d882', '容器与资源发现', 'Container and Resource Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('1cbe0389b2824857adb4062088a05732', '1969e1662f874258910e1c6f0dc8d882', '域信任发现', 'Domain Trust Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('ca60989313134feb9105561262f28405', '1969e1662f874258910e1c6f0dc8d882', '文件与目录发现', 'File and Directory Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('d35302212a364b7b92c8e8a1ec4a888d', '1969e1662f874258910e1c6f0dc8d882', '组策略发现', 'Group Policy Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('d2b7fffed81a4b9d87c74bf760e41f94', '1969e1662f874258910e1c6f0dc8d882', '网络服务扫描', 'Network Service Scanning');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('91194f4db92a4addb8c3def769956d61', '1969e1662f874258910e1c6f0dc8d882', '网络共享发现', 'Network Share Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('63fec528df9842c5acf86579d59c674e', '1969e1662f874258910e1c6f0dc8d882', '网络嗅探', 'Network Sniffing');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('91f6119bfc8e4dcdb1fc7f3154cfdaa7', '1969e1662f874258910e1c6f0dc8d882', '密码策略发现', 'Password Policy Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b09b145d30854aaca98ad274e088015c', '1969e1662f874258910e1c6f0dc8d882', '外围设备发现', 'Peripheral Device Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('858451ed27694ae1b3fcd5776dbfae96', '1969e1662f874258910e1c6f0dc8d882', '组权限发现', 'Permission Groups Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('997b0e1429b94c93b9ffba7d8a32a8a4', '1969e1662f874258910e1c6f0dc8d882', '进程发现', 'Process Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b117cfdf426d41d090ef3781c1bf0962', '1969e1662f874258910e1c6f0dc8d882', '查询注册表', 'Query Registry');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('c121f9c60fc040898499bced0e80dd02', '1969e1662f874258910e1c6f0dc8d882', '远程系统发现', 'Remote System Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0214e0a20ff24a66a0867e34e11b17a7', '1969e1662f874258910e1c6f0dc8d882', '软件发现', 'Software Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('3e58043b4f9c4a209a6ca3e76dca43fb', '1969e1662f874258910e1c6f0dc8d882', '系统信息发现', 'System Information Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('9e4e3921e1714ae7941a02e615555060', '1969e1662f874258910e1c6f0dc8d882', '系统位置发现', 'System Location Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('7ad8f2cc8be14cc088951b6b59ff1f82', '1969e1662f874258910e1c6f0dc8d882', '系统网络配置发现', 'System Network Configuration Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('2e20f490a08e4868af77bb9c93f664c0', '1969e1662f874258910e1c6f0dc8d882', '系统网络连接发现', 'System Network Connections Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('80c2c382b8634ee780c4e8c444717694', '1969e1662f874258910e1c6f0dc8d882', '系统所有者/用户发现', 'System Owner/User Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('f7445e9a044c49fb9658d48f287a6072', '1969e1662f874258910e1c6f0dc8d882', '系统服务发现', 'System Service Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('64ec24e2ae9942158417601358a53d95', '1969e1662f874258910e1c6f0dc8d882', '系统时间发现', 'System Time Discovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('ebfb3d096d5644ed8273b947965fc6a2', '1969e1662f874258910e1c6f0dc8d882', '逃避虚拟化/沙箱检测', 'Virtualization/Sandbox Evasion');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('ad77b1ef4cdd49c8bb6dea39ff5a75cc', '横向移动', 'Lateral Movement');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('105b1ef54d8a456eab89ca8cddc8b750', 'ad77b1ef4cdd49c8bb6dea39ff5a75cc', '利用远程服务', 'Exploitation of Remote Services');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('cf1cd1076db34865abdee56fa39ff270', 'ad77b1ef4cdd49c8bb6dea39ff5a75cc', '内部鱼叉式攻击', 'Internal Spearphishing');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('14f8ee95131e4454a15ae18214a1c30d', 'ad77b1ef4cdd49c8bb6dea39ff5a75cc', '工具横向转移', 'Lateral Tool Transfer');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('74805f682fca4d898a30a6cae5e20173', 'ad77b1ef4cdd49c8bb6dea39ff5a75cc', '远程服务会话劫持', 'Remote Service Session Hijacking');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('2712dc6e2bc24d33ad3f6be6d0877cfa', 'ad77b1ef4cdd49c8bb6dea39ff5a75cc', '远程服务', 'Remote Services');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('8a96217a3873406e9c98a30e158b2886', 'ad77b1ef4cdd49c8bb6dea39ff5a75cc', '通过可移动介质进行复制', 'Replication Through Removable Media');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('5b4378fd232a45daab193d4a6cbbe356', 'ad77b1ef4cdd49c8bb6dea39ff5a75cc', '利用第三方软件部署工具', 'Software Deployment Tools');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('52265ccc83ea4280abd23d833a328da9', 'ad77b1ef4cdd49c8bb6dea39ff5a75cc', '污染共享内容', 'Taint Shared Content');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('574ff14e2d1e41f88c9d53eb6d5a8020', 'ad77b1ef4cdd49c8bb6dea39ff5a75cc', '使用备用身份验证材料', 'Use Alternate Authentication Material');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('b160b85bfc524d45bf57e527501afb1f', '信息收集', 'Collection');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('8669c5517bd04a25a8e4b25f8d29087d', 'b160b85bfc524d45bf57e527501afb1f', '中间人', 'Adversary-in-the-Middle');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('6229c90f6b6143948b81a0a57f894086', 'b160b85bfc524d45bf57e527501afb1f', '存档已收集的数据', 'Archive Collected Data');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('1e689ca9404a4dbd96f2a59f86660754', 'b160b85bfc524d45bf57e527501afb1f', '音频捕获', 'Audio Capture');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('993c354e45314d1f8054281dbf420467', 'b160b85bfc524d45bf57e527501afb1f', '自动收集', 'Automated Collection');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('224d0a1a44594adea497ba20f0ee4fee', 'b160b85bfc524d45bf57e527501afb1f', '浏览器中间人', 'Browser Session Hijacking');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('7498c14b705c42ce8c9da77c72cd1ecb', 'b160b85bfc524d45bf57e527501afb1f', '剪贴板数据', 'Clipboard Data');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('237d01a8c36e45f1b48b9feab4cba339', 'b160b85bfc524d45bf57e527501afb1f', '来自云存储对象的数据', 'Data from Cloud Storage Object');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('2ef70e12a8e84cd0971667fbad606ad0', 'b160b85bfc524d45bf57e527501afb1f', '配置存储库中的数据', 'Data from Configuration Repository');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('66cb5d3839554b3e8c3c2128bb4e8aaa', 'b160b85bfc524d45bf57e527501afb1f', '信息存储库中的数据', 'Data from Information Repositories');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('bf2d11b89ef647c1be0fa99ef398a55f', 'b160b85bfc524d45bf57e527501afb1f', '从本地系统收集敏感数据', 'Data from Local System');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('cf718ffb646b44eeb7edfbc3455c52e7', 'b160b85bfc524d45bf57e527501afb1f', '网络共享驱动器中的数据', 'Data from Network Shared Drive');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('f2d3d625376d4ae5ac3125f17a3e59aa', 'b160b85bfc524d45bf57e527501afb1f', '来自可移动介质的数据', 'Data from Removable Media');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('655bd5faf879499ba2151862fcc043b3', 'b160b85bfc524d45bf57e527501afb1f', '暂存数据', 'Data Staged');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('7afbb5e06f854465ae1942df7dcd924b', 'b160b85bfc524d45bf57e527501afb1f', '电子邮件收集', 'Email Collection');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('f46401f93cab4468a70b7c0e6893aa5a', 'b160b85bfc524d45bf57e527501afb1f', '捕获用户输入', 'Input Capture');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('cf22287f891941f7a7177f6223814af6', 'b160b85bfc524d45bf57e527501afb1f', '屏幕截图', 'Screen Capture');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('1a5695f35b8244b3b77446a84b7faae9', 'b160b85bfc524d45bf57e527501afb1f', '视频捕获', 'Video Capture');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('a617513d4a2c442287fad4f1e4aa0662', '命令与控制', 'Command and Control');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('8e8591c879d84a919b972e317249ee73', 'a617513d4a2c442287fad4f1e4aa0662', '标准应用层协议', 'Application Layer Protocol');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('a66002aa02ba4b64ba6fa1afa9765ce2', 'a617513d4a2c442287fad4f1e4aa0662', '通过可移动介质进行通信', 'Communication Through Removable Media');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('a1d1feaa54fd4fa1b5452423163bfe56', 'a617513d4a2c442287fad4f1e4aa0662', '数据编码', 'Data Encoding');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('4804e60afe0c4ca9a17849fa2ca20f02', 'a617513d4a2c442287fad4f1e4aa0662', '数据混淆', 'Data Obfuscation');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('8ec115911f0841bda9a96985f0f80e0d', 'a617513d4a2c442287fad4f1e4aa0662', '动态解决方案', 'Dynamic Resolution');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('de892a070e384789b2f7a8ad2635d1b5', 'a617513d4a2c442287fad4f1e4aa0662', '加密信道', 'Encrypted Channel');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('bc0cc3043a85425e99510991d62d696d', 'a617513d4a2c442287fad4f1e4aa0662', '备用通信信道', 'Fallback Channels');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('fe3bd3da40fc4c1fb6e42e7124d6c31e', 'a617513d4a2c442287fad4f1e4aa0662', '远程文件拷贝', 'Ingress Tool Transfer');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('550658d6d90f42b5b2bd53f4e1a5edb9', 'a617513d4a2c442287fad4f1e4aa0662', '多阶段通信信道', 'Multi-Stage Channels');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0f6bc2407d404625b397d2cce3fe0757', 'a617513d4a2c442287fad4f1e4aa0662', '标准非应用层协议', 'Non-Application Layer Protocol');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('f544d75d04274c03b9cc5405b22bcfcf', 'a617513d4a2c442287fad4f1e4aa0662', '非标准端口', 'Non-Standard Port');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0d25d1b16cae4d4894ff61d9f0c63da2', 'a617513d4a2c442287fad4f1e4aa0662', '隧道协议', 'Protocol Tunneling');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('4264229e792c499baa964940ad9c2822', 'a617513d4a2c442287fad4f1e4aa0662', '连接代理', 'Proxy');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('bb6e3ad02e9844d2b6a6910c583981d4', 'a617513d4a2c442287fad4f1e4aa0662', '远程访问工具', 'Remote Access Software');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('04fe94c2fc4f49f08bd0b559fe8e03f9', 'a617513d4a2c442287fad4f1e4aa0662', '流量特征', 'Traffic Signaling');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b43946b288a94df3b66e21e1e049a166', 'a617513d4a2c442287fad4f1e4aa0662', '网络服务', 'Web Service');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('e7d18ad04f94409785e24d3d166da032', '信息盗取', 'Exfiltration');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('e6286d57f405470cbdebf59754768744', 'e7d18ad04f94409785e24d3d166da032', '自动化数据渗出', 'Automated Exfiltration');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('2cfa01a95aed4e8fbc2d8d3bc29c1bd4', 'e7d18ad04f94409785e24d3d166da032', '限制数据传输大小', 'Data Transfer Size Limits');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('af86ea506fdd46c4815be68f1ad0873f', 'e7d18ad04f94409785e24d3d166da032', '备用协议上的数据渗出', 'Exfiltration Over Alternative Protocol');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('d314954273c648e9a768104008a0a6eb', 'e7d18ad04f94409785e24d3d166da032', '使用命令与控制信道窃取', 'Exfiltration Over C2 Channel');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('899be3a3fb5c4e3b9b62afabd95533f2', 'e7d18ad04f94409785e24d3d166da032', '其他网络介质的数据渗漏', 'Exfiltration Over Other Network Medium');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('70f86ad3aa6f4fa794da81c39c33add2', 'e7d18ad04f94409785e24d3d166da032', '物理介质上的数据渗出', 'Exfiltration Over Physical Medium');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('5c83ae53e4114b72a4c54d0d9467e19b', 'e7d18ad04f94409785e24d3d166da032', '通过Web服务进行数据渗出', 'Exfiltration Over Web Service');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('58bba371bd48403a8e04c2eca099e808', 'e7d18ad04f94409785e24d3d166da032', '计划传输', 'Scheduled Transfer');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('9a6093d06eb14004b2111a9e6d006fc7', 'e7d18ad04f94409785e24d3d166da032', '将数据传输到云账户中', 'Transfer Data to Cloud Account');

insert into rtks_attack_category
    (resource_id, name_zh, name_en)
values ('5cb65e3f0bff433b9894065ed7702b7c', '影响', 'Impact');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('94dfe6d506eb420586e39130e78e4991', '5cb65e3f0bff433b9894065ed7702b7c', '删除账户访问权限', 'Account Access Removal');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('0bcb0ae9ff604ea4910d9c70ae24c490', '5cb65e3f0bff433b9894065ed7702b7c', '销毁数据', 'Data Destruction');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('7f622aad141c4e9fa4222c017e8768a5', '5cb65e3f0bff433b9894065ed7702b7c', '数据加密', 'Data Encrypted for Impact');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('38fc46beabea44fa8a60c079801363c9', '5cb65e3f0bff433b9894065ed7702b7c', '数据修改、监控', 'Data Manipulation');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('2cf23c083b764b8c921abdc442567789', '5cb65e3f0bff433b9894065ed7702b7c', '篡改', 'Defacement');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('6a580066fbb64015a954882b40cbcbde', '5cb65e3f0bff433b9894065ed7702b7c', '删除磁盘数据', 'Disk Wipe');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('ca2c48ba0854462aa8684f6b854744b3', '5cb65e3f0bff433b9894065ed7702b7c', '端点拒绝服务', 'Endpoint Denial of Service');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('281b69e14c724067978f3496db76ee75', '5cb65e3f0bff433b9894065ed7702b7c', '固件损坏', 'Firmware Corruption');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('ad55b35949c24c298559b77558c0baea', '5cb65e3f0bff433b9894065ed7702b7c', '禁用系统恢复', 'Inhibit System Recovery');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('8d9fc4046bb444de80aa98194b238c86', '5cb65e3f0bff433b9894065ed7702b7c', '网络拒绝服务', 'Network Denial of Service');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('e80475658e5f4bf2a6d52f19d529aa36', '5cb65e3f0bff433b9894065ed7702b7c', '资源劫持', 'Resource Hijacking');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('799c0194dca249828bf7041bee9b6bd0', '5cb65e3f0bff433b9894065ed7702b7c', '服务停止', 'Service Stop');

insert into rtks_attack
    (resource_id, category_id, name_zh, name_en)
values ('b91cdd0799a74369b05a3f166245820f', '5cb65e3f0bff433b9894065ed7702b7c', '系统关闭/重启', 'System Shutdown/Reboot');


#

insert into rtks_engage_category
    (resource_id, name_zh, name_en)
values ('84b419f1839049a4a8a6e2f66c9f1ead', '准备', 'Prepare');

insert into rtks_engage_sub_category
    (resource_id, category_id, name_zh, name_en)
values ('a806c0983ee14596b821fe34df646c06', '84b419f1839049a4a8a6e2f66c9f1ead', '规划', 'Plan');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('bd4817120ccc4882a76f432bb27f08f1', '84b419f1839049a4a8a6e2f66c9f1ead', 'a806c0983ee14596b821fe34df646c06', '网络威胁情报', 'Cyber Threat Intelligence');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('f35db4d2e8ad4587bf80ed90f7cb4218', '84b419f1839049a4a8a6e2f66c9f1ead', 'a806c0983ee14596b821fe34df646c06', '参与环境', 'Engagement Environment');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('f8ff871e12144280ae8698c0500be293', '84b419f1839049a4a8a6e2f66c9f1ead', 'a806c0983ee14596b821fe34df646c06', '选通标准', 'Gating Criteria');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('07e2bce31e0a4115a719aa565c1339c7', '84b419f1839049a4a8a6e2f66c9f1ead', 'a806c0983ee14596b821fe34df646c06', '作战目标', 'Operational Objective');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('2a9894027add49ae8cfca2285b08c1fb', '84b419f1839049a4a8a6e2f66c9f1ead', 'a806c0983ee14596b821fe34df646c06', '角色创建', 'Persona Creation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('6b494634bbab45de9214e3296b46d532', '84b419f1839049a4a8a6e2f66c9f1ead', 'a806c0983ee14596b821fe34df646c06', '故事板', 'Storyboarding');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('96dc5a77224c40df8659218295eda5d0', '84b419f1839049a4a8a6e2f66c9f1ead', 'a806c0983ee14596b821fe34df646c06', '威胁模型', 'Threat Model');

insert into rtks_engage_category
    (resource_id, name_zh, name_en)
values ('8b5d050442e742338195f1b383b369a9', '暴露', 'Expose');

insert into rtks_engage_sub_category
    (resource_id, category_id, name_zh, name_en)
values ('54e41ad2e2dd43eea3353150bf67a878', '8b5d050442e742338195f1b383b369a9', '收集', 'Collect');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('7242633569774cf89c1f6110267b657e', '8b5d050442e742338195f1b383b369a9', '54e41ad2e2dd43eea3353150bf67a878', 'API监控', 'API Monitoring');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('5dbe69a917af47f9a83e559bbc8f368b', '8b5d050442e742338195f1b383b369a9', '54e41ad2e2dd43eea3353150bf67a878', '网络监控', 'Network Monitoring');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('0a745b34e3de451e866180f369fe9a5d', '8b5d050442e742338195f1b383b369a9', '54e41ad2e2dd43eea3353150bf67a878', '软件操作', 'Software Manipulation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('79c8ec239e8644fc8b54967ca128184c', '8b5d050442e742338195f1b383b369a9', '54e41ad2e2dd43eea3353150bf67a878', '系统活动监控', 'System Activity Monitoring');

insert into rtks_engage_sub_category
    (resource_id, category_id, name_zh, name_en)
values ('0579c5c51e82427a95d7cd7be08e7761', '8b5d050442e742338195f1b383b369a9', '发现', 'Detect');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('fe5ef1debcc14f60a80c6e2c0db84a83', '8b5d050442e742338195f1b383b369a9', '0579c5c51e82427a95d7cd7be08e7761', '引入漏洞', 'Introduced Vulnerabilities');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('e949fee196004dfeafa9da76ab78c461', '8b5d050442e742338195f1b383b369a9', '0579c5c51e82427a95d7cd7be08e7761', '诱饵', 'Lures');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('4f6562ab745144a4b15c584eecee628f', '8b5d050442e742338195f1b383b369a9', '0579c5c51e82427a95d7cd7be08e7761', '引爆恶意软件', 'Malware Detonation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('35cec6b48d374455979016b95633c527', '8b5d050442e742338195f1b383b369a9', '0579c5c51e82427a95d7cd7be08e7761', '网络分析', 'Network Analysis');

insert into rtks_engage_category
    (resource_id, name_zh, name_en)
values ('6e5f3926876b4391bca49a7b207aaabd', '影响', 'Affect');

insert into rtks_engage_sub_category
    (resource_id, category_id, name_zh, name_en)
values ('44916b7179fc4e47aec33ff746d92953', '6e5f3926876b4391bca49a7b207aaabd', '防御', 'Prevent');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('0ad5f0e22f7f4fd2b70c06d17b1131a6', '6e5f3926876b4391bca49a7b207aaabd', '44916b7179fc4e47aec33ff746d92953', '基线', 'Baseline');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('e9eb6cc4d40846358602a13332cb743d', '6e5f3926876b4391bca49a7b207aaabd', '44916b7179fc4e47aec33ff746d92953', '硬件操作', 'Hardware Manipulation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('9a019500e2f84696b50a5d09d7d86cf9', '6e5f3926876b4391bca49a7b207aaabd', '44916b7179fc4e47aec33ff746d92953', '隔离', 'Isolation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('b08fbeeb1737433d8bbac92b21eae3b9', '6e5f3926876b4391bca49a7b207aaabd', '44916b7179fc4e47aec33ff746d92953', '网络操纵', 'Network Manipulation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('2f9a6ffc48d6492caee6b4227fa8a691', '6e5f3926876b4391bca49a7b207aaabd', '44916b7179fc4e47aec33ff746d92953', '安全控制', 'Security Controls');

insert into rtks_engage_sub_category
    (resource_id, category_id, name_zh, name_en)
values ('d68b3c396991470e93c67192fda0f4f2', '6e5f3926876b4391bca49a7b207aaabd', '转移', 'Direct');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('1c46d87547f845b2829f74f99825ddd6', '6e5f3926876b4391bca49a7b207aaabd', 'd68b3c396991470e93c67192fda0f4f2', '攻击向量转移', 'Attack Vector Migration');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('c20cbf2a6d5d4c0a88dffeb978e7fe40', '6e5f3926876b4391bca49a7b207aaabd', 'd68b3c396991470e93c67192fda0f4f2', '邮件管理', 'Email Manipulation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('6a3a4ee18ce6497ab740954733fdeefc', '6e5f3926876b4391bca49a7b207aaabd', 'd68b3c396991470e93c67192fda0f4f2', '引入漏洞', 'Introduced Vulnerabilities');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('c0111e6692c645eaa738e47c92cc07a4', '6e5f3926876b4391bca49a7b207aaabd', 'd68b3c396991470e93c67192fda0f4f2', '诱饵', 'Lures');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('e00a01097edb49f48bf532c2e484e35e', '6e5f3926876b4391bca49a7b207aaabd', 'd68b3c396991470e93c67192fda0f4f2', '引爆恶意软件', 'Malware Detonation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('003627bbb3974574bfe7d307a6774d4e', '6e5f3926876b4391bca49a7b207aaabd', 'd68b3c396991470e93c67192fda0f4f2', '网络操纵', 'Network Manipulation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('fb2a3b31076049f69b2b57ce1c02d597', '6e5f3926876b4391bca49a7b207aaabd', 'd68b3c396991470e93c67192fda0f4f2', '外设管理', 'Peripheral Management');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('45628f9c8c5d424a8718df641cf4cfa9', '6e5f3926876b4391bca49a7b207aaabd', 'd68b3c396991470e93c67192fda0f4f2', '安全控制', 'Security Controls');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('f0b0cf15f8964012ad674a25349194cb', '6e5f3926876b4391bca49a7b207aaabd', 'd68b3c396991470e93c67192fda0f4f2', '软件操作', 'Software Manipulation');

insert into rtks_engage_sub_category
    (resource_id, category_id, name_zh, name_en)
values ('5e02f56563524b3abdfe9a65877abe90', '6e5f3926876b4391bca49a7b207aaabd', '破坏', 'Disrupt');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('ae4800e238a940b39d5701e85f6a18ae', '6e5f3926876b4391bca49a7b207aaabd', '5e02f56563524b3abdfe9a65877abe90', '隔离', 'Isolation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('3fc4cea17bf14d81959def65004659f8', '6e5f3926876b4391bca49a7b207aaabd', '5e02f56563524b3abdfe9a65877abe90', '诱饵', 'Lures');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('8fade69b3b6c495aa37ef7596cc62e8a', '6e5f3926876b4391bca49a7b207aaabd', '5e02f56563524b3abdfe9a65877abe90', '网络操纵', 'Network Manipulation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('31563ec6874b47578aa9d7fe8a1ed04a', '6e5f3926876b4391bca49a7b207aaabd', '5e02f56563524b3abdfe9a65877abe90', '软件操作', 'Software Manipulation');

insert into rtks_engage_category
    (resource_id, name_zh, name_en)
values ('9f3f6e31bf0d40499a03beae487008e8', '引出', 'Elicit');

insert into rtks_engage_sub_category
    (resource_id, category_id, name_zh, name_en)
values ('b36c043564274070b9ce65d241e04f73', '9f3f6e31bf0d40499a03beae487008e8', '保证', 'Reassure');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('0efd3406d1a1491cbd8a2dae26533f6a', '9f3f6e31bf0d40499a03beae487008e8', 'b36c043564274070b9ce65d241e04f73', '应用多样性', 'Application Diversity');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('b6c7b5b5d5744a94ad44ee25e0231ebd', '9f3f6e31bf0d40499a03beae487008e8', 'b36c043564274070b9ce65d241e04f73', '工件多样性', 'Artifact Diversity');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('77fe17e4461c4229814a36740da02c88', '9f3f6e31bf0d40499a03beae487008e8', 'b36c043564274070b9ce65d241e04f73', '磨合', 'Burn-In');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('a335eddccea94322b50a00b71fe7d95e', '9f3f6e31bf0d40499a03beae487008e8', 'b36c043564274070b9ce65d241e04f73', '邮件管理', 'Email Manipulation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('4ddca25c7cd94c8faeddbbc8641f4068', '9f3f6e31bf0d40499a03beae487008e8', 'b36c043564274070b9ce65d241e04f73', '信息处理', 'Information Manipulation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('b7bd81ae933f44e7b9b23191e635c615', '9f3f6e31bf0d40499a03beae487008e8', 'b36c043564274070b9ce65d241e04f73', '网络多样性', 'Network Diversity');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('c151b3dc3bea4dc0902d2163a3e8f38a', '9f3f6e31bf0d40499a03beae487008e8', 'b36c043564274070b9ce65d241e04f73', '外设管理', 'Peripheral Management');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('3dca2ed26184431f96be878949acdf7e', '9f3f6e31bf0d40499a03beae487008e8', 'b36c043564274070b9ce65d241e04f73', '杂项信息', 'Pocket Litter');

insert into rtks_engage_sub_category
    (resource_id, category_id, name_zh, name_en)
values ('4bc48e6b8b6f4a06a248e637c5a6abb2', '9f3f6e31bf0d40499a03beae487008e8', '激励', 'Motivate');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('b1f09aab975f40c9977bf37ac88fd1e8', '9f3f6e31bf0d40499a03beae487008e8', '4bc48e6b8b6f4a06a248e637c5a6abb2', '应用多样性', 'Application Diversity');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('e88508bd7a064e93a9e1615ed9800b8d', '9f3f6e31bf0d40499a03beae487008e8', '4bc48e6b8b6f4a06a248e637c5a6abb2', '工件多样性', 'Artifact Diversity');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('183c250bb395489d8decffb3fb4aa289', '9f3f6e31bf0d40499a03beae487008e8', '4bc48e6b8b6f4a06a248e637c5a6abb2', '信息处理', 'Information Manipulation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('2ef10e0904fb49d7b33b3f11fa441c89', '9f3f6e31bf0d40499a03beae487008e8', '4bc48e6b8b6f4a06a248e637c5a6abb2', '引入漏洞', 'Introduced Vulnerabilities');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('c4b241fdaaec41b99465ace534f712a0', '9f3f6e31bf0d40499a03beae487008e8', '4bc48e6b8b6f4a06a248e637c5a6abb2', '引爆恶意软件', 'Malware Detonation');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('5a1fa286abfb48fd86c6584edb44d9ee', '9f3f6e31bf0d40499a03beae487008e8', '4bc48e6b8b6f4a06a248e637c5a6abb2', '网络多样性', 'Network Diversity');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('d42084cac51649538e45f05f807985c9', '9f3f6e31bf0d40499a03beae487008e8', '4bc48e6b8b6f4a06a248e637c5a6abb2', '人物角色', 'Persona');

insert into rtks_engage_category
    (resource_id, name_zh, name_en)
values ('f67e8ceafcce4551926f1300082dabe6', '理解', 'Understand');

insert into rtks_engage_sub_category
    (resource_id, category_id, name_zh, name_en)
values ('6a0a459caf7c41b8b6d16856fb19a898', 'f67e8ceafcce4551926f1300082dabe6', '分析', 'Analyze');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('34436532322d404798fa4b6470fc9db9', 'f67e8ceafcce4551926f1300082dabe6', '6a0a459caf7c41b8b6d16856fb19a898', '行动后审查', 'After-Action Review');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('155ca8ba9b5f4b4f960d1fdcddd4634d', 'f67e8ceafcce4551926f1300082dabe6', '6a0a459caf7c41b8b6d16856fb19a898', '网络威胁情报', 'Cyber Threat Intelligence');

insert into rtks_engage
    (resource_id, category_id, sub_category_id, name_zh, name_en)
values ('f8594529332a4e67bd378f5a867f1925', 'f67e8ceafcce4551926f1300082dabe6', '6a0a459caf7c41b8b6d16856fb19a898', '威胁模型', 'Threat Model');


