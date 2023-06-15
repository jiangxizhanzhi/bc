package com.cyberpeace.cr.evaluation.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Iterator;

public class MatrixData {

    public static double [][] getFormatMatrixData(String matrixData){
        JSONArray objects = JSONArray.parseArray(matrixData);
        double [][] res = new double[objects.size()][];
        for (int i = 0; i < objects.size(); i++) {
            JSONObject jsonObject = objects.getJSONObject(i);
            String data = jsonObject.get("data").toString();
            JSONObject dataJsonObj = JsonUtil.toJsonObject(data, JSONObject.class);
            Collection<Object> values = dataJsonObj.values();
            Iterator<Object> iterator = values.iterator();
            int j = 0;
            res[i] = new double[values.size()];
            while (iterator.hasNext()){
                Object next = iterator.next();
                if(null == next){
                    continue;
                }
                String str = String.valueOf(next);
                if(StringUtils.isBlank(str)){
                    continue;
                }
                double aDouble = Double.parseDouble(str);
                res[i][j++] = aDouble;
            }

        }
        return res;
    }
}