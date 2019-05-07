package be.kdg.cityofideas.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME: String = "CityOfIdeasApp"
        private const val DB_VERSION: Int = 1

        //region Datatypes
        //region Address
        object AddressEntry : BaseColumns {
            const val TBL_ADDRESS = "address_table"
            const val ADDRESS_ID = "AddressId"
            const val ADDRESS_STREET = "Street"
            const val ADDRESS_HOUSENR = "HouseNr"
            const val ADDRESS_CITY = "City"
            const val ADDRESS_ZIPCODE = "ZipCode"
        }

        private const val TABLE_DROP_ADDRESS = "DROP TABLE IF EXISTS ${AddressEntry.TBL_ADDRESS}"

        private const val TABLE_CREATE_ADDRESS =
            "CREATE TABLE ${AddressEntry.TBL_ADDRESS} (" +
                    "${AddressEntry.ADDRESS_ID} INTEGER PRIMARY KEY," +
                    "${AddressEntry.ADDRESS_STREET} TEXT," +
                    "${AddressEntry.ADDRESS_HOUSENR} TEXT," +
                    "${AddressEntry.ADDRESS_CITY} TEXT," +
                    "${AddressEntry.ADDRESS_ZIPCODE} TEXT)"

        //endregion

        //region Position
        object PositionEntry : BaseColumns {
            const val TBL_POSITION = "position_table"
            const val POSITION_ID = "PositionId"
            const val POSITION_LONG = "Long"
            const val POSITION_LAT = "Lat"
        }

        private const val TABLE_DROP_POSITION = "DROP TABLE IF EXISTS ${PositionEntry.TBL_POSITION}"

        private const val TABLE_CREATE_POSITION =
            "CREATE TABLE ${PositionEntry.TBL_POSITION} (" +
                    "${PositionEntry.POSITION_ID} INTEGER PRIMARY KEY," +
                    "${PositionEntry.POSITION_LAT} TEXT," +
                    "${PositionEntry.POSITION_LONG} TEXT)"

        //endregion

        //region Location
        object LocationEntry : BaseColumns {
            const val TBL_LOCATION = "location_table"
            const val LOCATION_ID = "LocationId"
            const val LOCATION_NAME = "LocationName"
        }

        private const val TABLE_DROP_LOCATION = "DROP TABLE IF EXISTS ${LocationEntry.TBL_LOCATION}"

        private const val TABLE_CREATE_LOCATION =
            "CREATE TABLE ${LocationEntry.TBL_LOCATION} (" +
                    "${LocationEntry.LOCATION_ID} INTEGER PRIMARY KEY," +
                    "${LocationEntry.LOCATION_NAME} TEXT," +
                    "${AddressEntry.ADDRESS_ID} INTEGER," +
                    "${PositionEntry.POSITION_ID} INTEGER)"

        //endregion
        //endregion

        //region Project
        //region Platform
        object PlatformEntry : BaseColumns {
            const val TBL_PLATFORM = "platform_table"
            const val PLATFORM_ID = "PlatformId"
            const val PLATFORM_NAME = "PlatformName"
            const val PLATFORM_LOGO = "Logo"
            const val PLATFORM_PHONENUMBER = "PhoneNumber"
            const val PLATFORM_DESCRIPTION = "Description"
            const val PLATFORM_BACKGROUND_IMAGE = "BackgroundImage"
        }

        private const val TABLE_DROP_PLATFORM = "DROP TABLE IF EXISTS ${PlatformEntry.TBL_PLATFORM}"

        private const val TABLE_CREATE_PLATFORM =
            "CREATE TABLE ${PlatformEntry.TBL_PLATFORM} (" +
                    "${PlatformEntry.PLATFORM_ID} INTEGER PRIMARY KEY," +
                    "${PlatformEntry.PLATFORM_NAME} TEXT," +
                    "${PlatformEntry.PLATFORM_LOGO} TEXT," +
                    "${AddressEntry.ADDRESS_ID} INTEGER," +
                    "${PlatformEntry.PLATFORM_PHONENUMBER} TEXT," +
                    "${PlatformEntry.PLATFORM_DESCRIPTION} TEXT," +
                    "${PlatformEntry.PLATFORM_BACKGROUND_IMAGE} TEXT)"

        //endregion

        // region project
        object ProjectEntry : BaseColumns {
            const val TBL_PROJECT = "project_table"
            const val PROJECT_ID = "ProjectId"
            const val PROJECT_NAME = "Name"
            const val PROJECT_LOGO = "Logo"
            const val PROJECT_STARTDATE = "StartDate"
            const val PROJECT_ENDDATE = "EndDate"
            const val PROJECT_OBJECTIVE = "Objective"
            const val PROJECT_DESCRIPTION = "Description"
            const val PROJECT_STATUS = "Status"
            const val PROJECT_BACKGROUND_IMAGE = "BackgroundImage"
        }

        private const val TABLE_DROP_PROJECT = "DROP TABLE IF EXISTS ${ProjectEntry.TBL_PROJECT}"

        private const val TABLE_CREATE_PROJECTS =
            "CREATE TABLE ${ProjectEntry.TBL_PROJECT} (" +
                    "${ProjectEntry.PROJECT_ID} INTEGER PRIMARY KEY," +
                    "${ProjectEntry.PROJECT_NAME} TEXT," +
                    "${ProjectEntry.PROJECT_LOGO} TEXT," +
                    "${ProjectEntry.PROJECT_STARTDATE} TEXT," +
                    "${ProjectEntry.PROJECT_ENDDATE} TEXT," +
                    "${ProjectEntry.PROJECT_OBJECTIVE} TEXT," +
                    "${ProjectEntry.PROJECT_DESCRIPTION} TEXT," +
                    "${ProjectEntry.PROJECT_STATUS} TEXT," +
                    "${ProjectEntry.PROJECT_BACKGROUND_IMAGE} TEXT," +
                    "${PlatformEntry.PLATFORM_ID} INTEGER," +
                    "${LocationEntry.LOCATION_ID} INTEGER)"
        //endregion

        //region Phase
        object PhaseEntry : BaseColumns {
            const val TBL_PHASE = "phase_table"
            const val PHASE_ID = "PhaseId"
            const val PHASE_NR = "PhaseNr"
            const val PHASE_NAME = "PhaseName"
            const val PHASE_DESCRIPTION = "Description"
            const val PHASE_STARTDATE = "StartDate"
            const val PHASE_ENDDATE = "EndDate"
        }

        private const val TABLE_DROP_PHASE = "DROP TABLE IF EXISTS ${PhaseEntry.TBL_PHASE}"

        private const val TABLE_CREATE_PHASE =
            "CREATE TABLE ${PhaseEntry.TBL_PHASE} (" +
                    "${PhaseEntry.PHASE_ID} INTEGER PRIMARY KEY," +
                    "${PhaseEntry.PHASE_NR} INTEGER," +
                    "${PhaseEntry.PHASE_NAME} TEXT," +
                    "${PhaseEntry.PHASE_DESCRIPTION} TEXT," +
                    "${PhaseEntry.PHASE_STARTDATE} TEXT," +
                    "${PhaseEntry.PHASE_ENDDATE} TEXT," +
                    "${ProjectEntry.PROJECT_ID} INTEGER)"

        //endregion
        //endregion

        //region user
        object UserEntry : BaseColumns {
            const val TBL_USER = "user_table"
            const val USER_ID = "Id"
            const val USER_NAME = "Name"
            const val USER_EMAIL = "Email"
            const val USER_PASSWORD = "PasswordHash"
        }

        private const val TABLE_DROP_USER = "DROP TABLE IF EXISTS ${UserEntry.TBL_USER}"

        private const val TABLE_CREATE_USER =
            "CREATE TABLE ${UserEntry.TBL_USER} (" +
                    "${UserEntry.USER_ID} TEXT PRIMARY KEY," +
                    "${UserEntry.USER_NAME} TEXT," +
                    "${UserEntry.USER_EMAIL} TEXT UNIQUE," +
                    "${UserEntry.USER_PASSWORD} TEXT," +
                    "${PlatformEntry.PLATFORM_ID} INTEGER)"

        //endregion

        //region Ideation
        //region Idea
        object IdeaEntry : BaseColumns {
            const val TBL_IDEA = "idea_table"
            const val IDEA_ID = "IdeaId"
            const val IDEA_THEME = "Theme"
            const val IDEA_REPORTED = "Reported"
            const val IDEA_TITLE = "Title"
        }

        private const val TABLE_DROP_IDEA = "DROP TABLE IF EXISTS ${IdeaEntry.TBL_IDEA}"

        private const val TABLE_CREATE_IDEA =
            "CREATE TABLE ${IdeaEntry.TBL_IDEA} (" +
                    "${IdeaEntry.IDEA_ID} INTEGER PRIMARY KEY," +
                    "${PositionEntry.POSITION_ID} INTEGER," +
                    "${IdeaEntry.IDEA_THEME} TEXT," +
                    "${IdeaEntry.IDEA_REPORTED} TEXT," +
                    "${IdeaEntry.IDEA_TITLE} TEXT," +
                    "${UserEntry.USER_ID} TEXT," +
                    "${IdeationEntry.IDEATION_ID} INTEGER)"

        //endregion

        //region ideation
        object IdeationEntry : BaseColumns {
            const val TBL_IDEATION = "ideation_table"
            const val IDEATION_ID = "IdeationId"
            const val IDEATION_CENTRAL_QUESTION = "CentralQuestion"
            const val IDEATION_INPUT_IDEATION = "InputIdeation"
        }

        private const val TABLE_DROP_IDEATION = "DROP TABLE IF EXISTS ${IdeationEntry.TBL_IDEATION}"

        private const val TABLE_CREATE_IDEATION =
            "CREATE TABLE ${IdeationEntry.TBL_IDEATION} (" +
                    "${IdeationEntry.IDEATION_ID} INTEGER PRIMARY KEY," +
                    "${IdeationEntry.IDEATION_CENTRAL_QUESTION} TEXT," +
                    "${IdeationEntry.IDEATION_INPUT_IDEATION} TEXT," +
                    "${PhaseEntry.PHASE_ID} INTEGER)"

        //endregion

        //region Reaction
        object ReactionEntry : BaseColumns {
            const val TBL_REACTION = "reaction_table"
            const val REACTION_ID = "ReactionId"
            const val REACTION_TEXT = "ReactionText"
            const val REACTION_REPORTED = "Reported"
        }

        private const val TABLE_DROP_REACTION = "DROP TABLE IF EXISTS ${ReactionEntry.TBL_REACTION}"

        private const val TABLE_CREATE_REACTION =
            "CREATE TABLE ${ReactionEntry.TBL_REACTION} (" +
                    "${ReactionEntry.REACTION_ID} INTEGER PRIMARY KEY," +
                    "${ReactionEntry.REACTION_TEXT} TEXT," +
                    "${ReactionEntry.REACTION_REPORTED} TEXT," +
                    "${UserEntry.USER_ID} TEXT," +
                    "${IdeationEntry.IDEATION_ID} INTEGER," +
                    "${IdeaEntry.IDEA_ID} INTEGER)"
        //endregion

        //region Vote
        object VoteEntry : BaseColumns {
            const val TBL_VOTE = "vote_table"
            const val VOTE_ID = "VoteId"
            const val VOTE_CONFIRMED = "Confirmed"
            const val VOTE_VOTE_TYPE = "VoteType"
        }

        private const val TABLE_DROP_VOTE = "DROP TABLE IF EXISTS ${VoteEntry.TBL_VOTE}"

        private const val TABLE_CREATE_VOTE =
            "CREATE TABLE ${VoteEntry.TBL_VOTE} (" +
                    "${VoteEntry.VOTE_ID} INTEGER PRIMARY KEY," +
                    "${VoteEntry.VOTE_CONFIRMED} TEXT," +
                    "${VoteEntry.VOTE_VOTE_TYPE} INTEGER," +
                    "${UserEntry.USER_ID} TEXT," +
                    "${IdeaEntry.IDEA_ID} INTEGER)"

        //endregion
        //endregion
        //endregion

        //region Surveys
        //region Survey
        object SurveyEntry : BaseColumns {
            const val TBL_SURVEY = "survey_table"
            const val SURVEY_ID = "SurveyId"
            const val SURVEY_TITLE = "Title"
        }

        private const val TABLE_DROP_SURVEY = "DROP TABLE IF EXISTS ${SurveyEntry.TBL_SURVEY}"

        private const val TABLE_CREATE_SURVEY =
            "CREATE TABLE ${SurveyEntry.TBL_SURVEY} (" +
                    "${SurveyEntry.SURVEY_ID} INTEGER PRIMARY KEY," +
                    "${SurveyEntry.SURVEY_TITLE} TEXT," +
                    "${PhaseEntry.PHASE_ID} INTEGER)"
        //endregion

        //region Question
        object QuestionEntry : BaseColumns {
            const val TBL_QUESTION = "question_Table"
            const val QUESTION_ID = "QuestionId"
            const val QUESTION_NR = "QuestionNr"
            const val QUESTION_TEXT = "QuestionText"
            const val QUESTION_TYPE = "QuestionType"
        }

        private const val TABLE_DROP_QUESTION = "DROP TABLE IF EXISTS ${QuestionEntry.TBL_QUESTION}"

        private const val TABLE_CREATE_QUESTION =
            "CREATE TABLE ${QuestionEntry.TBL_QUESTION} (" +
                    "${QuestionEntry.QUESTION_ID} INTEGER PRIMARY KEY," +
                    "${QuestionEntry.QUESTION_NR} INTEGER," +
                    "${QuestionEntry.QUESTION_TEXT} TEXT," +
                    "${QuestionEntry.QUESTION_TYPE} INTEGER," +
                    "${SurveyEntry.SURVEY_ID} INTEGER)"
        //endregion

        //region Answer
        object AnswerEntry : BaseColumns {
            const val TBL_ANSWER = "answer_table"
            const val ANSWER_ID = "AnswerId"
            const val ANSWER_TEXT = "AnswerText"
            const val ANSWER_TOTAL_TIMES_CHOSEN = "TotalTimesChosen"
        }

        private const val TABLE_DROP_ANSWER = "DROP TABLE IF EXISTS ${AnswerEntry.TBL_ANSWER}"

        private const val TABLE_CREATE_ANSWER =
            "CREATE TABLE ${AnswerEntry.TBL_ANSWER} (" +
                    "${AnswerEntry.ANSWER_ID} INTEGER PRIMARY KEY," +
                    "${AnswerEntry.ANSWER_TEXT} TEXT," +
                    "${AnswerEntry.ANSWER_TOTAL_TIMES_CHOSEN} INTEGER," +
                    "${QuestionEntry.QUESTION_ID} INTEGER)"

        //endregion
        //endregion
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(TABLE_CREATE_ADDRESS)
        db.execSQL(TABLE_CREATE_POSITION)
        db.execSQL(TABLE_CREATE_LOCATION)
        db.execSQL(TABLE_CREATE_PLATFORM)
        db.execSQL(TABLE_CREATE_PROJECTS)
        db.execSQL(TABLE_CREATE_PHASE)
        db.execSQL(TABLE_CREATE_USER)
        db.execSQL(TABLE_CREATE_IDEA)
        db.execSQL(TABLE_CREATE_IDEATION)
        db.execSQL(TABLE_CREATE_REACTION)
        db.execSQL(TABLE_CREATE_VOTE)
        db.execSQL(TABLE_CREATE_SURVEY)
        db.execSQL(TABLE_CREATE_QUESTION)
        db.execSQL(TABLE_CREATE_ANSWER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dropDb(db)
    }

    fun dropDb(db: SQLiteDatabase?) {
        db!!.execSQL(TABLE_DROP_ADDRESS)
        db.execSQL(TABLE_DROP_POSITION)
        db.execSQL(TABLE_DROP_LOCATION)
        db.execSQL(TABLE_DROP_PLATFORM)
        db.execSQL(TABLE_DROP_PROJECT)
        db.execSQL(TABLE_DROP_PHASE)
        db.execSQL(TABLE_DROP_USER)
        db.execSQL(TABLE_DROP_IDEA)
        db.execSQL(TABLE_DROP_IDEATION)
        db.execSQL(TABLE_DROP_REACTION)
        db.execSQL(TABLE_DROP_VOTE)
        db.execSQL(TABLE_DROP_SURVEY)
        db.execSQL(TABLE_DROP_QUESTION)
        db.execSQL(TABLE_DROP_ANSWER)
        db.execSQL(TABLE_CREATE_ADDRESS)
        db.execSQL(TABLE_CREATE_POSITION)
        db.execSQL(TABLE_CREATE_LOCATION)
        db.execSQL(TABLE_CREATE_PLATFORM)
        db.execSQL(TABLE_CREATE_PROJECTS)
        db.execSQL(TABLE_CREATE_PHASE)
        db.execSQL(TABLE_CREATE_USER)
        db.execSQL(TABLE_CREATE_IDEA)
        db.execSQL(TABLE_CREATE_IDEATION)
        db.execSQL(TABLE_CREATE_REACTION)
        db.execSQL(TABLE_CREATE_VOTE)
        db.execSQL(TABLE_CREATE_SURVEY)
        db.execSQL(TABLE_CREATE_QUESTION)
        db.execSQL(TABLE_CREATE_ANSWER)
    }

    //region User
    fun getUserContentValues(id: String, email: String, name: String?, password: String): ContentValues {
        return ContentValues().apply {
            put(UserEntry.USER_ID, id)
            put(UserEntry.USER_EMAIL, email)
            put(UserEntry.USER_NAME, name)
            put(UserEntry.USER_PASSWORD, password)
        }
    }

    fun getUserTable(): String {
        return UserEntry.TBL_USER
    }
    //endregion

    //region Project
    fun getProjectContentValues(
        id: Int,
        name: String?,
        start: String?,
        end: String?,
        objective: String?,
        description: String?,
        status: String?
    ): ContentValues {
        return ContentValues().apply {
            put(ProjectEntry.PROJECT_ID, id)
            put(ProjectEntry.PROJECT_NAME, name)
            put(ProjectEntry.PROJECT_STARTDATE, start)
            put(ProjectEntry.PROJECT_ENDDATE, end)
            put(ProjectEntry.PROJECT_OBJECTIVE, objective)
            put(ProjectEntry.PROJECT_DESCRIPTION, description)
            put(ProjectEntry.PROJECT_STATUS, status)
        }
    }

    fun getProjectTable(): String {
        return ProjectEntry.TBL_PROJECT
    }
    //endregion

    //region Phase
    fun getPhaseContentValues(
        id: Int,
        name: String?,
        nr: Int?,
        description: String?,
        start: String?,
        end: String?,
        projectId: Int?
    ): ContentValues {
        return ContentValues().apply {
            put(PhaseEntry.PHASE_ID, id)
            put(PhaseEntry.PHASE_NAME, name)
            put(PhaseEntry.PHASE_NR, nr)
            put(PhaseEntry.PHASE_DESCRIPTION, description)
            put(PhaseEntry.PHASE_STARTDATE, start)
            put(PhaseEntry.PHASE_ENDDATE, end)
            put(ProjectEntry.PROJECT_ID, projectId)
        }
    }

    fun getPhaseTable(): String {
        return PhaseEntry.TBL_PHASE
    }
    //endregion

    //region Ideation
    fun getIdeationContentValues(id: Int, question: String?, input: Boolean?, phaseId: Int?): ContentValues {
        return ContentValues().apply {
            put(IdeationEntry.IDEATION_ID, id)
            put(IdeationEntry.IDEATION_CENTRAL_QUESTION, question)
            put(IdeationEntry.IDEATION_INPUT_IDEATION, input)
            put(PhaseEntry.PHASE_ID, phaseId)
        }
    }

    fun getIdeationTable(): String {
        return IdeationEntry.TBL_IDEATION
    }
    //endregion

    //region Idea
    fun getIdeaContentValues(id: Int, reported: Boolean?, title: String?, ideationId: Int?): ContentValues {
        return ContentValues().apply {
            put(IdeaEntry.IDEA_ID, id)
            put(IdeaEntry.IDEA_REPORTED, reported)
            put(IdeaEntry.IDEA_TITLE, title)
            put(IdeationEntry.IDEATION_ID, ideationId)
        }
    }

    fun getIdeaTable(): String {
        return IdeaEntry.TBL_IDEA
    }
    //endregion
}