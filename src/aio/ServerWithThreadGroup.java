package aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerWithThreadGroup {
	public static void main(String[] args) throws Exception {
		
		ExecutorService executorService = Executors.newCachedThreadPool();
		AsynchronousChannelGroup threadGroup = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);
		
		final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open(threadGroup).bind(new InetSocketAddress(6888));
		
		serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {

			@Override
			public void completed(AsynchronousSocketChannel result, Object attachment) {
				// TODO Auto-generated method stub
				serverChannel.accept(null, this);
				
				try {
					System.out.println(client.getRemoteAddress());
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
						public void completed(Integer result, ByteBuffer attachment) {
							attachment.flip();
							System.out.println(new String(attachment.array(), 0, result));
							client.write(ByteBuffer.wrap("HelloClient".getBytes()));
						}
						
						
						public void failed(Throwable exc, Object attachment) {
							exc.printStackTrace();
						}
					});
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				// TODO Auto-generated method stub
				exc.printStackTrace();
			}
			
		});
		
		while(true) {
			Thread.sleep(1000);
		}
	}
}
