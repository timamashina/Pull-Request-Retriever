package kg.zensoft.pulls.service.git;

public interface GitServerService {

    <T> T publicPullRequests(String repository, String owner) throws InvalidUsernameOrRepositoryNameException;
}
