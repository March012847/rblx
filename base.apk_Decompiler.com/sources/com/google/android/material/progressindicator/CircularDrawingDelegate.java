package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Pair;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.math.MathUtils;
import com.google.android.material.progressindicator.DrawingDelegate;
import java.util.ArrayList;

final class CircularDrawingDelegate extends DrawingDelegate {
    private float adjustedRadius;
    private float adjustedWavelength;
    private final RectF arcBounds = new RectF();
    private float cachedAmplitude;
    private float cachedRadius;
    private int cachedWavelength;
    private float displayedAmplitude;
    private float displayedCornerRadius;
    private float displayedTrackThickness;
    private boolean drawingDeterminateIndicator;
    private final Pair endPoints = new Pair(new DrawingDelegate.PathPoint(), new DrawingDelegate.PathPoint());
    private float totalTrackLengthFraction;

    /* access modifiers changed from: package-private */
    public void drawStopIndicator(Canvas canvas, Paint paint, int i, int i2) {
    }

    CircularDrawingDelegate(CircularProgressIndicatorSpec circularProgressIndicatorSpec) {
        super(circularProgressIndicatorSpec);
    }

    /* access modifiers changed from: package-private */
    public int getPreferredWidth() {
        return getSize();
    }

    /* access modifiers changed from: package-private */
    public int getPreferredHeight() {
        return getSize();
    }

