package com.example.DavtyanBasicServer.repository;

import com.example.DavtyanBasicServer.entity.TasksEntity;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<TasksEntity, Long> {

    @Transactional
    void deleteAllByStatus(boolean status);

    @Modifying
    @Transactional
    @Query("UPDATE TasksEntity t SET t.status = :status")
    void patchAllStatus(Boolean status);
}
