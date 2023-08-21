package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.context.template.BusinessTemplateSimple;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.rtks.dao.entity.AttackTrainRelation;
import com.cyberpeace.rtks.dao.mapper.AttackMapper;
import com.cyberpeace.rtks.service.AttackService;
import com.cyberpeace.rtks.service.bo.career.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AttackServiceImpl implements AttackService {

	private final Object lock = new Object();

	@Autowired
	private AttackMapper attackMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	private volatile AttackCache cachedAttacks;

	@Override
	public List<AttackCategoryBo<AttackBackBo>> getAttacks() {
		ensureAttackCache();
		List<AttackCategoryBo<AttackBackBo>> res;
		if (CommonContext.isChinese()) {
			res = BeanUtils.deepCopy(cachedAttacks, AttackBackZh.class).getZh();
		} else {
			res = BeanUtils.deepCopy(cachedAttacks, AttackBackEn.class).getEn();
		}
		// count relation
		List<RelCount> countList = attackMapper.countRelsByAttackId();
		Map<String, Integer> countMap = Maps.newHashMap();
		countList.forEach((count) -> {
			countMap.put(count.getResourceId(), count.getCount());
		});
		// set count
		res.forEach((category -> {
			category.getAttacks().forEach((attackBackBo -> {
				if (countMap.containsKey(attackBackBo.getResourceId())) {
					attackBackBo.setCount(countMap.get(attackBackBo.getResourceId()));
				}
			}));
		}));

		return res;
	}

	@Override
	public List<AttackCategoryBo<AttackFrontBo>> getAttacksByTraining(String businessTemplateId) {
		ensureAttackCache();
		List<AttackCategoryBo<AttackFrontBo>> res;
		if (CommonContext.isChinese()) {
			res = BeanUtils.deepCopy(cachedAttacks, AttackFrontZh.class).getZh();
		} else {
			res = BeanUtils.deepCopy(cachedAttacks, AttackFrontEn.class).getEn();
		}
		List<String> relatedAttackIds = attackMapper.getRelByBusinessTemplateId(businessTemplateId);
		// set whether related
		res.forEach((category -> {
			category.getAttacks().forEach((attackFrontBo -> {
				if (relatedAttackIds.contains(attackFrontBo.getResourceId())) {
					attackFrontBo.setRelated(true);
				}
			}));
		}));
		return res;
	}

	@Override
	public AttackDetail getDetailById(String resourceId) {
		ensureAttackCache();
		AttackDetail detail = new AttackDetail();
		List<AttackCategoryBo<FrameItemBase>> cache;
		if (CommonContext.isChinese()) {
			cache = cachedAttacks.getZh();
		} else {
			cache = cachedAttacks.getEn();
		}
		// search
		cache.forEach((category) -> {
			category.getAttacks().forEach((attack) -> {
				if (attack.getResourceId().equals(resourceId)) {
					detail.setCategoryName(category.getCategoryName());
					detail.setResourceId(attack.getResourceId());
					detail.setName(attack.getName());
				}
			});
		});
		// get related business templates
		List<BusinessTemplateSimple> templates = attackMapper.getRelatedTemplates(resourceId);
		detail.setTemplates(templates);
		return detail;
	}

	@Override
	public void updateRelations(String resourceId, List<String> businessTemplateIds) {
		List<String> oldRel = attackMapper.getRelatedTemplateIds(resourceId);
		CrCommonUtil.differ2List(oldRel, businessTemplateIds);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				if (!oldRel.isEmpty()) {
					attackMapper.deleteRel(resourceId, oldRel);
				}
				if (!businessTemplateIds.isEmpty()) {
					attackMapper.addRel(resourceId, businessTemplateIds);
				}
			}
		});
	}

	@Override
	public List<AttackCategoryBo<FrameItemBase>> getCache() {
		ensureAttackCache();
		if (CommonContext.isChinese()) {
			return cachedAttacks.getZh();
		} else {
			return cachedAttacks.getEn();
		}
	}

	private void ensureAttackCache() {
		if (null == cachedAttacks) {
			synchronized (lock) {
				if (null == cachedAttacks) {
					initializeAttacks();
				}
			}
		}
	}

	/**
	 * 读取 ATT&CK 缓存
	 */
	private void initializeAttacks() {
		List<AttackContext> attackContextList = attackMapper.getAll();
		cachedAttacks = new AttackCache();
		String categoryId = "";
		AttackCategoryBo<FrameItemBase> categoryBoZh = new AttackCategoryBo<>();
		AttackCategoryBo<FrameItemBase> categoryBoEn = new AttackCategoryBo<>();
		for (AttackContext attackContext : attackContextList) {
			if (!categoryId.equals(attackContext.getCategoryId())) {
				categoryId = attackContext.getCategoryId();
				categoryBoZh = new AttackCategoryBo<>();
				categoryBoEn = new AttackCategoryBo<>();
				categoryBoZh.setCategoryName(attackContext.getCategoryNameZh());
				categoryBoEn.setCategoryName(attackContext.getCategoryNameEn());
				cachedAttacks.getZh().add(categoryBoZh);
				cachedAttacks.getEn().add(categoryBoEn);
			}
			FrameItemBase itemZh = new FrameItemBase();
			FrameItemBase itemEn = new FrameItemBase();
			itemZh.setResourceId(attackContext.getAttackId());
			itemZh.setName(attackContext.getAttackNameZh());
			itemEn.setResourceId(attackContext.getAttackId());
			itemEn.setName(attackContext.getAttackNameEn());

			categoryBoZh.getAttacks().add(itemZh);
			categoryBoEn.getAttacks().add(itemEn);
		}
	}

	private Map<String, String> getOldNewMap() {
		Map<String, String> oldNewMap = Maps.newHashMap();
		oldNewMap.put("d1b19920f21e44d789ac668411d24e12", "T1595");
		oldNewMap.put("529f69ac92e041448bb3e2fc9813a31e", "T1592");
		oldNewMap.put("a96b2ecd4495448ea7d54c93501cd4e2", "T1589");
		oldNewMap.put("4e170d8e0dab4eaca6c916ccd8a48c6b", "T1590");
		oldNewMap.put("fd1163fa00b14f17900a9805c73083d3", "T1591");
		oldNewMap.put("445b995c050c4a68a1cdad4c4d54e083", "T1598");
		oldNewMap.put("6be4726aef47474dbd9d221ef6b1cdc0", "T1597");
		oldNewMap.put("3e0fea09e107412a8342b3813819af60", "T1596");
		oldNewMap.put("0f5fcb4e96f34c389444c93d5fd54454", "T1593");
		oldNewMap.put("72df8b2120534205b273db0247750888", "T1594");
		oldNewMap.put("c33242bc4321424ba30d5ee80525f53a", "T1583");
		oldNewMap.put("82571065ffe643f2901e3a9f0daf62d2", "T1586");
		oldNewMap.put("6af1b3277ff14c67bea665b2d492708e", "T1584");
		oldNewMap.put("5015ba561c3341249c18d02a64fb9040", "T1587");
		oldNewMap.put("80b0aa737a694188a7f49d6badaca5e6", "T1585");
		oldNewMap.put("cab52852bd824fd98d7aabd777f86127", "T1588");
		oldNewMap.put("2c8617c99a6146208703428629ff544b", "T1608");
		oldNewMap.put("d4c20d13b3ae4615a2f2304ddd1ad46c", "T1189");
		oldNewMap.put("526b1489950147e29551dffdf2761cbd", "T1190");
		oldNewMap.put("7e5fd86518bf4ff9be455c1e5e18d0ea", "T1133");
		oldNewMap.put("fd78358d5bec449a87010b1f2f0fe357", "T1200");
		oldNewMap.put("084bc4a7ba1c4bb5812514d423773dac", "T1598");
		oldNewMap.put("0c70d58792f642a49cad9f9d149f5911", "T1091");
		oldNewMap.put("3243637138b44afe8975d10f5fdf80d9", "T1195");
		oldNewMap.put("71999010f66c433587adf1fa4ece4ad4", "T1199");
		oldNewMap.put("d6f626ad587e49f0bdd57a10df373793", "T1078");
		oldNewMap.put("b98b9869cf194fb6b87abac623fc786c", "T1059");
		oldNewMap.put("d5ffb6a424574ac38958a63dad3fa2b4", "T1609");
		oldNewMap.put("04f8cab86ef54bab82f70dad254c7692", "T1610");
		oldNewMap.put("29a2ccc54b494ddb959bbed1f8e0e402", "T1203");
		oldNewMap.put("bdcb85842d354b97b583a593cc4ce6ae", "T1559");
		oldNewMap.put("c4a79ad3c37149039a41b6cc77a80223", "T1106");
		oldNewMap.put("c3f66b94eeff40c598f0abbe27c8446a", "T1053");
		oldNewMap.put("57e74f475d204616b98b6c2ce738e985", "T1129");
		oldNewMap.put("4d100aa7304944b8b9011809ba356a55", "T1072");
		oldNewMap.put("0f7df937e4f1444ab518f810c3644c7e", "T1569");
		oldNewMap.put("c85335978dfa4ca09703179a6f51ef15", "T1204");
		oldNewMap.put("3cd04aa3baf04fcdba01f720c00168b5", "T1047");
		oldNewMap.put("f2affb19d3834fe88fd9c1a1c060260d", "T1098");
		oldNewMap.put("3b04ca02875a4c199db996b14b6d4927", "T1547");
		oldNewMap.put("89747db41902496eaec8eb5a56bc8fe7", "T1037");
		oldNewMap.put("ff32f8976b2d44d99bcf798b889cbedc", "T1176");
		oldNewMap.put("9b2ff3fd2a6f4b5eb010345a9d0f6072", "T1554");
		oldNewMap.put("dfe41ad5af544dbd93d984cbaf156b0c", "T1136");
		oldNewMap.put("7f2083b57c6245969eacdb7ea1bf1b4e", "T1543");
		oldNewMap.put("4e4960bd9c774e429de69b45decee583", "T1546");
		oldNewMap.put("de86e967abf24d91872e62c504ef7239", "T1133");
		oldNewMap.put("b5c6980ed1d04b2bb099d745f75dc199", "T1574");
		oldNewMap.put("a355f84cdbb74058919ccb4d73bc3dd7", "T1525");
		oldNewMap.put("91344e6094b448848649029d69b83ffd", "T1556");
		oldNewMap.put("5ceb7dd6b66a4a1081944ab8e7d18e59", "T1137");
		oldNewMap.put("626ef5e60d824b35bafd76818d25e7b4", "T1542");
		oldNewMap.put("60547a7c96ff4ad7a8fc706a0eaf9ccb", "T1053");
		oldNewMap.put("e2a539d7497447589f11d624180b5c10", "T1505");
		oldNewMap.put("799cea8942af4bdc94766b809eee9e87", "T1205");
		oldNewMap.put("ef97c2f61fb84fbd85d965d12d48e2d6", "T1078");
		oldNewMap.put("73d6727ec9ec477f80ac974d78bc2a6c", "T1548");
		oldNewMap.put("3ac10dc53d914e51b9d56315c64d9a70", "T1134");
		oldNewMap.put("03f8e0e873df45a1a1fa0c85d78f0fb1", "T1547");
		oldNewMap.put("52a3c9b9ebc848c7a037e5cec6bf6337", "T1037");
		oldNewMap.put("77767000a1a44f29a97872b5e84a4161", "T1543");
		oldNewMap.put("0d178c429f584dcda666db1a03333153", "T1484");
		oldNewMap.put("4f9ac7bfdd324eaf90fe25175a9c9d3c", "T1611");
		oldNewMap.put("ae36f03fb5ad4f619479154896b895d7", "T1546");
		oldNewMap.put("487d94c9bf65424585fcffb24e23dc23", "T1068");
		oldNewMap.put("935197ab99914b51b6217a944aea326c", "T1574");
		oldNewMap.put("05380ccc454241e291702487e8fd981f", "T1055");
		oldNewMap.put("0087aab3de584324b8657c9a408679f0", "T1053");
		oldNewMap.put("aa63062a40034832853ceb76b1ce23a4", "T1078");
		oldNewMap.put("30ddee65191240e3a6abda1307ea1ab5", "T1548");
		oldNewMap.put("a6ce851f087e4f76b5b41fac8a9873f9", "T1134");
		oldNewMap.put("69bf8415c82c4630b7636456251ee182", "T1197");
		oldNewMap.put("ad5f465910a44d8aa56247886002d663", "T1612");
		oldNewMap.put("e62f9b3312554700867fc88a27afaac2", "T1140");
		oldNewMap.put("bd1a8a79a2f8472bb057d2bdd78d95f7", "T1610");
		oldNewMap.put("57cf01e27da84f89be2673e0b7501231", "T1006");
		oldNewMap.put("1f1db4a4b4c040559fc7eb99b98a6042", "T1484");
		oldNewMap.put("5ac01d994fbf4084a3ba1013e74d072f", "T1480");
		oldNewMap.put("9f0b1bb07bb24dea806cc4314160d6bd", "T1211");
		oldNewMap.put("b0ea76a125ba41288bd3ca2526339f44", "T1222");
		oldNewMap.put("f6e03a8f3d55468eb81d9d8482ab33eb", "T1564");
		oldNewMap.put("fc7fc54f0dbe46edbd53a3b511affef7", "T1574");
		oldNewMap.put("c1c4a22655364696930a3255f8c4009d", "T1562");
		oldNewMap.put("2c9edb2fde9f45c792131eb05c409c4e", "T1070");
		oldNewMap.put("f56a466e544f4d05a1e62eeec0f6e201", "T1202");
		oldNewMap.put("251a76e8c6d84a01b03057dda309a9f8", "T1036");
		oldNewMap.put("0c54c519fe4242fcb9489aafad154609", "T1556");
		oldNewMap.put("b0b8144b556e4a24934db5b298b11af5", "T1578");
		oldNewMap.put("6dc6fc66b34f42ee994f258558295380", "T1112");
		oldNewMap.put("04a39c94f04a4fa4954e83dc54f9e4fa", "T1601");
		oldNewMap.put("6797bbeddbe344069ea536b7465c8e68", "T1599");
		oldNewMap.put("d03ed496069345efb4c61d7f1ee796b0", "T1027");
		oldNewMap.put("01b2ef8df3404cd9bd06f159b458e36e", "T1542");
		oldNewMap.put("95d06161f49a4991935e5a446d26c3eb", "T1055");
		oldNewMap.put("a79e807b71c74fa48cb1852aa985dd60", "T1620");
		oldNewMap.put("a182627cdecc47aa9a13f3b1b8409964", "T1207");
		oldNewMap.put("7d49c98518954438bc67839d640aeb9f", "T1014");
		oldNewMap.put("caa3f0005fbb40cebe3d6e043df109bc", "T1553");
		oldNewMap.put("b6e40a09bfeb477a9baad8d1cdeb3cb8", "T1221");
		oldNewMap.put("7a268c756bbc41d5b26c578999f81040", "T1205");
		oldNewMap.put("bbce173f856e4046a0d941e8e9936dc1", "T1127");
		oldNewMap.put("b6fcdd8ff1f8449ebf691c09c21f9dcb", "T1535");
		oldNewMap.put("bebc510e85ed400b94e848001d226578", "T1550");
		oldNewMap.put("c1699316dcd442a8941743a8d55d26be", "T1078");
		oldNewMap.put("b4f9cc2483bc4c0b8d37bce4a4c6bb3a", "T1497");
		oldNewMap.put("113f2fe6f0b247b1b17395dd5e608881", "T1600");
		oldNewMap.put("8d4cb0cef910463a99b22c7116024ccd", "T1220");
		oldNewMap.put("dccd50ba17e04438973c9928c646bbe5", "T1557");
		oldNewMap.put("deb00e514a9a45c9810b294ae7936766", "T1110");
		oldNewMap.put("e6ce4627b21443a8af8df959c4f52adb", "T1555");
		oldNewMap.put("98700980a536465dbd440bf70acfbfaa", "T1212");
		oldNewMap.put("a2f2e7322c9a4763b8af900598522a09", "T1187");
		oldNewMap.put("1a352244d82d425eb5fadb1c9b7207ac", "T1606");
		oldNewMap.put("18bb9da1d25c478f8f7f62fffe56b00f", "T1056");
		oldNewMap.put("fc60168ff50845109bf08eb3f6b3311b", "T1556");
		oldNewMap.put("5b39f8e34b4745ab9dcac29503e190a4", "T1040");
		oldNewMap.put("df5fb123a43c4982ba770d49bd64d3bb", "T1003");
		oldNewMap.put("976ac6f2793442e5b11075ec65fbeb10", "T1528");
		oldNewMap.put("1ce2073e8d394b1eb58822237034ab64", "T1558");
		oldNewMap.put("6edbe26057e141daa9e55aeca877492b", "T1539");
		oldNewMap.put("fe0956ff6e86479590d43d93d48480ad", "T1552");
		oldNewMap.put("f464e627a28c4a49a8da692d8657e00a", "T1087");
		oldNewMap.put("675c982d9e1140a2b8c2d6e6b3626a11", "T1010");
		oldNewMap.put("818a7976183b4cfd81132898d3c33700", "T1217");
		oldNewMap.put("5e0400a2222f43df9454f996e0d8e503", "T1580");
		oldNewMap.put("62d12588d5f94fbf90c2b3160f756e62", "T1538");
		oldNewMap.put("2bdbd1dd152a408dae6ff8e3fccc03df", "T1526");
		oldNewMap.put("920c994d66b94972b15a11181665f296", "T1619");
		oldNewMap.put("610897c920da42ed84a25eed2d2792de", "T1613");
		oldNewMap.put("1cbe0389b2824857adb4062088a05732", "T1482");
		oldNewMap.put("ca60989313134feb9105561262f28405", "T1083");
		oldNewMap.put("d35302212a364b7b92c8e8a1ec4a888d", "T1615");
		oldNewMap.put("91194f4db92a4addb8c3def769956d61", "T1135");
		oldNewMap.put("63fec528df9842c5acf86579d59c674e", "T1040");
		oldNewMap.put("91f6119bfc8e4dcdb1fc7f3154cfdaa7", "T1201");
		oldNewMap.put("b09b145d30854aaca98ad274e088015c", "T1120");
		oldNewMap.put("858451ed27694ae1b3fcd5776dbfae96", "T1069");
		oldNewMap.put("997b0e1429b94c93b9ffba7d8a32a8a4", "T1057");
		oldNewMap.put("b117cfdf426d41d090ef3781c1bf0962", "T1012");
		oldNewMap.put("c121f9c60fc040898499bced0e80dd02", "T1018");
		oldNewMap.put("0214e0a20ff24a66a0867e34e11b17a7", "T1518");
		oldNewMap.put("3e58043b4f9c4a209a6ca3e76dca43fb", "T1082");
		oldNewMap.put("9e4e3921e1714ae7941a02e615555060", "T1614");
		oldNewMap.put("7ad8f2cc8be14cc088951b6b59ff1f82", "T1016");
		oldNewMap.put("2e20f490a08e4868af77bb9c93f664c0", "T1049");
		oldNewMap.put("80c2c382b8634ee780c4e8c444717694", "T1033");
		oldNewMap.put("f7445e9a044c49fb9658d48f287a6072", "T1007");
		oldNewMap.put("64ec24e2ae9942158417601358a53d95", "T1124");
		oldNewMap.put("ebfb3d096d5644ed8273b947965fc6a2", "T1497");
		oldNewMap.put("105b1ef54d8a456eab89ca8cddc8b750", "T1210");
		oldNewMap.put("cf1cd1076db34865abdee56fa39ff270", "T1534");
		oldNewMap.put("14f8ee95131e4454a15ae18214a1c30d", "T1570");
		oldNewMap.put("74805f682fca4d898a30a6cae5e20173", "T1563");
		oldNewMap.put("2712dc6e2bc24d33ad3f6be6d0877cfa", "T1021");
		oldNewMap.put("8a96217a3873406e9c98a30e158b2886", "T1091");
		oldNewMap.put("5b4378fd232a45daab193d4a6cbbe356", "T1072");
		oldNewMap.put("52265ccc83ea4280abd23d833a328da9", "T1080");
		oldNewMap.put("574ff14e2d1e41f88c9d53eb6d5a8020", "T1550");
		oldNewMap.put("8669c5517bd04a25a8e4b25f8d29087d", "T1557");
		oldNewMap.put("6229c90f6b6143948b81a0a57f894086", "T1560");
		oldNewMap.put("1e689ca9404a4dbd96f2a59f86660754", "T1123");
		oldNewMap.put("993c354e45314d1f8054281dbf420467", "T1119");
		oldNewMap.put("224d0a1a44594adea497ba20f0ee4fee", "T1185");
		oldNewMap.put("7498c14b705c42ce8c9da77c72cd1ecb", "T1115");
		oldNewMap.put("2ef70e12a8e84cd0971667fbad606ad0", "T1602");
		oldNewMap.put("66cb5d3839554b3e8c3c2128bb4e8aaa", "T1213");
		oldNewMap.put("bf2d11b89ef647c1be0fa99ef398a55f", "T1005");
		oldNewMap.put("cf718ffb646b44eeb7edfbc3455c52e7", "T1039");
		oldNewMap.put("f2d3d625376d4ae5ac3125f17a3e59aa", "T1025");
		oldNewMap.put("655bd5faf879499ba2151862fcc043b3", "T1074");
		oldNewMap.put("7afbb5e06f854465ae1942df7dcd924b", "T1114");
		oldNewMap.put("f46401f93cab4468a70b7c0e6893aa5a", "T1056");
		oldNewMap.put("cf22287f891941f7a7177f6223814af6", "T1113");
		oldNewMap.put("1a5695f35b8244b3b77446a84b7faae9", "T1125");
		oldNewMap.put("8e8591c879d84a919b972e317249ee73", "T1071");
		oldNewMap.put("a66002aa02ba4b64ba6fa1afa9765ce2", "T1092");
		oldNewMap.put("a1d1feaa54fd4fa1b5452423163bfe56", "T1132");
		oldNewMap.put("4804e60afe0c4ca9a17849fa2ca20f02", "T1001");
		oldNewMap.put("8ec115911f0841bda9a96985f0f80e0d", "T1568");
		oldNewMap.put("de892a070e384789b2f7a8ad2635d1b5", "T1573");
		oldNewMap.put("bc0cc3043a85425e99510991d62d696d", "T1008");
		oldNewMap.put("fe3bd3da40fc4c1fb6e42e7124d6c31e", "T1105");
		oldNewMap.put("550658d6d90f42b5b2bd53f4e1a5edb9", "T1104");
		oldNewMap.put("0f6bc2407d404625b397d2cce3fe0757", "T1095");
		oldNewMap.put("f544d75d04274c03b9cc5405b22bcfcf", "T1571");
		oldNewMap.put("0d25d1b16cae4d4894ff61d9f0c63da2", "T1572");
		oldNewMap.put("4264229e792c499baa964940ad9c2822", "T1090");
		oldNewMap.put("bb6e3ad02e9844d2b6a6910c583981d4", "T1219");
		oldNewMap.put("04fe94c2fc4f49f08bd0b559fe8e03f9", "T1205");
		oldNewMap.put("b43946b288a94df3b66e21e1e049a166", "T1102");
		oldNewMap.put("e6286d57f405470cbdebf59754768744", "T1020");
		oldNewMap.put("2cfa01a95aed4e8fbc2d8d3bc29c1bd4", "T1030");
		oldNewMap.put("af86ea506fdd46c4815be68f1ad0873f", "T1048");
		oldNewMap.put("d314954273c648e9a768104008a0a6eb", "T1041");
		oldNewMap.put("899be3a3fb5c4e3b9b62afabd95533f2", "T1011");
		oldNewMap.put("70f86ad3aa6f4fa794da81c39c33add2", "T1052");
		oldNewMap.put("5c83ae53e4114b72a4c54d0d9467e19b", "T1567");
		oldNewMap.put("58bba371bd48403a8e04c2eca099e808", "T1029");
		oldNewMap.put("9a6093d06eb14004b2111a9e6d006fc7", "T1537");
		oldNewMap.put("94dfe6d506eb420586e39130e78e4991", "T1531");
		oldNewMap.put("0bcb0ae9ff604ea4910d9c70ae24c490", "T1485");
		oldNewMap.put("7f622aad141c4e9fa4222c017e8768a5", "T1486");
		oldNewMap.put("38fc46beabea44fa8a60c079801363c9", "T1565");
		oldNewMap.put("2cf23c083b764b8c921abdc442567789", "T1491");
		oldNewMap.put("6a580066fbb64015a954882b40cbcbde", "T1561");
		oldNewMap.put("ca2c48ba0854462aa8684f6b854744b3", "T1499");
		oldNewMap.put("281b69e14c724067978f3496db76ee75", "T1495");
		oldNewMap.put("ad55b35949c24c298559b77558c0baea", "T1490");
		oldNewMap.put("8d9fc4046bb444de80aa98194b238c86", "T1498");
		oldNewMap.put("e80475658e5f4bf2a6d52f19d529aa36", "T1496");
		oldNewMap.put("799c0194dca249828bf7041bee9b6bd0", "T1489");
		oldNewMap.put("b91cdd0799a74369b05a3f166245820f", "T1529");
		return oldNewMap;
	}
}

/**
 * Attack 缓存
 */
@Data
class AttackCache {

	private List<AttackCategoryBo<FrameItemBase>> zh = Lists.newArrayList();

	private List<AttackCategoryBo<FrameItemBase>> en = Lists.newArrayList();
}

/**
 * Attack 前台中文
 */
@Data
class AttackFrontZh {

	private List<AttackCategoryBo<AttackFrontBo>> zh;

}

/**
 * Attack 前台英文
 */
@Data
class AttackFrontEn {

	private List<AttackCategoryBo<AttackFrontBo>> en;
}

/**
 * Attack 后台中文
 */
@Data
class AttackBackZh {

	private List<AttackCategoryBo<AttackBackBo>> zh;

}

/**
 * Attack 后台英文
 */
@Data
class AttackBackEn {

	private List<AttackCategoryBo<AttackBackBo>> en;
}
