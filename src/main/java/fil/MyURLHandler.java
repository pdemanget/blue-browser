package fil;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class MyURLHandler extends URLStreamHandler
{

    @Override
    protected URLConnection openConnection(URL url) throws IOException
    {
        return new MyURLConnection(url);
    }

}