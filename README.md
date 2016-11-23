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
