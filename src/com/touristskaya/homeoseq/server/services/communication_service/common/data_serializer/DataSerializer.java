package com.touristskaya.homeoseq.server.services.communication_service.common.data_serializer;

import com.google.gson.Gson;
import com.touristskaya.homeoseq.common.camera.Camera;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
