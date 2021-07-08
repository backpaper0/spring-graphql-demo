package com.example.todo;

import java.util.Objects;

public class Task {

	private String id;
	private String text;
	private boolean done;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, text, done);
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
		Task other = (Task) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(text, other.text)
				&& done == other.done;
	}

	@Override
	public String toString() {
		return (done ? "[*]" : "[ ]") + text;
	}

}
