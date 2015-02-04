package io.github.mrixs.blockplacinglimiter;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

import java.sql.Connection;
import java.util.List;


/**
 * Created by Mrixs on 31.Jan.2015 18:50
 * Project: BlockPlacingLimiter
 * Package: io.github.mrixs.blockplacinglimiter
 */
public class BlockChecker implements Listener {

    public static int getBlockLimit(Material blocktype, List blocklist, List blocklimitnum) {
        int i;
        int limit = 0;
        for (i = 1; i == blocklist.size(); i++) {
            if (Integer.parseInt(blocklist.get(i).toString()) == blocktype.getId()) {                                          //TODO: Used deprecated method of getting BlockID. Change it ASAP
                limit = Integer.parseInt(blocklimitnum.get(i).toString());
                break;
            }
        }
        return limit;
    }

    public static int getPlacedBlocks(Block block, Connection dbConnection) {
        int placed = 0;


        return placed;
    }
}





