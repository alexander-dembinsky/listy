package listy.base.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

class TextModeTemplateParser implements TemplateParser {

	private char[] templateText;
	private int idx = 0;
	private final List<TemplateParameter> parameters = new ArrayList<>();

	@Override
	public Template parse(String templateText) throws TemplateParsingException {
		if (templateText == null) {
			throw new TemplateParsingException("Template text should not be null");
		}

		this.parameters.clear();
		this.templateText = templateText.toCharArray();
		this.idx = 0;

		return parseTemplate(templateText);
	}

	private Template parseTemplate(String templateText) throws TemplateParsingException {
		this.templateText = templateText.toCharArray();

		while (idx < templateText.length()) {
			parseParameter();

			idx++;
		}

		Map<String, TemplateParameter> parameterMap = parameters.stream()
			.collect(toMap(TemplateParameter::name, Function.identity()));
		return new Template(templateText, parameterMap);
	}

	private void parseParameter() throws TemplateParsingException {
		int i = idx;

		if (Arrays.equals(Tokens.PARAMETER_START, Arrays.copyOfRange(templateText, i, i + Tokens.PARAMETER_START.length))) {
			i += Tokens.PARAMETER_START.length;

			int paramNamePos = i;
			int paramNameLength = 0;

			boolean parameterEndFound = false;
			while (i < templateText.length) {
				parameterEndFound = templateText[i] == Tokens.PARAMETER_END[0];
				if (parameterEndFound) {
					break;
				}
				paramNameLength++;
				i++;
			}

			if (!parameterEndFound) {
				throw new TemplateParsingException("Invalid parameter definition at index: %d".formatted(idx));
			}

			String name = new String(Arrays.copyOfRange(templateText, paramNamePos, paramNamePos + paramNameLength));
			parameters.add(new TemplateParameter(name, idx, Map.of()));
			idx = ++i;
		}
	}
}
