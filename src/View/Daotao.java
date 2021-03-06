package View;

import Controller.DaoTaoMgr;
import entities.DaoTao;
import entities.LoggedRole;
import entities.NhanVien;
import entities.PhongBan;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public final class Daotao extends JInternalFrame {

    private JPanel contentPane, panel2;
    private JTextField txtMalop, txtTenLop, txtGhiChu, errMsg, txtTuNgay, txtDenNgay;
    private JComboBox cmbPhongBan;
    private JTextArea areaDanhSach;
    private JTable listNhanVien, listLopDT;
    private JButton btnTimKiem, btnThem, btnSua, btnXoa, btnTuNgay, btnDenNgay, btnLamMoi;
    JDialog d;
    private JScrollPane textArea;
    private final DaoTaoMgr daoTaoMgr = new DaoTaoMgr();
    final DefaultComboBoxModel Name = new DefaultComboBoxModel();
    String danhsach = "";
    MyTableModel model = new MyTableModel();
    DefaultTableModel dTaoModel = new DefaultTableModel();

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Daotao frame = new Daotao();

                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Daotao() {
        initUI();
        initTableDaoTao();
    }

    public void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1400, 650);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        panel2 = new JPanel();
        panel2.setBorder(new TitledBorder(null, "", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        panel2.setBounds(15, 10, 830, 370);
        panel2.setLayout(null);
        contentPane.add(panel2);
        JLabel maLop = new JLabel("Mã lớp");
        maLop.setBounds(50, 10, 150, 25);
        panel2.add(maLop);

        txtMalop = new JTextField();
        txtMalop.setBounds(200, 10, 250, 25);
        panel2.add(txtMalop);

        JLabel tenLop = new JLabel("Tên lớp");
        tenLop.setBounds(50, 55, 150, 25);
        panel2.add(tenLop);

        txtTenLop = new JTextField();
        txtTenLop.setBounds(200, 55, 550, 25);
        panel2.add(txtTenLop);

        JLabel phongBan = new JLabel("Mã phòng ban");
        phongBan.setBounds(50, 100, 150, 25);
        panel2.add(phongBan);
        fillCombobox();
        cmbPhongBan = new JComboBox(Name);
        cmbPhongBan.setBounds(200, 100, 250, 25);
        cmbPhongBan.setSelectedIndex(0);
        cmbPhongBan.setBackground(Color.white);
        JScrollPane ListScrollPane = new JScrollPane(cmbPhongBan);
        panel2.add(cmbPhongBan);
        cmbPhongBan.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {

                try {
                    String maPhongBan = cmbPhongBan.getSelectedItem().toString();
                    if (!maPhongBan.equals("Mã Phòng Ban")) {
                        initTable(maPhongBan);
                        txtMalop.setText("");
                        txtMalop.setEditable(true);
                        txtTenLop.setText("");
                        txtTuNgay.setText("");
                        txtDenNgay.setText("");
                        txtGhiChu.setText("");
                        areaDanhSach.setText("");
                    } else {
                        model.fireTableDataChanged();
                        model.setRowCount(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        JLabel thoiGian = new JLabel("Thời gian");
        thoiGian.setBounds(50, 145, 150, 25);
        panel2.add(thoiGian);

        JLabel tuNgay = new JLabel("Từ ngày");
        tuNgay.setBounds(200, 145, 150, 25);
        panel2.add(tuNgay);

        txtTuNgay = new JTextField();
        txtTuNgay.setBounds(280, 145, 150, 25);
        panel2.add(txtTuNgay);

        btnTuNgay = new JButton(new ImageIcon("src\\image\\datetimepicker.png"));
        btnTuNgay.setForeground(Color.BLUE);
        btnTuNgay.setBounds(435, 145, 25, 25);
        panel2.add(btnTuNgay);
        btnTuNgay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                DatePicker dp = new DatePicker();
                Point bP = btnTuNgay.getLocationOnScreen();
                dp.d.setLocation(bP.x, bP.y + btnTuNgay.getHeight());
                dp.d.setVisible(true);
                txtTuNgay.setText(dp.setPickedDate());

            }
        });

        JLabel denNgay = new JLabel("Đến ngày");
        denNgay.setBounds(480, 145, 150, 25);
        panel2.add(denNgay);

        txtDenNgay = new JTextField();
        txtDenNgay.setBounds(560, 145, 150, 25);
        panel2.add(txtDenNgay);

        btnDenNgay = new JButton(new ImageIcon("src\\image\\datetimepicker.png"));
        btnDenNgay.setForeground(Color.BLUE);
        btnDenNgay.setBounds(730, 155, 25, 25);
        contentPane.add(btnDenNgay);
        btnDenNgay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                DatePicker dp = new DatePicker();
                Point bP = btnDenNgay.getLocationOnScreen();
                dp.d.setLocation(bP.x, bP.y + btnDenNgay.getHeight());
                dp.d.setVisible(true);
                txtDenNgay.setText(dp.setPickedDate());

            }
        });

        JLabel danhSach = new JLabel("Danh sách");
        danhSach.setBounds(50, 190, 150, 25);
        panel2.add(danhSach);

        areaDanhSach = new JTextArea();
        areaDanhSach.setBounds(200, 190, 550, 100);
        panel2.add(areaDanhSach);
        textArea = new JScrollPane(areaDanhSach);
        textArea.setBounds(200, 190, 550, 100);
        textArea.setVisible(true);
        panel2.add(textArea);
        areaDanhSach.setEditable(false);
        areaDanhSach.setLineWrap(true);

        JLabel ghichu = new JLabel("Ghi chú");
        ghichu.setBounds(50, 315, 150, 25);
        panel2.add(ghichu);

        txtGhiChu = new JTextField();
        txtGhiChu.setBounds(200, 315, 550, 25);
        panel2.add(txtGhiChu);

        errMsg = new JTextField();
        errMsg.setBounds(200, 345, 250, 18);
        errMsg.setFont(new Font("Tahoma", Font.ITALIC, 14));
        errMsg.setBorder(null);
        errMsg.setForeground(Color.red);
        panel2.add(errMsg);
        ImageIcon icon = new ImageIcon("src\\image\\search.png");
        btnTimKiem = new JButton("Tìm kiếm", icon);
        btnTimKiem.setForeground(Color.BLUE);
        btnTimKiem.setBounds(80, 405, 120, 25);
        contentPane.add(btnTimKiem);
        btnTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String maLop = txtMalop.getText();
                if (txtMalop.getText().length() > 10) {
                    errMsg.setText("Độ dài mã lớp không hợp lệ (<= 10 kí tự)");
                    txtMalop.requestFocus();
                    return;
                }
                String maPhongBan = "";
                if (cmbPhongBan.getSelectedIndex() != 0) {
                    maPhongBan = cmbPhongBan.getSelectedItem().toString();
                }
                String tuNgay = txtTuNgay.getText();
                String denNgay = txtDenNgay.getText();
                String datePattern = "\\d{1,2}-\\d{1,2}-\\d{4}";
                boolean isDate = false;
                if (!tuNgay.equals("")) {
                    isDate = tuNgay.matches(datePattern);
                    if (isDate == false) {
                        errMsg.setText("Không đúng định dạng ngày tháng");
                        txtTuNgay.requestFocus();
                        return;
                    }
                }
                errMsg.setText("");
                if (!denNgay.equals("")) {
                    isDate = denNgay.matches(datePattern);
                    if (isDate == false) {
                        errMsg.setText("Không đúng định dạng ngày tháng");
                        txtDenNgay.requestFocus();
                        return;
                    }
                }
                if (!tuNgay.equals("") && !denNgay.equals("")) {
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date tuNgay1 = null;
                    Date denNgay1 = null;
                    Date now = new Date();
                    try {
                        tuNgay1 = format.parse(tuNgay);
                        denNgay1 = format.parse(denNgay);
                    } catch (ParseException ex) {
                        Logger.getLogger(Daotao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    boolean before = tuNgay1.before(denNgay1);
                    if (before == false) {
                        errMsg.setText("Từ ngày phải trước đến ngày");
                        txtDenNgay.requestFocus();
                        return;
                    }
                }

                errMsg.setText("");
                try {
                    List<DaoTao> list = daoTaoMgr.getListDaoTao(maLop, maPhongBan, tuNgay, denNgay);
                    if (list.size() > 0) {
                        loadTableDaoTao(list);
                        JOptionPane.showMessageDialog(d, "Thông tin lớp đào tạo bạn tìm như sau: ");
                    } else {
                        JOptionPane.showMessageDialog(d, "Tìm kiếm thất bại ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(d, "Không tồn tại lớp đào tạo có thông tin như trên ");
                }

            }
        });
        ImageIcon icon6 = new ImageIcon("src\\image\\them.png");
        btnLamMoi = new JButton("Làm mới", icon6);
        btnLamMoi.setForeground(Color.BLUE);
        btnLamMoi.setBounds(220, 405, 120, 25);
        btnLamMoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                LamMoi();
            }
        });
        contentPane.add(btnLamMoi);
        ImageIcon icon1 = new ImageIcon("src\\image\\save.png");
        btnThem = new JButton("Thêm", icon1);
        btnThem.setForeground(Color.BLUE);
        btnThem.setBounds(360, 405, 120, 25);
        btnThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (checkDaoTao()) {
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Date tuNgay = null;
                    Date now = new Date();
                    try {
                        tuNgay = format.parse(txtTuNgay.getText());
                    } catch (ParseException ex) {
                        Logger.getLogger(Daotao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    boolean beforeDatanow = tuNgay.before(now);
                    if (beforeDatanow == true) {
                        errMsg.setText("Từ ngày phải trước sau hiện tại");
                        txtTuNgay.requestFocus();
                        return;
                    }
                    if (daoTaoMgr.getDaoTaoById(txtMalop.getText()) == true) {
                        errMsg.setText("Không được trùng mã lớp");
                        txtMalop.requestFocus();
                        return;
                    }
                    errMsg.setText("");
                    DaoTao daoTao = new DaoTao();
                    daoTao.setMaLop(txtMalop.getText());
                    daoTao.setTenLop(txtTenLop.getText());
                    daoTao.setDanhSach(areaDanhSach.getText());
                    daoTao.setMaPhongBan(cmbPhongBan.getSelectedItem().toString());
                    daoTao.setTuNgay(txtTuNgay.getText());
                    daoTao.setDenNgay(txtDenNgay.getText());
                    daoTao.setGhiChu(txtGhiChu.getText());
                    if (daoTaoMgr.saveDaoTao(daoTao) == true) {
                        JOptionPane.showMessageDialog(d, "Thêm mới thành công");
                        LamMoi();
                    } else {
                        JOptionPane.showMessageDialog(d, "Thêm mới thất bại");
                    }
                    initTableDaoTao();
                }
            }
        });

        ImageIcon icon2 = new ImageIcon("src\\image\\edit.png");
        btnSua = new JButton("Cập nhật", icon2);
        btnSua.setForeground(Color.BLUE);
        btnSua.setBounds(500, 405, 120, 25);
        btnSua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (checkDaoTao()) {
                    DaoTao daoTao = new DaoTao();
                    daoTao.setMaLop(txtMalop.getText());
                    daoTao.setTenLop(txtTenLop.getText());
                    daoTao.setDanhSach(areaDanhSach.getText());
                    daoTao.setMaPhongBan(cmbPhongBan.getSelectedItem().toString());
                    daoTao.setTuNgay(txtTuNgay.getText());
                    daoTao.setDenNgay(txtDenNgay.getText());
                    daoTao.setGhiChu(txtGhiChu.getText());
                    if (daoTaoMgr.saveDaoTao(daoTao) == true) {
                        JOptionPane.showMessageDialog(d, "Cập nhật thành công");
                    } else {
                        JOptionPane.showMessageDialog(d, "Cập nhật thất bại");
                    }
                    initTableDaoTao();
                }
            }
        });

        ImageIcon icon3 = new ImageIcon("src\\image\\delete.png");
        btnXoa = new JButton("Xóa", icon3);
        btnXoa.setForeground(Color.BLUE);
        btnXoa.setBounds(640, 405, 120, 25);
        btnXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Bạn muốn xóa lớp đào tạo?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    if (txtMalop.getText().equals("")) {
                        JOptionPane.showMessageDialog(d, "Vui lòng chọn lớp đào tạo bạn muốn xóa");
                        return;
                    }
                    if (daoTaoMgr.deleteDaoTaoByID(txtMalop.getText()) == true) {
                        JOptionPane.showMessageDialog(d, "Xóa thành công");
                        LamMoi();
                    } else {
                        JOptionPane.showMessageDialog(d, "Xóa thất bại");
                    }
                }
                initTableDaoTao();
            }
        });
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 450, 1350, 160);
        contentPane.add(scrollPane);

        listLopDT = new JTable();
        listLopDT.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mã lớp", "Tên lớp", "Mã phòng ban", "Từ ngày", "Đến ngày", "Danh sách", "Ghi chú"
                }
        ));

        listLopDT.setBounds(0, 450, 1350, 160);
        scrollPane.setViewportView(listLopDT);

        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setBounds(900, 15, 450, 430);
        contentPane.add(scrollPane1);

        listNhanVien = new JTable();
        listNhanVien.setModel(new MyTableModel());

        listNhanVien.setBounds(900, 15, 450, 430);
        scrollPane1.setViewportView(listNhanVien);
        String role = LoggedRole.getLoggedRole();
        if (role.equals("Admin") || role.equals("SA")) {
            contentPane.add(btnXoa);
            contentPane.add(btnThem);
            contentPane.add(btnSua);
        } else if (role.equals("Execute")) {
            contentPane.add(btnThem);
            contentPane.add(btnSua);
        }

    }

    public void fillCombobox() {
        List<PhongBan> list = daoTaoMgr.getListPhongBan();
        Name.addElement("Mã Phòng Ban");
        for (int i = 0; i < list.size(); i++) {

            PhongBan pb = list.get(i);
            Name.addElement(pb.getMaPhongBan());
        }
    }

    private void initTableDaoTao() {
        dTaoModel.fireTableDataChanged();
        dTaoModel = (DefaultTableModel) listLopDT.getModel();
        dTaoModel.setRowCount(0);
        try {
            List<DaoTao> list = daoTaoMgr.getListDaoTao();
            System.out.println(list.size() + " nè");
            if (!list.isEmpty()) {
                for (DaoTao E : list) {

                    dTaoModel.addRow(new Object[]{
                        E.getMaLop(), E.getTenLop(), E.getMaPhongBan(), E.getTuNgay(), E.getDenNgay(), E.getDanhSach(), E.getGhiChu()
                    });

                }

            }
            listLopDT.setModel(dTaoModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listLopDT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                int index = listLopDT.getSelectedRow();
                if (index >= 0) {
                    txtMalop.setEditable(false);
                    txtMalop.setText(listLopDT.getValueAt(index, 0).toString());
                    txtTenLop.setText(listLopDT.getValueAt(index, 1).toString());
                    txtTuNgay.setText(listLopDT.getValueAt(index, 3).toString());
                    txtDenNgay.setText(listLopDT.getValueAt(index, 4).toString());
                    areaDanhSach.setText(listLopDT.getValueAt(index, 5).toString());
                    txtGhiChu.setText(listLopDT.getValueAt(index, 6).toString());
                    cmbPhongBan.setSelectedItem(listLopDT.getValueAt(index, 2));
                }
            }
        });
    }

    private void loadTableDaoTao(List<DaoTao> list) {
        dTaoModel.fireTableDataChanged();
        dTaoModel = (DefaultTableModel) listLopDT.getModel();
        dTaoModel.setRowCount(0);
        try {
            System.out.println(list.size() + " nè");
            if (!list.isEmpty()) {
                for (DaoTao E : list) {

                    dTaoModel.addRow(new Object[]{
                        E.getMaLop(), E.getTenLop(), E.getMaPhongBan(), E.getTuNgay(), E.getDenNgay(), E.getDanhSach(), E.getGhiChu()
                    });

                }

            }
            listLopDT.setModel(dTaoModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        listLopDT.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                int index = listLopDT.getSelectedRow();
                if (index >= 0) {
                    txtMalop.setEditable(false);
                    txtMalop.setText(listLopDT.getValueAt(index, 0).toString());
                    txtTenLop.setText(listLopDT.getValueAt(index, 1).toString());
                    txtTuNgay.setText(listLopDT.getValueAt(index, 3).toString());
                    txtDenNgay.setText(listLopDT.getValueAt(index, 4).toString());
                    areaDanhSach.setText(listLopDT.getValueAt(index, 5).toString());
                    txtGhiChu.setText(listLopDT.getValueAt(index, 6).toString());
                    cmbPhongBan.setSelectedItem(listLopDT.getValueAt(index, 2));
                }
            }
        });
    }

    private void initTable(String maPhongBan) {
        model.fireTableDataChanged();
        model.setRowCount(0);
        danhsach = "";
        try {
            System.out.println(maPhongBan);
            List<NhanVien> list = daoTaoMgr.getListNhanVien(maPhongBan);

            if (list.size() > 0) {
                list.forEach((E) -> {
                    model.addRow(new Object[]{E.getMaNhanVien(), E.getTenNhanVien(), true});
                    danhsach += E.getMaNhanVien() + ";";
                    areaDanhSach.setText(danhsach);
                });
                listNhanVien.setModel(model);
            }
            System.out.println(" danh sách"+danhsach);
        } catch (Exception e) {
            e.printStackTrace();
        }

        listNhanVien.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (lse.getValueIsAdjusting()) {
                    return;
                }
                int index = listNhanVien.getSelectedRow();
                if (index >= 0) {
                    String maNhanVien = listNhanVien.getValueAt(index, 0).toString();
                    boolean daotao = (boolean) listNhanVien.getValueAt(index, 2);
                    System.out.println(maNhanVien + "dao tao" + daotao);
                    areaDanhSach.setText(getDanhSach(maNhanVien, daotao));

                }
            }
        });

    }

    public String getDanhSach(String maNhanVien, boolean daotao) {
        if (daotao == true) {
            if (!danhsach.contains(maNhanVien)) {
                danhsach += maNhanVien + ";";
            }
            System.out.println("get danh sach" + danhsach);
        } else {
            int index = danhsach.indexOf(maNhanVien);
            System.out.println(index);
            if (index > 0) {
                String b = danhsach;
                danhsach = danhsach.substring(0, index);
                b = b.substring(index + 6);
                danhsach = danhsach + b;
                System.out.println(danhsach + "danh sach");
            } else if (index == 0) {
                danhsach = danhsach.substring(6);
            }

            System.out.println("xoanv" + danhsach);
        }
        return danhsach;
    }

    public boolean checkDaoTao() {
        if (txtMalop.getText().equals("")) {
            errMsg.setText("Không để trống mã lớp");
            txtMalop.requestFocus();
            return false;
        }
        if (txtMalop.getText().length() > 10) {
            errMsg.setText("Độ dài mã lớp không hợp lệ (<= 10 kí tự)");
            txtMalop.requestFocus();
            return false;
        }

        if (txtTenLop.getText().equals("")) {
            errMsg.setText("Không để trống tên lớp");
            txtTenLop.requestFocus();
            return false;
        }
        if (txtTenLop.getText().length() > 50) {
            errMsg.setText("Độ dài tên lớp không hợp lệ (<=50 kí tự)");
            txtTenLop.requestFocus();
            return false;
        }
        if (cmbPhongBan.getSelectedIndex() == 0) {
            errMsg.setText("Vui lòng chọn phòng ban");
            cmbPhongBan.requestFocus();
            return false;
        }
        if (txtTuNgay.getText().equals("")) {
            errMsg.setText("Không để trống từ ngày");
            txtTuNgay.requestFocus();
            return false;
        }
        if (txtDenNgay.getText().equals("")) {
            errMsg.setText("Không để trống đến ngày");
            txtDenNgay.requestFocus();
            return false;
        }
        boolean isDate = false;
        String date1 = txtTuNgay.getText();
        String datePattern = "\\d{1,2}-\\d{1,2}-\\d{4}";
        isDate = date1.matches(datePattern);
        if (isDate == false) {
            errMsg.setText("Không đúng định dạng ngày tháng");
            txtTuNgay.requestFocus();
            return isDate;
        }
        String date2 = txtDenNgay.getText();
        isDate = date2.matches(datePattern);
        if (isDate == false) {
            errMsg.setText("Không đúng định dạng ngày tháng");
            txtDenNgay.requestFocus();
            return isDate;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date tuNgay = null;
        Date denNgay = null;
        Date now = new Date();
        try {
            tuNgay = format.parse(date1);
            denNgay = format.parse(date2);
        } catch (ParseException ex) {
            Logger.getLogger(Daotao.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean before = tuNgay.before(denNgay);
        if (before == false) {
            errMsg.setText("Từ ngày phải trước đến ngày");
            txtDenNgay.requestFocus();
            return before;
        }
        errMsg.setText("");
        return true;
    }

    public void LamMoi() {
        txtMalop.setEditable(true);
        txtMalop.setText("");
        txtTenLop.setText("");
        areaDanhSach.setText("");
        txtTuNgay.setText("");
        txtDenNgay.setText("");
        txtGhiChu.setText("");
        cmbPhongBan.setSelectedIndex(0);
    }
}
