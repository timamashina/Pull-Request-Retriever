package kg.zensoft.pulls.repository.impl;

import kg.zensoft.pulls.model.PullRequest;
import kg.zensoft.pulls.model.Status;
import kg.zensoft.pulls.repository.PullRequestRepository;
import kg.zensoft.pulls.service.git.GitServerService;
import kg.zensoft.pulls.service.git.InvalidUsernameOrRepositoryNameException;
import kg.zensoft.pulls.service.json.CustomPullRequestJsonConverter;
import kg.zensoft.pulls.service.json.JsonConverter;
import kg.zensoft.pulls.service.json.TextDataExchangeFormatConverter;

import java.util.ArrayList;
import java.util.List;

public class GitServerPullRequestRepository implements PullRequestRepository {

    private GitServerService gitServerService;
    private TextDataExchangeFormatConverter textDataExchangeFormatConverter;

    public GitServerPullRequestRepository(GitServerService gitServerService) {
        this.gitServerService = gitServerService;
        textDataExchangeFormatConverter = new CustomPullRequestJsonConverter();
    }

    public GitServerPullRequestRepository(GitServerService gitServerService, TextDataExchangeFormatConverter textDataExchangeFormatConverter) {
        this.gitServerService = gitServerService;
        this.textDataExchangeFormatConverter = textDataExchangeFormatConverter;
    }

    @Override
    public List<PullRequest> getAllByRepositoryNameAndUsername(String repositoryName, String userName) {
        try {
            String pullRequestTextFile = gitServerService.publicPullRequests(repositoryName, userName);
            return textDataExchangeFormatConverter.toObject(pullRequestTextFile, PullRequest.class);
        } catch (InvalidUsernameOrRepositoryNameException e) {
            return null;
        }
    }

    public GitServerService getGitServerService() {
        return gitServerService;
    }

    public void setGitServerService(GitServerService gitServerService) {
        this.gitServerService = gitServerService;
    }

    public TextDataExchangeFormatConverter getTextDataExchangeFormatConverter() {
        return textDataExchangeFormatConverter;
    }

    public void setTextDataExchangeFormatConverter(TextDataExchangeFormatConverter textDataExchangeFormatConverter) {
        this.textDataExchangeFormatConverter = textDataExchangeFormatConverter;
    }
}
