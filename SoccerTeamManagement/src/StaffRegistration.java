import java.awt.Color;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import dbutil.DBUtil;
import java.awt.Font;
import javax.swing.JToolBar;

public class StaffRegistration extends JFrame {
	private JTextField backnumTf;
	private JTextField nameTf;
	private JTextField sickTf;
	private JTextField timeTf;
	private List<DoctorAppointment> doctorList = new ArrayList<>();
	private JComboBox<LocalDate> daycomboBox;
	private JComboBox<String> doctorcomboBox;
	public Player player;
	JTabbedPane pane1;
	JLabel lbl;
	private JFrame frame;
	private File selectedFile;
	private Image selectedImage;
	private JLabel 이미지라벨;
	private JPanel 일정창;
	private JPanel 컨디션창;
	private JPanel 의사소견창;
	private static JComboBox 선수정보콤보박스;
	private JPanel 개인정보창;
	private JTextField 이름수정텍스트필드;
	private JTextField 신장수정텍스트필드;
	private JTextField 몸무게수정텍스트필드;
	private JTextField 나이수정텍스트필드;
	private JTextField 포지션수정텍스트필드;
	private JTextField 담당코치수정텍스트필드;
	private JTextField 등번호수정텍스트필드;
	private JPanel 이미지등록수정창;
	private JLabel 선수정보수정라벨;
	private List<Player> playerList;
	private List<Schedule> scheduleList;
	private List<Condition> conditionList;
	private List<DoctorAppointment> doctorAppointmentList;
	private List<Comment> commentList;
	private List<String> selectedDates = new ArrayList<>();
	private List<String> selectedStartTimes = new ArrayList<>();
	private JTextField 담당의사수정텍스트필드;
	private JScrollPane scrolledTable;
	private JTable table1;
	private static JTable scheduleTable;
	private static JComboBox 날짜콤보박스;
	private String 일정창_콤보박스에서선택한날짜;
	private int 일정창_선수정보콤보박스에서선택한등번호;
	private JScrollPane scrolledTable_1;
	private JTable conditionTable;
	private JTable conditionTable2;
	private JTextArea 선수목록_컨디션_선수컨디션텍스트박스;
	private JLabel 선수컨디션라벨;
	private JScrollPane scrollPane_1;
	private JTextArea 선수목록_컨디션_코멘트텍스트박스;
	private JLabel 코멘트작성라벨;
	private JLabel lblNewLabel_4;
	private JLabel 컨디션의사소견_의사소견라벨;
	private JScrollPane scrollPane_3;
	private JTextArea 컨디션의사소견_의사소견_의사소견텍스트박스;
	private JScrollPane scrolledTable_2;
	private JTable doctorAppointmentTable;
	private JScrollPane scrollPane_4;
	private JLabel 컨디션의사소견_의사소견_코치코멘트라벨;
	private JTextArea 선수목록_의사소견_코치코멘트텍스트박스;
	private JButton 선수목록_의사소견_저장버튼;
	private JLabel lblNewLabel_11;
	private JLabel lblNewLabel_13;
	private JLabel 날짜라벨;
	private JLabel 선수정보라벨;
	public Condition condition;
	private JButton reservationBtn;
	private JLabel hintLbl;
	private JTextField 시간텍스트필드;
	private JTextField 일정텍스트필드;
	private JButton 저장버튼;
	private JLabel 컨디션의사소견_의사소견_감독코멘트라벨;
	private JTextArea 컨디션의사소견_감독코멘트텍스트박스;
	private JButton 수정버튼;
	private JButton 삭제버튼;
	private String 시간텍스트;
	private JLabel lblNewLabel_2;

