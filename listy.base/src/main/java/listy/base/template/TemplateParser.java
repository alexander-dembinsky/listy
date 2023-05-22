package listy.base.template;

public interface TemplateParser {

	interface Tokens {
		char[] PARAMETER_START = { '$', '{' };
		char[] PARAMETER_END = { '}' };
	}

	Template parse(String templateText) throws TemplateParsingException;

	static TemplateParser textModeParser() {
		return new TextModeTemplateParser();
	}
}
