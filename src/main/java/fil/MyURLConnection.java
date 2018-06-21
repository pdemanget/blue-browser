package fil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

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

            String fileName = getURL().getFile();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            copy(this.getClass().getResourceAsStream(fileName),byteArrayOutputStream);
           data = byteArrayOutputStream.toByteArray();

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