package distudios.at.carcassonne.engine.logic;

public class Player {

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
    // Kann mehrere Bauwerke auf einer Karte besetzen
    public Boolean canSetMultiplePeeps = false;
    // Ignorieren von marks
    public Boolean canSetOnExistingPeeps = false;
    // Setzen nur auf markierte Seiten
    public Boolean canOnlySetOnExisitingPeeps = false;
    // Bei geteilten Bauwerken einen Gegner-Peep in eigenen Umwandeln
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

