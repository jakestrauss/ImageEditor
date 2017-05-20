package imageEditorFull;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PaintBrushToolUI extends JPanel implements ChangeListener, ActionListener {

	private JSlider red_slider;
	private JSlider green_slider;
	private JSlider blue_slider;
	private JSlider opacity_slider;
	private JSlider brush_slider;
	private FrameView color_preview;
	private JButton undoButton;
	private JButton eraseButton;
	private JLabel eraserLabel;
	private PaintBrushTool tool;
	private boolean erase;
	
	public PaintBrushToolUI(Pixel savedP) {
		setLayout(new GridLayout(0,1));
		
		JPanel color_chooser_panel = new JPanel();
		color_chooser_panel.setLayout(new BorderLayout());
		
		JPanel slider_panel = new JPanel();
		slider_panel.setLayout(new GridLayout(0,1));
		
		JPanel red_slider_panel = new JPanel();
		JLabel red_label = new JLabel("Red:");
		red_slider_panel.setLayout(new BorderLayout());
		red_slider_panel.add(red_label, BorderLayout.WEST);
		red_slider = new JSlider(0,100, (int)(savedP.getRed() * 100));
		red_slider.addChangeListener(this);
		red_slider_panel.add(red_slider, BorderLayout.CENTER);

		JPanel green_slider_panel = new JPanel();
		JLabel green_label = new JLabel("Green:");
		green_slider_panel.setLayout(new BorderLayout());
		green_slider_panel.add(green_label, BorderLayout.WEST);
		green_slider = new JSlider(0,100, (int)(savedP.getGreen() * 100));
		green_slider.addChangeListener(this);
		green_slider_panel.add(green_slider, BorderLayout.CENTER);

		JPanel blue_slider_panel = new JPanel();
		JLabel blue_label = new JLabel("Blue: ");
		blue_slider_panel.setLayout(new BorderLayout());
		blue_slider_panel.add(blue_label, BorderLayout.WEST);
		blue_slider = new JSlider(0,100, (int)(savedP.getBlue() * 100));
		blue_slider.addChangeListener(this);
		blue_slider_panel.add(blue_slider, BorderLayout.CENTER);
		
		JPanel opacity_slider_panel = new JPanel();
		JLabel opacity_label = new JLabel("Opacity: ");
		opacity_slider_panel.setLayout(new BorderLayout());
		opacity_slider_panel.add(opacity_label, BorderLayout.WEST);
		opacity_slider = new JSlider(0,100,100);
		opacity_slider.addChangeListener(this);
		opacity_slider_panel.add(opacity_slider, BorderLayout.CENTER);
		
		JPanel brush_slider_panel = new JPanel();
		JLabel brush_label = new JLabel("Brush Size: ");
		brush_slider_panel.setLayout(new BorderLayout());
		brush_slider_panel.add(brush_label, BorderLayout.WEST);
		brush_slider = new JSlider(1,10,5);
		brush_slider.setSnapToTicks(true);
		brush_slider.setPaintTicks(true);
		brush_slider.setPaintLabels(true);
		brush_slider.setMajorTickSpacing(1);
		brush_slider.addChangeListener(this);
		brush_slider_panel.add(brush_slider, BorderLayout.CENTER);

		// Assumes brush label is widest and asks red and blue label
		// to be the same.
		Dimension d = brush_label.getPreferredSize();
		red_label.setPreferredSize(d);
		blue_label.setPreferredSize(d);
		green_label.setPreferredSize(d);
		opacity_label.setPreferredSize(d);
		
		slider_panel.add(red_slider_panel);
		slider_panel.add(green_slider_panel);
		slider_panel.add(blue_slider_panel);
		slider_panel.add(brush_slider_panel);
		slider_panel.add(opacity_slider_panel);
		
		undoButton = new JButton("Undo");
		undoButton.setActionCommand("undo");
		undoButton.addActionListener(this);
		JPanel undoButtonPanel = new JPanel();
		undoButtonPanel.add(undoButton);
		
		JPanel eraserPanel = new JPanel();
		eraserPanel.setLayout(new BorderLayout());
		eraserLabel = new JLabel("Eraser currently OFF");
		eraseButton = new JButton("Toggle eraser brush");
		eraseButton.setActionCommand("erase");
		eraseButton.addActionListener(this);
		erase = false;
		eraserPanel.add(eraserLabel, BorderLayout.WEST);
		
		JPanel eraseButtonPanel = new JPanel();
		eraseButtonPanel.add(eraseButton);
		eraserPanel.add(eraseButtonPanel, BorderLayout.EAST);
		
		JPanel eraseAndUndoPanel = new JPanel();
		eraseAndUndoPanel.setLayout(new BorderLayout());
		eraseAndUndoPanel.add(undoButtonPanel, BorderLayout.EAST);
		eraseAndUndoPanel.add(eraserPanel, BorderLayout.WEST);

		color_chooser_panel.add(slider_panel, BorderLayout.CENTER);
		color_chooser_panel.add(eraseAndUndoPanel, BorderLayout.SOUTH);

		color_preview = new FrameView(new ObservableFrame2DImpl(60,60));
		color_chooser_panel.add(color_preview, BorderLayout.EAST);

		add(color_chooser_panel);
		
		stateChanged(null);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		Pixel p = new ColorPixel(red_slider.getValue()/100.0,
				                 green_slider.getValue()/100.0,
				                 blue_slider.getValue()/100.0);
		ObservableFrame2D preview_frame = color_preview.getFrame();
		preview_frame.suspendObservable();
		for (int x=0; x<preview_frame.getWidth(); x++) {
			for (int y=0; y<preview_frame.getHeight(); y++) {
				preview_frame.setPixel(x, y, p);
			}
		}
		preview_frame.resumeObservable();
	}
	
	public Pixel getBrushColor() {
		return color_preview.getFrame().getPixel(0,0);
	}
	
	public int getBrushSize() {
		return brush_slider.getValue();
	}
	
	//returns opacity as decimal
	public double getOpacity() {
		return opacity_slider.getValue() / 100.0;
	}
	
	public void setTool(PaintBrushTool tool) {
		this.tool = tool;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("undo")) {
			tool.undo();
		} else if (e.getActionCommand().equals("erase")) {
			if(erase) {
				erase = false;
				eraserLabel.setText("Eraser currently OFF");
			} else {
				erase = true;
				eraserLabel.setText("Eraser currently ON");
			}
		}
		
	}
	
	public void setColor(Pixel p) {
		red_slider.setValue((int)(p.getRed() * 100.0));
		blue_slider.setValue((int)(p.getGreen() * 100.0));
		green_slider.setValue((int)(p.getBlue() * 100.0));
	}
	
	public boolean getEraseStatus() {
		return erase;
	}
		
}
	

