package kg.zensoft.pulls.service.model;

import kg.zensoft.pulls.model.PullRequest;

import java.util.List;

public interface PullRequestService {
    List<PullRequest> getAllByRepositoryNameAndUsername(String repositoryName,String userName);
}
