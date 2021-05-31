package com.funguscow.clim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parser for CLI options
 */
public class CliParser {

    private final Map<String, Option> optionsLong;
    private final Map<Character, Option> optionsShort;
    private final Map<String, List<String>> values;
    private final List<String> endArgs;

    public CliParser() {
        optionsLong = new HashMap<>();
        optionsShort = new HashMap<>();
        values = new HashMap<>();
        endArgs = new ArrayList<>();
    }

    /**
     * Register {@code arg}
     * @param arg Option to add
     * @return this for convenience
     */
    public CliParser addArgument(Option arg) {
        if (arg.shortName != null) {
            optionsShort.put(arg.shortName, arg);
        }
        if (arg.longName != null) {
            optionsLong.put(arg.longName, arg);
        }
        return this;
    }

    /**
     * Act on the option for a single char by its short name
     * @param c Short name of option
     */
    private void singleCharOpt(char c) {
        if (!optionsShort.containsKey(c)) {
            throw new UnknownOptionException("Unknown option \"-" + c + "\"");
        }
        Option option = optionsShort.get(c);
        if (option.type != Option.ArgumentType.NONE) {
            throw new IllegalArgumentException("Cannot chain multiple options with possible arguments");
        }
        gotOption(option, null);
    }

    /**
     * Act on an option given the option and the argument (may be {@code null})
     * @param option Option found
     * @param argument Argument, or {@code null}
     */
    private void gotOption(Option option, String argument) {
        List<String> list = values.computeIfAbsent(option.key, key -> new ArrayList<>());
        String value;
        switch (option.type) {
            case NONE: {
                value = option.value;
                break;
            }
            case REQUIRED: {
                value = argument;
                break;
            }
            default: {
                if (argument == null) {
                    value = option.value;
                } else {
                    value = argument;
                }
                break;
            }
        }
        if (option.action == Option.ArgumentAction.SET) {
            list.clear();
        }
        list.add(value);
    }

    /**
     * Parse a series of Strings as the CLI splits them up, same as passed to {@code main}
     * @param args Option strings
     */
    public void parse(String... args) {
        boolean parsing = true;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.charAt(0) == '-' && parsing) {
                if (arg.length() == 1) { // -
                    parsing = false;
                    continue;
                }
                Option option;
                String argument = null;
                if (arg.charAt(1) == '-') { // Long option
                    if (arg.length() == 2) { // --
                        parsing = false;
                        continue;
                    }
                    String optName;
                    int eq = arg.indexOf('=');
                    if (eq != -1) { // Argument specified
                        optName = arg.substring(2, eq);
                        if (arg.length() <= eq + 1) {
                            throw new InvalidArgumentException("Nothing follows assigning '=' in option");
                        }
                        argument = arg.substring(eq + 1);
                    } else {
                        optName = arg.substring(2);
                    }
                    if (!optionsLong.containsKey(optName)) {
                        throw new UnknownOptionException("Unknown option \"--" + optName + "\"");
                    }
                    option = optionsLong.get(optName);
                } else { // Short option
                    char key = arg.charAt(1);
                    if (!optionsShort.containsKey(key)) {
                        throw new UnknownOptionException("Unknown option \"-" + key + "\"");
                    }
                    option = optionsShort.get(key);
                    if (arg.length() > 2) {
                        if (option.type != Option.ArgumentType.NONE) {
                            argument = arg.substring(2);
                        }
                        else {
                            gotOption(option, null);
                            for (char c : arg.substring(2).toCharArray()) {
                                singleCharOpt(c);
                            }
                            continue;
                        }
                    }
                }
                if (option.type == Option.ArgumentType.NONE) {
                    if (argument != null) {
                        throw new IllegalArgumentException("Option given unexpected argument \"" + argument + "\"");
                    }
                    gotOption(option, null);
                    continue;
                }
                else if (argument == null && i + 1 < args.length) {
                    String next = args[i + 1];
                    if (next.charAt(0) != '-') {
                        i++;
                        argument = next;
                    }
                }
                if (option.type == Option.ArgumentType.REQUIRED && argument == null) {
                    throw new IllegalArgumentException("Option required argument but got none");
                }
                gotOption(option, argument);
            } else { // End of options
                for (; i < args.length; i++) {
                    endArgs.add(args[i]);
                }
                break;
            }
        }
    }

    /**
     * Get the single string representation of a key's value
     * @param key Named key for option value
     * @return The string of {@code key}, or a comma-delimited list
     */
    public String getString(String key) {
        if (values.containsKey(key)) {
            List<String> value = values.get(key);
            if (value.size() != 1) {
                return String.join(",", value);
            }
            return value.get(0);
        }
        return null;
    }

    /**
     * Get the list of values for a key
     * @param key Named key for option value
     * @return List of values for {@code key}
     */
    public List<String> getStrings(String key) {
        return values.get(key);
    }

    /**
     * Get an option cast to an integer
     * @param key Named key for option value
     * @param failure Integer value to return if {@code key} was not set
     * @return Integer value for {@code key}, or {@code failure} if there was none set
     *
     * @throws InvalidArgumentException when {@code key} has been set to a list of size > 1
     * @throws NumberFormatException when the value for {@code key} cannot be cast to an integer
     */
    public int getInt(String key, int failure) {
        if (values.containsKey(key)) {
            List<String> value = values.get(key);
            if (value.size() != 1) {
                throw new InvalidArgumentException("Cannot cast list to int");
            }
            return Integer.parseInt(value.get(0));
        }
        return failure;
    }

    /**
     *
     * @param key Named key for option value
     * @return {@code true} if a value was set for {@code key}, otherwise {@code false}
     */
    public boolean wasSet(String key) {
        return values.containsKey(key);
    }

    /**
     * Clear all stored arguments stored during parsing. Does not clear added arguments
     * @return this for convenience
     */
    public CliParser clear() {
        values.clear();
        endArgs.clear();
        return this;
    }

    /**
     *
     * @return The arguments encountered after finished parsing options
     */
    public List<String> getArguments() {
        return endArgs;
    }

}
