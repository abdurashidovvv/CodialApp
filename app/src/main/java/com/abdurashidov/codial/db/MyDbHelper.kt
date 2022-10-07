package com.abdurashidov.codial.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.abdurashidov.codial.models.Course
import com.abdurashidov.codial.models.Group
import com.abdurashidov.codial.models.Mentor
import com.abdurashidov.codial.models.Student

class MyDbHelper(context: Context):SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION), MyDbInterface {

    companion object{
        val DB_NAME="codial_db"
        val DB_VERSION=1

        val COURSE_TABLE="course_table"
        val COURSE_ID="ID"
        val COURSE_NAME="course_name"
        val COURSE_INFO="course_info"

        val MENTOR_TABLE="mentor_table"
        val MENTOR_ID="id"
        val MENTOR_NAME="name"
        val MENTOR_SURNAME="surname"
        val MENTOR_PHONE="phone"
        val MENTOR_COURSE_ID="course"

        val GROUP_TABLE="group_table"
        val GROUP_ID="id"
        val GROUP_NAME="name"
        val GROUP_MENTOR_ID="mentor"
        val GROUP_TIME="time"
        val GROUP_DAYSOFWEEK="days"
        val GROUP_COURSE_ID="course_id"
        val GROUP_OPEN="open"

        val STUDENT_TABLE="student_table"
        val STUDENT_ID="id"
        val STUDENT_NAME="name"
        val STUDENT_SURNAME="surname"
        val STUDENT_PHONE="phone"
        val STUDENT_DAY="days"
        val STUDENT_GROUP_ID="group_id"
    }


