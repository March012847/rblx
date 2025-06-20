package com.google.android.material.motion;

import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.R$dimen;
import com.google.android.material.animation.AnimationUtils;

public class MaterialBottomContainerBackHelper extends MaterialBackAnimationHelper {
    private final float maxScaleXDistance;
    private final float maxScaleYDistance;

    public MaterialBottomContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.maxScaleXDistance = resources.getDimension(R$dimen.m3_back_progress_bottom_container_max_scale_x_distance);
        this.maxScaleYDistance = resources.getDimension(R$dimen.m3_back_progress_bottom_container_max_scale_y_distance);
    }

    public void updateBackProgress(float f) {
        float interpolateProgress = interpolateProgress(f);
        float width = (float) this.view.getWidth();
        float height = (float) this.view.getHeight();
        if (width > 0.0f && height > 0.0f) {
            float lerp = 1.0f - AnimationUtils.lerp(0.0f, this.maxScaleXDistance / width, interpolateProgress);
            float lerp2 = 1.0f - AnimationUtils.lerp(0.0f, this.maxScaleYDistance / height, interpolateProgress);
            if (!Float.isNaN(lerp) && !Float.isNaN(lerp2)) {
                this.view.setScaleX(lerp);
                this.view.setPivotY(height);
                this.view.setScaleY(lerp2);
                View view = this.view;
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        View childAt = viewGroup.getChildAt(i);
                        childAt.setPivotY((float) (-childAt.getTop()));
                        childAt.setScaleY(lerp2 != 0.0f ? lerp / lerp2 : 1.0f);
                    }
                }
            }
        }
    }
}
