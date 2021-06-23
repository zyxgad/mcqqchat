
package com.github.zyxgad.qqchat.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

import org.bukkit.OfflinePlayer;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import com.github.zyxgad.qqchat.QQChat;
import com.github.zyxgad.qqchat.util.ErrorStackGetter;

public final class UserConfig{
	private static final UserConfig INSTANCE = new UserConfig();
	public static UserConfig getInstance(){
		return INSTANCE;
	}

	static final class Item{
		OfflinePlayer player;
		long qqid;

		Item(OfflinePlayer player, long qqid){
			this.player = player;
			this.qqid = qqid;
		}
		Item(JsonReader jreader) throws IOException{
			this.fromJson(jreader);
		}

		public void fromJson(JsonReader jreader) throws IOException{
			jreader.nextName();
			jreader.beginObject();
			while(jreader.hasNext()){
				final String name = jreader.nextName();
				if(name.equals("qq")){
					this.qqid = jreader.nextLong();
				}else if(name.equals("uuid")){
					this.player = QQChat.INSTANCE.getServer().getOfflinePlayer(UUID.fromString(jreader.nextString()));
				}else{
					jreader.skipValue();
				}
			}
			jreader.endObject();
		}

		public void toJson(JsonWriter jwriter) throws IOException{
			jwriter.name(String.valueOf(qqid));
			jwriter.beginObject();
			jwriter.name("qq");
			jwriter.value(qqid);
			jwriter.name("uuid");
			jwriter.value(player.getUniqueId().toString());
			jwriter.endObject();
		}
	}

	private Map<Long, Item> storage;

	private UserConfig(){
		this.storage = new HashMap<>();
	}

	public void setPlayerQQ(OfflinePlayer player, long qqid){
		this.storage.put(Long.valueOf(qqid), new Item(player, qqid));
	}

	public OfflinePlayer getQQPlayer(long qqid){
		Item item = this.storage.get(Long.valueOf(qqid));
		if(item == null){
			return null;
		}
		return item.player;
	}

	public void reload(){
		final File file = new File(QQChat.INSTANCE.getDataFolder(), "userconfig.json");
		this.storage.clear();
		if(!file.exists()){
			return;
		}
		try(
			FileReader filer = new FileReader(file);
			JsonReader jreader = new JsonReader(filer)
		){
			Item item;
			jreader.beginObject();
			while(jreader.hasNext()){
				item = new Item(jreader);
				this.storage.put(Long.valueOf(item.qqid), item);
			}
			jreader.endObject();
		}catch(IOException e){
			QQChat.LOGGER.severe("Read userconfig json error:\n" + ErrorStackGetter.getErrorStack(e));
		}
	}

	public void save(){
		final File file = new File(QQChat.INSTANCE.getDataFolder(), "userconfig.json");
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				QQChat.LOGGER.severe("Create userconfig.json error:\n" + ErrorStackGetter.getErrorStack(e));
				return;
			}
		}

		try(
			FileWriter filew = new FileWriter(file);
			JsonWriter jwriter = new JsonWriter(filew)
		){
			jwriter.beginObject();
			for(Item item: this.storage.values()){
				item.toJson(jwriter);
			}
			jwriter.endObject();
		}catch(IOException e){
			QQChat.LOGGER.severe("Write userconfig json error:\n" + ErrorStackGetter.getErrorStack(e));
		}
	}

}