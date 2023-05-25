package listy.base.template;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Template {

	final List<Parameter> parameters = new ArrayList<>();
	final String templateText;

	Template(String templateText) {
		this.templateText = templateText;
	}

	public static Template parse(String templateText) {
		var parser = new TemplateParser();
		return parser.parse(templateText);
	}

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
