
package com.github.zyxgad.qqchat;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import com.github.zyxgad.qqchat.config.UserConfig;
import com.github.zyxgad.qqchat.event.ChatListener;
import com.github.zyxgad.qqchat.websocket.TcpSocket;
import com.github.zyxgad.qqchat.util.ColorTextBuilder;

public final class QQChat extends JavaPlugin{
	public static QQChat INSTANCE = null;
	public static Logger LOGGER = null;

	public static final String PLUGIN_NAME = "QQChat";

	public QQChat(){}

	@Override
	public void onLoad(){
		if(QQChat.INSTANCE != this){
			QQChat.INSTANCE = this;
			QQChat.LOGGER = this.getLogger();
		}
		LOGGER.info("QQChat client is on load");
	}

	@Override
	public void onEnable(){
		if(QQChat.INSTANCE != this){
			QQChat.INSTANCE = this;
			QQChat.LOGGER = this.getLogger();
		}
		LOGGER.info("QQChat client is on enable");

		this.addListener(Bukkit.getPluginManager());
		this.bindCommands();
		UserConfig.getInstance().reload();
		try{
			TcpSocket.INSTANCE.bind(30203);
		}catch(IOException e){
			throw new AssertionError(e);
		}
	}

	@Override
	public void onDisable(){
		LOGGER.info("QQChat client is on disable");
		TcpSocket.INSTANCE.close();
		UserConfig.getInstance().save();

		QQChat.INSTANCE = null;
		QQChat.LOGGER = null;
	}

	private void addListener(PluginManager manager){
		manager.registerEvents(new ChatListener(), this);
	}

	private void bindCommands(){
		// this.getCommand("qqchat").setExecutor(new QQChatCommand());
		// this.getCommand("qqbind").setExecutor(new QQBindCommand());
	}

	public void sendMessage(OfflinePlayer player, String msg){
		final String message = new ColorTextBuilder()
			.add('[').yellow("WORLD").add('/').green(player.getName()).line("]:")
			.add(' ').add(msg)
			.toString();
		for(Player p: Bukkit.getOnlinePlayers()){
			p.sendRawMessage(message);
		}
	}
}
