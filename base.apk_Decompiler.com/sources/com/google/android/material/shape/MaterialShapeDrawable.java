package com.google.android.material.shape;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import androidx.core.util.ObjectsCompat;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.google.android.material.R$attr;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.math.MathUtils;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.ShapePath;
import java.util.BitSet;

public class MaterialShapeDrawable extends Drawable implements Shapeable {
    private static final SpringAnimatedCornerSizeProperty[] CORNER_SIZES_IN_PX = new SpringAnimatedCornerSizeProperty[4];
    static final ShapeAppearanceModel DEFAULT_INTERPOLATION_START_SHAPE_APPEARANCE_MODEL = ShapeAppearanceModel.builder().setAllCorners(0, 0.0f).build();
    private static final String TAG = MaterialShapeDrawable.class.getSimpleName();
    private static final Paint clearPaint;
    private boolean boundsIsEmpty;
    /* access modifiers changed from: private */
    public final BitSet containsIncompatibleShadowOp;
    /* access modifiers changed from: private */
    public final ShapePath.ShadowCompatOperation[] cornerShadowOperation;
    SpringAnimation[] cornerSpringAnimations;
    private SpringForce cornerSpringForce;
    private MaterialShapeDrawableState drawableState;
    /* access modifiers changed from: private */
    public final ShapePath.ShadowCompatOperation[] edgeShadowOperation;
    private final Paint fillPaint;
    private final RectF insetRectF;
    private final Matrix matrix;
    /* access modifiers changed from: private */
    public OnCornerSizeChangeListener onCornerSizeChangeListener;
    private final Path path;
    private final RectF pathBounds;
    /* access modifiers changed from: private */
    public boolean pathDirty;
    private final Path pathInsetByStroke;
    private final ShapeAppearancePathProvider pathProvider;
    private final ShapeAppearancePathProvider.PathListener pathShadowListener;
    private final RectF rectF;
    private int resolvedTintColor;
    private final Region scratchRegion;
    private boolean shadowBitmapDrawingEnable;
    private final ShadowRenderer shadowRenderer;
    /* access modifiers changed from: private */
    public float[] springAnimatedCornerSizes;
    private float[] springAnimatedStrokeCornerSizes;
    private final ShapeAppearanceModel.CornerSizeUnaryOperator strokeInsetCornerSizeUnaryOperator;
    private final Paint strokePaint;
    /* access modifiers changed from: private */
    public boolean strokePathDirty;
    private ShapeAppearanceModel strokeShapeAppearanceModel;
    private PorterDuffColorFilter strokeTintFilter;
    private PorterDuffColorFilter tintFilter;
    private final Region transparentRegion;

    public interface OnCornerSizeChangeListener {
        void onCornerSizeChange(float f);
    }

    private static int modulateAlpha(int i, int i2) {
        return (i * (i2 + (i2 >>> 7))) >>> 8;
    }

    public int getOpacity() {
        return -3;
    }

    static {
        int i = 0;
        Paint paint = new Paint(1);
        clearPaint = paint;
        paint.setColor(-1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        while (true) {
            SpringAnimatedCornerSizeProperty[] springAnimatedCornerSizePropertyArr = CORNER_SIZES_IN_PX;
            if (i < springAnimatedCornerSizePropertyArr.length) {
                springAnimatedCornerSizePropertyArr[i] = new SpringAnimatedCornerSizeProperty(i);
                i++;
            } else {
                return;
            }
        }
    }

    public static MaterialShapeDrawable createWithElevationOverlay(Context context, float f, ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(MaterialColors.getColor(context, R$attr.colorSurface, MaterialShapeDrawable.class.getSimpleName()));
        }
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        materialShapeDrawable.initializeElevationOverlay(context);
        materialShapeDrawable.setFillColor(colorStateList);
        materialShapeDrawable.setElevation(f);
        return materialShapeDrawable;
    }

    public MaterialShapeDrawable() {
        this(new ShapeAppearanceModel());
    }

    public MaterialShapeDrawable(Context context, AttributeSet attributeSet, int i, int i2) {
        this(ShapeAppearanceModel.builder(context, attributeSet, i, i2).build());
    }

