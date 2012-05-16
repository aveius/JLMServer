package jlm.gae.data;

import java.util.GregorianCalendar;

public class ExerciseData {

	private String name;
	private String lang;
	private int passedTests;
	private int totalTests;
	private String source;
	private GregorianCalendar date;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public int getPassedTests() {
		return passedTests;
	}
	
	public void setPassedTests(int passedTests) {
		this.passedTests = passedTests;
	}
	
	public int getTotalTests() {
		return totalTests;
	}
	
	public void setTotalTests(int totalTests) {
		this.totalTests = totalTests;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public GregorianCalendar getDate() {
		return date;
	}
	
	public void setDate(GregorianCalendar date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "Exercise [name=" + name + ", lang=" + lang + ", passedTests="
				+ passedTests + ", totalTests=" + totalTests + ", source="
				+ source + ", date=" + date + "]";
	}
}
