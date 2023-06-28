module listy.ui {
	requires javafx.controls;
	requires reactor.core;

	exports listy.ui.view to listy.main, javafx.graphics;
}