    override fun onCreate(db: SQLiteDatabase?) {
        val queryCourse="create table $COURSE_TABLE ($COURSE_ID integer not null primary key autoincrement unique, $COURSE_NAME text not null, $COURSE_INFO text not null)"
        db?.execSQL(queryCourse)

        val queryMentor="create table $MENTOR_TABLE ($MENTOR_ID integer not null primary key autoincrement unique, $MENTOR_NAME text not null, $MENTOR_SURNAME text not null, $MENTOR_PHONE, $MENTOR_COURSE_ID integer not null)"
        db?.execSQL(queryMentor)

        val queryGroup="create table $GROUP_TABLE ($GROUP_ID integer not null primary key autoincrement unique,  $GROUP_NAME text not null, $GROUP_MENTOR_ID integer not null, $GROUP_TIME text not null, $GROUP_DAYSOFWEEK text not null, $GROUP_COURSE_ID integer not null, $GROUP_OPEN numeric not null, foreign key($GROUP_COURSE_ID) references $COURSE_TABLE($COURSE_ID), foreign key($GROUP_MENTOR_ID) references $MENTOR_TABLE($MENTOR_ID))"
        db?.execSQL(queryGroup)

        val queryStudent="create table $STUDENT_TABLE ($STUDENT_ID integer not null primary key autoincrement unique, $STUDENT_NAME text not null, $STUDENT_SURNAME text not null, $STUDENT_PHONE text not null, $STUDENT_DAY text not null, $STUDENT_GROUP_ID integer not null, foreign key($STUDENT_GROUP_ID) references $GROUP_TABLE($GROUP_ID))"
        db?.execSQL(queryStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun addCourse(course: Course) {
        val database=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(COURSE_NAME, course.name)
        contentValues.put(COURSE_INFO, course.info)
        database.insert(COURSE_TABLE, null, contentValues)
        database.close()
    }

    override fun addMentor(mentor: Mentor) {
        val database=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(MENTOR_NAME, mentor.name)
        contentValues.put(MENTOR_SURNAME, mentor.surname)
        contentValues.put(MENTOR_PHONE, mentor.phone)
        contentValues.put(MENTOR_COURSE_ID, mentor.course)
        database.insert(MENTOR_TABLE, null, contentValues)
        database.close()
    }

    override fun addGroup(group: Group) {
        val database=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(GROUP_NAME, group.name)
        contentValues.put(GROUP_MENTOR_ID, group.mentor?.id)
        contentValues.put(GROUP_TIME, group.time)
        contentValues.put(GROUP_DAYSOFWEEK, group.daysOfWeek)
        contentValues.put(GROUP_COURSE_ID, group.course?.id)
        contentValues.put(GROUP_OPEN, group.open)
        database.insert(GROUP_TABLE, null, contentValues)
        database.close()
    }

    override fun addStudent(student: Student) {
        val database=writableDatabase
        val contentValues=ContentValues()
        contentValues.put(STUDENT_NAME, student.name)
        contentValues.put(STUDENT_SURNAME, student.surname)
        contentValues.put(STUDENT_PHONE, student.phone)
        contentValues.put(STUDENT_DAY, student.day)
        contentValues.put(STUDENT_GROUP_ID, student.group?.id)
        database.insert(STUDENT_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllCourses(): ArrayList<Course> {
        val list=ArrayList<Course>()
        val query="select * from $COURSE_TABLE"
        val database=readableDatabase
        val cursor=database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                list.add(
                    Course(
                        id = cursor.getInt(0),
                        name = cursor.getString(1),
                        info = cursor.getString(2)
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllMentors(): ArrayList<Mentor> {
        val list=ArrayList<Mentor>()
        val query="select * from $MENTOR_TABLE"
        val database=readableDatabase
        val cursor=database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                list.add(
                    Mentor(
                        id = cursor.getInt(0),
                        name = cursor.getString(1),
                        surname = cursor.getString(2),
                        phone = cursor.getString(3),
                        course = cursor.getInt(4)
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllGroups(): ArrayList<Group> {
        val list=ArrayList<Group>()
        val query="select * from $GROUP_TABLE"
        val database=readableDatabase
        val cursor=database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                list.add(
                    Group(
                        id = cursor.getInt(0),
                        name = cursor.getString(1),
                        mentor = getMentorById(cursor.getInt(2)),
                        time = cursor.getString(3),
                        daysOfWeek = cursor.getString(4),
                        course = getCourseById(cursor.getInt(5)),
                        open = false
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllStudents(): ArrayList<Student> {
        val list=ArrayList<Student>()
        val query="select * from $STUDENT_TABLE"
        val database=readableDatabase
        val cursor=database.rawQuery(query, null)
        if (cursor.moveToFirst()){
            do {
                list.add(
                    Student(
                        id = cursor.getInt(0),
                        name = cursor.getString(1),
                        surname = cursor.getString(2),
                        phone = cursor.getString(3),
                        day = cursor.getString(4),
                        group = getGroupById(cursor.getInt(5))
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }




    override fun getCourseById(id: Int): Course {
        val database=readableDatabase
        var cursor=database.query(
            COURSE_TABLE,
            arrayOf(
                COURSE_ID,
                COURSE_NAME,
                COURSE_INFO
            ),
            "$COURSE_ID=?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val course=Course(
            id = cursor.getInt(0),
            name = cursor.getString(1),
            info = cursor.getString(2)
        )
        return course
    }

    override fun getMentorById(id: Int): Mentor {
        val database=readableDatabase
        val cursor=database.query(
            MENTOR_TABLE,
            arrayOf(
                MENTOR_ID,
                GROUP_NAME,
                MENTOR_SURNAME,
                MENTOR_PHONE,
                MENTOR_COURSE_ID
            ),
            "$MENTOR_ID=?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val mentor=Mentor(
            id = cursor.getInt(0),
            name = cursor.getString(1),
            surname = cursor.getString(2),
            phone = cursor.getString(3),
            course = cursor.getInt(4)
        )
        return mentor
    }

    override fun getGroupById(id: Int): Group {
        val database=readableDatabase
        val cursor=database.query(
            GROUP_TABLE,
            arrayOf(
                GROUP_ID,
                GROUP_NAME,
                GROUP_MENTOR_ID,
                GROUP_TIME,
                GROUP_DAYSOFWEEK,
                GROUP_COURSE_ID,
                GROUP_OPEN
            ),
            "$GROUP_ID=?",
            arrayOf(id.toString()),
            null, null, null
        )
        cursor.moveToFirst()
        val group=Group(
            id = cursor.getInt(0),
            name = cursor.getString(1),
            mentor = getMentorById(cursor.getInt(2)),
            time = cursor.getString(3),
            daysOfWeek = cursor.getString(4),
            course = getCourseById(cursor.getInt(5)),
            open = cursor.getString(6).equals("0")
        )
        return group
    }
}