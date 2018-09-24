package cmen.tcp.server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import cmen.tcp.ChatThread;

public class TCPServer {
	public static final int DEFAULT_PORT = 9354;
	private int port;
	private ServerSocket ss = null;
	private Socket s = null;
	private InputStream input = null;
	private BufferedInputStream bif = null;
	private ChatThread ct = null;
	private Thread chatMain = null;
	
	public TCPServer() {
		this(DEFAULT_PORT);
	}
	
	public TCPServer(int port) {
		this.port = port;
	}
	
	public void connect() {
		int length = 0;
		byte[] data = new byte[2048];
		
		try {
			ss = new ServerSocket(port);
			System.out.println("[System]�z�n�A�@�����w���z�ǳƴN���F!");
			System.out.println("[System]���b��ť�Ȥ��......");
			s = ss.accept();
			s.getOutputStream().write("Hello World!!".getBytes("Utf-8"));
			System.out.println("[System]���\��ť��Ȥ�ݪ��s�u!");
			
			input = s.getInputStream();
			ct = new ChatThread(s);
			chatMain = new Thread(ct);
			chatMain.start();
			
			while(!ct.isStopped()) {
				if(!s.isClosed() && !ss.isClosed() && s.isConnected() && !s.isInputShutdown() && !s.isOutputShutdown()) {
					bif = new BufferedInputStream(input);
					while((length = bif.read(data)) > 0) {
						System.out.println("[Client]" + new String(data, 0, length, "Utf-8"));
					}
					System.out.println("[System]�����G�w�g���b�u�F�A���b�����Ҧ����q�T�{��...");
					this.stopAll();
					System.out.println("[System]�w�����Ҧ��s�u! bye bye~");
					System.exit(1);
				}else {
					chatMain.interrupt();
					break;
				}
			}
		}catch(IOException e) {
			if(!(e instanceof SocketException)) {
				e.printStackTrace();
			}
		} finally {
			try {
				System.out.println("[System]���b���������Ҧ��q�T�{��......");
				this.stopAll();
				System.out.println("[System]�w�����Ҧ��s�u! bye bye~");
				System.exit(1);
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopAll() throws IOException {
		ct.setStop();
		chatMain.interrupt();
		if(input != null)
			input.close();
		if(ss != null)
			ss.close();
		if(s != null) {
			s.close();
		}
	}
}
