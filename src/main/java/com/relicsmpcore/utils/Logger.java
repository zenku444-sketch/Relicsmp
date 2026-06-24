package com.relicsmpcore.utils;

import com.relicsmpcore.RelicSMPCore;

public class Logger {

    private static final RelicSMPCore plugin = RelicSMPCore.getInstance();

    public static void info(String message) {
        plugin.getLogger().info(message);
    }

    public static void warning(String message) {
        plugin.getLogger().warning(message);
    }

    public static void severe(String message) {
        plugin.getLogger().severe(message);
    }

    public static void debug(String message) {
        if (plugin.getConfigManager().isDebugMode()) {
            plugin.getLogger().info("[DEBUG] " + message);
        }
    }
}
