package listy.base.template;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("Template parsing")
class TemplateParserTest {

	@Test
	@DisplayName("Should throw exception when input is null")
	void testNullInput() {
		assertThrows(NullPointerException.class, () -> new TemplateParser().parse(null));
	}

	@ParameterizedTest
	@MethodSource("testParametersParsingArgs")
	void testParametersParsing(String input, List<Parameter> expectedParameters) {
		var template = new TemplateParser().parse(input);

		assertEquals(input, template.templateText);
		assertEquals(expectedParameters, template.parameters);
	}

	public static Stream<Arguments> testParametersParsingArgs() {
		return Stream.of(
			arguments("", List.of()),
			arguments("Simple text", List.of()),
			arguments("${param}", List.of(new Parameter("param", 0, 8))),
			arguments("text ${param1} text ${param2} text", List.of(
				new Parameter("param1", 5, 9),
				new Parameter("param2", 20, 9)
				)
			),
			arguments("\\${escaped}", List.of()),
			arguments("text \\${escaped} text", List.of())
		);
	}
}
