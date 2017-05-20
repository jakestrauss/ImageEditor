package imageEditorFull;

public interface RegisteredROIObserver extends ROIObserver {

	Region getROI();
	ROIObserver getObserver();

}
