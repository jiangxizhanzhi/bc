package com.cyberpeace.evaluate.constant;

public class EstimateConstant {

    /**
     * 评估实例结果保存状态
     * */
    public final static Integer MANUAL_SAVE = 1;
    public final static Integer AUTO_SAVE = 0;

    /**
     * 评估实例的状态：1=评估中，0=评估停止，2=计算中,3=停止采集数据
     * 4=不保存评估结果，这类实例也不会被查出；
    * */
    public final static Integer ESTIMATE_ON = 1;
    public final static Integer ESTIMATE_FINISH = 0;
    public final static Integer ESTIMATE_CALCULATE = 2;
    public final static Integer ESTIMATE_STOP = 3;
    public final static Integer ESTIMATE_ABANDON = 4;

}