	public StaffRegistration() {
		JPanel one, two;
		pane1 = new JTabbedPane();
		pane1.setBounds(0, 104, 984, 457);

		one = new JPanel();
		pane1.addTab("선수목록", one);

		JButton 개인정보버튼 = new JButton("");
		개인정보버튼.setOpaque(false);
		개인정보버튼.setContentAreaFilled(false);
		개인정보버튼.setBorderPainted(false);
		개인정보버튼.setFocusPainted(false);
		개인정보버튼.setBounds(0, 102, 120, 48);
		개인정보버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				날짜콤보박스.setVisible(false);
				날짜라벨.setVisible(false);
				선수정보콤보박스.setVisible(true);
				선수정보라벨.setVisible(true);
				개인정보창.setVisible(true);
				일정창.setVisible(false);
				컨디션창.setVisible(false);
				의사소견창.setVisible(false);
			}
		});
		one.setLayout(null);
		
				의사소견창 = new JPanel();
				의사소견창.setBounds(164, 82, 815, 351);
				one.add(의사소견창);
				의사소견창.setLayout(null);
				
						lblNewLabel_4 = new JLabel("");
						lblNewLabel_4.setBounds(69, 196, 57, 15);
						의사소견창.add(lblNewLabel_4);
						
								컨디션의사소견_의사소견라벨 = new JLabel("의사소견");
								컨디션의사소견_의사소견라벨.setHorizontalAlignment(SwingConstants.CENTER);

								컨디션의사소견_의사소견라벨.setBounds(159, 152, 82, 15);
								의사소견창.add(컨디션의사소견_의사소견라벨);
								
										scrollPane_3 = new JScrollPane();
										scrollPane_3.setBounds(105, 177, 185, 156);
										
										의사소견창.add(scrollPane_3);
										
												컨디션의사소견_의사소견_의사소견텍스트박스 = new JTextArea();
												컨디션의사소견_의사소견_의사소견텍스트박스.setForeground(Color.BLACK);
												컨디션의사소견_의사소견_의사소견텍스트박스.setLineWrap(true);
												scrollPane_3.setViewportView(컨디션의사소견_의사소견_의사소견텍스트박스);
												컨디션의사소견_의사소견_의사소견텍스트박스.setEnabled(false);
												
														scrolledTable_2 = new JScrollPane((Component) null);
														scrolledTable_2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
														scrolledTable_2.setBounds(84, 18, 616, 124);
														의사소견창.add(scrolledTable_2);
														
																conditionTable2 = new JTable(new DefaultTableModel(new Object[][] {},
																		new String[] { "등 번호", "선수명", "선수 컨디션", "시간" }));

								컨디션의사소견_의사소견라벨.setBounds(132, 152, 82, 15);
								의사소견창.add(컨디션의사소견_의사소견라벨);
								
										scrollPane_3 = new JScrollPane();
										scrollPane_3.setBounds(69, 177, 217, 156);
										의사소견창.add(scrollPane_3);
										
												컨디션의사소견_의사소견_의사소견텍스트박스 = new JTextArea();
												컨디션의사소견_의사소견_의사소견텍스트박스.setForeground(Color.BLACK);
												컨디션의사소견_의사소견_의사소견텍스트박스.setLineWrap(true);
												scrollPane_3.setViewportView(컨디션의사소견_의사소견_의사소견텍스트박스);
												컨디션의사소견_의사소견_의사소견텍스트박스.setEnabled(false);
												
														scrolledTable_2 = new JScrollPane((Component) null);
														scrolledTable_2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
														scrolledTable_2.setBounds(84, 18, 616, 124);
														의사소견창.add(scrolledTable_2);
														
																conditionTable2 = new JTable(new DefaultTableModel(new Object[][] {},
																		new String[] { "number", "playername", "playercondition", "when" }));
																scrolledTable_2.setViewportView(conditionTable2);
																
																		scrollPane_4 = new JScrollPane();
																		scrollPane_4.setBounds(567, 177, 185, 156);
																		의사소견창.add(scrollPane_4);
																		
																				선수목록_의사소견_코치코멘트텍스트박스 = new JTextArea();
																				선수목록_의사소견_코치코멘트텍스트박스.setLineWrap(true);
																				scrollPane_4.setViewportView(선수목록_의사소견_코치코멘트텍스트박스);
																				
																						컨디션의사소견_의사소견_코치코멘트라벨 = new JLabel("코치 코멘트");
																						컨디션의사소견_의사소견_코치코멘트라벨.setHorizontalAlignment(SwingConstants.CENTER);
																						컨디션의사소견_의사소견_코치코멘트라벨.setBounds(602, 152, 123, 15);
																						의사소견창.add(컨디션의사소견_의사소견_코치코멘트라벨);
																						
																								선수목록_의사소견_저장버튼 = new JButton("저장");
																								선수목록_의사소견_저장버튼.addActionListener(new ActionListener() {
																									public void actionPerformed(ActionEvent arg0) {

																										String comment = 선수목록_의사소견_코치코멘트텍스트박스.getText();
																										선수목록_의사소견_코멘트입력하고저장버튼누르면데이터베이스로이동하는메소드(일정창_선수정보콤보박스에서선택한등번호, comment);
																										JOptionPane.showMessageDialog(null, "저장되었습니다");
																									}
																								});
																								선수목록_의사소견_저장버튼.setBounds(655, 343, 97, 23);
																								의사소견창.add(선수목록_의사소견_저장버튼);
																								
																										컨디션의사소견_의사소견_감독코멘트라벨 = new JLabel("감독 코멘트");
																										컨디션의사소견_의사소견_감독코멘트라벨.setHorizontalAlignment(SwingConstants.CENTER);
																										컨디션의사소견_의사소견_감독코멘트라벨.setBounds(359, 152, 123, 15);
																										의사소견창.add(컨디션의사소견_의사소견_감독코멘트라벨);
																										
																												JScrollPane scrollPane_2 = new JScrollPane();
																												scrollPane_2.setBounds(336, 177, 185, 156);
																												의사소견창.add(scrollPane_2);
																												
																														컨디션의사소견_감독코멘트텍스트박스 = new JTextArea();
																														컨디션의사소견_감독코멘트텍스트박스.setForeground(Color.BLACK);
																														컨디션의사소견_감독코멘트텍스트박스.setLineWrap(true);
																														scrollPane_2.setViewportView(컨디션의사소견_감독코멘트텍스트박스);
																														의사소견창.setVisible(false);
																														컨디션의사소견_감독코멘트텍스트박스.setEnabled(false);

		일정창 = new JPanel();
		일정창.setBounds(164, 82, 815, 351);
		one.add(일정창);
		일정창.setLayout(null);

		scrolledTable = new JScrollPane((Component) null);
		scrolledTable.setBounds(39, 24, 617, 187);
		일정창.add(scrolledTable);
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		scheduleTable = new JTable(
				new DefaultTableModel(new Object[][] {}, new String[] { "시작시간", "끝나는시간", "일정", "승인여부" })) {
		};
		scrolledTable.setViewportView(scheduleTable);
		scheduleTable.addMouseListener(new MouseAdapter() {
	         

			@Override
	         public void mouseClicked(MouseEvent e) {
	        	 삭제버튼.setEnabled(true);
	            수정버튼.setEnabled(true);
	            저장버튼.setEnabled(false);
	            int selectedRow = scheduleTable.getSelectedRow();
	            if (selectedRow >= 0) {
	               DefaultTableModel model = (DefaultTableModel) scheduleTable.getModel();
	               String starttime = (String) model.getValueAt(selectedRow, 0);
	               String content = (String) model.getValueAt(selectedRow, 2);
	               
	               시간텍스트 = starttime;
	               System.out.println(시간텍스트);
	               시간텍스트필드.setText(starttime);
	               일정텍스트필드.setText(content);
	               
	            }
	         }
	      });
		
		
		

		저장버튼 = new JButton("저장");
		저장버튼.setBounds(678, 277, 97, 23);
		일정창.add(저장버튼);
		저장버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				선수일정추가();
			}
		});
		

		수정버튼 = new JButton("수정");
		수정버튼.setBounds(678, 302, 97, 23);
		일정창.add(수정버튼);
		수정버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				선수일정수정();

			}
		});

		삭제버튼 = new JButton("삭제");
		삭제버튼.setBounds(678, 330, 97, 23);
		일정창.add(삭제버튼);
		삭제버튼.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            int selectedRow = scheduleTable.getSelectedRow(); // 선택한 행의 인덱스 가져오기
	            if (selectedRow != -1) { // 선택한 행이 있는 경우
	               DefaultTableModel tableModel = (DefaultTableModel) scheduleTable.getModel();
	               String date = 날짜콤보박스.getSelectedItem().toString();
	               String starttime = tableModel.getValueAt(selectedRow, 0).toString();

	               // 삭제 메소드 호출
	               선수일정삭제(date, starttime);

	               // 테이블에서 선택한 행 삭제
	               tableModel.removeRow(selectedRow);
	            }
	         }
	      });
		JLabel 시간라벨 = new JLabel("시간");
		시간라벨.setHorizontalAlignment(SwingConstants.CENTER);
		시간라벨.setBounds(39, 255, 42, 15);
		일정창.add(시간라벨);

		시간텍스트필드 = new JTextField();
		시간텍스트필드.setBounds(93, 252, 116, 21);
		일정창.add(시간텍스트필드);
		시간텍스트필드.setColumns(10);

		JLabel 일정라벨 = new JLabel("일정");
		일정라벨.setHorizontalAlignment(SwingConstants.CENTER);
		일정라벨.setBounds(39, 306, 42, 15);
		일정창.add(일정라벨);

		일정텍스트필드 = new JTextField();
		일정텍스트필드.setBounds(93, 303, 426, 21);
		일정창.add(일정텍스트필드);
		일정텍스트필드.setColumns(10);
		일정창.setVisible(false);
		one.add(개인정보버튼);

		JButton 일정버튼 = new JButton("");
		일정버튼.setOpaque(false);
		일정버튼.setContentAreaFilled(false);
		일정버튼.setBorderPainted(false);
		일정버튼.setFocusPainted(false);
		일정버튼.setBounds(0, 216, 120, 49);
		일정버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				날짜콤보박스.setVisible(true);
				날짜라벨.setVisible(true);
				선수정보콤보박스.setVisible(true);
				선수정보라벨.setVisible(true);
				개인정보창.setVisible(false);
				일정창.setVisible(true);
				컨디션창.setVisible(false);
				의사소견창.setVisible(false);
			}
		});
		one.add(일정버튼);

		JButton 컨디션의사소견버튼 = new JButton("");
		컨디션의사소견버튼.setOpaque(false);
		컨디션의사소견버튼.setContentAreaFilled(false);
		컨디션의사소견버튼.setBorderPainted(false);
		컨디션의사소견버튼.setFocusPainted(false);
		컨디션의사소견버튼.setBounds(0, 160, 120, 46);
		컨디션의사소견버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				날짜콤보박스.setVisible(true);
				날짜라벨.setVisible(true);
				선수정보콤보박스.setVisible(true);
				선수정보라벨.setVisible(true);
				개인정보창.setVisible(false);
				일정창.setVisible(false);
				컨디션창.setVisible(false);
				의사소견창.setVisible(true);
			}
		});
		one.add(컨디션의사소견버튼);

		날짜콤보박스 = new JComboBox();
		날짜콤보박스.setBounds(424, 26, 94, 21);
		one.add(날짜콤보박스);

		LocalDate currentDate = LocalDate.now();
		LocalDate minusDate = currentDate.minusDays(15);
		List<LocalDate> calendar = new ArrayList<LocalDate>();
		for (int i = 0; i < 30; i++) {
			날짜콤보박스.addItem(minusDate.plusDays(i));
		}
		날짜콤보박스.setSelectedIndex(15);
		날짜콤보박스.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDate selectedDate = (LocalDate) 날짜콤보박스.getSelectedItem();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				일정창_콤보박스에서선택한날짜 = selectedDate.format(formatter);

				// 현재 날짜 가져오기
				LocalDate currentDate = LocalDate.now();

				// 선택한 날짜와 현재 날짜 비교
				if (selectedDate.isBefore(currentDate)) {
					// 선택한 날짜가 현재 날짜보다 이전인 경우
					저장버튼.setEnabled(false); // 저장 버튼 비활성화
					수정버튼.setEnabled(false);
					삭제버튼.setEnabled(false);
				} else {
					저장버튼.setEnabled(true);
					수정버튼.setEnabled(true);
					삭제버튼.setEnabled(true);
				}

				System.out.println("콤보박스에서 선택한 날짜 출력: " + 일정창_콤보박스에서선택한날짜);
				선수목록_일정_날짜와등번호콤보박스선택시일정창의선수일정표시하는메소드(scheduleList);
			}
		});
		날짜콤보박스.setVisible(false);

		선수정보콤보박스 = new JComboBox();
		선수정보콤보박스.setBounds(756, 26, 87, 21);
		one.add(선수정보콤보박스);
