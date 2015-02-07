package io.github.mrixs.blockplacinglimiter;

import org.bukkit.Material;
import java.util.List;

/**
 * Created by Mrixs on 31.Jan.2015 18:50
 * Project: BlockPlacingLimiter
 * Package: io.github.mrixs.blockplacinglimiter
 */

class BlockChecker{

    public static int getBlockLimit(Material blocktype, List blocklist, List blocklimitnum) {
        int i =0;
        int limit = 0;
        boolean exit = false;

        while (i < blocklist.size() && !exit) {
            if (Integer.parseInt(blocklist.get(i).toString()) == blocktype.getId()) {                                   //TODO: Used deprecated method of getting BlockID. Change it ASAP. List of deprecated: http://jd.bukkit.org/rb/doxygen/da/d58/deprecated.html
                limit = Integer.parseInt(blocklimitnum.get(i).toString());
                exit=true;
            }
            i++;
        }
        return limit;
    }


}





