package fil;
import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

/**
 * Register a protocol handler for URLs like this: <code>myapp:///pics/sland.gif</code><br>
 */
public class MyURLConnection extends URLConnection
{

    protected MyURLConnection(URL url) {
		super(url);
	}

	private byte[] data;

    @Override
    public void connect() throws IOException
    {
        if (connected)
        {
            return;
        }
        loadImage();
        connected = true;
    }

    public String getHeaderField(String name)
    {
        if ("Content-Type".equalsIgnoreCase(name))
        {
            return getContentType();
        }
        else if ("Content-Length".equalsIgnoreCase(name))
        {
            return "" + getContentLength();
        }
        return null;
    }

    public String getContentType()
    {
        String fileName = getURL().getFile();
        String ext = fileName.substring(fileName.lastIndexOf('.')+1);
        switch(ext){
        case "html":return "text/html";
        case "jpg":
        case "png":return "image/"+ext;
        case "js": return "application/javascript";
        }
        return "text/"+ext;
         // TODO: switch based on file-type "image/" + ext
    }

    public int getContentLength()
    {
        return data.length;
    }

    public long getContentLengthLong()
    {
        return data.length;
    }

    public boolean getDoInput()
    {
        return true;
    }

    public InputStream getInputStream() throws IOException
    {
        connect();
        return new ByteArrayInputStream(data);
    }

    /**
     * Reads all bytes from an input stream and writes them to an output stream.
     */
    private static long copy(InputStream source, OutputStream sink)
        throws IOException
    {
        long nread = 0L;
        byte[] buf = new byte[8192];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
            nread += n;
        }
        return nread;
    }
    
    private void loadImage() throws IOException
    {
        if (data != null)
        {
            return;
        }

            int timeout = this.getConnectTimeout();
            long start = System.currentTimeMillis();
            URL url = getURL();

//            String imgPath = url.toExternalForm();
//            imgPath = imgPath.startsWith("myapp://") ? imgPath.substring("myapp://".length()) : imgPath.substring("myapp:".length()); // attention: triple '/' is reduced to a single '/'
            String fileName = getURL().getFile();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            copy(this.getClass().getResourceAsStream(fileName),byteArrayOutputStream);
           data = byteArrayOutputStream.toByteArray(); 
            // this is my own asynchronous image implementation
            // instead of this part (including the following loop) you could do your own (synchronous) loading logic
//            MyImage img = MyApp.getImage(imgPath);
//            do
//            {
//                if (img.isFailed())
//                {
//                    throw new IOException("Could not load image: " + getURL());
//                }
//                else if (!img.hasData())
//                {
//                    long now = System.currentTimeMillis();
//                    if (now - start > timeout)
//                    {
//                        throw new SocketTimeoutException();
//                    }
//                    Thread.sleep(100);
//                }
//            } while (!img.hasData());
//            data = img.getData();

    }

    public OutputStream getOutputStream() throws IOException
    {
        // this might be unnecessary - the whole method can probably be omitted for our purposes
        return new ByteArrayOutputStream();
    }

    public java.security.Permission getPermission() throws IOException
    {
        return null; // we need no permissions to access this URL
    }

}