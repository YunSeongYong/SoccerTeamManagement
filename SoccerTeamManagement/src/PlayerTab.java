import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dbutil.DBUtil;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

public class PlayerTab extends JFrame implements ChangeListener {
	JTabbedPane pane;
	private JTextField startTxt;
	private JTextField endTxt;
	private JTextField contentTxt;
	public Player player;
	private JTable table;

	public PlayerTab() {
		player = new Player();
		LocalDate currentDate = LocalDate.now();
		
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

		JLabel startLbl = new JLabel("시작 시간");
		startLbl.setBounds(56, 67, 57, 15);
		one.add(startLbl);

		JLabel endLbl = new JLabel("끝나는 시간");
		endLbl.setBounds(212, 76, 73, 15);
		one.add(endLbl);

		JLabel scheduleLbl = new JLabel("일정");
		scheduleLbl.setBounds(400, 76, 31, 15);
		one.add(scheduleLbl);

		JLabel commentLbl = new JLabel("코멘트");
		commentLbl.setBounds(152, 376, 49, 61);
		one.add(commentLbl);

		// 공동일정 라벨
		JLabel commonScheduleLbl = new JLabel("공동일정");
		commonScheduleLbl.setBounds(125, 9, 653, 54);
		one.add(commonScheduleLbl);
		
		List<CommonSchedule> scTodayList = viewCommonSchedule(currentDate.toString());
		commonScheduleLbl.setText(scTodayList.toString());
		
		List<Comment> comTodayList = viewComment(player.getBackNumber(), currentDate.toString());
		
		// 코멘트 스크롤
		JLabel commentLbl2 =  new JLabel();
		commentLbl2.setBounds(1, 1, 265, 267);
		one.add(commentLbl2);
		commentLbl2.setText(comTodayList.toString());
				
		JScrollPane scrollPane = new JScrollPane(commentLbl2);
		scrollPane.setBounds(56, 115, 267, 269);
		one.add(scrollPane);
		

		// 콤보박스
		JComboBox comboBox = new JComboBox();
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

				List<CommonSchedule> csList = viewCommonSchedule(index);
				commonScheduleLbl.setText(csList.toString());
				
				List<Comment> cmtList = viewComment(player.getBackNumber(), index);
				commentLbl2.setText(cmtList.toString());
			}
		});

		one.add(comboBox);
		

		// 텍스트 필드
		startTxt = new JTextField();
		startTxt.setBounds(116, 73, 81, 21);
		one.add(startTxt);
		startTxt.setColumns(10);

		endTxt = new JTextField();
		endTxt.setBounds(297, 73, 81, 21);
		one.add(endTxt);
		endTxt.setColumns(10);

		contentTxt = new JTextField();
		contentTxt.setBounds(446, 73, 362, 21);
		one.add(contentTxt);
		contentTxt.setColumns(10);

		// 버튼
		// 일정등록
		JButton registrationBtn = new JButton("등록");
		registrationBtn.setBounds(843, 72, 62, 23);
		one.add(registrationBtn);
		
		registrationBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int number = player.getBackNumber();

				String selectedDate = comboBox.getSelectedItem().toString();

				registerSchedule(number, selectedDate, startTxt.getText(), endTxt.getText(), contentTxt.getText(), "선수");
			}
		});
		
		// JTabel
		JScrollPane scrolledTable = new JScrollPane((Component) null);
		scrolledTable.setBounds(377, 112, 477, 306);
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		one.add(scrolledTable);
		
		table = new JTable(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC2DC\uC791 \uC2DC\uAC04", "\uC885\uB8CC \uC2DC\uAC04", "\uB0B4\uC6A9"
			}
		));
		scrolledTable.setViewportView(table);
		
		
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
		

		
		
		


		// 컨디션 등록 탭
		two = new JPanel();
		pane.addTab("컨디션", two);
		two.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(41, 112, 279, 290);
		two.add(panel);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(351, 379, 65, 23);
		two.add(btnNewButton);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(351, 112, 279, 246);
		two.add(panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("코멘트");
		lblNewLabel_1.setBounds(670, 112, 260, 148);
		two.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("공동일정 : 이것저것 훈련할 계획이다. 운동장으로 나와라");
		lblNewLabel_2.setBounds(41, 26, 870, 65);
		two.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("New button");
		btnNewButton_1.setBounds(449, 379, 65, 23);
		two.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(551, 379, 65, 23);
		two.add(btnNewButton_2);

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

	// 코멘트 리스트 생성 메소드
	private static List<Comment> viewComment(int number, String datetime) {
		List<Comment> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT SUBSTRING(datetime, 12, 5), schedulecomment FROM comment \r\n"
					+ "WHERE SUBSTRING(datetime, 1, 10) = ? AND number = ? AND NOT schedulecomment IS NULL;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, datetime);
			stmt.setInt(2, number);

			rs = stmt.executeQuery();
			while (rs.next()) {
				String time = rs.getString(1);
				String scheduleComment = rs.getString(2);

				Comment comment = new Comment(time, scheduleComment);
				list.add(comment);
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

	// 코멘트 보여주는 메소드
	public static void main(String[] args) {
		new PlayerTab();

	}
}
