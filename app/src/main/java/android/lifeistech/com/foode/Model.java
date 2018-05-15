package android.lifeistech.com.foode;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kawamuradaisuke on 2018/05/14.
 */

public class Model {


    @SerializedName("html_attributions")
    private String[] html_attributions;

    @SerializedName("next_page_token")
    private String next_page_token;

    @SerializedName("results")
    private List<Result> results;

    @SerializedName("status")
    private String status;

    public String[] getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
