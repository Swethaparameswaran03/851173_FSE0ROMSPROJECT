package com.roms.component.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name="completeprocess")
public class CompleteProcess {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="processid")
	int processid;
	@Column(name="requestid")
	String requestid;
    @Column(name="cardnumber")
	String cardnumber;
    @Column(name="creditlimit")
	double creditLimit;
    @Column(name="processingcharge") 
	double processingCharge;

}
