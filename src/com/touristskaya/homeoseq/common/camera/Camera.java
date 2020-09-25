package com.touristskaya.homeoseq.common.camera;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Camera {
    private String mId;
    private String mName;
    private BufferedImage mImage;

    public Camera() {
        mId = UUID.randomUUID().toString();
        mName = "Camera";
        try {
            mImage = ImageIO.read(new File("C:\\Empty files\\im.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public BufferedImage getImage() {
        return mImage;
    }

    public void setName(String name) {
        mName = name;
    }

//    public String serializeShortForm() {
//        Map<String , String> cameraDescription = new HashMap<>();
//        cameraDescription.put("id", mId);
//        cameraDescription.put("name", mName);
//
//        return new Gson().toJson(cameraDescription);
//    }
}
