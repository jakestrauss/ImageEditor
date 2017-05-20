package imageEditorFull;

public class ChangedPixel {
	
	private Coordinate c;
	private Pixel p;
	
	public ChangedPixel(Coordinate c, Pixel p) {
		this.c = c;
		this.p = p;
	}
	
	public Coordinate getCoordinate() {
		return c;
	}
	
	public Pixel getPixel() {
		return p;
	}
}