    /* access modifiers changed from: package-private */
    public void adjustCanvas(Canvas canvas, Rect rect, float f, boolean z, boolean z2) {
        float width = ((float) rect.width()) / ((float) getPreferredWidth());
        float height = ((float) rect.height()) / ((float) getPreferredHeight());
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        float f2 = (((float) ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorSize) / 2.0f) + ((float) ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorInset);
        canvas.translate((f2 * width) + ((float) rect.left), (f2 * height) + ((float) rect.top));
        canvas.rotate(-90.0f);
        canvas.scale(width, height);
        if (((CircularProgressIndicatorSpec) this.spec).indicatorDirection != 0) {
            canvas.scale(1.0f, -1.0f);
        }
        float f3 = -f2;
        canvas.clipRect(f3, f3, f2, f2);
        BaseProgressIndicatorSpec baseProgressIndicatorSpec2 = this.spec;
        this.displayedTrackThickness = ((float) ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec2).trackThickness) * f;
        this.displayedCornerRadius = ((float) Math.min(((CircularProgressIndicatorSpec) baseProgressIndicatorSpec2).trackThickness / 2, ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec2).getTrackCornerRadiusInPx())) * f;
        BaseProgressIndicatorSpec baseProgressIndicatorSpec3 = this.spec;
        this.displayedAmplitude = ((float) ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec3).waveAmplitude) * f;
        float f4 = ((float) (((CircularProgressIndicatorSpec) baseProgressIndicatorSpec3).indicatorSize - ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec3).trackThickness)) / 2.0f;
        this.adjustedRadius = f4;
        if (z || z2) {
            float f5 = ((1.0f - f) * ((float) ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec3).trackThickness)) / 2.0f;
            if ((z && ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec3).showAnimationBehavior == 2) || (z2 && ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec3).hideAnimationBehavior == 1)) {
                this.adjustedRadius = f4 + f5;
            } else if ((z && ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec3).showAnimationBehavior == 1) || (z2 && ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec3).hideAnimationBehavior == 2)) {
                this.adjustedRadius = f4 - f5;
            }
        }
        if (!z2 || ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec3).hideAnimationBehavior != 3) {
            this.totalTrackLengthFraction = 1.0f;
        } else {
            this.totalTrackLengthFraction = f;
        }
    }

    /* access modifiers changed from: package-private */
    public void fillIndicator(Canvas canvas, Paint paint, DrawingDelegate.ActiveIndicator activeIndicator, int i) {
        DrawingDelegate.ActiveIndicator activeIndicator2 = activeIndicator;
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(activeIndicator2.color, i);
        canvas.save();
        canvas.rotate(activeIndicator2.rotationDegree);
        this.drawingDeterminateIndicator = activeIndicator2.isDeterminate;
        float f = activeIndicator2.startFraction;
        float f2 = activeIndicator2.endFraction;
        int i2 = activeIndicator2.gapSize;
        Canvas canvas2 = canvas;
        Paint paint2 = paint;
        drawArc(canvas2, paint2, f, f2, compositeARGBWithAlpha, i2, i2, activeIndicator2.amplitudeFraction, activeIndicator2.phaseFraction, true);
        canvas.restore();
    }

    /* access modifiers changed from: package-private */
    public void fillTrack(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3) {
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(i, i2);
        this.drawingDeterminateIndicator = false;
        drawArc(canvas, paint, f, f2, compositeARGBWithAlpha, i3, i3, 0.0f, 0.0f, false);
    }

    private void drawArc(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3, float f3, float f4, boolean z) {
        float f5 = f2 >= f ? f2 - f : (f2 + 1.0f) - f;
        float f6 = f % 1.0f;
        if (f6 < 0.0f) {
            f6 += 1.0f;
        }
        if (this.totalTrackLengthFraction < 1.0f) {
            float f7 = f6 + f5;
            if (f7 > 1.0f) {
                Canvas canvas2 = canvas;
                Paint paint2 = paint;
                int i4 = i;
                float f8 = f3;
                float f9 = f4;
                boolean z2 = z;
                drawArc(canvas2, paint2, f6, 1.0f, i4, i2, 0, f8, f9, z2);
                drawArc(canvas2, paint2, 1.0f, f7, i4, 0, i3, f8, f9, z2);
                return;
            }
        }
        float degrees = (float) Math.toDegrees((double) (this.displayedCornerRadius / this.adjustedRadius));
        float f10 = f5 - 0.99f;
        if (f10 >= 0.0f) {
            float f11 = ((f10 * degrees) / 180.0f) / 0.01f;
            f5 += f11;
            if (!z) {
                f6 -= f11 / 2.0f;
            }
        }
        float lerp = MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, f6);
        float lerp2 = MathUtils.lerp(0.0f, this.totalTrackLengthFraction, f5);
        float degrees2 = (float) Math.toDegrees((double) (((float) i2) / this.adjustedRadius));
        float degrees3 = ((lerp2 * 360.0f) - degrees2) - ((float) Math.toDegrees((double) (((float) i3) / this.adjustedRadius)));
        float f12 = (lerp * 360.0f) + degrees2;
        if (degrees3 > 0.0f) {
            boolean z3 = ((CircularProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator) && z && f3 > 0.0f;
            paint.setAntiAlias(true);
            paint.setColor(i);
            paint.setStrokeWidth(this.displayedTrackThickness);
            float f13 = this.displayedCornerRadius * 2.0f;
            float f14 = degrees * 2.0f;
            if (degrees3 < f14) {
                float f15 = degrees3 / f14;
                float f16 = f12 + (degrees * f15);
                DrawingDelegate.PathPoint pathPoint = new DrawingDelegate.PathPoint();
                if (!z3) {
                    pathPoint.rotate(f16 + 90.0f);
                    pathPoint.moveAcross(-this.adjustedRadius);
                } else {
                    float length = ((f16 / 360.0f) * this.activePathMeasure.getLength()) / 2.0f;
                    float f17 = this.displayedAmplitude * f3;
                    float f18 = this.adjustedRadius;
                    if (!(f18 == this.cachedRadius && f17 == this.cachedAmplitude)) {
                        this.cachedAmplitude = f17;
                        this.cachedRadius = f18;
                        invalidateCachedPaths();
                    }
                    this.activePathMeasure.getPosTan(length, pathPoint.posVec, pathPoint.tanVec);
                }
                paint.setStyle(Paint.Style.FILL);
                drawRoundedBlock(canvas, paint, pathPoint, f13, this.displayedTrackThickness, f15);
                return;
            }
            float f19 = f13;
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(((CircularProgressIndicatorSpec) this.spec).useStrokeCap() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
            float f20 = f12 + degrees;
            float f21 = degrees3 - f14;
            ((DrawingDelegate.PathPoint) this.endPoints.first).reset();
            ((DrawingDelegate.PathPoint) this.endPoints.second).reset();
            if (!z3) {
                ((DrawingDelegate.PathPoint) this.endPoints.first).rotate(f20 + 90.0f);
                ((DrawingDelegate.PathPoint) this.endPoints.first).moveAcross(-this.adjustedRadius);
                ((DrawingDelegate.PathPoint) this.endPoints.second).rotate(f20 + f21 + 90.0f);
                ((DrawingDelegate.PathPoint) this.endPoints.second).moveAcross(-this.adjustedRadius);
                RectF rectF = this.arcBounds;
                float f22 = this.adjustedRadius;
                rectF.set(-f22, -f22, f22, f22);
                canvas.drawArc(this.arcBounds, f20, f21, false, paint);
            } else {
                calculateDisplayedPath(this.activePathMeasure, this.displayedActivePath, this.endPoints, f20 / 360.0f, f21 / 360.0f, f3, f4);
                canvas.drawPath(this.displayedActivePath, paint);
            }
            if (!((CircularProgressIndicatorSpec) this.spec).useStrokeCap() && this.displayedCornerRadius > 0.0f) {
                paint.setStyle(Paint.Style.FILL);
                Canvas canvas3 = canvas;
                Paint paint3 = paint;
                float f23 = f19;
                drawRoundedBlock(canvas3, paint3, (DrawingDelegate.PathPoint) this.endPoints.first, f23, this.displayedTrackThickness);
                drawRoundedBlock(canvas3, paint3, (DrawingDelegate.PathPoint) this.endPoints.second, f23, this.displayedTrackThickness);
            }
        }
    }

    private int getSize() {
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        return ((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorSize + (((CircularProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorInset * 2);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate.PathPoint pathPoint, float f, float f2) {
        drawRoundedBlock(canvas, paint, pathPoint, f, f2, 1.0f);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate.PathPoint pathPoint, float f, float f2, float f3) {
        float min = Math.min(f2, this.displayedTrackThickness);
        float f4 = f / 2.0f;
        float min2 = Math.min(f4, (this.displayedCornerRadius * min) / this.displayedTrackThickness);
        RectF rectF = new RectF((-f) / 2.0f, (-min) / 2.0f, f4, min / 2.0f);
        canvas.save();
        float[] fArr = pathPoint.posVec;
        canvas.translate(fArr[0], fArr[1]);
        canvas.rotate(vectorToCanvasRotation(pathPoint.tanVec));
        canvas.scale(f3, f3);
        canvas.drawRoundRect(rectF, min2, min2, paint);
        canvas.restore();
    }

    /* access modifiers changed from: package-private */
    public void invalidateCachedPaths() {
        this.cachedActivePath.rewind();
        this.cachedActivePath.moveTo(1.0f, 0.0f);
        for (int i = 0; i < 2; i++) {
            this.cachedActivePath.cubicTo(1.0f, 0.5522848f, 0.5522848f, 1.0f, 0.0f, 1.0f);
            this.cachedActivePath.cubicTo(-0.5522848f, 1.0f, -1.0f, 0.5522848f, -1.0f, 0.0f);
            this.cachedActivePath.cubicTo(-1.0f, -0.5522848f, -0.5522848f, -1.0f, 0.0f, -1.0f);
            this.cachedActivePath.cubicTo(0.5522848f, -1.0f, 1.0f, -0.5522848f, 1.0f, 0.0f);
        }
        this.transform.reset();
        Matrix matrix = this.transform;
        float f = this.adjustedRadius;
        matrix.setScale(f, f);
        this.cachedActivePath.transform(this.transform);
        if (((CircularProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            this.activePathMeasure.setPath(this.cachedActivePath, false);
            createWavyPath(this.activePathMeasure, this.cachedActivePath, this.cachedAmplitude);
        }
        this.activePathMeasure.setPath(this.cachedActivePath, false);
    }

    private void createWavyPath(PathMeasure pathMeasure, Path path, float f) {
        path.rewind();
        float length = pathMeasure.getLength();
        int max = Math.max(3, (int) ((length / ((float) (this.drawingDeterminateIndicator ? ((CircularProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((CircularProgressIndicatorSpec) this.spec).wavelengthIndeterminate))) / 2.0f)) * 2;
        this.adjustedWavelength = length / ((float) max);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < max; i++) {
            DrawingDelegate.PathPoint pathPoint = new DrawingDelegate.PathPoint();
            float f2 = (float) i;
            pathMeasure.getPosTan(this.adjustedWavelength * f2, pathPoint.posVec, pathPoint.tanVec);
            DrawingDelegate.PathPoint pathPoint2 = new DrawingDelegate.PathPoint();
            float f3 = this.adjustedWavelength;
            pathMeasure.getPosTan((f2 * f3) + (f3 / 2.0f), pathPoint2.posVec, pathPoint2.tanVec);
            arrayList.add(pathPoint);
            pathPoint2.moveAcross(f * 2.0f);
            arrayList.add(pathPoint2);
        }
        arrayList.add((DrawingDelegate.PathPoint) arrayList.get(0));
        DrawingDelegate.PathPoint pathPoint3 = (DrawingDelegate.PathPoint) arrayList.get(0);
        float[] fArr = pathPoint3.posVec;
        int i2 = 1;
        path.moveTo(fArr[0], fArr[1]);
        while (i2 < arrayList.size()) {
            DrawingDelegate.PathPoint pathPoint4 = (DrawingDelegate.PathPoint) arrayList.get(i2);
            appendCubicPerHalfCycle(path, pathPoint3, pathPoint4);
            i2++;
            pathPoint3 = pathPoint4;
        }
    }

    private void appendCubicPerHalfCycle(Path path, DrawingDelegate.PathPoint pathPoint, DrawingDelegate.PathPoint pathPoint2) {
        float f = (this.adjustedWavelength / 2.0f) * 0.48f;
        DrawingDelegate.PathPoint pathPoint3 = new DrawingDelegate.PathPoint(this, pathPoint);
        DrawingDelegate.PathPoint pathPoint4 = new DrawingDelegate.PathPoint(this, pathPoint2);
        pathPoint3.moveAlong(f);
        pathPoint4.moveAlong(-f);
        float[] fArr = pathPoint3.posVec;
        float f2 = fArr[0];
        float f3 = fArr[1];
        float[] fArr2 = pathPoint4.posVec;
        float f4 = fArr2[0];
        float f5 = fArr2[1];
        float[] fArr3 = pathPoint2.posVec;
        path.cubicTo(f2, f3, f4, f5, fArr3[0], fArr3[1]);
    }

    private void calculateDisplayedPath(PathMeasure pathMeasure, Path path, Pair pair, float f, float f2, float f3, float f4) {
        float f5 = this.displayedAmplitude * f3;
        int i = this.drawingDeterminateIndicator ? ((CircularProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((CircularProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
        float f6 = this.adjustedRadius;
        if (f6 != this.cachedRadius || (pathMeasure == this.activePathMeasure && !(f5 == this.cachedAmplitude && i == this.cachedWavelength))) {
            this.cachedAmplitude = f5;
            this.cachedWavelength = i;
            this.cachedRadius = f6;
            invalidateCachedPaths();
        }
        path.rewind();
        float f7 = 0.0f;
        float clamp = androidx.core.math.MathUtils.clamp(f2, 0.0f, 1.0f);
        if (((CircularProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            float f8 = f4 / ((float) ((((double) this.adjustedRadius) * 6.283185307179586d) / ((double) this.adjustedWavelength)));
            f += f8;
            f7 = 0.0f - (f8 * 360.0f);
        }
        float f9 = f % 1.0f;
        float length = (pathMeasure.getLength() * f9) / 2.0f;
        float length2 = ((f9 + clamp) * pathMeasure.getLength()) / 2.0f;
        pathMeasure.getSegment(length, length2, path, true);
        DrawingDelegate.PathPoint pathPoint = (DrawingDelegate.PathPoint) pair.first;
        pathPoint.reset();
        pathMeasure.getPosTan(length, pathPoint.posVec, pathPoint.tanVec);
        DrawingDelegate.PathPoint pathPoint2 = (DrawingDelegate.PathPoint) pair.second;
        pathPoint2.reset();
        pathMeasure.getPosTan(length2, pathPoint2.posVec, pathPoint2.tanVec);
        this.transform.reset();
        this.transform.setRotate(f7);
        pathPoint.rotate(f7);
        pathPoint2.rotate(f7);
        path.transform(this.transform);
    }
}
