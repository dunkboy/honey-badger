package com.scissor.hand;


import com.alibaba.fastjson.JSON;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.commons.lang3.StringUtils;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

public class SecondaryController
{
    public static final String[] OPTIONS = {
            "--quiet",
            "--quiet-synchro",
            "--intf",
            "dummy"
    };

    private static final String MRL = "screen://";
    private static final String SOUT = ":sout=#transcode{vcodec=FLV1,vb=%d,scale=%f}:duplicate{dst=file{dst=%s}}";
    private static final String FPS = ":screen-fps=%d";
    private static final String CACHING = ":screen-caching=%d";

    private static final int fps = 20;
    private static final int caching = 500;
    private static final int bits = 4096;
    private static final float scale = 0.5f;


    @FXML
    private Button confirm;

    @FXML
    private TextField resource;

    @FXML
    private TextField name;

    private final MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(OPTIONS);

    public static final Map<String, VideoData> VIDEOS = new HashMap<>(20);
    public static final Map<String, Video> RESOURCE = new HashMap<>(20);
    public static final Map<String, Text> SHOPPING_CART = new HashMap<>(20);
    public static final List<Video> VIDEOS_LIST = new ArrayList<>(20);

    public static VBox vBox = null;
    public static VBox vBox2 = null;

    public void confirm() throws IOException
    {
        String nameText = name.getText();
        String resourceText = resource.getText();
        if (StringUtils.isBlank(nameText) || StringUtils.isBlank(resourceText))
        {
            return;
        }
        saveDB(nameText, resourceText);
        updateData(nameText);

        PrimaryController.stage.close();
        PrimaryController.stage = null;
    }

    private static String[] getMediaOptions(String destination)
    {
        return new String[]{
                String.format(SOUT, bits, scale, destination),
                String.format(FPS, fps),
                String.format(CACHING, caching)
        };
    }

