package io.github.mrixs.blockplacinglimiter;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Mrixs on 31.Jan.2015 20:30
 * Project: BlockPlacingLimiter
 * Package: io.github.mrixs.blockplacinglimiter
 */

public final class BlockPlacingLimiter extends JavaPlugin {

    private Logger log = getLogger();
    private List blockList = null;
    private List blockNum = null;

    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        if (!config.isBoolean("enable")) {                                                                              // If configfile not contains enable field, create it
            config.set("enable", true);
        }
        boolean enabled = config.getBoolean("enable");
        blockList=config.getIntegerList("blocks.blocks");
        blockNum=config.getIntegerList("blocks.number");
        if ((blockList == null) || (blockNum == null) || !enabled) {
            log.info("BPL Plugin has been disabled in config file or not contains block limits. Please check config file. ");
            this.getServer().getPluginManager().disablePlugin(this.getServer().getPluginManager().getPlugin("BlockPlacingLimiter")); // Unloads plugin if it's not configured
        }
        OnBlockPlace placeEvent = new OnBlockPlace(this);
        OnBlockBreak breakEvent = new OnBlockBreak(this);
        placeEvent.blockList = blockList;
        placeEvent.blockNum = blockNum;
        breakEvent.blockList = blockList;
        breakEvent.blockNum = blockNum;
        Commands command = new Commands(this);
        getCommand("bpl").setExecutor(command);
        command.blocklist = blockList;
        command.blocknum = blockNum;
        try {
            DB.Conn();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            DB.CreateDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onEnable();
    }

    public void onDisable(){
        FileConfiguration config = this.getConfig();
        config.set("blocks.blocks",blockList);
        config.set("blocks.number",blockNum);
        try {
            DB.CloseDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.saveConfig();
        System.out.println("BPL unloaded");
    }
}

