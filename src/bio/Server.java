package bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private static int cnt = 0;
	private static boolean start = true;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket ss = new ServerSocket();
		ss.bind(new InetSocketAddress("localhost", 8888));
		
		while(start) {
			Socket s = ss.accept();
			cnt++;
			System.out.println("No."+ cnt + " connecting successful!");
			
			
			//create a new thread for undertaking each client
			new Thread(()->{
				BufferedReader br = null;
				try {
					InputStreamReader isr = new InputStreamReader(s.getInputStream());
					br = new BufferedReader(isr);
					
					String str = br.readLine();
					System.out.println(str);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if(br != null) {
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

		
		ss.close();
	}
}
 