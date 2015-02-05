package io.github.mrixs.blockplacinglimiter;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import java.sql.SQLException;
import java.util.List;
import static io.github.mrixs.blockplacinglimiter.BlockChecker.getBlockLimit;


/**
 * Created by Mrixs on 05.Feb.2015 21:21
 * Project: BlockPlacingLimiter
 * Package: io.github.mrixs.blockplacinglimiter
 */
class OnBlockBreak implements Listener{
    public List blockList = null;
    public List blockNum = null;

    public OnBlockBreak(BlockPlacingLimiter plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){

        int placedBlockLimit;
        Block block = event.getBlock();
        Player player = event.getPlayer();
        placedBlockLimit = getBlockLimit(block.getType(), blockList, blockNum);
        if (placedBlockLimit != 0){

          DB database = new DB();

            try {
                database.Conn();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                database.CreateDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                database.RemoveBlock(block.getX(), block.getY(), block.getZ(), block.getWorld().getUID().hashCode());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                database.CloseDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            player.sendMessage("You removed block \"" + block.getType().toString().toLowerCase().replace("_", " ") + "\", that is limited. Limit is " + placedBlockLimit + "." );

        }
    }
}
