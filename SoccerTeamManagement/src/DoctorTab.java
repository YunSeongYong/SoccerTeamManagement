import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DoctorTab extends JFrame implements ChangeListener {
	JTabbedPane pane;

	public DoctorTab() {
		pane = new JTabbedPane();
		JPanel one, two;

		two = new JPanel();
		two.add(new JLabel("컨디션"));
		two.add(new JTextField("문자를 입력하세요"));
		pane.addTab("컨디션", two);

		pane.setSelectedIndex(0);
		pane.addChangeListener(this);
		this.getContentPane().add("Center", pane);

		one = new DoctorSchedule();
		one.setSize(1000, 600);
		one.setLayout(null);
		pane.addTab("개인일정", one);

		setBounds(100, 100, 1000, 600);
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int index = pane.getSelectedIndex();// 현재탭의 번호를 가져온다
		String msg = pane.getTitleAt(index); // index 위에 탭 문자열을 가져옴
		pane.setSelectedIndex(index); // 현재 선택한 탭으로 화면 출력 변경
	}

	public static void main(String[] args) {
		new DoctorTab();
	}
}
