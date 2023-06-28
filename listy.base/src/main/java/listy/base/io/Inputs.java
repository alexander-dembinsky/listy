package listy.base.io;

import reactor.core.publisher.Flux;

import java.util.Scanner;

public final class Inputs {

	private Inputs() {
		throw new UnsupportedOperationException();
	}

	public static Flux<String> standardInputLines() {
		return Flux.generate(
			() -> new Scanner(System.in),
			(scanner, sink) -> {
				if (scanner.hasNextLine()) {
					sink.next(scanner.nextLine());
				} else {
					scanner.close();
					sink.complete();
				}
				return scanner;
			}
		);
	}

}
