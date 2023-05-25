package listy.main;

import listy.base.template.Template;
import listy.base.template.TemplateContext;

public class Main {

	public static void main(String[] args) {
		var template = Template.parse("Hello, ${p}!");
		var ctx = new TemplateContext();
		ctx.setParam("p", "Listy");

		System.out.println(template.render(ctx));
	}

}
