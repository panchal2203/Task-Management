package com.taskapp.notification.repository;

import com.taskapp.notification.entity.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<MessageLog, Long> {
}
