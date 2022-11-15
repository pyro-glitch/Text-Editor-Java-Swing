import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Window extends JFrame implements ActionListener {
    JMenuBar menubar =new JMenuBar();
    JMenu file=new JMenu(" File ");

    JMenuItem newfile =new JMenuItem("New File");
    JMenuItem savefile =new JMenuItem("Save File");

    JMenu font=new JMenu(" Font ");

    JMenuItem incFontSize =new JMenuItem(" Size + ");
    JMenuItem decFontSize =new JMenuItem(" Size - ");

    JTextArea textArea = new JTextArea();
    JScrollPane scrollPane =new JScrollPane(textArea);

    int FONT_SIZE=32;       //default font size

    Window(){
        setTitle("Text Editor");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800,600));
        setLayout(new CardLayout());
        setResizable(true);

        initMenuBox();
        initTextBox();

        setJMenuBar(menubar);
        add(scrollPane);

        pack();
        setVisible(true);
    }

    void initMenuBox(){
        newfile.addActionListener(this);
        file.add(newfile);
        savefile.addActionListener(this);
        file.add(savefile);

        incFontSize.addActionListener(this);
        font.add(incFontSize);
        decFontSize.addActionListener(this);
        font.add(decFontSize);
        file.add(font);

        menubar.add(file);

    }

    void initTextBox(){
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //default font
        textArea.setFont(new Font("Ariel",Font.BOLD,FONT_SIZE));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==newfile){textArea.setText("");}
        else if(e.getSource()==savefile){saveAs();}
        else if(e.getSource()==incFontSize){textArea.setFont(new Font("Ariel",Font.BOLD,++FONT_SIZE));}
        else if(e.getSource()==decFontSize){textArea.setFont(new Font("Ariel",Font.BOLD,--FONT_SIZE));}

    }

    void saveAs(){

        final JFileChooser saveAsFileChooser = new JFileChooser();
        saveAsFileChooser.setDialogTitle("Sava as ");
        saveAsFileChooser.setApproveButtonText("Save");

        FileNameExtensionFilter extensionFilter1 = new FileNameExtensionFilter("Java File", "java");
        FileNameExtensionFilter extensionFilter2 = new FileNameExtensionFilter("Text File", "txt");

        saveAsFileChooser.setFileFilter(extensionFilter1);
        saveAsFileChooser.setFileFilter(extensionFilter2);

        int actionDialog = saveAsFileChooser.showOpenDialog(this);
        if (actionDialog != JFileChooser.APPROVE_OPTION) {  return;  }

        // !! File fileName = new File(SaveAs.getSelectedFile() + ".txt");
        File file = saveAsFileChooser.getSelectedFile();

        String fileTypeChosen=saveAsFileChooser.getFileFilter().getDescription();
        String extension="";
        if ("Java File".equals(fileTypeChosen)) {
            extension = ".java";
        } else {
            extension = ".txt";
        }


        if (!file.getName().endsWith(extension)) {
            file = new File(file.getAbsolutePath() + extension);
        }

        BufferedWriter outFile = null;
        try {
            outFile = new BufferedWriter(new FileWriter(file));

            textArea.write(outFile);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (outFile != null) {
                try {
                    outFile.close();
                } catch (IOException e) {}
            }
        }
    }



}

public class Main {
    public static void main(String[] args) {

        new Window();


    }
}