package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.TextViewCompat;
import androidx.customview.view.AbsSavedState;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import com.google.android.material.R$attr;
import com.google.android.material.R$color;
import com.google.android.material.R$dimen;
import com.google.android.material.R$id;
import com.google.android.material.R$string;
import com.google.android.material.R$style;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.StaticLayoutBuilderCompat;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class TextInputLayout extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final int DEF_STYLE_RES = R$style.Widget_Design_TextInputLayout;
    private static final int[][] EDIT_TEXT_BACKGROUND_RIPPLE_STATE = {new int[]{16842919}, new int[0]};
    private ValueAnimator animator;
    private boolean areCornerRadiiRtl;
    private MaterialShapeDrawable boxBackground;
    private boolean boxBackgroundApplied;
    private int boxBackgroundColor;
    private int boxBackgroundMode;
    private int boxCollapsedPaddingTopPx;
    private final int boxLabelCutoutPaddingPx;
    private int boxStrokeColor;
    private int boxStrokeWidthDefaultPx;
    private int boxStrokeWidthFocusedPx;
    private int boxStrokeWidthPx;
    private MaterialShapeDrawable boxUnderlineDefault;
    private MaterialShapeDrawable boxUnderlineFocused;
    final CollapsingTextHelper collapsingTextHelper;
    boolean counterEnabled;
    private int counterMaxLength;
    private int counterOverflowTextAppearance;
    private ColorStateList counterOverflowTextColor;
    private boolean counterOverflowed;
    private int counterTextAppearance;
    private ColorStateList counterTextColor;
    private TextView counterView;
    private ColorStateList cursorColor;
    private ColorStateList cursorErrorColor;
    private int defaultFilledBackgroundColor;
    private ColorStateList defaultHintTextColor;
    private int defaultStrokeColor;
    private int disabledColor;
    private int disabledFilledBackgroundColor;
    EditText editText;
    private final LinkedHashSet editTextAttachedListeners;
    private Drawable endDummyDrawable;
    private int endDummyDrawableWidth;
    /* access modifiers changed from: private */
    public final EndCompoundLayout endLayout;
    private boolean expandedHintEnabled;
    private final int extraSpaceBetweenPlaceholderAndHint;
    private StateListDrawable filledDropDownMenuBackground;
    private int focusedFilledBackgroundColor;
    private int focusedStrokeColor;
    private ColorStateList focusedTextColor;
    private boolean globalLayoutListenerAdded;
    private CharSequence hint;
    private boolean hintAnimationEnabled;
    private boolean hintEnabled;
    private boolean hintExpanded;
    private int hoveredFilledBackgroundColor;
    private int hoveredStrokeColor;
    private boolean inDrawableStateChanged;
    /* access modifiers changed from: private */
    public final IndicatorViewController indicatorViewController;
    private final FrameLayout inputFrame;
    private boolean isProvidingHint;
    private LengthCounter lengthCounter;
    private int maxEms;
    private int maxWidth;
    private int minEms;
    private int minWidth;
    private Drawable originalEditTextEndDrawable;
    int originalEditTextMinimumHeight;
    private CharSequence originalHint;
    private MaterialShapeDrawable outlinedDropDownMenuBackground;
    /* access modifiers changed from: private */
    public boolean placeholderEnabled;
    private Fade placeholderFadeIn;
    private Fade placeholderFadeOut;
    private CharSequence placeholderText;
    private int placeholderTextAppearance;
    private ColorStateList placeholderTextColor;
    private TextView placeholderTextView;
    /* access modifiers changed from: private */
    public boolean restoringSavedState;
    private ShapeAppearanceModel shapeAppearanceModel;
    private Drawable startDummyDrawable;
    private int startDummyDrawableWidth;
    /* access modifiers changed from: private */
    public final StartCompoundLayout startLayout;
    private ColorStateList strokeErrorColor;
    private final Rect tmpBoundsRect;
    private final Rect tmpRect;
    private final RectF tmpRectF;
    private Typeface typeface;

    public interface LengthCounter {
        int countLength(Editable editable);
    }

    public interface OnEditTextAttachedListener {
        void onEditTextAttached(TextInputLayout textInputLayout);
    }

    public static /* synthetic */ int $r8$lambda$o92w5de0KdW2_dMexjlsVyV6EjQ(Editable editable) {
        if (editable != null) {
            return editable.length();
        }
        return 0;
    }

    public TextInputLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.textInputStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TextInputLayout(android.content.Context r20, android.util.AttributeSet r21, int r22) {
        /*
            r19 = this;
            r0 = r19
            r2 = r21
            r4 = r22
            int r5 = DEF_STYLE_RES
            r1 = r20
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r2, r4, r5)
            r0.<init>(r1, r2, r4)
            r7 = -1
            r0.minEms = r7
            r0.maxEms = r7
            r0.minWidth = r7
            r0.maxWidth = r7
            com.google.android.material.textfield.IndicatorViewController r1 = new com.google.android.material.textfield.IndicatorViewController
            r1.<init>(r0)
            r0.indicatorViewController = r1
            com.google.android.material.textfield.TextInputLayout$$ExternalSyntheticLambda1 r1 = new com.google.android.material.textfield.TextInputLayout$$ExternalSyntheticLambda1
            r1.<init>()
            r0.lengthCounter = r1
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r0.tmpRect = r1
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r0.tmpBoundsRect = r1
            android.graphics.RectF r1 = new android.graphics.RectF
            r1.<init>()
            r0.tmpRectF = r1
            java.util.LinkedHashSet r1 = new java.util.LinkedHashSet
            r1.<init>()
            r0.editTextAttachedListeners = r1
            com.google.android.material.internal.CollapsingTextHelper r1 = new com.google.android.material.internal.CollapsingTextHelper
            r1.<init>(r0)
            r0.collapsingTextHelper = r1
            r8 = 0
            r0.globalLayoutListenerAdded = r8
            android.content.Context r3 = r0.getContext()
            r9 = 1
            r0.setOrientation(r9)
            r0.setWillNotDraw(r8)
            r0.setAddStatesFromChildren(r9)
            android.widget.FrameLayout r10 = new android.widget.FrameLayout
            r10.<init>(r3)
            r0.inputFrame = r10
            r10.setAddStatesFromChildren(r9)
            android.animation.TimeInterpolator r6 = com.google.android.material.animation.AnimationUtils.LINEAR_INTERPOLATOR
            r1.setTextSizeInterpolator(r6)
            r1.setPositionInterpolator(r6)
            r6 = 8388659(0x800033, float:1.1755015E-38)
            r1.setCollapsedTextGravity(r6)
            r1 = r3
            int[] r3 = com.google.android.material.R$styleable.TextInputLayout
            int r11 = com.google.android.material.R$styleable.TextInputLayout_counterTextAppearance
            int r12 = com.google.android.material.R$styleable.TextInputLayout_counterOverflowTextAppearance
            int r13 = com.google.android.material.R$styleable.TextInputLayout_errorTextAppearance
            int r14 = com.google.android.material.R$styleable.TextInputLayout_helperTextTextAppearance
            int r15 = com.google.android.material.R$styleable.TextInputLayout_hintTextAppearance
            int[] r6 = new int[]{r11, r12, r13, r14, r15}
            androidx.appcompat.widget.TintTypedArray r3 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r1, r2, r3, r4, r5, r6)
            com.google.android.material.textfield.StartCompoundLayout r6 = new com.google.android.material.textfield.StartCompoundLayout
            r6.<init>(r0, r3)
            r0.startLayout = r6
            int r8 = com.google.android.material.R$styleable.TextInputLayout_hintEnabled
            boolean r8 = r3.getBoolean(r8, r9)
            r0.hintEnabled = r8
            int r8 = com.google.android.material.R$styleable.TextInputLayout_android_hint
            java.lang.CharSequence r8 = r3.getText(r8)
            r0.setHint(r8)
            int r8 = com.google.android.material.R$styleable.TextInputLayout_hintAnimationEnabled
            boolean r8 = r3.getBoolean(r8, r9)
            r0.hintAnimationEnabled = r8
            int r8 = com.google.android.material.R$styleable.TextInputLayout_expandedHintEnabled
            boolean r8 = r3.getBoolean(r8, r9)
            r0.expandedHintEnabled = r8
            int r8 = com.google.android.material.R$styleable.TextInputLayout_android_minEms
            boolean r16 = r3.hasValue(r8)
            if (r16 == 0) goto L_0x00c1
            int r8 = r3.getInt(r8, r7)
            r0.setMinEms(r8)
            goto L_0x00d0
        L_0x00c1:
            int r8 = com.google.android.material.R$styleable.TextInputLayout_android_minWidth
            boolean r16 = r3.hasValue(r8)
            if (r16 == 0) goto L_0x00d0
            int r8 = r3.getDimensionPixelSize(r8, r7)
            r0.setMinWidth(r8)
        L_0x00d0:
            int r8 = com.google.android.material.R$styleable.TextInputLayout_android_maxEms
            boolean r16 = r3.hasValue(r8)
            if (r16 == 0) goto L_0x00e0
            int r8 = r3.getInt(r8, r7)
            r0.setMaxEms(r8)
            goto L_0x00ef
        L_0x00e0:
            int r8 = com.google.android.material.R$styleable.TextInputLayout_android_maxWidth
            boolean r16 = r3.hasValue(r8)
            if (r16 == 0) goto L_0x00ef
            int r8 = r3.getDimensionPixelSize(r8, r7)
            r0.setMaxWidth(r8)
        L_0x00ef:
            com.google.android.material.shape.ShapeAppearanceModel$Builder r2 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r1, (android.util.AttributeSet) r2, (int) r4, (int) r5)
            com.google.android.material.shape.ShapeAppearanceModel r2 = r2.build()
            r0.shapeAppearanceModel = r2
            android.content.res.Resources r2 = r1.getResources()
            int r4 = com.google.android.material.R$dimen.mtrl_textinput_box_label_cutout_padding
            int r2 = r2.getDimensionPixelOffset(r4)
            r0.boxLabelCutoutPaddingPx = r2
            int r2 = com.google.android.material.R$styleable.TextInputLayout_boxCollapsedPaddingTop
            r4 = 0
            int r2 = r3.getDimensionPixelOffset(r2, r4)
            r0.boxCollapsedPaddingTopPx = r2
            android.content.res.Resources r2 = r0.getResources()
            int r4 = com.google.android.material.R$dimen.m3_multiline_hint_filled_text_extra_space
            int r2 = r2.getDimensionPixelSize(r4)
            r0.extraSpaceBetweenPlaceholderAndHint = r2
            int r2 = com.google.android.material.R$styleable.TextInputLayout_boxStrokeWidth
            android.content.res.Resources r4 = r1.getResources()
            int r5 = com.google.android.material.R$dimen.mtrl_textinput_box_stroke_width_default
            int r4 = r4.getDimensionPixelSize(r5)
            int r2 = r3.getDimensionPixelSize(r2, r4)
            r0.boxStrokeWidthDefaultPx = r2
            int r2 = com.google.android.material.R$styleable.TextInputLayout_boxStrokeWidthFocused
            android.content.res.Resources r4 = r1.getResources()
            int r5 = com.google.android.material.R$dimen.mtrl_textinput_box_stroke_width_focused
            int r4 = r4.getDimensionPixelSize(r5)
            int r2 = r3.getDimensionPixelSize(r2, r4)
            r0.boxStrokeWidthFocusedPx = r2
            int r2 = r0.boxStrokeWidthDefaultPx
            r0.boxStrokeWidthPx = r2
            int r2 = com.google.android.material.R$styleable.TextInputLayout_boxCornerRadiusTopStart
            r4 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r2 = r3.getDimension(r2, r4)
            int r5 = com.google.android.material.R$styleable.TextInputLayout_boxCornerRadiusTopEnd
            float r5 = r3.getDimension(r5, r4)
            int r8 = com.google.android.material.R$styleable.TextInputLayout_boxCornerRadiusBottomEnd
            float r8 = r3.getDimension(r8, r4)
            int r9 = com.google.android.material.R$styleable.TextInputLayout_boxCornerRadiusBottomStart
            float r4 = r3.getDimension(r9, r4)
            com.google.android.material.shape.ShapeAppearanceModel r9 = r0.shapeAppearanceModel
            com.google.android.material.shape.ShapeAppearanceModel$Builder r9 = r9.toBuilder()
            r17 = 0
            int r18 = (r2 > r17 ? 1 : (r2 == r17 ? 0 : -1))
            if (r18 < 0) goto L_0x016b
            r9.setTopLeftCornerSize((float) r2)
        L_0x016b:
            int r2 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r2 < 0) goto L_0x0172
            r9.setTopRightCornerSize((float) r5)
        L_0x0172:
            int r2 = (r8 > r17 ? 1 : (r8 == r17 ? 0 : -1))
            if (r2 < 0) goto L_0x0179
            r9.setBottomRightCornerSize((float) r8)
        L_0x0179:
            int r2 = (r4 > r17 ? 1 : (r4 == r17 ? 0 : -1))
            if (r2 < 0) goto L_0x0180
            r9.setBottomLeftCornerSize((float) r4)
        L_0x0180:
            com.google.android.material.shape.ShapeAppearanceModel r2 = r9.build()
            r0.shapeAppearanceModel = r2
            int r2 = com.google.android.material.R$styleable.TextInputLayout_boxBackgroundColor
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (androidx.appcompat.widget.TintTypedArray) r3, (int) r2)
            if (r2 == 0) goto L_0x01e6
            int r4 = r2.getDefaultColor()
            r0.defaultFilledBackgroundColor = r4
            r0.boxBackgroundColor = r4
            boolean r4 = r2.isStateful()
            r5 = 16843623(0x1010367, float:2.3696E-38)
            r8 = -16842910(0xfffffffffefeff62, float:-1.6947497E38)
            if (r4 == 0) goto L_0x01c7
            int[] r4 = new int[]{r8}
            int r4 = r2.getColorForState(r4, r7)
            r0.disabledFilledBackgroundColor = r4
            r4 = 16842908(0x101009c, float:2.3693995E-38)
            r8 = 16842910(0x101009e, float:2.3694E-38)
            int[] r4 = new int[]{r4, r8}
            int r4 = r2.getColorForState(r4, r7)
            r0.focusedFilledBackgroundColor = r4
            int[] r4 = new int[]{r5, r8}
            int r2 = r2.getColorForState(r4, r7)
            r0.hoveredFilledBackgroundColor = r2
            goto L_0x01f1
        L_0x01c7:
            int r2 = r0.defaultFilledBackgroundColor
            r0.focusedFilledBackgroundColor = r2
            int r2 = com.google.android.material.R$color.mtrl_filled_background_color
            android.content.res.ColorStateList r2 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r1, r2)
            int[] r4 = new int[]{r8}
            int r4 = r2.getColorForState(r4, r7)
            r0.disabledFilledBackgroundColor = r4
            int[] r4 = new int[]{r5}
            int r2 = r2.getColorForState(r4, r7)
            r0.hoveredFilledBackgroundColor = r2
            goto L_0x01f1
        L_0x01e6:
            r4 = 0
            r0.boxBackgroundColor = r4
            r0.defaultFilledBackgroundColor = r4
            r0.disabledFilledBackgroundColor = r4
            r0.focusedFilledBackgroundColor = r4
            r0.hoveredFilledBackgroundColor = r4
        L_0x01f1:
            int r2 = com.google.android.material.R$styleable.TextInputLayout_android_textColorHint
            boolean r4 = r3.hasValue(r2)
            if (r4 == 0) goto L_0x0201
            android.content.res.ColorStateList r2 = r3.getColorStateList(r2)
            r0.focusedTextColor = r2
            r0.defaultHintTextColor = r2
        L_0x0201:
            int r2 = com.google.android.material.R$styleable.TextInputLayout_boxStrokeColor
            android.content.res.ColorStateList r4 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (androidx.appcompat.widget.TintTypedArray) r3, (int) r2)
            r5 = 0
            int r2 = r3.getColor(r2, r5)
            r0.focusedStrokeColor = r2
            int r2 = com.google.android.material.R$color.mtrl_textinput_default_box_stroke_color
            int r2 = androidx.core.content.ContextCompat.getColor(r1, r2)
            r0.defaultStrokeColor = r2
            int r2 = com.google.android.material.R$color.mtrl_textinput_disabled_color
            int r2 = androidx.core.content.ContextCompat.getColor(r1, r2)
            r0.disabledColor = r2
            int r2 = com.google.android.material.R$color.mtrl_textinput_hovered_box_stroke_color
            int r2 = androidx.core.content.ContextCompat.getColor(r1, r2)
            r0.hoveredStrokeColor = r2
            if (r4 == 0) goto L_0x022b
            r0.setBoxStrokeColorStateList(r4)
        L_0x022b:
            int r2 = com.google.android.material.R$styleable.TextInputLayout_boxStrokeErrorColor
            boolean r4 = r3.hasValue(r2)
            if (r4 == 0) goto L_0x023a
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (androidx.appcompat.widget.TintTypedArray) r3, (int) r2)
            r0.setBoxStrokeErrorColor(r1)
        L_0x023a:
            int r1 = r3.getResourceId(r15, r7)
            r4 = 0
            if (r1 == r7) goto L_0x0248
            int r1 = r3.getResourceId(r15, r4)
            r0.setHintTextAppearance(r1)
        L_0x0248:
            int r1 = com.google.android.material.R$styleable.TextInputLayout_cursorColor
            android.content.res.ColorStateList r1 = r3.getColorStateList(r1)
            r0.cursorColor = r1
            int r1 = com.google.android.material.R$styleable.TextInputLayout_cursorErrorColor
            android.content.res.ColorStateList r1 = r3.getColorStateList(r1)
            r0.cursorErrorColor = r1
            int r1 = r3.getResourceId(r13, r4)
            int r2 = com.google.android.material.R$styleable.TextInputLayout_errorContentDescription
            java.lang.CharSequence r2 = r3.getText(r2)
            int r5 = com.google.android.material.R$styleable.TextInputLayout_errorAccessibilityLiveRegion
            r8 = 1
            int r5 = r3.getInt(r5, r8)
            int r8 = com.google.android.material.R$styleable.TextInputLayout_errorEnabled
            boolean r8 = r3.getBoolean(r8, r4)
            int r9 = r3.getResourceId(r14, r4)
            int r13 = com.google.android.material.R$styleable.TextInputLayout_helperTextEnabled
            boolean r13 = r3.getBoolean(r13, r4)
            int r14 = com.google.android.material.R$styleable.TextInputLayout_helperText
            java.lang.CharSequence r14 = r3.getText(r14)
            int r15 = com.google.android.material.R$styleable.TextInputLayout_placeholderTextAppearance
            int r15 = r3.getResourceId(r15, r4)
            int r7 = com.google.android.material.R$styleable.TextInputLayout_placeholderText
            java.lang.CharSequence r7 = r3.getText(r7)
            r21 = r14
            int r14 = com.google.android.material.R$styleable.TextInputLayout_counterEnabled
            boolean r14 = r3.getBoolean(r14, r4)
            int r4 = com.google.android.material.R$styleable.TextInputLayout_counterMaxLength
            r22 = r14
            r14 = -1
            int r4 = r3.getInt(r4, r14)
            r0.setCounterMaxLength(r4)
            r4 = 0
            int r11 = r3.getResourceId(r11, r4)
            r0.counterTextAppearance = r11
            int r11 = r3.getResourceId(r12, r4)
            r0.counterOverflowTextAppearance = r11
            int r11 = com.google.android.material.R$styleable.TextInputLayout_boxBackgroundMode
            int r4 = r3.getInt(r11, r4)
            r0.setBoxBackgroundMode(r4)
            r0.setErrorContentDescription(r2)
            r0.setErrorAccessibilityLiveRegion(r5)
            int r2 = r0.counterOverflowTextAppearance
            r0.setCounterOverflowTextAppearance(r2)
            r0.setHelperTextTextAppearance(r9)
            r0.setErrorTextAppearance(r1)
            int r1 = r0.counterTextAppearance
            r0.setCounterTextAppearance(r1)
            r0.setPlaceholderText(r7)
            r0.setPlaceholderTextAppearance(r15)
            int r1 = com.google.android.material.R$styleable.TextInputLayout_errorTextColor
            boolean r2 = r3.hasValue(r1)
            if (r2 == 0) goto L_0x02e0
            android.content.res.ColorStateList r1 = r3.getColorStateList(r1)
            r0.setErrorTextColor(r1)
        L_0x02e0:
            int r1 = com.google.android.material.R$styleable.TextInputLayout_helperTextTextColor
            boolean r2 = r3.hasValue(r1)
            if (r2 == 0) goto L_0x02ef
            android.content.res.ColorStateList r1 = r3.getColorStateList(r1)
            r0.setHelperTextColor(r1)
        L_0x02ef:
            int r1 = com.google.android.material.R$styleable.TextInputLayout_hintTextColor
            boolean r2 = r3.hasValue(r1)
            if (r2 == 0) goto L_0x02fe
            android.content.res.ColorStateList r1 = r3.getColorStateList(r1)
            r0.setHintTextColor(r1)
        L_0x02fe:
            int r1 = com.google.android.material.R$styleable.TextInputLayout_counterTextColor
            boolean r2 = r3.hasValue(r1)
            if (r2 == 0) goto L_0x030d
            android.content.res.ColorStateList r1 = r3.getColorStateList(r1)
            r0.setCounterTextColor(r1)
        L_0x030d:
            int r1 = com.google.android.material.R$styleable.TextInputLayout_counterOverflowTextColor
            boolean r2 = r3.hasValue(r1)
            if (r2 == 0) goto L_0x031c
            android.content.res.ColorStateList r1 = r3.getColorStateList(r1)
            r0.setCounterOverflowTextColor(r1)
        L_0x031c:
            int r1 = com.google.android.material.R$styleable.TextInputLayout_placeholderTextColor
            boolean r2 = r3.hasValue(r1)
            if (r2 == 0) goto L_0x032b
            android.content.res.ColorStateList r1 = r3.getColorStateList(r1)
            r0.setPlaceholderTextColor(r1)
        L_0x032b:
            com.google.android.material.textfield.EndCompoundLayout r1 = new com.google.android.material.textfield.EndCompoundLayout
            r1.<init>(r0, r3)
            r0.endLayout = r1
            int r2 = com.google.android.material.R$styleable.TextInputLayout_android_enabled
            r4 = 1
            boolean r2 = r3.getBoolean(r2, r4)
            int r5 = com.google.android.material.R$styleable.TextInputLayout_hintMaxLines
            int r5 = r3.getInt(r5, r4)
            r0.setHintMaxLines(r5)
            r3.recycle()
            r3 = 2
            r0.setImportantForAccessibility(r3)
            r0.setImportantForAutofill(r4)
            r10.addView(r6)
            r10.addView(r1)
            r0.addView(r10)
            r0.setEnabled(r2)
            r0.setHelperTextEnabled(r13)
            r0.setErrorEnabled(r8)
            r1 = r22
            r0.setCounterEnabled(r1)
            r1 = r21
            r0.setHelperText(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void onGlobalLayout() {
        this.endLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        this.globalLayoutListenerAdded = false;
        boolean updateEditTextHeightBasedOnIcon = updateEditTextHeightBasedOnIcon();
        boolean updateDummyDrawables = updateDummyDrawables();
        if (updateEditTextHeightBasedOnIcon || updateDummyDrawables) {
            this.editText.post(new TextInputLayout$$ExternalSyntheticLambda0(this));
        }
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = (layoutParams2.gravity & -113) | 16;
            this.inputFrame.addView(view, layoutParams2);
            this.inputFrame.setLayoutParams(layoutParams);
            updateInputLayoutMargins();
            setEditText((EditText) view);
            return;
        }
        super.addView(view, i, layoutParams);
    }

    public void setBoxBackgroundMode(int i) {
        if (i != this.boxBackgroundMode) {
            this.boxBackgroundMode = i;
            if (this.editText != null) {
                onApplyBoxBackgroundMode();
            }
        }
    }

    public int getBoxBackgroundMode() {
        return this.boxBackgroundMode;
    }

    private void onApplyBoxBackgroundMode() {
        assignBoxBackgroundByMode();
        updateEditTextBoxBackgroundIfNeeded();
        updateTextInputBoxState();
        updateBoxCollapsedPaddingTop();
        adjustFilledEditTextPaddingForLargeFont();
        if (this.boxBackgroundMode != 0) {
            updateInputLayoutMargins();
        }
        setDropDownMenuBackgroundIfNeeded();
    }

    private void assignBoxBackgroundByMode() {
        int i = this.boxBackgroundMode;
        if (i == 0) {
            this.boxBackground = null;
            this.boxUnderlineDefault = null;
            this.boxUnderlineFocused = null;
        } else if (i == 1) {
            this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.boxUnderlineDefault = new MaterialShapeDrawable();
            this.boxUnderlineFocused = new MaterialShapeDrawable();
        } else if (i == 2) {
            if (!this.hintEnabled || (this.boxBackground instanceof CutoutDrawable)) {
                this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            } else {
                this.boxBackground = CutoutDrawable.create(this.shapeAppearanceModel);
            }
            this.boxUnderlineDefault = null;
            this.boxUnderlineFocused = null;
        } else {
            throw new IllegalArgumentException(this.boxBackgroundMode + " is illegal; only @BoxBackgroundMode constants are supported.");
        }
    }

    /* access modifiers changed from: package-private */
    public void updateEditTextBoxBackgroundIfNeeded() {
        EditText editText2 = this.editText;
        if (editText2 != null && this.boxBackground != null) {
            if ((this.boxBackgroundApplied || editText2.getBackground() == null) && this.boxBackgroundMode != 0) {
                updateEditTextBoxBackground();
                this.boxBackgroundApplied = true;
            }
        }
    }

    private void updateEditTextBoxBackground() {
        this.editText.setBackground(getEditTextBoxBackground());
    }

    private Drawable getEditTextBoxBackground() {
        EditText editText2 = this.editText;
        if (!(editText2 instanceof AutoCompleteTextView) || EditTextUtils.isEditable(editText2)) {
            return this.boxBackground;
        }
        int color = MaterialColors.getColor(this.editText, androidx.appcompat.R$attr.colorControlHighlight);
        int i = this.boxBackgroundMode;
        if (i == 2) {
            return getOutlinedBoxBackgroundWithRipple(getContext(), this.boxBackground, color, EDIT_TEXT_BACKGROUND_RIPPLE_STATE);
        }
        if (i == 1) {
            return getFilledBoxBackgroundWithRipple(this.boxBackground, this.boxBackgroundColor, color, EDIT_TEXT_BACKGROUND_RIPPLE_STATE);
        }
        return null;
    }

    private static Drawable getOutlinedBoxBackgroundWithRipple(Context context, MaterialShapeDrawable materialShapeDrawable, int i, int[][] iArr) {
        int color = MaterialColors.getColor(context, R$attr.colorSurface, "TextInputLayout");
        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(materialShapeDrawable.getShapeAppearanceModel());
        int layer = MaterialColors.layer(i, color, 0.1f);
        materialShapeDrawable2.setFillColor(new ColorStateList(iArr, new int[]{layer, 0}));
        materialShapeDrawable2.setTint(color);
        ColorStateList colorStateList = new ColorStateList(iArr, new int[]{layer, color});
        MaterialShapeDrawable materialShapeDrawable3 = new MaterialShapeDrawable(materialShapeDrawable.getShapeAppearanceModel());
        materialShapeDrawable3.setTint(-1);
        return new LayerDrawable(new Drawable[]{new RippleDrawable(colorStateList, materialShapeDrawable2, materialShapeDrawable3), materialShapeDrawable});
    }

    private static Drawable getFilledBoxBackgroundWithRipple(MaterialShapeDrawable materialShapeDrawable, int i, int i2, int[][] iArr) {
        return new RippleDrawable(new ColorStateList(iArr, new int[]{MaterialColors.layer(i2, i, 0.1f), i}), materialShapeDrawable, materialShapeDrawable);
    }

    private void setDropDownMenuBackgroundIfNeeded() {
        EditText editText2 = this.editText;
        if (editText2 instanceof AutoCompleteTextView) {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText2;
            if (autoCompleteTextView.getDropDownBackground() == null) {
                int i = this.boxBackgroundMode;
                if (i == 2) {
                    autoCompleteTextView.setDropDownBackgroundDrawable(getOrCreateOutlinedDropDownMenuBackground());
                } else if (i == 1) {
                    autoCompleteTextView.setDropDownBackgroundDrawable(getOrCreateFilledDropDownMenuBackground());
                }
            }
        }
    }

    private Drawable getOrCreateOutlinedDropDownMenuBackground() {
        if (this.outlinedDropDownMenuBackground == null) {
            this.outlinedDropDownMenuBackground = getDropDownMaterialShapeDrawable(true);
        }
        return this.outlinedDropDownMenuBackground;
    }

    private Drawable getOrCreateFilledDropDownMenuBackground() {
        if (this.filledDropDownMenuBackground == null) {
            StateListDrawable stateListDrawable = new StateListDrawable();
            this.filledDropDownMenuBackground = stateListDrawable;
            stateListDrawable.addState(new int[]{16842922}, getOrCreateOutlinedDropDownMenuBackground());
            this.filledDropDownMenuBackground.addState(new int[0], getDropDownMaterialShapeDrawable(false));
        }
        return this.filledDropDownMenuBackground;
    }

    private MaterialShapeDrawable getDropDownMaterialShapeDrawable(boolean z) {
        float f;
        float dimensionPixelOffset = (float) getResources().getDimensionPixelOffset(R$dimen.mtrl_shape_corner_size_small_component);
        float f2 = z ? dimensionPixelOffset : 0.0f;
        EditText editText2 = this.editText;
        if (editText2 instanceof MaterialAutoCompleteTextView) {
            f = ((MaterialAutoCompleteTextView) editText2).getPopupElevation();
        } else {
            f = (float) getResources().getDimensionPixelOffset(R$dimen.m3_comp_outlined_autocomplete_menu_container_elevation);
        }
        int dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R$dimen.mtrl_exposed_dropdown_menu_popup_vertical_padding);
        ShapeAppearanceModel build = ShapeAppearanceModel.builder().setTopLeftCornerSize(f2).setTopRightCornerSize(f2).setBottomLeftCornerSize(dimensionPixelOffset).setBottomRightCornerSize(dimensionPixelOffset).build();
        EditText editText3 = this.editText;
        MaterialShapeDrawable createWithElevationOverlay = MaterialShapeDrawable.createWithElevationOverlay(getContext(), f, editText3 instanceof MaterialAutoCompleteTextView ? ((MaterialAutoCompleteTextView) editText3).getDropDownBackgroundTintList() : null);
        createWithElevationOverlay.setShapeAppearanceModel(build);
        createWithElevationOverlay.setPadding(0, dimensionPixelOffset2, 0, dimensionPixelOffset2);
        return createWithElevationOverlay;
    }

    private void updateBoxCollapsedPaddingTop() {
        if (this.boxBackgroundMode != 1) {
            return;
        }
        if (MaterialResources.isFontScaleAtLeast2_0(getContext())) {
            this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(R$dimen.material_font_2_0_box_collapsed_padding_top);
        } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
            this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(R$dimen.material_font_1_3_box_collapsed_padding_top);
        }
    }

    private void adjustFilledEditTextPaddingForLargeFont() {
        if (this.editText != null && this.boxBackgroundMode == 1) {
            if (!isHintTextSingleLine()) {
                EditText editText2 = this.editText;
                editText2.setPaddingRelative(editText2.getPaddingStart(), (int) (this.collapsingTextHelper.getCollapsedTextHeight() + ((float) this.extraSpaceBetweenPlaceholderAndHint)), this.editText.getPaddingEnd(), getResources().getDimensionPixelSize(R$dimen.material_filled_edittext_font_1_3_padding_bottom));
            } else if (MaterialResources.isFontScaleAtLeast2_0(getContext())) {
                EditText editText3 = this.editText;
                editText3.setPaddingRelative(editText3.getPaddingStart(), getResources().getDimensionPixelSize(R$dimen.material_filled_edittext_font_2_0_padding_top), this.editText.getPaddingEnd(), getResources().getDimensionPixelSize(R$dimen.material_filled_edittext_font_2_0_padding_bottom));
            } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
                EditText editText4 = this.editText;
                editText4.setPaddingRelative(editText4.getPaddingStart(), getResources().getDimensionPixelSize(R$dimen.material_filled_edittext_font_1_3_padding_top), this.editText.getPaddingEnd(), getResources().getDimensionPixelSize(R$dimen.material_filled_edittext_font_1_3_padding_bottom));
            }
        }
    }

    public void setBoxStrokeColorStateList(ColorStateList colorStateList) {
        if (colorStateList.isStateful()) {
            this.defaultStrokeColor = colorStateList.getDefaultColor();
            this.disabledColor = colorStateList.getColorForState(new int[]{-16842910}, -1);
            this.hoveredStrokeColor = colorStateList.getColorForState(new int[]{16843623, 16842910}, -1);
            this.focusedStrokeColor = colorStateList.getColorForState(new int[]{16842908, 16842910}, -1);
        } else if (this.focusedStrokeColor != colorStateList.getDefaultColor()) {
            this.focusedStrokeColor = colorStateList.getDefaultColor();
        }
        updateTextInputBoxState();
    }

    public void setBoxStrokeErrorColor(ColorStateList colorStateList) {
        if (this.strokeErrorColor != colorStateList) {
            this.strokeErrorColor = colorStateList;
            updateTextInputBoxState();
        }
    }

    public void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel2) {
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable != null && materialShapeDrawable.getShapeAppearanceModel() != shapeAppearanceModel2) {
            this.shapeAppearanceModel = shapeAppearanceModel2;
            applyBoxAttributes();
        }
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        EditText editText2 = this.editText;
        if (editText2 == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i);
            return;
        }
        if (this.originalHint != null) {
            boolean z = this.isProvidingHint;
            this.isProvidingHint = false;
            CharSequence hint2 = editText2.getHint();
            this.editText.setHint(this.originalHint);
            try {
                super.dispatchProvideAutofillStructure(viewStructure, i);
            } finally {
                this.editText.setHint(hint2);
                this.isProvidingHint = z;
            }
        } else {
            viewStructure.setAutofillId(getAutofillId());
            onProvideAutofillStructure(viewStructure, i);
            onProvideAutofillVirtualStructure(viewStructure, i);
            viewStructure.setChildCount(this.inputFrame.getChildCount());
            for (int i2 = 0; i2 < this.inputFrame.getChildCount(); i2++) {
                View childAt = this.inputFrame.getChildAt(i2);
                ViewStructure newChild = viewStructure.newChild(i2);
                childAt.dispatchProvideAutofillStructure(newChild, i);
                if (childAt == this.editText) {
                    newChild.setHint(getHint());
                }
            }
        }
    }

    private void setEditText(EditText editText2) {
        if (this.editText == null) {
            if (getEndIconMode() != 3 && !(editText2 instanceof TextInputEditText)) {
                Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
            }
            this.editText = editText2;
            int i = this.minEms;
            if (i != -1) {
                setMinEms(i);
            } else {
                setMinWidth(this.minWidth);
            }
            int i2 = this.maxEms;
            if (i2 != -1) {
                setMaxEms(i2);
            } else {
                setMaxWidth(this.maxWidth);
            }
            this.boxBackgroundApplied = false;
            onApplyBoxBackgroundMode();
            setTextInputAccessibilityDelegate(new AccessibilityDelegate(this));
            this.collapsingTextHelper.setTypefaces(this.editText.getTypeface());
            this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
            this.collapsingTextHelper.setExpandedLetterSpacing(this.editText.getLetterSpacing());
            int gravity = this.editText.getGravity();
            this.collapsingTextHelper.setCollapsedTextGravity((gravity & -113) | 48);
            this.collapsingTextHelper.setExpandedTextGravity(gravity);
            this.originalEditTextMinimumHeight = editText2.getMinimumHeight();
            this.editText.addTextChangedListener(new TextWatcher(editText2) {
                int previousLineCount;
                final /* synthetic */ EditText val$editText;

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                {
                    this.val$editText = r2;
                    this.previousLineCount = r2.getLineCount();
                }

                public void afterTextChanged(Editable editable) {
                    int i;
                    TextInputLayout textInputLayout = TextInputLayout.this;
                    textInputLayout.updateLabelState(!textInputLayout.restoringSavedState);
                    TextInputLayout textInputLayout2 = TextInputLayout.this;
                    if (textInputLayout2.counterEnabled) {
                        textInputLayout2.updateCounter(editable);
                    }
                    if (TextInputLayout.this.placeholderEnabled) {
                        TextInputLayout.this.updatePlaceholderText(editable);
                    }
                    int lineCount = this.val$editText.getLineCount();
                    int i2 = this.previousLineCount;
                    if (lineCount != i2) {
                        if (lineCount < i2 && this.val$editText.getMinimumHeight() != (i = TextInputLayout.this.originalEditTextMinimumHeight)) {
                            this.val$editText.setMinimumHeight(i);
                        }
                        this.previousLineCount = lineCount;
                    }
                }
            });
            if (this.defaultHintTextColor == null) {
                this.defaultHintTextColor = this.editText.getHintTextColors();
            }
            if (this.hintEnabled) {
                if (TextUtils.isEmpty(this.hint)) {
                    CharSequence hint2 = this.editText.getHint();
                    this.originalHint = hint2;
                    setHint(hint2);
                    this.editText.setHint((CharSequence) null);
                }
                this.isProvidingHint = true;
            }
            updateCursorColor();
            if (this.counterView != null) {
                updateCounter(this.editText.getText());
            }
            updateEditTextBackground();
            this.indicatorViewController.adjustIndicatorPadding();
            this.startLayout.bringToFront();
            this.endLayout.bringToFront();
            dispatchOnEditTextAttached();
            this.endLayout.updateSuffixTextViewPadding();
            if (!isEnabled()) {
                editText2.setEnabled(false);
            }
            updateLabelState(false, true);
            return;
        }
        throw new IllegalArgumentException("We already have an EditText, can only have one");
    }

    private void updateInputLayoutMargins() {
        if (this.boxBackgroundMode != 1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.inputFrame.getLayoutParams();
            int calculateLabelMarginTop = calculateLabelMarginTop();
            if (calculateLabelMarginTop != layoutParams.topMargin) {
                layoutParams.topMargin = calculateLabelMarginTop;
                this.inputFrame.requestLayout();
            }
        }
    }

    public int getBaseline() {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            return editText2.getBaseline() + getPaddingTop() + calculateLabelMarginTop();
        }
        return super.getBaseline();
    }

    /* access modifiers changed from: package-private */
    public void updateLabelState(boolean z) {
        updateLabelState(z, false);
    }

    private void updateLabelState(boolean z, boolean z2) {
        ColorStateList colorStateList;
        TextView textView;
        int i;
        boolean isEnabled = isEnabled();
        EditText editText2 = this.editText;
        boolean z3 = false;
        boolean z4 = editText2 != null && !TextUtils.isEmpty(editText2.getText());
        EditText editText3 = this.editText;
        if (editText3 != null && editText3.hasFocus()) {
            z3 = true;
        }
        ColorStateList colorStateList2 = this.defaultHintTextColor;
        if (colorStateList2 != null) {
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(colorStateList2);
        }
        if (!isEnabled) {
            ColorStateList colorStateList3 = this.defaultHintTextColor;
            if (colorStateList3 != null) {
                i = colorStateList3.getColorForState(new int[]{-16842910}, this.disabledColor);
            } else {
                i = this.disabledColor;
            }
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(ColorStateList.valueOf(i));
        } else if (shouldShowError()) {
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(this.indicatorViewController.getErrorViewTextColors());
        } else if (this.counterOverflowed && (textView = this.counterView) != null) {
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(textView.getTextColors());
        } else if (z3 && (colorStateList = this.focusedTextColor) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
        }
        if (z4 || !this.expandedHintEnabled || (isEnabled() && z3)) {
            if (z2 || this.hintExpanded) {
                collapseHint(z);
            }
        } else if (z2 || !this.hintExpanded) {
            expandHint(z);
        }
    }

    public EditText getEditText() {
        return this.editText;
    }

    public void setMinEms(int i) {
        this.minEms = i;
        EditText editText2 = this.editText;
        if (editText2 != null && i != -1) {
            editText2.setMinEms(i);
        }
    }

    public void setMaxEms(int i) {
        this.maxEms = i;
        EditText editText2 = this.editText;
        if (editText2 != null && i != -1) {
            editText2.setMaxEms(i);
        }
    }

    public void setMinWidth(int i) {
        this.minWidth = i;
        EditText editText2 = this.editText;
        if (editText2 != null && i != -1) {
            editText2.setMinWidth(i);
        }
    }

    public void setMaxWidth(int i) {
        this.maxWidth = i;
        EditText editText2 = this.editText;
        if (editText2 != null && i != -1) {
            editText2.setMaxWidth(i);
        }
    }

    public void setHint(CharSequence charSequence) {
        if (this.hintEnabled) {
            setHintInternal(charSequence);
            sendAccessibilityEvent(2048);
        }
    }

    private void setHintInternal(CharSequence charSequence) {
        if (!TextUtils.equals(charSequence, this.hint)) {
            this.hint = charSequence;
            this.collapsingTextHelper.setText(charSequence);
            if (!this.hintExpanded) {
                openCutout();
            }
        }
    }

    public CharSequence getHint() {
        if (this.hintEnabled) {
            return this.hint;
        }
        return null;
    }

    public boolean isProvidingHint() {
        return this.isProvidingHint;
    }

    public void setHintTextAppearance(int i) {
        this.collapsingTextHelper.setCollapsedTextAppearance(i);
        this.focusedTextColor = this.collapsingTextHelper.getCollapsedTextColor();
        if (this.editText != null) {
            updateLabelState(false);
            updateInputLayoutMargins();
        }
    }

    public void setHintTextColor(ColorStateList colorStateList) {
        if (this.focusedTextColor != colorStateList) {
            if (this.defaultHintTextColor == null) {
                this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
            }
            this.focusedTextColor = colorStateList;
            if (this.editText != null) {
                updateLabelState(false);
            }
        }
    }

    public void setHintMaxLines(int i) {
        this.collapsingTextHelper.setCollapsedMaxLines(i);
        this.collapsingTextHelper.setExpandedMaxLines(i);
        requestLayout();
    }

    public int getHintMaxLines() {
        return this.collapsingTextHelper.getExpandedMaxLines();
    }

    private boolean isHintTextSingleLine() {
        return getHintMaxLines() == 1;
    }

    public void setErrorEnabled(boolean z) {
        this.indicatorViewController.setErrorEnabled(z);
    }

    public void setErrorTextAppearance(int i) {
        this.indicatorViewController.setErrorTextAppearance(i);
    }

    public void setErrorTextColor(ColorStateList colorStateList) {
        this.indicatorViewController.setErrorViewTextColor(colorStateList);
    }

    public int getErrorCurrentTextColors() {
        return this.indicatorViewController.getErrorViewCurrentTextColor();
    }

    public void setHelperTextTextAppearance(int i) {
        this.indicatorViewController.setHelperTextAppearance(i);
    }

    public void setHelperTextColor(ColorStateList colorStateList) {
        this.indicatorViewController.setHelperTextViewTextColor(colorStateList);
    }

    public boolean isErrorEnabled() {
        return this.indicatorViewController.isErrorEnabled();
    }

    public void setHelperTextEnabled(boolean z) {
        this.indicatorViewController.setHelperTextEnabled(z);
    }

    public void setHelperText(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (!isHelperTextEnabled()) {
                setHelperTextEnabled(true);
            }
            this.indicatorViewController.showHelper(charSequence);
        } else if (isHelperTextEnabled()) {
            setHelperTextEnabled(false);
        }
    }

    public boolean isHelperTextEnabled() {
        return this.indicatorViewController.isHelperTextEnabled();
    }

    public void setErrorContentDescription(CharSequence charSequence) {
        this.indicatorViewController.setErrorContentDescription(charSequence);
    }

    public void setErrorAccessibilityLiveRegion(int i) {
        this.indicatorViewController.setErrorAccessibilityLiveRegion(i);
    }

    public void setError(CharSequence charSequence) {
        if (!this.indicatorViewController.isErrorEnabled()) {
            if (!TextUtils.isEmpty(charSequence)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (!TextUtils.isEmpty(charSequence)) {
            this.indicatorViewController.showError(charSequence);
        } else {
            this.indicatorViewController.hideError();
        }
    }

    public void setErrorIconDrawable(Drawable drawable) {
        this.endLayout.setErrorIconDrawable(drawable);
    }

    public void setCounterEnabled(boolean z) {
        if (this.counterEnabled != z) {
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.counterView = appCompatTextView;
                appCompatTextView.setId(R$id.textinput_counter);
                Typeface typeface2 = this.typeface;
                if (typeface2 != null) {
                    this.counterView.setTypeface(typeface2);
                }
                this.counterView.setMaxLines(1);
                this.indicatorViewController.addIndicator(this.counterView, 2);
                ((ViewGroup.MarginLayoutParams) this.counterView.getLayoutParams()).setMarginStart(getResources().getDimensionPixelOffset(R$dimen.mtrl_textinput_counter_margin_start));
                updateCounterTextAppearanceAndColor();
                updateCounter();
            } else {
                this.indicatorViewController.removeIndicator(this.counterView, 2);
                this.counterView = null;
            }
            this.counterEnabled = z;
        }
    }

    public void setCounterTextAppearance(int i) {
        if (this.counterTextAppearance != i) {
            this.counterTextAppearance = i;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterTextColor(ColorStateList colorStateList) {
        if (this.counterTextColor != colorStateList) {
            this.counterTextColor = colorStateList;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterOverflowTextAppearance(int i) {
        if (this.counterOverflowTextAppearance != i) {
            this.counterOverflowTextAppearance = i;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterOverflowTextColor(ColorStateList colorStateList) {
        if (this.counterOverflowTextColor != colorStateList) {
            this.counterOverflowTextColor = colorStateList;
            updateCounterTextAppearanceAndColor();
        }
    }

    public void setCounterMaxLength(int i) {
        if (this.counterMaxLength != i) {
            if (i > 0) {
                this.counterMaxLength = i;
            } else {
                this.counterMaxLength = -1;
            }
            if (this.counterEnabled) {
                updateCounter();
            }
        }
    }

    private void updateCounter() {
        if (this.counterView != null) {
            EditText editText2 = this.editText;
            updateCounter(editText2 == null ? null : editText2.getText());
        }
    }

    /* access modifiers changed from: package-private */
    public void updateCounter(Editable editable) {
        int countLength = this.lengthCounter.countLength(editable);
        boolean z = this.counterOverflowed;
        int i = this.counterMaxLength;
        if (i == -1) {
            this.counterView.setText(String.valueOf(countLength));
            this.counterView.setContentDescription((CharSequence) null);
            this.counterOverflowed = false;
        } else {
            this.counterOverflowed = countLength > i;
            updateCounterContentDescription(getContext(), this.counterView, countLength, this.counterMaxLength, this.counterOverflowed);
            if (z != this.counterOverflowed) {
                updateCounterTextAppearanceAndColor();
            }
            this.counterView.setText(BidiFormatter.getInstance().unicodeWrap(getContext().getString(R$string.character_counter_pattern, new Object[]{Integer.valueOf(countLength), Integer.valueOf(this.counterMaxLength)})));
        }
        if (this.editText != null && z != this.counterOverflowed) {
            updateLabelState(false);
            updateTextInputBoxState();
            updateEditTextBackground();
        }
    }

    private static void updateCounterContentDescription(Context context, TextView textView, int i, int i2, boolean z) {
        int i3;
        if (z) {
            i3 = R$string.character_counter_overflowed_content_description;
        } else {
            i3 = R$string.character_counter_content_description;
        }
        textView.setContentDescription(context.getString(i3, new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
    }

    public void setPlaceholderText(CharSequence charSequence) {
        if (this.placeholderTextView == null) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
            this.placeholderTextView = appCompatTextView;
            appCompatTextView.setId(R$id.textinput_placeholder);
            this.placeholderTextView.setImportantForAccessibility(2);
            Fade createPlaceholderFadeTransition = createPlaceholderFadeTransition();
            this.placeholderFadeIn = createPlaceholderFadeTransition;
            createPlaceholderFadeTransition.setStartDelay(67);
            this.placeholderFadeOut = createPlaceholderFadeTransition();
            setPlaceholderTextAppearance(this.placeholderTextAppearance);
            setPlaceholderTextColor(this.placeholderTextColor);
        }
        if (TextUtils.isEmpty(charSequence)) {
            setPlaceholderTextEnabled(false);
        } else {
            if (!this.placeholderEnabled) {
                setPlaceholderTextEnabled(true);
            }
            this.placeholderText = charSequence;
        }
        updatePlaceholderText();
    }

    public CharSequence getPlaceholderText() {
        if (this.placeholderEnabled) {
            return this.placeholderText;
        }
        return null;
    }

    private void setPlaceholderTextEnabled(boolean z) {
        if (this.placeholderEnabled != z) {
            if (z) {
                addPlaceholderTextView();
            } else {
                removePlaceholderTextView();
                this.placeholderTextView = null;
            }
            this.placeholderEnabled = z;
        }
    }

    private Fade createPlaceholderFadeTransition() {
        Fade fade = new Fade();
        fade.setDuration((long) MotionUtils.resolveThemeDuration(getContext(), R$attr.motionDurationShort2, 87));
        fade.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), R$attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR));
        return fade;
    }

    private void updatePlaceholderText() {
        EditText editText2 = this.editText;
        updatePlaceholderText(editText2 == null ? null : editText2.getText());
    }

    /* access modifiers changed from: private */
    public void updatePlaceholderText(Editable editable) {
        if (this.lengthCounter.countLength(editable) != 0 || this.hintExpanded) {
            hidePlaceholderText();
        } else {
            showPlaceholderText();
        }
    }

    private void showPlaceholderText() {
        if (this.placeholderTextView != null && this.placeholderEnabled && !TextUtils.isEmpty(this.placeholderText)) {
            this.placeholderTextView.setText(this.placeholderText);
            TransitionManager.beginDelayedTransition(this.inputFrame, this.placeholderFadeIn);
            this.placeholderTextView.setVisibility(0);
            this.placeholderTextView.bringToFront();
            announceForAccessibility(this.placeholderText);
        }
    }

    private void hidePlaceholderText() {
        TextView textView = this.placeholderTextView;
        if (textView != null && this.placeholderEnabled) {
            textView.setText((CharSequence) null);
            TransitionManager.beginDelayedTransition(this.inputFrame, this.placeholderFadeOut);
            this.placeholderTextView.setVisibility(4);
        }
    }

    private void addPlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            this.inputFrame.addView(textView);
            this.placeholderTextView.setVisibility(0);
        }
    }

    private void removePlaceholderTextView() {
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            textView.setVisibility(8);
        }
    }

    public void setPlaceholderTextColor(ColorStateList colorStateList) {
        if (this.placeholderTextColor != colorStateList) {
            this.placeholderTextColor = colorStateList;
            TextView textView = this.placeholderTextView;
            if (textView != null && colorStateList != null) {
                textView.setTextColor(colorStateList);
            }
        }
    }

    public void setPlaceholderTextAppearance(int i) {
        this.placeholderTextAppearance = i;
        TextView textView = this.placeholderTextView;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, i);
        }
    }

    public CharSequence getPrefixText() {
        return this.startLayout.getPrefixText();
    }

    public TextView getPrefixTextView() {
        return this.startLayout.getPrefixTextView();
    }

    public CharSequence getSuffixText() {
        return this.endLayout.getSuffixText();
    }

    public void setEnabled(boolean z) {
        recursiveSetEnabled(this, z);
        super.setEnabled(z);
    }

    private static void recursiveSetEnabled(ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setEnabled(z);
            if (childAt instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) childAt, z);
            }
        }
    }

    public int getCounterMaxLength() {
        return this.counterMaxLength;
    }

    /* access modifiers changed from: package-private */
    public CharSequence getCounterOverflowDescription() {
        TextView textView;
        if (!this.counterEnabled || !this.counterOverflowed || (textView = this.counterView) == null) {
            return null;
        }
        return textView.getContentDescription();
    }

    private void updateCounterTextAppearanceAndColor() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        TextView textView = this.counterView;
        if (textView != null) {
            setTextAppearanceCompatWithErrorFallback(textView, this.counterOverflowed ? this.counterOverflowTextAppearance : this.counterTextAppearance);
            if (!this.counterOverflowed && (colorStateList2 = this.counterTextColor) != null) {
                this.counterView.setTextColor(colorStateList2);
            }
            if (this.counterOverflowed && (colorStateList = this.counterOverflowTextColor) != null) {
                this.counterView.setTextColor(colorStateList);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setTextAppearanceCompatWithErrorFallback(TextView textView, int i) {
        try {
            TextViewCompat.setTextAppearance(textView, i);
            if (textView.getTextColors().getDefaultColor() != -65281) {
                return;
            }
        } catch (Exception unused) {
        }
        TextViewCompat.setTextAppearance(textView, androidx.appcompat.R$style.TextAppearance_AppCompat_Caption);
        textView.setTextColor(ContextCompat.getColor(getContext(), R$color.design_error));
    }

    private int calculateLabelMarginTop() {
        if (!this.hintEnabled) {
            return 0;
        }
        int i = this.boxBackgroundMode;
        if (i == 0) {
            return (int) this.collapsingTextHelper.getCollapsedTextHeight();
        }
        if (i != 2) {
            return 0;
        }
        if (isHintTextSingleLine()) {
            return (int) (this.collapsingTextHelper.getCollapsedTextHeight() / 2.0f);
        }
        return Math.max(0, (int) (this.collapsingTextHelper.getCollapsedTextHeight() - (this.collapsingTextHelper.getCollapsedSingleLineHeight() / 2.0f)));
    }

    private Rect calculateCollapsedTextBounds(Rect rect) {
        if (this.editText != null) {
            Rect rect2 = this.tmpBoundsRect;
            boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
            rect2.bottom = rect.bottom;
            int i = this.boxBackgroundMode;
            if (i == 1) {
                rect2.left = getLabelLeftBoundAlignedWithPrefixAndSuffix(rect.left, isLayoutRtl);
                rect2.top = rect.top + this.boxCollapsedPaddingTopPx;
                rect2.right = getLabelRightBoundAlignedWithPrefixAndSuffix(rect.right, isLayoutRtl);
                return rect2;
            } else if (i != 2) {
                rect2.left = getLabelLeftBoundAlignedWithPrefixAndSuffix(rect.left, isLayoutRtl);
                rect2.top = getPaddingTop();
                rect2.right = getLabelRightBoundAlignedWithPrefixAndSuffix(rect.right, isLayoutRtl);
                return rect2;
            } else {
                rect2.left = rect.left + this.editText.getPaddingLeft();
                rect2.top = rect.top - calculateLabelMarginTop();
                rect2.right = rect.right - this.editText.getPaddingRight();
                return rect2;
            }
        } else {
            throw new IllegalStateException();
        }
    }

    private int getLabelLeftBoundAlignedWithPrefixAndSuffix(int i, boolean z) {
        int compoundPaddingLeft;
        if (!z && getPrefixText() != null) {
            compoundPaddingLeft = this.startLayout.getPrefixTextStartOffset();
        } else if (!z || getSuffixText() == null) {
            compoundPaddingLeft = this.editText.getCompoundPaddingLeft();
        } else {
            compoundPaddingLeft = this.endLayout.getSuffixTextEndOffset();
        }
        return i + compoundPaddingLeft;
    }

    private int getLabelRightBoundAlignedWithPrefixAndSuffix(int i, boolean z) {
        int compoundPaddingRight;
        if (!z && getSuffixText() != null) {
            compoundPaddingRight = this.endLayout.getSuffixTextEndOffset();
        } else if (!z || getPrefixText() == null) {
            compoundPaddingRight = this.editText.getCompoundPaddingRight();
        } else {
            compoundPaddingRight = this.startLayout.getPrefixTextStartOffset();
        }
        return i - compoundPaddingRight;
    }

    private Rect calculateExpandedTextBounds(Rect rect) {
        float f;
        if (this.editText != null) {
            Rect rect2 = this.tmpBoundsRect;
            if (isHintTextSingleLine()) {
                f = this.collapsingTextHelper.getExpandedTextSingleLineHeight();
            } else {
                f = this.collapsingTextHelper.getExpandedTextFullSingleLineHeight() * ((float) this.collapsingTextHelper.getExpandedLineCount());
            }
            rect2.left = rect.left + this.editText.getCompoundPaddingLeft();
            rect2.top = calculateExpandedLabelTop(rect, f);
            rect2.right = rect.right - this.editText.getCompoundPaddingRight();
            rect2.bottom = calculateExpandedLabelBottom(rect, rect2, f);
            return rect2;
        }
        throw new IllegalStateException();
    }

    private int calculateExpandedLabelTop(Rect rect, float f) {
        if (isSingleLineFilledTextField()) {
            return (int) (((float) rect.centerY()) - (f / 2.0f));
        }
        return (rect.top + this.editText.getCompoundPaddingTop()) - ((this.boxBackgroundMode != 0 || isHintTextSingleLine()) ? 0 : (int) (this.collapsingTextHelper.getExpandedTextSingleLineHeight() / 2.0f));
    }

    private int calculateExpandedLabelBottom(Rect rect, Rect rect2, float f) {
        if (isSingleLineFilledTextField()) {
            return (int) (((float) rect2.top) + f);
        }
        return rect.bottom - this.editText.getCompoundPaddingBottom();
    }

    private boolean isSingleLineFilledTextField() {
        return this.boxBackgroundMode == 1 && this.editText.getMinLines() <= 1;
    }

    private int calculateBoxBackgroundColor() {
        return this.boxBackgroundMode == 1 ? MaterialColors.layer(MaterialColors.getColor((View) this, R$attr.colorSurface, 0), this.boxBackgroundColor) : this.boxBackgroundColor;
    }

    private void applyBoxAttributes() {
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable != null) {
            ShapeAppearanceModel shapeAppearanceModel2 = materialShapeDrawable.getShapeAppearanceModel();
            ShapeAppearanceModel shapeAppearanceModel3 = this.shapeAppearanceModel;
            if (shapeAppearanceModel2 != shapeAppearanceModel3) {
                this.boxBackground.setShapeAppearanceModel(shapeAppearanceModel3);
            }
            if (canDrawOutlineStroke()) {
                this.boxBackground.setStroke((float) this.boxStrokeWidthPx, this.boxStrokeColor);
            }
            int calculateBoxBackgroundColor = calculateBoxBackgroundColor();
            this.boxBackgroundColor = calculateBoxBackgroundColor;
            this.boxBackground.setFillColor(ColorStateList.valueOf(calculateBoxBackgroundColor));
            applyBoxUnderlineAttributes();
            updateEditTextBoxBackgroundIfNeeded();
        }
    }

    private void applyBoxUnderlineAttributes() {
        ColorStateList colorStateList;
        if (this.boxUnderlineDefault != null && this.boxUnderlineFocused != null) {
            if (canDrawStroke()) {
                MaterialShapeDrawable materialShapeDrawable = this.boxUnderlineDefault;
                if (this.editText.isFocused()) {
                    colorStateList = ColorStateList.valueOf(this.defaultStrokeColor);
                } else {
                    colorStateList = ColorStateList.valueOf(this.boxStrokeColor);
                }
                materialShapeDrawable.setFillColor(colorStateList);
                this.boxUnderlineFocused.setFillColor(ColorStateList.valueOf(this.boxStrokeColor));
            }
            invalidate();
        }
    }

    private boolean canDrawOutlineStroke() {
        return this.boxBackgroundMode == 2 && canDrawStroke();
    }

    private boolean canDrawStroke() {
        return this.boxStrokeWidthPx > -1 && this.boxStrokeColor != 0;
    }

    /* access modifiers changed from: package-private */
    public void updateEditTextBackground() {
        Drawable background;
        TextView textView;
        EditText editText2 = this.editText;
        if (editText2 != null && this.boxBackgroundMode == 0 && (background = editText2.getBackground()) != null) {
            if (DrawableUtils.canSafelyMutateDrawable(background)) {
                background = background.mutate();
            }
            if (shouldShowError()) {
                background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(getErrorCurrentTextColors(), PorterDuff.Mode.SRC_IN));
            } else if (!this.counterOverflowed || (textView = this.counterView) == null) {
                DrawableCompat.clearColorFilter(background);
                this.editText.refreshDrawableState();
            } else {
                background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldShowError() {
        return this.indicatorViewController.errorShouldBeShown();
    }

    class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() {
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        CharSequence error;
        boolean isEndIconChecked;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.error = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.isEndIconChecked = parcel.readInt() != 1 ? false : true;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            TextUtils.writeToParcel(this.error, parcel, i);
            parcel.writeInt(this.isEndIconChecked ? 1 : 0);
        }

        public String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + this.error + "}";
        }
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (shouldShowError()) {
            savedState.error = getError();
        }
        savedState.isEndIconChecked = this.endLayout.isEndIconChecked();
        return savedState;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setError(savedState.error);
        if (savedState.isEndIconChecked) {
            post(new Runnable() {
                public void run() {
                    TextInputLayout.this.endLayout.checkEndIcon();
                }
            });
        }
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void dispatchRestoreInstanceState(SparseArray sparseArray) {
        this.restoringSavedState = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.restoringSavedState = false;
    }

    public CharSequence getError() {
        if (this.indicatorViewController.isErrorEnabled()) {
            return this.indicatorViewController.getErrorText();
        }
        return null;
    }

    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        boolean z = true;
        if (i != 1) {
            z = false;
        }
        if (z != this.areCornerRadiiRtl) {
            float cornerSize = this.shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(this.tmpRectF);
            float cornerSize2 = this.shapeAppearanceModel.getTopRightCornerSize().getCornerSize(this.tmpRectF);
            float cornerSize3 = this.shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(this.tmpRectF);
            float cornerSize4 = this.shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(this.tmpRectF);
            CornerTreatment topLeftCorner = this.shapeAppearanceModel.getTopLeftCorner();
            CornerTreatment topRightCorner = this.shapeAppearanceModel.getTopRightCorner();
            ShapeAppearanceModel build = ShapeAppearanceModel.builder().setTopLeftCorner(topRightCorner).setTopRightCorner(topLeftCorner).setBottomLeftCorner(this.shapeAppearanceModel.getBottomRightCorner()).setBottomRightCorner(this.shapeAppearanceModel.getBottomLeftCorner()).setTopLeftCornerSize(cornerSize2).setTopRightCornerSize(cornerSize).setBottomLeftCornerSize(cornerSize4).setBottomRightCornerSize(cornerSize3).build();
            this.areCornerRadiiRtl = z;
            setShapeAppearanceModel(build);
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (!this.globalLayoutListenerAdded) {
            this.endLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
            this.globalLayoutListenerAdded = true;
        }
        updatePlaceholderMeasurementsBasedOnEditText();
        this.endLayout.updateSuffixTextViewPadding();
        if (!isHintTextSingleLine()) {
            updateCollapsingTextDimens((this.editText.getMeasuredWidth() - this.editText.getCompoundPaddingLeft()) - this.editText.getCompoundPaddingRight());
        }
    }

    private void updateCollapsingTextDimens(int i) {
        this.collapsingTextHelper.updateTextHeights(i);
        Rect rect = this.tmpRect;
        DescendantOffsetUtils.getDescendantRect(this, this.editText, rect);
        this.collapsingTextHelper.setCollapsedBounds(calculateCollapsedTextBounds(rect));
        updateInputLayoutMargins();
        adjustFilledEditTextPaddingForLargeFont();
        updateEditTextHeight(i);
    }

    private void updateEditTextHeight(int i) {
        if (this.editText != null) {
            float expandedTextHeight = this.collapsingTextHelper.getExpandedTextHeight();
            float f = 0.0f;
            if (this.placeholderText != null) {
                TextPaint textPaint = new TextPaint(129);
                textPaint.set(this.placeholderTextView.getPaint());
                textPaint.setTextSize(this.placeholderTextView.getTextSize());
                textPaint.setTypeface(this.placeholderTextView.getTypeface());
                textPaint.setLetterSpacing(this.placeholderTextView.getLetterSpacing());
                StaticLayout build = StaticLayoutBuilderCompat.obtain(this.placeholderText, textPaint, i).setIsRtl(getLayoutDirection() == 1).setIncludePad(true).setLineSpacing(this.placeholderTextView.getLineSpacingExtra(), this.placeholderTextView.getLineSpacingMultiplier()).setStaticLayoutBuilderConfigurer(new TextInputLayout$$ExternalSyntheticLambda2(this)).build();
                if (this.boxBackgroundMode == 1) {
                    f = ((float) this.extraSpaceBetweenPlaceholderAndHint) + this.collapsingTextHelper.getCollapsedTextHeight() + ((float) this.boxCollapsedPaddingTopPx);
                }
                f += (float) build.getHeight();
            }
            float max = Math.max(expandedTextHeight, f);
            if (((float) this.editText.getMeasuredHeight()) < max) {
                this.editText.setMinimumHeight(Math.round(max));
            }
        }
    }

    private boolean updateEditTextHeightBasedOnIcon() {
        int max;
        if (this.editText == null || this.editText.getMeasuredHeight() >= (max = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight()))) {
            return false;
        }
        this.editText.setMinimumHeight(max);
        return true;
    }

    private void updatePlaceholderMeasurementsBasedOnEditText() {
        EditText editText2;
        if (this.placeholderTextView != null && (editText2 = this.editText) != null) {
            this.placeholderTextView.setGravity(editText2.getGravity());
            this.placeholderTextView.setPadding(this.editText.getCompoundPaddingLeft(), this.editText.getCompoundPaddingTop(), this.editText.getCompoundPaddingRight(), this.editText.getCompoundPaddingBottom());
        }
    }

    public Drawable getStartIconDrawable() {
        return this.startLayout.getStartIconDrawable();
    }

    public void refreshStartIconDrawableState() {
        this.startLayout.refreshStartIconDrawableState();
    }

    public int getEndIconMode() {
        return this.endLayout.getEndIconMode();
    }

    public void setEndIconVisible(boolean z) {
        this.endLayout.setEndIconVisible(z);
    }

    public boolean isEndIconVisible() {
        return this.endLayout.isEndIconVisible();
    }

    public void addOnEditTextAttachedListener(OnEditTextAttachedListener onEditTextAttachedListener) {
        this.editTextAttachedListeners.add(onEditTextAttachedListener);
        if (this.editText != null) {
            onEditTextAttachedListener.onEditTextAttached(this);
        }
    }

    public void setTextInputAccessibilityDelegate(AccessibilityDelegate accessibilityDelegate) {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            ViewCompat.setAccessibilityDelegate(editText2, accessibilityDelegate);
        }
    }

    /* access modifiers changed from: package-private */
    public CheckableImageButton getEndIconView() {
        return this.endLayout.getEndIconView();
    }

    private void dispatchOnEditTextAttached() {
        Iterator it = this.editTextAttachedListeners.iterator();
        while (it.hasNext()) {
            ((OnEditTextAttachedListener) it.next()).onEditTextAttached(this);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00d5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean updateDummyDrawables() {
        /*
            r10 = this;
            android.widget.EditText r0 = r10.editText
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            boolean r0 = r10.shouldUpdateStartDummyDrawable()
            r2 = 0
            r3 = 2
            r4 = 3
            r5 = 1
            if (r0 == 0) goto L_0x0049
            com.google.android.material.textfield.StartCompoundLayout r0 = r10.startLayout
            int r0 = r0.getMeasuredWidth()
            android.widget.EditText r6 = r10.editText
            int r6 = r6.getPaddingLeft()
            int r0 = r0 - r6
            android.graphics.drawable.Drawable r6 = r10.startDummyDrawable
            if (r6 == 0) goto L_0x0025
            int r6 = r10.startDummyDrawableWidth
            if (r6 == r0) goto L_0x0031
        L_0x0025:
            android.graphics.drawable.ColorDrawable r6 = new android.graphics.drawable.ColorDrawable
            r6.<init>()
            r10.startDummyDrawable = r6
            r10.startDummyDrawableWidth = r0
            r6.setBounds(r1, r1, r0, r5)
        L_0x0031:
            android.widget.EditText r0 = r10.editText
            android.graphics.drawable.Drawable[] r0 = r0.getCompoundDrawablesRelative()
            r6 = r0[r1]
            android.graphics.drawable.Drawable r7 = r10.startDummyDrawable
            if (r6 == r7) goto L_0x0062
            android.widget.EditText r6 = r10.editText
            r8 = r0[r5]
            r9 = r0[r3]
            r0 = r0[r4]
            r6.setCompoundDrawablesRelative(r7, r8, r9, r0)
            goto L_0x0060
        L_0x0049:
            android.graphics.drawable.Drawable r0 = r10.startDummyDrawable
            if (r0 == 0) goto L_0x0062
            android.widget.EditText r0 = r10.editText
            android.graphics.drawable.Drawable[] r0 = r0.getCompoundDrawablesRelative()
            android.widget.EditText r6 = r10.editText
            r7 = r0[r5]
            r8 = r0[r3]
            r0 = r0[r4]
            r6.setCompoundDrawablesRelative(r2, r7, r8, r0)
            r10.startDummyDrawable = r2
        L_0x0060:
            r0 = r5
            goto L_0x0063
        L_0x0062:
            r0 = r1
        L_0x0063:
            boolean r6 = r10.shouldUpdateEndDummyDrawable()
            if (r6 == 0) goto L_0x00d5
            com.google.android.material.textfield.EndCompoundLayout r2 = r10.endLayout
            android.widget.TextView r2 = r2.getSuffixTextView()
            int r2 = r2.getMeasuredWidth()
            android.widget.EditText r6 = r10.editText
            int r6 = r6.getPaddingRight()
            int r2 = r2 - r6
            com.google.android.material.textfield.EndCompoundLayout r6 = r10.endLayout
            com.google.android.material.internal.CheckableImageButton r6 = r6.getCurrentEndIconView()
            if (r6 == 0) goto L_0x0092
            int r7 = r6.getMeasuredWidth()
            int r2 = r2 + r7
            android.view.ViewGroup$LayoutParams r6 = r6.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r6 = (android.view.ViewGroup.MarginLayoutParams) r6
            int r6 = r6.getMarginStart()
            int r2 = r2 + r6
        L_0x0092:
            android.widget.EditText r6 = r10.editText
            android.graphics.drawable.Drawable[] r6 = r6.getCompoundDrawablesRelative()
            android.graphics.drawable.Drawable r7 = r10.endDummyDrawable
            if (r7 == 0) goto L_0x00b3
            int r8 = r10.endDummyDrawableWidth
            if (r8 == r2) goto L_0x00b3
            r10.endDummyDrawableWidth = r2
            r7.setBounds(r1, r1, r2, r5)
            android.widget.EditText r0 = r10.editText
            r1 = r6[r1]
            r2 = r6[r5]
            android.graphics.drawable.Drawable r10 = r10.endDummyDrawable
            r3 = r6[r4]
            r0.setCompoundDrawablesRelative(r1, r2, r10, r3)
            return r5
        L_0x00b3:
            if (r7 != 0) goto L_0x00c1
            android.graphics.drawable.ColorDrawable r7 = new android.graphics.drawable.ColorDrawable
            r7.<init>()
            r10.endDummyDrawable = r7
            r10.endDummyDrawableWidth = r2
            r7.setBounds(r1, r1, r2, r5)
        L_0x00c1:
            r2 = r6[r3]
            android.graphics.drawable.Drawable r3 = r10.endDummyDrawable
            if (r2 == r3) goto L_0x00f7
            r10.originalEditTextEndDrawable = r2
            android.widget.EditText r10 = r10.editText
            r0 = r6[r1]
            r1 = r6[r5]
            r2 = r6[r4]
            r10.setCompoundDrawablesRelative(r0, r1, r3, r2)
            return r5
        L_0x00d5:
            android.graphics.drawable.Drawable r6 = r10.endDummyDrawable
            if (r6 == 0) goto L_0x00f7
            android.widget.EditText r6 = r10.editText
            android.graphics.drawable.Drawable[] r6 = r6.getCompoundDrawablesRelative()
            r3 = r6[r3]
            android.graphics.drawable.Drawable r7 = r10.endDummyDrawable
            if (r3 != r7) goto L_0x00f3
            android.widget.EditText r0 = r10.editText
            r1 = r6[r1]
            r3 = r6[r5]
            android.graphics.drawable.Drawable r7 = r10.originalEditTextEndDrawable
            r4 = r6[r4]
            r0.setCompoundDrawablesRelative(r1, r3, r7, r4)
            goto L_0x00f4
        L_0x00f3:
            r5 = r0
        L_0x00f4:
            r10.endDummyDrawable = r2
            return r5
        L_0x00f7:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.updateDummyDrawables():boolean");
    }

    private boolean shouldUpdateStartDummyDrawable() {
        return (getStartIconDrawable() != null || (getPrefixText() != null && getPrefixTextView().getVisibility() == 0)) && this.startLayout.getMeasuredWidth() > 0;
    }

    private boolean shouldUpdateEndDummyDrawable() {
        return (this.endLayout.isErrorIconVisible() || ((this.endLayout.hasEndIcon() && isEndIconVisible()) || this.endLayout.getSuffixText() != null)) && this.endLayout.getMeasuredWidth() > 0;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        EditText editText2 = this.editText;
        if (editText2 != null) {
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(this, editText2, rect);
            updateBoxUnderlineBounds(rect);
            if (this.hintEnabled) {
                this.collapsingTextHelper.setExpandedTextSize(this.editText.getTextSize());
                int gravity = this.editText.getGravity();
                this.collapsingTextHelper.setCollapsedTextGravity((gravity & -113) | 48);
                this.collapsingTextHelper.setExpandedTextGravity(gravity);
                this.collapsingTextHelper.setCollapsedBounds(calculateCollapsedTextBounds(rect));
                this.collapsingTextHelper.setExpandedBounds(calculateExpandedTextBounds(rect));
                this.collapsingTextHelper.recalculate();
                if (cutoutEnabled() && !this.hintExpanded) {
                    openCutout();
                }
            }
        }
    }

    private void updateBoxUnderlineBounds(Rect rect) {
        MaterialShapeDrawable materialShapeDrawable = this.boxUnderlineDefault;
        if (materialShapeDrawable != null) {
            int i = rect.bottom;
            materialShapeDrawable.setBounds(rect.left, i - this.boxStrokeWidthDefaultPx, rect.right, i);
        }
        MaterialShapeDrawable materialShapeDrawable2 = this.boxUnderlineFocused;
        if (materialShapeDrawable2 != null) {
            int i2 = rect.bottom;
            materialShapeDrawable2.setBounds(rect.left, i2 - this.boxStrokeWidthFocusedPx, rect.right, i2);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawHint(canvas);
        drawBoxUnderline(canvas);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.collapsingTextHelper.maybeUpdateFontWeightAdjustment(configuration);
    }

    private void drawHint(Canvas canvas) {
        if (this.hintEnabled) {
            this.collapsingTextHelper.draw(canvas);
        }
    }

    private void drawBoxUnderline(Canvas canvas) {
        MaterialShapeDrawable materialShapeDrawable;
        if (this.boxUnderlineFocused != null && (materialShapeDrawable = this.boxUnderlineDefault) != null) {
            materialShapeDrawable.draw(canvas);
            if (this.editText.isFocused()) {
                Rect bounds = this.boxUnderlineFocused.getBounds();
                Rect bounds2 = this.boxUnderlineDefault.getBounds();
                float expansionFraction = this.collapsingTextHelper.getExpansionFraction();
                int centerX = bounds2.centerX();
                bounds.left = AnimationUtils.lerp(centerX, bounds2.left, expansionFraction);
                bounds.right = AnimationUtils.lerp(centerX, bounds2.right, expansionFraction);
                this.boxUnderlineFocused.draw(canvas);
            }
        }
    }

    private void collapseHint(boolean z) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (!z || !this.hintAnimationEnabled) {
            this.collapsingTextHelper.setExpansionFraction(1.0f);
        } else {
            animateToExpansionFraction(1.0f);
        }
        this.hintExpanded = false;
        if (cutoutEnabled()) {
            openCutout();
        }
        updatePlaceholderText();
        this.startLayout.onHintStateChanged(false);
        this.endLayout.onHintStateChanged(false);
    }

    private boolean cutoutEnabled() {
        return this.hintEnabled && !TextUtils.isEmpty(this.hint) && (this.boxBackground instanceof CutoutDrawable);
    }

    private void openCutout() {
        if (cutoutEnabled()) {
            RectF rectF = this.tmpRectF;
            this.collapsingTextHelper.getCollapsedTextBottomTextBounds(rectF, this.editText.getWidth(), this.editText.getGravity());
            if (rectF.width() > 0.0f && rectF.height() > 0.0f) {
                applyCutoutPadding(rectF);
                rectF.offset((float) (-getPaddingLeft()), (((float) (-getPaddingTop())) - (rectF.height() / 2.0f)) + ((float) this.boxStrokeWidthPx));
                rectF.top = 0.0f;
                ((CutoutDrawable) this.boxBackground).setCutout(rectF);
            }
        }
    }

    private void recalculateCutout() {
        if (cutoutEnabled() && !this.hintExpanded) {
            closeCutout();
            openCutout();
        }
    }

    private void closeCutout() {
        if (cutoutEnabled()) {
            ((CutoutDrawable) this.boxBackground).removeCutout();
        }
    }

    private void applyCutoutPadding(RectF rectF) {
        float f = rectF.left;
        int i = this.boxLabelCutoutPaddingPx;
        rectF.left = f - ((float) i);
        rectF.right += (float) i;
    }

    /* access modifiers changed from: package-private */
    public boolean cutoutIsOpen() {
        return cutoutEnabled() && ((CutoutDrawable) this.boxBackground).hasCutout();
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (!this.inDrawableStateChanged) {
            boolean z = true;
            this.inDrawableStateChanged = true;
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            boolean state = collapsingTextHelper2 != null ? collapsingTextHelper2.setState(drawableState) : false;
            if (this.editText != null) {
                if (!isLaidOut() || !isEnabled()) {
                    z = false;
                }
                updateLabelState(z);
            }
            updateEditTextBackground();
            updateTextInputBoxState();
            if (state) {
                invalidate();
            }
            this.inDrawableStateChanged = false;
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0012, code lost:
        r0 = r5.editText;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void updateTextInputBoxState() {
        /*
            r5 = this;
            com.google.android.material.shape.MaterialShapeDrawable r0 = r5.boxBackground
            if (r0 == 0) goto L_0x00ca
            int r0 = r5.boxBackgroundMode
            if (r0 != 0) goto L_0x000a
            goto L_0x00ca
        L_0x000a:
            boolean r0 = r5.isFocused()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x001f
            android.widget.EditText r0 = r5.editText
            if (r0 == 0) goto L_0x001d
            boolean r0 = r0.hasFocus()
            if (r0 == 0) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r0 = r1
            goto L_0x0020
        L_0x001f:
            r0 = r2
        L_0x0020:
            boolean r3 = r5.isHovered()
            if (r3 != 0) goto L_0x0030
            android.widget.EditText r3 = r5.editText
            if (r3 == 0) goto L_0x0031
            boolean r3 = r3.isHovered()
            if (r3 == 0) goto L_0x0031
        L_0x0030:
            r1 = r2
        L_0x0031:
            boolean r3 = r5.isEnabled()
            if (r3 != 0) goto L_0x003c
            int r3 = r5.disabledColor
            r5.boxStrokeColor = r3
            goto L_0x007a
        L_0x003c:
            boolean r3 = r5.shouldShowError()
            if (r3 == 0) goto L_0x0051
            android.content.res.ColorStateList r3 = r5.strokeErrorColor
            if (r3 == 0) goto L_0x004a
            r5.updateStrokeErrorColor(r0, r1)
            goto L_0x007a
        L_0x004a:
            int r3 = r5.getErrorCurrentTextColors()
            r5.boxStrokeColor = r3
            goto L_0x007a
        L_0x0051:
            boolean r3 = r5.counterOverflowed
            if (r3 == 0) goto L_0x0068
            android.widget.TextView r3 = r5.counterView
            if (r3 == 0) goto L_0x0068
            android.content.res.ColorStateList r4 = r5.strokeErrorColor
            if (r4 == 0) goto L_0x0061
            r5.updateStrokeErrorColor(r0, r1)
            goto L_0x007a
        L_0x0061:
            int r3 = r3.getCurrentTextColor()
            r5.boxStrokeColor = r3
            goto L_0x007a
        L_0x0068:
            if (r0 == 0) goto L_0x006f
            int r3 = r5.focusedStrokeColor
            r5.boxStrokeColor = r3
            goto L_0x007a
        L_0x006f:
            if (r1 == 0) goto L_0x0076
            int r3 = r5.hoveredStrokeColor
            r5.boxStrokeColor = r3
            goto L_0x007a
        L_0x0076:
            int r3 = r5.defaultStrokeColor
            r5.boxStrokeColor = r3
        L_0x007a:
            r5.updateCursorColor()
            com.google.android.material.textfield.EndCompoundLayout r3 = r5.endLayout
            r3.onTextInputBoxStateUpdated()
            r5.refreshStartIconDrawableState()
            int r3 = r5.boxBackgroundMode
            r4 = 2
            if (r3 != r4) goto L_0x00a4
            int r3 = r5.boxStrokeWidthPx
            if (r0 == 0) goto L_0x0099
            boolean r4 = r5.isEnabled()
            if (r4 == 0) goto L_0x0099
            int r4 = r5.boxStrokeWidthFocusedPx
            r5.boxStrokeWidthPx = r4
            goto L_0x009d
        L_0x0099:
            int r4 = r5.boxStrokeWidthDefaultPx
            r5.boxStrokeWidthPx = r4
        L_0x009d:
            int r4 = r5.boxStrokeWidthPx
            if (r4 == r3) goto L_0x00a4
            r5.recalculateCutout()
        L_0x00a4:
            int r3 = r5.boxBackgroundMode
            if (r3 != r2) goto L_0x00c7
            boolean r2 = r5.isEnabled()
            if (r2 != 0) goto L_0x00b3
            int r0 = r5.disabledFilledBackgroundColor
            r5.boxBackgroundColor = r0
            goto L_0x00c7
        L_0x00b3:
            if (r1 == 0) goto L_0x00bc
            if (r0 != 0) goto L_0x00bc
            int r0 = r5.hoveredFilledBackgroundColor
            r5.boxBackgroundColor = r0
            goto L_0x00c7
        L_0x00bc:
            if (r0 == 0) goto L_0x00c3
            int r0 = r5.focusedFilledBackgroundColor
            r5.boxBackgroundColor = r0
            goto L_0x00c7
        L_0x00c3:
            int r0 = r5.defaultFilledBackgroundColor
            r5.boxBackgroundColor = r0
        L_0x00c7:
            r5.applyBoxAttributes()
        L_0x00ca:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.updateTextInputBoxState():void");
    }

    private boolean isOnError() {
        if (!shouldShowError()) {
            return this.counterView != null && this.counterOverflowed;
        }
        return true;
    }

    private void updateStrokeErrorColor(boolean z, boolean z2) {
        int defaultColor = this.strokeErrorColor.getDefaultColor();
        int colorForState = this.strokeErrorColor.getColorForState(new int[]{16843623, 16842910}, defaultColor);
        int colorForState2 = this.strokeErrorColor.getColorForState(new int[]{16843518, 16842910}, defaultColor);
        if (z) {
            this.boxStrokeColor = colorForState2;
        } else if (z2) {
            this.boxStrokeColor = colorForState;
        } else {
            this.boxStrokeColor = defaultColor;
        }
    }

    private void updateCursorColor() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = this.cursorColor;
        if (colorStateList2 == null) {
            colorStateList2 = MaterialColors.getColorStateListOrNull(getContext(), androidx.appcompat.R$attr.colorControlActivated);
        }
        EditText editText2 = this.editText;
        if (editText2 != null && editText2.getTextCursorDrawable() != null) {
            Drawable mutate = DrawableCompat.wrap(this.editText.getTextCursorDrawable()).mutate();
            if (isOnError() && (colorStateList = this.cursorErrorColor) != null) {
                colorStateList2 = colorStateList;
            }
            mutate.setTintList(colorStateList2);
        }
    }

    private void expandHint(boolean z) {
        ValueAnimator valueAnimator = this.animator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.animator.cancel();
        }
        if (!z || !this.hintAnimationEnabled) {
            this.collapsingTextHelper.setExpansionFraction(0.0f);
        } else {
            animateToExpansionFraction(0.0f);
        }
        if (cutoutEnabled() && ((CutoutDrawable) this.boxBackground).hasCutout()) {
            closeCutout();
        }
        this.hintExpanded = true;
        hidePlaceholderText();
        this.startLayout.onHintStateChanged(true);
        this.endLayout.onHintStateChanged(true);
    }

    /* access modifiers changed from: package-private */
    public void animateToExpansionFraction(float f) {
        if (this.collapsingTextHelper.getExpansionFraction() != f) {
            if (this.animator == null) {
                ValueAnimator valueAnimator = new ValueAnimator();
                this.animator = valueAnimator;
                valueAnimator.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), R$attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
                this.animator.setDuration((long) MotionUtils.resolveThemeDuration(getContext(), R$attr.motionDurationMedium4, 167));
                this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        TextInputLayout.this.collapsingTextHelper.setExpansionFraction(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
            }
            this.animator.setFloatValues(new float[]{this.collapsingTextHelper.getExpansionFraction(), f});
            this.animator.start();
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean isHintExpanded() {
        return this.hintExpanded;
    }

    /* access modifiers changed from: package-private */
    public final boolean isHelperTextDisplayed() {
        return this.indicatorViewController.helperTextIsDisplayed();
    }

    /* access modifiers changed from: package-private */
    public final int getHintCurrentCollapsedTextColor() {
        return this.collapsingTextHelper.getCurrentCollapsedTextColor();
    }

    /* access modifiers changed from: package-private */
    public final float getHintCollapsedTextHeight() {
        return this.collapsingTextHelper.getCollapsedTextHeight();
    }

    public class AccessibilityDelegate extends AccessibilityDelegateCompat {
        private final TextInputLayout layout;

        public AccessibilityDelegate(TextInputLayout textInputLayout) {
            this.layout = textInputLayout;
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            EditText editText = this.layout.getEditText();
            Editable text = editText != null ? editText.getText() : null;
            CharSequence hint = this.layout.getHint();
            CharSequence error = this.layout.getError();
            CharSequence placeholderText = this.layout.getPlaceholderText();
            int counterMaxLength = this.layout.getCounterMaxLength();
            CharSequence counterOverflowDescription = this.layout.getCounterOverflowDescription();
            boolean isEmpty = TextUtils.isEmpty(text);
            boolean isEmpty2 = TextUtils.isEmpty(hint);
            boolean isHintExpanded = this.layout.isHintExpanded();
            boolean isEmpty3 = TextUtils.isEmpty(error);
            boolean z = !isEmpty3 || !TextUtils.isEmpty(counterOverflowDescription);
            String charSequence = !isEmpty2 ? hint.toString() : "";
            this.layout.startLayout.setupAccessibilityNodeInfo(accessibilityNodeInfoCompat);
            if (!isEmpty) {
                accessibilityNodeInfoCompat.setText(text);
            } else if (!TextUtils.isEmpty(charSequence)) {
                accessibilityNodeInfoCompat.setText(charSequence);
                if (!isHintExpanded && placeholderText != null) {
                    accessibilityNodeInfoCompat.setText(charSequence + ", " + placeholderText);
                }
            } else if (placeholderText != null) {
                accessibilityNodeInfoCompat.setText(placeholderText);
            }
            if (!TextUtils.isEmpty(charSequence)) {
                accessibilityNodeInfoCompat.setHintText(charSequence);
                accessibilityNodeInfoCompat.setShowingHintText(isEmpty);
            }
            if (text == null || text.length() != counterMaxLength) {
                counterMaxLength = -1;
            }
            accessibilityNodeInfoCompat.setMaxTextLength(counterMaxLength);
            if (z) {
                if (isEmpty3) {
                    error = counterOverflowDescription;
                }
                accessibilityNodeInfoCompat.setError(error);
            }
            View helperTextView = this.layout.indicatorViewController.getHelperTextView();
            if (helperTextView != null) {
                accessibilityNodeInfoCompat.setLabelFor(helperTextView);
            }
            this.layout.endLayout.getEndIconDelegate().onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            this.layout.endLayout.getEndIconDelegate().onPopulateAccessibilityEvent(view, accessibilityEvent);
        }
    }
}
