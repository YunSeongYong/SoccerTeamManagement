import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import dbutil.DBUtil;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class DirectorGUI extends JFrame implements ChangeListener {
	JTabbedPane pane;
	JLabel lbl;
	private JFrame frame;
	private JTextField 이름텍스트필드;
	private JTextField 신장텍스트필드;
	private JTextField 몸무게텍스트필드;
	private JTextField 나이텍스트필드;
	private JTextField 포지션텍스트필드;
	private JTextField 담당코치텍스트필드;
	private JTextField 담당의사텍스트필드;
	private JTextField 아이디텍스트필드;
	private JTextField 비밀번호텍스트필드;
	private JTabbedPane tabbedPane_1;
	private JTabbedPane tabbedPane_2;
	private JButton 이미지등록버튼;
	private JButton 저장버튼;
	private JLabel 등번호라벨;
	private JTextField 등번호텍스트필드;
	private JPanel 이미지등록창;
	private File selectedFile;
	private Image selectedImage;
	private JLabel 이미지라벨;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JLabel lblNewLabel_7;
	private JLabel 사용가능유무라벨;
	private JPanel 일정창;
	private JPanel 컨디션창;
	private JPanel 의사소견창;
	private JComboBox 선수정보콤보박스;
	private int backnumber;
	private JPanel 개인정보창;
	private JLabel 이름수정라벨;
	private JLabel 신장수정라벨;
	private JLabel 몸무게수정라벨;
	private JLabel 나이수정라벨;
	private JLabel 포지션수정라벨;
	private JLabel 담당코치수정라벨;
	private JLabel 담당의사수정라벨;
	private JTextField 이름수정텍스트필드;
	private JTextField 신장수정텍스트필드;
	private JTextField 몸무게수정텍스트필드;
	private JTextField 나이수정텍스트필드;
	private JTextField 포지션수정텍스트필드;
	private JTextField 담당코치수정텍스트필드;
	private JButton 이미지수정버튼;
	private JButton 수정버튼;
	private JLabel 등번호수정라벨;
	private JTextField 등번호수정텍스트필드;
	private JPanel 이미지등록수정창;
	private JLabel 선수정보수정라벨;
	private JLabel lbl_1;
	private JLabel lblNewLabel_8;
	private List<Player> playerList;
	private List<Schedule> scheduleList;
	private List<Condition> conditionList;
	private JTextField 담당의사수정텍스트필드;
	private JTextField 일정창_코멘트텍스트필드;
	private JScrollPane scrolledTable;
	private JTable table;
	private JComboBox 날짜콤보박스;
	private String 일정창_콤보박스에서선택한날짜;
	private JButton 일정창_저장버튼;
	private int 일정창_선수정보콤보박스에서선택한등번호;
	private JScrollPane scrolledTable_1;
	private JTable conditionTable;
	private JTextArea 선수목록_컨디션_선수컨디션텍스트박스;
	private JLabel 선수컨디션라벨;
	private JScrollPane scrollPane_1;
	private JTextArea 선수목록_컨디션_코멘트텍스트박스;
	private JLabel 코멘트라벨;

	private static int countStaff(String role) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT count(*) AS A FROM staff WHERE role = ?;";
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, role);

			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("A");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return -1;
	}

	public void insertStaff() {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("insert into staff(number, name, age, role) values (?, ?, ?, ?)");
			String role = textField_2.getText();
			if (role.equals("코치")) {
				stmt.setInt(1, countStaff("코치") + 200);
			} else if (role.equals("의사")) {
				stmt.setInt(1, countStaff("의사") + 300);
			}
			stmt.setString(2, textField.getText());
			stmt.setInt(3, Integer.valueOf(textField_1.getText()));
			stmt.setString(4, textField_2.getText());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			insertIMG(stmt);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		System.out.println("스태프 등록");
	}

	public void insertStaffID() {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("insert into identity(no, id, password, role) values (?, ?, ?, ?)");
			String role = textField_2.getText();
			if (role.equals("코치")) {
				stmt.setInt(1, countStaff("코치") + 200);
			} else if (role.equals("의사")) {
				stmt.setInt(1, countStaff("의사") + 300);
			}
			stmt.setString(2, textField_4.getText());
			stmt.setString(3, textField_5.getText());
			stmt.setString(4, textField_2.getText());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		System.out.println("아이디 등록");
	}

	public void insertImg() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile(); // 선택한 파일 가져오기

			// 이미지 아이콘 설정
			ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
			selectedImage = imageIcon.getImage().getScaledInstance(187, 275, Image.SCALE_SMOOTH);

			// 기존에 생성한 JLabel이 있을 경우 수정, 없을 경우 새로 생성
			if (lblNewLabel == null) {
				lblNewLabel = new JLabel();
				lblNewLabel.setBounds(12, 38, 136, 187);
				panel.add(lblNewLabel);
			}

			// JLabel에 이미지 아이콘 설정
			lblNewLabel.setIcon(new ImageIcon(selectedImage));

			// JPanel 갱신
			panel.revalidate();
			panel.repaint();
		}
	}

	public void insertIMG(PreparedStatement stmt) {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			BufferedImage bufferedImage = new BufferedImage(selectedImage.getWidth(null), selectedImage.getHeight(null),
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = bufferedImage.createGraphics();
			graphics.drawImage(selectedImage, 0, 0, null);
			graphics.dispose();
			ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
			byte[] imageData = byteArrayOutputStream.toByteArray();

			stmt = conn.prepareStatement("UPDATE staff set image = ? where number = ?");
			stmt.setBytes(1, imageData);
			String role = textField_2.getText();
			if (role.equals("코치")) {
				stmt.setInt(2, countStaff("코치") + 200);
			} else if (role.equals("의사")) {
				stmt.setInt(2, countStaff("의사") + 300);
			}

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 중복확인 버튼 클릭 시 실행되는 메서드
	private void checkDuplicateID() {
		String id = textField_4.getText(); // 입력한 아이디 가져오기
		boolean isDuplicate = checkIfIDExists(id); // 데이터베이스에서 아이디 중복 여부 확인

		// 중복 여부에 따라 메시지 업데이트
		if (isDuplicate) {
			lblNewLabel_7.setText("아이디가 이미 존재합니다.");
			lblNewLabel_7.setForeground(Color.RED);
		} else {
			lblNewLabel_7.setText("사용 가능한 아이디입니다.");
			lblNewLabel_7.setForeground(Color.GREEN);
		}
	}

	// 데이터베이스에서 아이디 중복 여부 확인하는 메서드
	private boolean checkIfIDExists(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean isDuplicate = false;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT COUNT(*) FROM identity WHERE id = ?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					isDuplicate = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}

		return isDuplicate;
	}

	// 중복확인 버튼 클릭 시 실행되는 메서드
	private void 아이디중복확인() {
		String id = 아이디텍스트필드.getText(); // 입력한 아이디 가져오기
		boolean isDuplicate = checkIfIDExists(id); // 데이터베이스에서 아이디 중복 여부 확인

		// 중복 여부에 따라 메시지 업데이트
		if (isDuplicate) {
			사용가능유무라벨.setText("아이디가 이미 존재합니다.");
			사용가능유무라벨.setForeground(Color.RED);
		} else {
			사용가능유무라벨.setText("사용 가능한 아이디입니다.");
			사용가능유무라벨.setForeground(Color.GREEN);
		}
	}

	// 데이터베이스에서 아이디 중복 여부 확인하는 메서드
	private boolean 아이디유무(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean isDuplicate = false;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement("SELECT COUNT(*) FROM identity WHERE id = ?");
			stmt.setString(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
					isDuplicate = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}

		return isDuplicate;
	}

	private static int 선수인원확인메소드() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT count(*) AS A FROM players";

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);

			rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt("A");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return -1;
	}

	public void 선수등록메소드() {
		String sql = "INSERT INTO players (backnumber, name, height, weight, age, position, coach, doctor, no) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, Integer.valueOf(등번호텍스트필드.getText()));
			stmt.setString(2, 이름텍스트필드.getText());
			stmt.setDouble(3, Double.valueOf(신장텍스트필드.getText()));
			stmt.setDouble(4, Double.valueOf(몸무게텍스트필드.getText()));
			stmt.setInt(5, Integer.valueOf(나이텍스트필드.getText()));
			stmt.setString(6, 포지션텍스트필드.getText());
			stmt.setString(7, 담당코치텍스트필드.getText());
			stmt.setString(8, 담당의사텍스트필드.getText());
			stmt.setInt(9, 선수인원확인메소드() + 1);

			int result = stmt.executeUpdate();
			if (result > 0) {
				System.out.println("데이터가 성공적으로 저장되었습니다.");
			} else {
				System.out.println("데이터 저장에 실패하였습니다.");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			이미지를데이터베이스에등록하는메소드(stmt);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void identity등록메소드() {
		String sql = "INSERT INTO identity (no, id, password, role) VALUES (?, ?, ?, '선수')";
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = DBUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 선수인원확인메소드() + 1);
			stmt.setString(2, 아이디텍스트필드.getText());
			stmt.setString(3, 비밀번호텍스트필드.getText());

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

	public void 이미지를화면에등록하는메소드() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile(); // 선택한 파일 가져오기

			// 이미지 아이콘 설정
			ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
			selectedImage = imageIcon.getImage().getScaledInstance(187, 275, Image.SCALE_SMOOTH);

			// 기존에 생성한 JLabel이 있을 경우 수정, 없을 경우 새로 생성
			if (이미지라벨 == null) {
				이미지라벨 = new JLabel();
				이미지라벨.setBounds(12, 38, 136, 187);
				이미지등록창.add(이미지라벨);
			}

			// JLabel에 이미지 아이콘 설정
			이미지라벨.setIcon(new ImageIcon(selectedImage));

			// JPanel 갱신
			이미지등록창.revalidate();
			이미지등록창.repaint();
		}
	}

	public void 이미지를데이터베이스에등록하는메소드(PreparedStatement stmt) {
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
			stmt.setInt(2, Integer.valueOf(등번호텍스트필드.getText()));
			stmt.executeUpdate();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void 이미지를데이터베이스에수정하는메소드(PreparedStatement stmt) {
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

	public void 선수정보콤보박스목록만드는메소드() {
		if (선수정보콤보박스 == null) {
			선수정보콤보박스 = new JComboBox<>();
		} else {
			선수정보콤보박스.removeAllItems();
		}

		// 콤보박스 초기화 코드 추가

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT backnumber, name FROM players";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {
						int backnumber = rs.getInt("backnumber");
						String name = rs.getString("name");
						String item = backnumber + " - " + name;
						선수정보콤보박스.addItem(item);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//	public void 선수정보콤보박스선택후출력메소드(int backnumber) {
	//	    Connection conn = null;
	//	    PreparedStatement stmt = null;
	//	    ResultSet rs = null;
	//	    try {
	//	        conn = DBUtil.getConnection(); // 연결 객체를 conn 변수에 할당
	//	        String sql = "SELECT backnumber, name FROM players WHERE backnumber = ?";
	//	        stmt = conn.prepareStatement(sql);
	//	        stmt.setInt(1, backnumber);
	//	        rs = stmt.executeQuery();
	//	        
	//	        while (rs.next()) {
	//	            int backnumber1 = rs.getInt("backnumber");
	//	            String name = rs.getString("name");
	//	            
	//	            System.out.println(backnumber1);
	//	            System.out.println(name);
	//	        }
	//	        
	//	    } catch (SQLException e) {
	//	        e.printStackTrace();
	//	    } finally {
	//	        // 사용한 자원을 닫아주어야 합니다.
	//	        DBUtil.close(rs);
	//	        DBUtil.close(stmt);
	//	        DBUtil.close(conn);
	//	    }
	//	}

	public List<Player> 선수정보콤보박스의등번호로선수정보의모든정보를리스트에저장하는메소드(int backnumber) {
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
				int age = rs.getInt("age");
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

	public void 콤보박스에서선택한등번호로선수정보의모든텍스트필드에추가하는메소드(List<Player> playerList) {
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

	public void 선수수정메소드(List<Player> playerList) {
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
			이미지를데이터베이스에수정하는메소드(stmt);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void 이미지를화면에수정하는메소드() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile(); // 선택한 파일 가져오기

			// 이미지 아이콘 설정
			ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
			selectedImage = imageIcon.getImage().getScaledInstance(187, 275, Image.SCALE_SMOOTH);

			// 이미지 라벨이 이미 추가되어 있는지 확인하고, 있다면 제거
			if (이미지라벨 != null) {
				이미지등록수정창.remove(이미지라벨);
			}

			// 새로운 이미지 라벨 생성
			이미지라벨 = new JLabel(new ImageIcon(selectedImage));
			이미지라벨.setBounds(12, 38, 136, 187);

			// 이미지 라벨을 패널에 추가
			이미지등록수정창.add(이미지라벨);

			// 패널 갱신
			이미지등록수정창.revalidate();
			이미지등록수정창.repaint();

			selectedImage = selectedImage;
		}
	}

	public void 선수삭제메소드(List<Player> playerList) {
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
			선수정보의모든텍스트필드값제거메소드();
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
	}

	public void 선수정보의모든텍스트필드값제거메소드() {
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

	public List<Schedule> 선수정보콤보박스의등번호로선수일정의모든정보를리스트에저장하는메소드(int backnumber) {
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
				String where = rs.getString("where");
				String who = rs.getString("who");

				scheduleList.add(new Schedule(number1, date, startTime, endTime, content, where, who));
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

	public void 콤보박스에서선택한등번호로일정창의테이블에추가하는메소드(List<Schedule> scheduleList) {
		List<Schedule> filteredList = new ArrayList<>();

		for (Schedule schedule : scheduleList) {
			if (schedule.getDate().equals(일정창_콤보박스에서선택한날짜)) {
				filteredList.add(schedule);
			}
		}

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		// 기존의 테이블 데이터 초기화
		tableModel.setRowCount(0);

		// filteredList의 데이터를 테이블 모델에 추가
		for (Schedule schedule : filteredList) {
			Object[] rowData = { schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(),
					schedule.getContent(), schedule.getWhere(), false // 체크박스 데이터 추가
			};
			tableModel.addRow(rowData);
		}

		// 체크박스 컬럼 추가
		table.getColumnModel().getColumn(tableModel.getColumnCount() - 1)
		.setCellRenderer(table.getDefaultRenderer(Boolean.class));
		table.getColumnModel().getColumn(tableModel.getColumnCount() - 1)
		.setCellEditor(table.getDefaultEditor(Boolean.class));

		tableModel.addTableModelListener(e -> {
			if (e.getColumn() == 5) {
				int rowIndex = e.getFirstRow();
				boolean isChecked = (boolean) tableModel.getValueAt(rowIndex, 5);
				if (isChecked) {
					체크박스선택시각행의값콘솔출력메소드(rowIndex);
				}
			}
		});
	}

	public void 날짜와등번호콤보박스선택시일정창의선수일정표시하는메소드(List<Schedule> scheduleList) {
		try {
			List<Schedule> filteredList = new ArrayList<>();

			for (Schedule schedule : scheduleList) {
				if (schedule.getDate().equals(일정창_콤보박스에서선택한날짜)) {
					filteredList.add(schedule);
				}
			}

			DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			// 기존의 테이블 데이터 초기화
			tableModel.setRowCount(0);

			// filteredList의 데이터를 테이블 모델에 추가
			for (Schedule schedule : filteredList) {
				Object[] rowData = { schedule.getDate(), schedule.getStartTime(), schedule.getEndTime(),
						schedule.getContent(), schedule.getWhere(), false // 체크박스 데이터 추가
				};
				tableModel.addRow(rowData);
			}

			// 체크박스 컬럼 추가
			table.getColumnModel().getColumn(tableModel.getColumnCount() - 1)
			.setCellRenderer(table.getDefaultRenderer(Boolean.class));
			table.getColumnModel().getColumn(tableModel.getColumnCount() - 1)
			.setCellEditor(table.getDefaultEditor(Boolean.class));

		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "선수를 먼저 선택하세요", "경고", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void 체크박스선택시각행의값콘솔출력메소드(int rowIndex) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		int columnCount = tableModel.getColumnCount();

		for (int j = 0; j < columnCount - 1; j++) {
			Object value = tableModel.getValueAt(rowIndex, j);
			System.out.print(value + " ");
		}
		System.out.println();
	}

	public void 코멘트입력하고저장버튼누르면데이터베이스로이동하는메소드(int backNumber, String comment) {
		String sql = "INSERT INTO comment (number, datetime, schedulecomment, who) VALUES (?, current_timestamp(), ?, '감독')";
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

	public List<Condition> 선수목록_컨디션_콤보박스에서선수를선택하면해당선수의컨디션리스트에저장되는메소드(int backnumber) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    conditionList = new ArrayList<>();

	    try {
	        conn = DBUtil.getConnection(); // 연결 객체를 conn 변수에 할당
	        String sql = "SELECT * FROM soccerteammanagement.condition WHERE number = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, backnumber);
	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            int number = rs.getInt("number");
	            String playerName = rs.getString("playername");
	            String playercondition = rs.getString("playercondition");
	            java.sql.Date when = rs.getDate("when");

	            conditionList.add(new Condition(number, playerName, playercondition, when.toString()));
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
	    DefaultTableModel model = (DefaultTableModel) conditionTable.getModel();
	    
	    for (Condition condition : conditionList) {
	        if (condition.getNumber() == 일정창_선수정보콤보박스에서선택한등번호) {
	            Object[] rowData = {
	                condition.getNumber(),
	                condition.getPlayerName(),
	                condition.getPlayercondition(),
	                condition.getDate()
	            };
	            model.addRow(rowData);
	        }
	    }
	}
	
	public void 선수목록_컨디션_선수와날짜콤보박스를선택했을때해당하는선수의컨디션텍스트에나오게하는메소드(List<Condition> conditionList) {
		List<Condition> condition1 = new ArrayList<>();

		for (Condition condition : conditionList) {
			if (condition.getDate().equals(일정창_콤보박스에서선택한날짜)) {
				condition1.add(condition);
			}
		}
		System.out.println(condition1);
	}


	// ======================================================================

	public DirectorGUI() {

		JPanel one, two, three;
		pane = new JTabbedPane();
		pane.setBounds(0, 101, 984, 460);
		lbl = new JLabel("              ");
		lbl.setBounds(0, 146, 284, 15);

		one = new JPanel();
		one.setBackground(Color.WHITE);
		pane.addTab("선수등록", one);
		one.setLayout(null);

		JLabel 이름라벨 = new JLabel("이름");
		이름라벨.setBounds(618, 106, 52, 15);
		one.add(이름라벨);

		JLabel 신장라벨 = new JLabel("신장");
		신장라벨.setBounds(618, 163, 62, 15);
		one.add(신장라벨);

		JLabel 몸무게라벨 = new JLabel("몸무게");
		몸무게라벨.setBounds(618, 206, 64, 15);
		one.add(몸무게라벨);

		JLabel 나이라벨 = new JLabel("나이");
		나이라벨.setBounds(618, 247, 62, 15);
		one.add(나이라벨);

		JLabel 포지션라벨 = new JLabel("포지션");
		포지션라벨.setBounds(618, 298, 81, 15);
		one.add(포지션라벨);

		JLabel 담당코치라벨 = new JLabel("담당 코치");
		담당코치라벨.setBounds(618, 351, 81, 15);
		one.add(담당코치라벨);

		JLabel 담당의사라벨 = new JLabel("담당 의사");
		담당의사라벨.setBounds(618, 393, 90, 15);
		one.add(담당의사라벨);

		JLabel 아이디라벨 = new JLabel("아이디");
		아이디라벨.setBounds(230, 167, 60, 15);
		one.add(아이디라벨);

		JLabel 비밀번호라벨 = new JLabel("비밀번호");
		비밀번호라벨.setBounds(230, 232, 64, 15);
		one.add(비밀번호라벨);

		이름텍스트필드 = new JTextField();
		이름텍스트필드.setBounds(699, 103, 116, 21);
		one.add(이름텍스트필드);
		이름텍스트필드.setColumns(10);

		신장텍스트필드 = new JTextField();
		신장텍스트필드.setBounds(699, 160, 116, 21);
		one.add(신장텍스트필드);
		신장텍스트필드.setColumns(10);

		몸무게텍스트필드 = new JTextField();
		몸무게텍스트필드.setBounds(699, 203, 116, 21);
		one.add(몸무게텍스트필드);
		몸무게텍스트필드.setColumns(10);

		나이텍스트필드 = new JTextField();
		나이텍스트필드.setBounds(699, 244, 116, 21);
		one.add(나이텍스트필드);
		나이텍스트필드.setColumns(10);

		포지션텍스트필드 = new JTextField();
		포지션텍스트필드.setBounds(699, 295, 116, 21);
		one.add(포지션텍스트필드);
		포지션텍스트필드.setColumns(10);

		담당코치텍스트필드 = new JTextField();
		담당코치텍스트필드.setBounds(699, 348, 116, 21);
		one.add(담당코치텍스트필드);
		담당코치텍스트필드.setColumns(10);

		담당의사텍스트필드 = new JTextField();
		담당의사텍스트필드.setBounds(699, 390, 116, 21);
		one.add(담당의사텍스트필드);
		담당의사텍스트필드.setColumns(10);

		아이디텍스트필드 = new JTextField();
		아이디텍스트필드.setBounds(302, 164, 116, 21);
		one.add(아이디텍스트필드);
		아이디텍스트필드.setColumns(10);

		비밀번호텍스트필드 = new JTextField();
		비밀번호텍스트필드.setBounds(302, 229, 116, 21);
		one.add(비밀번호텍스트필드);
		비밀번호텍스트필드.setColumns(10);

		이미지등록버튼 = new JButton("이미지등록");
		이미지등록버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				이미지를화면에등록하는메소드();
			}
		});
		이미지등록버튼.setBounds(64, 347, 126, 23);
		one.add(이미지등록버튼);

		저장버튼 = new JButton("저장");
		저장버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				identity등록메소드();
				선수등록메소드();
			}
		});

		저장버튼.setBounds(871, 368, 81, 39);
		one.add(저장버튼);

		등번호라벨 = new JLabel("등번호");
		등번호라벨.setBounds(618, 56, 64, 15);
		one.add(등번호라벨);

		등번호텍스트필드 = new JTextField();
		등번호텍스트필드.setBounds(699, 53, 116, 21);
		one.add(등번호텍스트필드);
		등번호텍스트필드.setColumns(10);

		이미지등록창 = new JPanel();
		이미지등록창.setBounds(26, 106, 192, 211);
		one.add(이미지등록창);

		JLabel 선수등록라벨 = new JLabel("선수등록");
		선수등록라벨.setBounds(713, 10, 116, 15);
		one.add(선수등록라벨);

		JLabel identity등록라벨 = new JLabel("identity등록");
		identity등록라벨.setBounds(333, 56, 116, 15);
		one.add(identity등록라벨);

		JButton 중복확인버튼 = new JButton("중복확인");
		중복확인버튼.setBounds(451, 163, 97, 23);
		one.add(중복확인버튼);
		중복확인버튼.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				아이디중복확인();
			}
		});

		사용가능유무라벨 = new JLabel("중복확인 버튼을 누르세요");
		사용가능유무라벨.setBounds(312, 195, 178, 15);
		one.add(사용가능유무라벨);

		// ==================================================================

		two = new JPanel();
		two.setForeground(Color.WHITE);
		two.add(new JLabel("두번째 탭입니다"));
		two.add(new JTextField("문자를 입력하세요"));

		pane.addTab("스태프 등록", two);

		two.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("이름");
		lblNewLabel_1.setBounds(193, 54, 57, 15);
		two.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("나이");
		lblNewLabel_2.setBounds(193, 106, 57, 15);
		two.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("직책");
		lblNewLabel_3.setBounds(193, 158, 57, 15);
		two.add(lblNewLabel_3);

		textField = new JTextField();
		textField.setBounds(239, 51, 116, 21);
		two.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(239, 103, 116, 21);
		two.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(239, 155, 116, 21);
		two.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("ID");
		lblNewLabel_5.setBounds(414, 58, 57, 15);
		two.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("PW");
		lblNewLabel_6.setBounds(414, 110, 57, 15);
		two.add(lblNewLabel_6);

		//		textField_3 = new JTextField();
		//		textField_3.setBounds(515, 51, 116, 21);
		//		two.add(textField_3);
		//		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(510, 55, 116, 21);
		two.add(textField_4);
		textField_4.setColumns(10);

		textField_5 = new JTextField();
		textField_5.setBounds(510, 107, 116, 21);
		two.add(textField_5);
		textField_5.setColumns(10);

		JButton btnNewButton = new JButton("등록");
		btnNewButton.setBounds(529, 191, 97, 23);
		two.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertStaffID();
				insertStaff();

			}
		});
		JButton btnNewButton_1 = new JButton("이미지");
		btnNewButton_1.setBounds(32, 239, 97, 23);
		two.add(btnNewButton_1);

		panel = new JPanel();
		panel.setBounds(12, 38, 136, 187);
		two.add(panel);

		JButton btnNewButton_2 = new JButton("중복확인");
		btnNewButton_2.setBounds(652, 54, 97, 23);
		two.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkDuplicateID();
			}
		});

		lblNewLabel_7 = new JLabel("중복여부");
		lblNewLabel_7.setBounds(510, 82, 239, 15);
		two.add(lblNewLabel_7);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertImg();

			}
		});

		// ====================================================================

		three = new JPanel();
		three.setBackground(Color.WHITE);
		pane.addTab("Three", three);
		three.setLayout(null);

		JButton 개인정보버튼 = new JButton("개인정보");
		개인정보버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				개인정보창.setVisible(true);
				일정창.setVisible(false);
				컨디션창.setVisible(false);
				의사소견창.setVisible(false);
			}
		});
		개인정보버튼.setBounds(32, 91, 120, 46);
		three.add(개인정보버튼);

		JButton 일정버튼 = new JButton("일정");
		일정버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				개인정보창.setVisible(false);
				일정창.setVisible(true);
				컨디션창.setVisible(false);
				의사소견창.setVisible(false);
			}
		});
		일정버튼.setBounds(32, 166, 120, 46);
		three.add(일정버튼);

		JButton 컨디션버튼 = new JButton("컨디션");
		컨디션버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				개인정보창.setVisible(false);
				일정창.setVisible(false);
				컨디션창.setVisible(true);
				의사소견창.setVisible(false);
			}
		});
		컨디션버튼.setBounds(32, 243, 120, 46);
		three.add(컨디션버튼);

		JButton 의사소견버튼 = new JButton("의사소견");
		의사소견버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				개인정보창.setVisible(false);
				일정창.setVisible(false);
				컨디션창.setVisible(false);
				의사소견창.setVisible(true);
			}
		});
		의사소견버튼.setBounds(32, 324, 117, 46);
		three.add(의사소견버튼);

		날짜콤보박스 = new JComboBox();
		날짜콤보박스.setBounds(424, 26, 127, 21);
		three.add(날짜콤보박스);

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

				System.out.println("콤보박스에서 선택한 날짜 출력 : " + 일정창_콤보박스에서선택한날짜);
				날짜와등번호콤보박스선택시일정창의선수일정표시하는메소드(scheduleList);
				선수목록_컨디션_선수와날짜콤보박스를선택했을때해당하는선수의컨디션텍스트에나오게하는메소드(conditionList);
			}
		});

		선수정보콤보박스 = new JComboBox();
		선수정보콤보박스.setBounds(756, 26, 117, 21);
		three.add(선수정보콤보박스);
		선수정보콤보박스목록만드는메소드();
		선수정보콤보박스.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) 선수정보콤보박스.getSelectedItem();
				일정창_선수정보콤보박스에서선택한등번호 = Integer.parseInt(selectedItem.split(" - ")[0]);
				System.out.println(일정창_선수정보콤보박스에서선택한등번호);
				선수정보콤보박스의등번호로선수정보의모든정보를리스트에저장하는메소드(일정창_선수정보콤보박스에서선택한등번호);
				콤보박스에서선택한등번호로선수정보의모든텍스트필드에추가하는메소드(playerList);
				선수정보콤보박스의등번호로선수일정의모든정보를리스트에저장하는메소드(일정창_선수정보콤보박스에서선택한등번호);
				콤보박스에서선택한등번호로일정창의테이블에추가하는메소드(scheduleList);
				날짜와등번호콤보박스선택시일정창의선수일정표시하는메소드(scheduleList);
				선수목록_컨디션_콤보박스에서선수를선택하면해당선수의컨디션리스트에저장되는메소드(일정창_선수정보콤보박스에서선택한등번호);
				선수목록_컨디션_컨디션리스트를바탕으로JTable에목록을띄우는메소드(conditionList);

			}
		});

		JLabel 날짜라벨 = new JLabel("날짜");
		날짜라벨.setBounds(372, 29, 44, 15);
		three.add(날짜라벨);

		JLabel 선수정보라벨 = new JLabel("선수정보");
		선수정보라벨.setBounds(678, 29, 66, 15);
		three.add(선수정보라벨);

		컨디션창 = new JPanel();
		컨디션창.setBounds(192, 57, 787, 376);
		three.add(컨디션창);
		컨디션창.setLayout(null);
		
		scrolledTable_1 = new JScrollPane((Component) null);
		scrolledTable_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrolledTable_1.setBounds(23, 28, 694, 110);
		컨디션창.add(scrolledTable_1);
		
		conditionTable = new JTable(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"number", "playername", "playercondition", "when"
			}
		));
		scrolledTable_1.setViewportView(conditionTable);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(146, 177, 195, 138);
		컨디션창.add(scrollPane);
		
		선수목록_컨디션_선수컨디션텍스트박스 = new JTextArea();
		scrollPane.setViewportView(선수목록_컨디션_선수컨디션텍스트박스);
		
		선수컨디션라벨 = new JLabel("선수 컨디션");
		선수컨디션라벨.setHorizontalAlignment(SwingConstants.RIGHT);
		선수컨디션라벨.setBounds(37, 240, 96, 15);
		컨디션창.add(선수컨디션라벨);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(489, 177, 195, 138);
		컨디션창.add(scrollPane_1);
		
		선수목록_컨디션_코멘트텍스트박스 = new JTextArea();
		scrollPane_1.setViewportView(선수목록_컨디션_코멘트텍스트박스);
		
		코멘트라벨 = new JLabel("코멘트");
		코멘트라벨.setHorizontalAlignment(SwingConstants.RIGHT);
		코멘트라벨.setBounds(381, 240, 96, 15);
		컨디션창.add(코멘트라벨);
		
		JButton 선수목록_컨디션_저장버튼 = new JButton("저장");
		선수목록_컨디션_저장버튼.setBounds(587, 325, 130, 41);
		컨디션창.add(선수목록_컨디션_저장버튼);

		일정창 = new JPanel();
		일정창.setBounds(192, 57, 787, 376);
		three.add(일정창);
		일정창.setLayout(null);

		JCheckBox 일정창_거부체크박스 = new JCheckBox("거절");
		일정창_거부체크박스.setBounds(662, 54, 115, 23);
		일정창.add(일정창_거부체크박스);

		일정창_코멘트텍스트필드 = new JTextField();
		일정창_코멘트텍스트필드.setBounds(89, 289, 513, 77);
		일정창.add(일정창_코멘트텍스트필드);
		일정창_코멘트텍스트필드.setColumns(10);

		일정창_저장버튼 = new JButton("저장");
		일정창_저장버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String comment = 일정창_코멘트텍스트필드.getText();
				코멘트입력하고저장버튼누르면데이터베이스로이동하는메소드(일정창_선수정보콤보박스에서선택한등번호, comment);
			}
		});
		일정창_저장버튼.setBounds(626, 293, 135, 73);
		일정창.add(일정창_저장버튼);

		scrolledTable = new JScrollPane((Component) null);
		scrolledTable.setBounds(23, 21, 617, 263);
		일정창.add(scrolledTable);
		scrolledTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		table = new JTable(new DefaultTableModel(new Object[][] {},
				new String[] { "date", "starttime", "endtime", "content", "where", "checkbox" }));
		scrolledTable.setViewportView(table);
		일정창.setVisible(false);

		의사소견창 = new JPanel();
		의사소견창.setBounds(92, 10, 10, 10);
		three.add(의사소견창);

		개인정보창 = new JPanel();
		개인정보창.setBounds(192, 57, 787, 376);
		three.add(개인정보창);
		개인정보창.setLayout(null);
		개인정보창.setBackground(Color.WHITE);

		이름수정라벨 = new JLabel("이름");
		이름수정라벨.setBounds(544, 55, 52, 15);
		개인정보창.add(이름수정라벨);

		신장수정라벨 = new JLabel("신장");
		신장수정라벨.setBounds(267, 106, 62, 15);
		개인정보창.add(신장수정라벨);

		몸무게수정라벨 = new JLabel("몸무게");
		몸무게수정라벨.setBounds(532, 106, 64, 15);
		개인정보창.add(몸무게수정라벨);

		나이수정라벨 = new JLabel("나이");
		나이수정라벨.setBounds(267, 166, 62, 15);
		개인정보창.add(나이수정라벨);

		포지션수정라벨 = new JLabel("포지션");
		포지션수정라벨.setBounds(532, 166, 81, 15);
		개인정보창.add(포지션수정라벨);

		담당코치수정라벨 = new JLabel("담당 코치");
		담당코치수정라벨.setBounds(267, 218, 81, 15);
		개인정보창.add(담당코치수정라벨);

		담당의사수정라벨 = new JLabel("담당 의사");
		담당의사수정라벨.setBounds(523, 218, 90, 15);
		개인정보창.add(담당의사수정라벨);

		이름수정텍스트필드 = new JTextField();
		이름수정텍스트필드.setColumns(10);
		이름수정텍스트필드.setBounds(623, 52, 116, 21);
		개인정보창.add(이름수정텍스트필드);

		신장수정텍스트필드 = new JTextField();
		신장수정텍스트필드.setColumns(10);
		신장수정텍스트필드.setBounds(341, 103, 116, 21);
		개인정보창.add(신장수정텍스트필드);

		몸무게수정텍스트필드 = new JTextField();
		몸무게수정텍스트필드.setColumns(10);
		몸무게수정텍스트필드.setBounds(623, 103, 116, 21);
		개인정보창.add(몸무게수정텍스트필드);

		나이수정텍스트필드 = new JTextField();
		나이수정텍스트필드.setColumns(10);
		나이수정텍스트필드.setBounds(341, 163, 116, 21);
		개인정보창.add(나이수정텍스트필드);

		포지션수정텍스트필드 = new JTextField();
		포지션수정텍스트필드.setColumns(10);
		포지션수정텍스트필드.setBounds(625, 163, 116, 21);
		개인정보창.add(포지션수정텍스트필드);

		담당코치수정텍스트필드 = new JTextField();
		담당코치수정텍스트필드.setColumns(10);
		담당코치수정텍스트필드.setBounds(341, 215, 116, 21);
		개인정보창.add(담당코치수정텍스트필드);

		담당의사수정텍스트필드 = new JTextField();
		담당의사수정텍스트필드.setColumns(10);
		담당의사수정텍스트필드.setBounds(623, 215, 116, 21);
		개인정보창.add(담당의사수정텍스트필드);

		이미지수정버튼 = new JButton("이미지수정");
		이미지수정버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				이미지를화면에수정하는메소드();
			}
		});
		이미지수정버튼.setBounds(48, 307, 126, 23);
		개인정보창.add(이미지수정버튼);

		수정버튼 = new JButton("수정버튼");
		수정버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				선수수정메소드(playerList);

			}
		});
		수정버튼.setBounds(585, 307, 90, 29);
		개인정보창.add(수정버튼);

		등번호수정라벨 = new JLabel("등번호");
		등번호수정라벨.setBounds(267, 55, 64, 15);
		개인정보창.add(등번호수정라벨);

		등번호수정텍스트필드 = new JTextField();
		등번호수정텍스트필드.setColumns(10);
		등번호수정텍스트필드.setBounds(341, 52, 116, 21);
		개인정보창.add(등번호수정텍스트필드);

		이미지등록수정창 = new JPanel();
		이미지등록수정창.setBounds(26, 55, 164, 207);
		개인정보창.add(이미지등록수정창);

		선수정보수정라벨 = new JLabel("선수정보수정");
		선수정보수정라벨.setBounds(377, 10, 116, 15);
		개인정보창.add(선수정보수정라벨);

		JButton 선수삭제버튼 = new JButton("선수삭제");
		선수삭제버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int choice = JOptionPane.showConfirmDialog(frame, "정말 삭제하시겠습니까?", "경고", JOptionPane.YES_NO_OPTION);

				if (choice == JOptionPane.YES_OPTION) {
					선수삭제메소드(playerList);
					System.out.println("삭제되었습니다.");
				} else {
					System.out.println("삭제가 취소되었습니다.");
				}
			}
		});
		선수삭제버튼.setBounds(367, 301, 90, 34);
		개인정보창.add(선수삭제버튼);

		lbl_1 = new JLabel("              ");
		lbl_1.setBounds(192, 146, 590, 212);
		three.add(lbl_1);
		개인정보창.setVisible(false);
		컨디션창.setVisible(false);
		의사소견창.setVisible(false);

		// ====================================================================

		pane.setSelectedIndex(0);
		pane.addChangeListener(this);
		getContentPane().setLayout(null);
		this.getContentPane().add(pane);
		this.getContentPane().add(lbl);

		lblNewLabel_8 = new JLabel("New label");
		lblNewLabel_8.setIcon(new ImageIcon(DirectorGUI.class.getResource("/image/선수위-배경.jpg")));
		lblNewLabel_8.setBounds(0, 0, 984, 104);
		getContentPane().add(lblNewLabel_8);

		this.setSize(1000, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}// end

	@Override
	public void stateChanged(ChangeEvent e) {
		int index = pane.getSelectedIndex();// 현재탭의 번호를 가져온다
		String msg = pane.getTitleAt(index); // index 위에 탭 문자열을 가져옴
		msg += "탭이 선택되었습니다";
		lbl.setText(msg);
		pane.setSelectedIndex(index); // 현재 선택한 탭으로 화면 출력 변경
	}

	public static void main(String[] args) {

		new DirectorGUI();
	}
}
