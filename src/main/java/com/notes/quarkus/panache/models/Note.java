package com.notes.quarkus.panache.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "notes")
public class Note extends PanacheEntity {
	@Column(length = 3000)
	public String note;
	@Column
	public Boolean important;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_fk")
	public User user;


	@Override
	public String toString() {
		return "Note{" +
				"note='" + note + '\'' +
				", importance=" + important +
				", user=" + user +
				'}';
	}
}