    public static void updateData(String nameText)
    {
        //更新主线程数据
        Label label = new Label(nameText);
        ContextMenu contextMenu = new ContextMenu();
        // 菜单项
//        MenuItem capture = new MenuItem("捕获");
//        capture.setOnAction(actionEvent -> {
//            VideoData videoData = VIDEOS.get(nameText);
//            if (videoData.getEmbeddedMediaPlayer().status().isPlaying())
//            {
//                videoData.getEmbeddedMediaPlayer().controls().stop();
//                String[] options = {":sout=#transcode{vcodec=mp4v,vb=4096,scale=1,acodec=mpga,ab=128,channels=2,samplerate=44100}:duplicate{dst=file{dst=" + UUID.randomUUID().toString() + ".mp4" + "},dst=display}", ":input-slave=alsa://hw:0,0"};
//                videoData.getEmbeddedMediaPlayer().media().play(SecondaryController.RESOURCE.get(nameText).getResource(), options);
//            }
//        });
        // 菜单项
        MenuItem screenshot = new MenuItem("截图");
        screenshot.setOnAction(actionEvent -> {
            VideoData videoData = VIDEOS.get(nameText);
            if (videoData.getEmbeddedMediaPlayer().status().isPlaying())
            {
                videoData.getEmbeddedMediaPlayer().snapshots().save(new File(UUID.randomUUID().toString() + "-" + DateUtils.formatNow(DateUtils.TIME_FORMAT_2) + ".png"));
            }
        });
        MenuItem play = new MenuItem("播放");
        play.setOnAction(actionEvent -> {
            VideoData videoData = VIDEOS.get(nameText);
            if (!videoData.getEmbeddedMediaPlayer().status().isPlaying())
            {
                videoData.getEmbeddedMediaPlayer().controls().play();
            }
        });
        MenuItem stop = new MenuItem("停止");
        stop.setOnAction(actionEvent -> {
            VideoData videoData = VIDEOS.get(nameText);
            if (videoData.getEmbeddedMediaPlayer().status().isPlaying())
            {
                videoData.getEmbeddedMediaPlayer().controls().stop();
            }
        });
        MenuItem pause = new MenuItem("暂停");
        pause.setOnAction(actionEvent -> {
            VideoData videoData = VIDEOS.get(nameText);
            if (videoData.getEmbeddedMediaPlayer().status().isPlaying())
            {
                videoData.getEmbeddedMediaPlayer().controls().pause();
            }
        });
        // 右键菜单===================================================================
        // 添加右键菜单到label
        contextMenu.getItems().addAll(screenshot, play, stop, pause);
        label.setContextMenu(contextMenu);

        label.setOnMouseClicked(mouseEvent -> {
            //单击
            if (mouseEvent.getClickCount() == 1 && mouseEvent.getButton() == MouseButton.PRIMARY)
            {
                //购物车是否存在
                if (!SHOPPING_CART.containsKey(nameText))
                {
                    Text text = new Text(nameText);
                    text.setOnMouseClicked(mouseEvent1 -> {
                        //删除购物车
                        if (mouseEvent1.getClickCount() == 2 && mouseEvent1.getButton() == MouseButton.SECONDARY)
                        {
                            Platform.runLater(() -> vBox2.getChildren().remove(text));
//                            if (VIDEOS.get(nameText).getEmbeddedMediaPlayer().status().isPlaying())
//                            {
//                                VIDEOS.get(nameText).getEmbeddedMediaPlayer().controls().stop();
//                            }
                            SHOPPING_CART.remove(nameText);
                        }
                    });
                    SHOPPING_CART.put(nameText, text);
                    Platform.runLater(() -> vBox2.getChildren().add(text));
                }
            }
            //双击 删除
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.SECONDARY)
            {
                Platform.runLater(() -> vBox.getChildren().remove(label));
                Text text = SHOPPING_CART.get(nameText);
                Platform.runLater(() -> vBox2.getChildren().remove(text));
                SHOPPING_CART.remove(nameText);
                try
                {
                    File fileMod = new File("db.json");
                    Video video2 = RESOURCE.get(nameText);
                    VIDEOS_LIST.remove(video2);
                    String jsonString = JSON.toJSONString(VIDEOS_LIST);
                    writeData(fileMod, jsonString);
                    RESOURCE.remove(nameText);
                    VideoData videoData1 = VIDEOS.get(nameText);
                    if (videoData1.getEmbeddedMediaPlayer().status().isPlaying())
                    {
                        videoData1.getEmbeddedMediaPlayer().controls().stop();
                    }
                    VIDEOS.remove(nameText);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
        Platform.runLater(() -> vBox.getChildren().add(label));
    }

    private void saveDB(String nameText, String resourceText) throws IOException
    {
        //存文本数据
        Video video = new Video();
        video.setName(nameText);
        video.setResource(resourceText);
        VIDEOS_LIST.add(video);
        String jsonString = JSON.toJSONString(VIDEOS_LIST);
        File file = new File("db.json");
        if (!file.exists())
        {
            file.createNewFile();
            writeData(file, jsonString);
        }
        else
        {
//            FileInputStream fis = new FileInputStream(file);
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            org.apache.commons.io.IOUtils.copy(fis, baos);
//            List<Video> videos = JSON.parseArray(new String(baos.toByteArray(), "UTF-8"), Video.class);
//            fis.close();
//            baos.close();
            writeData(file, jsonString);
        }

        //创建vlc
        VideoData videoData = createVideoData();
        VIDEOS.put(nameText, videoData);
        RESOURCE.put(nameText, video);
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
//        videoImageView.fitWidthProperty().bind(oneBorderPane.widthProperty());
//        videoImageView.fitHeightProperty().bind(oneBorderPane.heightProperty());
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

    public static void writeData(File file, String jsonString) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(file);
        ByteArrayInputStream bais = new ByteArrayInputStream(jsonString.getBytes("UTF-8"));
        org.apache.commons.io.IOUtils.copy(bais, fos);
        bais.close();
        fos.close();
    }

}