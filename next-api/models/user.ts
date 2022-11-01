import {Schema, model} from "mongoose";
import { user } from "../utils/types";

const userSchema = new Schema<user>({
    Fullname: {
        type: String, required: true,
    },
    username: {
        type: String, required: true,
    },
    password: String,
});

export const User = model<user>("User", userSchema);