package com.abdurashidov.codial.db

import com.abdurashidov.codial.models.Course
import com.abdurashidov.codial.models.Group
import com.abdurashidov.codial.models.Mentor
import com.abdurashidov.codial.models.Student

interface MyDbInterface {

    fun addCourse(course: Course)
    fun addMentor(mentor: Mentor)
    fun addGroup(group:Group)
    fun addStudent(student: Student)

    fun getAllCourses():ArrayList<Course>
    fun getAllMentors():ArrayList<Mentor>
    fun getAllGroups():ArrayList<Group>
    fun getAllStudents():ArrayList<Student>

    fun getCourseById(id:Int):Course
    fun getMentorById(id:Int):Mentor
    fun getGroupById(id: Int):Group
}