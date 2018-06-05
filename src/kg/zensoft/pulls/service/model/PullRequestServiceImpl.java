package kg.zensoft.pulls.service.model;

import kg.zensoft.pulls.model.PullRequest;
import kg.zensoft.pulls.repository.PullRequestRepository;
import kg.zensoft.pulls.service.model.PullRequestService;

import java.util.List;

public class PullRequestServiceImpl implements PullRequestService {
    private PullRequestRepository pullRequestRepository;

    public PullRequestServiceImpl(PullRequestRepository pullRequestRepository) {
        this.pullRequestRepository = pullRequestRepository;
    }

    @Override
    public List<PullRequest> getAllByRepositoryNameAndUsername(String repositoryName, String userName) {
        return pullRequestRepository.getAllByRepositoryNameAndUsername(repositoryName, userName);
    }
}
