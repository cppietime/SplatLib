package com.funguscow.splat.cli;

import com.funguscow.splat.data.Specs;
import com.funguscow.splat.data.SpriteGrid;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class SplatCli {

    public static void main(String[] args) {
        SplatParser parser = new SplatParser();
        parser.parse(args);
        Specs specs = parser.toSpecs();
        SpriteGrid grid = new SpriteGrid(specs);
        BufferedImage image = new BufferedImage(specs.targetWidth, specs.targetHeight, BufferedImage.TYPE_INT_ARGB);
        BufferedImage gridImage = null;
        Graphics2D gridGraphics = null;
        int gridW = 1, gridH = 1;
        if (parser.isGrid() && parser.getNumImages() > 1) {
            gridW = (int)(Math.sqrt(parser.getNumImages()) + 0.99);
            gridH = (parser.getNumImages() + gridW - 1) / gridW;
            gridImage = new BufferedImage(specs.targetWidth * gridW,
                    specs.targetHeight * gridH,
                    BufferedImage.TYPE_INT_ARGB);
            gridGraphics = (Graphics2D)gridImage.getGraphics();
        }
        DataBufferInt buffer = ((DataBufferInt)image.getRaster().getDataBuffer());
        OutputStream os = null;
        if (parser.getOutputDir() == null) {
            os = System.out;
        }
        Random random = new Random();
        try {
            for (int i = 0; i < parser.getNumImages(); i++) {
                int[] pixels = grid.draw();
                System.arraycopy(pixels, 0, buffer.getData(), 0, pixels.length);
                if (gridImage != null) {
                    gridGraphics.drawImage(image,
                            specs.targetWidth * (i % gridW),
                            specs.targetHeight * (i / gridH),
                            null);
                } else {
                    if (parser.getOutputDir() != null) {
                        String fileName = parser.getOutputDir();
                        if (parser.getNumImages() > 1) {
                            fileName += "_" + i;
                        }
                        fileName += "." + parser.getFormat().toLowerCase();
                        os = new FileOutputStream(fileName);
                    } else if (os == null) {
                        os = System.out;
                    }
                    ImageIO.write(image, parser.getFormat(), os);
                    if (parser.getOutputDir() != null) {
                        os.close();
                    }
                }
                if (specs.randomColor) {
                    specs.hue = random.nextFloat();
                    specs.saturation = random.nextFloat();
                    specs.value = random.nextFloat();
                }
                if (specs.randomSeed) {
                    grid.reseed(random.nextLong());
                }
            }
            if (gridImage != null) {
                if (parser.getOutputDir() != null) {
                    String fileName = parser.getOutputDir();
                    fileName += "." + parser.getFormat().toLowerCase();
                    os = new FileOutputStream(fileName);
                } else if (os == null) {
                    os = System.out;
                }
                ImageIO.write(gridImage, parser.getFormat(), os);
                if (parser.getOutputDir() != null) {
                    os.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
