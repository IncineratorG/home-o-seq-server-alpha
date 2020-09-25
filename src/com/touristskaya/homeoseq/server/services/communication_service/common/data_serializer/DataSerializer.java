package com.touristskaya.homeoseq.server.services.communication_service.common.data_serializer;

import com.google.gson.Gson;
import com.touristskaya.homeoseq.common.camera.Camera;
import com.touristskaya.old_homoseq.homeoseq.common.system_events_handler.SystemEventsHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class DataSerializer {
    private Gson mGson;

    public DataSerializer() {
        mGson = new Gson();
    }

    public String serialize(List<Camera> cameras) {
        Map<String, List<Map<String, String>>> serializedCamerasMap = new HashMap<>();

        List<Map<String, String>> serializedCamerasList = new ArrayList<>();
        cameras.forEach(camera -> {
            String id = camera.getId();
            String name = camera.getName();

            Map<String, String> serializedCameraMap = new HashMap<>();
            serializedCameraMap.put("id", id);
            serializedCameraMap.put("name", name);

            serializedCamerasList.add(serializedCameraMap);
        });

        serializedCamerasMap.put("cameras", serializedCamerasList);

        return mGson.toJson(serializedCamerasMap);
    }

    public String serialize(String cameraId, BufferedImage image) {
        Map<String, String> imageObjectMap = new HashMap<>();
        imageObjectMap.put("id", cameraId);
        imageObjectMap.put("serializedImage", "");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            SystemEventsHandler.onError("DataSerializer->serialize()->ERROR:" + e.getMessage());
            return mGson.toJson(imageObjectMap);
        }

        byte[] imageData = byteArrayOutputStream.toByteArray();
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            SystemEventsHandler.onError("DataSerializer->serialize()->ERROR:" + e.getMessage());
            return mGson.toJson(imageObjectMap);
        }

        String serializedImage = Base64.getEncoder().encodeToString(imageData);
        imageObjectMap.put("serializedImage", serializedImage);

        return mGson.toJson(imageObjectMap);
    }
}
