package com.google.android.material.button;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.R$attr;
import com.google.android.material.R$styleable;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.ripple.RippleUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import com.google.android.material.shape.StateListShapeAppearanceModel;

class MaterialButtonHelper {
    private boolean backgroundOverwritten = false;
    private ColorStateList backgroundTint;
    private PorterDuff.Mode backgroundTintMode;
    private boolean checkable;
    private int cornerRadius;
    private boolean cornerRadiusSet = false;
    private SpringForce cornerSpringForce;
    private int elevation;
    private int insetBottom;
    private int insetLeft;
    private int insetRight;
    private int insetTop;
    private Drawable maskDrawable;
    private final MaterialButton materialButton;
    private MaterialShapeDrawable.OnCornerSizeChangeListener onCornerSizeChangeListener;
    private ColorStateList rippleColor;
    private LayerDrawable rippleDrawable;
    private ShapeAppearanceModel shapeAppearanceModel;
    private boolean shouldDrawSurfaceColorStroke = false;
    private StateListShapeAppearanceModel stateListShapeAppearanceModel;
    private ColorStateList strokeColor;
    private int strokeWidth;
    private boolean toggleCheckedStateOnClick = true;

    MaterialButtonHelper(MaterialButton materialButton2, ShapeAppearanceModel shapeAppearanceModel2) {
        this.materialButton = materialButton2;
        this.shapeAppearanceModel = shapeAppearanceModel2;
    }

    /* access modifiers changed from: package-private */
    public void loadFromAttributes(TypedArray typedArray) {
        this.insetLeft = typedArray.getDimensionPixelOffset(R$styleable.MaterialButton_android_insetLeft, 0);
        this.insetRight = typedArray.getDimensionPixelOffset(R$styleable.MaterialButton_android_insetRight, 0);
        this.insetTop = typedArray.getDimensionPixelOffset(R$styleable.MaterialButton_android_insetTop, 0);
        this.insetBottom = typedArray.getDimensionPixelOffset(R$styleable.MaterialButton_android_insetBottom, 0);
        int i = R$styleable.MaterialButton_cornerRadius;
        if (typedArray.hasValue(i)) {
            int dimensionPixelSize = typedArray.getDimensionPixelSize(i, -1);
            this.cornerRadius = dimensionPixelSize;
            setShapeAppearanceModel(this.shapeAppearanceModel.withCornerSize((float) dimensionPixelSize));
            this.cornerRadiusSet = true;
        }
        this.strokeWidth = typedArray.getDimensionPixelSize(R$styleable.MaterialButton_strokeWidth, 0);
        this.backgroundTintMode = ViewUtils.parseTintMode(typedArray.getInt(R$styleable.MaterialButton_backgroundTintMode, -1), PorterDuff.Mode.SRC_IN);
        this.backgroundTint = MaterialResources.getColorStateList(this.materialButton.getContext(), typedArray, R$styleable.MaterialButton_backgroundTint);
        this.strokeColor = MaterialResources.getColorStateList(this.materialButton.getContext(), typedArray, R$styleable.MaterialButton_strokeColor);
        this.rippleColor = MaterialResources.getColorStateList(this.materialButton.getContext(), typedArray, R$styleable.MaterialButton_rippleColor);
        this.checkable = typedArray.getBoolean(R$styleable.MaterialButton_android_checkable, false);
        this.elevation = typedArray.getDimensionPixelSize(R$styleable.MaterialButton_elevation, 0);
        this.toggleCheckedStateOnClick = typedArray.getBoolean(R$styleable.MaterialButton_toggleCheckedStateOnClick, true);
        int paddingStart = this.materialButton.getPaddingStart();
        int paddingTop = this.materialButton.getPaddingTop();
        int paddingEnd = this.materialButton.getPaddingEnd();
        int paddingBottom = this.materialButton.getPaddingBottom();
        if (typedArray.hasValue(R$styleable.MaterialButton_android_background)) {
            setBackgroundOverwritten();
        } else {
            updateBackground();
        }
        this.materialButton.setPaddingRelative(paddingStart + this.insetLeft, paddingTop + this.insetTop, paddingEnd + this.insetRight, paddingBottom + this.insetBottom);
    }

    private void updateBackground() {
        this.materialButton.setInternalBackground(createBackground());
        MaterialShapeDrawable materialShapeDrawable = getMaterialShapeDrawable();
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation((float) this.elevation);
            materialShapeDrawable.setState(this.materialButton.getDrawableState());
        }
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundOverwritten() {
        this.backgroundOverwritten = true;
        this.materialButton.setSupportBackgroundTintList(this.backgroundTint);
        this.materialButton.setSupportBackgroundTintMode(this.backgroundTintMode);
    }

    /* access modifiers changed from: package-private */
    public boolean isBackgroundOverwritten() {
        return this.backgroundOverwritten;
    }

    private InsetDrawable wrapDrawableWithInset(Drawable drawable) {
        return new InsetDrawable(drawable, this.insetLeft, this.insetTop, this.insetRight, this.insetBottom);
    }

