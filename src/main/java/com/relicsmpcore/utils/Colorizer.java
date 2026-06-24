package com.relicsmpcore.utils;

public class Colorizer {

    public static String colorize(String text) {
        return text.replace("&", "§");
    }

    public static String removeColors(String text) {
        return text.replaceAll("§[0-9a-fk-or]", "");
    }

    public static String stripAllFormats(String text) {
        return removeColors(text).replaceAll("(?i)&[0-9a-fk-or]", "");
    }

    public static String bold(String text) {
        return "§l" + text + "§r";
    }

    public static String italic(String text) {
        return "§o" + text + "§r";
    }

    public static String underline(String text) {
        return "§n" + text + "§r";
    }

    public static String strikethrough(String text) {
        return "§m" + text + "§r";
    }

    public static String obfuscate(String text) {
        return "§k" + text + "§r";
    }

    public static String gold(String text) {
        return "§6" + text + "§r";
    }

    public static String green(String text) {
        return "§a" + text + "§r";
    }

    public static String red(String text) {
        return "§c" + text + "§r";
    }

    public static String blue(String text) {
        return "§9" + text + "§r";
    }

    public static String yellow(String text) {
        return "§e" + text + "§r";
    }

    public static String gray(String text) {
        return "§7" + text + "§r";
    }

    public static String white(String text) {
        return "§f" + text + "§r";
    }

    public static String darkRed(String text) {
        return "§4" + text + "§r";
    }

    public static String darkGreen(String text) {
        return "§2" + text + "§r";
    }

    public static String darkBlue(String text) {
        return "§1" + text + "§r";
    }

    public static String darkGray(String text) {
        return "§8" + text + "§r";
    }
}
