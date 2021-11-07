package agh.ics.oop;

public abstract class GrassField implements IWorldMap {
    int clumpsOfGrass;

    public GrassField(int clumpsOfGrass) {
        this.clumpsOfGrass = clumpsOfGrass;
    }

    @Override
    public String toString() {
        return "GrassField{" +
                "clumpsOfGrass=" + clumpsOfGrass +
                '}';
    }
}
