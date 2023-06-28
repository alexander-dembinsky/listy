package listy.config;

import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

final class FileConfigurationReader implements ConfigurationReader {
	private final Path configFile;

	FileConfigurationReader(Path configFile) {
		this.configFile = configFile;
	}

	private CompletableFuture<Configuration> readConfigurationFromFileAsync() {
		return CompletableFuture.supplyAsync(() -> {
			try {
				return readConfigurationFromFile();
			} catch (Exception e) {
				throw new CompletionException(e);
			}
		});
	}

	private Configuration readConfigurationFromFile() throws IOException {
		var parser = new ConfigParser();

		try (var lines = Files.lines(configFile)) {
			lines.forEach(parser::parseNextLine);
		}

		return new Configuration() {
			@Override
			public Optional<ConfigParameter> configParameter(String section, String name) {
				return parser.configParameter(section, name);
			}

			@Override
			public List<ConfigParameter> configParametersOfSection(String sectionName) {
				return parser.configParametersOfSection(sectionName);
			}
		};
	}

	@Override
	public Mono<Configuration> read() {
		if (configFile == null)
			return Mono.error(new ConfigurationException("file should not be null"));

		File file = configFile.toFile();
		if (!file.exists()) {
			return Mono.error(
				new ConfigurationException("configuration file %s does not exist".formatted(file.getAbsolutePath()))
			);
		}

		return Mono.fromFuture(readConfigurationFromFileAsync());
	}
}
