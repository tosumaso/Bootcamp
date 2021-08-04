package com.example.demo.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="artist")
public class Artist {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String name;
	
	@ManyToMany //親テーブルの方に中間テーブルの定義を書く,２つのmanytomanyテーブルの主キーを外部参照する中間テーブルをDBで定義
	@JoinTable(name="art_cd", joinColumns= @JoinColumn(name="artist_id"), inverseJoinColumns= @JoinColumn(name="cd_id"))
	private List<Cd> cds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Cd> getCds() {
		return cds;
	}

	public void setCds(List<Cd> cds) {
		this.cds = cds;
	}
	
	
}
