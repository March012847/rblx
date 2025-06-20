package com.google.android.material.progressindicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Pair;
import androidx.core.math.MathUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.DrawingDelegate;

final class LinearDrawingDelegate extends DrawingDelegate {
    private float adjustedWavelength;
    private int cachedWavelength;
    private float displayedAmplitude;
    private float displayedCornerRadius;
    private float displayedInnerCornerRadius;
    private float displayedTrackThickness;
    private boolean drawingDeterminateIndicator;
    Pair endPoints = new Pair(new DrawingDelegate.PathPoint(), new DrawingDelegate.PathPoint());
    private float totalTrackLengthFraction;
    private float trackLength = 300.0f;

    /* access modifiers changed from: package-private */
    public int getPreferredWidth() {
        return -1;
    }

    LinearDrawingDelegate(LinearProgressIndicatorSpec linearProgressIndicatorSpec) {
        super(linearProgressIndicatorSpec);
    }

    /* access modifiers changed from: package-private */
    public int getPreferredHeight() {
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        return ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness + (((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).waveAmplitude * 2);
    }

    /* access modifiers changed from: package-private */
    public void adjustCanvas(Canvas canvas, Rect rect, float f, boolean z, boolean z2) {
        if (this.trackLength != ((float) rect.width())) {
            this.trackLength = (float) rect.width();
            invalidateCachedPaths();
        }
        float preferredHeight = (float) getPreferredHeight();
        canvas.translate(((float) rect.left) + (((float) rect.width()) / 2.0f), ((float) rect.top) + (((float) rect.height()) / 2.0f) + Math.max(0.0f, (((float) rect.height()) - preferredHeight) / 2.0f));
        if (((LinearProgressIndicatorSpec) this.spec).drawHorizontallyInverse) {
            canvas.scale(-1.0f, 1.0f);
        }
        float f2 = this.trackLength / 2.0f;
        float f3 = preferredHeight / 2.0f;
        canvas.clipRect(-f2, -f3, f2, f3);
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        this.displayedTrackThickness = ((float) ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness) * f;
        this.displayedCornerRadius = ((float) Math.min(((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackThickness / 2, ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).getTrackCornerRadiusInPx())) * f;
        BaseProgressIndicatorSpec baseProgressIndicatorSpec2 = this.spec;
        this.displayedAmplitude = ((float) ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec2).waveAmplitude) * f;
        this.displayedInnerCornerRadius = Math.min(((float) ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec2).trackThickness) / 2.0f, (float) ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec2).getTrackInnerCornerRadiusInPx()) * f;
        if (z || z2) {
            if ((z && ((LinearProgressIndicatorSpec) this.spec).showAnimationBehavior == 2) || (z2 && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior == 1)) {
                canvas.scale(1.0f, -1.0f);
            }
            if (z || (z2 && ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior != 3)) {
                canvas.translate(0.0f, (((float) ((LinearProgressIndicatorSpec) this.spec).trackThickness) * (1.0f - f)) / 2.0f);
            }
        }
        if (!z2 || ((LinearProgressIndicatorSpec) this.spec).hideAnimationBehavior != 3) {
            this.totalTrackLengthFraction = 1.0f;
        } else {
            this.totalTrackLengthFraction = f;
        }
    }

    /* access modifiers changed from: package-private */
    public void fillIndicator(Canvas canvas, Paint paint, DrawingDelegate.ActiveIndicator activeIndicator, int i) {
        DrawingDelegate.ActiveIndicator activeIndicator2 = activeIndicator;
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(activeIndicator2.color, i);
        this.drawingDeterminateIndicator = activeIndicator2.isDeterminate;
        float f = activeIndicator2.startFraction;
        float f2 = activeIndicator2.endFraction;
        int i2 = activeIndicator2.gapSize;
        Canvas canvas2 = canvas;
        Paint paint2 = paint;
        drawLine(canvas2, paint2, f, f2, compositeARGBWithAlpha, i2, i2, activeIndicator2.amplitudeFraction, activeIndicator2.phaseFraction, true);
    }

    /* access modifiers changed from: package-private */
    public void fillTrack(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3) {
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(i, i2);
        this.drawingDeterminateIndicator = false;
        drawLine(canvas, paint, f, f2, compositeARGBWithAlpha, i3, i3, 0.0f, 0.0f, false);
    }

    private void drawLine(Canvas canvas, Paint paint, float f, float f2, int i, int i2, int i3, float f3, float f4, boolean z) {
        float f5;
        float f6;
        Paint paint2;
        Canvas canvas2;
        Paint paint3 = paint;
        float clamp = MathUtils.clamp(f, 0.0f, 1.0f);
        float clamp2 = MathUtils.clamp(f2, 0.0f, 1.0f);
        float lerp = com.google.android.material.math.MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, clamp);
        float lerp2 = com.google.android.material.math.MathUtils.lerp(1.0f - this.totalTrackLengthFraction, 1.0f, clamp2);
        float f7 = this.trackLength;
        int clamp3 = (int) ((lerp * f7) + ((float) ((int) ((((float) i2) * MathUtils.clamp(lerp, 0.0f, 0.01f)) / 0.01f))));
        int clamp4 = (int) ((lerp2 * f7) - ((float) ((int) ((((float) i3) * (1.0f - MathUtils.clamp(lerp2, 0.99f, 1.0f))) / 0.01f))));
        float f8 = this.displayedCornerRadius;
        float f9 = this.displayedInnerCornerRadius;
        if (f8 != f9) {
            float max = Math.max(f8, f9);
            float f10 = this.trackLength;
            float f11 = max / f10;
            float lerp3 = com.google.android.material.math.MathUtils.lerp(this.displayedCornerRadius, this.displayedInnerCornerRadius, MathUtils.clamp(((float) clamp3) / f10, 0.0f, f11) / f11);
            float f12 = this.displayedCornerRadius;
            float f13 = this.displayedInnerCornerRadius;
            float f14 = this.trackLength;
            f5 = com.google.android.material.math.MathUtils.lerp(f12, f13, MathUtils.clamp((f14 - ((float) clamp4)) / f14, 0.0f, f11) / f11);
            f6 = lerp3;
        } else {
            f6 = f8;
            f5 = f6;
        }
        float f15 = (-this.trackLength) / 2.0f;
        boolean z2 = ((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator) && z && f3 > 0.0f;
        if (clamp3 <= clamp4) {
            float f16 = ((float) clamp3) + f6;
            float f17 = ((float) clamp4) - f5;
            float f18 = f15;
            float f19 = f6 * 2.0f;
            float f20 = 2.0f * f5;
            paint3.setColor(i);
            paint3.setAntiAlias(true);
            paint3.setStrokeWidth(this.displayedTrackThickness);
            ((DrawingDelegate.PathPoint) this.endPoints.first).reset();
            ((DrawingDelegate.PathPoint) this.endPoints.second).reset();
            ((DrawingDelegate.PathPoint) this.endPoints.first).translate(f16 + f18, 0.0f);
            ((DrawingDelegate.PathPoint) this.endPoints.second).translate(f18 + f17, 0.0f);
            if (clamp3 != 0 || f17 + f5 >= f16 + f6) {
                float f21 = f20;
                if (f16 - f6 > f17 - f5) {
                    Pair pair = this.endPoints;
                    float f22 = this.displayedTrackThickness;
                    float f23 = f19;
                    float f24 = f21;
                    Canvas canvas3 = canvas;
                    drawRoundedBlock(canvas3, paint, (DrawingDelegate.PathPoint) pair.second, f24, f22, f5, (DrawingDelegate.PathPoint) pair.first, f23, f22, f6, false);
                    return;
                }
                Paint paint4 = paint;
                float f25 = f21;
                float f26 = f19;
                float f27 = f5;
                float f28 = f6;
                paint4.setStyle(Paint.Style.STROKE);
                paint4.setStrokeCap(((LinearProgressIndicatorSpec) this.spec).useStrokeCap() ? Paint.Cap.ROUND : Paint.Cap.BUTT);
                if (!z2) {
                    Pair pair2 = this.endPoints;
                    Object obj = pair2.first;
                    float f29 = ((DrawingDelegate.PathPoint) obj).posVec[0];
                    float f30 = ((DrawingDelegate.PathPoint) obj).posVec[1];
                    Object obj2 = pair2.second;
                    Paint paint5 = paint4;
                    canvas.drawLine(f29, f30, ((DrawingDelegate.PathPoint) obj2).posVec[0], ((DrawingDelegate.PathPoint) obj2).posVec[1], paint5);
                    paint2 = paint5;
                    canvas2 = canvas;
                } else {
                    paint2 = paint4;
                    PathMeasure pathMeasure = this.activePathMeasure;
                    Path path = this.displayedActivePath;
                    Pair pair3 = this.endPoints;
                    float f31 = this.trackLength;
                    calculateDisplayedPath(pathMeasure, path, pair3, f16 / f31, f17 / f31, f3, f4);
                    canvas2 = canvas;
                    canvas2.drawPath(this.displayedActivePath, paint2);
                }
                if (!((LinearProgressIndicatorSpec) this.spec).useStrokeCap()) {
                    if (f16 > 0.0f && f28 > 0.0f) {
                        drawRoundedBlock(canvas2, paint2, (DrawingDelegate.PathPoint) this.endPoints.first, f26, this.displayedTrackThickness, f28);
                    }
                    if (f17 < this.trackLength && f27 > 0.0f) {
                        drawRoundedBlock(canvas, paint, (DrawingDelegate.PathPoint) this.endPoints.second, f25, this.displayedTrackThickness, f27);
                        return;
                    }
                    return;
                }
                return;
            }
            Pair pair4 = this.endPoints;
            float f32 = f20;
            float f33 = this.displayedTrackThickness;
            drawRoundedBlock(canvas, paint3, (DrawingDelegate.PathPoint) pair4.first, f19, f33, f6, (DrawingDelegate.PathPoint) pair4.second, f32, f33, f5, true);
        }
    }

    /* access modifiers changed from: package-private */
    public void drawStopIndicator(Canvas canvas, Paint paint, int i, int i2) {
        float f;
        int compositeARGBWithAlpha = MaterialColors.compositeARGBWithAlpha(i, i2);
        this.drawingDeterminateIndicator = false;
        if (((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize > 0 && compositeARGBWithAlpha != 0) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(compositeARGBWithAlpha);
            BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
            if (((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackStopIndicatorPadding != null) {
                f = ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).trackStopIndicatorPadding.floatValue() + (((float) ((LinearProgressIndicatorSpec) this.spec).trackStopIndicatorSize) / 2.0f);
            } else {
                f = this.displayedTrackThickness / 2.0f;
            }
            DrawingDelegate.PathPoint pathPoint = new DrawingDelegate.PathPoint(new float[]{(this.trackLength / 2.0f) - f, 0.0f}, new float[]{1.0f, 0.0f});
            BaseProgressIndicatorSpec baseProgressIndicatorSpec2 = this.spec;
            drawRoundedBlock(canvas, paint, pathPoint, (float) ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec2).trackStopIndicatorSize, (float) ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec2).trackStopIndicatorSize, (this.displayedCornerRadius * ((float) ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec2).trackStopIndicatorSize)) / this.displayedTrackThickness);
        }
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate.PathPoint pathPoint, float f, float f2, float f3) {
        drawRoundedBlock(canvas, paint, pathPoint, f, f2, f3, (DrawingDelegate.PathPoint) null, 0.0f, 0.0f, 0.0f, false);
    }

    private void drawRoundedBlock(Canvas canvas, Paint paint, DrawingDelegate.PathPoint pathPoint, float f, float f2, float f3, DrawingDelegate.PathPoint pathPoint2, float f4, float f5, float f6, boolean z) {
        char c;
        float f7;
        float f8;
        Canvas canvas2 = canvas;
        Paint paint2 = paint;
        DrawingDelegate.PathPoint pathPoint3 = pathPoint;
        float f9 = f;
        float f10 = f3;
        DrawingDelegate.PathPoint pathPoint4 = pathPoint2;
        float min = Math.min(f2, this.displayedTrackThickness);
        float f11 = (-f9) / 2.0f;
        float f12 = (-min) / 2.0f;
        float f13 = f9 / 2.0f;
        float f14 = min / 2.0f;
        RectF rectF = new RectF(f11, f12, f13, f14);
        paint2.setStyle(Paint.Style.FILL);
        canvas2.save();
        if (pathPoint4 != null) {
            float min2 = Math.min(f5, this.displayedTrackThickness);
            float min3 = Math.min(f4 / 2.0f, (f6 * min2) / this.displayedTrackThickness);
            RectF rectF2 = new RectF();
            if (z) {
                c = 0;
                float f15 = (pathPoint4.posVec[0] - min3) - (pathPoint3.posVec[0] - f10);
                if (f15 > 0.0f) {
                    pathPoint4.translate((-f15) / 2.0f, 0.0f);
                    f7 = f4 + f15;
                } else {
                    f7 = f4;
                }
                rectF2.set(0.0f, f12, f13, f14);
            } else {
                c = 0;
                float f16 = (pathPoint4.posVec[0] + min3) - (pathPoint3.posVec[0] + f10);
                if (f16 < 0.0f) {
                    pathPoint4.translate((-f16) / 2.0f, 0.0f);
                    f8 = f4 - f16;
                } else {
                    f8 = f4;
                }
                rectF2.set(f11, f12, 0.0f, f14);
                f7 = f8;
            }
            RectF rectF3 = new RectF((-f7) / 2.0f, (-min2) / 2.0f, f7 / 2.0f, min2 / 2.0f);
            float[] fArr = pathPoint4.posVec;
            canvas2.translate(fArr[c], fArr[1]);
            canvas2.rotate(vectorToCanvasRotation(pathPoint4.tanVec));
            Path path = new Path();
            path.addRoundRect(rectF3, min3, min3, Path.Direction.CCW);
            canvas2.clipPath(path);
            canvas2.rotate(-vectorToCanvasRotation(pathPoint4.tanVec));
            float[] fArr2 = pathPoint4.posVec;
            canvas2.translate(-fArr2[c], -fArr2[1]);
            float[] fArr3 = pathPoint3.posVec;
            canvas2.translate(fArr3[c], fArr3[1]);
            canvas2.rotate(vectorToCanvasRotation(pathPoint3.tanVec));
            canvas2.drawRect(rectF2, paint2);
            canvas2.drawRoundRect(rectF, f10, f10, paint2);
        } else {
            float[] fArr4 = pathPoint3.posVec;
            canvas2.translate(fArr4[0], fArr4[1]);
            canvas2.rotate(vectorToCanvasRotation(pathPoint3.tanVec));
            canvas2.drawRoundRect(rectF, f10, f10, paint2);
        }
        canvas2.restore();
    }

    /* access modifiers changed from: package-private */
    public void invalidateCachedPaths() {
        this.cachedActivePath.rewind();
        if (((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator)) {
            int i = this.drawingDeterminateIndicator ? ((LinearProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((LinearProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
            float f = this.trackLength;
            int i2 = (int) (f / ((float) i));
            this.adjustedWavelength = f / ((float) i2);
            for (int i3 = 0; i3 <= i2; i3++) {
                int i4 = i3 * 2;
                float f2 = (float) (i4 + 1);
                this.cachedActivePath.cubicTo(((float) i4) + 0.48f, 0.0f, f2 - 0.48f, 1.0f, f2, 1.0f);
                float f3 = (float) (i4 + 2);
                this.cachedActivePath.cubicTo(f2 + 0.48f, 1.0f, f3 - 0.48f, 0.0f, f3, 0.0f);
            }
            this.transform.reset();
            this.transform.setScale(this.adjustedWavelength / 2.0f, -2.0f);
            this.transform.postTranslate(0.0f, 1.0f);
            this.cachedActivePath.transform(this.transform);
        } else {
            this.cachedActivePath.lineTo(this.trackLength, 0.0f);
        }
        this.activePathMeasure.setPath(this.cachedActivePath, false);
    }

    private void calculateDisplayedPath(PathMeasure pathMeasure, Path path, Pair pair, float f, float f2, float f3, float f4) {
        int i = this.drawingDeterminateIndicator ? ((LinearProgressIndicatorSpec) this.spec).wavelengthDeterminate : ((LinearProgressIndicatorSpec) this.spec).wavelengthIndeterminate;
        if (pathMeasure == this.activePathMeasure && i != this.cachedWavelength) {
            this.cachedWavelength = i;
            invalidateCachedPaths();
        }
        path.rewind();
        float f5 = (-this.trackLength) / 2.0f;
        boolean hasWavyEffect = ((LinearProgressIndicatorSpec) this.spec).hasWavyEffect(this.drawingDeterminateIndicator);
        if (hasWavyEffect) {
            float f6 = this.trackLength;
            float f7 = this.adjustedWavelength;
            float f8 = f6 / f7;
            float f9 = f4 / f8;
            float f10 = f8 / (f8 + 1.0f);
            f = (f + f9) * f10;
            f2 = (f2 + f9) * f10;
            f5 -= f4 * f7;
        }
        float length = f * pathMeasure.getLength();
        float length2 = f2 * pathMeasure.getLength();
        pathMeasure.getSegment(length, length2, path, true);
        DrawingDelegate.PathPoint pathPoint = (DrawingDelegate.PathPoint) pair.first;
        pathPoint.reset();
        pathMeasure.getPosTan(length, pathPoint.posVec, pathPoint.tanVec);
        DrawingDelegate.PathPoint pathPoint2 = (DrawingDelegate.PathPoint) pair.second;
        pathPoint2.reset();
        pathMeasure.getPosTan(length2, pathPoint2.posVec, pathPoint2.tanVec);
        this.transform.reset();
        this.transform.setTranslate(f5, 0.0f);
        pathPoint.translate(f5, 0.0f);
        pathPoint2.translate(f5, 0.0f);
        if (hasWavyEffect) {
            float f11 = this.displayedAmplitude * f3;
            this.transform.postScale(1.0f, f11);
            pathPoint.scale(1.0f, f11);
            pathPoint2.scale(1.0f, f11);
        }
        path.transform(this.transform);
    }
}
