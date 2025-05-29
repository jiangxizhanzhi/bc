package com.cyberpeace.cr.evaluation.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Iterator;

/**
 * 矩阵数据
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MatrixData` 的工具类，用于处理矩阵数据。具体来说，它提供了一个静态方法 `getFormatMatrixData`，该方法将字符串形式的矩阵数据转换为二维双精度浮点数数组。 
 * ### 主要组成部分 
 * 1. **导入的库**： 
 * - `com.alibaba.fastjson.JSONArray` 和 `com.alibaba.fastjson.JSONObject`：用于解析 JSON 数据。 
 * - `com.cyberpeace.common.util.JsonUtil`：用于辅助 JSON 操作。 
 * - `org.apache.commons.lang3.StringUtils`：用于字符串操作。 
 * - `java.util.Collection` 和 `java.util.Iterator`：用于遍历集合。 
 * 2. **类定义**： 
 * - `MatrixData` 类：包含一个静态方法 `getFormatMatrixData`。 
 * 3. **方法 `getFormatMatrixData`**： 
 * - **参数**：`String matrixData`，表示输入的矩阵数据，以字符串形式表示。 
 * - **返回值**：`double[][]`，表示格式化后的矩阵数据。 
 * - **步骤**： 
 * 1. 将输入的 JSON 字符串解析为 `JSONArray` 对象。 
 * 2. 初始化一个 `double[][]` 数组，其行数等于 `JSONArray` 的大小。 
 * 3. 遍历 `JSONArray` 中的每个元素（每个元素是一个 JSON 对象）。 
 * 4. 提取 JSON 对象中的 `"data"` 字段，并将其解析为另一个 `JSONObject`。 
 * 5. 获取 `JSONObject` 中的所有值，并遍历这些值。 
 * 6. 将非空且非空白的值转换为 `double` 并存储到结果数组中。 
 * 7. 返回格式化后的矩阵数据。 
 * ### 总结 
 * - 这段代码的主要功能是将 JSON 格式的字符串数据转换为二维双精度浮点数数组。 
 * - 它使用了 FastJSON 库来解析 JSON 数据，并使用 Apache Commons Lang 库进行字符串处理。 
 * - 方法逻辑清晰，通过嵌套循环处理每一层的数据结构。 
 * ### 可能的改进 
 * - 可以添加异常处理机制，以处理可能的解析错误或类型转换错误。 
 * - 可以增加更多的注释，以便其他开发者更好地理解代码的功能和实现细节。 
**/
public class MatrixData {

    /**
     * 获取格式矩阵数据
     *
     * @param matrixData 矩阵数据
     * @return {@code double [][] }
     */
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
