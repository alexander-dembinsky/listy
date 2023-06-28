package listy.config;

import reactor.core.publisher.Mono;

import java.nio.file.Path;

/**
 * An interface that allows to read configuration.
 * <p>
 * Example:
 * {@snippet :
 * ConfigurationReader reader = ConfigurationReader.ofConfigFile(Path.of("config", "path", "listy.conf"));
 * reader.read().doOnNext(configuration -> {
 *     var uiParameters = configuration.configParametersOfSection("ui");
 *     uiParameters.forEach(System.out::println);
 * })
 * }
 * </p>
 *
 * @author Oleksandr Dembinskyi
 */
public interface ConfigurationReader {

	/**
	 * Asynchronously reads the configuration.
	 *
	 * @return {@link Mono} with the {@link Configuration}.
	 * If there is an error during read() operation, error of {@link ConfigurationException} type will be emitted.
	 */
	Mono<Configuration> read();


	/**
	 * Returns a reader that reads the configuration from the specified {@link Path}.
	 * <p>
	 * Configuration files should have the following format:
	 *
	 * <pre>
	 * # Comments are allowed
	 * [section1] # Start of section 'section1'
	 * param1 = value1 # Parameter definition
	 * param2 = value2
	 *
	 * [section2] # Start of section 'section2'
	 * param1 = value3 # Parameter with the same name but in different section
	 * param3 = value4
	 * </pre>
	 *
	 * </p>
	 *
	 * @param configFile path to the configuration file.
	 *                   configFile should point to existing readable file in the file system.
	 *
	 * @return a reader that works with config files
	 */
	static ConfigurationReader ofConfigFile(Path configFile) {
		return new FileConfigurationReader(configFile);
	}

}

