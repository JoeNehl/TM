//Joseph Nehl
import java.io.*; // two files import the java utility and io libraries
import java.util.*;

public class TM { //wrong name my bad, TM

	public static void main(String[] args) { // wrote this main like discussed in class, pass args to a different method
		TM tm = new TM();
		tm.appMain(args);
	}
	
	void appMain(String[] args) { //args are passed here to be used
		int count = args.length; //this checks to make sure that no more that 3 args are input and if less than three fixes the out of bounds error
		if (count >4) {
			count = 4;
		}
		String[] cmdln = {"", "", "", ""};
		for (int i=0;i<count;i++) {// puts args into an array of strings
			cmdln[i]=args[i];
		}
		HashMap<String, Task> taskMap = null; // the beginning of how I store my data
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
		String cmd = cmdln[0];
		String data = cmdln[1];
		String des = cmdln[2];
		String size = cmdln[3];
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
				System.out.println("That task doesn't exist.");
				}
			break;
		case "describe":
			if(taskMap.containsKey(data)){
				taskMap.get(data).describe(des, size);
			} else {
				taskMap.put(data,new Task(data, des, size));
				}
			break;
		case "summary": // this 
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
	
	public Task(String taskName){// there are two different option, either start with a task and description or start with a task and start time
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
	
	void describe(String des, String size) {//writes the description and size if the user enters
		if (this.description == null) {
			this.description=des;
			this.taskSize=size;
		} else if (this.description != "") {
			this.description= this.description + "\n" + "             " + des;
			this.taskSize=size;
		}
	}
	
	void summary() {//prints out the data stored
		System.out.println("Task: " + taskName + "\nTime(seconds): " + totalTimeSpent + "\nSize: " + taskSize + "\nDescription: " + description + "\n\n");
	}
	
	void taskSize(String des) {//overwrites the size that the user enters for a task
		this.taskSize = des;
	}
	
	@Override
    public String toString() {// this is what saves it to the .ser file
        return "Task [taskName=" + taskName + ", description=" + description + ", totalTimeSpent=" + Long.toString(totalTimeSpent) + ", startTime=" + Long.toString(startTime) + ", endTime=" + Long.toString(endTime) + "taskSize=" + taskSize + "]";
    }
}
