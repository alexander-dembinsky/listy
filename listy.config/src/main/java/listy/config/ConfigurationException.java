package listy.config;

/**
 * Indicates error during reading configuration.
 *
 * @author Oleksandr Dembinskyi
 */
public class ConfigurationException extends RuntimeException {

	ConfigurationException(String message) {
		super(message);
	}

}
