package kg.zensoft.pulls.service.json;


import kg.zensoft.pulls.model.PullRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomPullRequestJsonConverter implements TextDataExchangeFormatConverter {

    @Override
    public <T> String toString(T t) {
        return null;
    }

    @Override
    public <T, E> T toObject(String json, Class<E> tClass) {
        List<PullRequest> pullRequests = new ArrayList<>();
        getAttributeValue(json, "html_url").forEach(htmlUrl -> {
            pullRequests.add(new PullRequest(htmlUrl, null));
        });
        return (T) pullRequests;
    }

    @Override
    public List<String> getAttributeValue(String json, String attributeName) {
        List<String> attributeValues = new ArrayList<>();
        String jsonRegex = "\"(" + attributeName + ")\":\"(\\S+?\")";
        Pattern datePatt = Pattern.compile(jsonRegex);
        Matcher m = datePatt.matcher(json);
        while (m.find()) {
            String value = m.group(2);
            value = value.replaceAll("\"", "");
            if (value.contains("pull")) {
                attributeValues.add(value);
            }
        }
        return attributeValues;
    }
}
