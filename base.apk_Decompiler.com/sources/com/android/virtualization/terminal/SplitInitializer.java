package com.android.virtualization.terminal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.startup.Initializer;
import androidx.window.embedding.ActivityFilter;
import androidx.window.embedding.EmbeddingAspectRatio;
import androidx.window.embedding.RuleController;
import androidx.window.embedding.SplitAttributes;
import androidx.window.embedding.SplitPairFilter;
import androidx.window.embedding.SplitPairRule;
import androidx.window.embedding.SplitPlaceholderRule;
import androidx.window.embedding.SplitRule;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;

/* compiled from: SplitInitializer.kt */
public final class SplitInitializer implements Initializer {
    public RuleController create(Context context) {
        context.getClass();
        Class<SettingsActivity> cls = SettingsActivity.class;
        Set mutableSetOf = SetsKt.mutableSetOf(new SplitPairFilter(new ComponentName(context, cls), new ComponentName(context, SettingsPortForwardingActivity.class), (String) null));
        mutableSetOf.add(new SplitPairFilter(new ComponentName(context, cls), new ComponentName(context, SettingsRecoveryActivity.class), (String) null));
        SplitPairRule.Builder clearTop = new SplitPairRule.Builder(mutableSetOf).setClearTop(true);
        SplitRule.FinishBehavior finishBehavior = SplitRule.FinishBehavior.ADJACENT;
        SplitPairRule.Builder finishSecondaryWithPrimary = clearTop.setFinishPrimaryWithSecondary(finishBehavior).setFinishSecondaryWithPrimary(SplitRule.FinishBehavior.ALWAYS);
        SplitAttributes.Builder builder = new SplitAttributes.Builder();
        SplitAttributes.LayoutDirection layoutDirection = SplitAttributes.LayoutDirection.LOCALE;
        SplitAttributes.Builder layoutDirection2 = builder.setLayoutDirection(layoutDirection);
        SplitAttributes.SplitType.Companion companion = SplitAttributes.SplitType.Companion;
        SplitPairRule.Builder defaultSplitAttributes = finishSecondaryWithPrimary.setDefaultSplitAttributes(layoutDirection2.setSplitType(companion.ratio(context.getResources().getFloat(2131099729))).build());
        EmbeddingAspectRatio embeddingAspectRatio = EmbeddingAspectRatio.ALWAYS_ALLOW;
        SplitPairRule build = defaultSplitAttributes.setMaxAspectRatioInPortrait(embeddingAspectRatio).setMinWidthDp(context.getResources().getInteger(2131296329)).build();
        SplitPlaceholderRule build2 = new SplitPlaceholderRule.Builder(SetsKt.setOf(new ActivityFilter(new ComponentName(context, cls), (String) null)), new Intent(context, SettingsDiskResizeActivity.class)).setFinishPrimaryWithPlaceholder(finishBehavior).setDefaultSplitAttributes(new SplitAttributes.Builder().setLayoutDirection(layoutDirection).setSplitType(companion.ratio(context.getResources().getFloat(2131099729))).build()).setMaxAspectRatioInPortrait(embeddingAspectRatio).setMinWidthDp(context.getResources().getInteger(2131296329)).setSticky(false).build();
        RuleController instance = RuleController.Companion.getInstance(context);
        instance.addRule(build);
        instance.addRule(build2);
        return instance;
    }

    public List dependencies() {
        return CollectionsKt.emptyList();
    }
}
