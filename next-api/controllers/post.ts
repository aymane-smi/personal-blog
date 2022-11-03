import { NextApiRequest, NextApiResponse } from "next";
import { Post } from "../models/post";
import { msgError } from "../utils/types";


export const createPost = async (req: NextApiRequest, res: NextApiResponse<msgError>)=>{
    if(!req.body.Title)
        res.status(404).json({
            message: "can't create a post without a title",
        });
    if(!req.body.Content)
        res.status(404).json({
            message: "can't create a post without a content",
        });
    const post = Post.create(req.body);
};