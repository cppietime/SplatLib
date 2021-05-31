package com.funguscow.clim;

import java.util.Arrays;

public class CliTest {

    private static CliParser parser = new CliParser().addArgument(
            new Option('n', "nothing", "nVar", "nVal", Option.ArgumentType.NONE, Option.ArgumentAction.SET)
    ).addArgument(
            new Option('v', "verbose", null, "", "Sets verbose mode on", Option.ArgumentType.NONE, Option.ArgumentAction.SET)
    ).addArgument(
            new Option('l', "list", null, "entry", Option.ArgumentType.OPTIONAL, Option.ArgumentAction.APPEND)
    ).addArgument(
            new Option('i', "int", null, null, Option.ArgumentType.REQUIRED, Option.ArgumentAction.SET)
    );

    private static void testOneSimpleShort() {
        String[] args = {"-n"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("nVar set to " + parser.getString("nVar"));
        System.out.println("verbose set? " + parser.wasSet("verbose"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testManySimpleShort() {
        String[] args = {"-n", "-l", "-v", "-l", "-l"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("nVar set to " + parser.getString("nVar"));
        System.out.println("verbose set? " + parser.wasSet("verbose"));
        System.out.println("list set to " + parser.getStrings("list"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testManyCompoundShort() {
        String[] args = {"-nv"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("nVar set to " + parser.getString("nVar"));
        System.out.println("verbose set? " + parser.wasSet("verbose"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testSingleShortWithArgNoSpace() {
        String[] args = {"-lword"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("list set to " + parser.getStrings("list"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testSingleShortWithArgSpace() {
        String[] args = {"-l", "-l", "word", "-l"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("list set to " + parser.getStrings("list"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testSingleShortWithArgNoSpaceEnd() {
        String[] args = {"-lword", "end", "-l"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("list set to " + parser.getStrings("list"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testSingleShortWithArgSpaceEnd() {
        String[] args = {"-l", "word", "end"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("list set to " + parser.getStrings("list"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testManySimpleLong() {
        String[] args = {"--nothing", "--list", "--verbose", "--list", "--list"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("nVar set to " + parser.getString("nVar"));
        System.out.println("verbose set? " + parser.wasSet("verbose"));
        System.out.println("list set to " + parser.getStrings("list"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testSingleLongEq() {
        String[] args = {"--list=string"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("list set to " + parser.getStrings("list"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testSingleLongSpace() {
        String[] args = {"--list", "--list", "word", "-l"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("list set to " + parser.getStrings("list"));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testShortRequiredSpace() {
        String[] args = {"-i", "27", "after"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("int set to " + parser.getInt("int", -1));
        System.out.println("endargs = " + parser.getArguments());
    }


    private static void testShortRequiredNoSpace() {
        String[] args = {"-i27", "after"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("int set to " + parser.getInt("int", -1));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testLongRequiredSpace() {
        String[] args = {"--int", "27", "after"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("int set to " + parser.getInt("int", -1));
        System.out.println("endargs = " + parser.getArguments());
    }

    private static void testLongRequiredEq() {
        String[] args = {"--int=27", "after"};
        System.out.println("\n\nTesting " + Arrays.toString(args));
        parser.clear();
        parser.parse(args);
        System.out.println("int set to " + parser.getInt("int", -1));
        System.out.println("endargs = " + parser.getArguments());
    }

    public static void main(String[] args) {
        parser.help();
        testOneSimpleShort();
        testManySimpleShort();
        testManyCompoundShort();
        testSingleShortWithArgNoSpace();
        testSingleShortWithArgSpace();
        testSingleShortWithArgNoSpaceEnd();
        testSingleShortWithArgSpaceEnd();
        testManySimpleLong();
        testSingleLongEq();
        testSingleLongSpace();
        testShortRequiredSpace();
        testShortRequiredNoSpace();
        testLongRequiredSpace();
        testLongRequiredEq();
    }

}
