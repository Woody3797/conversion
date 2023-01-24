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

            while (!(line.contains("quit"))) {
                line = dis.readUTF();
                int computerChoice = sps.generateComputerChoice();
                dos.writeUTF("enter 1 for scissors, 2 for paper, 3 for stone");
                dos.flush();
                int playerChoice = Integer.parseInt(line);

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
