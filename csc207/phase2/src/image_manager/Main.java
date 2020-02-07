package image_manager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Serializer;

/** The entry point of the whole program. */
public class Main extends Application {
  /**
   * The main methods to start this application.
   *
   * @param args the args.
   */
  public static void main(String[] args) {
    launch(args);
    Serializer.saveTo("finder");
    Serializer.saveTo("tags");
  }

  /**
   * The primary stage of this application.
   *
   * @param primaryStage the stage.
   * @throws Exception the io exception.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("../view/FileChooser.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setTitle("Image Manager 1.5");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
