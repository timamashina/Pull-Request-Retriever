package kg.zensoft.pulls.command.handler.impl;

import kg.zensoft.pulls.command.handler.CommandHandler;
import kg.zensoft.pulls.model.PullRequest;
import kg.zensoft.pulls.service.io.InputOutputService;
import kg.zensoft.pulls.service.model.PullRequestService;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class OpenPullRequestInBrowserCommandHandler extends ShowPullRequestCommandHandler implements CommandHandler {

    public OpenPullRequestInBrowserCommandHandler(PullRequestService pullRequests, InputOutputService inputOutputService, String username, String repoName) {
        super(pullRequests, inputOutputService, username, repoName);
    }

    @Override
    public void handleCommand() {
        super.handleCommand();
        if (currentPullRequests.isEmpty() || currentPullRequests == null)
            return;
        if (!Desktop.isDesktopSupported()) {
            inputOutputService.printMessage("You have no default browser\n");
            return;
        }
        currentPullRequests.forEach(pullRequest -> {
            try {
                Desktop.getDesktop().browse(new URI(pullRequest.getHtmlUrl()));
            } catch (IOException e) {
                inputOutputService.printMessage("Internal program error\n");
                e.printStackTrace();
            } catch (URISyntaxException e) {
                inputOutputService.printMessage("Invalid pull request urls");
                e.printStackTrace();
            }
        });
    }
}
