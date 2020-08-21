package com.scissor.hand;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * +---------------------------+
 * |I am the most handsome coding peasant.|
 * +---------------------------+
 * <p>Title: PlayOneController</p>
 * <p>Description: PlayOneController</p>
 * <p>Copyright:Copyright(c) coder 2018/p>
 * <p>Company: poor</p>
 * <p>CreateTime: 2020/8/21 16:01</p>
 * @author cb
 * @version 1.0
 **/
public class PlayOneController
{
    @FXML
    private BorderPane oneBorderPane;


    public BorderPane getOneBorderPane()
    {
        return oneBorderPane;
    }

    public void setOneBorderPane(BorderPane oneBorderPane)
    {
        this.oneBorderPane = oneBorderPane;
    }
}
