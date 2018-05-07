package distudios.at.carcassonne.gui.field;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import distudios.at.carcassonne.CarcassonneApp;
import distudios.at.carcassonne.R;
import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.GameController;
import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.engine.logic.IGameController;
import distudios.at.carcassonne.engine.logic.Orientation;

public class PlayfieldView extends View {

    public static int CARDS_COUNT_HORIZONTAL = 6;
    private Paint rasterPaint;
    private Paint cardPaint;

    private boolean dragging = false;
    private double lastX;
    private double lastY;
    private double diffX;
    private double diffY;

    private GameState gameState;

    private Map<String, CardContainer> placedCards = new HashMap<>();
    private Map<String, CardContainer> possibleLocations = new HashMap<>();

    public PlayfieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                initFieldFromGameState();
            }
        });
    }

    private void initPaint() {
        rasterPaint = new Paint();
        rasterPaint.setStyle(Paint.Style.STROKE);
        rasterPaint.setStrokeWidth(3);
        rasterPaint.setColor(Color.BLUE);
        rasterPaint.setTextSize(25);

        cardPaint = new Paint();
        cardPaint.setStyle(Paint.Style.FILL);
        cardPaint.setColor(getResources().getColor(R.color.colorAccent));
    }

    /**
     * Calculate distance between two points.
     * TODO: move to units class
     */
    private double distanceBetween(double p1x, double p1y, double p2x, double p2y) {
        double dx = p1x - p2x;
        double dy = p1y - p2y;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        super.onTouchEvent(e);

        double x = e.getX();
        double y = e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = x;
            lastY = y;
        } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
            if (!dragging) {
                if (distanceBetween(x, y, lastX, lastY) > 10) {
                    dragging = true;
                }
            }

            if (dragging) {
                diffX = lastX - x;
                diffY = lastY - y;

                for (String key : possibleLocations.keySet()) {
                    CardContainer c = possibleLocations.get(key);
                    c.offsetX = -diffX;
                    c.offsetY = -diffY;
                }

                for (String key : placedCards.keySet()) {
                    CardContainer c = placedCards.get(key);
                    c.offsetX = -diffX;
                    c.offsetY = -diffY;
                }

                invalidate();
            }

        } else if (e.getAction() == MotionEvent.ACTION_UP) {

            if (dragging) {
                for (String key : possibleLocations.keySet()) {
                    CardContainer c = possibleLocations.get(key);
                    c.pixelX += c.offsetX;
                    c.pixelY += c.offsetY;
                    c.offsetX = c.offsetY = 0;
                }

                for (String key : placedCards.keySet()) {
                    CardContainer c = placedCards.get(key);
                    c.pixelX += c.offsetX;
                    c.pixelY += c.offsetY;
                    c.offsetX = c.offsetY = 0;
                }

                invalidate();

            } else {
                IGameController controller = CarcassonneApp.getGameController();
                if (!controller.hasPlacedCard()) {
                    for (String key : possibleLocations.keySet()) {
                        CardContainer c = possibleLocations.get(key);

                        if (x >= c.pixelX && x <= (c.pixelX + c.size) && y >= c.pixelY && y <= (c.pixelY + c.size)) {
                            Toast.makeText(getContext(), "Clicked: x: " + c.fieldX + " y: " + c.fieldY, Toast.LENGTH_SHORT).show();

                            CardContainer c2 = placedCards.get(c.key());

                            if (c2 == null) {
                                c2 = c.copy();
                                c2.card = controller.getCurrentCard();
                                placedCards.put(c2.key(), c2);

                                Card card = controller.getCurrentCard();
                                card.setxCoordinate(c2.fieldX);
                                card.setyCoordinate(c2.fieldY);
                                controller.placeCard(card);

                                invalidate();
                            } else {
                                centerCard(c2);
                            }

                            break;
                        }
                    }
                }
            }

            dragging = false;
        }

        return true;
    }

    private void addSurroundingFields(CardContainer center) {
//        addSurroundingField(center, -1, 1);
        addSurroundingField(center, 0, 1);
//        addSurroundingField(center, 1, 1);
        addSurroundingField(center, -1, 0);
        addSurroundingField(center, 1, 0);
//        addSurroundingField(center, -1, -1);
        addSurroundingField(center, 0, -1);
//        addSurroundingField(center, 1, -1);
    }

    private void addSurroundingField(CardContainer center, int dirX, int dirY) {
        int targetX = center.fieldX + dirX;
        int targetY = center.fieldY + dirY;

        String key = targetX + "_" + targetY;

        if (possibleLocations.containsKey(key)) return;

        CardContainer c = new CardContainer();
        c.fieldX = targetX;
        c.fieldY = targetY;
        c.size = center.size;
        c.pixelX = center.pixelX + dirX * center.size;
        c.pixelY = center.pixelY + dirY * center.size;

        possibleLocations.put(key, c);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (String key : placedCards.keySet()) {
            CardContainer c = placedCards.get(key);
            drawCardContainer(c, canvas, cardPaint);
            canvas.drawText("" + c.card.getId(), (int) (c.pixelX + c.offsetX), (int)(c.pixelY + c.offsetY + 25), rasterPaint);
            canvas.drawText("" + c.card.getOrientation(), (int) (c.pixelX + c.offsetX), (int)(c.pixelY + c.offsetY + 50), rasterPaint);
        }

        for (String key: possibleLocations.keySet()) {
            CardContainer c = possibleLocations.get(key);
            drawCardContainer(c, canvas, rasterPaint);
            canvas.drawText("X: " + c.fieldX  + ", Y " + c.fieldY, (int) (c.pixelX + c.offsetX + c.size / 4), (int)(c.pixelY + c.offsetY + c.size / 2), rasterPaint);
        }
    }

    private void drawCardContainer(CardContainer c, Canvas canvas, Paint p) {
        float left = (float)(c.pixelX + c.offsetX);
        float top = (float)(c.pixelY + c.offsetY);
        float right = (float)(c.pixelX + c.offsetX + c.size);
        float bottom = (float)(c.pixelY + c.offsetY + c.size);
        canvas.drawRect(left, top, right, bottom, p);
    }

    public void centerCard(CardContainer target) {
        if (target == null) {
            target = placedCards.get("0_0");
        }

        if (target == null) return;

        int height = getHeight() - getPaddingBottom() - getPaddingTop();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();

        double centerX = width / 2 - target.size / 2;
        double centerY = height / 2 - target.size / 2;

        double offsetX = target.pixelX - centerX;
        double offsetY = target.pixelY - centerY;

        for (String key : placedCards.keySet()) {
            CardContainer c = placedCards.get(key);
            c.pixelX -= offsetX;
            c.pixelY -= offsetY;
        }

        for (String key : possibleLocations.keySet()) {
            CardContainer c = possibleLocations.get(key);
            c.pixelX -= offsetX;
            c.pixelY -= offsetY;
        }

        invalidate();

    }

