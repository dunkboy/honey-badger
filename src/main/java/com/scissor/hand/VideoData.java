package com.scissor.hand;

import javafx.scene.image.ImageView;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * +---------------------------+
 * |I am the most handsome coding peasant.|
 * +---------------------------+
 * <p>Title: VideoData</p>
 * <p>Description: VideoData</p>
 * <p>Copyright:Copyright(c) coder 2018/p>
 * <p>Company: poor</p>
 * <p>CreateTime: 2020/8/17 18:30</p>
 * @author cb
 * @version 1.0
 **/
public class VideoData
{

    private EmbeddedMediaPlayer embeddedMediaPlayer;

    private ImageView videoImageView;

    public EmbeddedMediaPlayer getEmbeddedMediaPlayer()
    {
        return embeddedMediaPlayer;
    }

    public void setEmbeddedMediaPlayer(EmbeddedMediaPlayer embeddedMediaPlayer)
    {
        this.embeddedMediaPlayer = embeddedMediaPlayer;
    }

    public ImageView getVideoImageView()
    {
        return videoImageView;
    }

    public void setVideoImageView(ImageView videoImageView)
    {
        this.videoImageView = videoImageView;
    }
}
