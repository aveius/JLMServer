package jlm.gae.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserData {

	private String username;
	private List<ExerciseData> exercises;
	private Date lastJoin;
	private Date lastLeave;
	private Date lastHeartbeat;
	
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
	
	public Date getLastJoin() {
		return lastJoin;
	}
	
	public void setLastJoin(Date lastJoin) {
		this.lastJoin = lastJoin;
	}
	
	public Date getLastLeave() {
		return lastLeave;
	}
	
	public void setLastLeave(Date lastLeave) {
		this.lastLeave = lastLeave;
	}
	
	public Date getLastHeartbeat() {
		return lastHeartbeat;
	}
	
	public void setLastHeartbeat(Date lastHeartbeat) {
		this.lastHeartbeat = lastHeartbeat;
	}
	
	@Override
	public String toString() {
		return "User [username=" + username + ", exercises=" + exercises
				+ ", lastJoin=" + lastJoin + ", lastLeave=" + lastLeave
				+ ", lastHeartbeat=" + lastHeartbeat + "]";
	}
}
