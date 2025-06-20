package com.google.android.material.shape;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.StateSet;
import com.google.android.material.R$attr;
import com.google.android.material.R$styleable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;

public class StateListShapeAppearanceModel {
    final StateListCornerSize bottomLeftCornerSizeOverride;
    final StateListCornerSize bottomRightCornerSizeOverride;
    final ShapeAppearanceModel defaultShape;
    final ShapeAppearanceModel[] shapeAppearanceModels;
    final int stateCount;
    final int[][] stateSpecs;
    final StateListCornerSize topLeftCornerSizeOverride;
    final StateListCornerSize topRightCornerSizeOverride;

    public static int swapCornerPositionRtl(int i) {
        int i2 = i & 5;
        return ((i & 10) >> 1) | (i2 << 1);
    }

    public final class Builder {
        /* access modifiers changed from: private */
        public StateListCornerSize bottomLeftCornerSizeOverride;
        /* access modifiers changed from: private */
        public StateListCornerSize bottomRightCornerSizeOverride;
        /* access modifiers changed from: private */
        public ShapeAppearanceModel defaultShape;
        /* access modifiers changed from: private */
        public ShapeAppearanceModel[] shapeAppearanceModels;
        /* access modifiers changed from: private */
        public int stateCount;
        /* access modifiers changed from: private */
        public int[][] stateSpecs;
        /* access modifiers changed from: private */
        public StateListCornerSize topLeftCornerSizeOverride;
        /* access modifiers changed from: private */
        public StateListCornerSize topRightCornerSizeOverride;

        private boolean containsFlag(int i, int i2) {
            return (i | i2) == i;
        }

        public Builder(StateListShapeAppearanceModel stateListShapeAppearanceModel) {
            int i = stateListShapeAppearanceModel.stateCount;
            this.stateCount = i;
            this.defaultShape = stateListShapeAppearanceModel.defaultShape;
            int[][] iArr = stateListShapeAppearanceModel.stateSpecs;
            int[][] iArr2 = new int[iArr.length][];
            this.stateSpecs = iArr2;
            this.shapeAppearanceModels = new ShapeAppearanceModel[stateListShapeAppearanceModel.shapeAppearanceModels.length];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            System.arraycopy(stateListShapeAppearanceModel.shapeAppearanceModels, 0, this.shapeAppearanceModels, 0, this.stateCount);
            this.topLeftCornerSizeOverride = stateListShapeAppearanceModel.topLeftCornerSizeOverride;
            this.topRightCornerSizeOverride = stateListShapeAppearanceModel.topRightCornerSizeOverride;
            this.bottomLeftCornerSizeOverride = stateListShapeAppearanceModel.bottomLeftCornerSizeOverride;
            this.bottomRightCornerSizeOverride = stateListShapeAppearanceModel.bottomRightCornerSizeOverride;
        }

        public Builder(ShapeAppearanceModel shapeAppearanceModel) {
            initialize();
            addStateShapeAppearanceModel(StateSet.WILD_CARD, shapeAppearanceModel);
        }

