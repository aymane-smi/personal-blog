import {Schema, model, models} from "mongoose";
import { user } from "../utils/types";

const userSchema = new Schema<user>({
    Fullname: {
        type: String, required: true,
    },
    username: {
        type: String, required: true, unique: true,
    },
    password: {type: String,},
});

export const User = models.User ||  model<user>("User", userSchema);