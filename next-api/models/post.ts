import { Schema, model, models} from "mongoose";
import { post } from "../utils/types";

const postSchema = new Schema<post>({
    Title: {
        type: String,
        required: true,
    },
    Content: {
        type: String,
        required: true,
    },
    ImagePath: String,
});

export const Post = models.Post || model("Post", postSchema);