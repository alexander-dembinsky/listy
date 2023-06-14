package listy.main;

import listy.ui.view.ListyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	private static List<String> readInput() throws IOException {
		List<String> items = new ArrayList<>();
		try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				items.add(line);
			}
		}
		return items;
	}

	public static void main(String[] args) throws IOException {
		List<String> items = readInput();
		ListyApplication.launch(items);
	}

}
