package com.scissor.hand;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * +---------------------------+
 * |I am the most handsome coding peasant.|
 * +---------------------------+
 * <p>Title: MyRunnable</p>
 * <p>Description: MyRunnable</p>
 * <p>Copyright:Copyright(c) coder 2018/p>
 * <p>Company: poor</p>
 * <p>CreateTime: 2020/3/21 16:41</p>
 * @author cb
 * @version 1.0
 **/
public class MyRunnable implements Runnable
{

    @FXML
    private VBox vBox;

    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(200);

    public MyRunnable(VBox vBox)
    {
        this.vBox = vBox;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                String data = queue.take();
                vBox.getChildren().add(new Label(data));
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}
