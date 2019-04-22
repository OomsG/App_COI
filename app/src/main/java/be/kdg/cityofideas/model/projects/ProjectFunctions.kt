package be.kdg.cityofideas.model.projects


public fun getTestProjects(): Array<Projects> {
    return arrayOf(
        Projects(1, "Test1", null, "1-1-1999", "10-1-1099", "test", "test", 0, "active", 1, null, null),
        Projects(2, "Test2", null, "1-1-1999", "10-1-1099", "test", "test", 0, "future", 1, null, null),
        Projects(3, "Test3", null, "1-1-1999", "10-1-1099", "test", "test", 0, "past", 1, null, null)
    )
}