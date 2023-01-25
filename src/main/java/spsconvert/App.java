package spsconvert;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(1234);
        Socket socket = ss.accept();
        SPS sps = new SPS();
        String winner = "";
        String line = "";

        try {
            OutputStream os = socket.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            InputStream is = socket.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            DataInputStream dis = new DataInputStream(bis);
            int playerChoice = 0;

            while (!(line.contains("quit"))) {
                line = dis.readUTF();
                if (line.equalsIgnoreCase("quit")) {
                    System.exit(0);
                }
                int computerChoice = sps.generateComputerChoice();
                try {
                    playerChoice = Integer.parseInt(line);
                } catch (Exception e) {
                    dos.writeUTF("invalid input, integers only!");
                    dos.flush();
                    continue;
                }

                if (!(playerChoice > 0 && playerChoice < 4)) {
                    dos.writeUTF("invalid input, enter number 1 or 2 or 3 ");
                    dos.flush();
                    continue;
                } else {
                    winner = sps.checkWinner(playerChoice, computerChoice);
                    if (winner.contains("draw")) {
                        dos.writeUTF(winner);
                        dos.flush();
                    } else {
                        dos.writeUTF(winner + " wins!");
                        dos.flush();
                    }
                }
            }
            dis.close();
            bis.close();
            is.close();
            dos.close();
            bos.close();
            os.close();
            ss.close();
        } catch (IOException e) {
            socket.close();
        }
    }
}
