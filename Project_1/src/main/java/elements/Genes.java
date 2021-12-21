package elements;

import java.util.Arrays;
import java.util.Random;

public class Genes {
    private int[] animalGenes;
    Random random = new Random();

    // Constructor
    public Genes() {
        this.animalGenes = new int[32];
        for (int i = 0; i < this.animalGenes.length; i++) {
            animalGenes[i] = random.nextInt(8);
        }
        Arrays.sort(animalGenes);
    }


    public Genes(Genes genes1, Genes genes2, int energy1, int energy2) {
        int[] animal1Genes = getGenes(genes1);
        int[] animal2Genes = getGenes(genes2);
        if (animal1Genes.length != animal2Genes.length)
            throw new IllegalArgumentException("Genes have different size.");
        int totalEnergy = energy1 + energy2;
        int dividedGenes1 = (int) (energy1 / totalEnergy * 32);
        int dividedGenes2 = 32 - dividedGenes1;
        int division = random.nextInt(2);
        int size = 0;
        if (division == 0) {
            for (int i = 0; i < dividedGenes1; i++) {
                this.animalGenes[size] = animal1Genes[i];
                size++;
            }
            for (int i = 0; i < dividedGenes2; i++) {
                this.animalGenes[size] = animal2Genes[32 - i - 1];
                size++;
            }
        } else {
            for (int i = 0; i < dividedGenes2; i++) {
                this.animalGenes[size] = animal2Genes[i];
                size++;
            }
            for (int i = 0; i < dividedGenes1; i++) {
                this.animalGenes[size] = animal1Genes[32 - i - 1];
                size++;
            }
        }
        Arrays.sort(this.animalGenes);
    }


    // Getters
    public int[] getGenes(Genes genes) {
        return genes.animalGenes;
    }

    public int getRandomGene() {
        int randomGene = random.nextInt(33);
        return this.animalGenes[randomGene];
    }


    // toString
    @Override
    public String toString() {
        return Arrays.toString(this.animalGenes);
    }
}