package kg.zensoft.pulls.service.git;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class AbstractJavaNativeLibraryGitServerHttpConnector implements GitServerService {
    protected String serverUrl;

    public AbstractJavaNativeLibraryGitServerHttpConnector(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public HttpURLConnection openConnectionByUrl(String url) throws IOException {
        URL obj = new URL(url);
        return (HttpURLConnection) obj.openConnection();
    }
}
