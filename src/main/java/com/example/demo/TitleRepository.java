package com.example.demo;

import com.example.demo.Title;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, Long>{

	public Title findById(int id);
	public void deleteById(int id);
}
