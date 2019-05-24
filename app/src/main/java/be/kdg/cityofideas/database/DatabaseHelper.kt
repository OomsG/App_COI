package be.kdg.cityofideas.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        // increment when you changed db schema
        private const val DB_VERSION: Int = 35
        private const val DB_NAME: String = "CityOfIdeasApp"

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

        // region Project
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

        //region User
        object UserEntry : BaseColumns {
            const val TBL_USER = "user_table"
            const val USER_ID = "UserId"
            const val USER_NAME = "Name"
            const val USER_EMAIL = "Email"
            const val USER_PASSWORD = "PasswordHash"
            const val USER_SURNAME = "Surname"
            const val USER_LAST_NAME = "LastName"
            const val USER_SEX = "Sex"
            const val USER_AGE = "Age"
            const val USER_ZIP = "ZipCode"
        }

        private const val TABLE_DROP_USER = "DROP TABLE IF EXISTS ${UserEntry.TBL_USER}"

        private const val TABLE_CREATE_USER =
            "CREATE TABLE ${UserEntry.TBL_USER} (" +
                    "${UserEntry.USER_ID} TEXT PRIMARY KEY," +
                    "${UserEntry.USER_NAME} TEXT," +
                    "${UserEntry.USER_EMAIL} TEXT UNIQUE," +
                    "${UserEntry.USER_PASSWORD} TEXT," +
                    "${UserEntry.USER_SURNAME} TEXT," +
                    "${UserEntry.USER_LAST_NAME} TEXT," +
                    "${UserEntry.USER_SEX} TEXT," +
                    "${UserEntry.USER_AGE} INTEGER," +
                    "${UserEntry.USER_ZIP} TEXT," +
                    "${PlatformEntry.PLATFORM_ID} INTEGER)"

        //endregion

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

        //region IdeaObject
        object IdeaObjectEntry : BaseColumns {
            const val TBL_IDEA_OBJECT = "idea_object_table"
            const val IDEA_OBJECT_ID = "IdeaObjectId"
            const val IDEA_OBJECT_ORDERNR = "OrderNr"
            const val IDEA_OBJECT_DISCRIMINATOR = "Discriminator"
            const val IDEA_OBJECT_IMAGE_NAME = "ImageName"
            const val IDEA_OBJECT_IMAGE_PATH = "ImagePath"
            const val IDEA_OBJECT_IMAGE_DATA = "ImageData"
            const val IDEA_OBJECT_TEXT = "Text"
            const val IDEA_OBJECT_URL = "Url"
        }

        private const val TABLE_DROP_IDEA_OBJECT = "DROP TABLE IF EXISTS ${IdeaObjectEntry.TBL_IDEA_OBJECT}"

        private const val TABLE_CREATE_IDEA_OBJECT =
            "CREATE TABLE ${IdeaObjectEntry.TBL_IDEA_OBJECT} (" +
                    "${IdeaObjectEntry.IDEA_OBJECT_ID} INTEGER PRIMARY KEY," +
                    "${IdeaObjectEntry.IDEA_OBJECT_ORDERNR} INTEGER," +
                    "${IdeaEntry.IDEA_ID} INTEGER," +
                    "${IdeaObjectEntry.IDEA_OBJECT_DISCRIMINATOR} TEXT," +
                    "${IdeaObjectEntry.IDEA_OBJECT_IMAGE_NAME} TEXT," +
                    "${IdeaObjectEntry.IDEA_OBJECT_IMAGE_PATH} TEXT," +
                    "${IdeaObjectEntry.IDEA_OBJECT_IMAGE_DATA} BLOB," +
                    "${IdeaObjectEntry.IDEA_OBJECT_TEXT} TEXT," +
                    "${IdeaObjectEntry.IDEA_OBJECT_URL} TEXT)"
        //endregion

        //region Tag
        object TagEntry : BaseColumns {
            const val TBL_IDEA_TAG = "idea_tag_table"
            const val TBL_TAG = "tag_table"
            const val TAG_ID = "TagId"
            const val TAG_NAME = "TagName"
            const val IDEA_TAG_ID = "IdeaTagId"
        }

        private const val TABLE_DROP_TAG = "DROP TABLE IF EXISTS ${TagEntry.TBL_TAG}"
        private const val TABLE_DROP_IDEA_TAG = "DROP TABLE IF EXISTS ${TagEntry.TBL_IDEA_TAG}"

        private const val TABLE_CREATE_TAG =
            "CREATE TABLE ${TagEntry.TBL_TAG} (" +
                    "${TagEntry.TAG_ID} INTEGER PRIMARY KEY," +
                    "${TagEntry.TAG_NAME} TEXT)"

        private const val TABLE_CREATE_IDEA_TAG =
            "CREATE TABLE ${TagEntry.TBL_IDEA_TAG} (" +
                    "${TagEntry.IDEA_TAG_ID} INTEGER PRIMARY KEY," +
                    "${IdeaEntry.IDEA_ID} INTEGER," +
                    "${TagEntry.TAG_ID} INTEGER)"
        //endregion

        //region Ideation
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

        //region Like
        object LikeEntry : BaseColumns {
            const val TBL_LIKE = "like_table"
            const val LIKE_ID = "LikeId"
        }

        private const val TABLE_DROP_LIKE = "DROP TABLE IF EXISTS ${LikeEntry.TBL_LIKE}"

        private const val TABLE_CREATE_LIKE =
            "CREATE TABLE ${LikeEntry.TBL_LIKE} (" +
                    "${LikeEntry.LIKE_ID} INTEGER PRIMARY KEY," +
                    "${ReactionEntry.REACTION_ID} INTEGER," +
                    "${UserEntry.USER_ID} TEXT)"
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

        //region IoT
        object IoTEntry : BaseColumns {
            const val TBL_IOT = "iot_table"
            const val IOT_CODE = "IoTCode"
        }

        private const val TABLE_DROP_IOT = "DROP TABLE IF EXISTS ${IoTEntry.TBL_IOT}"

        private const val TABLE_CREATE_IOT =
                "CREATE TABLE ${IoTEntry.TBL_IOT} (" +
                        "${IoTEntry.IOT_CODE} TEXT PRIMARY KEY," +
                        "${PositionEntry.POSITION_ID} INTEGER," +
                        "${IdeaEntry.IDEA_ID} INTEGER," +
                        "${QuestionEntry.QUESTION_ID} INTEGER)"
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
        db.execSQL(TABLE_CREATE_IDEA_OBJECT)
        db.execSQL(TABLE_CREATE_TAG)
        db.execSQL(TABLE_CREATE_IDEA_TAG)
        db.execSQL(TABLE_CREATE_IDEATION)
        db.execSQL(TABLE_CREATE_REACTION)
        db.execSQL(TABLE_CREATE_VOTE)
        db.execSQL(TABLE_CREATE_LIKE)
        db.execSQL(TABLE_CREATE_SURVEY)
        db.execSQL(TABLE_CREATE_QUESTION)
        db.execSQL(TABLE_CREATE_ANSWER)
        db.execSQL(TABLE_CREATE_IOT)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(TABLE_DROP_ADDRESS)
        db.execSQL(TABLE_DROP_POSITION)
        db.execSQL(TABLE_DROP_LOCATION)
        db.execSQL(TABLE_DROP_PLATFORM)
        db.execSQL(TABLE_DROP_PROJECT)
        db.execSQL(TABLE_DROP_PHASE)
        db.execSQL(TABLE_DROP_USER)
        db.execSQL(TABLE_DROP_IDEA)
        db.execSQL(TABLE_DROP_IDEA_OBJECT)
        db.execSQL(TABLE_DROP_TAG)
        db.execSQL(TABLE_DROP_IDEA_TAG)
        db.execSQL(TABLE_DROP_IDEATION)
        db.execSQL(TABLE_DROP_REACTION)
        db.execSQL(TABLE_DROP_VOTE)
        db.execSQL(TABLE_DROP_LIKE)
        db.execSQL(TABLE_DROP_SURVEY)
        db.execSQL(TABLE_DROP_QUESTION)
        db.execSQL(TABLE_DROP_ANSWER)
        db.execSQL(TABLE_DROP_IOT)
        onCreate(db)
    }

    //region Platform
    fun getPlatformEntry(): PlatformEntry {
        return PlatformEntry
    }
    //endregion

    //region User
    fun getUserContentValues(
        id: String,
        email: String,
        name: String?,
        password: String,
        surname: String?,
        lastName: String?,
        sex: String?,
        age: Int?,
        zip: String?
    ): ContentValues {
        return ContentValues().apply {
            put(UserEntry.USER_ID, id)
            put(UserEntry.USER_EMAIL, email)
            put(UserEntry.USER_NAME, name)
            put(UserEntry.USER_PASSWORD, password)
            put(UserEntry.USER_SURNAME, surname)
            put(UserEntry.USER_LAST_NAME, lastName)
            put(UserEntry.USER_SEX, sex)
            put(UserEntry.USER_AGE, age)
            put(UserEntry.USER_ZIP, zip)
        }
    }

    fun getUserEntry(): UserEntry {
        return UserEntry
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

    fun getProjectEntry(): ProjectEntry {
        return ProjectEntry
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

    fun getPhaseEntry(): PhaseEntry {
        return PhaseEntry
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

    fun getIdeationEntry(): IdeationEntry {
        return IdeationEntry
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

    fun getIdeaEntry(): IdeaEntry {
        return IdeaEntry
    }
    //endregion

    //region IdeaObject
    fun getIdeaObjectContentValues(
        id: Int,
        orderNr: Int?,
        ideaId: Int?,
        discriminator: String?,
        imageName: String?,
        imagePath: String?,
        imageData: ByteArray?,
        text: String?,
        url: String?
    ): ContentValues {
        return ContentValues().apply {
            put(IdeaObjectEntry.IDEA_OBJECT_ID, id)
            put(IdeaObjectEntry.IDEA_OBJECT_ORDERNR, orderNr)
            put(IdeaEntry.IDEA_ID, ideaId)
            put(IdeaObjectEntry.IDEA_OBJECT_DISCRIMINATOR, discriminator)
            put(IdeaObjectEntry.IDEA_OBJECT_IMAGE_NAME, imageName)
            put(IdeaObjectEntry.IDEA_OBJECT_IMAGE_PATH, imagePath)
            put(IdeaObjectEntry.IDEA_OBJECT_IMAGE_DATA, imageData)
            put(IdeaObjectEntry.IDEA_OBJECT_TEXT, text)
            put(IdeaObjectEntry.IDEA_OBJECT_URL, url)
        }
    }

    fun getIdeaObjectEntry(): IdeaObjectEntry {
        return IdeaObjectEntry
    }
    //endregion

    //region Tag
    fun getTagContentValues(id: Int, name: String?): ContentValues {
        return ContentValues().apply {
            put(TagEntry.TAG_ID, id)
            put(TagEntry.TAG_NAME, name)
        }
    }

    fun getIdeaTagContentValues(id: Int, ideaId: Int?): ContentValues {
        return ContentValues().apply {
            put(TagEntry.IDEA_TAG_ID, id)
            put(IdeaEntry.IDEA_ID, ideaId)
        }
    }

    fun getTagEntry(): TagEntry {
        return TagEntry
    }
    //endregion

    //region Vote
    fun getVoteContentValues(id: Int, confirmed: String, type: Int?, ideaId: Int?): ContentValues {
        return ContentValues().apply {
            put(VoteEntry.VOTE_ID, id)
            put(VoteEntry.VOTE_CONFIRMED, confirmed)
            put(VoteEntry.VOTE_VOTE_TYPE, type)
            put(IdeaEntry.IDEA_ID, ideaId)
        }
    }

    fun getVoteEntry(): VoteEntry {
        return VoteEntry
    }
    //endregion

    //region Reaction
    fun getReactionContentValues(id: Int, text: String?, reported: Boolean?): ContentValues {
        return ContentValues().apply {
            put(ReactionEntry.REACTION_ID, id)
            put(ReactionEntry.REACTION_TEXT, text)
            put(ReactionEntry.REACTION_REPORTED, reported)
        }
    }

    fun getReactionEntry(): ReactionEntry {
        return ReactionEntry
    }
    //endregion

    //region Like
    fun getLikeContentValues(id: Int, reactionId: Int?): ContentValues {
        return ContentValues().apply {
            put(LikeEntry.LIKE_ID, id)
            put(ReactionEntry.REACTION_ID, reactionId)
        }
    }

    fun getLikeEntry(): LikeEntry {
        return LikeEntry
    }
    //endregion

    //region Location
    fun getLocationContentValues(id: Int, name: String?): ContentValues {
        return ContentValues().apply {
            put(LocationEntry.LOCATION_ID, id)
            put(LocationEntry.LOCATION_NAME, name)
        }
    }

    fun getLocationEntry(): LocationEntry {
        return LocationEntry
    }
    //endregion

    //region Address
    fun getAddressContentValues(
        id: Int,
        street: String?,
        houseNr: String?,
        city: String?,
        zip: String?
    ): ContentValues {
        return ContentValues().apply {
            put(AddressEntry.ADDRESS_ID, id)
            put(AddressEntry.ADDRESS_STREET, street)
            put(AddressEntry.ADDRESS_HOUSENR, houseNr)
            put(AddressEntry.ADDRESS_CITY, city)
            put(AddressEntry.ADDRESS_ZIPCODE, zip)
        }
    }

    fun getAddressEntry(): AddressEntry {
        return AddressEntry
    }
    //endregion

    //region Position
    fun getPositionContentValues(id: Int, long: String?, lat: String?): ContentValues {
        return ContentValues().apply {
            put(PositionEntry.POSITION_ID, id)
            put(PositionEntry.POSITION_LONG, long)
            put(PositionEntry.POSITION_LAT, lat)
        }
    }

    fun getPositionEntry(): PositionEntry {
        return PositionEntry
    }
    //endregion

    //region Survey
    fun getSurveyContentValues(id: Int, title: String?, phaseId: Int?): ContentValues {
        return ContentValues().apply {
            put(SurveyEntry.SURVEY_ID, id)
            put(SurveyEntry.SURVEY_TITLE, title)
            put(PhaseEntry.PHASE_ID, phaseId)
        }
    }

    fun getSurveyEntry(): SurveyEntry {
        return SurveyEntry
    }
    //endregion

    //region Question
    fun getQuestionContentValues(id: Int, nr: Int?, text: String?, type: Int?, surveyId: Int?): ContentValues {
        return ContentValues().apply {
            put(QuestionEntry.QUESTION_ID, id)
            put(QuestionEntry.QUESTION_NR, nr)
            put(QuestionEntry.QUESTION_TEXT, text)
            put(QuestionEntry.QUESTION_TYPE, type)
            put(SurveyEntry.SURVEY_ID, surveyId)
        }
    }

    fun getQuestionEntry(): QuestionEntry {
        return QuestionEntry
    }
    //endregion

    //region Answer
    fun getAnswerContentValues(id: Int, text: String?, totalTimesChosen: Int?, questionId: Int?): ContentValues {
        return ContentValues().apply {
            put(AnswerEntry.ANSWER_ID, id)
            put(AnswerEntry.ANSWER_TEXT, text)
            put(AnswerEntry.ANSWER_TOTAL_TIMES_CHOSEN, totalTimesChosen)
            put(QuestionEntry.QUESTION_ID, questionId)
        }
    }

    fun getAnswerEntry(): AnswerEntry {
        return AnswerEntry
    }
    //endregion

    //region IoT
    fun getIoTContentValues(code: String): ContentValues {
        return ContentValues().apply {
            put(IoTEntry.IOT_CODE, code)
        }
    }

    fun getIoTEntry(): IoTEntry {
        return IoTEntry
    }
    //endregion
}