package com.cyberpeace.situation.model;

import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.cs.service.context.scene.ScenePlugins;
import com.cyberpeace.cs.service.context.scene.SceneTopology;
import lombok.Data;

import java.io.Serializable;

import static com.cyberpeace.common.constant.MpSceneStatusConstant.*;
import static com.cyberpeace.common.constant.MpSceneStatusConstant.MP_SCENE_PROGRESS_FINISH_CODE;
@Data
public class MpSceneData implements Serializable {

    private SceneTopology topology;

    private ScenePlugins plugins;

}