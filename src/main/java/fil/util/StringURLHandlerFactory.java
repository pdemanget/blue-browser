package fil.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Gère la couche URLStreamHandlerFactory -> URLStreamHandler -> URLConnection.
 *
 * Permet de rajouter plusieurs handler sur les stream handler, et de gérer un handler "Simple" basé sunr fonction pour les resources texte.
 *
 * Usage:
 *
 */
public class StringURLHandlerFactory implements URLStreamHandlerFactory {

  private Map<String, URLStreamHandler> handlers = new HashMap<>();

  public StringURLHandlerFactory () {
    // to be done only once.
    URL.setURLStreamHandlerFactory(this);
  }


  @Override
  public URLStreamHandler createURLStreamHandler (String protocol) {

    return handlers.get(protocol);
  }

  /**
   * Permet d'ajouter un nouveau protocole
   *
   * @param protocol
   * @param handler
   */
  public void addHandler (String protocol, URLStreamHandler handler) {
      handlers.put(protocol,handler);
  }

   /**
   * Permet d'ajouter un nouveau protocole basé sur une fonction.
   *
   * @param protocol
   * @param handler
   */
  public void addHandler (String protocol, Function<URL,String> fun) {
      handlers.put(protocol,new StringURLHandler(fun));
  }

  /* =========== INNER ===============*/

  public static class StringURLHandler extends URLStreamHandler{
      Function<URL,String> fun;
      public StringURLHandler(Function<URL,String> fun){
        this.fun=fun;
      }
      @Override
      protected URLConnection openConnection (URL url) throws IOException {
        return new StringURLConnection(url,fun);
      }

  }

  public static class StringURLConnection extends URLConnection {
    Function<URL,String> fun;
    public StringURLConnection (URL url,Function<URL,String> fun) {
      super(url);
      this.fun=fun;
    }

    @Override
    public void connect () throws IOException {
    }

    @Override
    public InputStream getInputStream () throws IOException {
      String str = fun.apply(url);

      return new ByteArrayInputStream(str.getBytes("UTF-8"));
    }
  }
}
