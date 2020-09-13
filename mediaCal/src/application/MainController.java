package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;



public class MainController implements Initializable {
	@FXML
    private MediaView mv;
	private MediaPlayer mp;
	private Media me;
	
	@FXML
	private Label result;
	private long number1=0;
	private String operator="";
	private boolean start=true;
	private Model model=new Model();
	
	public void initialize(URL arg0, ResourceBundle resources) {
		String path=new File("src/media/123.mp4").getAbsolutePath();
		me=new Media(new File(path).toURI().toString());
		mp=new MediaPlayer(me);
		mv.setMediaPlayer(mp);
		mp.setAutoPlay(true);
		DoubleProperty width=mv.fitWidthProperty();
		DoubleProperty height=mv.fitHeightProperty();
		width.bind(Bindings.selectDouble(mv.sceneProperty(),"width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(),"height"));
		
		
	}
	public void play(ActionEvent event) {
		mp.play();
	}
	public void pause(ActionEvent event) {
		mp.pause();
	}
	public void fast(ActionEvent event) {
		mp.setRate(2);
	}
	public void slow(ActionEvent event) {
		mp.setRate(.5);
	}
	public void reload(ActionEvent event) {
		mp.seek(mp.getStartTime());
		mp.play();
	}
	public void start(ActionEvent event) {
		mp.seek(mp.getStartTime());
		mp.stop();
	}
	public void last(ActionEvent event) {
		mp.seek(mp.getTotalDuration());
		mp.stop();
	}
	
	
	@FXML
	public void processNumbers(ActionEvent event) {
		if(start) {
			result.setText("");
			start=false;
		}
		String value=((Button)event.getSource()).getText();
		result.setText(result.getText()+value);
		
	}
	
	
	@FXML
	public void processOperators(ActionEvent event) {
		String value=((Button)event.getSource()).getText();
		if(!value.equals("=")) {
			if(!operator.isEmpty())
				return;
			
			operator=value;
			number1=Long.parseLong(result.getText());
			result.setText("");
		}else {
			if(operator.isEmpty())
				return;
			long number2=Long.parseLong(result.getText());
			float output=model.calculate(number1, number2, operator);
			result.setText(String.valueOf(output));
			operator="";
			start=true;
		}
		
	}

}
