package com.google.android.material.shape;

public class CutCornerTreatment extends CornerTreatment {
    float size = -1.0f;

    public void getCornerPath(ShapePath shapePath, float f, float f2, float f3) {
        float f4 = f3 * f2;
        shapePath.reset(0.0f, f4, 180.0f, 180.0f - f);
        double d = (double) f4;
        shapePath.lineTo((float) (Math.sin(Math.toRadians((double) f)) * d), (float) (Math.sin(Math.toRadians((double) (90.0f - f))) * d));
    }
}
