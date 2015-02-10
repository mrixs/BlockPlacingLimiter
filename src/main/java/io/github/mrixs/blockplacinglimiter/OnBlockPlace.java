package io.github.mrixs.blockplacinglimiter;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import java.sql.SQLException;
import java.util.List;
import static io.github.mrixs.blockplacinglimiter.BlockChecker.getBlockLimit;

/**
 * Created by Mrixs on 05.Feb.2015 21:20
 * Project: BlockPlacingLimiter
 * Package: io.github.mrixs.blockplacinglimiter
 */
class OnBlockPlace implements Listener{

    public List blockList = null;
    public List blockNum = null;

    public OnBlockPlace(BlockPlacingLimiter plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {

        int placedBlockLimit;
        int placedLimitedBlock = 0;
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();
        placedBlockLimit = getBlockLimit(block.getType(), blockList, blockNum);
        if (placedBlockLimit>0) {
            DB database = new DB();
            try {
                placedLimitedBlock = database.CountPlaced(block.getType().getId(),player.getName());                    //TODO: Used deprecated method of getting BlockID. Change it ASAP. List of deprecated: http://jd.bukkit.org/rb/doxygen/da/d58/deprecated.html
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (placedBlockLimit <= placedLimitedBlock & placedBlockLimit > 0) {                                        //If player can't place block
                event.setCancelled(true);
                player.sendMessage("You cannot place block \"" + block.getType().toString().toLowerCase().replace("_", " ") + "\". Limit is reached (" + placedLimitedBlock + "/" + placedBlockLimit + ")."); // Writes to player that he cannot place that block + name of block
            }
            if (placedBlockLimit > placedLimitedBlock) {                                                                //If player can place block
                try {
                    database.AddBlock(block.getX(), block.getY(), block.getZ(), block.getWorld().getUID().hashCode(), block.getType().getId(), player.getName());     //TODO: Used deprecated method of getting BlockID. Change it ASAP. List of deprecated: http://jd.bukkit.org/rb/doxygen/da/d58/deprecated.html
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                player.sendMessage("You placed block \"" + block.getType().toString().toLowerCase().replace("_", " ") + "\", that is limited. Limit is " + placedBlockLimit + ". And you already placed " + (placedLimitedBlock+1)); // Writes to player that he placed tha block + name of block
            }
        }

    }
}
