import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.ListSelectionModel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JTextField;
import java.awt.Color;

public class PlayerTab extends JFrame implements ChangeListener {
	private JTabbedPane pane;
	public Player player;
	public static JTable table;
	public static JComboBox comboBox;
	public static JComboBox dateComboBox;
	private static JTable conditionCommentTable;
	private static JTable doctorCommentTable;
	private JTextArea textArea;
	private JButton regiBtn;
	private JButton updateBtn;

	public PlayerTab() {
		LocalDate currentDate = LocalDate.now();
		player = new Player();

		JTextPane commentTp = new JTextPane();
		commentTp.setForeground(new Color(255, 255, 255));
		commentTp.setOpaque(false);
		commentTp.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		commentTp.setBackground(new Color(204, 204, 204));

		JPanel one, two;
		pane = new JTabbedPane();
		pane.setBounds(0, 85, 984, 476);

		one = new JPanel();

		pane.addTab("개인일정", one);
		one.setLayout(null);

		// 공동일정 스크롤
		JTextPane commonScheduleTp = new JTextPane();
		commonScheduleTp.setForeground(new Color(255, 255, 255));
		commonScheduleTp.setBackground(new Color(22, 47, 136));
		commonScheduleTp.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		commonScheduleTp.setOpaque(false);
		List<CommonSchedule> scTodayList = viewCommonSchedule(currentDate.toString());
		String csStr = "";
		for (int i = 0; i < scTodayList.size(); i++) {
			csStr += printCommonSchedule(scTodayList.get(i)) + "\n";
		}

		commonScheduleTp.setText(csStr);
		commonScheduleTp.setEditable(false);

		JScrollPane scrollPane2 = new JScrollPane(commonScheduleTp);
		scrollPane2.setBounds(45, 50, 870, 80);
		scrollPane2.setOpaque(false);
		scrollPane2.getViewport().setOpaque(false);
//		scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		scrollPane2.getVerticalScrollBar().setValue(scrollPane2.getVerticalScrollBar().getMinimum());

		one.add(scrollPane2);
		one.setOpaque(true);

		// 콤보박스
		comboBox = new JComboBox();
		comboBox.setBackground(new Color(255, 255, 255));
		comboBox.setBounds(825, 29, 90, 21);
		LocalDate minusDate = currentDate.minusDays(15);
//		List<LocalDate> calendar = new ArrayList<LocalDate>();
		for (int i = 0; i < 30; i++) {
			comboBox.addItem(minusDate.plusDays(i));
		}
		comboBox.setSelectedIndex(15);

		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> jcb = (JComboBox) e.getSource();
				String index = String.valueOf(jcb.getSelectedItem());

				List<CommonSchedule> csList = viewCommonSchedule(index);

				String csStr = "";
				for (int i = 0; i < csList.size(); i++) {
					csStr += printCommonSchedule(csList.get(i));
				}
				commonScheduleTp.setText(csStr);

				String scheduleSql = "SELECT SUBSTRING(datetime, 12, 5), schedulecomment FROM comment \r\n"
						+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT schedulecomment IS NULL;";

				List<Comment> cmtList = viewComment(player.getBackNumber(), index, scheduleSql);

				String coStr = "";
				for (int i = 0; i < cmtList.size(); i++) {
					coStr += printComment(cmtList.get(i));
				}
				commentTp.setText(coStr);

				List<Schedule> scList = viewPersonalSchedule(player.getBackNumber(), index);
				insertTabel(scList);

				// 버튼 비활성화!!!!!!
				LocalDate startDate = LocalDate.parse(comboBox.getSelectedItem().toString());
				Period period = Period.between(currentDate, startDate);
				int datePeriod = period.getDays();
				System.out.println(datePeriod);
				if (datePeriod < 0) {
					regiBtn.setEnabled(false);
				} else {
					regiBtn.setEnabled(true);

				}

			}
		});

		one.add(comboBox);

		// 코멘트 스크롤

		String scheduleSql = "SELECT SUBSTRING(datetime, 12, 5), schedulecomment FROM comment \r\n"
				+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT schedulecomment IS NULL;";
		List<Comment> scCommentList = viewComment(player.getBackNumber(), comboBox.getSelectedItem().toString(),
				scheduleSql);

