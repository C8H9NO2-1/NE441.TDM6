package fr.esisar.exercice25;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tdm.tdm07.exo3.checker.CodeChecker;

public class Philosophes extends Thread {
    enum Activity {
        EATING,
        DISCUSSING,
    }

    private Random rand;
    private int number;
    private Activity activity;

    private static Arbitre arbitre = new Arbitre(50);

    public Philosophes(int number) {
        // We have to initialize the randomizer
        rand = new Random();
        this.number = number;
    }

    public void run() {
        try {
            sleep(1);
        } catch (Exception e) {
            System.out.println("Well that sucks: " + e);
        }

        while (true) {
            if (activity == Activity.EATING) {
                CodeChecker.stopEating(number);
                arbitre.liberation(number);
                activity = Activity.DISCUSSING;
            } else {
                while (!arbitre.autorisation(number)) {
                    try {
                        sleep(1);
                    } catch (Exception e) {
                        System.out.println("Well that sucks: " + e);
                    }
                }
                CodeChecker.startEating(number);
                activity = Activity.EATING;
            }

            try {
                sleep(1);
            } catch(Exception e) {
                System.out.println("Well that sucks: " + e);
            }
        }
    }

    public static void main(String[] args) {
        List<Philosophes> philosophes = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            philosophes.add(new Philosophes(i + 1));
            philosophes.get(i).start();
        }
    }
}
