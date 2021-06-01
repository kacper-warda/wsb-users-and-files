package com.logintegra.wsbbugtracker.files;

import com.logintegra.wsbbugtracker.issues.Issue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDB {

    @Id
    @GeneratedValue
    Long id;
    @ManyToOne(optional = true)
    @JoinColumn(name = "issue_id", nullable = true)
    Issue issue;
    @Column(nullable = false)
    private String name;
    @Column
    private String type;
    @Lob
    private byte[] data;

    public FileDB(String fileName, String contentType, byte[] bytes, Issue issue) {
        this.name = fileName;
        this.type = contentType;
        this.data = bytes;
        this.issue = issue;
    }

    public FileDB(String fileName, String contentType, byte[] bytes) {
        this.name = fileName;
        this.type = contentType;
        this.data = bytes;
    }
}
