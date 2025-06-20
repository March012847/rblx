package com.google.android.material.carousel;

import androidx.core.math.MathUtils;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.carousel.CarouselStrategy;
import com.google.android.material.carousel.KeylineState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeylineStateList {
    private final KeylineState defaultState;
    private final float endShiftRange;
    private final List endStateSteps;
    private final float[] endStateStepsInterpolationPoints;
    private final float startShiftRange;
    private final List startStateSteps;
    private final float[] startStateStepsInterpolationPoints;

    private KeylineStateList(KeylineState keylineState, List list, List list2) {
        this.defaultState = keylineState;
        this.startStateSteps = Collections.unmodifiableList(list);
        this.endStateSteps = Collections.unmodifiableList(list2);
        float f = ((KeylineState) list.get(list.size() - 1)).getFirstKeyline().loc - keylineState.getFirstKeyline().loc;
        this.startShiftRange = f;
        float f2 = keylineState.getLastKeyline().loc - ((KeylineState) list2.get(list2.size() - 1)).getLastKeyline().loc;
        this.endShiftRange = f2;
        this.startStateStepsInterpolationPoints = getStateStepInterpolationPoints(f, list, true);
        this.endStateStepsInterpolationPoints = getStateStepInterpolationPoints(f2, list2, false);
    }

    static KeylineStateList from(Carousel carousel, KeylineState keylineState, float f, float f2, float f3, CarouselStrategy.StrategyType strategyType) {
        return new KeylineStateList(keylineState, getStateStepsStart(carousel, keylineState, f, f2, strategyType), getStateStepsEnd(carousel, keylineState, f, f3, strategyType));
    }

    /* access modifiers changed from: package-private */
    public KeylineState getDefaultState() {
        return this.defaultState;
    }

    /* access modifiers changed from: package-private */
    public KeylineState getStartState() {
        List list = this.startStateSteps;
        return (KeylineState) list.get(list.size() - 1);
    }

    /* access modifiers changed from: package-private */
    public KeylineState getEndState() {
        List list = this.endStateSteps;
        return (KeylineState) list.get(list.size() - 1);
    }

    public KeylineState getShiftedState(float f, float f2, float f3) {
        return getShiftedState(f, f2, f3, false);
    }

    /* access modifiers changed from: package-private */
    public KeylineState getShiftedState(float f, float f2, float f3, boolean z) {
        float[] fArr;
        List list;
        float f4;
        float f5 = this.startShiftRange + f2;
        float f6 = f3 - this.endShiftRange;
        float f7 = getStartState().getFirstFocalKeyline().leftOrTopPaddingShift;
        float f8 = getEndState().getFirstFocalKeyline().rightOrBottomPaddingShift;
        if (this.startShiftRange == f7) {
            f5 += f7;
        }
        if (this.endShiftRange == f8) {
            f6 -= f8;
        }
        if (f < f5) {
            f4 = AnimationUtils.lerp(1.0f, 0.0f, f2, f5, f);
            list = this.startStateSteps;
            fArr = this.startStateStepsInterpolationPoints;
        } else if (f <= f6) {
            return this.defaultState;
        } else {
            f4 = AnimationUtils.lerp(0.0f, 1.0f, f6, f3, f);
            list = this.endStateSteps;
            fArr = this.endStateStepsInterpolationPoints;
        }
        if (z) {
            return closestStateStepFromInterpolation(list, f4, fArr);
        }
        return lerp(list, f4, fArr);
    }

    private static KeylineState lerp(List list, float f, float[] fArr) {
        float[] stateStepsRange = getStateStepsRange(list, f, fArr);
        return KeylineState.lerp((KeylineState) list.get((int) stateStepsRange[1]), (KeylineState) list.get((int) stateStepsRange[2]), stateStepsRange[0]);
    }

    private static float[] getStateStepsRange(List list, float f, float[] fArr) {
        int size = list.size();
        float f2 = fArr[0];
        int i = 1;
        while (i < size) {
            float f3 = fArr[i];
            if (f <= f3) {
                return new float[]{AnimationUtils.lerp(0.0f, 1.0f, f2, f3, f), (float) (i - 1), (float) i};
            }
            i++;
            f2 = f3;
        }
        return new float[]{0.0f, 0.0f, 0.0f};
    }

    private KeylineState closestStateStepFromInterpolation(List list, float f, float[] fArr) {
        float[] stateStepsRange = getStateStepsRange(list, f, fArr);
        if (stateStepsRange[0] >= 0.5f) {
            return (KeylineState) list.get((int) stateStepsRange[2]);
        }
        return (KeylineState) list.get((int) stateStepsRange[1]);
    }

    private static float[] getStateStepInterpolationPoints(float f, List list, boolean z) {
        float f2;
        float f3;
        int size = list.size();
        float[] fArr = new float[size];
        for (int i = 1; i < size; i++) {
            int i2 = i - 1;
            KeylineState keylineState = (KeylineState) list.get(i2);
            KeylineState keylineState2 = (KeylineState) list.get(i);
            if (z) {
                f2 = keylineState2.getFirstKeyline().loc - keylineState.getFirstKeyline().loc;
            } else {
                f2 = keylineState.getLastKeyline().loc - keylineState2.getLastKeyline().loc;
            }
            float f4 = f2 / f;
            if (i == size - 1) {
                f3 = 1.0f;
            } else {
                f3 = fArr[i2] + f4;
            }
            fArr[i] = f3;
        }
        return fArr;
    }

    private static boolean isFirstFocalItemAtLeftOfContainer(KeylineState keylineState) {
        return keylineState.getFirstFocalKeyline().locOffset - (keylineState.getFirstFocalKeyline().maskedItemSize / 2.0f) >= 0.0f && keylineState.getFirstFocalKeyline() == keylineState.getFirstNonAnchorKeyline();
    }

    private static boolean isLastFocalItemVisibleAtRightOfContainer(Carousel carousel, KeylineState keylineState) {
        int containerHeight = carousel.getContainerHeight();
        if (carousel.isHorizontal()) {
            containerHeight = carousel.getContainerWidth();
        }
        return keylineState.getLastFocalKeyline().locOffset + (keylineState.getLastFocalKeyline().maskedItemSize / 2.0f) <= ((float) containerHeight) && keylineState.getLastFocalKeyline() == keylineState.getLastNonAnchorKeyline();
    }

    /* renamed from: com.google.android.material.carousel.KeylineStateList$1  reason: invalid class name */
    abstract /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$android$material$carousel$CarouselStrategy$StrategyType;

        static {
            int[] iArr = new int[CarouselStrategy.StrategyType.values().length];
            $SwitchMap$com$google$android$material$carousel$CarouselStrategy$StrategyType = iArr;
            try {
                iArr[CarouselStrategy.StrategyType.CONTAINED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    private static KeylineState shiftKeylineStateForPadding(KeylineState keylineState, float f, int i, boolean z, float f2, CarouselStrategy.StrategyType strategyType) {
        if (AnonymousClass1.$SwitchMap$com$google$android$material$carousel$CarouselStrategy$StrategyType[strategyType.ordinal()] != 1) {
            return shiftKeylineStateForPaddingUncontained(keylineState, f, i, z);
        }
        return shiftKeylineStateForPaddingContained(keylineState, f, i, z, f2);
    }

    private static KeylineState shiftKeylineStateForPaddingUncontained(KeylineState keylineState, float f, int i, boolean z) {
        int i2;
        float f2;
        int i3 = i;
        ArrayList arrayList = new ArrayList(keylineState.getKeylines());
        KeylineState.Builder builder = new KeylineState.Builder(keylineState.getItemSize(), i3);
        boolean z2 = true;
        if (z) {
            i2 = 0;
        } else {
            i2 = arrayList.size() - 1;
        }
        int i4 = 0;
        while (i4 < arrayList.size()) {
            KeylineState.Keyline keyline = (KeylineState.Keyline) arrayList.get(i4);
            if (!keyline.isAnchor || i4 != i2) {
                float f3 = keyline.locOffset;
                float f4 = z ? f3 + f : f3 - f;
                float f5 = z ? f : 0.0f;
                float f6 = z ? 0.0f : f;
                boolean z3 = (i4 < keylineState.getFirstFocalKeylineIndex() || i4 > keylineState.getLastFocalKeylineIndex()) ? false : z2;
                float f7 = f4;
                float f8 = keyline.mask;
                float f9 = keyline.maskedItemSize;
                boolean z4 = keyline.isAnchor;
                if (z) {
                    f2 = Math.max(0.0f, ((f9 / 2.0f) + f7) - ((float) i3));
                } else {
                    f2 = Math.min(0.0f, f7 - (f9 / 2.0f));
                }
                builder.addKeyline(f7, f8, f9, z3, z4, Math.abs(f2), f5, f6);
            } else {
                builder.addKeyline(keyline.locOffset, keyline.mask, keyline.maskedItemSize, false, true, keyline.cutoff);
            }
            i4++;
            z2 = true;
        }
        return builder.build();
    }

    private static KeylineState shiftKeylineStateForPaddingContained(KeylineState keylineState, float f, int i, boolean z, float f2) {
        ArrayList arrayList = new ArrayList(keylineState.getKeylines());
        KeylineState.Builder builder = new KeylineState.Builder(keylineState.getItemSize(), i);
        float numberOfNonAnchorKeylines = f / ((float) keylineState.getNumberOfNonAnchorKeylines());
        float f3 = z ? f : 0.0f;
        int i2 = 0;
        while (i2 < arrayList.size()) {
            KeylineState.Keyline keyline = (KeylineState.Keyline) arrayList.get(i2);
            if (keyline.isAnchor) {
                builder.addKeyline(keyline.locOffset, keyline.mask, keyline.maskedItemSize, false, true, keyline.cutoff);
                float f4 = f2;
            } else {
                boolean z2 = i2 >= keylineState.getFirstFocalKeylineIndex() && i2 <= keylineState.getLastFocalKeylineIndex();
                float f5 = keyline.maskedItemSize - numberOfNonAnchorKeylines;
                float childMaskPercentage = CarouselStrategy.getChildMaskPercentage(f5, keylineState.getItemSize(), f2);
                float f6 = (f5 / 2.0f) + f3;
                float abs = Math.abs(f6 - keyline.locOffset);
                builder.addKeyline(f6, childMaskPercentage, f5, z2, false, keyline.cutoff, z ? abs : 0.0f, z ? 0.0f : abs);
                f3 += f5;
            }
            i2++;
        }
        return builder.build();
    }

    private static List getStateStepsStart(Carousel carousel, KeylineState keylineState, float f, float f2, CarouselStrategy.StrategyType strategyType) {
        KeylineState keylineState2 = keylineState;
        ArrayList arrayList = new ArrayList();
        arrayList.add(keylineState2);
        int findFirstNonAnchorKeylineIndex = findFirstNonAnchorKeylineIndex(keylineState2);
        int containerWidth = carousel.isHorizontal() ? carousel.getContainerWidth() : carousel.getContainerHeight();
        if (!isFirstFocalItemAtLeftOfContainer(keylineState2) && findFirstNonAnchorKeylineIndex != -1) {
            int firstFocalKeylineIndex = keylineState2.getFirstFocalKeylineIndex() - findFirstNonAnchorKeylineIndex;
            float f3 = keylineState2.getFirstKeyline().locOffset - (keylineState2.getFirstKeyline().maskedItemSize / 2.0f);
            if (firstFocalKeylineIndex > 0 || keylineState2.getFirstFocalKeyline().cutoff <= 0.0f) {
                float f4 = 0.0f;
                for (int i = 0; i < firstFocalKeylineIndex; i++) {
                    KeylineState keylineState3 = (KeylineState) arrayList.get(arrayList.size() - 1);
                    int i2 = findFirstNonAnchorKeylineIndex + i;
                    int size = keylineState2.getKeylines().size() - 1;
                    f4 += ((KeylineState.Keyline) keylineState2.getKeylines().get(i2)).cutoff;
                    int i3 = i2 - 1;
                    if (i3 >= 0) {
                        size = findFirstIndexAfterLastFocalKeylineWithMask(keylineState3, ((KeylineState.Keyline) keylineState2.getKeylines().get(i3)).mask) - 1;
                    }
                    int i4 = containerWidth;
                    KeylineState moveKeylineAndCreateKeylineState = moveKeylineAndCreateKeylineState(keylineState3, findFirstNonAnchorKeylineIndex, size, f3 + f4, (keylineState2.getFirstFocalKeylineIndex() - i) - 1, (keylineState2.getLastFocalKeylineIndex() - i) - 1, i4);
                    containerWidth = i4;
                    if (i == firstFocalKeylineIndex - 1 && f2 > 0.0f) {
                        moveKeylineAndCreateKeylineState = shiftKeylineStateForPadding(moveKeylineAndCreateKeylineState, f2, containerWidth, true, f, strategyType);
                    }
                    arrayList.add(moveKeylineAndCreateKeylineState);
                }
            } else {
                arrayList.add(shiftKeylinesAndCreateKeylineState(keylineState2, f3 + keylineState2.getFirstFocalKeyline().cutoff + f2, containerWidth));
                return arrayList;
            }
        } else if (f2 > 0.0f) {
            arrayList.add(shiftKeylineStateForPadding(keylineState2, f2, containerWidth, true, f, strategyType));
        }
        return arrayList;
    }

    private static List getStateStepsEnd(Carousel carousel, KeylineState keylineState, float f, float f2, CarouselStrategy.StrategyType strategyType) {
        KeylineState keylineState2 = keylineState;
        ArrayList arrayList = new ArrayList();
        arrayList.add(keylineState2);
        int findLastNonAnchorKeylineIndex = findLastNonAnchorKeylineIndex(keylineState2);
        int containerWidth = carousel.isHorizontal() ? carousel.getContainerWidth() : carousel.getContainerHeight();
        if (isLastFocalItemVisibleAtRightOfContainer(carousel, keylineState) || findLastNonAnchorKeylineIndex == -1) {
            int i = containerWidth;
            if (f2 > 0.0f) {
                arrayList.add(shiftKeylineStateForPadding(keylineState2, f2, i, false, f, strategyType));
            }
        } else {
            int lastFocalKeylineIndex = findLastNonAnchorKeylineIndex - keylineState2.getLastFocalKeylineIndex();
            float f3 = keylineState2.getFirstKeyline().locOffset - (keylineState2.getFirstKeyline().maskedItemSize / 2.0f);
            if (lastFocalKeylineIndex > 0 || keylineState2.getLastFocalKeyline().cutoff <= 0.0f) {
                float f4 = 0.0f;
                int i2 = 0;
                while (i2 < lastFocalKeylineIndex) {
                    KeylineState keylineState3 = (KeylineState) arrayList.get(arrayList.size() - 1);
                    int i3 = findLastNonAnchorKeylineIndex - i2;
                    float f5 = f4 + ((KeylineState.Keyline) keylineState2.getKeylines().get(i3)).cutoff;
                    int i4 = i3 + 1;
                    int i5 = containerWidth;
                    KeylineState moveKeylineAndCreateKeylineState = moveKeylineAndCreateKeylineState(keylineState3, findLastNonAnchorKeylineIndex, i4 < keylineState2.getKeylines().size() ? findLastIndexBeforeFirstFocalKeylineWithMask(keylineState3, ((KeylineState.Keyline) keylineState2.getKeylines().get(i4)).mask) + 1 : 0, f3 - f5, keylineState2.getFirstFocalKeylineIndex() + i2 + 1, keylineState2.getLastFocalKeylineIndex() + i2 + 1, i5);
                    if (i2 == lastFocalKeylineIndex - 1 && f2 > 0.0f) {
                        int i6 = i5;
                        moveKeylineAndCreateKeylineState = shiftKeylineStateForPadding(moveKeylineAndCreateKeylineState, f2, i6, false, f, strategyType);
                        i5 = i6;
                    }
                    arrayList.add(moveKeylineAndCreateKeylineState);
                    i2++;
                    containerWidth = i5;
                    f4 = f5;
                }
            } else {
                arrayList.add(shiftKeylinesAndCreateKeylineState(keylineState2, (f3 - keylineState2.getLastFocalKeyline().cutoff) - f2, containerWidth));
                return arrayList;
            }
        }
        return arrayList;
    }

    private static KeylineState shiftKeylinesAndCreateKeylineState(KeylineState keylineState, float f, int i) {
        return moveKeylineAndCreateKeylineState(keylineState, 0, 0, f, keylineState.getFirstFocalKeylineIndex(), keylineState.getLastFocalKeylineIndex(), i);
    }

    private static KeylineState moveKeylineAndCreateKeylineState(KeylineState keylineState, int i, int i2, float f, int i3, int i4, int i5) {
        ArrayList arrayList = new ArrayList(keylineState.getKeylines());
        arrayList.add(i2, (KeylineState.Keyline) arrayList.remove(i));
        KeylineState.Builder builder = new KeylineState.Builder(keylineState.getItemSize(), i5);
        int i6 = 0;
        while (i6 < arrayList.size()) {
            KeylineState.Keyline keyline = (KeylineState.Keyline) arrayList.get(i6);
            float f2 = keyline.maskedItemSize;
            builder.addKeyline(f + (f2 / 2.0f), keyline.mask, f2, i6 >= i3 && i6 <= i4, keyline.isAnchor, keyline.cutoff);
            f += keyline.maskedItemSize;
            i6++;
        }
        return builder.build();
    }

    private static int findFirstIndexAfterLastFocalKeylineWithMask(KeylineState keylineState, float f) {
        for (int lastFocalKeylineIndex = keylineState.getLastFocalKeylineIndex(); lastFocalKeylineIndex < keylineState.getKeylines().size(); lastFocalKeylineIndex++) {
            if (f == ((KeylineState.Keyline) keylineState.getKeylines().get(lastFocalKeylineIndex)).mask) {
                return lastFocalKeylineIndex;
            }
        }
        return keylineState.getKeylines().size() - 1;
    }

    private static int findLastIndexBeforeFirstFocalKeylineWithMask(KeylineState keylineState, float f) {
        for (int firstFocalKeylineIndex = keylineState.getFirstFocalKeylineIndex() - 1; firstFocalKeylineIndex >= 0; firstFocalKeylineIndex--) {
            if (f == ((KeylineState.Keyline) keylineState.getKeylines().get(firstFocalKeylineIndex)).mask) {
                return firstFocalKeylineIndex;
            }
        }
        return 0;
    }

    private static int findFirstNonAnchorKeylineIndex(KeylineState keylineState) {
        for (int i = 0; i < keylineState.getKeylines().size(); i++) {
            if (!((KeylineState.Keyline) keylineState.getKeylines().get(i)).isAnchor) {
                return i;
            }
        }
        return -1;
    }

    private static int findLastNonAnchorKeylineIndex(KeylineState keylineState) {
        for (int size = keylineState.getKeylines().size() - 1; size >= 0; size--) {
            if (!((KeylineState.Keyline) keylineState.getKeylines().get(size)).isAnchor) {
                return size;
            }
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public Map getKeylineStateForPositionMap(int i, int i2, int i3, boolean z) {
        float itemSize = this.defaultState.getItemSize();
        HashMap hashMap = new HashMap();
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int i6 = -1;
            if (i4 >= i) {
                break;
            }
            int i7 = z ? (i - i4) - 1 : i4;
            float f = ((float) i7) * itemSize;
            if (!z) {
                i6 = 1;
            }
            if (f * ((float) i6) > ((float) i3) - this.endShiftRange || i4 >= i - this.endStateSteps.size()) {
                Integer valueOf = Integer.valueOf(i7);
                List list = this.endStateSteps;
                hashMap.put(valueOf, (KeylineState) list.get(MathUtils.clamp(i5, 0, list.size() - 1)));
                i5++;
            }
            i4++;
        }
        int i8 = 0;
        for (int i9 = i - 1; i9 >= 0; i9--) {
            int i10 = z ? (i - i9) - 1 : i9;
            if (((float) i10) * itemSize * ((float) (z ? -1 : 1)) < ((float) i2) + this.startShiftRange || i9 < this.startStateSteps.size()) {
                Integer valueOf2 = Integer.valueOf(i10);
                List list2 = this.startStateSteps;
                hashMap.put(valueOf2, (KeylineState) list2.get(MathUtils.clamp(i8, 0, list2.size() - 1)));
                i8++;
            }
        }
        return hashMap;
    }
}
