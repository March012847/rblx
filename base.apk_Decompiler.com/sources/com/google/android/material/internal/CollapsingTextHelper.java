package com.google.android.material.internal;

import android.animation.TimeInterpolator;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import androidx.core.math.MathUtils;
import androidx.core.text.TextDirectionHeuristicCompat;
import androidx.core.text.TextDirectionHeuristicsCompat;
import androidx.core.util.Preconditions;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.resources.TypefaceUtils;

public final class CollapsingTextHelper {
    private boolean alignBaselineAtBottom;
    private boolean boundsChanged;
    private final Rect collapsedBounds;
    private Rect collapsedBoundsForPlacement;
    private float collapsedDrawX;
    private float collapsedDrawY;
    private CancelableFontCallback collapsedFontCallback;
    private int collapsedHeight = -1;
    private float collapsedLetterSpacing;
    private int collapsedMaxLines = 1;
    private ColorStateList collapsedShadowColor;
    private float collapsedShadowDx;
    private float collapsedShadowDy;
    private float collapsedShadowRadius;
    private float collapsedTextBlend;
    private ColorStateList collapsedTextColor;
    private int collapsedTextGravity = 16;
    private float collapsedTextSize = 15.0f;
    private float collapsedTextWidth;
    private Typeface collapsedTypeface;
    private Typeface collapsedTypefaceBold;
    private Typeface collapsedTypefaceDefault;
    private final RectF currentBounds;
    private float currentDrawX;
    private float currentDrawY;
    private float currentLetterSpacing;
    private int currentMaxLines;
    private int currentOffsetY;
    private int currentShadowColor;
    private float currentShadowDx;
    private float currentShadowDy;
    private float currentShadowRadius;
    private float currentTextSize;
    private Typeface currentTypeface;
    private final Rect expandedBounds;
    private float expandedDrawX;
    private float expandedDrawY;
    private CancelableFontCallback expandedFontCallback;
    private float expandedFraction;
    private int expandedHeight = -1;
    private float expandedLetterSpacing;
    private int expandedLineCount;
    private int expandedMaxLines = 1;
    private ColorStateList expandedShadowColor;
    private float expandedShadowDx;
    private float expandedShadowDy;
    private float expandedShadowRadius;
    private float expandedTextBlend;
    private ColorStateList expandedTextColor;
    private int expandedTextGravity = 16;
    private float expandedTextSize = 15.0f;
    private Typeface expandedTypeface;
    private Typeface expandedTypefaceBold;
    private Typeface expandedTypefaceDefault;
    private boolean fadeModeEnabled;
    private float fadeModeStartFraction;
    private float fadeModeThresholdFraction;
    private int hyphenationFrequency = StaticLayoutBuilderCompat.DEFAULT_HYPHENATION_FREQUENCY;
    private boolean isRtl;
    private boolean isRtlTextDirectionHeuristicsEnabled = true;
    private float lineSpacingAdd = 0.0f;
    private float lineSpacingMultiplier = 1.0f;
    private TimeInterpolator positionInterpolator;
    private float scale;
    private int[] state;
    private StaticLayoutBuilderConfigurer staticLayoutBuilderConfigurer;
    private CharSequence text;
    private StaticLayout textLayout;
    private final TextPaint textPaint;
    private TimeInterpolator textSizeInterpolator;
    private CharSequence textToDraw;
    private CharSequence textToDrawCollapsed;
    private TextUtils.TruncateAt titleTextEllipsize = TextUtils.TruncateAt.END;
    private final TextPaint tmpPaint;
    private final View view;

    public CollapsingTextHelper(View view2) {
        this.view = view2;
        TextPaint textPaint2 = new TextPaint(129);
        this.textPaint = textPaint2;
        this.tmpPaint = new TextPaint(textPaint2);
        this.collapsedBounds = new Rect();
        this.expandedBounds = new Rect();
        this.currentBounds = new RectF();
        this.fadeModeThresholdFraction = calculateFadeModeThresholdFraction();
        maybeUpdateFontWeightAdjustment(view2.getContext().getResources().getConfiguration());
    }

    public void setCollapsedMaxLines(int i) {
        if (i != this.collapsedMaxLines) {
            this.collapsedMaxLines = i;
            recalculate();
        }
    }

    public void setTextSizeInterpolator(TimeInterpolator timeInterpolator) {
        this.textSizeInterpolator = timeInterpolator;
        recalculate();
    }

    public void setPositionInterpolator(TimeInterpolator timeInterpolator) {
        this.positionInterpolator = timeInterpolator;
        recalculate();
    }

