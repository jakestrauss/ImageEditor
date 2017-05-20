package imageEditorFull;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PixelInspectorUI extends JPanel implements ActionListener {

	private JLabel x_label;
	private JLabel y_label;
	private JLabel magnifiedLabel;
	private JLabel pixel_info;
	private JPanel magGlass;
	private JPanel grid;
	private JButton save_pixel_color;
	private Pixel currentP;
	private Pixel savedP;
	private ImageEditorModel model;
	private FrameView magnified;
	
	public PixelInspectorUI(ImageEditorModel model) {
		this.model = model;
		x_label = new JLabel("X: ");
		y_label = new JLabel("Y: ");
		pixel_info = new JLabel("(r,g,b)");
		save_pixel_color = new JButton("Save Color for Paintbrush");
		save_pixel_color.setActionCommand("save");
		save_pixel_color.addActionListener(this);
		savedP = new ColorPixel(.5, .5, .5);
		magGlass = new JPanel();
		magGlass.setLayout(new BorderLayout());
		magnifiedLabel = new JLabel("Selected Area Magnified: ");
		magnified = new FrameView(new ObservableFrame2DImpl(81, 81));
		magGlass.add(magnifiedLabel, BorderLayout.NORTH);
		magGlass.add(magnified, BorderLayout.CENTER);
		
		grid = new JPanel();
		setLayout(new BorderLayout());
		grid.setLayout(new GridLayout(2,2));
		grid.add(x_label);
		grid.add(y_label);
		grid.add(pixel_info);
		grid.add(save_pixel_color);
		add(grid, BorderLayout.NORTH);
		add(magGlass, BorderLayout.CENTER);
	}
	
	public void setInfo(int x, int y, Pixel p) {
		x_label.setText("X: " + x);
		y_label.setText("Y: " + y);
		pixel_info.setText(String.format("(%3.2f, %3.2f, %3.2f)", p.getRed(), p.getBlue(), p.getGreen()));	
		currentP = p;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("save")) {
			savedP = currentP;
		}
	}
	
	public Pixel getSavedPixel() {
		return savedP;
	}
	
	public void setMagnified(Frame2D f) {
		magnified.setFrame(f.createObservable());
	}
}
