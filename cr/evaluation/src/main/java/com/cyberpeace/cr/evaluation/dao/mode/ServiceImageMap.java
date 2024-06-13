package com.cyberpeace.cr.evaluation.dao.mode;

import lombok.Data;

import java.util.List;

@Data
public class ServiceImageMap {
    private List<ServiceImage> serviceImage;

    public String getImageByService(String name) {
        ServiceImage image = serviceImage.stream().filter(service -> service.getName().equals(name)).findAny().orElse(null);
        return image == null ? "default" : image.getImage();
    }
}
