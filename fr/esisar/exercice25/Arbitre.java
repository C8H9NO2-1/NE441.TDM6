package fr.esisar.exercice25;

import java.util.ArrayList;
import java.util.List;

public class Arbitre {
    private int numberOfPhilosophers;
    private List<Boolean> baguettes; // This variable contains true if the baguette 
                                 // is free and false otherwise

    public Arbitre(int numberOfPhilosophers) {
        this.numberOfPhilosophers = numberOfPhilosophers;

        baguettes = new ArrayList<>();

        for (int i = 0; i < numberOfPhilosophers; i++) {
            baguettes.add(true);
        }
    }

    synchronized boolean autorisation(int numPhilo) {
        // We have multiple cases depending on the philosophers calling the
        // method
        if (numPhilo == 1) {
            // In this case we have to check the last and first baguettes
            if (baguettes.get(numPhilo - 1) && baguettes.get(numberOfPhilosophers - 1)) {
                baguettes.set(numPhilo - 1, false);
                baguettes.set(numberOfPhilosophers -1, false);
                return true;
            } else {
                return false;
            }
        } else {
            // This case is the generic cases in which we easily check both
            // baguettes
            if (baguettes.get(numPhilo - 1) && baguettes.get(numPhilo - 2)) {
                baguettes.set(numPhilo - 1, false);
                baguettes.set(numPhilo - 2, false);
                return true;
            } else {
                return false;
            }
        }
    }

    synchronized void liberation(int numPhilo) {
        // Here we have the same cases as in the function above
        if (numPhilo == 1) {
            baguettes.set(numPhilo - 1, true);
            baguettes.set(numberOfPhilosophers - 1, true);
        } else {
            baguettes.set(numPhilo - 1, true);
            baguettes.set(numPhilo - 2, true);
        }
    }
}
