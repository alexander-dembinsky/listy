package listy.base.template;

record Parameter(String name, int templatePosition, int templateLength) {

	Parameter {
		if (name == null) {
			throw new IllegalArgumentException("Parameter name should not be null");
		}
		if (templatePosition < 0) {
			throw new IllegalArgumentException("Parameter position in template should be >= 0");
		}
		if (templateLength <= 0) {
			throw new IllegalArgumentException("Parameter length in template should be > 0");
		}
	}

}
