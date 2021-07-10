package com.example.todo;

import java.util.Objects;

public class TaskText {

	private final String value;

	public TaskText(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (getClass() != obj.getClass()) {
			return false;
		}
		TaskText other = (TaskText) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return value;
	}
}
