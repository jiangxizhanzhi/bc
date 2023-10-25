package com.cyberpeace.cr.crbase.service.context;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.DateUtil;
import com.cyberpeace.cr.crbase.service.context.mp.AttackTypeCount;
import com.cyberpeace.cr.crbase.service.context.score.GetScoreRsp;
import com.cyberpeace.cr.crbase.service.context.score.SkillScoreContext;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreTrend;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

@Data
public class TrainReportExportContext extends TrainReportContext {

	@ApiModelProperty("任务分类：key 名称 value个数")
	private String kindMapString;

	@ApiModelProperty("红方任务分类：key 名称 value个数")
	private String redMapString;

	@ApiModelProperty("蓝方任务分类：key 名称 value个数")
	private String blueMapString;

	private String timeListString;

	@ApiModelProperty("红方得分趋势")
	private String redTrendsString;

	@ApiModelProperty("蓝方得分趋势")
	private String blueTrendsString;

	@ApiModelProperty("团队得分趋势")
	private String teamTrendsString;

	@ApiModelProperty("攻击流量各类别统计")
	private String attackTypeCountString;

	@ApiModelProperty("场景流量趋势图")
	private String sceneTrendTimeString;

	@ApiModelProperty("场景流量趋势图")
	private String sceneTrendSizeString;

	@ApiModelProperty("攻击流量分类总数")
	private Integer attackTypeAllCount;

	public void initExport() {
        kindMapString=getKindString(getKindMap());
        redMapString=getKindString(getRedMap());
        blueMapString=getKindString(getBlueMap());


        timeListString = getTimeList().stream().map(a->"'"+ DateUtil.dateToString(a,"yyyy-MM-dd HH:mm:ss")+"'").collect(Collectors.joining(","));

        redTrendsString = getTrendString(getRedTrends());
        blueTrendsString = getTrendString(getBlueTrends());
		teamTrendsString = getTrendString(getTeamTrends());


		attackTypeCountString=getKindString(getAttackTypeCount());
		attackTypeAllCount=getAttackTypeCount()==null?0:getAttackTypeCount().stream().mapToInt(AttackTypeCount::getTimes).sum();
		sceneTrendSizeString = getSceneTrend()==null?"": getSceneTrend().stream().map(a->a.getData_size()+"").collect(Collectors.joining(","));
		sceneTrendTimeString = getSceneTrend()==null?"":getSceneTrend().stream().map(a->"'"+a.getTime()+"'").collect(Collectors.joining(","));
		dealScoreRsp(getGetScoreRsps());

    }

	private void dealScoreRsp(List<GetScoreRsp> redGetScoreRsps) {
		for (GetScoreRsp redGetScoreRsp : redGetScoreRsps) {
			List<SkillScoreContext> skillScoreContextList = redGetScoreRsp.getSkillScoreContextList();
			if (CollectionUtils.isNotEmpty(skillScoreContextList)) {
				for (SkillScoreContext skillScoreContext : skillScoreContextList) {
					if (skillScoreContext.getScore()!=null&&skillScoreContext.getScore()==0) {
						skillScoreContext.setScore(null);
					}
				}
			}
		}
	}

	public String getKindString(Map<String, Integer> map) {
		if (map == null || map.size() == 0) {
			return "";
		}
		return map.entrySet().stream()
				.map(a -> "{name:'" + a.getKey() + "',value:" + a.getValue() + "}")
				.collect(Collectors.joining(","));
	}

	public String getKindString(List<AttackTypeCount> list) {
		if (list == null || list.isEmpty()) {
			return "";
		}
		return list.stream()
				.map(a -> "{name:'" + a.getAttack_type() + "',value:" + a.getTimes() + "}")
				.collect(Collectors.joining(","));
	}

	public String getTrendString(List<StudentScoreTrend> list) {
		if (list == null || list.isEmpty()) {
			return "";
		}
		for (StudentScoreTrend trend : list) {
			if(trend.getUserData().getRepName().getBytes().length>19) {
				trend.getUserData().setRepName(getName(trend.getUserData().getRepName()));
			}
		}
		return list.stream()
				.map(a -> "{name:'" + a.getUserData().getRepName() + "',type: 'line',data:[" + a.getScoreTrends().stream().map(b->b.getScore()+"").collect(Collectors.joining(",")) + "],\n" +
						"      showSymbol: false,\n" +
						"      lineStyle: {\n" +
						"        width: 2,\n" +
						"      },\n" +
						"      symbol: 'none',\n" +
						"      smooth: true,\n" +
						"    }")
				.collect(Collectors.joining(","));
	}

	public static String getName(String name) {
		String startName = name;
		//获取name中的前13个字符  一个汉字等于两个字符
		int num = 0;
		int index = 0;
		if (getLength(name) <= 13) {
			return name;
		}
		for (int i = 0; i < name.length(); i++) {
			if (num >= 12) {
				index = i;
				break;
			}
			if (name.charAt(i) > 255) {
				num += 2;
				if (num > 12) {
					index = i ;
					break;
				}
			} else {
				num++;
			}
		}
		if (index > 0) {
			name = name.substring(0, index);
			if (!name.equals(startName)) {
				name += "...";
			}
		}
		return name;
	}

	public static int getLength (String name) {
		int num = 0;
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) > 255) {
				num += 2;
			} else {
				num++;
			}
		}
		return num;
	}
}
