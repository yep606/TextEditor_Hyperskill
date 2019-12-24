package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    public boolean isClicked = false;
    public Pattern pattern;
    public Matcher matcher;
    private SearchWorker worker;
    private JTextArea textArea;
    private JTextField searchField;
    private JScrollPane scrollBar;
    private JButton saveButton;
    private JButton loadButton;
    private JButton startSearch;
    private JButton nextMatch;
    private JButton previousMatch;
    private JCheckBox checkBox;
    private JPanel topPanel;
    private Properties properties;
    private static int increment = 0;
    private Map<Integer, Integer> search = new LinkedHashMap<>();

    public Map<Integer, Integer> getSearch() {
        return search;
    }

    public TextEditor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 450);
        setTitle("Text Editor");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setName("TextArea");
        textArea.setLineWrap(true);

        scrollBar = new JScrollPane(textArea);
        scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollBar.setName("ScrollPane");

        initialProperties();
        loadPanel();
        setupListeners();

        add(topPanel, BorderLayout.NORTH);
        add(scrollBar, BorderLayout.CENTER);

        setupMenu();
        setVisible(true);
    }

    private void initialProperties() {

        properties = new Properties();
        try {
            FileInputStream file = new FileInputStream("YOUR SOURCE");
            properties.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadPanel() {

        topPanel = new JPanel();

        saveButton = new JButton(new ImageIcon(properties.getProperty("save")));
        saveButton.setName("SaveButton");

        loadButton = new JButton(new ImageIcon(properties.getProperty("load")));
        loadButton.setName("LoadButton");

        Dimension dimension = new Dimension(300, 20);
        searchField = new JTextField();
        searchField.setMinimumSize(dimension);
        searchField.setPreferredSize(dimension);
        searchField.setName("FilenameField");

        startSearch = new JButton(new ImageIcon(properties.getProperty("start")));
        startSearch.setName("StartSearchButton");

        nextMatch = new JButton(new ImageIcon(properties.getProperty("next")));
        nextMatch.setName("NextMatchButton");

        previousMatch = new JButton(new ImageIcon(properties.getProperty("previous")));
        previousMatch.setName("PreviousMatchButton");

        checkBox = new JCheckBox("Use regex");
        checkBox.setName("UseRegExCheckbox");

        topPanel.setLayout(new FlowLayout());
        topPanel.add(saveButton);
        topPanel.add(loadButton);
        topPanel.add(searchField);
        topPanel.add(startSearch);
        topPanel.add(previousMatch);
        topPanel.add(nextMatch);
        topPanel.add(checkBox);

    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JTextField getSearchField() {
        return searchField;
    }


    private void setupListeners() {

        saveButton.addActionListener((ActionEvent event) -> {

            final String classPath = searchField.getText();
            try (FileWriter writer = new FileWriter(new File(classPath))) {

                writer.write(textArea.getText());

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        loadButton.addActionListener((ActionEvent event) -> {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showOpenDialog(null);
            File loadFile = jfc.getSelectedFile();
            System.out.println(loadFile.getAbsolutePath());
            try (Scanner scanner = new Scanner(loadFile)) {
                while (scanner.hasNext()) {
                    textArea.setText(scanner.nextLine() + " ");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });

        nextMatch.addActionListener((ActionEvent event) -> {
            if (increment < search.size() - 1 ){

                int start = (int) search.keySet().toArray()[++increment];
                int end = search.get(start);
                textArea.setCaretPosition(start + end);
                textArea.select(start, start + end);
                textArea.grabFocus();

            }

            else
                System.out.println("error");

        });

        startSearch.addActionListener((ActionEvent event) -> {

            worker = new SearchWorker(this);
            worker.execute();

        });

        checkBox.addActionListener((ActionEvent event) -> isClicked = !isClicked
        );


    }

    private void setupMenu() {

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
        exitItem.addActionListener((ActionEvent event) -> dispose());

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
