package eus.ehu.bummer4;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;




public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField authorName;

    @FXML
    private TextField dateText;

    @FXML
    private WebView tootView;


    @FXML
    private CheckBox boosted_;

    private int index;
    private int index_not_boosted;

    private List<Status> toots;

    @FXML
    void nextButton(ActionEvent event) {

        if (!boosted_.isSelected()) {
            index_not_boosted = getNextMineToot();
            index = index_not_boosted;
            ActionMine();
        }
        else {
            if (index != toots.size() - 1) {
                index++;
            }
            Action();
        }

    }

    @FXML
    void previousButton(ActionEvent event) {

        if (!boosted_.isSelected()) {
            index_not_boosted = getPreviousMineToot();
            index = index_not_boosted;
            ActionMine();
        }
        else {
            if (index != 0) {
                index--;
            }
            Action();
        }


    }

    @FXML
    void initialize() {


        String id = "109897302633891405";
        index = 0;
        index_not_boosted = 0;
        //String id = "109842111446764244";
        Gson gson = new Gson();
        String body = Utils.request("accounts/" + id + "/statuses");
        JsonArray jsonArray = gson.fromJson(body, JsonArray.class);
        Type statusList = new TypeToken<ArrayList<Status>>() {}.getType();
        toots = gson.fromJson(jsonArray.getAsJsonArray(), statusList);
        index_not_boosted = getNextMineToot();
        ActionMine();
    }

    void Action() {

        dateText.setText(toots.get(index).created_at);
        if (toots.get(index).content.isEmpty()) {
            authorName.setText(toots.get(index).reblog.account.username);
            tootView.getEngine().loadContent(toots.get(index).reblog.content);
        } else {
            authorName.setText(toots.get(index).account.username);
            tootView.getEngine().loadContent(toots.get(index).content);
        }

    }

    void ActionMine() {

        dateText.setText(toots.get(index_not_boosted).created_at);
        if (toots.get(index_not_boosted).content.isEmpty()) {
            authorName.setText(toots.get(index_not_boosted).reblog.account.username);
            tootView.getEngine().loadContent(toots.get(index_not_boosted).reblog.content);
        } else {
            authorName.setText(toots.get(index).account.username);
            tootView.getEngine().loadContent(toots.get(index_not_boosted).content);
        }

    }

    int getNextMineToot() {
        for (int i = index_not_boosted + 1; i < toots.size(); i++) {
            if (!toots.get(i).content.isEmpty()) {
                return i;
            }
        }
        return index_not_boosted;
    }

    int getPreviousMineToot() {
        for (int i = index_not_boosted - 1; i >= 0; i--) {
            if (!toots.get(i).content.isEmpty()) {
                return i;
            }
        }
        return index_not_boosted;
    }

}
