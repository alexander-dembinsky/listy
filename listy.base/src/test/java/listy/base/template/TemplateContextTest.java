package listy.base.template;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Template context")
class TemplateContextTest {

	TemplateContext ctx = new TemplateContext();

	@Test
	@DisplayName("Should allow to set parameter")
	void testSetParam() {
		ctx.setParam("foo", "bar");
		assertEquals("bar", ctx.param("foo"));
	}

	@Test
	@DisplayName("Should allow to unset parameter")
	void testUnsetParam() {
		ctx.setParam("foo", "bar");
		ctx.unsetParam("foo");
		assertNull(ctx.param("foo"));
	}

	@Test
	@DisplayName("Should allow to clear parameters")
	void testClearParam() {
		ctx.setParam("foo", "bar");
		ctx.setParam("foo2", "bar2");

		ctx.clearParams();

		assertNull(ctx.param("foo"));
		assertNull(ctx.param("foo2"));
	}
}
