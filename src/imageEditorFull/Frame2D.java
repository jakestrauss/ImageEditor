package imageEditorFull;

public interface Frame2D {

	int getWidth();
	int getHeight();

	Pixel getPixel(int x, int y);
	Pixel getPixel(Coordinate c);

	Frame2D setPixel(int x, int y, Pixel p);
	Frame2D setPixel(Coordinate c, Pixel p);
	
	IndirectFrame2D extract(int xoff, int yoff, int width, int height);
	IndirectFrame2D extract(Coordinate a, Coordinate b);
	IndirectFrame2D extract(Region r);
	
	ObservableFrame2D createObservable();
	
	Frame2D copy();
}

