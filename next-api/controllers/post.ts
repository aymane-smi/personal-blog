import { NextApiRequest, NextApiResponse } from "next";
import { Post } from "../models/post";
import { fieldsType, msgError, post, PostMessage } from "../utils/types";
import { IncomingForm } from "formidable";
let FILE_NAME:String = "";


export const createPost = async (req: NextApiRequest, res: NextApiResponse<msgError | PostMessage>)=>{
    try{
        let {fields}:fieldsType = await uploadLogic(req);
        if(!('Title' in fields) || fields.Title === '')
            return res.status(404).json({
                message: "Post Title not found!",
            });
        else if(!('Content' in fields) || fields.Content === '')
            return res.status(404).json({
                message: "post Content not found!",
            });
        else{
            const obj:post = {
                ...fields,
                ImagePath: '.public/images/'+FILE_NAME,
            }
            const newPost = await Post.create(obj);
            if(!newPost)
                return res.status(404).json({
                    message: "cannot create a new post!",
                });
            else
                return res.status(200).json({
                    message: "new post created!",
                    post: obj,
                });
        }
    }catch(e){
        console.log(e);
        return res.status(500).json({
            message: "somthing went wrong!",
        })
    }
};


const uploadLogic = async (req: NextApiRequest)=>{
    return new Promise((resolve, reject)=>{
        const form = new IncomingForm({multiples: true, uploadDir: './public/images', keepExtensions: true, filename: (name, ext, part, form)=>{
            FILE_NAME = `${name}${ext}`;
            return FILE_NAME;
        }});
        form.parse(req, (err, fields, files)=>{
            if(err)
                return reject(err);
            resolve({fields, files});
        });
    });
};

// const updatePost = async (req: NextApiRequest, res: NextApiResponse)=>{

// };