package com.spring.data.bug.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Language implements Serializable {

    private static final long serialVersionUID = -575490702950122998L;

    @Id
    private String languageId;
    private String description;
}
