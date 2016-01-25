package fil;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class MyURLStreamHandlerFactory implements URLStreamHandlerFactory
{

    public URLStreamHandler createURLStreamHandler(String protocol)
    {
        if (protocol.equals("myapp"))
        {
            return new MyURLHandler();
        }
        return null;
    }

}