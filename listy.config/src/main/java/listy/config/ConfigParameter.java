package listy.config;

/**
 * A configuration parameter.
 *
 * @param name parameter name
 * @param value raw parameter value
 * @param sectionName parameter section name
 *
 * @author Oleksandr Dembinskyi
 */
public record ConfigParameter(String name, String value, String sectionName) {
}
