
package com.github.zyxgad.qqchat.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.github.zyxgad.qqchat.websocket.TcpSocket;
import com.github.zyxgad.qqchat.util.ColorTextBuilder;

public final class ChatListener implements Listener{

	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerChat(AsyncPlayerChatEvent event){
		Player player = event.getPlayer();
		event.setFormat(new ColorTextBuilder()
			.add('[').yellow("WORLD").add('/').green(player.getName()).line("]:")
			.add(" %2$s")
			.toString());

		if(!event.isCancelled()){
			TcpSocket.INSTANCE.onPlayerChat(player, event.getMessage());
		}
	}
}