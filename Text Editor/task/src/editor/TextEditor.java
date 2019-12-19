package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class TextEditor extends JFrame {
    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setTitle("Text Editor");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setName("TextArea");

        Dimension dimension = new Dimension(300,20);
        JTextField field = new JTextField();
        field.setMinimumSize(dimension);
        field.setPreferredSize(dimension);
        field.setName("FilenameField");

        JScrollPane scrollBar = new JScrollPane(textArea);
        scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setName("ScrollPane");

        JButton saveButton = new JButton("Save");
        saveButton.setName("SaveButton");
        saveButton.addActionListener((ActionEvent event)->{

            final String classPath = field.getText();
            try(FileWriter writer = new FileWriter(new File("Text Editor\\task\\src\\"+ classPath))){

                writer.write(textArea.getText());

            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        });

        JButton loadButton = new JButton("Load");
        loadButton.setName("LoadButton");
        loadButton.addActionListener((ActionEvent event)->{
            final String classPath = field.getText();
            File loadFile = new File("Text Editor\\task\\src\\" + classPath);
            System.out.println(loadFile.getAbsolutePath());
            try(Scanner scanner = new Scanner(loadFile)){
                while (scanner.hasNext()){
                    textArea.setText(scanner.nextLine() + " ");
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        });

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(field);
        topPanel.add(saveButton);
        topPanel.add(loadButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollBar, BorderLayout.CENTER);

        setupMenu();
        setVisible(true);
    }

    void setupMenu(){

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.setName("MenuLoad");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setName("MenuSave");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setName("MenuExit");
        exitItem.addActionListener((ActionEvent event) -> {

            dispose();

        });

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

    }


}
