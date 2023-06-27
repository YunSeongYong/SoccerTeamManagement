import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import dbutil.DBUtil;




public class PlayerRegistration extends JFrame implements ChangeListener{

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
	private JLabel no라벨2;
	private JTextField no2텍스트필드;
	private JTextField no1텍스트필드;
	private JTextField 역할텍스트필드;
	private JPanel 이미지등록창;
	private File selectedFile;
	private Image selectedImage;
	
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
	        stmt.setString(9, no2텍스트필드.getText());

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
		String sql = "INSERT INTO identity (no, id, password, role) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.valueOf(no1텍스트필드.getText()));
            stmt.setString(2, 아이디텍스트필드.getText());
            stmt.setString(3, 비밀번호텍스트필드.getText());
            stmt.setString(4, 역할텍스트필드.getText());
            
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
	        selectedFile = fileChooser.getSelectedFile(); // 필드에 할당

	        // 이미지 아이콘 설정
	        ImageIcon imageIcon = new ImageIcon(selectedFile.getAbsolutePath());
	        selectedImage = imageIcon.getImage().getScaledInstance(187, 275, Image.SCALE_SMOOTH);

	        // JLabel 생성 및 이미지 아이콘 설정
	        JLabel 이미지라벨 = new JLabel();
	        이미지라벨.setIcon(new ImageIcon(selectedImage));

