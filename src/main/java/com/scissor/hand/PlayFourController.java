package com.scissor.hand;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * +---------------------------+
 * |I am the most handsome coding peasant.|
 * +---------------------------+
 * <p>Title: PlayFourController</p>
 * <p>Description: PlayFourController</p>
 * <p>Copyright:Copyright(c) coder 2018/p>
 * <p>Company: poor</p>
 * <p>CreateTime: 2020/8/21 16:33</p>
 * @author cb
 * @version 1.0
 **/
public class PlayFourController
{
    @FXML
    private BorderPane oneBorderPane;
    @FXML
    private BorderPane twoBorderPane;
    @FXML
    private BorderPane threeBorderPane;
    @FXML
    private BorderPane fourBorderPane;


    public BorderPane getOneBorderPane()
    {
        return oneBorderPane;
    }

    public void setOneBorderPane(BorderPane oneBorderPane)
    {
        this.oneBorderPane = oneBorderPane;
    }

    public BorderPane getTwoBorderPane()
    {
        return twoBorderPane;
    }

    public void setTwoBorderPane(BorderPane twoBorderPane)
    {
        this.twoBorderPane = twoBorderPane;
    }

    public BorderPane getThreeBorderPane()
    {
        return threeBorderPane;
    }

    public void setThreeBorderPane(BorderPane threeBorderPane)
    {
        this.threeBorderPane = threeBorderPane;
    }

    public BorderPane getFourBorderPane()
    {
        return fourBorderPane;
    }

    public void setFourBorderPane(BorderPane fourBorderPane)
    {
        this.fourBorderPane = fourBorderPane;
    }
}
