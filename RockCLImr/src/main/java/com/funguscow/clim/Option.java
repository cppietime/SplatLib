package com.funguscow.clim;

/**
 * A type of CLI option
 */
public class Option {

    public enum ArgumentType {
        REQUIRED, OPTIONAL, NONE
    }

    public enum ArgumentAction {
        SET, APPEND
    }

    public final Character shortName; // Single character tag, e.g. -v
    public final String longName; // Long name, e.g. --verbose
    public final String key; // Name to store value under, default is same as {@code longName}
    public final String value; // If SET, the value to store, otherwise unused
    public final ArgumentType type; // What arguments does this take?
    public final ArgumentAction action; // What to do when encountered

    /**
     *
     * @param shortName single-character tag following a single hyphen
     * @param longName long-string tag following a double hyphen
     * @param key Name in value map to assign to
     * @param value Value to assign by default, ignored if {@code type} == {@code REQUIRED}
     * @param type Requirement for an argument
     * @param action Action to perform
     */
    public Option(Character shortName,
                  String longName,
                  String key,
                  String value,
                  ArgumentType type,
                  ArgumentAction action) {
        if (shortName == null && longName == null) {
            throw new IllegalArgumentException("Either short or long name must be non-null");
        }
        this.shortName = shortName;
        this.longName = longName;
        this.key = (key == null) ? (longName == null) ? (shortName + "") : longName : key;
        this.value = value;
        this.type = type;
        this.action = action;
    }



}
