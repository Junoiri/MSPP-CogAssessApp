package firebase

/**
 * Fetches games for the currently logged in user from Firebase Firestore.
 *
 * @param onComplete A callback function that is invoked when the game fetching operation is complete.
 * The function takes a List of GameDto objects as a parameter. If the game fetching is successful,
 * the List contains the fetched games. If the game fetching fails, the List is null.
 */
data class UserDataDto(
    var email: String = "",
    var name: String = "",
    var sex: String = "",
    var age: Int = 0,
)

{
    constructor() : this("", "", "", 0)
}