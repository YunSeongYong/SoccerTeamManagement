
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

//import dbutil.DBUtil;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import dbutil.DBUtil;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class DoctorSchedule extends JFrame implements ChangeListener {
	private JTabbedPane pane;

	private JTable table_1;
	private Staff staff;

	private JTextArea textArea;

	private JButton btnNewButton;
	private JTable table_2;

	private JComboBox nameComboBox;
	private JComboBox endComboBox;
	private JButton btnNewButton_1;

	public DoctorSchedule() {
		super();
		staff = new Staff();
		JPanel one, two, three;
		pane = new JTabbedPane();
		pane.setBounds(0, 85, 984, 476);

		one = new JPanel();
		pane.addTab("예약목록", one);
		one.setLayout(null);

		// 날짜 콤보 박스
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(826, 73, 91, 27);
		one.add(comboBox);

		LocalDate currentDate = LocalDate.now();
		LocalDate minusDate2 = currentDate.minusDays(15);
		for (int i = 0; i < 15; i++) {
			comboBox.addItem(currentDate.plusDays(i));
		}
		comboBox.setSelectedIndex(0);

		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> jcb = (JComboBox) e.getSource();
				String index = String.valueOf(jcb.getSelectedItem());

				//List<DoctorAppointment> appointmentList = makeAppointmentList(staff.getName(), index);
				//insertAppointmentTabel(appointmentList, table_1);

//				// 저장 버튼 비활성화

				btnNewButton.setEnabled(false);

			}
		});

		btnNewButton = new JButton("");
		btnNewButton.setBounds(736, 368, 62, 23);
		btnNewButton.setOpaque(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setFocusPainted(false);
		
		one.add(btnNewButton);
		btnNewButton.setEnabled(false);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table_1.getSelectedRow();
				System.out.println(row);
				TableModel data = table_1.getModel();
				int number = (Integer) data.getValueAt(row, 1);
				System.out.println(number);

				//insertDoctorComment(number, textArea.getText());

			}
		});

		// 스크롤 팬 + 테이블
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 100, 515, 250);
		one.add(scrollPane);

		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\uC2DC\uAC04", "\uB4F1\uBC88\uD638", "\uC774\uB984", "\uC99D\uC0C1" }));
		scrollPane.setViewportView(table_1);

		// 버튼 활성화, 비활성화 index.equals(currentDate.toString())
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table_1.getSelectedRowCount() == 1
						&& comboBox.getSelectedItem().toString().equals(currentDate.toString())) {
					btnNewButton.setEnabled(true);
				} else {
					btnNewButton.setEnabled(false);
				}
			}
		});

		//List<DoctorAppointment> appointmentList = makeAppointmentList(staff.getName(),
			//	comboBox.getSelectedItem().toString());
		//insertAppointmentTabel(appointmentList, table_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(617, 100, 300, 250);
		scrollPane_1.setViewportBorder(null);
		scrollPane_1.getVerticalScrollBar().setOpaque(false);
		scrollPane_1.getHorizontalScrollBar().setOpaque(false);
		scrollPane_1.setOpaque(false);
		scrollPane_1.getViewport().setOpaque(false);
		one.add(scrollPane_1);

		textArea = new JTextArea();
		textArea.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		textArea.setForeground(Color.WHITE);
		textArea.setOpaque(false);
		scrollPane_1.setViewportView(textArea);
		textArea.setLineWrap(true);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(DoctorSchedule.class.getResource("/image/의사일정-배경-1.jpg")));
		lblNewLabel.setBounds(0, 0, 979, 428);
		one.add(lblNewLabel);

		two = new JPanel();
		pane.addTab("병력보기", two);
		two.setLayout(null);

		JComboBox startComboBox = new JComboBox();
		startComboBox.setBounds(323, 135, 90, 21);
		two.add(startComboBox);

		for (int i = 30; i >= 0; i--) {
			startComboBox.addItem(currentDate.minusDays(i));
		}
		startComboBox.setSelectedIndex(0);

		startComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				endComboBox.setEnabled(true);
				JComboBox<String> jcb = (JComboBox) e.getSource();
				String index = String.valueOf(jcb.getSelectedItem());

				LocalDate startDate = LocalDate.parse(startComboBox.getSelectedItem().toString());
				Period period = Period.between(startDate, currentDate);
				int datePeriod = period.getDays();
				System.out.println(datePeriod);

				for (int i = 0; i <= datePeriod; i++) {
					endComboBox.addItem(startDate.plusDays(i));
				}
			}
		});

		endComboBox = new JComboBox();
		endComboBox.setBounds(441, 135, 90, 21);
		two.add(endComboBox);
		endComboBox.setEnabled(false);
		
		endComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnNewButton_1.setEnabled(true);
			}
		});

		nameComboBox = new JComboBox();
		nameComboBox.setBounds(558, 135, 90, 21);
		two.add(nameComboBox);

		//addPlayer(staff.getName());
		//nameComboBox.setSelectedIndex(0);

		btnNewButton_1 = new JButton("");
		btnNewButton_1.setBounds(677, 85, 63, 23);
		btnNewButton_1.setOpaque(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setFocusPainted(false);
		two.add(btnNewButton_1);
		btnNewButton_1.setEnabled(false);

		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String str = nameComboBox.getSelectedItem().toString();
				String backNumber = str.substring(0, str.indexOf("."));
				String startDate = startComboBox.getSelectedItem().toString();
				String endDate = endComboBox.getSelectedItem().toString();
			
				//List<Comment> list = makeCommentList(backNumber, startDate, endDate);
				//insertCommentTabel(list, table_2);
			}
		});

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(203, 160, 570, 250);
		two.add(scrollPane_2);

		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\uB0A0\uC9DC", "\uB0B4\uC6A9" }));
		scrollPane_2.setViewportView(table_2);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(DoctorSchedule.class.getResource("/image/병력관리-배경3.jpg")));
		lblNewLabel_2.setBounds(0, 0, 979, 428);
		two.add(lblNewLabel_2);

		pane.setSelectedIndex(0);
		pane.addChangeListener(this);

		this.getContentPane().add("Center", pane);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(DoctorSchedule.class.getResource("/image/선수위-배경.jpg")));
		getContentPane().add(lblNewLabel_1, BorderLayout.NORTH);
		this.setSize(1000, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// 의사 예약 일정 리스트 만들기
//	private static List<DoctorAppointment> makeAppointmentList(String name, String date) {
//		List<DoctorAppointment> list = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;

//		try {
//			conn = DBUtil.getConnection();
//			String sql = "SELECT number, name, `condition`, time FROM appointment \r\n"
//					+ "where doctor = ? AND date = ?;";
//
//			stmt = conn.prepareStatement(sql);
//			stmt.setString(1, name);
//			stmt.setString(2, date);
//
//			rs = stmt.executeQuery();
//			while (rs.next()) {
//				int number = rs.getInt(1);
//				String nameParse = rs.getString(2);
//				String condition = rs.getString(3);
//				String time = rs.getString(4);
//
//				DoctorAppointment d = new DoctorAppointment(number, nameParse, time, condition);
//				list.add(d);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtil.close(rs);
//			DBUtil.close(stmt);
//			DBUtil.close(conn);
//		}
//		return list;
//	}

	// 예약 일정 테이블에 넣는 메소드
	private static void insertAppointmentTabel(List<DoctorAppointment> list, JTable table) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		// 기존의 테이블 데이터 초기화
		tableModel.setRowCount(0);

		// filteredList의 데이터를 테이블 모델에 추가
		for (DoctorAppointment doctor : list) {
			Object[] rowData = { doctor.getTime(), doctor.getBacknumber(), doctor.getPlayerName(),
					doctor.getCondition() };
			tableModel.addRow(rowData);
		}
	}

	// 의사 코멘트 데이터베이스에 저장