	        // JLabel을 JPanel에 추가
	        이미지등록창.add(이미지라벨);

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
	        BufferedImage bufferedImage = new BufferedImage(selectedImage.getWidth(null), selectedImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
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

	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerRegistration window = new PlayerRegistration();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public PlayerRegistration() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		이미지등록창 = new JPanel();
		이미지등록창.setBounds(36, 94, 189, 227);
		frame.getContentPane().add(이미지등록창);
		
		JLabel 이름라벨 = new JLabel("이름");
		이름라벨.setBounds(696, 99, 58, 15);
		frame.getContentPane().add(이름라벨);
		
		JLabel 신장라벨 = new JLabel("신장");
		신장라벨.setBounds(696, 145, 58, 15);
		frame.getContentPane().add(신장라벨);
		
		JLabel 몸무게라벨 = new JLabel("몸무게");
		몸무게라벨.setBounds(696, 202, 58, 15);
		frame.getContentPane().add(몸무게라벨);
		
		JLabel 나이라벨 = new JLabel("나이");
		나이라벨.setBounds(696, 261, 58, 15);
		frame.getContentPane().add(나이라벨);
		
		JLabel 포지션라벨 = new JLabel("포지션");
		포지션라벨.setBounds(696, 314, 58, 15);
		frame.getContentPane().add(포지션라벨);
		
		JLabel 담당코치라벨 = new JLabel("담당 코치");
		담당코치라벨.setBounds(696, 352, 58, 15);
		frame.getContentPane().add(담당코치라벨);
		
		JLabel 담당의사라벨 = new JLabel("담당 의사");
		담당의사라벨.setBounds(696, 413, 58, 15);
		frame.getContentPane().add(담당의사라벨);
		
		JLabel 아이디라벨 = new JLabel("아이디");
		아이디라벨.setBounds(291, 188, 57, 15);
		frame.getContentPane().add(아이디라벨);
		
		JLabel 비밀번호라벨 = new JLabel("비밀번호");
		비밀번호라벨.setBounds(291, 235, 57, 15);
		frame.getContentPane().add(비밀번호라벨);
		
		이름텍스트필드 = new JTextField();
		이름텍스트필드.setBounds(790, 96, 116, 21);
		frame.getContentPane().add(이름텍스트필드);
		이름텍스트필드.setColumns(10);
		
		신장텍스트필드 = new JTextField();
		신장텍스트필드.setBounds(790, 142, 116, 21);
		frame.getContentPane().add(신장텍스트필드);
		신장텍스트필드.setColumns(10);
		
		몸무게텍스트필드 = new JTextField();
		몸무게텍스트필드.setBounds(790, 199, 116, 21);
		frame.getContentPane().add(몸무게텍스트필드);
		몸무게텍스트필드.setColumns(10);
		
		나이텍스트필드 = new JTextField();
		나이텍스트필드.setBounds(790, 258, 116, 21);
		frame.getContentPane().add(나이텍스트필드);
		나이텍스트필드.setColumns(10);
		
		포지션텍스트필드 = new JTextField();
		포지션텍스트필드.setBounds(790, 311, 116, 21);
		frame.getContentPane().add(포지션텍스트필드);
		포지션텍스트필드.setColumns(10);
		
		담당코치텍스트필드 = new JTextField();
		담당코치텍스트필드.setBounds(790, 352, 116, 21);
		frame.getContentPane().add(담당코치텍스트필드);
		담당코치텍스트필드.setColumns(10);
		
		담당의사텍스트필드 = new JTextField();
		담당의사텍스트필드.setBounds(790, 410, 116, 21);
		frame.getContentPane().add(담당의사텍스트필드);
		담당의사텍스트필드.setColumns(10);
		
		아이디텍스트필드 = new JTextField();
		아이디텍스트필드.setBounds(403, 186, 116, 21);
		frame.getContentPane().add(아이디텍스트필드);
		아이디텍스트필드.setColumns(10);
		
		비밀번호텍스트필드 = new JTextField();
		비밀번호텍스트필드.setBounds(403, 232, 116, 21);
		frame.getContentPane().add(비밀번호텍스트필드);
		비밀번호텍스트필드.setColumns(10);
		
		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_1.setToolTipText("asdads");
		tabbedPane_1.setBounds(103, 0, 126, 28);
		frame.getContentPane().add(tabbedPane_1);
		
		tabbedPane_2 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane_2.setBounds(224, 0, 126, 28);
		frame.getContentPane().add(tabbedPane_2);
		
		이미지등록버튼 = new JButton("이미지등록");
		이미지등록버튼.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				이미지를화면에등록하는메소드();
			}
		});
		이미지등록버튼.setBounds(66, 365, 126, 23);
		frame.getContentPane().add(이미지등록버튼);
		
		저장버튼 = new JButton("저장");
		저장버튼.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        identity등록메소드();
		        선수등록메소드();
		    }
		});

		저장버튼.setBounds(779, 528, 97, 23);
		frame.getContentPane().add(저장버튼);
		
		등번호라벨 = new JLabel("등번호");
		등번호라벨.setBounds(696, 57, 57, 15);
		frame.getContentPane().add(등번호라벨);
		
		등번호텍스트필드 = new JTextField();
		등번호텍스트필드.setBounds(790, 54, 116, 21);
		frame.getContentPane().add(등번호텍스트필드);
		등번호텍스트필드.setColumns(10);
		
		no라벨2 = new JLabel("no");
		no라벨2.setBounds(697, 465, 57, 15);
		frame.getContentPane().add(no라벨2);
		
		no2텍스트필드 = new JTextField();
		no2텍스트필드.setBounds(790, 462, 116, 21);
		frame.getContentPane().add(no2텍스트필드);
		no2텍스트필드.setColumns(10);
		
		JLabel 선수등록라벨 = new JLabel("선수등록");
		선수등록라벨.setBounds(785, 20, 57, 15);
		frame.getContentPane().add(선수등록라벨);
		
		JLabel no라벨1 = new JLabel("no");
		no라벨1.setBounds(291, 150, 57, 15);
		frame.getContentPane().add(no라벨1);
		
		no1텍스트필드 = new JTextField();
		no1텍스트필드.setBounds(403, 147, 116, 21);
		frame.getContentPane().add(no1텍스트필드);
		no1텍스트필드.setColumns(10);
		
		JLabel 역할라벨 = new JLabel("역할");
		역할라벨.setBounds(291, 295, 57, 15);
		frame.getContentPane().add(역할라벨);
		
		역할텍스트필드 = new JTextField();
		역할텍스트필드.setBounds(403, 292, 116, 21);
		frame.getContentPane().add(역할텍스트필드);
		역할텍스트필드.setColumns(10);
		
		JLabel identity등록라벨 = new JLabel("identity등록");
		identity등록라벨.setBounds(369, 80, 102, 15);
		frame.getContentPane().add(identity등록라벨);
		
		
		
	}


	@Override
	public void stateChanged(ChangeEvent e) {

		
	}
}
