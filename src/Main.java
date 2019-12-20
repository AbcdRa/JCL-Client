import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {
        private static Socket socket;
        private static Scanner scan = new Scanner(System.in);
        private static String name;
        private static BufferedWriter outBW;
        private static String ip = "localhost";
        private static Process process = null;

        //Инициализируем сокет
        private static boolean initSocket() {
            while(true) {
                int port = 1234;
                try {
                    socket = new Socket(ip, port);
                    return true;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    System.out.println("IP: ");
                    ip = scan.nextLine();
                    if(ip.compareToIgnoreCase("0") == 0) return false;
                }

            }

        }


        public static void main(String[] args) {
            if(!initSocket()) {
                scan.close();
                System.out.println("Не удалось найти доступный сервер");
                return;
            }
            try {
                OutputStream out = socket.getOutputStream();
                OutputStreamWriter outW = new OutputStreamWriter(out);
                outBW = new BufferedWriter(outW);
            } catch (IOException e1) {
                System.out.println("Не удалось загенерить выходной поток");
                e1.printStackTrace();
            }
            System.out.println("Вроде подключились! ");
            new Updater(socket);
            String word;
            while(true) {
                try {
                    //Ждем пока клиент что-нибудь напишет
                    word = scan.nextLine();
                    if(word.compareToIgnoreCase("exit") == 0) break;
                    outBW.write(word+"\n");
                    outBW.flush();
                } catch (IOException e) {
                    System.out.println("Вас отключили от сервера");
                    break;
                }
            }
            System.out.println("Завершение сеанса");
            System.exit(0);
        }




}

