
package com.github.zyxgad.qqchat.websocket;

import java.util.UUID;
import java.util.Set;

import org.bukkit.Server;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import com.github.zyxgad.qqchat.QQChat;
import com.github.zyxgad.qqchat.util.ColorTextBuilder;

public final class WebCommandSender implements CommandSender{
	private static final String NO_OPER_ERROR_MSG = "[错误]您没有权限执行该指令";
	private static final String NO_COMMAND_MSG = "[错误]没有找到该指令";

	private final Server server;
	private final OfflinePlayer player;
	private final CommandSender sender;
	private ColorTextBuilder buffer;
	public WebCommandSender(OfflinePlayer player){
		this.server = QQChat.INSTANCE.getServer();
		this.player = player;
		this.sender = this.server.getConsoleSender();
	}

	public String runCommand(String command){
		if(!this.player.isOp()){
			return NO_OPER_ERROR_MSG;
		}
		this.buffer = new ColorTextBuilder();
		try{
			if(!this.server.dispatchCommand(this, command)){
				return NO_COMMAND_MSG;
			}
		}catch(CommandException e){
			return new ColorTextBuilder().add("[Error]").add(e.getMessage()).toWhiteString();
		}
		final String msg = this.buffer.toWhiteString();
		this.buffer = null;
		return msg;
	}

	@Override
	public void sendMessage(String message){
		this.buffer.line(message);
	}

	@Override
	public void sendMessage(String[] messages){
		for(String msg: messages){
			this.buffer.line(msg);
		}
	}

	// @Override
	// public void sendMessage(UUID sender, String message){
	// 	this.buffer.append(message).append('\n');
	// }

	// @Override
	// public void sendMessage(UUID sender, String[] messages){
	// 	for(String msg: messages){
	// 		this.buffer.append(msg).append('\n');
	// 	}
	// }

	@Override
	public CommandSender.Spigot spigot(){
		return this.sender.spigot();
	}

	@Override
	public Server getServer(){
		return this.server;
	}

	@Override
	public String getName(){
		return this.player.getName();
	}

	@Override
	public boolean isPermissionSet(String name){
		return this.sender.isPermissionSet(name);
	}

	@Override
	public boolean isPermissionSet(Permission perm){
		return this.sender.isPermissionSet(perm);
	}

	@Override
	public boolean hasPermission(String name){
		return this.sender.hasPermission(name);
	}

	@Override
	public boolean hasPermission(Permission perm){
		return this.sender.hasPermission(perm);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value){
		return this.sender.addAttachment(plugin, name, value);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin){
		return this.sender.addAttachment(plugin);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks){
		return this.sender.addAttachment(plugin, name, value, ticks);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks){
		return this.sender.addAttachment(plugin, ticks);
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment){
		this.sender.removeAttachment(attachment);
	}

	@Override
	public void recalculatePermissions(){
		this.sender.recalculatePermissions();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions(){
		return this.sender.getEffectivePermissions();
	}

	@Override
	public boolean isOp(){
		return this.player.isOp();
	}

	@Override
	public void setOp(boolean value){
		this.player.setOp(value);
	}
}