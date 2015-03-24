package it.alessandraferrua.chartbarSample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

public class PowerSnrView extends View {
    private final String TAG = this.getClass().getCanonicalName();
    private int parentWidth;
    private int parentHeight;
    private int increment;
    private final int numerSatellites = getResources().getInteger(R.integer.numSatellites);
    private String[] prn;
    private HashMap<Integer, Paint> paints;
    private HashMap<Integer, Rect> rects;
    private int[] lefts;


    public PowerSnrView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "PowerSnrView");

    }

    private int[] getLeftsIncrement(int increment) {
        int[] lefts = new int[12];
        prn = new String[12];
        for (int i = 0; i < lefts.length; i++) {
            lefts[i] = (increment*2) * i;
            prn[i] = "";
        }
        return lefts;
    }


    private int calculateIncrementalBar(int dimen) {
        return dimen / numerSatellites / 2;
    }

    private void initPaint() {
        if (paints == null) {
            Log.i(TAG, "initPaint()");
            paints = new HashMap<Integer, Paint>();
            for (int i = 0; i < numerSatellites; i++) {
                paints.put(i, new Paint());
                paints.get(i).setTextSize(37);
            }
        }
    }

    private void initRect() {
        int base_top = parentHeight;
        int base_bottom = parentHeight - getResources().getInteger(R.integer.spaceOfNumberBottom);
        if (rects == null) {
            Log.i(TAG, "initRect()");
            rects = new HashMap<Integer, Rect>();
            for (int i = 0; i < numerSatellites; i++) {
                rects.put(i, new Rect(lefts[i] + this.increment, base_top, lefts[i] + this.increment + increment, base_bottom));
            }
        }
    }


    public void onChangeColor(int color, int rec) {
        Paint paint = paints.get(rec);
        if (paint != null)
            paint.setColor(color);
    }

    public void onRectSizeChanged(int y, int rec, String prn) {
        Canvas canvas = new Canvas();
        int x = (parentHeight) - y;
        this.prn[rec] = prn;
        Rect rect = rects.get(rec);
        if (rect != null)
            rect.top = x;

        this.invalidate();
        this.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < numerSatellites; i++) {
            canvas.drawRect(rects.get(i), paints.get(i));
            canvas.drawText(prn[i], lefts[i] + (increment-16)/5 + increment, parentHeight - 92, paints.get(i));
        }

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure()");
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);

        increment = calculateIncrementalBar(parentWidth-32);

        lefts = getLeftsIncrement(increment);

        Log.i(TAG, "incremental: " + increment);

        initPaint();
        initRect();

        this.setMeasuredDimension(parentWidth, parentWidth);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
