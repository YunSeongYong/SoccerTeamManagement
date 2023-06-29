import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import dbutil.DBUtil;
import java.awt.Component;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

public class StaffRegistration extends JFrame implements ChangeListener {
	JTabbedPane pane;
	private JLabel lblNewLabel;
	private JTextField numTxt;
	private JTextField nameTxt;
	private JTextField textField_2;
	private JTextField sickTxt;
	JComboBox<String> comboBox;
	JComboBox<LocalDate> comboBox_1;
	private JTable table;
	private DefaultTableModel tableModel;
	private List<DoctorAppointment> list2 = new ArrayList<>();
	private JTextField timeTxt;

	public List<DoctorAppointment> selectList(String doctor) {
		list2 = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM appointment where doctor = ?;");
			stmt.setString(1, doctor);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int number = rs.getInt("number");
				String name = rs.getString("name");
				String date = rs.getString("date");
				String time = rs.getString("time");
				String condition = rs.getString("condition");
				String doctor2 = rs.getString("doctor");

				System.out.printf("%d, %s, %s, %s, %s, %s", number, name, time, date, condition, doctor);
				list2.add(new DoctorAppointment(number, name, date, time, condition, doctor));
				System.out.println(list2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list2;
	}

	public void doctorSchedule(List<DoctorAppointment> list2) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		// Clear the existing table data
		tableModel.setRowCount(0);

		// Add the appointments to the table
		for (DoctorAppointment appointment : list2) {
			Object[] rowData = { appointment.getBacknumber(), appointment.getPlayerName(), appointment.getDate(),
					appointment.getTime(), appointment.getCondition(), appointment.getDoctor() };
			tableModel.addRow(rowData);
		}
	}

