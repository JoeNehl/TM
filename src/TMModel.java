import java.io.*;
import java.util.*;

class TMModel implements Serializable {
	
	String description;
	String taskName;
	String taskSize;
	long totalTimeSpent;
	long startTime;
	long endTime;
	
	public TMModel(String taskName){
		this.taskName = taskName;
		this.totalTimeSpent = 0;
		this.startTime = System.currentTimeMillis();
		this.endTime = -1;
		this.taskSize = "";
		}

	public TMModel(String taskName, String descript, String size){
		this.taskName = taskName;
		this.totalTimeSpent = 0;
		this.startTime = 0;
		this.description = descript;
		this.endTime = -1;
		this.taskSize = size;
	}
	
	boolean startTask() {
		if (this.startTime <= 0) {
			this.startTime = System.currentTimeMillis();
		} else {
			System.out.println("Task has already been started.");
		}
		return true;
	}
	
	boolean stopTask() {
		if (this.startTime <=0) {
			System.out.println("Task was not started.");
		} else {
		long partialTime = 0;
		this.endTime=System.currentTimeMillis();
		partialTime = this.endTime - this.startTime;
		partialTime = partialTime/1000;
		this.totalTimeSpent = this.totalTimeSpent + partialTime;
		this.startTime = 0;
		this.endTime = -1;
		}
		return true;
	}
	
	boolean describeTask(String des, String size) {
		if (this.description == null && des!= "") {
			this.description = des;
		} else if (this.description == null && des == "") {
		} else if (des != "") {
			this.description= this.description + "\n" + "             " + des;
		}
		if (checkSize(size)==true) {
			this.taskSize = size;
		}
		return true;
	}
	
	boolean sizeTask(String des) {
		if (checkSize(des)==true) {
			this.taskSize = des;
		}
		return true;
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
	
	boolean deleteTask(){
		return true;
	}
	
	boolean renameTask(String Name) {
		this.taskName = Name;
		return true;
	}
	
	String taskElapsedTime(String name) {
		String tmp = timeOut(this.totalTimeSpent);
		return tmp;
	}
	String taskSize(String name) {
		String size = taskSize;
		return size;
	}
	
	String taskDescription(String name) {
		String des = description;
		return des;
	}
	
	String timeOut(long totalTimeSpent) {
		long tmp = totalTimeSpent;
		long hr =tmp/3600;
		long min =(tmp%3600)/60;
		long sec =tmp%60;
		return (String.format("%02d:%02d:%02d",hr, min, sec));
	}
	
	String minTimeForSize(String size) {
		String minTime = "";
		return minTime;
	}
	
	String maxTimeForSize(String size) {
		String maxTime ="";
		return maxTime;
	}
	
	String avgTimeForSize(String size) {
		String avgTime ="";
		return avgTime;
	}
	
	String elapsedTimeForAllTasks() {
		String total = "";
		return total;
	}
	
	@Override
    public String toString() {
        return "Task [taskName=" + taskName + ", description=" + description + ", totalTimeSpent=" + Long.toString(totalTimeSpent) + ", startTime=" + Long.toString(startTime) + ", endTime=" + Long.toString(endTime) + ", taskSize=" + taskSize + "]";
    }
}