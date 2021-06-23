
package com.github.zyxgad.qqchat.websocket;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.SocketException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.bukkit.entity.Player;

import com.github.zyxgad.qqchat.QQChat;
import com.github.zyxgad.qqchat.util.ColorTextBuilder;

public final class TcpSocket{
	public static final TcpSocket INSTANCE = new TcpSocket();
	
	static final class TcpHelper{
		private BlockingQueue<Event> queue;
		private Socket socket;
		private HSender sender;
		private HRecver recver;

		TcpHelper(Socket socket) throws IOException{
			QQChat.LOGGER.info("new connect");
			this.socket = socket;
			this.queue = new LinkedBlockingQueue<>();
			this.sender = new HSender(this.socket.getOutputStream());
			this.recver = new HRecver(this.socket.getInputStream());
		}

		final class HSender extends Thread{
			private final OutputStream outstream;
			HSender(OutputStream outstream){
				super("qqchat-tcp-sender");
				this.outstream = outstream;
			}

			@Override
			public void run(){
				DataOutputStream dataout = new DataOutputStream(this.outstream);
				try{
					Event event;
					while(true){
						event = TcpHelper.this.queue.take();
						event.writeTo(dataout);
					}
				}catch(EOFException | SocketException | InterruptedException e){
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}

		final class HRecver extends Thread{
			private final InputStream instream;
			HRecver(InputStream instream){
				super("qqchat-tcp-recver");
				this.instream = instream;
			}

			@Override
			public void run(){
				DataInputStream datain = new DataInputStream(this.instream);
				Event event;
				while(!this.isInterrupted()){
					try{
						event = Event.recvMessage(datain);
						if(event != null){
							TcpHelper.this.send(event);
						}
					}catch(EOFException | SocketException e){
						break;
					}catch(IOException e){
						e.printStackTrace();
					}
				}
				
			}
		}

		public void send(Event event){
			this.queue.offer(event);
		}

		public void start(){
			this.sender.start();
			this.recver.start();
		}

		public void close(){
			this.sender.interrupt();
			this.recver.interrupt();
			try{
				this.socket.close();
			}catch(IOException e){
			}
		}
	}

	private Set<TcpHelper> helperSet;
	private Thread serverThread = null;
	private ServerSocket server = null;
	private TcpSocket(){
		this.helperSet = new HashSet<>();
	}

	public void bind(int port) throws IOException{
		QQChat.LOGGER.info("binding...");
		this.server = new ServerSocket(port);
		this.serverThread = new Thread(){
			@Override
			public void run(){
				while(!this.isInterrupted()){
					try{
						QQChat.LOGGER.info("accepting...");
						Socket socket = TcpSocket.this.server.accept();
						TcpHelper helper = new TcpHelper(socket);
						TcpSocket.this.helperSet.add(helper);
						helper.start();
					}catch(EOFException | SocketException e){
						break;
					}catch(IOException e){
						e.printStackTrace();
					}
				}
			}
		};
		this.serverThread.start();
	}

	public void send(Event event){
		this.helperSet.forEach((TcpHelper helper)->{
			helper.send(event);
		});
	}

	public void close(){
		if(this.serverThread != null){
			this.serverThread.interrupt();
			this.serverThread = null;
		}
		if(this.server != null){
			try{
				this.server.close();
				this.server = null;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		this.helperSet.forEach((TcpHelper helper)->{
			helper.close();
		});
		this.helperSet.clear();
	}

	public void onPlayerChat(Player player, String message){
		this.send(Event.newPlayerChatMessage(new ColorTextBuilder()
			.add('[').add(player.getName()).add("]: ").add(message)
			.toString()));
	}
}
