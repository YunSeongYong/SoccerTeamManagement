import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dbutil.DBUtil;

import javax.swing.JComboBox;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import javax.swing.JTextField;

public class PlayerTab extends JFrame implements ChangeListener {
	private JTabbedPane pane;
	public Player player;
	public static JTable table;
	public static JComboBox comboBox;
	public static JComboBox dateComboBox;
	private static JTable conditionCommentTable;
	private static JTable doctorCommentTable;
	private JTextArea textArea;

	public PlayerTab() {
		LocalDate currentDate = LocalDate.now();
		player = new Player();

		JTextPane commentTp = new JTextPane();

		JPanel one, two;
		pane = new JTabbedPane();
		pane.setBounds(0, 85, 984, 476);

		one = new JPanel();

		pane.addTab("개인일정", one);
		one.setLayout(null);

		// 개인일정 탭
		// 라벨
		JLabel label = new JLabel("공동일정");
		label.setBounds(56, 15, 57, 42);
		one.add(label);

		JLabel commentLbl = new JLabel("코멘트");
		commentLbl.setBounds(152, 376, 49, 61);
		one.add(commentLbl);

		// 공동일정 스크롤
		JTextPane commonScheduleTp = new JTextPane();
		
		List<CommonSchedule> scTodayList = viewCommonSchedule(currentDate.toString());
		
		String csStr = printCommonSchedule(scTodayList);

		commonScheduleTp.setText(csStr);
		commonScheduleTp.setEditable(false);
		
		
		JScrollPane scrollPane2 = new JScrollPane(commonScheduleTp);
		scrollPane2.setBounds(125, 9, 653, 54);
		one.add(scrollPane2);

		// 콤보박스
		comboBox = new JComboBox();
		comboBox.setBounds(822, 9, 131, 21);
		LocalDate minusDate = currentDate.minusDays(15);
		List<LocalDate> calendar = new ArrayList<LocalDate>();
		for (int i = 0; i < 30; i++) {
			comboBox.addItem(minusDate.plusDays(i));
		}
		comboBox.setSelectedIndex(15);

		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> jcb = (JComboBox) e.getSource();
				String index = String.valueOf(jcb.getSelectedItem());

				// 수정수정!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				List<CommonSchedule> csList = viewCommonSchedule(index);
				
				String csStr = printCommonSchedule(csList);
				commonScheduleTp.setText(csStr);

				String scheduleSql = "SELECT SUBSTRING(datetime, 12, 5), schedulecomment FROM comment \r\n"
						+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT schedulecomment IS NULL;";

				List<Comment> cmtList = viewComment(player.getBackNumber(), index, scheduleSql);
			
				String coStr = printComment(cmtList);
				commentTp.setText(coStr);

				List<Schedule> scList = viewPersonalSchedule(player.getBackNumber(), index);
				insertTabel(scList);
			}
		});

		one.add(comboBox);

		// 코멘트 스크롤

		String scheduleSql = "SELECT SUBSTRING(datetime, 12, 5), schedulecomment FROM comment \r\n"
				+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT schedulecomment IS NULL;";
		List<Comment> scCommentList = viewComment(player.getBackNumber(), comboBox.getSelectedItem().toString(),
				scheduleSql);
		
		String coStr = printComment(scCommentList);

		commentTp.setText(coStr);
		commentTp.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(commentTp);
		scrollPane.setBounds(56, 115, 267, 269);
		one.add(scrollPane);

		// 등록 버튼 누르면 새 창 열림
		JButton regiBtn = new JButton("등록");
		regiBtn.setBounds(856, 122, 81, 48);
		one.add(regiBtn);

		regiBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PersonalSchedule();
			}
		});

		// 삭제 버튼
		JButton deleteBtn = new JButton("삭제");
		deleteBtn.setBounds(856, 200, 81, 48);
		one.add(deleteBtn);
		deleteBtn.setEnabled(false);

		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				TableModel data = table.getModel();
				String startTime = (String) data.getValueAt(row, 0);

				deletePersonalSchedule(player.getBackNumber(), comboBox.getSelectedItem().toString(), startTime);

				removeRecord(row);
			}
		});

		// 수정 버튼
		// 테이블에서 선택된 값을 가져옴
		JButton updateBtn = new JButton("수정");
		updateBtn.setBounds(856, 272, 81, 54);
		one.add(updateBtn);
		updateBtn.setEnabled(false);

		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				TableModel data = table.getModel();
				String startTime = (String) data.getValueAt(row, 0);
				String endTime = (String) data.getValueAt(row, 1);
				String content = (String) data.getValueAt(row, 2);

				Schedule schedule = new Schedule(startTime, endTime, content);

				UpdateSchedule Jframe = new UpdateSchedule(schedule);
			}
		});

		// JTabel
		JScrollPane scrolledTable = new JScrollPane((Component) null);
		scrolledTable.setBounds(377, 112, 477, 306);
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		one.add(scrolledTable);

		table = new JTable(new DefaultTableModel(new Object[][] {}, new String[] { "\uC2DC\uC791 \uC2DC\uAC04",
				"\uC885\uB8CC \uC2DC\uAC04", "\uB0B4\uC6A9", "\uC2B9\uC778\uC5EC\uBD80" }));

		List<Schedule> psList = viewPersonalSchedule(player.getBackNumber(), comboBox.getSelectedItem().toString());
		insertTabel(psList);

		scrolledTable.setViewportView(table);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				updateBtn.setEnabled(true);
				deleteBtn.setEnabled(true);
			}
		});

		// 컨디션 등록 탭
		// ////////////////////////////////////////////////////////////////////////////
		two = new JPanel();
		pane.addTab("컨디션", two);
		two.setLayout(null);

		JLabel commentLbl_ = new JLabel("컨디션 코멘트");
		commentLbl_.setBounds(323, 25, 97, 28);
		commentLbl_.setHorizontalAlignment(SwingConstants.CENTER);
		two.add(commentLbl_);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(40, 63, 250, 308);
		two.add(scrollPane_1);
		
		textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);
		textArea.setLineWrap(true);

		JButton saveBtn = new JButton("저장");
		saveBtn.setBounds(113, 386, 97, 23);
		two.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertCondition(player);
			}
		});

		JLabel lblNewLabel_1 = new JLabel("오늘의 몸 상태");
		lblNewLabel_1.setBounds(40, 32, 86, 15);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		two.add(lblNewLabel_1);

		dateComboBox = new JComboBox();
		dateComboBox.setBounds(837, 10, 92, 21);
		two.add(dateComboBox);
		LocalDate minusDate2 = currentDate.minusDays(15);
		List<LocalDate> calendar2 = new ArrayList<LocalDate>();
		for (int i = 0; i < 16; i++) {
			dateComboBox.addItem(minusDate.plusDays(i));
		}
		dateComboBox.setSelectedIndex(15);

		dateComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> jcb = (JComboBox) e.getSource();
				String index = String.valueOf(jcb.getSelectedItem());

				String sql = "SELECT SUBSTRING(datetime, 12, 5), conditioncomment FROM comment \r\n"
						+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT conditioncomment IS NULL;";

				List<Comment> conditionCommentList = viewComment(player.getBackNumber(), index, sql);
				insertCommentTabel(conditionCommentList, conditionCommentTable);

				String sql2 = "SELECT SUBSTRING(datetime, 12, 5), doctorcomment FROM comment \r\n"
						+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT doctorcomment IS NULL;";

				List<Comment> doctorCommentList = viewComment(player.getBackNumber(), index, sql2);
				insertCommentTabel(doctorCommentList, doctorCommentTable);

				if (!index.equals(currentDate.toString())) {
					saveBtn.setEnabled(false);
				} else {
					saveBtn.setEnabled(true);
				}

			}
		});

		JScrollPane scrolledTable_1 = new JScrollPane((Component) null);
		scrolledTable_1.setBounds(323, 63, 532, 141);
		scrolledTable_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		two.add(scrolledTable_1);

		// 컨디션 코멘트 테이블
		conditionCommentTable = new JTable(
				new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC2DC\uAC04", "\uCF54\uBA58\uD2B8", "\uC791\uC131\uC790"
			}
		));

