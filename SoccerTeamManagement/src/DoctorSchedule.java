import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import dbutil.DBUtil;
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

import dbutil.DBUtil;

public class DoctorSchedule extends JFrame implements ChangeListener {

   private Connection con;
   private Statement stmt;
   private ResultSet rs;
   
   JTabbedPane pane;
   JLabel lbl;
   private JTextArea textArea;
   private JTextField textField;
   private JTextArea textArea2;
   private JTextArea textArea3;
   private JTextArea textArea4;

   public DoctorSchedule() {
      super();
      JPanel one, two, three;
      pane = new JTabbedPane();
      lbl = new JLabel("              ");

      one = new JPanel();
      one.setBackground(Color.pink);
      pane.addTab("의사일정", one);
      one.setLayout(null);

      

      textArea = new JTextArea();
      textArea.setBounds(25, 10, 688, 85);
      one.add(textArea);
      textArea.setColumns(10);

      JButton btnNewButton_1 = new JButton("불러오기");
      btnNewButton_1.setBounds(725, 4, 140, 78);
      btnNewButton_1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            try {
               //textArea 은 텍스트 박스 안에 있는 문장을 여러 문장으로 쓸꺼다
               textArea.setText("");
               // 데이터베이스에 연결합니다.
               Connection connection = DBUtil.getConnection();
               Statement statement = connection.createStatement();
               String sql = "SELECT * FROM `condition`"; // 백틱 backtick
               ResultSet resultSet = statement.executeQuery(sql);
               while (resultSet.next()) {
                  int no = resultSet.getInt("no");
                  int numder = resultSet.getInt("number");
                  String playername = resultSet.getString("playername");
                  String plyerconditon = resultSet.getString("playercondition");
                  LocalDateTime when = resultSet.getTimestamp("when").toLocalDateTime();
                     //String format = String.format("%d : %d : %s : %s : %s", no, numder, playername, plyerconditon, when);
                  //여러문장을 이렇게 쓴다 생각 하시면되요
                  String format = String.format("%d : %d : %s : %s : %s", no, numder, playername, plyerconditon, when);
                  System.out.println(format);
                  textArea.append(format+"\n");
               }

            } catch (SQLException e1) {
               e1.printStackTrace();
            }
         }
      });
      one.add(btnNewButton_1);
      
      JComboBox comboBox = new JComboBox();
      comboBox.setBounds(876, 4, 61, 34);
      comboBox.addItem("다른의사");
      
      comboBox.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              // 3번째 탭으로 이동
              JTabbedPane tabbedPane = (JTabbedPane) comboBox.getParent();
              tabbedPane.setSelectedIndex(2);
          }
      });
      one.add(comboBox);
      

      two = new JPanel();
      two.setBackground(Color.yellow);
      pane.addTab("Two", two);
      two.setLayout(null);
      
      textField = new JTextField();
      textField.setBounds(12, 10, 588, 74);
      two.add(textField);
      textField.setColumns(10);
      
      textArea2 = new JTextArea();
      textArea2.setBounds(12, 104, 588, 74);
      two.add(textArea2);
      textArea2.setColumns(10);
      
      textArea3 = new JTextArea();
      textArea3.setBounds(12, 200, 588, 68);
      two.add(textArea3);
      textArea3.setColumns(10);
      
      textArea4 = new JTextArea();
      textArea4.setBounds(12, 299, 588, 60);
      two.add(textArea4);
      textArea4.setColumns(10);
      
      
      JButton btnNewButton_2 = new JButton("넣기");
      btnNewButton_2.setBounds(695, 10, 167, 74);
      btnNewButton_2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            Connection conn =null;
            PreparedStatement statement = null;
            try {
               //textArea 은 텍스트 박스 안에 있는 문장을 여러 문장으로 쓸꺼다
               String string = textField.getText();
               String string2 = textArea2.getText();
               String string3 = textArea3.getText();
               String string4 = textArea4.getText();
               
               
               
               // 데이터베이스에 연결합니다.
               conn = DBUtil.getConnection();
               
               statement =conn.prepareStatement("INSERT INTO comment (number, datetime, doctorcomment, who) VALUES (?, ?, ?, ?)");
               statement.setInt(1, Integer.valueOf(string));
               statement.setTimestamp(2, Timestamp.valueOf(string2));
               statement.setString(3, string3);
               statement.setString(4, string4);
               statement.executeUpdate();
               
               

               


                  // Timestamp timestamp = Timestamp.valueOf(starttime);
               //   String sql1 = "INSERT INTO comment (number, , doctorcomment, who) VALUES ('" + number + "', '" + starttime + "', '"  + doctorcomment + "', '" + who + "');";
               //   statement.executeUpdate(sql1);
               
            } catch (SQLException e1) {
               e1.printStackTrace();
            } finally {
               try {
                  conn.close();
               } catch (SQLException e1) {
                  // TODO Auto-generated catch block
                  e1.printStackTrace();
               }
            }
         }
         
         });
      two.add(btnNewButton_2);
      
      

      three = new JPanel();
      three.add(new JLabel("세번째 탭입니다"));
      three.add(new JTextField("문자를 입력하세요"));
      three.setBackground(Color.cyan);
      pane.addTab("Three", three);

      pane.setSelectedIndex(0);
      pane.addChangeListener(this);
      this.getContentPane().add("North", new JLabel("탭을 사용한 예"));
      this.getContentPane().add("Center", pane);
      this.getContentPane().add("South", lbl);

      this.setSize(981, 781);
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
      new DoctorSchedule();
   }
}