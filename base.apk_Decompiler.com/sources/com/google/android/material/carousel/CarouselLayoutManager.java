package com.google.android.material.carousel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.R$dimen;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.carousel.KeylineState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CarouselLayoutManager extends RecyclerView.LayoutManager implements Carousel, RecyclerView.SmoothScroller.ScrollVectorProvider {
    private int carouselAlignment;
    private CarouselStrategy carouselStrategy;
    private int currentEstimatedPosition;
    private int currentFillStartPosition;
    private KeylineState currentKeylineState;
    private final DebugItemDecoration debugItemDecoration;
    private boolean isDebuggingEnabled;
    /* access modifiers changed from: private */
    public KeylineStateList keylineStateList;
    private Map keylineStatePositionMap;
    private int lastItemCount;
    int maxScroll;
    int minScroll;
    private CarouselOrientationHelper orientationHelper;
    private final View.OnLayoutChangeListener recyclerViewSizeChangeListener;
    int scrollOffset;

    private static int calculateShouldScrollBy(int i, int i2, int i3, int i4) {
        int i5 = i2 + i;
        return i5 < i3 ? i3 - i2 : i5 > i4 ? i4 - i2 : i;
    }

    private void updateChildMaskForLocation(View view, float f, KeylineRange keylineRange) {
    }

    public boolean isAutoMeasureEnabled() {
        return true;
    }

    public static /* synthetic */ void $r8$lambda$l_qCTHOyjlXYirl7hEzmLbCJVAg(CarouselLayoutManager carouselLayoutManager, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        carouselLayoutManager.getClass();
        if (i3 - i != i7 - i5 || i4 - i2 != i8 - i6) {
            view.post(new CarouselLayoutManager$$ExternalSyntheticLambda1(carouselLayoutManager));
        }
    }

    final class ChildCalculations {
        final float center;
        final View child;
        final float offsetCenter;
        final KeylineRange range;

        ChildCalculations(View view, float f, float f2, KeylineRange keylineRange) {
            this.child = view;
            this.center = f;
            this.offsetCenter = f2;
            this.range = keylineRange;
        }
    }

    public CarouselLayoutManager() {
        this(new MultiBrowseCarouselStrategy());
    }

    public CarouselLayoutManager(CarouselStrategy carouselStrategy2) {
        this(carouselStrategy2, 0);
    }

    public CarouselLayoutManager(CarouselStrategy carouselStrategy2, int i) {
        this.isDebuggingEnabled = false;
        this.debugItemDecoration = new DebugItemDecoration();
        this.currentFillStartPosition = 0;
        this.recyclerViewSizeChangeListener = new CarouselLayoutManager$$ExternalSyntheticLambda0(this);
        this.currentEstimatedPosition = -1;
        this.carouselAlignment = 0;
        setCarouselStrategy(carouselStrategy2);
        setOrientation(i);
    }

    @SuppressLint({"UnknownNullness"})
    public CarouselLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.isDebuggingEnabled = false;
        this.debugItemDecoration = new DebugItemDecoration();
        this.currentFillStartPosition = 0;
        this.recyclerViewSizeChangeListener = new CarouselLayoutManager$$ExternalSyntheticLambda0(this);
        this.currentEstimatedPosition = -1;
        this.carouselAlignment = 0;
        setCarouselStrategy(new MultiBrowseCarouselStrategy());
        setCarouselAttributes(context, attributeSet);
    }

    private void setCarouselAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Carousel);
            setCarouselAlignment(obtainStyledAttributes.getInt(R$styleable.Carousel_carousel_alignment, 0));
            setOrientation(obtainStyledAttributes.getInt(androidx.recyclerview.R$styleable.RecyclerView_android_orientation, 0));
            obtainStyledAttributes.recycle();
        }
    }

    public void setCarouselAlignment(int i) {
        this.carouselAlignment = i;
        refreshKeylineState();
    }

    public int getCarouselAlignment() {
        return this.carouselAlignment;
    }

    private int getLeftOrTopPaddingForKeylineShift() {
        if (getClipToPadding()) {
            return 0;
        }
        if (getOrientation() == 1) {
            return getPaddingTop();
        }
        return getPaddingLeft();
    }

    private int getRightOrBottomPaddingForKeylineShift() {
        if (getClipToPadding()) {
            return 0;
        }
        if (getOrientation() == 1) {
            return getPaddingBottom();
        }
        return getPaddingRight();
    }

    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    public void setCarouselStrategy(CarouselStrategy carouselStrategy2) {
        this.carouselStrategy = carouselStrategy2;
        refreshKeylineState();
    }

    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        this.carouselStrategy.initialize(recyclerView.getContext());
        refreshKeylineState();
        recyclerView.addOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        recyclerView.removeOnLayoutChangeListener(this.recyclerViewSizeChangeListener);
    }

    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() <= 0 || ((float) getContainerSize()) <= 0.0f) {
            removeAndRecycleAllViews(recycler);
            this.currentFillStartPosition = 0;
            return;
        }
        boolean isLayoutRtl = isLayoutRtl();
        KeylineStateList keylineStateList2 = this.keylineStateList;
        boolean z = keylineStateList2 == null;
        if (z || keylineStateList2.getDefaultState().getCarouselSize() != getContainerSize()) {
            recalculateKeylineStateList(recycler);
        }
        int calculateStartScroll = calculateStartScroll(this.keylineStateList);
        int calculateEndScroll = calculateEndScroll(state, this.keylineStateList);
        this.minScroll = isLayoutRtl ? calculateEndScroll : calculateStartScroll;
        if (isLayoutRtl) {
            calculateEndScroll = calculateStartScroll;
        }
        this.maxScroll = calculateEndScroll;
        if (z) {
            this.scrollOffset = calculateStartScroll;
            this.keylineStatePositionMap = this.keylineStateList.getKeylineStateForPositionMap(getItemCount(), this.minScroll, this.maxScroll, isLayoutRtl());
            int i = this.currentEstimatedPosition;
            if (i != -1) {
                this.scrollOffset = getScrollOffsetForPosition(i, getKeylineStateForPosition(i));
            }
        }
        int i2 = this.scrollOffset;
        this.scrollOffset = i2 + calculateShouldScrollBy(0, i2, this.minScroll, this.maxScroll);
        this.currentFillStartPosition = MathUtils.clamp(this.currentFillStartPosition, 0, state.getItemCount());
        updateCurrentKeylineStateForScrollOffset(this.keylineStateList);
        detachAndScrapAttachedViews(recycler);
        fill(recycler, state);
        this.lastItemCount = getItemCount();
    }

    private void recalculateKeylineStateList(RecyclerView.Recycler recycler) {
        View viewForPosition = recycler.getViewForPosition(0);
        measureChildWithMargins(viewForPosition, 0, 0);
        KeylineState onFirstChildMeasuredWithMargins = this.carouselStrategy.onFirstChildMeasuredWithMargins(this, viewForPosition);
        if (isLayoutRtl()) {
            onFirstChildMeasuredWithMargins = KeylineState.reverse(onFirstChildMeasuredWithMargins, getContainerSize());
        }
        this.keylineStateList = KeylineStateList.from(this, onFirstChildMeasuredWithMargins, (float) getItemMargins(), (float) getLeftOrTopPaddingForKeylineShift(), (float) getRightOrBottomPaddingForKeylineShift(), this.carouselStrategy.getStrategyType());
    }

    private int getItemMargins() {
        int i;
        int i2;
        if (getChildCount() <= 0) {
            return 0;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) getChildAt(0).getLayoutParams();
        if (this.orientationHelper.orientation == 0) {
            i = layoutParams.leftMargin;
            i2 = layoutParams.rightMargin;
        } else {
            i = layoutParams.topMargin;
            i2 = layoutParams.bottomMargin;
        }
        return i + i2;
    }

    /* access modifiers changed from: private */
    public void refreshKeylineState() {
        this.keylineStateList = null;
        requestLayout();
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        removeAndRecycleOutOfBoundsViews(recycler);
        if (getChildCount() == 0) {
            addViewsStart(recycler, this.currentFillStartPosition - 1);
            addViewsEnd(recycler, state, this.currentFillStartPosition);
        } else {
            int position = getPosition(getChildAt(0));
            int position2 = getPosition(getChildAt(getChildCount() - 1));
            addViewsStart(recycler, position - 1);
            addViewsEnd(recycler, state, position2 + 1);
        }
        validateChildOrderIfDebugging();
    }

    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        if (getChildCount() == 0) {
            this.currentFillStartPosition = 0;
        } else {
            this.currentFillStartPosition = getPosition(getChildAt(0));
        }
        validateChildOrderIfDebugging();
    }

    private void addViewsStart(RecyclerView.Recycler recycler, int i) {
        float calculateChildStartForFill = calculateChildStartForFill(i);
        while (i >= 0) {
            float addEnd = addEnd(calculateChildStartForFill, this.currentKeylineState.getItemSize() / 2.0f);
            KeylineRange surroundingKeylineRange = getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), addEnd, false);
            float calculateChildOffsetCenterForLocation = calculateChildOffsetCenterForLocation(addEnd, surroundingKeylineRange);
            if (!isLocOffsetOutOfFillBoundsStart(calculateChildOffsetCenterForLocation, surroundingKeylineRange)) {
                calculateChildStartForFill = addStart(calculateChildStartForFill, this.currentKeylineState.getItemSize());
                if (!isLocOffsetOutOfFillBoundsEnd(calculateChildOffsetCenterForLocation, surroundingKeylineRange)) {
                    View viewForPosition = recycler.getViewForPosition(i);
                    addAndLayoutView(viewForPosition, 0, new ChildCalculations(viewForPosition, addEnd, calculateChildOffsetCenterForLocation, surroundingKeylineRange));
                }
                i--;
            } else {
                return;
            }
        }
    }

    private void addViewAtPosition(RecyclerView.Recycler recycler, int i, int i2) {
        if (i >= 0 && i < getItemCount()) {
            ChildCalculations makeChildCalculations = makeChildCalculations(recycler, calculateChildStartForFill(i), i);
            addAndLayoutView(makeChildCalculations.child, i2, makeChildCalculations);
        }
    }

    private void addViewsEnd(RecyclerView.Recycler recycler, RecyclerView.State state, int i) {
        float calculateChildStartForFill = calculateChildStartForFill(i);
        while (i < state.getItemCount()) {
            float addEnd = addEnd(calculateChildStartForFill, this.currentKeylineState.getItemSize() / 2.0f);
            KeylineRange surroundingKeylineRange = getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), addEnd, false);
            float calculateChildOffsetCenterForLocation = calculateChildOffsetCenterForLocation(addEnd, surroundingKeylineRange);
            if (!isLocOffsetOutOfFillBoundsEnd(calculateChildOffsetCenterForLocation, surroundingKeylineRange)) {
                calculateChildStartForFill = addEnd(calculateChildStartForFill, this.currentKeylineState.getItemSize());
                if (!isLocOffsetOutOfFillBoundsStart(calculateChildOffsetCenterForLocation, surroundingKeylineRange)) {
                    View viewForPosition = recycler.getViewForPosition(i);
                    addAndLayoutView(viewForPosition, -1, new ChildCalculations(viewForPosition, addEnd, calculateChildOffsetCenterForLocation, surroundingKeylineRange));
                }
                i++;
            } else {
                return;
            }
        }
    }

    private void logChildrenIfDebugging() {
        if (this.isDebuggingEnabled && Log.isLoggable("CarouselLayoutManager", 3)) {
            Log.d("CarouselLayoutManager", "internal representation of views on the screen");
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                float decoratedCenterWithMargins = getDecoratedCenterWithMargins(childAt);
                Log.d("CarouselLayoutManager", "item position " + getPosition(childAt) + ", center:" + decoratedCenterWithMargins + ", child index:" + i);
            }
            Log.d("CarouselLayoutManager", "==============");
        }
    }

    private void validateChildOrderIfDebugging() {
        if (this.isDebuggingEnabled && getChildCount() >= 1) {
            int i = 0;
            while (i < getChildCount() - 1) {
                int position = getPosition(getChildAt(i));
                int i2 = i + 1;
                int position2 = getPosition(getChildAt(i2));
                if (position <= position2) {
                    i = i2;
                } else {
                    logChildrenIfDebugging();
                    throw new IllegalStateException("Detected invalid child order. Child at index [" + i + "] had adapter position [" + position + "] and child at index [" + i2 + "] had adapter position [" + position2 + "].");
                }
            }
        }
    }

    private ChildCalculations makeChildCalculations(RecyclerView.Recycler recycler, float f, int i) {
        View viewForPosition = recycler.getViewForPosition(i);
        measureChildWithMargins(viewForPosition, 0, 0);
        float addEnd = addEnd(f, this.currentKeylineState.getItemSize() / 2.0f);
        KeylineRange surroundingKeylineRange = getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), addEnd, false);
        return new ChildCalculations(viewForPosition, addEnd, calculateChildOffsetCenterForLocation(addEnd, surroundingKeylineRange), surroundingKeylineRange);
    }

    private void addAndLayoutView(View view, int i, ChildCalculations childCalculations) {
        float itemSize = this.currentKeylineState.getItemSize() / 2.0f;
        addView(view, i);
        measureChildWithMargins(view, 0, 0);
        float f = childCalculations.offsetCenter;
        this.orientationHelper.layoutDecoratedWithMargins(view, (int) (f - itemSize), (int) (f + itemSize));
        updateChildMaskForLocation(view, childCalculations.center, childCalculations.range);
    }

    private boolean isLocOffsetOutOfFillBoundsStart(float f, KeylineRange keylineRange) {
        float addEnd = addEnd(f, getMaskedItemSizeForLocOffset(f, keylineRange) / 2.0f);
        return isLayoutRtl() ? addEnd > ((float) getContainerSize()) : addEnd < 0.0f;
    }

    public boolean isHorizontal() {
        return this.orientationHelper.orientation == 0;
    }

    private boolean isLocOffsetOutOfFillBoundsEnd(float f, KeylineRange keylineRange) {
        float addStart = addStart(f, getMaskedItemSizeForLocOffset(f, keylineRange) / 2.0f);
        return isLayoutRtl() ? addStart < 0.0f : addStart > ((float) getContainerSize());
    }

    public void getDecoratedBoundsWithMargins(View view, Rect rect) {
        super.getDecoratedBoundsWithMargins(view, rect);
        float centerY = (float) rect.centerY();
        if (isHorizontal()) {
            centerY = (float) rect.centerX();
        }
        float maskedItemSizeForLocOffset = getMaskedItemSizeForLocOffset(centerY, getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), centerY, true));
        float f = 0.0f;
        float width = isHorizontal() ? (((float) rect.width()) - maskedItemSizeForLocOffset) / 2.0f : 0.0f;
        if (!isHorizontal()) {
            f = (((float) rect.height()) - maskedItemSizeForLocOffset) / 2.0f;
        }
        rect.set((int) (((float) rect.left) + width), (int) (((float) rect.top) + f), (int) (((float) rect.right) - width), (int) (((float) rect.bottom) - f));
    }

    private float getDecoratedCenterWithMargins(View view) {
        int centerY;
        Rect rect = new Rect();
        super.getDecoratedBoundsWithMargins(view, rect);
        if (isHorizontal()) {
            centerY = rect.centerX();
        } else {
            centerY = rect.centerY();
        }
        return (float) centerY;
    }

    private void removeAndRecycleOutOfBoundsViews(RecyclerView.Recycler recycler) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            float decoratedCenterWithMargins = getDecoratedCenterWithMargins(childAt);
            if (!isLocOffsetOutOfFillBoundsStart(decoratedCenterWithMargins, getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), decoratedCenterWithMargins, true))) {
                break;
            }
            removeAndRecycleView(childAt, recycler);
        }
        while (getChildCount() - 1 >= 0) {
            View childAt2 = getChildAt(getChildCount() - 1);
            float decoratedCenterWithMargins2 = getDecoratedCenterWithMargins(childAt2);
            if (isLocOffsetOutOfFillBoundsEnd(decoratedCenterWithMargins2, getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), decoratedCenterWithMargins2, true))) {
                removeAndRecycleView(childAt2, recycler);
            } else {
                return;
            }
        }
    }

    private static KeylineRange getSurroundingKeylineRange(List list, float f, boolean z) {
        float f2 = Float.MAX_VALUE;
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        float f3 = -3.4028235E38f;
        float f4 = Float.MAX_VALUE;
        float f5 = Float.MAX_VALUE;
        for (int i5 = 0; i5 < list.size(); i5++) {
            KeylineState.Keyline keyline = (KeylineState.Keyline) list.get(i5);
            float f6 = z ? keyline.locOffset : keyline.loc;
            float abs = Math.abs(f6 - f);
            if (f6 <= f && abs <= f2) {
                i = i5;
                f2 = abs;
            }
            if (f6 > f && abs <= f4) {
                i3 = i5;
                f4 = abs;
            }
            if (f6 <= f5) {
                i2 = i5;
                f5 = f6;
            }
            if (f6 > f3) {
                i4 = i5;
                f3 = f6;
            }
        }
        if (i == -1) {
            i = i2;
        }
        if (i3 == -1) {
            i3 = i4;
        }
        return new KeylineRange((KeylineState.Keyline) list.get(i), (KeylineState.Keyline) list.get(i3));
    }

    private KeylineState getKeylineStartingState(KeylineStateList keylineStateList2) {
        return isLayoutRtl() ? keylineStateList2.getEndState() : keylineStateList2.getStartState();
    }

    private void updateCurrentKeylineStateForScrollOffset(KeylineStateList keylineStateList2) {
        int i = this.maxScroll;
        int i2 = this.minScroll;
        if (i <= i2) {
            this.currentKeylineState = getKeylineStartingState(keylineStateList2);
        } else {
            this.currentKeylineState = keylineStateList2.getShiftedState((float) this.scrollOffset, (float) i2, (float) i);
        }
        this.debugItemDecoration.setKeylines(this.currentKeylineState.getKeylines());
    }

    private int calculateStartScroll(KeylineStateList keylineStateList2) {
        boolean isLayoutRtl = isLayoutRtl();
        KeylineState endState = isLayoutRtl ? keylineStateList2.getEndState() : keylineStateList2.getStartState();
        return (int) (((float) getParentStart()) - addStart((isLayoutRtl ? endState.getLastFocalKeyline() : endState.getFirstFocalKeyline()).loc, endState.getItemSize() / 2.0f));
    }

    private int calculateEndScroll(RecyclerView.State state, KeylineStateList keylineStateList2) {
        boolean isLayoutRtl = isLayoutRtl();
        KeylineState startState = isLayoutRtl ? keylineStateList2.getStartState() : keylineStateList2.getEndState();
        KeylineState.Keyline firstFocalKeyline = isLayoutRtl ? startState.getFirstFocalKeyline() : startState.getLastFocalKeyline();
        int i = 1;
        float itemCount = ((((float) (state.getItemCount() - 1)) * startState.getItemSize()) * (isLayoutRtl ? -1.0f : 1.0f)) - (firstFocalKeyline.loc - ((float) getParentStart()));
        if (isLayoutRtl) {
            i = -1;
        }
        int i2 = (int) (itemCount + ((((float) i) * firstFocalKeyline.maskedItemSize) / 2.0f));
        return isLayoutRtl ? Math.min(0, i2) : Math.max(0, i2);
    }

    private float calculateChildStartForFill(int i) {
        return addEnd((float) (getParentStart() - this.scrollOffset), this.currentKeylineState.getItemSize() * ((float) i));
    }

    private float calculateChildOffsetCenterForLocation(float f, KeylineRange keylineRange) {
        KeylineState.Keyline keyline = keylineRange.leftOrTop;
        float f2 = keyline.locOffset;
        KeylineState.Keyline keyline2 = keylineRange.rightOrBottom;
        float lerp = AnimationUtils.lerp(f2, keyline2.locOffset, keyline.loc, keyline2.loc, f);
        if (keylineRange.rightOrBottom != this.currentKeylineState.getFirstKeyline() && keylineRange.leftOrTop != this.currentKeylineState.getLastKeyline()) {
            return lerp;
        }
        KeylineState.Keyline keyline3 = keylineRange.rightOrBottom;
        return lerp + ((f - keyline3.loc) * (1.0f - keyline3.mask));
    }

    private float getMaskedItemSizeForLocOffset(float f, KeylineRange keylineRange) {
        KeylineState.Keyline keyline = keylineRange.leftOrTop;
        float f2 = keyline.maskedItemSize;
        KeylineState.Keyline keyline2 = keylineRange.rightOrBottom;
        return AnimationUtils.lerp(f2, keyline2.maskedItemSize, keyline.locOffset, keyline2.locOffset, f);
    }

    public void measureChildWithMargins(View view, int i, int i2) {
        throw new IllegalStateException("All children of a RecyclerView using CarouselLayoutManager must use MaskableFrameLayout as their root ViewGroup.");
    }

    /* access modifiers changed from: private */
    public int getParentLeft() {
        return this.orientationHelper.getParentLeft();
    }

    private int getParentStart() {
        return this.orientationHelper.getParentStart();
    }

    /* access modifiers changed from: private */
    public int getParentRight() {
        return this.orientationHelper.getParentRight();
    }

    /* access modifiers changed from: private */
    public int getParentTop() {
        return this.orientationHelper.getParentTop();
    }

    /* access modifiers changed from: private */
    public int getParentBottom() {
        return this.orientationHelper.getParentBottom();
    }

    public int getContainerWidth() {
        return getWidth();
    }

    public int getContainerHeight() {
        return getHeight();
    }

    private int getContainerSize() {
        if (isHorizontal()) {
            return getContainerWidth();
        }
        return getContainerHeight();
    }

    /* access modifiers changed from: package-private */
    public boolean isLayoutRtl() {
        return isHorizontal() && getLayoutDirection() == 1;
    }

    private float addStart(float f, float f2) {
        return isLayoutRtl() ? f + f2 : f - f2;
    }

    private float addEnd(float f, float f2) {
        return isLayoutRtl() ? f - f2 : f + f2;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(getPosition(getChildAt(0)));
            accessibilityEvent.setToIndex(getPosition(getChildAt(getChildCount() - 1)));
        }
    }

    private int getScrollOffsetForPosition(int i, KeylineState keylineState) {
        float itemSize;
        if (isLayoutRtl()) {
            itemSize = ((((float) getContainerSize()) - keylineState.getLastFocalKeyline().loc) - (((float) i) * keylineState.getItemSize())) - (keylineState.getItemSize() / 2.0f);
        } else {
            itemSize = ((((float) i) * keylineState.getItemSize()) - keylineState.getFirstFocalKeyline().loc) + (keylineState.getItemSize() / 2.0f);
        }
        return (int) itemSize;
    }

    private int getSmallestScrollOffsetToFocalKeyline(int i, KeylineState keylineState) {
        int i2;
        int i3 = Integer.MAX_VALUE;
        for (KeylineState.Keyline keyline : keylineState.getFocalKeylines()) {
            float itemSize = (((float) i) * keylineState.getItemSize()) + (keylineState.getItemSize() / 2.0f);
            if (isLayoutRtl()) {
                i2 = (int) ((((float) getContainerSize()) - keyline.loc) - itemSize);
            } else {
                i2 = (int) (itemSize - keyline.loc);
            }
            int i4 = i2 - this.scrollOffset;
            if (Math.abs(i3) > Math.abs(i4)) {
                i3 = i4;
            }
        }
        return i3;
    }

    public PointF computeScrollVectorForPosition(int i) {
        if (this.keylineStateList == null) {
            return null;
        }
        int offsetToScrollToPosition = getOffsetToScrollToPosition(i, getKeylineStateForPosition(i));
        if (isHorizontal()) {
            return new PointF((float) offsetToScrollToPosition, 0.0f);
        }
        return new PointF(0.0f, (float) offsetToScrollToPosition);
    }

    /* access modifiers changed from: package-private */
    public int getOffsetToScrollToPosition(int i, KeylineState keylineState) {
        return getScrollOffsetForPosition(i, keylineState) - this.scrollOffset;
    }

    private KeylineState getKeylineStateForPosition(int i) {
        KeylineState keylineState;
        Map map = this.keylineStatePositionMap;
        if (map == null || (keylineState = (KeylineState) map.get(Integer.valueOf(MathUtils.clamp(i, 0, Math.max(0, getItemCount() - 1))))) == null) {
            return this.keylineStateList.getDefaultState();
        }
        return keylineState;
    }

    public void scrollToPosition(int i) {
        this.currentEstimatedPosition = i;
        if (this.keylineStateList != null) {
            this.scrollOffset = getScrollOffsetForPosition(i, getKeylineStateForPosition(i));
            this.currentFillStartPosition = MathUtils.clamp(i, 0, Math.max(0, getItemCount() - 1));
            updateCurrentKeylineStateForScrollOffset(this.keylineStateList);
            requestLayout();
        }
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        AnonymousClass1 r2 = new LinearSmoothScroller(recyclerView.getContext()) {
            public PointF computeScrollVectorForPosition(int i) {
                return CarouselLayoutManager.this.computeScrollVectorForPosition(i);
            }

            public int calculateDxToMakeVisible(View view, int i) {
                if (CarouselLayoutManager.this.keylineStateList == null || !CarouselLayoutManager.this.isHorizontal()) {
                    return 0;
                }
                CarouselLayoutManager carouselLayoutManager = CarouselLayoutManager.this;
                return carouselLayoutManager.calculateScrollDeltaToMakePositionVisible(carouselLayoutManager.getPosition(view));
            }

            public int calculateDyToMakeVisible(View view, int i) {
                if (CarouselLayoutManager.this.keylineStateList == null || CarouselLayoutManager.this.isHorizontal()) {
                    return 0;
                }
                CarouselLayoutManager carouselLayoutManager = CarouselLayoutManager.this;
                return carouselLayoutManager.calculateScrollDeltaToMakePositionVisible(carouselLayoutManager.getPosition(view));
            }
        };
        r2.setTargetPosition(i);
        startSmoothScroll(r2);
    }

    /* access modifiers changed from: package-private */
    public int calculateScrollDeltaToMakePositionVisible(int i) {
        return (int) (((float) this.scrollOffset) - ((float) getScrollOffsetForPosition(i, getKeylineStateForPosition(i))));
    }

    public boolean canScrollHorizontally() {
        return isHorizontal();
    }

    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (canScrollHorizontally()) {
            return scrollBy(i, recycler, state);
        }
        return 0;
    }

    public boolean canScrollVertically() {
        return !isHorizontal();
    }

    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (canScrollVertically()) {
            return scrollBy(i, recycler, state);
        }
        return 0;
    }

    private int convertFocusDirectionToLayoutDirection(int i) {
        int orientation = getOrientation();
        if (i == 1) {
            return -1;
        }
        if (i == 2) {
            return 1;
        }
        if (i != 17) {
            if (i == 33) {
                return orientation == 1 ? -1 : Integer.MIN_VALUE;
            }
            if (i != 66) {
                if (i == 130) {
                    return orientation == 1 ? 1 : Integer.MIN_VALUE;
                }
                Log.d("CarouselLayoutManager", "Unknown focus request:" + i);
                return Integer.MIN_VALUE;
            } else if (orientation == 0) {
                return isLayoutRtl() ? -1 : 1;
            } else {
                return Integer.MIN_VALUE;
            }
        } else if (orientation == 0) {
            return isLayoutRtl() ? 1 : -1;
        } else {
            return Integer.MIN_VALUE;
        }
    }

    public View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int convertFocusDirectionToLayoutDirection;
        if (getChildCount() == 0 || (convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i)) == Integer.MIN_VALUE) {
            return null;
        }
        if (convertFocusDirectionToLayoutDirection == -1) {
            if (getPosition(view) == 0) {
                return null;
            }
            addViewAtPosition(recycler, getPosition(getChildAt(0)) - 1, 0);
            return getChildClosestToStart();
        } else if (getPosition(view) == getItemCount() - 1) {
            return null;
        } else {
            addViewAtPosition(recycler, getPosition(getChildAt(getChildCount() - 1)) + 1, -1);
            return getChildClosestToEnd();
        }
    }

    private View getChildClosestToStart() {
        return getChildAt(isLayoutRtl() ? getChildCount() - 1 : 0);
    }

    private View getChildClosestToEnd() {
        return getChildAt(isLayoutRtl() ? 0 : getChildCount() - 1);
    }

    public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
        int smallestScrollOffsetToFocalKeyline;
        if (this.keylineStateList == null || (smallestScrollOffsetToFocalKeyline = getSmallestScrollOffsetToFocalKeyline(getPosition(view), getKeylineStateForPosition(getPosition(view)))) == 0) {
            return false;
        }
        scrollBy(recyclerView, getSmallestScrollOffsetToFocalKeyline(getPosition(view), this.keylineStateList.getShiftedState((float) (this.scrollOffset + calculateShouldScrollBy(smallestScrollOffsetToFocalKeyline, this.scrollOffset, this.minScroll, this.maxScroll)), (float) this.minScroll, (float) this.maxScroll)));
        return true;
    }

    private void scrollBy(RecyclerView recyclerView, int i) {
        if (isHorizontal()) {
            recyclerView.scrollBy(i, 0);
        } else {
            recyclerView.scrollBy(0, i);
        }
    }

    private int scrollBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        float f;
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        if (this.keylineStateList == null) {
            recalculateKeylineStateList(recycler);
        }
        if (getItemCount() <= getKeylineStartingState(this.keylineStateList).getTotalVisibleFocalItems()) {
            return 0;
        }
        int calculateShouldScrollBy = calculateShouldScrollBy(i, this.scrollOffset, this.minScroll, this.maxScroll);
        this.scrollOffset += calculateShouldScrollBy;
        updateCurrentKeylineStateForScrollOffset(this.keylineStateList);
        float itemSize = this.currentKeylineState.getItemSize() / 2.0f;
        float calculateChildStartForFill = calculateChildStartForFill(getPosition(getChildAt(0)));
        Rect rect = new Rect();
        if (isLayoutRtl()) {
            f = this.currentKeylineState.getLastFocalKeyline().locOffset;
        } else {
            f = this.currentKeylineState.getFirstFocalKeyline().locOffset;
        }
        float f2 = Float.MAX_VALUE;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            float abs = Math.abs(f - offsetChild(childAt, calculateChildStartForFill, itemSize, rect));
            if (childAt != null && abs < f2) {
                this.currentEstimatedPosition = getPosition(childAt);
                f2 = abs;
            }
            calculateChildStartForFill = addEnd(calculateChildStartForFill, this.currentKeylineState.getItemSize());
        }
        fill(recycler, state);
        return calculateShouldScrollBy;
    }

    private float offsetChild(View view, float f, float f2, Rect rect) {
        float addEnd = addEnd(f, f2);
        KeylineRange surroundingKeylineRange = getSurroundingKeylineRange(this.currentKeylineState.getKeylines(), addEnd, false);
        float calculateChildOffsetCenterForLocation = calculateChildOffsetCenterForLocation(addEnd, surroundingKeylineRange);
        super.getDecoratedBoundsWithMargins(view, rect);
        updateChildMaskForLocation(view, addEnd, surroundingKeylineRange);
        this.orientationHelper.offsetChild(view, rect, f2, calculateChildOffsetCenterForLocation);
        return calculateChildOffsetCenterForLocation;
    }

    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.scrollOffset;
    }

    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        if (getChildCount() == 0 || this.keylineStateList == null || getItemCount() <= 1) {
            return 0;
        }
        return (int) (((float) getWidth()) * (this.keylineStateList.getDefaultState().getItemSize() / ((float) computeHorizontalScrollRange(state))));
    }

    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.scrollOffset;
    }

    public int computeVerticalScrollExtent(RecyclerView.State state) {
        if (getChildCount() == 0 || this.keylineStateList == null || getItemCount() <= 1) {
            return 0;
        }
        return (int) (((float) getHeight()) * (this.keylineStateList.getDefaultState().getItemSize() / ((float) computeVerticalScrollRange(state))));
    }

    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.maxScroll - this.minScroll;
    }

    public int getOrientation() {
        return this.orientationHelper.orientation;
    }

    public void setOrientation(int i) {
        if (i == 0 || i == 1) {
            assertNotInLayoutOrScroll((String) null);
            CarouselOrientationHelper carouselOrientationHelper = this.orientationHelper;
            if (carouselOrientationHelper == null || i != carouselOrientationHelper.orientation) {
                this.orientationHelper = CarouselOrientationHelper.createOrientationHelper(this, i);
                refreshKeylineState();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation:" + i);
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        super.onItemsAdded(recyclerView, i, i2);
        updateItemCount();
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        super.onItemsRemoved(recyclerView, i, i2);
        updateItemCount();
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        super.onItemsChanged(recyclerView);
        updateItemCount();
    }

    private void updateItemCount() {
        int itemCount = getItemCount();
        int i = this.lastItemCount;
        if (itemCount != i && this.keylineStateList != null) {
            if (this.carouselStrategy.shouldRefreshKeylineState(this, i)) {
                refreshKeylineState();
            }
            this.lastItemCount = itemCount;
        }
    }

    class KeylineRange {
        final KeylineState.Keyline leftOrTop;
        final KeylineState.Keyline rightOrBottom;

        KeylineRange(KeylineState.Keyline keyline, KeylineState.Keyline keyline2) {
            Preconditions.checkArgument(keyline.loc <= keyline2.loc);
            this.leftOrTop = keyline;
            this.rightOrBottom = keyline2;
        }
    }

    class DebugItemDecoration extends RecyclerView.ItemDecoration {
        private List keylines = Collections.unmodifiableList(new ArrayList());
        private final Paint linePaint;

        DebugItemDecoration() {
            Paint paint = new Paint();
            this.linePaint = paint;
            paint.setStrokeWidth(5.0f);
            paint.setColor(-65281);
        }

        /* access modifiers changed from: package-private */
        public void setKeylines(List list) {
            this.keylines = Collections.unmodifiableList(list);
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
            super.onDrawOver(canvas, recyclerView, state);
            this.linePaint.setStrokeWidth(recyclerView.getResources().getDimension(R$dimen.m3_carousel_debug_keyline_width));
            for (KeylineState.Keyline keyline : this.keylines) {
                this.linePaint.setColor(ColorUtils.blendARGB(-65281, -16776961, keyline.mask));
                if (((CarouselLayoutManager) recyclerView.getLayoutManager()).isHorizontal()) {
                    canvas.drawLine(keyline.locOffset, (float) ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentTop(), keyline.locOffset, (float) ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentBottom(), this.linePaint);
                } else {
                    canvas.drawLine((float) ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentLeft(), keyline.locOffset, (float) ((CarouselLayoutManager) recyclerView.getLayoutManager()).getParentRight(), keyline.locOffset, this.linePaint);
                }
            }
        }
    }
}
