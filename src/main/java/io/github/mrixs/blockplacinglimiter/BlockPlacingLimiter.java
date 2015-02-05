package io.github.mrixs.blockplacinglimiter;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
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
        if (!config.isBoolean("enable")) {  // If configfile not contains enable field, create it
            config.set("enable", true);
        }
        boolean enabled = config.getBoolean("enable");
        blockList=config.getIntegerList("blocks.blocks");
        blockNum=config.getIntegerList("blocks.number");
        if ((blockList == null) || (blockNum == null) || !enabled) {
            log.info("BPL Plugin has been disabled in config file or misconfigured. Please check config file. ");
            this.getServer().getPluginManager().disablePlugin(this.getServer().getPluginManager().getPlugin("BlockPlacingLimiter")); // Unloads plugin if it's misconfigured
        }
        OnBlockPlace placeEvent = new OnBlockPlace(this);
        OnBlockBreak breakEvent = new OnBlockBreak(this);
        placeEvent.blockList = blockList;
        placeEvent.blockNum = blockNum;
        breakEvent.blockList = blockList;
        breakEvent.blockNum = blockNum;
        super.onEnable();
    }

    public void onDisable(){
        FileConfiguration config = this.getConfig();
        config.set("blocks.blocks",blockList);
        config.set("blocks.number",blockNum);
        this.saveConfig();
        System.out.println("BPL unloaded");
    }
}

