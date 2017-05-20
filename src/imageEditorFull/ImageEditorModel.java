package imageEditorFull;


public class ImageEditorModel {

	private Frame2D original;
	private ObservableFrame2D current;

	public ImageEditorModel(Frame2D f) {
		original = f;
		current = original.copy().createObservable();
	}

	public ObservableFrame2D getCurrent() {
		return current;
	}

	public Pixel getPixel(int x, int y) {
		return current.getPixel(x, y);
	}

	public void paintAt(int x, int y, Pixel brushColor, int brush_size, double opacity) {
		current.suspendObservable();

		for (int xpos = x - brush_size + 1; xpos <= x + brush_size - 1; xpos++) {
			for (int ypos = y - brush_size + 1; ypos <= y + brush_size - 1; ypos++) {
				if (xpos >= 0 && xpos < current.getWidth() && ypos >= 0 && ypos < current.getHeight()) {
					Pixel orig = original.getPixel(xpos, ypos);
					double redNew = (brushColor.getRed() * opacity) + (orig.getRed() * (1 - opacity));
					double greenNew = (brushColor.getGreen() * opacity) + (orig.getGreen() * (1 - opacity));
					double blueNew = (brushColor.getBlue() * opacity) + (orig.getBlue() * (1 - opacity));
					current.setPixel(xpos, ypos, new ColorPixel(redNew, greenNew, blueNew));
				}
			}
		}
		current.resumeObservable();
		
	}
	
	public Frame2D getOriginal() {
		return original;
	}

}
