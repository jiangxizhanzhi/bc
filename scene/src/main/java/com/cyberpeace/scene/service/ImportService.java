package com.cyberpeace.scene.service;

import com.cyberpeace.scene.base.BaseSceneTopologyBO;

import java.util.List;

public interface ImportService {

     String importByYmlConfig(String ymlFilePath, String imageFilePath, String sceneFilePath);
}
