package com.logintegra.wsbbugtracker.issues;

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
public class Issue {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, unique = true)
    String code; // coś w stylu A-1234, PCC-43 - do ogarnięcia

    @Column(nullable = false)
    String title;

    @Column(columnDefinition = "TEXT")
    String content;


    public Issue(String code, String title, String content) {
        this.code = code;
        this.title = title;
        this.content = content;
    }

}
