package fr.esisar.exercice1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Philosophes2 extends Thread {
    enum Activity {
        EATING,
        DISCUSSING,
    }

    private Random rand;
    private int number;
    private Activity activity;

    public Philosophes2(int number) {
        // We have to initialize the randomizer
        rand = new Random();
        this.number = number;
    }

    public void run() {
        // We decide whether we begin we eating or discussing
        switch (rand.nextInt(1, 2)) {
            case 1:
                beginActivity("manger");
                activity = Activity.EATING;
                break;
            case 2:
                beginActivity("discuter");
                activity = Activity.DISCUSSING;
        }

        while (true) {
            int temp = rand.nextInt(10);
            try {
                sleep(temp * 1000);
            } catch(InterruptedException e) {
                System.out.println("Well that sucks: " + e);
            }

            if (activity == Activity.EATING) {
                beginActivity("discuter");
                activity = Activity.DISCUSSING;
            } else {
                beginActivity("manger");
                activity = Activity.EATING;
            }
        }
    }

    private void beginActivity(String message) {
        System.out.println("Le philosophe " + number + " commence à " + message);
    }

    public static void main(String[] args) {
        List<Philosophes2> philosophes = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            philosophes.add(new Philosophes2(i + 1));
            philosophes.get(i).start();
        }
    }
}