//	public void insertDoctorComment(int number, String comment) {
//		Connection conn = null;
//		PreparedStatement stmt = null;
//
//		try {
//			conn = DBUtil.getConnection();
//			stmt = conn.prepareStatement("INSERT INTO comment (number, doctorcomment, who) VALUES (?, ?, '의사')");
//			stmt.setInt(1, number);
//			stmt.setString(2, comment);
//
//			stmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtil.close(stmt);
//			DBUtil.close(conn);
//		}
//	}

	// 담당 선수 콤보박스 만드는 메소드
//	public void addPlayer(String doctorName) {
//		List<Player> list = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			conn = DBUtil.getConnection();
//			String sql = "SELECT backnumber, name FROM players WHERE doctor = ?";
//
//			stmt = conn.prepareStatement(sql);
//			stmt.setString(1, doctorName);
//
//			rs = stmt.executeQuery();
//			while (rs.next()) {
//				int backNumber = rs.getInt(1);
//				String name = rs.getString(2);
//
//				nameComboBox.addItem(backNumber + ". " + name);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtil.close(rs);
//			DBUtil.close(stmt);
//			DBUtil.close(conn);
//		}
//	}

	// 선택된 날짜 + 선수의 의사 코멘트를 리스트로 가지고 오는 메소드
//	private static List<Comment> makeCommentList(String backNumber, String startDate, String endDate) {
//		List<Comment> list = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			conn = DBUtil.getConnection();
//			String sql = "SELECT SUBSTRING(datetime, 1, 10), doctorcomment FROM comment\r\n"
//					+ "WHERE SUBSTRING(datetime, 1, 10) BETWEEN ? AND ?\r\n"
//					+ "AND number = ? AND NOT doctorcomment IS NULL;";
//
//			stmt = conn.prepareStatement(sql);
//			stmt.setString(1, startDate);
//			stmt.setString(2, endDate);
//			stmt.setString(3, backNumber);
//
//			rs = stmt.executeQuery();
//			while (rs.next()) {
//				String date = rs.getString(1);
//				String comment = rs.getString(2);
//
//				Comment c = new Comment(date, comment);
//				list.add(c);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBUtil.close(rs);
//			DBUtil.close(stmt);
//			DBUtil.close(conn);
//		}
//		return list;
//	}

	// 코멘트 테이블에 넣는 메소드
	private static void insertCommentTabel(List<Comment> list, JTable table) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		// 기존의 테이블 데이터 초기화
		tableModel.setRowCount(0);

		// filteredList의 데이터를 테이블 모델에 추가
		for (Comment comment : list) {
			Object[] rowData = { comment.getDatetime(), comment.getConditioncomment() };
			tableModel.addRow(rowData);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int index = pane.getSelectedIndex();// 현재탭의 번호를 가져온다
		String msg = pane.getTitleAt(index); // index 위에 탭 문자열을 가져옴
		pane.setSelectedIndex(index); // 현재 선택한 탭으로 화면 출력 변경
	}

	public static void main(String[] args) {
		new DoctorSchedule();
	}
}