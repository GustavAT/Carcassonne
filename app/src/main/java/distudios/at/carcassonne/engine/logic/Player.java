package distudios.at.carcassonne.engine.logic;

public class Player {

    public PlayerColors playerColor;
    public int peeps = 10;
    public int playerID;

    //TODO: MICHI AUSWERTUNG VON SCORE KLASSE
    public float scoreMultiplierTotal = 1;
    public float scoreMultiplierCathedral = 1;
    public float scoreMultiplierCastles = 1;
    public float scoreMultiplierStreets = 1;
    public float peepStrength = 1;
    public Boolean gainsStrengthFromOtherPeeps = false;
    public float peepStrengthMultiplierPerOtherPeep = 1;

    //TODO: SIMON EINBAU IN MEEPLE SET
    // Can set multiple peeps on own player ID
    public Boolean canSetMultiplePeeps = false;
    // Can set multiple peeps on other player ID
    public Boolean canSetOnExistingPeeps = false;
    // Can only set peeops on other player ID
    public Boolean canOnlySetOnExisitingPeeps = false;
    // Ccan Convert other player ID peeps when they have multiple on same field.
    public Boolean canConvertPeeps = false;
    //CONVERTS A peep when they have multiples of his strength
    public float convertsPeepsOnMultipleOf = 1;

    public Player(int playerID) {
        this.playerID = playerID;
    }

    public static Player getRaceFromPlayer(int race, int playerID) {
        switch (race) {
            case 1:
                return new Player(playerID);

            case 2:
                return new ZergRace(playerID);

            case 3:
                return new ProtossRace(playerID);

            case 4:
                return new GoaUldRace(playerID);

            case 5:
                return new DalekRace(playerID);

            case 6:
                return new KlingonRace(playerID);

            case 7:
                return new FederationRace(playerID);

            case 8:
                return new ScientologyRace(playerID);

            case 9:
                return new SuperSaiyajinRace(playerID);

            default:
                return new Player(playerID);

        }
    }

}

