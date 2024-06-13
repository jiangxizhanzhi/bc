package com.cyberpeace.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtils {
    
    public static String convertKeysToCamelCase(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode objectNode = objectMapper.readValue(json, ObjectNode.class);

            ObjectNode camelCaseObjectNode = objectMapper.createObjectNode();
            objectNode.fields().forEachRemaining(entry -> {
                camelCaseObjectNode.set(convertToCamelCase(entry.getKey()), entry.getValue());
            });

            return objectMapper.writeValueAsString(camelCaseObjectNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertToCamelCase(String input) {
        StringBuilder camelCase = new StringBuilder();
        boolean nextUpperCase = false;

        for (char c : input.toCharArray()) {
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    camelCase.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    camelCase.append(Character.toLowerCase(c));
                }
            }
        }

        return camelCase.toString();
    }
}