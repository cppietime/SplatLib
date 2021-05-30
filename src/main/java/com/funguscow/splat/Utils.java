package com.funguscow.splat;

/**
 * Common functions, mostly math/IO
 */
public class Utils {

    public static final float LOG_0_5 = (float) Math.log(0.5);

    /**
     * Convert hue, saturation, value, and alpha to ARGB
     *
     * @param h Hue in [0, 1]
     * @param s Saturation in [0, 1]
     * @param v Value in [0, 1]
     * @param a Alpha in [0, 1]
     * @return Color as 0xAARRGGBB
     */
    public static int HSVA_to_ARGB(float h, float s, float v, float a) {
        h = (((h % 1) + 1) % 1) * 6; // Adjust possible negatives
        int sextant = (int) h;
        h %= 1;
        if ((sextant & 1) == 1) {
            h = 1f - h;
        }
        // hi = v
        float low = v * (1f - s);
        float medium = low + v * s * h;
        int r, g, b;
        switch (sextant) {
            case 0: // Red-Yellow
                r = (int) (v * 255);
                g = (int) (medium * 255);
                b = (int) (low * 255);
                break;
            case 1: // Yellow-Green
                r = (int) (medium * 255);
                g = (int) (v * 255);
                b = (int) (low * 255);
                break;
            case 2: // Green-Cyan
                r = (int) (low * 255);
                g = (int) (v * 255);
                b = (int) (medium * 255);
                break;
            case 3: // Cyan-Blue
                r = (int) (low * 255);
                g = (int) (medium * 255);
                b = (int) (v * 255);
                break;
            case 4: // Blue-Magenta
                r = (int) (medium * 255);
                g = (int) (low * 255);
                b = (int) (v * 255);
                break;
            default: // Magenta-Red
                r = (int) (v * 255);
                g = (int) (low * 255);
                b = (int) (medium * 255);
                break;
        }
        int alpha = (int) (a * 255);
        return (alpha << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Convert hue, saturation, and value to ARGB
     *
     * @param h Hue in [0, 1]
     * @param s Saturation in [0, 1]
     * @param v Value in [0, 1]
     * @return Color as 0xFFRRGGBB (alpha = 255)
     */
    public static int HSV_to_ARGB(float h, float s, float v) {
        return HSVA_to_ARGB(h, s, v, 1f);
    }

    /**
     * Perlin bias function
     *
     * @param t    Independent variable
     * @param bias Bias parameter
     * @return {@code t} ^ ( log({@code bias}) / log(0.5) )
     */
    public static float bias(float t, float bias) {
        return (float) Math.pow(t, Math.log(bias) / LOG_0_5);
    }

    /**
     * Perlin gain function
     *
     * @param t    Independent variable
     * @param gain Gain parameter
     * @return Scaled/mirrored bias function
     */
    public static float gain(float t, float gain) {
        if (t <= 0.5f) {
            return 0.5f * bias(t * 2, 1f - gain);
        }
        return 1f - 0.5f * bias(2f - t * 2, 1f - gain);
    }

    /**
     * Linear interpolation
     *
     * @param a Left-value
     * @param b Right-value
     * @param z Interpolation amount
     * @return {@code a} + ({@code b} - {@code a}) * {@code z}
     */
    public static float lerp(float a, float b, float z) {
        return a + (b - a) * z;
    }

    /**
     * Clamp {@code x} between {@code min} and {@code max}
     *
     * @param x   Value to clamp
     * @param min Minimum value
     * @param max Maximum value
     * @return {@code min} if {@code x} \< {@code min}. {@code max} if {@code x} \> {@code max}.
     * otherwise {@code x}
     */
    public static float clamp(float x, float min, float max) {
        return Math.min(Math.max(x, min), max);
    }

    /**
     * Clamp {@code x} between {@code min} and {@code max}
     *
     * @param x   Value to clamp
     * @param min Minimum value
     * @param max Maximum value
     * @return {@code min} if {@code x} \< {@code min}. {@code max} if {@code x} \> {@code max}.
     * otherwise {@code x}
     */
    public static int clamp(int x, int min, int max) {
        return Math.min(Math.max(x, min), max);
    }

    /**
     * Sample a clamped pixel
     *
     * @param image  Image from which to sample
     * @param width  Width in pixels of {@code image}
     * @param height Height in pixels of {@code image}
     * @param x      X index in pixels to sample
     * @param y      Y index in pixels to sample
     * @return Pixel in {@code image} at {@code y} * {@code width} + {@code x}, or the nearest
     * border pixel if out of bounds.
     */
    public static int clampedPixelAt(int[] image, int width, int height, int x, int y) {
        x = clamp(x, 0, width - 1);
        y = clamp(y, 0, height - 1);
        return image[y * width + x];
    }

}
