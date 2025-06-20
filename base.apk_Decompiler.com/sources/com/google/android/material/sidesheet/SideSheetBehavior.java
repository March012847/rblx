package com.google.android.material.sidesheet;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.view.AbsSavedState;
import androidx.customview.widget.ViewDragHelper;
import com.google.android.material.R$string;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.motion.MaterialSideContainerBackHelper;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class SideSheetBehavior<V extends View> extends CoordinatorLayout.Behavior {
    private static final int DEFAULT_ACCESSIBILITY_PANE_TITLE = R$string.side_sheet_accessibility_pane_title;
    private static final int DEF_STYLE_RES = R$style.Widget_Material3_SideSheet;
    private ColorStateList backgroundTint;
    private final Set callbacks = new LinkedHashSet();
    /* access modifiers changed from: private */
    public int childWidth;
    private int coplanarSiblingViewId = -1;
    private WeakReference coplanarSiblingViewRef;
    private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback() {
        public boolean tryCaptureView(View view, int i) {
            return (SideSheetBehavior.this.state == 1 || SideSheetBehavior.this.viewRef == null || SideSheetBehavior.this.viewRef.get() != view) ? false : true;
        }

        public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
            ViewGroup.MarginLayoutParams marginLayoutParams;
            View coplanarSiblingView = SideSheetBehavior.this.getCoplanarSiblingView();
            if (!(coplanarSiblingView == null || (marginLayoutParams = (ViewGroup.MarginLayoutParams) coplanarSiblingView.getLayoutParams()) == null)) {
                SideSheetBehavior.this.sheetDelegate.updateCoplanarSiblingLayoutParams(marginLayoutParams, view.getLeft(), view.getRight());
                coplanarSiblingView.setLayoutParams(marginLayoutParams);
            }
            SideSheetBehavior.this.dispatchOnSlide(view, i);
        }

        public void onViewDragStateChanged(int i) {
            if (i == 1 && SideSheetBehavior.this.draggable) {
                SideSheetBehavior.this.setStateInternal(1);
            }
        }

        public void onViewReleased(View view, float f, float f2) {
            int access$500 = SideSheetBehavior.this.calculateTargetStateOnViewReleased(view, f, f2);
            SideSheetBehavior sideSheetBehavior = SideSheetBehavior.this;
            sideSheetBehavior.startSettling(view, access$500, sideSheetBehavior.shouldSkipSmoothAnimation());
        }

        public int clampViewPositionVertical(View view, int i, int i2) {
            return view.getTop();
        }

        public int clampViewPositionHorizontal(View view, int i, int i2) {
            return MathUtils.clamp(i, SideSheetBehavior.this.sheetDelegate.getMinViewPositionHorizontal(), SideSheetBehavior.this.sheetDelegate.getMaxViewPositionHorizontal());
        }

        public int getViewHorizontalDragRange(View view) {
            return SideSheetBehavior.this.childWidth + SideSheetBehavior.this.getInnerMargin();
        }
    };
    /* access modifiers changed from: private */
    public boolean draggable = true;
    private float elevation;
    private float hideFriction = 0.1f;
    private boolean ignoreEvents;
    private int initialX;
    private int innerMargin;
    private int lastStableState = 5;
    private MaterialShapeDrawable materialShapeDrawable;
    private float maximumVelocity;
    private int parentInnerEdge;
    private int parentWidth;
    private ShapeAppearanceModel shapeAppearanceModel;
    /* access modifiers changed from: private */
    public SheetDelegate sheetDelegate;
    private MaterialSideContainerBackHelper sideContainerBackHelper;
    /* access modifiers changed from: private */
    public int state = 5;
    private final StateSettlingTracker stateSettlingTracker = new StateSettlingTracker();
    private VelocityTracker velocityTracker;
    /* access modifiers changed from: private */
    public ViewDragHelper viewDragHelper;
    /* access modifiers changed from: private */
    public WeakReference viewRef;

    /* access modifiers changed from: package-private */
    public float getHideThreshold() {
        return 0.5f;
    }

    /* access modifiers changed from: package-private */
    public int getSignificantVelocityThreshold() {
        return 500;
    }

    public boolean shouldSkipSmoothAnimation() {
        return true;
    }

    public SideSheetBehavior() {
    }

    public SideSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SideSheetBehavior_Layout);
        int i = R$styleable.SideSheetBehavior_Layout_backgroundTint;
        if (obtainStyledAttributes.hasValue(i)) {
            this.backgroundTint = MaterialResources.getColorStateList(context, obtainStyledAttributes, i);
        }
        if (obtainStyledAttributes.hasValue(R$styleable.SideSheetBehavior_Layout_shapeAppearance)) {
            this.shapeAppearanceModel = ShapeAppearanceModel.builder(context, attributeSet, 0, DEF_STYLE_RES).build();
        }
        int i2 = R$styleable.SideSheetBehavior_Layout_coplanarSiblingViewId;
        if (obtainStyledAttributes.hasValue(i2)) {
            setCoplanarSiblingViewId(obtainStyledAttributes.getResourceId(i2, -1));
        }
        createMaterialShapeDrawableIfNeeded(context);
        this.elevation = obtainStyledAttributes.getDimension(R$styleable.SideSheetBehavior_Layout_android_elevation, -1.0f);
        setDraggable(obtainStyledAttributes.getBoolean(R$styleable.SideSheetBehavior_Layout_behavior_draggable, true));
        obtainStyledAttributes.recycle();
        this.maximumVelocity = (float) ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    private void setSheetEdge(View view, int i) {
        setSheetEdge(Gravity.getAbsoluteGravity(((CoordinatorLayout.LayoutParams) view.getLayoutParams()).gravity, i) == 3 ? 1 : 0);
    }

    private void setSheetEdge(int i) {
        SheetDelegate sheetDelegate2 = this.sheetDelegate;
        if (sheetDelegate2 != null && sheetDelegate2.getSheetEdge() == i) {
            return;
        }
        if (i == 0) {
            this.sheetDelegate = new RightSheetDelegate(this);
            if (this.shapeAppearanceModel != null && !hasRightMargin()) {
                ShapeAppearanceModel.Builder builder = this.shapeAppearanceModel.toBuilder();
                builder.setTopRightCornerSize(0.0f).setBottomRightCornerSize(0.0f);
                updateMaterialShapeDrawable(builder.build());
            }
        } else if (i == 1) {
            this.sheetDelegate = new LeftSheetDelegate(this);
            if (this.shapeAppearanceModel != null && !hasLeftMargin()) {
                ShapeAppearanceModel.Builder builder2 = this.shapeAppearanceModel.toBuilder();
                builder2.setTopLeftCornerSize(0.0f).setBottomLeftCornerSize(0.0f);
                updateMaterialShapeDrawable(builder2.build());
            }
        } else {
            throw new IllegalArgumentException("Invalid sheet edge position value: " + i + ". Must be " + 0 + " or " + 1 + ".");
        }
    }

    private boolean hasRightMargin() {
        CoordinatorLayout.LayoutParams viewLayoutParams = getViewLayoutParams();
        return viewLayoutParams != null && viewLayoutParams.rightMargin > 0;
    }

    private boolean hasLeftMargin() {
        CoordinatorLayout.LayoutParams viewLayoutParams = getViewLayoutParams();
        return viewLayoutParams != null && viewLayoutParams.leftMargin > 0;
    }

    private CoordinatorLayout.LayoutParams getViewLayoutParams() {
        View view;
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || (view = (View) weakReference.get()) == null || !(view.getLayoutParams() instanceof CoordinatorLayout.LayoutParams)) {
            return null;
        }
        return (CoordinatorLayout.LayoutParams) view.getLayoutParams();
    }

    private void updateMaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel2) {
        MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
        if (materialShapeDrawable2 != null) {
            materialShapeDrawable2.setShapeAppearanceModel(shapeAppearanceModel2);
        }
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, View view) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, view), this);
    }

    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, View view, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        if (savedState.getSuperState() != null) {
            super.onRestoreInstanceState(coordinatorLayout, view, savedState.getSuperState());
        }
        int i = savedState.state;
        if (i == 1 || i == 2) {
            i = 5;
        }
        this.state = i;
        this.lastStableState = i;
    }

    public void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
        super.onAttachedToLayoutParams(layoutParams);
        this.viewRef = null;
        this.viewDragHelper = null;
        this.sideContainerBackHelper = null;
    }

    public void onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams();
        this.viewRef = null;
        this.viewDragHelper = null;
        this.sideContainerBackHelper = null;
    }

    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, coordinatorLayout.getPaddingLeft() + coordinatorLayout.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, -1, marginLayoutParams.width), getChildMeasureSpec(i3, coordinatorLayout.getPaddingTop() + coordinatorLayout.getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, -1, marginLayoutParams.height));
        return true;
    }

    private int getChildMeasureSpec(int i, int i2, int i3, int i4) {
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i, i2, i4);
        if (i3 == -1) {
            return childMeasureSpec;
        }
        int mode = View.MeasureSpec.getMode(childMeasureSpec);
        int size = View.MeasureSpec.getSize(childMeasureSpec);
        if (mode == 1073741824) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i3), 1073741824);
        }
        if (size != 0) {
            i3 = Math.min(size, i3);
        }
        return View.MeasureSpec.makeMeasureSpec(i3, Integer.MIN_VALUE);
    }

    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
        if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
        }
        if (this.viewRef == null) {
            this.viewRef = new WeakReference(view);
            this.sideContainerBackHelper = new MaterialSideContainerBackHelper(view);
            MaterialShapeDrawable materialShapeDrawable2 = this.materialShapeDrawable;
            if (materialShapeDrawable2 != null) {
                view.setBackground(materialShapeDrawable2);
                MaterialShapeDrawable materialShapeDrawable3 = this.materialShapeDrawable;
                float f = this.elevation;
                if (f == -1.0f) {
                    f = view.getElevation();
                }
                materialShapeDrawable3.setElevation(f);
            } else {
                ColorStateList colorStateList = this.backgroundTint;
                if (colorStateList != null) {
                    ViewCompat.setBackgroundTintList(view, colorStateList);
                }
            }
            updateSheetVisibility(view);
            updateAccessibilityActions();
            if (view.getImportantForAccessibility() == 0) {
                view.setImportantForAccessibility(1);
            }
            ensureAccessibilityPaneTitleIsSet(view);
        }
        setSheetEdge(view, i);
        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(coordinatorLayout, this.dragCallback);
        }
        int outerEdge = this.sheetDelegate.getOuterEdge(view);
        coordinatorLayout.onLayoutChild(view, i);
        this.parentWidth = coordinatorLayout.getWidth();
        this.parentInnerEdge = this.sheetDelegate.getParentInnerEdge(coordinatorLayout);
        this.childWidth = view.getWidth();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        this.innerMargin = marginLayoutParams != null ? this.sheetDelegate.calculateInnerMargin(marginLayoutParams) : 0;
        ViewCompat.offsetLeftAndRight(view, calculateCurrentOffset(outerEdge, view));
        maybeAssignCoplanarSiblingViewBasedId(coordinatorLayout);
        for (Object m : this.callbacks) {
            ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(m);
        }
        return true;
    }

    private void updateSheetVisibility(View view) {
        int i = this.state == 5 ? 4 : 0;
        if (view.getVisibility() != i) {
            view.setVisibility(i);
        }
    }

    private void ensureAccessibilityPaneTitleIsSet(View view) {
        if (ViewCompat.getAccessibilityPaneTitle(view) == null) {
            ViewCompat.setAccessibilityPaneTitle(view, view.getResources().getString(DEFAULT_ACCESSIBILITY_PANE_TITLE));
        }
    }

    private void maybeAssignCoplanarSiblingViewBasedId(CoordinatorLayout coordinatorLayout) {
        int i;
        View findViewById;
        if (this.coplanarSiblingViewRef == null && (i = this.coplanarSiblingViewId) != -1 && (findViewById = coordinatorLayout.findViewById(i)) != null) {
            this.coplanarSiblingViewRef = new WeakReference(findViewById);
        }
    }

    /* access modifiers changed from: package-private */
    public int getChildWidth() {
        return this.childWidth;
    }

    /* access modifiers changed from: package-private */
    public int getParentWidth() {
        return this.parentWidth;
    }

    /* access modifiers changed from: package-private */
    public int getParentInnerEdge() {
        return this.parentInnerEdge;
    }

    /* access modifiers changed from: package-private */
    public int getInnerMargin() {
        return this.innerMargin;
    }

    private int calculateCurrentOffset(int i, View view) {
        int i2 = this.state;
        if (i2 == 1 || i2 == 2) {
            return i - this.sheetDelegate.getOuterEdge(view);
        }
        if (i2 == 3) {
            return 0;
        }
        if (i2 == 5) {
            return this.sheetDelegate.getHiddenOffset();
        }
        throw new IllegalStateException("Unexpected value: " + this.state);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003d, code lost:
        r2 = r2.viewDragHelper;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onInterceptTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout r3, android.view.View r4, android.view.MotionEvent r5) {
        /*
            r2 = this;
            boolean r3 = r2.shouldInterceptTouchEvent(r4)
            r4 = 1
            r0 = 0
            if (r3 != 0) goto L_0x000b
            r2.ignoreEvents = r4
            return r0
        L_0x000b:
            int r3 = r5.getActionMasked()
            if (r3 != 0) goto L_0x0014
            r2.resetVelocity()
        L_0x0014:
            android.view.VelocityTracker r1 = r2.velocityTracker
            if (r1 != 0) goto L_0x001e
            android.view.VelocityTracker r1 = android.view.VelocityTracker.obtain()
            r2.velocityTracker = r1
        L_0x001e:
            android.view.VelocityTracker r1 = r2.velocityTracker
            r1.addMovement(r5)
            if (r3 == 0) goto L_0x0032
            if (r3 == r4) goto L_0x002b
            r1 = 3
            if (r3 == r1) goto L_0x002b
            goto L_0x0039
        L_0x002b:
            boolean r3 = r2.ignoreEvents
            if (r3 == 0) goto L_0x0039
            r2.ignoreEvents = r0
            return r0
        L_0x0032:
            float r3 = r5.getX()
            int r3 = (int) r3
            r2.initialX = r3
        L_0x0039:
            boolean r3 = r2.ignoreEvents
            if (r3 != 0) goto L_0x0048
            androidx.customview.widget.ViewDragHelper r2 = r2.viewDragHelper
            if (r2 == 0) goto L_0x0048
            boolean r2 = r2.shouldInterceptTouchEvent(r5)
            if (r2 == 0) goto L_0x0048
            return r4
        L_0x0048:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.sidesheet.SideSheetBehavior.onInterceptTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    private boolean shouldInterceptTouchEvent(View view) {
        return (view.isShown() || ViewCompat.getAccessibilityPaneTitle(view) != null) && this.draggable;
    }

    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        if (!view.isShown()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.state == 1 && actionMasked == 0) {
            return true;
        }
        if (shouldHandleDraggingWithHelper()) {
            this.viewDragHelper.processTouchEvent(motionEvent);
        }
        if (actionMasked == 0) {
            resetVelocity();
        }
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.velocityTracker.addMovement(motionEvent);
        if (shouldHandleDraggingWithHelper() && actionMasked == 2 && !this.ignoreEvents && isDraggedFarEnough(motionEvent)) {
            this.viewDragHelper.captureChildView(view, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.ignoreEvents;
    }

    private boolean isDraggedFarEnough(MotionEvent motionEvent) {
        if (shouldHandleDraggingWithHelper() && calculateDragDistance((float) this.initialX, motionEvent.getX()) > ((float) this.viewDragHelper.getTouchSlop())) {
            return true;
        }
        return false;
    }

    private float calculateDragDistance(float f, float f2) {
        return Math.abs(f - f2);
    }

    public int getExpandedOffset() {
        return this.sheetDelegate.getExpandedOffset();
    }

    public void setDraggable(boolean z) {
        this.draggable = z;
    }

    public float getHideFriction() {
        return this.hideFriction;
    }

    public void setState(int i) {
        if (i == 1 || i == 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("STATE_");
            sb.append(i == 1 ? "DRAGGING" : "SETTLING");
            sb.append(" should not be set externally.");
            throw new IllegalArgumentException(sb.toString());
        }
        WeakReference weakReference = this.viewRef;
        if (weakReference == null || weakReference.get() == null) {
            setStateInternal(i);
        } else {
            runAfterLayout((View) this.viewRef.get(), new SideSheetBehavior$$ExternalSyntheticLambda1(this, i));
        }
    }

    public static /* synthetic */ void $r8$lambda$o7Vv2e9fpJtQ0OuFkAQda6gk1mg(SideSheetBehavior sideSheetBehavior, int i) {
        View view = (View) sideSheetBehavior.viewRef.get();
        if (view != null) {
            sideSheetBehavior.startSettling(view, i, false);
        }
    }

    private void runAfterLayout(View view, Runnable runnable) {
        if (isLayingOut(view)) {
            view.post(runnable);
        } else {
            runnable.run();
        }
    }

    private boolean isLayingOut(View view) {
        ViewParent parent = view.getParent();
        return parent != null && parent.isLayoutRequested() && view.isAttachedToWindow();
    }

    /* access modifiers changed from: package-private */
    public void setStateInternal(int i) {
        View view;
        if (this.state != i) {
            this.state = i;
            if (i == 3 || i == 5) {
                this.lastStableState = i;
            }
            WeakReference weakReference = this.viewRef;
            if (weakReference != null && (view = (View) weakReference.get()) != null) {
                updateSheetVisibility(view);
                Iterator it = this.callbacks.iterator();
                if (!it.hasNext()) {
                    updateAccessibilityActions();
                } else {
                    ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                    throw null;
                }
            }
        }
    }

    private void resetVelocity() {
        VelocityTracker velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
            this.velocityTracker = null;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean shouldHide(View view, float f) {
        return this.sheetDelegate.shouldHide(view, f);
    }

    private boolean shouldHandleDraggingWithHelper() {
        if (this.viewDragHelper != null) {
            return this.draggable || this.state == 1;
        }
        return false;
    }

    private void createMaterialShapeDrawableIfNeeded(Context context) {
        if (this.shapeAppearanceModel != null) {
            MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.materialShapeDrawable = materialShapeDrawable2;
            materialShapeDrawable2.initializeElevationOverlay(context);
            ColorStateList colorStateList = this.backgroundTint;
            if (colorStateList != null) {
                this.materialShapeDrawable.setFillColor(colorStateList);
                return;
            }
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(16842801, typedValue, true);
            this.materialShapeDrawable.setTint(typedValue.data);
        }
    }

    /* access modifiers changed from: private */
    public void startSettling(View view, int i, boolean z) {
        if (isSettling(view, i, z)) {
            setStateInternal(2);
            this.stateSettlingTracker.continueSettlingToState(i);
            return;
        }
        setStateInternal(i);
    }

    private boolean isSettling(View view, int i, boolean z) {
        int outerEdgeOffsetForState = getOuterEdgeOffsetForState(i);
        ViewDragHelper viewDragHelper2 = getViewDragHelper();
        if (viewDragHelper2 != null) {
            return z ? viewDragHelper2.settleCapturedViewAt(outerEdgeOffsetForState, view.getTop()) : viewDragHelper2.smoothSlideViewTo(view, outerEdgeOffsetForState, view.getTop());
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public int getOuterEdgeOffsetForState(int i) {
        if (i == 3) {
            return getExpandedOffset();
        }
        if (i == 5) {
            return this.sheetDelegate.getHiddenOffset();
        }
        throw new IllegalArgumentException("Invalid state to get outer edge offset: " + i);
    }

    /* access modifiers changed from: package-private */
    public ViewDragHelper getViewDragHelper() {
        return this.viewDragHelper;
    }

    /* access modifiers changed from: private */
    public int calculateTargetStateOnViewReleased(View view, float f, float f2) {
        if (isExpandingOutwards(f)) {
            return 3;
        }
        if (shouldHide(view, f)) {
            if (this.sheetDelegate.isSwipeSignificant(f, f2) || this.sheetDelegate.isReleasedCloseToInnerEdge(view)) {
                return 5;
            }
            return 3;
        } else if (f != 0.0f && SheetUtils.isSwipeMostlyHorizontal(f, f2)) {
            return 5;
        } else {
            int left = view.getLeft();
            if (Math.abs(left - getExpandedOffset()) < Math.abs(left - this.sheetDelegate.getHiddenOffset())) {
                return 3;
            }
            return 5;
        }
    }

    private boolean isExpandingOutwards(float f) {
        return this.sheetDelegate.isExpandingOutwards(f);
    }

    /* access modifiers changed from: private */
    public void dispatchOnSlide(View view, int i) {
        if (!this.callbacks.isEmpty()) {
            this.sheetDelegate.calculateSlideOffset(i);
            Iterator it = this.callbacks.iterator();
            if (it.hasNext()) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
        }
    }

    public void setCoplanarSiblingViewId(int i) {
        this.coplanarSiblingViewId = i;
        clearCoplanarSiblingView();
        WeakReference weakReference = this.viewRef;
        if (weakReference != null) {
            View view = (View) weakReference.get();
            if (i != -1 && view.isLaidOut()) {
                view.requestLayout();
            }
        }
    }

    public View getCoplanarSiblingView() {
        WeakReference weakReference = this.coplanarSiblingViewRef;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    private void clearCoplanarSiblingView() {
        WeakReference weakReference = this.coplanarSiblingViewRef;
        if (weakReference != null) {
            weakReference.clear();
        }
        this.coplanarSiblingViewRef = null;
    }

    /* access modifiers changed from: package-private */
    public MaterialSideContainerBackHelper getBackHelper() {
        return this.sideContainerBackHelper;
    }

    class StateSettlingTracker {
        private final Runnable continueSettlingRunnable = new SideSheetBehavior$StateSettlingTracker$$ExternalSyntheticLambda0(this);
        private boolean isContinueSettlingRunnablePosted;
        private int targetState;

        StateSettlingTracker() {
        }

        /* renamed from: $r8$lambda$moQjjbcBo6A-pC5WH96319nIACo  reason: not valid java name */
        public static /* synthetic */ void m126$r8$lambda$moQjjbcBo6ApC5WH96319nIACo(StateSettlingTracker stateSettlingTracker) {
            stateSettlingTracker.isContinueSettlingRunnablePosted = false;
            if (SideSheetBehavior.this.viewDragHelper != null && SideSheetBehavior.this.viewDragHelper.continueSettling(true)) {
                stateSettlingTracker.continueSettlingToState(stateSettlingTracker.targetState);
            } else if (SideSheetBehavior.this.state == 2) {
                SideSheetBehavior.this.setStateInternal(stateSettlingTracker.targetState);
            }
        }

        /* access modifiers changed from: package-private */
        public void continueSettlingToState(int i) {
            if (SideSheetBehavior.this.viewRef != null && SideSheetBehavior.this.viewRef.get() != null) {
                this.targetState = i;
                if (!this.isContinueSettlingRunnablePosted) {
                    ((View) SideSheetBehavior.this.viewRef.get()).postOnAnimation(this.continueSettlingRunnable);
                    this.isContinueSettlingRunnablePosted = true;
                }
            }
        }
    }

    public class SavedState extends AbsSavedState {
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
        final int state;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, SideSheetBehavior sideSheetBehavior) {
            super(parcelable);
            this.state = sideSheetBehavior.state;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.state);
        }
    }

    private void updateAccessibilityActions() {
        View view;
        WeakReference weakReference = this.viewRef;
        if (weakReference != null && (view = (View) weakReference.get()) != null) {
            ViewCompat.removeAccessibilityAction(view, 262144);
            ViewCompat.removeAccessibilityAction(view, 1048576);
            if (this.state != 5) {
                replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, 5);
            }
            if (this.state != 3) {
                replaceAccessibilityActionForState(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_EXPAND, 3);
            }
        }
    }

    private void replaceAccessibilityActionForState(View view, AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, int i) {
        ViewCompat.replaceAccessibilityAction(view, accessibilityActionCompat, (CharSequence) null, createAccessibilityViewCommandForState(i));
    }

    private AccessibilityViewCommand createAccessibilityViewCommandForState(int i) {
        return new SideSheetBehavior$$ExternalSyntheticLambda0(this, i);
    }
}
