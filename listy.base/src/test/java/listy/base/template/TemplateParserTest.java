package listy.base.template;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@DisplayName("Template parser")
class TemplateParserTest {

	@Nested
	@DisplayName("Text mode template parser")
	class TextModeTemplateParser {
		@Test
		@DisplayName("parse template without parameters")
		void parseTemplateWithoutParameter() throws TemplateParsingException {
			var templateText = "Template string without parameters";
			TemplateParser templateParser = TemplateParser.textModeParser();

			Template template = templateParser.parse(templateText);

			assertEquals(templateText, template.templateText());
			assertEquals(Map.of(), template.parameters());
		}

		@Test
		@DisplayName("parse null string is not allowed")
		void parseNullString() {
			assertThrowsExactly(
				TemplateParsingException.class,
				() -> TemplateParser.textModeParser().parse(null),
				"Template text should not be null"
			);
		}

		@Test
		@DisplayName("parse template with parameters")
		void parseTemplateWithParameters() throws TemplateParsingException {
			var templateText = "The first parameter is ${first} and the second is ${second}";
			Template template = TemplateParser.textModeParser().parse(templateText);

			assertEquals(templateText, template.templateText());

			var parameters = template.parameters();

			assertEquals(2, parameters.size());
			assertEquals(new TemplateParameter("first", 23, Map.of()), parameters.get("first"));
			assertEquals(new TemplateParameter("second", 50, Map.of()), parameters.get("second"));
		}

		@Test
		@DisplayName("parse template with parameter without closing '}'")
		void parseTemplateWithNotClosedParameter() {
			assertThrowsExactly(
				TemplateParsingException.class,
				() -> TemplateParser.textModeParser().parse("Some ${incorrect parameter"),
				"Invalid parameter definition at index: 5"
			);
		}

	}

}
