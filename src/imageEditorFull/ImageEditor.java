package imageEditorFull;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageEditor {
	public static void main(String[] args) throws IOException {
		Frame2D f = MutableFrame2D.readFromURL("https://s-media-cache-ak0.pinimg.com/originals/d4/3f/97/d43f97bff190cae66ccee6a8ec81bb13.jpg");

		JFrame main_frame = new JFrame();
		main_frame.setTitle("Image Editor by Jake Strauss");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageEditorModel model = new ImageEditorModel(f);
		ImageEditorView view = new ImageEditorView(main_frame, model);
 		ImageEditorController controller = new ImageEditorController(view, model);
		

		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		top_panel.add(view, BorderLayout.CENTER);
		main_frame.setContentPane(top_panel);

		main_frame.pack();
		main_frame.setVisible(true);
	}
}