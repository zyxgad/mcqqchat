
package com.github.zyxgad.qqchat.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import com.github.zyxgad.qqchat.config.UserConfig;

public final class BindQQCommand implements CommandExecutor{
	public BindQQCommand(){}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!(sender instanceof Player)){
			return false;
		}
		Player player = (Player)sender;
		if(args.length != 1){
			return false;
		}
		long qqid = Long.parseLong(args[0]);
		UserConfig.getInstance().setPlayerQQ(player, qqid);
		sender.sendMessage("QQ设置成功");
		return true;
	}
}