package imageEditorFull;

abstract public class AnyFrame2D implements Frame2D {

	abstract public int getWidth();
	abstract public int getHeight();

	abstract public Pixel getPixel(int x, int y);
	
	@Override
	public Pixel getPixel(Coordinate c) {
		if (c == null) {
			throw new IllegalArgumentException("Coordinate is null");
		}
		return this.getPixel(c.getX(), c.getY());
	}

	abstract public Frame2D setPixel(int x, int y, Pixel p);
	
	@Override
	public Frame2D setPixel(Coordinate c, Pixel p) {
		if (c == null) {
			throw new IllegalArgumentException("Coordinate is null");
		}
		return this.setPixel(c.getX(), c.getY(), p);
	}
	
	@Override
	public IndirectFrame2D extract(int xoff, int yoff, int width, int height) {
		return new IndirectFrame2DImpl(this, xoff, yoff, width, height);
	}

	@Override 
	public IndirectFrame2D extract(Coordinate corner_a, Coordinate corner_b) {
		if (corner_a == null || corner_b == null) {
			throw new IllegalArgumentException("One or both coordinates is null");
		}
		
		int min_x = corner_a.getX() < corner_b.getX() ? corner_a.getX() : corner_b.getX();
		int min_y = corner_a.getY() < corner_b.getY() ? corner_a.getY() : corner_b.getY();
		int max_x = corner_a.getX() > corner_b.getX() ? corner_a.getX() : corner_b.getX();
		int max_y = corner_a.getY() > corner_b.getY() ? corner_a.getY() : corner_b.getY();
		
		return extract(min_x, min_y, (max_x-min_x)+1, (max_y-min_y)+1);
	}
	
	@Override
	public IndirectFrame2D extract(Region r) {
		if (r == null) {
			throw new IllegalArgumentException("Region is null");
		}

		return extract(r.getUpperLeft(), r.getLowerRight());
	}
	
	@Override
	public ObservableFrame2D createObservable() {
		return new ObservableFrame2DImpl(this);
	}
	
	protected Region getFrameRegion() {
		return new Region(new Coordinate(0,0), new Coordinate(getWidth()-1, getHeight()-1));
	}
	
	@Override
	public Frame2D copy() {
		Frame2D copy = new MutableFrame2D(getWidth(), getHeight());
		for (int x=0; x<getWidth(); x++) {
			for (int y=0; y<getHeight(); y++) {
				copy = copy.setPixel(x, y, getPixel(x,y));
			}
		}
		return copy;
	}

}
