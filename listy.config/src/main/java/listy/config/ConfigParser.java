package listy.config;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class ConfigParser {

	private static final Pattern SECTION_PATTERN = Pattern.compile("^\\s*\\[(?<sectionName>[A-Za-z0-9]+)]\\s*$"); // [sectionName]
	private static final Pattern PARAMETER_PATTERN = Pattern.compile("^\\s*(?<paramName>[A-Za-z0-9.]+)\\s*=\\s*(?<paramValue>[A-Za-z0-9,._-]+)\\s*$"); // param = value
	private static final Pattern COMMENT_PATTERN = Pattern.compile("#.*");

	private static final String SECTION_GLOBAL = "global";
	private String currentSection = SECTION_GLOBAL;

	private final Map<String, List<ConfigParameter>> parsedParameters = new HashMap<>();

	private boolean matchSection(String line) {
		Matcher matcher = SECTION_PATTERN.matcher(line);

		if (matcher.matches()) {
			currentSection = matcher.group("sectionName");
			return true;
		}

		return false;
	}

	private boolean matchParameter(String line) {
		Matcher matcher = PARAMETER_PATTERN.matcher(line);

		if (matcher.matches()) {
			String parameterName = matcher.group("paramName");
			String parameterValue = matcher.group("paramValue");

			ConfigParameter parameter = new ConfigParameter(parameterName, parameterValue, currentSection);
			parsedParameters.putIfAbsent(currentSection, new ArrayList<>());
			parsedParameters.get(currentSection).add(parameter);

			return true;
		}

		return false;
	}

	private String stripComment(String line) {
		return line.replaceFirst(COMMENT_PATTERN.pattern(), "");
	}

	private void parseLine(String line) {
		boolean parsed = matchSection(line) || matchParameter(line);

		if (!parsed) {
			throw new ConfigurationException("invalid configuration file line '%s'".formatted(line));
		}
	}

	void parseNextLine(String line) {
		String strippedLine = stripComment(line);

		if (!strippedLine.isBlank()) {
			parseLine(strippedLine);
		}
	}

	Optional<ConfigParameter> configParameter(String sectionName, String parameterName) {
		return configParametersOfSection(sectionName)
			.stream()
			.filter(p -> p.name().equals(parameterName))
			.findFirst();
	}

	List<ConfigParameter> configParametersOfSection(String sectionName) {
		return parsedParameters.getOrDefault(sectionName, List.of());
	}
}
