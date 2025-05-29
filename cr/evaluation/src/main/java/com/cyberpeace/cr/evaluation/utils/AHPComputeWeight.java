package com.cyberpeace.cr.evaluation.utils;

import java.math.BigDecimal;
import java.util.*;

/**
 * AHPP计算权重
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码实现了一个用于层次分析法（AHP）计算权重的工具类 `AHPComputeWeight`。以下是对该代码的简要分析： 
 * ### 类和方法概述 
 * - **类**: `AHPComputeWeight` 
 * - 该类主要用于计算层次分析法中的权重。 
 * - 使用了单例模式来确保只有一个实例。 
 * - **主要方法**: 
 * - `main`: 程序入口，用于测试。 
 * - `getInstance`: 提供单例实例。 
 * - `weight`: 计算权重的核心方法。 
 * - `round`: 四舍五入的方法。 
 * - `getCR`: 获取随机一致性比率。 
 * ### 核心逻辑 
 * 1. **初始化和单例模式** 
 * - `ACW` 是 `AHPComputeWeight` 的单例实例。 
 * - `getInstance` 方法返回这个单例实例。 
 * 2. **权重计算 (`weight` 方法)** 
 * - 输入参数: 
 * - `a`: 判断矩阵。 
 * - `weight`: 输出权重数组。 
 * - `N`: 矩阵的大小。 
 * - 初始化向量 `w0`, `w1`, `w2`。 
 * - 迭代计算新的权重向量，直到满足误差条件。 
 * - 计算最大特征值 `lamta`、一致性指标 `CI` 和一致性比率 `CR`。 
 * - 结果四舍五入并存储到输出数组 `weight` 中。 
 * 3. **四舍五入 (`round` 方法)** 
 * - 将 `double` 类型的数值四舍五入到指定的小数位数。 
 * 4. **随机一致性比率 (`getCR` 方法)** 
 * - 返回计算得到的一致性比率 `CR`。 
 * ### 示例用法 
 * 在 `main` 方法中，通过创建实例并调用 `weight` 方法，计算给定判断矩阵的权重，并打印结果。 
 * ```java 
 * public static void main(String[] args) { 
 * double[][] a = new double[][]{ 
 * {1, 1.8, 2.2, 1}, 
 * {0.6, 1, 3, 1.7}, 
 * {0.4, 0.3, 1, 0.5}, 
 * {1, 0.5, 2, 1} 
 * }; 
 * int n = a[0].length; 
 * double[] weight = new double[n]; 
 * AHPComputeWeight instance = AHPComputeWeight.getInstance(); 
 * instance.weight(a, weight, n); 
 * System.out.println(Arrays.toString(weight)); 
 * } 
 * ``` 
 * ### 总结 
 * - **优点**: 
 * - 使用了单例模式，保证了全局唯一性。 
 * - 实现了权重计算的核心算法，包括迭代和四舍五入等。 
 * - 代码结构清晰，易于理解和维护。 
 * - **改进点**: 
 * - 可以考虑增加更多的错误检查和异常处理。 
 * - 增加文档注释，详细说明每个方法的作用和输入输出参数。 
 * - 可以优化一些常量的定义，使其更易读和管理。 
 * 整体来看，这是一个功能完整的 AHP 权重计算工具类，适用于需要进行层次分析的应用场景。 
**/
public class AHPComputeWeight {

    /**
     * 主要
     *
     * @param args 参数
     */
    public static void main(String[] args) {


        double[][] a = new double[][]{{1, 1.8, 2.2, 1},
                {0.6, 1, 3, 1.7},
                {0.4, 0.3, 1, 0.5}, {1, 0.5, 2, 1}
        };
        int n = a[0].length;
        double[] weight = new double[n];
        AHPComputeWeight instance = AHPComputeWeight.getInstance();
        instance.weight(a, weight, n);
        System.out.println(Arrays.toString(weight));
    }

    /**
     * 单例
     */
    private static final AHPComputeWeight ACW = new AHPComputeWeight();

    /**
     * 平均随机一致性指针
     */
    private double[] RI = {0.00, 0.00, 0.58, 0.90, 1.12, 1.21, 1.32, 1.41,
            1.45, 1.49};

    /**
     * 随机一致性比率
     */
    private double CR = 0.0;

    /**
     * 最大特征值
     */
    private double lamta = 0.0;

    /**
     * 私有构造
     */
    private AHPComputeWeight() {

    }

    /**
     * 返回单例
     *
     * @return
     */
    public static AHPComputeWeight getInstance() {
        return ACW;
    }


    /**
     * 重量
     *
     * @param a      一个
     * @param weight 重量
     * @param N      n
     */
    public void weight(double[][] a, double[] weight, int N) {
        // 初始向量Wk
        double[] w0 = new double[N];
        for (int i = 0; i < N; i++) {
            w0[i] = 1.0 / N;
        }

        // 一般向量W（k+1）
        double[] w1 = new double[N];
        // W（k+1）的归一化向量
        double[] w2 = new double[N];
        double sum = 1.0;
        double d = 1.0;

        // 误差
        double delt = 0.00001;
        while (d > delt) {
            d = 0.0;
            sum = 0;
            // 获取向量
            //int index = 0;
            for (int j = 0; j < N; j++) {
                double t = 0.0;
                for (int l = 0; l < N; l++) {
                    t += a[j][l] * w0[l];
                }
                w1[j] = t;
                sum += w1[j];
            }
			sum = sum == 0 ? 1 : sum;
            // 向量归一化
            for (int k = 0; k < N; k++) {
                w2[k] = w1[k] / sum;
                // 最大差值
                d = Math.max(Math.abs(w2[k] - w0[k]), d);
                // 用于下次迭代使用
                w0[k] = w2[k];
            }
        }

        // 计算矩阵最大特征值lamta，CI，RI
        lamta = 0.0;
        for (int k = 0; k < N; k++) {
            lamta += w1[k] / (N * w0[k]);
        }
        double CI = (lamta - N) / (N - 1);
        if (RI[N - 1] != 0) {
            CR = CI / RI[N - 1];
        }
        // 四舍五入处理
        lamta = round(lamta, 3);
        CI = round(CI, 3);
        CR = round(CR, 3);

        for (int i = 0; i < N; i++) {
            w0[i] = round(w0[i], 4);
            w1[i] = round(w1[i], 4);
            w2[i] = round(w2[i], 4);
        }

        System.arraycopy(w2, 0, weight, 0, N);
    }


    /**
     * 四舍五入
     *
     * @param v
     * @param scale
     * @return
     */
    public double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 返回随机一致性比率
     *
     * @return
     */
    public double getCR() {
        return CR;
    }
}
