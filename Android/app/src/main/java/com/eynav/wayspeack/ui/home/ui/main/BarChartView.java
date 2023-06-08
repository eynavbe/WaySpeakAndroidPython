package com.eynav.wayspeack.ui.home.ui.main;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.eynav.wayspeack.R;

public class BarChartView extends View {
    private Paint barPaint;
    private Paint textPaint;
    static int[] dataPoints =  new int[]{50, 80, 120, 90, 110,120,50, 80, 120, 90, 110,120};;
    static String[] xAxisLabels= new String[]{"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6","Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};

    public BarChartView(Context context,int[] dataPoints, String[] xAxisLabels) {
        super(context);
        System.out.println("BarChartView");
        this.dataPoints = dataPoints;
        this.xAxisLabels = xAxisLabels;
        init();
    }
    public BarChartView(Context context) {
        super(context);
        init();
        // Sample data points (replace with your own data)
//        dataPoints = new int[]{50, 80, 120, 90, 110,120,50, 80, 120, 90, 110,120};
//
//        // Sample x-axis labels (replace with your own labels)
//        xAxisLabels = new String[]{"Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6","Label 1", "Label 2", "Label 3", "Label 4", "Label 5", "Label 6"};

    }

    public BarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        System.out.println("init");
        barPaint = new Paint();
        int blueColor = ContextCompat.getColor(getContext(), R.color.blue);

        barPaint.setColor(blueColor);
        barPaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20f);

         }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Measure the size of the BarChartView and its child views
        int desiredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int desiredHeight = MeasureSpec.getSize(heightMeasureSpec);

        // Measure the child views, if any
//        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // Calculate the desired dimensions based on the content and constraints
        int width = Math.max(getSuggestedMinimumWidth(), desiredWidth);
        int height = Math.max(getSuggestedMinimumHeight(), desiredHeight);

        // Use MeasureSpec.makeMeasureSpec(desiredWidth, MeasureSpec.EXACTLY) and MeasureSpec.makeMeasureSpec(desiredHeight, MeasureSpec.EXACTLY) to set the measured dimensions
        int widthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
        int heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        // Call setMeasuredDimension(widthSpec, heightSpec) to store the measured dimensions
        setMeasuredDimension(widthSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // Position and layout the child views within the BarChartView
//        for (int i = 0; i < getChildCount(); i++) {
//            View child = getChildAt(i);
//
//            // Calculate the coordinates and sizes of each child view based on the available space and desired layout
//            int childLeft = getPaddingLeft();
//            int childTop = getPaddingTop();
//            int childRight = right - left - getPaddingRight();
//            int childBottom = bottom - top - getPaddingBottom();
//
//            // Use child.layout(left, top, right, bottom) to position each child view
//            child.layout(childLeft, childTop, childRight, childBottom);
//        }
    }

    public void setData(int[] dataPoints, String[] xAxisLabels) {
        this.dataPoints = dataPoints;
        this.xAxisLabels = xAxisLabels;

        // Call requestLayout() to trigger a layout pass
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("onDraw");
        float width = getWidth();
        float height = getHeight();
        System.out.println("ssssss");
        System.out.println(height);
        System.out.println(width);
        // Calculate the position and scale of the chart within the view
        float chartWidth = width - getPaddingLeft() - getPaddingRight();
        float chartHeight = height - getPaddingTop() - getPaddingBottom();
        System.out.println(chartWidth);
        System.out.println(chartHeight);
        float barWidth = chartWidth / dataPoints.length;
        float maxValue = getMaxValue();
        float yScale = chartHeight / maxValue;
        float maxTop = 0;
        // Draw the bars
        for (int i = 0; i < dataPoints.length; i++) {
            float left = 40+( i * barWidth + getPaddingLeft());
            float top = height - dataPoints[i] * yScale - getPaddingBottom();
            float right = left + barWidth;
            float bottom = height - getPaddingBottom();
            if (maxTop < top){
                maxTop = top;
            }
            System.out.println("left "+left);
            System.out.println("top "+top);
            System.out.println("right "+right);
            System.out.println("bottom "+bottom);
            canvas.drawRect(left, top, right, bottom, barPaint);

            Paint framePaint = new Paint();
            framePaint.setColor(Color.BLACK);
            framePaint.setStyle(Paint.Style.STROKE);
            framePaint.setStrokeWidth(2f);

            canvas.drawRect(left, top, right, bottom,framePaint);
        }

//        // Draw the x-axis labels
//        for (int i = 0; i < dataPoints.length; i++) {
//            float x = i * barWidth + getPaddingLeft() + barWidth / 2;
//            float y = height - getPaddingBottom() + textPaint.getTextSize() + 10;
//            canvas.drawText(xAxisLabels[i], x, 0, textPaint);
//        }

        // Draw the x-axis labels
        for (int i = 0; i < dataPoints.length; i++) {

//            float x = 40;
            float x = 40+( i * barWidth + getPaddingLeft());

            float y = (height - dataPoints[i] * yScale - getPaddingBottom())-30;

//            float y = 600+100;
            canvas.drawText(xAxisLabels[i], x, y, textPaint);
        }
        System.out.println( "y-axis");
        System.out.println("maxValue "+maxValue);
        // Draw the y-axis numbers
        int interval = 1; // Interval between y-axis numbers
        int maxIntervalCount = (int) Math.ceil(maxValue / interval);

        for (int i = 0; i <= maxIntervalCount; i++) {
            int yValue = i * interval;
            float x = getPaddingLeft() - 10;
            float y = height - yValue * yScale - getPaddingBottom() - 5;

            canvas.drawText(String.valueOf(yValue), x, y, textPaint);
        }
        // Draw the y-axis numbers
//        float y = 0;
////        int i =0;
////        while ( y <= maxTop+20){
//            for (int i = 0; i < chartHeight/20; i++) {
//
//                float x = getPaddingLeft();
//                 y = (height -(i *20))* yScale - getPaddingBottom();
//
//                System.out.println(x);
//            System.out.println(y);
//            canvas.drawText(String.valueOf(Math.round(i * maxValue / 5)), x, y, textPaint);
//            i += 1;
//        }
    }

    private int getMaxValue() {
        int max = 0;
        for (int value : dataPoints) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
