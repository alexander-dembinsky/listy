package listy.ui.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("view options")
class ViewOptionsTest {

	@Nested
	@DisplayName("location")
	class LocationTests {

		@Test
		@DisplayName("should throw exception if value of the location string is incorrect")
		void shouldThrowExceptionForInvalidLocationString() {
			assertThrows(IllegalArgumentException.class, () -> ViewOptions.Location.parse("50,60,70"));
		}

		@Test
		@DisplayName("should return correct location for valid location string")
		void shouldReturnCorrectParsedLocation() {
			ViewOptions.Location location = ViewOptions.Location.parse("300,400");

			assertEquals(new ViewOptions.Location(300.0, 400.0), location);
		}
	}

	@Nested
	@DisplayName("size")
	class SizeTests {
		@Test
		@DisplayName("should throw exception if value of the size string is incorrect")
		void shouldThrowExceptionForInvalidLocationString() {
			assertThrows(IllegalArgumentException.class, () -> ViewOptions.Size.parse("50,60,70"));
		}

		@Test
		@DisplayName("should return correct size for valid size string")
		void shouldReturnCorrectParsedLocation() {
			ViewOptions.Size size = ViewOptions.Size.parse("300,400");

			assertEquals(new ViewOptions.Size(300.0, 400.0), size);
		}

	}

}
