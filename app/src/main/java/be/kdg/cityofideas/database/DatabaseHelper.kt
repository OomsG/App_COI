package be.kdg.cityofideas.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import be.kdg.cityofideas.model.Surveys.QuestionType
import okhttp3.internal.platform.Platform


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME: String = "CityOfIdeasApp"
        private val DB_VERSION: Int = 1
    }

    //region Datatypes
    //region Address
    val TBL_ADDRESS = "address_table"
    val ADDRESS_ID = "AddressId"
    val ADDRESS_STREET = "Street"
    val ADDRESS_HOUSENR = "HouseNr"
    val ADDRESS_CITY = "City"
    val ADDRESS_ZIPCODE = "ZipCode"

    val TABLE_ADDRESS_DROP = "DROP TABLE IF EXISTS " + TBL_ADDRESS

    val TABLE_CREATE_ADDRESS = "CREATE TABLE " + TBL_ADDRESS + " (" +
            ADDRESS_ID + " integer PRIMARY KEY," +
            ADDRESS_STREET + " text," +
            ADDRESS_HOUSENR + " text," +
            ADDRESS_CITY + " text," +
            ADDRESS_ZIPCODE + " text)"
    //endregion
    //region Position
    val TBL_POSITION = "position_id"
    val POSITION_ID = "PositionId"
    val POSITION_LONG = "Lng"
    val POSITION_LAT = "lat"

    val TABLE_POSITION_DROP = "CREATE TABLE IF EXISTS " + TBL_POSITION

    val TABLE_CREATE_POSITION = "CREATE TABLE " + TBL_POSITION + " (" +
            POSITION_ID + " integer PRIMARY KEY," +
            POSITION_LAT + " text," +
            POSITION_LONG + " text)"

    //endregion
    //region Location
    val TBL_LOCATION = "location_table"
    val LOCATION_ID = "LocationId"
    val LOCATION_NAME = "LocationName"

    val TABLE_LOCATION_DROP = "DROP TABLE IF EXISTS " + TBL_LOCATION

    val TABLE_CREATE_LOCATION = "CREATE TABLE " + TBL_LOCATION + " (" +
            LOCATION_ID + " integer PRIMARY KEY," +
            LOCATION_NAME + " text," +
            ADDRESS_ID + " integer," +
            POSITION_ID + "integer)"

    //endregion
    //endregion
    //region Projects
    //region Platform
    val TBL_PLATFORM = "platform_table"
    val PLATFORM_ID = "PlatfromId"
    val PLATFORM_NAME = "PlatformName"
    val PLATFROM_LOGO = "Logo"
    val PLATFORM_PHONENUMBER = "PhoneNumber"
    val PLATFORM_DESCRIPTION = "Description"
    val PLATFORM_BACKGROUND_IMAGE = "BackgroundImage"

    val TABLE_PLATFORM_DROP = "DROP TABLE IF EXISTS " + TBL_PLATFORM

    val TABLE_CREATE_PLATFORM = "CREATE TABLE " + TBL_PLATFORM + " (" +
            PLATFORM_ID + " integer PRIMARY KEY," +
            PLATFORM_NAME + " text," +
            PLATFROM_LOGO + " text," +
            ADDRESS_ID + " integer," +
            PLATFORM_PHONENUMBER + " text," +
            PLATFORM_DESCRIPTION + " text," +
            PLATFORM_BACKGROUND_IMAGE + "text)"

    //endregion
    // region Projects
    val TBL_PROJECT = "project_table"
    val PROJECT_ID = "ProjectId"
    val PROJECT_NAME = "Name"
    val PROJECT_LOGO = "Logo"
    val PROJECT_STARTDATE = "StartDate"
    val PROJECT_ENDDATE = "EndDate"
    val PROJECT_OBJECTIVE = "Objective"
    val PROJECT_DESCRIPTION = "Description"
    val PROJECT_STATUS = "Status"
    val PROJECT_BACKGROUNDIMAGE = "BackgroundImage"

    val TABLE_PROJECTS_DROP = "DROP IF EXISTS " + TBL_PROJECT

    val TABLE_CREATE_PROJECTS = "CREATE TABLE " + TBL_PROJECT + " (" +
            PROJECT_ID + " integer PRIMARY KEY," +
            PROJECT_NAME + " text," +
            PROJECT_LOGO + " text," +
            PROJECT_STARTDATE + " text," +
            PROJECT_ENDDATE + " text," +
            PROJECT_DESCRIPTION + " text," +
            PROJECT_OBJECTIVE + " text," +
            PROJECT_STATUS + " text," +
            PROJECT_BACKGROUNDIMAGE + "text," +
            PLATFORM_ID + "integer, " +
            LOCATION_ID + "integer)"
    //endregion
    //region Phases
    val TBL_PHASE = "phase_table"
    val PHASE_ID = "PhaseId"
    val PHASE_NR = "PhaseNr"
    val PHASE_NAME = "PhaseName"
    val PHASE_DESCRIPTION = "Description"
    val PHASE_STARTDATE = "StartDate"
    val PHASE_ENDDATE = "EndDate"

    val TABLE_PHASE_DROP = "DROP TABLE IF EXISTS " + TBL_PHASE

    val TABLE_CREATE_PHASE = "CREATE TABLE " + TBL_PHASE + " (" +
            PHASE_ID + " integer PRIMARY KEY," +
            PHASE_NR + " integer," +
            PHASE_NAME + " text," +
            PHASE_DESCRIPTION + " text," +
            PHASE_STARTDATE + " text," +
            PHASE_ENDDATE + " text," +
            PROJECT_ID + " integer)"
    //endregion
    //endregion
    //region User
    val TBL_USER = "user_table"
    val USER_ID = "Id"
    val USER_NAME = "Name"
    val USER_EMAIL = "Email"
    val USER_PASSWORD = "PasswordHash"

    val TABLE_USER_DROP = "DROP TABLE IF EXISTS " + TBL_USER

    val TABLE_CREATE_USER = "CREATE TABLE " + TBL_USER + " (" +
            USER_ID + " integer PRIMARY KEY ," +
            USER_NAME + " text," +
            USER_EMAIL + " text UNIQUE," +
            USER_PASSWORD + " text," +
            PLATFORM_ID + " integer)"

    //endregion
    //region Ideations
    //region Idea
    val TBL_IDEA = "idea_table"
    val IDEA_ID = "IdeaId"
    val IDEA_THEME = "Theme"
    val IDEA_REPORTED = "Reported"
    val IDEA_TITLE = "Title"

    val TABLE_IDEA_DROP = "DROP TABLE IF EXISTS " + TBL_IDEA

    val TABLE_CREATE_IDEA = " CREATE TABLE " + TBL_IDEA + " (" +
            IDEA_ID + " integer PRIMARY KEY," +
            POSITION_ID + " integer," +
            IDEA_THEME + " text," +
            IDEA_REPORTED + " integer," +
            IDEA_TITLE + " text," +
            USER_ID + " text)"
    //IDEATION_ID + " integer)"


    //endregion
    //region Ideation
    val TBL_IDEATION = "ideation_table"
    val IDEATION_ID = "IdeationId"
    val IDEATION_CENTRALQUESTION = "CentralQuestion"
    val IDEATION_INPUTIDEATION = "InputIdeation"

    val TABLE_IDEATION_DROP = "DROP TABLE IF EXISTS " + TBL_IDEATION

    val TABLE_CREATE_IDEATION = "CREATE TABLE " + TBL_IDEATION + " (" +
            IDEATION_ID + " integer PRIMARY KEY," +
            IDEATION_CENTRALQUESTION + " text," +
            IDEATION_INPUTIDEATION + "text," +
            PHASE_ID + " integer)"

    //endregion
    //region Reaction
    val TBL_REACTION = "reaction_table"
    val REACTION_ID = "ReactionId"
    val REACTION_TEXT = "ReactionText"
    val REACTION_REPORTED = "Reported"

    val TABLE_REACTION_DROP = "DROP IF TABLE EXISTS " + TBL_REACTION

    val TABLE_CREATE_REACTION = "CREATE TABLE " + TBL_REACTION + " (" +
            REACTION_ID + " integer PRIMARY KEY," +
            REACTION_TEXT + " text," +
            REACTION_REPORTED + " integer," +
            USER_ID + " integer," +
            IDEATION_ID + " integer," +
            IDEA_ID + " integer)"
    //endregion
    //region Vote
    val TBL_VOTE = "vote_table"
    val VOTE_ID = "VoteId"
    val VOTE_CONFIRMED = "Confirmed"
    val VOTE_VOTETYPE = "VoteType"

    val TABLE_VOTE_DROP = "DROP TABLE IF EXISTS " + TBL_VOTE

    val TABLE_CREATE_VOTE = "CREATE TABLE " + TBL_VOTE + " (" +
            VOTE_ID + " integer PRIMARY KEY," +
            VOTE_CONFIRMED + " integer," +
            VOTE_VOTETYPE + " integer," +
            USER_ID + " text," +
            IDEA_ID + " integer)"

    //endregio
    //endregion
    //endregion
    //region Surveys
    //region Survey
    val TBL_SURVEY = "survey_table"
    val SURVEY_ID = "SurveyId"
    val SURVEY_TITLE = "Title"

    val TABLE_SURVEY_DROP = "DROP TABLE IF EXISTS " + TBL_SURVEY

    val TABLE_CREATE_SURVEY = "CREATE TABLE " + TBL_SURVEY + " (" +
            SURVEY_ID + " integer PRIMARY KEY," +
            SURVEY_TITLE + " text," +
            PHASE_ID + " ineteger)"
    //endregion
    //region Question
    val TBL_QUESTION = "question_Table"
    val QUESTION_ID = "QuestionId"
    val QUESTION_NR = "QuestionNr"
    val QUESTION_TEXT = "QuestionText"
    val QUESTION_TYPE = "QuestionType"

    val TABLE_QUESTION_DROP = "DROP TABLE IF EXISTS " + TBL_QUESTION

    val TABLE_CREATE_QUESTION = "CREATE TABLE " + TBL_QUESTION + " (" +
            QUESTION_ID + " integer PRIMARY KEY," +
            QUESTION_NR + " integer," +
            QUESTION_TEXT + " text," +
            QUESTION_TYPE + " integer," +
            SURVEY_ID + " integer)"
    //endregion
    //region Answer
    val TBL_ANSWER ="answer_table"
    val ANSWER_ID = "AnswerId"
    val ANSWER_TEXT = "Answertext"
    val ANSWER_TOTALTIMESCHOSEN = "TotalTimesChosen"

    val TABLE_ANSWER_DROP = "DROP TABLE IF EXISTS "+TBL_ANSWER

    val TABLE_CREATE_ANSWER = "CREATE TABLE "+TBL_ANSWER+" ("+
            ANSWER_ID + " integer PRIMARY KEY,"+
            ANSWER_TEXT +" text,"+
            ANSWER_TOTALTIMESCHOSEN+" integer,"+
            QUESTION_ID + " integer)"


    //endregion
    //endregion

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(TABLE_CREATE_ADDRESS)
        db!!.execSQL(TABLE_CREATE_POSITION)
        db!!.execSQL(TABLE_CREATE_LOCATION)
        db!!.execSQL(TABLE_CREATE_PLATFORM)
        db.execSQL(TABLE_CREATE_PROJECTS)
        db!!.execSQL(TABLE_CREATE_PHASE)
        db!!.execSQL(TABLE_CREATE_USER)
        db!!.execSQL(TABLE_CREATE_IDEA)
        db!!.execSQL(TABLE_CREATE_IDEATION)
        db!!.execSQL(TABLE_CREATE_REACTION)
        db!!.execSQL(TABLE_CREATE_VOTE)
        db!!.execSQL(TABLE_CREATE_SURVEY)
        db!!.execSQL(TABLE_CREATE_QUESTION)
        db!!.execSQL(TABLE_CREATE_ANSWER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dropDb(db)
    }

    fun dropDb(db: SQLiteDatabase?) {
        db!!.execSQL(TABLE_ADDRESS_DROP)
        db.execSQL(TABLE_POSITION_DROP)
        db.execSQL(TABLE_LOCATION_DROP)
        db.execSQL(TABLE_PLATFORM_DROP)
        db.execSQL(TABLE_PROJECTS_DROP)
        db!!.execSQL(TABLE_PHASE_DROP)
        db.execSQL(TABLE_USER_DROP)
        db.execSQL(TABLE_IDEA_DROP)
        db.execSQL(TABLE_IDEATION_DROP)
        db.execSQL(TABLE_REACTION_DROP)
        db.execSQL(TABLE_VOTE_DROP)
        db.execSQL(TABLE_SURVEY_DROP)
        db.execSQL(TABLE_QUESTION_DROP)
        db.execSQL(TABLE_ANSWER_DROP)
        db.execSQL(TABLE_CREATE_ADDRESS)
        db.execSQL(TABLE_CREATE_POSITION)
        db.execSQL(TABLE_CREATE_LOCATION)
        db.execSQL(TABLE_CREATE_PLATFORM)
        db.execSQL(TABLE_CREATE_PROJECTS)
        db!!.execSQL(TABLE_CREATE_PHASE)
        db.execSQL(TABLE_CREATE_USER)
        db.execSQL(TABLE_CREATE_IDEA)
        db.execSQL(TABLE_CREATE_IDEATION)
        db.execSQL(TABLE_CREATE_REACTION)
        db.execSQL(TABLE_CREATE_VOTE)
        db.execSQL(TABLE_CREATE_SURVEY)
        db.execSQL(TABLE_CREATE_QUESTION)
        db.execSQL(TABLE_CREATE_ANSWER)
    }
}