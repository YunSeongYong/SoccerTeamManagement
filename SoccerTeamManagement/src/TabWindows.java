import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;

import dbutil.DBUtil;
import java.awt.CardLayout;

public class TabWindows extends JFrame implements ChangeListener {
   JTabbedPane pane;
   JLabel  lbl;
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
	private JLabel 이미지라벨;
	private JPanel 개인정보창;
	private JPanel 일정창;
	private JPanel 컨디션창;
	private JPanel 의사소견창;
	
	
	
	
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
	
	
	
   
   public TabWindows(String str){
      super(str);
      JPanel  one, two, three;
      pane = new JTabbedPane();
      pane.setBounds(0, 0, 972, 551);
      lbl = new JLabel("              ");
      lbl.setBounds(0, 146, 284, 15);
		
      
      one = new JPanel();
      one.setBackground(Color.WHITE);
      pane.addTab("One", one);
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
      아이디라벨.setBounds(239, 163, 60, 15);
      one.add(아이디라벨);
      
      JLabel 비밀번호라벨 = new JLabel("비밀번호");
      비밀번호라벨.setBounds(239, 228, 64, 15);
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
      나이텍스트필드.setBounds(709, 244, 116, 21);
      one.add(나이텍스트필드);
      나이텍스트필드.setColumns(10);
      
      포지션텍스트필드 = new JTextField();
      포지션텍스트필드.setBounds(711, 295, 116, 21);
      one.add(포지션텍스트필드);
      포지션텍스트필드.setColumns(10);
      
      담당코치텍스트필드 = new JTextField();
      담당코치텍스트필드.setBounds(723, 348, 116, 21);
      one.add(담당코치텍스트필드);
      담당코치텍스트필드.setColumns(10);
      
      담당의사텍스트필드 = new JTextField();
      담당의사텍스트필드.setBounds(720, 390, 116, 21);
      one.add(담당의사텍스트필드);
      담당의사텍스트필드.setColumns(10);
      
      아이디텍스트필드 = new JTextField();
      아이디텍스트필드.setBounds(311, 160, 116, 21);
      one.add(아이디텍스트필드);
      아이디텍스트필드.setColumns(10);
      
      비밀번호텍스트필드 = new JTextField();
      비밀번호텍스트필드.setBounds(311, 225, 116, 21);
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
      
      		저장버튼.setBounds(422, 434, 149, 46);
      		one.add(저장버튼);
      		
      		등번호라벨 = new JLabel("등번호");
      		등번호라벨.setBounds(618, 56, 64, 15);
      		one.add(등번호라벨);
      		
      		등번호텍스트필드 = new JTextField();
      		등번호텍스트필드.setBounds(700, 53, 116, 21);
      		one.add(등번호텍스트필드);
      		등번호텍스트필드.setColumns(10);
      		
      		no라벨2 = new JLabel("no");
      		no라벨2.setBounds(618, 434, 81, 15);
      		one.add(no라벨2);
      		
      		이미지등록창 = new JPanel();
      		이미지등록창.setBounds(26, 106, 192, 211);
      		one.add(이미지등록창);
      		
      		no2텍스트필드 = new JTextField();
      		no2텍스트필드.setBounds(723, 431, 116, 21);
      		one.add(no2텍스트필드);
      		no2텍스트필드.setColumns(10);
      		
      		JLabel 선수등록라벨 = new JLabel("선수등록");
      		선수등록라벨.setBounds(699, 10, 48, 15);
      		one.add(선수등록라벨);
      		
      		JLabel no라벨1 = new JLabel("no");
      		no라벨1.setBounds(237, 104, 62, 15);
      		one.add(no라벨1);
      		
      		no1텍스트필드 = new JTextField();
      		no1텍스트필드.setBounds(311, 101, 116, 21);
      		one.add(no1텍스트필드);
      		no1텍스트필드.setColumns(10);
      		
      		JLabel 역할라벨 = new JLabel("역할");
      		역할라벨.setBounds(239, 285, 60, 15);
      		one.add(역할라벨);
      		
      		역할텍스트필드 = new JTextField();
      		역할텍스트필드.setBounds(311, 282, 116, 21);
      		one.add(역할텍스트필드);
      		역할텍스트필드.setColumns(10);
      		
      		JLabel identity등록라벨 = new JLabel("identity등록");
      		identity등록라벨.setBounds(315, 56, 64, 15);
      		one.add(identity등록라벨);
      		
      		JButton 중복확인버튼 = new JButton("중복확인");
      		중복확인버튼.setBounds(460, 159, 97, 23);
      		one.add(중복확인버튼);
      		
      		JLabel 사용가능유무라벨 = new JLabel("중복확인 버튼을 누르세요");
      		사용가능유무라벨.setBounds(321, 191, 178, 15);
      		one.add(사용가능유무라벨);
      		
      
      
      
      
      //==================================================================
      
      two = new JPanel();
      two.add(new JLabel("두번째 탭입니다"));
      two.add(new JTextField("문자를 입력하세요"));
      two.setBackground(Color.yellow);
      pane.addTab("Two", two);
      
      
      
      //====================================================================
      
      three = new JPanel();
      three.setBackground(Color.WHITE);
      pane.addTab("Three", three);
      three.setLayout(null);
      
   
      
      JButton 개인정보버튼 = new JButton("개인정보");
      개인정보버튼.addActionListener(new ActionListener() {
    	  public void actionPerformed(ActionEvent arg0) {
    		  JButton 개인정보버튼 = new JButton("개인정보");
    		  개인정보버튼.addActionListener(new ActionListener() {
    		      public void actionPerformed(ActionEvent arg0) {
    		          개인정보창.setVisible(true);
    		          일정창.setVisible(false);
    		          컨디션창.setVisible(false);
    		          의사소견창.setVisible(false);
    		      }
    		  });
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
		일정버튼.setBounds(32, 213, 120, 46);
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
		컨디션버튼.setBounds(32, 346, 120, 46);
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
		의사소견버튼.setBounds(32, 466, 117, 46);
		three.add(의사소견버튼);
		
		JComboBox 날짜콤보박스 = new JComboBox();
		날짜콤보박스.setBounds(424, 26, 127, 21);
		three.add(날짜콤보박스);
		
		JComboBox 선수정보콤보박스 = new JComboBox();
		선수정보콤보박스.setBounds(756, 26, 117, 21);
		three.add(선수정보콤보박스);
		
		JLabel 날짜라벨 = new JLabel("날짜");
		날짜라벨.setBounds(372, 29, 44, 15);
		three.add(날짜라벨);
		
		JLabel 선수정보라벨 = new JLabel("선수정보");
		선수정보라벨.setBounds(678, 29, 66, 15);
		three.add(선수정보라벨);
		
		개인정보창 = new JPanel();
		개인정보창.setBounds(257, 91, 710, 421);
		three.add(개인정보창);
		
		일정창 = new JPanel();
		일정창.setBounds(12, 10, 10, 10);
		three.add(일정창);
		
		컨디션창 = new JPanel();
		컨디션창.setBounds(43, 10, 10, 10);
		three.add(컨디션창);
		
		의사소견창 = new JPanel();
		의사소견창.setBounds(92, 10, 10, 10);
		three.add(의사소견창);
      

      
      
      //====================================================================
      
      pane.setSelectedIndex(0);
      pane.addChangeListener(this);
      getContentPane().setLayout(null);
      this.getContentPane().add(pane);
      this.getContentPane().add(lbl);
      
      this.setSize(1000, 600);
      this.setVisible(true);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }//end

   @Override
   public void stateChanged(ChangeEvent e) {
      int index = pane.getSelectedIndex();//현재탭의 번호를 가져온다
      String msg = pane.getTitleAt(index); //index 위에 탭 문자열을 가져옴
      msg += "탭이 선택되었습니다";
      lbl.setText(msg);
      pane.setSelectedIndex(index); //현재 선택한 탭으로 화면 출력 변경
   }
   
   
   public static void main(String[] args) {
      new TabWindows("탭 예제");
      
   }
}
