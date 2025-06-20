package com.google.android.material.button;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.material.R$style;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.shape.StateListCornerSize;
import com.google.android.material.shape.StateListShapeAppearanceModel;
import com.google.android.material.shape.StateListSizeChange;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public abstract class MaterialButtonGroup extends LinearLayout {
    private static final int DEF_STYLE_RES = R$style.Widget_Material3_MaterialButtonGroup;
    private StateListSizeChange buttonSizeChange;
    private Integer[] childOrder;
    private final Comparator childOrderComparator = new MaterialButtonGroup$$ExternalSyntheticLambda0(this);
    private StateListShapeAppearanceModel groupStateListShapeAppearance;
    StateListCornerSize innerCornerSize;
    private boolean isChildrenShapeInvalidated = true;
    private final List originalChildShapeAppearanceModels = new ArrayList();
    private final List originalChildStateListShapeAppearanceModels = new ArrayList();
    private final PressedStateTracker pressedStateTracker = new PressedStateTracker();
    private int spacing;

    public static /* synthetic */ int $r8$lambda$Rax0YMRIbiIrB6RD8v2eDsNN8o4(MaterialButtonGroup materialButtonGroup, MaterialButton materialButton, MaterialButton materialButton2) {
        materialButtonGroup.getClass();
        int compareTo = Boolean.valueOf(materialButton.isChecked()).compareTo(Boolean.valueOf(materialButton2.isChecked()));
        if (compareTo != 0) {
            return compareTo;
        }
        int compareTo2 = Boolean.valueOf(materialButton.isPressed()).compareTo(Boolean.valueOf(materialButton2.isPressed()));
        if (compareTo2 != 0) {
            return compareTo2;
        }
        return Integer.compare(materialButtonGroup.indexOfChild(materialButton), materialButtonGroup.indexOfChild(materialButton2));
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MaterialButtonGroup(android.content.Context r8, android.util.AttributeSet r9, int r10) {
        /*
            r7 = this;
            int r4 = DEF_STYLE_RES
            android.content.Context r8 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r8, r9, r10, r4)
            r7.<init>(r8, r9, r10)
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r7.originalChildShapeAppearanceModels = r8
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r7.originalChildStateListShapeAppearanceModels = r8
            com.google.android.material.button.MaterialButtonGroup$PressedStateTracker r8 = new com.google.android.material.button.MaterialButtonGroup$PressedStateTracker
            r0 = 0
            r8.<init>()
            r7.pressedStateTracker = r8
            com.google.android.material.button.MaterialButtonGroup$$ExternalSyntheticLambda0 r8 = new com.google.android.material.button.MaterialButtonGroup$$ExternalSyntheticLambda0
            r8.<init>(r7)
            r7.childOrderComparator = r8
            r8 = 1
            r7.isChildrenShapeInvalidated = r8
            android.content.Context r0 = r7.getContext()
            int[] r2 = com.google.android.material.R$styleable.MaterialButtonGroup
            r6 = 0
            int[] r5 = new int[r6]
            r1 = r9
            r3 = r10
            android.content.res.TypedArray r9 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r0, r1, r2, r3, r4, r5)
            int r10 = com.google.android.material.R$styleable.MaterialButtonGroup_buttonSizeChange
            boolean r1 = r9.hasValue(r10)
            if (r1 == 0) goto L_0x0046
            com.google.android.material.shape.StateListSizeChange r10 = com.google.android.material.shape.StateListSizeChange.create(r0, r9, r10)
            r7.buttonSizeChange = r10
        L_0x0046:
            int r10 = com.google.android.material.R$styleable.MaterialButtonGroup_shapeAppearance
            boolean r1 = r9.hasValue(r10)
            if (r1 == 0) goto L_0x0073
            com.google.android.material.shape.StateListShapeAppearanceModel r1 = com.google.android.material.shape.StateListShapeAppearanceModel.create(r0, r9, r10)
            r7.groupStateListShapeAppearance = r1
            if (r1 != 0) goto L_0x0073
            com.google.android.material.shape.StateListShapeAppearanceModel$Builder r1 = new com.google.android.material.shape.StateListShapeAppearanceModel$Builder
            int r10 = r9.getResourceId(r10, r6)
            int r2 = com.google.android.material.R$styleable.MaterialButtonGroup_shapeAppearanceOverlay
            int r2 = r9.getResourceId(r2, r6)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r10 = com.google.android.material.shape.ShapeAppearanceModel.builder(r0, r10, r2)
            com.google.android.material.shape.ShapeAppearanceModel r10 = r10.build()
            r1.<init>((com.google.android.material.shape.ShapeAppearanceModel) r10)
            com.google.android.material.shape.StateListShapeAppearanceModel r10 = r1.build()
            r7.groupStateListShapeAppearance = r10
        L_0x0073:
            int r10 = com.google.android.material.R$styleable.MaterialButtonGroup_innerCornerSize
            boolean r1 = r9.hasValue(r10)
            if (r1 == 0) goto L_0x0087
            com.google.android.material.shape.AbsoluteCornerSize r1 = new com.google.android.material.shape.AbsoluteCornerSize
            r2 = 0
            r1.<init>(r2)
            com.google.android.material.shape.StateListCornerSize r10 = com.google.android.material.shape.StateListCornerSize.create(r0, r9, r10, r1)
            r7.innerCornerSize = r10
        L_0x0087:
            int r10 = com.google.android.material.R$styleable.MaterialButtonGroup_android_spacing
            int r10 = r9.getDimensionPixelSize(r10, r6)
            r7.spacing = r10
            r7.setChildrenDrawingOrderEnabled(r8)
            int r10 = com.google.android.material.R$styleable.MaterialButtonGroup_android_enabled
            boolean r8 = r9.getBoolean(r10, r8)
            r7.setEnabled(r8)
            r9.recycle()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.button.MaterialButtonGroup.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        updateChildOrder();
        super.dispatchDraw(canvas);
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (!(view instanceof MaterialButton)) {
            Log.e("MButtonGroup", "Child views must be of type MaterialButton.");
            return;
        }
        recoverAllChildrenLayoutParams();
        this.isChildrenShapeInvalidated = true;
        super.addView(view, i, layoutParams);
        MaterialButton materialButton = (MaterialButton) view;
        setGeneratedIdIfNeeded(materialButton);
        materialButton.setOnPressedChangeListenerInternal(this.pressedStateTracker);
        this.originalChildShapeAppearanceModels.add(materialButton.getShapeAppearanceModel());
        this.originalChildStateListShapeAppearanceModels.add(materialButton.getStateListShapeAppearanceModel());
        materialButton.setEnabled(isEnabled());
    }

    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        if (view instanceof MaterialButton) {
            ((MaterialButton) view).setOnPressedChangeListenerInternal((MaterialButton.OnPressedChangeListener) null);
        }
        int indexOfChild = indexOfChild(view);
        if (indexOfChild >= 0) {
            this.originalChildShapeAppearanceModels.remove(indexOfChild);
            this.originalChildStateListShapeAppearanceModels.remove(indexOfChild);
        }
        this.isChildrenShapeInvalidated = true;
        updateChildShapes();
        recoverAllChildrenLayoutParams();
        adjustChildMarginsAndUpdateLayout();
    }

    private void recoverAllChildrenLayoutParams() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildButton(i).recoverOriginalLayoutParams();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        updateChildShapes();
        adjustChildMarginsAndUpdateLayout();
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            recoverAllChildrenLayoutParams();
            adjustChildSizeChange();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateChildShapes() {
        int i;
        if (!(this.innerCornerSize == null && this.groupStateListShapeAppearance == null) && this.isChildrenShapeInvalidated) {
            this.isChildrenShapeInvalidated = false;
            int childCount = getChildCount();
            int firstVisibleChildIndex = getFirstVisibleChildIndex();
            int lastVisibleChildIndex = getLastVisibleChildIndex();
            int i2 = 0;
            while (i2 < childCount) {
                MaterialButton childButton = getChildButton(i2);
                if (childButton.getVisibility() != 8) {
                    boolean z = i2 == firstVisibleChildIndex;
                    boolean z2 = i2 == lastVisibleChildIndex;
                    StateListShapeAppearanceModel.Builder originalStateListShapeBuilder = getOriginalStateListShapeBuilder(z, z2, i2);
                    boolean z3 = getOrientation() == 0;
                    boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
                    if (z3) {
                        i = z ? 5 : 0;
                        if (z2) {
                            i |= 10;
                        }
                        if (isLayoutRtl) {
                            i = StateListShapeAppearanceModel.swapCornerPositionRtl(i);
                        }
                    } else {
                        i = z ? 3 : 0;
                        if (z2) {
                            i |= 12;
                        }
                    }
                    StateListShapeAppearanceModel build = originalStateListShapeBuilder.setCornerSizeOverride(this.innerCornerSize, ~i).build();
                    if (build.isStateful()) {
                        childButton.setStateListShapeAppearanceModel(build);
                    } else {
                        childButton.setShapeAppearanceModel(build.getDefaultShape(true));
                    }
                }
                i2++;
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: com.google.android.material.shape.StateListShapeAppearanceModel} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.material.shape.StateListShapeAppearanceModel.Builder getOriginalStateListShapeBuilder(boolean r2, boolean r3, int r4) {
        /*
            r1 = this;
            com.google.android.material.shape.StateListShapeAppearanceModel r0 = r1.groupStateListShapeAppearance
            if (r0 == 0) goto L_0x0009
            if (r2 != 0) goto L_0x0012
            if (r3 == 0) goto L_0x0009
            goto L_0x0012
        L_0x0009:
            java.util.List r2 = r1.originalChildStateListShapeAppearanceModels
            java.lang.Object r2 = r2.get(r4)
            r0 = r2
            com.google.android.material.shape.StateListShapeAppearanceModel r0 = (com.google.android.material.shape.StateListShapeAppearanceModel) r0
        L_0x0012:
            if (r0 != 0) goto L_0x0022
            com.google.android.material.shape.StateListShapeAppearanceModel$Builder r2 = new com.google.android.material.shape.StateListShapeAppearanceModel$Builder
            java.util.List r1 = r1.originalChildShapeAppearanceModels
            java.lang.Object r1 = r1.get(r4)
            com.google.android.material.shape.ShapeAppearanceModel r1 = (com.google.android.material.shape.ShapeAppearanceModel) r1
            r2.<init>((com.google.android.material.shape.ShapeAppearanceModel) r1)
            return r2
        L_0x0022:
            com.google.android.material.shape.StateListShapeAppearanceModel$Builder r1 = r0.toBuilder()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.button.MaterialButtonGroup.getOriginalStateListShapeBuilder(boolean, boolean, int):com.google.android.material.shape.StateListShapeAppearanceModel$Builder");
    }

    /* access modifiers changed from: protected */
    public int getChildDrawingOrder(int i, int i2) {
        Integer[] numArr = this.childOrder;
        if (numArr != null && i2 < numArr.length) {
            return numArr[i2].intValue();
        }
        Log.w("MButtonGroup", "Child order wasn't updated");
        return i2;
    }

    private void adjustChildMarginsAndUpdateLayout() {
        int firstVisibleChildIndex = getFirstVisibleChildIndex();
        if (firstVisibleChildIndex != -1) {
            for (int i = firstVisibleChildIndex + 1; i < getChildCount(); i++) {
                MaterialButton childButton = getChildButton(i);
                int min = this.spacing <= 0 ? Math.min(childButton.getStrokeWidth(), getChildButton(i - 1).getStrokeWidth()) : 0;
                LinearLayout.LayoutParams buildLayoutParams = buildLayoutParams(childButton);
                if (getOrientation() == 0) {
                    buildLayoutParams.setMarginEnd(0);
                    buildLayoutParams.setMarginStart(this.spacing - min);
                    buildLayoutParams.topMargin = 0;
                } else {
                    buildLayoutParams.bottomMargin = 0;
                    buildLayoutParams.topMargin = this.spacing - min;
                    buildLayoutParams.setMarginStart(0);
                }
                childButton.setLayoutParams(buildLayoutParams);
            }
            resetChildMargins(firstVisibleChildIndex);
        }
    }

    private void resetChildMargins(int i) {
        if (getChildCount() != 0 && i != -1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getChildButton(i).getLayoutParams();
            if (getOrientation() == 1) {
                layoutParams.topMargin = 0;
                layoutParams.bottomMargin = 0;
                return;
            }
            layoutParams.setMarginEnd(0);
            layoutParams.setMarginStart(0);
            layoutParams.leftMargin = 0;
            layoutParams.rightMargin = 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void onButtonWidthChanged(MaterialButton materialButton, int i) {
        int indexOfChild = indexOfChild(materialButton);
        if (indexOfChild >= 0) {
            MaterialButton prevVisibleChildButton = getPrevVisibleChildButton(indexOfChild);
            MaterialButton nextVisibleChildButton = getNextVisibleChildButton(indexOfChild);
            if (prevVisibleChildButton != null || nextVisibleChildButton != null) {
                if (prevVisibleChildButton == null) {
                    nextVisibleChildButton.setDisplayedWidthDecrease(i);
                }
                if (nextVisibleChildButton == null) {
                    prevVisibleChildButton.setDisplayedWidthDecrease(i);
                }
                if (prevVisibleChildButton != null && nextVisibleChildButton != null) {
                    prevVisibleChildButton.setDisplayedWidthDecrease(i / 2);
                    nextVisibleChildButton.setDisplayedWidthDecrease((i + 1) / 2);
                }
            }
        }
    }

    private void adjustChildSizeChange() {
        if (this.buttonSizeChange != null) {
            int firstVisibleChildIndex = getFirstVisibleChildIndex();
            int lastVisibleChildIndex = getLastVisibleChildIndex();
            int i = Integer.MAX_VALUE;
            for (int i2 = firstVisibleChildIndex; i2 <= lastVisibleChildIndex; i2++) {
                if (isChildVisible(i2)) {
                    int buttonAllowedWidthIncrease = getButtonAllowedWidthIncrease(i2);
                    if (!(i2 == firstVisibleChildIndex || i2 == lastVisibleChildIndex)) {
                        buttonAllowedWidthIncrease /= 2;
                    }
                    i = Math.min(i, buttonAllowedWidthIncrease);
                }
            }
            int i3 = firstVisibleChildIndex;
            while (i3 <= lastVisibleChildIndex) {
                if (isChildVisible(i3)) {
                    getChildButton(i3).setSizeChange(this.buttonSizeChange);
                    getChildButton(i3).setWidthChangeMax((i3 == firstVisibleChildIndex || i3 == lastVisibleChildIndex) ? i : i * 2);
                }
                i3++;
            }
        }
    }

    private int getButtonAllowedWidthIncrease(int i) {
        int i2;
        int i3 = 0;
        if (!isChildVisible(i) || this.buttonSizeChange == null) {
            return 0;
        }
        int max = Math.max(0, this.buttonSizeChange.getMaxWidthChange(getChildButton(i).getWidth()));
        MaterialButton prevVisibleChildButton = getPrevVisibleChildButton(i);
        if (prevVisibleChildButton == null) {
            i2 = 0;
        } else {
            i2 = prevVisibleChildButton.getAllowedWidthDecrease();
        }
        MaterialButton nextVisibleChildButton = getNextVisibleChildButton(i);
        if (nextVisibleChildButton != null) {
            i3 = nextVisibleChildButton.getAllowedWidthDecrease();
        }
        return Math.min(max, i2 + i3);
    }

    public StateListSizeChange getButtonSizeChange() {
        return this.buttonSizeChange;
    }

    /* access modifiers changed from: package-private */
    public MaterialButton getChildButton(int i) {
        return (MaterialButton) getChildAt(i);
    }

    /* access modifiers changed from: package-private */
    public LinearLayout.LayoutParams buildLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            return (LinearLayout.LayoutParams) layoutParams;
        }
        return new LinearLayout.LayoutParams(layoutParams.width, layoutParams.height);
    }

    private int getFirstVisibleChildIndex() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (isChildVisible(i)) {
                return i;
            }
        }
        return -1;
    }

    private int getLastVisibleChildIndex() {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            if (isChildVisible(childCount)) {
                return childCount;
            }
        }
        return -1;
    }

    private boolean isChildVisible(int i) {
        return getChildAt(i).getVisibility() != 8;
    }

    private void setGeneratedIdIfNeeded(MaterialButton materialButton) {
        if (materialButton.getId() == -1) {
            materialButton.setId(View.generateViewId());
        }
    }

    private MaterialButton getNextVisibleChildButton(int i) {
        int childCount = getChildCount();
        do {
            i++;
            if (i >= childCount) {
                return null;
            }
        } while (!isChildVisible(i));
        return getChildButton(i);
    }

    private MaterialButton getPrevVisibleChildButton(int i) {
        for (int i2 = i - 1; i2 >= 0; i2--) {
            if (isChildVisible(i2)) {
                return getChildButton(i2);
            }
        }
        return null;
    }

    private void updateChildOrder() {
        TreeMap treeMap = new TreeMap(this.childOrderComparator);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            treeMap.put(getChildButton(i), Integer.valueOf(i));
        }
        this.childOrder = (Integer[]) treeMap.values().toArray(new Integer[0]);
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        for (int i = 0; i < getChildCount(); i++) {
            getChildButton(i).setEnabled(z);
        }
    }

    class PressedStateTracker implements MaterialButton.OnPressedChangeListener {
        private PressedStateTracker() {
        }

        public void onPressedChanged(MaterialButton materialButton, boolean z) {
            MaterialButtonGroup.this.invalidate();
        }
    }
}
