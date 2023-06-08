package com.eynav.wayspeack.ui.home.ui.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GraphView extends View {
    private Paint linePaint;
    private float[] dataPoints;

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.RED);
        linePaint.setStrokeWidth(3f);

        // Sample data points (replace with your own data)
        dataPoints = new float[]{0f, 50f, 100f, 80f, 120f, 90f};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        // Calculate the position and scale of the graph within the view
        float graphWidth = width - getPaddingLeft() - getPaddingRight();
        float graphHeight = height - getPaddingTop() - getPaddingBottom();

        float xStep = graphWidth / (dataPoints.length - 1);
        float yScale = graphHeight / getMaxValue();

        // Draw the graph line
        for (int i = 0; i < dataPoints.length - 1; i++) {
            float x1 = i * xStep + getPaddingLeft();
            float y1 = height - dataPoints[i] * yScale - getPaddingBottom();

            float x2 = (i + 1) * xStep + getPaddingLeft();
            float y2 = height - dataPoints[i + 1] * yScale - getPaddingBottom();

            canvas.drawLine(x1, y1, x2, y2, linePaint);
        }
    }

    private float getMaxValue() {
        float max = 0f;
        for (float value : dataPoints) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
