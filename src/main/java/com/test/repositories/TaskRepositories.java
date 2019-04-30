package com.test.repositories;

import com.test.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepositories extends JpaRepository<Task, UUID> {

}
