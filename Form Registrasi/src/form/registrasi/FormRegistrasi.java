/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package form.registrasi;

/**
 *
 * @author This PC
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author This PC
 */
public class FormRegistrasi {

    private JFrame frame;
    private JPanel panel;
    private JLabel label1, label2, labelTotalSKS, labelJudul;
    private JTextField text1, text2;
    private JButton button1, button2;
    private JTable table;
    private DefaultTableModel model;
    private int totalSKS = 0;

    public FormRegistrasi() {
        frame = new JFrame("Form Registration");
        frame.setSize(600, 200);
        frame.setLayout(new BorderLayout());
        
        // Panel baru untuk menampilkan judul
        JPanel panelJudul = new JPanel();
        panelJudul.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Menambahkan label Total SKS ke panel baru
        labelJudul = new JLabel("Form Registration");
        panelJudul.add(labelJudul);

        frame.add(panelJudul, BorderLayout.NORTH);

        panel = new JPanel(new GridLayout(3, 2, 20, 20));

        label1 = new JLabel("Subject Name");
        panel.add(label1);

        text1 = new JTextField(12);
        text1.setColumns(10);
        panel.add(text1);

        label2 = new JLabel("SKS Credit");
        panel.add(label2);

        text2 = new JTextField(12);
        panel.add(text2);

        button1 = new JButton("Save");
        button2 = new JButton("Delete");
        panel.add(button1);
        panel.add(button2);

        table = new JTable();
        model = new DefaultTableModel();
        model.addColumn("Subject Name");
        model.addColumn("SKS Credit");
        table.setModel(model);

        frame.add(panel, BorderLayout.WEST);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel baru untuk menampilkan total SKS
        JPanel panelTotalSKS = new JPanel();
        panelTotalSKS.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Menambahkan label Total SKS ke panel baru
        labelTotalSKS = new JLabel("Total SKS: 0");
        panelTotalSKS.add(labelTotalSKS);

        frame.add(panelTotalSKS, BorderLayout.SOUTH);

        button1.addActionListener((ActionEvent e) -> {
            simpanData();
        });

        button2.addActionListener((ActionEvent e) -> {
            hapusData();
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void simpanData() {
    String subjectName = text1.getText().trim();
    String sksCreditText = text2.getText().trim();

    if (subjectName.isEmpty() || sksCreditText.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Semua field harus diisi!");
        return;
    }

    int sks;

    try {
        sks = Integer.parseInt(sksCreditText);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "SKS harus berupa angka!");
        return;
    }

    // Validasi Subject name tidak boleh sama dengan yang sudah ada 
    for (int i = 0; i < model.getRowCount(); i++) {
        String existingSubjectName = (String) model.getValueAt(i, 0);
        if (existingSubjectName.equalsIgnoreCase(subjectName)) {
            JOptionPane.showMessageDialog(null, "Subject name sudah ada dalam tabel!");
            return;
        }
    }

    // Validasi SKS harus bilangan bulat positif
    if (sks <= 0) {
        JOptionPane.showMessageDialog(null, "SKS harus bilangan bulat positif!");
        return;
    }

    // Validasi Jumlah seluruh SKS yang diambil tidak boleh lebih dari 20 SKS
    if (totalSKS + sks > 20) {
        JOptionPane.showMessageDialog(null, "Jumlah SKS tidak boleh lebih dari 20!");
        return;
    }

    // Menambahkan baris baru ke tabel
    model.addRow(new Object[]{subjectName, sks});

    // Scroll tabel ke bawah agar data yang baru ditambahkan terlihat
    table.scrollRectToVisible(table.getCellRect(model.getRowCount() - 1, 0, true));

    totalSKS += sks;
    labelTotalSKS.setText("Total SKS: " + totalSKS);

    // Reset input fields
    text1.setText("");
    text2.setText("");
}

    private void hapusData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Data belum dipilih!");
            return;
        }

        int sks = Integer.parseInt(model.getValueAt(selectedRow, 1).toString());

        totalSKS -= sks;
        labelTotalSKS.setText("Total SKS: " + totalSKS);

        model.removeRow(selectedRow);

        JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FormRegistrasi::new);
    }
}