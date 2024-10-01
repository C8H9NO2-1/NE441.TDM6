package fr.esisar.exercice3;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serveur extends Thread {
    private static int number = 1; // Cette variable est la variable qu'on
                                   // incrémente à chaque envoi au client, elle
                                   // doit être synchronisée

    private int portNumber; 

    public Serveur(int portNumber) {
        this.portNumber = portNumber;
    }

    public void run() {
        try {
            // On écoute sur le port précisé
            ServerSocket socketEcoute = new ServerSocket();        
            socketEcoute.bind(new InetSocketAddress(portNumber));

            while (true) {
                // On attend que le client se connecte
                Socket socketConnexion = socketEcoute.accept();

                // On attend ensuite le message du client
                byte[] bufR = new byte[2048];
                String message = "";
                InputStream is = socketConnexion.getInputStream();
                int lenBufR = is.read(bufR);
                message += new String(bufR, 0, lenBufR);
                while (message.length() != 7 && lenBufR != -1) {
                    lenBufR = is.read(bufR);
                    message += new String(bufR, 0, lenBufR);
                }
                //System.out.println("Le message est : " + message);

                if (message.equals("NUMERO?")) {
                    sendNumber(socketConnexion);
                } else {
                    byte[] bufE = new String("VOUS AVEZ FAIT UNE ERREUR.").getBytes();
                    OutputStream os = socketConnexion.getOutputStream();
                    os.write(bufE);
                }


                socketConnexion.close();
            }

        } catch (Exception e) {
            System.out.println("Il y a eu un problème lors de l'exécution du thread responsable du port "
                    + portNumber);
        }
    }

    public static void main(String[] args) throws Exception {
        // On initialise le bon nombre de threads
        List<Serveur> threads = new ArrayList<>();

        for (int i = 21000; i <= 25000; i++) {
            threads.add(new Serveur(i));
            threads.get(i - 21000).start();
        }

        for (Serveur thread: threads) {
            thread.join();
        }
    }

    private static synchronized void sendNumber(Socket socketConnexion) throws Exception {
        byte[] bufE = (new String("NUMERO=") + number).getBytes();
        OutputStream os = socketConnexion.getOutputStream();
        os.write(bufE);
        number++;
    }
}