    public void setExpandedTextSize(float f) {
        if (this.expandedTextSize != f) {
            this.expandedTextSize = f;
            recalculate();
        }
    }

    public void setCollapsedTextColor(ColorStateList colorStateList) {
        if (this.collapsedTextColor != colorStateList) {
            this.collapsedTextColor = colorStateList;
            recalculate();
        }
    }

    public void setCollapsedAndExpandedTextColor(ColorStateList colorStateList) {
        if (this.collapsedTextColor != colorStateList || this.expandedTextColor != colorStateList) {
            this.collapsedTextColor = colorStateList;
            this.expandedTextColor = colorStateList;
            recalculate();
        }
    }

    public void setExpandedLetterSpacing(float f) {
        if (this.expandedLetterSpacing != f) {
            this.expandedLetterSpacing = f;
            recalculate();
        }
    }

    public void setExpandedBounds(int i, int i2, int i3, int i4, boolean z) {
        if (!rectEquals(this.expandedBounds, i, i2, i3, i4) || z != this.alignBaselineAtBottom) {
            this.expandedBounds.set(i, i2, i3, i4);
            this.boundsChanged = true;
            this.alignBaselineAtBottom = z;
        }
    }

    public void setExpandedBounds(int i, int i2, int i3, int i4) {
        setExpandedBounds(i, i2, i3, i4, true);
    }

