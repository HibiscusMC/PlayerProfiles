package me.aglerr.playerprofiles.configs;

import me.aglerr.lazylibs.libs.CustomConfig;

public class ConfigManager {

    public static CustomConfig CONFIG;
    public static CustomConfig GUI;

    public static void initialize(){
        CONFIG = new CustomConfig("config.yml", null);
        GUI = new CustomConfig("gui.yml", null);
    }

}