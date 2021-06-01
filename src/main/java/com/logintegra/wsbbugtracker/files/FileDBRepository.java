package com.logintegra.wsbbugtracker.files;

import com.logintegra.wsbbugtracker.issues.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, Long> {

    List<FileDB> findAllByIssue(Issue issue);
}