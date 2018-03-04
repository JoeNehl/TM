import java.io.*;
import java.util.*;

public class CLInterface {

	public static void main(String[] args) {
		CLInterface cl = new CLInterface();
		cl.appMain(args);
	}

	void appMain(String[] args) {
		int count = args.length;
		if (count >4) {
			count = 4;
		}
		String[] cmdln = {"", "", "", ""};
		for (int i=0;i<count;i++) {
			cmdln[i]=args[i];
		}
		HashMap<String, TMModel> taskMap = null;
	      try
	      {
	         FileInputStream fis = new FileInputStream("taskMap.ser"); // opens a .ser file for data to be written into
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         taskMap = (HashMap) ois.readObject();
	         ois.close();
	         fis.close();
	      } catch(IOException ioe) {
	         taskMap = new HashMap<String, TMModel>();
	      } catch(ClassNotFoundException c) {
	    	  c.printStackTrace();
	    	  return;
	      }
		String cmd = cmdln[0].toLowerCase();
		String data = cmdln[1].toLowerCase();
		String des = cmdln[2].toLowerCase();
		String size = cmdln[3].toLowerCase();
		switch (cmd) { //switch statement for the command supplied by the user (in arg 0)
		case "start":
			if(taskMap.containsKey(data)){
				taskMap.get(data).startTask(data);
			} else {
				taskMap.put(data,new TMModel(data));
			}
			break;
		case "stop":
			if(taskMap.containsKey(data)){
				taskMap.get(data).stopTask(data);
			} else {
				System.out.println("That task doesn't exist; did you want to create it?");
			}
			break;
		case "describe":
			if(taskMap.containsKey(data)){
					taskMap.get(data).describeTask(des, size);
			} else {
				if (size.equals("xs") || size.equals("s") || size.equals("m") || size.equals("l") ||size.equals("xl")||size.equals("")) {
					taskMap.put(data,new TMModel(data, des, size));
				} else {
					System.out.println("Unexpected value in size, please enter in xs, s, m, l or xl");
				}
			}
			break;
		case "size": 
			if(taskMap.containsKey(data)){
				taskMap.get(data).sizeTask(data, des);
			} else {
				System.out.println("That task doesn't exist; did you want to create it?");
			}
			break;
		case "delete":
			if (taskMap.containsKey(data)) {
				taskMap.remove(data);
			}else if (data == "") {
				taskMap.clear();
			}else {
				System.out.println("That task doesn't exist; you cannot delete it");
			}
			break;
		case "rename":
			if (taskMap.containsKey(des)) {
				System.out.println("The task name you wish to change to already exists; enter in another name");
			} else {
				if (taskMap.containsKey(data)) {
					taskMap.put(des, taskMap.remove(data));
					taskMap.get(des).renameTask(data, des);
				} else {
					System.out.println("That task doesn't exist; did you want to create it?");
				}
			}
			break;
		case "summary":
			if(taskMap.containsKey(data)) {
				String tmp = taskMap.get(data).taskElapsedTime(data);
				String siz = taskMap.get(data).taskSize(data);
				String Des = taskMap.get(data).taskDescription(data);
				System.out.println("Time: " + tmp + "\nSize: " + siz + "\nDescription: " + ((Des!=null)?(Des):("")) + "\n\n");
			} else if (data == "") {
				for(TMModel task: taskMap.values()){
				String tmp = task.taskElapsedTime(data);
				String siz = task.taskSize(data);
				String Des = task.taskDescription(data);
				System.out.println("Time: " + tmp + "\nSize: " + siz + "\nDescription: " + ((Des!=null)?(Des):("")) + "\n\n");
				}
			} else {
				System.out.println("That task doesn't exist; did you want to create it?");
			}
			break;
		default:
			System.out.println("Make sure you are using the program properly.");
			break;
		}
		try {
			FileOutputStream fos = new FileOutputStream("taskMap.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(taskMap);
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
