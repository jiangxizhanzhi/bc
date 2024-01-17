package com.cyberpeace.scene.sceneimport.convert;


import com.cyberpeace.scene.base.BaseSceneElementModel;

/**
 * @Description
 * @Author maojun
 * @Date 2021/10/12
 */
interface IConverter {
    void convert2BO(ConvertDataContainer container);

    static String getPortId(String nodeId, Integer type, int existPortNum) {
        if (type != null && (type == BaseSceneElementModel.Type.TERMINAL||type == BaseSceneElementModel.Type.DOCKER)) {
            return "port_" + nodeId + "_" + (existPortNum + 1);
        } else {
            return "port_" + nodeId + "_electrical_" + (existPortNum + 1);
        }
    }
}
