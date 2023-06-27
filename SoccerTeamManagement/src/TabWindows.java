import java.awt.Color;

import javax.swing.*;
import javax.swing.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class TabWindows extends JFrame implements ChangeListener {
	
   JTabbedPane pane;
   JLabel  lbl;
   
   
   public TabWindows(String str){
      super(str);
      JPanel  one, two, three;
      pane = new JTabbedPane();
      lbl = new JLabel("              ");
      
      one = new JPanel();
      one.setLayout(null);
      JTextField textField = new JTextField("1.");
      textField.setBounds(53, 33, 597, 21);
      one.add(textField);
      one.setBackground(Color.WHITE);
      pane.addTab("의사일정", one);
      
      JButton button = new JButton("확인");
      one.add(button);
      button.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              String text = textField.getText();

              // 확인 버튼을 누르면 텍스트 필드의 내용이 테이블에 들어가야 합니다.
              if (!text.isEmpty()) {
                  // SQL 문을 업데이트하여 텍스트 필드의 내용을 포함합니다.
                  String sql = "SELECT * FROM project.condition WHERE number = " + text;

                  // SQL 문을 실행하고 결과를 인쇄합니다.
                  try {
                      Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.182:3306/project", "project", "project");
                      Statement statement = connection.createStatement();
                      ResultSet resultSet = statement.executeQuery(sql);

                      while (resultSet.next()) {
                          int no = resultSet.getInt("no");
                          int number = resultSet.getInt("number");
                          String playername = resultSet.getString("playername");
                          String playercondition = resultSet.getString("playercondition");
                          LocalDateTime when = resultSet.getTimestamp("when").toLocalDateTime();
                          System.out.print(no + number + playername +playercondition + when );
                      
                         
                      }

                      connection.close();
                  } catch (SQLException e1) {
                      e1.printStackTrace();
                  }
              }
          }
      });

      button.setBounds(650, 33, 89, 23);
      one.add(button);

      
      
      two = new JPanel();
      two.setLayout(null);
      JTextField textField_3 = new JTextField("문자를 입력하세요");
      textField_3.setBounds(48, 23, 563, 21);
      two.add(textField_3);
      two.setBackground(Color.WHITE);
      pane.addTab("환자상태", two);
      
      JButton btnNewButton = new JButton("확인");
      btnNewButton.setBounds(623, 22, 97, 23);
      two.add(btnNewButton);
      JButton btnNewButton1 = new JButton("확인");
      btnNewButton1.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              String text = textField.getText();

              // 확인 버튼을 누르면 텍스트 필드의 내용이 테이블에 들어가야 합니다.
              if (!text.isEmpty()) {
                  // SQL 문을 업데이트하여 텍스트 필드의 내용을 포함합니다.
                  String sql = "SELECT * FROM project.comment WHERE number = " + text;

                  // SQL 문을 실행하고 결과를 인쇄합니다.
                  try {
                      Connection connection = DriverManager.getConnection("jdbc:mysql://192.168.0.182:3306/project", "project", "project");
                      Statement statement = connection.createStatement();
                      ResultSet resultSet = statement.executeQuery(sql);

                      while (resultSet.next()) {
                    	  int number = resultSet.getInt("number");
                          LocalDateTime datetime = resultSet.getTimestamp("datetime").toLocalDateTime();
                          String schedulecomment = resultSet.getString("schedulecomment");
                          String conditioncomment = resultSet.getString("conditioncomment");
                          String doctorcomment = resultSet.getString("doctorcomment");
                          String who = resultSet.getString("who");
                          
                          System.out.print(resultSet.getString("number,datetime,schedulecomment,conditioncomment,doctorcomment,who");
                        
                         
                      }

                      connection.close();
                  } catch (SQLException e1) {
                      e1.printStackTrace();
                  }
              }
          }
      });

      button.setBounds(650, 33, 89, 23);
      one.add(button);
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
      
      this.setSize(784, 595);
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