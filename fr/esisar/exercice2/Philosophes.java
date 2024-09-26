package fr.esisar.exercice2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Philosophes extends Thread {
    enum Activity {
        EATING,
        DISCUSSING,
    }

    private Random rand;
    private int number;
    private Activity activity;

    private static Arbitre arbitre = new Arbitre(5);

    public Philosophes(int number) {
        // We have to initialize the randomizer
        rand = new Random();
        this.number = number;
    }

    public void run() {
        beginActivity("discuter");
        try {
            sleep(rand.nextInt(10) * 1000);
        } catch (Exception e) {
            System.out.println("Well that sucks: " + e);
        }

        while (true) {
            if (activity == Activity.EATING) {
                arbitre.liberation(number);
                beginActivity("discuter");
                activity = Activity.DISCUSSING;
            } else {
                while (!arbitre.autorisation(number)) {
                    try {
                        sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Well that sucks: " + e);
                    }
                }
                beginActivity("manger");
                activity = Activity.EATING;
            }

            try {
                sleep(rand.nextInt(10) * 1000);
            } catch(Exception e) {
                System.out.println("Well that sucks: " + e);
            }
        }
    }

    private void beginActivity(String message) {
        System.out.println("Le philosophe " + number + " commence à " + message);
    }

    public static void main(String[] args) {
        List<Philosophes> philosophes = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            philosophes.add(new Philosophes(i + 1));
            philosophes.get(i).start();
        }
    }
}
