package com.cyberpeace.scene.sceneimport.convert;


import com.cyberpeace.scene.base.BaseCloudPluginSceneBO;
import com.cyberpeace.scene.base.BaseSceneBO;
import com.cyberpeace.scene.sceneimport.parse.result.SceneElementParseResult;

/**
 * @Description 将解析出的scene节点转成BO
 * @Author maojun
 * @Date 2021/10/12
 */
class SceneElementConverter implements IConverter {

    private SceneElementParseResult parseResult;

    public SceneElementConverter(SceneElementParseResult parseResult) {
        this.parseResult = parseResult;
    }

    public void convert2BO(ConvertDataContainer container) {
        BaseSceneBO sceneConfigBO = this.createSceneConfigBO(this.parseResult);
        container.setSceneConfigBO(sceneConfigBO);

        BaseCloudPluginSceneBO cloudPlugin = new BaseCloudPluginSceneBO();
        container.setPluginSceneConfigBO(cloudPlugin);
    }

    private BaseSceneBO createSceneConfigBO(SceneElementParseResult scene) {
        if (scene == null) {
            return null;
        }
        BaseSceneBO sceneConfigBO = new BaseSceneBO();
        sceneConfigBO.setName(scene.getName());
        sceneConfigBO.setDescription(scene.getDesc());
        // 传递场景信息
        sceneConfigBO.setDisplayContext(scene.getDisplayContext());
        return sceneConfigBO;
    }
}
