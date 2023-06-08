package main_screen;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import mydatabase.MySQLConnect;



public class MyBackgroundPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4647310868286988256L;

	/**
	 * Create the panel.
	 */

	String[] areas = { "", "ATTIC", "BACKYARD","BATH1", "BATH2","BDRM1","BDRM2","BDRM3","BDRM4",
			"FRONTYARD", "GARAGE", "HALLWAY", "KITCHEN","LIVING ROOM", "ROOF"};
	String[] items = { "", "ANTENNA", "CHIMNEY", "CLOSET","DISHWASHER","DOOR","DRYER", "FAN",
			"FENCE", "FLOOR", "FRIG", "GUTTERS", "INTERNET", "LIGHTS","MIRROR", 
			"MISC",  "OUTLETS","OVEN", "PLANTS", "SEWAGE LINE", "SHOWER/TUB", "SINK","TILE",  
			"TOILET","TV", "WALLS","WASHER",  "WINDOWS"};
	private BufferedImage img;
	private BufferedImage scaled;
	//private final static String inputFile = "cost.csv";
	//public final static String credentialsFilename = "mysignonstuff.txt" ;
	MySQLConnect myDatabase;
	private final String BACKGROUND_PIC = "ourHouse2022.gif";
	private final String HOMEIMPROVEMENT_DATABASE = "houseexpenses";//database that stores past locker information

	public MyBackgroundPanel(MySQLConnect mySQLDatabase) {
		this.myDatabase = mySQLDatabase;
		setupPanel();

	}

	public static void main(String[] args) {

		/*
		 * JFrame frame = new JFrame();
		 * 
		 * frame.setTitle("Home Improvement Tasks Completed");
		 * frame.getContentPane().setLayout(new BorderLayout());
		 * //frame.add(myBackgroundPanel, BorderLayout.CENTER);
		 * frame.getContentPane().add(new MyBackgroundPanel()); frame.pack();
		 * frame.setVisible(true); frame.setResizable(false);
		 */
	}

	private void setupPanel() {
		// TODO Auto-generated method stub

		setLayout(null);

		try {
			setBackground(ImageIO.read(new File(BACKGROUND_PIC)));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"Page Load Fault: Can not find background image"  );
		}

		JLabel lblHomeImprovementRecords = new JLabel("Home Improvement Records");
		lblHomeImprovementRecords.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomeImprovementRecords.setBounds(120, 11, 360, 29);
		lblHomeImprovementRecords.setFont(new Font("Tahoma", Font.BOLD, 24));
		add(lblHomeImprovementRecords);
		
		
		
		
		

		JLabel lblDate = new JLabel("DATE");
		lblDate.setBounds(10, 63, 70, 25);
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDate.setForeground(Color.RED);
		add(lblDate);

		JLabel lblArea = new JLabel("AREA");
		lblArea.setBounds(150, 63, 70, 25);
		lblArea.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblArea.setForeground(Color.BLUE);
		add(lblArea);

		JLabel lblItems = new JLabel("ITEMS");
		lblItems.setBounds(287, 63, 70, 25);
		lblItems.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblItems.setForeground(Color.RED);
		add(lblItems);

		JLabel lblCost = new JLabel("COST");
		lblCost.setBounds(417, 63, 70, 25);
		lblCost.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblCost.setForeground(Color.BLUE);
		add(lblCost);

		JLabel lblReceipt = new JLabel("RECEIPT");
		lblReceipt.setBounds(507, 65, 80, 21);
		lblReceipt.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblReceipt.setForeground(Color.RED);
		add(lblReceipt);

		JDateChooser dateChooserDay = new JDateChooser();
		dateChooserDay.setBounds(10, 88, 105, 31);
		Date date = new Date();
		dateChooserDay.setDate(date);
		dateChooserDay.setDateFormatString("yyyy-MM-dd");
		dateChooserDay.setBackground(Color.RED);
		add(dateChooserDay);

		JComboBox<Object> comboBoxAREA = new JComboBox<Object>();
		comboBoxAREA.setBounds(130, 88, 105, 31);
		comboBoxAREA.setModel(new DefaultComboBoxModel<Object>( areas));
		comboBoxAREA.setEditable(false);
		add(comboBoxAREA);

		JComboBox<Object> comboBoxITEMS = new JComboBox<Object>();
		comboBoxITEMS.setBounds(260, 88, 105, 31);
		comboBoxITEMS.setModel(new DefaultComboBoxModel<Object>( items));
		comboBoxITEMS.setEditable(false);
		add(comboBoxITEMS);

		JButton btnReceipt = new JButton("ADD RECEIPT");
		btnReceipt.setBounds(490, 88, 133, 31);
		add(btnReceipt);

		JTextField textFieldCost = new JTextField();
		textFieldCost.setBounds(390, 88, 86, 31);
		textFieldCost.setColumns(10);
		add(textFieldCost);

		JLabel lblFilename = new JLabel("filename");
		lblFilename.setBounds(507, 118, 105, 25);
		lblFilename.setForeground(Color.YELLOW);
		lblFilename.setFont(new Font("Tahoma", Font.BOLD, 12));
		add(lblFilename);

		JLabel lblAddInfo = new JLabel("ADDITIONAL INFORMATION");
		lblAddInfo.setBounds(10, 205, 250, 35);
		lblAddInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAddInfo.setForeground(Color.BLUE);
		add(lblAddInfo);

		JCheckBox chckbxValue = new JCheckBox("Value+");
		chckbxValue.setBounds(266, 211, 97, 23);
		chckbxValue.setFont(new Font("Tahoma", Font.BOLD, 16));
		chckbxValue.setSelected(false);
		chckbxValue.setBackground(Color.WHITE);
		add(chckbxValue);

		JTextArea textAreaInfo = new JTextArea();
		Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
		textAreaInfo.setBorder(border);
		textAreaInfo.setOpaque(false);
		textAreaInfo.setWrapStyleWord(true);
		textAreaInfo.setFont(new Font("Arial", Font.BOLD, 13));
		textAreaInfo.setToolTipText("Type additional Information here");
		textAreaInfo.setBounds(10, 238, 500, 60);
		add(textAreaInfo);

		JButton btnAccept = new JButton("ACCEPT");
		btnAccept.setBounds(261, 309, 89, 31);
		btnAccept.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(btnAccept);


		JFrame frame = new JFrame();
		//Listeners Code
		btnReceipt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				File file = null;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				int option = fileChooser.showOpenDialog(frame);
				if(option == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
					lblFilename.setText(file.getName().toString());;

				}
			}
		});

		btnAccept.addActionListener(new ActionListener() {


			public void actionPerformed(ActionEvent e) {

				//final String inputFile = "cost.csv";
				Date selectedDate = dateChooserDay.getDate();
				String pattern = "yyyy-MM-dd";
				DateFormat formatter = new SimpleDateFormat(pattern);
				String date = (formatter.format(selectedDate));
				Boolean checked;
				if(chckbxValue.isSelected()) {
					checked = true;
				}else {
					checked = false;
				}

				String query = "INSERT INTO " + HOMEIMPROVEMENT_DATABASE +
						" (DATE, AREA, ITEMS, COST, FILENAME, INFO, VALUE)\n" + "VALUES ('" +
						date + "', '" + comboBoxAREA.getSelectedItem().toString() + "' ,'" +
						comboBoxITEMS.getSelectedItem().toString()+ "', " +
						textFieldCost.getText().toString() + ", '" + lblFilename.getText() + "', '"
						+textAreaInfo.getText() + "', " + checked + ")" ;


				
				try {
					if(myDatabase.insertData(query)) {
						JOptionPane.showMessageDialog(null, "Data Sent To Server" );
						comboBoxAREA.setSelectedIndex(0);
						comboBoxITEMS.setSelectedIndex(0);
						textFieldCost.setText("");
						lblFilename.setText("filename");
						textAreaInfo.setText("");
					}else {
						String message = "Data was not written to file. \n "
								+"Check for the following: \n"
								+ "1} Internet Connection is present. \n"
								+ "2) All fields are populated with data. \n"
								+ "3) The cost.csv file is close.";
						JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);		
					}

				}catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
					String message = "Data was not written to file. \n "
							+"Check for proper COST field input format (ex 124.55) ";
					JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);

				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// HomeMainGui.getMonthlyExpenses(false);
				
				
				/*

						try {

							if(HomeMainGui.placeInFile(new HomeData(date, comboBoxAREA.getSelectedItem().toString(),  comboBoxITEMS.getSelectedItem().toString(),  
									Double.parseDouble(textFieldCost.getText().toString()), 
							        lblFilename.getText(),textAreaInfo.getText(), chckbxValue.isSelected()) )) {		
								List<HomeData> list = HomeMainGui.getDataFromFile(); 

								//overwrite old data in file with list content
								if(HomeMainGui.replaceDataInFile(list, inputFile)) {
								JOptionPane.showMessageDialog(null, "Data Sent To File" );
								 comboBoxAREA.setSelectedIndex(0);
								 comboBoxITEMS.setSelectedIndex(0);
								 textFieldCost.setText("");
								 lblFilename.setText("filename");
								 textAreaInfo.setText("");
								 HomeMainGui.getMonthlyExpenses(false);

								}	 
							} else {

								String message = "Data was not written to file. \n "
										+"Check for the following: \n"
										+ "1} Internet Connection is present. \n"
										+ "2) All fields are populated with data. \n"
										+ "3) The cost.csv file is close.";
								JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);						
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							//e1.printStackTrace();
							String message = "Data was not written to file. \n "
									+"Check for proper COST field input format (ex 124.55) ";
							JOptionPane.showMessageDialog(null, message, "Input Error", JOptionPane.ERROR_MESSAGE);

						} catch (HeadlessException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				 */
			}

		});
	}



	@Override
	public Dimension getPreferredSize() {
		return img == null ? super.getPreferredSize() : new Dimension(img.getWidth(), img.getHeight());
	}

	public void setBackground(BufferedImage value) {
		if (value != img) {
			this.img = value;
			repaint();
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
		if (getWidth() > img.getWidth() || getHeight() > img.getHeight()) {
			scaled = getScaledInstanceToFill(img, getSize());
		} else {
			scaled = img;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (scaled != null) {
			int x = (getWidth() - scaled.getWidth()) / 2;
			int y = (getHeight() - scaled.getHeight()) / 2;
			g.drawImage(scaled, x, y, this);
		}
	}

	public static BufferedImage getScaledInstanceToFill(BufferedImage img, Dimension size) {

		double scaleFactor = getScaleFactorToFill(img, size);

		return getScaledInstance(img, scaleFactor);

	}

	public static double getScaleFactorToFill(BufferedImage img, Dimension size) {

		double dScale = 1;

		if (img != null) {

			int imageWidth = img.getWidth();
			int imageHeight = img.getHeight();

			double dScaleWidth = getScaleFactor(imageWidth, size.width);
			double dScaleHeight = getScaleFactor(imageHeight, size.height);

			dScale = Math.max(dScaleHeight, dScaleWidth);

		}

		return dScale;

	}

	public static double getScaleFactor(int iMasterSize, int iTargetSize) {

		double dScale = (double) iTargetSize / (double) iMasterSize;

		return dScale;

	}

	public static BufferedImage getScaledInstance(BufferedImage img, double dScaleFactor) {
		return getScaledInstance(img, dScaleFactor, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);

	}



	protected static BufferedImage getScaledInstance(BufferedImage img, double dScaleFactor, Object hint, boolean bHighQuality) {

		BufferedImage imgScale = img;

		int iImageWidth = (int) Math.round(img.getWidth() * dScaleFactor);
		int iImageHeight = (int) Math.round(img.getHeight() * dScaleFactor);
		if (dScaleFactor <= 1.0d) {
			imgScale = getScaledDownInstance(img, iImageWidth, iImageHeight, hint, bHighQuality);
		} else {
			imgScale = getScaledUpInstance(img, iImageWidth, iImageHeight, hint, bHighQuality);
		}

		return imgScale;

	}
	protected static BufferedImage getScaledDownInstance(BufferedImage img,
			int targetWidth,
			int targetHeight,
			Object hint,
			boolean higherQuality) {

		int type = (img.getTransparency() == Transparency.OPAQUE)
				? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

		BufferedImage ret = (BufferedImage) img;
		if (targetHeight > 0 || targetWidth > 0) {
			int w, h;
			if (higherQuality) {
				// Use multi-step technique: start with original size, then
				// scale down in multiple passes with drawImage()
				// until the target size is reached
				w = img.getWidth();
				h = img.getHeight();
			} else {
				// Use one-step technique: scale directly from original
				// size to target size with a single drawImage() call
				w = targetWidth;
				h = targetHeight;
			}

			do {
				if (higherQuality && w > targetWidth) {
					w /= 2;
					if (w < targetWidth) {
						w = targetWidth;
					}
				}

				if (higherQuality && h > targetHeight) {
					h /= 2;
					if (h < targetHeight) {
						h = targetHeight;
					}
				}

				BufferedImage tmp = new BufferedImage(Math.max(w, 1), Math.max(h, 1), type);
				Graphics2D g2 = tmp.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
				g2.drawImage(ret, 0, 0, w, h, null);
				g2.dispose();

				ret = tmp;
			} while (w != targetWidth || h != targetHeight);
		} else {
			ret = new BufferedImage(1, 1, type);
		}
		return ret;
	}

	protected static BufferedImage getScaledUpInstance(BufferedImage img,
			int targetWidth,
			int targetHeight,
			Object hint,
			boolean higherQuality) {

		int type = BufferedImage.TYPE_INT_ARGB;

		BufferedImage ret = (BufferedImage) img;
		int w, h;
		if (higherQuality) {
			// Use multi-step technique: start with original size, then
			// scale down in multiple passes with drawImage()
			// until the target size is reached
			w = img.getWidth();
			h = img.getHeight();
		} else {
			// Use one-step technique: scale directly from original
			// size to target size with a single drawImage() call
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w < targetWidth) {
				w *= 2;
				if (w > targetWidth) {
					w = targetWidth;
				}
			}

			if (higherQuality && h < targetHeight) {
				h *= 2;
				if (h > targetHeight) {
					h = targetHeight;
				}
			}

			BufferedImage tmp = new BufferedImage(w, h, type);
			Graphics2D g2 = tmp.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
			tmp = null;

		} while (w != targetWidth || h != targetHeight);
		return ret;
	}


}
