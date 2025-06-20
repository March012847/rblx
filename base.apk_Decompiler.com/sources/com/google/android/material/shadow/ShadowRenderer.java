package com.google.android.material.shadow;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader;
import androidx.core.graphics.ColorUtils;

public class ShadowRenderer {
    private static final int[] cornerColors = new int[4];
    private static final float[] cornerPositions = {0.0f, 0.0f, 0.5f, 1.0f};
    private static final int[] edgeColors = new int[3];
    private static final float[] edgePositions = {0.0f, 0.5f, 1.0f};
    private final Paint cornerShadowPaint;
    private final Paint edgeShadowPaint;
    private final Path scratch;
    private int shadowEndColor;
    private int shadowMiddleColor;
    private final Paint shadowPaint;
    private int shadowStartColor;
    private final Paint transparentPaint;

    public ShadowRenderer() {
        this(-16777216);
    }

    public ShadowRenderer(int i) {
        this.scratch = new Path();
        Paint paint = new Paint();
        this.transparentPaint = paint;
        this.shadowPaint = new Paint();
        setShadowColor(i);
        paint.setColor(0);
        Paint paint2 = new Paint(4);
        this.cornerShadowPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.edgeShadowPaint = new Paint(paint2);
    }

    public void setShadowColor(int i) {
        this.shadowStartColor = ColorUtils.setAlphaComponent(i, 68);
        this.shadowMiddleColor = ColorUtils.setAlphaComponent(i, 20);
        this.shadowEndColor = ColorUtils.setAlphaComponent(i, 0);
        this.shadowPaint.setColor(this.shadowStartColor);
    }

    public void drawEdgeShadow(Canvas canvas, Matrix matrix, RectF rectF, int i) {
        rectF.bottom += (float) i;
        rectF.offset(0.0f, (float) (-i));
        int[] iArr = edgeColors;
        iArr[0] = this.shadowEndColor;
        iArr[1] = this.shadowMiddleColor;
        iArr[2] = this.shadowStartColor;
        Paint paint = this.edgeShadowPaint;
        float f = rectF.left;
        paint.setShader(new LinearGradient(f, rectF.top, f, rectF.bottom, iArr, edgePositions, Shader.TileMode.CLAMP));
        canvas.save();
        canvas.concat(matrix);
        canvas.drawRect(rectF, this.edgeShadowPaint);
        canvas.restore();
    }

    public void drawCornerShadow(Canvas canvas, Matrix matrix, RectF rectF, int i, float f, float f2) {
        float f3;
        Canvas canvas2 = canvas;
        RectF rectF2 = rectF;
        int i2 = i;
        float f4 = f2;
        boolean z = f4 < 0.0f;
        Path path = this.scratch;
        if (z) {
            int[] iArr = cornerColors;
            iArr[0] = 0;
            iArr[1] = this.shadowEndColor;
            iArr[2] = this.shadowMiddleColor;
            iArr[3] = this.shadowStartColor;
            f3 = f;
        } else {
            path.rewind();
            path.moveTo(rectF2.centerX(), rectF2.centerY());
            f3 = f;
            path.arcTo(rectF2, f3, f4);
            path.close();
            float f5 = (float) (-i2);
            rectF2.inset(f5, f5);
            int[] iArr2 = cornerColors;
            iArr2[0] = 0;
            iArr2[1] = this.shadowStartColor;
            iArr2[2] = this.shadowMiddleColor;
            iArr2[3] = this.shadowEndColor;
        }
        float width = rectF2.width() / 2.0f;
        if (width > 0.0f) {
            float f6 = 1.0f - (((float) i2) / width);
            float[] fArr = cornerPositions;
            fArr[1] = f6;
            fArr[2] = ((1.0f - f6) / 2.0f) + f6;
            this.cornerShadowPaint.setShader(new RadialGradient(rectF2.centerX(), rectF2.centerY(), width, cornerColors, fArr, Shader.TileMode.CLAMP));
            canvas2.save();
            canvas.concat(matrix);
            canvas2.scale(1.0f, rectF2.height() / rectF2.width());
            if (!z) {
                canvas2.clipPath(path, Region.Op.DIFFERENCE);
                canvas2.drawPath(path, this.transparentPaint);
            }
            canvas2.drawArc(rectF2, f3, f2, true, this.cornerShadowPaint);
            canvas.restore();
        }
    }

    public Paint getShadowPaint() {
        return this.shadowPaint;
    }
}
