package listy.base.template;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Parameter")
class ParameterTest {

	@Test
	@DisplayName("parameter created successfully with valid arguments")
	void testCreateParameter() {
		assertDoesNotThrow(() -> new Parameter("Some text", 1, 2));
	}

	@Test
	@DisplayName("should not allow null name")
	void testNullName() {
		assertEquals(
			"Parameter name should not be null",
			assertThrows(IllegalArgumentException.class, () -> new Parameter(null, 1, 2)).getMessage()
		);
	}

	@Test
	@DisplayName("should not allow negative position")
	void testZeroLength() {
		assertEquals(
			"Parameter length in template should be > 0",
			assertThrows(IllegalArgumentException.class, () -> new Parameter("Some text", 1, 0)).getMessage()
		);
	}

	@Test
	@DisplayName("should not allow zero length")
	void testNegativePosition() {
		assertEquals(
			"Parameter position in template should be >= 0",
			assertThrows(IllegalArgumentException.class, () -> new Parameter("Some text", -1, 1)).getMessage()
		);
	}
}
