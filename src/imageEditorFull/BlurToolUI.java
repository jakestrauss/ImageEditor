package imageEditorFull;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class BlurToolUI extends JPanel implements ActionListener {

	private JSlider blurSlider;
	private JLabel blurLabel;
	private JPanel blurSizePanel;
	private JSlider blurLevelSlider;
	private JPanel blurLevelPanel;
	private JButton clearButton;
	private JLabel clearLabel;
	private JPanel clearPanel;
	private boolean clear;
	private Dimension smallD;
	private Dimension largeD;

	public BlurToolUI() {
		setLayout(new GridLayout(0, 1));

		blurSizePanel = new JPanel();
		blurLabel = new JLabel("Blur Size: ");
		blurSizePanel.setLayout(new BorderLayout());
		blurSizePanel.add(blurLabel, BorderLayout.WEST);
		blurSlider = new JSlider(2, 20, 10);
		blurSlider.setSnapToTicks(true);
		blurSlider.setPaintTicks(true);
		blurSlider.setPaintLabels(true);
		blurSlider.setMajorTickSpacing(2);
		blurSizePanel.add(blurSlider, BorderLayout.CENTER);

		blurLevelPanel = new JPanel();
		JLabel blurLevelLabel = new JLabel("Degree of blur: ");
		blurLevelPanel.setLayout(new BorderLayout());
		blurLevelPanel.add(blurLevelLabel, BorderLayout.WEST);
		blurLevelSlider = new JSlider(1, 10, 5);
		blurLevelSlider.setSnapToTicks(true);
		blurLevelSlider.setPaintTicks(true);
		blurLevelSlider.setPaintLabels(true);
		blurLevelSlider.setMajorTickSpacing(1);
		blurLevelPanel.add(blurLevelSlider, BorderLayout.CENTER);

		clearPanel = new JPanel();
		clearPanel.setLayout(new BorderLayout());
		clearLabel = new JLabel("De-bluring brush currently OFF");
		clearButton = new JButton("Toggle de-bluring brush");
		clearButton.setActionCommand("erase");
		clearButton.addActionListener(this);
		clear = false;
		clearPanel.add(clearLabel, BorderLayout.WEST);
		clearPanel.add(clearButton, BorderLayout.EAST);
		
		this.add(blurSizePanel);
		this.add(blurLevelPanel);
		this.add(clearPanel);
	}

	public int getBlurSize() {
		return blurSlider.getValue();
	}

	public int getBlurLevel() {
		return blurLevelSlider.getValue();
	}

	public boolean getClearStatus() {
		return clear;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(clear) {
			clear = false;
			clearLabel.setText("De-bluring brush currently OFF");
			blurLabel.setText("Blur Size: ");
			this.remove(clearPanel);
			this.add(blurLevelPanel);
			this.add(clearPanel);
			this.setPreferredSize(new Dimension(397, 156));
		} else {
			clear = true;
			clearLabel.setText("De-bluring brush currently ON");
			blurLabel.setText("De-blur Size: ");
			this.remove(blurLevelPanel);
			this.setPreferredSize(new Dimension(397, 156));
		}
		
	}
}
