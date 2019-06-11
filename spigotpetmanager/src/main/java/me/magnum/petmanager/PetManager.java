package me.magnum.petmanager;

import co.aikar.commands.BukkitCommandManager;
import me.kangarko.ui.UIDesignerAPI;
import me.magnum.lib.Common;
import me.magnum.petmanager.commands.MenuCommand;
import me.magnum.petmanager.listeners.ClickPet;
import me.magnum.petmanager.util.Settings;
import me.magnum.petmanager.util.SimpleConfig;
import me.vagdedes.mysql.database.MySQL;
import me.vagdedes.mysql.database.SQL;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;


public class PetManager extends JavaPlugin {

    private static PetManager instance;
    public SimpleConfig cfg;
    //    public String pre;
    public static String pre;// = "&9[&6PetManager&9] ";
    //    public final Permission<ClickPet> clickPetPermission;
    public UUID ownerid, petid;
    public Permission perm;
    private static BukkitCommandManager CM;
private static PetManager plugin;

    @Override
    public void onEnable() {
        plugin = this;
        MySQL.connect();
        if (!(SQL.tableExists("petmanager"))) {
            SQL.createTable("petmanager", "UUID uuid IGN varchar(16) x int y int z int");
        }
        instance = this;
        cfg = new SimpleConfig("config.yml");
        Settings.init();
        registerCommand();
        pre = (String) cfg.get("pets.prefix");
        UIDesignerAPI.setPlugin(this);
        Common.setInstance(this);
//        this.getCommand("ui").setExecutor(new UIMenu());
//        this.getCommand("menu").setExecutor(new MenuCommand());
//        getServer().getPluginManager().registerEvents(new MenuBuilder(), this);
//        Common.registerCommand(new MenuCommand());
        getServer().getPluginManager().registerEvents(new ClickPet(), this);
        getLogger().info(pre + getDescription().getName() + " " + getDescription().getVersion() + " enabled");
    }

    private void registerCommand(){
        CM = new BukkitCommandManager(plugin);
        CM.registerCommand(new MenuCommand());



    }

    @Override
    public void onDisable() {
        getLogger().info("PetManager unloaded");
    }

    public static PetManager getInstance() {
        return instance;
    }
}
