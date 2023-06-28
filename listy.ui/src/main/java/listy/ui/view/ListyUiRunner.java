package listy.ui.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import reactor.core.publisher.Flux;

public final class ListyUiRunner {

	private static final String LISTY_TITLE = "Listy";
	private final ViewOptions viewOptions;
	private final Flux<String> items;

	public ListyUiRunner(ViewOptions viewOptions, Flux<String> items) {
		this.viewOptions = viewOptions;
		this.items = items;
	}

	public void startUi() {
		Platform.startup(this::startup);
	}

	private void startup() {
		var stage = new Stage();
		stage.setTitle(LISTY_TITLE);
		stage.setX(viewOptions.location().x());
		stage.setY(viewOptions.location().y());

		var listView = new ListView<String>();
      var listViewItems = listView.getItems();

		items.doOnNext(item -> Platform.runLater(() -> listViewItems.add(item))).subscribe();

		var scene = new Scene(listView, viewOptions.size().width(), viewOptions.size().height());
		stage.setScene(scene);

		stage.show();
	}


}
