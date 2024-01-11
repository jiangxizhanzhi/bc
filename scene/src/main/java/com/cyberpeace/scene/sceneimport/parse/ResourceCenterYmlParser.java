package com.cyberpeace.scene.sceneimport.parse;

import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import lombok.var;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.cyberpeace.scene.sceneimport.parse.result.YmlParseResult;
import com.cyberpeace.scene.sceneimport.parse.result.image.ImageInfoParseResult;
import com.cyberpeace.scene.sceneimport.parse.result.image.ImageParseResult;
import com.cyberpeace.scene.sceneimport.parse.result.v2.YmlParseResultV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description 解析资管的yml
 * @Author maojun
 * @Date 2021/10/12
 */
public final class ResourceCenterYmlParser {
    private static String FILE_PATH_SEPARATOR = "/";

    private static Logger log = LoggerFactory.getLogger(ResourceCenterYmlParser.class);

    private static final String CPMS="/cpms/upload/logo/";

    public static YmlParseResult parseTopoYml(String filePath) {
        YmlParseResult result = null;
        File topoV2 = new File(filePath + FILE_PATH_SEPARATOR + "topo2.yml");
        if (topoV2.exists()) {
            try {
                result = new FilePathYmlParser<>(filePath + FILE_PATH_SEPARATOR + "topo2.yml", YmlParseResult.class).parse();
            } catch (Exception e) {
                YmlParseResultV2 resultV2 = new FilePathYmlParser<>(filePath + FILE_PATH_SEPARATOR + "topo2.yml", YmlParseResultV2.class).parse();
                result = resultV2.convert();
            }
        } else {
            try {
                result = new FilePathYmlParser<>(filePath + FILE_PATH_SEPARATOR + "topo.yml", YmlParseResult.class).parse();
            } catch (Exception e) {
                YmlParseResultV2 resultV2 = new FilePathYmlParser<>(filePath + FILE_PATH_SEPARATOR + "topo.yml", YmlParseResultV2.class).parse();
                result = resultV2.convert();
            }
        }

        if (CollectionUtils.isNotEmpty(result.getNodes())) {
            result.getNodes().forEach(node -> {
                if (StringUtils.isNotEmpty(node.getFile())) {
                    node.setFile(filePath + FILE_PATH_SEPARATOR + node.getFile());
                }
            });
        }
        return result;
    }

    public static List<ImageParseResult> parseImageServicesYml(String filePath, String imageFilePath) {
        List<ImageParseResult> results = new ArrayList<>();
        // env/images 下的目录
        results.addAll(parseServicesYml(filePath + FILE_PATH_SEPARATOR + "images"));

        // imageFilePath 下的目录
        if (!StringUtils.isEmpty(imageFilePath)) {
            results.addAll(parseServicesYml(imageFilePath));
        }

        // env 下的server.yml
        String envServerYmlPath = filePath + FILE_PATH_SEPARATOR + "servers.yml";
        File file = new File(envServerYmlPath);
        if (file.exists()) {
            ImageParseResult imageParseResult = new FilePathYmlParser<>(envServerYmlPath, ImageParseResult.class).parse();
            for (Map.Entry<String, ImageInfoParseResult> entry : imageParseResult.getServers().entrySet()) {

                String logo = StringUtils.replace(envServerYmlPath, "servers.yml", "") + entry.getValue().getLOGO();
                if (new File(logo).exists()) {
                    String desLogo = CPMS + IDGeneratorUtil.getID() + FILE_PATH_SEPARATOR + entry.getValue().getLOGO();
                    FileUtil.copyFile(logo, desLogo);
                    entry.getValue().setLOGO(desLogo);
                }
            }
            var servers = imageParseResult.getServers();
            addAllPrivateKey(servers, envServerYmlPath);
            results.add(imageParseResult);
        }
        return results;
    }

