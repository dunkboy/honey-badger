package com.scissor.hand;

import com.alibaba.fastjson.JSON;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.scissor.hand.SecondaryController.SHOPPING_CART;
import static com.scissor.hand.SecondaryController.VIDEOS_LIST;
import static com.scissor.hand.SecondaryController.updateData;
import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;


public class PrimaryController
{

    @FXML
    private Button add;

    @FXML
    private Button play;

    @FXML
    private Button restore;

    @FXML
    private VBox vBox;

    @FXML
    private VBox vBox2;

    @FXML
    private TabPane tabPane;

    /**
     * 模态框
     */
    public static Stage stage = null;

    private final MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();

    public PrimaryController()
    {
    }

    public void restore() throws IOException
    {
        initVBOX();
        File file = new File("db.json");
        if (file.exists())
        {
            VIDEOS_LIST.clear();
            SecondaryController.VIDEOS.clear();
            SecondaryController.RESOURCE.clear();
            SecondaryController.SHOPPING_CART.clear();
            vBox.getChildren().clear();
            vBox2.getChildren().clear();

            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            org.apache.commons.io.IOUtils.copy(fis, baos);
            List<Video> videos = JSON.parseArray(new String(baos.toByteArray(), "UTF-8"), Video.class);
            SecondaryController.VIDEOS_LIST.addAll(videos);
            for (Video video : videos)
            {
                SecondaryController.VIDEOS.put(video.getName(), createVideoData());
                SecondaryController.RESOURCE.put(video.getName(), video);
                updateData(video.getName());
            }
        }
    }

