package me.magnum.petmanager.util;

import me.vagdedes.mysql.basic.Config;

public class Settings extends SimpleConfig {

    private Settings(String filename) {
        super(filename);
        setHeader(new String[]{
                "+---------------------------------------------------------+",
                "| This file has been updated automatically by the plugin. |",
                "|                                                         |",
                "| Due to Bukkit limitations comments have been removed.   |",
                "+---------------------------------------------------------+",
                "  Please open " + filename + " to see default values and comments."});
    }

    private void onLoad() {
        Config.setHost(getString("sql.host"));
        Config.setPort(getString("sql.port"));
        Config.setUser(getString("sql.user"));
        Config.setPassword(getString("sql.password"));
        Config.setDatabase(getString("sql.database" + "?useSSL=false"));

    }

    public static void init() {
        new Settings("config.yml").onLoad();
    }

}
