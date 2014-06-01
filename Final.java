import static java.nio.file.StandardCopyOption.*;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import static java.nio.file.LinkOption.*;

import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;
import java.lang.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class Final {
	public static String myname="user1";
    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private final boolean recursive;
    private boolean trace = false;

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    /**
     * Register the given directory with the WatchService
     */
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

               Path destFile = dest.resolve(file.getFileName());
              
               //recursive copy
               copyFolder(srcFile,destFile);
            }
        }
            catch (IOException | DirectoryIteratorException x) {
   
            System.err.println(x);
                    }
                }
            
                else{
            //if file, then copy it
            //Use bytes stream to support all file types
            Files.copy(src,dest,StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied from " + src + " to " + dest);
        }
    }



    private void register(Path dir) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    /**
     * Register the given directory, and all its sub-directories, with the
     * WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Creates a WatchService and registers the given directory
     */

    Final(Path src, boolean recursive) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();
        this.recursive = recursive;

        if (recursive) {
            System.out.format("Scanning %s ...\n", src);
            registerAll(src);
            System.out.println("Done.");
        } else {
            register(src);
        }

        // enable trace after initial registration
        this.trace = true;
    }

    /**
     * Process all events for keys queued to the watcher
     */
    void processEvents(Path dir) {
        Timer t = new Timer();
		t.scheduleAtFixedRate(
    	new TimerTask()
    {
    	public void run()
    	{
            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path src = keys.get(key);
            if (src == null) {
                System.err.println("WatchKey not recognized!!");
                
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                // TBD - provide example of how OVERFLOW event is handled
                    if (kind == OVERFLOW) {
                   
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = src.resolve(name);
                String s1,s2;
                s1=child.toString().substring(5);//code_red: to remove initial /test from pathname_may not be needed in final code
                int b=s1.lastIndexOf("\\");
                if (Files.isDirectory(child, NOFOLLOW_LINKS)){
                    s2=s1;
                }
                else {
                s2=s1.substring(0,b+1)+myname+"_"+s1.substring(b+1);}
                Path man=dir.resolve(Paths.get(s2));
               
                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child);
                try{

                 copyFolder(child,man);
             		}
                 catch (IOException x) {
                        System.out.println(x);
                    }
                if (kind == ENTRY_DELETE){
                    try {
                        System.out.println(dir.resolve(Paths.get(child.toString().substring(5)).toString()));
                       if (Files.exists(man)){ Files.delete(man);}
                        if (Files.exists(dir.resolve(Paths.get(child.toString().substring(5)).toString()))){
                       Files.delete(dir.resolve(Paths.get(child.toString().substring(5)).toString()));
                        }
                    }
                    
                    catch (IOException x) {
                        System.out.println(x);
                    }
                }

                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readbale
                    }
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);

                // all directories are inaccessible
                if (keys.isEmpty()) {
                   // break;
                }
            }
        
    }
	}	,
    0,2000);
}

	public static void add_user(String my_name){
		try{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
 			String host="jdbc:mysql://localhost:3306/ganga";
			String username="root";
			String password="";
			Connection con = DriverManager.getConnection( host, username, password );
			String change = "'" + my_name + "'";
			String query1 = "insert users(name) values("+ change + ")";
			PreparedStatement q1 = con.prepareStatement(query1);
			q1.executeUpdate();
			
		}
		catch(SQLException err){
			System.out.println(err.getMessage());
		}
	}
	

	public static void make_friend(String friend_name){
		try{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
 			String host="jdbc:mysql://localhost:3306/ganga";
			String username="root";
			String password="";
			Connection con = DriverManager.getConnection( host, username, password );
			
			String change = "'" + myname + "'";
			String s = new String();
			String query = "select * from users where name = " + change;
			PreparedStatement q = con.prepareStatement(query);
			ResultSet rs = q.executeQuery();
			while(rs.next()){
				s = rs.getString(3);
			}
			
			String[] divide = s.split(" ");
			String[] naya = new String[divide.length+1];
			for(int i=0;i<divide.length;i++){
				naya[i]=divide[i];
			}
			naya[divide.length]=friend_name;
			String key = new String();
			key = "'";
			for(int i=0;i<naya.length;i++){
				key = key + " " + naya[i] ;
			}
			key = key + "'";
			
			/////////////////////////////////////////////////////////////////
			String query1 = "Update users set friends = " + key + "where name = " + change ;
			PreparedStatement q1 = con.prepareStatement(query1);
			q1.executeUpdate();
			
		}
		catch(SQLException err){
			System.out.println(err.getMessage());
		}
		
	}

