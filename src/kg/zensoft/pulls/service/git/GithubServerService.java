package kg.zensoft.pulls.service.git;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class GithubServerService extends AbstractJavaNativeLibraryGitServerHttpConnector implements GitServerService {


    public GithubServerService() {
        super("https://api.github.com/");
    }

    @Override
    public String publicPullRequests(String repository, String owner) throws InvalidUsernameOrRepositoryNameException {
        String serviceUrl = "repos/" + owner + "/" + repository + "/pulls";
        String fullUrl = serverUrl + serviceUrl;
        try {
            HttpURLConnection con = openConnectionByUrl(fullUrl);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            throw new InvalidUsernameOrRepositoryNameException("Invalid owner or repository");
        }
    }
}