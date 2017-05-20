package imageEditorFull;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class PixelInspectorTool implements Tool {

	private PixelInspectorUI ui;
	private ImageEditorModel model;

	public PixelInspectorTool(ImageEditorModel model) {
		this.model = model;
		ui = new PixelInspectorUI(model);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		try {
			int mag = 13;
			ui.setInfo(e.getX(), e.getY(), model.getPixel(e.getX(), e.getY()));
			int xSel = e.getX();
			int ySel = e.getY();
			int dispLeft = xSel < mag ? xSel : mag;
			int dispRight = (model.getCurrent().getWidth() - 1 - xSel) < mag ? model.getCurrent().getWidth() - 1 - xSel
					: mag;
			int dispUp = ySel < mag ? ySel : mag;
			int dispDown = (model.getCurrent().getHeight() - 1 - ySel) < mag ? model.getCurrent().getHeight() - 1 - ySel
					: mag;
			MutableFrame2D sel = new MutableFrame2D(dispLeft + dispRight + 1, dispUp + dispDown + 1);
			Coordinate topLeft = new Coordinate(e.getX() - dispLeft, e.getY() - dispUp);
			for (int x = 0; x < sel.getWidth(); x++) {
				for (int y = 0; y < sel.getHeight(); y++) {
					sel.setPixel(x, y, model.getPixel(topLeft.getX() + x, topLeft.getY() + y));
				}
			}
			MutableFrame2D bigger = new MutableFrame2D(81, 81);
			for (int x = 0; x < bigger.getWidth(); x++) {
				for (int y = 0; y < bigger.getHeight(); y++) {
					int selX = 0;
					int selY = 0;
					for (int xMax = 3; xMax < bigger.getWidth(); xMax += (81 / 27)) {
						if (x < xMax)
							break;
						else
							selX++;
					}
					for (int yMax = 3; yMax < bigger.getHeight(); yMax += (81 / 27)) {
						if (y < yMax)
							break;
						else
							selY++;
					}
					bigger.setPixel(x, y, sel.getPixel(selX, selY));
				}
			}
			ui.setMagnified(bigger);
		} catch (Exception ex) {
			// Click may have been out of bounds. Do nothing in this case.
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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
	public String getName() {
		return "Pixel Inspector";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		try {
			int mag = 13;
			ui.setInfo(e.getX(), e.getY(), model.getPixel(e.getX(), e.getY()));
			int xSel = e.getX();
			int ySel = e.getY();
			int dispLeft = xSel < mag ? xSel : mag;
			int dispRight = (model.getCurrent().getWidth() - 1 - xSel) < mag ? model.getCurrent().getWidth() - 1 - xSel
					: mag;
			int dispUp = ySel < mag ? ySel : mag;
			int dispDown = (model.getCurrent().getHeight() - 1 - ySel) < mag ? model.getCurrent().getHeight() - 1 - ySel
					: mag;
			MutableFrame2D sel = new MutableFrame2D(dispLeft + dispRight + 1, dispUp + dispDown + 1);
			Coordinate topLeft = new Coordinate(e.getX() - dispLeft, e.getY() - dispUp);
			for (int x = 0; x < sel.getWidth(); x++) {
				for (int y = 0; y < sel.getHeight(); y++) {
					sel.setPixel(x, y, model.getPixel(topLeft.getX() + x, topLeft.getY() + y));
				}
			}
			MutableFrame2D bigger = new MutableFrame2D(81, 81);
			for (int x = 0; x < bigger.getWidth(); x++) {
				for (int y = 0; y < bigger.getHeight(); y++) {
					int selX = 0;
					int selY = 0;
					for (int xMax = 3; xMax < bigger.getWidth(); xMax += (81 / 27)) {
						if (x < xMax)
							break;
						else
							selX++;
					}
					for (int yMax = 3; yMax < bigger.getHeight(); yMax += (81 / 27)) {
						if (y < yMax)
							break;
						else
							selY++;
					}
					if (selX < sel.getWidth() && selY < sel.getHeight()) {
						bigger.setPixel(x, y, sel.getPixel(selX, selY));
					} else {
						bigger.setPixel(x, y, new ColorPixel(1, 1, 1));
					}
				}
			}

			ui.setMagnified(bigger);
		} catch (Exception ex) {
			// Click may have been out of bounds. Do nothing in this case.
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
