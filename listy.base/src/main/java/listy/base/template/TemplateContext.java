package listy.base.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class TemplateContext {

	private final Map<String, String> params = new HashMap<>();

	public void setParam(String name, String value) {
		Objects.requireNonNull(name, "param name should not be null");
		this.params.put(name, value);
	}

	public void unsetParam(String name) {
		Objects.requireNonNull(name, "param name should not be null");
		this.params.remove(name);
	}

	public String param(String name) {
		Objects.requireNonNull(name, "param name should not be null");
		return this.params.get(name);
	}

	public void clearParams() {
		this.params.clear();
	}

}
