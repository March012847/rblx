package com.google.android.material.textfield;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;

abstract class CutoutDrawable extends MaterialShapeDrawable {
    CutoutDrawableState drawableState;

    static CutoutDrawable create(ShapeAppearanceModel shapeAppearanceModel) {
        if (shapeAppearanceModel == null) {
            shapeAppearanceModel = new ShapeAppearanceModel();
        }
        return create(new CutoutDrawableState(shapeAppearanceModel, new RectF()));
    }

    /* access modifiers changed from: private */
    public static CutoutDrawable create(CutoutDrawableState cutoutDrawableState) {
        return new ImplApi18(cutoutDrawableState);
    }

    private CutoutDrawable(CutoutDrawableState cutoutDrawableState) {
        super((MaterialShapeDrawable.MaterialShapeDrawableState) cutoutDrawableState);
        this.drawableState = cutoutDrawableState;
    }

    public Drawable mutate() {
        this.drawableState = new CutoutDrawableState(this.drawableState);
        return this;
    }

    /* access modifiers changed from: package-private */
    public boolean hasCutout() {
        return !this.drawableState.cutoutBounds.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public void setCutout(float f, float f2, float f3, float f4) {
        if (f != this.drawableState.cutoutBounds.left || f2 != this.drawableState.cutoutBounds.top || f3 != this.drawableState.cutoutBounds.right || f4 != this.drawableState.cutoutBounds.bottom) {
            this.drawableState.cutoutBounds.set(f, f2, f3, f4);
            invalidateSelf();
        }
    }

    /* access modifiers changed from: package-private */
    public void setCutout(RectF rectF) {
        setCutout(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }

    /* access modifiers changed from: package-private */
    public void removeCutout() {
        setCutout(0.0f, 0.0f, 0.0f, 0.0f);
    }

    class ImplApi18 extends CutoutDrawable {
        ImplApi18(CutoutDrawableState cutoutDrawableState) {
            super(cutoutDrawableState);
        }

        /* access modifiers changed from: protected */
        public void drawStrokeShape(Canvas canvas) {
            if (this.drawableState.cutoutBounds.isEmpty()) {
                super.drawStrokeShape(canvas);
                return;
            }
            canvas.save();
            canvas.clipOutRect(this.drawableState.cutoutBounds);
            super.drawStrokeShape(canvas);
            canvas.restore();
        }
    }

    final class CutoutDrawableState extends MaterialShapeDrawable.MaterialShapeDrawableState {
        /* access modifiers changed from: private */
        public final RectF cutoutBounds;

        private CutoutDrawableState(ShapeAppearanceModel shapeAppearanceModel, RectF rectF) {
            super(shapeAppearanceModel, (ElevationOverlayProvider) null);
            this.cutoutBounds = rectF;
        }

        private CutoutDrawableState(CutoutDrawableState cutoutDrawableState) {
            super(cutoutDrawableState);
            this.cutoutBounds = cutoutDrawableState.cutoutBounds;
        }

        public Drawable newDrawable() {
            CutoutDrawable access$400 = CutoutDrawable.create(this);
            access$400.invalidateSelf();
            return access$400;
        }
    }
}
