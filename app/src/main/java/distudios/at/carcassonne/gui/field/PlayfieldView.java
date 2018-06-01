package distudios.at.carcassonne.gui.field;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaExtractor;
import android.support.annotation.Nullable;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.util.Log;
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
import distudios.at.carcassonne.engine.logic.CState;
import distudios.at.carcassonne.engine.logic.Card;
import distudios.at.carcassonne.engine.logic.CardDataBase;
import distudios.at.carcassonne.engine.logic.CardSide;
import distudios.at.carcassonne.engine.logic.ExtendedCard;
import distudios.at.carcassonne.engine.logic.GameController;
import distudios.at.carcassonne.engine.logic.GameEngine;
import distudios.at.carcassonne.engine.logic.GameState;
import distudios.at.carcassonne.engine.logic.IGameController;
import distudios.at.carcassonne.engine.logic.Orientation;

public class PlayfieldView extends View {

    public ICardPlaced callbackCardPlaced;
    public IPeepPlaced callbackPeepPlaced;

    public static int CARDS_COUNT_HORIZONTAL = 6;

    private Paint rasterPaint;
    private Paint debugPaint;
    private Paint cardPaint;

    // todo remove after graphics for cards are set up
    private Paint paintStreet;
    private Paint paintGrass;
    private Paint paintCastle;
    private Paint paintSpecial;

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
        initResourceMappings();

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                initFieldFromGameState();
            }
        });
    }



    public static HashMap<Integer, Bitmap> ResourceMappings = null;

    private void initPaint() {
        rasterPaint = new Paint();
        rasterPaint.setStyle(Paint.Style.STROKE);
        rasterPaint.setStrokeWidth(3);
        rasterPaint.setColor(Color.YELLOW);
        rasterPaint.setTextSize(25);

        debugPaint = new Paint();
        debugPaint.setStyle(Paint.Style.STROKE);
        debugPaint.setStrokeWidth(3);
        debugPaint.setColor(Color.RED);
        debugPaint.setTextSize(25);

        cardPaint = new Paint();
        cardPaint.setStyle(Paint.Style.FILL);
        cardPaint.setColor(getResources().getColor(R.color.white));

        paintCastle = new Paint();
        paintCastle.setStyle(Paint.Style.FILL);
        paintCastle.setColor(getResources().getColor(R.color.white));

        paintStreet = new Paint();
        paintStreet.setStyle(Paint.Style.FILL);
        paintStreet.setColor(getResources().getColor(R.color.black));

        paintGrass = new Paint();
        paintGrass.setStyle(Paint.Style.FILL);
        paintGrass.setColor(getResources().getColor(R.color.colorPrimary));


        paintSpecial = new Paint();
        paintSpecial.setStyle(Paint.Style.FILL);
        paintSpecial.setColor(getResources().getColor(R.color.yellow));
    }

    private void initResourceMappings() {
        if (ResourceMappings == null) {
            ResourceMappings = new HashMap<>();

            ResourceMappings.put(1, BitmapFactory.decodeResource(getResources(), R.drawable.a));
            ResourceMappings.put(2, BitmapFactory.decodeResource(getResources(), R.drawable.b));
            ResourceMappings.put(3, BitmapFactory.decodeResource(getResources(), R.drawable.c));
            ResourceMappings.put(4, BitmapFactory.decodeResource(getResources(), R.drawable.d));
            ResourceMappings.put(5, BitmapFactory.decodeResource(getResources(), R.drawable.e));
            ResourceMappings.put(6, BitmapFactory.decodeResource(getResources(), R.drawable.f));
            ResourceMappings.put(7, BitmapFactory.decodeResource(getResources(), R.drawable.g));
            ResourceMappings.put(8, BitmapFactory.decodeResource(getResources(), R.drawable.h));
            ResourceMappings.put(9, BitmapFactory.decodeResource(getResources(), R.drawable.i));
            ResourceMappings.put(10, BitmapFactory.decodeResource(getResources(), R.drawable.j));
            ResourceMappings.put(11, BitmapFactory.decodeResource(getResources(), R.drawable.k));
            ResourceMappings.put(12, BitmapFactory.decodeResource(getResources(), R.drawable.l));
            ResourceMappings.put(13, BitmapFactory.decodeResource(getResources(), R.drawable.m));
            ResourceMappings.put(14, BitmapFactory.decodeResource(getResources(), R.drawable.n));
            ResourceMappings.put(15, BitmapFactory.decodeResource(getResources(), R.drawable.o));
            ResourceMappings.put(16, BitmapFactory.decodeResource(getResources(), R.drawable.p));
            ResourceMappings.put(17, BitmapFactory.decodeResource(getResources(), R.drawable.q));
            ResourceMappings.put(18, BitmapFactory.decodeResource(getResources(), R.drawable.r));
            ResourceMappings.put(19, BitmapFactory.decodeResource(getResources(), R.drawable.s));
            ResourceMappings.put(20, BitmapFactory.decodeResource(getResources(), R.drawable.t));
            ResourceMappings.put(21, BitmapFactory.decodeResource(getResources(), R.drawable.u));
            ResourceMappings.put(22, BitmapFactory.decodeResource(getResources(), R.drawable.v));
            ResourceMappings.put(23, BitmapFactory.decodeResource(getResources(), R.drawable.w));
            ResourceMappings.put(24, BitmapFactory.decodeResource(getResources(), R.drawable.x));
        }
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
                if (controller.getCState() == CState.PLACE_CARD && callbackCardPlaced != null) {
                    for (String key : possibleLocations.keySet()) {
                        CardContainer c = possibleLocations.get(key);

                        if (x >= c.pixelX && x <= (c.pixelX + c.size) && y >= c.pixelY && y <= (c.pixelY + c.size)) {

                            CardContainer c2 = placedCards.get(c.key());

                            if (c2 == null) {
                                c2 = c.copy();

                                c2.card = controller.getCurrentCard();
                                placedCards.put(c2.key(), c2);

                                clearPossibleLocations();
                                callbackCardPlaced.cardPlaced(c2.fieldX, c2.fieldY);

                                invalidate();
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

    @Override
    protected void onDraw(Canvas canvas) {

        for (String key : placedCards.keySet()) {
            CardContainer c = placedCards.get(key);
            drawCardContainer(c, canvas);
//            canvas.drawText(c.card.getId() + " " +  c.card.getOrientation(), (int) (c.pixelX + c.offsetX), (int)(c.pixelY + c.offsetY + 25), rasterPaint);
        }

        for (String key: possibleLocations.keySet()) {
            CardContainer c = possibleLocations.get(key);
            drawCardContainer(c, canvas, rasterPaint);
//            canvas.drawText("X: " + c.fieldX  + ", Y " + c.fieldY, (int) (c.pixelX + c.offsetX + c.size / 4), (int)(c.pixelY + c.offsetY + c.size / 2), debugPaint);
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

    public void initFieldFromGameState() {
        IGameController controller = CarcassonneApp.getGameController();
        gameState = controller.getGameState();
        List<Card> cards = gameState.getCards();

        possibleLocations.clear();
        placedCards.clear();

        int width = getWidth() - getPaddingLeft() - getPaddingRight();

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

        if (controller.getCState() == CState.PLACE_CARD) {
            addPossibleLocations();
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
        }

        List<Pair<Integer, Integer>> locations = controller.getPossibleLocations(currentCard);

        // get initial card
        CardContainer initialCard = placedCards.get("0_0");

        double size = initialCard.size;

        for (Pair<Integer, Integer> position : locations) {
            CardContainer c = new CardContainer();
            c.fieldX = position.first;
            c.fieldY = position.second;
            c.offsetX = initialCard.offsetX;
            c.offsetY = initialCard.offsetY;
            c.size = initialCard.size;
            c.pixelX = initialCard.pixelX + c.fieldX * size;
            c.pixelY = initialCard.pixelY + c.fieldY * size;
            possibleLocations.put(c.key(), c);
        }

        invalidate();
    }

    private static int cardIdToBitmapId(int cardId) {
        if (cardId == 2 || cardId == 3) {
            return 1;
        } else if (cardId == 4 || cardId == 5 || cardId == 6 || cardId == 7) {
            return 2;
        } else if (cardId == 8) {
            return 3;
        } else if (cardId == 9 || cardId == 10 || cardId == 11 || cardId == 12) {
            return 4;
        } else if (cardId == 13 || cardId == 14 || cardId ==  15 || cardId == 16 || cardId ==  17) {
            return 5;
        } else if (cardId == 18 || cardId ==  19) {
            return 6;
        } else if (cardId == 20) {
            return 7;
        } else if (cardId == 21 || cardId == 22 || cardId ==  23) {
            return 8;
        } else if (cardId ==  24 || cardId == 25) {
            return 9;
        } else if (cardId ==  26 || cardId ==  27 || cardId ==  28) {
            return 10;
        } else if (cardId ==  1 || cardId == 30 || cardId == 31) {
            return 11;
        } else if (cardId == 32 || cardId ==  33 || cardId == 34) {
            return 12;
        } else if (cardId == 35 || cardId == 36) {
            return 13;
        } else if (cardId == 37 || cardId ==  38 || cardId ==  39) {
            return 14;
        } else if (cardId == 40 || cardId ==  41) {
            return 15;
        } else if (cardId ==  42 || cardId ==  43 || cardId == 44) {
            return 16;
        } else if (cardId ==  45) {
            return 17;
        } else if (cardId == 46 || cardId ==  47 || cardId ==  48) {
            return 18;
        } else if (cardId == 49 || cardId ==  50) {
            return 19;
        } else if (cardId == 51) {
            return 20;
        } else if (cardId == 52 || cardId == 53 || cardId == 54 || cardId == 55
                || cardId == 56 || cardId == 57 || cardId == 58 || cardId == 59) {
            return 21;
        } else if (cardId == 60 || cardId == 61 || cardId == 62 || cardId == 63
                || cardId == 64 || cardId == 65 || cardId == 66 || cardId == 67
                || cardId == 68) {
            return 22;
        } else if (cardId == 69 || cardId == 70 || cardId == 71 || cardId == 72) {
            return 23;
        } else if (cardId == 29) {
            return 24;
        }
        return -1;
    }

    public static Bitmap cardIdToBitmap(int cardId) {
        int bitmapId = cardIdToBitmapId(cardId);
        if (bitmapId == -1) return null;
        return ResourceMappings.get(bitmapId);
    }

    public void clearPossibleLocations() {
        possibleLocations.clear();
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

    private void drawCardContainer(CardContainer c, Canvas canvas) {
        ExtendedCard ec = CardDataBase.getCardById(c.card.getId());
        if (ec == null) return;


        Bitmap bitmap = cardIdToBitmap(ec.getId());
        if (bitmap == null) return;

        // todo add rotation later

        float left = (float)(c.pixelX + c.offsetX);
        float top = (float)(c.pixelY + c.offsetY);
        float right = (float)(c.pixelX + c.offsetX + c.size);
        float bottom = (float)(c.pixelY + c.offsetY + c.size);

        canvas.drawBitmap(bitmap, null, new RectF(left, top, right, bottom), null);

        if (bitmap == null) {

            Log.d("CARD", " " + ec.getId());


            canvas.drawRect(left, top, right, bottom, paintGrass);

            float offset = (float) c.size / 5;
            float half = (float) c.size / 2;

            if (ec.isCathedral()) {
                canvas.drawCircle(left + half, top + half, offset, paintSpecial);
            } else if (ec.isWappen()) {
                canvas.drawCircle(left + half + 10, top + half + 10, offset, cardPaint);
            }


            if (ec.getDown() == CardSide.CASTLE || ec.getBottomLeftCorner() == CardSide.CASTLE || ec.getBottomRightCorner() == CardSide.CASTLE) {
                canvas.drawRect(left + offset, top, right - half, top + offset, paintCastle);
            } else if (ec.getTop() == CardSide.STREET || ec.getTopLeftCorner() == CardSide.STREET || ec.getTopRightCorner() == CardSide.STREET) {
                canvas.drawRect(left + half, top, right - offset, top + offset, paintStreet);
            }

            if (ec.getRight() == CardSide.CASTLE || ec.getTopRightCorner() == CardSide.CASTLE || ec.getBottomRightCorner() == CardSide.CASTLE) {
                canvas.drawRect(right - offset, top + offset, right, top + half, paintCastle);
            } else if (ec.getRight() == CardSide.STREET || ec.getTopRightCorner() == CardSide.STREET || ec.getBottomRightCorner() == CardSide.STREET) {
                canvas.drawRect(right - offset, bottom - half, right, bottom - offset, paintStreet);
            }

            if (ec.getTop() == CardSide.CASTLE || ec.getTopLeftCorner() == CardSide.CASTLE || ec.getTopRightCorner() == CardSide.CASTLE) {
                canvas.drawRect(left + offset, bottom, right - half, bottom - offset, paintCastle);
            } else if (ec.getDown() == CardSide.STREET || ec.getBottomRightCorner() == CardSide.STREET || ec.getBottomLeftCorner() == CardSide.STREET) {
                canvas.drawRect(left + half, bottom, right - offset, bottom - offset, paintStreet);
                Log.d("CARD", "Street down");
            }

            if (ec.getLeft() == CardSide.CASTLE || ec.getBottomLeftCorner() == CardSide.CASTLE || ec.getTopLeftCorner() == CardSide.CASTLE) {
                canvas.drawRect(left, top + offset, left + offset, top + half, paintCastle);
            } else if (ec.getLeft() == CardSide.STREET || ec.getBottomLeftCorner() == CardSide.STREET || ec.getTopLeftCorner() == CardSide.STREET) {
                canvas.drawRect(left, bottom - half, left + offset, bottom - offset, paintStreet);
            }
        }
    }


    public interface ICardPlaced {
        void cardPlaced(int x, int y);
    }

    public interface IPeepPlaced {
        void peepPlaced();
    }
}
