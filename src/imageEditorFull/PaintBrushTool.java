package imageEditorFull;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PaintBrushTool implements Tool, ROIObserver {

	private PaintBrushToolUI ui;
	private ImageEditorModel model;
	private Frame2D lastChanged;
	private ObservableFrame2D actualCurrent;
	private int brush_size;
	private ArrayList<ArrayList<ChangedPixel>> allChanges;
	private int changeInd = 0;

	public PaintBrushTool(ImageEditorModel model, Pixel p) {
		brush_size = 5;
		this.model = model;
		this.lastChanged = model.getOriginal();
		ui = new PaintBrushToolUI(p);
		allChanges = new ArrayList<ArrayList<ChangedPixel>>();
		model.getCurrent().registerROIObserver(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		ArrayList<ChangedPixel> changes = new ArrayList<ChangedPixel>();
		ui.setTool(this);
		brush_size = ui.getBrushSize();
		for (int x = e.getX() - brush_size + 1; x <= e.getX() + brush_size - 1; x++) {
			for (int y = e.getY() - brush_size + 1; y <= e.getY() + brush_size - 1; y++) {
				if (x >= 0 && x < model.getCurrent().getWidth() && y >= 0 && y < model.getCurrent().getHeight()) {
					changes.add(new ChangedPixel(new Coordinate(x, y), lastChanged.getPixel(x, y)));
				}
			}
		}

		allChanges.add(changeInd, changes);
		if(ui.getEraseStatus() == false) {
			model.paintAt(e.getX(), e.getY(), ui.getBrushColor(), brush_size, ui.getOpacity());
		} else {
			model.paintAt(e.getX(), e.getY(), ui.getBrushColor(), brush_size, 0);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		lastChanged = actualCurrent;
		changeInd++;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		brush_size = ui.getBrushSize();
		for (int x = e.getX() - brush_size + 1; x <= e.getX() + brush_size - 1; x++) {
			for (int y = e.getY() - brush_size + 1; y <= e.getY() + brush_size - 1; y++) {
				if (x >= 0 && x < model.getCurrent().getWidth() && y >= 0 && y < model.getCurrent().getHeight()) {
					

					ArrayList<ChangedPixel> changes = allChanges.get(changeInd);
					boolean unique = true;
					for (ChangedPixel c : changes) {
						if ((c.getCoordinate().getX() == x) && (c.getCoordinate().getY() == y)) {
							unique = false;
							break;
						}
					}
					if (unique)
						allChanges.get(changeInd)
								.add(new ChangedPixel(new Coordinate(x, y), lastChanged.getPixel(x, y)));

				}
			}
		}
		if(ui.getEraseStatus() == false) {
			model.paintAt(e.getX(), e.getY(), ui.getBrushColor(), brush_size, ui.getOpacity());
		} else {
			model.paintAt(e.getX(), e.getY(), ui.getBrushColor(), brush_size, 0);
		}
	}	

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Paint Brush";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

	public void setBrushSize(int brush_size) {
		this.brush_size = brush_size;
	}
	
	public void setColor(Pixel p) {
		ui.setColor(p);
	}

	public ArrayList<ArrayList<ChangedPixel>> getAllChanges() {
		return allChanges;
	}

	public ArrayList<ChangedPixel> removeChange() {
		return allChanges.remove(allChanges.size() - 1);
	}

	public void undo() {
		if (allChanges.size() > 0) {
			ArrayList<ChangedPixel> changes = allChanges.remove(allChanges.size() - 1);
			changeInd--;
			for (ChangedPixel c : changes) {
				model.paintAt(c.getCoordinate().getX(), c.getCoordinate().getY(), c.getPixel(), 1, 1);
			}
			lastChanged = actualCurrent;
		}
	}

	public void setModel(ImageEditorModel model) {
		this.model = model;
	}

	@Override
	public void notify(ObservableFrame2D frame, Region r) {
		this.actualCurrent = frame;
	}

}