//    public void initField() {
//        int height = getHeight() - getPaddingBottom() - getPaddingTop();
//        int width = getWidth() - getPaddingLeft() - getPaddingRight();
//
//        int centerX = width / 2;
//        int centerY = height / 2;
//        double cardSize = width / CARDS_COUNT_HORIZONTAL;
//
//        possibleLocations.clear();
//        placedCards.clear();
//
//        CardContainer initialCard = new CardContainer();
//        initialCard.fieldY = initialCard.fieldX = 0;
//        initialCard.pixelX = centerX - cardSize / 2;
//        initialCard.pixelY = centerY - cardSize / 2;
//        initialCard.size = cardSize;
//        placedCards.put(initialCard.key(), initialCard);
//
//        addSurroundingFields(initialCard);
//        invalidate();
//
//    }

    public void initFieldFromGameState() {
        gameState = CarcassonneApp.getGameController().getGameState();
        List<Card> cards = gameState.getCards();

        possibleLocations.clear();
        placedCards.clear();

        int height = getHeight() - getPaddingBottom() - getPaddingTop();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();

        int centerX = width / 2;
        int centerY = height / 2;
        double cardSize = width / CARDS_COUNT_HORIZONTAL;

        CardContainer lastCard = null;
        for (Card c : cards) {
            CardContainer ctn = new CardContainer();
            ctn.id = c.getId();
            ctn.fieldX = c.getxCoordinate();
            ctn.fieldY = c.getyCoordinate();
            ctn.pixelX = ctn.fieldX * cardSize - cardSize / 2;
            ctn.pixelY = ctn.fieldY * cardSize - cardSize / 2;
            ctn.size = getCardSize();
            ctn.card = c;
            placedCards.put(ctn.key(), ctn);
            lastCard = ctn;
        }

        if (lastCard != null) {
            centerCard(lastCard);
        }
        invalidate();
    }

    public void addPossibleLocations() {
        possibleLocations.clear();

        IGameController controller = CarcassonneApp.getGameController();
        Card currentCard = controller.getCurrentCard();

        // user has not drawn a card yet
        if (currentCard == null) {
            invalidate();
            return;
        };

        List<Pair<Integer, Integer>> locations = controller.getPossibleLocations(currentCard);

        // get initial card
        CardContainer initialCard = placedCards.get("0_0");

        double size = initialCard.size;

        for (Pair<Integer, Integer> position : locations) {
            CardContainer c = new CardContainer();
            c.fieldX = position.first;
            c.fieldY = position.second;
            c.offsetX = initialCard.offsetY;
            c.offsetY = initialCard.offsetY;
            c.size = initialCard.size;
            c.pixelX = initialCard.pixelX + c.fieldX * size;
            c.pixelY = initialCard.pixelY + c.fieldY * size;
            possibleLocations.put(c.key(), c);
        }

        invalidate();
    }

    public double getCardSize() {
        int height = getHeight() - getPaddingBottom() - getPaddingTop();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();

        int centerX = width / 2;
        int centerY = height / 2;
        return width / CARDS_COUNT_HORIZONTAL;
    }

    public class CardContainer {
        public int id;
        public double pixelX = 0;
        public double pixelY = 0;
        public double size;
        public double offsetX = 0;
        public double offsetY = 0;

        public int fieldX;
        public int fieldY;

        public Card card;

        public String key() {
            return fieldX + "_" + fieldY;
        }

        public CardContainer copy() {
            CardContainer c = new CardContainer();
            c.pixelX = pixelX;
            c.pixelY = pixelY;
            c.size = size;
            c.offsetX = offsetX;
            c.offsetY = offsetY;
            c.fieldX = fieldX;
            c.fieldY = fieldY;
            c.card = card;
            return c;
        }
    }


}
