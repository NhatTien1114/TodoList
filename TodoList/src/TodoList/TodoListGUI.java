package TodoList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

public class TodoListGUI extends JFrame {
    private TodoList todoList;
    private JTable taskTable;
    private DefaultTableModel tableModel;

    private JTextField txtId, txtTitle, txtDueDate;
    private JTextArea txtDescription;
    private JCheckBox chkCompleted;

    public TodoListGUI() {
        todoList = new TodoList();
        setTitle("Todo List App");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Dark theme colors
        Color bgColor = new Color(30, 30, 30);
        Color fgColor = Color.WHITE;

        // Table
        String[] columnNames = {"ID", "Tiêu đề", "Mô tả", "Ngày hoàn thành", "Trạng thái"};
        tableModel = new DefaultTableModel(columnNames, 0);
        taskTable = new JTable(tableModel);
        taskTable.setBackground(bgColor);
        taskTable.setForeground(fgColor);
        taskTable.setGridColor(Color.GRAY);
        taskTable.setFont(new Font("Consolas", Font.PLAIN, 14));
        taskTable.setRowHeight(30);

        // Set renderer to wrap text
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setVerticalAlignment(SwingConstants.TOP);
        taskTable.getColumnModel().getColumn(2).setCellRenderer(cellRenderer); 

        JScrollPane scrollPane = new JScrollPane(taskTable);
        scrollPane.getViewport().setBackground(bgColor);
        add(scrollPane, BorderLayout.CENTER);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(bgColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField();
        txtTitle = new JTextField();
        txtDueDate = new JTextField();

        txtDescription = new JTextArea(10, 50);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(txtDescription);

        chkCompleted = new JCheckBox("Đã hoàn thành");
        chkCompleted.setForeground(fgColor);
        chkCompleted.setBackground(bgColor);

        Font labelFont = new Font("Arial", Font.BOLD, 13);

        JLabel lblId = new JLabel("ID:"); lblId.setForeground(fgColor); lblId.setFont(labelFont);
        JLabel lblTitle = new JLabel("Tiêu đề:"); lblTitle.setForeground(fgColor); lblTitle.setFont(labelFont);
        JLabel lblDesc = new JLabel("Mô tả:"); lblDesc.setForeground(fgColor); lblDesc.setFont(labelFont);
        JLabel lblDue = new JLabel("Ngày hoàn thành (yyyy-MM-dd):"); lblDue.setForeground(fgColor); lblDue.setFont(labelFont);
        JLabel lblStatus = new JLabel("Trạng thái:"); lblStatus.setForeground(fgColor); lblStatus.setFont(labelFont);

        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(lblId, gbc);
        gbc.gridx = 1;
        formPanel.add(txtId, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(lblTitle, gbc);
        gbc.gridx = 1;
        formPanel.add(txtTitle, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(lblDesc, gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        formPanel.add(descScroll, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(lblDue, gbc);
        gbc.gridx = 1;
        formPanel.add(txtDueDate, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        formPanel.add(lblStatus, gbc);
        gbc.gridx = 1;
        formPanel.add(chkCompleted, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);

        JButton btnAdd = new JButton("Thêm");
        JButton btnSearch = new JButton("Tìm kiếm");
        JButton btnUpdate = new JButton("Cập nhật");
        JButton btnDelete = new JButton("Xóa");
        JButton btnComplete = new JButton("Đánh dấu hoàn thành");

        Font btnFont = new Font("Arial", Font.BOLD, 13);
        Color btnColor = new Color(60, 63, 65);
        Color btnText = Color.WHITE;

        for (JButton btn : new JButton[]{btnAdd, btnSearch, btnUpdate, btnDelete, btnComplete}) {
            btn.setBackground(btnColor);
            btn.setForeground(btnText);
            btn.setFocusPainted(false);
            btn.setFont(btnFont);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        // Event listeners
        btnAdd.addActionListener(e -> addTask());
        btnSearch.addActionListener(e -> searchTask());
        btnUpdate.addActionListener(e -> updateTask());
        btnDelete.addActionListener(e -> deleteTask());
        btnComplete.addActionListener(e -> markCompleted());

        setVisible(true);
    }
    
    private void searchTask() {
        try {
            String idText = txtId.getText().trim();
            System.out.println("ID nhập: " + idText);  // ← in ra kiểm tra
            if (idText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ID để tìm kiếm!");
                return;
            }

            int id = Integer.parseInt(idText);
            System.out.println("Tìm với ID: " + id);  // ← in ra kiểm tra

            Task task = todoList.findTaskById(id);
            if (task != null) {
                txtTitle.setText(task.getTitle());
                txtDescription.setText(task.getDescription());
                txtDueDate.setText(task.getDueDate().toString());
                chkCompleted.setSelected(task.isCompleted());
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy task với ID này!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID phải là số nguyên!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }



    private void addTask() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String title = txtTitle.getText();
            String desc = txtDescription.getText();
            LocalDate due = LocalDate.parse(txtDueDate.getText());
            boolean completed = chkCompleted.isSelected();

            Task t = new Task(id, title, desc, due, completed);
            if (todoList.addTask(t)) {
                updateTable(); 
                JOptionPane.showMessageDialog(this, "Thêm task thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "ID đã tồn tại!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }


    private void updateTask() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Task task = todoList.findTaskById(id);
            if (task != null) {
                task.setTitle(txtTitle.getText());
                task.setDescription(txtDescription.getText());
                task.setDueDate(LocalDate.parse(txtDueDate.getText()));
                task.setCompleted(chkCompleted.isSelected());

                updateTable();
                JOptionPane.showMessageDialog(this, "Cập nhật task thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy task để cập nhật!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void deleteTask() {
        try {
            int id = Integer.parseInt(txtId.getText());
            if (todoList.removeTask(id)) {
                updateTable();
                JOptionPane.showMessageDialog(this, "Xóa task thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy task!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void markCompleted() {
        try {
            int id = Integer.parseInt(txtId.getText());
            if (todoList.markTaskAsCompleted(id)) {
                updateTable();
                JOptionPane.showMessageDialog(this, "Đã đánh dấu hoàn thành!");
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy task!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void updateTable() {
    	tableModel.setRowCount(0); // Xóa các dòng trong bảng
        for (Task t : todoList.list) {
            tableModel.addRow(new Object[]{
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.getDueDate(),
                t.isCompleted() ? "Đã hoàn thành" : "Chưa hoàn thành"
            });
        }

        // Đặt chiều rộng cột mô tả tự động điều chỉnh để phù hợp với nội dung
        taskTable.getColumnModel().getColumn(2).setPreferredWidth(300); // Cột mô tả

        // Cấu hình renderer cho cột mô tả
        taskTable.getColumnModel().getColumn(2).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Tạo JTextArea để hiển thị mô tả, cho phép xuống dòng
                JTextArea textArea = new JTextArea();
                textArea.setText(value == null ? "" : value.toString());
                textArea.setWrapStyleWord(true);  // Cho phép xuống dòng trong từ
                textArea.setLineWrap(true);       // Cho phép xuống dòng tự động
                textArea.setBackground(table.getBackground());
                textArea.setForeground(table.getForeground());
                textArea.setFont(table.getFont());
                textArea.setEditable(false);  // Không cho phép chỉnh sửa
                textArea.setCaretPosition(0);  // Đảm bảo cuộn lên trên khi có nội dung mới

                // Tính toán chiều cao của ô mô tả dựa trên số dòng của nội dung
                int lines = textArea.getLineCount(); // Đếm số dòng
                int lineHeight = textArea.getFontMetrics(textArea.getFont()).getHeight();
                int rowHeight = Math.max(lineHeight * lines, 40); // Đảm bảo tối thiểu chiều cao 40px
                table.setRowHeight(row, rowHeight); // Cập nhật chiều cao dòng cho bảng

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setBorder(null);
                return scrollPane;
            }
        });

        // Cập nhật lại bảng để đảm bảo mọi thay đổi đều được áp dụng
        taskTable.revalidate();
        taskTable.repaint();
    }






    public static void main(String[] args) {
        SwingUtilities.invokeLater(TodoListGUI::new);
    }
}
