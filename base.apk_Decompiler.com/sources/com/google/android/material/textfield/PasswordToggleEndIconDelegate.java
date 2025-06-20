package com.google.android.material.textfield;

import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.R$drawable;
import com.google.android.material.R$string;

class PasswordToggleEndIconDelegate extends EndIconDelegate {
    private EditText editText;
    private int iconResId = R$drawable.design_password_eye;
    private final View.OnClickListener onIconClickListener = new PasswordToggleEndIconDelegate$$ExternalSyntheticLambda0(this);

    /* access modifiers changed from: package-private */
    public boolean isIconCheckable() {
        return true;
    }

    public static /* synthetic */ void $r8$lambda$T4f7xxY_3Sk8DJHDU1zyNxpd1D4(PasswordToggleEndIconDelegate passwordToggleEndIconDelegate, View view) {
        EditText editText2 = passwordToggleEndIconDelegate.editText;
        if (editText2 != null) {
            int selectionEnd = editText2.getSelectionEnd();
            if (passwordToggleEndIconDelegate.hasPasswordTransformation()) {
                passwordToggleEndIconDelegate.editText.setTransformationMethod((TransformationMethod) null);
            } else {
                passwordToggleEndIconDelegate.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            if (selectionEnd >= 0) {
                passwordToggleEndIconDelegate.editText.setSelection(selectionEnd);
            }
            passwordToggleEndIconDelegate.refreshIconState();
        }
    }

    PasswordToggleEndIconDelegate(EndCompoundLayout endCompoundLayout, int i) {
        super(endCompoundLayout);
        if (i != 0) {
            this.iconResId = i;
        }
    }

    /* access modifiers changed from: package-private */
    public void setUp() {
        if (isInputTypePassword(this.editText)) {
            this.editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /* access modifiers changed from: package-private */
    public void tearDown() {
        EditText editText2 = this.editText;
        if (editText2 != null) {
            editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    /* access modifiers changed from: package-private */
    public int getIconDrawableResId() {
        return this.iconResId;
    }

    /* access modifiers changed from: package-private */
    public int getIconContentDescriptionResId() {
        return R$string.password_toggle_content_description;
    }

    /* access modifiers changed from: package-private */
    public boolean isIconChecked() {
        return !hasPasswordTransformation();
    }

    /* access modifiers changed from: package-private */
    public View.OnClickListener getOnIconClickListener() {
        return this.onIconClickListener;
    }

    /* access modifiers changed from: package-private */
    public void onEditTextAttached(EditText editText2) {
        this.editText = editText2;
        refreshIconState();
    }

    /* access modifiers changed from: package-private */
    public void beforeEditTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        refreshIconState();
    }

    private boolean hasPasswordTransformation() {
        EditText editText2 = this.editText;
        return editText2 != null && (editText2.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    private static boolean isInputTypePassword(EditText editText2) {
        if (editText2 != null) {
            return editText2.getInputType() == 16 || editText2.getInputType() == 128 || editText2.getInputType() == 144 || editText2.getInputType() == 224;
        }
        return false;
    }
}
