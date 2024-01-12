package com.cyberpeace.scene.sceneimport.convert;

import com.cyberpeace.scene.base.BaseCloudPluginSceneElementBO;
import com.cyberpeace.scene.base.BaseCloudPluginSceneElementModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;



/**
 * @Description 写入images目录中解析出来的镜像信息
 * @Author maojun
 * @Date 2021/10/12
 */
class SceneFilePathConverter implements IConverter {

    @Override
    public void convert2BO(ConvertDataContainer container) {
        String sceneFilePath = container.getSceneFilePath();
        if (StringUtils.isEmpty(sceneFilePath)) {
            return;
        }
        var cloudElementMap = container.getCloudPluginElements();
        if (CollectionUtils.isNotEmpty(container.getNodeElements())) {
            container.getNodeElements().forEach(e -> {
                BaseCloudPluginSceneElementBO cloudElement = cloudElementMap.get(e.getId());
                if (cloudElement.getRole() != null && cloudElement.getRole() == BaseCloudPluginSceneElementModel.Role.VIRTUAL_TERMINAL) {
                    e.setFile(sceneFilePath);
                }
            });
        }
    }
}
