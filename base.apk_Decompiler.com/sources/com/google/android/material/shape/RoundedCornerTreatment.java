package com.google.android.material.shape;

public class RoundedCornerTreatment extends CornerTreatment {
    float radius = -1.0f;

    public void getCornerPath(ShapePath shapePath, float f, float f2, float f3) {
        float f4 = f3 * f2;
        shapePath.reset(0.0f, f4, 180.0f, 180.0f - f);
        float f5 = f4 * 2.0f;
        shapePath.addArc(0.0f, 0.0f, f5, f5, 180.0f, f);
    }
}
