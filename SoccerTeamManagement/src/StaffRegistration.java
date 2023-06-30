import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dbutil.DBUtil;

import javax.swing.JTextField;
import javax.swing.ImageIcon;


public class StaffRegistration extends JFrame{
	private JTabbedPane pane;
	private JTable table;
	private JTextField backnumTf;
	private JTextField nameTf;
	private JTextField sickTf;
	private JTextField timeTf;
	private List<DoctorAppointment> doctorList = new ArrayList<>();
	private JComboBox<LocalDate> daycomboBox;
	private JComboBox<String> doctorcomboBox;
	public Player player;
	private JSpinner spinner;
	
	
	public StaffRegistration() {
		JPanel one, two;
		pane = new JTabbedPane();
		pane.setBounds(0, 104, 984, 457);
		
		one = new JPanel();
		one.setLayout(null);
		pane.addTab("선수목록", one);
		
		
		
		
		
		
		
		
		//===========================================================================================================================================================
		
		two = new JPanel();
		pane.addTab("예약하기", two);
		two.setLayout(null);
		
		JLabel dayLabel = new JLabel("날짜");
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(71, 10, 47, 15);
		two.add(dayLabel);
		
		daycomboBox = new JComboBox();
		daycomboBox.setBounds(130, 7, 97, 21);
		two.add(daycomboBox);
		
		LocalDate currentDate = LocalDate.now();
		for (int i = 0; i < 30; i++) {
			daycomboBox.addItem(currentDate.plusDays(i));
		}
		daycomboBox.setSelectedIndex(0);
		
		JLabel doctorLbl = new JLabel("의사");
		doctorLbl.setHorizontalAlignment(SwingConstants.CENTER);
		doctorLbl.setBounds(239, 10, 57, 15);
		two.add(doctorLbl);
		
		doctorcomboBox = new JComboBox();
		doctorcomboBox.setBounds(291, 7, 97, 21);
		two.add(doctorcomboBox);
		
		viewDoctor();
		
		
		JButton viewButton = new JButton("조회");
		viewButton.setBounds(417, 6, 97, 23);
		two.add(viewButton);
		viewButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				displayDoctorSchedule();
				
			}
		});
		
		JScrollPane scrolledTable = new JScrollPane((Component) null);
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrolledTable.setBounds(71, 55, 803, 221);
		two.add(scrolledTable);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uB4F1\uBC88\uD638", "\uC120\uC218\uC774\uB984", "\uC2DC\uAC04", "\uC99D\uC0C1"
			}
		));
		scrolledTable.setViewportView(table);
		
		JLabel reservationLbl = new JLabel("예약하기");
		reservationLbl.setBounds(71, 305, 57, 15);
		two.add(reservationLbl);
		
		JLabel backnumLbl = new JLabel("등번호");
		backnumLbl.setBounds(71, 336, 57, 15);
		two.add(backnumLbl);
		
		backnumTf = new JTextField();
		backnumTf.setColumns(10);
		backnumTf.setBounds(123, 333, 116, 21);
		two.add(backnumTf);
		
		backnumTf.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
		            int backNumber = Integer.parseInt(backnumTf.getText().trim());
		            selectName(backNumber, nameTf);
		        } catch (NumberFormatException ex) {
		            // 잘못된 입력 처리 (예: 숫자가 아닌 문자 등)
		        }
			}
		});
		
		JLabel playerNameLbl = new JLabel("선수 이름");
		playerNameLbl.setBounds(251, 336, 57, 15);
		two.add(playerNameLbl);
		
		nameTf = new JTextField();
		nameTf.setColumns(10);
		nameTf.setBounds(320, 333, 116, 21);
		two.add(nameTf);
		
		JLabel symptomLbl = new JLabel("증상");
		symptomLbl.setBounds(82, 384, 29, 15);
		two.add(symptomLbl);
		
		sickTf = new JTextField();
		sickTf.setColumns(10);
		sickTf.setBounds(123, 381, 283, 21);
		two.add(sickTf);
		
		JLabel timeLbl = new JLabel("시간");
		timeLbl.setBounds(448, 336, 35, 15);
		two.add(timeLbl);
		
		timeTf = new JTextField();
		timeTf.setColumns(10);
		timeTf.setBounds(495, 333, 116, 21);
		two.add(timeTf);
		
		JButton reservationBtn = new JButton("예약");
		reservationBtn.setBounds(815, 332, 97, 23);
		two.add(reservationBtn);
		
		reservationBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				insertAppointment();
			}
		});
		
		JButton deleteBtn = new JButton("삭제");
		deleteBtn.setBounds(815, 398, 97, 23);
		two.add(deleteBtn);
		
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					TableModel data = table.getModel();
					int number = (int) data.getValueAt(row, 0);
					String date = daycomboBox.getSelectedItem().toString();
					String time = (String) data.getValueAt(row, 2);
					
					deleteAppointment(number, date, time); // Delete appointment from the database
					removeRecord(row); // Remove the selected row from the table
				}
			}
		});
		
		JButton changeBtn = new JButton("수정");
		changeBtn.setBounds(815, 365, 97, 23);
		two.add(changeBtn);
		
		changeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		pane.setSelectedIndex(0);
	    JLabel label = new JLabel("");
	    label.setIcon(new ImageIcon(StaffRegistration.class.getResource("/image/선수위-배경.jpg")));
	    this.getContentPane().add("North", label);
	    this.getContentPane().add("Center", pane);
		
		this.setSize(1000, 600);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	public static void main(String[] args) {
		new StaffRegistration();
	}
	
	public List<DoctorAppointment> selectList() {
		
		String selectedDoctor = doctorcomboBox.getSelectedItem().toString();
		doctorList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT * FROM appointment where doctor = ?;");
			stmt.setString(1, selectedDoctor);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int number = rs.getInt("number");
				String name = rs.getString("name");
				String time = rs.getString("time");
				String condition = rs.getString("condition");
				

				System.out.printf("%d, %s, %s, %s", number, name, time, condition);
				doctorList.add(new DoctorAppointment(number, name, time, condition));
				System.out.println(doctorList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return doctorList;
	}
	
	// 테이블 기록 가져오기
	public void doctorSchedule(List<DoctorAppointment> doctorList) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		
		for(DoctorAppointment doctorAppointment : doctorList) {
			Object[] rowData = { doctorAppointment.getBacknumber(), doctorAppointment.getPlayerName(), doctorAppointment.getTime(), doctorAppointment.getCondition() };
			model.addRow(rowData);
		}				
	}
	
	
	public void displayDoctorSchedule() {
		String selectedDate = daycomboBox.getSelectedItem().toString();
		String selectedDoctor = doctorcomboBox.getSelectedItem().toString();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			// doctor
			String query = "SELECT * FROM appointment WHERE date = ? AND doctor = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, selectedDate);
			stmt.setString(2, selectedDoctor);

			rs = stmt.executeQuery();

			doctorList.clear(); // 이전 데이터를 비워줍니다

			while (rs.next()) {
				int number = rs.getInt("number");
				String name = rs.getString("name");
				String time = rs.getString("time");
				String condition = rs.getString("condition");

				// Print appointment details for testing
				System.out.printf("%d %s %s %s\n", number, name, time, condition);

				// Add the appointment to the list
				doctorList.add(new DoctorAppointment(number, name, time, condition));
			}

			doctorSchedule(doctorList); // Update the table with the retrieved appointments

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}
	
	public void displaySchedule() {
		String selectedDate = daycomboBox.getSelectedItem().toString();
		String selectedDoctor = doctorcomboBox.getSelectedItem().toString();

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

			doctorList.clear(); // Clear the existing list of appointments

			while (rs.next()) {
				int number = rs.getInt("number");
				String name = rs.getString("name");
				String date = rs.getString("date");
				String time = rs.getString("time");
				String condition = rs.getString("condition");
				

				// Print appointment details for testing
				System.out.printf("%d %s %s %s %s %s\n", number, name, date, time, condition);

				// Add the appointment to the list
				doctorList.add(new DoctorAppointment(number, name, date, time, condition));
			}

			doctorSchedule(doctorList); // Update the table with the retrieved appointments

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}
	
	public void viewDoctor() {
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
				doctorcomboBox.addItem(name);
			}
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
			String selectedDate = daycomboBox.getSelectedItem().toString();
			String selectedDoctor = (String) doctorcomboBox.getSelectedItem();

			stmt = conn.prepareStatement("insert into appointment(number, name, date, time,`condition`, doctor) values (?, ?, ?, ?, ?, ?)");
			stmt.setInt(1, Integer.valueOf(backnumTf.getText()));
			stmt.setString(2, nameTf.getText());
			stmt.setString(3, selectedDate);
			stmt.setString(4, timeTf.getText());
			stmt.setString(5, sickTf.getText());
			stmt.setString(6, selectedDoctor); // 선택된 의사 값을 바인딩합니다

			stmt.executeUpdate();

			// 예약 추가 후 테이블을 갱신합니다

			doctorSchedule(doctorList);

			// 예약 후 텍스트 필드들을 빈 칸으로 초기화
			backnumTf.setText("");
			nameTf.setText("");
			sickTf.setText("");
			timeTf.setText("");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}
	
	// 데이터베이스 appointment 데이터 삭제
	private void deleteAppointment(int number, String date, String time) {
	    Connection conn = null;
	    PreparedStatement stmt = null;

	    try {
	        conn = DBUtil.getConnection();
	        stmt = conn.prepareStatement("DELETE FROM appointment WHERE number = ? AND date = ? AND time = ?");
	        stmt.setInt(1, number);
	        stmt.setString(2, date);
	        stmt.setString(3, time);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.close(stmt);
	        DBUtil.close(conn);
	    }
	}
		
	// 테이블 기록 삭제
	private void removeRecord(int index) {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    if (index >= 0 && index < model.getRowCount()) {
	        model.removeRow(index);
	    }
	}
	
	// 테이블 기록 수정
	private void updateAppointment(int number, String date, String time, String condition) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("UPDATE appointment SET condition = ? WHERE number = ? AND date = ? AND time = ?");
			stmt.setString(1, condition);
			stmt.setInt(2, number);
			stmt.setString(3, date);
			stmt.setString(4, time);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}
	
	// 등번호에 해당하는 선수 찾기
	private void selectName(int backNumber, JTextField nameField) {
	    String playerName = "";
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        conn = DBUtil.getConnection();
	        stmt = conn.prepareStatement("SELECT name FROM players WHERE backnumber = ?");
	        stmt.setInt(1, backNumber);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            playerName = rs.getString("name");
	            nameField.setText(playerName);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.close(rs);
	        DBUtil.close(stmt);
	        DBUtil.close(conn);
	    }
	}
	
	private void updateAppointment() {
		Connection conn = null;
	}
}