//		String coStr = printComment(scCommentList);
		commentTp.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(commentTp);
		scrollPane.setBounds(45, 195, 267, 170);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		// scrollPane.setBorder(null);

		one.add(scrollPane);

		// 등록 버튼 누르면 새 창 열림
		regiBtn = new JButton("");
		regiBtn.setFocusPainted(false);
		regiBtn.setContentAreaFilled(false);
		regiBtn.setBorderPainted(false);
		regiBtn.setBounds(493, 385, 90, 36);
		one.add(regiBtn);
		regiBtn.setEnabled(true);

		regiBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PersonalSchedule();
			}
		});

		// 삭제 버튼
		JButton deleteBtn = new JButton("");
		deleteBtn.setBounds(726, 385, 83, 36);
		deleteBtn.setOpaque(false);
		deleteBtn.setContentAreaFilled(false);
		deleteBtn.setBorderPainted(false);
		deleteBtn.setFocusPainted(false);
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

				// 버튼 비활성화!!
				if (table.getRowCount() == 0) {
					updateBtn.setEnabled(false);
					deleteBtn.setEnabled(false);
				} else {
					updateBtn.setEnabled(true);
					deleteBtn.setEnabled(true);
				}
			}
		});

		updateBtn = new JButton("");
		updateBtn.setFocusPainted(false);
		updateBtn.setContentAreaFilled(false);
		updateBtn.setBorderPainted(false);
		updateBtn.setBounds(614, 385, 83, 36);
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
		scrolledTable.setOpaque(false);

		scrolledTable.setBounds(377, 165, 550, 210);
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		one.add(scrolledTable);

		// table 수정 못하게
		DefaultTableModel mod = new DefaultTableModel(new Object[][] {},
				new String[] { "시작 시간", "종료 시간", "내용", "승인여부" }) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		table = new JTable(mod);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		table.setFillsViewportHeight(true);
		table.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		table.setForeground(new Color(22, 47, 136));
		table.setBackground(new Color(255, 255, 255));
		table.getColumnModel().getColumn(0).setMaxWidth(70);
		table.getColumnModel().getColumn(0).setMinWidth(70);
		table.getColumnModel().getColumn(0).setWidth(70);
		table.getColumnModel().getColumn(1).setMaxWidth(70);
		table.getColumnModel().getColumn(1).setMinWidth(70);
		table.getColumnModel().getColumn(1).setWidth(70);
		table.getColumnModel().getColumn(3).setMaxWidth(70);
		table.getColumnModel().getColumn(3).setMinWidth(70);
		table.getColumnModel().getColumn(3).setWidth(70);
		table.setRowHeight(20);

		// Create a custom TableCellRenderer to center the text
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		// Apply the custom renderer to each column of the table
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		List<Schedule> psList = viewPersonalSchedule(player.getBackNumber(), comboBox.getSelectedItem().toString());
		insertTabel(psList);

		scrolledTable.setViewportView(table);

		JLabel lblNewLabel_3 = new JLabel("New label");
		lblNewLabel_3.setIcon(new ImageIcon(PlayerTab.class.getResource("/image/축구장-배경12.jpg")));
		lblNewLabel_3.setBounds(0, 0, 979, 447);
		one.add(lblNewLabel_3);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// 버튼 비활성화!!!
				LocalDate startDate = LocalDate.parse(comboBox.getSelectedItem().toString());
				Period period = Period.between(currentDate, startDate);
				int datePeriod = period.getDays();
				if (table.getSelectedRowCount() == 1 && datePeriod >= 0) {
					updateBtn.setEnabled(true);
					deleteBtn.setEnabled(true);
					regiBtn.setEnabled(true);
				} else if (table.getSelectedRowCount() == 0) {
					updateBtn.setEnabled(false);
					deleteBtn.setEnabled(false);
				}
			}
		});

		// 컨디션 등록 탭
		// ////////////////////////////////////////////////////////////////////////////
		two = new JPanel();
		pane.addTab("컨디션", two);
		two.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane() {
			@Override
			protected void paintComponent(Graphics g) {
				// Make the scroll pane background transparent
				g.setColor(new Color(0, 0, 0, 0));
				g.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(g);
			}
		};
		scrollPane_1.setBounds(40, 63, 250, 308);
		scrollPane_1.setOpaque(false);
		scrollPane_1.getViewport().setOpaque(false);
		// scrollPane_1.setBorder(null);
		scrollPane_1.setViewportBorder(null);
		scrollPane_1.getVerticalScrollBar().setOpaque(false);
		scrollPane_1.getHorizontalScrollBar().setOpaque(false);
		two.add(scrollPane_1);

		textArea = new JTextArea();
		textArea.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		textArea.setForeground(Color.WHITE);
		textArea.setOpaque(false); // Set the textArea as transparent
		scrollPane_1.setViewportView(textArea);
		textArea.setLineWrap(true);

		JButton saveBtn = new JButton("");
		saveBtn.setBounds(137, 386, 59, 28);
		saveBtn.setOpaque(false);
		saveBtn.setContentAreaFilled(false);
		saveBtn.setBorderPainted(false);
		saveBtn.setFocusPainted(false);

		two.add(saveBtn);
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textArea.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "내용을 입력해주세요", "확인", JOptionPane.WARNING_MESSAGE);
				} else {
					insertCondition(player);
					JOptionPane.showMessageDialog(null, "저장되었습니다", "확인", JOptionPane.INFORMATION_MESSAGE);
					textArea.setText(null);
				}

			}
		});

		dateComboBox = new JComboBox();
		dateComboBox.setBounds(812, 44, 92, 21);
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

				List<Comment> conditionCommentList = viewConditionComment(player.getBackNumber(), index);
				insertConditionCommentTabel(conditionCommentList, conditionCommentTable);

				String sql2 = "SELECT SUBSTRING(datetime, 12, 8), doctorcomment FROM comment \r\n"
						+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT doctorcomment IS NULL;";

				List<Comment> doctorCommentList = viewComment(player.getBackNumber(), index, sql2);
				insertCommentTabel(doctorCommentList, doctorCommentTable);
				
				

				if (!index.equals(currentDate.toString())) {
					saveBtn.setEnabled(false);
					textArea.setEnabled(false);
				} else {
					saveBtn.setEnabled(true);
					textArea.setEnabled(true);
				}

			}
		});

		JScrollPane scrolledTable_1 = new JScrollPane((Component) null);
		scrolledTable_1.setBounds(400, 55, 515, 141);
		scrolledTable_1.setOpaque(false);
		scrolledTable_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		two.add(scrolledTable_1);

		// 컨디션 코멘트 테이블
		DefaultTableModel mod2 = new DefaultTableModel(new Object[][] {}, new String[] { "시간", "코멘트", "작성자" }) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		conditionCommentTable = new JTable(mod2);
		conditionCommentTable.getTableHeader().setReorderingAllowed(false);
		conditionCommentTable.getTableHeader().setResizingAllowed(false);

		conditionCommentTable.getColumnModel().getColumn(0).setMaxWidth(70);
		conditionCommentTable.getColumnModel().getColumn(0).setMinWidth(70);
		conditionCommentTable.getColumnModel().getColumn(0).setWidth(70);
		conditionCommentTable.getColumnModel().getColumn(2).setMaxWidth(50);
		conditionCommentTable.getColumnModel().getColumn(2).setMinWidth(50);
		conditionCommentTable.getColumnModel().getColumn(2).setWidth(50);
		conditionCommentTable.setRowHeight(20);

		conditionCommentTable.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		conditionCommentTable.setForeground(new Color(22, 47, 136));
		conditionCommentTable.setBackground(new Color(255, 255, 255));

		for (int i = 0; i < conditionCommentTable.getColumnCount(); i++) {
			conditionCommentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		List<Comment> conditionCommentList = viewConditionComment(player.getBackNumber(),
				dateComboBox.getSelectedItem().toString());
		insertConditionCommentTabel(conditionCommentList, conditionCommentTable);
		System.out.println(conditionCommentList.toString());

		conditionCommentTable.setBounds(0, 0, 457, 1);
		scrolledTable_1.setViewportView(conditionCommentTable);

		JScrollPane scrolledTable_2 = new JScrollPane((Component) null);
		scrolledTable_2.setBounds(400, 223, 515, 153);
		scrolledTable_2.setOpaque(false);
		scrolledTable_2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		two.add(scrolledTable_2);

		// 의사 코멘트 테이블

		DefaultTableModel mod3 = new DefaultTableModel(new Object[][] {}, new String[] { "시간", "코멘트" }) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};

		doctorCommentTable = new JTable(mod3);
		doctorCommentTable.getTableHeader().setReorderingAllowed(false);
		doctorCommentTable.getTableHeader().setResizingAllowed(false);

		doctorCommentTable.getColumnModel().getColumn(0).setMaxWidth(70);
		doctorCommentTable.getColumnModel().getColumn(0).setMinWidth(70);
		doctorCommentTable.getColumnModel().getColumn(0).setWidth(70);
		doctorCommentTable.setRowHeight(20);

		doctorCommentTable.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		doctorCommentTable.setForeground(new Color(22, 47, 136));
		doctorCommentTable.setBackground(new Color(255, 255, 255));

		for (int i = 0; i < doctorCommentTable.getColumnCount(); i++) {
			doctorCommentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		// Create a custom TableCellRenderer to center the text
		DefaultTableCellRenderer centerRenderer3 = new DefaultTableCellRenderer();
		centerRenderer3.setHorizontalAlignment(JLabel.CENTER);

		// Apply the custom renderer to each column of the table
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer3);
		}

		String sql2 = "SELECT SUBSTRING(datetime, 12, 8), doctorcomment FROM comment \r\n"
				+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT doctorcomment IS NULL;";

		List<Comment> doctorCommentList = viewComment(player.getBackNumber(), dateComboBox.getSelectedItem().toString(),
				sql2);
		insertCommentTabel(doctorCommentList, doctorCommentTable);

		doctorCommentTable.setBounds(0, 0, 457, 1);
		scrolledTable_2.setViewportView(doctorCommentTable);

		JLabel lblNewLabel_4 = new JLabel("  ");
		lblNewLabel_4.setIcon(new ImageIcon(PlayerTab.class.getResource("/image/선수컨디션등록-배경-2.jpg")));
		lblNewLabel_4.setBounds(0, 0, 979, 447);
		two.add(lblNewLabel_4);

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
		setResizable(false);
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

	// 일정 코멘트 & 의사 코멘트 리스트 생성 메소드
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
			String sql = "SELECT SUBSTRING(datetime, 12, 8), conditioncomment, who FROM comment\r\n"
					+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT conditioncomment IS NULL;";

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
			Object[] rowData = { comment.getDatetime(), comment.getConditioncomment(), comment.getWho() };
			tableModel.addRow(rowData);
		}
	}

	// 공동일정 보여주는 메소드
	private static String printCommonSchedule(CommonSchedule cs) {
		String str = "";
		str = cs.getStartTime().substring(0, 5) + "~" + cs.getEndTime().substring(0, 5) + "\n장소 : " + cs.getLocation()
				+ "\n내용 : " + cs.getContent() + "\n";
		return str;
	}

	// 일정 코멘트 보여주는 메소드
	private static String printComment(Comment comment) {
		String str = "";
			str = comment.getDatetime() + " : " + comment.getConditioncomment() + "\n";
		return str;
	}

	public static void main(String[] args) {
		new PlayerTab();

	}
}
