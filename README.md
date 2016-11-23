blue browser
====


JavaFX Web browser using integrated webkit of the JVM

Using webView
-----
    @FXML WebView webView;
    webView.getEngine()
	 webEngine.load("http://localhost:8001/index.html");
	 webEngine.loadContent("<html><head><title>IWH</title></head><body><h1>test</h1><script>alert('toto');</script></body></html>");
	 webView.getEngine().getHistory().go(-1);

Using customPage (desktopViewer)
--

URL.setURLStreamHandlerFactory(new MyURLStreamHandlerFactory());


Execute JS from Java
--
		JSObject window = (JSObject) webEngine.executeScript("window");

Execute Java from JS
--


Redirect protocol to custom generation
---
The secret behind resource and local file usage instead of remote files :

		URL.setURLStreamHandlerFactory(new MyURLStreamHandlerFactory());

With a custom implementation of MyURLStreamHandlerFactory which extends URLStreamHandler