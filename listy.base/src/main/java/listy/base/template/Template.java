package listy.base.template;

import java.util.Map;

public record Template(String templateText, Map<String, TemplateParameter> parameters) {
}
