package imageEditorFull;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class BlurTool implements Tool {

	private BlurToolUI ui;
	private ImageEditorModel model;
	private int blurSize;
	private Frame2D frame;

	public BlurTool(ImageEditorModel model) {
		blurSize = 1;
		this.model = model;
		ui = new BlurToolUI();
		this.frame = model.getCurrent();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		blurSize = ui.getBlurSize();
		int blur = ui.getBlurLevel();
		for (int x = e.getX() - blurSize + 1; x <= e.getX() + blurSize - 1; x++) {
			for (int y = e.getY() - blurSize + 1; y <= e.getY() + blurSize - 1; y++) {

				double totalInAvg = 0;
				double redSum = 0;
				double greenSum = 0;
				double blueSum = 0;
				for (int xOff = -blur; xOff <= blur; xOff++) {
					for (int yOff = -blur; yOff <= blur; yOff++) {
						if ((x + xOff) >= 0 && (x + xOff) < frame.getWidth() && (y + yOff) >= 0
								&& (y + yOff) < frame.getHeight() && (xOff != 0 || yOff != 0)) {
							redSum += frame.getPixel(x + xOff, y + yOff).getRed();
							blueSum += frame.getPixel(x + xOff, y + yOff).getBlue();
							greenSum += frame.getPixel(x + xOff, y + yOff).getGreen();
							totalInAvg++;
						}
					}
				}
				double newRed = redSum / totalInAvg;
				double newGreen = greenSum / totalInAvg;
				double newBlue = blueSum / totalInAvg;
				if(ui.getClearStatus() == false) {
					model.paintAt(x, y, new ColorPixel(newRed, newGreen, newBlue), 1, 1);
				} else if(x >= 0 && x < frame.getWidth() && y >= 0 && y < frame.getHeight()){
					model.paintAt(x, y, frame.getPixel(x, y), 1, 0);
				}
			}
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
	public void mouseDragged(MouseEvent e) {
		blurSize = ui.getBlurSize();
		int blur = ui.getBlurLevel();
		for (int x = e.getX() - blurSize + 1; x <= e.getX() + blurSize - 1; x++) {
			for (int y = e.getY() - blurSize + 1; y <= e.getY() + blurSize - 1; y++) {

				double totalInAvg = 0;
				double redSum = 0;
				double greenSum = 0;
				double blueSum = 0;
				for (int xOff = -blur; xOff <= blur; xOff++) {
					for (int yOff = -blur; yOff <= blur; yOff++) {
						if ((x + xOff) >= 0 && (x + xOff) < frame.getWidth() && (y + yOff) >= 0
								&& (y + yOff) < frame.getHeight() && (xOff != 0 || yOff != 0)) {
							redSum += frame.getPixel(x + xOff, y + yOff).getRed();
							blueSum += frame.getPixel(x + xOff, y + yOff).getBlue();
							greenSum += frame.getPixel(x + xOff, y + yOff).getGreen();
							totalInAvg++;
						}
					}
				}
				double newRed = redSum / totalInAvg;
				double newGreen = greenSum / totalInAvg;
				double newBlue = blueSum / totalInAvg;
				if(ui.getClearStatus() == false) {
					model.paintAt(x, y, new ColorPixel(newRed, newGreen, newBlue), 1, 1);
				} else if(x >= 0 && x < frame.getWidth() && y >= 0 && y < frame.getHeight()) {
					model.paintAt(x, y, frame.getPixel(x, y), 1, 0);
				}
			}
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Blur Tool";
	}

	@Override
	public JPanel getUI() {
		return ui;
	}

}
