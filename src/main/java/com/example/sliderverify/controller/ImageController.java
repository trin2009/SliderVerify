package com.example.sliderverify.controller;

import com.example.sliderverify.service.ImageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @Autowired
    public ImageProducer imageProducer;

    @PostMapping("getImage")
    @CrossOrigin
    public String getImage() {
        String imgBase64Str = imageProducer.getImageBase64Str();
        if (imgBase64Str == null) {
            return "";
        }
        return imgBase64Str;
    }

    @PostMapping("verify")
    @CrossOrigin
    public String verify(int x, int y) {
        String result = "fail";
        if (imageProducer.verify(x, y)){
            return "success";
        }
        return result;
    }
}
