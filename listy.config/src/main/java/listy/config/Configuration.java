package listy.config;

import java.util.List;
import java.util.Optional;

/**
 * A configuration interface provides the ability to retrieve configuration parameters.
 * Besides the name and the value, each parameter is related to the specific sectionName.
 * Sections could be used to group related parameters together. Additionally, it allows to
 * have parameters with the same name, but related to a different sectionName.
 *
 * @author Oleksandr Dembinskyi
 */
public interface Configuration {

	/**
	 * Returns the configuration parameter by sectionName and parameter name.
	 *
	 * @param sectionName name of the sectionName
	 * @param name name of the parameter
	 *
	 * @return configuration parameter if it is present in the configuration
	 */
	Optional<ConfigParameter> configParameter(String sectionName, String name);

	/**
	 * Returns the list of parameters in the given sectionName
	 *
	 * @param sectionName name of the sectionName
	 *
	 * @return list of configuration parameters related to the given sectionName. Empty list is returned if
	 * there is no sectionName with the given name, or if it does not have any parameters.
	 */
	List<ConfigParameter> configParametersOfSection(String sectionName);

}
