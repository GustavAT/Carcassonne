package distudios.at.carcassonne.gui.field;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PlayfieldView extends View {

    public static int CARDS_COUNT_HORIZONTAL = 6;
    private Paint rasterPaint;

    private double offsetX = 0;
    private double offsetY = 0;
    private double scale = 1;

    private List<CardContainer> raster = new ArrayList<>();

    public PlayfieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        rasterPaint = new Paint();
        rasterPaint.setStyle(Paint.Style.STROKE);
        rasterPaint.setStrokeWidth(3);
        rasterPaint.setColor(Color.BLUE);
        rasterPaint.setTextSize(25);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        super.onTouchEvent(e);

        double x = e.getX();
        double y = e.getY();

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("Carcassonne", "Touch - X: " + x + " Y: " + y);
            for (CardContainer c :
                    raster) {
                if (c.raster.contains((int)x, (int)y)) {
                    Toast.makeText(getContext(), "Clicked - X: " + c.x + " Y: " + c.y, Toast.LENGTH_SHORT).show();
                }
            }
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("Carcassonne", "Canvas draw");
        int height = getHeight() - getPaddingBottom() - getPaddingTop();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();

        int centerX = width / 2;
        int centerY = height / 2;

        double cardSize = width / CARDS_COUNT_HORIZONTAL;

        for (CardContainer c: raster) {
            canvas.drawRect(c.raster, rasterPaint);
            canvas.drawText("X: " + c.x + ", Y " + c.y, c.raster.left, c.raster.bottom, rasterPaint);

        }

    }

    public void initField() {
        int height = getHeight() - getPaddingBottom() - getPaddingTop();
        int width = getWidth() - getPaddingLeft() - getPaddingRight();

        int centerX = width / 2;
        int centerY = height / 2;
        double cardSize = width / CARDS_COUNT_HORIZONTAL;

        raster.clear();
        for (int i = 0; i < CARDS_COUNT_HORIZONTAL; i++) {
            for (int j = 0; j < height / cardSize; j++) {
                CardContainer c = new CardContainer();
                c.x = i;
                c.y = j;
                c.raster = new Rect(i * (int)cardSize, j * (int) cardSize, i * (int)cardSize + (int) cardSize, j * (int) cardSize + (int) cardSize);
                raster.add(c);
            }
        }

        invalidate();
    }

    public class CardContainer {
        public Rect raster;
        public int x;
        public int y;
    }
}