    public static List<ImageParseResult> parseImageServicesYml(String filePath, List<String> imageFilePathList) {
        List<ImageParseResult> results = new ArrayList<>();
        // env/images 下的目录
        results.addAll(parseServicesYml(filePath + FILE_PATH_SEPARATOR + "images"));

        // imageFilePath 下的目录
        if (CollectionUtils.isNotEmpty(imageFilePathList)) {
            imageFilePathList.forEach(imageFilePath -> results.addAll(parseServicesYml(imageFilePath)));
        }

        // env 下的server.yml
        String envServerYmlPath = filePath + FILE_PATH_SEPARATOR + "servers.yml";
        File file = new File(envServerYmlPath);
        if (file.exists()) {
            ImageParseResult imageParseResult = new FilePathYmlParser<>(envServerYmlPath, ImageParseResult.class).parse();
            for (Map.Entry<String, ImageInfoParseResult> entry : imageParseResult.getServers().entrySet()) {

                String logo = StringUtils.replace(envServerYmlPath, "servers.yml", "") + entry.getValue().getLOGO();
                if (new File(logo).exists()) {
                    String desLogo = CPMS + IDGeneratorUtil.getID() + FILE_PATH_SEPARATOR + entry.getValue().getLOGO();
                    FileUtil.copyFile(logo, desLogo);
                    entry.getValue().setLOGO(desLogo);
                }
            }
            var servers = imageParseResult.getServers();
            addAllPrivateKey(servers, envServerYmlPath);
            results.add(imageParseResult);
        }
        return results;
    }

    private static List<ImageParseResult> parseServicesYml(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory() || file.listFiles() == null || file.listFiles().length == 0) {
            return new ArrayList<>();
        }
        File[] imageFiles = file.listFiles();
        // 取images目录下存在的servers.yml文件路径
        List<String> serversYmlPaths = Arrays.asList(imageFiles).stream()
                .map(imageFile -> imageFile.getPath() + FILE_PATH_SEPARATOR + "servers.yml")
                .filter(serversYmlPath -> new File(serversYmlPath).exists())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(serversYmlPaths)) {
            return new ArrayList<>();
        }
        // 解析所有的 servers.yml
        var imageParseResult = serversYmlPaths.stream()
                .map(serversYmlPath -> {
                    ImageParseResult parse = new FilePathYmlParser<>(serversYmlPath, ImageParseResult.class).parse();
                    for (Map.Entry<String, ImageInfoParseResult> entry : parse.getServers().entrySet()) {

                        String logo = StringUtils.replace(serversYmlPath, "servers.yml", "") + entry.getValue().getLOGO();
                        if (new File(logo).exists()) {
                            String desLogo = CPMS + IDGeneratorUtil.getID() + FILE_PATH_SEPARATOR + entry.getValue().getLOGO();
                            FileUtil.copyFile(logo, desLogo);
                            entry.getValue().setLOGO(desLogo);
                        }
                    }
                    return parse;
                })
                .collect(Collectors.toList());

        for (int i = 0; i < imageParseResult.size(); i++) {
            var servers = imageParseResult.get(i).getServers();
            var path = serversYmlPaths.get(i);
            addAllPrivateKey(servers, path);
        }

        return imageParseResult;
    }

    private static void addAllPrivateKey(Map<String, ImageInfoParseResult> servers, String path) {
        if (servers != null && !servers.isEmpty()) {
            var accessModes = servers.values().stream().findFirst().get().getAccessModes();
            var manageAccessModes = servers.values().stream().findFirst().get().getManageAccessModes();

            if (CollectionUtils.isNotEmpty(accessModes)) {
                for (var accessMode : accessModes) {
                    if (StringUtils.isNotEmpty(accessMode.getPrivateKey())) {
                        //上传了秘钥,保存秘钥文件地址
                        accessMode.setPrivateKeyPath(StringUtils.replace(path, "servers.yml", "") + accessMode.getPrivateKey());
                    }
                }
            }

            if (CollectionUtils.isNotEmpty(manageAccessModes)) {
                for (var manageAccessMode : manageAccessModes) {
                    if (StringUtils.isNotEmpty(manageAccessMode.getPrivateKey())) {
                        //上传了秘钥,保存秘钥文件地址
                        manageAccessMode.setPrivateKeyPath(StringUtils.replace(path, "servers.yml", "") + manageAccessMode.getPrivateKey());
                    }
                }
            }
        }
    }
}
