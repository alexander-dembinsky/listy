package listy.base.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

record Parameter(String name, int templatePosition, int templateLength) { }

/**
 * A Template provides functionality of parameterized output.
 *
 * <p>
 * For example, users could specify template string as: "Hello, ${p}",
 * and then create and instance of this class by calling {@link Template#parse(String)}
 * method. The returned object represents parsed template and contains information about
 * {@code p} parameter.
 * The value for the {@code p} can then be provided to {@link Template#render(TemplateContext)} method
 * as part of the {@link TemplateContext} object.
 * <p>
 * Example:
 * {@snippet :
 *	var template = Template.parse("Hello, ${p}");
 *	var ctx = new TemplateContext();
 *	ctx.setParam("p", "World!");
 *	String result = template.render(ctx);
 *	System.out.println(result); // Prints "Hello, World!"
 * }
 * </p>
 *
 * @see TemplateContext
 * @author Oleksandr Dembinskyi
 */
public final class Template {

	final List<Parameter> parameters = new ArrayList<>();
	final String templateText;

	Template(String templateText) {
		this.templateText = templateText;
	}

	/**
	 * Returns an instance of {@link Template}, constructed by
	 * parsing the {@code templateText} string.
	 *
	 * @param templateText template text. Could not be {@code null}
	 * @return parsed template
	 */
	public static Template parse(String templateText) {
		var parser = new TemplateParser();
		return parser.parse(templateText);
	}

	/**
	 * Returns string created by substituting template parameters
	 * with actual values provided in {@link TemplateContext} argument.
	 *
	 * @param ctx a context. Contains values of parameters and additional information needed for template
	 *            rendering.
	 * @return string created based on template and parameter values
	 */
	public String render(TemplateContext ctx) {
		Objects.requireNonNull(ctx, "template context should not be null");

		if (parameters.isEmpty()) {
			return templateText;
		}

		var buffer = new StringBuilder(templateText);

		for (Parameter parameter : parameters) {
			String value = ctx.param(parameter.name());

			if (value != null) {
				int start = parameter.templatePosition();
				int end = start + parameter.templateLength();
				buffer.replace(start, end, value);
			}
		}

		return buffer.toString();
	}

}
