package com.google.android.material.timepicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.math.MathUtils;
import com.google.android.material.motion.MotionUtils;
import java.util.ArrayList;
import java.util.List;

class ClockHandView extends View {
    private boolean animatingOnTouchUp;
    private final int animationDuration;
    private final TimeInterpolator animationInterpolator;
    private final float centerDotRadius;
    private boolean changedDuringTouch;
    private int circleRadius;
    private int currentLevel;
    private double degRad;
    private float downX;
    private float downY;
    private boolean isInTapRegion;
    private boolean isMultiLevel;
    private final List listeners;
    private float originalDeg;
    private final Paint paint;
    private final ValueAnimator rotationAnimator;
    private final int scaledTouchSlop;
    private final RectF selectorBox;
    private final int selectorRadius;
    private final int selectorStrokeWidth;

    public interface OnRotateListener {
        void onRotate(float f, boolean z);
    }

    public ClockHandView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClockHandView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.materialClockStyle);
    }

    public ClockHandView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.rotationAnimator = new ValueAnimator();
        this.listeners = new ArrayList();
        Paint paint2 = new Paint();
        this.paint = paint2;
        this.selectorBox = new RectF();
        this.currentLevel = 1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ClockHandView, i, R$style.Widget_MaterialComponents_TimePicker_Clock);
        this.animationDuration = MotionUtils.resolveThemeDuration(context, R$attr.motionDurationLong2, 200);
        this.animationInterpolator = MotionUtils.resolveThemeInterpolator(context, R$attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(R$styleable.ClockHandView_materialCircleRadius, 0);
        this.selectorRadius = obtainStyledAttributes.getDimensionPixelSize(R$styleable.ClockHandView_selectorSize, 0);
        Resources resources = getResources();
        this.selectorStrokeWidth = resources.getDimensionPixelSize(R$dimen.material_clock_hand_stroke_width);
        this.centerDotRadius = (float) resources.getDimensionPixelSize(R$dimen.material_clock_hand_center_dot_radius);
        int color = obtainStyledAttributes.getColor(R$styleable.ClockHandView_clockHandColor, 0);
        paint2.setAntiAlias(true);
        paint2.setColor(color);
        setHandRotation(0.0f);
        this.scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        setImportantForAccessibility(2);
        obtainStyledAttributes.recycle();
        initRotationAnimator();
    }

    private void initRotationAnimator() {
        this.rotationAnimator.addUpdateListener(new ClockHandView$$ExternalSyntheticLambda0(this));
        this.rotationAnimator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationCancel(Animator animator) {
                animator.end();
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$PRvqYbjc7A5gQVhxbWP7FwHQ_H4(ClockHandView clockHandView, ValueAnimator valueAnimator) {
        clockHandView.getClass();
        clockHandView.setHandRotationInternal(((Float) valueAnimator.getAnimatedValue()).floatValue(), true);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (!this.rotationAnimator.isRunning()) {
            setHandRotation(getHandRotation());
        }
    }

    public void setHandRotation(float f) {
        setHandRotation(f, false);
    }

    public void setHandRotation(float f, boolean z) {
        this.rotationAnimator.cancel();
        if (!z) {
            setHandRotationInternal(f, false);
            return;
        }
        Pair valuesForAnimation = getValuesForAnimation(f);
        this.rotationAnimator.setFloatValues(new float[]{((Float) valuesForAnimation.first).floatValue(), ((Float) valuesForAnimation.second).floatValue()});
        this.rotationAnimator.setDuration((long) this.animationDuration);
        this.rotationAnimator.setInterpolator(this.animationInterpolator);
        this.rotationAnimator.start();
    }

    private Pair getValuesForAnimation(float f) {
        float handRotation = getHandRotation();
        if (Math.abs(handRotation - f) > 180.0f) {
            if (handRotation > 180.0f && f < 180.0f) {
                f += 360.0f;
            }
            if (handRotation < 180.0f && f > 180.0f) {
                handRotation += 360.0f;
            }
        }
        return new Pair(Float.valueOf(handRotation), Float.valueOf(f));
    }

    private void setHandRotationInternal(float f, boolean z) {
        float f2 = f % 360.0f;
        this.originalDeg = f2;
        this.degRad = Math.toRadians((double) (f2 - 90.0f));
        float leveledCircleRadius = (float) getLeveledCircleRadius(this.currentLevel);
        float width = ((float) (getWidth() / 2)) + (((float) Math.cos(this.degRad)) * leveledCircleRadius);
        float height = ((float) (getHeight() / 2)) + (leveledCircleRadius * ((float) Math.sin(this.degRad)));
        RectF rectF = this.selectorBox;
        int i = this.selectorRadius;
        rectF.set(width - ((float) i), height - ((float) i), width + ((float) i), height + ((float) i));
        for (OnRotateListener onRotate : this.listeners) {
            onRotate.onRotate(f2, z);
        }
        invalidate();
    }

    public void addOnRotateListener(OnRotateListener onRotateListener) {
        this.listeners.add(onRotateListener);
    }

    public float getHandRotation() {
        return this.originalDeg;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSelector(canvas);
    }

    private void drawSelector(Canvas canvas) {
        int height = getHeight() / 2;
        int width = getWidth() / 2;
        int leveledCircleRadius = getLeveledCircleRadius(this.currentLevel);
        float f = (float) width;
        float f2 = (float) leveledCircleRadius;
        float cos = (((float) Math.cos(this.degRad)) * f2) + f;
        float f3 = (float) height;
        this.paint.setStrokeWidth(0.0f);
        canvas.drawCircle(cos, (f2 * ((float) Math.sin(this.degRad))) + f3, (float) this.selectorRadius, this.paint);
        double sin = Math.sin(this.degRad);
        double cos2 = Math.cos(this.degRad);
        double d = (double) ((float) (leveledCircleRadius - this.selectorRadius));
        float f4 = (float) (height + ((int) (d * sin)));
        this.paint.setStrokeWidth((float) this.selectorStrokeWidth);
        Canvas canvas2 = canvas;
        canvas2.drawLine(f, f3, (float) (width + ((int) (cos2 * d))), f4, this.paint);
        canvas2.drawCircle(f, f3, this.centerDotRadius, this.paint);
    }

    public RectF getCurrentSelectorBox() {
        return this.selectorBox;
    }

    public int getSelectorRadius() {
        return this.selectorRadius;
    }

    public void setCircleRadius(int i) {
        this.circleRadius = i;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        boolean z3;
        int actionMasked = motionEvent.getActionMasked();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (actionMasked == 0) {
            this.downX = x;
            this.downY = y;
            this.isInTapRegion = true;
            this.changedDuringTouch = false;
            z2 = true;
            z3 = false;
            z = false;
        } else if (actionMasked == 1 || actionMasked == 2) {
            int i = (int) (x - this.downX);
            int i2 = (int) (y - this.downY);
            this.isInTapRegion = (i * i) + (i2 * i2) > this.scaledTouchSlop;
            z3 = this.changedDuringTouch;
            boolean z4 = actionMasked == 1;
            if (this.isMultiLevel) {
                adjustLevel(x, y);
            }
            z = z4;
            z2 = false;
        } else {
            z3 = false;
            z2 = false;
            z = false;
        }
        this.changedDuringTouch = handleTouchInput(x, y, z3, z2, z) | this.changedDuringTouch;
        return true;
    }

    private void adjustLevel(float f, float f2) {
        int i = 2;
        if (MathUtils.dist((float) (getWidth() / 2), (float) (getHeight() / 2), f, f2) > ((float) getLeveledCircleRadius(2)) + ViewUtils.dpToPx(getContext(), 12)) {
            i = 1;
        }
        this.currentLevel = i;
    }

    private boolean handleTouchInput(float f, float f2, boolean z, boolean z2, boolean z3) {
        float degreesFromXY = (float) getDegreesFromXY(f, f2);
        boolean z4 = false;
        boolean z5 = getHandRotation() != degreesFromXY;
        if (z2 && z5) {
            return true;
        }
        if (!z5 && !z) {
            return false;
        }
        if (z3 && this.animatingOnTouchUp) {
            z4 = true;
        }
        setHandRotation(degreesFromXY, z4);
        return true;
    }

    private int getDegreesFromXY(float f, float f2) {
        int degrees = (int) Math.toDegrees(Math.atan2((double) (f2 - ((float) (getHeight() / 2))), (double) (f - ((float) (getWidth() / 2)))));
        int i = degrees + 90;
        return i < 0 ? degrees + 450 : i;
    }

    /* access modifiers changed from: package-private */
    public void setMultiLevel(boolean z) {
        if (this.isMultiLevel && !z) {
            this.currentLevel = 1;
        }
        this.isMultiLevel = z;
        invalidate();
    }

    private int getLeveledCircleRadius(int i) {
        int i2 = this.circleRadius;
        return i == 2 ? Math.round(((float) i2) * 0.66f) : i2;
    }
}