        /* JADX WARNING: Removed duplicated region for block: B:11:0x001f A[Catch:{ all -> 0x0033, all -> 0x0047, NotFoundException | IOException | XmlPullParserException -> 0x004c }] */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x0039 A[SYNTHETIC, Splitter:B:18:0x0039] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private Builder(android.content.Context r5, int r6) {
            /*
                r4 = this;
                r4.<init>()
                r4.initialize()
                android.content.res.Resources r0 = r5.getResources()     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x004c }
                android.content.res.XmlResourceParser r6 = r0.getXml(r6)     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x004c }
                android.util.AttributeSet r0 = android.util.Xml.asAttributeSet(r6)     // Catch:{ all -> 0x0033 }
            L_0x0012:
                int r1 = r6.next()     // Catch:{ all -> 0x0033 }
                r2 = 2
                if (r1 == r2) goto L_0x001d
                r3 = 1
                if (r1 == r3) goto L_0x001d
                goto L_0x0012
            L_0x001d:
                if (r1 != r2) goto L_0x0039
                java.lang.String r1 = r6.getName()     // Catch:{ all -> 0x0033 }
                java.lang.String r2 = "selector"
                boolean r1 = r1.equals(r2)     // Catch:{ all -> 0x0033 }
                if (r1 == 0) goto L_0x0035
                android.content.res.Resources$Theme r1 = r5.getTheme()     // Catch:{ all -> 0x0033 }
                com.google.android.material.shape.StateListShapeAppearanceModel.loadShapeAppearanceModelsFromItems(r4, r5, r6, r0, r1)     // Catch:{ all -> 0x0033 }
                goto L_0x0035
            L_0x0033:
                r5 = move-exception
                goto L_0x0041
            L_0x0035:
                r6.close()     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x004c }
                return
            L_0x0039:
                org.xmlpull.v1.XmlPullParserException r5 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ all -> 0x0033 }
                java.lang.String r0 = "No start tag found"
                r5.<init>(r0)     // Catch:{ all -> 0x0033 }
                throw r5     // Catch:{ all -> 0x0033 }
            L_0x0041:
                if (r6 == 0) goto L_0x004b
                r6.close()     // Catch:{ all -> 0x0047 }
                goto L_0x004b
            L_0x0047:
                r6 = move-exception
                r5.addSuppressed(r6)     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x004c }
            L_0x004b:
                throw r5     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x004c }
            L_0x004c:
                r4.initialize()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.shape.StateListShapeAppearanceModel.Builder.<init>(android.content.Context, int):void");
        }

        private void initialize() {
            this.defaultShape = new ShapeAppearanceModel();
            this.stateSpecs = new int[10][];
            this.shapeAppearanceModels = new ShapeAppearanceModel[10];
        }

        public Builder setCornerSizeOverride(StateListCornerSize stateListCornerSize, int i) {
            if (containsFlag(i, 1)) {
                this.topLeftCornerSizeOverride = stateListCornerSize;
            }
            if (containsFlag(i, 2)) {
                this.topRightCornerSizeOverride = stateListCornerSize;
            }
            if (containsFlag(i, 4)) {
                this.bottomLeftCornerSizeOverride = stateListCornerSize;
            }
            if (containsFlag(i, 8)) {
                this.bottomRightCornerSizeOverride = stateListCornerSize;
            }
            return this;
        }

        public Builder addStateShapeAppearanceModel(int[] iArr, ShapeAppearanceModel shapeAppearanceModel) {
            int i = this.stateCount;
            if (i == 0 || iArr.length == 0) {
                this.defaultShape = shapeAppearanceModel;
            }
            if (i >= this.stateSpecs.length) {
                growArray(i, i + 10);
            }
            int[][] iArr2 = this.stateSpecs;
            int i2 = this.stateCount;
            iArr2[i2] = iArr;
            this.shapeAppearanceModels[i2] = shapeAppearanceModel;
            this.stateCount = i2 + 1;
            return this;
        }

        private void growArray(int i, int i2) {
            int[][] iArr = new int[i2][];
            System.arraycopy(this.stateSpecs, 0, iArr, 0, i);
            this.stateSpecs = iArr;
            ShapeAppearanceModel[] shapeAppearanceModelArr = new ShapeAppearanceModel[i2];
            System.arraycopy(this.shapeAppearanceModels, 0, shapeAppearanceModelArr, 0, i);
            this.shapeAppearanceModels = shapeAppearanceModelArr;
        }

        public StateListShapeAppearanceModel build() {
            if (this.stateCount == 0) {
                return null;
            }
            return new StateListShapeAppearanceModel(this);
        }
    }

    public static StateListShapeAppearanceModel create(Context context, TypedArray typedArray, int i) {
        int resourceId = typedArray.getResourceId(i, 0);
        if (resourceId != 0 && Objects.equals(context.getResources().getResourceTypeName(resourceId), "xml")) {
            return new Builder(context, resourceId).build();
        }
        return null;
    }

    /* access modifiers changed from: private */
    public static void loadShapeAppearanceModelsFromItems(Builder builder, Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray typedArray;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                int depth2 = xmlPullParser.getDepth();
                if (depth2 < depth && next == 3) {
                    return;
                }
                if (next == 2 && depth2 <= depth && xmlPullParser.getName().equals("item")) {
                    Resources resources = context.getResources();
                    if (theme == null) {
                        typedArray = resources.obtainAttributes(attributeSet, R$styleable.MaterialShape);
                    } else {
                        typedArray = theme.obtainStyledAttributes(attributeSet, R$styleable.MaterialShape, 0, 0);
                    }
                    ShapeAppearanceModel build = ShapeAppearanceModel.builder(context, typedArray.getResourceId(R$styleable.MaterialShape_shapeAppearance, 0), typedArray.getResourceId(R$styleable.MaterialShape_shapeAppearanceOverlay, 0)).build();
                    typedArray.recycle();
                    int attributeCount = attributeSet.getAttributeCount();
                    int[] iArr = new int[attributeCount];
                    int i = 0;
                    for (int i2 = 0; i2 < attributeCount; i2++) {
                        int attributeNameResource = attributeSet.getAttributeNameResource(i2);
                        if (!(attributeNameResource == R$attr.shapeAppearance || attributeNameResource == R$attr.shapeAppearanceOverlay)) {
                            int i3 = i + 1;
                            if (!attributeSet.getAttributeBooleanValue(i2, false)) {
                                attributeNameResource = -attributeNameResource;
                            }
                            iArr[i] = attributeNameResource;
                            i = i3;
                        }
                    }
                    builder.addStateShapeAppearanceModel(StateSet.trimStateSet(iArr, i), build);
                }
            } else {
                return;
            }
        }
    }

