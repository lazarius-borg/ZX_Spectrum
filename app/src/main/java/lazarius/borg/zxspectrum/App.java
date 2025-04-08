package lazarius.borg.zxspectrum.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lazarius.borg.emulator.*;

import java.io.File;

public class App extends Application {

    private ZXEmulator emulator;
    private VideoOutput videoOutput;
    private KeyboardInput keyboardInput;
    private TapeInput tapeInput;

    @Override
    public void start(Stage primaryStage) {
        emulator = new ZXEmulator();
        videoOutput = new VideoOutput();
        keyboardInput = new KeyboardInput(emulator.getCPU());
        tapeInput = new TapeInput(emulator.getMemory(), emulator.getCPU());

        BorderPane root = new BorderPane();

        // Video Output Screen
        VBox videoBox = new VBox();
        videoBox.setStyle("-fx-background-color: black; -fx-min-height: 400px; -fx-min-width: 600px;");
        root.setCenter(videoBox);

        // Keyboard Input
        HBox keyboardBox = new HBox();
        for (char c = 'A'; c <= 'Z'; c++) {
            Button keyButton = new Button(String.valueOf(c));
            keyButton.setOnAction(e -> keyboardInput.processKeyPress(c));
            keyboardBox.getChildren().add(keyButton);
        }
        root.setBottom(keyboardBox);

        // Tape Recorder Controls
        VBox tapeBox = new VBox();
        Button loadTapeButton = new Button("Load Tape");
        loadTapeButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File tapeFile = fileChooser.showOpenDialog(primaryStage);
            if (tapeFile != null) {
                try {
                    tapeInput.loadTape(tapeFile.getAbsolutePath());
                } catch (Exception ex) {
                    System.err.println("Error loading tape: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

        Button playTapeButton = new Button("Play Tape");
        playTapeButton.setOnAction(e -> {
            try {
                tapeInput.playTape();
            } catch (Exception ex) {
                System.err.println("Error playing tape: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        Button stopTapeButton = new Button("Stop Tape");
        stopTapeButton.setOnAction(e -> tapeInput.stopTape());

        tapeBox.getChildren().addAll(loadTapeButton, playTapeButton, stopTapeButton);
        root.setRight(tapeBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("ZX Spectrum Emulator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
