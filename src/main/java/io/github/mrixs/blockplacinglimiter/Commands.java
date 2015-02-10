package io.github.mrixs.blockplacinglimiter;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Mrixs on 10.Feb.2015 19:27
 * Project: BlockPlacingLimiter
 * Package: io.github.mrixs.blockplacinglimiter
 */

public class Commands implements CommandExecutor {

    private BlockPlacingLimiter plugin;
    public List blocklist =null;
    public List blocknum = null;

    public Commands(BlockPlacingLimiter plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (command.getName().equalsIgnoreCase("bpl")){
            if(args.length==0){
                sender.sendMessage("Use \"/bpl count\" for counting blocks you placed.");
                sender.sendMessage("OPs also can use \"/bpl count <player>\" for counting blocks placed by <player>");
            }
            if ((player == null)&&args.length==1) {
                sender.sendMessage("This command can only be run by a player");
            } else {
                    // If command is /bpl count
                if (args.length>0) {
                    if (args[0].equalsIgnoreCase("count")) {
                        String playername = sender.getName();
                        if (sender.isOp() && args.length > 1) {
                            playername =args[1];
                        }
                        if (!sender.isOp() && args.length > 1) {
                            sender.sendMessage("You can only see how many blocks was placed by you");
                        }
                        int placed = 0 ;
                        for (int i = 0; i != blocklist.size(); i++) {
                            int limit = BlockChecker.getBlockLimit(Material.getMaterial(Integer.parseInt(blocklist.get(i).toString())), blocklist, blocknum);
                            try {
                                placed = DB.CountPlaced(Integer.parseInt(blocklist.get(i).toString()), playername);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            sender.sendMessage("BPL: Block \"" + Material.getMaterial(Integer.parseInt(blocklist.get(i).toString())).toString() + "\". Placed: " + placed + ". Limit: " + limit + ".");
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}