    public void add() throws IOException
    {
        initVBOX();
        //弹出添加模态框
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("secondary.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("添加视频资源");
        stage.show();
    }

    private void initVBOX()
    {
        if (SecondaryController.vBox == null)
        {
            SecondaryController.vBox = vBox;
        }
        if (SecondaryController.vBox2 == null)
        {
            SecondaryController.vBox2 = vBox2;
        }
    }

    @FXML
    public void play() throws IOException
    {
        if (SecondaryController.SHOPPING_CART.size() == 0)
        {
            return;
        }
        //单画面
        if (SecondaryController.SHOPPING_CART.size() == 1 && tabPane.getSelectionModel().isSelected(0))
        {
            String key = SHOPPING_CART.keySet().iterator().next();
            VideoData videoData = SecondaryController.VIDEOS.get(key);
            stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("play1.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("honey-badger");
            PlayOneController controller = fxmlLoader.getController();
            stage.setOnCloseRequest((windowEvent) -> {
                if (videoData.getEmbeddedMediaPlayer().status().isPlaying())
                {
                    videoData.getEmbeddedMediaPlayer().controls().stop();
                    controller.getOneBorderPane().setCenter(null);
                }
            });
            stage.show();
            videoData.getVideoImageView().setFitWidth(controller.getOneBorderPane().getWidth());
            videoData.getVideoImageView().setFitHeight(controller.getOneBorderPane().getHeight());
            controller.getOneBorderPane().setCenter(videoData.getVideoImageView());
            videoData.getEmbeddedMediaPlayer().media().play(SecondaryController.RESOURCE.get(key).getResource());
        }
        //四宫格
        if (SecondaryController.SHOPPING_CART.size() > 1 && SecondaryController.SHOPPING_CART.size() <= 4 && tabPane.getSelectionModel().isSelected(1))
        {
            Set<String> keySet = SHOPPING_CART.keySet();
            List<VideoData> list = new ArrayList<>(keySet.size());
            for (String key : keySet)
            {
                list.add(SecondaryController.VIDEOS.get(key));
            }
            stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("play4.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("honey-badger");
            PlayFourController controller = fxmlLoader.getController();
            stage.setOnCloseRequest((windowEvent) -> {
                for (VideoData videoData : list)
                {
                    if (videoData.getEmbeddedMediaPlayer().status().isPlaying())
                    {
                        videoData.getEmbeddedMediaPlayer().controls().stop();
                    }
                }
                controller.getOneBorderPane().setCenter(null);
                controller.getTwoBorderPane().setCenter(null);
                controller.getThreeBorderPane().setCenter(null);
                controller.getFourBorderPane().setCenter(null);
            });
            stage.show();

            int i = 0;
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext())
            {
                String key = iterator.next();
                VideoData videoData = SecondaryController.VIDEOS.get(key);
                if (i == 0)
                {
                    videoData.getVideoImageView().setFitWidth(controller.getOneBorderPane().getWidth());
                    videoData.getVideoImageView().setFitHeight(controller.getOneBorderPane().getHeight());
                    controller.getOneBorderPane().setCenter(videoData.getVideoImageView());
                    videoData.getEmbeddedMediaPlayer().media().play(SecondaryController.RESOURCE.get(key).getResource());
                }
                if (i == 1)
                {
                    videoData.getVideoImageView().setFitWidth(controller.getTwoBorderPane().getWidth());
                    videoData.getVideoImageView().setFitHeight(controller.getTwoBorderPane().getHeight());
                    controller.getTwoBorderPane().setCenter(videoData.getVideoImageView());
                    videoData.getEmbeddedMediaPlayer().media().play(SecondaryController.RESOURCE.get(key).getResource());
                }
                if (i == 2)
                {
                    videoData.getVideoImageView().setFitWidth(controller.getThreeBorderPane().getWidth());
                    videoData.getVideoImageView().setFitHeight(controller.getThreeBorderPane().getHeight());
                    controller.getThreeBorderPane().setCenter(videoData.getVideoImageView());
                    videoData.getEmbeddedMediaPlayer().media().play(SecondaryController.RESOURCE.get(key).getResource());
                }
                if (i == 3)
                {
                    videoData.getVideoImageView().setFitWidth(controller.getFourBorderPane().getWidth());
                    videoData.getVideoImageView().setFitHeight(controller.getFourBorderPane().getHeight());
                    controller.getFourBorderPane().setCenter(videoData.getVideoImageView());
                    videoData.getEmbeddedMediaPlayer().media().play(SecondaryController.RESOURCE.get(key).getResource());
                }
                i++;
            }
        }
//        //九宫格
//        if (SecondaryController.SHOPPING_CART.size() > 4 && tabPane.getSelectionModel().isSelected(2))
//        {
//            SHOPPING_CART.forEach((k, v) -> {
//                VideoData videoData = SecondaryController.VIDEOS.get(k);
//                videoData.getVideoImageView().setFitWidth(oneBorderPane.getWidth());
//                videoData.getVideoImageView().setFitHeight(oneBorderPane.getHeight());
//                oneBorderPane.setCenter(videoData.getVideoImageView());
//                videoData.getEmbeddedMediaPlayer().media().play(SecondaryController.RESOURCE.get(k).getResource());
//            });
//        }
    }

    private VideoData createVideoData()
    {
        VideoData videoData = new VideoData();
        EmbeddedMediaPlayer embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();

        ImageView videoImageView = new ImageView();
        videoImageView.setPreserveRatio(true);
        embeddedMediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter()
        {
            @Override
            public void playing(MediaPlayer mediaPlayer)
            {
            }

            @Override
            public void paused(MediaPlayer mediaPlayer)
            {
            }

            @Override
            public void stopped(MediaPlayer mediaPlayer)
            {
            }

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime)
            {
            }
        });
        embeddedMediaPlayer.videoSurface().set(videoSurfaceForImageView(videoImageView));
//        oneBorderPane.widthProperty().addListener((observableValue, oldValue, newValue) -> {
//            // If you need to know about resizes
//        });
//        oneBorderPane.heightProperty().addListener((observableValue, oldValue, newValue) -> {
//            // If you need to know about resizes
//        });
        videoData.setEmbeddedMediaPlayer(embeddedMediaPlayer);
        videoData.setVideoImageView(videoImageView);
        return videoData;
    }

}
