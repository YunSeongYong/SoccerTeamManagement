import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;

public class DoctorSchedule extends JPanel {
	JTabbedPane pane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	public DoctorSchedule() {
		setLayout(null);

		JLabel lblNewLabel = new JLabel("공동일정");
		lblNewLabel.setBounds(63, 71, 48, 15);
		add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("시작 시간");
		lblNewLabel_1.setBounds(63, 154, 52, 15);
		add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(127, 151, 116, 21);
		add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("끝나는 시간");
		lblNewLabel_2.setBounds(255, 154, 64, 15);
		add(lblNewLabel_2);

		textField_1 = new JTextField();
		textField_1.setBounds(331, 151, 116, 21);
		add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("일정");
		lblNewLabel_3.setBounds(459, 157, 24, 15);
		add(lblNewLabel_3);

		textField_2 = new JTextField();
		textField_2.setBounds(495, 151, 364, 21);
		add(textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton = new JButton("등록");
		btnNewButton.setBounds(871, 150, 57, 23);
		add(btnNewButton);

		JLabel lblNewLabel_5 = new JLabel("코멘트");
		lblNewLabel_5.setBounds(75, 502, 36, 15);
		add(lblNewLabel_5);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(835, 16, 0, 0);
		add(lblNewLabel_4);

		JButton btnNewButton_1 = new JButton("수정");
		btnNewButton_1.setBounds(769, 206, 57, 23);
		add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("삭제");
		btnNewButton_2.setBounds(839, 206, 57, 23);
		add(btnNewButton_2);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setBounds(88, 206, 21, 23);
		add(chckbxNewCheckBox);
		
		JLabel lblNewLabel_6 = new JLabel("New label");
		lblNewLabel_6.setBounds(120, 202, 622, 31);
		add(lblNewLabel_6);
		
//		setSize(979,532);
		setSize(1164, 710);

	}
}
