/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qa.com.qic.anoud.vo;
import java.util.List;
public class FeedBackInfoBO {

    private String id;
    private String question;
    private List<KeyValue> options;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<KeyValue> getOptions() {
        return options;
    }

    public void setOptions(List<KeyValue> options) {
        this.options = options;
    }
}