//		String sql = "SELECT SUBSTRING(datetime, 12, 5), conditioncomment FROM comment \r\n"
//				+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT conditioncomment IS NULL;";

		List<Comment> conditionCommentList = viewConditionComment(player.getBackNumber(), dateComboBox.getSelectedItem().toString());
		insertConditionCommentTabel(conditionCommentList, conditionCommentTable);

		conditionCommentTable.setBounds(0, 0, 457, 1);
		scrolledTable_1.setViewportView(conditionCommentTable);

		JLabel lblNewLabel_2 = new JLabel("의사 코멘트");
		lblNewLabel_2.setBounds(343, 214, 77, 15);
		two.add(lblNewLabel_2);

		JScrollPane scrolledTable_2 = new JScrollPane((Component) null);
		scrolledTable_2.setBounds(333, 239, 515, 158);
		scrolledTable_2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		two.add(scrolledTable_2);

		// 의사 코멘트 테이블
		doctorCommentTable = new JTable(
				new DefaultTableModel(new Object[][] {}, new String[] { "\uC2DC\uAC04", "\uCF54\uBA58\uD2B8" }));

		String sql2 = "SELECT SUBSTRING(datetime, 12, 5), doctorcomment FROM comment \r\n"
				+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT doctorcomment IS NULL;";

		List<Comment> doctorCommentList = viewComment(player.getBackNumber(), dateComboBox.getSelectedItem().toString(),
				sql2);
		insertCommentTabel(doctorCommentList, doctorCommentTable);

		doctorCommentTable.setBounds(0, 0, 457, 1);
		scrolledTable_2.setViewportView(doctorCommentTable);

		pane.setSelectedIndex(0);
		pane.addChangeListener(this);
		getContentPane().setLayout(null);
		this.getContentPane().add(pane);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(PlayerTab.class.getResource("/image/선수위-배경.jpg")));
		lblNewLabel.setBounds(0, 0, 984, 90);
		getContentPane().add(lblNewLabel);

		setBounds(100, 100, 1000, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private String[][] change(List<Schedule> scList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int index = pane.getSelectedIndex();// 현재탭의 번호를 가져온다
		String msg = pane.getTitleAt(index); // index 위에 탭 문자열을 가져옴
		pane.setSelectedIndex(index); // 현재 선택한 탭으로 화면 출력 변경
	}

	// 일정 코멘트 리스트를 JTextPane에 넣기
//	private static void insertTextPane(List<Comment> list) {
//		
//	}

	// 공동일정 리스트 생성 메소드
	private static List<CommonSchedule> viewCommonSchedule(String date) {
		List<CommonSchedule> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT * FROM commonschedule WHERE date = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, date);

			rs = stmt.executeQuery();
			while (rs.next()) {
				String dateParse = rs.getString(1);
				String startTime = rs.getString(2);
				String endTime = rs.getString(3);
				String content = rs.getString(4);
				String where = rs.getString(5);

				CommonSchedule schedule = new CommonSchedule(dateParse, startTime, endTime, content, where);
				list.add(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list;
	}

	// 개인 일정 리스트 생성
	private static List<Schedule> viewPersonalSchedule(int number, String date) {
		List<Schedule> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT starttime, endtime, content, confirm FROM playerschedule \r\n"
					+ "WHERE number = ? AND date = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, number);
			stmt.setString(2, date);

			rs = stmt.executeQuery();
			while (rs.next()) {
				String starttime = rs.getString(1);
				String endtime = rs.getString(2);
				String content = rs.getString(3);
				String check = rs.getString(4);

				Schedule schedule = new Schedule(starttime, endtime, content, check);
				list.add(schedule);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list;
	}

	// 데이터베이스 데이터 삭제
	private static void deletePersonalSchedule(int number, String date, String startTime) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "DELETE FROM playerschedule WHERE number = ? AND date = ? AND starttime = ?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, number);
			stmt.setString(2, date);
			stmt.setString(3, startTime);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	// 테이블 기록 가져오기
	private static void insertTabel(List<Schedule> list) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setRowCount(0);

		for (Schedule schedule : list) {
			Object[] rowData = { schedule.getStartTime(), schedule.getEndTime(), schedule.getContent(),
					schedule.getCheck() };
			tableModel.addRow(rowData);
		}
	}

	// 테이블 기록 삭제
	private static void removeRecord(int index) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		if (index < 0) {
			if (table.getRowCount() == 0)// 비어있는 테이블이면
				return;
			index = 0;
		}
		model.removeRow(index);
	}

	// 선수 이름 찾기
	private static String getPlayerName(int backNumber) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String playerName = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT name FROM players WHERE backnumber = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, backNumber);

			rs = stmt.executeQuery();
			if (rs.next()) {
				playerName = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return playerName;
	}

	// 선수 컨디션 저장
	public void insertCondition(Player player) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn
					.prepareStatement("INSERT INTO `condition` (number, playername, playercondition) VALUES (?, ?, ?)");
			stmt.setInt(1, player.getBackNumber());
			stmt.setString(2, getPlayerName(player.getBackNumber()));
			stmt.setString(3, textArea.getText());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	// 날짜에 맞는 선수 컨디션 찾기
	public List<Condition> viewCondition(int number) {
		List<Condition> conditionList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("select playercondition from project_test.condition where number = ?");
			stmt.setInt(1, number);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
		return conditionList;
	}

	// 일정 코멘트 & 컨디션 코멘트 & 의사 코멘트 리스트 생성 메소드
	private static List<Comment> viewComment(int number, String datetime, String sql) {
		List<Comment> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, datetime);
			stmt.setInt(2, number);

			rs = stmt.executeQuery();
			while (rs.next()) {
				String time = rs.getString(1);
				String comment = rs.getString(2);

				System.out.println(time);
				System.out.println(comment);

				Comment c = new Comment(time, comment);
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list;
	}

	// 테이블에 코멘트 넣는 메소드
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
	
	// 컨디션 코멘트 리스트 만들기
	private static List<Comment> viewConditionComment(int number, String datetime) {
		List<Comment> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT SUBSTRING(datetime, 12, 5), conditioncomment, who FROM comment\r\n" + 
					"WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT conditioncomment IS NULL;";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, datetime);
			stmt.setInt(2, number);

			rs = stmt.executeQuery();
			while (rs.next()) {
				String time = rs.getString(1);
				String comment = rs.getString(2);
				String who = rs.getString(3);

				Comment c = new Comment(time, comment, who);
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return list;
	}
	
	// 컨디션 코멘트 테이블에 넣는 메소드
	private static void insertConditionCommentTabel(List<Comment> list, JTable table) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		// 기존의 테이블 데이터 초기화
		tableModel.setRowCount(0);

		// filteredList의 데이터를 테이블 모델에 추가
		for (Comment comment : list) {
			Object[] rowData = {comment.getDatetime(), comment.getConditioncomment(), comment.getWho()};
			tableModel.addRow(rowData);
		}
	}
	
	// 공동일정 보여주는 메소드
	private static String printCommonSchedule (List<CommonSchedule> list) {
		String str = "";
		for (CommonSchedule cs : list) {
			str = cs.getDate() + " " + cs.getStartTime() + "~" + cs.getEndTime() + 
					"\n장소 : " + cs.getLocation() +  "\n내용 : " + cs.getContent() + "\n";
		}
		return str;
	}
	
	// 일정 코멘트 보여주는 메소드
	private static String printComment (List<Comment> list) {
		String str = "";
		for (Comment comment : list) {
			str = comment.getDatetime() + " : " + comment.getConditioncomment() + "\n";
		}
		return str;
	}
	
	public static void main(String[] args) {
		new PlayerTab();

	}
}
