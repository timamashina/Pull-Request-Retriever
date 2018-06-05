package kg.zensoft.pulls.model;

import kg.zensoft.pulls.service.json.JsonConverterConfig;

public class PullRequest {
    @JsonConverterConfig(replaceWith = "html_url")
    private String htmlUrl;
    @JsonConverterConfig(replaceWith = "state")
    private Status status;

    public PullRequest() {
    }

    public PullRequest(String htmlUrl, Status status) {
        this.htmlUrl = htmlUrl;
        this.status = status;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}