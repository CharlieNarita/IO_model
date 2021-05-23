package bio;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {
		Socket s = new Socket("localhost", 8888);
		Socket s2 = new Socket("localhost", 8888);
		
		OutputStreamWriter osw = new OutputStreamWriter(s.getOutputStream());
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write("hi, i am client Charlie");
		bw.newLine();
		bw.flush();
		bw.close();
		
		OutputStreamWriter osw2 = new OutputStreamWriter(s2.getOutputStream());
		BufferedWriter bw2 = new BufferedWriter(osw2);
		bw2.write("hello, i am client NPC");
		bw2.newLine();
		bw2.flush();
		bw2.close();
	
		s.close();
		s2.close();
	}
}
