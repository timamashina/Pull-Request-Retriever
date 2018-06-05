package kg.zensoft.pulls.command.handler.impl;

import kg.zensoft.pulls.command.handler.CommandHandler;
import kg.zensoft.pulls.model.PullRequest;
import kg.zensoft.pulls.service.io.InputOutputService;
import kg.zensoft.pulls.service.model.PullRequestService;

import java.util.List;


public class ShowPullRequestCommandHandler implements CommandHandler {

    protected PullRequestService pullRequestsService;
    protected InputOutputService inputOutputService;
    protected String username;
    protected String repoName;
    protected List<PullRequest> currentPullRequests;

    public ShowPullRequestCommandHandler(PullRequestService pullRequests, InputOutputService inputOutputService, String username, String repoName) {
        this.pullRequestsService = pullRequests;
        this.inputOutputService = inputOutputService;
        this.username = username;
        this.repoName = repoName;
    }

    @Override
    public void handleCommand() {
        if (repoName.equals("")) {
            inputOutputService.printMessage("Please enter a repository name: ");
            repoName = (String) inputOutputService.getUserInput();
        }
        if (username.equals("")) {
            inputOutputService.printMessage("Please enter a username that owns " + repoName + " repository: ");
            username = (String) inputOutputService.getUserInput();
        }
        try {
            currentPullRequests = pullRequestsService.getAllByRepositoryNameAndUsername(repoName, username);
            if (currentPullRequests.isEmpty()) {
                inputOutputService.printMessage("No pull requests found\n");
                return;
            }
            inputOutputService.printMessage("Pull requests of " + username + " repository\n");
            currentPullRequests.forEach(pullRequest -> {
                inputOutputService.printMessage(" -" + pullRequest.getHtmlUrl() + "\n");
            });

        } catch (Exception e) {
            inputOutputService.printMessage("Invalid Username or Repository name\n");
        }
        username = "";
        repoName = "";
    }

    public PullRequestService getPullRequestService() {
        return pullRequestsService;
    }

    public void setPullRequestService(PullRequestService pullRequests) {
        this.pullRequestsService = pullRequests;
    }

    public InputOutputService getInputOutputService() {
        return inputOutputService;
    }

    public void setInputOutputService(InputOutputService inputOutputService) {
        this.inputOutputService = inputOutputService;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }
}