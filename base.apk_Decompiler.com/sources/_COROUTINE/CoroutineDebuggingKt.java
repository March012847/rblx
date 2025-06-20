package _COROUTINE;

/* compiled from: CoroutineDebugging.kt */
public abstract class CoroutineDebuggingKt {
    private static final String ARTIFICIAL_FRAME_PACKAGE_NAME = "_COROUTINE";

    /* access modifiers changed from: private */
    public static final StackTraceElement artificialFrame(Throwable th, String str) {
        StackTraceElement stackTraceElement = th.getStackTrace()[0];
        String str2 = ARTIFICIAL_FRAME_PACKAGE_NAME;
        return new StackTraceElement(str2 + "." + str, "_", stackTraceElement.getFileName(), stackTraceElement.getLineNumber());
    }
}
