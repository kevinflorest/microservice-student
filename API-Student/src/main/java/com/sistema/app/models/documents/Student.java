package com.sistema.app.models.documents;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="student")
public class Student {

	@Id
	private String id;
	
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String secondName;
	@NotEmpty
	private String paternalSurname;
	@NotEmpty
	private String maternalSurname;
	@NotEmpty
	private String genderStudent;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private Date birthDate;
	@NotEmpty
	private String typeDocument;
	@NotEmpty
	private String numberDocument;

}
