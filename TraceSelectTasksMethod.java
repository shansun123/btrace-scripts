import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.*;

// comments.
@BTrace public class TraceSelectTasksMethod {
	private static int taskCnt = 0;
	static {
		jstack();
		// Sys.Memory.dumpHeap("test.bin");
	}

	@OnMethod(
		clazz="com.shansun.demo.InventoryExpiringProcessor",
		method="selectTasks",
		location=@Location(Kind.RETURN)
	)
	public static void traceSelectTasks(@Self com.shansun.demo.InventoryExpiringProcessor instance, String taskParam, String ownSign, int taskItemNum, java.util.List<com.taobao.pamirs.schedule.TaskItemDefine> taskItemList, int eachFetchNum, @Return java.util.List<com.taobao.inventory.model.inventory.IpmTimeoutTaskDO> tasks) {
		//println(strcat(strcat(str(taskItemList), ":"), str(tasks)));
		//jstack();
		taskCnt += size(tasks);
	}

	@OnMethod(
		//clazz="/com\\.shansun\\.demo\\..*/",
		clazz="com.shansun.demo.InventoryExpiringProcessor",
		method="selectTasks", // "/.*/"
		location=@Location(value=Kind.CALL, clazz="/.*/", method="/.*/")
	)
	public static void traceCaller(@Self Object self, @ProbeClassName String pcn, @ProbeMethodName String pmn, @TargetInstance Object instance , @TargetMethodOrField String method) {
		//println("-----------------------------------------");
		//println(strcat("ProbeClassName: ", pcn));
		//println(strcat("ProbeMethodName: ", pmn));
		//println(strcat("TargetInstance: ", str(instance)));
		//println(strcat("TargetMethodOrField: ", str(method)));
	}

	@OnTimer(10000)
	public static void print() {
		println("------------------------------------------------");
		println(strcat("Got tasks: ", str(taskCnt)));
		taskCnt = 0;
	}

	@TLS private static long startTime = 0;


	@OnMethod(
		clazz="com.shansun.demo.InventoryExpiringProcessor",
		method="execute"
	)
	public static void startExecute() {
		startTime = timeNanos();
	}

	@OnMethod(
		clazz="com.shansun.demo.InventoryExpiringProcessor",
		method="execute",
		location=@Location(Kind.RETURN)
	)
	public static void endExecute(@Duration long duration) {
		long time = timeNanos() - startTime;
		println("-------------------------------------------------------------");
		println(strcat("Execute time(nanos): ", str(time)));
		println(strcat("Duration(nanos): ", str(duration)));
	}
}
