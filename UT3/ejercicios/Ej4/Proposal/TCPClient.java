package Procesos_Servicios.UT3.ejercicios.Ej4.Proposal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class TCPClient {

    public TCPClient(String ipServer, int port) {
        try {
            Socket client = createSocket(ipServer, port);
            Scanner keyboardInput = new Scanner(System.in);

            gameLoop(client, keyboardInput);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Usted a perdido.");
        }
    }

    private Socket createSocket(String ipServer, int port) throws IOException {
        return new Socket(InetAddress.getByName(ipServer), port);
    }

    private void gameLoop(Socket client, Scanner keyboardInput) throws IOException, ClassNotFoundException {
        int roundCount = 1;
        boolean hasWon = false;

        try (ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream())) {

            while (!hasWon) {
                int playerGuess = promptUserInput(keyboardInput, roundCount);

                sendMessage(outputStream, playerGuess);

                String serverResponse = receiveMessage(inputStream);
                System.out.println(serverResponse);

                if (guardIsWinningResponse(serverResponse)) {
                    hasWon = true;
                }

                roundCount++;
            }
        }
    }

    private int promptUserInput(Scanner keyboardInput, int roundCount) {
        System.out.print("Ronda " + roundCount + ": \n \t Indique el número que cree correcto:");
        int input = keyboardInput.nextInt();
        System.out.println();
        return input;
    }

    private void sendMessage(ObjectOutputStream outputStream, int number) throws IOException {
        outputStream.writeObject(number);
        outputStream.flush();
    }

    private String receiveMessage(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        return String.valueOf(inputStream.readObject());
    }

    private boolean guardIsWinningResponse(String response) {
        return response.endsWith("ganado.");
    }
}
