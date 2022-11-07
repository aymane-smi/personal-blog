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

export type post = {
    Title: String,
    Content: String,
    ImagePath: String,
};

export type PostMessage = {
    message: String,
    post: post,
}

export type fieldsType = {
    Title: String,
    Content: String,
}