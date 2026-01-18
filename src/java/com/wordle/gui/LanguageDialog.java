package com.wordle.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LanguageDialog extends JDialog {
    
    private String selectedPath = null; // Will store the result

    public LanguageDialog() {
        setTitle("Welcome / Witaj / Bienvenido / Willkommen");
        setModal(true);
        setSize(1000, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Choose your language", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBorder(new EmptyBorder(20, 0, 20, 0)); // Add padding
        add(header, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 20, 0));
        buttonPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        buttonPanel.add(createOptionButton("English", "assets/flags/uk.png", "assets/dictionary_en_easy.txt"));
        buttonPanel.add(createOptionButton("EnglishHARD", "assets/flags/uk.png", "assets/dictionary_en_hard.txt"));
        buttonPanel.add(createOptionButton("Polski", "assets/flags/pl.png", "assets/dictionary_pl.txt"));
        buttonPanel.add(createOptionButton("Español", "assets/flags/es.png", "assets/dictionary_es.txt"));
        buttonPanel.add(createOptionButton("Deutsch", "assets/flags/de.png", "assets/dictionary_de.txt"));

        add(buttonPanel, BorderLayout.CENTER);
        
        JLabel footer = new JLabel("Wordle Clone v1.0", SwingConstants.CENTER);
        footer.setBorder(new EmptyBorder(10, 0, 10, 0));
        footer.setForeground(Color.GRAY);
        add(footer, BorderLayout.SOUTH);
    }

    private JButton createOptionButton(String language, String iconPath, String dictPath) {
        JButton btn = new JButton(language);
        
        btn.setFont(new Font("Arial", Font.PLAIN, 18));
        btn.setFocusPainted(false);
        
        ImageIcon icon = new ImageIcon(iconPath);
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(img));
            btn.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
        }

        btn.addActionListener((ActionEvent e) -> {
            this.selectedPath = dictPath;
            dispose();
        });

        return btn;
    }

    public String getSelectedDictionary() {
        return selectedPath;
    }
}