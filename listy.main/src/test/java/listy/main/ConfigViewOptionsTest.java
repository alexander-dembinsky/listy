package listy.main;

import listy.config.ConfigParameter;
import listy.config.Configuration;
import listy.ui.view.ViewOptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("configurable view options")
class ConfigViewOptionsTest {

	@InjectMocks
	private ConfigViewOptions configViewOptions;

	@Mock
	private Configuration configuration;

	@Test
	@DisplayName("should return view options specified in the configuration")
	void shouldReturnViewOptionsSpecifiedInTheConfiguration() {
		when(configuration.configParameter("ui", "location"))
			.thenReturn(Optional.of(new ConfigParameter("location", "300,400", "ui")));

		when(configuration.configParameter("ui", "size"))
			.thenReturn(Optional.of(new ConfigParameter("size", "500,600", "ui")));

		assertThat(configViewOptions.location(), equalTo(new ViewOptions.Location(300.0, 400.0)));
		assertThat(configViewOptions.size(), equalTo(new ViewOptions.Size(500.0, 600.0)));
	}

	@Test
	@DisplayName("should return default options if no options were specified in the configuration")
	void shouldReturnDefaultViewOptions() {
		assertThat(configViewOptions.location(), equalTo(ConfigViewOptions.DEFAULT_LOCATION));
		assertThat(configViewOptions.size(), equalTo(ConfigViewOptions.DEFAULT_SIZE));
	}

}
