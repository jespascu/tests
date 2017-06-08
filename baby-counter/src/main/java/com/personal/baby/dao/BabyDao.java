package com.personal.baby.dao;

import java.util.List;

import com.personal.baby.model.Baby;

public interface BabyDao {
	
	public int update(Baby b);
	
	public Baby findByName(String name);
	
	public List<Baby> findAll();

}
