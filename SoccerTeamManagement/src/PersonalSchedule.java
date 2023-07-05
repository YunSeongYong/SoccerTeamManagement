import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dbutil.DBUtil;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;

public class PersonalSchedule extends JFrame {
	public Player player;
	private JTextField startTxt;
	private JTextField endTxt;
	private JTextArea contentTxt;
	public PlayerTab playerTab;

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

		contentTxt = new JTextArea();
		contentTxt.setBounds(39, 89, 341, 129);
		getContentPane().add(contentTxt);
		contentTxt.setColumns(10);
		contentTxt.setLineWrap(true);

		JButton registerBtn = new JButton("등록");
		registerBtn.setBounds(185, 228, 66, 23);
		getContentPane().add(registerBtn);

		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedDate = playerTab.comboBox.getSelectedItem().toString();
				String startTime = startTxt.getText();
				String endTime = endTxt.getText();
				String content = contentTxt.getText();
				
				if (startTime.equals("") || content.equals("")) {
					JOptionPane.showMessageDialog(null, "시작 시간과 내용을 입력해주세요", "확인", JOptionPane.WARNING_MESSAGE);
				} else if (!timeFormat(startTime) || !timeFormat(endTime)) {
					JOptionPane.showMessageDialog(null, "시간을 정확히 입력해주세요", "확인", JOptionPane.WARNING_MESSAGE);
				} else if (startTime != "" && content != "" && timeFormat(startTime) && timeFormat(endTime)) {
					LocalTime startTimeParse = LocalTime.parse(startTxt.getText());
					LocalTime endTimeParse = LocalTime.parse(endTxt.getText());
					if (startTimeParse.isAfter(endTimeParse) || startTimeParse.equals(endTimeParse) || !timeFormat(startTime) || !timeFormat(endTime)) {
						JOptionPane.showMessageDialog(null, "시간을 정확히 입력해주세요", "확인", JOptionPane.WARNING_MESSAGE);
					} else {
						int number = player.getBackNumber();
						registerSchedule(number, selectedDate, startTxt.getText(), endTxt.getText(), contentTxt.getText(),
								"선수");
						addRecord();
						dispose();
					}
				}
			}
		});

		setSize(450, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
	}

	// 일정 등록 메소드
	private static void registerSchedule(int number, String date, String startTime, String endTime, String content,
			String who) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "INSERT INTO playerschedule (number, date, starttime, endtime, content, who) VALUES (?, ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, number);
			stmt.setString(2, date);
			stmt.setString(3, startTime);
			stmt.setString(4, endTime);
			stmt.setString(5, content);
			stmt.setString(6, who);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}
	
	// 24시간 시간 입력 정규표현식
	private static boolean timeFormat (String input) {
		Pattern p = Pattern.compile("^([01][0-9]|2[0-3]):([0-5][0-9])$");

		Matcher m = p.matcher(input);

		return m.matches();
	}


	public void addRecord() {
		JTable table = playerTab.table;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String[] record = new String[3];
		record[0] = startTxt.getText();
		record[1] = endTxt.getText();
		record[2] = contentTxt.getText();
		
		model.addRow(record);
	}

	public static void main(String[] args) {
		new PersonalSchedule();
	}
}
