package com.funguscow.splat.data;

import java.util.Random;

/**
 * Necessary parameters for image generation
 */
public class Specs {

    public int width = 16, height = 16;
    public int colors = 4, seeds = 4;
    public int targetWidth = 16, targetHeight = 16;
    public String scaleName = "NearestNeighbor";
    public long seed;

    public float minProb = 0f, maxProb = 1f, bias = 0.5f, gain = 0.5f;
    public float xMirror = 0.5f, yMirror = 0.5f, pMirror = 0.5f, nMirror = 0.5f;
    public float variance = 0.5f, mutation = 0.5f;
    public float hue = 0, saturation = 0, value = 0;
    public float hue_sigma = 1f / 6 / 2, saturation_sigma = 0.1f, value_sigma = 0.1f;

    public float[] caProbs = {0.9f, 0.5f, 0, 0};
    public int caGenerations = 1;

    public boolean randomSeed = true, randomColor = true;

    /**
     * Randomize the specs given a RNG
     * @param random RNG to use
     */
    public void randomize(Random random) {
        colors = 1 + random.nextInt(8);
        seeds = 1 + random.nextInt(width);
        seed = random.nextLong();
        minProb = random.nextFloat();
        maxProb = random.nextFloat();
        bias = random.nextFloat();
        gain = random.nextFloat();
        xMirror = random.nextFloat();
        yMirror = random.nextFloat();
        pMirror = random.nextFloat();
        nMirror = random.nextFloat();
        hue = random.nextFloat();
        saturation = (float) Math.max(0, 1 - Math.abs(random.nextGaussian()) * .25);
        value = (float) Math.max(0, 1 - Math.abs(random.nextGaussian()) * .33);
        variance = 0.1f + 0.9f * random.nextFloat();
        mutation = random.nextFloat() * 0.5f;
        for (int i = 0; i < caProbs.length; i++) {
            caProbs[i] = random.nextFloat();
        }
    }

}
