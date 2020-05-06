package com.example.sliderverify.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Service
public class ImageProducer {

    private int x = -1;
    private int y = -1;

    private int xDelta = 10;
    private int yDelta = 10;

    public BufferedImage loadImage(String filePath) {
        BufferedImage bufferedImage = null;
        ClassPathResource classPathResource = new ClassPathResource(filePath);

        InputStream inputStream = null;
        try {
            inputStream = classPathResource.getInputStream();
            bufferedImage = ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;
    }

    //生成一张已合成槽位的图片
    public byte[] getVerifyImage() {
        BufferedImage imgBg = loadImage("img/verifyBg.png");
        BufferedImage imgSlot = loadImage("img/verifySlot.png");
        if (imgBg == null || imgSlot == null) {
            return null;
        }

        int bgWidth = imgBg.getWidth();
        int bgHeight = imgBg.getHeight();
        int slotWidth = imgSlot.getWidth();
        int slotHeight = imgSlot.getHeight();

        //以槽位图片中心点为移动点，实际活动范围
        int minX = slotWidth / 2;
        int maxX = bgWidth - slotWidth / 2;
        int minY = slotHeight / 2;
        int maxY = bgHeight - slotHeight / 2;
        //随机一个位置
        x = (int) (Math.random() * (maxX - minX) + minX);
        y = (int) (Math.random() * (maxY - minY) + minY);

        Graphics2D g = imgBg.createGraphics();
        g.drawImage(imgSlot, x - slotWidth / 2, y - slotHeight / 2, imgSlot.getWidth(), imgSlot.getHeight(), null);
        g.dispose();

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(imgBg, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //将一张图片的二进制数据转换为Base64编码的字符串
    public String getImageBase64Str() {
        String base64Str = null;
        byte[] bytes = getVerifyImage();
        if (bytes == null) {
            return null;
        }
        final Base64.Encoder encoder = Base64.getEncoder();
        base64Str = encoder.encodeToString(bytes);
        return base64Str;
    }

    public boolean verify(int clientX, int clientY) {
        boolean b = false;
        if (clientX >= x - xDelta && clientX <= x + xDelta
//                && clientY >= y - yDelta && clientY <= y + yDelta
                ) {
            return true;
        }
        return b;
    }

}
