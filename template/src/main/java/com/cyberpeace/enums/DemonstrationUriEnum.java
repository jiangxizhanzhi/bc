package com.cyberpeace.enums;


import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.DateUtil;

import java.util.*;

/**
 * 演示uri枚举
 *
 * @author admin
 * @date 2024/11/12
 */
public enum DemonstrationUriEnum {
    SITUATION_RANKING("/ojj/rtks/situation/ranking/","/cpms/demonstration/situationRanking.json","classpath:static/demonstration/situationRanking.json",null),
    SITUATION_SITUATION("/ojj/rtks/situation/situation/","/cpms/demonstration/situationSituation.json","classpath:static/demonstration/situationSituation.json",null),
    SITUATION_TREND("/ojj/rtks/situation/trend/","/cpms/demonstration/situationTrend.json","classpath:static/demonstration/situationTrend.json",new SituationTrendServiceImpl()),
    SITUATION_NEW("/ojj/rtks/situation/news/","/cpms/demonstration/situationNew.json","classpath:static/demonstration/situationNew.json",new SituationNewsServiceImpl());


    private String uri;

    private String filePath;

    private String systemPath;

    private DemonstrationService demonstrationService;

    /**
     * 演示uri枚举
     *
     * @param uri                  uri
     * @param filePath             文件路径
     * @param systemPath           系统路径
     * @param demonstrationService 示范服务
     */
    DemonstrationUriEnum(String uri, String filePath, String systemPath, DemonstrationService demonstrationService) {
        this.uri = uri;
        this.filePath = filePath;
        this.systemPath = systemPath;
        this.demonstrationService = demonstrationService;
    }

    /**
     * 获得演示服务
     *
     * @return {@link DemonstrationService }
     */
    public DemonstrationService getDemonstrationService() {
        return demonstrationService;
    }

    /**
     * 获取uri
     *
     * @return {@link String }
     */
    public String getUri() {
        return uri;
    }

    /**
     * 获取文件路径
     *
     * @return {@link String }
     */
    public String getFilePath() {
        return filePath;
    }


    /**
     * 获取系统路径
     *
     * @return {@link String }
     */
    public String getSystemPath() {
        return systemPath;
    }

    /**
     * 得到uri
     *
     * @param uri uri
     * @return {@link DemonstrationUriEnum }
     */
    public static DemonstrationUriEnum getByUri(String uri) {

        for (DemonstrationUriEnum demonstrationUriEnum : DemonstrationUriEnum.values()) {
            if (demonstrationUriEnum.getUri().equals(uri)) {
                return demonstrationUriEnum;
            }
        }
        return null;
    }

    /**
     * 示范服务
     *
     * @author admin
     * @date 2024/11/12
     */
    public interface DemonstrationService {
        /**
         * 处理
         *
         * @param content 内容
         * @return {@link String }
         */
        String deal(String content);
    }

    /**
     * 形势新闻服务impl
     *
     * @author admin
     * @date 2024/11/12
     */
    public static class SituationNewsServiceImpl implements DemonstrationService{

        /**
         * 处理
         *
         * @param content 内容
         * @return {@link String }
         */
        @Override
        public String deal(String content) {
            List<JSONObject> list = JSONObject.parseArray(content, JSONObject.class);
            String date = DateUtil.DateToString(new Date(),"yyyy-MM-dd");
            for (JSONObject jsonObject : list) {
                jsonObject.put("create_time",date);
            }
            return JSONObject.toJSONString(list);
        }
    }

    /**
     * 态势趋势服务impl
     *
     * @author admin
     * @date 2024/11/12
     */
    public static class SituationTrendServiceImpl implements DemonstrationService{

        /**
         * 处理
         *
         * @param content 内容
         * @return {@link String }
         */
        @Override
        public String deal(String content) {
            JSONObject trend = JSONObject.parseObject(content);
            List<String> xDates = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < 10; i++) {
                xDates.add(DateUtil.DateToString(calendar.getTime(),"yyyy-MM-dd"));
                calendar.add(Calendar.MONTH,-1);
            }
            Collections.reverse(xDates);
            trend.put("xdates",xDates);
            return trend.toJSONString();
        }
    }
}
