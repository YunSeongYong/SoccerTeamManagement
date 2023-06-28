import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import dbutil.DBUtil;

import javax.swing.JButton;

public class Login {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblNewLabel_2;
	public Player player;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		player = new Player();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("아이디");
		lblNewLabel.setBounds(295, 392, 57, 15);
		frame.getContentPane().add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(364, 389, 176, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("비밀번호");
		lblNewLabel_1.setBounds(295, 434, 57, 15);
		frame.getContentPane().add(lblNewLabel_1);

		textField_1 = new JTextField();
		textField_1.setBounds(364, 431, 176, 21);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton = new JButton("로그인");
		btnNewButton.setBounds(552, 388, 97, 64);
		frame.getContentPane().add(btnNewButton);

		lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setBounds(351, 500, 229, 15);
		frame.getContentPane().add(lblNewLabel_2);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String id = textField.getText();
				String password = textField_1.getText();
				
				
				int check = checkIdentity(id, password);

				if (check == 1) {
					String role = getRole(textField.getText());
					lblNewLabel_2.setText(role);
					
					if (role.equals("감독")) {
						new DirectorGUI();
					} else if (role.equals("선수")) {
						
						player.setBackNumber(checkBackNumber(id));	
						new PlayerTab();
					}
				} else {
					lblNewLabel_2.setText("회원정보가 일치하지 않습니다.");
				}
			}
		});
	}

	private static String getRole(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT role FROM identity WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);

			rs = stmt.executeQuery();
			if (rs.next()) {
				String role = rs.getString("role");
				return role;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return null;
	}
	
	private static int checkIdentity(String id, String password) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT count(*) AS A FROM identity WHERE id = ? AND password = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, password);

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
		return 0;
	}
	// 회원정보 번호 찾기
	private static int checkNumber(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT no FROM identity WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);

			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("no");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return 0;
	}
	// 등 번호 찾기
	private static int checkBackNumber(String id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT backnumber FROM players WHERE no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, checkNumber(id));

			rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("backnumber");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(stmt);
			DBUtil.close(conn);
		}
		return 0;
	}
}
