package com.spring.gotgongbang.craft.model;

public interface InterCraftDAO {

	// 이미 존재하는 '공방이름'인지 알아오기 위한 것
	int craft_check_name(String craft_name);

}
