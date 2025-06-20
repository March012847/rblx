package com.android.virtualization.terminal;

import java.util.function.Predicate;
import kotlin.jvm.functions.Function1;

/* compiled from: Logger.kt */
final /* synthetic */ class Logger$sam$java_util_function_Predicate$0 implements Predicate {
    private final /* synthetic */ Function1 function;

    Logger$sam$java_util_function_Predicate$0(Function1 function1) {
        function1.getClass();
        this.function = function1;
    }

    public final /* synthetic */ boolean test(Object obj) {
        return ((Boolean) this.function.invoke(obj)).booleanValue();
    }
}
