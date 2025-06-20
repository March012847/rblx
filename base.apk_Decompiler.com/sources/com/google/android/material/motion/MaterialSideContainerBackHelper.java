package com.google.android.material.motion;

import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.R$dimen;
import com.google.android.material.animation.AnimationUtils;

public class MaterialSideContainerBackHelper extends MaterialBackAnimationHelper {
    private final float maxScaleXDistanceGrow;
    private final float maxScaleXDistanceShrink;
    private final float maxScaleYDistance;

    public MaterialSideContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.maxScaleXDistanceShrink = resources.getDimension(R$dimen.m3_back_progress_side_container_max_scale_x_distance_shrink);
        this.maxScaleXDistanceGrow = resources.getDimension(R$dimen.m3_back_progress_side_container_max_scale_x_distance_grow);
        this.maxScaleYDistance = resources.getDimension(R$dimen.m3_back_progress_side_container_max_scale_y_distance);
    }

    public void updateBackProgress(float f, boolean z, int i) {
        int i2;
        float interpolateProgress = interpolateProgress(f);
        boolean checkAbsoluteGravity = checkAbsoluteGravity(i, 3);
        boolean z2 = z == checkAbsoluteGravity;
        int width = this.view.getWidth();
        int height = this.view.getHeight();
        float f2 = (float) width;
        if (f2 > 0.0f) {
            float f3 = (float) height;
            if (f3 > 0.0f) {
                float f4 = this.maxScaleXDistanceShrink / f2;
                float f5 = this.maxScaleXDistanceGrow / f2;
                float f6 = this.maxScaleYDistance / f3;
                View view = this.view;
                if (checkAbsoluteGravity) {
                    f2 = 0.0f;
                }
                view.setPivotX(f2);
                if (!z2) {
                    f5 = -f4;
                }
                float lerp = AnimationUtils.lerp(0.0f, f5, interpolateProgress);
                float f7 = lerp + 1.0f;
                float lerp2 = 1.0f - AnimationUtils.lerp(0.0f, f6, interpolateProgress);
                if (!Float.isNaN(f7) && !Float.isNaN(lerp2)) {
                    this.view.setScaleX(f7);
                    this.view.setScaleY(lerp2);
                    View view2 = this.view;
                    if (view2 instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) view2;
                        for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                            View childAt = viewGroup.getChildAt(i3);
                            if (checkAbsoluteGravity) {
                                i2 = (width - childAt.getRight()) + childAt.getWidth();
                            } else {
                                i2 = -childAt.getLeft();
                            }
                            childAt.setPivotX((float) i2);
                            childAt.setPivotY((float) (-childAt.getTop()));
                            float f8 = z2 ? 1.0f - lerp : 1.0f;
                            float f9 = lerp2 != 0.0f ? (f7 / lerp2) * f8 : 1.0f;
                            if (!Float.isNaN(f8) && !Float.isNaN(f9)) {
                                childAt.setScaleX(f8);
                                childAt.setScaleY(f9);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkAbsoluteGravity(int i, int i2) {
        return (Gravity.getAbsoluteGravity(i, this.view.getLayoutDirection()) & i2) == i2;
    }
}