    public void setExpandedBounds(Rect rect) {
        setExpandedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setCollapsedBounds(int i, int i2, int i3, int i4) {
        if (!rectEquals(this.collapsedBounds, i, i2, i3, i4)) {
            this.collapsedBounds.set(i, i2, i3, i4);
            this.boundsChanged = true;
        }
    }

    public void setCollapsedBounds(Rect rect) {
        setCollapsedBounds(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void getCollapsedTextBottomTextBounds(RectF rectF, int i, int i2) {
        this.isRtl = calculateIsRtl(this.text);
        rectF.left = Math.max(getCollapsedTextLeftBound(i, i2), (float) this.collapsedBounds.left);
        rectF.top = (float) this.collapsedBounds.top;
        rectF.right = Math.min(getCollapsedTextRightBound(rectF, i, i2), (float) this.collapsedBounds.right);
        rectF.bottom = ((float) this.collapsedBounds.top) + getCollapsedTextHeight();
        if (this.textLayout != null && !shouldTruncateCollapsedToSingleLine()) {
            StaticLayout staticLayout = this.textLayout;
            float lineWidth = staticLayout.getLineWidth(staticLayout.getLineCount() - 1) * (this.collapsedTextSize / this.expandedTextSize);
            if (this.isRtl) {
                rectF.left = rectF.right - lineWidth;
            } else {
                rectF.right = rectF.left + lineWidth;
            }
        }
    }

    private float getCollapsedTextLeftBound(int i, int i2) {
        float f;
        float f2;
        int i3;
        if (i2 == 17 || (i2 & 7) == 1) {
            f = ((float) i) / 2.0f;
            f2 = this.collapsedTextWidth / 2.0f;
        } else {
            if ((i2 & 8388613) == 8388613 || (i2 & 5) == 5) {
                if (this.isRtl) {
                    i3 = this.collapsedBounds.left;
                } else {
                    f = (float) this.collapsedBounds.right;
                    f2 = this.collapsedTextWidth;
                }
            } else if (this.isRtl) {
                f = (float) this.collapsedBounds.right;
                f2 = this.collapsedTextWidth;
            } else {
                i3 = this.collapsedBounds.left;
            }
            return (float) i3;
        }
        return f - f2;
    }

    private float getCollapsedTextRightBound(RectF rectF, int i, int i2) {
        float f;
        float f2;
        int i3;
        if (i2 == 17 || (i2 & 7) == 1) {
            f = ((float) i) / 2.0f;
            f2 = this.collapsedTextWidth / 2.0f;
        } else {
            if ((i2 & 8388613) == 8388613 || (i2 & 5) == 5) {
                if (this.isRtl) {
                    f = rectF.left;
                    f2 = this.collapsedTextWidth;
                } else {
                    i3 = this.collapsedBounds.right;
                }
            } else if (this.isRtl) {
                i3 = this.collapsedBounds.right;
            } else {
                f = rectF.left;
                f2 = this.collapsedTextWidth;
            }
            return (float) i3;
        }
        return f + f2;
    }

    public float getExpandedTextSingleLineHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getExpandedTextFullSingleLineHeight() {
        getTextPaintExpanded(this.tmpPaint);
        return (-this.tmpPaint.ascent()) + this.tmpPaint.descent();
    }

    public void updateTextHeights(int i) {
        getTextPaintCollapsed(this.tmpPaint);
        float f = (float) i;
        this.collapsedHeight = createStaticLayout(this.collapsedMaxLines, this.tmpPaint, this.text, f * (this.collapsedTextSize / this.expandedTextSize), this.isRtl).getHeight();
        getTextPaintExpanded(this.tmpPaint);
        this.expandedHeight = createStaticLayout(this.expandedMaxLines, this.tmpPaint, this.text, f, this.isRtl).getHeight();
    }

    public float getCollapsedTextHeight() {
        int i = this.collapsedHeight;
        return i != -1 ? (float) i : getCollapsedSingleLineHeight();
    }

    public float getExpandedTextHeight() {
        int i = this.expandedHeight;
        return i != -1 ? (float) i : getExpandedTextSingleLineHeight();
    }

    public float getCollapsedSingleLineHeight() {
        getTextPaintCollapsed(this.tmpPaint);
        return -this.tmpPaint.ascent();
    }

    public float getCollapsedFullSingleLineHeight() {
        getTextPaintCollapsed(this.tmpPaint);
        return (-this.tmpPaint.ascent()) + this.tmpPaint.descent();
    }

    private float calculateFadeModeThresholdFraction() {
        float f = this.fadeModeStartFraction;
        return f + ((1.0f - f) * 0.5f);
    }

    private void getTextPaintExpanded(TextPaint textPaint2) {
        textPaint2.setTextSize(this.expandedTextSize);
        textPaint2.setTypeface(this.expandedTypeface);
        textPaint2.setLetterSpacing(this.expandedLetterSpacing);
    }

    private void getTextPaintCollapsed(TextPaint textPaint2) {
        textPaint2.setTextSize(this.collapsedTextSize);
        textPaint2.setTypeface(this.collapsedTypeface);
        textPaint2.setLetterSpacing(this.collapsedLetterSpacing);
    }

    public void setExpandedTextGravity(int i) {
        if (this.expandedTextGravity != i) {
            this.expandedTextGravity = i;
            recalculate();
        }
    }

    public void setCollapsedTextGravity(int i) {
        if (this.collapsedTextGravity != i) {
            this.collapsedTextGravity = i;
            recalculate();
        }
    }

    public void setCollapsedTextAppearance(int i) {
        TextAppearance textAppearance = new TextAppearance(this.view.getContext(), i);
        if (textAppearance.getTextColor() != null) {
            this.collapsedTextColor = textAppearance.getTextColor();
        }
        if (textAppearance.getTextSize() != 0.0f) {
            this.collapsedTextSize = textAppearance.getTextSize();
        }
        ColorStateList colorStateList = textAppearance.shadowColor;
        if (colorStateList != null) {
            this.collapsedShadowColor = colorStateList;
        }
        this.collapsedShadowDx = textAppearance.shadowDx;
        this.collapsedShadowDy = textAppearance.shadowDy;
        this.collapsedShadowRadius = textAppearance.shadowRadius;
        this.collapsedLetterSpacing = textAppearance.letterSpacing;
        CancelableFontCallback cancelableFontCallback = this.collapsedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        this.collapsedFontCallback = new CancelableFontCallback(new CancelableFontCallback.ApplyFont() {
            public void apply(Typeface typeface) {
                CollapsingTextHelper.this.setCollapsedTypeface(typeface);
            }
        }, textAppearance.getFallbackFont());
        textAppearance.getFontAsync(this.view.getContext(), this.collapsedFontCallback);
        recalculate();
    }

    public void setCollapsedTypeface(Typeface typeface) {
        if (setCollapsedTypefaceInternal(typeface)) {
            recalculate();
        }
    }

    public void setTypefaces(Typeface typeface) {
        boolean collapsedTypefaceInternal = setCollapsedTypefaceInternal(typeface);
        boolean expandedTypefaceInternal = setExpandedTypefaceInternal(typeface);
        if (collapsedTypefaceInternal || expandedTypefaceInternal) {
            recalculate();
        }
    }

    private boolean setCollapsedTypefaceInternal(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.collapsedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.collapsedTypefaceDefault == typeface) {
            return false;
        }
        this.collapsedTypefaceDefault = typeface;
        Typeface maybeCopyWithFontWeightAdjustment = TypefaceUtils.maybeCopyWithFontWeightAdjustment(this.view.getContext().getResources().getConfiguration(), typeface);
        this.collapsedTypefaceBold = maybeCopyWithFontWeightAdjustment;
        if (maybeCopyWithFontWeightAdjustment == null) {
            maybeCopyWithFontWeightAdjustment = this.collapsedTypefaceDefault;
        }
        this.collapsedTypeface = maybeCopyWithFontWeightAdjustment;
        return true;
    }

    private boolean setExpandedTypefaceInternal(Typeface typeface) {
        CancelableFontCallback cancelableFontCallback = this.expandedFontCallback;
        if (cancelableFontCallback != null) {
            cancelableFontCallback.cancel();
        }
        if (this.expandedTypefaceDefault == typeface) {
            return false;
        }
        this.expandedTypefaceDefault = typeface;
        Typeface maybeCopyWithFontWeightAdjustment = TypefaceUtils.maybeCopyWithFontWeightAdjustment(this.view.getContext().getResources().getConfiguration(), typeface);
        this.expandedTypefaceBold = maybeCopyWithFontWeightAdjustment;
        if (maybeCopyWithFontWeightAdjustment == null) {
            maybeCopyWithFontWeightAdjustment = this.expandedTypefaceDefault;
        }
        this.expandedTypeface = maybeCopyWithFontWeightAdjustment;
        return true;
    }

    public void maybeUpdateFontWeightAdjustment(Configuration configuration) {
        Typeface typeface = this.collapsedTypefaceDefault;
        if (typeface != null) {
            this.collapsedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(configuration, typeface);
        }
        Typeface typeface2 = this.expandedTypefaceDefault;
        if (typeface2 != null) {
            this.expandedTypefaceBold = TypefaceUtils.maybeCopyWithFontWeightAdjustment(configuration, typeface2);
        }
        Typeface typeface3 = this.collapsedTypefaceBold;
        if (typeface3 == null) {
            typeface3 = this.collapsedTypefaceDefault;
        }
        this.collapsedTypeface = typeface3;
        Typeface typeface4 = this.expandedTypefaceBold;
        if (typeface4 == null) {
            typeface4 = this.expandedTypefaceDefault;
        }
        this.expandedTypeface = typeface4;
        recalculate(true);
    }

    public void setExpansionFraction(float f) {
        float clamp = MathUtils.clamp(f, 0.0f, 1.0f);
        if (clamp != this.expandedFraction) {
            this.expandedFraction = clamp;
            calculateCurrentOffsets();
        }
    }

    public final boolean setState(int[] iArr) {
        this.state = iArr;
        if (!isStateful()) {
            return false;
        }
        recalculate();
        return true;
    }

    public final boolean isStateful() {
        ColorStateList colorStateList = this.collapsedTextColor;
        if (colorStateList != null && colorStateList.isStateful()) {
            return true;
        }
        ColorStateList colorStateList2 = this.expandedTextColor;
        return colorStateList2 != null && colorStateList2.isStateful();
    }

    public float getExpansionFraction() {
        return this.expandedFraction;
    }

    private void calculateCurrentOffsets() {
        calculateOffsets(this.expandedFraction);
    }

    private void calculateOffsets(float f) {
        float f2;
        interpolateBounds(f);
        if (!this.fadeModeEnabled) {
            this.currentDrawX = lerp(this.expandedDrawX, this.collapsedDrawX, f, this.positionInterpolator);
            this.currentDrawY = lerp(this.expandedDrawY, this.collapsedDrawY, f, this.positionInterpolator);
            setInterpolatedTextSize(f);
            f2 = f;
        } else if (f < this.fadeModeThresholdFraction) {
            this.currentDrawX = this.expandedDrawX;
            this.currentDrawY = this.expandedDrawY;
            setInterpolatedTextSize(0.0f);
            f2 = 0.0f;
        } else {
            this.currentDrawX = this.collapsedDrawX;
            this.currentDrawY = this.collapsedDrawY - ((float) Math.max(0, this.currentOffsetY));
            setInterpolatedTextSize(1.0f);
            f2 = 1.0f;
        }
        TimeInterpolator timeInterpolator = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR;
        setCollapsedTextBlend(1.0f - lerp(0.0f, 1.0f, 1.0f - f, timeInterpolator));
        setExpandedTextBlend(lerp(1.0f, 0.0f, f, timeInterpolator));
        if (this.collapsedTextColor != this.expandedTextColor) {
            this.textPaint.setColor(blendARGB(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), f2));
        } else {
            this.textPaint.setColor(getCurrentCollapsedTextColor());
        }
        float f3 = this.collapsedLetterSpacing;
        float f4 = this.expandedLetterSpacing;
        if (f3 != f4) {
            this.textPaint.setLetterSpacing(lerp(f4, f3, f, timeInterpolator));
        } else {
            this.textPaint.setLetterSpacing(f3);
        }
        this.currentShadowRadius = lerp(this.expandedShadowRadius, this.collapsedShadowRadius, f, (TimeInterpolator) null);
        this.currentShadowDx = lerp(this.expandedShadowDx, this.collapsedShadowDx, f, (TimeInterpolator) null);
        this.currentShadowDy = lerp(this.expandedShadowDy, this.collapsedShadowDy, f, (TimeInterpolator) null);
        int blendARGB = blendARGB(getCurrentColor(this.expandedShadowColor), getCurrentColor(this.collapsedShadowColor), f);
        this.currentShadowColor = blendARGB;
        this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, blendARGB);
        if (this.fadeModeEnabled) {
            this.textPaint.setAlpha((int) (calculateFadeModeTextAlpha(f) * ((float) this.textPaint.getAlpha())));
            TextPaint textPaint2 = this.textPaint;
            textPaint2.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, textPaint2.getAlpha()));
        }
        this.view.postInvalidateOnAnimation();
    }

    private float calculateFadeModeTextAlpha(float f) {
        float f2 = this.fadeModeThresholdFraction;
        if (f <= f2) {
            return AnimationUtils.lerp(1.0f, 0.0f, this.fadeModeStartFraction, f2, f);
        }
        return AnimationUtils.lerp(0.0f, 1.0f, f2, 1.0f, f);
    }

    private int getCurrentExpandedTextColor() {
        return getCurrentColor(this.expandedTextColor);
    }

    public int getCurrentCollapsedTextColor() {
        return getCurrentColor(this.collapsedTextColor);
    }

    private int getCurrentColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return 0;
        }
        int[] iArr = this.state;
        if (iArr != null) {
            return colorStateList.getColorForState(iArr, 0);
        }
        return colorStateList.getDefaultColor();
    }

    private boolean shouldTruncateCollapsedToSingleLine() {
        return this.collapsedMaxLines == 1;
    }

    private void calculateBaseOffsets(boolean z) {
        float f;
        CharSequence charSequence;
        calculateUsingTextSize(1.0f, z);
        if (!(this.textToDraw == null || this.textLayout == null)) {
            if (shouldTruncateCollapsedToSingleLine()) {
                charSequence = TextUtils.ellipsize(this.textToDraw, this.textPaint, (float) this.textLayout.getWidth(), this.titleTextEllipsize);
            } else {
                charSequence = this.textToDraw;
            }
            this.textToDrawCollapsed = charSequence;
        }
        CharSequence charSequence2 = this.textToDrawCollapsed;
        float f2 = 0.0f;
        if (charSequence2 != null) {
            this.collapsedTextWidth = measureTextWidth(this.textPaint, charSequence2);
        } else {
            this.collapsedTextWidth = 0.0f;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(this.collapsedTextGravity, this.isRtl ? 1 : 0);
        Rect rect = this.collapsedBoundsForPlacement;
        if (rect == null) {
            rect = this.collapsedBounds;
        }
        int i = absoluteGravity & 112;
        if (i == 48) {
            this.collapsedDrawY = (float) rect.top;
        } else if (i != 80) {
            this.collapsedDrawY = ((float) rect.centerY()) - ((this.textPaint.descent() - this.textPaint.ascent()) / 2.0f);
        } else {
            this.collapsedDrawY = ((float) rect.bottom) + this.textPaint.ascent();
        }
        int i2 = absoluteGravity & 8388615;
        if (i2 == 1) {
            this.collapsedDrawX = ((float) rect.centerX()) - (this.collapsedTextWidth / 2.0f);
        } else if (i2 != 5) {
            this.collapsedDrawX = (float) rect.left;
        } else {
            this.collapsedDrawX = ((float) rect.right) - this.collapsedTextWidth;
        }
        if (this.collapsedTextWidth <= ((float) this.collapsedBounds.width())) {
            float f3 = this.collapsedDrawX;
            float max = f3 + Math.max(0.0f, ((float) this.collapsedBounds.left) - f3);
            this.collapsedDrawX = max;
            this.collapsedDrawX = max + Math.min(0.0f, ((float) this.collapsedBounds.right) - (this.collapsedTextWidth + max));
        }
        if (getCollapsedFullSingleLineHeight() <= ((float) this.collapsedBounds.height())) {
            float f4 = this.collapsedDrawY;
            float max2 = f4 + Math.max(0.0f, ((float) this.collapsedBounds.top) - f4);
            this.collapsedDrawY = max2;
            this.collapsedDrawY = max2 + Math.min(0.0f, ((float) this.collapsedBounds.bottom) - (getCollapsedTextHeight() + max2));
        }
        calculateUsingTextSize(0.0f, z);
        StaticLayout staticLayout = this.textLayout;
        float height = staticLayout != null ? (float) staticLayout.getHeight() : 0.0f;
        StaticLayout staticLayout2 = this.textLayout;
        if (staticLayout2 == null || this.expandedMaxLines <= 1) {
            CharSequence charSequence3 = this.textToDraw;
            f = charSequence3 != null ? measureTextWidth(this.textPaint, charSequence3) : 0.0f;
        } else {
            f = (float) staticLayout2.getWidth();
        }
        StaticLayout staticLayout3 = this.textLayout;
        this.expandedLineCount = staticLayout3 != null ? staticLayout3.getLineCount() : 0;
        int absoluteGravity2 = Gravity.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0);
        int i3 = absoluteGravity2 & 112;
        if (i3 == 48) {
            this.expandedDrawY = (float) this.expandedBounds.top;
        } else if (i3 != 80) {
            this.expandedDrawY = ((float) this.expandedBounds.centerY()) - (height / 2.0f);
        } else {
            float f5 = ((float) this.expandedBounds.bottom) - height;
            if (this.alignBaselineAtBottom) {
                f2 = this.textPaint.descent();
            }
            this.expandedDrawY = f5 + f2;
        }
        int i4 = absoluteGravity2 & 8388615;
        if (i4 == 1) {
            this.expandedDrawX = ((float) this.expandedBounds.centerX()) - (f / 2.0f);
        } else if (i4 != 5) {
            this.expandedDrawX = (float) this.expandedBounds.left;
        } else {
            this.expandedDrawX = ((float) this.expandedBounds.right) - f;
        }
        setInterpolatedTextSize(this.expandedFraction);
    }

    private float measureTextWidth(TextPaint textPaint2, CharSequence charSequence) {
        return textPaint2.measureText(charSequence, 0, charSequence.length());
    }

    private void interpolateBounds(float f) {
        if (this.fadeModeEnabled) {
            this.currentBounds.set(f < this.fadeModeThresholdFraction ? this.expandedBounds : this.collapsedBounds);
            return;
        }
        this.currentBounds.left = lerp((float) this.expandedBounds.left, (float) this.collapsedBounds.left, f, this.positionInterpolator);
        this.currentBounds.top = lerp(this.expandedDrawY, this.collapsedDrawY, f, this.positionInterpolator);
        this.currentBounds.right = lerp((float) this.expandedBounds.right, (float) this.collapsedBounds.right, f, this.positionInterpolator);
        this.currentBounds.bottom = lerp((float) this.expandedBounds.bottom, (float) this.collapsedBounds.bottom, f, this.positionInterpolator);
    }

    private void setCollapsedTextBlend(float f) {
        this.collapsedTextBlend = f;
        this.view.postInvalidateOnAnimation();
    }

    private void setExpandedTextBlend(float f) {
        this.expandedTextBlend = f;
        this.view.postInvalidateOnAnimation();
    }

    public void draw(Canvas canvas) {
        int save = canvas.save();
        if (this.textToDraw != null && this.currentBounds.width() > 0.0f && this.currentBounds.height() > 0.0f) {
            this.textPaint.setTextSize(this.currentTextSize);
            float f = this.currentDrawX;
            float f2 = this.currentDrawY;
            float f3 = this.scale;
            if (f3 != 1.0f && !this.fadeModeEnabled) {
                canvas.scale(f3, f3, f, f2);
            }
            if (!shouldDrawMultiline() || !shouldTruncateCollapsedToSingleLine() || (this.fadeModeEnabled && this.expandedFraction <= this.fadeModeThresholdFraction)) {
                canvas.translate(f, f2);
                this.textLayout.draw(canvas);
            } else {
                drawMultilineTransition(canvas, this.currentDrawX - ((float) this.textLayout.getLineStart(0)), f2);
            }
            canvas.restoreToCount(save);
        }
    }

    private boolean shouldDrawMultiline() {
        if (this.expandedMaxLines > 1 || this.collapsedMaxLines > 1) {
            return !this.isRtl || this.fadeModeEnabled;
        }
        return false;
    }

    private void drawMultilineTransition(Canvas canvas, float f, float f2) {
        int alpha = this.textPaint.getAlpha();
        canvas.translate(f, f2);
        if (!this.fadeModeEnabled) {
            this.textPaint.setAlpha((int) (this.expandedTextBlend * ((float) alpha)));
            TextPaint textPaint2 = this.textPaint;
            textPaint2.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, textPaint2.getAlpha()));
            this.textLayout.draw(canvas);
        }
        if (!this.fadeModeEnabled) {
            this.textPaint.setAlpha((int) (this.collapsedTextBlend * ((float) alpha)));
        }
        TextPaint textPaint3 = this.textPaint;
        textPaint3.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, MaterialColors.compositeARGBWithAlpha(this.currentShadowColor, textPaint3.getAlpha()));
        int lineBaseline = this.textLayout.getLineBaseline(0);
        CharSequence charSequence = this.textToDrawCollapsed;
        float f3 = (float) lineBaseline;
        Canvas canvas2 = canvas;
        canvas2.drawText(charSequence, 0, charSequence.length(), 0.0f, f3, this.textPaint);
        this.textPaint.setShadowLayer(this.currentShadowRadius, this.currentShadowDx, this.currentShadowDy, this.currentShadowColor);
        if (!this.fadeModeEnabled) {
            String trim = this.textToDrawCollapsed.toString().trim();
            if (trim.endsWith("…")) {
                trim = trim.substring(0, trim.length() - 1);
            }
            this.textPaint.setAlpha(alpha);
            float f4 = f3;
            canvas2.drawText(trim, 0, Math.min(this.textLayout.getLineEnd(0), trim.length()), 0.0f, f4, this.textPaint);
        }
    }

    private boolean calculateIsRtl(CharSequence charSequence) {
        boolean isDefaultIsRtl = isDefaultIsRtl();
        return this.isRtlTextDirectionHeuristicsEnabled ? isTextDirectionHeuristicsIsRtl(charSequence, isDefaultIsRtl) : isDefaultIsRtl;
    }

    private boolean isDefaultIsRtl() {
        return this.view.getLayoutDirection() == 1;
    }

    private boolean isTextDirectionHeuristicsIsRtl(CharSequence charSequence, boolean z) {
        TextDirectionHeuristicCompat textDirectionHeuristicCompat;
        if (z) {
            textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL;
        } else {
            textDirectionHeuristicCompat = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
        }
        return textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
    }

    private void setInterpolatedTextSize(float f) {
        calculateUsingTextSize(f);
        this.view.postInvalidateOnAnimation();
    }

    private void calculateUsingTextSize(float f) {
        calculateUsingTextSize(f, false);
    }

    private void calculateUsingTextSize(float f, boolean z) {
        float f2;
        Typeface typeface;
        float f3;
        float f4;
        if (this.text != null) {
            float width = (float) this.collapsedBounds.width();
            float width2 = (float) this.expandedBounds.width();
            float f5 = 1.0f;
            if (isClose(f, 1.0f)) {
                f2 = shouldTruncateCollapsedToSingleLine() ? this.collapsedTextSize : this.expandedTextSize;
                f3 = shouldTruncateCollapsedToSingleLine() ? this.collapsedLetterSpacing : this.expandedLetterSpacing;
                if (shouldTruncateCollapsedToSingleLine()) {
                    f4 = 1.0f;
                } else {
                    f4 = lerp(this.expandedTextSize, this.collapsedTextSize, f, this.textSizeInterpolator) / this.expandedTextSize;
                }
                this.scale = f4;
                if (!shouldTruncateCollapsedToSingleLine()) {
                    width = width2;
                }
                typeface = this.collapsedTypeface;
                width2 = width;
            } else {
                float f6 = this.expandedTextSize;
                float f7 = this.expandedLetterSpacing;
                typeface = this.expandedTypeface;
                if (isClose(f, 0.0f)) {
                    this.scale = 1.0f;
                } else {
                    this.scale = lerp(this.expandedTextSize, this.collapsedTextSize, f, this.textSizeInterpolator) / this.expandedTextSize;
                }
                float f8 = this.collapsedTextSize / this.expandedTextSize;
                float f9 = width2 * f8;
                if (!z && !this.fadeModeEnabled && f9 > width && shouldTruncateCollapsedToSingleLine()) {
                    width2 = Math.min(width / f8, width2);
                }
                f2 = f6;
                f3 = f7;
            }
            int i = f < 0.5f ? this.expandedMaxLines : this.collapsedMaxLines;
            boolean z2 = false;
            if (width2 > 0.0f) {
                boolean z3 = this.currentTextSize != f2;
                boolean z4 = this.currentLetterSpacing != f3;
                boolean z5 = this.currentTypeface != typeface;
                StaticLayout staticLayout = this.textLayout;
                boolean z6 = z3 || z4 || (staticLayout != null && (width2 > ((float) staticLayout.getWidth()) ? 1 : (width2 == ((float) staticLayout.getWidth()) ? 0 : -1)) != 0) || z5 || (this.currentMaxLines != i) || this.boundsChanged;
                this.currentTextSize = f2;
                this.currentLetterSpacing = f3;
                this.currentTypeface = typeface;
                this.boundsChanged = false;
                this.currentMaxLines = i;
                TextPaint textPaint2 = this.textPaint;
                if (this.scale != 1.0f) {
                    z2 = true;
                }
                textPaint2.setLinearText(z2);
                z2 = z6;
            }
            if (this.textToDraw == null || z2) {
                this.textPaint.setTextSize(this.currentTextSize);
                this.textPaint.setTypeface(this.currentTypeface);
                this.textPaint.setLetterSpacing(this.currentLetterSpacing);
                this.isRtl = calculateIsRtl(this.text);
                int i2 = shouldDrawMultiline() ? i : 1;
                TextPaint textPaint3 = this.textPaint;
                CharSequence charSequence = this.text;
                if (!shouldTruncateCollapsedToSingleLine()) {
                    f5 = this.scale;
                }
                StaticLayout createStaticLayout = createStaticLayout(i2, textPaint3, charSequence, width2 * f5, this.isRtl);
                this.textLayout = createStaticLayout;
                this.textToDraw = createStaticLayout.getText();
            }
        }
    }

    private StaticLayout createStaticLayout(int i, TextPaint textPaint2, CharSequence charSequence, float f, boolean z) {
        return (StaticLayout) Preconditions.checkNotNull(StaticLayoutBuilderCompat.obtain(charSequence, textPaint2, (int) f).setEllipsize(this.titleTextEllipsize).setIsRtl(z).setAlignment(i == 1 ? Layout.Alignment.ALIGN_NORMAL : getMultilineTextLayoutAlignment()).setIncludePad(false).setMaxLines(i).setLineSpacing(this.lineSpacingAdd, this.lineSpacingMultiplier).setHyphenationFrequency(this.hyphenationFrequency).setStaticLayoutBuilderConfigurer(this.staticLayoutBuilderConfigurer).build());
    }

    private Layout.Alignment getMultilineTextLayoutAlignment() {
        int absoluteGravity = Gravity.getAbsoluteGravity(this.expandedTextGravity, this.isRtl ? 1 : 0) & 7;
        if (absoluteGravity != 1) {
            return absoluteGravity != 5 ? this.isRtl ? Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL : this.isRtl ? Layout.Alignment.ALIGN_NORMAL : Layout.Alignment.ALIGN_OPPOSITE;
        }
        return Layout.Alignment.ALIGN_CENTER;
    }

    public void recalculate() {
        recalculate(false);
    }

    public void recalculate(boolean z) {
        if ((this.view.getHeight() > 0 && this.view.getWidth() > 0) || z) {
            calculateBaseOffsets(z);
            calculateCurrentOffsets();
        }
    }

    public void setText(CharSequence charSequence) {
        if (charSequence == null || !TextUtils.equals(this.text, charSequence)) {
            this.text = charSequence;
            this.textToDraw = null;
            recalculate();
        }
    }

    public void setExpandedMaxLines(int i) {
        if (i != this.expandedMaxLines) {
            this.expandedMaxLines = i;
            recalculate();
        }
    }

    public int getExpandedMaxLines() {
        return this.expandedMaxLines;
    }

    public int getExpandedLineCount() {
        return this.expandedLineCount;
    }

    private static boolean isClose(float f, float f2) {
        return Math.abs(f - f2) < 1.0E-5f;
    }

    public ColorStateList getCollapsedTextColor() {
        return this.collapsedTextColor;
    }

    private static int blendARGB(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.argb(Math.round((((float) Color.alpha(i)) * f2) + (((float) Color.alpha(i2)) * f)), Math.round((((float) Color.red(i)) * f2) + (((float) Color.red(i2)) * f)), Math.round((((float) Color.green(i)) * f2) + (((float) Color.green(i2)) * f)), Math.round((((float) Color.blue(i)) * f2) + (((float) Color.blue(i2)) * f)));
    }

    private static float lerp(float f, float f2, float f3, TimeInterpolator timeInterpolator) {
        if (timeInterpolator != null) {
            f3 = timeInterpolator.getInterpolation(f3);
        }
        return AnimationUtils.lerp(f, f2, f3);
    }

    private static boolean rectEquals(Rect rect, int i, int i2, int i3, int i4) {
        return rect.left == i && rect.top == i2 && rect.right == i3 && rect.bottom == i4;
    }
}
