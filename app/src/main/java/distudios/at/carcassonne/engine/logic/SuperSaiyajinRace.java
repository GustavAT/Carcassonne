package distudios.at.carcassonne.engine.logic;

public class SuperSaiyajinRace extends Player {
    public SuperSaiyajinRace(int playerID) {
        super(playerID);
        this.peeps = 6;
        this.scoreMultiplierTotal = 1.5f;
        this.canSetOnExistingPeeps = true;
        this.peepStrength = 1;
        this.gainsStrengthFromOtherPeeps = true;
        this.peepStrengthMultiplierPerOtherPeep = 0.2f;

    }

}
