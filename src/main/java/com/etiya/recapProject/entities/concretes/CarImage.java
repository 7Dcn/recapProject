package com.etiya.recapProject.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_images")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "car" })
public class CarImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "image_name")
	private String imageName;
	
	@Column(name = "image_path")
	private String imagePath;

	@Column(name = "date")
	private LocalDate date;

	@ManyToOne()
	@JoinColumn(name = "car_id")
	private Car car;
}
