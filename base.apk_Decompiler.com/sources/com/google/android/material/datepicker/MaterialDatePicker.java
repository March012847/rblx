package com.google.android.material.datepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.R$attr;
import com.google.android.material.R$dimen;
import com.google.android.material.R$drawable;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import com.google.android.material.R$string;
import com.google.android.material.R$style;
import com.google.android.material.R$styleable;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.EdgeToEdgeUtils;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class MaterialDatePicker<S> extends DialogFragment {
    static final Object CANCEL_BUTTON_TAG = "CANCEL_BUTTON_TAG";
    static final Object CONFIRM_BUTTON_TAG = "CONFIRM_BUTTON_TAG";
    static final Object TOGGLE_BUTTON_TAG = "TOGGLE_BUTTON_TAG";
    private MaterialShapeDrawable background;
    private MaterialCalendar calendar;
    private CalendarConstraints calendarConstraints;
    private Button confirmButton;
    private boolean edgeToEdgeEnabled;
    private CharSequence fullTitleText;
    private boolean fullscreen;
    private TextView headerSelectionText;
    private TextView headerTitleTextView;
    private CheckableImageButton headerToggleButton;
    private int inputMode;
    private CharSequence negativeButtonContentDescription;
    private int negativeButtonContentDescriptionResId;
    private CharSequence negativeButtonText;
    private int negativeButtonTextResId;
    private final LinkedHashSet onCancelListeners = new LinkedHashSet();
    private final LinkedHashSet onDismissListeners = new LinkedHashSet();
    private final LinkedHashSet onNegativeButtonClickListeners = new LinkedHashSet();
    private final LinkedHashSet onPositiveButtonClickListeners = new LinkedHashSet();
    private int overrideThemeResId;
    private PickerFragment pickerFragment;
    private CharSequence positiveButtonContentDescription;
    private int positiveButtonContentDescriptionResId;
    private CharSequence positiveButtonText;
    private int positiveButtonTextResId;
    private CharSequence singleLineTitleText;
    private CharSequence titleText;
    private int titleTextResId;

    public String getHeaderText() {
        getDateSelector();
        getContext();
        throw null;
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("OVERRIDE_THEME_RES_ID", this.overrideThemeResId);
        bundle.putParcelable("DATE_SELECTOR_KEY", (Parcelable) null);
        CalendarConstraints.Builder builder = new CalendarConstraints.Builder(this.calendarConstraints);
        MaterialCalendar materialCalendar = this.calendar;
        Month currentMonth = materialCalendar == null ? null : materialCalendar.getCurrentMonth();
        if (currentMonth != null) {
            builder.setOpenAt(currentMonth.timeInMillis);
        }
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", builder.build());
        bundle.putParcelable("DAY_VIEW_DECORATOR_KEY", (Parcelable) null);
        bundle.putInt("TITLE_TEXT_RES_ID_KEY", this.titleTextResId);
        bundle.putCharSequence("TITLE_TEXT_KEY", this.titleText);
        bundle.putInt("INPUT_MODE_KEY", this.inputMode);
        bundle.putInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY", this.positiveButtonTextResId);
        bundle.putCharSequence("POSITIVE_BUTTON_TEXT_KEY", this.positiveButtonText);
        bundle.putInt("POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY", this.positiveButtonContentDescriptionResId);
        bundle.putCharSequence("POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY", this.positiveButtonContentDescription);
        bundle.putInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY", this.negativeButtonTextResId);
        bundle.putCharSequence("NEGATIVE_BUTTON_TEXT_KEY", this.negativeButtonText);
        bundle.putInt("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY", this.negativeButtonContentDescriptionResId);
        bundle.putCharSequence("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY", this.negativeButtonContentDescription);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = getArguments();
        }
        this.overrideThemeResId = bundle.getInt("OVERRIDE_THEME_RES_ID");
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(bundle.getParcelable("DATE_SELECTOR_KEY"));
        this.calendarConstraints = (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(bundle.getParcelable("DAY_VIEW_DECORATOR_KEY"));
        this.titleTextResId = bundle.getInt("TITLE_TEXT_RES_ID_KEY");
        this.titleText = bundle.getCharSequence("TITLE_TEXT_KEY");
        this.inputMode = bundle.getInt("INPUT_MODE_KEY");
        this.positiveButtonTextResId = bundle.getInt("POSITIVE_BUTTON_TEXT_RES_ID_KEY");
        this.positiveButtonText = bundle.getCharSequence("POSITIVE_BUTTON_TEXT_KEY");
        this.positiveButtonContentDescriptionResId = bundle.getInt("POSITIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY");
        this.positiveButtonContentDescription = bundle.getCharSequence("POSITIVE_BUTTON_CONTENT_DESCRIPTION_KEY");
        this.negativeButtonTextResId = bundle.getInt("NEGATIVE_BUTTON_TEXT_RES_ID_KEY");
        this.negativeButtonText = bundle.getCharSequence("NEGATIVE_BUTTON_TEXT_KEY");
        this.negativeButtonContentDescriptionResId = bundle.getInt("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_RES_ID_KEY");
        this.negativeButtonContentDescription = bundle.getCharSequence("NEGATIVE_BUTTON_CONTENT_DESCRIPTION_KEY");
        CharSequence charSequence = this.titleText;
        if (charSequence == null) {
            charSequence = requireContext().getResources().getText(this.titleTextResId);
        }
        this.fullTitleText = charSequence;
        this.singleLineTitleText = getFirstLineBySeparator(charSequence);
    }

    private int getThemeResId(Context context) {
        int i = this.overrideThemeResId;
        if (i != 0) {
            return i;
        }
        getDateSelector();
        throw null;
    }

    public final Dialog onCreateDialog(Bundle bundle) {
        Dialog dialog = new Dialog(requireContext(), getThemeResId(requireContext()));
        Context context = dialog.getContext();
        this.fullscreen = isFullscreen(context);
        int i = R$attr.materialCalendarStyle;
        int i2 = R$style.Widget_MaterialComponents_MaterialCalendar;
        this.background = new MaterialShapeDrawable(context, (AttributeSet) null, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, R$styleable.MaterialCalendar, i, i2);
        int color = obtainStyledAttributes.getColor(R$styleable.MaterialCalendar_backgroundTint, 0);
        obtainStyledAttributes.recycle();
        this.background.initializeElevationOverlay(context);
        this.background.setFillColor(ColorStateList.valueOf(color));
        this.background.setElevation(dialog.getWindow().getDecorView().getElevation());
        return dialog;
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(this.fullscreen ? R$layout.mtrl_picker_fullscreen : R$layout.mtrl_picker_dialog, viewGroup);
        Context context = inflate.getContext();
        if (this.fullscreen) {
            inflate.findViewById(R$id.mtrl_calendar_frame).setLayoutParams(new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -2));
        } else {
            inflate.findViewById(R$id.mtrl_calendar_main_pane).setLayoutParams(new LinearLayout.LayoutParams(getPaddedPickerWidth(context), -1));
        }
        TextView textView = (TextView) inflate.findViewById(R$id.mtrl_picker_header_selection_text);
        this.headerSelectionText = textView;
        textView.setAccessibilityLiveRegion(1);
        this.headerToggleButton = (CheckableImageButton) inflate.findViewById(R$id.mtrl_picker_header_toggle);
        this.headerTitleTextView = (TextView) inflate.findViewById(R$id.mtrl_picker_title_text);
        initHeaderToggle(context);
        this.confirmButton = (Button) inflate.findViewById(R$id.confirm_button);
        getDateSelector();
        throw null;
    }

    public void onStart() {
        super.onStart();
        Window window = requireDialog().getWindow();
        if (this.fullscreen) {
            window.setLayout(-1, -1);
            window.setBackgroundDrawable(this.background);
            enableEdgeToEdgeIfNeeded(window);
        } else {
            window.setLayout(-2, -2);
            int dimensionPixelOffset = getResources().getDimensionPixelOffset(R$dimen.mtrl_calendar_dialog_background_inset);
            Rect rect = new Rect(dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset);
            window.setBackgroundDrawable(new InsetDrawable(this.background, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset, dimensionPixelOffset));
            window.getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(requireDialog(), rect));
        }
        startPickerFragment();
    }

    public void onStop() {
        this.pickerFragment.clearOnSelectionChangedListeners();
        super.onStop();
    }

    public final void onCancel(DialogInterface dialogInterface) {
        Iterator it = this.onCancelListeners.iterator();
        while (it.hasNext()) {
            ((DialogInterface.OnCancelListener) it.next()).onCancel(dialogInterface);
        }
        super.onCancel(dialogInterface);
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        Iterator it = this.onDismissListeners.iterator();
        while (it.hasNext()) {
            ((DialogInterface.OnDismissListener) it.next()).onDismiss(dialogInterface);
        }
        ViewGroup viewGroup = (ViewGroup) getView();
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        super.onDismiss(dialogInterface);
    }

    private void enableEdgeToEdgeIfNeeded(Window window) {
        if (!this.edgeToEdgeEnabled) {
            final View findViewById = requireView().findViewById(R$id.fullscreen_header);
            EdgeToEdgeUtils.applyEdgeToEdge(window, true, ViewUtils.getBackgroundColor(findViewById), (Integer) null);
            final int paddingTop = findViewById.getPaddingTop();
            final int paddingLeft = findViewById.getPaddingLeft();
            final int paddingRight = findViewById.getPaddingRight();
            final int i = findViewById.getLayoutParams().height;
            ViewCompat.setOnApplyWindowInsetsListener(findViewById, new OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                    Insets insets = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.systemBars());
                    if (i >= 0) {
                        findViewById.getLayoutParams().height = i + insets.top;
                        View view2 = findViewById;
                        view2.setLayoutParams(view2.getLayoutParams());
                    }
                    View view3 = findViewById;
                    view3.setPadding(paddingLeft + insets.left, paddingTop + insets.top, paddingRight + insets.right, view3.getPaddingBottom());
                    return windowInsetsCompat;
                }
            });
            this.edgeToEdgeEnabled = true;
        }
    }

    private void updateTitle() {
        this.headerTitleTextView.setText((this.inputMode != 1 || !isLandscape()) ? this.fullTitleText : this.singleLineTitleText);
    }

    /* access modifiers changed from: package-private */
    public void updateHeader(String str) {
        this.headerSelectionText.setContentDescription(getHeaderContentDescription());
        this.headerSelectionText.setText(str);
    }

    private String getHeaderContentDescription() {
        getDateSelector();
        requireContext();
        throw null;
    }

    private void startPickerFragment() {
        int themeResId = getThemeResId(requireContext());
        getDateSelector();
        MaterialCalendar newInstance = MaterialCalendar.newInstance((DateSelector) null, themeResId, this.calendarConstraints, (DayViewDecorator) null);
        this.calendar = newInstance;
        PickerFragment pickerFragment2 = newInstance;
        if (this.inputMode == 1) {
            getDateSelector();
            pickerFragment2 = MaterialTextInputPicker.newInstance((DateSelector) null, themeResId, this.calendarConstraints);
        }
        this.pickerFragment = pickerFragment2;
        updateTitle();
        updateHeader(getHeaderText());
        FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
        beginTransaction.replace(R$id.mtrl_calendar_frame, this.pickerFragment);
        beginTransaction.commitNow();
        this.pickerFragment.addOnSelectionChangedListener(new OnSelectionChangedListener() {
        });
    }

    private void initHeaderToggle(Context context) {
        this.headerToggleButton.setTag(TOGGLE_BUTTON_TAG);
        this.headerToggleButton.setImageDrawable(createHeaderToggleDrawable(context));
        this.headerToggleButton.setChecked(this.inputMode != 0);
        ViewCompat.setAccessibilityDelegate(this.headerToggleButton, (AccessibilityDelegateCompat) null);
        updateToggleContentDescription(this.headerToggleButton);
        this.headerToggleButton.setOnClickListener(new MaterialDatePicker$$ExternalSyntheticLambda0(this));
    }

    public static /* synthetic */ void $r8$lambda$_7eg0aLhI4_FyxsSORjppBhoKm4(MaterialDatePicker materialDatePicker, View view) {
        materialDatePicker.getDateSelector();
        throw null;
    }

    private void updateToggleContentDescription(CheckableImageButton checkableImageButton) {
        String str;
        if (this.inputMode == 1) {
            str = checkableImageButton.getContext().getString(R$string.mtrl_picker_toggle_to_calendar_input_mode);
        } else {
            str = checkableImageButton.getContext().getString(R$string.mtrl_picker_toggle_to_text_input_mode);
        }
        this.headerToggleButton.setContentDescription(str);
    }

    private DateSelector getDateSelector() {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(getArguments().getParcelable("DATE_SELECTOR_KEY"));
        return null;
    }

    private static Drawable createHeaderToggleDrawable(Context context) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842912}, AppCompatResources.getDrawable(context, R$drawable.material_ic_calendar_black_24dp));
        stateListDrawable.addState(new int[0], AppCompatResources.getDrawable(context, R$drawable.material_ic_edit_black_24dp));
        return stateListDrawable;
    }

    private static CharSequence getFirstLineBySeparator(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        String[] split = TextUtils.split(String.valueOf(charSequence), "\n");
        return split.length > 1 ? split[0] : charSequence;
    }

    private boolean isLandscape() {
        return getResources().getConfiguration().orientation == 2;
    }

    static boolean isFullscreen(Context context) {
        return readMaterialCalendarStyleBoolean(context, 16843277);
    }

    static boolean isNestedScrollable(Context context) {
        return readMaterialCalendarStyleBoolean(context, R$attr.nestedScrollable);
    }

    static boolean readMaterialCalendarStyleBoolean(Context context, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(MaterialAttributes.resolveOrThrow(context, R$attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()), new int[]{i});
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        return z;
    }

    private static int getPaddedPickerWidth(Context context) {
        Resources resources = context.getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R$dimen.mtrl_calendar_content_padding);
        int i = Month.current().daysInWeek;
        return (dimensionPixelOffset * 2) + (resources.getDimensionPixelSize(R$dimen.mtrl_calendar_day_width) * i) + ((i - 1) * resources.getDimensionPixelOffset(R$dimen.mtrl_calendar_month_horizontal_padding));
    }
}
