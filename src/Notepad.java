import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Notepad extends JDialog {
    private JPanel contentPane;
    private JButton открытьФайлButton;
    private JButton сохранитьButton;
    private JButton сохранитьКакButton;
    private JTextArea textArea1;
    private JLabel openFile;
    File file = null;
    boolean newFile = true;
    File currentDirectory = null;

    public Notepad() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(открытьФайлButton);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        открытьФайлButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileOpen = new JFileChooser(currentDirectory);
                String fileString = "";
                int result = fileOpen.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fileOpen.getSelectedFile();
                    try {
                        Scanner sc = new Scanner(file);
                        while (sc.hasNextLine()) {
                            fileString += sc.nextLine() + "\n";
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    textArea1.setText(fileString);
                    openFile.setText(fileOpen.getName(file));
                    newFile = false;
                    currentDirectory = fileOpen.getCurrentDirectory();
                }
            }
        });
        сохранитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (newFile) {
                    JFileChooser fc = new JFileChooser(currentDirectory);
                    int result = fc.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        file = fc.getSelectedFile();
                    }
                    currentDirectory = fc.getCurrentDirectory();
                }
                try {
                    FileWriter fw = new FileWriter(file);
                    fw.write(textArea1.getText());
                    fw.close();
                    openFile.setText(file.getName());
                    newFile = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        сохранитьКакButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fc = new JFileChooser(currentDirectory);
                int result = fc.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                }
                currentDirectory = fc.getCurrentDirectory();
                try {
                    FileWriter fw = new FileWriter(file);
                    fw.write(textArea1.getText());
                    fw.close();
                    openFile.setText(file.getName());
                    newFile = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Notepad dialog = new Notepad();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
