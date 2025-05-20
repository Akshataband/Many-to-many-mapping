package manytomany;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Subject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sub_id")
	@SequenceGenerator(name = "sub_id", initialValue = 100, allocationSize = 1)
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumofdays() {
		return numofdays;
	}

	public void setNumofdays(int numofdays) {
		this.numofdays = numofdays;
	}

	private int numofdays;
}
