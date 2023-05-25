package listy.base.template;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This class contains information needed to "render" templates.
 * If template has parameters, this class allows to specify values for them.
 * See usage example in the documentation for {@link Template} class.
 *
 * @see Template
 * @author Oleksandr Dembinskyi
 */
public final class TemplateContext {

	private final Map<String, String> params = new HashMap<>();

	/**
	 * Set the value for the template parameter.
	 *
	 * @param name parameter name. Could not be {@code null}
	 * @param value parameter value
	 */
	public void setParam(String name, String value) {
		Objects.requireNonNull(name, "param name should not be null");
		this.params.put(name, value);
	}

	/**
	 * Unset the value for the template parameter.
	 *
	 * @param name parameter name. Could not be {@code null}
	 */
	public void unsetParam(String name) {
		Objects.requireNonNull(name, "param name should not be null");
		this.params.remove(name);
	}

	/**
	 * Returns the value of the parameter.
	 *
	 * @param name parameter name. Could not be {@code null}
	 * @return parameter value
	 */
	public String param(String name) {
		Objects.requireNonNull(name, "param name should not be null");
		return this.params.get(name);
	}

	/**
	 * Unset values for all parameters.
	 */
	public void clearParams() {
		this.params.clear();
	}

}
