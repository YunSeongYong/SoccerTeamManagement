import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class PlayerList {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerList window = new PlayerList();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PlayerList() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton 개인정보버튼 = new JButton("개인정보");
		개인정보버튼.setBounds(43, 78, 110, 42);
		frame.getContentPane().add(개인정보버튼);
		
		JButton 일정버튼 = new JButton("일정");
		일정버튼.setBounds(43, 161, 110, 42);
		frame.getContentPane().add(일정버튼);
		
		JButton 컨디션버튼 = new JButton("컨디션");
		컨디션버튼.setBounds(43, 279, 110, 42);
		frame.getContentPane().add(컨디션버튼);
		
		JButton 의사소견버튼 = new JButton("의사소견");
		의사소견버튼.setBounds(43, 392, 110, 42);
		frame.getContentPane().add(의사소견버튼);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(437, 51, 103, 21);
		frame.getContentPane().add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(725, 51, 110, 21);
		frame.getContentPane().add(comboBox_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(246, 126, 671, 368);
		frame.getContentPane().add(panel);
		
		JLabel 날짜라벨 = new JLabel("날짜");
		날짜라벨.setBounds(353, 54, 57, 15);
		frame.getContentPane().add(날짜라벨);
		
		JLabel 선수정보라벨 = new JLabel("선수정보");
		선수정보라벨.setBounds(656, 54, 57, 15);
		frame.getContentPane().add(선수정보라벨);
	}
}
