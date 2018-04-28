package distudios.at.carcassonne.engine.logic;

public class ExtendedCard {
    private int id;
    private CardSide top;
    private CardSide left;
    private CardSide right;
    private CardSide down;
    private CardSide topLeftCorner;
    private CardSide topRightCorner;
    private CardSide bottomLeftCorner;
    private CardSide bottomRightCorner;
    private boolean cathedral;
    private boolean wappen;


        /*todo: Add new Variable for Connections on own Card, z.B. weise jeder Seite einen boolean für Connection bzw no-Connection zu.
          todo: Füge zusätzlich Liste mit den Connections auf der Karte hinzu (Aktuell bijektiv und dual)
        */

        /*
        @Param      id
        @Param      top
        @Param      left
        @Param      right
        @Param      down
        @Param      topLeftCorner
        @Param      topRightCorner
        @Param      bottomLeftCorner
        @Param      bottomRightCorner
         */

    public ExtendedCard(int id, CardSide top, CardSide left, CardSide right, CardSide down, CardSide topLeftCorner, CardSide topRightCorner, CardSide bottomLeftCorner, CardSide bottomRightCorner) {
        this.id = id;
        this.top = top;
        this.left = left;
        this.right = right;
        this.down = down;
        this.topLeftCorner = topLeftCorner;
        this.topRightCorner = topRightCorner;
        this.bottomLeftCorner = bottomLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
        this.cathedral = false;
    }

    public int getId() {
        return id;
    }

    public CardSide getTop() {
        return top;
    }

    public CardSide getLeft() {
        return left;
    }

    public CardSide getRight() {
        return right;
    }

    public CardSide getDown() {
        return down;
    }

    public void setTop(CardSide top) {
        this.top = top;
    }

    public void setLeft(CardSide left) {
        this.left = left;
    }

    public void setRight(CardSide right) {
        this.right = right;
    }

    public void setDown(CardSide down) {
        this.down = down;
    }

    public CardSide getTopLeftCorner() {
        return topLeftCorner;
    }

    public void setTopLeftCorner(CardSide topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }

    public CardSide getTopRightCorner() {
        return topRightCorner;
    }

    public void setTopRightCorner(CardSide topRightCorner) {
        this.topRightCorner = topRightCorner;
    }

    public CardSide getBottomLeftCorner() {
        return bottomLeftCorner;
    }

    public void setBottomLeftCorner(CardSide bottomLeftCorner) {
        this.bottomLeftCorner = bottomLeftCorner;
    }

    public CardSide getBottomRightCorner() {
        return bottomRightCorner;
    }

    public void setBottomRightCorner(CardSide bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }

    public Boolean getCathedral() {
        return cathedral;
    }

    public void setCathedral(Boolean cathedral) {
        this.cathedral = cathedral;
    }
}
