package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Properties;
import java.util.Scanner;

public class TextEditor extends JFrame {
    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setTitle("Text Editor");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Properties properties = new Properties();
        try {
            FileInputStream file = new FileInputStream("PROPERTY_SOURCE");
            properties.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        JButton saveButton = new JButton(new ImageIcon(properties.getProperty("save")));
        saveButton.setName("SaveButton");
        saveButton.addActionListener((ActionEvent event)->{

            final String classPath = field.getText();
            try(FileWriter writer = new FileWriter(new File(classPath))){

                writer.write(textArea.getText());

            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        });


        JButton loadButton = new JButton(new ImageIcon(properties.getProperty("load")));
        loadButton.setName("LoadButton");
        loadButton.addActionListener((ActionEvent event)->{
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showOpenDialog(null);
            File loadFile = jfc.getSelectedFile();
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

        JButton startSearch = new JButton(new ImageIcon(properties.getProperty("start")));
        startSearch.setName("StartSearchButton");

        JButton nextMatch = new JButton(new ImageIcon(properties.getProperty("next")));
        nextMatch.setName("NextMatchButton");

        JButton previousMatch = new JButton(new ImageIcon(properties.getProperty("previous")));
        previousMatch.setName("PreviousMatchButton");


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(field);
        topPanel.add(startSearch);
        topPanel.add(previousMatch);
        topPanel.add(nextMatch);


        add(topPanel, BorderLayout.NORTH);
        add(scrollBar, BorderLayout.CENTER);

        setupMenu();
        setVisible(true);
    }

    void setupMenu(){

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        JMenu searchMenu = new JMenu("Search");
        fileMenu.setName("MenuSearch");

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.setName("MenuLoad");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.setName("MenuSave");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setName("MenuExit");
        exitItem.addActionListener((ActionEvent event) -> {

            dispose();

        });


        JMenuItem startItem = new JMenuItem("Start Search");
        loadItem.setName("MenuStartSearch");
        JMenuItem prevItem = new JMenuItem("Previous search");
        saveItem.setName("MenuPreviousMatch");
        JMenuItem nextItem = new JMenuItem("Next match");
        exitItem.setName("MenuNextMatch");
        JMenuItem regExItem = new JMenuItem("Use regular expressions");
        exitItem.setName("MenuUseRegExp");

        searchMenu.add(startItem);
        searchMenu.add(prevItem);
        searchMenu.add(nextItem);
        searchMenu.add(regExItem);

        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        setJMenuBar(menuBar);

    }


}
