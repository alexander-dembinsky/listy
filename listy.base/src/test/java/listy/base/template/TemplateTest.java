package listy.base.template;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Template")
class TemplateTest {

	@Test
	@DisplayName("parse() should return parsed template")
	void testParse() {
		var templateText = "Text, ${param}";
		var template = Template.parse(templateText);

		assertEquals(templateText, template.templateText);
		assertEquals(List.of(new Parameter("param", 6, 8)), template.parameters);
	}

	@Test
	@DisplayName("render() should throw NullPointerException for null context")
	void testRenderNullContext() {
		var template = Template.parse("");
		assertThrows(NullPointerException.class, () -> template.render(null));
	}

	@Test
	@DisplayName("render() should return template without parameters as is")
	void testRenderNoParameters() {
		var template = Template.parse("Hello, World!");
		String text = template.render(new TemplateContext());

		assertEquals("Hello, World!", text);
	}

	@Test
	@DisplayName("render() should correctly substitute parameter values")
	void testRender() {
		var template = Template.parse("This param is ${regular}, this param is \\${escaped}, and this is ${missing}");

		var ctx = new TemplateContext();
		ctx.setParam("regular", "found");
		String text = template.render(ctx);

		assertEquals("This param is found, this param is \\${escaped}, and this is ${missing}", text);
	}

}
