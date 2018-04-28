package distudios.at.carcassonne.engine.logic;

import java.util.ArrayList;



public class CardDataBase {

    //Singleton Class CardDataBase that currently generates a full stack of cards to choose from



    public ArrayList<ExtendedCard> cardDB=new ArrayList<>();
    // 72 Cards including start card leads to STACK SIZE of 71;
    private final int STACK_SIZE=71;

    private static CardDataBase INSTANCE = new CardDataBase();


    private CardDataBase(){
        //START CARD
        cardDB.add(new ExtendedCard(1, CardSide.CASTLE, CardSide.STREET, CardSide.GRASS, CardSide.STREET, CardSide.CLOSED, CardSide.CLOSED, CardSide.OPEN, CardSide.OPEN ));
        //CARD A CATHEDRAL w. Street- 2x
        cardDB.add(new ExtendedCard(2, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        getCardById(2).setCathedral(true);
        cardDB.add(new ExtendedCard(3, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.STREET, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        // CARD B CATHEDRAL - 4x
        cardDB.add(new ExtendedCard(4, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(5, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(6, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        cardDB.add(new ExtendedCard(7, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.GRASS, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN, CardSide.OPEN));
        // CARD C CASTLE 4 SIDED - 1x



    }

    public static CardDataBase getInstance(){
        return (INSTANCE);
    }


    public static CardDataBase cardDataBase = CardDataBase.getInstance();

    public static ExtendedCard getCardById (int id){
        if (id>0) {
            return cardDataBase.cardDB.get(id - 1);
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

    public CardSide getOrientation (int id, Orientation orientation){
        switch (orientation){
            case NORTH:
                return cardDB.get(id-1).getTop();

            case WEST:
                return cardDB.get(id-1).getLeft();

            case EAST:
                return cardDB.get(id-1).getRight();

            case SOUTH:
                return cardDB.get(id-1).getDown();

            default:
                return null;

        }
    }
}