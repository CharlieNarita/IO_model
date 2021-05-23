package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;



public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.socket().bind(new InetSocketAddress("127.0.0.1", 5888));
		ssc.configureBlocking(false);
		
		System.out.println("server started, listening on :" + ssc.getLocalAddress());
		
		//create a selector for undertaking clients and events
		Selector selector = Selector.open();
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		
		while(true) {
			selector.select();
			Set<SelectionKey> keys = selector.selectedKeys();
			Iterator<SelectionKey> it = keys.iterator();
			while(it.hasNext()) {
				SelectionKey key = it.next();
				handle(key);
			}	
		}
		
	}
	
	private static void handle(SelectionKey key) throws Exception{
		if(key.isAcceptable()) {
			ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
			SocketChannel sc = ssc.accept();
			sc.configureBlocking(false);
			
			//new Client
			//String hostIP = (InetSocketAddress)sc.getRemoteAddress()).getHostString();
			/*
			log.info("client " + hostIP + " trying to connect");
			for(int i=0; i<clients.size(); i++) {
				String clientHostIP = clients.get(i).clientAddress.getHostString();
				if(hostIP.equals(clientHostIP)) {
					log.info("this client has already connected! is he alive" + clients.get(i).live);
					sc.close();
					return;
				}
			}
			*/
			
			sc.register(key.selector(), SelectionKey.OP_READ);
			
		} else if (key.isReadable()) {
			SocketChannel sc = null;
			
			sc = (SocketChannel)key.channel();
			
			ByteBuffer buffer = ByteBuffer.allocate(512);
			buffer.clear();
			int len = sc.read(buffer);
			
			if(len != -1) {
				System.out.println(new String(buffer.array(), 0, len));
			}
			
			ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
			sc.write(bufferToWrite);
			
			sc.close();
		}
	}
}
