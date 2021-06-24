
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

		if(!event.isCancelled()){
			TcpSocket.INSTANCE.onPlayerChat(player, 
				new ColorTextBuilder(String.format(event.getFormat(), event.getMessage())).toWhiteString());
		}
	}
}