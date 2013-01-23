import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.*;

// https://gist.github.com/2000950
// http://dikar.iteye.com/blog/1503756
@BTrace public class TraceSystemGC {

	static {
		println("Start tracing.......");
	}
	
	@OnMethod(
		clazz="/.*/"
		method="/.*/",
		location=@Location(value=Kind.CALL, clazz="/java\\.lang\\.(?:System|Runtime)/", method="/.*/")
	)
	public static void tr(@Self Object self, @ProbeClassName String pcn, @ProbeMethodName String pmn, @TargetInstance Object instance , @TargetMethodOrField String method) {
		println("-----------------------------------------");
		println(strcat("ProbeClassName: ", pcn));
		println(strcat("ProbeMethodName: ", pmn));
		println(strcat("TargetInstance: ", str(instance)));
		println(strcat("TargetMethodOrField: ", str(method)));
		jstack();
	}
}
