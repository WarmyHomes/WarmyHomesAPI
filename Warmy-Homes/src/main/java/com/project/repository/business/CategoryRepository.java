package com.project.repository.business;

import com.project.entity.business.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface categoriesRepository extends JpaRepository<Category,Long> {

}