	public void displayDoctorSchedule() {
		String selectedDate = comboBox_1.getSelectedItem().toString();
		String selectedDoctor = comboBox.getSelectedItem().toString();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			// Prepare the SQL query to retrieve appointments for the selected date and
			// doctor
			String query = "SELECT * FROM appointment WHERE date = ? AND doctor = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, selectedDate);
			stmt.setString(2, selectedDoctor);

			rs = stmt.executeQuery();

			list2.clear(); // Clear the existing list of appointments

			while (rs.next()) {
				int number = rs.getInt("number");
				String name = rs.getString("name");
				String date = rs.getString("date");
				String time = rs.getString("time");
				String condition = rs.getString("condition");
				String doctor = rs.getString("doctor");

				// Print appointment details for testing
				System.out.printf("%d %s %s %s %s %s\n", number, name, date, time, condition, doctor);

				// Add the appointment to the list
				list2.add(new DoctorAppointment(number, name, date, time, condition, doctor));
			}

			doctorSchedule(list2); // Update the table with the retrieved appointments

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void displaySchedule() {
		String selectedDate = comboBox_1.getSelectedItem().toString();
		String selectedDoctor = comboBox.getSelectedItem().toString();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			// Prepare the SQL query to retrieve appointments for the selected date and
			// doctor
			String query = "SELECT * FROM appointment WHERE date = ? AND doctor = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, selectedDate);
			stmt.setString(2, selectedDoctor);

			rs = stmt.executeQuery();

			list2.clear(); // Clear the existing list of appointments

			while (rs.next()) {
				int number = rs.getInt("number");
				String name = rs.getString("name");
				String date = rs.getString("date");
				String time = rs.getString("time");
				String condition = rs.getString("condition");
				String doctor = rs.getString("doctor");

				// Print appointment details for testing
				System.out.printf("%d %s %s %s %s %s\n", number, name, date, time, condition, doctor);

				// Add the appointment to the list
				list2.add(new DoctorAppointment(number, name, date, time, condition, doctor));
			}

			doctorSchedule(list2); // Update the table with the retrieved appointments

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void insertAppointment() {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();

			// 의사 콤보박스에서 선택된 값을 가져옵니다
			String selectedDoctor = (String) comboBox.getSelectedItem();

			stmt = conn.prepareStatement(
					"insert into appointment(number, name, date, time,`condition`, doctor) values (?, ?, ?, ?, ?, ?)");
			stmt.setInt(1, Integer.valueOf(numTxt.getText()));
			stmt.setString(2, nameTxt.getText());
			stmt.setString(3, textField_2.getText());
			stmt.setString(4, timeTxt.getText());
			stmt.setString(5, sickTxt.getText());
			stmt.setString(6, selectedDoctor); // 선택된 의사 값을 바인딩합니다

			stmt.executeUpdate();

			// 예약 추가 후 테이블을 갱신합니다
			selectList(selectedDoctor);
			doctorSchedule(list2);

			// 예약 후 텍스트 필드들을 빈 칸으로 초기화
			numTxt.setText("");
			nameTxt.setText("");
			textField_2.setText("");
			sickTxt.setText("");
			timeTxt.setText("");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void box() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("select name from staff where role = ?");
			stmt.setString(1, "의사");
			rs = stmt.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				comboBox.addItem(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public StaffRegistration() {
		JPanel one, two;
		pane = new JTabbedPane();
		pane.setBounds(0, 104, 984, 457);

		one = new JPanel();
		one.setLayout(null);
		pane.addTab("선수목록", one);

		two = new JPanel();
		pane.addTab("예약하기", two);
		two.setLayout(null);

		// Create a JTable instance
		table = new JTable();

		// Create a DefaultTableModel
		tableModel = new DefaultTableModel(new Object[][] {},
				new String[] { "\uB4F1\uBC88\uD638", "\uC120\uC218\uC774\uB984", "\uB0A0\uC9DC", "\uC2DC\uAC04",
						"\uBCD1\uBA85", "\uB2F4\uB2F9\uC758\uC0AC" });

		// Set the table model to the table
		table.setModel(tableModel);

		// Create a JScrollPane and add the table to it
		JScrollPane scrolledTable = new JScrollPane(table);
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrolledTable.setBounds(64, 38, 803, 221);
		two.add(scrolledTable);

		// ===========================================================================================

		comboBox = new JComboBox<>();
		comboBox.setBounds(331, 7, 98, 21);
		two.add(comboBox);
		box();

		JLabel lblNewLabel_1 = new JLabel("의사");
		lblNewLabel_1.setBounds(290, 13, 29, 15);
		two.add(lblNewLabel_1);

		comboBox_1 = new JComboBox<>();
		comboBox_1.setBounds(147, 10, 98, 21);
		two.add(comboBox_1);

		LocalDate currentDate = LocalDate.now();
		LocalDate minusDate = currentDate.minusDays(15);
		List<LocalDate> calendar = new ArrayList<>();
		for (int i = 0; i < 30; i++) {
			comboBox_1.addItem(minusDate.plusDays(i));
		}
		comboBox_1.setSelectedIndex(15);

		JLabel lblNewLabel_2 = new JLabel("날짜");
		lblNewLabel_2.setBounds(106, 13, 29, 15);
		two.add(lblNewLabel_2);

		JLabel lblNewLabel_4 = new JLabel("예약하기");
		lblNewLabel_4.setBounds(41, 300, 57, 15);
		two.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("등번호");
		lblNewLabel_5.setBounds(41, 331, 57, 15);
		two.add(lblNewLabel_5);

		numTxt = new JTextField();
		numTxt.setBounds(93, 328, 116, 21);
		two.add(numTxt);
		numTxt.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("선수 이름");
		lblNewLabel_6.setBounds(221, 331, 57, 15);
		two.add(lblNewLabel_6);

		nameTxt = new JTextField();
		nameTxt.setBounds(290, 328, 116, 21);
		two.add(nameTxt);
		nameTxt.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("예약 날짜");
		lblNewLabel_7.setBounds(418, 331, 57, 15);
		two.add(lblNewLabel_7);

//       textField_2 = new JTextField();
//       textField_2.setBounds(484, 328, 116, 21);
//       two.add(textField_2);
//       textField_2.setColumns(10);

		JLabel lblNewLabel_8 = new JLabel("증상");
		lblNewLabel_8.setBounds(52, 379, 29, 15);
		two.add(lblNewLabel_8);

		sickTxt = new JTextField();
		sickTxt.setBounds(93, 376, 283, 21);
		two.add(sickTxt);
		sickTxt.setColumns(10);

		JButton btnNewButton = new JButton("예약");
		btnNewButton.setBounds(825, 367, 124, 38);
		two.add(btnNewButton);

		JLabel lblNewLabel_3 = new JLabel("시간");
		lblNewLabel_3.setBounds(622, 331, 35, 15);
		two.add(lblNewLabel_3);

		timeTxt = new JTextField();
		timeTxt.setBounds(669, 328, 116, 21);
		two.add(timeTxt);
		timeTxt.setColumns(10);

		JButton updateBtn = new JButton("수정");
		updateBtn.setBounds(825, 334, 97, 23);
		two.add(updateBtn);

		JButton deleteBtn = new JButton("삭제");
		deleteBtn.setBounds(825, 296, 97, 23);
		two.add(deleteBtn);

		JButton viewBtn = new JButton("조회");
		viewBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				displayDoctorSchedule();
			}
		});
		viewBtn.setBounds(457, 5, 97, 23);
		two.add(viewBtn);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertAppointment();
			}
		});
		

		pane.setSelectedIndex(0);
		pane.addChangeListener(this);
		getContentPane().setLayout(null);

		JLabel label_1 = new JLabel();
		label_1.setBounds(0, 0, 984, 0);
		this.getContentPane().add(label_1);
		this.getContentPane().add(pane);

		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(StaffRegistration.class.getResource("/image/선수위-배경.jpg")));
		lblNewLabel.setBounds(0, 0, 984, 104);
		getContentPane().add(lblNewLabel);

		this.setSize(1000, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int index = pane.getSelectedIndex();// 현재탭의 번호를 가져온다
		String msg = pane.getTitleAt(index); // index 위에 탭 문자열을 가져옴
		msg += "탭이 선택되었습니다";

		pane.setSelectedIndex(index); // 현재 선택한 탭으로 화면 출력 변경
	}

	public static void main(String[] args) {
		new StaffRegistration();
	}
}