    private StateListShapeAppearanceModel(Builder builder) {
        this.stateCount = builder.stateCount;
        this.defaultShape = builder.defaultShape;
        this.stateSpecs = builder.stateSpecs;
        this.shapeAppearanceModels = builder.shapeAppearanceModels;
        this.topLeftCornerSizeOverride = builder.topLeftCornerSizeOverride;
        this.topRightCornerSizeOverride = builder.topRightCornerSizeOverride;
        this.bottomLeftCornerSizeOverride = builder.bottomLeftCornerSizeOverride;
        this.bottomRightCornerSizeOverride = builder.bottomRightCornerSizeOverride;
    }

    public ShapeAppearanceModel getDefaultShape(boolean z) {
        if (!z || (this.topLeftCornerSizeOverride == null && this.topRightCornerSizeOverride == null && this.bottomLeftCornerSizeOverride == null && this.bottomRightCornerSizeOverride == null)) {
            return this.defaultShape;
        }
        ShapeAppearanceModel.Builder builder = this.defaultShape.toBuilder();
        StateListCornerSize stateListCornerSize = this.topLeftCornerSizeOverride;
        if (stateListCornerSize != null) {
            builder.setTopLeftCornerSize(stateListCornerSize.getDefaultCornerSize());
        }
        StateListCornerSize stateListCornerSize2 = this.topRightCornerSizeOverride;
        if (stateListCornerSize2 != null) {
            builder.setTopRightCornerSize(stateListCornerSize2.getDefaultCornerSize());
        }
        StateListCornerSize stateListCornerSize3 = this.bottomLeftCornerSizeOverride;
        if (stateListCornerSize3 != null) {
            builder.setBottomLeftCornerSize(stateListCornerSize3.getDefaultCornerSize());
        }
        StateListCornerSize stateListCornerSize4 = this.bottomRightCornerSizeOverride;
        if (stateListCornerSize4 != null) {
            builder.setBottomRightCornerSize(stateListCornerSize4.getDefaultCornerSize());
        }
        return builder.build();
    }

    /* access modifiers changed from: protected */
    public ShapeAppearanceModel getShapeForState(int[] iArr) {
        int indexOfStateSet = indexOfStateSet(iArr);
        if (indexOfStateSet < 0) {
            indexOfStateSet = indexOfStateSet(StateSet.WILD_CARD);
        }
        if (this.topLeftCornerSizeOverride == null && this.topRightCornerSizeOverride == null && this.bottomLeftCornerSizeOverride == null && this.bottomRightCornerSizeOverride == null) {
            return this.shapeAppearanceModels[indexOfStateSet];
        }
        ShapeAppearanceModel.Builder builder = this.shapeAppearanceModels[indexOfStateSet].toBuilder();
        StateListCornerSize stateListCornerSize = this.topLeftCornerSizeOverride;
        if (stateListCornerSize != null) {
            builder.setTopLeftCornerSize(stateListCornerSize.getCornerSizeForState(iArr));
        }
        StateListCornerSize stateListCornerSize2 = this.topRightCornerSizeOverride;
        if (stateListCornerSize2 != null) {
            builder.setTopRightCornerSize(stateListCornerSize2.getCornerSizeForState(iArr));
        }
        StateListCornerSize stateListCornerSize3 = this.bottomLeftCornerSizeOverride;
        if (stateListCornerSize3 != null) {
            builder.setBottomLeftCornerSize(stateListCornerSize3.getCornerSizeForState(iArr));
        }
        StateListCornerSize stateListCornerSize4 = this.bottomRightCornerSizeOverride;
        if (stateListCornerSize4 != null) {
            builder.setBottomRightCornerSize(stateListCornerSize4.getCornerSizeForState(iArr));
        }
        return builder.build();
    }

    private int indexOfStateSet(int[] iArr) {
        int[][] iArr2 = this.stateSpecs;
        for (int i = 0; i < this.stateCount; i++) {
            if (StateSet.stateSetMatches(iArr2[i], iArr)) {
                return i;
            }
        }
        return -1;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public boolean isStateful() {
        StateListCornerSize stateListCornerSize;
        StateListCornerSize stateListCornerSize2;
        StateListCornerSize stateListCornerSize3;
        StateListCornerSize stateListCornerSize4;
        if (this.stateCount > 1 || (((stateListCornerSize = this.topLeftCornerSizeOverride) != null && stateListCornerSize.isStateful()) || (((stateListCornerSize2 = this.topRightCornerSizeOverride) != null && stateListCornerSize2.isStateful()) || (((stateListCornerSize3 = this.bottomLeftCornerSizeOverride) != null && stateListCornerSize3.isStateful()) || ((stateListCornerSize4 = this.bottomRightCornerSizeOverride) != null && stateListCornerSize4.isStateful()))))) {
            return true;
        }
        return false;
    }
}
