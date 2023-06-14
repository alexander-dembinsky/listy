module listy.ui {
	requires javafx.controls;
	requires java.base;

	exports listy.ui.view to listy.main, javafx.graphics;
}
