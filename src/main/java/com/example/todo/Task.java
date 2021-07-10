package com.example.todo;

import java.util.Objects;

public class Task {

	private String id;
	private TaskText text;
	private TaskStatus status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TaskText getText() {
		return text;
	}

	public void setText(TaskText text) {
		this.text = text;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, text, status);
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
				&& status == other.status;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", text=" + text + ", status=" + status + "]";
	}
}
