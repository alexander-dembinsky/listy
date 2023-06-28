package listy.main;

import listy.config.ConfigParameter;
import listy.config.Configuration;
import listy.ui.view.ViewOptions;

class ConfigViewOptions implements ViewOptions {

	private static final String UI_SECTION = "ui";
	private final Configuration configuration;

	static final Location DEFAULT_LOCATION = new Location(0, 0);
	static final Size DEFAULT_SIZE = new Size(500, 500);

	ConfigViewOptions(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public Location location() {
		return configuration.configParameter(UI_SECTION, "location")
			.map(ConfigParameter::value)
			.map(ViewOptions.Location::parse)
			.orElse(DEFAULT_LOCATION);
	}

	@Override
	public Size size() {
		return configuration.configParameter(UI_SECTION, "size")
			.map(ConfigParameter::value)
			.map(ViewOptions.Size::parse)
			.orElse(DEFAULT_SIZE);
	}
}
