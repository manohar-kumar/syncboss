import static java.nio.file.StandardCopyOption.*;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;
import java.nio.file.attribute.*;
import java.util.*;
import java.lang.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JFileChooser.*;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory; 
import javax.swing.border.*;
class GroupUI extends JFrame{
	public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 400; 
    private DrawCanvas canvas;
	
	
	public GroupUI(String Name) {
		//
		canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setLayout(new BorderLayout());
        //
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        //
        //Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        //
        JPanel GroupInfo = new JPanel();
        GroupInfo.setLayout(new BoxLayout(GroupInfo,BoxLayout.Y_AXIS));
        GroupInfo.setPreferredSize(new Dimension(150,400));
        GroupInfo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        GroupInfo.setBackground(Color.white);
        
        
        JPanel FriendInfo = new JPanel();
        FriendInfo.setLayout(new BoxLayout(FriendInfo,BoxLayout.Y_AXIS));
        FriendInfo.setPreferredSize(new Dimension(150,400));
        FriendInfo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        FriendInfo.setBackground(Color.white);
        
        
        JPanel GroupPage = new JPanel();
        GroupPage.setLayout(new BoxLayout(GroupPage,BoxLayout.Y_AXIS));
        GroupPage.setPreferredSize(new Dimension(300,400));
        GroupPage.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        // ActivityInfo.setBackground(Color.white);
        
        canvas.add(GroupInfo,BorderLayout.WEST);
        canvas.add(GroupPage,BorderLayout.CENTER);
        canvas.add(FriendInfo,BorderLayout.EAST);
        //				
			try{	
			ArrayList<String> friends = new ArrayList<String>();
			friends=new Final(Paths.get("C:\\Newfolder"),true).my_friends();
			String line;
			for(int i=0;i<friends.size();i++){
			line = friends.get(i);
			if(!line.equals("")){
				JPanel Friend =new JPanel();
				Friend.setPreferredSize(new Dimension(150,20));
				JLabel label = new JLabel(line);
				label.setFont(new Font("Serif", Font.PLAIN, 14));
				Friend.add(label);
				FriendInfo.add(Friend);
				}	
			}
		}
			catch(IOException e){}
			
			
			JLabel	GN=new JLabel(Name);
			GroupPage.add(GN);
			//JButton Home	
			//
			JPanel createGroup=new JPanel();
			JTextField GroupName=new JTextField(10);
			JButton create=new JButton("+ CreateGroup");
			GroupInfo.add(createGroup);
			createGroup.add(GroupName);
			createGroup.add(create);
			create.addActionListener(new ActionListener(){
					//action
					 @Override
					public void actionPerformed(ActionEvent evt) {
					try {
					String groupName=GroupName.getText();
					/*BufferedWriter bw=new BufferedWriter(new FileWriter("Groups.txt",true));
					bw.write(groupName);
					bw.write("\r\n");
					bw.close();*/
					String my_name=new Final(Paths.get("C:\\Newfolder"),true).myname;
					new Final(Paths.get("C:\\Newfolder"),true).create_group(groupName,my_name);
					

					//FriendInfo.add(new JButton(friendName));
					JPanel group =new JPanel();
					group.setPreferredSize(new Dimension(150,20));
					JLabel label = new JLabel(groupName);
					label.setFont(new Font("Serif", Font.PLAIN, 14));
					group.add(label);
				
					GroupInfo.add(group);
					GroupInfo.revalidate();
					}
					catch(IOException e){}
					}
					});
			try{	
			/*BufferedReader br = new BufferedReader(new FileReader("Groups.txt")); 
			for(String line = br.readLine(); 
			(line != null && !(line.isEmpty()) && !(line.trim().isEmpty())); */
			ArrayList<String> groups = new ArrayList<String>();
			groups=new Final(Paths.get("C:\\Newfolder"),true).my_groups();
			String line2;
			for(int i=0;i<groups.size();i++){

			line2 = groups.get(i);
			if(!line2.equals("")){
				JPanel group =new JPanel();
					group.setPreferredSize(new Dimension(150,20));
					JLabel label = new JLabel(line2);
					label.setFont(new Font("Serif", Font.PLAIN, 14));
					group.add(label);
					GroupInfo.add(group);
				}	
			}
		}
			catch(IOException e){}
					
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle the CLOSE button
		setTitle("SyncNow");
		pack();           // pack all the components in the JFrame
		setVisible(true); // show it
		//
		
		}
	
	
	
	
	class DrawCanvas extends JPanel {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         setBackground(Color.white);
         }
      }
	}
public class UI extends JFrame{
	public static final int CANVAS_WIDTH = 600;
    public static final int CANVAS_HEIGHT = 400;
    