    /* access modifiers changed from: package-private */
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        if (this.backgroundTint != colorStateList) {
            this.backgroundTint = colorStateList;
            if (getMaterialShapeDrawable() != null) {
                getMaterialShapeDrawable().setTintList(this.backgroundTint);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public ColorStateList getSupportBackgroundTintList() {
        return this.backgroundTint;
    }

    /* access modifiers changed from: package-private */
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.backgroundTintMode != mode) {
            this.backgroundTintMode = mode;
            if (getMaterialShapeDrawable() != null && this.backgroundTintMode != null) {
                getMaterialShapeDrawable().setTintMode(this.backgroundTintMode);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return this.backgroundTintMode;
    }

    /* access modifiers changed from: package-private */
    public void setShouldDrawSurfaceColorStroke(boolean z) {
        this.shouldDrawSurfaceColorStroke = z;
        updateStroke();
    }

    private Drawable createBackground() {
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(this.shapeAppearanceModel);
        StateListShapeAppearanceModel stateListShapeAppearanceModel2 = this.stateListShapeAppearanceModel;
        if (stateListShapeAppearanceModel2 != null) {
            materialShapeDrawable.setStateListShapeAppearanceModel(stateListShapeAppearanceModel2);
        }
        SpringForce springForce = this.cornerSpringForce;
        if (springForce != null) {
            materialShapeDrawable.setCornerSpringForce(springForce);
        }
        MaterialShapeDrawable.OnCornerSizeChangeListener onCornerSizeChangeListener2 = this.onCornerSizeChangeListener;
        if (onCornerSizeChangeListener2 != null) {
            materialShapeDrawable.setOnCornerSizeChangeListener(onCornerSizeChangeListener2);
        }
        materialShapeDrawable.initializeElevationOverlay(this.materialButton.getContext());
        materialShapeDrawable.setTintList(this.backgroundTint);
        PorterDuff.Mode mode = this.backgroundTintMode;
        if (mode != null) {
            materialShapeDrawable.setTintMode(mode);
        }
        materialShapeDrawable.setStroke((float) this.strokeWidth, this.strokeColor);
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(this.shapeAppearanceModel);
        StateListShapeAppearanceModel stateListShapeAppearanceModel3 = this.stateListShapeAppearanceModel;
        if (stateListShapeAppearanceModel3 != null) {
            materialShapeDrawable2.setStateListShapeAppearanceModel(stateListShapeAppearanceModel3);
        }
        SpringForce springForce2 = this.cornerSpringForce;
        if (springForce2 != null) {
            materialShapeDrawable2.setCornerSpringForce(springForce2);
        }
        materialShapeDrawable2.setTint(0);
        materialShapeDrawable2.setStroke((float) this.strokeWidth, this.shouldDrawSurfaceColorStroke ? MaterialColors.getColor(this.materialButton, R$attr.colorSurface) : 0);
        MaterialShapeDrawable materialShapeDrawable3 = new MaterialShapeDrawable(this.shapeAppearanceModel);
        this.maskDrawable = materialShapeDrawable3;
        StateListShapeAppearanceModel stateListShapeAppearanceModel4 = this.stateListShapeAppearanceModel;
        if (stateListShapeAppearanceModel4 != null) {
            MaterialShapeDrawable materialShapeDrawable4 = materialShapeDrawable3;
            materialShapeDrawable3.setStateListShapeAppearanceModel(stateListShapeAppearanceModel4);
        }
        SpringForce springForce3 = this.cornerSpringForce;
        if (springForce3 != null) {
            ((MaterialShapeDrawable) this.maskDrawable).setCornerSpringForce(springForce3);
        }
        this.maskDrawable.setTint(-1);
        RippleDrawable rippleDrawable2 = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.rippleColor), wrapDrawableWithInset(new LayerDrawable(new Drawable[]{materialShapeDrawable2, materialShapeDrawable})), this.maskDrawable);
        this.rippleDrawable = rippleDrawable2;
        return rippleDrawable2;
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundColor(int i) {
        if (getMaterialShapeDrawable() != null) {
            getMaterialShapeDrawable().setTint(i);
        }
    }

    /* access modifiers changed from: package-private */
    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    private void updateStroke() {
        MaterialShapeDrawable materialShapeDrawable = getMaterialShapeDrawable();
        MaterialShapeDrawable surfaceColorStrokeDrawable = getSurfaceColorStrokeDrawable();
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setStroke((float) this.strokeWidth, this.strokeColor);
            if (surfaceColorStrokeDrawable != null) {
                surfaceColorStrokeDrawable.setStroke((float) this.strokeWidth, this.shouldDrawSurfaceColorStroke ? MaterialColors.getColor(this.materialButton, R$attr.colorSurface) : 0);
            }
        }
    }

    private MaterialShapeDrawable getMaterialShapeDrawable(boolean z) {
        LayerDrawable layerDrawable = this.rippleDrawable;
        if (layerDrawable == null || layerDrawable.getNumberOfLayers() <= 0) {
            return null;
        }
        return (MaterialShapeDrawable) ((LayerDrawable) ((InsetDrawable) this.rippleDrawable.getDrawable(0)).getDrawable()).getDrawable(z ^ true ? 1 : 0);
    }

