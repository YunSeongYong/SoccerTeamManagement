import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PersonalSchedule extends JFrame{
	public Player player;
	private JTextField startTxt;
	private JTextField endTxt;
	private JTextField contentTxt;
	public PersonalSchedule() {
		player = new Player();
		
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("시작 시간");
		lblNewLabel.setBounds(39, 27, 57, 15);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("종료 시간");
		lblNewLabel_1.setBounds(209, 27, 57, 15);
		getContentPane().add(lblNewLabel_1);
		
		startTxt = new JTextField();
		startTxt.setBounds(103, 24, 94, 21);
		getContentPane().add(startTxt);
		startTxt.setColumns(10);
		
		endTxt = new JTextField();
		endTxt.setBounds(278, 24, 102, 21);
		getContentPane().add(endTxt);
		endTxt.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("내용");
		lblNewLabel_2.setBounds(39, 64, 35, 15);
		getContentPane().add(lblNewLabel_2);
		
		contentTxt = new JTextField();
		contentTxt.setBounds(39, 89, 341, 129);
		getContentPane().add(contentTxt);
		contentTxt.setColumns(10);
		
		JButton registerBtn = new JButton("등록");
		registerBtn.setBounds(185, 228, 66, 23);
		getContentPane().add(registerBtn);
		
//		registerBtn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int number = player.getBackNumber();
//
//				String selectedDate = comboBox.getSelectedItem().toString();
//
//				registerSchedule(number, selectedDate, startTxt.getText(), endTxt.getText(), contentTxt.getText(), "선수");
//			}
//		});
		
		setSize(450, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
