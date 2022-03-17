package com.baba.babaisyou.view;

import com.baba.babaisyou.model.Object;
import com.baba.babaisyou.model.enums.Material;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Animation extends ImageView {

    Map<Material, ArrayList<ImageView>> imageViewInstances = createImageViewInstances();

    private static Map<Material, ArrayList<ImageView>> createImageViewInstances() {
        Map<Material, ArrayList<ImageView>> instances = new HashMap<Material, ArrayList<ImageView>>();
        for (Material material : Material.values()) {
            instances.put(material, new ArrayList<ImageView>());
        }
        return instances;
    }


    Animation(Image image, Material material) {
        super(image);

    }
}