/////////////////////////////////////////////////////////////////

public static void create_group(String group_name,String admin){
		try{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
 			String host="jdbc:mysql://localhost:3306/ganga";
			String username="root";
			String password="";
			Connection con = DriverManager.getConnection( host, username, password );
			
			String new_group_name = "'" + group_name + "'";
			String new_admin = "'" + admin + "'";
			String query1 = "insert into groups(name,admin) values (" +  new_group_name + "," + new_admin + ")";                     
			PreparedStatement q1 = con.prepareStatement(query1);
			q1.executeUpdate();
			
			///adding group to mygroups list
		
			String query3 = "select * from users where name = " + new_admin;
			PreparedStatement q3 = con.prepareStatement(query3);
			ResultSet res = q3.executeQuery();
			String old_groups = new String();
			while(res.next()){
				old_groups = res.getString(6);
			}
			String[] divide2 = old_groups.split(" ");
			String[] new_groups = new String[divide2.length+1];
			for(int i=0;i<divide2.length;i++){
				new_groups[i] = divide2[i];
			}
			new_groups[divide2.length]=group_name;
			String key2 = new String();
			key2 = "'" + new_groups[0];
			for(int i=1;i<new_groups.length;i++){
				key2 = key2 + " " + new_groups[i] ;
			}
			key2 = key2 + "'";
			String query4 = "update users set myGroups = " + key2 + "where name = " + new_admin ;
			PreparedStatement q4 = con.prepareStatement(query4);
			q4.executeUpdate();	
		}
		catch(SQLException err){
			System.out.println(err.getMessage());
		}
		
	}

public static ArrayList<String> my_groups(){
		try{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
 			String host="jdbc:mysql://localhost:3306/ganga";
			String username="root";
			String password="";
			Connection con = DriverManager.getConnection( host, username, password );
			String change = "'" + myname + "'";
			String query1 = "select * from users where name = " + change;
			PreparedStatement q1 = con.prepareStatement(query1);
			ResultSet rs = q1.executeQuery();
			String list = new String();
			while(rs.next()){
				list = rs.getString(6);
			}
			String[] k = list.split(" ");
			ArrayList<String> answer = new ArrayList<String>();
			for(int i=0;i<k.length;i++){
				answer.add(k[i]);
			}
			return answer;
		}
		catch(SQLException err){
			System.out.println(err.getMessage());
		}
		ArrayList<String> b = new ArrayList<String>();
		return b;
		
	}

	public static ArrayList<String> my_friends(){
		ArrayList<String> b = new ArrayList<String>();
		try{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
 			String host="jdbc:mysql://localhost:3306/ganga";
			String username="root";
			String password="";
			Connection con = DriverManager.getConnection( host, username, password );
			String change = "'" + myname + "'";
			String query1 = "select * from users where name = " + change;
			PreparedStatement q1 = con.prepareStatement(query1);
			ResultSet rs = q1.executeQuery();
			String s = new String();
			while(rs.next()){
				s = rs.getString(3);
			}
			String[] divide = s.split(" ");
			ArrayList<String> a  = new ArrayList<String>();
			for(int i=0;i<divide.length;i++){
				a.add(divide[i]);
			}
			return a;
		}
		catch(SQLException err){
			System.out.println(err.getMessage());
		}
		return b;
	}





	public static void main(String[] args) {
		ArrayList<String> my_req=new ArrayList<String>();
		//my_req=my_request(myname);

			
	}
}
