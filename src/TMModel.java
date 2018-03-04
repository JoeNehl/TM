import java.io.*;
import java.util.*;

class TMModel implements Serializable {
	
	String description;
	String taskName;
	String taskSize;
	long totalTimeSpent;
	long startTime;
	long endTime;
	
	public TMModel(String taskName){// there are two different options, either start with a task and description or start with a task and start time
		this.taskName = taskName;//this is for time
		this.totalTimeSpent = 0;
		this.startTime = System.currentTimeMillis();
		this.endTime = -1;
		this.taskSize = "";
		}

	public TMModel(String taskName, String descript, String size){//this is for starting with description
		this.taskName = taskName;
		this.totalTimeSpent = 0;
		this.startTime = 0;
		this.description = descript;
		this.endTime = -1;
		this.taskSize = size;
	}
	
	void startTask() {//starts time
		if (this.startTime <= 0) {//checks to see if time has already started
			this.startTime = System.currentTimeMillis();
		} else {
			System.out.println("Task has already been started.");
		}
	}
	
	void stopTask() { //stops the task
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
	
	void describeTask(String des, String size) {
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
	
	void sizeTask(String des) {
		if (checkSize(des)==true) {
			this.taskSize = des;
		}
	}
	
	void renameTask(String Name) {
		this.taskName = Name;
	}
	
	boolean checkSize(String size) {
		boolean check = false;
		if (size.equals("xs") || size.equals("s") || size.equals("m") || size.equals("l") ||size.equals("xl")||size.equals("")) {
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