//		선수목록_선수정보콤보박스목록만드는메소드();
		Staff staff = new Staff();
		addPlayer(staff.getName());

		선수정보콤보박스.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) 선수정보콤보박스.getSelectedItem();
				일정창_선수정보콤보박스에서선택한등번호 = Integer.parseInt(selectedItem.split(" - ")[0]);
				System.out.println(일정창_선수정보콤보박스에서선택한등번호);
				선수목록_개인정보_선수정보콤보박스의등번호로선수정보의모든정보를리스트에저장하는메소드(일정창_선수정보콤보박스에서선택한등번호);
				선수목록_개인정보_콤보박스에서선택한등번호로선수정보의모든텍스트필드에추가하는메소드(playerList);
				선수목록_일정_선수정보콤보박스의등번호로선수일정의모든정보를리스트에저장하는메소드(일정창_선수정보콤보박스에서선택한등번호);
				선수목록_일정_날짜와등번호콤보박스선택시일정창의선수일정표시하는메소드(scheduleList);
				선수목록_컨디션_콤보박스에서선수를선택하면해당선수의컨디션리스트에저장되는메소드(일정창_선수정보콤보박스에서선택한등번호);
				선수목록_의사소견_콤보박스에서선수를선택하면해당선수의의사소견리스트에저장되는메소드(일정창_선수정보콤보박스에서선택한등번호);
				선수목록_의사소견_콤보박스에서선수를선택하면해당선수의코멘트리스트에저장되는메소드(일정창_선수정보콤보박스에서선택한등번호);
				선수목록_컨디션_선수와날짜콤보박스를선택했을때해당하는선수의컨디션텍스트에나오게하는메소드(conditionList);
				선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의컨디션텍스트에나오게하는메소드(doctorAppointmentList);
				선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의의사소견텍스트에나오게하는메소드(commentList, 일정창_선수정보콤보박스에서선택한등번호);
				선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의감독코멘트텍스트에나오게하는메소드(commentList, 일정창_선수정보콤보박스에서선택한등번호);
				선수목록_컨디션_컨디션리스트와날짜를바탕으로JTable에목록을띄우는메소드(conditionList);
				선수목록_컨디션_선수와날짜콤보박스를선택했을때해당하는선수의컨디션텍스트에나오게하는메소드(conditionList);
				선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의컨디션텍스트에나오게하는메소드(doctorAppointmentList);
				선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의의사소견텍스트에나오게하는메소드(commentList, 일정창_선수정보콤보박스에서선택한등번호);
				선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의감독코멘트텍스트에나오게하는메소드(commentList, 일정창_선수정보콤보박스에서선택한등번호);
				선수목록_컨디션_컨디션리스트와날짜를바탕으로JTable에목록을띄우는메소드(conditionList);
			}

		});
		선수정보콤보박스.setVisible(false);

		컨디션창 = new JPanel();
		컨디션창.setBounds(164, 82, 815, 351);
		one.add(컨디션창);
		컨디션창.setLayout(null);

		scrolledTable_1 = new JScrollPane((Component) null);
		scrolledTable_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrolledTable_1.setBounds(23, 28, 694, 110);
		컨디션창.add(scrolledTable_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(146, 177, 195, 138);
		컨디션창.add(scrollPane);

		선수목록_컨디션_선수컨디션텍스트박스 = new JTextArea();
		scrollPane.setViewportView(선수목록_컨디션_선수컨디션텍스트박스);

		선수컨디션라벨 = new JLabel("선수 컨디션");
		선수컨디션라벨.setHorizontalAlignment(SwingConstants.CENTER);
		선수컨디션라벨.setBounds(194, 152, 96, 15);
		컨디션창.add(선수컨디션라벨);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(489, 177, 195, 138);
		컨디션창.add(scrollPane_1);

		선수목록_컨디션_코멘트텍스트박스 = new JTextArea();
		scrollPane_1.setViewportView(선수목록_컨디션_코멘트텍스트박스);

		코멘트작성라벨 = new JLabel("코멘트 작성");
		코멘트작성라벨.setHorizontalAlignment(SwingConstants.CENTER);
		코멘트작성라벨.setBounds(539, 152, 96, 15);
		컨디션창.add(코멘트작성라벨);

		JButton 선수목록_컨디션_저장버튼 = new JButton("저장");
		선수목록_컨디션_저장버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String comment = 선수목록_컨디션_코멘트텍스트박스.getText();
				선수목록_컨디션_코멘트입력하고저장버튼누르면데이터베이스로이동하는메소드(일정창_선수정보콤보박스에서선택한등번호, comment);
			}
		});
		선수목록_컨디션_저장버튼.setBounds(587, 325, 130, 41);
		컨디션창.add(선수목록_컨디션_저장버튼);

		lblNewLabel_11 = new JLabel("선수를 선택하면 해당하는 선수의 전체 목록이 나옵니다");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_11.setBounds(201, 3, 372, 15);
		컨디션창.add(lblNewLabel_11);

		lblNewLabel_13 = new JLabel("날짜를 선택하면 해당하는 날짜의 선수 컨디션이 나옵니다");
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_13.setBounds(49, 338, 355, 15);
		컨디션창.add(lblNewLabel_13);
		
		lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon(StaffRegistration.class.getResource("/image/선수목록화면-일정-배경1.jpg")));
		lblNewLabel_2.setBounds(0, 0, 815, 341);
		컨디션창.add(lblNewLabel_2);
		컨디션창.setVisible(false);

		날짜라벨 = new JLabel("날짜");
		날짜라벨.setHorizontalAlignment(SwingConstants.RIGHT);
		날짜라벨.setForeground(Color.WHITE);
		날짜라벨.setFont(new Font("나눔고딕", Font.BOLD, 15));
		날짜라벨.setBounds(372, 29, 44, 15);
		one.add(날짜라벨);

		선수정보라벨 = new JLabel("선수정보");
		선수정보라벨.setHorizontalAlignment(SwingConstants.RIGHT);
		선수정보라벨.setFont(new Font("나눔고딕", Font.BOLD, 15));
		선수정보라벨.setForeground(Color.WHITE);
		선수정보라벨.setBounds(678, 29, 66, 15);
		one.add(선수정보라벨);
		날짜라벨.setVisible(false);
		선수정보라벨.setVisible(false);

		개인정보창 = new JPanel();
		개인정보창.setBounds(164, 82, 840, 376);
		one.add(개인정보창);
		개인정보창.setLayout(null);
		개인정보창.setBackground(Color.WHITE);
		개인정보창.setVisible(false);

		이름수정텍스트필드 = new JTextField();
		이름수정텍스트필드.setHorizontalAlignment(SwingConstants.CENTER);
		이름수정텍스트필드.setForeground(Color.WHITE);
		이름수정텍스트필드.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		이름수정텍스트필드.setOpaque(false);
		이름수정텍스트필드.setBorder(null);
		이름수정텍스트필드.setColumns(10);
		이름수정텍스트필드.setBounds(145, 220, 116, 21);
		개인정보창.add(이름수정텍스트필드);

		신장수정텍스트필드 = new JTextField();
		신장수정텍스트필드.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		신장수정텍스트필드.setForeground(Color.WHITE);
		신장수정텍스트필드.setOpaque(false);
		신장수정텍스트필드.setBorder(null);
		신장수정텍스트필드.setColumns(10);
		신장수정텍스트필드.setBounds(473, 119, 116, 21);
		개인정보창.add(신장수정텍스트필드);

		몸무게수정텍스트필드 = new JTextField();
		몸무게수정텍스트필드.setForeground(Color.WHITE);
		몸무게수정텍스트필드.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		몸무게수정텍스트필드.setOpaque(false);
		몸무게수정텍스트필드.setBorder(null);
		몸무게수정텍스트필드.setColumns(10);
		몸무게수정텍스트필드.setBounds(473, 151, 116, 21);
		개인정보창.add(몸무게수정텍스트필드);

		나이수정텍스트필드 = new JTextField();
		나이수정텍스트필드.setForeground(Color.WHITE);
		나이수정텍스트필드.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		나이수정텍스트필드.setOpaque(false);
		나이수정텍스트필드.setBorder(null);
		나이수정텍스트필드.setColumns(10);
		나이수정텍스트필드.setBounds(473, 86, 116, 21);
		개인정보창.add(나이수정텍스트필드);

		포지션수정텍스트필드 = new JTextField();
		포지션수정텍스트필드.setForeground(Color.WHITE);
		포지션수정텍스트필드.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		포지션수정텍스트필드.setOpaque(false);
		포지션수정텍스트필드.setBorder(null);
		포지션수정텍스트필드.setColumns(10);
		포지션수정텍스트필드.setBounds(473, 183, 116, 21);
		개인정보창.add(포지션수정텍스트필드);

		담당코치수정텍스트필드 = new JTextField();
		담당코치수정텍스트필드.setHorizontalAlignment(SwingConstants.LEFT);
		담당코치수정텍스트필드.setForeground(Color.WHITE);
		담당코치수정텍스트필드.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		담당코치수정텍스트필드.setOpaque(false);
		담당코치수정텍스트필드.setBorder(null);
		담당코치수정텍스트필드.setColumns(10);
		담당코치수정텍스트필드.setBounds(473, 215, 164, 21);
		개인정보창.add(담당코치수정텍스트필드);

		담당의사수정텍스트필드 = new JTextField();
		담당의사수정텍스트필드.setForeground(Color.WHITE);
		담당의사수정텍스트필드.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		담당의사수정텍스트필드.setOpaque(false);
		담당의사수정텍스트필드.setBorder(null);
		담당의사수정텍스트필드.setColumns(10);
		담당의사수정텍스트필드.setBounds(473, 245, 164, 21);
		개인정보창.add(담당의사수정텍스트필드);

		등번호수정텍스트필드 = new JTextField();
		등번호수정텍스트필드.setOpaque(false);
		등번호수정텍스트필드.setBorder(null);
		등번호수정텍스트필드.setForeground(Color.WHITE);
		등번호수정텍스트필드.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		등번호수정텍스트필드.setHorizontalAlignment(SwingConstants.CENTER);
		등번호수정텍스트필드.setColumns(10);
		등번호수정텍스트필드.setBounds(117, 56, 24, 25);
		개인정보창.add(등번호수정텍스트필드);

		이미지등록수정창 = new JPanel();
		
		이미지등록수정창.setBounds(105, 75, 192, 178);
		이미지등록수정창.setOpaque(false);
		개인정보창.add(이미지등록수정창);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(StaffRegistration.class.getResource("/image/선수목록화면-선수정보-코치1.jpg")));
		lblNewLabel_1.setBounds(0, 0, 815, 352);
		개인정보창.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(StaffRegistration.class.getResource("/image/선수목록-배경2.jpg")));
		lblNewLabel.setBounds(0, 0, 979, 428);
		one.add(lblNewLabel);

		// ===========================================================================================================================================================

		two = new JPanel();
		pane1.addTab("예약하기", two);
		two.setLayout(null);

		JLabel dayLabel = new JLabel("날짜");
		dayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dayLabel.setBounds(71, 10, 47, 15);
		two.add(dayLabel);

		daycomboBox = new JComboBox();
		daycomboBox.setBounds(130, 7, 97, 21);
		two.add(daycomboBox);

		LocalDate currentDate2 = LocalDate.now();
		for (int i = 0; i < 30; i++) {
			daycomboBox.addItem(currentDate2.plusDays(i));
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

		JButton deleteBtn = new JButton("삭제");
		JButton changeBtn = new JButton("수정");
		JButton viewButton = new JButton("조회");
		viewButton.setBounds(417, 6, 97, 23);
		two.add(viewButton);
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayDoctorSchedule();
				deleteBtn.setEnabled(false);
				changeBtn.setEnabled(false);
				reservationBtn.setEnabled(true);
				backnumTf.setText("");
				nameTf.setText("");
				sickTf.setText("");
				timeTf.setText("");
			}
		});

		JScrollPane scrolledTable = new JScrollPane((Component) null);
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrolledTable.setBounds(71, 55, 803, 221);
		two.add(scrolledTable);

		table1 = new JTable();
		table1.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\uB4F1\uBC88\uD638", "\uC120\uC218\uC774\uB984", "\uC2DC\uAC04", "\uC99D\uC0C1" }));
		scrolledTable.setViewportView(table1);

		// JTable 모델을 생성하고 컬럼 이름을 설정합니다.
		DefaultTableModel tableModel = new DefaultTableModel(new Object[][] {},
				new String[] { "등번호", "선수 이름", "시간", "증상" }) {
			@Override
			public boolean isCellEditable(int row, int column) {

				return false;
			}
		};
		table1.getTableHeader().setReorderingAllowed(false);
		table1.getTableHeader().setResizingAllowed(false);
		table1.setModel(tableModel);

		deleteBtn.setEnabled(false);
		changeBtn.setEnabled(false);
		table1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				deleteBtn.setEnabled(true);
				changeBtn.setEnabled(true);
				reservationBtn.setEnabled(false);
				int selectedRow = table1.getSelectedRow();
				if (selectedRow >= 0) {
					DefaultTableModel model = (DefaultTableModel) table1.getModel();
					int number = (int) model.getValueAt(selectedRow, 0);
					String playerName = (String) model.getValueAt(selectedRow, 1);
					String time = (String) model.getValueAt(selectedRow, 2);
					String condition = (String) model.getValueAt(selectedRow, 3);

					backnumTf.setText(Integer.toString(number));
					nameTf.setText(playerName);
					timeTf.setText(time);
					sickTf.setText(condition);
				}
			}
		});

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

		nameTf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String playerName = nameTf.getText().trim();

				// 선수 이름을 바탕으로 등번호를 가져와 등번호 텍스트 필드에 설정합니다
				int backNumber = getBackNumberFromDatabase(playerName);
				backnumTf.setText(Integer.toString(backNumber));
			}
		});

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

		reservationBtn = new JButton("예약");
		reservationBtn.setBounds(815, 332, 97, 23);
		two.add(reservationBtn);

		reservationBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertAppointment();
			}
		});

		deleteBtn.setBounds(815, 398, 97, 23);
		two.add(deleteBtn);

		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table1.getSelectedRow();
				if (row >= 0) {
					TableModel data = table1.getModel();
					int number = (int) data.getValueAt(row, 0);
					String date = daycomboBox.getSelectedItem().toString();
					String time = (String) data.getValueAt(row, 2);

					deleteAppointment(number, date, time); // Delete appointment from the database
					removeRecord(row); // Remove the selected row from the table
					changeBtn.setEnabled(false);
					deleteBtn.setEnabled(false);
					reservationBtn.setEnabled(true);
				}
			}
		});

		changeBtn.setBounds(815, 365, 97, 23);
		two.add(changeBtn);

		hintLbl = new JLabel("등번호 확인 시 선수이름창에서 엔터를 누르세요");
		hintLbl.setBounds(71, 359, 283, 15);
		two.add(hintLbl);
		hintLbl.setVisible(false);
		changeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateAppointment();
				changeBtn.setEnabled(false);
				deleteBtn.setEnabled(false);
				reservationBtn.setEnabled(true);
			}
		});

		pane1.setSelectedIndex(0);
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(StaffRegistration.class.getResource("/image/선수위-배경.jpg")));
		this.getContentPane().add("North", label);
		this.getContentPane().add("Center", pane1);

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
		DefaultTableModel model = (DefaultTableModel) table1.getModel();
		model.setRowCount(0);

		for (DoctorAppointment doctorAppointment : doctorList) {
			Object[] rowData = { doctorAppointment.getBacknumber(), doctorAppointment.getPlayerName(),
					doctorAppointment.getTime(), doctorAppointment.getCondition() };
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

	// 예약하기
	public void insertAppointment() {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();

			// 의사 콤보박스에서 선택된 값을 가져옵니다
			String selectedDate = daycomboBox.getSelectedItem().toString();
			String selectedDoctor = (String) doctorcomboBox.getSelectedItem();

			// 등번호에 일치하는 선수 이름이 맞는지 확인합니다
			if (!isPlayerNameValid()) {
				JOptionPane.showMessageDialog(this, "등번호와 선수 이름이 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
				hintLbl.setVisible(true);
				return; // 예약을 하지 않고 메서드를 종료합니다
			}

			// 예약 정보의 유효성을 검사합니다.
			if (backnumTf.getText().isEmpty() || nameTf.getText().isEmpty() || timeTf.getText().isEmpty()
					|| sickTf.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "예약 정보를 모두 입력해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
				return; // 예약 정보가 비어있는 경우 처리를 중단합니다.
			}

			stmt = conn.prepareStatement(
					"insert into appointment(number, name, date, time,`condition`, doctor) values (?, ?, ?, ?, ?, ?)");
			stmt.setInt(1, Integer.valueOf(backnumTf.getText()));
			stmt.setString(2, nameTf.getText());
			stmt.setString(3, selectedDate);
			stmt.setString(4, timeTf.getText());
			stmt.setString(5, sickTf.getText());
			stmt.setString(6, selectedDoctor);

			stmt.executeUpdate();

			// 예약 추가 후 테이블을 갱신합니다
			DefaultTableModel model = (DefaultTableModel) table1.getModel();
			model.addRow(new Object[] { backnumTf.getText(), nameTf.getText(), timeTf.getText(), sickTf.getText() });
			model.fireTableDataChanged();
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

	// 등번호에 해당하는 선수 이름이 일치하는지 확인하는 메서드
	public boolean isPlayerNameValid() {
		int backNumber = Integer.parseInt(backnumTf.getText().trim());
		String playerName = getPlayerNameFromDatabase(backNumber); // 등번호에 해당하는 선수 이름을 데이터베이스에서 가져옵니다
		String enteredName = nameTf.getText().trim(); // 사용자가 입력한 선수 이름을 가져옵니다

		return playerName.equalsIgnoreCase(enteredName); // 대소문자 구분 없이 두 이름을 비교합니다
	}

	// 등번호에 해당하는 선수 이름을 데이터베이스에서 가져오는 메서드
	public String getPlayerNameFromDatabase(int backNumber) {
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

	// 데이터베이스 appointment 데이터 삭제
	public void deleteAppointment(int number, String date, String time) {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("DELETE FROM appointment WHERE number = ? AND date = ? AND time = ?");
			stmt.setInt(1, number);
			stmt.setString(2, date);
			stmt.setString(3, time);
			stmt.executeUpdate();

			// 삭제 후 텍스트 필드들을 빈 칸으로 초기화
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

	// 테이블 기록 삭제
	public void removeRecord(int index) {
		DefaultTableModel model = (DefaultTableModel) table1.getModel();
		if (index >= 0 && index < model.getRowCount()) {
			model.removeRow(index);
		}
	}

	// 테이블 기록 수정
	public void updateAppointment() {
		String selectedDate = daycomboBox.getSelectedItem().toString();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn
					.prepareStatement("UPDATE appointment SET time = ?, `condition` = ? WHERE number = ? and date = ?");
			stmt.setString(1, timeTf.getText());
			stmt.setString(2, sickTf.getText());
			stmt.setInt(3, Integer.valueOf(backnumTf.getText()));
			stmt.setString(4, selectedDate);
			stmt.executeUpdate();

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

	// 등번호에 해당하는 선수 찾기
	public void selectName(int backNumber, JTextField nameField) {
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

	// 선수 이름을 바탕으로 등번호를 데이터베이스에서 가져오는 메서드
	public int getBackNumberFromDatabase(String playerName) {
		int backNumber = 0;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT backnumber FROM players WHERE name = ?");
			stmt.setString(1, playerName);
			rs = stmt.executeQuery();

			if (rs.next()) {
				backNumber = rs.getInt("backnumber");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}

		return backNumber;
	}

	public void 선수목록_개인정보_이미지를데이터베이스에수정하는메소드(PreparedStatement stmt) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			// 이미지를 바이트 배열로 변환
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedImage bufferedImage = new BufferedImage(selectedImage.getWidth(null), selectedImage.getHeight(null),
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = bufferedImage.createGraphics();
			graphics.drawImage(selectedImage, 0, 0, null);
			graphics.dispose();
			ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
			byte[] imageData = byteArrayOutputStream.toByteArray();

			// 선수 이미지를 업데이트하는 쿼리 실행
			String updateQuery = "UPDATE players SET image = ? WHERE backnumber = ?";
			stmt = conn.prepareStatement(updateQuery);
			stmt.setBytes(1, imageData);
			stmt.setInt(2, Integer.valueOf(등번호수정텍스트필드.getText()));
			stmt.executeUpdate();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public void 선수목록_선수정보콤보박스목록만드는메소드() {
//
//		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
//		선수정보콤보박스.setModel(model);
//
//		Connection conn = null;
//		try {
//			conn = DBUtil.getConnection();
//			String sql = "SELECT backnumber, name FROM players";
//			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//				try (ResultSet rs = stmt.executeQuery()) {
//					while (rs.next()) {
//						int backnumber = rs.getInt("backnumber");
//						String name = rs.getString("name");
//						String item = backnumber + " - " + name;
//						선수정보콤보박스.addItem(item);
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void addPlayer(String coachName) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT backnumber, name FROM players WHERE coach = ?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, coachName);

			rs = stmt.executeQuery();
			while (rs.next()) {
				int backNumber = rs.getInt(1);
				String name = rs.getString(2);
				System.out.println("등번호" + backNumber);
				System.out.println("이름" + name);

				선수정보콤보박스.addItem(backNumber + " - " + name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		} 
	}

	public List<Player> 선수목록_개인정보_선수정보콤보박스의등번호로선수정보의모든정보를리스트에저장하는메소드(int backnumber) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		playerList = new ArrayList<>();

		try {
			conn = DBUtil.getConnection(); // 연결 객체를 conn 변수에 할당
			String sql = "SELECT * from players where backnumber = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, backnumber);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int backnumber1 = rs.getInt("backnumber");
				String name = rs.getString("name");
				double height = rs.getDouble("height");
				double weight = rs.getDouble("weight");
				String age = rs.getString("birthdate");
				String position = rs.getString("position");
				String coach = rs.getString("coach");
				String doctor = rs.getString("doctor");
				Blob imagePath = rs.getBlob("image");
				Image image = ImageIO.read(imagePath.getBinaryStream());

				playerList.add(new Player(backnumber1, name, height, weight, age, position, coach, doctor, image));
				System.out.println(playerList);
			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원을 닫아주어야 합니다.
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return playerList;
	}

	public void 선수목록_개인정보_콤보박스에서선택한등번호로선수정보의모든텍스트필드에추가하는메소드(List<Player> playerList) {
		if (!playerList.isEmpty()) {
			Player player = playerList.get(0); // 첫 번째 Player 객체 가져오기

			등번호수정텍스트필드.setText(String.valueOf(player.getBackNumber()));
			이름수정텍스트필드.setText(player.getName());
			신장수정텍스트필드.setText(String.valueOf(player.getHeight()));
			몸무게수정텍스트필드.setText(String.valueOf(player.getWeight()));
			나이수정텍스트필드.setText(String.valueOf(player.getAge()));
			포지션수정텍스트필드.setText(player.getPosition());
			담당코치수정텍스트필드.setText(player.getCoach());
			담당의사수정텍스트필드.setText(player.getDoctor());

			// 이미지 표시를 위한 JLabel 생성
			Image image = player.getImage();
			JLabel imageLabel = new JLabel(new ImageIcon(image));

			// 이미지를 추가하기 전에 이전에 추가한 이미지 제거
			이미지등록수정창.removeAll();

			SwingUtilities.invokeLater(() -> {
				이미지등록수정창.add(imageLabel);
				이미지등록수정창.revalidate();
				이미지등록수정창.repaint();
			});

			// 이미지 변수 설정
			selectedImage = image;
		}
	}

	public void 선수목록_개인정보_선수수정메소드(List<Player> playerList) {
		Player player = playerList.get(0);
		String sql = "UPDATE players SET backnumber=?, name=?, height=?, weight=?, age=?, position=?, coach=?, doctor=? WHERE backnumber = ?";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Integer.valueOf(등번호수정텍스트필드.getText()));
			stmt.setString(2, 이름수정텍스트필드.getText());
			stmt.setDouble(3, Double.valueOf(신장수정텍스트필드.getText()));
			stmt.setDouble(4, Double.valueOf(몸무게수정텍스트필드.getText()));
			stmt.setInt(5, Integer.valueOf(나이수정텍스트필드.getText()));
			stmt.setString(6, 포지션수정텍스트필드.getText());
			stmt.setString(7, 담당코치수정텍스트필드.getText());
			stmt.setString(8, 담당의사수정텍스트필드.getText());
			stmt.setInt(9, player.getBackNumber());

			int result = stmt.executeUpdate();
			if (result > 0) {
				System.out.println("데이터가 성공적으로 저장되었습니다.");

			} else {
				System.out.println("데이터 저장에 실패하였습니다.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			선수목록_개인정보_이미지를데이터베이스에수정하는메소드(stmt);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void 선수목록_개인정보_선수삭제메소드(List<Player> playerList) {
		Player player = playerList.get(0);
		String sql = "delete from players where backnumber = ?";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, player.getBackNumber());

			int result = stmt.executeUpdate();
			if (result > 0) {
				System.out.println("데이터가 성공적으로 삭제되었습니다.");
			} else {
				System.out.println("데이터 삭제에 실패하였습니다.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			선수목록_개인정보_선수정보의모든텍스트필드값제거메소드();
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void 선수목록_개인정보_선수정보의모든텍스트필드값제거메소드() {
		등번호수정텍스트필드.setText("");
		이름수정텍스트필드.setText("");
		신장수정텍스트필드.setText("");
		몸무게수정텍스트필드.setText("");
		나이수정텍스트필드.setText("");
		포지션수정텍스트필드.setText("");
		담당코치수정텍스트필드.setText("");
		담당의사수정텍스트필드.setText("");
		이미지등록수정창.removeAll();
		이미지등록수정창.revalidate();
		이미지등록수정창.repaint();
	}

	public List<Schedule> 선수목록_일정_선수정보콤보박스의등번호로선수일정의모든정보를리스트에저장하는메소드(int backnumber) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		scheduleList = new ArrayList<>();

		try {
			conn = DBUtil.getConnection(); // 연결 객체를 conn 변수에 할당
			String sql = "SELECT * from playerschedule where number = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, backnumber);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int number1 = rs.getInt("number");
				String date = rs.getString("date");
				String startTime = rs.getString("starttime");
				String endTime = rs.getString("endtime");
				String content = rs.getString("content");
				String who = rs.getString("who");
				String check = rs.getString("confirm");

				scheduleList.add(new Schedule(number1, date, startTime, endTime, content, who, check));
				System.out.println(scheduleList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원을 닫아주어야 합니다.
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return scheduleList;
	}

	public void 선수목록_일정_콤보박스에서선택한등번호로일정창의테이블에추가하는메소드(List<Schedule> scheduleList) {
		List<Schedule> filteredList = new ArrayList<>();

		for (Schedule schedule : scheduleList) {
			if (schedule.getDate().equals(일정창_콤보박스에서선택한날짜)) {
				filteredList.add(schedule);
			}
		}

		DefaultTableModel tableModel = (DefaultTableModel) scheduleTable.getModel();
		// 기존의 테이블 데이터 초기화
		tableModel.setRowCount(0);

		// filteredList의 데이터를 테이블 모델에 추가
		for (Schedule schedule : filteredList) {
			Object[] rowData = { schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(),
					schedule.getContent(), false// 체크박스 데이터와 마지막 열 값 추가
			};
			tableModel.addRow(rowData);
		}

		tableModel.addTableModelListener(e -> {
			if (e.getColumn() == 4) {
				int rowIndex = e.getFirstRow();
				boolean isChecked = (boolean) tableModel.getValueAt(rowIndex, 4);
				if (isChecked) {
					System.out.println("거절");
				} else {
					System.out.println("승인");
				}
			}
		});
	}

	public void 선수목록_일정_테이블에서체크한date와starttime값출력() {
		DefaultTableModel tableModel = (DefaultTableModel) scheduleTable.getModel();
		int rowCount = tableModel.getRowCount();

		for (int i = 0; i < rowCount; i++) {
			boolean isChecked = (boolean) tableModel.getValueAt(i, 4);
			if (isChecked) {
				String date = (String) tableModel.getValueAt(i, 0);
				String starttime = (String) tableModel.getValueAt(i, 1);

				selectedDates.add(date);
				selectedStartTimes.add(starttime);
				System.out.println(selectedDates);
				System.out.println(selectedStartTimes);
			}
		}
	}

	public void 선수목록_일정_저장버튼을눌렀을때체크되어있는값의데이터를받아와서playerschedule의confirm열의값에거절삽입하기(int backnumber,
			List<String> selectedDates, List<String> selectedStartTimes) {
		String sql = "UPDATE playerschedule SET confirm = '거절' WHERE number = ? and date = ? and starttime = ?";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < selectedDates.size(); i++) {
				String date = selectedDates.get(i);
				String startTime = selectedStartTimes.get(i);

				stmt.setInt(1, backnumber);
				stmt.setString(2, date);
				stmt.setString(3, startTime);

				int result = stmt.executeUpdate();
				if (result > 0) {
					System.out.println("데이터가 성공적으로 저장되었습니다.");
				} else {
					System.out.println("데이터 저장에 실패하였습니다.");
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void 선수목록_일정_날짜와등번호콤보박스선택시일정창의선수일정표시하는메소드(List<Schedule> scheduleList) {
		try {
			List<Schedule> filteredList = new ArrayList<>();

			for (Schedule schedule : scheduleList) {
				if (schedule.getDate().equals(일정창_콤보박스에서선택한날짜)) {
					filteredList.add(schedule);
				}
			}

			DefaultTableModel tableModel = (DefaultTableModel) scheduleTable.getModel();
			// 기존의 테이블 데이터 초기화
			tableModel.setRowCount(0);

			// filteredList의 데이터를 테이블 모델에 추가
			for (Schedule schedule : filteredList) {
				Object[] rowData = { schedule.getStartTime(), schedule.getEndTime(), schedule.getContent(),
						schedule.getCheck() };
				tableModel.addRow(rowData);
			}

			// 체크박스 컬럼 추가

		} catch (NullPointerException e) {
		}

	}

	public void 선수목록_일정_코멘트입력하고저장버튼누르면데이터베이스로이동하는메소드(int backNumber, String comment, List<String> selectedStartTimes) {
		String sql = "INSERT INTO comment (number, datetime, schedulecomment, who) VALUES (?, ?, ?, '감독')";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);

			for (int i = 0; i < selectedDates.size(); i++) {
				String selectedDateStr = selectedDates.get(i);
				LocalDate selectedDate = LocalDate.parse(selectedDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String startTime = selectedStartTimes.get(i);

				// 'selectedDate'와 'startTime'을 합치기 위해 'HH:mm:ss'를 붙여줌
				String dateTimeString = selectedDate.toString() + " " + startTime;

				// 'dateTimeString'을 'yyyy-MM-dd HH:mm:ss' 형식으로 변환
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				String formattedDateTime = LocalDateTime
						.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).format(formatter);

				stmt.setInt(1, backNumber);
				stmt.setString(2, formattedDateTime);
				stmt.setString(3, comment);

				int result = stmt.executeUpdate();
				if (result > 0) {
					System.out.println("데이터가 성공적으로 저장되었습니다.");
				} else {
					System.out.println("데이터 저장에 실패하였습니다.");
				}
			}
		} catch (java.sql.SQLIntegrityConstraintViolationException ex) {
			// 이미 입력한 경우의 예외 처리
			String errorMessage = "이미 코멘트 입력하셨습니다.";
			JOptionPane.showMessageDialog(null, errorMessage, "경고", JOptionPane.WARNING_MESSAGE);
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public List<Condition> 선수목록_컨디션_콤보박스에서선수를선택하면해당선수의컨디션리스트에저장되는메소드(int backnumber) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conditionList = new ArrayList<>();

		try {
			conn = DBUtil.getConnection(); // 연결 객체를 conn 변수에 할당
			String sql = "SELECT * FROM `condition` WHERE number = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, backnumber);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int number = rs.getInt("number");
				String playerName = rs.getString("playername");
				String playercondition = rs.getString("playercondition");
				String when = rs.getString("when");

				conditionList.add(new Condition(number, playerName, playercondition, when));
				System.out.println(conditionList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원을 닫아주어야 합니다.
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return conditionList;
	}

	public void 선수목록_컨디션_컨디션리스트를바탕으로JTable에목록을띄우는메소드(List<Condition> conditionList) {
		DefaultTableModel model = (DefaultTableModel) conditionTable2.getModel();
		model.setRowCount(0);

		for (Condition condition : conditionList) {
			if (condition.getNumber() == 일정창_선수정보콤보박스에서선택한등번호) {
				Object[] rowData = { condition.getNumber(), condition.getPlayerName(), condition.getPlayercondition(),
						condition.getDate() };
				model.addRow(rowData);
			}
		}
	}

	public void 선수목록_컨디션_선수와날짜콤보박스를선택했을때해당하는선수의컨디션텍스트에나오게하는메소드(List<Condition> conditionList) {
		try {
			List<Condition> condition1 = new ArrayList<>();

			for (Condition condition : conditionList) {
				if (condition.getDate().startsWith(일정창_콤보박스에서선택한날짜)) {
					condition1.add(condition);
				}
			}

			// JTextArea에 condition1의 playerCondition 설정
			StringBuilder sb = new StringBuilder();
			for (Condition condition : condition1) {
				sb.append(condition.getPlayercondition()).append("\n");
			}
			선수목록_컨디션_선수컨디션텍스트박스.setText(sb.toString());

		} catch (NullPointerException e) {
			System.out.println("괜찮음 계속 하세요");
			;
		}
	}

	public void 선수목록_컨디션_코멘트입력하고저장버튼누르면데이터베이스로이동하는메소드(int backNumber, String comment) {
		String sql = "INSERT INTO comment (number, datetime, conditioncomment, who) VALUES (?, current_timestamp(), ?, '감독')";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, backNumber);
			stmt.setString(2, comment);

			int result = stmt.executeUpdate();
			if (result > 0) {
				System.out.println("데이터가 성공적으로 저장되었습니다.");
			} else {
				System.out.println("데이터 저장에 실패하였습니다.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public List<DoctorAppointment> 선수목록_의사소견_콤보박스에서선수를선택하면해당선수의의사소견리스트에저장되는메소드(int backnumber) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		doctorAppointmentList = new ArrayList<>();

		try {
			conn = DBUtil.getConnection(); // 연결 객체를 conn 변수에 할당
			String sql = "SELECT * FROM appointment WHERE number = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, backnumber);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int backnumber1 = rs.getInt("number");
				String playerName = rs.getString("name");
				String condition = rs.getString("condition");
				String doctor = rs.getString("doctor");
				String date = rs.getString("date");
				String time = rs.getString("time");

				doctorAppointmentList
						.add(new DoctorAppointment(backnumber1, playerName, condition, doctor, date, time));
				System.out.println(doctorAppointmentList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원을 닫아주어야 합니다.
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return doctorAppointmentList;
	}

	public void 선수목록_의사소견_의사소견리스트를바탕으로JTable에목록을띄우는메소드(List<DoctorAppointment> doctorAppointmentList) {
		DefaultTableModel model = (DefaultTableModel) doctorAppointmentTable.getModel();
		model.setRowCount(0);

		for (DoctorAppointment doctorAppointment : doctorAppointmentList) {
			if (doctorAppointment.getBacknumber() == 일정창_선수정보콤보박스에서선택한등번호) {
				Object[] rowData = { doctorAppointment.getBacknumber(), doctorAppointment.getPlayerName(),
						doctorAppointment.getDate(), doctorAppointment.getTime(), doctorAppointment.getCondition(),
						doctorAppointment.getDoctor(), doctorAppointment.getCoach() };
				model.addRow(rowData);
			}
		}
	}

//   public void 선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의컨디션텍스트에나오게하는메소드(List<DoctorAppointment> doctorAppointmentList) {
//      try {
//         List<DoctorAppointment> doctorAppointment1 = new ArrayList<>();
//
//         for (DoctorAppointment doctorAppointment : doctorAppointmentList) {
//            if (doctorAppointment.getDate().startsWith(일정창_콤보박스에서선택한날짜)) {
//               doctorAppointment1.add(doctorAppointment);
//            }
//         }
//
//         // JTextArea에 condition1의 playerCondition 설정
//         StringBuilder sb = new StringBuilder();
//         for (DoctorAppointment condition : doctorAppointment1) {
//            sb.append(condition.getCondition()).append("\n");
//         }
//         선수목록_의사소견_선수컨디션텍스트박스.setText(sb.toString());
//
//      } catch (NullPointerException e) {
//         System.out.println("괜찮음 계속 하세요");
//         ;
//      }
//   }

	public List<Comment> 선수목록_의사소견_콤보박스에서선수를선택하면해당선수의코멘트리스트에저장되는메소드(int backnumber) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		commentList = new ArrayList<>();

		try {
			conn = DBUtil.getConnection(); // 연결 객체를 conn 변수에 할당
			String sql = "SELECT * FROM comment where number = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, backnumber);
			rs = stmt.executeQuery();

			while (rs.next()) {
				int number1 = rs.getInt("number");
				String datetime = rs.getString("datetime");
				String dateschedulecomment = rs.getString("schedulecomment");
				String conditioncomment = rs.getString("conditioncomment");
				String doctorcomment = rs.getString("doctorcomment");
				String who = rs.getString("who");

				commentList
						.add(new Comment(number1, datetime, dateschedulecomment, conditioncomment, doctorcomment, who));
				System.out.println(commentList);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 사용한 자원을 닫아주어야 합니다.
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return commentList;
	}

	public void 선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의의사소견텍스트에나오게하는메소드(List<Comment> commentList, int backnumber) {
		try {
			컨디션의사소견_의사소견_의사소견텍스트박스.setText("");
			List<Comment> comment1 = new ArrayList<>();

			for (Comment comment : commentList) {
				if (comment.getWho().equals("의사") && comment.getNumber() == backnumber
						&& comment.getDatetime().startsWith(일정창_콤보박스에서선택한날짜)) {
					comment1.add(comment);
				}
			}

			StringBuilder sb = new StringBuilder();
			for (Comment comment : comment1) {
				sb.append(comment.getDoctorcomment()).append("\n");
			}
			컨디션의사소견_의사소견_의사소견텍스트박스.setText(sb.toString());

		} catch (NullPointerException e) {
			System.out.println("괜찮음 계속 하세요");
			// Handle the NullPointerException here if needed.
		}
	}

	public void 선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의감독코멘트텍스트에나오게하는메소드(List<Comment> commentList, int backnumber) {
		try {
			컨디션의사소견_감독코멘트텍스트박스.setText("");
			List<Comment> comment1 = new ArrayList<>();

			for (Comment comment : commentList) {
				if (comment.getWho().equals("감독") && comment.getNumber() == backnumber
						&& comment.getDatetime().startsWith(일정창_콤보박스에서선택한날짜)) {
					comment1.add(comment);
				}
			}

			StringBuilder sb = new StringBuilder();
			for (Comment comment : comment1) {
				sb.append(comment.getSchedulecomment()).append("\n");
			}
			컨디션의사소견_감독코멘트텍스트박스.setText(sb.toString());

		} catch (NullPointerException e) {
			System.out.println("괜찮음 계속 하세요");
			// Handle the NullPointerException here if needed.
		}
	}

	public void 선수목록_의사소견_코멘트입력하고저장버튼누르면데이터베이스로이동하는메소드(int backNumber, String comment) {
		String sql = "INSERT INTO comment (number, datetime, conditioncomment, who) VALUES (?, current_timestamp(), ?, '코치')";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, backNumber);
			stmt.setString(2, comment);

			int result = stmt.executeUpdate();
			if (result > 0) {
				System.out.println("데이터가 성공적으로 저장되었습니다.");
			} else {
				System.out.println("데이터 저장에 실패하였습니다.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void 선수일정추가() {
		String selectedDate = 날짜콤보박스.getSelectedItem().toString();
		String selectedPlayer = 선수정보콤보박스.getSelectedItem().toString();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(
					"insert into playerschedule(number, date, starttime, content, who) values (?, ?, ?, ?, ?)");
			stmt.setInt(1, Integer.valueOf(selectedPlayer.substring(0, selectedPlayer.indexOf(" "))));
			stmt.setString(2, selectedDate);
			stmt.setString(3, 시간텍스트필드.getText());
			stmt.setString(4, 일정텍스트필드.getText());
			stmt.setString(5, "코치");

			stmt.executeUpdate();

			시간텍스트필드.setText("");
			일정텍스트필드.setText("");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void 선수일정수정() {
		String selectedDate = 날짜콤보박스.getSelectedItem().toString();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("UPDATE playerschedule SET starttime = ?, content = ? WHERE `date` = ? AND starttime = ?;");
			stmt.setString(1, 시간텍스트필드.getText());
			stmt.setString(2, 일정텍스트필드.getText());
			stmt.setString(3, selectedDate);
			stmt.setString(4, 시간텍스트);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("메소드 실행 완료");
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void 선수일정삭제(String date, String starttime) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("DELETE FROM playerschedule WHERE  date = ? AND starttime = ?");
			stmt.setString(1, date);
			stmt.setString(2, starttime);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

//	private static List<Schedule> viewPlayerSchedule() {
//		String selectedDate = 날짜콤보박스.getSelectedItem().toString();
//		String selectedPlayer = 선수정보콤보박스.getSelectedItem().toString();
//		List<Schedule> list = new ArrayList<>();
//		Connection conn = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			conn = DBUtil.getConnection();
//			String sql = "SELECT starttime, endtime, content, confirm FROM playerschedule \r\n"
//					+ "WHERE number = ? AND date = ?;";
//			stmt = conn.prepareStatement(sql);
//			stmt.setInt(1, Integer.valueOf(selectedPlayer.substring(4, 8)));
//			stmt.setString(2, selectedDate);
//
//			rs = stmt.executeQuery();
//			while (rs.next()) {
//				String starttime = rs.getString("starttime");
//				String endtime = rs.getString("endtime");
//				String content = rs.getString("content");
//				String check = rs.getString("check");
//
//            Schedule schedule = new Schedule(starttime, endtime, content, check);
//				list.add(new Schedule(starttime, endtime, content, check));
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

	// 테이블 기록 가져오기
	private static void insertTabel(List<Schedule> list) {
		DefaultTableModel tableModel = (DefaultTableModel) scheduleTable.getModel();
		tableModel.setRowCount(0);

		for (Schedule schedule : list) {
			Object[] rowData = { schedule.getStartTime(), schedule.getEndTime(), schedule.getContent(),
					schedule.getCheck() };
			tableModel.addRow(rowData);
		}
	}

	public void 선수목록_컨디션_컨디션리스트와날짜를바탕으로JTable에목록을띄우는메소드(List<Condition> conditionList) {
	       try {
	      DefaultTableModel model = (DefaultTableModel) conditionTable2.getModel();
	      model.setRowCount(0);

	      for (Condition condition : conditionList) {
	         if (condition.getNumber() == 일정창_선수정보콤보박스에서선택한등번호) {
	            // Check if the condition's date matches the selectedDate
	            if (condition.getDate().startsWith(일정창_콤보박스에서선택한날짜)) {
	               Object[] rowData = { condition.getNumber(), condition.getPlayerName(),
	                     condition.getPlayercondition(), condition.getDate() };
	               model.addRow(rowData);
	            }
	         }
	      }
	   }catch (NullPointerException ex) {
	    }
	   }

	public void 선수목록_의사소견_선수와날짜콤보박스를선택했을때해당하는선수의컨디션텍스트에나오게하는메소드(List<DoctorAppointment> doctorAppointmentList) {
		try {
			List<DoctorAppointment> doctorAppointment1 = new ArrayList<>();

			for (DoctorAppointment doctorAppointment : doctorAppointmentList) {
				if (doctorAppointment.getDate().startsWith(일정창_콤보박스에서선택한날짜)) {
					doctorAppointment1.add(doctorAppointment);
				}
			}

			// JTextArea에 condition1의 playerCondition 설정
			StringBuilder sb = new StringBuilder();
			for (DoctorAppointment condition : doctorAppointment1) {
				sb.append(condition.getCondition()).append("\n");
			}
			컨디션의사소견_의사소견_의사소견텍스트박스.setText(sb.toString());

		} catch (NullPointerException e) {
			System.out.println("괜찮음 계속 하세요");
			;
		}
	}
}