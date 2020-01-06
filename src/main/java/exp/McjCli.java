package exp;

import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@Command(name = "microcj", mixinStandardHelpOptions = true, version = "0.3")
public class McjCli implements Callable<Integer> {
	@Parameters(hidden = true)  // "hidden": don't show this parameter in usage help message
			List<String> allParameters; // no "index" attribute: captures _all_ arguments (as Strings)

	@Parameters(index = "0") String settings;
	@Parameters(index = "1")   int iterations;
	@Parameters(index = "2") int steps;

	public static void main(String[] args) {
		System.out.println("args" + Arrays.toString(args));
		int exitCode = new CommandLine(new McjCli()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public Integer call() throws Exception {
		Experiment experiment = new Experiment(settings, iterations, steps);
		experiment.start();
		return 0;
	}
}
