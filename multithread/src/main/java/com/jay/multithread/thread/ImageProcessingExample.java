package com.jay.multithread.thread;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessingExample {

    private static final String SOURCE_FILE = "C:\\Users\\jayrajpo\\Desktop\\Jayhind\\JayhindRajpoot\\Image Processing\\A.jpg";
    private static final String DESTINATION_FILE = "C:\\Users\\jayrajpo\\Desktop\\Jayhind\\JayhindRajpoot\\Image Processing\\output\\2.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();
//        reColorSingleThreaded(originalImage, resultImage);
        reColorMultiThreaded(originalImage, resultImage, 5);
        long endTime = System.currentTimeMillis();

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);

        System.out.println(String.format("Time to processing image %d ", endTime - startTime));
    }

    public static void reColorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage, int noOfThreads) {
        List<Thread> threads = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / noOfThreads;

        for (int i = 0; i < noOfThreads; i++) {
            final int threadMultiplier = i;

            threads.add(new Thread(() -> {
                int leftCorner = 0;
                int topCorner = height * threadMultiplier;
                recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
            }));
        }

        threads.forEach(Thread::start);

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public static void reColorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height) {

        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
                reColorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    public static void reColorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        if (isShadeOfGrey(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 10);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static int createRGBFromColors(int red, int green, int blue) {
        int rgb = 0;
        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000;

        return rgb;
    }

    public static boolean isShadeOfGrey(int red, int green, int blue) {
        return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
    }

    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return (rgb & 0x000000FF);
    }
}
