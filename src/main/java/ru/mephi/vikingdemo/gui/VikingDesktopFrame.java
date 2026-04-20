package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;

import javax.swing.*;
import java.awt.*;

public class VikingDesktopFrame extends JFrame {

    private final VikingService vikingService;
    private final VikingTableModel tableModel;

    public VikingDesktopFrame(VikingService vikingService, VikingTableModel tableModel) {
        this.vikingService = vikingService;
        this.tableModel = tableModel;

        setTitle("Viking Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(1000, 420));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel header = new JLabel("Viking Demo", SwingConstants.CENTER);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 18f));
        add(header, BorderLayout.NORTH);

        JTable vikingTable = new JTable(tableModel);
        vikingTable.setRowHeight(28);
        add(new JScrollPane(vikingTable), BorderLayout.CENTER);

        JButton createButton = new JButton("Create random viking");
        createButton.addActionListener(event -> onCreateViking());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void onCreateViking() {
        vikingService.createRandomViking();
    }

    public void addNewViking(Viking viking) {
        tableModel.addViking(viking);
    }
}