package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {


        try {

            ServerSocket serverSocket = new ServerSocket(61616);

            while (true) {

                Socket clientSocket = serverSocket.accept();

                new Thread(
                        new Runnable() {

                            public void run() {
                                try {
                                    OutputStream outputStream = clientSocket.getOutputStream();
                                    PrintWriter printWriter = new PrintWriter(outputStream);
                                    InputStream inputStream = clientSocket.getInputStream();
                                    InputStreamReader catalogStreamReader = new InputStreamReader
                                            (new FileInputStream("C:\\Users\\Rand Botani\\IdeaProjects\\AddressBookDemoServer\\Central katalog.csv"));
                                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                                    BufferedReader reader = new BufferedReader(catalogStreamReader);
                                    String catalog = "";
                                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                                        catalog += line.replace(",", " ") + "\n";
                                    }
                                    BufferedReader requestReader = new BufferedReader(inputStreamReader);

                                    String request;
                                    do {
                                        request = requestReader.readLine();
                                        if (request.equals("getall")) {
                                            printWriter.print(catalog);
                                            printWriter.flush();
                                        }
                                    } while (!request.equals("exit"));

                                    printWriter.close();
                                    requestReader.close();
                                    clientSocket.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                ).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}