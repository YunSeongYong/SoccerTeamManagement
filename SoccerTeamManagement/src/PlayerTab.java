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
import javax.swing.JScrollPane;

public class PlayerTab extends JFrame implements ChangeListener {
	JTabbedPane pane;
	private JTextField startTxt;
	private JTextField endTxt;
	private JTextField contentTxt;
	public Player player;

	public PlayerTab() {
		player = new Player();
		JPanel one, two;
		pane = new JTabbedPane();

		one = new JPanel();
		one.setLayout(null);

		pane.addTab("개인일정", one);

		// 개인일정 탭
		// 라벨
		JLabel label = new JLabel("공동일정");
		label.setBounds(56, 46, 57, 42);
		one.add(label);

		JLabel startLbl = new JLabel("시작 시간");
		startLbl.setBounds(56, 116, 57, 15);
		one.add(startLbl);

		JLabel endLbl = new JLabel("끝나는 시간");
		endLbl.setBounds(219, 116, 73, 15);
		one.add(endLbl);

		JLabel scheduleLbl = new JLabel("일정");
		scheduleLbl.setBounds(403, 116, 31, 15);
		one.add(scheduleLbl);

		JLabel commentLbl = new JLabel("코멘트");
		commentLbl.setBounds(93, 373, 49, 103);
		one.add(commentLbl);

		JLabel commonScheduleLbl = new JLabel("공동일정");
		commonScheduleLbl.setBounds(125, 40, 764, 54);
		one.add(commonScheduleLbl);

		// 콤보박스
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(783, 9, 170, 21);
		LocalDate currentDate = LocalDate.now();
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
			}
		});

		one.add(comboBox);

		// 텍스트 필드
		startTxt = new JTextField();
		startTxt.setBounds(116, 113, 81, 21);
		one.add(startTxt);
		startTxt.setColumns(10);

		endTxt = new JTextField();
		endTxt.setBounds(304, 113, 81, 21);
		one.add(endTxt);
		endTxt.setColumns(10);

		contentTxt = new JTextField();
		contentTxt.setBounds(446, 113, 362, 21);
		one.add(contentTxt);
		contentTxt.setColumns(10);

		// 버튼
		// 일정등록
		JButton registrationBtn = new JButton("등록");
		registrationBtn.setBounds(843, 112, 62, 23);
		one.add(registrationBtn);

		registrationBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int number = player.getBackNumber();

				String selectedDate = comboBox.getSelectedItem().toString();

				registerSchedule(number, selectedDate, startTxt.getText(), endTxt.getText(), contentTxt.getText(), "선수");
			}
		});

		JButton updateBtn = new JButton("수정");
		updateBtn.setBounds(768, 161, 62, 23);
		one.add(updateBtn);

		JButton deleteBtn = new JButton("삭제");
		deleteBtn.setBounds(843, 161, 62, 23);
		one.add(deleteBtn);

		// 체크박스
		JCheckBox cbox1 = new JCheckBox("New check box");
		cbox1.setBounds(93, 161, 663, 23);
		one.add(cbox1);

		JCheckBox cbox2 = new JCheckBox("");
		cbox2.setBounds(92, 197, 21, 23);
		one.add(cbox2);

		JCheckBox cbox3 = new JCheckBox("");
		cbox3.setBounds(93, 240, 20, 23);
		one.add(cbox3);

		JCheckBox cbox4 = new JCheckBox("New check box");
		cbox4.setBounds(93, 277, 20, 23);
		one.add(cbox4);

		JCheckBox cbox5 = new JCheckBox("New check box");
		cbox5.setBounds(93, 317, 20, 23);
		one.add(cbox5);

		JScrollPane commentScroll = new JScrollPane();
		commentScroll.setBounds(154, 373, 722, 103);
		one.add(commentScroll);

		// 컨디션 등록 탭
		two = new JPanel();
		two.add(new JLabel("컨디션"));
		two.add(new JTextField("문자를 입력하세요"));
		pane.addTab("컨디션", two);

		pane.setSelectedIndex(0);
		pane.addChangeListener(this);
		this.getContentPane().add("Center", pane);

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

	public static void main(String[] args) {
		new PlayerTab();

	}
}
