package listy.base.template;

import java.util.Map;

public record TemplateParameter(String name, int position, Map<String, ParameterAttribute> attributes) {
}
