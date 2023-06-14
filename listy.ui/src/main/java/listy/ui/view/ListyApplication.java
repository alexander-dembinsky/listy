package listy.ui.view;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ListyApplication extends Application {

	private static final List<String> items = new ArrayList<>();

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Listy");

		var vbox = new VBox();

		List<Label> labels = items.stream().map(Label::new).toList();
		vbox.getChildren().addAll(labels);

		stage.setScene(new Scene(vbox, 500, 500));
		stage.show();
	}

	public static void launch(List<String> itemsList) {
		items.clear();
		items.addAll(itemsList);
		launch();
	}

}
