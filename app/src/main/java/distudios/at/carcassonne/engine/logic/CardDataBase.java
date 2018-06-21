package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class CardDataBase {

    //Singleton Class CardDataBase that currently generates a full stack of cards to choose from



    public ArrayList<ExtendedCard> cardDB;
    // 72 Cards including start card leads to STACK SIZE of 71;
    private final int STACK_SIZE=71;

    private static CardDataBase INSTANCE;


    private CardDataBase(){
        cardDB = new ArrayList<>();
    }

    private void init() {
        //CARD A CATHEDRAL w. Street- 2x
        cardDB.add(new ExtendedCard(2, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(2).setCathedral(true);
        cardDB.add(new ExtendedCard(3, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(3).setCathedral(true);
        // CARD B CATHEDRAL - 4x
        cardDB.add(new ExtendedCard(4, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(4).setCathedral(true);
        cardDB.add(new ExtendedCard(5, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(5).setCathedral(true);
        cardDB.add(new ExtendedCard(6, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(6).setCathedral(true);
        cardDB.add(new ExtendedCard(7, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(7).setCathedral(true);
        // CARD C CASTLE 4 SIDED - 1x
        cardDB.add(new ExtendedCard(8, CardSide.CASTLE,CardSide.CASTLE,CardSide.CASTLE,CardSide.CASTLE,CardSide.CLOSED,CardSide.CLOSED,CardSide.CLOSED,CardSide.CLOSED));
        // CARD D CARRD STREET - 4x
        cardDB.add(new ExtendedCard(9, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(10, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(11, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(12, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        //CARRD E CARD CASTLE TOP - 5x
        cardDB.add(new ExtendedCard(13, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(14, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(15, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(16, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(17, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        //CARD F CARD MIDDLE CASTLE - 2x + WAPPEN
        cardDB.add(new ExtendedCard(18, CardSide.GRASS, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED));
        getCardById(18).setWappen(true);
        cardDB.add(new ExtendedCard(19, CardSide.GRASS, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED));
        getCardById(19).setWappen(true);
        // CARD G CARD MIDDLE  CASTLE - 1x
        cardDB.add(new ExtendedCard(20, CardSide.GRASS, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED));
        // CARD H MIDDLE GRAS - 3x + SPLITSTOP
        cardDB.add(new ExtendedCard(21, CardSide.GRASS, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED));
        getCardById(21).setSplitStop(true);
        cardDB.add(new ExtendedCard(22, CardSide.GRASS, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED));
        getCardById(22).setSplitStop(true);
        cardDB.add(new ExtendedCard(23, CardSide.GRASS, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED, CardSide.CLOSED));
        getCardById(23).setSplitStop(true);

        // CARD I - 2x + SPLITSTOP
        cardDB.add(new ExtendedCard(24, CardSide.CASTLE, CardSide.GRASS, CardSide.CASTLE, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN,CardSide.CLOSED));
        getCardById(24).setSplitStop(true);
        cardDB.add(new ExtendedCard(25, CardSide.CASTLE, CardSide.GRASS, CardSide.CASTLE, CardSide.GRASS, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN,CardSide.CLOSED));
        getCardById(25).setSplitStop(true);
        //CARD J - 3x
        cardDB.add(new ExtendedCard(26, CardSide.CASTLE, CardSide.GRASS, CardSide.STREET, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(27, CardSide.CASTLE, CardSide.GRASS, CardSide.STREET, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(28, CardSide.CASTLE, CardSide.GRASS, CardSide.STREET, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        // CARD K - 3x
        cardDB.add(new ExtendedCard(1, CardSide.CASTLE, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(30, CardSide.CASTLE, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(31, CardSide.CASTLE, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        // CARD L - 3x + SPLITSTOP
        cardDB.add(new ExtendedCard(32, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        getCardById(32).setSplitStop(true);
        cardDB.add(new ExtendedCard(33, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        getCardById(33).setSplitStop(true);
        cardDB.add(new ExtendedCard(34, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN));
        getCardById(34).setSplitStop(true);
        // CARD M - 2x + WAPPEN
        cardDB.add(new ExtendedCard(35, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        getCardById(35).setWappen(true);
        cardDB.add(new ExtendedCard(36, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        getCardById(36).setWappen(true);
        // CARD N - 3x
        cardDB.add(new ExtendedCard(37, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        cardDB.add(new ExtendedCard(38, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        cardDB.add(new ExtendedCard(39, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        //CARD O - 2x
        cardDB.add(new ExtendedCard(40, CardSide.CASTLE, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        getCardById(35).setWappen(true);
        cardDB.add(new ExtendedCard(41, CardSide.CASTLE, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        getCardById(35).setWappen(true);
        // CARD P - 3x
        cardDB.add(new ExtendedCard(42, CardSide.CASTLE, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        cardDB.add(new ExtendedCard(43, CardSide.CASTLE, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        cardDB.add(new ExtendedCard(44, CardSide.CASTLE, CardSide.CASTLE, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN));
        // CARD Q - 1x + WAPPEN
        cardDB.add(new ExtendedCard(45, CardSide.CASTLE, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED));
        getCardById(45).setWappen(true);
        // CARD R - 3x
        cardDB.add(new ExtendedCard(46, CardSide.CASTLE, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED));
        cardDB.add(new ExtendedCard(47, CardSide.CASTLE, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED));
        cardDB.add(new ExtendedCard(48, CardSide.CASTLE, CardSide.CASTLE, CardSide.CASTLE, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED));
        // CARD S - 2x + WAPPEN
        cardDB.add(new ExtendedCard(49, CardSide.CASTLE, CardSide.CASTLE, CardSide.CASTLE, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED));
        getCardById(49).setWappen(true);
        cardDB.add(new ExtendedCard(50, CardSide.CASTLE, CardSide.CASTLE, CardSide.CASTLE, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED));
        getCardById(50).setWappen(true);
        // CARD T - 1x
        cardDB.add(new ExtendedCard(51, CardSide.CASTLE, CardSide.CASTLE, CardSide.CASTLE, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.CLOSED, CardSide.CLOSED));
        // CARD U - 8x
        cardDB.add(new ExtendedCard(52, CardSide.STREET, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(53, CardSide.STREET, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(54, CardSide.STREET, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(55, CardSide.STREET, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(56, CardSide.STREET, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(57, CardSide.STREET, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(58, CardSide.STREET, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(59, CardSide.STREET, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        // CARD V - 9x
        cardDB.add(new ExtendedCard(60, CardSide.GRASS, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(61, CardSide.GRASS, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(62, CardSide.GRASS, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(63, CardSide.GRASS, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(64, CardSide.GRASS, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(65, CardSide.GRASS, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(66, CardSide.GRASS, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(67, CardSide.GRASS, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(68, CardSide.GRASS, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        // CARD W - 4x
        cardDB.add(new ExtendedCard(69, CardSide.GRASS, CardSide.STREET, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(69).setSplitStop(true);
        cardDB.add(new ExtendedCard(70, CardSide.GRASS, CardSide.STREET, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(70).setSplitStop(true);
        cardDB.add(new ExtendedCard(71, CardSide.GRASS, CardSide.STREET, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(71).setSplitStop(true);
        cardDB.add(new ExtendedCard(72, CardSide.GRASS, CardSide.STREET, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(72).setSplitStop(true);

        // CARD X
        cardDB.add(new ExtendedCard(29, CardSide.STREET, CardSide.STREET, CardSide.STREET, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(29).setSplitStop(true);



    }

    public static CardDataBase getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new CardDataBase();
            INSTANCE.init();
        }
        return INSTANCE;
    }


    public static ExtendedCard getCardById (int id){
        if (id>0) {
            for (ExtendedCard ec :
                    getInstance().cardDB) {
                if (ec.getId() == id) {
                    return ec;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /*
    private ExtendedCard generateRandomCard(int id){
        return new ExtendedCard(id, CardSide.randomCarSide(), CardSide.randomCarSide(), CardSide.randomCarSide(), CardSide.randomCarSide());
    }
*/
    //method orientation + id returns CardSide

    public void sortCardDB(){
        Collections.sort(cardDB, new Comparator<ExtendedCard>() {
            @Override
            public int compare(ExtendedCard o1, ExtendedCard o2) {
                return (int)(o1.getId()-o2.getId());
            }
        });
    }

    public CardSide getCardSide(int id, Orientation orientation){
        switch (orientation){
            case NORTH:
                return getCardById(id).getTop();

            case WEST:
                return getCardById(id).getLeft();

            case EAST:
                return getCardById(id).getRight();

            case SOUTH:
                return getCardById(id).getDown();

            default:
                return null;

        }
    }



    /*
    Returns an Array with Orientations where CardSides match the parameter CardSide of a given Card (by ID)
     */
    public List<Orientation> getMatchingOrientations(int id, CardSide cardSide) {
        ArrayList<Orientation> cardSides = new ArrayList<Orientation>();
        if (this.getCardSide(id,Orientation.NORTH)== cardSide) cardSides.add(Orientation.NORTH);
        if (this.getCardSide(id,Orientation.EAST)==cardSide) cardSides.add(Orientation.EAST);
        if (this.getCardSide(id,Orientation.SOUTH)==cardSide) cardSides.add(Orientation.SOUTH);
        if (this.getCardSide(id,Orientation.WEST)==cardSide) cardSides.add(Orientation.WEST);

        return cardSides;
    }
}

