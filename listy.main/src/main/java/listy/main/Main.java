package listy.main;

import listy.base.io.Inputs;
import listy.config.ConfigurationReader;
import listy.ui.view.ListyUiRunner;
import listy.ui.view.ViewOptions;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

	private static final String CONFIG_FILE_LOCATION = "%s/.config/listy.conf".formatted(System.getenv("HOME"));

	public static void main(String[] args) {
		Path configPath = Paths.get(CONFIG_FILE_LOCATION);

		ConfigurationReader.ofConfigFile(configPath).read()
			.map(ConfigViewOptions::new)
			.doOnSuccess(Main::runApp)
			.doOnError(Main::reportError)
			.subscribe();
	}

	private static void reportError(Throwable e) {
		System.err.println(e.getMessage());
	}

	private static void runApp(ViewOptions viewOptions) {
		Flux<String> items = Inputs.standardInputLines()
			.subscribeOn(Schedulers.boundedElastic());

		var runner = new ListyUiRunner(viewOptions, items);
		runner.startUi();
	}

}
