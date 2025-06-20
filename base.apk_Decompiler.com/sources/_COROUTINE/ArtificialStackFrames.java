package _COROUTINE;

/* compiled from: CoroutineDebugging.kt */
public final class ArtificialStackFrames {
    public final StackTraceElement coroutineBoundary() {
        return CoroutineDebuggingKt.artificialFrame(new Exception(), _BOUNDARY.class.getSimpleName());
    }
}
