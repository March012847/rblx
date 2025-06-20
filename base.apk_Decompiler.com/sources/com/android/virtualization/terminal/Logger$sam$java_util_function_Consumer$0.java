package com.android.virtualization.terminal;

import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;

/* compiled from: Logger.kt */
final /* synthetic */ class Logger$sam$java_util_function_Consumer$0 implements Consumer {
    private final /* synthetic */ Function1 function;

    Logger$sam$java_util_function_Consumer$0(Function1 function1) {
        function1.getClass();
        this.function = function1;
    }

    public final /* synthetic */ void accept(Object obj) {
        this.function.invoke(obj);
    }
}