    public MaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
        this(new MaterialShapeDrawableState(shapeAppearanceModel, (ElevationOverlayProvider) null));
    }

    protected MaterialShapeDrawable(MaterialShapeDrawableState materialShapeDrawableState) {
        ShapeAppearancePathProvider shapeAppearancePathProvider;
        this.strokeInsetCornerSizeUnaryOperator = new ShapeAppearanceModel.CornerSizeUnaryOperator() {
            public CornerSize apply(CornerSize cornerSize) {
                if (cornerSize instanceof RelativeCornerSize) {
                    return cornerSize;
                }
                return new AdjustedCornerSize(-MaterialShapeDrawable.this.getStrokeInsetLength(), cornerSize);
            }
        };
        this.cornerShadowOperation = new ShapePath.ShadowCompatOperation[4];
        this.edgeShadowOperation = new ShapePath.ShadowCompatOperation[4];
        this.containsIncompatibleShadowOp = new BitSet(8);
        this.matrix = new Matrix();
        this.path = new Path();
        this.pathInsetByStroke = new Path();
        this.rectF = new RectF();
        this.insetRectF = new RectF();
        this.transparentRegion = new Region();
        this.scratchRegion = new Region();
        Paint paint = new Paint(1);
        this.fillPaint = paint;
        Paint paint2 = new Paint(1);
        this.strokePaint = paint2;
        this.shadowRenderer = new ShadowRenderer();
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            shapeAppearancePathProvider = ShapeAppearancePathProvider.getInstance();
        } else {
            shapeAppearancePathProvider = new ShapeAppearancePathProvider();
        }
        this.pathProvider = shapeAppearancePathProvider;
        this.pathBounds = new RectF();
        this.shadowBitmapDrawingEnable = true;
        this.boundsIsEmpty = true;
        this.cornerSpringAnimations = new SpringAnimation[4];
        this.drawableState = materialShapeDrawableState;
        paint2.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        updateTintFilter();
        updateColorsForState(getState());
        this.pathShadowListener = new ShapeAppearancePathProvider.PathListener() {
            public void onCornerPathCreated(ShapePath shapePath, Matrix matrix, int i) {
                MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(i, shapePath.containsIncompatibleShadowOp());
                MaterialShapeDrawable.this.cornerShadowOperation[i] = shapePath.createShadowCompatOperation(matrix);
            }

            public void onEdgePathCreated(ShapePath shapePath, Matrix matrix, int i) {
                MaterialShapeDrawable.this.containsIncompatibleShadowOp.set(i + 4, shapePath.containsIncompatibleShadowOp());
                MaterialShapeDrawable.this.edgeShadowOperation[i] = shapePath.createShadowCompatOperation(matrix);
            }
        };
    }

    public Drawable.ConstantState getConstantState() {
        return this.drawableState;
    }

    public Drawable mutate() {
        this.drawableState = new MaterialShapeDrawableState(this.drawableState);
        return this;
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        materialShapeDrawableState.shapeAppearanceModel = shapeAppearanceModel;
        materialShapeDrawableState.stateListShapeAppearanceModel = null;
        this.springAnimatedCornerSizes = null;
        this.springAnimatedStrokeCornerSizes = null;
        invalidateSelf();
    }

    public ShapeAppearanceModel getShapeAppearanceModel() {
        return this.drawableState.shapeAppearanceModel;
    }

    public void setStateListShapeAppearanceModel(StateListShapeAppearanceModel stateListShapeAppearanceModel) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.stateListShapeAppearanceModel != stateListShapeAppearanceModel) {
            materialShapeDrawableState.stateListShapeAppearanceModel = stateListShapeAppearanceModel;
            updateShape(getState(), true);
            invalidateSelf();
        }
    }

    public void setCornerSpringForce(SpringForce springForce) {
        if (this.cornerSpringForce != springForce) {
            this.cornerSpringForce = springForce;
            int i = 0;
            while (true) {
                SpringAnimation[] springAnimationArr = this.cornerSpringAnimations;
                if (i < springAnimationArr.length) {
                    if (springAnimationArr[i] == null) {
                        springAnimationArr[i] = new SpringAnimation(this, CORNER_SIZES_IN_PX[i]);
                    }
                    this.cornerSpringAnimations[i].setSpring(new SpringForce().setDampingRatio(springForce.getDampingRatio()).setStiffness(springForce.getStiffness()));
                    i++;
                } else {
                    updateShape(getState(), true);
                    invalidateSelf();
                    return;
                }
            }
        }
    }

    public void setFillColor(ColorStateList colorStateList) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.fillColor != colorStateList) {
            materialShapeDrawableState.fillColor = colorStateList;
            onStateChange(getState());
        }
    }

    public ColorStateList getFillColor() {
        return this.drawableState.fillColor;
    }

    public void setStrokeColor(ColorStateList colorStateList) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.strokeColor != colorStateList) {
            materialShapeDrawableState.strokeColor = colorStateList;
            onStateChange(getState());
        }
    }

    public void setTintMode(PorterDuff.Mode mode) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.tintMode != mode) {
            materialShapeDrawableState.tintMode = mode;
            updateTintFilter();
            invalidateSelfIgnoreShape();
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        this.drawableState.tintList = colorStateList;
        updateTintFilter();
        invalidateSelfIgnoreShape();
    }

    public void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    public void setStroke(float f, int i) {
        setStrokeWidth(f);
        setStrokeColor(ColorStateList.valueOf(i));
    }

    public void setStroke(float f, ColorStateList colorStateList) {
        setStrokeWidth(f);
        setStrokeColor(colorStateList);
    }

    public void setStrokeWidth(float f) {
        this.drawableState.strokeWidth = f;
        invalidateSelf();
    }

    public int getAlpha() {
        return this.drawableState.alpha;
    }

    public void setAlpha(int i) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.alpha != i) {
            materialShapeDrawableState.alpha = i;
            invalidateSelfIgnoreShape();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.drawableState.colorFilter = colorFilter;
        invalidateSelfIgnoreShape();
    }

    public Region getTransparentRegion() {
        this.transparentRegion.set(getBounds());
        calculatePath(getBoundsAsRectF(), this.path);
        this.scratchRegion.setPath(this.path, this.transparentRegion);
        this.transparentRegion.op(this.scratchRegion, Region.Op.DIFFERENCE);
        return this.transparentRegion;
    }

    /* access modifiers changed from: protected */
    public RectF getBoundsAsRectF() {
        this.rectF.set(getBounds());
        return this.rectF;
    }

    public void setCornerSize(float f) {
        setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(f));
    }

    public void setCornerSize(CornerSize cornerSize) {
        setShapeAppearanceModel(this.drawableState.shapeAppearanceModel.withCornerSize(cornerSize));
    }

    public boolean getPadding(Rect rect) {
        Rect rect2 = this.drawableState.padding;
        if (rect2 == null) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.padding == null) {
            materialShapeDrawableState.padding = new Rect();
        }
        this.drawableState.padding.set(i, i2, i3, i4);
        invalidateSelf();
    }

    public boolean isElevationOverlayEnabled() {
        ElevationOverlayProvider elevationOverlayProvider = this.drawableState.elevationOverlayProvider;
        return elevationOverlayProvider != null && elevationOverlayProvider.isThemeElevationOverlayEnabled();
    }

    public void initializeElevationOverlay(Context context) {
        this.drawableState.elevationOverlayProvider = new ElevationOverlayProvider(context);
        updateZ();
    }

    /* access modifiers changed from: protected */
    public int compositeElevationOverlayIfNeeded(int i) {
        float z = getZ() + getParentAbsoluteElevation();
        ElevationOverlayProvider elevationOverlayProvider = this.drawableState.elevationOverlayProvider;
        return elevationOverlayProvider != null ? elevationOverlayProvider.compositeOverlayIfNeeded(i, z) : i;
    }

    public float getInterpolation() {
        return this.drawableState.interpolation;
    }

    public void setInterpolation(float f) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.interpolation != f) {
            materialShapeDrawableState.interpolation = f;
            this.pathDirty = true;
            this.strokePathDirty = true;
            invalidateSelf();
        }
    }

    public float getParentAbsoluteElevation() {
        return this.drawableState.parentAbsoluteElevation;
    }

    public void setParentAbsoluteElevation(float f) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.parentAbsoluteElevation != f) {
            materialShapeDrawableState.parentAbsoluteElevation = f;
            updateZ();
        }
    }

    public float getElevation() {
        return this.drawableState.elevation;
    }

    public void setElevation(float f) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.elevation != f) {
            materialShapeDrawableState.elevation = f;
            updateZ();
        }
    }

    public float getTranslationZ() {
        return this.drawableState.translationZ;
    }

    public float getZ() {
        return getElevation() + getTranslationZ();
    }

    private void updateZ() {
        float z = getZ();
        this.drawableState.shadowCompatRadius = (int) Math.ceil((double) (0.75f * z));
        this.drawableState.shadowCompatOffset = (int) Math.ceil((double) (z * 0.25f));
        updateTintFilter();
        invalidateSelfIgnoreShape();
    }

    public void setShadowBitmapDrawingEnable(boolean z) {
        this.shadowBitmapDrawingEnable = z;
    }

    public boolean requiresCompatShadow() {
        if (isRoundRect()) {
            return false;
        }
        this.path.isConvex();
        return false;
    }

    public void invalidateSelf() {
        this.pathDirty = true;
        this.strokePathDirty = true;
        super.invalidateSelf();
    }

    private void invalidateSelfIgnoreShape() {
        super.invalidateSelf();
    }

    public void setShadowColor(int i) {
        this.shadowRenderer.setShadowColor(i);
        this.drawableState.useTintColorForShadow = false;
        invalidateSelfIgnoreShape();
    }

    private boolean hasCompatShadow() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        int i = materialShapeDrawableState.shadowCompatMode;
        if (i == 1 || materialShapeDrawableState.shadowCompatRadius <= 0) {
            return false;
        }
        if (i == 2 || requiresCompatShadow()) {
            return true;
        }
        return false;
    }

    private boolean hasFill() {
        Paint.Style style = this.drawableState.paintStyle;
        return style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.FILL;
    }

    private boolean hasStroke() {
        Paint.Style style = this.drawableState.paintStyle;
        return (style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.STROKE) && this.strokePaint.getStrokeWidth() > 0.0f;
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect rect) {
        this.pathDirty = true;
        this.strokePathDirty = true;
        super.onBoundsChange(rect);
        if (this.drawableState.stateListShapeAppearanceModel != null && !rect.isEmpty()) {
            updateShape(getState(), this.boundsIsEmpty);
        }
        this.boundsIsEmpty = rect.isEmpty();
    }

    public void draw(Canvas canvas) {
        this.fillPaint.setColorFilter(this.tintFilter);
        int alpha = this.fillPaint.getAlpha();
        this.fillPaint.setAlpha(modulateAlpha(alpha, this.drawableState.alpha));
        this.strokePaint.setColorFilter(this.strokeTintFilter);
        this.strokePaint.setStrokeWidth(this.drawableState.strokeWidth);
        int alpha2 = this.strokePaint.getAlpha();
        this.strokePaint.setAlpha(modulateAlpha(alpha2, this.drawableState.alpha));
        if (hasFill()) {
            if (this.pathDirty) {
                calculatePath(getBoundsAsRectF(), this.path);
                this.pathDirty = false;
            }
            maybeDrawCompatShadow(canvas);
            drawFillShape(canvas);
        }
        if (hasStroke()) {
            if (this.strokePathDirty) {
                calculateStrokePath();
                this.strokePathDirty = false;
            }
            drawStrokeShape(canvas);
        }
        this.fillPaint.setAlpha(alpha);
        this.strokePaint.setAlpha(alpha2);
    }

    private void maybeDrawCompatShadow(Canvas canvas) {
        if (hasCompatShadow()) {
            canvas.save();
            prepareCanvasForShadow(canvas);
            if (!this.shadowBitmapDrawingEnable) {
                drawCompatShadow(canvas);
                canvas.restore();
                return;
            }
            int width = (int) (this.pathBounds.width() - ((float) getBounds().width()));
            int height = (int) (this.pathBounds.height() - ((float) getBounds().height()));
            if (width < 0 || height < 0) {
                throw new IllegalStateException("Invalid shadow bounds. Check that the treatments result in a valid path.");
            }
            Bitmap createBitmap = Bitmap.createBitmap(((int) this.pathBounds.width()) + (this.drawableState.shadowCompatRadius * 2) + width, ((int) this.pathBounds.height()) + (this.drawableState.shadowCompatRadius * 2) + height, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(createBitmap);
            float f = (float) ((getBounds().left - this.drawableState.shadowCompatRadius) - width);
            float f2 = (float) ((getBounds().top - this.drawableState.shadowCompatRadius) - height);
            canvas2.translate(-f, -f2);
            drawCompatShadow(canvas2);
            canvas.drawBitmap(createBitmap, f, f2, (Paint) null);
            createBitmap.recycle();
            canvas.restore();
        }
    }

    /* access modifiers changed from: protected */
    public void drawShape(Canvas canvas, Paint paint, Path path2, RectF rectF2) {
        drawShape(canvas, paint, path2, this.drawableState.shapeAppearanceModel, this.springAnimatedCornerSizes, rectF2);
    }

    private void drawShape(Canvas canvas, Paint paint, Path path2, ShapeAppearanceModel shapeAppearanceModel, float[] fArr, RectF rectF2) {
        float calculateRoundRectCornerSize = calculateRoundRectCornerSize(rectF2, shapeAppearanceModel, fArr);
        if (calculateRoundRectCornerSize >= 0.0f) {
            float f = calculateRoundRectCornerSize * this.drawableState.interpolation;
            canvas.drawRoundRect(rectF2, f, f, paint);
            return;
        }
        canvas.drawPath(path2, paint);
    }

    private void drawFillShape(Canvas canvas) {
        drawShape(canvas, this.fillPaint, this.path, this.drawableState.shapeAppearanceModel, this.springAnimatedCornerSizes, getBoundsAsRectF());
    }

    /* access modifiers changed from: protected */
    public void drawStrokeShape(Canvas canvas) {
        drawShape(canvas, this.strokePaint, this.pathInsetByStroke, this.strokeShapeAppearanceModel, this.springAnimatedStrokeCornerSizes, getBoundsInsetByStroke());
    }

    private void prepareCanvasForShadow(Canvas canvas) {
        canvas.translate((float) getShadowOffsetX(), (float) getShadowOffsetY());
    }

    private void drawCompatShadow(Canvas canvas) {
        if (this.containsIncompatibleShadowOp.cardinality() > 0) {
            Log.w(TAG, "Compatibility shadow requested but can't be drawn for all operations in this shape.");
        }
        if (this.drawableState.shadowCompatOffset != 0) {
            canvas.drawPath(this.path, this.shadowRenderer.getShadowPaint());
        }
        for (int i = 0; i < 4; i++) {
            this.cornerShadowOperation[i].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, canvas);
            this.edgeShadowOperation[i].draw(this.shadowRenderer, this.drawableState.shadowCompatRadius, canvas);
        }
        if (this.shadowBitmapDrawingEnable) {
            int shadowOffsetX = getShadowOffsetX();
            int shadowOffsetY = getShadowOffsetY();
            canvas.translate((float) (-shadowOffsetX), (float) (-shadowOffsetY));
            canvas.drawPath(this.path, clearPaint);
            canvas.translate((float) shadowOffsetX, (float) shadowOffsetY);
        }
    }

    private float calculateRoundRectCornerSize(RectF rectF2, ShapeAppearanceModel shapeAppearanceModel, float[] fArr) {
        if (fArr == null) {
            if (shapeAppearanceModel.isRoundRect(rectF2)) {
                return shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(rectF2);
            }
            return -1.0f;
        } else if (!MathUtils.areAllElementsEqual(fArr) || !shapeAppearanceModel.hasRoundedCorners()) {
            return -1.0f;
        } else {
            return fArr[0];
        }
    }

    public int getShadowOffsetX() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        return (int) (((double) materialShapeDrawableState.shadowCompatOffset) * Math.sin(Math.toRadians((double) materialShapeDrawableState.shadowCompatRotation)));
    }

    public int getShadowOffsetY() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        return (int) (((double) materialShapeDrawableState.shadowCompatOffset) * Math.cos(Math.toRadians((double) materialShapeDrawableState.shadowCompatRotation)));
    }

    /* access modifiers changed from: protected */
    public final void calculatePathForSize(RectF rectF2, Path path2) {
        ShapeAppearancePathProvider shapeAppearancePathProvider = this.pathProvider;
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        RectF rectF3 = rectF2;
        shapeAppearancePathProvider.calculatePath(materialShapeDrawableState.shapeAppearanceModel, this.springAnimatedCornerSizes, materialShapeDrawableState.interpolation, rectF3, this.pathShadowListener, path2);
    }

    private void calculateStrokePath() {
        updateStrokeShapeAppearanceModels();
        this.pathProvider.calculatePath(this.strokeShapeAppearanceModel, this.springAnimatedStrokeCornerSizes, this.drawableState.interpolation, getBoundsInsetByStroke(), (ShapeAppearancePathProvider.PathListener) null, this.pathInsetByStroke);
    }

    private void updateStrokeShapeAppearanceModels() {
        this.strokeShapeAppearanceModel = getShapeAppearanceModel().withTransformedCornerSizes(this.strokeInsetCornerSizeUnaryOperator);
        float[] fArr = this.springAnimatedCornerSizes;
        if (fArr == null) {
            this.springAnimatedStrokeCornerSizes = null;
            return;
        }
        if (this.springAnimatedStrokeCornerSizes == null) {
            this.springAnimatedStrokeCornerSizes = new float[fArr.length];
        }
        float strokeInsetLength = getStrokeInsetLength();
        int i = 0;
        while (true) {
            float[] fArr2 = this.springAnimatedCornerSizes;
            if (i < fArr2.length) {
                this.springAnimatedStrokeCornerSizes[i] = Math.max(0.0f, fArr2[i] - strokeInsetLength);
                i++;
            } else {
                return;
            }
        }
    }

    public void getOutline(Outline outline) {
        if (this.drawableState.shadowCompatMode != 2) {
            RectF boundsAsRectF = getBoundsAsRectF();
            if (!boundsAsRectF.isEmpty()) {
                float calculateRoundRectCornerSize = calculateRoundRectCornerSize(boundsAsRectF, this.drawableState.shapeAppearanceModel, this.springAnimatedCornerSizes);
                if (calculateRoundRectCornerSize >= 0.0f) {
                    outline.setRoundRect(getBounds(), calculateRoundRectCornerSize * this.drawableState.interpolation);
                    return;
                }
                if (this.pathDirty) {
                    calculatePath(boundsAsRectF, this.path);
                    this.pathDirty = false;
                }
                DrawableUtils.setOutlineToPath(outline, this.path);
            }
        }
    }

    private void calculatePath(RectF rectF2, Path path2) {
        calculatePathForSize(rectF2, path2);
        if (this.drawableState.scale != 1.0f) {
            this.matrix.reset();
            Matrix matrix2 = this.matrix;
            float f = this.drawableState.scale;
            matrix2.setScale(f, f, rectF2.width() / 2.0f, rectF2.height() / 2.0f);
            path2.transform(this.matrix);
        }
        path2.computeBounds(this.pathBounds, true);
    }

    private boolean updateTintFilter() {
        PorterDuffColorFilter porterDuffColorFilter = this.tintFilter;
        PorterDuffColorFilter porterDuffColorFilter2 = this.strokeTintFilter;
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        this.tintFilter = calculateTintFilter(materialShapeDrawableState.tintList, materialShapeDrawableState.tintMode, this.fillPaint, true);
        MaterialShapeDrawableState materialShapeDrawableState2 = this.drawableState;
        this.strokeTintFilter = calculateTintFilter(materialShapeDrawableState2.strokeTintList, materialShapeDrawableState2.tintMode, this.strokePaint, false);
        MaterialShapeDrawableState materialShapeDrawableState3 = this.drawableState;
        if (materialShapeDrawableState3.useTintColorForShadow) {
            this.shadowRenderer.setShadowColor(materialShapeDrawableState3.tintList.getColorForState(getState(), 0));
        }
        if (!ObjectsCompat.equals(porterDuffColorFilter, this.tintFilter) || !ObjectsCompat.equals(porterDuffColorFilter2, this.strokeTintFilter)) {
            return true;
        }
        return false;
    }

    private PorterDuffColorFilter calculateTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, Paint paint, boolean z) {
        if (colorStateList == null || mode == null) {
            return calculatePaintColorTintFilter(paint, z);
        }
        return calculateTintColorTintFilter(colorStateList, mode, z);
    }

    private PorterDuffColorFilter calculatePaintColorTintFilter(Paint paint, boolean z) {
        if (!z) {
            return null;
        }
        int color = paint.getColor();
        int compositeElevationOverlayIfNeeded = compositeElevationOverlayIfNeeded(color);
        this.resolvedTintColor = compositeElevationOverlayIfNeeded;
        if (compositeElevationOverlayIfNeeded != color) {
            return new PorterDuffColorFilter(compositeElevationOverlayIfNeeded, PorterDuff.Mode.SRC_IN);
        }
        return null;
    }

    private PorterDuffColorFilter calculateTintColorTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, boolean z) {
        int colorForState = colorStateList.getColorForState(getState(), 0);
        if (z) {
            colorForState = compositeElevationOverlayIfNeeded(colorForState);
        }
        this.resolvedTintColor = colorForState;
        return new PorterDuffColorFilter(colorForState, mode);
    }

    public boolean isStateful() {
        if (super.isStateful()) {
            return true;
        }
        ColorStateList colorStateList = this.drawableState.tintList;
        if (colorStateList != null && colorStateList.isStateful()) {
            return true;
        }
        ColorStateList colorStateList2 = this.drawableState.strokeTintList;
        if (colorStateList2 != null && colorStateList2.isStateful()) {
            return true;
        }
        ColorStateList colorStateList3 = this.drawableState.strokeColor;
        if (colorStateList3 != null && colorStateList3.isStateful()) {
            return true;
        }
        ColorStateList colorStateList4 = this.drawableState.fillColor;
        if (colorStateList4 != null && colorStateList4.isStateful()) {
            return true;
        }
        StateListShapeAppearanceModel stateListShapeAppearanceModel = this.drawableState.stateListShapeAppearanceModel;
        return stateListShapeAppearanceModel != null && stateListShapeAppearanceModel.isStateful();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] iArr) {
        if (this.drawableState.stateListShapeAppearanceModel != null) {
            updateShape(iArr);
        }
        boolean z = updateColorsForState(iArr) || updateTintFilter();
        if (z) {
            invalidateSelf();
        }
        return z;
    }

    private void updateShape(int[] iArr) {
        updateShape(iArr, false);
    }

    private void updateShape(int[] iArr, boolean z) {
        RectF boundsAsRectF = getBoundsAsRectF();
        if (this.drawableState.stateListShapeAppearanceModel != null && !boundsAsRectF.isEmpty()) {
            boolean z2 = z | (this.cornerSpringForce == null);
            if (this.springAnimatedCornerSizes == null) {
                this.springAnimatedCornerSizes = new float[4];
            }
            ShapeAppearanceModel shapeForState = this.drawableState.stateListShapeAppearanceModel.getShapeForState(iArr);
            for (int i = 0; i < 4; i++) {
                float cornerSize = this.pathProvider.getCornerSizeForIndex(i, shapeForState).getCornerSize(boundsAsRectF);
                if (z2) {
                    this.springAnimatedCornerSizes[i] = cornerSize;
                }
                SpringAnimation springAnimation = this.cornerSpringAnimations[i];
                if (springAnimation != null) {
                    springAnimation.animateToFinalPosition(cornerSize);
                    if (z2) {
                        this.cornerSpringAnimations[i].skipToEnd();
                    }
                }
            }
            if (z2) {
                invalidateSelf();
            }
        }
    }

    private boolean updateColorsForState(int[] iArr) {
        boolean z;
        int color;
        int colorForState;
        int color2;
        int colorForState2;
        if (this.drawableState.fillColor == null || (color2 = this.fillPaint.getColor()) == (colorForState2 = this.drawableState.fillColor.getColorForState(iArr, color2))) {
            z = false;
        } else {
            this.fillPaint.setColor(colorForState2);
            z = true;
        }
        if (this.drawableState.strokeColor == null || (color = this.strokePaint.getColor()) == (colorForState = this.drawableState.strokeColor.getColorForState(iArr, color))) {
            return z;
        }
        this.strokePaint.setColor(colorForState);
        return true;
    }

    /* access modifiers changed from: private */
    public float getStrokeInsetLength() {
        if (hasStroke()) {
            return this.strokePaint.getStrokeWidth() / 2.0f;
        }
        return 0.0f;
    }

    private RectF getBoundsInsetByStroke() {
        this.insetRectF.set(getBoundsAsRectF());
        float strokeInsetLength = getStrokeInsetLength();
        this.insetRectF.inset(strokeInsetLength, strokeInsetLength);
        return this.insetRectF;
    }

    public float getTopLeftCornerResolvedSize() {
        float[] fArr = this.springAnimatedCornerSizes;
        if (fArr != null) {
            return fArr[3];
        }
        return this.drawableState.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public float getTopRightCornerResolvedSize() {
        float[] fArr = this.springAnimatedCornerSizes;
        if (fArr != null) {
            return fArr[0];
        }
        return this.drawableState.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public float getBottomLeftCornerResolvedSize() {
        float[] fArr = this.springAnimatedCornerSizes;
        if (fArr != null) {
            return fArr[2];
        }
        return this.drawableState.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public float getBottomRightCornerResolvedSize() {
        float[] fArr = this.springAnimatedCornerSizes;
        if (fArr != null) {
            return fArr[1];
        }
        return this.drawableState.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(getBoundsAsRectF());
    }

    public boolean isRoundRect() {
        if (this.drawableState.shapeAppearanceModel.isRoundRect(getBoundsAsRectF())) {
            return true;
        }
        float[] fArr = this.springAnimatedCornerSizes;
        return fArr != null && MathUtils.areAllElementsEqual(fArr) && this.drawableState.shapeAppearanceModel.hasRoundedCorners();
    }

    public void setOnCornerSizeChangeListener(OnCornerSizeChangeListener onCornerSizeChangeListener2) {
        this.onCornerSizeChangeListener = onCornerSizeChangeListener2;
    }

    public float getCornerSizeDiffX() {
        float[] fArr = this.springAnimatedCornerSizes;
        if (fArr != null) {
            return (((fArr[3] + fArr[2]) - fArr[1]) - fArr[0]) / 2.0f;
        }
        RectF boundsAsRectF = getBoundsAsRectF();
        return (((this.pathProvider.getCornerSizeForIndex(3, getShapeAppearanceModel()).getCornerSize(boundsAsRectF) + this.pathProvider.getCornerSizeForIndex(2, getShapeAppearanceModel()).getCornerSize(boundsAsRectF)) - this.pathProvider.getCornerSizeForIndex(1, getShapeAppearanceModel()).getCornerSize(boundsAsRectF)) - this.pathProvider.getCornerSizeForIndex(0, getShapeAppearanceModel()).getCornerSize(boundsAsRectF)) / 2.0f;
    }

    class SpringAnimatedCornerSizeProperty extends FloatPropertyCompat {
        private final int index;

        SpringAnimatedCornerSizeProperty(int i) {
            super("cornerSizeAtIndex" + i);
            this.index = i;
        }

        public float getValue(MaterialShapeDrawable materialShapeDrawable) {
            if (materialShapeDrawable.springAnimatedCornerSizes != null) {
                return materialShapeDrawable.springAnimatedCornerSizes[this.index];
            }
            return 0.0f;
        }

        public void setValue(MaterialShapeDrawable materialShapeDrawable, float f) {
            if (materialShapeDrawable.springAnimatedCornerSizes != null && materialShapeDrawable.springAnimatedCornerSizes[this.index] != f) {
                materialShapeDrawable.springAnimatedCornerSizes[this.index] = f;
                if (materialShapeDrawable.onCornerSizeChangeListener != null) {
                    materialShapeDrawable.onCornerSizeChangeListener.onCornerSizeChange(materialShapeDrawable.getCornerSizeDiffX());
                }
                materialShapeDrawable.invalidateSelf();
            }
        }
    }

    public class MaterialShapeDrawableState extends Drawable.ConstantState {
        int alpha = 255;
        ColorFilter colorFilter;
        float elevation = 0.0f;
        ElevationOverlayProvider elevationOverlayProvider;
        ColorStateList fillColor = null;
        float interpolation = 1.0f;
        Rect padding = null;
        Paint.Style paintStyle = Paint.Style.FILL_AND_STROKE;
        float parentAbsoluteElevation = 0.0f;
        float scale = 1.0f;
        int shadowCompatMode = 0;
        int shadowCompatOffset = 0;
        int shadowCompatRadius = 0;
        int shadowCompatRotation = 0;
        ShapeAppearanceModel shapeAppearanceModel;
        StateListShapeAppearanceModel stateListShapeAppearanceModel;
        ColorStateList strokeColor = null;
        ColorStateList strokeTintList = null;
        float strokeWidth;
        ColorStateList tintList = null;
        PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
        float translationZ = 0.0f;
        boolean useTintColorForShadow = false;

        public int getChangingConfigurations() {
            return 0;
        }

        public MaterialShapeDrawableState(ShapeAppearanceModel shapeAppearanceModel2, ElevationOverlayProvider elevationOverlayProvider2) {
            this.shapeAppearanceModel = shapeAppearanceModel2;
            this.elevationOverlayProvider = elevationOverlayProvider2;
        }

        public MaterialShapeDrawableState(MaterialShapeDrawableState materialShapeDrawableState) {
            this.shapeAppearanceModel = materialShapeDrawableState.shapeAppearanceModel;
            this.stateListShapeAppearanceModel = materialShapeDrawableState.stateListShapeAppearanceModel;
            this.elevationOverlayProvider = materialShapeDrawableState.elevationOverlayProvider;
            this.strokeWidth = materialShapeDrawableState.strokeWidth;
            this.colorFilter = materialShapeDrawableState.colorFilter;
            this.fillColor = materialShapeDrawableState.fillColor;
            this.strokeColor = materialShapeDrawableState.strokeColor;
            this.tintMode = materialShapeDrawableState.tintMode;
            this.tintList = materialShapeDrawableState.tintList;
            this.alpha = materialShapeDrawableState.alpha;
            this.scale = materialShapeDrawableState.scale;
            this.shadowCompatOffset = materialShapeDrawableState.shadowCompatOffset;
            this.shadowCompatMode = materialShapeDrawableState.shadowCompatMode;
            this.useTintColorForShadow = materialShapeDrawableState.useTintColorForShadow;
            this.interpolation = materialShapeDrawableState.interpolation;
            this.parentAbsoluteElevation = materialShapeDrawableState.parentAbsoluteElevation;
            this.elevation = materialShapeDrawableState.elevation;
            this.translationZ = materialShapeDrawableState.translationZ;
            this.shadowCompatRadius = materialShapeDrawableState.shadowCompatRadius;
            this.shadowCompatRotation = materialShapeDrawableState.shadowCompatRotation;
            this.strokeTintList = materialShapeDrawableState.strokeTintList;
            this.paintStyle = materialShapeDrawableState.paintStyle;
            if (materialShapeDrawableState.padding != null) {
                this.padding = new Rect(materialShapeDrawableState.padding);
            }
        }

        public Drawable newDrawable() {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(this);
            boolean unused = materialShapeDrawable.pathDirty = true;
            boolean unused2 = materialShapeDrawable.strokePathDirty = true;
            return materialShapeDrawable;
        }
    }
}
