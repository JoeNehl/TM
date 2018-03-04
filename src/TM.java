//Joseph Nehl
import java.io.*; // two files import the java utility and io libraries
import java.util.*;

public class TM {

	public static void main(String[] args) { // wrote this main like discussed in class, pass args to a different method
		TM tm = new TM();
		tm.appMain(args);
	}
	
	void appMain(String[] args) { //args are passed here to be used
		int count = args.length; //this checks to make sure that no more that 4 args are input and if less than three fixes the out of bounds error
		if (count >4) {
			count = 4;
		}
		String[] cmdln = {"", "", "", ""};// creates an array of empty strings
		for (int i=0;i<count;i++) {// puts args taken from command line into an array of strings
			cmdln[i]=args[i];
		}
		HashMap<String, Task> taskMap = null;
	      try
	      {
	         FileInputStream fis = new FileInputStream("taskMap.ser"); // opens a .ser file for data to be written into
	         ObjectInputStream ois = new ObjectInputStream(fis);
	         taskMap = (HashMap) ois.readObject();
	         ois.close();
	         fis.close();
	      } catch(IOException ioe) {
	         taskMap = new HashMap<String, Task>();
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
				taskMap.get(data).start();
			} else {
				taskMap.put(data,new Task(data));
			}
			break;
		case "stop":
			if(taskMap.containsKey(data)){
				taskMap.get(data).stop();
			} else {
				System.out.println("That task doesn't exist; did you want to create it?");
			}
			break;
		case "describe":
			if(taskMap.containsKey(data)){
					taskMap.get(data).describe(des, size);
			} else {
				if (size.equals("xs") || size.equals("s") || size.equals("m") || size.equals("l") ||size.equals("xl")) {
					taskMap.put(data,new Task(data, des, size));
				} else {
					System.out.println("Unexpected value in size, please enter in xs, s, m, l or xl");
				}
			}
			break;
		case "summary":
			if(taskMap.containsKey(data)){
				taskMap.get(data).summary();
			} else if(data == ""){
				for(Task task: taskMap.values()){
				task.summary();
				}
			} else {
				System.out.println("That task doesn't exist; did you want to create it?");
			}
			break;
		case "size": 
			if(taskMap.containsKey(data)){
				taskMap.get(data).taskSize(des);
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
					taskMap.get(des).rename(des);
				} else {
					System.out.println("That task doesn't exist; did you want to create it?");
				}
			}
			break;
		default:
			System.out.println("Make sure you are using the program properly.");
			break;
		}
		try {//this outputs to the .ser file and closes it
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

class Task implements Serializable {
	
	String description;
	String taskName;
	String taskSize;
	long totalTimeSpent;
	long startTime;
	long endTime;
	
	public Task(String taskName){// there are two different options, either start with a task and description or start with a task and start time
		this.taskName = taskName;//this is for time
		this.totalTimeSpent = 0;
		this.startTime = System.currentTimeMillis();
		this.endTime = -1;
		this.taskSize = "";
		}

	public Task(String taskName, String descript, String size){//this is for starting with description
		this.taskName = taskName;
		this.totalTimeSpent = 0;
		this.startTime = 0;
		this.description = descript;
		this.endTime = -1;
		this.taskSize = size;
	}
	
	void start() {//starts time
		if (this.startTime <= 0) {//checks to see if time has already started
			this.startTime = System.currentTimeMillis();
		} else {
			System.out.println("Task has already been started.");
		}
	}
	
	void stop() { //stops the task
		if (this.startTime <=0) { //if task isnt started, falls here
			System.out.println("Task was not started.");
		} else { //if task is started goes here
		long partialTime = 0;
		this.endTime=System.currentTimeMillis();
		partialTime = this.endTime - this.startTime;
		partialTime = partialTime/1000;
		this.totalTimeSpent = this.totalTimeSpent + partialTime;
		this.startTime = 0;
		this.endTime = -1;
		}
	}
	
	void describe(String des, String size) {
		if (this.description == null && des!= "") {
			this.description = des;
		} else if (this.description == null && des == "") {
		} else if (des != "") {
			this.description= this.description + "\n" + "             " + des;
		}
		if (checkSize(size)==true) {
			this.taskSize = size;
		}
	}
	
	void summary() {
		String tmp = timeOut(this.totalTimeSpent);
		System.out.println("Task: " + taskName + "\nTime: " + tmp + "\nSize: " + taskSize + "\nDescription: " + ((description!=null)?(description):("")) + "\n\n");
	}
	
	String timeOut(long totalTimeSpent) {
		long tmp = totalTimeSpent;
		long hr =tmp/3600;
		long min =(tmp%3600)/60;
		long sec =tmp%60;
		return (String.format("%02d:%02d:%02d",hr, min, sec));
	}
	
	void taskSize(String des) {
		if (checkSize(des)==true) {
			this.taskSize = des;
		}
	}
	
	void rename(String Name) {
		this.taskName = Name;
	}
	
	boolean checkSize(String size) {
		boolean check = false;
		if (size.equals("xs") || size.equals("s") || size.equals("m") || size.equals("l") ||size.equals("xl")) {
			check = true;
		} else {
			System.out.println("Unexpected value in size, please enter in xs, s, m, l or xl");
		}
		return check;
	}
	
	@Override
    public String toString() {
        return "Task [taskName=" + taskName + ", description=" + description + ", totalTimeSpent=" + Long.toString(totalTimeSpent) + ", startTime=" + Long.toString(startTime) + ", endTime=" + Long.toString(endTime) + "taskSize=" + taskSize + "]";
    }
}