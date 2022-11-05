import { NextApiRequest, NextApiResponse } from "next";
import { createPost } from "../../../controllers/post";
import { msgError } from "../../../utils/types";
import { withUpload } from "../../../middleware/fileUpload";

export default async function handler(req: NextApiRequest, res: NextApiResponse<msgError>){
    if(req.method === "POST")
        createPost(req, res);
    else
        res.status(404).json({
            message: `${req.method} not supported for this endpoint`,
        });
}

export const config= {
    api:{
        bodyParser: false,
    }
};