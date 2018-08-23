package com.demo.export.type;

public enum SeverityType {

	info(1),
	warning(2),
	error(3);
	
	private int value;
	
	private SeverityType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}

}
