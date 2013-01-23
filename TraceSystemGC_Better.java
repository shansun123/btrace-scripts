import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.*;

@BTrace public class TraceSystemGC {

	static {
		println("Start tracing.......");
	}
	
	@OnMethod(
		clazz="/java\\.lang\\.(?:System|Runtime)/",
		method="gc"
	)
	public static void tr() {
		println("-----------------------------------------");
		jstack();
	}
}
