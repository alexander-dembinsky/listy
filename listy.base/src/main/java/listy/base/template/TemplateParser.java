package listy.base.template;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TemplateParser {
	private static final String PARAM_NAME_GROUP = "paramName";
	private static final String PARAM_DEF_GROUP = "paramDef";
	private static final Pattern PARAMETER_PATTERN = Pattern.compile("(^|[^\\\\])(?<paramDef>\\$\\{(?<paramName>[A-Za-z0-9_]+)})");

	Template parse(String input) {
		Objects.requireNonNull(input, "Input should not be null");

		Matcher matcher = PARAMETER_PATTERN.matcher(input);

		Template template = new Template(input);
		while (matcher.find()) {
			String paramName = matcher.group(PARAM_NAME_GROUP);
			int start = matcher.start(PARAM_DEF_GROUP);
			int end = matcher.end(PARAM_DEF_GROUP);

			template.parameters.add(new Parameter(paramName, start, end - start));
		}

		return template;
	}

}
