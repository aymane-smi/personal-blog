export type user = {
    Fullname: String,
    username: String,
    password: String,
};
 export type userResponse = {
    user: user,
    message: String,
 };

export type msgError = {
    message: String,
}

export type loginResponse = userResponse & {
    token: String,
}