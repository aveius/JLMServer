package jlm.gae.data;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class UserData {

	private String username;
	private List<ExerciseData> exercises;
	private GregorianCalendar lastJoin;
	private GregorianCalendar lastLeave;
	private GregorianCalendar lastHeartbeat;
	
	public UserData() {
		exercises = new ArrayList<ExerciseData>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public List<ExerciseData> getExercises() {
		return exercises;
	}
	
	public GregorianCalendar getLastJoin() {
		return lastJoin;
	}
	
	public void setLastJoin(GregorianCalendar lastJoin) {
		this.lastJoin = lastJoin;
	}
	
	public GregorianCalendar getLastLeave() {
		return lastLeave;
	}
	
	public void setLastLeave(GregorianCalendar lastLeave) {
		this.lastLeave = lastLeave;
	}
	
	public GregorianCalendar getLastHeartbeat() {
		return lastHeartbeat;
	}
	
	public void setLastHeartbeat(GregorianCalendar lastHeartbeat) {
		this.lastHeartbeat = lastHeartbeat;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", exercises=" + exercises
				+ ", lastJoin=" + lastJoin + ", lastLeave=" + lastLeave
				+ ", lastHeartbeat=" + lastHeartbeat + "]";
	}
}
