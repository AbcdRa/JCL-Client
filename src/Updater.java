import java.io.*;
import java.net.Socket;

public class Updater extends Thread {
    private Socket socket;
    private BufferedReader inBR;

    Updater(Socket socket) {
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            InputStream in = socket.getInputStream();
            InputStreamReader inR = new InputStreamReader(in);
            inBR = new BufferedReader(inR);
        } catch (IOException e1) {
            System.out.println("Не удалось создать входной поток");
            return;
        }
        while(true) {
            try {
                System.out.println(inBR.readLine());
            } catch ( IOException e) {
                System.out.println("Не удалось получить сообщение с сервера");
                break;
            }
        }
    }

}
