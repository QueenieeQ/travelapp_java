package com.example.virtualtravelapp.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CustomChartView extends View {

    private Paint paint;
    private RectF rectF;
    private float[] data = {40f, 30f, 20f, 10f}; // Default data
    private int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW}; // Default colors
    private String[] labels = {"A", "B", "C", "D"}; // Default labels

    public CustomChartView(Context context) {
        super(context);
        init();
    }

    public CustomChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(30f); // Set default text size
        paint.setTextAlign(Paint.Align.CENTER); // Center-align text
        rectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Calculate total value
        float total = 0;
        for (float value : data) {
            total += value;
        }

        // Draw pie chart
        float startAngle = 0f;
        int width = getWidth();
        int height = getHeight();
        float padding = 100f;
        rectF.set(padding, padding, width - padding, height - padding);

        for (int i = 0; i < data.length; i++) {
            float sweepAngle = (data[i] / total) * 360;
            paint.setColor(colors[i]);
            canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
            startAngle += sweepAngle;
        }

        // Draw labels
        drawLabels(canvas, total);
    }

    private void drawLabels(Canvas canvas, float total) {
        float startAngle = 0f;
        int width = getWidth();
        int height = getHeight();
        float radius = Math.min(width, height) / 2f - 100f; // Radius of the chart area
        float labelRadius = radius * 0.7f; // Radius for labels

        for (int i = 0; i < data.length; i++) {
            float sweepAngle = (data[i] / total) * 360; // Angle of the slice
            float midAngle = startAngle + sweepAngle / 2; // Mid-point angle of the slice
            float x = width / 2 + labelRadius * (float) Math.cos(Math.toRadians(midAngle));
            float y = height / 2 - labelRadius * (float) Math.sin(Math.toRadians(midAngle));

            // Adjust y-coordinate to center the text vertically
            y = y - (paint.descent() + paint.ascent()) / 2;

            // Draw label with a background color matching the slice
            paint.setColor(Color.BLACK); // Text color
            canvas.drawText(labels[i], x, y, paint);

            // Optional: Draw outline to make the text more readable
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            canvas.drawText(labels[i], x, y, paint);

            startAngle += sweepAngle;
        }
    }

    public void setData(float[] data, int[] colors, String[] labels) {
        this.data = data;
        this.colors = colors;
        this.labels = labels;
        invalidate(); // Redraw the view with the new data
    }
}
