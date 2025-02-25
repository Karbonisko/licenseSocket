package dev.karbon;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        if (Main.class.getClassLoader().getResourceAsStream("config.yaml") == null) {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("default-config.yaml");
            Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
            String defaultConfigText = scanner.hasNext() ? scanner.next() : "";
            File configFile = new File("config.yaml");
            if (!configFile.exists()) {
                FileOutputStream outputStream = new FileOutputStream(configFile);
                outputStream.write(defaultConfigText.getBytes());
                outputStream.close();
                System.out.flush();
                System.out.println("New config file created. Please fill it with your data and restart the bot.");
                System.exit(0);
            }
            LicenseBase.init(configFile.getAbsolutePath());
        } else {
            LicenseBase.init();
        }
        int port = 20035;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serwer uruchomiony na porcie: " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("polaczono z klientem: " + clientSocket.getInetAddress());

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = in.readLine();
                System.out.println("Odebrano wiadomosc: " + message);

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                if(LicenseBase.getLicenses().contains(message)) {
                    out.println("true");
                }else {
                    out.println("false");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
