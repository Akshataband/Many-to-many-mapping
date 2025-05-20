package manytomany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class StudSubDriver {
	public static void main(String[] args) {
//		addData();
//		allocateStudent(102,2);
//		deallocateStudent(2, 102);
//		allocateSubject(3,100);
//		deallocateSubject(102, 2);
//		addStudent();
//		addSubject();
//		deleteByStudent(4);
//		deleteBySubject(103);
		findBySubjectId(102);
//		findByStudentId(2);
	}

	public static void addData() { // use this method only once, after inserting all the values use other methods to add more students
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Subject sub1 = new Subject();// 100
		sub1.setName("java");
		sub1.setNumofdays(100);

		Subject sub2 = new Subject();// 101
		sub2.setName("SQL");
		sub2.setNumofdays(40);

		Subject sub3 = new Subject();// 102
		sub3.setName("WebTech");
		sub3.setNumofdays(100);

		ArrayList al = new ArrayList();
		al.add(sub1);
		al.add(sub2);
		al.add(sub3);

		Student s = new Student();
		s.setName("Vedashri");
		s.setAge(20);

		s.setSubject(al);

		et.begin();
		em.persist(s);
		em.persist(sub1);
		em.persist(sub2);
		em.persist(sub3);
		et.commit();
	}
	

	public static void addStudent() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student student = new Student();
	    student.setName("libra");
	    student.setAge(50);
//	    student.setSubject(subject);  // Set the subjects list

	    et.begin();
	    em.persist(student);
	    et.commit();
	}

	public static void addSubject() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		
		Subject s = new Subject();
		s.setName("Adv java");
		s.setNumofdays(60);
		
		et.begin();
		em.persist(s);
		et.commit();

	}

	public static void find() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Query q = em.createQuery("Select s from Subject s");
		List<Subject> li = q.getResultList();
		Student s = new Student();
		s.setName("Allen");
		s.setAge(20);
		s.setSubject(li);
		em.persist(s);
		et.commit();
	}

	public static void allocateStudent(int sub_id, int st_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Subject subject = em.find(Subject.class, sub_id);
		Student student = em.find(Student.class, st_id);

		List<Subject> li = student.getSubject();
		li.add(subject);

		et.begin();
		em.merge(student);
		et.commit();

	}

	public static void deallocateStudent(int st_id, int sub_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Subject subject = em.find(Subject.class, sub_id);
		Student student = em.find(Student.class, st_id);

		List<Subject> li = student.getSubject();
		li.remove(subject); // remove this subject from student's list
		student.setSubject(li);

		et.begin();
		em.merge(student);
		et.commit();

	}

	public static void allocateSubject(int st_id, int sub_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student student = em.find(Student.class, st_id);
		Subject subject = em.find(Subject.class, sub_id);

		et.begin();
		List<Subject> list = student.getSubject();

		list.add(subject);
		student.setSubject(list);

		em.merge(student);
		et.commit();

	}

	public static void deallocateSubject(int sub_id,int st_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student student = em.find(Student.class, st_id);
	    Subject subject = em.find(Subject.class, sub_id);

	    List<Subject> li = student.getSubject();
	    li.remove(subject); // remove subject from student's list
	    student.setSubject(li);

	    et.begin();
	    em.merge(student);
	    et.commit();
	}


	public static void deleteByStudent(int st_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
	    Student st = em.find(Student.class, st_id);
	    em.remove(st);
	    et.commit();
	}

	public static void deleteBySubject(int sub_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		et.begin();
		Subject sub = em.find(Subject.class, sub_id);
		em.remove(sub);
		et.commit();
	}

	public static void findBySubjectId(int sub_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Subject subject = em.find(Subject.class, sub_id);
	    System.out.println("Subject: " + subject.getName());

	    List<Student> students = em.createQuery(
	        "SELECT s FROM Student s JOIN s.subject sub WHERE sub.id = :id", Student.class)
	        .setParameter("id", sub_id)
	        .getResultList();

	    for (Student s : students) {
	        System.out.println("Student: " + s.getName() + ", Age: " + s.getAge());
	    }

	}

	public static void findByStudentId(int st_id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgres");
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();

		Student student = em.find(Student.class, st_id);
	    System.out.println("Student: " + student.getName());

	    for (Subject s : student.getSubject()) {
	        System.out.println("Subject: " + s.getName() + ", Days: " + s.getNumofdays());
	    }


	}

}
