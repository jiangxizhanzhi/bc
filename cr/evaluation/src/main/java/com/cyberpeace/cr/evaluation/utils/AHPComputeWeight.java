package com.cyberpeace.cr.evaluation.utils;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AHPComputeWeight {

    public static void main(String[] args) {


        double[][] a = new double[][]{{1, 1.8, 2.2, 1},
                {0.6, 1, 3, 1.7},
                {0.4, 0.3, 1, 0.5}, {1, 0.5, 2, 1}
        };
        int n = a[0].length;
        double[] weight = new double[n];
        AHPComputeWeight instance = AHPComputeWeight.getInstance();
        instance.weight(a, weight, n);
    }

    /**
     * 单例
     */
    private static final AHPComputeWeight ACW = new AHPComputeWeight();

    /**
     * 平均随机一致性指针
     */
	private double[] ri = { 0.00, 0.00, 0.58, 0.90, 1.12, 1.21, 1.32, 1.41,
            1.45, 1.49};

    /**
     * 随机一致性比率
     */
	private double cr = 0.0;

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


	public void weight(double[][] a, double[] weight, int n) {
        // 初始向量Wk
		double[] w0 = new double[n];
		for (int i = 0; i < n; i++) {
			w0[i] = 1.0 / n;
        }

        // 一般向量W（k+1）
		double[] w1 = new double[n];
        // W（k+1）的归一化向量
		double[] w2 = new double[n];
        double sum;
        double d = 1.0;

        // 误差
        double delt = 0.00001;
        while (d > delt) {
            d = 0.0;
            sum = 0;
            // 获取向量
			for (int j = 0; j < n; j++) {
                double t = 0.0;
				for (int l = 0; l < n; l++) {
                    t += a[j][l] * w0[l];
                }
                w1[j] = t;
                sum += w1[j];
            }

            // 向量归一化
			for (int k = 0; k < n; k++) {
				if (sum == 0) {
					log.error("Sum cant be zero!");
					break;
				}
                w2[k] = w1[k] / sum;
                // 最大差值
                d = Math.max(Math.abs(w2[k] - w0[k]), d);
                // 用于下次迭代使用
                w0[k] = w2[k];
            }
        }

        // 计算矩阵最大特征值lamta，CI，RI
		/**
		 * 最大特征值
		 */
		double lamta = 0.0;
		for (int k = 0; k < n; k++) {
			lamta += w1[k] / (n * w0[k]);
        }
		double CI = (lamta - n) / (n - 1);
		if (ri[n - 1] != 0) {
			cr = CI / ri[n - 1];
        }
        // 四舍五入处理
		cr = round(cr, 3);

		for (int i = 0; i < n; i++) {
            w0[i] = round(w0[i], 4);
            w1[i] = round(w1[i], 4);
            w2[i] = round(w2[i], 4);
        }

		System.arraycopy(w2, 0, weight, 0, n);
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
	public double getCr() {
		return cr;
    }
}