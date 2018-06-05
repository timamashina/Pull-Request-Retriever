package kg.zensoft.pulls.repository;

import kg.zensoft.pulls.model.PullRequest;

import java.util.List;

public interface PullRequestRepository {
    List<PullRequest> getAllByRepositoryNameAndUsername(String repositoryName,String userName);
}