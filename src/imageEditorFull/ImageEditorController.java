package imageEditorFull;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ImageEditorController implements ToolChoiceListener, MouseListener, MouseMotionListener, ActionListener {

	private ImageEditorView view;
	private ImageEditorModel model;
	private Tool current_tool;
	private PixelInspectorTool inspector_tool;
	private PaintBrushTool paint_brush_tool;
	private BlurTool blur_tool;
	private JButton open_button;

	public ImageEditorController(ImageEditorView view, ImageEditorModel model) {
		this.view = view;
		this.model = model;
		this.open_button = view.getOpenButton();
		open_button.addActionListener(this);

		inspector_tool = new PixelInspectorTool(model);
		paint_brush_tool = new PaintBrushTool(model, ((PixelInspectorUI) (inspector_tool.getUI())).getSavedPixel());
		blur_tool = new BlurTool(model);

		view.addToolChoiceListener(this);
		view.addMouseListener(this);
		view.addMouseMotionListener(this);

		this.toolChosen(view.getCurrentToolName());
	}

	@Override
	public void toolChosen(String tool_name) {
		if (tool_name.equals("Pixel Inspector")) {
			view.installToolUI(inspector_tool.getUI());
			current_tool = inspector_tool;
		} else if (tool_name.equals("Paint Brush")) {
			Pixel p = ((PixelInspectorUI) (inspector_tool.getUI())).getSavedPixel();
			paint_brush_tool.setColor(p);
			view.installToolUI(paint_brush_tool.getUI());
			current_tool = paint_brush_tool;
		} else if (tool_name.equals("Blur Tool")) {
			view.installToolUI(blur_tool.getUI());
			current_tool = blur_tool;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		current_tool.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		current_tool.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		current_tool.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		current_tool.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		current_tool.mouseExited(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		current_tool.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		current_tool.mouseMoved(e);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean invalidURL = false;
		boolean picLoadSuccess = true;
		Frame2D loadedFrame = new MutableFrame2D(1, 1);
		do {
			String url = JOptionPane.showInputDialog("Enter URL of picture");
			try {
				loadedFrame = MutableFrame2D.readFromURL(url);
				invalidURL = false;
			} catch (IOException e1) {
				System.out.println("Invalid URL");
				int choice = JOptionPane.showConfirmDialog(null,
						"Invalid URL. Would you like to try to enter URL again?", "Invalid URL",
						JOptionPane.YES_NO_OPTION);
				if (choice == JOptionPane.YES_OPTION) {
					invalidURL = true;
				} else {
					invalidURL = false;
					picLoadSuccess = false;
				}
			}
		} while (invalidURL);
		if (picLoadSuccess) {
			model = view.setFrame(loadedFrame);
			inspector_tool = new PixelInspectorTool(model);
			paint_brush_tool = new PaintBrushTool(model, ((PixelInspectorUI) (inspector_tool.getUI())).getSavedPixel());
			blur_tool = new BlurTool(model);

			view.addToolChoiceListener(this);
			view.addMouseListener(this);
			view.addMouseMotionListener(this);

			this.toolChosen(view.getCurrentToolName());
		}
	}

}
