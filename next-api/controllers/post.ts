import { NextApiRequest, NextApiResponse } from "next";
import { Post } from "../models/post";
import { msgError } from "../utils/types";
import { IncomingForm } from "formidable";


export const createPost = async (req: NextApiRequest, res: NextApiResponse<msgError>)=>{
    // if(!req.body.Title)
    //     res.status(404).json({
    //         message: "can't create a post without a title",
    //     });
    // if(!req.body.Content)
    //     res.status(404).json({
    //         message: "can't create a post without a content",
    //     });

    console.log("inside the create controller");
    let msg = await uploadLogic(req)
    console.log(msg);
    //const post = Post.create(req.body);
};


const uploadLogic = async (req: NextApiRequest)=>{
    return new Promise((resolve, reject)=>{
        const form = new IncomingForm({multiples: true, uploadDir: './public/images'});
        form.keepExtensions = true;
        form.parse(req, (err, fields, files)=>{
            if(err)
                return reject(err);
            resolve({fields, files});
        });
    });
};