    private DrawCanvas canvas;
	public static void copyFolder(Path src, Path dest)
    	throws IOException{
    	
    	if(Files.isDirectory(src)){
 
    	//if directory not exists, create it
    	if(!Files.exists(dest)){
    	   Files.createDirectory(dest);
    	  
    	}
 
    	//list all the directory contents

    	try (DirectoryStream<Path> stream = Files.newDirectoryStream(src)) {
    	for (Path file: stream) {
       	   	
    	   //construct the src and dest file structure
    	   Path srcFile = src.resolve(file);
               String s=file.getFileName().toString();
               String temp=s.substring(s.indexOf("_",0)+1);

    	   Path destFile = dest.resolve(Paths.get(temp));
    	  
    	   //recursive copy
    	   copyFolder(srcFile,destFile);
    	}
    	}
    	catch (IOException | DirectoryIteratorException x) {
   
    	
	}
	}
	
	else{

    	Files.copy(src,dest,StandardCopyOption.REPLACE_EXISTING);
    	}
    }
	public UI() {
		//
		canvas = new DrawCanvas();
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        canvas.setLayout(new BorderLayout());
        //
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        //
        //Border raisedetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        //
        JPanel GroupInfo = new JPanel();
        GroupInfo.setLayout(new BoxLayout(GroupInfo,BoxLayout.Y_AXIS));
        GroupInfo.setPreferredSize(new Dimension(150,400));
        GroupInfo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        GroupInfo.setBackground(Color.white);
        
        
        JPanel FriendInfo = new JPanel();
        FriendInfo.setLayout(new BoxLayout(FriendInfo,BoxLayout.Y_AXIS));
        FriendInfo.setPreferredSize(new Dimension(150,400));
        FriendInfo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        FriendInfo.setBackground(Color.white);
        
        
        JPanel ActivityInfo = new JPanel();
        ActivityInfo.setLayout(new BoxLayout(ActivityInfo,BoxLayout.Y_AXIS));
        ActivityInfo.setPreferredSize(new Dimension(300,400));
        ActivityInfo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        // ActivityInfo.setBackground(Color.white);
        
        canvas.add(GroupInfo,BorderLayout.WEST);
        canvas.add(ActivityInfo,BorderLayout.CENTER);
        canvas.add(FriendInfo,BorderLayout.EAST);
        //
        
        JPanel feld = new JPanel(new FlowLayout());
		JPanel feld2 = new JPanel(new FlowLayout());
        JButton AddFriend=new JButton("Add Friend");
		JTextField BrowseFriend=new JTextField(20);
		JButton select=new JButton("upload");
		JTextField Browse=new JTextField(20);
			feld2.add(BrowseFriend);
			feld2.add(AddFriend);
			feld.add(Browse);
			feld.add(select);
			
			ActivityInfo.add(feld2);
			ActivityInfo.add(feld);
		select.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt) {
			try {
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(null);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			String frien = Browse.getText();
			System.out.println(frien);
			String path = chooser.getSelectedFile().getAbsolutePath();
			System.out.println(path);
			String nameoffile = chooser.getSelectedFile().getName();
			System.out.println(nameoffile);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			copyFolder(Paths.get(path),Paths.get("C:\\NewFolder\\"+frien+"\\" + nameoffile));
			}
			}
			}
				catch(Exception e){}	
					}
					});
					
			AddFriend.addActionListener(new ActionListener(){
					//action
					 @Override
					public void actionPerformed(ActionEvent evt) {
					try {
					String friendName=BrowseFriend.getText();
					
					JTextField Friend =new JTextField();
					Friend.setEditable(false);
					Friend.setText(friendName);
					Friend.setFont(new Font("Serif", Font.PLAIN, 14));
					FriendInfo.add(Friend);
					FriendInfo.revalidate();
					new Final(Paths.get("C:\\Newfolder"),true).make_friend(friendName);

					/*BufferedWriter bw=new BufferedWriter(new FileWriter("Friends.txt",true));
					bw.write(friendName);
					bw.write("\r\n");
					bw.close();	*/
					//FriendInfo.add(new JButton(friendName));
					
					}
					catch(IOException e){}
					}
					});
			try{	
			/*BufferedReader br = new BufferedReader(new FileReader("Friends.txt")); 
			for(String line = br.readLine(); 
			(line != null && !(line.isEmpty()) && !(line.trim().isEmpty()));*/
			ArrayList<String> friends = new ArrayList<String>();
			friends=new Final(Paths.get("C:\\Newfolder"),true).my_friends();
			String line;
			for(int i=0;i<friends.size();i++){

			line = friends.get(i);
			if(!line.equals("")){
				JTextField Friend =new JTextField();
					Friend.setEditable(false);
					Friend.setText(line);
					Friend.setFont(new Font("Serif", Font.PLAIN, 14));
					FriendInfo.add(Friend);
				}
				}	
			}
			catch(IOException e){}
			
			
					
			//
			JPanel createGroup=new JPanel();
			createGroup.setPreferredSize(new Dimension(150,100));
			JTextField GroupName=new JTextField(10);
			JButton create=new JButton("+ CreateGroup");
			GroupInfo.add(createGroup);
			createGroup.add(GroupName);
			createGroup.add(create);
			create.addActionListener(new ActionListener(
				){
					//action
					 @Override
					public void actionPerformed(ActionEvent evt) {
					try {
					String groupName=GroupName.getText();
					String my_name=new Final(Paths.get("C:\\Newfolder"),true).myname;
					JTextField group =new JTextField();
					group.setEditable(false);
					group.setText(groupName);
					group.setFont(new Font("Serif", Font.PLAIN, 14));
					GroupInfo.add(group);
					new Final(Paths.get("C:\\Newfolder"),true).create_group(groupName,my_name);
					/*BufferedWriter bw=new BufferedWriter(new FileWriter("Groups.txt",true));
					bw.write("\r\n");
					bw.write(groupName);
					bw.close();*/
					//FriendInfo.add(new JButton(friendName));
					
					group.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
			try {
				new GroupUI(groupName);
			}
			
				catch(Exception exp){}	
					}
					public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
					});
					GroupInfo.add(group);
					GroupInfo.revalidate();
					}
					catch(IOException ex){}
					}
					});
			try{	
			/*BufferedReader br = new BufferedReader(new FileReader("Groups.txt")); 
			for(String line = br.readLine(); 
			(line != null && !(line.isEmpty()) && !(line.trim().isEmpty())); 
			line = br.readLine()){*/
	ArrayList<String> groups = new ArrayList<String>();
			groups=new Final(Paths.get("C:\\Newfolder"),true).my_groups();
			String line;
			for(int i=0;i<groups.size();i++){

			line = groups.get(i);
			if(!line.equals("")){
				JTextField group =new JTextField();
					group.setEditable(false);
					group.setText(line);
					group.setFont(new Font("Serif", Font.PLAIN, 14));
					GroupInfo.add(group);
					String s=line;
					group.addMouseListener(new MouseListener(){
						
			@Override
			
			public void mouseClicked(MouseEvent e) {
			try {
				new GroupUI(s);
			}
			
				catch(Exception ex){}	
					}
					public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }
					});
					GroupInfo.add(group);
				}	
			}
		}
			catch(IOException e){}
					
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle the CLOSE button
		setTitle("SyncNow");
		pack();           // pack all the components in the JFrame
		setVisible(true); // show it
		//
		
		}
	
	public static void mai() {
 
		Timer t = new Timer();
t.scheduleAtFixedRate(
    new TimerTask()
    {
        public void run()
        {
BufferedReader br = null;
 		try {
 			String sCurrentLine;
 		//	br = new BufferedReader(new FileReader("C:\\Users\\kethans\\Desktop\\jar\\Friends.txt"));
 			ArrayList<String> friends = new ArrayList<String>();
			friends=new Final(Paths.get("C:\\Newfolder"),true).my_friends();
 			for(int i=0;i<friends.size();i++){
 				sCurrentLine = friends.get(i);
			File f = new File("C:\\NewFolder\\"+sCurrentLine);
	f.mkdir();
}
String sCurrent;
 			//br = new BufferedReader(new FileReader("C:\\Users\\kethans\\Desktop\\jar\\Groups.txt"));
ArrayList<String> groups = new ArrayList<String>();
groups=new Final(Paths.get("C:\\Newfolder"),true).my_groups();
 			for(int i=0;i<groups.size();i++) {
 				sCurrent = groups.get(i);
			File f = new File("C:\\NewFolder\\"+sCurrent);
	f.mkdir();
}
} catch (IOException e) {
			e.printStackTrace();
		}
	finally {
		try {
			if (br != null)br.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}            
        }
    },
    0,      // run first occurrence immediatetly
    2000);
	}
	
	
	class DrawCanvas extends JPanel {
      @Override
      public void paintComponent(Graphics g) {
         super.paintComponent(g);
         setBackground(Color.white);
         }
      }
      
    public static void main(String[] args) {
		
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
		 mai();
		 try{

		new Final(Paths.get("C:\\Newfolder"),true).processEvents(Paths.get("C:\\server"));
	}
	 catch (IOException x) {
                        System.out.println(x);
                    }
            new UI(); // Let the constructor do the job
         }
      });
   }
   
	}
