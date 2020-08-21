package com.scissor.hand;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;

/**
 * +---------------------------+
 * |I am the most handsome coding peasant.|
 * +---------------------------+
 * <p>Title: TestChen</p>
 * <p>Description: TestChen</p>
 * <p>Copyright:Copyright(c) coder 2018/p>
 * <p>Company: poor</p>
 * <p>CreateTime: 2020/8/17 15:15</p>
 * @author cb
 * @version 1.0
 **/
public class TestChen
{
    private static TestChen thisApp = null;

    private final JFrame frame;

    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public static void main(String[] args)
    {
        thisApp = new TestChen();
    }

    public TestChen()
    {
        frame = new JFrame("My First Media Player");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        frame.setContentPane(mediaPlayerComponent);

        frame.setVisible(true);

        mediaPlayerComponent.mediaPlayer().media().play("rtmp://frp.intellicloudai.com:20101/live/88426e8d0a1a3a49292710c0f46865dc");
    }

}
