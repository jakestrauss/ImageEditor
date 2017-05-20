package imageEditorFull;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ImageEditorView extends JPanel {

	private JFrame main_frame;
	private FrameView frame_view;
	private ImageEditorModel model;
	private ToolChooserWidget chooser_widget;
	private JPanel tool_ui_panel;
	private JPanel tool_ui;
	private JButton open_button;
	
	public ImageEditorView(JFrame main_frame, ImageEditorModel model) {
		this.main_frame = main_frame;
		
		setLayout(new BorderLayout());
		
		frame_view = new FrameView(model.getCurrent());
		
		add(frame_view, BorderLayout.CENTER);
		
		tool_ui_panel = new JPanel();
		tool_ui_panel.setLayout(new BorderLayout());
		
		chooser_widget = new ToolChooserWidget();
		tool_ui_panel.add(chooser_widget, BorderLayout.NORTH);
		
		JPanel open_button_panel = new JPanel();
		open_button = new JButton("Open new picture from URL");
		open_button_panel.add(open_button);
		
		JPanel tool_and_open_panel = new JPanel();
		tool_and_open_panel.setLayout(new BorderLayout());
		tool_and_open_panel.add(tool_ui_panel, BorderLayout.NORTH);
		tool_and_open_panel.add(open_button_panel, BorderLayout.SOUTH);
		add(tool_and_open_panel, BorderLayout.SOUTH);
		
		tool_ui = null;
	}

	public void addToolChoiceListener(ToolChoiceListener l) {
		chooser_widget.addToolChoiceListener(l);
	}
	
	public String getCurrentToolName() {
		return chooser_widget.getCurrentToolName();
	}

	public JButton getOpenButton() {
		return open_button;
	}
	
	public void installToolUI(JPanel ui) {
		if (tool_ui != null) {
			tool_ui_panel.remove(tool_ui);
		}
		tool_ui = ui;
		tool_ui_panel.add(tool_ui, BorderLayout.CENTER);
		validate();
		main_frame.pack();
	}
	
	@Override
	public void addMouseMotionListener(MouseMotionListener l) {
		frame_view.addMouseMotionListener(l);
	}
	
	@Override
	public void removeMouseMotionListener(MouseMotionListener l) {
		frame_view.removeMouseMotionListener(l);
	}

	@Override
	public void addMouseListener(MouseListener l) {
		frame_view.addMouseListener(l);
	}
	
	public void removeMouseListener(MouseListener l) {
		frame_view.removeMouseListener(l);
	}
	
	public ImageEditorModel setFrame(Frame2D f) {
		remove(frame_view);
		
		ImageEditorModel model = new ImageEditorModel(f);
		frame_view = new FrameView(model.getCurrent());
		
		
		add(frame_view, BorderLayout.CENTER);
		validate();
		main_frame.pack();
		return model;
	}
	

}
