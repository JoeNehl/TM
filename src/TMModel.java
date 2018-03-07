import java.io.*;
import java.util.*;

class sizeCalc{
	public static long s=-1;
	public static long m=-1;
	public static long l=-1;
	public static long t=0;
	public static String ss="";
	public static String ms ="";
	public static String ls="";
	public static int [] counter = {0,0,0};
}

class TMModel implements Serializable, ITMModel {
	
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
	
	public boolean startTask(String name) {
		if (this.startTime <= 0) {
			this.startTime = System.currentTimeMillis();
		} else {
			System.out.println("Task has already been started.");
		}
		return true;
	}
	
	public boolean stopTask(String name) {
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
	
	public boolean describeTask(String des, String size) {
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
	
	public boolean sizeTask(String name, String des) {
		if (checkSize(des)==true) {
			this.taskSize = des;
		}
		return true;
	}
	
	boolean checkSize(String size) {
		boolean check = false;
		if (size.equals("s") || size.equals("m") || size.equals("l") ||size.equals("")) {
			check = true;
		} else {
			System.out.println("Unexpected value in size, please enter in s, m or l");
		}
		return check;
	}
	
	public boolean deleteTask(String name){
		return true;
	}
	
	public boolean renameTask(String Name, String newName) {
		this.taskName = Name;
		return true;
	}
	
	public String taskElapsedTime(String name) {
		String tmp = timeOut(this.totalTimeSpent);
		return tmp;
	}
	public String taskSize(String name) {
		String size = taskSize;
		return size;
	}
	
	public String taskDescription(String name) {
		System.out.println("Task: " + taskName);
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
	
	public String minTimeForSize(String size) {
		String ready = "";
		if (this.taskSize.equals("s")) {
			if (sizeCalc.s>this.totalTimeSpent || sizeCalc.s==-1) {
				sizeCalc.s=this.totalTimeSpent;
				sizeCalc.ss=this.taskName;
			}
			sizeCalc.counter[0]++;
		} else if (this.taskSize.equals("m")) {
			if (sizeCalc.m>this.totalTimeSpent|| sizeCalc.m==-1) {
				sizeCalc.m=this.totalTimeSpent;
				sizeCalc.ms=this.taskName;
			}
			sizeCalc.counter[1]++;
		} else if (this.taskSize.equals("l")) {
			if (sizeCalc.l>this.totalTimeSpent|| sizeCalc.l==-1) {
				sizeCalc.l=this.totalTimeSpent;
				sizeCalc.ls=this.taskName;
			}
			sizeCalc.counter[2]++;
		}
		return ready;
	}
	
	public String maxTimeForSize(String size) {
		String maxTime="";
		return maxTime;
	}
	
	public String avgTimeForSize(String size) {
		String avgTime ="";
		return avgTime;
	}
	
	public String elapsedTimeForAllTasks() {
		String total = "";
		return total;
	}
	
    public Set<String> taskNamesForSize(String size){
   	 	Set<String> tNameSize = new TreeSet<String>();
   	 	return tNameSize;
    }
    
    public Set<String> taskNames(){
    	 Set<String> tName = new TreeSet<String>();
    	return tName;
    }
    
    public Set<String> taskSizes(){
   	 Set<String> tSize = new TreeSet<String>();
   	return tSize;
    }
	
	@Override
    public String toString() {
        return "Task [taskName=" + taskName + ", description=" + description + ", totalTimeSpent=" + Long.toString(totalTimeSpent) + ", startTime=" + Long.toString(startTime) + ", endTime=" + Long.toString(endTime) + ", taskSize=" + taskSize + "]";
    }
}