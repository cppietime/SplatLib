package com.funguscow.splat.cli;

import com.funguscow.clim.CliParser;
import com.funguscow.clim.Option;
import com.funguscow.splat.data.Specs;

import java.util.Random;

public class SplatParser extends CliParser {

    private Random random = new Random();
    private String outputDir = null;
    private int numImages = 1;

    public SplatParser() {
        super();
        addArgument(new Option(
                'b',
                "bias",
                null,
                null,
                "Perlin bias function parameter",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Perlin bias parameter
        addArgument(new Option(
                'g',
                "gain",
                null,
                null,
                "Perlin gain function parameter",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Perlin gain parameter
        addArgument(new Option(
                'w',
                "width",
                null,
                null,
                "Width in pixels of the grid to generate",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Width of grid
        addArgument(new Option(
                'h',
                "height",
                null,
                null,
                "Height in pixels of the grid to generate",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Height of grid
        addArgument(new Option(
                'W',
                "scaled_width",
                null,
                null,
                "Width in pixels of output image",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Width of image
        addArgument(new Option(
                'H',
                "scaled_height",
                null,
                null,
                "Height in image of output image",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Height of image
        addArgument(new Option(
                'd',
                "edge",
                null,
                null,
                "Filled pixel density at image edges",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Edge density
        addArgument(new Option(
                'D',
                "center",
                null,
                null,
                "Filled pixel density at image center",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Center density
        addArgument(new Option(
                'x',
                "mirror_x",
                null,
                null,
                "Probability to mirror on X-axis",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // X-axis mirror
        addArgument(new Option(
                'y',
                "mirror_y",
                null,
                null,
                "Probability to mirror on Y-axis",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Y-axis mirror
        addArgument(new Option(
                'i',
                "mirror_positive",
                null,
                null,
                "Probability to mirror on positive diagonal",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // +diagonal (identity) mirror
        addArgument(new Option(
                'I',
                "mirror_negative",
                null,
                null,
                "Probability to mirror on negative diagonal",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // -diagonal (identity) mirror
        addArgument(new Option(
                null,
                "despeckle",
                null,
                null,
                "Probability to remove pixels with 0 filled neighbors",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Despeckle
        addArgument(new Option(
                null,
                "despur",
                null,
                null,
                "Probability to remove pixels with 1 filled neighbor",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Despur
        addArgument(new Option(
                null,
                "relax",
                null,
                null,
                "Probability to fill pixels with 7 filled neighbors",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Relax
        addArgument(new Option(
                null,
                "devoid",
                null,
                null,
                "Probability to fill pixels with 8 filled neighbors",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Devoid
        addArgument(new Option(
                'h',
                "hue",
                null,
                null,
                "Hue of thematic color",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Hue
        addArgument(new Option(
                's',
                "saturation",
                null,
                null,
                "Saturation of thematic color",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Saturation
        addArgument(new Option(
                'v',
                "value",
                null,
                null,
                "Value of thematic color",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Value
        addArgument(new Option(
                'H',
                "hue_sigma",
                null,
                null,
                "Standard deviation of hue for random colors",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Hue sigma
        addArgument(new Option(
                'S',
                "saturation_sigma",
                null,
                null,
                "Standard deviation of saturation for random colors",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Saturation sigma
        addArgument(new Option(
                'V',
                "value_sigma",
                null,
                null,
                "Standard deviation of value for random colors",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Value sigma
        addArgument(new Option(
                'c',
                "colors",
                null,
                null,
                "Number of colors in palette",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Num colors
        addArgument(new Option(
                'C',
                "seeds",
                null,
                null,
                "Number of locations to start color propagation",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Num seeds
        addArgument(new Option(
                'e',
                "color_speed",
                null,
                null,
                "Probability of successful color propagation",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Color speed
        addArgument(new Option(
                'E',
                "mutation",
                null,
                null,
                "Probability of color mutation",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Mutation
        addArgument(new Option(
                'z',
                "seed",
                null,
                null,
                "Random number seed",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Seed
        addArgument(new Option(
                null,
                "eagle2x",
                "function",
                "Eagle2x",
                "Use Eagle2x for scaling",
                Option.ArgumentType.NONE,
                Option.ArgumentAction.SET
        )); // Scaling function
        addArgument(new Option(
                null,
                "eagle3x",
                "function",
                "Eagle3x",
                "Use Eagle3x for scaling",
                Option.ArgumentType.NONE,
                Option.ArgumentAction.SET
        )); // Scaling function
        addArgument(new Option(
                null,
                "scale2x",
                "function",
                "Scale2x",
                "Use Scale2x for scaling",
                Option.ArgumentType.NONE,
                Option.ArgumentAction.SET
        )); // Scaling function
        addArgument(new Option(
                null,
                "scale3x",
                "function",
                "Scale3x",
                "Use Scale3x for scaling",
                Option.ArgumentType.NONE,
                Option.ArgumentAction.SET
        )); // Scaling function
        addArgument(new Option(
                null,
                "random_seed",
                null,
                "set",
                "Use a new seed for each image",
                Option.ArgumentType.NONE,
                Option.ArgumentAction.SET
        )); // Randomize seed
        addArgument(new Option(
                null,
                "random_color",
                null,
                "set",
                "Randomize color for each image",
                Option.ArgumentType.NONE,
                Option.ArgumentAction.SET
        )); // Randomize colors
        addArgument(new Option(
                null,
                "randomize_all",
                null,
                "set",
                "Randomize (almost) all parameters",
                Option.ArgumentType.NONE,
                Option.ArgumentAction.SET
        )); // Randomize all
        addArgument(new Option(
                'o',
                "output",
                null,
                null,
                "Output path of generated image",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Output filename
        addArgument(new Option(
                'n',
                "num_images",
                null,
                null,
                "Number of images to generate",
                Option.ArgumentType.REQUIRED,
                Option.ArgumentAction.SET
        )); // Batch size
    }

    private int intOf(String key, int failure, int min, int max) {
        if (!wasSet(key)) {
            return failure;
        }
        if (getString(key).equals("X")) {
            return random.nextInt(max - min) + min;
        }
        return getInt(key, failure);
    }

    private float floatOf(String key, float failure, float min, float max) {
        if (!wasSet(key)) {
            return failure;
        }
        if (getString(key).equals("X")) {
            return random.nextFloat() * (max - min) + min;
        }
        return getFloat(key, failure);
    }

    private float floatOf(String key, float failure) {
        return floatOf(key, failure, 0, 1);
    }

    public Specs toSpecs() {
        Specs specs = new Specs();
        if (wasSet("randomize_all")) {
            specs.randomize(random);
        }

        specs.width = getInt("width", specs.width);
        specs.height  = getInt("height", specs.height);
        specs.targetWidth = getInt("scaled_width", specs.width);
        specs.targetHeight = getInt("scaled_height", specs.height);
        specs.colors = intOf("colors", specs.colors, 2, 15);
        specs.seeds = intOf("seeds", specs.seeds, 4, 16);

        specs.variance = floatOf("color_speed", specs.variance, .1f, 1f);
        specs.mutation = floatOf("mutation", specs.mutation);
        specs.minProb = floatOf("edge", specs.minProb, 0, 0.33f);
        specs.maxProb = floatOf("center", specs.maxProb, 0.5f, 1);
        specs.xMirror = floatOf("mirror_x", specs.xMirror);
        specs.yMirror = floatOf("mirror_y", specs.yMirror);
        specs.pMirror = floatOf("mirror_positive", specs.pMirror);
        specs.nMirror = floatOf("mirror_negative", specs.nMirror);
        specs.bias = floatOf("bias", specs.bias);
        specs.gain = floatOf("gain", specs.gain);
        specs.hue = floatOf("hue", specs.hue);
        specs.saturation = floatOf("saturation", specs.saturation);
        specs.value = floatOf("value", specs.value);
        specs.hue_sigma = floatOf("hue_sigma", specs.hue_sigma);
        specs.saturation_sigma = floatOf("saturation_sigma", specs.saturation_sigma);
        specs.value_sigma = floatOf("value_sigma", specs.value_sigma);
        specs.caProbs[0] = floatOf("despeckle", specs.caProbs[0]);
        specs.caProbs[1] = floatOf("despur", specs.caProbs[1]);
        specs.caProbs[2] = floatOf("relax", specs.caProbs[2]);
        specs.caProbs[3] = floatOf("devoid", specs.caProbs[3]);
        specs.seed = random.nextLong();
        if (wasSet("seed")) {
            specs.seed = Long.parseLong(getString("seed"));
        }

        specs.randomSeed = wasSet("random_seed");
        specs.randomColor = wasSet("random_color");
        return specs;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public int getNumImages() {
        return numImages;
    }
}
