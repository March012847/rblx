package com.android.virtualization.terminal;

import java.util.function.ToLongFunction;
import kotlin.jvm.functions.Function1;

/* compiled from: Logger.kt */
final /* synthetic */ class Logger$sam$java_util_function_ToLongFunction$0 implements ToLongFunction {
    private final /* synthetic */ Function1 function;

    Logger$sam$java_util_function_ToLongFunction$0(Function1 function1) {
        function1.getClass();
        this.function = function1;
    }

    public final /* synthetic */ long applyAsLong(Object obj) {
        return ((Number) this.function.invoke(obj)).longValue();
    }
}
