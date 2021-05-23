package aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class Server {
	public static void main(String[] args) throws Exception{
		
		final AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(6888));
		
		serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object> () {
			@Override
			public void completed(AsynchronousSocketChannel result, Object attachment) {
				serverChannel.accept(null, this);
				try {
					System.out.println(client.getRemoteAddress());
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
						@Override
						public void completed(Integer result, ByteBuffer attachement) {
							attachement.flip();
							System.out.println(new String(attachment.array(), 0, result()));
							client.write(ByteBuffer.wrap("HelloClient".getBytes()));
						}

						@Override
						public void failed(Throwable exc, ByteBuffer attachment) {
							// TODO Auto-generated method stub
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
