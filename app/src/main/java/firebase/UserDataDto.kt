package firebase

data class UserDataDto(
    var email: String = "",
    var name: String = "",
    var sex: String = "",
    var age: Int = 0,
)

{
    constructor() : this("", "", "", 0)
}