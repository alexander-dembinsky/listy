package listy.ui.view;

import java.util.Arrays;

public interface ViewOptions {

	private static Double[] parseCommaSeparatedDoubles(String rawString) {
		return Arrays.stream(rawString.split(","))
			.map(String::trim)
			.map(Double::parseDouble)
			.toArray(Double[]::new);
	}

	record Location(double x, double y) {
		public static Location parse(String rawString) {
			Double[] doubles = parseCommaSeparatedDoubles(rawString);
			if (doubles.length != 2) {
				throw new IllegalArgumentException("invalid location value '%s'. Value should be comma separated numbers like '200,300'".formatted(rawString));
			}
			return new Location(doubles[0], doubles[1]);
		}
	}

	record Size(double width, double height) {
		public static Size parse(String rawString) {
			Double[] doubles = parseCommaSeparatedDoubles(rawString);
			if (doubles.length != 2) {
				throw new IllegalArgumentException("invalid size value '%s'. Value should be comma separated numbers like '200,300'".formatted(rawString));
			}
			return new Size(doubles[0], doubles[1]);
		}
	}

	Location location();

	Size size();

}
