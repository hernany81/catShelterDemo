import org.springframework.web.servlet.i18n.FixedLocaleResolver;

// Place your Spring DSL code here
beans = {
	customPropertyEditorRegistrar(CustomDateEditorRegistrar)
	
	localeResolver(FixedLocaleResolver) {
		defaultLocale = new Locale("en");
	}
}
