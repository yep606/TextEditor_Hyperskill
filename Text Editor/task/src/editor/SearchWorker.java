package editor;

import javax.swing.*;
import java.util.Map;
import java.util.regex.Pattern;

public class SearchWorker extends SwingWorker<String, Object> {

    TextEditor editor;

    public SearchWorker(TextEditor editor) {

        this.editor = editor;

    }


    @Override
    protected String doInBackground() throws Exception {
        editor.pattern = Pattern.compile(editor.getSearchField().getText(), Pattern.CASE_INSENSITIVE);
        editor.matcher = editor.pattern.matcher(editor.getTextArea().getText().toLowerCase());
        if (editor.isClicked) {

        } else {

            while (editor.matcher.find())
                editor.getSearch().put(editor.matcher.start(), editor.matcher.group().length());

            for (Map.Entry<Integer, Integer> item : editor.getSearch().entrySet()){

                System.out.println("Start: " + item.getKey() + " " + "Length: " + item.getValue());

            }

        }

        return null;

    }

    @Override
    protected void done() {
        super.done();
        if (editor.getSearch().size() != 0) {
            int start = (int) editor.getSearch().keySet().toArray()[0];
            int end = editor.getSearch().get(start);
            editor.getTextArea().setCaretPosition(start + end);
            editor.getTextArea().select(start, start + end);
            editor.getTextArea().grabFocus();
        }

    }
}
