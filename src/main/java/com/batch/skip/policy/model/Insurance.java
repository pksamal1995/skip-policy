package com.batch.skip.policy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Insurance {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "policy_id")
	private Long policyID;
	private String statecode;
	private String county;
	private String line;
	private String construction;
	@Column(name = "point_granularity")
	private Integer point_granularity;

	public Long getPolicyID() {
		return policyID;
	}

	public void setPolicyID(Long policyID) {
		this.policyID = policyID;
	}

	public String getStatecode() {
		return statecode;
	}

	public void setStatecode(String statecode) {
		this.statecode = statecode;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getConstruction() {
		return construction;
	}

	public void setConstruction(String construction) {
		this.construction = construction;
	}

	public Integer getPoint_granularity() {
		return point_granularity;
	}

	public void setPoint_granularity(Integer point_granularity) {
		this.point_granularity = point_granularity;
	}

	public Insurance() {

	}

	@Override
	public String toString() {
		return "Insurance [policyID=" + policyID + ", statecode=" + statecode + ", county=" + county + ", line=" + line
				+ ", construction=" + construction + ", point_granularity=" + point_granularity + "]";
	}

}
