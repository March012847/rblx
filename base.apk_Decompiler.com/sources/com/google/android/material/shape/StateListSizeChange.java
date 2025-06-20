package com.google.android.material.shape;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import com.google.android.material.R$attr;
import com.google.android.material.R$styleable;
import org.xmlpull.v1.XmlPullParser;

public class StateListSizeChange {
    private SizeChange defaultSizeChange;
    SizeChange[] sizeChanges = new SizeChange[10];
    int stateCount;
    int[][] stateSpecs = new int[10][];

    public enum SizeChangeType {
        PERCENT,
        PIXELS
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0038 A[Catch:{ all -> 0x004c, all -> 0x0060, NotFoundException | IOException | XmlPullParserException -> 0x0065 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0052 A[SYNTHETIC, Splitter:B:23:0x0052] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.material.shape.StateListSizeChange create(android.content.Context r5, android.content.res.TypedArray r6, int r7) {
        /*
            r0 = 0
            int r6 = r6.getResourceId(r7, r0)
            r7 = 0
            if (r6 != 0) goto L_0x0009
            return r7
        L_0x0009:
            android.content.res.Resources r0 = r5.getResources()
            java.lang.String r0 = r0.getResourceTypeName(r6)
            java.lang.String r1 = "xml"
            boolean r0 = r0.equals(r1)
            if (r0 != 0) goto L_0x001a
            return r7
        L_0x001a:
            android.content.res.Resources r0 = r5.getResources()     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x0065 }
            android.content.res.XmlResourceParser r6 = r0.getXml(r6)     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x0065 }
            com.google.android.material.shape.StateListSizeChange r0 = new com.google.android.material.shape.StateListSizeChange     // Catch:{ all -> 0x004c }
            r0.<init>()     // Catch:{ all -> 0x004c }
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r6)     // Catch:{ all -> 0x004c }
        L_0x002b:
            int r2 = r6.next()     // Catch:{ all -> 0x004c }
            r3 = 2
            if (r2 == r3) goto L_0x0036
            r4 = 1
            if (r2 == r4) goto L_0x0036
            goto L_0x002b
        L_0x0036:
            if (r2 != r3) goto L_0x0052
            java.lang.String r2 = r6.getName()     // Catch:{ all -> 0x004c }
            java.lang.String r3 = "selector"
            boolean r2 = r2.equals(r3)     // Catch:{ all -> 0x004c }
            if (r2 == 0) goto L_0x004e
            android.content.res.Resources$Theme r2 = r5.getTheme()     // Catch:{ all -> 0x004c }
            r0.loadSizeChangeFromItems(r5, r6, r1, r2)     // Catch:{ all -> 0x004c }
            goto L_0x004e
        L_0x004c:
            r5 = move-exception
            goto L_0x005a
        L_0x004e:
            r6.close()     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x0065 }
            return r0
        L_0x0052:
            org.xmlpull.v1.XmlPullParserException r5 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ all -> 0x004c }
            java.lang.String r0 = "No start tag found"
            r5.<init>(r0)     // Catch:{ all -> 0x004c }
            throw r5     // Catch:{ all -> 0x004c }
        L_0x005a:
            if (r6 == 0) goto L_0x0064
            r6.close()     // Catch:{ all -> 0x0060 }
            goto L_0x0064
        L_0x0060:
            r6 = move-exception
            r5.addSuppressed(r6)     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x0065 }
        L_0x0064:
            throw r5     // Catch:{ NotFoundException | IOException | XmlPullParserException -> 0x0065 }
        L_0x0065:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.shape.StateListSizeChange.create(android.content.Context, android.content.res.TypedArray, int):com.google.android.material.shape.StateListSizeChange");
    }

    public SizeChange getSizeChangeForState(int[] iArr) {
        int indexOfStateSet = indexOfStateSet(iArr);
        if (indexOfStateSet < 0) {
            indexOfStateSet = indexOfStateSet(StateSet.WILD_CARD);
        }
        return indexOfStateSet < 0 ? this.defaultSizeChange : this.sizeChanges[indexOfStateSet];
    }

    public int getMaxWidthChange(int i) {
        float max;
        int i2 = -i;
        for (int i3 = 0; i3 < this.stateCount; i3++) {
            SizeChangeAmount sizeChangeAmount = this.sizeChanges[i3].widthChange;
            SizeChangeType sizeChangeType = sizeChangeAmount.type;
            if (sizeChangeType == SizeChangeType.PIXELS) {
                max = Math.max((float) i2, sizeChangeAmount.amount);
            } else if (sizeChangeType == SizeChangeType.PERCENT) {
                max = Math.max((float) i2, ((float) i) * sizeChangeAmount.amount);
            }
            i2 = (int) max;
        }
        return i2;
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

    private void loadSizeChangeFromItems(Context context, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
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
                        typedArray = resources.obtainAttributes(attributeSet, R$styleable.StateListSizeChange);
                    } else {
                        typedArray = theme.obtainStyledAttributes(attributeSet, R$styleable.StateListSizeChange, 0, 0);
                    }
                    SizeChangeAmount sizeChangeAmount = getSizeChangeAmount(typedArray, R$styleable.StateListSizeChange_widthChange, (SizeChangeAmount) null);
                    typedArray.recycle();
                    int attributeCount = attributeSet.getAttributeCount();
                    int[] iArr = new int[attributeCount];
                    int i = 0;
                    for (int i2 = 0; i2 < attributeCount; i2++) {
                        int attributeNameResource = attributeSet.getAttributeNameResource(i2);
                        if (attributeNameResource != R$attr.widthChange) {
                            int i3 = i + 1;
                            if (!attributeSet.getAttributeBooleanValue(i2, false)) {
                                attributeNameResource = -attributeNameResource;
                            }
                            iArr[i] = attributeNameResource;
                            i = i3;
                        }
                    }
                    addStateSizeChange(StateSet.trimStateSet(iArr, i), new SizeChange(sizeChangeAmount));
                }
            } else {
                return;
            }
        }
    }

    private SizeChangeAmount getSizeChangeAmount(TypedArray typedArray, int i, SizeChangeAmount sizeChangeAmount) {
        TypedValue peekValue = typedArray.peekValue(i);
        if (peekValue != null) {
            int i2 = peekValue.type;
            if (i2 == 5) {
                return new SizeChangeAmount(SizeChangeType.PIXELS, (float) TypedValue.complexToDimensionPixelSize(peekValue.data, typedArray.getResources().getDisplayMetrics()));
            }
            if (i2 == 6) {
                return new SizeChangeAmount(SizeChangeType.PERCENT, peekValue.getFraction(1.0f, 1.0f));
            }
        }
        return sizeChangeAmount;
    }

    private void addStateSizeChange(int[] iArr, SizeChange sizeChange) {
        int i = this.stateCount;
        if (i == 0 || iArr.length == 0) {
            this.defaultSizeChange = sizeChange;
        }
        if (i >= this.stateSpecs.length) {
            growArray(i, i + 10);
        }
        int[][] iArr2 = this.stateSpecs;
        int i2 = this.stateCount;
        iArr2[i2] = iArr;
        this.sizeChanges[i2] = sizeChange;
        this.stateCount = i2 + 1;
    }

    private void growArray(int i, int i2) {
        int[][] iArr = new int[i2][];
        System.arraycopy(this.stateSpecs, 0, iArr, 0, i);
        this.stateSpecs = iArr;
        SizeChange[] sizeChangeArr = new SizeChange[i2];
        System.arraycopy(this.sizeChanges, 0, sizeChangeArr, 0, i);
        this.sizeChanges = sizeChangeArr;
    }

    public class SizeChange {
        public SizeChangeAmount widthChange;

        SizeChange(SizeChangeAmount sizeChangeAmount) {
            this.widthChange = sizeChangeAmount;
        }
    }

    public class SizeChangeAmount {
        float amount;
        SizeChangeType type;

        SizeChangeAmount(SizeChangeType sizeChangeType, float f) {
            this.type = sizeChangeType;
            this.amount = f;
        }

        public int getChange(int i) {
            float f;
            SizeChangeType sizeChangeType = this.type;
            if (sizeChangeType == SizeChangeType.PERCENT) {
                f = this.amount * ((float) i);
            } else if (sizeChangeType != SizeChangeType.PIXELS) {
                return 0;
            } else {
                f = this.amount;
            }
            return (int) f;
        }
    }
}
