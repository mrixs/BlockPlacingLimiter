package io.github.mrixs.blockplacinglimiter;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

import static io.github.mrixs.blockplacinglimiter.BlockChecker.getBlockLimit;

/**
 * Created by Mrixs on 31.Jan.2015 20:30
 * Project: BlockPlacingLimiter
 * Package: io.github.mrixs.blockplacinglimiter
 */
public final class BlockPlacingLimiter extends JavaPlugin {

    public Logger log = getLogger();
    public List blockList = null;
    public List blockNum = null;
    private boolean enabled;

    @Override
    public void onEnable() {

        FileConfiguration config = this.getConfig();

        if (!config.isBoolean("enable")) {  // If configfile not contains enable field, create it
            config.set("enable", true);
        }

        this.enabled = config.getBoolean("enabled");

        this.blockList = config.getStringList("blocks.blocks");                             //      It is not worked
        this.blockNum = config.getStringList("blocks.number");                              //      so plugin unloads itself
        System.out.println(blockList);                                                      //      debug
        System.out.println(blockNum);                                                       //      debug

        if ((blockList == null) || (blockNum == null) || !enabled) {

            log.info("BPL Plugin has been disabled in config file or misconfigured. Please check config file. ");
            this.getServer().getPluginManager().disablePlugin(this.getServer().getPluginManager().getPlugin("BlockPlacingLimiter")); // Unloads plugin if it's misconfigured
        }
        super.onEnable();
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        int placedBlockLimit = 0;
        int placedLimitedBlock = 3;
        Boolean limited = false;
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        placedBlockLimit = getBlockLimit(block.getType(), blockList, blockNum);

/*

        Here the code getting actually palced blocks by player

 */
        if (placedBlockLimit <= placedLimitedBlock & placedBlockLimit != 0) {
            event.setCancelled(true);
            player.sendMessage("You cannot place block " + block.getType().toString().toLowerCase().replace("_", " ") + "Limit is reached (" + placedLimitedBlock + "/" + placedBlockLimit + ")."); // Writes to player that he cannot place that block + name of block
        }
        if (placedBlockLimit > placedLimitedBlock) {
            player.sendMessage("You placed block " + block.getType().toString().toLowerCase().replace("_", " ") + ", that is limited. Limit is " + placedBlockLimit + ". And you already placed " + placedLimitedBlock); // Writes to player that he cannot place that block + name of block
        }

    }
}

