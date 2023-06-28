package listy.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@DisplayName("Configuration reader")
class ConfigurationReaderTest {

	@Nested
	@DisplayName("configuration file")
	class FileConfigurationReader {

		@Test
		@DisplayName("should emit error if provided file object is null")
		void shouldEmitErrorIfFileIsNull() {
			Mono<Configuration> config = ConfigurationReader.ofConfigFile(null).read();

			StepVerifier.create(config)
				.expectErrorMessage("file should not be null")
				.verify();
		}

		@Test
		@DisplayName("should emit error if file does not exist")
		void shouldEmitErrorIfFileDoesNotExist() {
			Path invalidFilePath = Path.of("invalid", "file", "name");
			Mono<Configuration> config = ConfigurationReader.ofConfigFile(invalidFilePath).read();

			StepVerifier.create(config)
				.expectErrorMessage("configuration file %s does not exist".formatted(invalidFilePath.toAbsolutePath()))
				.verify();
		}

		@Test
		@DisplayName("should emmit error if file contains invalid line")
		void shouldEmitErrorIfFileContainsInvalidLine() throws URISyntaxException {
			var file = Path.of(getClass().getResource("/config/listy_invalid_1.conf").toURI());

			Mono<Configuration> config = ConfigurationReader.ofConfigFile(file).read();

			StepVerifier.create(config)
				.expectErrorMessage("invalid configuration file line '%s'".formatted("invalid_line"))
				.verify();
		}

		@Test
		@DisplayName("should return configuration with all expected parameters if file is valid")
		void shouldReturnConfigurationWithAllExpectedParamsForValidFile() throws URISyntaxException {
			var file = Path.of(getClass().getResource("/config/listy_valid_1.conf").toURI());

			Mono<Configuration> config = ConfigurationReader.ofConfigFile(file).read();

			StepVerifier.create(config)
				.assertNext(c -> {
					var location = c.configParameter("ui", "location");
					var size = c.configParameter("ui", "size");
					var expectedLocation = new ConfigParameter("location", "200,200", "ui");
					var expectedSize = new ConfigParameter("size", "500,600", "ui");

					assertThat(location, equalTo(Optional.of(expectedLocation)));
					assertThat(size, equalTo(Optional.of(expectedSize)));
				})
				.verifyComplete();
		}


		@Test
		@DisplayName("should return configuration with all expected parameters if there are multiple sections in the file")
		void shouldReturnConfigurationWithAllExpectedParamsForMultipleSections() throws URISyntaxException {
			var file = Path.of(getClass().getResource("/config/listy_valid_2.conf").toURI());

			Mono<Configuration> config = ConfigurationReader.ofConfigFile(file).read();

			StepVerifier.create(config)
				.assertNext(c -> {
					var param1 = c.configParameter("section1", "param1");
					var param2 = c.configParameter("section2", "param2");
					var paramFromInvalidSection = c.configParameter("invalid_section", "param1");
					var invalidParam = c.configParameter("section1", "invalid_param");

					var expectedParam1 = new ConfigParameter("param1", "foo", "section1");
					var expectedParam2 = new ConfigParameter("param2", "bar", "section2");

					assertThat(param1, equalTo(Optional.of(expectedParam1)));
					assertThat(param2, equalTo(Optional.of(expectedParam2)));
					assertThat(paramFromInvalidSection, is(Optional.empty()));
					assertThat(invalidParam, is(Optional.empty()));
				})
				.verifyComplete();
		}

		@Test
		@DisplayName("should return list of parameters for the sectionName")
		void shouldReturnListOfParamsForTheSection() throws URISyntaxException {
			var file = Path.of(getClass().getResource("/config/listy_valid_1.conf").toURI());

			Mono<Configuration> config = ConfigurationReader.ofConfigFile(file).read();

			StepVerifier.create(config)
				.assertNext(c -> {
					List<ConfigParameter> uiParams = c.configParametersOfSection("ui");
					var expectedLocation = new ConfigParameter("location", "200,200", "ui");
					var expectedSize = new ConfigParameter("size", "500,600", "ui");

					assertThat(uiParams, equalTo(List.of(expectedLocation, expectedSize)));
				})
				.verifyComplete();
		}
	}

}
