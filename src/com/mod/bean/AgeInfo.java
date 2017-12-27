package com.mod.bean;

import org.springframework.stereotype.Component;

@Component
public class AgeInfo {
		public String ageStep;
		public int value;
		public String sex;
		
		public AgeInfo() {
		}
		public String getAgeStep() {
			return ageStep;
		}
		public void setAgeStep(String ageStep) {
			this.ageStep = ageStep;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		
}