    /* access modifiers changed from: package-private */
    public MaterialShapeDrawable getMaterialShapeDrawable() {
        return getMaterialShapeDrawable(false);
    }

    /* access modifiers changed from: package-private */
    public void setCheckable(boolean z) {
        this.checkable = z;
    }

    /* access modifiers changed from: package-private */
    public boolean isCheckable() {
        return this.checkable;
    }

    /* access modifiers changed from: package-private */
    public boolean isToggleCheckedStateOnClick() {
        return this.toggleCheckedStateOnClick;
    }

    /* access modifiers changed from: package-private */
    public void setCornerSizeChangeListener(MaterialShapeDrawable.OnCornerSizeChangeListener onCornerSizeChangeListener2) {
        this.onCornerSizeChangeListener = onCornerSizeChangeListener2;
        MaterialShapeDrawable materialShapeDrawable = getMaterialShapeDrawable();
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setOnCornerSizeChangeListener(onCornerSizeChangeListener2);
        }
    }

    private MaterialShapeDrawable getSurfaceColorStrokeDrawable() {
        return getMaterialShapeDrawable(true);
    }

    private void updateButtonShape() {
        MaterialShapeDrawable materialShapeDrawable = getMaterialShapeDrawable();
        if (materialShapeDrawable != null) {
            StateListShapeAppearanceModel stateListShapeAppearanceModel2 = this.stateListShapeAppearanceModel;
            if (stateListShapeAppearanceModel2 != null) {
                materialShapeDrawable.setStateListShapeAppearanceModel(stateListShapeAppearanceModel2);
            } else {
                materialShapeDrawable.setShapeAppearanceModel(this.shapeAppearanceModel);
            }
            SpringForce springForce = this.cornerSpringForce;
            if (springForce != null) {
                materialShapeDrawable.setCornerSpringForce(springForce);
            }
        }
        MaterialShapeDrawable surfaceColorStrokeDrawable = getSurfaceColorStrokeDrawable();
        if (surfaceColorStrokeDrawable != null) {
            StateListShapeAppearanceModel stateListShapeAppearanceModel3 = this.stateListShapeAppearanceModel;
            if (stateListShapeAppearanceModel3 != null) {
                surfaceColorStrokeDrawable.setStateListShapeAppearanceModel(stateListShapeAppearanceModel3);
            } else {
                surfaceColorStrokeDrawable.setShapeAppearanceModel(this.shapeAppearanceModel);
            }
            SpringForce springForce2 = this.cornerSpringForce;
            if (springForce2 != null) {
                surfaceColorStrokeDrawable.setCornerSpringForce(springForce2);
            }
        }
        Shapeable maskDrawable2 = getMaskDrawable();
        if (maskDrawable2 != null) {
            maskDrawable2.setShapeAppearanceModel(this.shapeAppearanceModel);
            if (maskDrawable2 instanceof MaterialShapeDrawable) {
                MaterialShapeDrawable materialShapeDrawable2 = (MaterialShapeDrawable) maskDrawable2;
                StateListShapeAppearanceModel stateListShapeAppearanceModel4 = this.stateListShapeAppearanceModel;
                if (stateListShapeAppearanceModel4 != null) {
                    materialShapeDrawable2.setStateListShapeAppearanceModel(stateListShapeAppearanceModel4);
                }
                SpringForce springForce3 = this.cornerSpringForce;
                if (springForce3 != null) {
                    materialShapeDrawable2.setCornerSpringForce(springForce3);
                }
            }
        }
    }

    public Shapeable getMaskDrawable() {
        LayerDrawable layerDrawable = this.rippleDrawable;
        if (layerDrawable == null || layerDrawable.getNumberOfLayers() <= 1) {
            return null;
        }
        if (this.rippleDrawable.getNumberOfLayers() > 2) {
            return (Shapeable) this.rippleDrawable.getDrawable(2);
        }
        return (Shapeable) this.rippleDrawable.getDrawable(1);
    }

    /* access modifiers changed from: package-private */
    public void setCornerSpringForce(SpringForce springForce) {
        this.cornerSpringForce = springForce;
        if (this.stateListShapeAppearanceModel != null) {
            updateButtonShape();
        }
    }

    /* access modifiers changed from: package-private */
    public SpringForce getCornerSpringForce() {
        return this.cornerSpringForce;
    }

    /* access modifiers changed from: package-private */
    public void setStateListShapeAppearanceModel(StateListShapeAppearanceModel stateListShapeAppearanceModel2) {
        this.stateListShapeAppearanceModel = stateListShapeAppearanceModel2;
        updateButtonShape();
    }

    /* access modifiers changed from: package-private */
    public StateListShapeAppearanceModel getStateListShapeAppearanceModel() {
        return this.stateListShapeAppearanceModel;
    }

    /* access modifiers changed from: package-private */
    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel2) {
        this.shapeAppearanceModel = shapeAppearanceModel2;
        this.stateListShapeAppearanceModel = null;
        updateButtonShape();
    }

    /* access modifiers changed from: package-private */
    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.shapeAppearanceModel;
    }
}
