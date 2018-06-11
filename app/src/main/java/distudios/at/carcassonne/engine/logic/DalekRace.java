package distudios.at.carcassonne.engine.logic;

public class DalekRace extends Player {
    //BOSS KLASSE FOR 3v1 Mode
    public DalekRace(int playerID) {
        super(playerID);
        this.peeps = 5;
        this.scoreMultiplierTotal = 2;
        this.canSetOnExistingPeeps = true;
        this.peepStrength = 20;

    }
}
