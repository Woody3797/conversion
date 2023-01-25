package spsconvert;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    
    public static void main(String[] args) throws IOException {
        
        Socket socket = new Socket("localhost", 1234);
        Console con = System.console();

        try {
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);

            String readInput = "";

            while (!readInput.contains("quit")) {
                readInput = con.readLine("enter 1 for scissors, 2 for paper, 3 for stone or quit to exit\n");
                dos.writeUTF(readInput);
                dos.flush();

                String response = dis.readUTF();
                System.out.println(response);
            }
            dis.close();
            bis.close();
            is.close();
            dos.close();
            bos.close();
            os.close();   
        } catch (Exception e) {
            socket.close();
        }
    }
}
